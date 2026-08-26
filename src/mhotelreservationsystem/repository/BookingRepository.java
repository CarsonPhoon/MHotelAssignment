/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.entity.Booking;
import mhotelreservationsystem.entity.BookingStatus;
import mhotelreservationsystem.entity.RoomType;
import mhotelreservationsystem.utility.FileUtility;
import mhotelreservationsystem.utility.FilePath;
/**
 *
 * @author phoon
 */
public class BookingRepository {

    // Use an ArrayList to store reservation data
    private ArrayListADT<Booking> bookings;

    public BookingRepository() {

        bookings = new ArrayListADT<Booking>();

        if (FileUtility.fileExists(FilePath.BOOKING_FILE)) {
            loadFromFile();
        }

    }
    
    // CRUD operation
    public boolean addBooking(Booking booking){
        // // ArrayList addition O(1)
        boolean success = bookings.add(booking); 

        if(success){
            saveToFile();
        }

        return success;
    }

    public Booking getBooking(int index){
        return bookings.get(index);
    }

    public int getTotalBooking(){
        return bookings.getNumberOfEntries();
    }

    public Booking[] getAllBookings(){
        Booking[] result = new Booking[bookings.getNumberOfEntries()];
        for (int i = 0; i < bookings.getNumberOfEntries(); i++) {
            result[i] = bookings.get(i);
        }
        return result;
    }

    public Booking searchBooking(String bookingID){

        for(int i = 0; i < bookings.getNumberOfEntries(); i++){
            Booking b = bookings.get(i);
            if(b.getBookingID().equalsIgnoreCase(bookingID)){
                return b;
            }
        }
        return null;
    }

    public boolean updateBooking(Booking booking){

        for(int i = 0; i < bookings.getNumberOfEntries(); i++){
            Booking b = bookings.get(i);
            if(b.getBookingID().equalsIgnoreCase(booking.getBookingID())){
                bookings.replace(i, booking);
                saveToFile();
                return true;
            }
        }
        return false;
    }
   
    // Load data from txt file
    private void loadFromFile(){

        try{
            BufferedReader reader = FileUtility.openReader(FilePath.BOOKING_FILE);
            String line;

            while((line = reader.readLine()) != null){

                if(line.trim().isEmpty()){
                    continue;
                }
                
                Booking booking = convertToBooking(line);
                
                if(booking != null){
                    bookings.add(booking);
                }
            }
            reader.close();
        }catch(IOException e){
            System.out.println("Error loading Booking.txt");

        }
    }
    
    // Save data to txt file
    public void saveToFile(){

        try{
            BufferedWriter writer = FileUtility.openWriter(FilePath.BOOKING_FILE);
            for(int i = 0; i < bookings.getNumberOfEntries(); i++){
                writer.write(convertToString(bookings.get(i)));
                writer.newLine();
            }
            writer.close();
        }catch(IOException e){
            System.out.println("Error saving Booking.txt");
        }
    }
    
    private Booking convertToBooking(String line){
        String[] data = line.split("\\|");

        if(data.length != 10){
            return null;
        }

        return new Booking(
                data[0],                                // BookingID
                data[1],                                // Confirmation Number
                Integer.parseInt(data[2]),              // Room Number
                RoomType.fromDisplayName(data[3]),      // Room Type
                Integer.parseInt(data[4]),              // Number Of Guests
                LocalDate.parse(data[5]),               // Booking Date
                LocalDate.parse(data[6]),               // Check In
                LocalDate.parse(data[7]),               // Check Out
                Double.parseDouble(data[8]),            // Total Amount
                BookingStatus.fromDisplayName(data[9])  // Booking Status
        );
    }
    
    private String convertToString(Booking booking){
        return booking.getBookingID() + "|" +
               booking.getConfirmationNumber() + "|" +
               booking.getRoomNumber() + "|" +
               booking.getRoomType() + "|" +
               booking.getNumberOfGuests() + "|" +
               booking.getBookingDate() + "|" +
               booking.getCheckInDate() + "|" +
               booking.getCheckOutDate() + "|" +
               booking.getTotalAmount() + "|" +
               booking.getBookingStatus();
    }
}
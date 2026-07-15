/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
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

    private Booking[] bookings;

    private int size;

    public BookingRepository() {

        bookings = new Booking[100];
        size = 0;

        if (FileUtility.fileExists(FilePath.BOOKING_FILE)) {
            loadFromFile();
        }

    }
    
    // CRUD operation
    public boolean addBooking(Booking booking){

        if(size >= bookings.length){
            return false;
        }

        bookings[size++] = booking;

        saveToFile();

        return true;
    }

    public Booking getBooking(int index){

        if(index < 0 || index >= size){
            return null;
        }

        return bookings[index];
    }

    public int getTotalBooking(){
        return size;
    }

    public void displayAllBookings(){

        System.out.println();
        System.out.println("==============================================================");
        System.out.printf("%-8s %-10s %-10s %-5s %-10s %-15s\n",
                "Book ID",
                "Confirm",
                "Room",
                "Guest",
                "Amount",
                "Status");
        System.out.println("==============================================================");

        for(int i = 0; i < size; i++){
            System.out.println(bookings[i]);
        }

        System.out.println("==============================================================");
        System.out.println("Total Booking : " + size);
    }
    
    public Booking searchBooking(String bookingID){

        for(int i = 0; i < size; i++){

            if(bookings[i].getBookingID().equalsIgnoreCase(bookingID)){
                return bookings[i];
            }

        }

        return null;
    }

    public boolean updateBooking(Booking booking){

        for(int i = 0; i < size; i++){

            if(bookings[i].getBookingID().equalsIgnoreCase(booking.getBookingID())){

                bookings[i] = booking;

                saveToFile();

                return true;
            }

        }

        return false;
    }
    
    public boolean removeBooking(String bookingID){

        for(int i = 0; i < size; i++){

            if(bookings[i].getBookingID().equalsIgnoreCase(bookingID)){

                for(int j = i; j < size - 1; j++){

                    bookings[j] = bookings[j + 1];

                }

                bookings[size - 1] = null;

                size--;

                saveToFile();

                return true;
            }

        }

        return false;
    }
    
    private void loadFromFile(){

        size = 0;

        try{

            BufferedReader reader =
                    FileUtility.openReader(FilePath.BOOKING_FILE);

            String line;

            while((line = reader.readLine()) != null){

                if(line.trim().isEmpty()){
                    continue;
                }

                Booking booking = convertToBooking(line);

                if(booking != null){
                    bookings[size++] = booking;
                }

            }

            reader.close();

        }catch(IOException e){

            System.out.println("Error loading Booking.txt");

        }

    }
    
    public void saveToFile(){

        try{

            BufferedWriter writer =
                    FileUtility.openWriter(FilePath.BOOKING_FILE);

            for(int i = 0; i < size; i++){

                writer.write(convertToString(bookings[i]));

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
                RoomType.valueOf(data[3]),              // Room Type
                Integer.parseInt(data[4]),              // Number Of Guests
                LocalDate.parse(data[5]),               // Booking Date
                LocalDate.parse(data[6]),               // Check In
                LocalDate.parse(data[7]),               // Check Out
                Double.parseDouble(data[8]),            // Total Amount
                BookingStatus.valueOf(data[9])          // Booking Status
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
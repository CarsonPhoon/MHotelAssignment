/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.repository;


import java.time.LocalDate;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.entity.Booking;
import mhotelreservationsystem.entity.BookingStatus;
import mhotelreservationsystem.entity.RoomType;
/**
 *
 * @author phoon
 */
public class BookingRepository {

    private ArrayListADT<Booking> bookingList;

    public BookingRepository() {

        bookingList = new ArrayListADT<>();
        initializeSampleData();
    }

    // Sample data
    
    private void initializeSampleData() {

        bookingList.add(new Booking(
                "BK1001",
                "10000001",
                101,
                RoomType.DELUXE,
                2,
                LocalDate.of(2026,7,1),
                LocalDate.of(2026,7,5),
                LocalDate.of(2026,7,8),
                840,
                BookingStatus.CONFIRMED));

        bookingList.add(new Booking(
                "BK1002",
                "10000002",
                102,
                RoomType.STANDARD,
                1,
                LocalDate.of(2026,7,2),
                LocalDate.of(2026,7,10),
                LocalDate.of(2026,7,12),
                400,
                BookingStatus.CHECKED_IN));

        bookingList.add(new Booking(
                "BK1003",
                "10000003",
                201,
                RoomType.FAMILY,
                4,
                LocalDate.of(2026,7,3),
                LocalDate.of(2026,7,15),
                LocalDate.of(2026,7,18),
                1350,
                BookingStatus.CONFIRMED));

    }
    
    // CRUD operation
    
    public boolean addBooking(Booking booking){
        return bookingList.add(booking);
    }

    public Booking getBooking(int index){
        return bookingList.get(index);
    }

    public int getTotalBooking(){
        return bookingList.getNumberOfEntries();
    }

    public void displayAllBookings(){

        System.out.println();
        System.out.println("==============================================================");
        System.out.printf("%-8s %-10s %-6s %-10s %-5s %-10s %-15s\n",
                "Book ID",
                "Confirm",
                "Room",
                "Type",
                "Guest",
                "Amount",
                "Status");
        System.out.println("==============================================================");

        for(int i=0;i<bookingList.getNumberOfEntries();i++){
            System.out.println(bookingList.get(i));
        }
    }

    public ArrayListADT<Booking> getBookingList(){
        return bookingList;
    }
}
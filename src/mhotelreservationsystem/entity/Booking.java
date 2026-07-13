/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.entity;


import java.time.LocalDate;
/**
 *
 * @author phoon
 */
public class Booking {

    private String bookingID;
    private String confirmationNumber;
    private int roomNumber;
    private RoomType roomType;
    private int numberOfGuests;
    private LocalDate bookingDate;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalAmount;
    private BookingStatus bookingStatus;
    
    public Booking() {}

    public Booking(String bookingID, String confirmationNumber, int roomNumber, RoomType roomType, int numberOfGuests, LocalDate bookingDate, LocalDate checkInDate, LocalDate checkOutDate, double totalAmount, BookingStatus bookingStatus) {
        this.bookingID = bookingID;
        this.confirmationNumber = confirmationNumber;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.numberOfGuests = numberOfGuests;
        this.bookingDate = bookingDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }
    
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }


    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    @Override
    public String toString() {

        return String.format(
                "%-8s %-10s %-6d %-10s %-5d RM%-8.2f %-15s",
                bookingID,
                confirmationNumber,
                roomNumber,
                roomType,
                numberOfGuests,
                totalAmount,
                bookingStatus
        );
    }
}

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
public class Guest{
    
    private String confirmationNumber;
    private String guestName;
    private String phoneNumber;
    private String email;
    private String bookingID;
    private int roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private GuestStatus status;
    
    public Guest() {}
    
    public Guest (String confirmationNumber, String guestName, String phoneNumber, String email, String bookingID, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate, GuestStatus status){
    
        this.confirmationNumber = confirmationNumber;
        this.guestName = guestName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bookingID = bookingID;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
    }
    
    public void setConfirmationNumber(String confirmationNumber){
        this.confirmationNumber = confirmationNumber;
    }
    
    public String getConfirmationNumber() {
        return confirmationNumber;
    }
    
    public void setGuestName(String guestName){
        this.guestName = guestName;
    }
    
    public String getGuestName() {
        return guestName;
    }
    
    public void setPhoneNumber (String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setBookingID(String bookingID){
        this.bookingID = bookingID;
    }
    
    public String getBookingID() {
        return bookingID;
    }
    
    public void setRoomNumber (int roomNumber){
        this.roomNumber = roomNumber;
    }  
    
    public int getRoomNumber() {
        return roomNumber;
    }
    
    public void setCheckInDate(LocalDate checkInDate){
        this.checkInDate = checkInDate;
    } 
    
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    
    public void setCheckOutDate (LocalDate checkOutDate){
        this.checkOutDate = checkOutDate;
    }
    
    public LocalDate getCheckOutDate(){
        return checkOutDate;
    }
    
    public void setStatus(GuestStatus status){
        this.status = status;
    }
    
    public GuestStatus getStatus() {
        return status;
    }
    
    @Override
    public String toString() {
    
        return String.format(
                "%-12s %-20s %-6d %-12s",
                confirmationNumber,
                guestName,
                roomNumber,
                status
        );
    }
}

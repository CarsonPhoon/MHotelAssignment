/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mhotelreservationsystem.entity;

/**
 *
 * @author phoon
 */
public enum BookingStatus {
    
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    CHECKED_IN("Checked In"),
    CHECKED_OUT("Checked Out"),
    CANCELLED("Cancelled");
    
    private final String displayName;
    
    BookingStatus(String displayName){
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}

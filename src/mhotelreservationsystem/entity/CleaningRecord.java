/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.entity;

import java.time.LocalDateTime;

/**
 *
 * @author chanzj
 */
public class CleaningRecord {
    private int roomNumber;
    private String staffID;
    private String staffName;
    private LocalDateTime completedAt;
    
    // creates a completed cleaning record
    public CleaningRecord(int roomNumber, String staffID, String staffName, LocalDateTime completedAt){
        this.roomNumber = roomNumber;
        this.staffID = staffID;
        this.staffName = staffName;
        this.completedAt = completedAt;
    }
    
    // gets room number
    public int getRoomNumber(){
        return roomNumber;
    }
    
    // gets staff ID
    public String getStaffID(){
        return staffID;
    }
    
    // gets staff name
    public String getStaffName(){
        return staffName;
    }
    
    // gets completion time
    public LocalDateTime getCompletedAt(){
        return completedAt;
    }
}

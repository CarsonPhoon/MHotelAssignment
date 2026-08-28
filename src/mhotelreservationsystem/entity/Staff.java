/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.entity;

/**
 *
 * @author chanzj
 */
public class Staff {
    private String staffID;
    private String staffName;
    private boolean available;
    private int assignedRoom;
    
    // creates a new available staff member
    public Staff(String staffID, String staffName){
        this.staffID = staffID;
        this.staffName = staffName;
        this.available = true;
        this.assignedRoom = -1; // -1 means not currently assigned
    }

    // gets staf ID
    public String getStaffID(){
        return this.staffID;
    }

    // sets staff ID
    public void setStaffID(String staffID){
        this.staffID = staffID;
    }

    // gets staff name
    public String getStaffName(){
        return this.staffName;
    }

    // sets staff name 
    public void setStaffName(String staffName){
        this.staffName = staffName;
    }

    // check availability
    public boolean isAvailable(){
        return this.available;
    }

    // gets assigned room
    public int getAssignedRoom(){
        return this.assignedRoom;
    }

    // assigned staff to a room
    public void assignToRoom(int roomNumber){
        this.assignedRoom = roomNumber;
        this.available = false;
    }

    // frees the staff after cleaning task completion
    public void completeCleaningTask(){
        this.assignedRoom = -1;
        this.available = true;
    }
}

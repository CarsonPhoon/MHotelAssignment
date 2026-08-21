/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.entity;

/**
 *
 * @author user
 */
public class Staff {
    private String staffID;
    private String staffName;
    private boolean available;
    private int assignedRoom;
    
    public Staff(String staffID, String staffName){
        this.staffID = staffID;
        this.staffName = staffName;
        this.available = true;
        this.assignedRoom = -1; // -1 means not currently assigned
    }

    public String getStaffID(){
        return this.staffID;
    }

    public void setStaffID(String staffID){
        this.staffID = staffID;
    }

    public String getStaffName(){
        return this.staffName;
    }

    public void setStaffName(String staffName){
        this.staffName = staffName;
    }

    public boolean isAvailable(){
        return this.available;
    }

    public int getAssignedRoom(){
        return this.assignedRoom;
    }

    public void assignToRoom(int roomNumber){
        this.assignedRoom = roomNumber;
        this.available = false;
    }

    public void completeCleaningTask(){
        this.assignedRoom = -1;
        this.available = true;
    }
}

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
    
    public Staff(String staffID, String staffName){
        this.staffID = staffID;
        this.staffName = staffName;
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
}

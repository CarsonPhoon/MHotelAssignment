/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mhotelreservationsystem.entity;

/**
 *
 * @author phoon
 */
public enum RoomStatus {

    AVAILABLE("Available"),
    OCCUPIED("Occupied"),
    RESERVED("Reserved"),
    CLEANING("Cleaning"),
    MAINTENANCE("Maintenance");

    private final String displayName;

    RoomStatus(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String toString(){
        return displayName;
    }

}
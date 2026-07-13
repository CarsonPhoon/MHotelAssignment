/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.entity;


/**
 *
 * @author phoon
 */
public class Room {
    
    private int roomNumber;
    private RoomType roomType;
    private int floor;
    private int capacity;
    private double roomRate;
    private RoomStatus status;
    
    public Room() {}
    
    public Room(int roomNumber, RoomType roomType, int floor, int capacity, double roomRate,  RoomStatus status){
    
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.floor = floor;
        this.capacity = capacity;
        this.roomRate = roomRate;
        this.status = status;
    }
    
    public void setRoomNumber(int roomNumber){
        this.roomNumber = roomNumber;
    }
        
    public int getRoomNumber(){
        return roomNumber;
    }
    
    public void setRoomType(RoomType roomType){
        this.roomType = roomType; 
    }

    public RoomType getRoomType(){
        return roomType;
    }
    
    public void setFloor(int floor){
        this.floor = floor;
    }
    
    public int getFloor(){
        return floor;
    }
    
    public void setCapicity(int capacity){
        this.capacity = capacity;
    }
    
    public int getCapicity(){
        return capacity;
    }
      
    public void setRoomRate(double roomRate){
        this.roomRate = roomRate;
    }
    
    public double getRoomRate(){
        return roomRate;
    }
    
    public void setStatus(RoomStatus status){
        this.status = status;
    }
    
    public RoomStatus getStatus(){
        return status;
    }

    @Override
    public String toString() {
    
    return String.format(
           "%-8d %-10s %-6d %-8d RM %-8.2f %-15s",
           roomNumber,
           roomType,
           floor,
           capacity,
           roomRate,
           status
       );
    }
}

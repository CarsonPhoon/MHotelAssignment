/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.repository;


import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.entity.Room;
import mhotelreservationsystem.entity.RoomStatus;
import mhotelreservationsystem.entity.RoomType;
/**
 *
 * @author phoon
 */
public class RoomRepository {

    private ArrayListADT<Room> roomList;

    public RoomRepository(){
        roomList=new ArrayListADT<>();
        initializeSampleData();
    }

    // Sample data
    
    private void initializeSampleData(){
        roomList.add(new Room(101,RoomType.DELUXE,1,2,280,RoomStatus.AVAILABLE));
        roomList.add(new Room(102,RoomType.STANDARD,1,2,200,RoomStatus.OCCUPIED));
        roomList.add(new Room(201,RoomType.FAMILY,2,4,450,RoomStatus.RESERVED));
        roomList.add(new Room(301,RoomType.VIP,3,2,650,RoomStatus.AVAILABLE));
        roomList.add(new Room(401,RoomType.SUITE,4,3,800,RoomStatus.OCCUPIED));
    }

    public boolean addRoom(Room room){
        return roomList.add(room);
    }

    public Room getRoom(int index){
        return roomList.get(index);
    }

    public int getTotalRoom(){
        return roomList.getNumberOfEntries();
    }

    public void displayAllRooms(){

        System.out.println();
        System.out.println("==============================================================");
        for(int i=0;i<roomList.getNumberOfEntries();i++){
            System.out.println(roomList.get(i));
        }
    }

    public ArrayListADT<Room> getRoomList(){
        return roomList;
    }
}
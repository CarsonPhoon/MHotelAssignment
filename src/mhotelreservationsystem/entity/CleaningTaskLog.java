/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.entity;


import java.util.HashMap;
import java.util.Map;
import mhotelreservationsystem.adt.StackInterface;
import mhotelreservationsystem.adt.LinkedStack;

/**
 *
 * @author user
 */
public class CleaningTaskLog {
    // private String roomID;
    // private StackInterface<CleaningTaskLog> statusHistory;
    private Map<Integer, StackInterface<RoomCleaningStatus>> roomHistories;

    public CleaningTaskLog(){
        this.roomHistories = new HashMap<>();
    }

    public void logStatusChange(int roomNumber, RoomCleaningStatus newStatus){
        roomHistories.putIfAbsent(roomNumber, new LinkedStack<>());
        roomHistories.get(roomNumber).push(newStatus);
    }

    public RoomCleaningStatus rollback(int roomNumber){
        StackInterface<RoomCleaningStatus> stack = roomHistories.get(roomNumber);
        if (stack == null || stack.isEmpty()){
            System.out.println("No status history to rollback for the room, " + roomNumber);
            return null;
        }
        return stack.pop();
    }

    public RoomCleaningStatus getCurrentStatus(int roomNumber){
        StackInterface<RoomCleaningStatus> stack = roomHistories.get(roomNumber);
        if (stack == null || stack.isEmpty()){
            return null;
        }
        return stack.peek();
    }

    public boolean hasRoom(int roomNumber){
        return roomHistories.containsKey(roomNumber); // 
    }

}

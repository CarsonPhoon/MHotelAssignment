    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.entity;


import mhotelreservationsystem.adt.ListInterface;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.adt.StackInterface;
import mhotelreservationsystem.adt.LinkedStack;

/**
 *
 * @author chanzj
 */
public class CleaningTaskLog {
    private ListInterface<RoomHistoryEntry> roomHistories;

    // create empty log
    public CleaningTaskLog(){
        this.roomHistories = new ArrayListADT<>();
    }

    // pushes a new status for a room
    public void logStatusChange(int roomNumber, RoomCleaningStatus newStatus){
        RoomHistoryEntry entry = findEntry(roomNumber);
        if (entry == null){
            entry = new RoomHistoryEntry(roomNumber);
            roomHistories.add(entry);
        }
        entry.getHistory().push(newStatus);
    }

    // pops the last status off a room's history
    public RoomCleaningStatus rollback(int roomNumber){
        RoomHistoryEntry entry = findEntry(roomNumber);
        if (entry == null || entry.getHistory().isEmpty()){
            System.out.println("No status history to rollback for the room, " + roomNumber);
            return null;
        }
        return entry.getHistory().pop();
    }

    // peeks the current status of a room
    public RoomCleaningStatus getCurrentStatus(int roomNumber){
        RoomHistoryEntry entry = findEntry(roomNumber);
        if (entry == null || entry.getHistory().isEmpty()){
            return null;
        }
        return entry.getHistory().peek();
    }

    // prints a room's full history
    public void getRoomStatusHistories(int roomNumber){
        RoomHistoryEntry entry = findEntry(roomNumber);
        if(entry == null || entry.getHistory().isEmpty()){
            System.out.println("No history recorded for room " + roomNumber);
            return;
        }

        StackInterface<RoomCleaningStatus> stack = entry.getHistory();
        StackInterface<RoomCleaningStatus> tempStore = new LinkedStack();
        while(!stack.isEmpty()){
            RoomCleaningStatus status = stack.pop();
            System.out.println("- " + status.getLabel());
            tempStore.push(status);
        }

        while(!tempStore.isEmpty()){
            stack.push(tempStore.pop());
        }
    }

    // checks if a room has any history
    public boolean hasRoom(int roomNumber){
        return findEntry(roomNumber) != null; // 
    }

    // finds a room's history entry
    private RoomHistoryEntry findEntry(int roomNumber){
        for (int i = 0; i < roomHistories.getNumberOfEntries(); i++){
            RoomHistoryEntry entry = roomHistories.get(i);
            if (entry.getRoomNumber() == roomNumber){
                return entry;
            }
        }
        return null;
    }

}

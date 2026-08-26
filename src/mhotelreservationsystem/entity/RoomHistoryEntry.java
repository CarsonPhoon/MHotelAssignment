package mhotelreservationsystem.entity;

import mhotelreservationsystem.adt.StackInterface;
import mhotelreservationsystem.adt.LinkedStack;

/**
 *
 * @author chanzj
 */

public class RoomHistoryEntry {
    private int roomNumber;
    private StackInterface<RoomCleaningStatus> statusHistory;

    public RoomHistoryEntry(int roomNumber) {
        this.roomNumber = roomNumber;
        this.statusHistory = new LinkedStack<>(); 
    }

    public int getRoomNumber(){
        return roomNumber;
    }

    public StackInterface<RoomCleaningStatus> getHistory(){
        return statusHistory;
    }
}

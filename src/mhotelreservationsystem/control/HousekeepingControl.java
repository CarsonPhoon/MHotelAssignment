/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.control;

import mhotelreservationsystem.entity.CleaningTaskLog;
import mhotelreservationsystem.entity.RoomCleaningStatus;

/**
 *
 * @author phoon
 * update cleaning status
 * roll back status
 */
public class HousekeepingControl {
    private CleaningTaskLog taskLog;

    public HousekeepingControl(){
        this.taskLog = new CleaningTaskLog();
    }

    public void updateCleaningStatus(int roomNumber, RoomCleaningStatus newStatus){
        taskLog.logStatusChange(roomNumber, newStatus);
    }

    public boolean rollBackStatus(int roomNumber){
        RoomCleaningStatus removed = taskLog.rollback(roomNumber);
        return removed != null;
    }

    public void viewRoomCleaningStatus(int roomNumber){
        RoomCleaningStatus status = taskLog.getCurrentStatus(roomNumber);
        if (status == null){
            System.out.println("No status recorded for room" + roomNumber);
        } else {
            System.out.println("Room " + roomNumber + ", Status: " + status.getLabel());
        }
    }

    public void viewRoomCurrentStatus(int roomNumber){
        
    }
}

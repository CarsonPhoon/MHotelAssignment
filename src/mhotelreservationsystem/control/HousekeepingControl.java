/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.control;

import java.util.HashMap;
import java.util.Map;
import mhotelreservationsystem.entity.CleaningTaskLog;
import mhotelreservationsystem.entity.RoomCleaningStatus;
import mhotelreservationsystem.entity.Staff;
import mhotelreservationsystem.entity.StaffRoomAssignment;

/**
 *
 * @author chanzj
 * update cleaning status
 * roll back status
 */
public class HousekeepingControl {
    private CleaningTaskLog taskLog;
    private StaffRoomAssignment staffAssign;
    private Map<Integer, Staff> roomAssign;

    public HousekeepingControl(){
        this.taskLog = new CleaningTaskLog();
        this.staffAssign = new StaffRoomAssignment();
        this.roomAssign = new HashMap<>();

        staffAssign.addStaff(new Staff("S001", "Sarah"));
        staffAssign.addStaff(new Staff("S002", "Jason"));
        staffAssign.addStaff(new Staff("S003", "Wei Lin"));
    }

    public void updateCleaningStatus(int roomNumber, RoomCleaningStatus newStatus){
        taskLog.logStatusChange(roomNumber, newStatus);
    }

    public boolean advanceCleaningStatus(int roomNumber){
        RoomCleaningStatus current = taskLog.getCurrentStatus(roomNumber);

        RoomCleaningStatus nextStatus;
        boolean isDirty = false;

        if (current == null){
            nextStatus = RoomCleaningStatus.DIRTY;
            isDirty = true;
        } else {
            nextStatus = current.next();
        }

        if (nextStatus == null){
            System.out.println("Room " + roomNumber + " is already Ready for Check-In. No further status to advance to.");
            return false;
        }

        taskLog.logStatusChange(roomNumber, nextStatus);
        System.out.println("Room " + roomNumber + " status advanced to " + nextStatus.getLabel());

        if (isDirty){
            Staff assigned = staffAssign.assignNextStaff(roomNumber);
            if (assigned != null){
                roomAssign.put(roomNumber, assigned);
                System.out.println(assigned.getStaffName() + " has been auto-assigned to room " + roomNumber);
            }
        }

        if (nextStatus == RoomCleaningStatus.READYFORCHECKIN){
            Staff staff = roomAssign.get(roomNumber);
            if (staff != null){
                staffAssign.completeTaskAndRequeue(staff);
                roomAssign.remove(roomNumber);
                System.out.println(staff.getStaffName() + " is now free");
            }
        }

        return true;
    }

    public boolean rollBackStatus(int roomNumber){
        RoomCleaningStatus removed = taskLog.rollback(roomNumber);
        if (removed == null){
            return false;
        }
        RoomCleaningStatus current = taskLog.getCurrentStatus(roomNumber);
        if (current == null){
            System.out.println("Room " + roomNumber + " rolled back. No previous status remains (room has no history now).");
        } else {
            System.out.println("Room " + roomNumber + " rolled back to: " + current.getLabel());
        }
        return true;
    }

    public void viewTaskLog(int roomNumber){
        taskLog.getRoomStatusHistories(roomNumber);
    }

    public void viewRoomCleaningStatus(int roomNumber){
        RoomCleaningStatus status = taskLog.getCurrentStatus(roomNumber);
        if (status == null){
            System.out.println("No status recorded for room" + roomNumber);
        } else {
            System.out.println("Room " + roomNumber + ", Status: " + status.getLabel());
        }
    }

    public void addStaffToQueue(Staff staff){
        staffAssign.addStaff(staff);
    }
}

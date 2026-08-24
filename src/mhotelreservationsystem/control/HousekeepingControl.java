/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.control;

import java.util.HashMap;
import java.util.Map;
import mhotelreservationsystem.entity.CleaningTaskLog;
import mhotelreservationsystem.entity.RoomCleaningStatus;
import mhotelreservationsystem.entity.RoomStatus;
import mhotelreservationsystem.entity.Staff;
import mhotelreservationsystem.entity.StaffRoomAssignment;
import mhotelreservationsystem.repository.RoomRepository;

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
    private RoomRepository roomRepository;

    public HousekeepingControl(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
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

        // Sync Room.status in RoomRepository based on cleaning status
        syncRoomStatus(roomNumber, nextStatus);

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
            // Sync Room.status in RoomRepository based on rolled back cleaning status
            syncRoomStatus(roomNumber, current);
        }
        return true;
    }

    // Sync Room.status in RoomRepository based on RoomCleaningStatus
    private void syncRoomStatus(int roomNumber, RoomCleaningStatus cleaningStatus){
        mhotelreservationsystem.entity.Room room = roomRepository.searchRoom(roomNumber);
        if (room == null) return;

        RoomStatus newRoomStatus;
        switch (cleaningStatus){
            case DIRTY:
            case INPROGRESS:
                newRoomStatus = RoomStatus.CLEANING;
                break;
            case READYFORCHECKIN:
                newRoomStatus = RoomStatus.AVAILABLE;
                break;
            case INSPECTED:
                newRoomStatus = RoomStatus.CLEANING;
                break;
            default:
                return;
        }

        if (room.getStatus() != newRoomStatus){
            room.setStatus(newRoomStatus);
            roomRepository.updateRoom(room);
        }
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

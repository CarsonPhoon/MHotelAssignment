/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.control;

import java.time.LocalDateTime;

import mhotelreservationsystem.adt.ListInterface;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.entity.RoomAssignmentEntry;
import mhotelreservationsystem.entity.CleaningTaskLog;
import mhotelreservationsystem.entity.RoomCleaningStatus;
import mhotelreservationsystem.entity.RoomStatus;
import mhotelreservationsystem.entity.Staff;
import mhotelreservationsystem.entity.StaffRoomAssignment;
import mhotelreservationsystem.entity.CleaningRecord;
import mhotelreservationsystem.repository.RoomRepository;
import mhotelreservationsystem.repository.StaffRepository;

/**
 *
 * @author chanzj
 * update cleaning status
 * roll back status
 */
public class HousekeepingControl {
    private CleaningTaskLog taskLog;
    private StaffRoomAssignment staffAssign;
    private ListInterface<RoomAssignmentEntry> roomAssign;
    private RoomRepository roomRepository;
    
    private ListInterface<CleaningRecord> completedRecords;
    
    // constructor
    // sets up housekeeping: loads staff into the assignment queue
    public HousekeepingControl(RoomRepository roomRepository, StaffRepository staffRepository){
        this.roomRepository = roomRepository;
        this.taskLog = new CleaningTaskLog();
        this.staffAssign = new StaffRoomAssignment();
        this.roomAssign = new ArrayListADT<>();
        this.completedRecords = new ArrayListADT<>();

        ListInterface<Staff> staffList = staffRepository.getAllStaff();
        for (int i = 0; i < staffList.getNumberOfEntries(); i++){
            staffAssign.addStaff(staffList.get(i));
        }
        
    }

    // logs a status directly for a room
    public void updateCleaningStatus(int roomNumber, RoomCleaningStatus newStatus){
        taskLog.logStatusChange(roomNumber, newStatus);
    }
    
    // moves room to next cleaning status, assigns/frees staff as needed
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
                putAssignment(roomNumber, assigned);
                System.out.println(assigned.getStaffName() + " has been auto-assigned to room " + roomNumber);
            }
        }

        if (nextStatus == RoomCleaningStatus.READYFORCHECKIN){
            Staff staff = findAssignment(roomNumber) == null ? null : findAssignment(roomNumber).getStaff();
            if (staff != null){
                completedRecords.add(new CleaningRecord(roomNumber, staff.getStaffID(), staff.getStaffName(), LocalDateTime.now()));
                staffAssign.completeTaskAndRequeue(staff);
                removeAssignment(roomNumber);
                System.out.println(staff.getStaffName() + " is now free");
            }
        }

        return true;
    }
    
    // undoes the last status change for a room
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
    
    // prints a room's full status history
    public void viewTaskLog(int roomNumber){
        taskLog.getRoomStatusHistories(roomNumber);
    }
    
    // prints a room's current status
    public void viewRoomCleaningStatus(int roomNumber){
        RoomCleaningStatus status = taskLog.getCurrentStatus(roomNumber);
        if (status == null){
            System.out.println("No status recorded for room " + roomNumber);
        } else {
            System.out.println("Room " + roomNumber + ", Status: " + status.getLabel());
        }
    }
    
    // gets a room's current status
    public RoomCleaningStatus getCurrentStatus(int roomNumber){
        return taskLog.getCurrentStatus(roomNumber);
    }
    
    // gets all completed cleaning records
    public ListInterface<CleaningRecord> getCompletedRecords(){
        return completedRecords;
    }
    
    // adds staff to the assignment pool
    public void addStaffToQueue(Staff staff){
        staffAssign.addStaff(staff);
    }
    
    // state the checked out room to dirty cleaning status
    public void stateRoomDirtyAfterCheckout(int roomNumber){
        RoomCleaningStatus current = taskLog.getCurrentStatus(roomNumber);
        if (current != null && current != RoomCleaningStatus.READYFORCHECKIN){
            System.out.println("Room " + roomNumber + " already has a cleaning status: " + current.getLabel());
            return;
        }
        
        taskLog.logStatusChange(roomNumber, RoomCleaningStatus.DIRTY);
        System.out.println("Room " + roomNumber + " marked Dirty after checkout.");
        syncRoomStatus(roomNumber, RoomCleaningStatus.DIRTY);
        
        Staff assigned = staffAssign.assignNextStaff(roomNumber);
        if (assigned != null){
            putAssignment(roomNumber, assigned);
            System.out.println(assigned.getStaffName() + " has been auto-assigned to room " + roomNumber);
        }
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
    
    // finds a room's staff assignment
    private RoomAssignmentEntry findAssignment(int roomNumber){
        for (int i = 0; i < roomAssign.getNumberOfEntries(); i++){
            RoomAssignmentEntry entry = roomAssign.get(i);
            if (entry.getRoomNumber() == roomNumber){
                return entry;
            }
        }
        return null;
    }
    
    // adds/updates a room's staff assignment
    private void putAssignment(int roomNumber, Staff staff){
        for (int i = 0; i < roomAssign.getNumberOfEntries(); i++){
            if (roomAssign.get(i).getRoomNumber() == roomNumber){
                roomAssign.replace(i, new RoomAssignmentEntry(roomNumber, staff));
                return;
            }
        }
        roomAssign.add(new RoomAssignmentEntry(roomNumber, staff));
    }
    
    // removes a room's staff assignment
    private void removeAssignment(int roomNumber){
        for (int i = 0; i < roomAssign.getNumberOfEntries(); i++){
            if (roomAssign.get(i).getRoomNumber() == roomNumber){
                roomAssign.remove(i);
                return;
            }
        }
    }
}

package mhotelreservationsystem.entity;

import mhotelreservationsystem.adt.QueueInterface;
import mhotelreservationsystem.adt.LinkedQueue;

/**
 *
 * @author chanzj
 */

public class StaffRoomAssignment {
    private QueueInterface<Staff> availableStaff;
    
    // creates empty staff queue
    public StaffRoomAssignment(){
        this.availableStaff = new LinkedQueue<>();
    }
    
    // adds staff to queue
    public void addStaff(Staff staff){
        availableStaff.enqueue(staff);
    }

    // assigns next available staff to a room
    public Staff assignNextStaff(int roomNumber){
        if (availableStaff.isEmpty()){
            System.out.println("No staff available for room assignment");
            return null;
        }
        Staff staff = availableStaff.dequeue(); // staff is null when queue was empty
        staff.assignToRoom(roomNumber); 
        return staff;
    }

    // frees staff and requeues them
    public void completeTaskAndRequeue(Staff staff){
        staff.completeCleaningTask();
        availableStaff.enqueue(staff);
    }

    // checks if queue is empty
    public boolean isEmpty(){
        return availableStaff.isEmpty();
    }

}

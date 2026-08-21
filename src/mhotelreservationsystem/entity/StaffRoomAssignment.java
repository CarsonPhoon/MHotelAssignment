package mhotelreservationsystem.entity;

import mhotelreservationsystem.adt.QueueInterface;
import mhotelreservationsystem.adt.LinkedQueue;

public class StaffRoomAssignment {
    private QueueInterface<Staff> availableStaff;
    
    public StaffRoomAssignment(){
        this.availableStaff = new LinkedQueue<>();
    }
    
    public void addStaff(Staff staff){
        availableStaff.enqueue(staff);
    }

    public Staff assignNextStaff(int roomNumber){
        if (availableStaff.isEmpty()){
            System.out.println("No staff available for room assignment");
            return null;
        }
        Staff staff = availableStaff.dequeue(); // staff is null when queue was empty
        staff.assignToRoom(roomNumber); 
        return staff;
    }

    public void completeTaskAndRequeue(Staff staff){
        staff.completeCleaningTask();
        availableStaff.enqueue(staff);
    }

    public boolean isEmpty(){
        return availableStaff.isEmpty();
    }

}

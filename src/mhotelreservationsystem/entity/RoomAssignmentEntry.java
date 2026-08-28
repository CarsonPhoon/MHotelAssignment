package mhotelreservationsystem.entity;

/**
 *
 * @author chanzj
 */
public class RoomAssignmentEntry {
    private int roomNumber;
    private Staff staff;

    // pairs a room with an assigned staff member
    public RoomAssignmentEntry(int roomNumber, Staff staff){
        this.roomNumber = roomNumber;
        this.staff = staff;
    }

    // gets room number
    public int getRoomNumber(){
        return roomNumber;
    }

    // gets assigned staff
    public Staff getStaff(){
        return staff;
    }
}

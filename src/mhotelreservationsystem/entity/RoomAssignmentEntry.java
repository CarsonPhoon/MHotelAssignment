package mhotelreservationsystem.entity;

/**
 *
 * @author chanzj
 */
public class RoomAssignmentEntry {
    private int roomNumber;
    private Staff staff;

    public RoomAssignmentEntry(int roomNumber, Staff staff){
        this.roomNumber = roomNumber;
        this.staff = staff;
    }

    public int getRoomNumber(){
        return roomNumber;
    }

    public Staff getStaff(){
        return staff;
    }
}

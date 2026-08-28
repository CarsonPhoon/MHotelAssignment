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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RoomAssignmentEntry other = (RoomAssignmentEntry) obj;
        return roomNumber == other.roomNumber;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(roomNumber);
    }
}

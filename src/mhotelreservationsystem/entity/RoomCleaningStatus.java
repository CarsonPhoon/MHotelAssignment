/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mhotelreservationsystem.entity;

/**
 *
 * @author chanzj
 */
public enum RoomCleaningStatus {
   
    DIRTY("Dirty"),
    INPROGRESS("Cleaning In Progress"),
    INSPECTED("Checked"),
    READYFORCHECKIN("Ready for Check-In");

    private final String displayName;

    // sets display label per status
    RoomCleaningStatus(String displayName){
        this.displayName = displayName;
    }

    // gets display label
    public String getLabel(){
        return displayName;
    }

    // gets next status in sequence
    public RoomCleaningStatus next(){
        RoomCleaningStatus[] values = RoomCleaningStatus.values();
        int nextIndex = this.ordinal() + 1;
        if (nextIndex >= values.length){
            return null;
        }
        return values[nextIndex];
    }
}

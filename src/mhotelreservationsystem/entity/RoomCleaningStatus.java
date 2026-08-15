/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mhotelreservationsystem.entity;

/**
 *
 * @author user
 */
public enum RoomCleaningStatus {
   
    DIRTY("Dirty"),
    INPROGRESS("Cleaning In Progress"),
    INSPECTED("Checked"),
    READYFORCHECKIN("Ready for Check-In");

    private final String displayName;

    RoomCleaningStatus(String displayName){
        this.displayName = displayName;
    }

    public String getLabel(){
        return displayName;
    }
}

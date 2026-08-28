/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mhotelreservationsystem.entity;

/**
 *
 * @author phoon
 */
public enum MembershipStatus {

    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String displayName;

    MembershipStatus(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String toString(){
        return displayName;
    }

    // Method of Ignore case matching
    public static MembershipStatus fromDisplayName(String displayName) {
        for (MembershipStatus status : MembershipStatus.values()) {
            if (status.displayName.equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown MembershipStatus: " + displayName);
    }

}
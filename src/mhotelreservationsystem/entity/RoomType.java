/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mhotelreservationsystem.entity;

/**
 *
 * @author phoon
 */
public enum RoomType {

    SINGLE("Single"),
    DOUBLE("Double"),
    DELUXE("Deluxe"),
    FAMILY("Family"),
    SUITE("Suite"),
    VIP("VIP");

    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
    
    public static RoomType fromDisplayName(String displayName) {
        for (RoomType type : RoomType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown RoomType: " + displayName);
    }
}
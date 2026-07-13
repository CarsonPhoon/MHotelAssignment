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

    STANDARD("Standard"),
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
}
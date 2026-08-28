/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mhotelreservationsystem.entity;

/**
 *
 * @author phoon
 */
public enum MemberLevel {

    SILVER("Silver"),
    BRONZE("Bronze"),
    GOLD("Gold"),
    ELITE("Elite"),
    DIAMOND("Diamond"),
    PLATINUM("Platinum");
    

    private final String displayName;

    MemberLevel(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String toString(){
        return displayName;
    }

    // Method of Ignore case matching
    public static MemberLevel fromDisplayName(String displayName) {
        for (MemberLevel level : MemberLevel.values()) {
            if (level.displayName.equalsIgnoreCase(displayName)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown MemberLevel: " + displayName);
    }

}
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

    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),
    PLATINUM("Platinum");

    private final String displayName;

    MemberLevel(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String toString(){
        return displayName;
    }

}
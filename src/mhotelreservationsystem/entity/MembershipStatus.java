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
    SUSPENDED("Suspended"),
    EXPIRED("Expired");

    private final String displayName;

    MembershipStatus(String displayName){
        this.displayName = displayName;
    }

    @Override
    public String toString(){
        return displayName;
    }

}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.boundary.frontdeskmodule;

import mhotelreservationsystem.boundary.Navigable;

/**
 *
 * @author phoon
 */
public class FrontDeskUI implements Navigable {

    private static final String Y = "\033[33m";
    private static final String R = "\033[0m";

    @Override
    public void display() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         " + Y + "FRONT DESK SERVICE" + R);
        System.out.println("========================================");
        System.out.println("1. Guest Management");
        System.out.println("2. Room & Booking Management");
        System.out.println("3. Comment Management");
        System.out.println("4. Report Management");
        System.out.println("0. Back");
        System.out.println("========================================");
    }

    @Override
    public Navigable handleChoice(int choice) {
        switch (choice) {
            case 1: return new GuestManagementUI();
            case 2: return new RoomBookingManagementUI();
            case 3: return new CommentManagementUI();
            case 4: return new ReportManagementUI();
            default: return null;
        }
    }

    @Override
    public int getMaxChoice() {
        return 4;
    }
}

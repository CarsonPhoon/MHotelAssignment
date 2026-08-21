/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.boundary;

import mhotelreservationsystem.boundary.frontdeskmodule.FrontDeskUI;
import mhotelreservationsystem.utility.MessageUI;
import mhotelreservationsystem.utility.Validation;

/**
 *
 * @author phoon
 */
public class MainMenuUI {

    private Navigator navigator;

    public MainMenuUI() {
        this.navigator = new Navigator();
    }

    public void start() {
        int mainMenuChoice;

        do {
            MessageUI.displayMainHeader();

            System.out.println();
            System.out.println("\n\t\t------------------------------------------------------");
            System.out.println("\n\t\t\t\t  Welcome to M Hotel");
            System.out.println("\t\t\t\tEnter CHOICE to continue...");
            System.out.println("\n\t\t------------------------------------------------------");
            System.out.println();
            System.out.println("1. Walk-In Registration & Booking");
            System.out.println("2. VIP Room Allocation");
            System.out.println("3. Housekeeping & Task Log");
            System.out.println("4. Front Desk Service");
            System.out.println("0. Exit");

            mainMenuChoice = Validation.getIntOrReturn("\nEnter your choice (0-4): ", 0, 4);

            switch (mainMenuChoice) {
                case 1:
                    WalkInUI walkInUI = new WalkInUI();
                    walkInUI.start();
                    break;
                case 2:
                    VIPRoomUI vipUI = new VIPRoomUI();
                    vipUI.startUI();
                    break;
                case 3:
                    HousekeepingUI housekeepingUI = new HousekeepingUI();
                    housekeepingUI.startModule();
                    break;
                case 4:
                    navigator.navigateTo(new FrontDeskUI());
                    navigator.run();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\nInvalid choice, pls enter again (0 - 4 only)");
                    Validation.pressEnterToContinue();
            }
        } while (mainMenuChoice != 0);

        System.out.println("\n\t\tExiting the M Hotel System ...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\t\tThank you, have a nice day ^-^");
    }
}

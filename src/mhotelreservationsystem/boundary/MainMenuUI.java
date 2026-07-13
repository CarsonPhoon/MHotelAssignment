/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.boundary;

import java.io.IOException;
import mhotelreservationsystem.utility.MessageUI;
import mhotelreservationsystem.utility.ScannerUtility;
import mhotelreservationsystem.utility.Validation;
/**
 *
 * @author phoon
 */
public class MainMenuUI {
    
    public void start() {
        int mainMenuChoice;
        
        
        do{
            MessageUI.displayMainHeader();
            
            System.out.println();
            System.out.println("\n\t\t------------------------------------------------------");
            System.out.println("\n\t\t\t\t  Welcome to M Hotel ^-^");
            System.out.println("\t\t\t\tEnter CHOICE to continue...");
            System.out.println("\n\t\t------------------------------------------------------");
            System.out.println();
            System.out.println("1. Walk-In Registration & Booking");
            System.out.println("2. VIP Room Allocation");
            System.out.println("3. Housekeeping & Task Log");
            System.out.println("4. Front Desk Service");
            System.out.println("5. Loyalty & Rewards");
            System.out.println("0. Exit");
                
           mainMenuChoice = Validation.getInt("\nEnter your choice (0-5): ",0,5);
            
            switch (mainMenuChoice){
            
                case 1:
                    System.out.println("\nWalk-In Registration & Booking Module");
                    Validation.pressEnterToContinue();
                    break;
                case 2:
                    System.out.println("\nVIP Room Allocation Module");
                    Validation.pressEnterToContinue();
                    break;
                case 3:
                    System.out.println("\nHousekeeping & Task Log Module");
                    Validation.pressEnterToContinue();
                    break;
                case 4:
                    System.out.println("\nFront Desk Service Module");
                    Validation.pressEnterToContinue();
                    break;
                case 5:
                    System.out.println("\nLoyalty & Rewards Module");
                    Validation.pressEnterToContinue();
                    break;
                case 0:
                    System.out.println("\n\t\tExiting the M Hotel System ...");
                    try{
                        // Pause for 1000 miliseconds (1 second)
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
                        // This help block execute if the interrupted by another thread
                        e.printStackTrace();
                    }
                    System.out.println("\t\tThank you, have a nice day ^-^");
                    return;
                default:
                    System.out.println("\nInvalid choice, pls enter again (0 - 5 only)");
                    Validation.pressEnterToContinue();
            }   
        } while (mainMenuChoice != 0);
    }
  
}

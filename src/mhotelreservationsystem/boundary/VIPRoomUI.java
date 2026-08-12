/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.boundary;

import java.util.Scanner;
import mhotelreservationsystem.control.VIPRoomControl;
import mhotelreservationsystem.report.VIPQueueReport;
import mhotelreservationsystem.utility.Validation;

/**
 * Boundary class for VIP Room Allocation UI.
 * @author zekai
 */
public class VIPRoomUI {

    private VIPRoomControl vipControl = new VIPRoomControl();
    private VIPQueueReport reportGenerator = new VIPQueueReport(vipControl);
    private Scanner scanner = new Scanner(System.in);

    public void startUI() {
        int choice = -1;
        
        while (choice != 0) {
            System.out.println("\n=====================================");
            System.out.println("     VIP Priority Room Allocation    ");
            System.out.println("=====================================");
            System.out.println("1. Register VIP to Waiting Queue");
            System.out.println("2. Assign Room to Next VIP");
            System.out.println("3. View All Waiting VIPs");
            System.out.println("4. Search VIP Status in Queue");
            System.out.println("5. Report: Queue Status by Level");
            System.out.println("6. Report: High-Value VIPs");
            System.out.println("0. Return to Main Menu");
            System.out.println("=====================================");
            System.out.print("Please enter your choice: ");
            
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } else {
                System.out.println("Error: Please enter a valid number!");
                scanner.nextLine(); 
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n--- Register New VIP ---");
                    System.out.print("Enter Member ID (e.g., M005): ");
                    String memId = scanner.nextLine().trim();
                    
                    System.out.print("Enter Confirmation Number (e.g., C1005): ");
                    String confirmNum = scanner.nextLine().trim();
                    
                    System.out.print("Enter Member Level (BRONZE/SILVER/GOLD/PLATINUM): ");
                    String levelStr = scanner.nextLine().toUpperCase().trim();
                    
                    mhotelreservationsystem.entity.MemberLevel level;
                    try {
                        level = mhotelreservationsystem.entity.MemberLevel.valueOf(levelStr);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid level entered! Defaulting to BRONZE.");
                        level = mhotelreservationsystem.entity.MemberLevel.BRONZE;
                    }
                    
                    System.out.print("Enter Reward Points: ");
                    int points = 0;
                    if (scanner.hasNextInt()) {
                        points = scanner.nextInt();
                        scanner.nextLine(); 
                    } else {
                        System.out.println("Invalid points entered! Registration cancelled.");
                        scanner.nextLine(); 
                        break; 
                    }
                    
                    int minRequired = 0;
                    switch (level) {
                        case BRONZE: minRequired = 500; break;
                        case SILVER: minRequired = 1500; break;
                        case GOLD: minRequired = 3000; break;
                        case PLATINUM: minRequired = 5000; break;
                    }
                    
                    if (points < minRequired) {
                        System.out.println("\n[Registration Failed] Insufficient Points!");
                        System.out.println("-> A " + level + " member must have at least " + minRequired + " points.");
                        System.out.println("-> Points entered: " + points);
                        break;
                    }

                    mhotelreservationsystem.entity.Member newVip = new mhotelreservationsystem.entity.Member(
                        memId, 
                        confirmNum, 
                        level, 
                        points, 
                        java.time.LocalDate.now(), 
                        mhotelreservationsystem.entity.MembershipStatus.ACTIVE
                    );
                    
                    if (vipControl.addVipToQueue(newVip)) {
                        System.out.println("VIP successfully added to the waiting queue!");
                    } else {
                        System.out.println("Failed to add VIP to the queue.");
                    }
                    break;
                case 2:
                    System.out.println("\n--- Assign Room ---");
                    mhotelreservationsystem.entity.Member assignedVip = vipControl.assignRoomToNextVip();
                    
                    if (assignedVip != null) {
                        System.out.println("Room assigned to the highest priority VIP:");
                        System.out.println("-> " + assignedVip.toString());
                        System.out.println("This VIP has been removed from the waiting queue.");
                        
                        if (assignedVip.getRewardPoints() > 10000) {
                            System.out.println("\n*** SUPREME VIP ALERT ***");
                            System.out.println("Guest points exceed 10,000! Eligible for special perks.");
                            System.out.println("Please offer: 1. Breakfast  2. Spa  3. Late Check-out");
                        }
                        // ==========================================

                    } else {
                        System.out.println("The waiting queue is currently empty. No VIPs waiting.");
                    }
                    break;
                case 3:
                    System.out.println("\n[System] Generating waiting list...\n");
                    vipControl.displayAllWaitingVips();
                    break;

                case 4:
                    System.out.println("\n--- Search VIP in Queue ---");
                    System.out.print("Enter Confirmation Number to search: ");
                    String searchTarget = scanner.nextLine().trim();
                    
                    mhotelreservationsystem.entity.Member foundVip = vipControl.searchVip(searchTarget);
                    
                    if (foundVip != null) {
                        System.out.println("VIP Found in the waiting queue:");
                        System.out.println(foundVip.toString());
                    } else {
                        System.out.println("VIP with Confirmation Number '" + searchTarget + "' is not in the queue.");
                    }
                    break;

                case 5:
                    reportGenerator.generateQueueByLevelReport();
                    break;

                case 6:
                    reportGenerator.generateHighValueVipReport();
                    break;

                case 0:
                    System.out.println("\nReturning to Main Menu...");
                    break;

                default:
                    System.out.println("\nInvalid option, please try again!");
            }

            if (choice != 0) {
                System.out.println(); 
                Validation.pressEnterToContinue();
            }
        }
    }
}
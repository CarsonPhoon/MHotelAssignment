package mhotelreservationsystem.boundary;

import java.util.Scanner;
import mhotelreservationsystem.control.VIPRoomControl;
import mhotelreservationsystem.report.VIPQueueReport;
import mhotelreservationsystem.repository.GuestRepository;
import mhotelreservationsystem.repository.MemberRepository;
import mhotelreservationsystem.utility.Validation;

/**
 * Boundary class for VIP Room Allocation UI.
 * @author zekai
 */
public class VIPRoomUI implements Navigable {

    private VIPRoomControl vipControl;
    private VIPQueueReport reportGenerator;
    private Scanner scanner = new Scanner(System.in);

    public VIPRoomUI(MemberRepository memberRepository, GuestRepository guestRepository) {
        this.vipControl = new VIPRoomControl(memberRepository, guestRepository);
        this.reportGenerator = new VIPQueueReport(vipControl);
    }

    // OLD: while + switch navigation pattern (kept for future use)
    /*
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
            System.out.println("5. VIP Rewards Redemption");
            System.out.println("6. View Allocation Audit Log");
            System.out.println("7. Report: Queue Status by Level");
            System.out.println("8. Report: High-Value VIPs");
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
                case 1: { 
                    System.out.println("\n--- Register New VIP ---");
                    
                    String memId = vipControl.generateNextMemberId();
                    System.out.println("[System] Generated New Member ID: " + memId);
                    
                    String confirmNum = "";
                    boolean isCancelled = false; 
                    
                    while (true) {
                        System.out.print("Enter Confirmation Number (or enter 0 to cancel): ");
                        confirmNum = scanner.nextLine().trim();
                        
                        if (confirmNum.equals("0")) {
                            System.out.println("[System] Registration cancelled. Returning to menu...");
                            isCancelled = true;
                            break; 
                        }
                        
                        if (vipControl.isVipAlreadyRegistered(confirmNum)) {
                            System.out.println("\n[Error] Duplicate Registration!");
                            System.out.println("-> The confirmation number '" + confirmNum + "' is already registered as a VIP.");
                            System.out.println("-> Please enter a different number.\n");
                            continue;
                        }
                        
                        if (vipControl.verifyGuestExists(confirmNum)) {
                            System.out.println("[System] Booking Record Verified!");
                            break; 
                        } else {
                            System.out.println("\n[Error] Booking Record Not Found!");
                            System.out.println("-> The confirmation number '" + confirmNum + "' does not exist in the Walk-In database.");
                            System.out.println("-> Please try again.\n");
                        }
                    }
                    
                    if (isCancelled) {
                        break; 
                    }
                    
                    System.out.println("\n--- Level & Points Guidelines ---");
                    System.out.println("BRONZE   : 500  - 1499 pts");
                    System.out.println("SILVER   : 1500 - 2999 pts");
                    System.out.println("GOLD     : 3000 - 4999 pts");
                    System.out.println("PLATINUM : 5000+ pts");
                    System.out.println("---------------------------------");
                    
                    mhotelreservationsystem.entity.MemberLevel level = null;
                    while (true) {
                        System.out.print("Enter Member Level (BRONZE/SILVER/GOLD/PLATINUM): ");
                        String levelStr = scanner.nextLine().toUpperCase().trim();
                        
                        try {
                            level = mhotelreservationsystem.entity.MemberLevel.valueOf(levelStr);
                            break; 
                        } catch (IllegalArgumentException e) {
                            System.out.println("\n[Error] Invalid member level!");
                            System.out.println("-> Please type exactly: BRONZE, SILVER, GOLD, or PLATINUM.\n");
                        }
                    }
                    
                    int minRequired = 0;
                    int maxAllowed = Integer.MAX_VALUE; 
                    
                    switch (level) {
                        case BRONZE: 
                            minRequired = 500; 
                            maxAllowed = 1499; 
                            break;
                        case SILVER: 
                            minRequired = 1500; 
                            maxAllowed = 2999; 
                            break;
                        case GOLD: 
                            minRequired = 3000; 
                            maxAllowed = 4999; 
                            break;
                        case PLATINUM: 
                            minRequired = 5000; 
                            maxAllowed = Integer.MAX_VALUE; 
                            break;
                    }

                    int points = 0;
                    while (true) {
                        System.out.print("Enter Reward Points: ");
                        if (scanner.hasNextInt()) {
                            points = scanner.nextInt();
                            scanner.nextLine();
                            
                            if (points < minRequired || points > maxAllowed) {
                                System.out.println("\n[Error] Points do not match the Member Level!");
                                if (level == mhotelreservationsystem.entity.MemberLevel.PLATINUM) {
                                    System.out.println("-> " + level + " tier requires at least " + minRequired + " points.");
                                } else {
                                    System.out.println("-> " + level + " tier points must be between " + minRequired + " and " + maxAllowed + ".");
                                }
                                System.out.println("-> Please try again.\n");
                                continue;
                            }
                            break;
                        } else {
                            System.out.println("\n[Error] Invalid points entered!");
                            System.out.println("-> Please enter a valid number (e.g., 500).\n");
                            scanner.nextLine();
                        }
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
                        System.out.println("\n[Success] VIP successfully added to the waiting queue!");
                        System.out.println("[System] VIP record successfully saved to database.");
                    } else {
                        System.out.println("\n[Error] Failed to add VIP to the queue.");
                    }
                    break;
                }
                case 2: {
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

                        System.out.println("\n--- Update Room Status ---");
                        while (true) {
                            System.out.print("Enter Room Number to assign (or enter 0 to skip): ");
                            String roomNum = scanner.nextLine().trim();
                            
                            if (roomNum.equals("0")) {
                                System.out.println("[System] Room status update skipped.");
                                break;
                            }
                            
                            if (roomNum.isEmpty()) {
                                System.out.println("[Error] Room number cannot be empty! Please try again.\n");
                                continue;
                            }
                            
                            if (vipControl.updateRoomStatus(roomNum, "Reserved")) {
                                System.out.println("[System] Success! Room " + roomNum + " status updated to 'Reserved' in Room.txt.");
                                break;
                            } else {
                                System.out.println("[Error] Room '" + roomNum + "' does not exist in the database! Please check and try again.\n");
                            }
                        }  
                    } else {
                        System.out.println("The waiting queue is currently empty. No VIPs waiting.");
                    }
                    break;
                }
                case 3:
                    System.out.println("\n[System] Generating waiting list...\n");
                    vipControl.displayAllWaitingVips();
                    break;

                case 4:{
                    System.out.println("\n--- Search ---");
                    String confirmNum = "";
                    mhotelreservationsystem.entity.Member vip = null;

                    while (true) {
                        System.out.print("Enter Confirmation Number (or enter 0 to cancel): ");
                        confirmNum = scanner.nextLine().trim();

                        if (confirmNum.isEmpty()) {
                            System.out.println("[Error] Input cannot be empty! Please try again.\n");
                            continue;
                        }
                        
                        if (confirmNum.equals("0")) {
                            System.out.println("[System] Operation cancelled.");
                            break;
                        }
                        
                        vip = vipControl.searchVip(confirmNum);
                        if (vip == null) {
                            System.out.println("[Error] VIP Number '" + confirmNum + "' not found! Please check and try again.\n");
                            continue; 
                        }

                        break;
                    }
                    
                    if (confirmNum.equals("0")) {
                        break;
                    }
 
                    System.out.println("\n[Success] VIP Found!");
                    System.out.println("Current Level  : " + vip.getMemberLevel());
                    System.out.println("Current Points : " + vip.getRewardPoints());
                }

                case 5: {
                    System.out.println("\n--- VIP Rewards Redemption ---");
                    String confirmNum = "";
                    mhotelreservationsystem.entity.Member vip = null;

                    while (true) {
                        System.out.print("Enter Confirmation Number (or enter 0 to cancel): ");
                        confirmNum = scanner.nextLine().trim();

                        // 1. 取消退出
                        if (confirmNum.equals("0")) {
                            System.out.println("[System] Operation cancelled. Returning to menu.");
                            break;
                        }

                        if (confirmNum.isEmpty()) {
                            System.out.println("[Error] Confirmation Number cannot be empty or just spaces! Please try again.\n");
                            continue;
                        }
                        
                        vip = vipControl.searchVip(confirmNum);
                        if (vip == null) {
                            System.out.println("[Error] VIP Number '" + confirmNum + "' not found in the waiting queue! Please check and try again.\n");
                            continue;
                        }
                        
                        break; 
                    }
                    
                    if (confirmNum.equals("0")) {
                        break; 
                    }

                    System.out.println("\n[Success] VIP Found!");
                    System.out.println("Current Level  : " + vip.getMemberLevel());
                    System.out.println("Current Points : " + vip.getRewardPoints());
                    
                    System.out.println("\n--- Rewards Catalog ---");
                    System.out.println("1. Welcome Drink          (200 pts)");
                    System.out.println("2. Free Breakfast         (600 pts)");
                    System.out.println("3. Late Check-out         (1000 pts)");
                    System.out.println("4. Free Room Upgrade      (2500 pts)");
                    System.out.println("0. Cancel");
                    System.out.print("Select item to redeem: ");
                    
                    int redeemChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    int pointsCost = 0;
                    String itemName = "";
                    
                    switch(redeemChoice) {
                        case 1: pointsCost = 200; itemName = "Welcome Drink"; break;
                        case 2: pointsCost = 600; itemName = "Free Breakfast"; break;
                        case 3: pointsCost = 1000; itemName = "Late Check-out"; break;
                        case 4: pointsCost = 2500; itemName = "Free Room Upgrade"; break;
                        case 0: System.out.println("Redemption cancelled."); break;
                        default: System.out.println("Invalid choice."); break;
                    }
                    
                    if (pointsCost > 0) {
                        String result = vipControl.redeemPoints(confirmNum, pointsCost);
                        if (result.equals("SUCCESS")) {
                            System.out.println("\n[Success] " + itemName + " redeemed successfully!");
                            System.out.println("-> Remaining Points: " + vip.getRewardPoints());
                            System.out.println("-> Member Level remains: " + vip.getMemberLevel() + " (Tier Protected)");
                            
                            if (redeemChoice == 4) {
                                System.out.println("\n*** Room Upgrade Selection ***");
                                while (true) {
                                    System.out.print("Please enter the Premium Room Number to upgrade to (or enter 0 to skip for now): ");
                                    String upgradeRoom = scanner.nextLine().trim();
                                    
                                    if (upgradeRoom.equals("0")) {
                                        System.out.println("[System] Room upgrade selection skipped. Please manually assign the room later.");
                                        break;
                                    }

                                    if (upgradeRoom.isEmpty()) {
                                        System.out.println("[Error] Room number cannot be empty! Please try again.\n");
                                        continue;
                                    }
                                    
                                    if (vipControl.updateRoomStatus(upgradeRoom, "Reserved")) {
                                        System.out.println("[System] Awesome! Premium Room " + upgradeRoom + " has been successfully reserved for this VIP.");
                                        break;
                                    } else {
                                        System.out.println("[Error] Room '" + upgradeRoom + "' does not exist in the database! Please check and try again.\n");
                                    }
                                }
                            }
                            
                        } else if (result.equals("INSUFFICIENT")) {
                            System.out.println("\n[Error] Insufficient points to redeem " + itemName + ".");
                        }
                    }
                    break;
                }

                case 6:
                    vipControl.displayAssignedHistory();
                    break;

                case 7:
                    reportGenerator.generateQueueByLevelReport();
                    break;

                case 8:
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
    */

    // Stack navigation: display menu for Navigator
    @Override
    public void display() {
        System.out.println("\n=====================================");
        System.out.println("     VIP Priority Room Allocation    ");
        System.out.println("=====================================");
        System.out.println("1. Register VIP to Waiting Queue");
        System.out.println("2. Assign Room to Next VIP");
        System.out.println("3. View All Waiting VIPs");
        System.out.println("4. Search VIP Status in Queue");
        System.out.println("5. Report: Queue Status by Level");
        System.out.println("6. Report: High-Value VIPs");
        System.out.println("0. Back");
        System.out.println("=====================================");
    }

    // Stack navigation: route choice to action, return null to stay on this page
    @Override
    public Navigable handleChoice(int choice) {
        switch (choice) {
            case 1: registerVip(); break;
            case 2: assignRoom(); break;
            case 3: vipControl.displayAllWaitingVips(); break;
            case 4: searchVip(); break;
            case 5: reportGenerator.generateQueueByLevelReport(); break;
            case 6: reportGenerator.generateHighValueVipReport(); break;
            default: break;
        }
        return null;
    }

    // Stack navigation: max selectable option (0 is handled by Navigator)
    @Override
    public int getMaxChoice() {
        return 6;
    }

    // Register VIP to waiting queue (extracted from old case 1)
    private void registerVip() {
        System.out.println("\n--- Register New VIP ---");
        
        String memId = vipControl.generateNextMemberId();
        System.out.println("[System] Generated New Member ID: " + memId);
        
        String confirmNum = "";
        boolean isCancelled = false; 
        
        // Validate Confirmation Number with retry
        while (true) {
            System.out.print("Enter Confirmation Number (or enter 0 to cancel): ");
            confirmNum = scanner.nextLine().trim();
            
            if (confirmNum.equals("0")) {
                System.out.println("[System] Registration cancelled. Returning to menu...");
                isCancelled = true;
                break; 
            }
            
            if (vipControl.isVipAlreadyRegistered(confirmNum)) {
                System.out.println("\n[Error] Duplicate Registration!");
                System.out.println("-> The confirmation number '" + confirmNum + "' is already registered as a VIP.");
                System.out.println("-> Please enter a different number.\n");
                continue;
            }
            
            if (vipControl.verifyGuestExists(confirmNum)) {
                System.out.println("[System] Booking Record Verified!");
                break; 
            } else {
                System.out.println("\n[Error] Booking Record Not Found!");
                System.out.println("-> The confirmation number '" + confirmNum + "' does not exist in the Walk-In database.");
                System.out.println("-> Please try again.\n");
            }
        }
        
        if (isCancelled) {
            return; 
        }
        
        // Display level & points guidelines
        System.out.println("\n--- Level & Points Guidelines ---");
        System.out.println("BRONZE   : 500  - 1499 pts");
        System.out.println("SILVER   : 1500 - 2999 pts");
        System.out.println("GOLD     : 3000 - 4999 pts");
        System.out.println("PLATINUM : 5000+ pts");
        System.out.println("---------------------------------");
        
        // Validate member level with retry
        mhotelreservationsystem.entity.MemberLevel level = null;
        while (true) {
            System.out.print("Enter Member Level (BRONZE/SILVER/GOLD/PLATINUM): ");
            String levelStr = scanner.nextLine().toUpperCase().trim();
            
            try {
                level = mhotelreservationsystem.entity.MemberLevel.valueOf(levelStr);
                break; 
            } catch (IllegalArgumentException e) {
                System.out.println("\n[Error] Invalid member level!");
                System.out.println("-> Please type exactly: BRONZE, SILVER, GOLD, or PLATINUM.\n");
            }
        }
        
        // Set point range for the selected level
        int minRequired = 0;
        int maxAllowed = Integer.MAX_VALUE; 
        
        switch (level) {
            case BRONZE: minRequired = 500; maxAllowed = 1499; break;
            case SILVER: minRequired = 1500; maxAllowed = 2999; break;
            case GOLD: minRequired = 3000; maxAllowed = 4999; break;
            case PLATINUM: minRequired = 5000; maxAllowed = Integer.MAX_VALUE; break;
        }

        // Validate points within range with retry
        int points = 0;
        while (true) {
            System.out.print("Enter Reward Points: ");
            if (scanner.hasNextInt()) {
                points = scanner.nextInt();
                scanner.nextLine();
                
                if (points < minRequired || points > maxAllowed) {
                    System.out.println("\n[Error] Points do not match the Member Level!");
                    if (level == mhotelreservationsystem.entity.MemberLevel.PLATINUM) {
                        System.out.println("-> " + level + " tier requires at least " + minRequired + " points.");
                    } else {
                        System.out.println("-> " + level + " tier points must be between " + minRequired + " and " + maxAllowed + ".");
                    }
                    System.out.println("-> Please try again.\n");
                    continue;
                }
                break;
            } else {
                System.out.println("\n[Error] Invalid points entered!");
                System.out.println("-> Please enter a valid number (e.g., 500).\n");
                scanner.nextLine();
            }
        }

        // Create VIP object and add to waiting queue
        mhotelreservationsystem.entity.Member newVip = new mhotelreservationsystem.entity.Member(
            memId, confirmNum, level, points,
            java.time.LocalDate.now(),
            mhotelreservationsystem.entity.MembershipStatus.ACTIVE
        );
        
        if (vipControl.addVipToQueue(newVip)) {
            System.out.println("\n[Success] VIP successfully added to the waiting queue!");
            System.out.println("[System] VIP record successfully saved to database.");
        } else {
            System.out.println("\n[Error] Failed to add VIP to the queue.");
        }
    }

    // Assign room to next VIP in queue (extracted from old case 2)
    private void assignRoom() {
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
        } else {
            System.out.println("The waiting queue is currently empty. No VIPs waiting.");
        }
    }

    // Search VIP by confirmation number (extracted from old case 4)
    private void searchVip() {
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
    }
}
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
                case 1: { 
                    System.out.println("\n--- Register New VIP ---");
                    
                    String memId = vipControl.generateNextMemberId();
                    System.out.println("[System] Generated New Member ID: " + memId);
                    
                    String confirmNum = "";
                    boolean isCancelled = false; 
                    
                    // 1. 无限重试：验证 Confirmation Number
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
                    
                    // 2. 打印计分规则
                    System.out.println("\n--- Level & Points Guidelines ---");
                    System.out.println("BRONZE   : 500  - 1499 pts");
                    System.out.println("SILVER   : 1500 - 2999 pts");
                    System.out.println("GOLD     : 3000 - 4999 pts");
                    System.out.println("PLATINUM : 5000+ pts");
                    System.out.println("---------------------------------");
                    
                    // 3. 无限重试：输入并验证等级
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
                    
                    // 提前设定好该等级的积分区间
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

                    // 4. 无限重试：输入分数并验证是否在区间内
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

                    // 5. 生成对象并存入等待队列
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
                    } else {
                        System.out.println("The waiting queue is currently empty. No VIPs waiting.");
                    }
                    break;
                }
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
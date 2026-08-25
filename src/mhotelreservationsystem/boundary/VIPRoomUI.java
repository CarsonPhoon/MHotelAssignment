package mhotelreservationsystem.boundary;

import java.util.Scanner;
import mhotelreservationsystem.control.VIPRoomControl;
import mhotelreservationsystem.report.VIPQueueReport;
import mhotelreservationsystem.repository.GuestRepository;
import mhotelreservationsystem.repository.MemberRepository;
import mhotelreservationsystem.utility.Validation;

/**
 *
 * @author zekai
 */
public class VIPRoomUI implements Navigable {

    private VIPRoomControl vipControl;
    private VIPQueueReport reportGenerator;
    private Scanner scanner = new Scanner(System.in);

    // 队友更新的构造函数 (接收 Repository)
    public VIPRoomUI(MemberRepository memberRepo, GuestRepository guestRepo) {
        this.vipControl = new VIPRoomControl(memberRepo, guestRepo);
        this.reportGenerator = new VIPQueueReport(this.vipControl);
    }

    @Override
    public void display() {
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
        System.out.println("0. Back to Main Menu");
        System.out.println("=====================================");
        System.out.print("Please enter your choice: ");
    }

    @Override
    public int getMaxChoice() {
        return 8;
    }

    @Override
    public Navigable handleChoice(int choice) {
        switch (choice) {
            case 1:
                this.registerVip();
                break;
            case 2:
                this.assignRoom();
                break;
            case 3:
                System.out.println("\n[System] Generating waiting list...\n");
                vipControl.displayAllWaitingVips();
                break;
            case 4:
                this.searchVip();
                break;
            case 5:
                this.redeemRewards();
                break;
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
                System.out.println("\nReturning to previous menu...");
                return null; // 返回 null 退出当前菜单
            default:
                System.out.println("\nInvalid option, please try again!");
        }

        // 完美保留了你的 DRY 原则：每次执行完停顿一下
        if (choice != 0) {
            System.out.println();
            Validation.pressEnterToContinue();
        }
        return this; // 继续留在当前菜单
    }

    // ==========================================
    // 下面全部是你写的、带有完美防呆机制的业务代码！
    // ==========================================

    private void registerVip() {
        System.out.println("\n--- Register New VIP ---");
        String memId = vipControl.generateNextMemberId();
        System.out.println("[System] Generated New Member ID: " + memId);
        
        String confirmNum = "";
        boolean isCancelled = false; 
        
        while (true) {
            System.out.print("Enter Confirmation Number (or enter 0 to cancel): ");
            confirmNum = scanner.nextLine().trim();
            
            if (confirmNum.equals("0")) {
                System.out.println("[System] Registration cancelled.");
                isCancelled = true;
                break; 
            }
            if (confirmNum.isEmpty()) {
                System.out.println("[Error] Input cannot be empty! Please try again.\n");
                continue;
            }
            if (vipControl.isVipAlreadyRegistered(confirmNum)) {
                System.out.println("\n[Error] Duplicate Registration!");
                System.out.println("-> The confirmation number '" + confirmNum + "' is already registered as a VIP.\n");
                continue;
            }
            if (vipControl.verifyGuestExists(confirmNum)) {
                System.out.println("[System] Booking Record Verified!");
                break; 
            } else {
                System.out.println("\n[Error] Booking Record Not Found!");
                System.out.println("-> The confirmation number '" + confirmNum + "' does not exist in the Walk-In database.\n");
            }
        }
        
        if (isCancelled) return; 
        
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
                System.out.println("\n[Error] Invalid member level! Please type exactly: BRONZE, SILVER, GOLD, or PLATINUM.\n");
            }
        }
        
        int minRequired = 0;
        int maxAllowed = Integer.MAX_VALUE; 
        switch (level) {
            case BRONZE: minRequired = 500; maxAllowed = 1499; break;
            case SILVER: minRequired = 1500; maxAllowed = 2999; break;
            case GOLD: minRequired = 3000; maxAllowed = 4999; break;
            case PLATINUM: minRequired = 5000; maxAllowed = Integer.MAX_VALUE; break;
        }

        int points = 0;
        while (true) {
            System.out.print("Enter Reward Points: ");
            if (scanner.hasNextInt()) {
                points = scanner.nextInt();
                scanner.nextLine();
                if (points < minRequired || points > maxAllowed) {
                    System.out.println("\n[Error] Points do not match the Member Level!");
                    System.out.println("-> Please enter points between " + minRequired + " and " + maxAllowed + ".\n");
                    continue;
                }
                break;
            } else {
                System.out.println("\n[Error] Invalid points entered! Please enter a valid number (e.g., 500).\n");
                scanner.nextLine();
            }
        }

        mhotelreservationsystem.entity.Member newVip = new mhotelreservationsystem.entity.Member(
            memId, confirmNum, level, points, 
            java.time.LocalDate.now(), 
            mhotelreservationsystem.entity.MembershipStatus.ACTIVE
        );
        
        if (vipControl.addVipToQueue(newVip)) {
            System.out.println("\n[Success] VIP successfully added to the waiting queue!");
        } else {
            System.out.println("\n[Error] Failed to add VIP to the queue.");
        }
    }

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
    }

    private void searchVip() {
        System.out.println("\n--- Search VIP Status ---");
        String confirmNum = "";
        mhotelreservationsystem.entity.Member vip = null;

        while (true) {
            System.out.print("Enter Confirmation Number (or enter 0 to cancel): ");
            confirmNum = scanner.nextLine().trim();

            if (confirmNum.equals("0")) {
                System.out.println("[System] Operation cancelled.");
                return; // 直接退出方法
            }
            if (confirmNum.isEmpty()) {
                System.out.println("[Error] Input cannot be empty! Please try again.\n");
                continue;
            }
            
            vip = vipControl.searchVip(confirmNum);
            if (vip == null) {
                System.out.println("[Error] VIP Number '" + confirmNum + "' not found! Please check and try again.\n");
                continue; 
            }
            break;
        }

        System.out.println("\n[Success] VIP Found!");
        System.out.println("Current Level  : " + vip.getMemberLevel());
        System.out.println("Current Points : " + vip.getRewardPoints());
    }

    private void redeemRewards() {
        System.out.println("\n--- VIP Rewards Redemption ---");
        String confirmNum = "";
        mhotelreservationsystem.entity.Member vip = null;

        while (true) {
            System.out.print("Enter Confirmation Number (or enter 0 to cancel): ");
            confirmNum = scanner.nextLine().trim();

            if (confirmNum.equals("0")) {
                System.out.println("[System] Operation cancelled.");
                return; 
            }
            if (confirmNum.isEmpty()) {
                System.out.println("[Error] Input cannot be empty! Please try again.\n");
                continue;
            }
            
            vip = vipControl.searchVip(confirmNum);
            if (vip == null) {
                System.out.println("[Error] VIP not found in the waiting queue! Please try again.\n");
                continue;
            }
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
        
        int redeemChoice = -1;
        if (scanner.hasNextInt()) {
            redeemChoice = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("[Error] Invalid input.");
            scanner.nextLine();
            return;
        }
        
        int pointsCost = 0;
        String itemName = "";
        
        switch(redeemChoice) {
            case 1: pointsCost = 200; itemName = "Welcome Drink"; break;
            case 2: pointsCost = 600; itemName = "Free Breakfast"; break;
            case 3: pointsCost = 1000; itemName = "Late Check-out"; break;
            case 4: pointsCost = 2500; itemName = "Free Room Upgrade"; break;
            case 0: System.out.println("Redemption cancelled."); return;
            default: System.out.println("Invalid choice."); return;
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
                        System.out.print("Please enter the Premium Room Number to upgrade to (or enter 0 to skip): ");
                        String upgradeRoom = scanner.nextLine().trim();
                        
                        if (upgradeRoom.equals("0")) {
                            System.out.println("[System] Room upgrade selection skipped.");
                            break;
                        }
                        if (upgradeRoom.isEmpty()) {
                            System.out.println("[Error] Room number cannot be empty! Please try again.\n");
                            continue;
                        }
                        
                        if (vipControl.updateRoomStatus(upgradeRoom, "Reserved")) {
                            System.out.println("[System] Awesome! Premium Room " + upgradeRoom + " has been successfully reserved.");
                            break;
                        } else {
                            System.out.println("[Error] Room '" + upgradeRoom + "' does not exist! Please try again.\n");
                        }
                    }
                }
            } else if (result.equals("INSUFFICIENT")) {
                System.out.println("\n[Error] Insufficient points to redeem " + itemName + ".");
            }
        }
    }
}
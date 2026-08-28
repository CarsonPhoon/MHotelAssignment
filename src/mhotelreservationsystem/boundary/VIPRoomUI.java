package mhotelreservationsystem.boundary;

import mhotelreservationsystem.control.VIPRoomControl;
import mhotelreservationsystem.report.VIPQueueReport;
import mhotelreservationsystem.repository.GuestRepository;
import mhotelreservationsystem.repository.MemberRepository;
import mhotelreservationsystem.repository.RoomRepository;
import mhotelreservationsystem.utility.ScannerUtility;
import mhotelreservationsystem.utility.Validation;

/**
 *
 * @author zekai
 */
public class VIPRoomUI implements Navigable {

    private VIPRoomControl vipControl;
    private VIPQueueReport reportGenerator;

    public VIPRoomUI(MemberRepository memberRepo, GuestRepository guestRepo, RoomRepository roomRepo) {
        this.vipControl = new VIPRoomControl(memberRepo, guestRepo, roomRepo);
        this.reportGenerator = new VIPQueueReport(this.vipControl);
    }

    @Override
    public void display() {
        System.out.println("\n=====================================");
        System.out.println("    VIP Priority Room Allocation     ");
        System.out.println("=====================================");
        System.out.println("1. Register VIP to Waiting Queue");
        System.out.println("2. Assign Room to Next VIP");
        System.out.println("3. View All Waiting VIPs");
        System.out.println("4. Search VIP Status in Queue");
        System.out.println("5. VIP Rewards Redemption");
        System.out.println("6. View Allocation Audit Log");
        System.out.println("7. Update VIP Membership Status");
        System.out.println("8. Report: Queue Status by Level");
        System.out.println("9. Report: High-Value VIPs");
        System.out.println("0. Back to Main Menu");
        System.out.println("=====================================");
    }

    @Override
    public int getMaxChoice() {
        return 9;
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
                vipControl.displayActiveAndCompletedVips();
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
                this.updateVipStatus();
                break;
            case 8:
                reportGenerator.generateQueueByLevelReport();
                break;
            case 9:
                reportGenerator.generateHighValueVipReport();
                break;
            case 0:
                System.out.println("\nReturning to previous menu...");
                return null;
            default:
                System.out.println("\nInvalid option, please try again!");
        }

        if (choice != 0) {
            System.out.println();
            Validation.pressEnterToContinue();
        }
        return this;
    }

    private void registerVip() {
        System.out.println("\n--- Register New VIP ---");
        String memId = vipControl.generateNextMemberId();
        System.out.println("[System] Generated New Member ID: " + memId);
        
        String confirmNum = "";
        boolean isCancelled = false; 
        
        while (true) {
            System.out.print("Enter Confirmation Number (or enter 0 to cancel): ");
            confirmNum = ScannerUtility.scanner.nextLine().trim();
            
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
                System.out.println("[System] Confirmation Number Verified!");
                break; 
            } else {
                System.out.println("\n[Error] Confirmation Number Not Found!");
                System.out.println("-> The confirmation number '" + confirmNum + "' does not exist in the Walk-In database.\n");
            }
        }
        
        if (isCancelled) return; 
        
        System.out.println("\n--- Level & Points Guidelines ---");
        System.out.println("BRONZE   : 100  - 299 pts");
        System.out.println("SILVER   : 300  - 499 pts");
        System.out.println("GOLD     : 500  - 2999 pts");
        System.out.println("ELITE    : 3000 - 5999 pts");
        System.out.println("DIAMOND  : 6000 - 9999 pts");
        System.out.println("PLATINUM : 10000+ pts");
        System.out.println("---------------------------------");
        
        mhotelreservationsystem.entity.MemberLevel level = null;
        while (true) {
            System.out.print("Enter Member Level (BRONZE/SILVER/GOLD/ELITE/DIAMOND/PLATINUM): ");
            String levelStr = ScannerUtility.scanner.nextLine().toUpperCase().trim();
            try {
                level = mhotelreservationsystem.entity.MemberLevel.valueOf(levelStr);
                break; 
            } catch (IllegalArgumentException e) {
                System.out.println("\n[Error] Invalid member level! Please type exactly: BRONZE, SILVER, GOLD, ELITE, DIAMOND, or PLATINUM.\n");
            }
        }
        
        int minRequired = 0;
        int maxAllowed = Integer.MAX_VALUE; 
        switch (level) {
            case BRONZE: minRequired = 100; maxAllowed = 299; break;
            case SILVER: minRequired = 300; maxAllowed = 499; break;
            case GOLD: minRequired = 500; maxAllowed = 2999; break;
            case ELITE: minRequired = 3000; maxAllowed = 5999; break;
            case DIAMOND: minRequired = 6000; maxAllowed = 9999; break;
            case PLATINUM: minRequired = 10000; maxAllowed = Integer.MAX_VALUE; break;
        }

        int points = 0;
        while (true) {
            System.out.print("Enter Reward Points: ");
            String pointStr = ScannerUtility.scanner.nextLine().trim();
            try {
                points = Integer.parseInt(pointStr);
                if (points < minRequired || points > maxAllowed) {
                    System.out.println("\n[Error] Points do not match the Member Level!");
                    System.out.println("-> Please enter points between " + minRequired + " and " + maxAllowed + ".\n");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("\n[Error] Invalid points entered! Please enter a valid number (e.g., 500).\n");
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

            vipControl.updateMemberStatus(assignedVip.getConfirmationNumber(), mhotelreservationsystem.entity.MembershipStatus.COMPLETED);

            System.out.println("\n--- Update Room Status ---");
            while (true) {
                System.out.print("Enter Room Number to assign (or enter 0 to skip): ");
                String roomNum = ScannerUtility.scanner.nextLine().trim();
                
                if (roomNum.equals("0")) {
                    System.out.println("[System] Room status update skipped.");
                    break;
                }
                if (roomNum.isEmpty()) {
                    System.out.println("[Error] Room number cannot be empty! Please try again.\n");
                    continue;
                }
                
                String statusResult = vipControl.updateRoomStatus(roomNum, mhotelreservationsystem.entity.RoomStatus.RESERVED);
                
                if (statusResult.equals("SUCCESS")) {
                    System.out.println("[System] Success! Room " + roomNum + " status updated to 'Reserved' in Room.txt.");
                    break;
                } else if (statusResult.equals("NOT_AVAILABLE")) {
                    System.out.println("[Error] Room '" + roomNum + "' is currently Occupied, Reserved, or under Maintenance!");
                    System.out.println("-> Please choose another available room.\n");
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
            confirmNum = mhotelreservationsystem.utility.ScannerUtility.scanner.nextLine().trim();

            if (confirmNum.equals("0")) {
                System.out.println("[System] Operation cancelled.");
                return; 
            }
            if (confirmNum.isEmpty()) {
                System.out.println("[Error] Input cannot be empty! Please try again.\n");
                continue;
            }

            vip = vipControl.searchVipInDatabase(confirmNum);
            
            if (vip == null) {
                System.out.println("[Error] VIP Number '" + confirmNum + "' not found! Please check and try again.\n");
                continue; 
            }
            break;
        }

        System.out.println("\n[Success] VIP Found!");
        System.out.println("Current Level  : " + vip.getMemberLevel());
        System.out.println("Current Points : " + vip.getRewardPoints());
        System.out.println("Current Status : " + vip.getMembershipStatus());
    }

    private void redeemRewards() {
        System.out.println("\n--- VIP Rewards Redemption ---");
        String confirmNum = "";
        mhotelreservationsystem.entity.Member vip = null;

        while (true) {
            System.out.print("Enter Confirmation Number (or enter 0 to cancel): ");
            confirmNum = ScannerUtility.scanner.nextLine().trim();

            if (confirmNum.equals("0")) {
                System.out.println("[System] Operation cancelled.");
                return; 
            }
            if (confirmNum.isEmpty()) {
                System.out.println("[Error] Input cannot be empty! Please try again.\n");
                continue;
            }
            
            vip = vipControl.searchVipInDatabase(confirmNum);
            
            if (vip == null) {
                System.out.println("[Error] VIP Number '" + confirmNum + "' not found! Please check and try again.\n");
                continue;
            }
            
            if (vip.getMembershipStatus() == mhotelreservationsystem.entity.MembershipStatus.INACTIVE) {
                System.out.println("[Error] This VIP account is currently INACTIVE. Redemption is not allowed.\n");
                continue;
            }
            
            break; 
        }
        
        System.out.println("\n[Success] VIP Found!");
        System.out.println("Current Level  : " + vip.getMemberLevel());
        System.out.println("Current Points : " + vip.getRewardPoints());
        
        int pointsCost = 0;
        String itemName = "";
        while (true) {
            System.out.println("\n--- Rewards Catalog ---");
            System.out.println("1. Welcome Drink          (200 pts)");
            System.out.println("2. Free Breakfast         (600 pts)");
            System.out.println("3. Late Check-out         (1000 pts)");
            System.out.println("0. Cancel");
            System.out.print("Select item to redeem: ");
            
            String inputChoice = ScannerUtility.scanner.nextLine().trim();
            
            if (inputChoice.isEmpty()) {
                System.out.println("\n[Error] Input cannot be empty or just spaces! Please try again.");
                continue;
            }

            int redeemChoice = -1;
            try {
                redeemChoice = Integer.parseInt(inputChoice);
            } catch (NumberFormatException e) {
                System.out.println("\n[Error] Invalid input! Please enter a valid number (0-3).");
                continue;
            }

            if (redeemChoice == 0) {
                System.out.println("[System] Redemption cancelled.");
                return;
            } else if (redeemChoice == 1) {
                pointsCost = 200; itemName = "Welcome Drink"; break;
            } else if (redeemChoice == 2) {
                pointsCost = 600; itemName = "Free Breakfast"; break;
            } else if (redeemChoice == 3) {
                pointsCost = 1000; itemName = "Late Check-out"; break;
            } else {
                System.out.println("\n[Error] Invalid choice! Please select an option between 0 and 3.");
            }
        }

        if (pointsCost > 0) {
            String result = vipControl.redeemPoints(confirmNum, pointsCost);
            if (result.equals("SUCCESS")) {
                System.out.println("\n[Success] " + itemName + " redeemed successfully!");
                System.out.println("-> Remaining Points: " + vip.getRewardPoints());
            } else if (result.equals("INSUFFICIENT")) {
                System.out.println("\n[Error] Insufficient points to redeem " + itemName + ".");
            }
        }
    }

    private void updateVipStatus() {
        System.out.println("\n--- Update VIP Membership Status ---");
        String confirmNum = "";
        mhotelreservationsystem.entity.Member vip = null;

        while (true) {
            System.out.print("Enter Confirmation Number (or enter 0 to cancel): ");
            confirmNum = ScannerUtility.scanner.nextLine().trim();
            
            if (confirmNum.equals("0")) {
                System.out.println("[System] Operation cancelled.");
                return;
            }
            if (confirmNum.isEmpty()) {
                System.out.println("\n[Error] Input cannot be empty or just spaces! Please try again.\n");
                continue;
            }

            vip = vipControl.searchVipInDatabase(confirmNum);
            if (vip == null) {
                System.out.println("\n[Error] VIP Number '" + confirmNum + "' not found in the database! Please try again.\n");
                continue;
            }
            break;
        }

        System.out.println("\n[Success] VIP Found!");
        System.out.println("Current Level  : " + vip.getMemberLevel());
        System.out.println("Current Status : " + vip.getMembershipStatus());

        mhotelreservationsystem.entity.MembershipStatus newStatus = null;

        while (true) {
            System.out.println("\n--- Select New Status ---");
            System.out.println("1. ACTIVE");
            System.out.println("2. INACTIVE");
            System.out.println("0. Cancel");
            System.out.print("Enter choice (1-2 or 0 to cancel): ");
            
            String choiceStr = ScannerUtility.scanner.nextLine().trim();
            
            if (choiceStr.isEmpty()) {
                System.out.println("\n[Error] Input cannot be empty or just spaces! Please try again.");
                continue;
            }

            if (choiceStr.equals("0")) {
                System.out.println("[System] Status update cancelled.");
                return;
            } else if (choiceStr.equals("1")) {
                newStatus = mhotelreservationsystem.entity.MembershipStatus.ACTIVE;
                break;
            } else if (choiceStr.equals("2")) {
                newStatus = mhotelreservationsystem.entity.MembershipStatus.INACTIVE;
                break;
            } else {
                System.out.println("\n[Error] Invalid choice! Please enter 1, 2, or 0.");
            }
        }

        if (vip.getMembershipStatus() == newStatus) {
            System.out.println("\n[System] The VIP is already " + newStatus + ". No changes made.");
            return;
        }

        if (vipControl.updateMemberStatus(confirmNum, newStatus)) {
            System.out.println("\n[Success] VIP status successfully updated to " + newStatus + "!");
        } else {
            System.out.println("\n[Error] Failed to update status. Please try again.");
        }
    }
}
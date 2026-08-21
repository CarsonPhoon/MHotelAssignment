package mhotelreservationsystem.boundary.frontdeskmodule;

import mhotelreservationsystem.boundary.Navigable;
import mhotelreservationsystem.control.FrontDeskControl;
import mhotelreservationsystem.entity.Guest;
import mhotelreservationsystem.entity.Member;
import mhotelreservationsystem.utility.ScannerUtility;
import mhotelreservationsystem.utility.Validation;

public class GuestManagementUI implements Navigable {

    private static final String Y = "\033[33m";
    private static final String R = "\033[0m";

    private FrontDeskControl control;

    public GuestManagementUI() {
        control = new FrontDeskControl();
    }

    @Override
    public void display() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         " + Y + "GUEST MANAGEMENT" + R);
        System.out.println("========================================");
        System.out.println("1. Search Guest");
        System.out.println("2. View Complete Guest Information");
        System.out.println("3. View Member Details");
        System.out.println("0. Back");
        System.out.println("========================================");
    }

    @Override
    public Navigable handleChoice(int choice) {
        switch (choice) {
            case 1: searchGuest(); break;
            case 2: viewCompleteGuestInformation(); break;
            case 3: viewMemberDetails(); break;
        }
        return null;
    }

    @Override
    public int getMaxChoice() {
        return 3;
    }

    private void searchGuest() {
        while (true) {
            String confirmationNumber = Validation.getStringOrReturn("Enter Confirmation Number (0 to return back): ");

            if (confirmationNumber.equals("0")) {
                return;
            }

            Guest guest = control.getGuestProfile(confirmationNumber);
            if (guest == null) {
                System.out.println("Guest not found. Please try again");
                continue;
            }

            System.out.println();
            System.out.println("========== Guest Information ==========");
            System.out.println("Confirmation No : " + guest.getConfirmationNumber());
            System.out.println("Guest Name      : " + guest.getGuestName());
            System.out.println("Phone Number    : " + guest.getPhoneNumber());
            System.out.println("Email           : " + guest.getEmail());
            System.out.println("Booking ID      : " + guest.getBookingID());
            System.out.println("Room Number     : " + guest.getRoomNumber());
            System.out.println("Check In Date   : " + guest.getCheckInDate());
            System.out.println("Check Out Date  : " + guest.getCheckOutDate());
            System.out.println("Status          : " + guest.getStatus());

            while (true) {
                System.out.print("\nPress ENTER to continue search, or enter 0 to return to menu: ");
                String continueInput = ScannerUtility.scanner.nextLine().trim();
                if (continueInput.equals("0")) {
                    return;
                }
                if (continueInput.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input, pls press ENTER to continue or 0 to return");
            }
        }
    }

    private void viewCompleteGuestInformation() {
        while (true) {
            String confirmationNumber = Validation.getStringOrReturn("Enter Confirmation Number (0 to return back): ");

            if (confirmationNumber.equals("0")) {
                return;
            }

            String completeInfo = control.getCompleteGuestInformation(confirmationNumber);
            if (completeInfo == null) {
                System.out.println("Guest not found. Please try again");
                continue;
            }

            System.out.println(completeInfo);

            while (true) {
                System.out.print("\nPress ENTER to continue search, or enter 0 to return to menu: ");
                String continueInput = ScannerUtility.scanner.nextLine().trim();
                if (continueInput.equals("0")) {
                    return;
                }
                if (continueInput.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input, pls press ENTER to continue or 0 to return");
            }
        }
    }

    private void viewMemberDetails() {
        while (true) {
            String memberID = Validation.getStringOrReturn("Enter Member ID (0 to return back): ");

            if (memberID.equals("0")) {
                return;
            }

            Member member = control.getMemberDetails(memberID);
            if (member == null) {
                System.out.println("Member not found. Please try again");
                continue;
            }

            System.out.println();
            System.out.println("========== Member Information ==========");
            System.out.println("Member ID        : " + member.getMemberID());
            System.out.println("Confirmation No  : " + member.getConfirmationNumber());
            System.out.println("Member Level     : " + member.getMemberLevel());
            System.out.println("Reward Points    : " + member.getRewardPoints());
            System.out.println("Join Date        : " + member.getJoinDate());
            System.out.println("Status           : " + member.getMembershipStatus());

            while (true) {
                System.out.print("\nPress ENTER to continue search, or enter 0 to return to menu: ");
                String continueInput = ScannerUtility.scanner.nextLine().trim();
                if (continueInput.equals("0")) {
                    return;
                }
                if (continueInput.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input, pls press ENTER to continue or 0 to return");
            }
        }
    }
}

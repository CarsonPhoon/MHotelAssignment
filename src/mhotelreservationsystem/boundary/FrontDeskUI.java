/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.boundary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.control.FrontDeskControl;
import mhotelreservationsystem.entity.*;
import mhotelreservationsystem.report.FrontDeskReport;
import mhotelreservationsystem.utility.ScannerUtility;
import mhotelreservationsystem.utility.Validation;
/**
 *
 * @author phoon
 */
public class FrontDeskUI {
    
    private FrontDeskControl control;
    private FrontDeskReport report;
    
    public FrontDeskUI(){
        control = new FrontDeskControl();
        report = new FrontDeskReport(control.getGuestRepository(), control.getBookingRepository(), control.getRoomRepository(), control.getMemberRepository());
    }
    
    // Click selection to call each function
    public void start(){
        int choice;
        
        do{
            displayMenu();
            choice = Validation.getIntOrReturn("Enter your choice: ", 0, 11);
            
            switch(choice) {

                case 1:
                    searchGuest();
                    break;

                case 2:
                    viewCompleteGuestInformation();
                    break;

                case 3:
                    viewMemberDetails();
                    break;

                case 4:
                    viewRoomDetails();
                    break;

                case 5:
                    viewBookingDetails();
                    break;

                case 6:
                    viewBilling();
                    break;

                case 7:
                    viewAllComments();
                    break;

                    
                case 8:
                    searchCommentsByGuest();
                    break;
                    
                case 9:
                    searchCommentsByDate();
                    break;

                case 10:
                    generateRoomOccupancyReport();
                    break;

                case 11:
                    generateGuestCheckInOutReport();
                    break;

                case 0:
                    System.out.println("Returning...");
                    break;
            }
        }while(choice != 0);
    }
    
    // Display UI Selection 
    private void displayMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         FRONT DESK SERVICE");
        System.out.println("========================================");
        System.out.println("1.  Search Guest");
        System.out.println("2.  View Complete Guest Information");
        System.out.println("3.  View Member Details");
        System.out.println("4.  View Room Details");
        System.out.println("5.  View Booking Details");
        System.out.println("6.  View Billing");
        System.out.println("7.  View All Comments");
        System.out.println("8.  Search Comments by Guest");
        System.out.println("9.  Search Comments by Date");
        System.out.println("10. Daily Room Occupancy Report");
        System.out.println("11. Daily Guest Check-In/Check-Out Report");
        System.out.println("0. Back");
        System.out.println("========================================");
    }
    
    // Search Guest
    private void searchGuest() {
        while(true) {
            String confirmationNumber = Validation.getStringOrReturn("Enter Confirmation Number (0 to return back): ");

            if(confirmationNumber.equals("0")) {
                return;
            }

            Guest guest = control.getGuestProfile(confirmationNumber);
            if(guest == null) {
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

            while(true) {
                System.out.print("\nPress ENTER to continue search, or enter 0 to return to menu: ");
                String continueInput = ScannerUtility.scanner.nextLine().trim();
                if(continueInput.equals("0")) {
                    return;
                }
                if(continueInput.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input, pls press ENTER to continue or 0 to return");
            }
        }
    }

    // View Complete Guest Information
    private void viewCompleteGuestInformation() {
        while(true) {
            String confirmationNumber = Validation.getStringOrReturn("Enter Confirmation Number (0 to return back): ");

            if(confirmationNumber.equals("0")) {
                return;
            }

            String completeInfo = control.getCompleteGuestInformation(confirmationNumber);
            if(completeInfo == null) {
                System.out.println("Guest not found. Please try again");
                continue;
            }

            System.out.println(completeInfo);

            while(true) {
                System.out.print("\nPress ENTER to continue search, or enter 0 to return to menu: ");
                String continueInput = ScannerUtility.scanner.nextLine().trim();
                if(continueInput.equals("0")) {
                    return;
                }
                if(continueInput.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input, pls press ENTER to continue or 0 to return");
            }
        }
    }

    // View Member Details
    private void viewMemberDetails() {
        while(true) {
            String memberID = Validation.getStringOrReturn("Enter Member ID (0 to return back): ");

            if(memberID.equals("0")) {
                return;
            }

            Member member = control.getMemberDetails(memberID);
            if(member == null) {
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

            while(true) {
                System.out.print("\nPress ENTER to continue search, or enter 0 to return to menu: ");
                String continueInput = ScannerUtility.scanner.nextLine().trim();
                if(continueInput.equals("0")) {
                    return;
                }
                if(continueInput.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input, pls press ENTER to continue or 0 to return");
            }
        }
    }

    // View Room Details
    private void viewRoomDetails() {
        while(true) {
            int roomNumber = Validation.getIntOrReturn("Enter Room Number (0 to return back): ",1,9999);

            if(roomNumber == 0) {
                return;
            }

            Room room = control.getRoomDetails(roomNumber);
            if(room == null) {
                System.out.println("Room not found. Please try again");
                continue;
            }

            System.out.println();
            System.out.println("========== Room Information ==========");
            System.out.println("Room Number : " + room.getRoomNumber());
            System.out.println("Room Type   : " + room.getRoomType());
            System.out.println("Floor       : " + room.getFloor());
            System.out.println("Capacity    : " + room.getCapacity());
            System.out.println("Room Rate   : RM " + String.format("%.2f", room.getRoomRate()));
            System.out.println("Status      : " + room.getStatus());

            while(true) {
                System.out.print("\nPress ENTER to continue search, or enter 0 to return to menu: ");
                String continueInput = ScannerUtility.scanner.nextLine().trim();
                if(continueInput.equals("0")) {
                    return;
                }
                if(continueInput.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input, pls press ENTER to continue or 0 to return");
            }
        }
    }

    // View Booking Details
    private void viewBookingDetails() {
        while(true) {
            String bookingID = Validation.getStringOrReturn("Enter Booking ID (0 to return back): ");

            if(bookingID.equals("0")) {
                return;
            }

            Booking booking = control.getBookingDetails(bookingID);
            if(booking == null) {
                System.out.println("Booking not found. Please try again");
                continue;
            }

            System.out.println();
            System.out.println("========== Booking Information ==========");
            System.out.println("Booking ID      : " + booking.getBookingID());
            System.out.println("Confirmation No : " + booking.getConfirmationNumber());
            System.out.println("Room Number     : " + booking.getRoomNumber());
            System.out.println("Room Type       : " + booking.getRoomType());
            System.out.println("Booking Date    : " + booking.getBookingDate());
            System.out.println("Check In Date   : " + booking.getCheckInDate());
            System.out.println("Check Out Date  : " + booking.getCheckOutDate());
            System.out.println("Guests          : " + booking.getNumberOfGuests());
            System.out.println("Total Amount    : RM " + String.format("%.2f", booking.getTotalAmount()));
            System.out.println("Status          : " + booking.getBookingStatus());

            while(true) {
                System.out.print("\nPress ENTER to continue search, or enter 0 to return to menu: ");
                String continueInput = ScannerUtility.scanner.nextLine().trim();
                if(continueInput.equals("0")) {
                    return;
                }
                if(continueInput.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input, pls press ENTER to continue or 0 to return");
            }
        }
    }

    // View Billing
    private void viewBilling() {
        while(true) {
            String bookingID = Validation.getStringOrReturn("Enter Booking ID (0 to return back): ");

            if(bookingID.equals("0")) {
                return;
            }

            Booking booking = control.getBookingDetails(bookingID);
            if(booking == null) {
                System.out.println("Booking not found. Please try again");
                continue;
            }

            System.out.println();
            System.out.println("======= Billing Information ========");
            System.out.println("Booking ID      : " + booking.getBookingID());
            System.out.println("Confirmation No : " + booking.getConfirmationNumber());
            System.out.println("Room Number     : " + booking.getRoomNumber());
            System.out.println("Room Type       : " + booking.getRoomType());
            System.out.println("Booking Date    : " + booking.getBookingDate());
            System.out.println("Check In        : " + booking.getCheckInDate());
            System.out.println("Check Out       : " + booking.getCheckOutDate());
            System.out.println("Total Amount    : RM " + String.format("%.2f", booking.getTotalAmount()));
            System.out.println("Status          : " + booking.getBookingStatus());

            while(true) {
                System.out.print("\nPress ENTER to continue search, or enter 0 to return to menu: ");
                String continueInput = ScannerUtility.scanner.nextLine().trim();
                if(continueInput.equals("0")) {
                    return;
                }
                if(continueInput.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input, pls press ENTER to continue or 0 to return");
            }
        }
    }

    // View All Comments (Sorted)
    private void viewAllComments() {
        ArrayListADT<Comment> allComments = control.getAllComments();

        // Bubble Sort by date descending (newest first)
        Comment[] arr = new Comment[allComments.getNumberOfEntries()];
        for(int i = 0; i < allComments.getNumberOfEntries(); i++){
            arr[i] = allComments.get(i);
        }
        for(int i = 0; i < arr.length - 1; i++){
            for(int j = 0; j < arr.length - 1 - i; j++){
                if(arr[j].getDate().compareTo(arr[j + 1].getDate()) < 0){
                    Comment temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        System.out.println();
        System.out.println("==================================================================================================");
        System.out.printf("%-12s %-8s %-12s %-6s %-10s %-10s %s%n",
                "Date", "ID", "Confirm", "Room", "Type", "Status", "Description");
        System.out.println("==================================================================================================");

        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }

        System.out.println("==================================================================================================");
        System.out.println("Total Comments : " + arr.length);

        System.out.print("\nPress ENTER to return to menu... ");
        ScannerUtility.scanner.nextLine();
    }
    
    
    // Search Comments by Guest Confirmation Number
    private void searchCommentsByGuest() {
        while(true) {
            String confirmationNumber = Validation.getStringOrReturn("Enter Confirmation Number (0 to return back): ");

            if(confirmationNumber.equals("0")) {
                return;
            }

            if(control.searchGuest(confirmationNumber) == null) {
                System.out.println("Guest not found. Please try again");
                continue;
            }

            ArrayListADT<Comment> results = control.searchCommentsByConfirmation(confirmationNumber);

            System.out.println();
            System.out.println("========== Comments for Guest " + confirmationNumber + " ==========");
            System.out.printf("%-12s %-8s %-12s %-6s %-10s %-10s %s%n",
                    "Date", "ID", "Confirm", "Room", "Type", "Status", "Description");
            System.out.println("---------------------------------------------------------------------------");

            if(results.getNumberOfEntries() == 0){
                System.out.println("No comments found for this guest.");
            } else {
                for(int i = 0; i < results.getNumberOfEntries(); i++){
                    System.out.println(results.get(i));
                }
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("Total Comments : " + results.getNumberOfEntries());
            }

            while(true) {
                System.out.print("\nPress ENTER to continue search, or enter 0 to return to menu: ");
                String continueInput = ScannerUtility.scanner.nextLine().trim();
                if(continueInput.equals("0")) {
                    return;
                }
                if(continueInput.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input, pls press ENTER to continue or 0 to return");
            }
        }
    }

    // Search Comments by Date
    private void searchCommentsByDate() {
        while(true) {
            System.out.print("Enter date (yyyy-MM-dd) (0 to return back): ");
            String input = ScannerUtility.scanner.nextLine().trim();

            if(input.equals("0")) {
                return;
            }

            LocalDate date;
            try{
                date = LocalDate.parse(input);
            }catch(DateTimeParseException e){
                System.out.println("Invalid date format. Please use yyyy-MM-dd. Try again");
                continue;
            }

            ArrayListADT<Comment> results = control.searchCommentsByDate(date);

            System.out.println();
            System.out.println("========== Comments on " + date + " ==========");
            System.out.printf("%-12s %-8s %-12s %-6s %-10s %-10s %s%n",
                    "Date", "ID", "Confirm", "Room", "Type", "Status", "Description");
            System.out.println("---------------------------------------------------------------------------");

            if(results.getNumberOfEntries() == 0){
                System.out.println("No comments found on this date.");
            } else {
                for(int i = 0; i < results.getNumberOfEntries(); i++){
                    System.out.println(results.get(i));
                }
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("Total Comments : " + results.getNumberOfEntries());
            }

            while(true) {
                System.out.print("\nPress ENTER to continue search, or enter 0 to return to menu: ");
                String continueInput = ScannerUtility.scanner.nextLine().trim();
                if(continueInput.equals("0")) {
                    return;
                }
                if(continueInput.isEmpty()) {
                    break;
                }
                System.out.println("Invalid input, pls press ENTER to continue or 0 to return");
            }
        }
    }

    // Daily Room Occupancy Report
    private void generateRoomOccupancyReport() {
        report.generateRoomOccupancyReport();
        System.out.print("\nPress ENTER to return to menu... ");
        ScannerUtility.scanner.nextLine();
    }

    // Daily Guest Check-In/Check-Out Report
    private void generateGuestCheckInOutReport() {
        report.generateGuestCheckInOutReport();
        System.out.print("\nPress ENTER to return to menu... ");
        ScannerUtility.scanner.nextLine();
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.boundary;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import mhotelreservationsystem.control.WalkInControl;
import mhotelreservationsystem.entity.Booking;
import mhotelreservationsystem.report.WalkInReport;
import mhotelreservationsystem.utility.ScannerUtility;
import mhotelreservationsystem.utility.Validation;

/**
 * Walk-In UI: Register walk-in guests, manage pending bookings, and generate reports.
 */
public class WalkInUI {

    private WalkInControl control;
    private WalkInReport report;

    public WalkInUI(){
        control = new WalkInControl();
        report = new WalkInReport(
            control.getGuestRepository(),
            control.getBookingRepository(),
            control.getRoomRepository(),
            control.getPendingBookings()
        );
    }

    public void start(){
        int choice;

        do{
            displayMenu();
            choice = Validation.getIntOrReturn("Enter your choice: ", 0, 9);

            switch(choice){
                case 1:
                    registerWalkIn();
                    break;
                case 2:
                    viewPendingBookings();
                    break;
                case 3:
                    confirmPendingBooking();
                    break;
                case 4:
                    checkOutGuest();
                    break;
                case 5:
                    control.displayRoomStatus();
                    break;
                case 6:
                    report.generateDailyWalkInReport();
                    break;
                case 7:
                    report.generateWalkInRevenueAnalysis();
                    break;
                case 8:
                    report.generateDailyCheckOutReport();
                    break;
                case 9:
                    addCommentOrComplain();
                    break;
                case 0:
                    System.out.println("Returning...");
                    break;
            }

            if(choice != 0){
                Validation.pressEnterToContinue();
            }

        }while(choice != 0);
    }

    private void displayMenu(){
        System.out.println();
        System.out.println("========================================");
        System.out.println("         WALK-IN REGISTRATION");
        System.out.println("========================================");
        System.out.println("1. Register Walk-In");
        System.out.println("2. View Pending Bookings");
        System.out.println("3. Confirm Pending Booking (Check-In)");
        System.out.println("4. Check-Out Guest");
        System.out.println("5. View Room Status");
        System.out.println("6. Daily Walk-In Report");
        System.out.println("7. Revenue Analysis Report");
        System.out.println("8. Daily Check-Out Report");
        System.out.println("9. Add Comment/Complain");
        System.out.println("0. Back");
        System.out.println("========================================");
    }

    private void registerWalkIn(){
        // Get guest name with validation
        String name = Validation.getNameOrReturn("Enter guest name (0 to return back): ");
        if(name.equals("0")) return;

        // Get phone number with validation
        String phone = Validation.getPhoneOrReturn("Enter phone number (0 to return back): ");
        if(phone.equals("0")) return;

        // Get email with validation
        String email = Validation.getEmailOrReturn("Enter email (0 to return back): ");
        if(email.equals("0")) return;

        // Show available rooms
        control.displayAvailableRooms();

        // Get room number and validate it exists and is available
        int roomNumber;
        int capacity;
        while(true) {
            roomNumber = Validation.getIntOrReturn("Enter Room Number (0 to return back): ", 1, 9999);
            if(roomNumber == 0) return;
            
            // Check if room exists
            if(!control.isRoomAvailable(roomNumber)) {
                System.out.println("Room not available. Please select from available rooms.");
                continue;
            }
            
            // Get room capacity from repository
            capacity = control.getRoomCapacity(roomNumber);
            break;
        }

        // Get number of guests with validation against room capacity
        int numGuests;
        while(true) {
            numGuests = Validation.getIntOrReturn("Enter number of guests (0 to return back): ", 1, 100);
            if(numGuests == 0) return;
            
            if(numGuests > capacity) {
                System.out.println("Number of guests (" + numGuests + ") exceeds room capacity (" + capacity + ").");
                continue;
            }
            break;
        }

        // Get check-in date with validation
        LocalDate checkIn = null;
        while(checkIn == null) {
            System.out.print("Enter check-in date (yyyy-MM-dd) (0 to return back): ");
            String in = ScannerUtility.scanner.nextLine().trim();
            
            if(in.equals("0")) return;
            
            if(in.isEmpty()) {
                System.out.println("Date cannot be empty.");
                continue;
            }
            
            try {
                checkIn = LocalDate.parse(in);
                
                // Check if check-in date is not in the past
                if(checkIn.isBefore(LocalDate.now())) {
                    System.out.println("Check-in date cannot be in the past.");
                    checkIn = null;
                    continue;
                }
            }catch(DateTimeParseException e){
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
                continue;
            }
        }

        // Get check-out date with validation
        LocalDate checkOut = null;
        while(checkOut == null) {
            System.out.print("Enter check-out date (yyyy-MM-dd) (0 to return back): ");
            String out = ScannerUtility.scanner.nextLine().trim();
            
            if(out.equals("0")) return;
            
            if(out.isEmpty()) {
                System.out.println("Date cannot be empty.");
                continue;
            }
            
            try {
                checkOut = LocalDate.parse(out);
                
                // Check if check-out date is after check-in date
                if(checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
                    System.out.println("Check-out date must be after check-in date.");
                    checkOut = null;
                    continue;
                }
                
                // Check maximum stay (e.g., 365 days)
                int stayDays = (int)(checkOut.toEpochDay() - checkIn.toEpochDay());
                if(stayDays > 365) {
                    System.out.println("Stay period cannot exceed 365 days.");
                    checkOut = null;
                    continue;
                }
            }catch(DateTimeParseException e){
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
                continue;
            }
        }

        // Register the booking
        Booking booking = control.registerWalkInPending(name, phone, email, roomNumber, numGuests, checkIn, checkOut);

        if(booking != null){
            System.out.println("Booking added to pending queue. Please wait for confirmation.");
            System.out.println("Booking ID: " + booking.getBookingID());
            System.out.println("Confirmation Number: " + booking.getConfirmationNumber());

            if (Validation.confirmYesNo("Submit comment/complaint now?")) {
                addCommentByConfirmation(booking.getConfirmationNumber());
            }
        } else {
            System.out.println("Failed to register booking. Please try again.");
        }
    }

    private void viewPendingBookings(){
        control.displayPendingBookings();
    }

    private void confirmPendingBooking(){
        control.displayPendingBookings();
        
        if(control.getPendingBookings().getNumberOfEntries() == 0){
            System.out.println("No pending bookings to confirm.");
            return;
        }

        int index = Validation.getIntOrReturn("Enter booking number to confirm (0 to cancel): ", 0, control.getPendingBookings().getNumberOfEntries());
        
        if(index == 0){
            return;
        }

        if(control.confirmPendingBooking(index - 1)){
            System.out.println("Booking confirmed successfully.");
        } else {
            System.out.println("Failed to confirm booking.");
        }
    }

    private void checkOutGuest(){
        control.displayCheckedInBookings();

        if (control.getCheckedInCount() == 0) {
            return;
        }

        String confirmation = Validation.getStringOrReturn("Enter No. OR confirmation number to check out (0 to cancel): ");
        if (confirmation.equals("0")) {
            return;
        }

        // If user enters displayed row number, map it to confirmation number.
        if (confirmation.matches("\\d+")) {
            try {
                int no = Integer.parseInt(confirmation);
                String mapped = control.getCheckedInConfirmationByDisplayIndex(no);
                if (mapped != null) {
                    confirmation = mapped;
                }
            } catch (NumberFormatException e) {
                // keep original input as confirmation number
            }
        }

        if (control.checkOutGuest(confirmation)) {
            System.out.println("Check-out completed.");
            if (Validation.confirmYesNo("Submit comment/complaint during check-out?")) {
                addCommentByConfirmation(confirmation);
            }
        } else {
            System.out.println("Check-out failed.");
        }
    }

    private void addCommentOrComplain(){
        String confirmation = Validation.getStringOrReturn("Enter confirmation number (0 to cancel): ");
        if (confirmation.equals("0")) {
            return;
        }

        if (!control.hasGuestByConfirmation(confirmation)) {
            System.out.println("Confirmation number not found.");
            return;
        }

        addCommentByConfirmation(confirmation);
    }

    private void addCommentByConfirmation(String confirmation){
        int typeChoice = Validation.getIntOrReturn("Select type (1=Comment, 2=Complain, 0=Cancel): ", 1, 2);
        if (typeChoice == 0) {
            return;
        }

        String description = Validation.getStringOrReturn("Enter description (0 to cancel): ");
        if (description.equals("0")) {
            return;
        }

        if (typeChoice == 1) {
            control.addGuestComment(confirmation, mhotelreservationsystem.entity.CommentType.COMMENT, description);
        } else {
            control.addGuestComment(confirmation, mhotelreservationsystem.entity.CommentType.COMPLAINT, description);
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.boundary;

import mhotelreservationsystem.control.FrontDeskControl;
import mhotelreservationsystem.report.FrontDeskReport;
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
        report = new FrontDeskReport();
    }
    
    public void start(){
        int choice;
        
        do{
            displayMenu();
            choice = Validation.getIntOrReturn("Enter your choice: ", 0, 9);
            boolean back = false;
            
            switch(choice) {

                case 1:
                    back = searchGuest();
                    break;

                case 2:
                    back = viewCompleteGuestInformation();
                    break;

                case 3:
                    back = checkRoomAvailability();
                    break;

                case 4:
                    back = viewRoomDetails();
                    break;

                case 5:
                    back = viewBookingDetails();
                    break;

                case 6:
                    back = viewBilling();
                    break;

                case 7:
                    back = viewMemberDetails();
                    break;

                case 8:
                    report.generateRoomOccupancyReport();
                    break;

                case 9:
                    report.generateGuestCheckInOutReport();
                    break;

                case 0:
                    System.out.println("Returning...");
                    break;
            }
            
            if(choice != 0 && !back){
                Validation.pressEnterToContinue();
            }
        }while(choice != 0);
    }
    
    private void displayMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         FRONT DESK SERVICE");
        System.out.println("========================================");
        System.out.println("1. Search Guest");
        System.out.println("2. View Complete Guest Information");
        System.out.println("3. Check Room Availability");
        System.out.println("4. View Room Details");
        System.out.println("5. View Booking Details");
        System.out.println("6. View Billing");
        System.out.println("7. View Member Details");
        System.out.println("8. Room Occupancy Report");
        System.out.println("9. Guest Check-In/Check-Out Report");
        System.out.println("0. Back");
        System.out.println("========================================");
    }
    
    // Search Guest
    private boolean searchGuest() {
        String confirmationNumber = Validation.getStringOrReturn("Enter Confirmation Number (0 to return back):");

        if(confirmationNumber.equals("0")) {
            return true;
        }

        control.viewGuestProfile(confirmationNumber);
        return false;
    }

    
    // View Complete Guest Information
    private boolean viewCompleteGuestInformation() {
        String confirmationNumber = Validation.getStringOrReturn("Enter Confirmation Number (0 to return back):");

        if(confirmationNumber.equals("0")) {
            return true;
        }

        control.viewCompleteGuestInformation(confirmationNumber);
        return false;
    }


    // Check Room Availability
    private boolean checkRoomAvailability() {
        int roomNumber = Validation.getIntOrReturn("Enter Room Number (0 to return back):",1,9999);

        if(roomNumber == 0) {
            return true;
        }

        control.displayRoomAvailability(roomNumber);
        return false;
    }


    // View Room Details
    private boolean viewRoomDetails() {
        int roomNumber = Validation.getIntOrReturn("Enter Room Number (0 to return back):",1,9999);

        if(roomNumber == 0) {
            return true;
        }

        control.viewRoomDetails(roomNumber);
        return false;
    }


    // View Booking Details
    private boolean viewBookingDetails() {
        String bookingID = Validation.getStringOrReturn("Enter Booking ID (0 to return back):");

        if(bookingID.equals("0")) {
            return true;
        }

        control.viewBookingDetails(bookingID);
        return false;
    }


    // View Billing
    private boolean viewBilling() {
        String bookingID = Validation.getStringOrReturn("Enter Booking ID (0 to return back):");

        if(bookingID.equals("0")) {
            return true;
        }

        control.viewBilling(bookingID);
        return false;
    }

    
    // View Member Details
    private boolean viewMemberDetails() {
        String confirmationNumber = Validation.getStringOrReturn("Enter Confirmation Number (0 to return back):");

        if(confirmationNumber.equals("0")) {
            return true;
        }

        control.viewMemberDetails(confirmationNumber);
        return false;
    }
}
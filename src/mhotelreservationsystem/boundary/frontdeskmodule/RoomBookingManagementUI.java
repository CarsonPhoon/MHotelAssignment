/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phoon
 */

package mhotelreservationsystem.boundary.frontdeskmodule;

import mhotelreservationsystem.boundary.Navigable;
import mhotelreservationsystem.control.FrontDeskControl;
import mhotelreservationsystem.entity.Booking;
import mhotelreservationsystem.entity.Room;
import mhotelreservationsystem.repository.*;
import mhotelreservationsystem.utility.ScannerUtility;
import mhotelreservationsystem.utility.Validation;

public class RoomBookingManagementUI implements Navigable {

    private static final String Y = "\033[33m";
    private static final String R = "\033[0m";

    private FrontDeskControl control;

    // Constructor: Create a Control object
    public RoomBookingManagementUI(GuestRepository guestRepository, BookingRepository bookingRepository, RoomRepository roomRepository, MemberRepository memberRepository, CommentRepository commentRepository) {
        control = new FrontDeskControl(guestRepository, bookingRepository, roomRepository, memberRepository, commentRepository);
    }

    @Override
    public void display() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("      " + Y + "ROOM & BOOKING MANAGEMENT" + R);
        System.out.println("========================================");
        System.out.println("1. View Room Details");
        System.out.println("2. View Booking Details");
        System.out.println("3. View Billing");
        System.out.println("0. Back");
        System.out.println("========================================");
    }

    @Override
    public Navigable handleChoice(int choice) {
        switch (choice) {
            case 1: viewRoomDetails(); break;
            case 2: viewBookingDetails(); break;
            case 3: viewBilling(); break;
        }
        return null;
    }

    @Override
    public int getMaxChoice() {
        return 3;
    }

    // View room details
    private void viewRoomDetails() {
        while (true) {
            int roomNumber = Validation.getIntOrReturn("Enter Room Number (0 to return back): ", 1, 9999);

            if (roomNumber == 0) {
                return;
            }

            Room room = control.getRoomDetails(roomNumber);
            if (room == null) {
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

    
    // View booking details
    private void viewBookingDetails() {
        while (true) {
            String bookingID = Validation.getStringOrReturn("Enter Booking ID (0 to return back): ");

            if (bookingID.equals("0")) {
                return;
            }

            Booking booking = control.getBookingDetails(bookingID);
            if (booking == null) {
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
 
    // View booking details
    private void viewBilling() {
        while (true) {
            String bookingID = Validation.getStringOrReturn("Enter Booking ID (0 to return back): ");

            if (bookingID.equals("0")) {
                return;
            }

            Booking booking = control.getBookingDetails(bookingID);
            if (booking == null) {
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
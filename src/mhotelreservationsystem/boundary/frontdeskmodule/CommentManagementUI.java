/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author phoon
 */
package mhotelreservationsystem.boundary.frontdeskmodule;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import mhotelreservationsystem.boundary.Navigable;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.control.FrontDeskControl;
import mhotelreservationsystem.entity.Comment;
import mhotelreservationsystem.repository.*;
import mhotelreservationsystem.utility.ScannerUtility;
import mhotelreservationsystem.utility.Validation;

public class CommentManagementUI implements Navigable {

    private static final String Y = "\033[33m";
    private static final String R = "\033[0m";
    private static final int PAGE_SIZE = 15;  // Comments per page

    private FrontDeskControl control;

    // Constructor: Create a Control object
    public CommentManagementUI(GuestRepository guestRepository, BookingRepository bookingRepository, RoomRepository roomRepository, MemberRepository memberRepository, CommentRepository commentRepository) {
        control = new FrontDeskControl(guestRepository, bookingRepository, roomRepository, memberRepository, commentRepository);
    }

    @Override
    public void display() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("        " + Y + "COMMENT MANAGEMENT" + R);
        System.out.println("========================================");
        System.out.println("1. View All Comments");
        System.out.println("2. Search Comments by Guest");
        System.out.println("3. Search Comments by Date");
        System.out.println("0. Back");
        System.out.println("========================================");
    }

    @Override
    public Navigable handleChoice(int choice) {
        switch (choice) {
            case 1: viewAllComments(); break;
            case 2: searchCommentsByGuest(); break;
            case 3: searchCommentsByDate(); break;
        }
        return null;
    }

    @Override
    public int getMaxChoice() {
        return 3;
    }
    
    // View all comments with pagination
    private void viewAllComments() {
        Comment[] arr = control.getAllCommentsSortedByDate();
        int totalComments = arr.length;
        
        if (totalComments == 0) {
            System.out.println("\nNo comments found.");
            System.out.print("\nPress ENTER to return to menu... ");
            ScannerUtility.scanner.nextLine();
            return;
        }
        
        int totalPages = (totalComments + PAGE_SIZE - 1) / PAGE_SIZE;  // Calculate total pages
        int currentPage = 0;  // Start from page 0
        
        while (true) {
            // Calculate start and end index for current page
            int startIndex = currentPage * PAGE_SIZE;
            int endIndex = Math.min(startIndex + PAGE_SIZE, totalComments);
            
            // Clear screen and display header
            System.out.println();
            System.out.println("==================================================================================================");
            System.out.printf("%-12s %-8s %-12s %-6s %-10s %-10s %s%n",
                    "Date", "ID", "Confirm", "Room", "Type", "Status", "Description");
            System.out.println("==================================================================================================");
            
            // Display comments for current page
            for (int i = startIndex; i < endIndex; i++) {
                System.out.println(arr[i]);
            }
            
            System.out.println("==================================================================================================");
            System.out.println("Total Comments : " + totalComments + " | Page " + (currentPage + 1) + " of " + totalPages);
            
            // Display navigation options
            if (currentPage < totalPages - 1) {
                System.out.println("\nPress ENTER for next page, or enter 0 to return to menu: ");
            } else {
                System.out.println("\nThis is the last page. Press ENTER to return to menu: ");
            }
            
            String input = ScannerUtility.scanner.nextLine().trim();
            
            if (input.equals("0")) {
                return;  // Return to menu
            }
            
            if (currentPage < totalPages - 1) {
                currentPage++;  // Go to next page
            } else {
                return;  // Last page, return to menu
            }
        }
    }
    
    // Search comment by guest
    private void searchCommentsByGuest() {
        while (true) {
            String confirmationNumber = Validation.getStringOrReturn("Enter Confirmation Number (0 to return back): ");

            if (confirmationNumber.equals("0")) {
                return;
            }

            if (control.searchGuest(confirmationNumber) == null) {
                System.out.println("Guest not found. Please try again");
                continue;
            }

            ArrayListADT<Comment> results = control.searchCommentsByConfirmation(confirmationNumber);

            System.out.println();
            System.out.println("========== Comments for Guest " + confirmationNumber + " ==========");
            System.out.printf("%-12s %-8s %-12s %-6s %-10s %-10s %s%n",
                    "Date", "ID", "Confirm", "Room", "Type", "Status", "Description");
            System.out.println("---------------------------------------------------------------------------");

            if (results.getNumberOfEntries() == 0) {
                System.out.println("No comments found for this guest.");
            } else {
                // Display search results
                for (int i = 0; i < results.getNumberOfEntries(); i++) {
                    System.out.println(results.get(i));
                }
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("Total Comments : " + results.getNumberOfEntries());
            }

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

    // Search comments by date
    private void searchCommentsByDate() {
        while (true) {
            System.out.print("Enter date (yyyy-MM-dd) (0 to return back): ");
            String input = ScannerUtility.scanner.nextLine().trim();

            if (input.equals("0")) {
                return;
            }

            LocalDate date;
            try {
                date = LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd. Try again");
                continue;
            }

            ArrayListADT<Comment> results = control.searchCommentsByDate(date);

            System.out.println();
            System.out.println("========== Comments on " + date + " ==========");
            System.out.printf("%-12s %-8s %-12s %-6s %-10s %-10s %s%n",
                    "Date", "ID", "Confirm", "Room", "Type", "Status", "Description");
            System.out.println("---------------------------------------------------------------------------");

            if (results.getNumberOfEntries() == 0) {
                System.out.println("No comments found on this date.");
            } else {
                // Display search results
                for (int i = 0; i < results.getNumberOfEntries(); i++) {
                    System.out.println(results.get(i));
                }
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("Total Comments : " + results.getNumberOfEntries());
            }

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

package mhotelreservationsystem.boundary.frontdeskmodule;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import mhotelreservationsystem.boundary.Navigable;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.control.FrontDeskControl;
import mhotelreservationsystem.entity.Comment;
import mhotelreservationsystem.utility.ScannerUtility;
import mhotelreservationsystem.utility.Validation;

public class CommentManagementUI implements Navigable {

    private static final String Y = "\033[33m";
    private static final String R = "\033[0m";

    private FrontDeskControl control;

    public CommentManagementUI() {
        control = new FrontDeskControl();
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

    private void viewAllComments() {
        ArrayListADT<Comment> allComments = control.getAllComments();

        Comment[] arr = new Comment[allComments.getNumberOfEntries()];
        for (int i = 0; i < allComments.getNumberOfEntries(); i++) {
            arr[i] = allComments.get(i);
        }
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j].getDate().compareTo(arr[j + 1].getDate()) < 0) {
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

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

        System.out.println("==================================================================================================");
        System.out.println("Total Comments : " + arr.length);

        System.out.print("\nPress ENTER to return to menu... ");
        ScannerUtility.scanner.nextLine();
    }

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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.boundary;

import mhotelreservationsystem.entity.RoomCleaningStatus;
import mhotelreservationsystem.control.HousekeepingControl;
import mhotelreservationsystem.repository.RoomRepository;
import mhotelreservationsystem.repository.StaffRepository;
import mhotelreservationsystem.report.HousekeepingReport;
import mhotelreservationsystem.utility.Validation;
import mhotelreservationsystem.utility.ScannerUtility;

/**
 *
 * @author chanzj
 */
public class HousekeepingUI implements Navigable {
    private HousekeepingControl houseKeeping;
    private HousekeepingReport report;
    private RoomRepository roomRepository;

    public HousekeepingUI(RoomRepository roomRepository, HousekeepingControl housekeepingControl){
        this.roomRepository = roomRepository;
        this.houseKeeping = housekeepingControl;
        this.report = new HousekeepingReport(houseKeeping, roomRepository);
    }

    // OLD: do-while + switch navigation pattern 
    /*
    public void startModule(){
        int choice;
        do{
            menu();
            choice = getIntInput();

            switch(choice){
                case 1 -> updateCleaningStatus();
                case 2 -> viewRoomStatus();
                case 3 -> viewCleaningTaskLog();
                case 4 -> rollBackStatus();
                case 5 -> System.out.println("Exiting Houseking Module");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 5);

    }

    public void menu(){
        System.out.println("\n");
        System.out.println(" ---------------------------- ");
        System.out.println("|                            |");
        System.out.println("|        HOUSEKEEPING        |");
        System.out.println("|                            |");
        System.out.println(" ---------------------------- ");
        System.out.println("\n1. Update Cleaning Status");
        System.out.println("2. View Room Status");
        System.out.println("3. View Cleaning Task Log");
        System.out.println("4. Roll Back Room Status");
        System.out.println("5. Back to Main Menu");
        System.out.print("The options choosed: ");

    }
    */

    // Stack navigation: display menu for Navigator
    @Override
    public void display() {
        System.out.println("\n");
        System.out.println(" ---------------------------- ");
        System.out.println("|                            |");
        System.out.println("|        HOUSEKEEPING        |");
        System.out.println("|                            |");
        System.out.println(" ---------------------------- ");
        System.out.println("\n1. Update Cleaning Status");
        System.out.println("2. View Room Status");
        System.out.println("3. View Cleaning Task Log");
        System.out.println("4. Roll Back Room Status");
        System.out.println("5. View Housekeeping Report");
        System.out.println("0. Back");
        System.out.println(" ---------------------------- ");
    }

    // Stack navigation - route choice to action, return null to stay on this page
    @Override
    public Navigable handleChoice(int choice) {
        switch (choice) {
            case 1: updateCleaningStatus(); 
                break;
            case 2: viewRoomStatus(); 
                break;
            case 3: viewCleaningTaskLog(); 
                break;
            case 4: rollBackStatus(); 
                break;
            case 5: viewReport();
                break;
            default: 
                    break;
        }
        if (choice >= 1 && choice <= 5){
            Validation.pressEnterToContinue();
        }
        return null;
    }

    // Stack navigation: max selectable option (0 is handled by Navigator)
    @Override
    public int getMaxChoice() {
        return 5;
    }
    
    // checks if room exists
    private boolean isValidRoom(int roomNumber){
        return roomRepository.searchRoom(roomNumber) != null;
    }
    
    // prints status of all rooms
    private void displayAllRoomStatus(){
        System.out.println("\n--- Current Room Status ---");
        for (int i = 0; i < roomRepository.getTotalRoom(); i++){
            int roomNumber = roomRepository.getRoom(i).getRoomNumber();
            RoomCleaningStatus status = houseKeeping.getCurrentStatus(roomNumber);
            String label = (status == null) ? "No cleaning record available" : status.getLabel();
            System.out.println("Room " + roomNumber + " : " + label);
        }
        System.out.println("---------------------------");
    }
    
    // reads a validated integer input from console
    private int getIntInput(){
        while (!ScannerUtility.scanner.hasNextInt()){
            System.out.println("Invalid input. Please enter a number.");
            ScannerUtility.scanner.next();
            System.out.print("The option chosen: ");
        }
        int input = ScannerUtility.scanner.nextInt();
        ScannerUtility.scanner.nextLine();
        return input;
    }
    
    // advances a room's cleaning status
    private void updateCleaningStatus(){
        displayAllRoomStatus();
        System.out.print("\nEnter room number: ");
        int roomNumber = getIntInput();
        if (!isValidRoom(roomNumber)){
            System.out.println("Room " + roomNumber + " does not exist.");
            return;
        }
        houseKeeping.advanceCleaningStatus(roomNumber);
    }

    // shows all room room statuses
    private void viewRoomStatus(){
        displayAllRoomStatus();
    }

    // shows one room's task log
    private void viewCleaningTaskLog(){
        displayAllRoomStatus();
        System.out.print("\nEnter room number: ");
        int roomNumber = getIntInput();
        if (!isValidRoom(roomNumber)){
            System.out.println("Room " + roomNumber + " does not exist.");
            return;
        }
        houseKeeping.viewTaskLog(roomNumber);
    }

    // rolls back one room's status
    private void rollBackStatus(){
        displayAllRoomStatus();
        System.out.print("\nEnter room number: ");
        int roomNumber = getIntInput();
        if (!isValidRoom(roomNumber)){
            System.out.println("Room " + roomNumber + " does not exist.");
            return;
        }
        houseKeeping.rollBackStatus(roomNumber);
    }
    
    // shows the housekeeping report
    private void viewReport(){
        report.generateReport();
    }
   
}

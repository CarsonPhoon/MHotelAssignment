/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.boundary;

import java.util.Scanner;
import mhotelreservationsystem.entity.RoomCleaningStatus;
import mhotelreservationsystem.control.HousekeepingControl;
import mhotelreservationsystem.repository.RoomRepository;

/**
 *
 * @author chanzj
 */
public class HousekeepingUI implements Navigable {
    private HousekeepingControl houseKeeping;
    private Scanner scanner;

    public HousekeepingUI(RoomRepository roomRepository){
        this.houseKeeping = new HousekeepingControl(roomRepository);
        this.scanner = new Scanner(System.in);
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
            default: 
                    break;
        }
        return null;
    }

    // Stack navigation: max selectable option (0 is handled by Navigator)
    @Override
    public int getMaxChoice() {
        return 4;
    }

    private int getIntInput(){
        while (!scanner.hasNextInt()){
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            System.out.print("The option chosen: ");
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    private void updateCleaningStatus(){
        System.out.print("\nEnter room number: ");
        int roomNumber = getIntInput();
        houseKeeping.advanceCleaningStatus(roomNumber);
    }

    private void viewRoomStatus(){
        System.out.print("\nEnter room number: ");
        int roomNumber = getIntInput();
        houseKeeping.viewRoomCleaningStatus(roomNumber);
    }

    private void viewCleaningTaskLog(){
        System.out.print("\nEnter room number: ");
        int roomNumber = getIntInput();
        houseKeeping.viewTaskLog(roomNumber);
    }

    private void rollBackStatus(){
        System.out.print("\nEnter room number: ");
        int roomNumber = getIntInput();
        houseKeeping.rollBackStatus(roomNumber);
    }
   
}

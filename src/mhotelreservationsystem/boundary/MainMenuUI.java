/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.boundary;

import mhotelreservationsystem.boundary.frontdeskmodule.FrontDeskUI;
import mhotelreservationsystem.repository.*;
import mhotelreservationsystem.control.HousekeepingControl;
import mhotelreservationsystem.utility.MessageUI;
import mhotelreservationsystem.utility.Validation;

/**
 *
 * @author phoon
 */
public class MainMenuUI {

    private Navigator navigator;
    private HousekeepingUI housekeepingUI;
    private HousekeepingControl housekeepingControl;

    // Shared repository instances (single source of truth for all modules)
    private GuestRepository guestRepository;
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private MemberRepository memberRepository;
    private CommentRepository commentRepository;
    private StaffRepository staffRepository;

    public MainMenuUI() {
        this.navigator = new Navigator();
        this.guestRepository = new GuestRepository();
        this.bookingRepository = new BookingRepository();
        this.roomRepository = new RoomRepository();
        this.memberRepository = new MemberRepository();
        this.commentRepository = new CommentRepository();
        this.staffRepository = new StaffRepository();
        
        this.housekeepingControl = new HousekeepingControl(roomRepository, staffRepository);
        this.housekeepingUI = new HousekeepingUI(roomRepository, housekeepingControl);
    }
    

    public void start() {
        int mainMenuChoice;

        do {
            MessageUI.displayMainHeader();

            System.out.println();
            System.out.println("\n\t\t------------------------------------------------------");
            System.out.println("\n\t\t\t\t  Welcome to M Hotel");
            System.out.println("\t\t\t\tEnter CHOICE to continue...");
            System.out.println("\n\t\t------------------------------------------------------");
            System.out.println();
            System.out.println("1. Walk-In Registration & Booking");
            System.out.println("2. VIP Room Allocation");
            System.out.println("3. Housekeeping & Task Log");
            System.out.println("4. Front Desk Service");
            System.out.println("0. Exit");

            mainMenuChoice = Validation.getIntOrReturn("\nEnter your choice (0-4): ", 0, 4);

            switch (mainMenuChoice) {
                case 1:
                    navigator.navigateTo(new WalkInUI(guestRepository, bookingRepository,
                                                      roomRepository, memberRepository, commentRepository, housekeepingControl));
                    navigator.run();
                    break;
                case 2:
                    navigator.navigateTo(new VIPRoomUI(memberRepository, guestRepository, roomRepository, bookingRepository));
                    navigator.run();
                    break;
                case 3:
                    navigator.navigateTo(housekeepingUI);
                    navigator.run();
                    break;
                case 4:
                    navigator.navigateTo(new FrontDeskUI(guestRepository, bookingRepository,
                                                         roomRepository, memberRepository, commentRepository));
                    navigator.run();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("\nInvalid choice, pls enter again (0 - 4 only)");
                    Validation.pressEnterToContinue();
            }
        } while (mainMenuChoice != 0);

        System.out.println("\n\t\t\033[33mExiting the M Hotel System ...\033[0m");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\t\t\033[33mThank you, have a nice day ^-^\033[0m");
    }
}
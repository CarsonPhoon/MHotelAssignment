/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.report;


import mhotelreservationsystem.entity.Room;
import mhotelreservationsystem.entity.RoomStatus;
import mhotelreservationsystem.entity.GuestStatus;
import mhotelreservationsystem.repository.*;
/**
 *
 * @author phoon
 */
public class FrontDeskReport {
    
    private GuestRepository guestRepository;
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private MemberRepository memberRepository;
    
    public FrontDeskReport(){
        guestRepository = new GuestRepository();
        bookingRepository = new BookingRepository();
        roomRepository = new RoomRepository();
        memberRepository = new MemberRepository();
    }
    
    public void generateRoomOccupancyReport(){

        int availableCount = 0;
        int occupiedCount = 0;
        int maintenanceCount = 0;

        System.out.println();
        System.out.println("========================================================");
        System.out.println("              ROOM OCCUPANCY REPORT");
        System.out.println("========================================================");

        // Available Room
        System.out.println();
        System.out.println("AVAILABLE ROOM");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i < roomRepository.getTotalRoom(); i++){
            Room room = roomRepository.getRoom(i);
            if(room.getStatus() == RoomStatus.AVAILABLE){
                System.out.println(room);
                availableCount++;
            }
        }

        // Occupied Room
        System.out.println();
        System.out.println("OCCUPIED ROOM");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i < roomRepository.getTotalRoom(); i++){
            Room room = roomRepository.getRoom(i);
            if(room.getStatus() == RoomStatus.OCCUPIED){
                System.out.println(room);
                occupiedCount++;
            }
        }

        // Maintenance Room
        System.out.println();
        System.out.println("MAINTENANCE ROOM");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i < roomRepository.getTotalRoom(); i++){
            Room room = roomRepository.getRoom(i);
            if(room.getStatus() == RoomStatus.MAINTENANCE){
                System.out.println(room);
                maintenanceCount++;
            }
        }

        System.out.println();
        System.out.println("========================================================");
        System.out.println("Available   : " + availableCount);
        System.out.println("Occupied    : " + occupiedCount);
        System.out.println("Maintenance : " + maintenanceCount);
        System.out.println("Total Room  : " + roomRepository.getTotalRoom());
        System.out.println("========================================================");
    }
    
    public void generateGuestCheckInOutReport(){

        System.out.println();
        System.out.println("========================================================");
        System.out.println("      GUEST CHECK-IN / CHECK-OUT / RESERVED REPORT");
        System.out.println("========================================================");

        System.out.println();
        System.out.println("CHECKED IN GUESTS");
        System.out.println("--------------------------------------------------------");

        int checkedIn =
                guestRepository.displayGuestByStatus(
                        GuestStatus.CHECKED_IN);

        System.out.println();
        System.out.println("Total Checked In : " + checkedIn);

        System.out.println();
        System.out.println("CHECKED OUT GUESTS");
        System.out.println("--------------------------------------------------------");

        int checkedOut =
                guestRepository.displayGuestByStatus(
                        GuestStatus.CHECKED_OUT);

        System.out.println();
        System.out.println("Total Checked Out : " + checkedOut);

        System.out.println();
        System.out.println("RESERVED GUESTS");
        System.out.println("--------------------------------------------------------");

        int reserved =
                guestRepository.displayGuestByStatus(
                        GuestStatus.RESERVED);

        System.out.println();
        System.out.println("Total Reserved : " + reserved);

        System.out.println();
        System.out.println("========================================================");
        System.out.println("Total Guest : "
                + guestRepository.getTotalGuest());
        System.out.println("========================================================");
    }
    
}

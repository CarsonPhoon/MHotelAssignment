/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.report;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import mhotelreservationsystem.entity.*;
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
    
    public FrontDeskReport(GuestRepository guestRepository, BookingRepository bookingRepository, RoomRepository roomRepository, MemberRepository memberRepository){
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
    }
    

    //  Quick Sort - Sort Room array by roomRate in descending order
    private void quickSortRoomsByRate(Room[] arr, int low, int high){
        if(low < high){
            int pivotIndex = partitionRoomsByRate(arr, low, high);
            quickSortRoomsByRate(arr, low, pivotIndex - 1);
            quickSortRoomsByRate(arr, pivotIndex + 1, high);
        }
    }

    private int partitionRoomsByRate(Room[] arr, int low, int high){
        Room pivot = arr[high];
        int i = low - 1;

        for(int j = low; j < high; j++){
            if(arr[j].getRoomRate() >= pivot.getRoomRate()){
                i++;
                Room temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Room temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }
    
    //  Quick Sort - Sort Guest array by guestName in ascending order
    private void quickSortGuestsByName(Guest[] arr, int low, int high){
        if(low < high){
            int pivotIndex = partitionGuestsByName(arr, low, high);
            quickSortGuestsByName(arr, low, pivotIndex - 1);
            quickSortGuestsByName(arr, pivotIndex + 1, high);
        }
    }

    private int partitionGuestsByName(Guest[] arr, int low, int high){
        Guest pivot = arr[high];
        int i = low - 1;

        for(int j = low; j < high; j++){
            if(arr[j].getGuestName().compareTo(pivot.getGuestName()) <= 0){
                i++;
                Guest temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        Guest temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }
    
    //  Report 1 - Daily Room Occupancy Report
    public void generateRoomOccupancyReport(){
        
        int totalRoom = roomRepository.getTotalRoom();
        
        // Copy all rooms into a temporary array for sorting
        Room[] allRooms = new Room[totalRoom];
        for(int i = 0; i < totalRoom; i++){
            allRooms[i] = roomRepository.getRoom(i);
        }
        
        // Sort all rooms by rate descending before filtering
        quickSortRoomsByRate(allRooms, 0, totalRoom - 1);
        
        int availableCount = 0;
        int occupiedCount = 0;
        int maintenanceCount = 0;

        System.out.println();
        System.out.println("========================================================");
        System.out.println("              DAILY ROOM OCCUPANCY REPORT");
        System.out.println("  Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("========================================================");

        // Available Room (filtered + sorted)
        System.out.println();
        System.out.println("AVAILABLE ROOM");
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-8s %-10s %-6s %-8s %-10s %-15s%n",
                "Room", "Type", "Floor", "Capacity", "Rate", "Status");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i < totalRoom; i++){
            if(allRooms[i].getStatus() == RoomStatus.AVAILABLE){
                System.out.println(allRooms[i]);
                availableCount++;
            }
        }

        // Occupied Room (filtered + sorted)
        System.out.println();
        System.out.println("OCCUPIED ROOM");
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-8s %-10s %-6s %-8s %-10s %-15s%n",
                "Room", "Type", "Floor", "Capacity", "Rate", "Status");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i < totalRoom; i++){
            if(allRooms[i].getStatus() == RoomStatus.OCCUPIED){
                System.out.println(allRooms[i]);
                occupiedCount++;
            }
        }

        // Maintenance Room (filtered + sorted)
        System.out.println();
        System.out.println("MAINTENANCE ROOM");
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-8s %-10s %-6s %-8s %-10s %-15s%n",
                "Room", "Type", "Floor", "Capacity", "Rate", "Status");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i < totalRoom; i++){
            if(allRooms[i].getStatus() == RoomStatus.MAINTENANCE){
                System.out.println(allRooms[i]);
                maintenanceCount++;
            }
        }

        System.out.println();
        System.out.println("========================================================");
        System.out.println("Available   : " + availableCount);
        System.out.println("Occupied    : " + occupiedCount);
        System.out.println("Maintenance : " + maintenanceCount);
        System.out.println("Total Room  : " + totalRoom);
        System.out.println("========================================================");
    }
    
    
    //  Report 2 - Daily Guest Check-In / Check-Out Report
    public void generateGuestCheckInOutReport(){
        
        // Collect all guests from BST into array (inorder traversal)
        Guest[] allGuests = guestRepository.getAllGuests();
        int totalGuest = allGuests.length;
        
        // Sort all guests by name ascending 
        quickSortGuestsByName(allGuests, 0, totalGuest - 1);
        
        int checkedInCount = 0;
        int checkedOutCount = 0;
        int reservedCount = 0;

        System.out.println();
        System.out.println("========================================================");
        System.out.println("      DAILY GUEST CHECK-IN / CHECK-OUT / RESERVED REPORT");
        System.out.println("  Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("========================================================");

        // Checked-In Guests (filtered + sorted)
        System.out.println();
        System.out.println("CHECKED IN GUESTS");
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-12s %-20s %-6s %-12s%n",
                "Confirm No", "Guest Name", "Room", "Status");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i < totalGuest; i++){
            if(allGuests[i].getStatus() == GuestStatus.CHECKED_IN){
                System.out.println(allGuests[i]);
                checkedInCount++;
            }
        }

        System.out.println();
        System.out.println("Total Checked In : " + checkedInCount);

        // Checked-Out Guests (filtered + sorted)
        System.out.println();
        System.out.println("CHECKED OUT GUESTS");
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-12s %-20s %-6s %-12s%n",
                "Confirm No", "Guest Name", "Room", "Status");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i < totalGuest; i++){
            if(allGuests[i].getStatus() == GuestStatus.CHECKED_OUT){
                System.out.println(allGuests[i]);
                checkedOutCount++;
            }
        }

        System.out.println();
        System.out.println("Total Checked Out : " + checkedOutCount);

        // Reserved Guests (filtered + sorted)
        System.out.println();
        System.out.println("RESERVED GUESTS");
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-12s %-20s %-6s %-12s%n",
                "Confirm No", "Guest Name", "Room", "Status");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i < totalGuest; i++){
            if(allGuests[i].getStatus() == GuestStatus.RESERVED){
                System.out.println(allGuests[i]);
                reservedCount++;
            }
        }

        System.out.println();
        System.out.println("Total Reserved : " + reservedCount);

        System.out.println();
        System.out.println("========================================================");
        System.out.println("Total Guest : " + totalGuest);
        System.out.println("========================================================");
    }
}
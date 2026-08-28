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

    private static final String Y = "\033[33m";
    private static final String R = "\033[0m";
    
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
            int pivotIndex = partitionRoomsByRate(arr, low, high); // Partition
            quickSortRoomsByRate(arr, low, pivotIndex - 1); // Recursively sort the left half
            quickSortRoomsByRate(arr, pivotIndex + 1, high); // Recursively sort the right half
        }
    }

    // Partitioning method
    private int partitionRoomsByRate(Room[] arr, int low, int high){
        Room pivot = arr[high]; // Select the last element as the pivot
        int i = low - 1; // i points to the region smaller than the pivot

        for(int j = low; j < high; j++){
            // If the current element is greater than or equal to the pivot, swap it to the left side
            if(arr[j].getRoomRate() >= pivot.getRoomRate()){
                i++;
                Room temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Position the datum correctly
        Room temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        // Return to the reference position
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

    // Partitioning method
    private int partitionGuestsByName(Guest[] arr, int low, int high){
        Guest pivot = arr[high];
        int i = low - 1;

        for(int j = low; j < high; j++){
            // If the current name is less than or equal to the pivot name, swap it to the left side
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
        
        // Sort all rooms by rate/price descending before filtering
        quickSortRoomsByRate(allRooms, 0, totalRoom - 1);
        
        // Initialize counter
        int availableCount = 0;
        int occupiedCount = 0;
        int reservedCount = 0;
        int cleaningCount = 0;
        int maintenanceCount = 0;

        System.out.println();
        System.out.println("========================================================");
        System.out.println("              "+Y+"DAILY ROOM OCCUPANCY REPORT"+R);
        System.out.println("                    Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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

        // Reserved Room (filtered + sorted)
        System.out.println();
        System.out.println("RESERVED ROOM");
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-8s %-10s %-6s %-8s %-10s %-15s%n",
                "Room", "Type", "Floor", "Capacity", "Rate", "Status");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i < totalRoom; i++){
            if(allRooms[i].getStatus() == RoomStatus.RESERVED){
                System.out.println(allRooms[i]);
                reservedCount++;
            }
        }

        // Cleaning Room (filtered + sorted)
        System.out.println();
        System.out.println("CLEANING ROOM");
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-8s %-10s %-6s %-8s %-10s %-15s%n",
                "Room", "Type", "Floor", "Capacity", "Rate", "Status");
        System.out.println("--------------------------------------------------------");

        for(int i = 0; i < totalRoom; i++){
            if(allRooms[i].getStatus() == RoomStatus.CLEANING){
                System.out.println(allRooms[i]);
                cleaningCount++;
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
        System.out.println("Reserved    : " + reservedCount);
        System.out.println("Cleaning    : " + cleaningCount);
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
        
         // Initialize counter
        int checkedInCount = 0;
        int checkedOutCount = 0;
        int reservedCount = 0;

        System.out.println();
        System.out.println("========================================================");
        System.out.println("      "+Y+"DAILY GUEST CHECK-IN / CHECK-OUT / RESERVED REPORT"+R);
        System.out.println("                   Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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
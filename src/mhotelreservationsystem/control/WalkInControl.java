/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.control;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.entity.*;
import mhotelreservationsystem.repository.*;

/**
 * Walk-In control: manage registration, queue of pending bookings, and reports.
 */
public class WalkInControl {

    private GuestRepository guestRepository;
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private MemberRepository memberRepository;
    
    // Queue for pending walk-in bookings (using ArrayListADT as queue)
    private ArrayListADT<Booking> pendingBookings;

    public WalkInControl(){
        guestRepository = new GuestRepository();
        bookingRepository = new BookingRepository();
        roomRepository = new RoomRepository();
        memberRepository = new MemberRepository();
        pendingBookings = new ArrayListADT<>();
        syncPendingQueueFromRepository();
    }

    private void syncPendingQueueFromRepository(){
        pendingBookings.clear();
        Booking[] allBookings = bookingRepository.getAllBookings();
        for (Booking booking : allBookings) {
            if (booking != null && booking.getBookingStatus() == BookingStatus.PENDING) {
                pendingBookings.add(booking);
            }
        }
    }

    // Show available rooms
    public void displayAvailableRooms(){
        System.out.println();
        System.out.println("Available Rooms");
        System.out.println("--------------------------------");
        for(int i = 0; i < roomRepository.getTotalRoom(); i++){
            Room r = roomRepository.getRoom(i);
            if(r.getStatus() == RoomStatus.AVAILABLE){
                System.out.println(r);
            }
        }
    }

    // Generate next booking id based on existing BKxxxx pattern
    private String generateNextBookingID(){
        int max = 0;
        for(int i = 0; i < bookingRepository.getTotalBooking(); i++){
            String id = bookingRepository.getBooking(i).getBookingID();
            if(id != null && id.length() > 2 && id.startsWith("BK")){
                try{
                    int num = Integer.parseInt(id.substring(2));
                    if(num > max) max = num;
                }catch(NumberFormatException e){
                    // ignore malformed ids
                }
            }
        }
        return String.format("BK%04d", max + 1);
    }

    // Generate next confirmation number based on Guest confirmation numbers
    private String generateNextConfirmationNumber(){
        Guest[] guests = guestRepository.getAllGuests();
        long max = 10000000L; // base if none
        for(Guest g : guests){
            if(g == null) continue;
            try{
                long n = Long.parseLong(g.getConfirmationNumber());
                if(n > max) max = n;
            }catch(NumberFormatException e){
                // ignore
            }
        }
        return Long.toString(max + 1);
    }

    // Register a walk-in: save to repository as pending and reserve the room.
    public Booking registerWalkInPending(String guestName, String phone, String email, int roomNumber, int numberOfGuests, LocalDate checkIn, LocalDate checkOut){

        Room room = roomRepository.searchRoom(roomNumber);

        if(room == null){
            System.out.println("Room not found.");
            return null;
        }

        if(room.getStatus() != RoomStatus.AVAILABLE){
            System.out.println("Room is not available.");
            return null;
        }

        String bookingID = generateNextBookingID();
        String confirmation = generateNextConfirmationNumber();

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        if(nights <= 0){
            System.out.println("Invalid check-in/check-out dates.");
            return null;
        }

        double total = room.getRoomRate() * nights;

        Booking booking = new Booking(
                bookingID,
                confirmation,
                roomNumber,
                room.getRoomType(),
                numberOfGuests,
                LocalDate.now(),
                checkIn,
                checkOut,
                total,
                BookingStatus.PENDING
        );

        Guest guest = new Guest(
                confirmation,
                guestName,
                phone,
                email,
                bookingID,
                roomNumber,
                checkIn,
                checkOut,
                GuestStatus.RESERVED
        );

        boolean okBooking = bookingRepository.addBooking(booking);
        boolean okGuest = guestRepository.addGuest(guest);

        if (!okBooking || !okGuest) {
            System.out.println("Failed to save walk-in booking to data files.");
            return null;
        }

        pendingBookings.add(booking);

        room.setStatus(RoomStatus.RESERVED);
        roomRepository.updateRoom(room);

        System.out.println("Walk-in added to pending queue and saved to data files.");
        System.out.println("Booking ID: " + bookingID);
        System.out.println("Confirmation No: " + confirmation);
        System.out.println("Status: PENDING (awaiting confirmation)");
        return booking;
    }

    // Confirm pending booking: update repository statuses and room to occupied.
    public boolean confirmPendingBooking(int queueIndex){
        syncPendingQueueFromRepository();

        if(queueIndex < 0 || queueIndex >= pendingBookings.getNumberOfEntries()){
            System.out.println("Invalid booking index.");
            return false;
        }

        Booking booking = pendingBookings.get(queueIndex);

        Room room = roomRepository.searchRoom(booking.getRoomNumber());
        if(room == null){
            System.out.println("Room not found in system.");
            return false;
        }

        Guest guest = guestRepository.searchGuest(booking.getConfirmationNumber());
        if(guest == null){
            System.out.println("Guest record not found for booking.");
            return false;
        }

        booking.setBookingStatus(BookingStatus.CHECKED_IN);
        boolean ok1 = bookingRepository.updateBooking(booking);

        guest.setStatus(GuestStatus.CHECKED_IN);
        boolean ok2 = guestRepository.updateGuest(guest);

        room.setStatus(RoomStatus.OCCUPIED);
        boolean ok3 = roomRepository.updateRoom(room);

        if(ok1 && ok2 && ok3){
            pendingBookings.remove(queueIndex);
            System.out.println("Booking confirmed and saved.");
            return true;
        }

        System.out.println("Failed to confirm booking.");
        return false;
    }

    // View pending bookings in queue
    public void displayPendingBookings(){
        syncPendingQueueFromRepository();

        int total = pendingBookings.getNumberOfEntries();
        
        if(total == 0){
            System.out.println("No pending bookings.");
            return;
        }

        System.out.println();
        System.out.println("==============================================================");
        System.out.printf("%-3s %-8s %-10s %-5s %-6s %-10s %s%n",
                "No", "Book ID", "Confirm", "Room", "Guest", "Check-In", "Status");
        System.out.println("==============================================================");

        for(int i = 0; i < total; i++){
            Booking b = pendingBookings.get(i);
            System.out.printf("%-3d %-8s %-10s %-5d %-6d %-10s PENDING%n",
                    i + 1, 
                    b.getBookingID(), 
                    b.getConfirmationNumber(), 
                    b.getRoomNumber(), 
                    b.getNumberOfGuests(),
                    b.getCheckInDate());
        }

        System.out.println("==============================================================");
        System.out.println("Total Pending: " + total);
    }

    // Accessor methods for reports
    public GuestRepository getGuestRepository(){
        return guestRepository;
    }

    public BookingRepository getBookingRepository(){
        return bookingRepository;
    }

    public RoomRepository getRoomRepository(){
        return roomRepository;
    }
    
    public ArrayListADT<Booking> getPendingBookings(){
        return pendingBookings;
    }
    
    // Validation methods for UI
    public boolean isRoomAvailable(int roomNumber){
        Room room = roomRepository.searchRoom(roomNumber);
        if(room == null) return false;
        return room.getStatus() == RoomStatus.AVAILABLE;
    }
    
    public int getRoomCapacity(int roomNumber){
        Room room = roomRepository.searchRoom(roomNumber);
        if(room == null) return 0;
        return room.getCapacity();
    }

}

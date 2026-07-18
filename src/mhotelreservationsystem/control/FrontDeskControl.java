/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.control;


import mhotelreservationsystem.entity.*;
import mhotelreservationsystem.repository.*; 
/**
 *
 * @author phoon
 */
public class FrontDeskControl {
    
    private GuestRepository guestRepository;
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private MemberRepository memberRepository;
    
    public FrontDeskControl(){
        guestRepository = new GuestRepository(); 
        bookingRepository = new BookingRepository(); 
        roomRepository = new RoomRepository(); 
        memberRepository = new MemberRepository(); 
    }
    
    // Search Methods (Return Object)
    public Guest searchGuest(String confirmationNumber){
        return guestRepository.searchGuest(confirmationNumber);
    }
    
    public Booking searchBooking(String bookingID){
        return bookingRepository.searchBooking(bookingID);
    }
    
    public Room searchRoom(int roomNumber){
        return roomRepository.searchRoom(roomNumber);
    }
    
    public Member searchMember(String confirmationNumber){
        return memberRepository.searchByConfirmation(confirmationNumber);
    }
    
    // Helper method
    private boolean notFound(Object object, String message){

        if(object == null){

            System.out.println(message);
            return true;
        }
        return false;
    }
    
    // Room Availability
    public boolean checkRoomAvailability (int roomNumber){
           Room room = searchRoom(roomNumber);

           if(room == null){
               return false;
           }

           return room.getStatus() == RoomStatus.AVAILABLE;
       }

    public void displayRoomAvailability(int roomNumber){

        Room room = searchRoom(roomNumber);

        if(room == null){
            System.out.println("Room not found.");
            return;
        }

        System.out.println();
        System.out.println("========== Room Availability ==========");
        System.out.println("Room Number : " + room.getRoomNumber());
        System.out.println("Room Type   : " + room.getRoomType());

        if(room.getStatus() == RoomStatus.AVAILABLE){
            System.out.println("Availability: Available");
        }else{
            System.out.println("Availability: Not Available");
        }
    }
       
    public void viewGuestProfile(String confirmationNumber){

        Guest guest = searchGuest(confirmationNumber);

        if(notFound(guest, "Guest not found.")){
            return;
        }

        System.out.println();
        System.out.println("========== Guest Information ==========");
        System.out.println("Confirmation No : " + guest.getConfirmationNumber());
        System.out.println("Guest Name      : " + guest.getGuestName());
        System.out.println("Phone Number    : " + guest.getPhoneNumber());
        System.out.println("Email           : " + guest.getEmail());
        System.out.println("Booking ID      : " + guest.getBookingID());
        System.out.println("Room Number     : " + guest.getRoomNumber());
        System.out.println("Check In Date   : " + guest.getCheckInDate());
        System.out.println("Check Out Date  : " + guest.getCheckOutDate());
        System.out.println("Status          : " + guest.getStatus());
    }
    
    public void viewBookingDetails(String bookingID){

        Booking booking = searchBooking(bookingID);

        if(notFound(booking, "Booking not found.")){
            return;
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
        System.out.println("Total Amount    : RM " + booking.getTotalAmount());
        System.out.println("Status          : " + booking.getBookingStatus());
    }

    public void viewRoomDetails(int roomNumber){

        Room room = searchRoom(roomNumber);

        if(room == null){
            System.out.println("Room not found");
            return;
        }

        System.out.println();
        System.out.println("========== Room Information ==========");
        System.out.println("Room Number : " + room.getRoomNumber());
        System.out.println("Room Type   : " + room.getRoomType());
        System.out.println("Floor       : " + room.getFloor());
        System.out.println("Capacity    : " + room.getCapacity());
        System.out.println("Room Rate   : RM " + String.format("%.2f", room.getRoomRate()));
        System.out.println("Status      : " + room.getStatus());
    }

    public void viewMemberDetails(String confirmationNumber){

        Member member = searchMember(confirmationNumber);

        if(notFound(member, "Member not found.")){
            return;
        }

        System.out.println();
        System.out.println("========== Member Information ==========");
        System.out.println("Member ID        : " + member.getMemberID());
        System.out.println("Confirmation No  : " + member.getConfirmationNumber());
        System.out.println("Member Level     : " + member.getMemberLevel());
        System.out.println("Reward Points    : " + member.getRewardPoints());
        System.out.println("Join Date        : " + member.getJoinDate());
        System.out.println("Status           : " + member.getMembershipStatus());
    }
    
    public void viewBilling(String bookingID){
        Booking booking = searchBooking(bookingID);
        
        System.out.println();
        System.out.println("========== Billing ==========");
        System.out.println("Booking ID      : " + booking.getBookingID());
        System.out.println("Confirmation No : " + booking.getConfirmationNumber());
        System.out.println("Room Number     : " + booking.getRoomNumber());
        System.out.println("Room Type       : " + booking.getRoomType());
        System.out.println("Booking Date    : " + booking.getBookingDate());
        System.out.println("Check In        : " + booking.getCheckInDate());
        System.out.println("Check Out       : " + booking.getCheckOutDate());
        System.out.println("Total Amount    : RM " + booking.getTotalAmount());
        System.out.println("Status          : " + booking.getBookingStatus());
    }
    
    public void viewCompleteGuestInformation(String confirmationNumber){

        Guest guest = searchGuest(confirmationNumber);

        Booking booking = searchBooking(guest.getBookingID());
        Room room = searchRoom(guest.getRoomNumber());
        Member member = searchMember(confirmationNumber);

        System.out.println();
        System.out.println("======================================================");
        System.out.println("            COMPLETE GUEST INFORMATION");
        System.out.println("======================================================");

        System.out.println();
        System.out.println("[Guest Information]");
        System.out.println("--------------------------------------");
        System.out.println("Confirmation No : " + guest.getConfirmationNumber());
        System.out.println("Guest Name      : " + guest.getGuestName());
        System.out.println("Phone Number    : " + guest.getPhoneNumber());
        System.out.println("Email           : " + guest.getEmail());
        System.out.println("Status          : " + guest.getStatus());

        if(booking != null){
            System.out.println();
            System.out.println("[Booking Information]");
            System.out.println("--------------------------------------");
            System.out.println("Booking ID      : " + booking.getBookingID());
            System.out.println("Room Number     : " + booking.getRoomNumber());
            System.out.println("Room Type       : " + booking.getRoomType());
            System.out.println("Booking Date    : " + booking.getBookingDate());
            System.out.println("Check In Date   : " + booking.getCheckInDate());
            System.out.println("Check Out Date  : " + booking.getCheckOutDate());
            System.out.println("Guests          : " + booking.getNumberOfGuests());
            System.out.println("Amount          : RM " + booking.getTotalAmount());
        }

        if(room != null){
            System.out.println();
            System.out.println("[Room Information]");
            System.out.println("--------------------------------------");
            System.out.println("Room Number     : " + room.getRoomNumber());
            System.out.println("Room Type       : " + room.getRoomType());
            System.out.println("Floor           : " + room.getFloor());
            System.out.println("Capacity        : " + room.getCapacity());
            System.out.println("Room Rate       : RM " + room.getRoomRate());
            System.out.println("Room Status     : " + room.getStatus());
        }
        
        if(member != null){
            System.out.println();
            System.out.println("[Member Information]");
            System.out.println("--------------------------------------");
            System.out.println("Member ID        : " + member.getMemberID());
            System.out.println("Member Level     : " + member.getMemberLevel());
            System.out.println("Reward Points    : " + member.getRewardPoints());
            System.out.println("Join Date        : " + member.getJoinDate());
            System.out.println("Status           : " + member.getMembershipStatus());

        }
        System.out.println("======================================================");
    }
   
    // Filter & Search List
    public void searchRoomByStatus(RoomStatus status){

        System.out.println();
        System.out.println("========== Room List ==========");

        for(int i = 0; i < roomRepository.getTotalRoom(); i++){

            Room room = roomRepository.getRoom(i);

            if(room.getStatus() == status){
                System.out.println(room);
            }
        }
    }
    
    public void searchBookingByStatus(BookingStatus status){

        System.out.println();
        System.out.println("========== Booking List ==========");
        
        for(int i = 0; i < bookingRepository.getTotalBooking(); i++){

            Booking booking = bookingRepository.getBooking(i);

            if(booking.getBookingStatus() == status){
                System.out.println(booking);
            }
        }
    }
    
    public void searchMemberByLevel(MemberLevel level){

        System.out.println();
        System.out.println("========== Member List ==========");

        for(int i = 0; i < memberRepository.getTotalMember(); i++){

            Member member = memberRepository.getMember(i);

            if(member.getMemberLevel() == level){
                System.out.println(member);
            }
        }
    }
}
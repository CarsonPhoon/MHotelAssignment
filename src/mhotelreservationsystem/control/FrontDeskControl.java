/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.control;

import java.time.LocalDate;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.entity.*;
import mhotelreservationsystem.repository.*; 
/**
 *
 * @author phoon
 */
public class FrontDeskControl {
    // Declare 5 repository variables for accessing different types of txt databases
    private GuestRepository guestRepository;
    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;
    private MemberRepository memberRepository;
    private CommentRepository commentRepository;

    // Initialize all repositories (passed in from an external source, not created internally)
    public FrontDeskControl(GuestRepository guestRepository, BookingRepository bookingRepository, RoomRepository roomRepository, MemberRepository memberRepository, CommentRepository commentRepository){
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
        this.commentRepository = commentRepository;
    }

    // Accessors for repositories, it used by FrontDeskReport to avoid duplicate instances
    public GuestRepository getGuestRepository(){
        return guestRepository;
    }

    public BookingRepository getBookingRepository(){
        return bookingRepository;
    }

    public RoomRepository getRoomRepository(){
        return roomRepository;
    }

    public MemberRepository getMemberRepository(){
        return memberRepository;
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

    public Member searchMemberByID(String memberID){
        return memberRepository.searchByMemberID(memberID);
    }

    // View Methods - Return objects, no System.out ((Retrieve information for a single entity)
    public Guest getGuestProfile(String confirmationNumber){
        return searchGuest(confirmationNumber);
    }

    public Booking getBookingDetails(String bookingID){
        return searchBooking(bookingID);
    }

    public Room getRoomDetails(int roomNumber){
        return searchRoom(roomNumber);
    }

    public Member getMemberDetails(String memberID){
        return searchMemberByID(memberID);
    }

    public String getCompleteGuestInformation(String confirmationNumber){

        Guest guest = searchGuest(confirmationNumber);
        if(guest == null) return null;

        Booking booking = searchBooking(guest.getBookingID());
        Room room = searchRoom(guest.getRoomNumber());
        Member member = searchMember(confirmationNumber);

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("======================================================\n");
        sb.append("            COMPLETE GUEST INFORMATION\n");
        sb.append("======================================================\n");

        sb.append("\n");
        sb.append("[Guest Information]\n");
        sb.append("--------------------------------------\n");
        sb.append("Confirmation No : ").append(guest.getConfirmationNumber()).append("\n");
        sb.append("Guest Name      : ").append(guest.getGuestName()).append("\n");
        sb.append("Phone Number    : ").append(guest.getPhoneNumber()).append("\n");
        sb.append("Email           : ").append(guest.getEmail()).append("\n");
        sb.append("Status          : ").append(guest.getStatus()).append("\n");

        if(booking != null){
            sb.append("\n");
            sb.append("[Booking Information]\n");
            sb.append("--------------------------------------\n");
            sb.append("Booking ID      : ").append(booking.getBookingID()).append("\n");
            sb.append("Room Number     : ").append(booking.getRoomNumber()).append("\n");
            sb.append("Room Type       : ").append(booking.getRoomType()).append("\n");
            sb.append("Booking Date    : ").append(booking.getBookingDate()).append("\n");
            sb.append("Check In Date   : ").append(booking.getCheckInDate()).append("\n");
            sb.append("Check Out Date  : ").append(booking.getCheckOutDate()).append("\n");
            sb.append("Guests          : ").append(booking.getNumberOfGuests()).append("\n");
            sb.append("Amount          : RM ").append(String.format("%.2f", booking.getTotalAmount())).append("\n");
        }

        if(room != null){
            sb.append("\n");
            sb.append("[Room Information]\n");
            sb.append("--------------------------------------\n");
            sb.append("Room Number     : ").append(room.getRoomNumber()).append("\n");
            sb.append("Room Type       : ").append(room.getRoomType()).append("\n");
            sb.append("Floor           : ").append(room.getFloor()).append("\n");
            sb.append("Capacity        : ").append(room.getCapacity()).append("\n");
            sb.append("Room Rate       : RM ").append(String.format("%.2f", room.getRoomRate())).append("\n");
            sb.append("Room Status     : ").append(room.getStatus()).append("\n");
        }

        if(member != null){
            sb.append("\n");
            sb.append("[Member Information]\n");
            sb.append("--------------------------------------\n");
            sb.append("Member ID        : ").append(member.getMemberID()).append("\n");
            sb.append("Member Level     : ").append(member.getMemberLevel()).append("\n");
            sb.append("Reward Points    : ").append(member.getRewardPoints()).append("\n");
            sb.append("Join Date        : ").append(member.getJoinDate()).append("\n");
            sb.append("Status           : ").append(member.getMembershipStatus()).append("\n");
        }

        sb.append("======================================================\n");
        return sb.toString();
    }

    // Comment / Complaint Methods - Return data, no System.out
    // Get all comments
    public ArrayListADT<Comment> getAllComments(){
        ArrayListADT<Comment> allComments = new ArrayListADT<Comment>();
        for(int i = 0; i < commentRepository.getTotalComment(); i++){
            allComments.add(commentRepository.getComment(i));
        }
        return allComments;
    }

    // Search comments by date
    public ArrayListADT<Comment> searchCommentsByDate(LocalDate date){
        ArrayListADT<Comment> results = new ArrayListADT<Comment>();
        for(int i = 0; i < commentRepository.getTotalComment(); i++){
            Comment c = commentRepository.getComment(i);
            if(c.getDate().equals(date)){
                results.add(c);
            }
        }
        return results;
    }
    
    // Search comment by guest
    public ArrayListADT<Comment> searchCommentsByConfirmation(String confirmationNumber){
        return commentRepository.searchByConfirmation(confirmationNumber);
    }
}
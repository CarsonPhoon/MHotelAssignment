/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.report;


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
}

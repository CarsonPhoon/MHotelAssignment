/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.control;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import mhotelreservationsystem.adt.LinkedStack;
import mhotelreservationsystem.adt.VipBST;
import mhotelreservationsystem.entity.*;
import mhotelreservationsystem.repository.BookingRepository;
import mhotelreservationsystem.repository.GuestRepository;
import mhotelreservationsystem.repository.MemberRepository;
import mhotelreservationsystem.repository.RoomRepository;

/**
 * Control class for managing VIP Room operations and queue.
 * @author zekai
 */
public class VIPRoomControl {
    
    private VipBST vipQueue; 
    private LinkedStack<Member> assignedHistoryStack;
    private MemberRepository memberRepository;
    private GuestRepository guestRepository;
    private RoomRepository roomRepository;
    private BookingRepository bookingRepository;

    public VIPRoomControl(MemberRepository memberRepo, GuestRepository guestRepo, RoomRepository roomRepo, BookingRepository bookingRepo) {
        this.memberRepository = memberRepo;
        this.guestRepository = guestRepo;
        this.roomRepository = roomRepo;
        this.bookingRepository = bookingRepo;
        
        this.vipQueue = new VipBST();
        this.assignedHistoryStack = new LinkedStack<>();
        
        loadVipsFromRepository(); 
    }

    private void loadVipsFromRepository() {
        for (int i = 0; i < memberRepository.getTotalMember(); i++) {
            Member member = memberRepository.getMember(i);
            if (member.getMembershipStatus() == MembershipStatus.ACTIVE) {
                vipQueue.enqueue(member);
            }
        }
        System.out.println("[System] Successfully loaded VIP data from MemberRepository!");
    }

    public boolean addVipToQueue(Member vipMember) {
        if (vipMember == null) return false;
        
        vipQueue.enqueue(vipMember);
        memberRepository.addMember(vipMember);
        return true;
    }

    public boolean verifyGuestExists(String confirmNum) {
        return guestRepository.searchGuest(confirmNum) != null;
    }

    public String generateNextMemberId() {
        int maxId = 0;
        for (int i = 0; i < memberRepository.getTotalMember(); i++) {
            Member m = memberRepository.getMember(i);
            String id = m.getMemberID();
            if (id != null && id.startsWith("MB")) {
                try {
                    int currentId = Integer.parseInt(id.substring(2));
                    if (currentId > maxId) {
                        maxId = currentId;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("MB%04d", maxId + 1);
    }

    public boolean isVipAlreadyRegistered(String confirmNum) {
        return memberRepository.searchByConfirmation(confirmNum) != null;
    }


    public String updateRoomStatus(String roomNumber, mhotelreservationsystem.entity.RoomStatus newStatus) {
        return roomRepository.updateStatus(roomNumber, newStatus);
    }

    public void updateVipPointsInFile(String memberID, int newPoints) {
        memberRepository.updatePoints(memberID, newPoints);
    }

    public Member assignRoomToNextVip() {
        Member assignedVip = vipQueue.dequeue();
        
        if (assignedVip != null) {
            assignedHistoryStack.push(assignedVip);
        }
        
        return assignedVip;
    }

    public void displayAssignedHistory() {
        System.out.println("\n=====================================");
        System.out.println("   Recent VIP Room Allocations ");
        System.out.println("=====================================");
        
        if (assignedHistoryStack.isEmpty()) {
            System.out.println("No rooms have been assigned yet today.");
            return;
        }
        
        LinkedStack<Member> tempStack = new LinkedStack<>();
        int count = 1;
        
        while (!assignedHistoryStack.isEmpty()) {
            Member m = assignedHistoryStack.pop();
            System.out.println((count++) + ". " + m.toString());
            tempStack.push(m);
        }

        while (!tempStack.isEmpty()) {
            assignedHistoryStack.push(tempStack.pop());
        }
    }

    public String redeemPoints(String confirmNum, int pointsToDeduct) {
        mhotelreservationsystem.entity.Member vip = this.searchVipInDatabase(confirmNum); 
        
        if (vip == null) {
            return "NOT_FOUND";
        }
        
        if (vip.getMembershipStatus() == mhotelreservationsystem.entity.MembershipStatus.INACTIVE) {
            return "NOT_FOUND"; 
        }
        
        if (vip.getRewardPoints() < pointsToDeduct) {
            return "INSUFFICIENT";
        }
        
        int newPoints = vip.getRewardPoints() - pointsToDeduct;
        vip.setRewardPoints(newPoints);

        updateVipPointsInFile(vip.getMemberID(), newPoints);
        
        return "SUCCESS";
    }


    public Member searchVip(String confirmNum) {
        return vipQueue.searchByConfirmationNumber(confirmNum);
    }

    public int getVipCountByLevel(MemberLevel level) {
        return vipQueue.getCountByLevel(level);
    }

    public int getTotalWaitingCount() {
        return vipQueue.getNumberOfElements();
    }

    public String getHighValueVipsData(int minPoints) {
        return vipQueue.getHighValueVipsData(minPoints);
    }

    public int getHighValueVipsCount(int minPoints) {
        return vipQueue.getHighValueVipsCount(minPoints);
    }

    public mhotelreservationsystem.entity.Member searchVipInDatabase(String confirmNum) {
        return memberRepository.searchByConfirmation(confirmNum);
    }

    public boolean updateMemberStatus(String confirmNum, mhotelreservationsystem.entity.MembershipStatus newStatus) {
        boolean isUpdated = memberRepository.updateMembershipStatus(confirmNum, newStatus);
        
        mhotelreservationsystem.entity.Member vipInQueue = vipQueue.searchByConfirmationNumber(confirmNum);
        if (vipInQueue != null) {
            vipInQueue.setMembershipStatus(newStatus);
        }
        
        return isUpdated;
    }

    public void displayActiveAndCompletedVips() {
        mhotelreservationsystem.adt.VipBST tempDisplayTree = new mhotelreservationsystem.adt.VipBST();
        
        for (int i = 0; i < memberRepository.getTotalMember(); i++) {
            mhotelreservationsystem.entity.Member m = memberRepository.getMember(i);
            
        if (m.getMembershipStatus() == mhotelreservationsystem.entity.MembershipStatus.ACTIVE || 
                m.getMembershipStatus() == mhotelreservationsystem.entity.MembershipStatus.COMPLETED) {
                
                tempDisplayTree.enqueue(m);
            }
        }

        tempDisplayTree.displayAll();
    }

    public mhotelreservationsystem.entity.Room searchRoom(String roomNumber) {
        try {
            int roomNumInt = Integer.parseInt(roomNumber);
            return roomRepository.searchRoom(roomNumInt);
        } catch (NumberFormatException e) {
            return null;
        }
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

    public boolean createBookingForVip(Member member, int roomNumber){
        if(member == null) return false;

        String confirmNum = member.getConfirmationNumber();
        Room room = roomRepository.searchRoom(roomNumber);
        if(room == null){
            System.out.println("[Error] Room not found.");
            return false;
        }

        Guest existingGuest = guestRepository.searchGuest(confirmNum);
        if(existingGuest == null){
            System.out.println("[Error] Guest record not found for confirmation number: " + confirmNum);
            return false;
        }

        String bookingID = generateNextBookingID();
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = checkIn.plusDays(1);
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        double total = room.getRoomRate() * nights;

        Booking booking = new Booking(
            bookingID,
            confirmNum,
            roomNumber,
            room.getRoomType(),
            2,
            LocalDate.now(),
            checkIn,
            checkOut,
            total,
            BookingStatus.CHECKED_IN
        );

        existingGuest.setBookingID(bookingID);
        existingGuest.setRoomNumber(roomNumber);
        existingGuest.setCheckInDate(checkIn);
        existingGuest.setCheckOutDate(checkOut);
        existingGuest.setStatus(GuestStatus.CHECKED_IN);

        boolean ok1 = bookingRepository.addBooking(booking);
        boolean ok2 = guestRepository.updateGuest(existingGuest);
        boolean ok3 = roomRepository.updateStatus(String.valueOf(roomNumber), RoomStatus.OCCUPIED).equals("SUCCESS");

        if(ok1 && ok2 && ok3){
            System.out.println("[System] Booking created: " + bookingID + " | Guest updated | Room occupied.");
            return true;
        }

        System.out.println("[Error] Failed to create booking for VIP.");
        return false;
    }
}
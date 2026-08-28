/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.control;

import mhotelreservationsystem.adt.LinkedStack;
import mhotelreservationsystem.adt.VipBST;
import mhotelreservationsystem.entity.Member;
import mhotelreservationsystem.entity.MemberLevel;
import mhotelreservationsystem.entity.MembershipStatus;
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

    public VIPRoomControl(MemberRepository memberRepo, GuestRepository guestRepo, RoomRepository roomRepo) {
        this.memberRepository = memberRepo;
        this.guestRepository = guestRepo;
        this.roomRepository = roomRepo;
        
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
    }
}
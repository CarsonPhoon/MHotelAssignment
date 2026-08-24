/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.control;

import mhotelreservationsystem.adt.VipBST;
import mhotelreservationsystem.entity.Member;
import mhotelreservationsystem.entity.MemberLevel;
import mhotelreservationsystem.entity.MembershipStatus;
import mhotelreservationsystem.repository.GuestRepository;
import mhotelreservationsystem.repository.MemberRepository;

/**
 * 
 * @author zekai
 */
public class VIPRoomControl {

    private VipBST vipQueue;
    private MemberRepository memberRepository;
    private GuestRepository guestRepository;

    public VIPRoomControl(MemberRepository memberRepository, GuestRepository guestRepository) {
        this.memberRepository = memberRepository;
        this.guestRepository = guestRepository;
        this.vipQueue = new VipBST();
        loadVipsFromRepository(); 
    }

    private void loadVipsFromRepository() {
        for (int i = 0; i < memberRepository.getTotalMember(); i++) {
            Member member = memberRepository.getMember(i);
            if (member.getMembershipStatus() == MembershipStatus.ACTIVE) {
                vipQueue.insert(member);
            }
        }
        System.out.println("[System] Successfully loaded VIP data from MemberRepository!");
    }

    public boolean addVipToQueue(Member vipMember) {
        if (vipMember == null) return false;
        
        boolean isAdded = vipQueue.insert(vipMember);
        
        if (isAdded) {
            memberRepository.addMember(vipMember);
        }
        return isAdded;
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

    public Member assignRoomToNextVip() {
        if (vipQueue.isEmpty()) return null;
        return vipQueue.getHighestPriorityVip();
    }

    public void displayAllWaitingVips() {
        vipQueue.displayAll();
    }

    public Member searchVip(String confirmNum) {
        return vipQueue.searchByConfirmationNumber(confirmNum);
    }

    public int getVipCountByLevel(MemberLevel level) {
        return vipQueue.getCountByLevel(level);
    }

    public int getTotalWaitingCount() {
        return vipQueue.getSize();
    }

    public String getHighValueVipsData(int minPoints) {
        return vipQueue.getHighValueVipsData(minPoints);
    }

    public int getHighValueVipsCount(int minPoints) {
        return vipQueue.getHighValueVipsCount(minPoints);
    }
}

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
    private java.util.Stack<Member> assignedHistoryStack;
    private MemberRepository memberRepository;
    private GuestRepository guestRepository;

    public VIPRoomControl(MemberRepository memberRepository, GuestRepository guestRepository) {
        this.memberRepository = memberRepository;
        this.guestRepository = guestRepository;
        this.vipQueue = new VipBST();
        this.assignedHistoryStack = new java.util.Stack<>();
        
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

    public boolean updateRoomStatus(String roomNumber, String newStatus) {
        String filePath = "data/Room.txt"; 
        java.util.List<String> allLines = new java.util.ArrayList<>();
        boolean roomFound = false;
        
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split("\\|");
                
                if (parts.length > 0 && parts[0].trim().equals(roomNumber)) {
                    parts[parts.length - 1] = newStatus; 
                    line = String.join("|", parts);
                    roomFound = true;
                }
                allLines.add(line);
            }
        } catch (Exception e) {
            System.out.println("[System Error] Cannot read Room.txt.");
            return false;
        }
        
        if (roomFound) {
            try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(filePath, false))) {
                for (String l : allLines) {
                    bw.write(l);
                    bw.newLine();
                }
                return true;
            } catch (Exception e) {
                System.out.println("[System Error] Failed to update Room.txt.");
            }
        }
        return false;
    }

    public mhotelreservationsystem.entity.Member assignRoomToNextVip() {
        mhotelreservationsystem.entity.Member assignedVip = vipQueue.getHighestPriorityVip();
        if (assignedVip != null) {
            assignedHistoryStack.push(assignedVip);
        }
        return assignedVip;
    }

    public void displayAssignedHistory() {
        System.out.println("\n=====================================");
        System.out.println("   Recent VIP Room Allocations   ");
        System.out.println("=====================================");
        
        if (assignedHistoryStack.isEmpty()) {
            System.out.println("No rooms have been assigned yet today.");
            return;
        }
        
        for (int i = assignedHistoryStack.size() - 1; i >= 0; i--) {
            System.out.println((assignedHistoryStack.size() - i) + ". " + assignedHistoryStack.get(i).toString());
        }
    }

    public void updateVipPointsInFile(String memberID, int newPoints) {
        String filePath = "data/Member.txt";
        java.util.List<String> allLines = new java.util.ArrayList<>();
        
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                
                if (parts.length > 3 && parts[0].trim().equals(memberID)) {
                    parts[3] = String.valueOf(newPoints); 
                    line = String.join("|", parts);
                }
                allLines.add(line);
            }
        } catch (Exception e) {
            System.out.println("[System Error] Cannot read Member.txt.");
        }
        
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(filePath, false))) {
            for (String l : allLines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("[System Error] Failed to update points in Member.txt.");
        }
    }

    public String redeemPoints(String confirmNum, int pointsToDeduct) {
        mhotelreservationsystem.entity.Member vip = vipQueue.searchByConfirmationNumber(confirmNum);
        
        if (vip == null) {
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

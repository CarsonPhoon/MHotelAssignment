/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import mhotelreservationsystem.adt.VipBST;
import mhotelreservationsystem.entity.Member;
import mhotelreservationsystem.entity.MemberLevel;
import mhotelreservationsystem.entity.MembershipStatus;

/**
 * 
 * @author zekai
 */
public class VIPRoomControl {

    private VipBST vipQueue;

    public VIPRoomControl() {
        this.vipQueue = new VipBST();
        loadVipsFromFile(); 
    }

    private void loadVipsFromFile() {
        String filepath = "data/Member.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    String memberID = parts[0];
                    String confirmationNumber = parts[1];
                    MemberLevel level = MemberLevel.valueOf(parts[2].toUpperCase());
                    int points = Integer.parseInt(parts[3]);
                    LocalDate joinDate = LocalDate.parse(parts[4]);
                    MembershipStatus status = MembershipStatus.valueOf(parts[5].toUpperCase());

                    Member member = new Member(memberID, confirmationNumber, level, points, joinDate, status);
                
                    if (status == mhotelreservationsystem.entity.MembershipStatus.ACTIVE) {
                        vipQueue.insert(member); 
                    }
                }
            }
            System.out.println("[System] Successfully loaded default VIP data from Member.txt!");
        } catch (Exception e) {
            System.out.println("[System Warning] Member.txt not found or failed to read. Starting with an empty queue.");
        }
    }

    public boolean addVipToQueue(Member vipMember) {
        if (vipMember == null) return false;
        
        boolean isAdded = vipQueue.insert(vipMember);
        
        if (isAdded) {
            appendVipToFile(vipMember);
        }
        return isAdded;
    }

    private void appendVipToFile(Member member) {
        String filepath = "data/Member.txt";
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(filepath, true))) {
            String line = member.getMemberID() + "|" + 
                          member.getConfirmationNumber() + "|" + 
                          member.getMemberLevel() + "|" + 
                          member.getRewardPoints() + "|" + 
                          member.getJoinDate() + "|" + 
                          member.getMembershipStatus();
            bw.write(line);
            bw.newLine();
        } catch (Exception e) {
            System.out.println("[System Error] Failed to save VIP to Member.txt");
        }
    }

    public boolean verifyGuestExists(String confirmNum) {
        String walkInFilePath = "data/Guest.txt";
        
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(walkInFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length > 0 && data[0].trim().equals(confirmNum)) {
                    return true;
                }
            }
        } catch (java.io.IOException e) {
            System.out.println("Could not find in Walk-In text file.");
        }
        return false;
    }

  public String generateNextMemberId() {
        int maxId = 0;
        String filePath = "data/Member.txt"; 
        
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] data = line.split("\\|");
                if (data.length > 0 && data[0].startsWith("MB")) {
                    try {
                        int currentId = Integer.parseInt(data[0].substring(2));
                        if (currentId > maxId) {
                            maxId = currentId;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (java.io.IOException e) {
            System.out.println("[System Warning] Could not read Member.txt to generate ID. Starting from MB0001.");
        }
        
        return String.format("MB%04d", maxId + 1);
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
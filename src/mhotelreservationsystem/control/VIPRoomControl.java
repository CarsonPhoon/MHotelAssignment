/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import mhotelreservationsystem.entity.Member;
import mhotelreservationsystem.entity.MemberLevel;
import mhotelreservationsystem.entity.MembershipStatus;

/**
 * 
 * @author zekai
 */
public class VIPRoomControl {
    private mhotelreservationsystem.adt.VipBST vipQueue;
    private java.util.Stack<mhotelreservationsystem.entity.Member> assignedHistoryStack;

    public VIPRoomControl() {
        this.vipQueue = new mhotelreservationsystem.adt.VipBST();
        this.assignedHistoryStack = new java.util.Stack<>();
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
            System.out.println("Could not find in Guest text file.");
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

    public boolean isVipAlreadyRegistered(String confirmNum) {
        String filePath = "data/Member.txt";
        
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("\\|");
                if (parts.length >= 2 && parts[1].trim().equals(confirmNum)) {
                    return true;
                }
            }
        } catch (java.io.IOException e) {
        }
        return false; 
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
        System.out.println("   Recent VIP Room Allocations (LIFO)");
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
        String filePath = "data/Member.txt"; // 确认你的路径是 data/Member.txt 还是 Member.txt
        java.util.List<String> allLines = new java.util.ArrayList<>();
        
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                
                // 假设 Member ID 在第 1 列 (索引 0)，积分在第 4 列 (索引 3)
                // 请核对你的 Member.txt，如果积分不是第 4 列，请把 parts[3] 改成对应的数字
                if (parts.length > 3 && parts[0].trim().equals(memberID)) {
                    parts[3] = String.valueOf(newPoints); // 更新分数
                    line = String.join("|", parts);
                }
                allLines.add(line);
            }
        } catch (Exception e) {
            System.out.println("[System Error] Cannot read Member.txt.");
        }
        
        // 覆写文件
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
        vip.setRewardPoints(newPoints); // 你的 Member class 需要有 setRewardPoints 方法

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
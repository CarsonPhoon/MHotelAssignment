/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package mhotelreservationsystem.report;

import mhotelreservationsystem.control.VIPRoomControl;
import mhotelreservationsystem.entity.MemberLevel;

/**
 * Report generation class for VIP Room Allocation.
 * @author Ang Ze Kai
 */
public class VIPQueueReport {

    private VIPRoomControl vipControl;

    public VIPQueueReport(VIPRoomControl vipControl) {
        this.vipControl = vipControl;
    }


    public void generateQueueByLevelReport() {
        System.out.println("\n=======================================================");
        System.out.println("          REPORT 1: VIP QUEUE STATUS BY LEVEL          ");
        System.out.println("=======================================================");
        
        int platinum = vipControl.getVipCountByLevel(MemberLevel.PLATINUM);
        int elite = vipControl.getVipCountByLevel(MemberLevel.ELITE);
        int gold = vipControl.getVipCountByLevel(MemberLevel.GOLD);
        int silver = vipControl.getVipCountByLevel(MemberLevel.SILVER);
        int bronze = vipControl.getVipCountByLevel(MemberLevel.BRONZE);
        int total = vipControl.getTotalWaitingCount();
        
        System.out.println("PLATINUM Members Waiting : " + platinum);
        System.out.println("ELITE Members Waiting    : " + elite);
        System.out.println("GOLD Members Waiting     : " + gold);
        System.out.println("SILVER Members Waiting   : " + silver);
        System.out.println("BRONZE Members Waiting   : " + bronze);
        System.out.println("-------------------------------------------------------");
        System.out.println("Total VIPs in Queue      : " + total);
        System.out.println("=======================================================\n");
    }

    public void generateHighValueVipReport() {
        int threshold = 3000; 
        
        System.out.println("\n=======================================================");
        System.out.println("       REPORT 2: HIGH-VALUE VIPs (>" + threshold + " POINTS)       ");
        System.out.println("=======================================================");
        System.out.println(String.format("%-10s %-10s %-12s %-8s", "MemberID", "Confirm#", "Level", "Points"));
        System.out.println("-------------------------------------------------------");
        
        String dataLines = vipControl.getHighValueVipsData(threshold);
        int count = vipControl.getHighValueVipsCount(threshold);
        
        if (dataLines.isEmpty()) {
            System.out.println("No high-value VIPs found in the queue.");
        } else {
            System.out.print(dataLines); 
        }
        
        System.out.println("-------------------------------------------------------");
        System.out.println("Total High-Value VIPs Found: " + count);
        System.out.println("=======================================================\n");
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.report;

/**
 *
 * @author chanzj
 */

import mhotelreservationsystem.adt.ListInterface;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.control.HousekeepingControl;
import mhotelreservationsystem.entity.CleaningRecord;
import mhotelreservationsystem.entity.Room;
import mhotelreservationsystem.entity.RoomCleaningStatus;
import mhotelreservationsystem.repository.RoomRepository;
import java.time.format.DateTimeFormatter;

public class HousekeepingReport {
    private HousekeepingControl housekeepingControl;
    private RoomRepository roomRepository;
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    // wires report to control/repository
    public HousekeepingReport(HousekeepingControl housekeepingControl, RoomRepository roomRepository){
        this.housekeepingControl = housekeepingControl;
        this.roomRepository = roomRepository;
    }
    
    // prints full report
    public void generateReport(){
        printRoomStatusSummary();
        System.out.println("\n");
        printStaffWorkload();
    }
    
    // Room Status Summary - list the room status
    
    // prints room status counts
    private void printRoomStatusSummary(){
        System.out.println(" --------------------- ");
        System.out.println("| ROOM STATUS SUMMARY |");
        System.out.println(" --------------------- ");
        
        int totalRooms = roomRepository.getTotalRoom();
        
        int dirtyCount = 0;
        int inProgressCount = 0;
        int inspectedCount = 0;
        int readyCount = 0;
        int untouchedCount = 0; // rooms that no cleaning status currently
        
        for (int i = 0; i < totalRooms; i++){
            Room room = roomRepository.getRoom(i);
            RoomCleaningStatus status = housekeepingControl.getCurrentStatus(room.getRoomNumber());
            
            if (status == null){
                untouchedCount++;
            } else {
                switch (status){
                    case DIRTY -> dirtyCount++;
                    case INPROGRESS -> inProgressCount++;
                    case INSPECTED -> inspectedCount++;
                    case READYFORCHECKIN -> readyCount++;
                }
            }
        }
        
        System.out.println("Dirty                : " + dirtyCount);
        System.out.println("Cleaning In Progress : " + inProgressCount);
        System.out.println("Checked              : " + inspectedCount);
        System.out.println("Ready for Check-In   : " + readyCount);
        System.out.println("Room (no status)     : " + untouchedCount);
        System.out.println("Total rooms          : " + totalRooms);
    }
    
    // Staff Workload
    //prints staff workload summary
    private void printStaffWorkload(){
        System.out.println(" --------------------- ");
        System.out.println("|    STAFF WORKLOAD   |");
        System.out.println(" --------------------- ");
        
        ListInterface<CleaningRecord> records = housekeepingControl.getCompletedRecords();
        
        if (records.isEmpty()){
            System.out.println("No completed cleaning tasks recorded yet.");
            return;
        }
        
        ListInterface<WorkloadTally> tallies = new ArrayListADT<>();
        
        for (int i = 0; i < records.getNumberOfEntries(); i++){
            CleaningRecord record = records.get(i);
            WorkloadTally tally = findTally(tallies, record.getStaffID());
            if (tally == null){
                tallies.add(new WorkloadTally(record.getStaffID(), record.getStaffName()));
            } else {
                tally.increment();
            }
        }
        
        for (int i = 0; i < tallies.getNumberOfEntries(); i++){
            WorkloadTally tally = tallies.get(i);
            System.out.println(tally.getStaffName() + "(" + tally.getStaffID() + "):" + tally.getCount() + " room(s) cleaned");
        }
        
        System.out.println("\nMost recent completions:");
        int start = Math.max(0, records.getNumberOfEntries() - 5);
        for (int i = start; i < records.getNumberOfEntries(); i++){
            CleaningRecord record = records.get(i);
            System.out.println("- Room" + record.getRoomNumber() + " by " + record.getStaffName() + " at " + record.getCompletedAt().format(FORMAT));
        }
    }
    
    // finds a staff's workload tally
    private WorkloadTally findTally(ListInterface<WorkloadTally> tallies, String staffID){
        for (int i = 0; i < tallies.getNumberOfEntries(); i++){
            if (tallies.get(i).getStaffID().equalsIgnoreCase(staffID)){
                return tallies.get(i);
            }
        }
        return null;
    }
    
    // creates a tally starting at 1
    private static class WorkloadTally {
        private String staffID;
        private String staffName;
        private int count;
        
        WorkloadTally(String staffID, String staffName){
            this.staffID = staffID;
            this.staffName = staffName;
            this.count = 1;
        }
        
        // increaments tally count
        void increment(){
            count++;
        }
        
        String getStaffID(){
            return staffID;
        }
        
        String getStaffName(){
            return staffName;
        }
        
        int getCount(){
            return count;
        }
    }
}

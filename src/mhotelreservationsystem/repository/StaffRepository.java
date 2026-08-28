/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.repository;

import java.io.*;
import mhotelreservationsystem.adt.ListInterface;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.entity.Staff;
import mhotelreservationsystem.utility.FilePath;
/**
 *
 * @author chanzj
 */
public class StaffRepository {
   
    private ListInterface<Staff> staffList;
    
    // loads staff from file
    public StaffRepository(){
        this.staffList = loadStaffFromFile();
    }
    
    // ---------- Staff CRUD --------------
    // gets all staff
    public ListInterface<Staff> getAllStaff(){
        return staffList;
    }
    
    // find staff by ID
    public Staff getStaff(String staffID){
        for (int i = 0; i < staffList.getNumberOfEntries(); i++){
            Staff s = staffList.get(i);
            if (s.getStaffID().equalsIgnoreCase(staffID)){
                return s;
            }
        }
        return null;
    }
    
    // Adds new staff, saves to file
    public boolean addStaff(Staff staff){
        if (getStaff(staff.getStaffID()) != null){
            return false;
        }
        staffList.add(staff);
        saveStaffToFile();
        return true;
    }
    
    // file i/o
    // reads staff list from file
    private ListInterface<Staff> loadStaffFromFile(){
        ListInterface<Staff> list = new ArrayListADT<>();
        File file = new File(FilePath.STAFF_FILE);
        if (!file.exists()){
            return list;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                if (line.isBlank()) continue;
                String[] parts = line.split("\\|");
                if (parts.length >= 2){
                    list.add(new Staff(parts[0].trim(), parts[1].trim()));
                }
            }
        } catch (IOException e){
            System.out.println("Error laoding staff data: " + e.getMessage());
        }
        return list;
    }
    
    // writes staff list to file
    private void saveStaffToFile(){
        File file = new File(FilePath.STAFF_FILE);
        file.getParentFile().mkdirs();
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))){
            for (int i = 0; i < staffList.getNumberOfEntries(); i++){
                Staff s = staffList.get(i);
                writer.println(s.getStaffID() + "|" + s.getStaffName());
            }
        } catch (IOException e){
            System.out.println("Error saving staff data: " + e.getMessage());
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import mhotelreservationsystem.adt.GuestBST;
import mhotelreservationsystem.entity.Guest;
import mhotelreservationsystem.entity.GuestStatus;
import mhotelreservationsystem.utility.FileUtility;
import mhotelreservationsystem.utility.FilePath;
/**
 *
 * @author phoon
 */
public class GuestRepository {

    private GuestBST guestBST;

    public GuestRepository() {

        guestBST = new GuestBST();

        if (FileUtility.fileExists(FilePath.GUEST_FILE)) {
            loadFromFile();
        }
    }

    public boolean addGuest(Guest guest) {

        boolean success = guestBST.insert(guest);

        if (success) {
            saveToFile();
        }

        return success;
    }

    public Guest searchGuest(String confirmationNumber) {
        return guestBST.search(confirmationNumber);
    }
  
    public boolean removeGuest(String confirmationNumber){

        boolean success = guestBST.remove(confirmationNumber);

        if(success){
            saveToFile();
        }

        return success;
    }
    
    public void displayAllGuests(){

        System.out.println("==============================================================");
        System.out.printf("%-12s %-20s %-6s %-12s\n",
                "Confirm No",
                "Guest Name",
                "Room",
                "Status");
        System.out.println("==============================================================");

        guestBST.inorderTraversal();

        System.out.println("==============================================================");
        System.out.println("Total Guests : " + guestBST.getSize());

    }
    
    public boolean updateGuest(Guest guest){

        if(searchGuest(guest.getConfirmationNumber()) == null){
            return false;
        }

        guestBST.remove(guest.getConfirmationNumber());

        guestBST.insert(guest);

        saveToFile();

        return true;
    }
    
    public boolean isEmpty() {
        return guestBST.isEmpty();
    }
    
    public int getTotalGuest(){
        return guestBST.getSize();
    }

    public void clearAllGuests() {

        guestBST.clear();

        saveToFile();
    }
    
    public GuestBST getGuestBST() {
        return guestBST;
    }

    // Load data from txt 
    private void loadFromFile() {
        
        guestBST.clear();

        try {
            BufferedReader reader =
                    FileUtility.openReader(FilePath.GUEST_FILE);

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                Guest guest = convertToGuest(line);
                guestBST.insert(guest);
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error loading Guest.txt");
        }
    }
    
    public void saveToFile() {

        try {

            BufferedWriter writer =
                    FileUtility.openWriter(FilePath.GUEST_FILE);

            guestBST.saveToFile(writer);

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving Guest.txt");

        }

    }

    private Guest convertToGuest(String line){
        
        String [] data = line.split("\\|");
        
        if(data.length != 9){
            return null;
        }
        
        Guest guest = convertToGuest(line);

        if(guest != null){
            guestBST.insert(guest);
        }
        
        return new Guest(
            data[0],
            data[1],    
            data[2],
            data[3],
            data[4],
            Integer.parseInt(data[5]),
            LocalDate.parse(data[6]),    
            LocalDate.parse(data[7]),
            GuestStatus.valueOf(data[8])
        );
    }
}
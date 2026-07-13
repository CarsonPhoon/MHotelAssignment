/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.repository;

import java.time.LocalDate;
import mhotelreservationsystem.adt.GuestBST;
import mhotelreservationsystem.entity.Guest;
import mhotelreservationsystem.entity.GuestStatus;
/**
 *
 * @author phoon
 */
public class GuestRepository {

    private GuestBST guestBST;

    public GuestRepository() {
        guestBST = new GuestBST();
        loadSampleData();
    }

    public boolean addGuest(Guest guest) {
        return guestBST.insert(guest);
    }

    public Guest searchGuest(String confirmationNumber) {
        return guestBST.search(confirmationNumber);
    }
  
    public boolean removeGuest(String confirmationNumber){
        return guestBST.remove(confirmationNumber);
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
    
    public boolean isEmpty() {
        return guestBST.isEmpty();
    }
    
    public int getTotalGuest(){
        return guestBST.getSize();
    }

    public void clearAllGuests() {
        guestBST.clear();
    }
    
    public GuestBST getGuestBST() {
        return guestBST;
    }

    // Sample Data

    private void loadSampleData() {

        addGuest(new Guest(
                "10000001",
                "Carson Phoon",
                "0123456789",
                "carson@gmail.com",
                "BK0001",
                101,
                LocalDate.of(2026,7,1),
                LocalDate.of(2026,7,3),
                GuestStatus.CHECKED_IN));

        addGuest(new Guest(
                "10000002",
                "Alex Tan",
                "0112233445",
                "alex@gmail.com",
                "BK0002",
                102,
                LocalDate.of(2026,7,2),
                LocalDate.of(2026,7,4),
                GuestStatus.RESERVED));

        addGuest(new Guest(
                "10000003",
                "John Lim",
                "0178899001",
                "john@gmail.com",
                "BK0003",
                201,
                LocalDate.of(2026,7,5),
                LocalDate.of(2026,7,7),
                GuestStatus.CHECKED_OUT));

        addGuest(new Guest(
                "10000004",
                "Alice Lee",
                "0187654321",
                "alice@gmail.com",
                "BK0004",
                202,
                LocalDate.of(2026,7,6),
                LocalDate.of(2026,7,8),
                GuestStatus.CHECKED_IN));

        addGuest(new Guest(
                "10000005",
                "David Wong",
                "0198888777",
                "david@gmail.com",
                "BK0005",
                301,
                LocalDate.of(2026,7,7),
                LocalDate.of(2026,7,9),
                GuestStatus.RESERVED));

        addGuest(new Guest(
                "10000006",
                "Sarah Ng",
                "0129988776",
                "sarah@gmail.com",
                "BK0006",
                302,
                LocalDate.of(2026,7,8),
                LocalDate.of(2026,7,10),
                GuestStatus.CHECKED_IN));

        addGuest(new Guest(
                "10000007",
                "Kevin Chan",
                "0166677889",
                "kevin@gmail.com",
                "BK0007",
                401,
                LocalDate.of(2026,7,9),
                LocalDate.of(2026,7,11),
                GuestStatus.RESERVED));

        addGuest(new Guest(
                "10000008",
                "Emily Goh",
                "0134455667",
                "emily@gmail.com",
                "BK0008",
                402,
                LocalDate.of(2026,7,10),
                LocalDate.of(2026,7,12),
                GuestStatus.CHECKED_IN));

        addGuest(new Guest(
                "10000009",
                "Jason Yap",
                "0115566778",
                "jason@gmail.com",
                "BK0009",
                501,
                LocalDate.of(2026,7,11),
                LocalDate.of(2026,7,13),
                GuestStatus.CHECKED_OUT));

        addGuest(new Guest(
                "10000010",
                "Michelle Teo",
                "0183344556",
                "michelle@gmail.com",
                "BK0010",
                502,
                LocalDate.of(2026,7,12),
                LocalDate.of(2026,7,14),
                GuestStatus.CHECKED_IN));
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.entity;

import java.time.LocalDate;

/**
 * A guest waiting for a room to become available (in-memory only; front-desk session data).
 * @author xb
 */
public class WaitingListEntry {

    private String waitingID;
    private String guestName;
    private String phoneNumber;
    private String email;
    private RoomType requestedRoomType;
    private int numberOfGuests;
    private int requestedNights;
    private LocalDate requestDate;

    public WaitingListEntry() {}

    public WaitingListEntry(String waitingID, String guestName, String phoneNumber, String email,
                             RoomType requestedRoomType, int numberOfGuests, int requestedNights, LocalDate requestDate) {
        this.waitingID = waitingID;
        this.guestName = guestName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.requestedRoomType = requestedRoomType;
        this.numberOfGuests = numberOfGuests;
        this.requestedNights = requestedNights;
        this.requestDate = requestDate;
    }

    public String getWaitingID() {
        return waitingID;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public RoomType getRequestedRoomType() {
        return requestedRoomType;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public int getRequestedNights() {
        return requestedNights;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    @Override
    public String toString() {
        return String.format(
                "%-8s %-20s %-10s %-6d %-8d %s",
                waitingID,
                guestName,
                requestedRoomType,
                numberOfGuests,
                requestedNights,
                requestDate
        );
    }
}

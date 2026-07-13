/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.entity;

import java.time.LocalDate;
/**
 *
 * @author phoon
 */
public class Member {

    private String memberID;
    private String confirmationNumber;
    private MemberLevel memberLevel;
    private int rewardPoints;
    private LocalDate joinDate;
    private MembershipStatus membershipStatus;

    public Member() {}

    public Member(String memberID, String confirmationNumber, MemberLevel memberLevel, int rewardPoints, LocalDate joinDate, MembershipStatus membershipStatus) {
        this.memberID = memberID;
        this.confirmationNumber = confirmationNumber;
        this.memberLevel = memberLevel;
        this.rewardPoints = rewardPoints;
        this.joinDate = joinDate;
        this.membershipStatus = membershipStatus;
    }
  
    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setMemberLevel(MemberLevel memberLevel) {
        this.memberLevel = memberLevel;
    }

    public MemberLevel getMemberLevel() {
        return memberLevel;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setMembershipStatus(MembershipStatus membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public MembershipStatus getMembershipStatus() {
        return membershipStatus;
    }

    @Override
    public String toString() {

        return String.format(
                "%-8s %-10s %-10s %-8d %-12s %-10s",
                memberID,
                confirmationNumber,
                memberLevel,
                rewardPoints,
                joinDate,
                membershipStatus
        );
    }
}
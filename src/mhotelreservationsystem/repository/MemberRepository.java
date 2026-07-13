/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.repository;

import java.time.LocalDate;
import mhotelreservationsystem.adt.ArrayListADT;
import mhotelreservationsystem.entity.Member;
import mhotelreservationsystem.entity.MemberLevel;
import mhotelreservationsystem.entity.MembershipStatus;
/**
 *
 * @author phoon
 */
public class MemberRepository {

    private ArrayListADT<Member> memberList;
    public MemberRepository(){
        memberList=new ArrayListADT<>();
        initializeSampleData();
    }

    // Sample data
    
    private void initializeSampleData(){
        memberList.add(new Member(
                "M0001",
                "10000001",
                MemberLevel.GOLD,
                2500,
                LocalDate.of(2024,1,5),
                MembershipStatus.ACTIVE));

        memberList.add(new Member(
                "M0002",
                "10000003",
                MemberLevel.SILVER,
                1200,
                LocalDate.of(2025,3,10),
                MembershipStatus.ACTIVE));

        memberList.add(new Member(
                "M0003",
                "10000008",
                MemberLevel.PLATINUM,
                6800,
                LocalDate.of(2023,6,18),
                MembershipStatus.ACTIVE));
    }

    public boolean addMember(Member member){
        return memberList.add(member);
    }

    public Member getMember(int index){
        return memberList.get(index);
    }

    public int getTotalMember(){
        return memberList.getNumberOfEntries();
    }

    public void displayAllMembers(){

        System.out.println();
        System.out.println("==============================================================");
        for(int i=0;i<memberList.getNumberOfEntries();i++){
            System.out.println(memberList.get(i));
        }
    }

    public ArrayListADT<Member> getMemberList(){
        return memberList;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import mhotelreservationsystem.entity.Member;
import mhotelreservationsystem.entity.MemberLevel;
import mhotelreservationsystem.entity.MembershipStatus;
import mhotelreservationsystem.utility.FilePath;
import mhotelreservationsystem.utility.FileUtility;
/**
 *
 * @author phoon
 */
public class MemberRepository{
    
    private static final int MAX_MEMBER = 100;
    private Member[] members;
    private int size;
    
    public MemberRepository(){
        members = new Member[MAX_MEMBER];
        size = 0;
        
        if(FileUtility.fileExists(FilePath.MEMBER_FILE)){
            loadFromFile();
        }
    }
    
    // CRUD operation
    public boolean addMember(Member member){
        if(size >= MAX_MEMBER){
            return false;
        }
        
        members[size++] = member;
        saveToFile();
        return true;
    }
    
    public Member getMember(int index){
        if(index < 0 || index >= size){
            return null;
        }
        
        return members[index];
    }
    
    public int getTotalMember(){
        return size;
    }
    
    public void displayAllMembers(){

        System.out.println();
        System.out.println("==================================================================================");
        System.out.printf("%-8s %-10s %-10s %-8s %-12s %-10s%n",
                "Member",
                "Confirm",
                "Level",
                "Points",
                "Join Date",
                "Status");
        System.out.println("==================================================================================");

        for(int i = 0; i < size; i++){

            System.out.println(members[i]);

        }

        System.out.println("==================================================================================");
        System.out.println("Total Members : " + size);

    }
    
    public Member searchMember(String memberID){
        for(int i = 0; i < size; i++){
            if(members[i].getMemberID().equalsIgnoreCase(memberID)){
                return members[i];
            }
        }
        return null;
    }
    
    public Member searchByConfirmation(String confirmationNumber){
        for(int i = 0; i < size; i++){
            if(members[i].getConfirmationNumber().equalsIgnoreCase(confirmationNumber)){
                return members[i];
            }
        }
        return null;
    }
    
    public boolean updateMember(Member member){
        for(int i = 0; i < size; i++){
            if(members[i].getMemberID().equalsIgnoreCase(member.getMemberID())){
                members[i] = member;
                saveToFile();
                return true;
            }
        }
        return false;
    }
    
    public boolean removeMember(String memberID){
        for(int i = 0; i < size; i++){
            if(members[i].getMemberID().equalsIgnoreCase(memberID)){
                for(int j = i; j < size - 1; j++){
                    members[j] = members[j + 1];
                }
                
                members[size - 1] = null;
                size--;
                saveToFile();
                return true;
            }
        }
        return false;
    }
    
    private void loadFromFile(){
        size = 0;
        
        try{
            BufferedReader reader = FileUtility.openReader(FilePath.MEMBER_FILE);
            
            String line;
            
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }
                
                Member member = convertToMember(line);
                
                if(member != null){
                    members[size++] = member;
                }
            }
            
            reader.close();
        } catch(IOException e){
            System.out.println("Error loading Mmeber.txt");
        }
    }
    
    public void saveToFile(){
        try{
            BufferedWriter writer = FileUtility.openWriter(FilePath.MEMBER_FILE);
            
            for(int i = 0; i < size; i++){
                writer.write(convertToString(members[i]));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e){
            System.out.println("Error saving Member.txt");
        }
    }
    
    private Member convertToMember(String line){

        String[] data = line.split("\\|");

        if(data.length != 6){
            return null;
        }

        return new Member(

                data[0],
                data[1],
                MemberLevel.valueOf(data[2]),
                Integer.parseInt(data[3]),
                LocalDate.parse(data[4]),
                MembershipStatus.valueOf(data[5])

        );
    }
    
    private String convertToString(Member member){

        return member.getMemberID() + "|" +
               member.getConfirmationNumber() + "|" +
               member.getMemberLevel() + "|" +
               member.getRewardPoints() + "|" +
               member.getJoinDate() + "|" +
               member.getMembershipStatus();
    }
}
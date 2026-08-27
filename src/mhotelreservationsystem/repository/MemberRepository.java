/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import mhotelreservationsystem.adt.ArrayListADT;
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
    
    // Use an ArrayList to store member data
    private ArrayListADT<Member> members;
    
    public MemberRepository(){
        members = new ArrayListADT<Member>();
        
        if(FileUtility.fileExists(FilePath.MEMBER_FILE)){
            loadFromFile();
        }
    }
    
    // CRUD operation
    public boolean addMember(Member member){

        boolean success = members.add(member);

        if(success){
            saveToFile();
        }

        return success;
    }
    
    public Member getMember(int index){
        return members.get(index);
    }
    
    public int getTotalMember(){
        return members.getNumberOfEntries();
    }
    
    public Member searchByConfirmation(String confirmationNumber){
        for(int i = 0; i < members.getNumberOfEntries(); i++){
            Member m = members.get(i);
            if(m.getConfirmationNumber().equalsIgnoreCase(confirmationNumber)){
                return m;
            }
        }
        return null;
    }

    public Member searchByMemberID(String memberID){
        for(int i = 0; i < members.getNumberOfEntries(); i++){
            Member m = members.get(i);
            if(m.getMemberID().equalsIgnoreCase(memberID)){
                return m;
            }
        }
        return null;
    }
    
    // Load data from txt file
    private void loadFromFile(){
        
        try{
            BufferedReader reader = FileUtility.openReader(FilePath.MEMBER_FILE);
            
            String line;
            
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }
                
                Member member = convertToMember(line);
                
                if(member != null){
                    members.add(member);
                }
            }
            reader.close();
        } catch(IOException e){
            System.out.println("Error loading Member.txt");
        }
    }
    
    // Save data to txt file
    public void saveToFile(){
        try{
            BufferedWriter writer = FileUtility.openWriter(FilePath.MEMBER_FILE);
            
            for(int i = 0; i < members.getNumberOfEntries(); i++){
                writer.write(convertToString(members.get(i)));
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
                data[0],                               // Member ID
                data[1],                               // Confirmation Number
                MemberLevel.fromDisplayName(data[2]),  // Member Level
                Integer.parseInt(data[3]),             // Reward Point
                LocalDate.parse(data[4]),              // Join Date
                MembershipStatus.fromDisplayName(data[5])  // Membership Status

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

    public void updatePoints(String memberID, int newPoints) {
        for (int i = 0; i < members.getNumberOfEntries(); i++) {
            mhotelreservationsystem.entity.Member member = members.get(i);
            if (member.getMemberID().equals(memberID)) {
                member.setRewardPoints(newPoints);
                saveToFile();
                return;
            }
        }
    }
}
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
import mhotelreservationsystem.entity.Comment;
import mhotelreservationsystem.entity.CommentStatus;
import mhotelreservationsystem.entity.CommentType;
import mhotelreservationsystem.utility.FilePath;
import mhotelreservationsystem.utility.FileUtility;
/**
 *
 * @author phoon
 */
public class CommentRepository {
    
    private ArrayListADT<Comment> comments;
    
    public CommentRepository(){
        comments = new ArrayListADT<Comment>();
        
        if(FileUtility.fileExists(FilePath.COMMENT_FILE)){
            loadFromFile();
        }
    }
    
    // CRUD operation
    public boolean addComment(Comment comment){
        boolean success = comments.add(comment);
        if(success){
            saveToFile();
        }
        return success;
    }
    
    public Comment getComment(int index){
        return comments.get(index);
    }
    
    public int getTotalComment(){
        return comments.getNumberOfEntries();
    }
    
    public Comment searchComment(String commentID){
        for(int i = 0; i < comments.getNumberOfEntries(); i++){
            Comment c = comments.get(i);
            if(c.getCommentID().equalsIgnoreCase(commentID)){
                return c;
            }
        }
        return null;
    }
    
    public ArrayListADT<Comment> searchByConfirmation(String confirmationNumber){
        ArrayListADT<Comment> result = new ArrayListADT<Comment>();
        for(int i = 0; i < comments.getNumberOfEntries(); i++){
            Comment c = comments.get(i);
            if(c.getConfirmationNumber().equalsIgnoreCase(confirmationNumber)){
                result.add(c);
            }
        }
        return result;
    }
    
    public boolean updateComment(Comment comment){
        for(int i = 0; i < comments.getNumberOfEntries(); i++){
            Comment c = comments.get(i);
            if(c.getCommentID().equalsIgnoreCase(comment.getCommentID())){
                comments.replace(i, comment);
                saveToFile();
                return true;
            }
        }
        return false;
    }
    
    public boolean removeComment(String commentID){
        for(int i = 0; i < comments.getNumberOfEntries(); i++){
            Comment c = comments.get(i);
            if(c.getCommentID().equalsIgnoreCase(commentID)){
                comments.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }
    
    // Bubble Sort - sort comments by date descending (newest first)
    private void bubbleSortByDateDesc(Comment[] arr, int size){
        for(int i = 0; i < size - 1; i++){
            for(int j = 0; j < size - 1 - i; j++){
                if(arr[j].getDate().compareTo(arr[j + 1].getDate()) < 0){
                    Comment temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    public void displayAllComments(){
        int size = comments.getNumberOfEntries();
        Comment[] arr = new Comment[size];
        for(int i = 0; i < size; i++){
            arr[i] = comments.get(i);
        }
        bubbleSortByDateDesc(arr, size);
        
        System.out.println();
        System.out.println("==================================================================================================");
        System.out.printf("%-12s %-8s %-12s %-6s %-10s %-10s %s%n",
                "Date", "ID", "Confirm", "Room", "Type", "Status", "Description");
        System.out.println("==================================================================================================");
        
        for(int i = 0; i < size; i++){
            System.out.println(arr[i]);
        }
        
        System.out.println("==================================================================================================");
        System.out.println("Total Comments : " + size);
    }
    
    // Load data from txt file
    private void loadFromFile(){
        try{
            BufferedReader reader = FileUtility.openReader(FilePath.COMMENT_FILE);
            String line;
            while((line = reader.readLine()) != null){
                if(line.trim().isEmpty()) continue;
                Comment comment = convertToComment(line);
                if(comment != null){
                    comments.add(comment);
                }
            }
            reader.close();
        }catch(IOException e){
            System.out.println("Error loading Comment.txt");
        }
    }
    
    // Save data to txt file
    public void saveToFile(){
        try{
            BufferedWriter writer = FileUtility.openWriter(FilePath.COMMENT_FILE);
            for(int i = 0; i < comments.getNumberOfEntries(); i++){
                writer.write(convertToString(comments.get(i)));
                writer.newLine();
            }
            writer.close();
        }catch(IOException e){
            System.out.println("Error saving Comment.txt");
        }
    }
    
    private Comment convertToComment(String line){
        String[] data = line.split("\\|");
        if(data.length != 7) return null;
        return new Comment(
                data[0],                                  // Comment ID
                data[1],                                  // Confirmation Number
                Integer.parseInt(data[2]),                // Room No
                CommentType.fromDisplayName(data[3]),     // Comment Type
                data[4],                                  // Comment
                CommentStatus.fromDisplayName(data[5]),   // Comment Status
                LocalDate.parse(data[6])                  // Comment Date
        );
    }
    
    private String convertToString(Comment comment){
        return comment.getCommentID() + "|" +
               comment.getConfirmationNumber() + "|" +
               comment.getRoomNumber() + "|" +
               comment.getType() + "|" +
               comment.getDescription() + "|" +
               comment.getStatus() + "|" +
               comment.getDate();
    }
}
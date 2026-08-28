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
public class Comment {
    
    private String commentID;
    private String confirmationNumber;
    private int roomNumber;
    private CommentType type;
    private String description;
    private CommentStatus status;
    private LocalDate date;
    
    public Comment() {}
    
    public Comment(String commentID, String confirmationNumber, int roomNumber,
                   CommentType type, String description, CommentStatus status, LocalDate date){
        this.commentID = commentID;
        this.confirmationNumber = confirmationNumber;
        this.roomNumber = roomNumber;
        this.type = type;
        this.description = description;
        this.status = status;
        this.date = date;
    }
    
    public void setCommentID(String commentID){
        this.commentID = commentID;
    }
    
    public String getCommentID(){
        return commentID;
    }
    
    public void setConfirmationNumber(String confirmationNumber){
        this.confirmationNumber = confirmationNumber;
    }
    
    public String getConfirmationNumber(){
        return confirmationNumber;
    }
    
    public void setRoomNumber(int roomNumber){
        this.roomNumber = roomNumber;
    }
    
    public int getRoomNumber(){
        return roomNumber;
    }
    
    public void setType(CommentType type){
        this.type = type;
    }
    
    public CommentType getType(){
        return type;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setStatus(CommentStatus status){
        this.status = status;
    }
    
    public CommentStatus getStatus(){
        return status;
    }
    
    public void setDate(LocalDate date){
        this.date = date;
    }
    
    public LocalDate getDate(){
        return date;
    }
    
    @Override
    public String toString(){
        return String.format("%-12s %-8s %-12s %-6d %-10s %-10s %s",
                date,
                commentID,
                confirmationNumber,
                roomNumber,
                type,
                status,
                description);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Comment other = (Comment) obj;
        return commentID != null && commentID.equals(other.commentID);
    }
    
    @Override
    public int hashCode() {
        return commentID != null ? commentID.hashCode() : 0;
    }
}

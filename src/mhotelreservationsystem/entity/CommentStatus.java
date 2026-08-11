/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mhotelreservationsystem.entity;

/**
 *
 * @author phoon
 */
public enum CommentStatus {
    PENDING("Pending"),
    RESOLVED("Resolved"),
    IGNORED("Ignored");
    
    private final String displayName;
    
    CommentStatus(String displayName){
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
    
    public static CommentStatus fromDisplayName(String displayName) {
        for (CommentStatus status : CommentStatus.values()) {
            if (status.displayName.equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown CommentStatus: " + displayName);
    }
}

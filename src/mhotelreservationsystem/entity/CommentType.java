/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mhotelreservationsystem.entity;

/**
 *
 * @author phoon
 */
public enum CommentType {
    COMMENT("Comment"),
    COMPLAINT("Complaint");
    
    private final String displayName;
    
    CommentType(String displayName){
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
    
    public static CommentType fromDisplayName(String displayName) {
        for (CommentType type : CommentType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown CommentType: " + displayName);
    }
}

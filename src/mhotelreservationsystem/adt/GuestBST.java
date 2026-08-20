/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.adt;

import java.io.BufferedWriter;
import java.io.IOException;
import mhotelreservationsystem.entity.Guest;
import mhotelreservationsystem.entity.GuestStatus;
/**
 *
 * @author phoon 
 */
//ADT: Binary Search Tree for Guest records
public class GuestBST implements BSTInterface<Guest, String>, GuestBSTInterface {

    private GuestBSTNode root;
    private int size;
    
    public GuestBST() {
        root = null;
        size = 0;
    }

    //Recursive helper to insert a Guest into the sub tree rooted at current     
    private GuestBSTNode insertNode(GuestBSTNode current, Guest guest) {

        if (current == null) {
            size++;
            return new GuestBSTNode(guest);
        }
        int compare = guest.getConfirmationNumber().compareTo(current.getData().getConfirmationNumber());

        if (compare < 0) {
            current.setLeft(insertNode(current.getLeft(), guest));
        } else if (compare > 0) {
            current.setRight(insertNode(current.getRight(), guest));
        }
        return current;
    }

    // Insert guest with returns false if confirmationNumber already exists
    @Override
    public boolean insert(Guest guest) {

        if (search(guest.getConfirmationNumber()) != null) {
            return false;
        }
        root = insertNode(root, guest);
        return true;
    }
    
    // Search by confirmationNumber with returns the Guest or null if not found
    @Override
    public Guest search(String confirmationNumber) {
        return searchNode(root, confirmationNumber);
    }
    
    // Recursive helper to search the sub tree rooted at current
    private Guest searchNode(GuestBSTNode current, String confirmationNumber) {

        if (current == null) {
            return null;
        }

        int compare = confirmationNumber.compareTo(
                current.getData().getConfirmationNumber());

        if (compare == 0) {
            return current.getData();
        } else if (compare < 0) {
            return searchNode(current.getLeft(), confirmationNumber);
        } else {
            return searchNode(current.getRight(), confirmationNumber);
        }
    }

    // Remove a guest by confirmationNumber with returns false if not found
    @Override
    public boolean remove(String confirmationNumber) {

        if (search(confirmationNumber) == null) {
            return false;
        }

        root = removeNode(root, confirmationNumber);
        size--;
        return true;

    }
    
    /**
     * Recursive helper to remove a node from the sub tree rooted at current
     * Handles 4 situation:
     *   Situation 1: No child (leaf)       -> it will return null
     *   Situation 2: Only left child       -> it will replace with left sub tree
     *   Situation 3: Only right child      -> it will replace with right sub tree
     *   Situation 4: Two children          -> it will copy in-order successor and delete successor
     */
    private GuestBSTNode removeNode(GuestBSTNode current,
                                    String confirmationNumber) {

        if (current == null) {
            return null;
        }

        int compare = confirmationNumber.compareTo(current.getData().getConfirmationNumber());

        if (compare < 0) {
            current.setLeft(removeNode(current.getLeft(), confirmationNumber));
        } else if (compare > 0) {
            current.setRight(removeNode(current.getRight(), confirmationNumber));
        } else {
            // Situation 1
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            }
            
            // Situation 2
            if (current.getRight() == null) {
                return current.getLeft();
            }

            // Situation 3
            if (current.getLeft() == null) {
                return current.getRight();
            }
            
            // Situation 4
            Guest successor = findMin(current.getRight());

            current.setData(successor);
            current.setRight(removeNode(
                    current.getRight(),
                    successor.getConfirmationNumber()));
        }
        return current;
    }
    
    // Find the node with the smallest confirmationNumber in the subtree
    private Guest findMin(GuestBSTNode current) {
        
        while (current.getLeft() != null) {
            current = current.getLeft();
        }

        return current.getData();
    }
    
    // Print all guests in ascending order of confirmationNumber (in-order traversal)
    @Override
    public void inorderTraversal() {
        
        inorder(root);
    }
    
    
    private void inorder(GuestBSTNode current){
    
        if (current == null){
            return;
        }
        
        inorder(current.getLeft());
        System.out.println(current.getData());
        inorder(current.getRight());
    }
    
    @Override
    public void clear() {
        root = null;
        size = 0;
    }
    
    @Override
    public boolean isEmpty() {
        return root == null;
    }
    
    @Override
    public int getSize(){
        return size;
    }
    
    public GuestBSTNode getRoot(){
        return root;
    }
    
    // Save all guests to file in sorted order using in-order traversal
    @Override
    public void saveToFile(BufferedWriter writer) throws IOException {

        saveNode(root, writer);

    }

    private void saveNode(GuestBSTNode current,
                          BufferedWriter writer) throws IOException {

        if (current == null) {
            return;
        }

        saveNode(current.getLeft(), writer);

        Guest guest = current.getData();

        writer.write(
                guest.getConfirmationNumber() + "|" +
                guest.getGuestName() + "|" +
                guest.getPhoneNumber() + "|" +
                guest.getEmail() + "|" +
                guest.getBookingID() + "|" +
                guest.getRoomNumber() + "|" +
                guest.getCheckInDate() + "|" +
                guest.getCheckOutDate() + "|" +
                guest.getStatus()
        );

        writer.newLine();

        saveNode(current.getRight(), writer);

    }
    
    // Display and count all guests matching the given status (in-order with filter)
    @Override
    public int displayGuestByStatus(GuestStatus status){
        return displayGuestByStatus(root,status);
    }
    
    private int displayGuestByStatus(GuestBSTNode current, GuestStatus status){
        
        if(current == null){
            return 0;
        }
        
        int count = 0;
        count += displayGuestByStatus(current.getLeft(), status);
        
        if(current.getData().getStatus() == status){
            System.out.println(current.getData());
            count++;
        }
        count += displayGuestByStatus(current.getRight(), status);
        return count;
    }
}
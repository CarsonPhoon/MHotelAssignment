/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mhotelreservationsystem.adt;


import mhotelreservationsystem.entity.Guest;
/**
 *
 * @author phoon jiale
 */
public class GuestBST implements GuestBSTInterface {

    private GuestBSTNode root;
    private int size;
    
    public GuestBST() {
        root = null;
        size = 0;
    }

    private GuestBSTNode insertNode(GuestBSTNode current, Guest guest) {

        if (current == null) {
            size++;
            return new GuestBSTNode(guest);
        }
        int compare = guest.getConfirmationNumber().compareTo(current.getData().getConfirmationNumber());

        if (compare < 0) {
            current.setLeft(insertNode(current.getLeft(), guest));
        } else {
            current.setRight(insertNode(current.getRight(), guest));
        }

        return current;
    }

    @Override
    public boolean insert(Guest guest) {

        if (search(guest.getConfirmationNumber()) != null) {
            return false;
        }
        root = insertNode(root, guest);
        return true;
    }
    
    @Override
    public Guest search(String confirmationNumber) {
        return searchNode(root, confirmationNumber);
    }
    
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

    @Override
    public boolean remove(String confirmationNumber) {

        if (search(confirmationNumber) == null) {
            return false;
        }

        root = removeNode(root, confirmationNumber);
        size--;
        return true;

    }
    
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
            // Case 1：No child
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            }

            // Case 2：Only right child
            if (current.getLeft() == null) {
                return current.getRight();
            }

            // Case 3：Only left child
            if (current.getRight() == null) {
                return current.getLeft();
            }

            // Case 4：Two children
            Guest successor = findMin(current.getRight());

            current.setData(successor);
            current.setRight(removeNode(
                    current.getRight(),
                    successor.getConfirmationNumber()));
        }
        return current;
    }
    
    private Guest findMin(GuestBSTNode current) {
        
        while (current.getLeft() != null) {
            current = current.getLeft();
        }

        return current.getData();
    }
    
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
}
/*
 * GuestBSTNode - Adapted from course materials (TARUMT DSA)
 */
package mhotelreservationsystem.adt;

import mhotelreservationsystem.entity.Guest;
/**
 *
 * @author phoon
 */


public class GuestBSTNode {

    private Guest data;
    private GuestBSTNode left;
    private GuestBSTNode right;

    public GuestBSTNode(Guest data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
    
    public void setData(Guest data) {
        this.data = data;
    }

    public Guest getData() {
        return data;
    }
    
    public void setLeft(GuestBSTNode left) {
        this.left = left;
    }

    public GuestBSTNode getLeft() {
        return left;
    }

    public void setRight(GuestBSTNode right) {
        this.right = right;
    }

    public GuestBSTNode getRight() {
        return right;
    }
}
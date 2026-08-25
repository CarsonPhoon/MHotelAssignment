/*
 * VipBSTNode - Adapted from course materials (TARUMT DSA)
 */
package mhotelreservationsystem.adt;

import mhotelreservationsystem.entity.Member;

/**
 * 
 * @author zekai
 */
public class VipBSTNode {
    private Member data;
    private VipBSTNode left;
    private VipBSTNode right;

    public VipBSTNode(Member data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public Member getData() {
        return data;
    }

    public void setData(Member data) {
        this.data = data;
    }

    public VipBSTNode getLeft() {
        return left;
    }

    public void setLeft(VipBSTNode left) {
        this.left = left;
    }

    public VipBSTNode getRight() {
        return right;
    }

    public void setRight(VipBSTNode right) {
        this.right = right;
    }
}
/*
 * VipBST - Adapted from course materials (TARUMT DSA)
 */
package mhotelreservationsystem.adt;

import mhotelreservationsystem.entity.Member;

/**
 * 
 * @author zekai
 */
public class VipBST implements PriorityQueueInterface<mhotelreservationsystem.entity.Member> {

    private VipBSTNode root;
    private int size;
    
    public VipBST() {
        root = null;
        size = 0;
    }

   
    public boolean insert(Member member) {
        if (member == null || member.getMemberLevel() == null) {
            return false;
        }
        root = insertNode(root, member);
        return true;
    }

    private VipBSTNode insertNode(VipBSTNode currentNode, mhotelreservationsystem.entity.Member newMember) {
        if (currentNode == null) {
            this.size++;
            return new VipBSTNode(newMember);
        }

        int levelCompare = newMember.getMemberLevel().compareTo(currentNode.getData().getMemberLevel());

        if (levelCompare > 0) {
            currentNode.setRight(insertNode(currentNode.getRight(), newMember));
        } 
        else if (levelCompare < 0) {
            currentNode.setLeft(insertNode(currentNode.getLeft(), newMember));
        } 
        else {
            int pointsCompare = Integer.compare(newMember.getRewardPoints(), currentNode.getData().getRewardPoints());
            
            if (pointsCompare > 0) {
                currentNode.setRight(insertNode(currentNode.getRight(), newMember));
            } 
            else if (pointsCompare < 0) {
                currentNode.setLeft(insertNode(currentNode.getLeft(), newMember));
            } 
            else {
                currentNode.setLeft(insertNode(currentNode.getLeft(), newMember));
            }
        }

        return currentNode;
    }


    public Member getHighestPriorityVip() {
        if (root == null) {
            return null;
        }

        VipBSTNode parent = null;
        VipBSTNode current = root;

        while (current.getRight() != null) {
            parent = current;
            current = current.getRight();
        }

        Member highestVip = current.getData();

        if (parent == null) {
            root = current.getLeft();
        } else {
            parent.setRight(current.getLeft());
        }

        size--;
        return highestVip;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getSize() {
        return size;
    }

    public void clear() {
        root = null;
        size = 0;
    }


    public void displayAll() {
        if (root == null) {
            System.out.println("The VIP waiting queue is currently empty.");
            return;
        }
        System.out.println(String.format("%-8s %-10s %-10s %-8s %-12s %-10s", 
                "MemberID", "Confirm#", "Level", "Points", "JoinDate", "Status"));
        System.out.println("------------------------------------------------------------------");
        displayReverseInOrder(root);
    }

    private void displayReverseInOrder(VipBSTNode current) {
        if (current == null) {
            return;
        }

        displayReverseInOrder(current.getRight()); 

        System.out.println(current.getData().toString());
        
        displayReverseInOrder(current.getLeft());
    }

    public mhotelreservationsystem.entity.Member searchByConfirmationNumber(String confirmNum) {
        return searchRecursive(this.root, confirmNum);
    }

    private mhotelreservationsystem.entity.Member searchRecursive(VipBSTNode node, String confirmNum) {
        if (node == null) {
            return null;
        }

        if (node.getData().getConfirmationNumber().equalsIgnoreCase(confirmNum)) {
            return node.getData();
        }

        mhotelreservationsystem.entity.Member foundInLeft = searchRecursive(node.getLeft(), confirmNum);
        
        if (foundInLeft != null) {
            return foundInLeft; 
        }

        return searchRecursive(node.getRight(), confirmNum);
    }

    public int getCountByLevel(mhotelreservationsystem.entity.MemberLevel level) {
        return countByLevelRecursive(root, level);
    }

    private int countByLevelRecursive(VipBSTNode current, mhotelreservationsystem.entity.MemberLevel level) {
        if (current == null) return 0;
        int count = (current.getData().getMemberLevel() == level) ? 1 : 0;
        return count + countByLevelRecursive(current.getLeft(), level) + countByLevelRecursive(current.getRight(), level);
    }

    public String getHighValueVipsData(int minPoints) {
        StringBuilder sb = new StringBuilder();
        getHighValueDataRecursive(root, minPoints, sb);
        return sb.toString();
    }

    private void getHighValueDataRecursive(VipBSTNode current, int minPoints, StringBuilder sb) {
        if (current == null) return;
        
        getHighValueDataRecursive(current.getRight(), minPoints, sb); 
        
        if (current.getData().getRewardPoints() > minPoints) {
            Member m = current.getData();
            sb.append(String.format("%-10s %-10s %-12s %-8s\n", 
                m.getMemberID(), m.getConfirmationNumber(), m.getMemberLevel(), m.getRewardPoints()));
        }
        
        getHighValueDataRecursive(current.getLeft(), minPoints, sb);
    }

    public int getHighValueVipsCount(int minPoints) {
        return countHighValueRecursive(root, minPoints);
    }

    private int countHighValueRecursive(VipBSTNode current, int minPoints) {
        if (current == null) return 0;
        int count = (current.getData().getRewardPoints() > minPoints) ? 1 : 0;
        return count + countHighValueRecursive(current.getLeft(), minPoints) + countHighValueRecursive(current.getRight(), minPoints);
    }

    @Override
    public void enqueue(mhotelreservationsystem.entity.Member newEntry) {
        this.insert(newEntry); 
    }

    @Override
    public mhotelreservationsystem.entity.Member dequeue() {
        return this.getHighestPriorityVip(); 
    }

    @Override
    public mhotelreservationsystem.entity.Member peek() {
        return this.getHighestPriorityVip(); 
    }

    @Override
    public int getNumberOfElements() {
        return this.getSize(); 
    }
}
package mhotelreservationsystem.adt;

import mhotelreservationsystem.entity.Member;

/**
 * 
 * @author zekai
 */
public class VipBST {

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

    private VipBSTNode insertNode(VipBSTNode current, Member member) {
        if (current == null) {
            size++;
            return new VipBSTNode(member);
        }

     
        int levelCompare = member.getMemberLevel().compareTo(current.getData().getMemberLevel());

        if (levelCompare < 0) {
            current.setLeft(insertNode(current.getLeft(), member));
        } else if (levelCompare > 0) {
            current.setRight(insertNode(current.getRight(), member));
        } else {
            int idCompare = member.getConfirmationNumber().compareTo(current.getData().getConfirmationNumber());
            
            if (idCompare <= 0) {
                current.setLeft(insertNode(current.getLeft(), member));
            } else {
                current.setRight(insertNode(current.getRight(), member));
            }
        }
        return current;
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

    public Member searchByConfirmationNumber(String confirmNum) {
        return searchRecursive(root, confirmNum);
    }

    private Member searchRecursive(VipBSTNode current, String confirmNum) {
        if (current == null) {
            return null;
        }
        
        if (current.getData().getConfirmationNumber().equalsIgnoreCase(confirmNum)) {
            return current.getData();
        }
        
        Member leftResult = searchRecursive(current.getLeft(), confirmNum);
        if (leftResult != null) {
            return leftResult;
        }
        
        return searchRecursive(current.getRight(), confirmNum);
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
}
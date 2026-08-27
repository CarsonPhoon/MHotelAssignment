package mhotelreservationsystem.control;

import mhotelreservationsystem.adt.LinkedStack;
import mhotelreservationsystem.adt.VipBST; // 🟡 HIGHLIGHT YELLOW: Import Custom ADT
import mhotelreservationsystem.entity.Member;
import mhotelreservationsystem.entity.MemberLevel;
import mhotelreservationsystem.entity.MembershipStatus;
import mhotelreservationsystem.repository.GuestRepository;
import mhotelreservationsystem.repository.MemberRepository;
import mhotelreservationsystem.repository.RoomRepository;

/**
 * Control class for managing VIP Room operations and queue.
 * @author zekai
 */
public class VIPRoomControl {
    
    private VipBST vipQueue; // 🟡 HIGHLIGHT YELLOW (Declaration of ADT)
    private LinkedStack<Member> assignedHistoryStack; // 🟡 HIGHLIGHT YELLOW (Declaration of CUSTOM LinkedStack ADT, solving Issue 3)
    private MemberRepository memberRepository;
    private GuestRepository guestRepository;
    private RoomRepository roomRepository; // 🟡 新增：为了不直接写文件，引入 RoomRepo

    public VIPRoomControl(MemberRepository memberRepo, GuestRepository guestRepo, RoomRepository roomRepo) {
        this.memberRepository = memberRepo;
        this.guestRepository = guestRepo;
        this.roomRepository = roomRepo;
        
        this.vipQueue = new VipBST(); // 🟡 HIGHLIGHT YELLOW (Creation)
        this.assignedHistoryStack = new LinkedStack<>(); // 🟡 HIGHLIGHT YELLOW (Creation of CUSTOM LinkedStack)
        
        loadVipsFromRepository(); 
    }

    private void loadVipsFromRepository() {
        for (int i = 0; i < memberRepository.getTotalMember(); i++) {
            Member member = memberRepository.getMember(i);
            if (member.getMembershipStatus() == MembershipStatus.ACTIVE) {
                // 🟡 呼叫你在 VipBST 里新改的 enqueue 方法 (满足 PriorityQueueInterface)
                vipQueue.enqueue(member); // 🟡 HIGHLIGHT YELLOW
            }
        }
        System.out.println("[System] Successfully loaded VIP data from MemberRepository!");
    }

    public boolean addVipToQueue(Member vipMember) {
        if (vipMember == null) return false;
        
        // 🟡 呼叫你在 VipBST 里新改的 enqueue 方法 (如果你还没把 insert 改名，就暂时写回 insert)
        vipQueue.enqueue(vipMember); // 🟡 HIGHLIGHT YELLOW
        memberRepository.addMember(vipMember);
        return true;
    }

    public boolean verifyGuestExists(String confirmNum) {
        return guestRepository.searchGuest(confirmNum) != null;
    }

    public String generateNextMemberId() {
        int maxId = 0;
        for (int i = 0; i < memberRepository.getTotalMember(); i++) {
            Member m = memberRepository.getMember(i);
            String id = m.getMemberID();
            if (id != null && id.startsWith("MB")) {
                try {
                    int currentId = Integer.parseInt(id.substring(2));
                    if (currentId > maxId) {
                        maxId = currentId;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("MB%04d", maxId + 1);
    }

    public boolean isVipAlreadyRegistered(String confirmNum) {
        return memberRepository.searchByConfirmation(confirmNum) != null;
    }


    public String updateRoomStatus(String roomNumber, mhotelreservationsystem.entity.RoomStatus newStatus) {
        return roomRepository.updateStatus(roomNumber, newStatus);
    }

    public void updateVipPointsInFile(String memberID, int newPoints) {
        memberRepository.updatePoints(memberID, newPoints);
    }

    public Member assignRoomToNextVip() {
        // 🟡 呼叫你在 VipBST 里新改的 dequeue 方法 (满足 PriorityQueueInterface)
        Member assignedVip = vipQueue.dequeue(); // 🟡 HIGHLIGHT YELLOW
        if (assignedVip != null) {
            assignedHistoryStack.push(assignedVip); // 🟡 HIGHLIGHT YELLOW (Invocation of CUSTOM LinkedStack method)
        }
        return assignedVip;
    }

    public void displayAssignedHistory() {
        System.out.println("\n=====================================");
        System.out.println("   Recent VIP Room Allocations (LIFO)");
        System.out.println("=====================================");
        
        if (assignedHistoryStack.isEmpty()) { // 🟡 HIGHLIGHT YELLOW
            System.out.println("No rooms have been assigned yet today.");
            return;
        }
        
        // 由于你的 LinkedStack 可能没有 get(i) 方法，为了显示历史，我们用一个临时栈来倒腾数据
        LinkedStack<Member> tempStack = new LinkedStack<>(); // 🟡 HIGHLIGHT YELLOW
        int count = 1;
        
        // 倒出来打印 (保证 LIFO 后进先出)
        while (!assignedHistoryStack.isEmpty()) {
            Member m = assignedHistoryStack.pop(); // 🟡 HIGHLIGHT YELLOW
            System.out.println((count++) + ". " + m.toString());
            tempStack.push(m); // 🟡 HIGHLIGHT YELLOW
        }
        
        // 装回去恢复原样
        while (!tempStack.isEmpty()) {
            assignedHistoryStack.push(tempStack.pop()); // 🟡 HIGHLIGHT YELLOW
        }
    }

    public String redeemPoints(String confirmNum, int pointsToDeduct) {
        Member vip = vipQueue.searchByConfirmationNumber(confirmNum); // 🟡 HIGHLIGHT YELLOW
        
        if (vip == null) {
            return "NOT_FOUND";
        }
        
        if (vip.getRewardPoints() < pointsToDeduct) {
            return "INSUFFICIENT";
        }
        
        int newPoints = vip.getRewardPoints() - pointsToDeduct;
        vip.setRewardPoints(newPoints);

        // 呼叫改写后的架构标准方法
        updateVipPointsInFile(vip.getMemberID(), newPoints);
        
        return "SUCCESS";
    }

    public void displayAllWaitingVips() {
        vipQueue.displayAll(); // 🟡 HIGHLIGHT YELLOW
    }

    public Member searchVip(String confirmNum) {
        return vipQueue.searchByConfirmationNumber(confirmNum); // 🟡 HIGHLIGHT YELLOW
    }

    public int getVipCountByLevel(MemberLevel level) {
        return vipQueue.getCountByLevel(level); // 🟡 HIGHLIGHT YELLOW
    }

    public int getTotalWaitingCount() {
        return vipQueue.getNumberOfElements(); // 🟡 HIGHLIGHT YELLOW (改成 Interface 里定义的名字)
    }

    public String getHighValueVipsData(int minPoints) {
        return vipQueue.getHighValueVipsData(minPoints); // 🟡 HIGHLIGHT YELLOW
    }

    public int getHighValueVipsCount(int minPoints) {
        return vipQueue.getHighValueVipsCount(minPoints); // 🟡 HIGHLIGHT YELLOW
    }
}
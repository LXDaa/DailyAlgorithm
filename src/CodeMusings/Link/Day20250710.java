package CodeMusings.Link;

public class Day20250710 {
    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 19.删除链表的倒数第 N 个结点
     * https://leetcode.cn/problems/remove-nth-node-from-end-of-list/description/
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode first = dummy;
        ListNode second = dummy;
        for (int i = 0; i <= n; i++) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
    }

    /**
     * 160.链表相交
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表没有交点，返回 null 。
     * https://leetcode.cn/problems/intersection-of-two-linked-lists-lcci/description/
     * @param headA
     * @param headB
     * @return
     *
     *
     *     设「第一个公共节点」为 node ，「链表 headA」的节点数量为 a ，「链表 headB」的节点数量为 b ，「两链表的公共尾部」的节点数量为 c ，则有：
     *
     *     头节点 headA 到 node 前，共有 a−c 个节点；
     *     头节点 headB 到 node 前，共有 b−c 个节点；
     *
     *
     *     考虑构建两个节点指针 A , B 分别指向两链表头节点 headA , headB ，做如下操作：
     *
     *     指针 A 先遍历完链表 headA ，再开始遍历链表 headB ，当走到 node 时，共走步数为：
     *     a+(b−c)
     *     指针 B 先遍历完链表 headB ，再开始遍历链表 headA ，当走到 node 时，共走步数为：
     *     b+(a−c)
     *     如下式所示，此时指针 A , B 重合，并有两种情况：
     *
     *     a+(b−c)=b+(a−c)
     *     若两链表 有 公共尾部 (即 c>0 ) ：指针 A , B 同时指向「第一个公共节点」node 。
     *     若两链表 无 公共尾部 (即 c=0 ) ：指针 A , B 同时指向 null 。
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null){
            return null;
        }
        ListNode a = headA;
        ListNode b = headB;
        while (a != b) {
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }
}

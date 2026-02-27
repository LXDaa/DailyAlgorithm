package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/intersection-of-two-linked-lists/description/">160. 相交链表</a>
 * <p>
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。
 * 如果两个链表不存在相交节点，返回 null 。
 * 题目数据保证整个链式结构中不存在环。
 * <p>
 * 解题思路：
 * 使用双指针技巧，让两个指针分别遍历两个链表，当到达末尾时转向另一个链表的头部继续遍历。
 * 这样两个指针最终会在相交节点相遇，或者同时到达null（表示无相交）。
 * <p>
 * 核心原理：
 * 设链表A的长度为a+c，链表B的长度为b+c（c为公共部分长度）
 * 当两个指针分别走完a+b+c距离后必然相遇
 * <p>
 * 时间复杂度：O(m+n)，其中m和n分别是两个链表的长度
 * 空间复杂度：O(1)，只使用了常数级别的额外空间
 * <p>
 * 示例：
 * 链表A: 4->1->8->4->5
 * 链表B: 5->6->1->8->4->5
 * 相交节点值为8
 * 执行过程：
 * <ol>
 * <li>pA: 4->1->8->4->5->null->5->6->1->8...</li>
 * <li>pB: 5->6->1->8->4->5->null->4->1->8...</li>
 * <li>在节点8处相遇</li>
 * </ol>
 *
 * @author lxd
 **/
public class Day20260227 {
    /**
     * 查找两个链表的相交起始节点
     *
     * @param headA 第一个链表的头节点
     * @param headB 第二个链表的头节点
     * @return 相交的起始节点，如果不存在相交则返回null
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 边界条件：如果任一链表为空，则不可能有相交节点
        if (headA == null || headB == null) {
            return null;
        }

        // 初始化两个指针分别指向两个链表的头节点
        ListNode pA = headA, pB = headB;

        // 当两个指针不相等时继续遍历
        // 关键技巧：当指针到达链表末尾时，转向另一个链表继续遍历
        while (pA != pB) {
            // 如果pA到达链表A末尾，则转向链表B的头部
            // 否则继续向后移动一位
            pA = pA == null ? headB : pA.next;

            // 如果pB到达链表B末尾，则转向链表A的头部
            // 否则继续向后移动一位
            pB = pB == null ? headA : pB.next;
        }

        // 返回相交节点（如果pA==pB==null表示无相交）
        return pA;
    }
}

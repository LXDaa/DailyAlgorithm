package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/reorder-list/description/">143. 重排链表</a>
 *
 * <p>解题思路：</p>
 * <ol>
 * <li>将链表分为前后两部分：使用快慢指针找到中点</li>
 * <li>反转后半部分链表</li>
 * <li>将前半部分和反转后的后半部分交替合并</li>
 * </ol>
 *
 * <p>算法复杂度：</p>
 * <ul>
 * <li>时间复杂度：O(n)，需要遍历链表三次（找中点、反转、合并）</li>
 * <li>空间复杂度：O(1)，只使用常数额外空间</li>
 * </ul>
 *
 * <p>执行示例：</p>
 * <pre>
 * 输入：1->2->3->4->5
 * 步骤1：找到中点3，分割为 1->2->3 和 4->5
 * 步骤2：反转后半部分得到 1->2->3 和 5->4
 * 步骤3：交替合并得到 1->5->2->4->3
 *
 * 输入：1->2->3->4
 * 步骤1：找到中点2，分割为 1->2 和 3->4
 * 步骤2：反转后半部分得到 1->2 和 4->3
 * 步骤3：交替合并得到 1->4->2->3
 * </pre>
 *
 * @author lxd
 **/
public class Day20260213 {
    public void reorderList(ListNode head) {
        // 边界条件：空链表或只有一个节点无需重排
        if (head == null || head.next == null) {
            return;
        }

        // 第一步：使用快慢指针找到链表中点
        // slow指向前后两部分的分界点
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 第二步：断开链表并反转后半部分
        // second指向后半部分的头节点
        ListNode second = reverse(slow.next);
        // 断开前后两部分的连接
        slow.next = null;
        // first指向前半部分的头节点
        ListNode first = head;

        // 第三步：交替合并两个链表
        // second不为null时继续合并（后半部分长度≤前半部分）
        while (second != null) {
            // 保存下一个节点，防止断链
            ListNode firstNext = first.next;
            ListNode secondNext = second.next;

            // 交叉连接：first->second->firstNext
            first.next = second;
            second.next = firstNext;

            // 移动指针到下一位置
            first = firstNext;
            second = secondNext;
        }
    }

    /**
     * 反转链表的标准实现
     *
     * @param head 需要反转的链表头节点
     * @return 反转后的链表头节点
     */
    private ListNode reverse(ListNode head) {
        // pre指向上一个已反转的节点
        ListNode pre = null;
        // cur指向当前待处理的节点
        ListNode cur = head;

        while (cur != null) {
            // 保存下一个节点
            ListNode next = cur.next;
            // 反转当前节点的指针
            cur.next = pre;
            // 更新pre和cur指针
            pre = cur;
            cur = next;
        }

        // pre指向原链表的最后一个节点，即新链表的头节点
        return pre;
    }
}

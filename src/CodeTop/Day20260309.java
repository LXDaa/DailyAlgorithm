package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/remove-nth-node-from-end-of-list/">19. 删除链表的倒数第 N 个节点</a>
 * <p>
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 *
 * <p>
 * <b>解题思路：</b>
 * <p>
 * 本题的关键在于如何找到倒数第 n 个节点。最优解是使用<b>双指针技巧</b>，只需遍历一次链表即可完成任务。
 * <p>
 * <b>核心逻辑：</b>
 * <ol>
 *     <li><b>dummy 节点：</b>由于可能需要删除头节点（当删除倒数第 len 个节点时），使用 dummy 节点可以统一处理所有情况</li>
 *     <li><b>双指针间距：</b>让 fast 指针先走 n+1 步，这样 fast 和 slow 之间保持 n 个节点的间距
 *         <ul>
 *             <li>为什么是 n+1 步？因为我们需要找到<b>倒数第 n+1 个节点</b>（即要删除节点的前一个节点）</li>
 *             <li>当 fast 到达链表末尾时，slow 刚好指向倒数第 n+1 个节点</li>
 *         </ul>
 *     </li>
 *     <li><b>同时移动：</b>fast 和 slow 同时向前移动，直到 fast 为 null，此时 slow 指向要删除节点的前一个节点</li>
 *     <li><b>删除节点：</b>执行 slow.next = slow.next.next 即可删除目标节点</li>
 * </ol>
 * <p>
 * <b>算法步骤：</b>
 * <ol>
 *     <li>创建 dummy 节点指向 head，初始化 fast 和 slow 都指向 dummy</li>
 *     <li>fast 先向前走 n+1 步</li>
 *     <li>fast 和 slow 同时前进，直到 fast 到达链表末尾</li>
 *     <li>此时 slow 指向倒数第 n+1 个节点，修改其 next 指针跳过倒数第 n 个节点</li>
 *     <li>返回 dummy.next 作为新的链表头</li>
 * </ol>
 * <p>
 * <b>时间复杂度：</b>O(n)，其中 n 是链表长度，只需遍历一次链表<br>
 * <b>空间复杂度：</b>O(1)，只使用了常数个额外指针
 *
 * @author lxd
 **/
public class Day20260309 {
    /**
     * 删除链表的倒数第 n 个节点
     *
     * @param head 链表头节点
     * @param n    倒数第 n 个节点（从 1 开始计数）
     * @return 删除节点后的新链表头节点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 创建 dummy 节点指向头节点，防止需要删除头节点的情况
        ListNode dummy = new ListNode(0, head);
        // 初始化快慢指针都指向 dummy
        ListNode fast = dummy;
        ListNode slow = dummy;

        // fast 指针先向前走 n+1 步
        // 这样做的目的是让 fast 和 slow 之间保持 n 个节点的间距
        // 当 fast 到达链表末尾时，slow 刚好指向要删除节点的前一个节点
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // fast 和 slow 同时向前移动，直到 fast 到达链表末尾
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 此时 slow 指向倒数第 n+1 个节点（即要删除节点的前一个节点）
        // 修改 next 指针，跳过倒数第 n 个节点，完成删除操作
        slow.next = slow.next.next;

        // 返回新的链表头节点
        return dummy.next;
    }
}
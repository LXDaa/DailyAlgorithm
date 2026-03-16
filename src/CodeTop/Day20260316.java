package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/sort-list/description/">148. 排序链表</a>
 * <p>
 * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
 *
 * <p><strong>题目分析：</strong></p>
 * <p>要求对链表进行排序，时间复杂度 O(n log n)，空间复杂度 O(log n)</p>
 *
 * <p><strong>解题思路：</strong></p>
 * <ol>
 *     <li><strong>选择归并排序：</strong>链表适合归并排序，因为不需要额外数组空间，且时间复杂度稳定 O(n log n)</li>
 *     <li><strong>分治法核心：</strong>
 *         <ol>
 *             <li>使用快慢指针找到链表中点，将链表断开</li>
 *             <li>递归地对左右两部分分别排序</li>
 *             <li>合并两个有序链表</li>
 *         </ol>
 *     </li>
 *     <li><strong>快慢指针找中点：</strong>slow 每次走一步，fast 每次走两步，当 fast 到达末尾时，slow 正好在中点</li>
 *     <li><strong>合并有序链表：</strong>使用双指针比较两个链表的节点值，依次连接较小的节点</li>
 * </ol>
 *
 * <p><strong>复杂度分析：</strong></p>
 * <ul>
 *     <li>时间复杂度：O(n log n)，归并排序的时间复杂度</li>
 *     <li>空间复杂度：O(log n)，递归调用栈的深度</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260316 {
    /**
     * 对链表进行归并排序
     *
     * <p><strong>实现步骤：</strong></p>
     * <ol>
     *     <li>边界条件处理：空链表或只有一个节点时直接返回</li>
     *     <li>使用快慢指针找到链表中点，将链表分为两部分</li>
     *     <li>递归排序左半部分和右半部分</li>
     *     <li>合并两个有序链表</li>
     * </ol>
     *
     * @param head 链表头节点
     * @return 排序后的链表头节点
     */
    public ListNode sortList(ListNode head) {
        // 边界条件：空链表或只有一个节点时，已经是有序的
        if (head == null || head.next == null) {
            return head;
        }

        // 使用快慢指针找到链表中点
        // slow 初始化为 head，fast 初始化为 head.next
        // 这样当链表长度为偶数时，slow 会停在前半部分的最后一个节点
        ListNode slow = head;
        ListNode fast = head.next;

        // 快指针每次走两步，慢指针每次走一步
        // 当快指针到达末尾时，慢指针正好在中间位置
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // second 指向右半部分的起始节点
        ListNode second = slow.next;

        // 断开链表，将链表分为两部分
        slow.next = null;

        // first 指向左半部分的起始节点
        ListNode first = head;

        // 递归排序左半部分
        first = sortList(first);

        // 递归排序右半部分
        second = sortList(second);

        // 合并两个有序链表
        return mergeList(first, second);
    }

    /**
     * 合并两个有序链表
     *
     * <p><strong>实现步骤：</strong></p>
     * <ol>
     *     <li>创建虚拟头节点 dummy，简化边界处理</li>
     *     <li>使用 tail 指针追踪合并后链表的尾部</li>
     *     <li>比较两个链表的当前节点，将较小的节点接到 tail 后面</li>
     *     <li>处理剩余节点（其中一个链表遍历完后，另一个链表的剩余部分直接接上）</li>
     * </ol>
     *
     * @param left  第一个有序链表的头节点
     * @param right 第二个有序链表的头节点
     * @return 合并后的有序链表头节点
     */
    private ListNode mergeList(ListNode left, ListNode right) {
        // 创建虚拟头节点，值为 0（任意值都可以，不会被使用）
        // 使用 dummy 可以避免特殊处理头节点
        ListNode dummy = new ListNode(0);

        // tail 指针始终指向合并后链表的最后一个节点
        ListNode tail = dummy;

        // 当两个链表都未遍历完时，比较当前节点的值
        while (left != null && right != null) {
            // 将较小的节点接到合并链表的尾部
            if (left.val < right.val) {
                tail.next = left;
                // left 指针后移
                left = left.next;
            } else {
                tail.next = right;
                // right 指针后移
                right = right.next;
            }
            // tail 指针后移
            tail = tail.next;
        }

        // 处理剩余节点
        // 如果 left 有剩余，直接接到 tail 后面
        // 如果 right 有剩余，直接接到 tail 后面
        // 最多只有一个链表会有剩余节点
        tail.next = left != null ? left : right;

        // 返回合并后的链表头节点（dummy.next 才是真正的头节点）
        return dummy.next;
    }
}

package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/remove-duplicates-from-sorted-list-ii/description/">82. 删除排序链表中的重复元素 II</a>
 * <p>
 * 给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字 。返回 已排序的链表 。
 *
 * <p>
 * <b>解题思路：</b>
 * <p>
 * 本题的关键在于需要删除<b>所有</b>出现重复的节点，而不是保留一个。由于链表已经排序，相同的节点一定是相邻的。
 * <p>
 * <b>核心逻辑：</b>
 * <ol>
 *     <li><b>dummy 节点：</b>由于头节点可能被删除（如果它有重复值），需要使用 dummy 节点指向头节点，方便统一处理</li>
 *     <li><b>双指针遍历：</b>使用 cur 指针作为当前不重复节点的末尾，通过比较 cur.next 和 cur.next.next 的值来判断是否重复</li>
 *     <li><b>删除重复节点：</b>当发现重复时，记录重复值，然后连续删除所有等于该值的节点，直到遇到不同的值或链表末尾</li>
 *     <li><b>移动指针：</b>只有当 cur.next 和 cur.next.next 不重复时，才将 cur 向前移动一位</li>
 * </ol>
 * <p>
 * <b>时间复杂度：</b>O(n)，其中 n 是链表长度，只需遍历一次链表<br>
 * <b>空间复杂度：</b>O(1)，只使用了常数个额外指针
 *
 * @author lxd
 **/
public class Day20260306 {
    /**
     * 删除排序链表中所有重复的节点
     *
     * @param head 链表头节点
     * @return 删除重复节点后的新链表头节点
     */
    public ListNode deleteDuplicates(ListNode head) {
        // 边界情况：空链表直接返回 null
        if (head == null) {
            return null;
        }

        // 创建 dummy 节点指向头节点，防止头节点被删除的情况
        ListNode dummy = new ListNode(0, head);
        // cur 指针指向当前不重复节点的最后一个位置
        ListNode cur = dummy;

        // 当至少还有两个节点时继续遍历
        while (cur.next != null && cur.next.next != null) {
            // 检查相邻的两个节点值是否相同
            if (cur.next.val == cur.next.next.val) {
                // 记录重复的值
                int val = cur.next.val;
                // 循环删除所有值等于 val 的节点
                while (cur.next != null && cur.next.val == val) {
                    cur.next = cur.next.next;
                }
            } else {
                // 没有重复，cur 指针向前移动一位
                cur = cur.next;
            }
        }
        // 返回新的链表头节点（dummy 的下一个节点）
        return dummy.next;
    }
}

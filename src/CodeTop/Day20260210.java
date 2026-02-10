package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/linked-list-cycle/submissions/697799788/">141. 环形链表</a>
 *
 *
 * <p>解题思路：</p>
 * <ol>
 * <li>使用Floyd判圈算法（快慢指针法）检测链表环</li>
 * <li>设置两个指针：快指针每次移动2步，慢指针每次移动1步</li>
 * <li>如果链表有环，快慢指针终将相遇；如果无环，快指针会先到达链表末尾</li>
 * <li>数学原理：在环中，快指针以相对速度1追赶慢指针，必定能追上</li>
 * </ol>
 *
 * <p>算法复杂度：</p>
 * <ul>
 * <li>时间复杂度：O(n)，最坏情况下遍历整个链表</li>
 * <li>空间复杂度：O(1)，只使用两个指针变量</li>
 * </ul>
 *
 * <p>执行示例：</p>
 * <pre>
 * 示例1：有环链表
 * 3 -> 2 -> 0 -> -4 -> 2(回到索引1)
 * 快指针路径：3->0->2->(-4)->2
 * 慢指针路径：3->2->0->(-4)->2
 * 在节点2处相遇，返回true
 *
 * 示例2：无环链表
 * 1 -> 2 -> null
 * 快指针最终为null，返回false
 * </pre>
 *
 * @author lxd
 **/
public class Day20260210 {
    public boolean hasCycle(ListNode head) {
        // 初始化快慢指针，都指向头节点
        ListNode fast = head;
        ListNode slow = head;

        // 循环条件：确保快指针及其下一个节点都不为空
        // 这样才能安全地执行 fast.next.next 操作
        while (fast != null && fast.next != null) {
            // 快指针每次移动两步
            fast = fast.next.next;
            // 慢指针每次移动一步
            slow = slow.next;

            // 如果快慢指针相遇，说明存在环
            // 在环中，快指针必然能追上慢指针
            if (fast == slow) {
                return true;
            }
        }

        // 快指针到达链表末尾，说明无环
        return false;
    }
}

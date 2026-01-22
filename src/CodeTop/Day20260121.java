package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/merge-two-sorted-lists/description/">21. 合并两个有序链表</a>
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * <p>
 * 解题思路：
 * 1. 使用递归方法，每次比较两个链表当前节点的值
 * 2. 选择较小的节点作为结果链表的当前节点
 * 3. 将选中的节点的next指针指向剩余部分的合并结果（递归调用）
 * 4. 当其中一个链表为空时，直接返回另一个链表的剩余部分
 * <p>
 * 算法复杂度：
 * 时间复杂度：O(m+n)，其中m和n分别是两个链表的长度
 * 空间复杂度：O(m+n)，递归调用栈的深度
 * <p>
 * 示例说明：
 * 输入：l1=[1,2,4], l2=[1,3,4]
 * 执行过程：
 * - 比较1(l1)和1(l2)，两者相等，选择l1的节点，然后继续合并l1.next和l2
 * - 比较2(l1)和1(l2)，选择l2的节点，然后继续合并l1和l2.next
 * - 以此类推，直到一个链表为空
 * 输出：[1,1,2,3,4,4]
 *
 *     mergeTwoLists([1,2,4], [1,3,4])
 *     ├── 1(来自list2) -> mergeTwoLists([1,2,4], [3,4])
 *         ├── 1(来自list1) -> mergeTwoLists([2,4], [3,4])
 *             ├── 2(来自list1) -> mergeTwoLists([4], [3,4])
 *                 ├── 3(来自list2) -> mergeTwoLists([4], [4])
 *                     ├── 4(来自list2) -> mergeTwoLists([4], null)
 *                         └── 返回 [4]
 *                     └── 返回 [4,4]
 *                 └── 返回 [3,4,4]
 *             └── 返回 [2,3,4,4]
 *         └── 返回 [1,2,3,4,4]
 *     └── 返回 [1,1,2,3,4,4]
 *
 * @author lxd
 **/
public class Day20260121 {
    /**
     * 合并两个有序链表
     *
     * @param list1 第一个有序链表
     * @param list2 第二个有序链表
     * @return 合并后的有序链表
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 如果list1为空，则直接返回list2（包含其所有后续节点）
        if (list1 == null) {
            return list2;
        }
        // 如果list2为空，则直接返回list1（包含其所有后续节点）
        if (list2 == null) {
            return list1;
        }
        // 比较两个链表当前节点的值
        if (list1.val < list2.val) {
            // 如果list1当前节点值更小，则将list1的下一个节点与list2合并
            // 这样可以确保结果链表仍保持升序
            list1.next = mergeTwoLists(list1.next, list2);
            // 返回list1作为当前节点
            return list1;

        } else {
            // 如果list2当前节点值更小或相等，则将list1与list2的下一个节点合并
            list2.next = mergeTwoLists(list1, list2.next);
            // 返回list2作为当前节点
            return list2;
        }
    }

    public class ListNode {
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
}

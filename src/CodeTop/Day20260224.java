package CodeTop;

/**
 *
 * <a href="https://leetcode.cn/problems/merge-k-sorted-lists/description/">23. 合并 K 个升序链表</a>
 * <p>
 * <p>题目描述：
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 *
 * <p>解题思路：
 * 使用分治法（Divide and Conquer）来解决这个问题：
 * <ol>
 *   <li>将K个链表分成两部分，递归地合并每一部分</li>
 *   <li>然后将两个已排序的链表合并成一个</li>
 *   <li>这类似于归并排序的思想</li>
 * </ol>
 *
 * <p>时间复杂度分析：
 * <ul>
 *   <li>假设总共有N个节点，K个链表</li>
 *   <li>每次合并两个链表的时间复杂度是O(N)</li>
 *   <li>分治过程需要logK层递归</li>
 *   <li>总体时间复杂度：O(N * logK)</li>
 * </ul>
 *
 * <p>空间复杂度：O(logK) - 递归调用栈的深度
 *
 * <p>示例：
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 *
 * @author lxd
 **/
public class Day20260224 {
    /**
     * 主函数：合并K个升序链表的入口方法
     *
     * @param lists 链表数组，每个元素都是一个升序链表的头节点
     * @return 合并后的升序链表的头节点
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // 边界条件检查：如果数组为空或长度为0，直接返回null
        if (lists == null || lists.length == 0) {
            return null;
        }

        // 调用分治合并函数，从索引0到length-1进行合并
        return merge(lists, 0, lists.length - 1);
    }

    /**
     * 分治合并函数：递归地将链表数组中的链表两两合并
     *
     * @param lists 链表数组
     * @param l     左边界索引
     * @param r     右边界索引
     * @return 合并后链表的头节点
     */
    private ListNode merge(ListNode[] lists, int l, int r) {
        // 基础情况1：当左右边界相等时，只有一个链表，直接返回
        if (l == r) {
            return lists[l];
        }

        // 基础情况2：当左边界大于右边界时，表示无效区间，返回null
        if (l > r) {
            return null;
        }

        // 计算中间位置，使用位运算>>1相当于除以2，效率更高
        int mid = (l + r) >> 1;

        // 递归合并：
        // 1. 合并左半部分 [l, mid]
        // 2. 合并右半部分 [mid+1, r]
        // 3. 将两个合并结果再进行合并
        return mergeTwoLists(
                merge(lists, l, mid),      // 递归合并左半部分
                merge(lists, mid + 1, r)   // 递归合并右半部分
        );
    }

    /**
     * 合并两个升序链表的核心函数
     * 使用双指针技术，类似归并排序中的合并过程
     *
     * @param a 第一个升序链表
     * @param b 第二个升序链表
     * @return 合并后的升序链表的头节点
     */
    private ListNode mergeTwoLists(ListNode a, ListNode b) {
        // 处理边界情况：如果其中一个链表为空，直接返回另一个
        if (a == null || b == null) {
            return a != null ? a : b;
        }

        // 创建虚拟头节点，简化链表操作
        // 虚拟头节点的作用是避免处理头节点的特殊情况
        ListNode dummy = new ListNode(0);

        // 初始化三个指针：
        // tail：指向结果链表的当前末尾
        // aPtr：指向链表a的当前位置
        // bPtr：指向链表b的当前位置
        ListNode tail = dummy, aPtr = a, bPtr = b;

        // 双指针遍历两个链表，比较节点值的大小
        while (aPtr != null && bPtr != null) {
            // 比较两个链表当前节点的值
            if (aPtr.val < bPtr.val) {
                // a链表当前节点值较小，将其连接到结果链表
                tail.next = aPtr;
                aPtr = aPtr.next;  // 移动a链表指针
            } else {
                // b链表当前节点值较小或相等，将其连接到结果链表
                tail.next = bPtr;
                bPtr = bPtr.next;  // 移动b链表指针
            }
            // 移动结果链表的尾指针
            tail = tail.next;
        }

        // 处理剩余节点：
        // 循环结束后，至少有一个链表已经遍历完
        // 将另一个链表的剩余部分直接连接到结果链表末尾
        tail.next = (aPtr != null ? aPtr : bPtr);

        // 返回真正的头节点（跳过虚拟头节点）
        return dummy.next;
    }
}

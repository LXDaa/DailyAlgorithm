package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/reverse-linked-list-ii/description/">92. 反转链表 II</a>
 * <p>
 * 给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链表节点，返回 反转后的链表 。
 * <p>
 * 解题思路：<br>
 * 1. 使用哑结点(dummy node)处理边界情况，如从头节点开始反转<br>
 * 2. 找到反转区间的前一个节点p0，即第left-1个节点<br>
 * 3. 使用经典的链表反转算法，仅对[left, right]区间内的节点进行反转<br>
 * 4. 将反转后的子链表重新连接到原链表中<br>
 * <p>
 * 算法步骤：<br>
 * <ol>
 * <li>创建哑结点并连接到原链表头部，避免对头节点特殊处理</li>
 * <li>遍历到left-1位置，找到反转区间的前一个节点p0</li>
 * <li>对[left, right]区间内的节点执行反转操作，使用三指针法(prev, curr, next_temp)</li>
 * <li>将反转后的子链表重新连接：原前部分 -> 反转后子链表 -> 原后部分</li>
 * </ol>
 * <p>
 * 示例：<br>
 * 输入：head = [1,2,3,4,5], left = 2, right = 4<br>
 * 原链表：1->2->3->4->5<br>
 * 反转区间[2,4]，即节点2,3,4<br>
 * 输出：1->4->3->2->5<br>
 * <p>
 * 时间复杂度：O(n)，其中n是链表长度，最多遍历整个链表<br>
 * 空间复杂度：O(1)，只使用了常数额外空间<br>
 *
 * @author lxd
 **/
public class Day20260206 {
    /**
     * 反转链表指定区间
     *
     * @param head  原链表的头节点
     * @param left  需要反转的起始位置(从1开始计数)
     * @param right 需要反转的结束位置(从1开始计数)
     * @return 反转后的链表头节点
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 创建哑结点，简化边界处理，特别是在left=1的情况下
        ListNode dummy = new ListNode(0, head);
        // p0指向需要反转区间的前一个节点，即第left-1个节点
        ListNode p0 = dummy;
        // 移动到反转区间的前一个位置
        for (int i = 0; i < left - 1; i++) {
            p0 = p0.next;
        }

        // 开始反转[left,right]区间内的节点
        // pre指向已反转部分的头节点(相对于反转子链表而言)
        ListNode pre = null;
        // cur指向当前正在处理的节点，初始为第left个节点
        ListNode cur = p0.next;

        // 反转right-left+1个节点
        for (int i = 0; i < right - left + 1; i++) {
            // 临时保存下一个节点，防止断链后丢失
            ListNode temp = cur.next;
            // 将当前节点的next指向前一个节点，实现反转
            cur.next = pre;
            // pre移到当前节点
            pre = cur;
            // cur移到下一个待处理节点
            cur = temp;
        }

        // 此时的状态：
        // pre指向原[left,right]区间反转后的头节点(也是反转子链表的尾节点)
        // cur指向原[right+1,n]区间的头节点
        // p0指向原[1,left-1]区间的尾节点

        /*
         * 示例说明（以 head=[1,2,3,4,5], left=2, right=4 为例）:
         *
         * 初始状态: 1 -> 2 -> 3 -> 4 -> 5 -> NULL
         *           ^    ^              ^    ^
         *          dum  p0             pre  cur
         *
         * 反转后状态: 1 -> 4 -> 3 -> 2 -> 5 -> NULL
         *           ^    ^              ^    ^
         *          dum  p0            pre  cur
         *
         * 具体过程:
         * 原链表: [1] -> [2] -> [3] -> [4] -> [5]
         *         p0
         *               <- [2] <- [3] <- [4] - pre
         *                                    cur
         *
         * 最终连接: p0.next = pre (将1连到4)
         *          p0.next.next = cur (将2连到5)
         */

        /*
         * 重新连接链表：
         * 1. 将原前部分的尾节点(p0)连接到反转子链表的头节点(pre)
         * 2. 将反转子链表的尾节点(p0.next，因为此时p0.next仍指向原left位置节点)连接到原后部分的头节点(cur)
         *
         * 注意：这两步操作顺序不能交换，因为：
         * - 如果先执行 p0.next = pre，那么 p0.next 就变成了反转后区域的头部节点
         * - 再执行 p0.next.next = cur 时，实际上是在修改反转后区域头部节点的 next 的 next，
         *   这会导致错误的链接关系并可能丢失对原反转区域尾节点的引用
         *
         * 示例说明（以 head=[1,2,3,4,5], left=2, right=4 为例）:
         * 原链表: [1] -> [2] -> [3] -> [4] -> [5]
         *         p0   <-rev_part(2,3,4)->  cur
         *
         * 正确顺序:
         * 1. p0.next.next = cur (让节点2的next指向节点5，完成反转区域尾部连接)
         * 2. p0.next = pre (让节点1的next指向节点4，完成反转区域头部连接)
         *
         * 最终结果: [1] -> [4] -> [3] -> [2] -> [5]
         */

        // 将原反转区间的尾节点连接到后续节点
        p0.next.next = cur;
        // 将前置部分的尾节点连接到反转区间的头节点
        p0.next = pre;

        // 返回真正的头节点
        return dummy.next;
    }
}

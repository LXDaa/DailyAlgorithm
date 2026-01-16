package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/reverse-nodes-in-k-group/">25. K个一组翻转链表</a>
 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 * <p>
 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * <p>
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 *
 * @author lxd
 **/
public class Day20260115 {
    /**
     * 定义链表节点结构
     */
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

    class Solution {
        /**
         * K个一组翻转链表的主要方法
         * <p>
         * 解题思路：
         * 1. 使用哑结点(dummy node)简化边界情况处理
         * 2. 将链表按k个节点为一组进行分割
         * 3. 对每一组进行局部翻转操作
         * 4. 将翻转后的组重新连接到原链表中
         * 5. 如果最后一组不足k个节点，则保持原有顺序不翻转
         * <p>
         * 示例：对于链表 1->2->3->4->5, k=2
         * 原始: 1->2->3->4->5
         * 第1组(1,2): 2->1->3->4->5
         * 第2组(3,4): 2->1->4->3->5
         * 最后组(5): 保持不变，最终结果 2->1->4->3->5
         *
         * @param head 链表的头节点
         * @param k    每组翻转的节点数量
         * @return 修改后的链表头节点
         */
        public ListNode reverseKGroup(ListNode head, int k) {
            // 创建哑结点，简化边界处理，dummy.next始终指向原链表头部
            ListNode dummy = new ListNode(0, head);
            // groupPrev记录当前处理组的前一个节点，初始为哑结点
            ListNode groupPrev = dummy;

            while (true) {
                // 步骤1：检查当前是否还有k个节点可以翻转
                // 从groupPrev开始向后移动k步，看能否找到k个连续节点
                ListNode cur = groupPrev;
                for (int i = 0; i < k && cur != null; i++) {
                    cur = cur.next;
                }

                // 如果找不到k个节点（cur为null），说明剩余节点不足k个，结束循环
                if (cur == null)
                    break;

                // 步骤2：确定当前要翻转的分组边界
                // groupNext是下一组的起始节点，翻转后的组尾需要连接到这里
                ListNode groupNext = cur.next;
                // groupStart是当前组的起始节点，翻转后会成为该组的尾节点
                ListNode groupStart = groupPrev.next;

                // 步骤3：翻转当前组内的节点 [groupStart, cur]
                // 使用经典的三指针法翻转链表片段
                // prev初始化为下一组的开始节点，这样翻转后当前组的尾部能正确连接到下一组
                ListNode prev = groupNext;
                // curr指向当前正在处理的节点，从当前组的开始节点开始
                ListNode curr = groupStart;

                // 循环直到处理完当前组的所有节点（到达groupNext停止）
                while (curr != groupNext) {
                    // 临时保存下一个节点，防止断链后丢失
                    ListNode tmp = curr.next;
                    // 翻转当前节点的指针方向
                    curr.next = prev;
                    // 移动prev到当前位置
                    prev = curr;
                    // 移动curr到下一个待处理节点
                    curr = tmp;
                }

                // 步骤4：重新连接翻转后的分组到原链表中
                // 翻转前：groupPrev -> groupStart -> ... -> cur -> groupNext
                // 翻转后：groupPrev -> cur(新头) -> ... -> groupStart(新尾) -> groupNext
                // 所以groupPrev.next应指向翻转后的新头节点（即原组的尾节点cur）
                groupPrev.next = cur;

                // 更新groupPrev为当前组翻转后的尾节点（原组的头节点groupStart）
                // 这样在下一轮循环中，新的分组就能正确连接
                // 注意：翻转后groupStart变成了当前组的尾节点
                groupPrev = groupStart;

                /*
                 * 具体示例：假设链表 1->2->3->4->5->6, k=3
                 * 初始状态: dummy->1->2->3->4->5->6
                 * 第一次翻转前: groupPrev=dummy(0), groupStart=1, cur=3, groupNext=4
                 * 翻转[1,2,3]: 3->2->1
                 * 翻转后: dummy->3->2->1->4->5->6
                 * 连接后: groupPrev.next=cur(3), groupPrev更新为groupStart(1)
                 * 此时groupPrev指向翻转后的尾部(1)，为下次循环做准备
                 */
            }

            // 返回实际的头节点（跳过哑结点）
            return dummy.next;
        }
    }
}
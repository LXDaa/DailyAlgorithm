package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/reverse-linked-list/description/">206. 反转链表</a>
 * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 * <p>
 * 解题思路：
 * 使用双指针迭代法反转链表
 * 1. 定义两个指针pre和cur，pre指向当前节点的前一个节点，初始为null
 * 2. cur指向当前处理的节点，初始为head
 * 3. 遍历链表，每次保存当前节点的下一个节点，然后将当前节点的next指向前一个节点
 * 4. 移动pre和cur指针继续处理下一个节点
 * 5. 当cur为null时，pre即为新的头节点
 * <p>
 * 算法复杂度：时间复杂度O(n)，空间复杂度O(1)
 *
 * @author lxd
 **/
public class Day20260113 {
    /**
     * 反转链表
     *
     * @param head 原链表的头节点
     * @return 反转后链表的头节点
     */
    public ListNode reverseList(ListNode head) {
        // pre指向前一个节点，初始化为null
        ListNode pre = null;
        // cur指向当前处理的节点，初始化为head
        ListNode cur = head;

        // 遍历原链表直到末尾
        while (cur != null) {
            // 临时保存当前节点的下一个节点，防止断链后丢失
            ListNode temp = cur.next;

            // 将当前节点的next指向前一个节点，实现反转
            cur.next = pre;

            // pre和cur指针向后移动
            // pre移动到当前节点
            pre = cur;
            // cur移动到原来的下一个节点
            cur = temp;

        }

        // 循环结束后，pre指向原链表的最后一个节点，即新链表的头节点
        return pre;
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

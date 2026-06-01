package Hot100.链表;

/**
 * @author lxd
 **/
public class Hot024 {
    /**
     * <a href="https://leetcode.cn/problems/palindrome-linked-list/description/">234. 回文链表</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。</p>
     *
     * <h3>💡 核心思路：快慢指针 + 反转后半部分</h3>
     * <ul>
     *   <li><b>基本思想</b>：找到链表中点，反转后半部分，然后比较前后两部分是否相同
     *     <ul>
     *       <li>使用快慢指针找到链表中点（slow 每次走1步，fast 每次走2步）</li>
     *       <li>将后半部分链表反转</li>
     *       <li>从头节点和反转后的后半部分头节点开始，逐个比较节点值</li>
     *       <li>如果所有节点值都相同，则是回文链表</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，找中点 O(n/2) + 反转 O(n/2) + 比较 O(n/2)</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用了常数个额外指针</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例1：head = [1, 2, 2, 1]（回文）
     *
     * 【步骤1】找中点（快慢指针）
     *   初始：slow = 1, fast = 2
     *   第1轮：slow = 2, fast = null（到达末尾）
     *
     *   链表：1 → 2 → 2 → 1
     *              ↑
     *            slow（中点）
     *
     * 【步骤2】分割链表
     *   first = 1 → 2 → null
     *   second = 2 → 1 → null
     *
     * 【步骤3】反转后半部分
     *   second 反转后：1 → 2 → null
     *
     * 【步骤4】比较前后两部分
     *   first:  1 → 2
     *   second: 1 → 2
     *   逐个比较：1==1 ✅, 2==2 ✅
     *   结果：true（是回文链表）✅
     *
     * ---
     *
     * 示例2：head = [1, 2, 3, 4]（非回文）
     *
     * 【步骤1】找中点
     *   初始：slow = 1, fast = 2
     *   第1轮：slow = 2, fast = null
     *
     *   链表：1 → 2 → 3 → 4
     *              ↑
     *            slow（中点）
     *
     * 【步骤2】分割链表
     *   first = 1 → 2 → null
     *   second = 3 → 4 → null
     *
     * 【步骤3】反转后半部分
     *   second 反转后：4 → 3 → null
     *
     * 【步骤4】比较前后两部分
     *   first:  1 → 2
     *   second: 4 → 3
     *   逐个比较：1!=4 ❌
     *   结果：false（不是回文链表）❌
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用快慢指针找中点？
     *    - 快指针速度是慢指针的2倍
     *    - 当快指针到达末尾时，慢指针刚好在中点
     *    - 只需遍历一次链表，时间复杂度 O(n)
     *
     * 2️⃣ 为什么要反转后半部分？
     *    - 链表只能单向遍历，无法从尾部向前比较
     *    - 反转后半部分后，可以从两个方向同时向中间比较
     *    - 这样就能判断是否回文
     *
     * 3️⃣ 奇数长度链表如何处理？
     *    - 例如 [1, 2, 3, 2, 1]
     *    - slow 停在中间节点 3
     *    - second 从 3.next = 2 开始
     *    - 中间节点不影响回文判断
     *
     * 4️⃣ 为什么只比较 second 不为空？
     *    - second 的长度 <= first 的长度
     *    - 当 second 遍历完时，已经比较了所有需要比较的节点
     *    - 如果 first 还有剩余，说明是奇数长度，中间节点无需比较
     *
     * 5️⃣ 边界情况处理？
     *    - 空链表或单节点：直接返回 true
     *    - 两个节点：直接比较即可
     *    - 算法能正确处理所有情况
     *
     * 6️⃣ 与数组方法对比？
     *    - 数组：复制到数组，双指针比较，空间 O(n)
     *    - 当前方法：空间 O(1) ✅ 更优
     *    - 但会修改原链表结构（如果需要恢复，可以再次反转后半部分）
     *
     * 7️⃣ 完整流程图示：
     *    原始：    1 → 2 → 2 → 1
     *
     *    找中点：  1 → 2 | 2 → 1
     *                     ↑
     *                   slow
     *
     *    反转后半：1 → 2 | 1 → 2
     *
     *    比较：    1==1 ✅, 2==2 ✅
     *    结果：回文 ✅
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>边界检查</b>：空链表或单节点链表直接返回 true</li>
     *   <li><b>快慢指针初始化</b>：fast 从 head.next 开始，确保正确找到中点</li>
     *   <li><b>链表分割</b>：需要将 slow.next 设为 null，断开前后两部分</li>
     *   <li><b>比较长度</b>：只需要比较到 second 为空即可</li>
     *   <li><b>原链表修改</b>：该方法会修改原链表结构</li>
     * </ul>
     *
     * @param head 链表的头节点
     * @return 如果是回文链表返回 true，否则返回 false
     */
    public boolean isPalindrome(ListNode head) {
        // 🔍 边界检查：空链表或单节点链表是回文
        if (head == null || head.next == null) {
            return true;
        }

        // 🏃 快慢指针找中点
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;       // 慢指针每次走1步
            fast = fast.next.next;  // 快指针每次走2步
        }

        // ✂️ 分割链表：first 和 second 两部分
        ListNode second = slow.next;
        slow.next = null;  // 断开前后两部分
        ListNode first = head;

        // 🔄 反转后半部分链表
        second = reverse(second);

        // 🔍 比较前后两部分是否相同
        while (second != null) {
            if (first.val != second.val) {
                return false;  // 有任何一个节点不同，不是回文
            }
            first = first.next;
            second = second.next;
        }

        // ✅ 所有节点都相同，是回文链表
        return true;
    }

    /**
     * 反转链表（辅助方法）
     *
     * @param node 需要反转的链表头节点
     * @return 反转后的链表头节点
     */
    private ListNode reverse(ListNode node) {
        ListNode pre = null;
        ListNode cur = node;
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }
}

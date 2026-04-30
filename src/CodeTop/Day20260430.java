package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/palindrome-linked-list/description/">234. 回文链表</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>给你一个单链表的头节点 head，请你判断该链表是否为回文链表。</p>
 * <p>如果是，返回 true；否则，返回 false。</p>
 * <p><b>回文链表</b>：正读和反读都相同的链表，例如 1→2→2→1 或 1→2→3→2→1。</p>
 *
 * <h3>💡 核心思路：快慢指针 + 反转链表</h3>
 * <ul>
 *   <li><b>问题挑战</b>：链表只能单向遍历，无法像数组那样双指针从两端向中间</li>
 *   <li><b>解决方案</b>：
 *     <ol>
 *       <li>用快慢指针找到链表中点</li>
 *       <li>反转后半部分链表</li>
 *       <li>比较前半部分和反转后的后半部分</li>
 *     </ol>
 *   </li>
 *   <li><b>时间复杂度</b>：O(n)，每个步骤都是线性扫描</li>
 *   <li><b>空间复杂度</b>：O(1)，只使用了常数个指针变量</li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>边界处理</b>：空链表或单节点链表，直接返回 true</li>
 *   <li><b>找中点</b>：使用快慢指针
 *     <ul>
 *       <li>slow 每次走一步，fast 每次走两步</li>
 *       <li>当 fast 到达末尾时，slow 刚好在中点</li>
 *       <li>偶数长度：slow 指向后半段的第一个节点</li>
 *       <li>奇数长度：slow 指向中间节点的下一个节点</li>
 *     </ul>
 *   </li>
 *   <li><b>反转后半部分</b>：从 slow 开始反转链表</li>
 *   <li><b>双指针比较</b>：
 *     <ul>
 *       <li>firstHalf 从头节点开始</li>
 *       <li>secondHalf 从反转后的后半部分头节点开始</li>
 *       <li>逐个比较节点值，发现不同则返回 false</li>
 *       <li>只需遍历后半部分（较短）</li>
 *     </ul>
 *   </li>
 *   <li><b>返回结果</b>：如果所有节点都匹配，返回 true</li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例1：回文链表 1→2→2→1
 *
 * 【Step 1】找中点（快慢指针）
 *
 * 初始: slow=1, fast=1
 *   1 → 2 → 2 → 1 → null
 *   S,F
 *
 * 第1步: slow=2, fast=2
 *   1 → 2 → 2 → 1 → null
 *        S    F
 *
 * 第2步: slow=2, fast=null
 *   1 → 2 → 2 → 1 → null
 *             S    F(null)
 *
 * slow 指向第二个 2（后半段起点）✅
 *
 * 【Step 2】反转后半部分
 *
 * 原后半部分: 2 → 1 → null
 * 反转后:     1 → 2 → null
 *
 * secondHalfHead = 1
 *
 * 【Step 3】双指针比较
 *
 * firstHalf:  1 → 2 → ...
 * secondHalf: 1 → 2 → null
 *             ↑   ↑
 *             ✓   ✓
 *
 * 比较:
 *   firstHalf.val=1, secondHalf.val=1 → ✅
 *   firstHalf.val=2, secondHalf.val=2 → ✅
 *   secondHalf 到达 null，结束
 *
 * 返回 true ✅
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 示例2：非回文链表 1→2→3→1
 *
 * 【Step 1】找中点
 *   1 → 2 → 3 → 1 → null
 *        S    F(null)
 *
 * slow 指向 3（后半段起点）
 *
 * 【Step 2】反转后半部分
 *   原: 3 → 1 → null
 *   反转: 1 → 3 → null
 *
 * 【Step 3】双指针比较
 *
 * firstHalf:  1 → 2 → ...
 * secondHalf: 1 → 3 → null
 *             ↑   ↑
 *             ✓   ✗
 *
 * 比较:
 *   firstHalf.val=1, secondHalf.val=1 → ✅
 *   firstHalf.val=2, secondHalf.val=3 → ❌
 *
 * 返回 false ❌
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 示例3：奇数长度 1→2→3→2→1
 *
 * 【Step 1】找中点
 *   1 → 2 → 3 → 2 → 1 → null
 *              S          F(null)
 *
 * slow 指向第三个 2（中间节点 3 的下一个）
 *
 * 【Step 2】反转后半部分
 *   原: 2 → 1 → null
 *   反转: 1 → 2 → null
 *
 * 【Step 3】双指针比较
 *
 * firstHalf:  1 → 2 → 3 → ...
 * secondHalf: 1 → 2 → null
 *             ↑   ↑
 *             ✓   ✓
 *
 * 比较:
 *   firstHalf.val=1, secondHalf.val=1 → ✅
 *   firstHalf.val=2, secondHalf.val=2 → ✅
 *   secondHalf 到达 null，结束
 *
 * 注意：中间的 3 没有被比较，但这不影响结果
 * 因为回文的对称性，中间节点不需要匹配
 *
 * 返回 true ✅
 * </pre>
 *
 * <h3>🔑 关键技巧解析</h3>
 * <pre>
 * 1️⃣ 为什么用快慢指针找中点？
 *    - 链表无法随机访问，不能直接用索引
 *    - 快慢指针可以在一次遍历中找到中点
 *    - slow 走 n/2 步，fast 走 n 步
 *
 * 2️⃣ 为什么 fast 和 slow 都从 head 开始？
 *    - 偶数长度：slow 指向后半段第一个节点
 *      例如：1→2→3→4，slow 指向 3
 *    - 奇数长度：slow 指向中间节点的下一个
 *      例如：1→2→3→4→5，slow 指向 4
 *    - 这样保证后半部分长度 ≤ 前半部分
 *
 * 3️⃣ 为什么只遍历后半部分进行比较？
 *    - 后半部分长度 ≤ 前半部分
 *    - 如果是奇数长度，中间节点不需要比较
 *    - 例如：1→2→3→2→1，只比较 1,2 和 1,2
 *
 * 4️⃣ 反转链表的影响？
 *    - 原链表被修改了（后半部分反转）
 *    - 如果需要保持原链表不变，可以再次反转恢复
 *    - 但本题只需要判断，不需要恢复
 *
 * 5️⃣ 时间复杂度：O(n)
 *    - 找中点：O(n/2)
 *    - 反转后半部分：O(n/2)
 *    - 比较：O(n/2)
 *    - 总体：O(n)
 *
 * 6️⃣ 空间复杂度：O(1)
 *    - 只使用了几个指针变量
 *    - 没有额外的数据结构
 *
 * 7️⃣ 与其他解法的对比？
 *    - 数组法：将链表值存入数组，双指针比较 → O(n) 空间
 *    - 递归法：利用递归栈逆序访问 → O(n) 空间
 *    - 快慢指针+反转：最优解 → O(1) 空间
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>边界条件</b>：空链表或单节点链表直接返回 true</li>
 *   <li><b>快慢指针终止条件</b>：fast != null && fast.next != null</li>
 *   <li><b>反转链表</b>：注意暂存 next 节点，防止断链</li>
 *   <li><b>比较范围</b>：只需遍历后半部分（secondHalf != null）</li>
 *   <li><b>链表修改</b>：原链表的后半部分被反转，如需恢复可再次反转</li>
 *   <li><b>偶数/奇数长度</b>：算法同时适用，无需特殊处理</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260430 {
    /**
     * 🔍 判断链表是否为回文链表
     *
     * @param head 链表头节点
     * @return 如果是回文链表返回 true，否则返回 false
     */
    public boolean isPalindrome(ListNode head) {
        // 🛑 边界情况：空链表或只有一个节点，视为回文
        if (head == null || head.next == null) {
            return true;
        }

        // 🏃 Step 1: 使用快慢指针找到链表中点
        ListNode slow = head;  // 慢指针：每次走一步
        ListNode fast = head;  // 快指针：每次走两步

        // 当 fast 到达末尾时，slow 刚好在中间
        while (fast != null && fast.next != null) {
            slow = slow.next;      // 慢指针走一步
            fast = fast.next.next; // 快指针走两步
        }
        // 此时 slow 指向后半部分的起点

        // 🔄 Step 2: 反转后半部分链表
        ListNode secondHalfHead = reverseList(slow);

        // ⚖️ Step 3: 双指针比较前半部分和后半部分
        ListNode firstHalf = head;           // 前半部分指针
        ListNode secondHalf = secondHalfHead; // 后半部分指针（已反转）

        // 只需要遍历后半部分，因为后半部分长度 ≤ 前半部分
        while (secondHalf != null) {
            if (firstHalf.val != secondHalf.val) {
                return false; // ❌ 发现不匹配，直接返回 false
            }
            firstHalf = firstHalf.next;    // 前半部分指针前移
            secondHalf = secondHalf.next;  // 后半部分指针前移
        }

        return true; // ✅ 所有节点都匹配
    }

    /**
     * 🔄 辅助方法：反转链表
     *
     * @param head 链表头节点
     * @return 反转后的链表头节点
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;   // 前一个节点
        ListNode curr = head;   // 当前节点

        while (curr != null) {
            ListNode nextTemp = curr.next; // 💾 暂存下一个节点，防止断链
            curr.next = prev;              // 🔄 反转指针方向
            prev = curr;                   // ⬅️ prev 向前移动
            curr = nextTemp;               // ➡️ curr 向前移动
        }

        // prev 最终指向新的头节点（原链表的尾节点）
        return prev;
    }
}

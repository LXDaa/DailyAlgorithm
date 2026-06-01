package Hot100.链表;

/**
 * @author lxd
 **/
public class Hot025 {
    /**
     * <a href="https://leetcode.cn/problems/linked-list-cycle/description/">141. 环形链表</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个链表的头节点 head ，判断链表中是否有环。如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达该节点，则称该链表中存在环。</p>
     *
     * <h3>💡 核心思路：快慢指针（Floyd 判圈算法）</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用两个指针，快指针每次走2步，慢指针每次走1步，如果有环，两者必然相遇
     *     <ul>
     *       <li>slow 指针每次移动1步，fast 指针每次移动2步</li>
     *       <li>如果链表无环，fast 会先到达 null</li>
     *       <li>如果链表有环，fast 和 slow 最终会在环内相遇</li>
     *     </ul>
     *   </li>
     *   <li><b>数学原理</b>：假设环外长度为 a，环长度为 b
     *     <ul>
     *       <li>当 slow 进入环时，fast 已经在环内某处</li>
     *       <li>fast 相对于 slow 的速度是 1 步/次</li>
     *       <li>最多经过 b 次迭代，fast 就能追上 slow</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，n 为链表节点数</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用了两个指针</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例1：head = [3, 2, 0, -4]，pos = 1（有环）
     *
     * 链表结构：
     *   3 → 2 → 0 → -4
     *       ↑         |
     *       └─────────┘
     *
     * 【步骤1】初始化
     *   slow = 3, fast = 3
     *
     * 【步骤2】开始移动
     *   轮次  slow  fast
     *   初始   3     3
     *   1      2     0    (slow走1步，fast走2步)
     *   2      0     2    (slow到0，fast从0→-4→2)
     *   3     -4    -4    (slow到-4，fast从2→0→-4) ← 相遇！✅
     *
     * 结果：true（有环）✅
     *
     * ---
     *
     * 示例2：head = [1, 2]，pos = -1（无环）
     *
     * 链表结构：
     *   1 → 2 → null
     *
     * 【步骤1】初始化
     *   slow = 1, fast = 1
     *
     * 【步骤2】开始移动
     *   轮次  slow  fast
     *   初始   1     1
     *   1      2     null  (slow到2，fast从2→null)
     *
     * 结果：false（无环）❌
     *
     * ---
     *
     * 追击过程图示（有环情况）：
     *
     *   环外: 3 → [2 → 0 → -4]
     *              ↑           |
     *              └───────────┘
     *              环入口
     *
     *   slow进入环时：
     *   环内: 2 → 0 → -4 → 2
     *         ↑
     *        slow
     *              ↑
     *             fast（在slow前面某处）
     *
     *   每次迭代，fast相对slow靠近1步：
     *   第1次：距离减少1
     *   第2次：距离减少1
     *   ...
     *   第b次：距离为0，相遇！✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么快慢指针能检测环？
     *    - 无环时：fast 会先到达 null，循环结束
     *    - 有环时：fast 和 slow 都在环内，fast 速度更快
     *    - fast 相对于 slow 的速度是 1 步/次
     *    - 就像操场跑步，快的总会追上慢的
     *
     * 2️⃣ 为什么 fast 每次走2步而不是3步或更多？
     *    - 走2步是最优选择
     *    - 走2步能保证在环内相遇时不会"跳过" slow
     *    - 走3步或更多可能会错过相遇点，需要更复杂的判断
     *
     * 3️⃣ 为什么需要先检查 head 和 head.next？
     *    - 空链表或单节点链表不可能有环
     *    - 避免后续 fast.next.next 出现空指针异常
     *    - 提前返回 false，提高效率
     *
     * 4️⃣ 循环条件为什么是 fast != null && fast.next != null？
     *    - fast != null：确保 fast 当前节点有效
     *    - fast.next != null：确保 fast 可以再走一步
     *    - 两个条件缺一不可，否则会出现空指针异常
     *
     * 5️⃣ 相遇点一定在环内吗？
     *    - 是的！相遇点一定在环内
     *    - slow 刚进入环时，fast 已经在环内
     *    - 两者的相对运动只在环内进行
     *    - 所以相遇点必然在环内
     *
     * 6️⃣ 时间复杂度证明：
     *    - 无环情况：fast 遍历完整个链表，O(n)
     *    - 有环情况：
     *      * slow 进入环需要 a 步
     *      * 环内最多追 b 步（环的长度）
     *      * 总步数 ≤ a + b ≤ n
     *      * 所以时间复杂度 O(n)
     *
     * 7️⃣ 与哈希表方法对比：
     *    - 哈希表：遍历链表，将节点存入集合，遇到重复则有环
     *    - 哈希表：时间 O(n)，空间 O(n)
     *    - 快慢指针：时间 O(n)，空间 O(1) ✅ 更优
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>边界检查</b>：必须先检查 head 和 head.next 是否为 null</li>
     *   <li><b>循环条件</b>：同时检查 fast 和 fast.next，避免空指针异常</li>
     *   <li><b>移动顺序</b>：先移动指针，再判断是否相遇</li>
     *   <li><b>返回值</b>：相遇返回 true，循环结束返回 false</li>
     *   <li><b>不修改链表</b>：该方法不会改变链表结构</li>
     * </ul>
     *
     * @param head 链表的头节点
     * @return 如果链表中有环返回 true，否则返回 false
     */
    public boolean hasCycle(ListNode head) {
        // 🔍 边界检查：空链表或单节点链表不可能有环
        if (head == null || head.next == null) {
            return false;
        }

        // 🏃 初始化快慢指针，都从头节点开始
        ListNode slow = head;
        ListNode fast = head;

        // 🔄 快慢指针开始移动
        while (fast != null && fast.next != null) {
            slow = slow.next;        // 慢指针每次走1步
            fast = fast.next.next;   // 快指针每次走2步

            // ✅ 如果快慢指针相遇，说明有环
            if (slow == fast) {
                return true;
            }
        }

        // ❌ 循环结束，fast 到达 null，说明无环
        return false;
    }
}

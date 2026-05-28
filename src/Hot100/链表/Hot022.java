package Hot100.链表;

/**
 * @author lxd
 **/
public class Hot022 {
    /**
     * <a href="https://leetcode.cn/problems/intersection-of-two-linked-lists/description/">160. 相交链表</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。</p>
     *
     * <h3>💡 核心思路：双指针相遇法</h3>
     * <ul>
     *   <li><b>基本思想</b>：让两个指针分别遍历两个链表，当到达末尾时切换到另一个链表继续遍历，最终会在交点相遇
     *     <ul>
     *       <li>指针 pA 从 headA 开始遍历，到达末尾后跳到 headB 继续</li>
     *       <li>指针 pB 从 headB 开始遍历，到达末尾后跳到 headA 继续</li>
     *       <li>如果两链表相交，pA 和 pB 会在交点处相遇</li>
     *       <li>如果不相交，pA 和 pB 会同时到达 null</li>
     *     </ul>
     *   </li>
     *   <li><b>数学原理</b>：设 A 链表长度为 a，B 链表长度为 b，公共部分长度为 c
     *     <ul>
     *       <li>pA 走过的路径：a + (b - c)</li>
     *       <li>pB 走过的路径：b + (a - c)</li>
     *       <li>两者相等：a + b - c = b + a - c，必然在交点相遇</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(m + n)，m 和 n 分别为两个链表的长度</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用了两个指针</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5]
     *
     * 链表结构：
     *   A: 4 → 1 → 8 → 4 → 5
     *              ↑
     *   B: 5 → 6 → 1 ┘
     *
     * 【步骤1】初始化
     *   pA = headA (指向 4)
     *   pB = headB (指向 5)
     *
     * 【步骤2】第一轮遍历
     *   轮次  pA      pB
     *   1     4       5
     *   2     1       6
     *   3     8       1
     *   4     4       8
     *   5     5       4
     *   6     null    5
     *
     * 【步骤3】切换链表继续遍历
     *   - pA 到达 null，切换到 headB (指向 5)
     *   - pB 到达 null，切换到 headA (指向 4)
     *
     * 【步骤4】第二轮遍历
     *   轮次  pA      pB
     *   7     5       4
     *   8     6       1
     *   9     1       8  ← 相遇！✅
     *
     * 最终结果：返回节点 8（相交的起始节点）
     *
     * 路径分析：
     *   pA: 4→1→8→4→5→null → 5→6→1→8 (共走 9 步)
     *   pB: 5→6→1→8→4→5→null → 4→1→8 (共走 9 步)
     *   两者在节点 8 相遇 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么这种方法能保证相遇？
     *    - 设 A 链表非公共部分长度为 a-c，B 链表非公共部分长度为 b-c
     *    - pA 走完 A 再走 B：(a-c) + c + (b-c) = a + b - c
     *    - pB 走完 B 再走 A：(b-c) + c + (a-c) = a + b - c
     *    - 两者走的总距离相等，如果有交点，必然在交点相遇
     *
     * 2️⃣ 如果没有交点会怎样？
     *    - pA 和 pB 都会遍历完两个链表的所有节点
     *    - 最终同时到达 null，循环结束，返回 null
     *    - 这种情况相当于 c=0，两者都走了 a+b 步后同时为 null
     *
     * 3️⃣ 为什么要先判断空链表？
     *    - 如果任一链表为空，不可能相交
     *    - 避免后续遍历时出现空指针异常
     *
     * 4️⃣ 循环条件为什么是 pA != pB？
     *    - 如果相交，pA 和 pB 会在交点处相等
     *    - 如果不相交，pA 和 pB 会同时为 null，也会相等
     *    - 两种情况都能正确退出循环
     *
     * 5️⃣ 切换链表的时机？
     *    - 当指针到达 null 时立即切换
     *    - 使用三元运算符：pA == null ? headB : pA.next
     *    - 这样写简洁且高效
     *
     * 6️⃣ 与哈希表方法对比？
     *    - 哈希表：遍历 A 存入集合，遍历 B 查找，时间 O(m+n)，空间 O(m)
     *    - 双指针：时间 O(m+n)，空间 O(1) ✅ 更优
     *    - 双指针方法不需要额外空间，是最优解
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>空指针检查</b>：必须先判断 headA 和 headB 是否为 null</li>
     *   <li><b>循环终止</b>：无论是否相交，循环都会终止（最多遍历 m+n 步）</li>
     *   <li><b>返回值</b>：相交时返回交点节点，不相交时返回 null</li>
     *   <li><b>链表不变</b>：算法不会修改原链表的结构</li>
     *   <li><b>唯一性</b>：题目保证链表中没有环</li>
     * </ul>
     *
     * @param headA 第一个链表的头节点
     * @param headB 第二个链表的头节点
     * @return 两个链表相交的起始节点，如果不相交则返回 null
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 🔍 边界检查：如果任一链表为空，不可能相交
        if (headA == null || headB == null) {
            return null;
        }

        // 👥 初始化双指针，分别从两个链表的头部开始
        ListNode pA = headA, pB = headB;

        // 🔄 循环直到两个指针相遇（在交点或 null 处）
        while (pA != pB) {
            // 📍 pA 移动：如果到达末尾则切换到 headB，否则继续向后
            pA = pA == null ? headB : pA.next;
            // 📍 pB 移动：如果到达末尾则切换到 headA，否则继续向后
            pB = pB == null ? headA : pB.next;
        }

        // ✅ 返回相遇节点（相交节点或 null）
        return pA;
    }
}

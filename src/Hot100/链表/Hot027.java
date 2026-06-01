package Hot100.链表;

/**
 * @author lxd
 **/
public class Hot027 {
    /**
     * <a href="https://leetcode.cn/problems/merge-two-sorted-lists/description/">21. 合并两个有序链表</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。</p>
     *
     * <h3>💡 核心思路：迭代法（双指针 +  dummy 节点）</h3>
     * <ul>
     *   <li><b>基本思想</b>：同时遍历两个链表，每次选择较小的节点接到结果链表后面
     *     <ul>
     *       <li>创建 dummy 节点作为结果链表的虚拟头节点</li>
     *       <li>使用 tail 指针指向结果链表的尾部</li>
     *       <li>比较 list1 和 list2 当前节点的值，将较小的节点接到 tail 后面</li>
     *       <li>移动对应链表的指针和 tail 指针</li>
     *       <li>当其中一个链表遍历完后，将另一个链表剩余部分直接接到 tail 后面</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(m + n)，m 和 n 分别是两个链表的长度</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用了常数个额外指针</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：list1 = [1, 2, 4], list2 = [1, 3, 4]
     *
     * 【初始状态】
     *   dummy → null
     *           ↑
     *          tail
     *
     *   list1: 1 → 2 → 4 → null
     *   list2: 1 → 3 → 4 → null
     *
     * 【第1轮】比较 list1.val=1 和 list2.val=1
     *   - 1 <= 1，选择 list1
     *   - tail.next = list1 (dummy → 1)
     *   - list1 = list1.next (list1 指向 2)
     *   - tail = tail.next (tail 指向 1)
     *
     *   结果：dummy → 1 → null
     *                ↑
     *               tail
     *   list1: 2 → 4 → null
     *   list2: 1 → 3 → 4 → null
     *
     * 【第2轮】比较 list1.val=2 和 list2.val=1
     *   - 2 > 1，选择 list2
     *   - tail.next = list2 (1 → 1)
     *   - list2 = list2.next (list2 指向 3)
     *   - tail = tail.next (tail 指向第二个 1)
     *
     *   结果：dummy → 1 → 1 → null
     *                     ↑
     *                    tail
     *   list1: 2 → 4 → null
     *   list2: 3 → 4 → null
     *
     * 【第3轮】比较 list1.val=2 和 list2.val=3
     *   - 2 <= 3，选择 list1
     *   - tail.next = list1 (1 → 2)
     *   - list1 = list1.next (list1 指向 4)
     *   - tail = tail.next (tail 指向 2)
     *
     *   结果：dummy → 1 → 1 → 2 → null
     *                          ↑
     *                         tail
     *   list1: 4 → null
     *   list2: 3 → 4 → null
     *
     * 【第4轮】比较 list1.val=4 和 list2.val=3
     *   - 4 > 3，选择 list2
     *   - tail.next = list2 (2 → 3)
     *   - list2 = list2.next (list2 指向 4)
     *   - tail = tail.next (tail 指向 3)
     *
     *   结果：dummy → 1 → 1 → 2 → 3 → null
     *                               ↑
     *                              tail
     *   list1: 4 → null
     *   list2: 4 → null
     *
     * 【第5轮】比较 list1.val=4 和 list2.val=4
     *   - 4 <= 4，选择 list1
     *   - tail.next = list1 (3 → 4)
     *   - list1 = list1.next (list1 指向 null)
     *   - tail = tail.next (tail 指向 4)
     *
     *   结果：dummy → 1 → 1 → 2 → 3 → 4 → null
     *                                    ↑
     *                                   tail
     *   list1: null
     *   list2: 4 → null
     *
     * 【循环结束】list1 == null，退出循环
     *   - 将 list2 剩余部分接到 tail 后面
     *   - tail.next = list2 (4 → 4)
     *
     *   最终结果：dummy → 1 → 1 → 2 → 3 → 4 → 4 → null
     *
     * 返回 dummy.next：1 → 1 → 2 → 3 → 4 → 4 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要用 dummy 节点？
     *    - dummy 节点作为虚拟头节点，简化边界处理
     *    - 不需要特殊处理第一个节点的插入
     *    - 最后返回 dummy.next 就是合并后的链表头
     *    - 避免判断结果链表是否为空的复杂逻辑
     *
     * 2️⃣ 为什么用 tail 指针？
     *    - tail 始终指向结果链表的尾部
     *    - 新节点可以直接接在 tail 后面，O(1) 操作
     *    - 不需要每次都遍历到结果链表末尾
     *    - 提高插入效率
     *
     * 3️⃣ 为什么条件是 list1.val <= list2.val？
     *    - 使用 <= 保证稳定性（相等时优先选择 list1 的节点）
     *    - 如果用 <，相等时会优先选择 list2 的节点
     *    - 两种都可以，但要保持一致
     *
     * 4️⃣ 循环条件为什么是 && 而不是 ||？
     *    - while (list1 != null && list2 != null)
     *    - 只要有一个链表为空，就退出循环
     *    - 退出后将非空链表的剩余部分直接接上
     *    - 这样避免了在循环内判断哪个链表为空的复杂逻辑
     *
     * 5️⃣ 最后为什么要接剩余部分？
     *    - 循环结束时，至少有一个链表已经遍历完
     *    - 另一个链表可能还有剩余节点
     *    - 因为两个链表都是有序的，剩余部分也是有序的
     *    - 直接接到 tail 后面即可，无需再比较
     *
     * 6️⃣ 边界情况处理？
     *    - 一个或两个链表为空：算法能正确处理
     *    - list1 为空：直接返回 list2
     *    - list2 为空：直接返回 list1
     *    - 都为空：返回 null
     *
     * 7️⃣ 与递归方法对比？
     *    - 递归：代码简洁，但空间复杂度 O(m+n)（递归栈）
     *    - 迭代：空间复杂度 O(1) ✅ 更优
     *    - 迭代方法不会栈溢出，适合大规模数据
     *
     * 8️⃣ 完整流程图示：
     *
     *    输入：  1 → 2 → 4
     *            1 → 3 → 4
     *
     *    过程：  逐个比较，小的先接
     *            ↓
     *    输出：  1 → 1 → 2 → 3 → 4 → 4
     *
     *    特点：  保持有序性 ✅
     *            不创建新节点 ✅
     *            原地合并 ✅
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>dummy 节点</b>：必须创建 dummy 节点，简化头节点处理</li>
     *   <li><b>tail 更新</b>：每次添加节点后，记得更新 tail = tail.next</li>
     *   <li><b>循环条件</b>：使用 &&，确保两个链表都非空时才比较</li>
     *   <li><b>剩余部分</b>：循环结束后，别忘了将剩余部分接到 tail 后面</li>
     *   <li><b>返回值</b>：返回 dummy.next，不是 dummy</li>
     *   <li><b>不修改原链表</b>：该方法只是重新连接节点，不创建新节点</li>
     * </ul>
     *
     * @param list1 第一个升序链表的头节点
     * @param list2 第二个升序链表的头节点
     * @return 合并后的升序链表头节点
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 🎭 创建 dummy 节点，简化边界处理
        ListNode dummy = new ListNode();
        // 👆 tail 指针始终指向结果链表的尾部
        ListNode tail = dummy;

        // 🔄 同时遍历两个链表，每次选择较小的节点
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                // ✅ list1 的节点较小，接到 tail 后面
                tail.next = list1;
                list1 = list1.next;  // list1 指针后移
            } else {
                // ✅ list2 的节点较小，接到 tail 后面
                tail.next = list2;
                list2 = list2.next;  // list2 指针后移
            }
            // ⏭️ tail 指针后移，指向新的尾部
            tail = tail.next;
        }

        // 📎 将剩余部分接到 tail 后面（只有一个链表会有剩余）
        tail.next = list1 == null ? list2 : list1;

        // ✅ 返回合并后的链表头节点（跳过 dummy）
        return dummy.next;
    }
}

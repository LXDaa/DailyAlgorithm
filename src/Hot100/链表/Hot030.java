package Hot100.链表;

/**
 * @author lxd
 **/
public class Hot030 {
    /**
     * <a href="https://leetcode.cn/problems/swap-nodes-in-pairs/description/">24. 两两交换链表中的节点</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。</p>
     *
     * <h3>💡 核心思路：迭代法（三指针交换）</h3>
     * <ul>
     *   <li><b>基本思想</b>：每次处理两个节点，通过改变指针指向来交换它们的位置
     *     <ul>
     *       <li>使用 dummy 节点简化边界处理</li>
     *       <li>使用 pre 指针指向前一个节点（初始为 dummy）</li>
     *       <li>每次取出 pre 后面的两个节点 first 和 second</li>
     *       <li>通过三步操作交换 first 和 second 的位置</li>
     *       <li>更新 pre 指针，继续处理下一对节点</li>
     *     </ul>
     *   </li>
     *   <li><b>交换步骤</b>：
     *     <ul>
     *       <li>保存第三个节点：next = second.next</li>
     *       <li>pre 指向 second：pre.next = second</li>
     *       <li>second 指向 first：second.next = first</li>
     *       <li>first 指向 next：first.next = next</li>
     *       <li>更新 pre = first（为下一轮做准备）</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，n 为链表节点数，遍历一次</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用了常数个额外指针</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：head = [1, 2, 3, 4]
     *
     * 【初始状态】
     *   dummy → 1 → 2 → 3 → 4 → null
     *   ↑
     *   pre
     *
     * 【第1轮】交换节点 1 和 2
     *
     *   初始化：
     *   first = pre.next = 1
     *   second = pre.next.next = 2
     *   next = second.next = 3
     *
     *   dummy → 1 → 2 → 3 → 4 → null
     *   ↑     ↑   ↑   ↑
     *   pre  first sec next
     *
     *   执行交换（三步操作）：
     *   1️⃣ pre.next = second  (dummy → 2)
     *   2️⃣ second.next = first (2 → 1)
     *   3️⃣ first.next = next   (1 → 3)
     *
     *   dummy → 2 → 1 → 3 → 4 → null
     *   ↑           ↑
     *   pre        first
     *              (也是下一轮的pre)
     *
     *   更新 pre = first (pre 指向节点 1)
     *
     * 【第2轮】交换节点 3 和 4
     *
     *   初始化：
     *   first = pre.next = 3
     *   second = pre.next.next = 4
     *   next = second.next = null
     *
     *   dummy → 2 → 1 → 3 → 4 → null
     *             ↑   ↑   ↑   ↑
     *            pre first sec next
     *
     *   执行交换（三步操作）：
     *   1️⃣ pre.next = second  (1 → 4)
     *   2️⃣ second.next = first (4 → 3)
     *   3️⃣ first.next = next   (3 → null)
     *
     *   dummy → 2 → 1 → 4 → 3 → null
     *                   ↑
     *                  first
     *                  (也是下一轮的pre)
     *
     *   更新 pre = first (pre 指向节点 3)
     *
     * 【循环结束】pre.next.next = null，不足两个节点，退出循环
     *
     * 最终结果：返回 dummy.next = [2, 1, 4, 3] ✅
     *
     * ---
     *
     * 奇数长度示例：head = [1, 2, 3]
     *
     * 【第1轮】交换节点 1 和 2
     *   dummy → 1 → 2 → 3 → null
     *   ↓
     *   dummy → 2 → 1 → 3 → null
     *             ↑
     *            pre
     *
     * 【第2轮】检查 pre.next = 3, pre.next.next = null
     *   只有一个节点，不满足交换条件，退出循环
     *
     * 最终结果：[2, 1, 3] ✅（最后一个节点不变）
     *
     * ---
     *
     * 交换过程图示（以第一次交换为例）：
     *
     *   交换前：
     *   pre → first → second → next
     *         (1)     (2)      (3)
     *
     *   步骤1：pre.next = second
     *   pre → second   first → second → next
     *         (2)      (1)     (2)      (3)
     *
     *   步骤2：second.next = first
     *   pre → second → first   next
     *         (2)      (1)     (3)
     *                  ↑
     *               first.next 还指向 second（临时环）
     *
     *   步骤3：first.next = next
     *   pre → second → first → next
     *         (2)      (1)     (3)
     *
     *   交换完成！✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要用 dummy 节点？
     *    - 第一个节点也需要交换，没有 dummy 需要特殊处理
     *    - 有了 dummy，所有交换操作都统一为相同的三步
     *    - 最后返回 dummy.next 即可
     *
     * 2️⃣ 为什么需要保存 next 节点？
     *    - 交换后会丢失原链表中 second 后面的节点
     *    - next 起到"桥梁"作用，保存后续链表
     *    - 最后让 first.next = next，连接回原链表
     *
     * 3️⃣ 三步操作的顺序能改变吗？
     *    - 不能！顺序非常关键
     *    - 必须先保存 next（否则丢失后续节点）
     *    - 然后依次修改 pre.next、second.next、first.next
     *    - 顺序错误会导致链表断裂或死循环
     *
     * 4️⃣ 为什么 pre 更新为 first 而不是 second？
     *    - 交换后，first 变成了这一对的第二个节点
     *    - 下一对节点需要接在 first 后面
     *    - 所以 pre 应该指向 first，为下一轮做准备
     *
     * 5️⃣ 循环条件为什么是 pre.next != null && pre.next.next != null？
     *    - 需要保证 pre 后面至少有两个节点才能交换
     *    - pre.next != null：确保有第一个节点
     *    - pre.next.next != null：确保有第二个节点
     *    - 如果只剩一个节点或没有节点，不需要交换
     *
     * 6️⃣ 奇数长度链表如何处理？
     *    - 例如 [1, 2, 3]
     *    - 第一轮交换 1 和 2，得到 [2, 1, 3]
     *    - 第二轮检查时，pre.next = 3，pre.next.next = null
     *    - 不满足循环条件，退出循环
     *    - 最后一个节点保持不变 ✅
     *
     * 7️⃣ 与递归方法对比？
     *    - 递归：代码简洁，但空间复杂度 O(n)（递归栈）
     *    - 迭代：空间复杂度 O(1) ✅ 更优
     *    - 迭代方法不会栈溢出，适合大规模数据
     *
     * 8️⃣ 完整流程总结：
     *
     *    初始化：
     *    ├─ 创建 dummy 节点，指向 head
     *    └─ pre 指向 dummy
     *
     *    循环处理（每次处理一对节点）：
     *    ├─ 检查是否有足够节点（pre.next 和 pre.next.next）
     *    ├─ 保存三个节点：first、second、next
     *    ├─ 执行三步交换操作
     *    └─ 更新 pre = first
     *
     *    返回：dummy.next
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>dummy 节点</b>：必须创建 dummy 节点，处理头节点交换的情况</li>
     *   <li><b>循环条件</b>：同时检查 pre.next 和 pre.next.next，确保有两个节点</li>
     *   <li><b>保存 next</b>：必须先保存 second.next，否则会丢失后续节点</li>
     *   <li><b>操作顺序</b>：严格按照 pre.next → second.next → first.next 的顺序</li>
     *   <li><b>更新 pre</b>：每轮结束后，pre 更新为 first（不是 second）</li>
     *   <li><b>返回值</b>：返回 dummy.next，不是 head（因为 head 可能被交换）</li>
     * </ul>
     *
     * @param head 链表的头节点
     * @return 交换后的链表头节点
     */
    public ListNode swapPairs(ListNode head) {
        // 🎭 创建 dummy 节点，简化边界处理
        ListNode dummy = new ListNode(0, head);
        // 👆 pre 指针指向前一个节点，初始为 dummy
        ListNode pre = dummy;

        // 🔄 循环处理，每次交换一对节点
        while (pre.next != null && pre.next.next != null) {
            // 💾 保存需要交换的两个节点和后续节点
            ListNode first = pre.next;      // 第一个节点
            ListNode second = pre.next.next; // 第二个节点
            ListNode next = second.next;     // 第三个节点（后续链表的头）

            // 🔀 执行三步交换操作
            pre.next = second;   // 步骤1：pre 指向 second
            second.next = first; // 步骤2：second 指向 first
            first.next = next;   // 步骤3：first 指向后续链表

            // ⏭️ 更新 pre 指针，为下一轮交换做准备
            pre = first;
        }

        // ✅ 返回交换后的链表头节点
        return dummy.next;
    }
}

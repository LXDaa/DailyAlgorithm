package Hot100.链表;

/**
 * @author lxd
 **/
public class Hot031 {
    /**
     * <a href="https://leetcode.cn/problems/reverse-nodes-in-k-group/description/">25. K 个一组翻转链表</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。</p>
     *
     * <h3>💡 核心思路：分组 + 反转 + 连接</h3>
     * <ul>
     *   <li><b>基本思想</b>：先计算链表长度，然后每 k 个节点为一组进行反转，最后连接各组
     *     <ul>
     *       <li>第一步：遍历链表计算总长度 len</li>
     *       <li>第二步：使用 dummy 节点简化边界处理</li>
     *       <li>第三步：循环处理，每次反转一组 k 个节点</li>
     *       <li>第四步：将反转后的子链表重新连接到原链表</li>
     *       <li>第五步：更新 groupPrev 指针，处理下一组</li>
     *     </ul>
     *   </li>
     *   <li><b>反转方法</b>：使用标准的链表反转方法（pre、cur、tmp 三指针）
     *     <ul>
     *       <li>对每组 k 个节点执行反转操作</li>
     *       <li>反转后，pre 指向新的头节点，cur 指向下一组的第一个节点</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，n 为链表节点数，每个节点最多被访问两次</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用了常数个额外指针</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：head = [1, 2, 3, 4, 5], k = 2
     *
     * 【步骤1】计算链表长度
     *   len = 5
     *
     * 【步骤2】初始化
     *   dummy → 1 → 2 → 3 → 4 → 5 → null
     *   ↑
     *   groupPrev
     *
     * 【第1组】反转前2个节点 [1, 2]
     *
     *   初始状态：
     *   groupPrev = dummy
     *   pre = null, cur = 1
     *   len = 5 >= 2，进入循环
     *   len -= k → len = 3
     *
     *   执行反转（k=2次迭代）：
     *   第1次：1 → null, pre = 1, cur = 2
     *   第2次：2 → 1, pre = 2, cur = 3
     *
     *   反转后：
     *   2 → 1 → null
     *   pre = 2（新头）, cur = 3（下一组开始）
     *
     *   连接回原链表：
     *   nxt = groupPrev.next = 1（原组的第一个节点，反转后变成最后一个）
     *   groupPrev.next.next = cur → 1.next = 3
     *   groupPrev.next = pre → dummy.next = 2
     *   groupPrev = nxt → groupPrev = 1
     *
     *   结果：dummy → 2 → 1 → 3 → 4 → 5 → null
     *                ↑
     *             groupPrev
     *
     * 【第2组】反转接下来的2个节点 [3, 4]
     *
     *   初始状态：
     *   groupPrev = 1
     *   pre = null, cur = 3
     *   len = 3 >= 2，继续循环
     *   len -= k → len = 1
     *
     *   执行反转（k=2次迭代）：
     *   第1次：3 → null, pre = 3, cur = 4
     *   第2次：4 → 3, pre = 4, cur = 5
     *
     *   反转后：
     *   4 → 3 → null
     *   pre = 4（新头）, cur = 5（下一组开始）
     *
     *   连接回原链表：
     *   nxt = groupPrev.next = 3
     *   groupPrev.next.next = cur → 3.next = 5
     *   groupPrev.next = pre → 1.next = 4
     *   groupPrev = nxt → groupPrev = 3
     *
     *   结果：dummy → 2 → 1 → 4 → 3 → 5 → null
     *                         ↑
     *                      groupPrev
     *
     * 【第3组】检查剩余节点
     *   len = 1 < 2，不满足条件，退出循环
     *   剩余节点 5 保持不变
     *
     * 最终结果：[2, 1, 4, 3, 5] ✅
     *
     * ---
     *
     * 示例2：head = [1, 2, 3, 4, 5], k = 3
     *
     * 【步骤1】len = 5
     *
     * 【第1组】反转 [1, 2, 3]
     *   反转后：3 → 2 → 1
     *   连接：dummy → 3 → 2 → 1 → 4 → 5 → null
     *                       ↑
     *                    groupPrev
     *   len = 5 - 3 = 2
     *
     * 【第2组】检查 len = 2 < 3，退出循环
     *   剩余节点 [4, 5] 保持不变
     *
     * 最终结果：[3, 2, 1, 4, 5] ✅
     *
     * ---
     *
     * 连接过程详解（以第一组为例）：
     *
     *   反转前：
     *   groupPrev → first → ... → last → next
     *      (A)      (1)              (2)    (3)
     *
     *   反转后（局部）：
     *   last → ... → first → null
     *   (2)          (1)
     *   pre=2, cur=3
     *
     *   连接步骤：
     *   1️⃣ nxt = first = 1（保存原组的第一个节点）
     *   2️⃣ first.next = cur → 1.next = 3（连接后续链表）
     *   3️⃣ groupPrev.next = pre → A.next = 2（连接前驱）
     *   4️⃣ groupPrev = nxt → groupPrev = 1（移动到下一组的前驱）
     *
     *   最终：
     *   A → 2 → 1 → 3 → ...
     *         ↑   ↑
     *        pre  nxt(groupPrev)
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要先计算链表长度？
     *    - 需要知道还剩多少节点，判断是否够 k 个
     *    - 如果剩余节点不足 k 个，不需要反转
     *    - 避免额外的遍历来判断是否有足够节点
     *
     * 2️⃣ 为什么用 groupPrev 指针？
     *    - groupPrev 始终指向前一组的最后一个节点
     *    - 用于连接反转后的子链表
     *    - 每次处理完一组后，更新为当前组的最后一个节点
     *
     * 3️⃣ 反转后如何连接回原链表？
     *    - 保存原组的第一个节点 nxt（反转后变成最后一个）
     *    - nxt.next = cur（连接到下一组的第一个节点）
     *    - groupPrev.next = pre（连接到反转后的新头节点）
     *    - 这样就完成了前后连接
     *
     * 4️⃣ 为什么 groupPrev 更新为 nxt？
     *    - nxt 是原组的第一个节点
     *    - 反转后，它变成了该组的最后一个节点
     *    - 下一组的前驱应该是当前组的最后一个节点
     *    - 所以 groupPrev = nxt
     *
     * 5️⃣ len -= k 的作用？
     *    - 每处理一组，剩余长度减少 k
     *    - 当 len < k 时，说明剩余节点不足 k 个
     *    - 此时退出循环，保持剩余节点不变
     *
     * 6️⃣ 边界情况处理？
     *    - k = 1：不需要反转，直接返回原链表
     *    - k > len：不满足循环条件，直接返回原链表
     *    - len 是 k 的整数倍：所有节点都被反转
     *    - len 不是 k 的整数倍：最后一组保持不变
     *
     * 7️⃣ 与递归方法对比？
     *    - 递归：代码简洁，但空间复杂度 O(n/k)（递归栈）
     *    - 迭代：空间复杂度 O(1) ✅ 更优
     *    - 迭代方法不会栈溢出，适合大规模数据
     *
     * 8️⃣ 完整流程总结：
     *
     *    第一步：计算长度
     *    └─ 遍历链表，统计节点数 len
     *
     *    第二步：初始化
     *    ├─ 创建 dummy 节点
     *    └─ groupPrev 指向 dummy
     *
     *    第三步：循环处理每组
     *    ├─ 检查 len >= k
     *    ├─ 反转当前组的 k 个节点
     *    ├─ 连接前后链表
     *    ├─ 更新 groupPrev
     *    └─ len -= k
     *
     *    第四步：返回结果
     *    └─ return dummy.next
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>长度计算</b>：必须先计算链表长度，确保只反转完整的 k 个节点组</li>
     *   <li><b>dummy 节点</b>：必须创建 dummy 节点，处理头节点变化的情况</li>
     *   <li><b>连接顺序</b>：先连接后续链表（first.next = cur），再连接前驱（groupPrev.next = pre）</li>
     *   <li><b>更新 groupPrev</b>：必须更新为原组的第一个节点（反转后的最后一个）</li>
     *   <li><b>循环条件</b>：while (len >= k)，确保有足够节点才反转</li>
     *   <li><b>返回值</b>：返回 dummy.next，不是 head（因为 head 可能被反转）</li>
     * </ul>
     *
     * @param head 链表的头节点
     * @param k    每组翻转的节点数
     * @return 翻转后的链表头节点
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // 📏 第一步：计算链表长度
        int len = 0;
        for (ListNode p = head; p != null; p = p.next) {
            len++;
        }

        // 🎭 创建 dummy 节点，简化边界处理
        ListNode dummy = new ListNode(-1, head);
        // 👆 groupPrev 指向前一组的最后一个节点
        ListNode groupPrev = dummy;

        // 🔄 循环处理，每次反转一组 k 个节点
        while (len >= k) {
            // 📉 剩余长度减少 k
            len -= k;

            // 🔀 反转当前组的 k 个节点
            ListNode pre = null, cur = groupPrev.next;
            for (int i = 0; i < k; i++) {
                ListNode tmp = cur.next;  // 保存下一个节点
                cur.next = pre;           // 反转指针
                pre = cur;                // pre 前移
                cur = tmp;                // cur 前移
            }

            // 🔗 连接前后链表
            ListNode nxt = groupPrev.next;  // 保存原组的第一个节点（反转后变成最后一个）
            groupPrev.next.next = cur;      // 连接到下一组的第一个节点
            groupPrev.next = pre;           // 连接到反转后的新头节点
            groupPrev = nxt;                // 更新 groupPrev 为当前组的最后一个节点
        }

        // ✅ 返回翻转后的链表头节点
        return dummy.next;
    }
}

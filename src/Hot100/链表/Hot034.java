package Hot100.链表;

/**
 * @author lxd
 **/
public class Hot034 {
    /**
     * <a href="https://leetcode.cn/problems/merge-k-sorted-lists/description/">23. 合并 K 个升序链表</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个链表数组，每个链表都已经按升序排列。请你将所有链表合并到一个升序链表中，返回合并后的链表。</p>
     *
     * <h3>💡 核心思路：分治法（二分合并）</h3>
     * <ul>
     *   <li><b>基本思想</b>：将 K 个链表两两分组，递归合并，最终得到一个有序链表
     *     <ul>
     *       <li>第一步：将链表数组分成两半 [l, mid] 和 [mid+1, r]</li>
     *       <li>第二步：递归合并左半部分，得到 left</li>
     *       <li>第三步：递归合并右半部分，得到 right</li>
     *       <li>第四步：合并 left 和 right 两个有序链表</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用分治法</b>：
     *     <ul>
     *       <li>直接逐个合并：时间复杂度 O(kn)，k 是链表数，n 是平均长度</li>
     *       <li>分治法：时间复杂度 O(n log k)，每层合并 O(n)，共 log k 层</li>
     *       <li>分治法显著减少了比较次数</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n log k)，n 是所有节点总数，k 是链表数量</li>
     *   <li><b>空间复杂度</b>：O(log k)，递归栈的空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：lists = [[1, 4, 5], [1, 3, 4], [2, 6]]
     *
     * 【分治过程】
     *
     * 第1层：合并 [0, 2] 区间
     *   mid = 0 + (2-0)/2 = 1
     *   分成 [0, 1] 和 [2, 2]
     *
     * 第2层：合并 [0, 1] 区间
     *   mid = 0 + (1-0)/2 = 0
     *   分成 [0, 0] 和 [1, 1]
     *
     * 第3层：基本情况
     *   [0, 0] → 返回 lists[0] = [1, 4, 5]
     *   [1, 1] → 返回 lists[1] = [1, 3, 4]
     *   [2, 2] → 返回 lists[2] = [2, 6]
     *
     * 【合并过程】（回溯）
     *
     * 合并 [0, 0] 和 [1, 1]：
     *   left = [1, 4, 5], right = [1, 3, 4]
     *   合并结果：[1, 1, 3, 4, 4, 5]
     *
     * 合并 [0, 1] 和 [2, 2]：
     *   left = [1, 1, 3, 4, 4, 5], right = [2, 6]
     *   合并结果：[1, 1, 2, 3, 4, 4, 5, 6]
     *
     * 最终结果：[1, 1, 2, 3, 4, 4, 5, 6] ✅
     *
     * ---
     *
     * 分治树结构：
     *
     *                    [0, 2]
     *                   /      \
     *              [0, 1]      [2, 2]
     *             /      \         |
     *          [0, 0]  [1, 1]   [2, 6]
     *            |        |
     *        [1,4,5]  [1,3,4]
     *            \      /
     *         [1,1,3,4,4,5]
     *                \
     *           [1,1,2,3,4,4,5,6]
     *
     * ---
     *
     * 详细合并步骤（以第一次合并为例）：
     *
     * 合并 [1, 4, 5] 和 [1, 3, 4]：
     *
     * 初始状态：
     *   left:  1 → 4 → 5 → null
     *   right: 1 → 3 → 4 → null
     *   dummy → null
     *           ↑
     *          tail
     *
     * 第1轮：left.val=1, right.val=1
     *   1 >= 1，选择 right（或 left，相等时任选）
     *   tail.next = 1(right), tail = 1, right = 3
     *   dummy → 1 → null
     *
     * 第2轮：left.val=1, right.val=3
     *   1 < 3，选择 left
     *   tail.next = 1(left), tail = 1, left = 4
     *   dummy → 1 → 1 → null
     *
     * 第3轮：left.val=4, right.val=3
     *   4 > 3，选择 right
     *   tail.next = 3, tail = 3, right = 4
     *   dummy → 1 → 1 → 3 → null
     *
     * 第4轮：left.val=4, right.val=4
     *   4 >= 4，选择 right
     *   tail.next = 4(right), tail = 4, right = null
     *   dummy → 1 → 1 → 3 → 4 → null
     *
     * 循环结束，right == null
     *   tail.next = left (接上剩余的 4 → 5)
     *   dummy → 1 → 1 → 3 → 4 → 4 → 5 → null
     *
     * 合并结果：[1, 1, 3, 4, 4, 5] ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用分治法而不是逐个合并？
     *    - 逐个合并：第1次合并2个链表，第2次合并3个链表...
     *    - 总比较次数：n + 2n + 3n + ... + kn = O(k²n)
     *    - 分治法：每次都将问题规模减半
     *    - 总比较次数：n log k，显著更优
     *
     * 2️⃣ 分治法的层数是多少？
     *    - 每次将 k 个链表分成两半
     *    - 需要 log k 次分割才能到基本情况
     *    - 每层合并的总节点数是 n
     *    - 所以时间复杂度是 O(n log k)
     *
     * 3️⃣ 递归的终止条件是什么？
     *    - l == r：区间只剩一个链表
     *    - 直接返回该链表，无需合并
     *    - 这是分治的基本情况
     *
     * 4️⃣ 为什么要用 mid = l + (r - l) / 2？
     *    - 避免整数溢出
     *    - 当 l 和 r 很大时，(l + r) / 2 可能溢出
     *    - l + (r - l) / 2 更安全
     *
     * 5️⃣ mergeTwoLists 如何处理 null 输入？
     *    - 如果 l1 == null，循环不执行，直接返回 l2
     *    - 如果 l2 == null，循环不执行，直接返回 l1
     *    - 如果都为 null，返回 null
     *    - 天然支持 null 输入，无需特殊处理
     *
     * 6️⃣ 与优先队列方法对比？
     *    - 优先队列：维护大小为 k 的最小堆
     *    - 每次取出最小值，加入下一个节点
     *    - 时间复杂度：O(n log k)，与分治法相同
     *    - 空间复杂度：O(k)（堆的空间）
     *    - 分治法：空间 O(log k)（递归栈），更优
     *
     * 7️⃣ 分治法的优势？
     *    - 代码简洁，逻辑清晰
     *    - 充分利用了"合并两个有序链表"的基础操作
     *    - 空间复杂度低（只有递归栈）
     *    - 适合大规模数据
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ 边界检查：lists 为空返回 null
     *    └─ 调用 merge(lists, 0, lists.length - 1)
     *
     *    分治函数 merge(l, r)：
     *    ├─ 基本情况：l == r → 返回 lists[l]
     *    ├─ 计算中点：mid = l + (r - l) / 2
     *    ├─ 递归左半部分：left = merge(l, mid)
     *    ├─ 递归右半部分：right = merge(mid + 1, r)
     *    └─ 合并：return mergeTwoLists(left, right)
     *
     *    合并函数 mergeTwoLists(l1, l2)：
     *    ├─ 创建 dummy 节点
     *    ├─ 双指针遍历，每次选择较小的节点
     *    └─ 接上剩余部分，返回 dummy.next
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>边界检查</b>：必须先检查 lists 是否为 null 或空数组</li>
     *   <li><b>中点计算</b>：使用 l + (r - l) / 2 避免整数溢出</li>
     *   <li><b>递归终止</b>：l == r 时直接返回，不再分割</li>
     *   <li><b>null 处理</b>：mergeTwoLists 要能处理 null 输入</li>
     *   <li><b>返回值</b>：最终返回 merge 的结果，即合并后的链表头</li>
     *   <li><b>稳定性</b>：合并时使用 < 而不是 <=，保持稳定性</li>
     * </ul>
     *
     * @param lists 链表数组，每个链表已按升序排列
     * @return 合并后的升序链表头节点
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // 🔍 边界检查：数组为空或长度为0，返回 null
        if (lists == null || lists.length == 0) {
            return null;
        }

        // 🔄 调用分治函数，合并整个区间的链表
        return merge(lists, 0, lists.length - 1);
    }

    /**
     * 分治合并：合并 [l, r] 区间内的所有链表
     *
     * @param lists 链表数组
     * @param l     区间左边界
     * @param r     区间右边界
     * @return 合并后的链表头节点
     */
    private ListNode merge(ListNode[] lists, int l, int r) {
        // 🎯 基本情况：区间只剩一个链表，直接返回
        if (l == r) {
            return lists[l];
        }

        // 📍 计算中点，避免整数溢出
        int mid = l + (r - l) / 2;

        // 🔄 递归合并左半部分和右半部分
        ListNode left = merge(lists, l, mid);      // 合并 [l, mid]
        ListNode right = merge(lists, mid + 1, r); // 合并 [mid+1, r]

        // 🔗 合并两个有序链表
        return mergeTwoLists(left, right);
    }

    /**
     * 合并两个有序链表
     *
     * @param l1 第一个有序链表的头节点
     * @param l2 第二个有序链表的头节点
     * @return 合并后的有序链表头节点
     */
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 🎭 创建 dummy 节点，简化边界处理
        ListNode dummy = new ListNode();
        // 👆 tail 指针始终指向结果链表的尾部
        ListNode tail = dummy;

        // 🔄 同时遍历两个链表，每次选择较小的节点
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                tail.next = l1;  // 选择 l1 的节点
                l1 = l1.next;    // l1 指针后移
            } else {
                tail.next = l2;  // 选择 l2 的节点
                l2 = l2.next;    // l2 指针后移
            }
            tail = tail.next;  // tail 指针后移
        }

        // 📎 将剩余部分接到 tail 后面（其中一个链表已遍历完）
        tail.next = l1 != null ? l1 : l2;

        // ✅ 返回合并后的链表头节点
        return dummy.next;
    }
}

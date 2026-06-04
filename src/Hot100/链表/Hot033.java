package Hot100.链表;

/**
 * @author lxd
 **/
public class Hot033 {
    /**
     * <a href="https://leetcode.cn/problems/sort-list/description/">148. 排序链表</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你链表的头结点 head ，请将其按升序排列并返回排序后的链表。</p>
     *
     * <h3>💡 核心思路：归并排序（自顶向下）</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用归并排序算法，将链表不断二分，然后合并有序子链表
     *     <ul>
     *       <li>第一步：使用快慢指针找到链表中点，将链表分成两半</li>
     *       <li>第二步：递归地对两半分别排序</li>
     *       <li>第三步：合并两个有序链表</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用归并排序</b>：
     *     <ul>
     *       <li>链表不支持随机访问，快速排序效率低</li>
     *       <li>归并排序只需要顺序访问，适合链表</li>
     *       <li>时间复杂度稳定为 O(n log n)</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n log n)，每次分割 O(1)，合并 O(n)，共 log n 层</li>
     *   <li><b>空间复杂度</b>：O(log n)，递归栈的空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：head = [4, 2, 1, 3]
     *
     * 【分割阶段】（递归分割）
     *
     * 第1层：[4, 2, 1, 3]
     *   找中点：slow=2, second=1
     *   分割：[4, 2] 和 [1, 3]
     *
     * 第2层：[4, 2]
     *   找中点：slow=4, second=2
     *   分割：[4] 和 [2]
     *
     * 第2层：[1, 3]
     *   找中点：slow=1, second=3
     *   分割：[1] 和 [3]
     *
     * 第3层：[4], [2], [1], [3]
     *   单节点，直接返回（递归终止）
     *
     * 【合并阶段】（递归回溯）
     *
     * 合并 [4] 和 [2]：
     *   2 < 4 → [2, 4]
     *
     * 合并 [1] 和 [3]：
     *   1 < 3 → [1, 3]
     *
     * 合并 [2, 4] 和 [1, 3]：
     *   比较：1 < 2 → 接 1
     *   比较：2 < 3 → 接 2
     *   比较：3 < 4 → 接 3
     *   剩余：4 → 接 4
     *   结果：[1, 2, 3, 4] ✅
     *
     * ---
     *
     * 完整递归树：
     *
     *                    [4, 2, 1, 3]
     *                   /            \
     *              [4, 2]          [1, 3]
     *             /      \        /      \
     *           [4]      [2]   [1]      [3]
     *             \      /        \      /
     *              [2, 4]          [1, 3]
     *                   \          /
     *                  [1, 2, 3, 4]
     *
     * ---
     *
     * 合并过程详解（以 [2, 4] 和 [1, 3] 为例）：
     *
     * 初始状态：
     *   left:  2 → 4 → null
     *   right: 1 → 3 → null
     *   dummy → null
     *           ↑
     *          tail
     *
     * 第1轮：left.val=2, right.val=1
     *   1 < 2，选择 right
     *   tail.next = 1, tail = 1, right = 3
     *   dummy → 1 → null
     *           ↑
     *          tail
     *
     * 第2轮：left.val=2, right.val=3
     *   2 < 3，选择 left
     *   tail.next = 2, tail = 2, left = 4
     *   dummy → 1 → 2 → null
     *                ↑
     *               tail
     *
     * 第3轮：left.val=4, right.val=3
     *   3 < 4，选择 right
     *   tail.next = 3, tail = 3, right = null
     *   dummy → 1 → 2 → 3 → null
     *                     ↑
     *                    tail
     *
     * 循环结束，right == null
     *   tail.next = left (接上剩余的 4)
     *   dummy → 1 → 2 → 3 → 4 → null
     *
     * 最终结果：[1, 2, 3, 4] ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用快慢指针找中点？
     *    - 链表不支持随机访问，无法直接计算中点位置
     *    - 快慢指针只需一次遍历就能找到中点
     *    - slow 每次走1步，fast 每次走2步
     *    - 当 fast 到达末尾时，slow 刚好在中点
     *
     * 2️⃣ 为什么 fast 从 head.next 开始？
     *    - 这样当链表长度为偶数时，slow 停在前半部分的最后一个节点
     *    - 例如 [1, 2, 3, 4]，slow 停在 2，而不是 3
     *    - 保证分割均匀，避免一边过长
     *
     * 3️⃣ 为什么要断开链表（slow.next = null）？
     *    - 必须将链表真正分成两个独立的部分
     *    - 否则递归时会形成环，导致无限递归
     *    - first 和 second 成为两个独立的子链表
     *
     * 4️⃣ 递归的终止条件是什么？
     *    - head == null：空链表
     *    - head.next == null：单节点链表
     *    - 这两种情况都已经"有序"，直接返回
     *
     * 5️⃣ 合并两个有序链表的复杂度？
     *    - 时间复杂度：O(m + n)，m 和 n 是两个链表的长度
     *    - 空间复杂度：O(1)，只使用了常数个指针
     *    - 这是归并排序的核心操作
     *
     * 6️⃣ 为什么归并排序适合链表？
     *    - 不需要额外数组空间（数组归并需要 O(n) 辅助空间）
     *    - 只需要修改指针，不需要移动元素
     *    - 时间复杂度稳定 O(n log n)
     *    - 是链表排序的最优选择
     *
     * 7️⃣ 与数组归并排序对比？
     *    - 数组：需要 O(n) 辅助空间，分割 O(1)
     *    - 链表：需要 O(log n) 递归栈，分割 O(n/2)（找中点）
     *    - 但链表归并不需要额外的合并空间
     *    - 总体空间复杂度更优
     *
     * 8️⃣ 完整流程总结：
     *
     *    基本情况：
     *    ├─ head == null 或 head.next == null → 返回 head
     *
     *    分割阶段：
     *    ├─ 快慢指针找中点
     *    ├─ 断开链表：slow.next = null
     *    └─ 得到 first 和 second 两个子链表
     *
     *    递归阶段：
     *    ├─ first = sortList(first)
     *    └─ second = sortList(second)
     *
     *    合并阶段：
     *    └─ return mergeList(first, second)
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>边界检查</b>：必须先检查 head 和 head.next，单节点无需排序</li>
     *   <li><b>断开链表</b>：必须执行 slow.next = null，否则会导致无限递归</li>
     *   <li><b>快慢指针</b>：fast 从 head.next 开始，确保分割均匀</li>
     *   <li><b>递归终止</b>：空链表或单节点链表直接返回</li>
     *   <li><b>合并方法</b>：mergeList 要能处理 null 输入</li>
     *   <li><b>稳定性</b>：归并排序是稳定排序，相等元素的相对顺序不变</li>
     * </ul>
     *
     * @param head 链表的头节点
     * @return 排序后的链表头节点
     */
    public ListNode sortList(ListNode head) {
        // 🔍 边界检查：空链表或单节点链表已经有序
        if (head == null || head.next == null) {
            return head;
        }

        // 🏃 快慢指针找中点
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;       // 慢指针每次走1步
            fast = fast.next.next;  // 快指针每次走2步
        }

        // ✂️ 分割链表为两部分
        ListNode second = slow.next;
        slow.next = null;  // 断开链表，形成两个独立的子链表
        ListNode first = head;

        // 🔄 递归排序两半
        first = sortList(first);    // 排序前半部分
        second = sortList(second);  // 排序后半部分

        // 🔗 合并两个有序链表
        return mergeList(first, second);
    }

    /**
     * 合并两个有序链表
     *
     * @param left  第一个有序链表的头节点
     * @param right 第二个有序链表的头节点
     * @return 合并后的有序链表头节点
     */
    private ListNode mergeList(ListNode left, ListNode right) {
        // 🎭 创建 dummy 节点，简化边界处理
        ListNode dummy = new ListNode();
        // 👆 tail 指针始终指向结果链表的尾部
        ListNode tail = dummy;

        // 🔄 同时遍历两个链表，每次选择较小的节点
        while (left != null && right != null) {
            if (left.val < right.val) {
                tail.next = left;   // 选择 left 的节点
                left = left.next;   // left 指针后移
            } else {
                tail.next = right;  // 选择 right 的节点
                right = right.next; // right 指针后移
            }
            tail = tail.next;  // tail 指针后移
        }

        // 📎 将剩余部分接到 tail 后面
        tail.next = left == null ? right : left;

        // ✅ 返回合并后的链表头节点
        return dummy.next;
    }
}

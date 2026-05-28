package Hot100.链表;

/**
 * @author lxd
 **/
public class Hot023 {
    /**
     * <a href="https://leetcode.cn/problems/reverse-linked-list/description/">206. 反转链表</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。</p>
     *
     * <h3>💡 核心思路：迭代法（双指针）</h3>
     * <ul>
     *   <li><b>基本思想</b>：遍历链表，逐个改变节点的 next 指针方向，使其指向前一个节点
     *     <ul>
     *       <li>使用 pre 指针指向前一个节点（初始为 null）</li>
     *       <li>使用 cur 指针指向当前节点（初始为 head）</li>
     *       <li>在每次迭代中，保存 cur.next，然后将 cur.next 指向 pre</li>
     *       <li>更新 pre 和 cur 指针，继续处理下一个节点</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，需要遍历整个链表一次</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用了常数个额外指针</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：head = [1, 2, 3, 4, 5]
     *
     * 【初始状态】
     *   pre  = null
     *   cur  = 1 → 2 → 3 → 4 → 5 → null
     *
     * 【第1轮迭代】处理节点 1
     *   temp = cur.next = 2
     *   cur.next = pre (1 → null)
     *   pre = cur (pre = 1)
     *   cur = temp (cur = 2)
     *
     *   结果：null ← 1    2 → 3 → 4 → 5 → null
     *         pre↑      cur↑
     *
     * 【第2轮迭代】处理节点 2
     *   temp = cur.next = 3
     *   cur.next = pre (2 → 1)
     *   pre = cur (pre = 2)
     *   cur = temp (cur = 3)
     *
     *   结果：null ← 1 ← 2    3 → 4 → 5 → null
     *              pre↑      cur↑
     *
     * 【第3轮迭代】处理节点 3
     *   temp = cur.next = 4
     *   cur.next = pre (3 → 2)
     *   pre = cur (pre = 3)
     *   cur = temp (cur = 4)
     *
     *   结果：null ← 1 ← 2 ← 3    4 → 5 → null
     *                   pre↑      cur↑
     *
     * 【第4轮迭代】处理节点 4
     *   temp = cur.next = 5
     *   cur.next = pre (4 → 3)
     *   pre = cur (pre = 4)
     *   cur = temp (cur = 5)
     *
     *   结果：null ← 1 ← 2 ← 3 ← 4    5 → null
     *                        pre↑      cur↑
     *
     * 【第5轮迭代】处理节点 5
     *   temp = cur.next = null
     *   cur.next = pre (5 → 4)
     *   pre = cur (pre = 5)
     *   cur = temp (cur = null)
     *
     *   结果：null ← 1 ← 2 ← 3 ← 4 ← 5
     *                             pre↑
     *                             cur=null
     *
     * 【循环结束】cur == null，退出循环
     *   返回 pre，即新的头节点 5
     *
     * 最终结果：5 → 4 → 3 → 2 → 1 → null ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要用 temp 保存 cur.next？
     *    - 因为修改 cur.next 后会丢失原链表的后续节点
     *    - temp 起到"桥梁"作用，保存下一步要处理的节点
     *    - 没有 temp 就无法继续遍历原链表
     *
     * 2️⃣ 四步操作的顺序能改变吗？
     *    - 不能！顺序非常关键
     *    - 必须先保存 next（temp = cur.next）
     *    - 然后才能修改 cur.next（cur.next = pre）
     *    - 最后再移动 pre 和 cur
     *    - 顺序错误会导致链表断裂或死循环
     *
     * 3️⃣ 为什么 pre 初始化为 null？
     *    - 反转后，原头节点变成尾节点
     *    - 尾节点的 next 应该是 null
     *    - 所以第一个节点的 next 应该指向 null
     *
     * 4️⃣ 为什么返回 pre 而不是 cur？
     *    - 循环结束时，cur == null
     *    - pre 指向最后一个处理的节点，即新的头节点
     *    - 所以返回 pre
     *
     * 5️⃣ 边界情况处理？
     *    - 空链表（head == null）：直接返回 null
     *    - 单节点链表：循环执行一次，pre 指向该节点，返回 pre
     *    - 两种情况算法都能正确处理
     *
     * 6️⃣ 与递归方法对比？
     *    - 迭代：时间 O(n)，空间 O(1) ✅ 更优
     *    - 递归：时间 O(n)，空间 O(n)（递归栈）
     *    - 迭代方法不需要额外空间，是推荐解法
     *
     * 7️⃣ 内存图示理解：
     *    原始：  1→2→3→null
     *            ↑
     *           cur
     *
     *    第1步： 1→null  2→3→null
     *            ↑       ↑
     *           pre     cur
     *
     *    第2步： 1←2    3→null
     *                 ↑  ↑
     *                pre cur
     *
     *    第3步： 1←2←3
     *                    ↑
     *                   pre (cur=null)
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>操作顺序</b>：必须严格按照 temp → cur.next → pre → cur 的顺序执行</li>
     *   <li><b>空指针</b>：如果 head 为 null，直接返回 null</li>
     *   <li><b>返回值</b>：循环结束后返回 pre，不是 cur</li>
     *   <li><b>原地反转</b>：算法在原链表上操作，不创建新节点</li>
     *   <li><b>指针更新</b>：每次迭代都要同时更新 pre 和 cur</li>
     * </ul>
     *
     * @param head 链表的头节点
     * @return 反转后的链表头节点
     */
    public ListNode reverseList(ListNode head) {
        // 👈 pre 指向前一个节点，初始为 null（反转后尾节点指向 null）
        ListNode pre = null;
        // 👉 cur 指向当前节点，从头节点开始
        ListNode cur = head;

        // 🔄 遍历链表，逐个反转指针方向
        while (cur != null) {
            // 💾 保存下一个节点，防止链表断裂
            ListNode temp = cur.next;

            // 🔀 反转当前节点的指针，指向前一个节点
            cur.next = pre;

            // ⏭️ pre 和 cur 都向前移动一步
            pre = cur;    // pre 移到当前位置
            cur = temp;   // cur 移到下一个位置
        }

        // ✅ 循环结束时，pre 指向新的头节点（原链表的尾节点）
        return pre;
    }
}

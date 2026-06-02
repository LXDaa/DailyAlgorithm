package Hot100.链表;

/**
 * @author lxd
 **/
public class Hot029 {
    /**
     * <a href="https://leetcode.cn/problems/remove-nth-node-from-end-of-list/description/">19. 删除链表的倒数第 N 个结点</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。</p>
     *
     * <h3>💡 核心思路：快慢指针（一次遍历）</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用快慢指针，让快指针先走 n+1 步，然后快慢指针同时前进，当快指针到达末尾时，慢指针正好在倒数第 n+1 个节点
     *     <ul>
     *       <li>创建 dummy 节点，简化边界处理（特别是删除头节点的情况）</li>
     *       <li>fast 指针先走 n+1 步</li>
     *       <li>然后 slow 和 fast 同时前进，直到 fast 为 null</li>
     *       <li>此时 slow 指向倒数第 n+1 个节点，slow.next 就是倒数第 n 个节点</li>
     *       <li>执行删除操作：slow.next = slow.next.next</li>
     *     </ul>
     *   </li>
     *   <li><b>数学原理</b>：设链表长度为 L
     *     <ul>
     *       <li>fast 先走 n+1 步，剩余距离为 L-(n+1)</li>
     *       <li>slow 和 fast 同时走 L-(n+1) 步后，fast 到达 null</li>
     *       <li>slow 从头走了 L-(n+1) 步，位置是 L-n-1</li>
     *       <li>L-n-1 正好是倒数第 n+1 个节点的位置（从0开始计数）</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(L)，只需遍历链表一次</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用了常数个指针</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：head = [1, 2, 3, 4, 5], n = 2（删除倒数第2个节点，即节点4）
     *
     * 【步骤1】初始化
     *   dummy → 1 → 2 → 3 → 4 → 5 → null
     *   ↑
     *   slow, fast
     *
     * 【步骤2】fast 先走 n+1 = 3 步
     *   第1步：fast = 1
     *   第2步：fast = 2
     *   第3步：fast = 3
     *
     *   dummy → 1 → 2 → 3 → 4 → 5 → null
     *   ↑              ↑
     *   slow          fast
     *
     * 【步骤3】slow 和 fast 同时前进，直到 fast 为 null
     *   第1轮：slow = 1, fast = 4
     *   第2轮：slow = 2, fast = 5
     *   第3轮：slow = 3, fast = null
     *
     *   dummy → 1 → 2 → 3 → 4 → 5 → null
     *                ↑              ↑
     *               slow          fast=null
     *
     *   此时 slow 指向节点 3（倒数第3个节点，即倒数第 n+1 个）
     *   slow.next 指向节点 4（倒数第2个节点，即要删除的节点）
     *
     * 【步骤4】执行删除操作
     *   slow.next = slow.next.next
     *   即：3.next = 5
     *
     *   dummy → 1 → 2 → 3 → 5 → null
     *                      ↗
     *                  (跳过4)
     *
     * 最终结果：返回 dummy.next = [1, 2, 3, 5] ✅
     *
     * ---
     *
     * 特殊情况：删除头节点 head = [1], n = 1
     *
     * 【步骤1】初始化
     *   dummy → 1 → null
     *   ↑
     *   slow, fast
     *
     * 【步骤2】fast 先走 n+1 = 2 步
     *   第1步：fast = 1
     *   第2步：fast = null
     *
     *   dummy → 1 → null
     *   ↑          ↑
     *   slow      fast=null
     *
     * 【步骤3】fast 已经是 null，不进入循环
     *
     * 【步骤4】执行删除操作
     *   slow.next = slow.next.next
     *   即：dummy.next = null
     *
     *   dummy → null
     *
     * 最终结果：返回 dummy.next = null ✅
     *
     * ---
     *
     * 距离分析：
     *   链表长度 L = 5, n = 2
     *
     *   fast 先走 n+1 = 3 步
     *   剩余距离 = L - (n+1) = 5 - 3 = 2 步
     *
     *   slow 和 fast 同时走 2 步：
     *   slow 从 dummy(位置-1) 走到位置 1（节点3）
     *   fast 从位置 2（节点3）走到 null
     *
     *   slow 的位置 = L - n - 1 = 5 - 2 - 1 = 2（从0开始）
     *   即倒数第 n+1 = 3 个节点 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要用 dummy 节点？
     *    - 如果要删除的是头节点，没有 dummy 需要特殊处理
     *    - 有了 dummy，所有节点的删除操作都统一为 slow.next = slow.next.next
     *    - 最后返回 dummy.next 即可，代码更简洁
     *
     * 2️⃣ 为什么 fast 要先走 n+1 步而不是 n 步？
     *    - 如果 fast 走 n 步，当 fast 到达最后一个节点时
     *    - slow 会指向倒数第 n 个节点（要删除的节点）
     *    - 但删除节点需要知道前一个节点
     *    - 所以 fast 走 n+1 步，让 slow 停在倒数第 n+1 个节点
     *    - 这样 slow.next 就是要删除的节点
     *
     * 3️⃣ 为什么循环条件是 fast != null？
     *    - fast 先走了 n+1 步
     *    - 当 fast == null 时，说明 fast 已经走过整个链表
     *    - 此时 slow 正好在倒数第 n+1 个位置
     *    - 循环次数 = L - (n+1)
     *
     * 4️⃣ 边界情况处理？
     *    - 删除头节点：fast 先走 n+1 步后直接为 null，slow 仍在 dummy，删除 dummy.next
     *    - 删除尾节点：fast 走到 null 时，slow 在倒数第2个节点，删除最后一个
     *    - 单节点链表：算法能正确处理
     *
     * 5️⃣ 与两次遍历方法对比？
     *    - 两次遍历：第一次计算长度 L，第二次找到第 L-n 个节点删除
     *    - 两次遍历：时间 O(L)，但需要遍历两次
     *    - 快慢指针：时间 O(L)，只需遍历一次 ✅ 更优
     *
     * 6️⃣ 为什么只需要一次遍历？
     *    - fast 先走 n+1 步，建立了 fast 和 slow 之间的距离差
     *    - 这个距离差保证了当 fast 到达末尾时，slow 正好在目标位置的前一个
     *    - 巧妙利用了相对距离，避免了计算总长度
     *
     * 7️⃣ 完整流程总结：
     *    第一步：初始化
     *    ├─ 创建 dummy 节点，指向 head
     *    └─ slow 和 fast 都指向 dummy
     *
     *    第二步：建立距离差
     *    └─ fast 先走 n+1 步
     *
     *    第三步：同步移动
     *    └─ slow 和 fast 同时前进，直到 fast 为 null
     *
     *    第四步：删除节点
     *    └─ slow.next = slow.next.next
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>dummy 节点</b>：必须创建 dummy 节点，处理删除头节点的边界情况</li>
     *   <li><b>步数计算</b>：fast 先走 n+1 步，不是 n 步</li>
     *   <li><b>循环条件</b>：while (fast != null)，不是 fast.next != null</li>
     *   <li><b>删除操作</b>：slow.next = slow.next.next，不要遗漏</li>
     *   <li><b>返回值</b>：返回 dummy.next，不是 head（因为 head 可能被删除）</li>
     * </ul>
     *
     * @param head 链表的头节点
     * @param n    倒数第 n 个节点（从1开始）
     * @return 删除节点后的链表头节点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 🎭 创建 dummy 节点，简化边界处理
        ListNode dummy = new ListNode(0, head);
        // 👥 初始化快慢指针，都从 dummy 开始
        ListNode slow = dummy;
        ListNode fast = dummy;

        // 🏃 fast 指针先走 n+1 步，建立距离差
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // 🔄 slow 和 fast 同时前进，直到 fast 到达末尾
        while (fast != null) {
            slow = slow.next;  // slow 每次走1步
            fast = fast.next;  // fast 每次走1步
        }

        // ✂️ 删除倒数第 n 个节点（slow.next）
        slow.next = slow.next.next;

        // ✅ 返回删除后的链表头节点
        return dummy.next;
    }
}

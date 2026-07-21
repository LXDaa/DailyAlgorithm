package Hot100.技巧;

/**
 * @author lxd
 **/
public class Hot100 {
    /**
     * <a href="https://leetcode.cn/problems/find-the-duplicate-number/description/">287. 寻找重复数</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个包含 n + 1 个整数的数组 nums，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。</p>
     * <p>假设 nums 只有 一个重复的整数 ，返回这个重复的数。</p>
     * <p>你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。</p>
     *
     * <h3>💡 核心思路：快慢指针（Floyd 判圈算法）</h3>
     * <ul>
     *   <li><b>基本思想</b>：将数组视为链表，利用快慢指针找到环的入口
     *     <ul>
     *       <li>将数组索引视为链表节点</li>
     *       <li>nums[i] 表示从节点 i 指向节点 nums[i]</li>
     *       <li>重复的数字就是环的入口点</li>
     *       <li>阶段1：快慢指针找到相遇点</li>
     *       <li>阶段2：重置快指针，快慢指针以相同速度移动，相遇点就是环的入口</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么这个算法有效</b>：
     *     <ul>
     *       <li>由于数组只有一个重复数，必然形成一个环</li>
     *       <li>重复数是环的入口点</li>
     *       <li>Floyd 判圈算法可以在 O(1) 空间内找到环的入口</li>
     *       <li>不修改原数组</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，快慢指针遍历数组</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常量空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [1, 3, 4, 2, 2]
     *
     * 将数组视为链表：
     *   0 → 1 → 3 → 2 → 4 → 2（形成环）
     *                     ↑     |
     *                     └─────┘
     *   环的入口是节点 2，重复数是 2
     *
     * 阶段1：快慢指针找相遇点
     *   slow = 0, fast = 0
     *
     *   第1步：
     *     slow = nums[0] = 1
     *     fast = nums[nums[0]] = nums[1] = 3
     *     slow != fast
     *
     *   第2步：
     *     slow = nums[1] = 3
     *     fast = nums[nums[3]] = nums[2] = 4
     *     slow != fast
     *
     *   第3步：
     *     slow = nums[3] = 2
     *     fast = nums[nums[4]] = nums[2] = 4
     *     slow != fast
     *
     *   第4步：
     *     slow = nums[2] = 4
     *     fast = nums[nums[4]] = nums[2] = 4
     *     slow == fast = 4，相遇！
     *
     * 阶段2：重置快指针，找环的入口
     *   fast = 0
     *
     *   第1步：
     *     slow = nums[4] = 2
     *     fast = nums[0] = 1
     *     slow != fast
     *
     *   第2步：
     *     slow = nums[2] = 4
     *     fast = nums[1] = 3
     *     slow != fast
     *
     *   第3步：
     *     slow = nums[4] = 2
     *     fast = nums[3] = 2
     *     slow == fast = 2，相遇！
     *
     * 结果：重复数是 2 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么数组可以视为链表？
     *    - 数组索引范围：0 到 n
     *    - nums[i] 范围：1 到 n
     *    - 可以将 nums[i] 看作从 i 指向 nums[i] 的指针
     *    - 重复数会导致多个索引指向同一个位置，形成环
     *    - 例如：nums = [1,3,4,2,2]，索引 2 和 4 都指向 2
     *
     * 2️⃣ 为什么重复数是环的入口？
     *    - 假设重复数是 target
     *    - 至少有两个不同的索引 i 和 j，满足 nums[i] = nums[j] = target
     *    - 从 i 和 j 出发都会到达 target
     *    - target 是环的入口点
     *
     * 3️⃣ 阶段1：快慢指针如何找到相遇点？
     *    - slow 每次走一步：slow = nums[slow]
     *    - fast 每次走两步：fast = nums[nums[fast]]
     *    - 如果存在环，快慢指针一定会相遇
     *    - 相遇时，slow 和 fast 指向同一个位置
     *
     * 4️⃣ 阶段2：为什么重置快指针后相遇点是环的入口？
     *    - 设从起点到环入口的距离为 a
     *    - 设环的长度为 b
     *    - 相遇时：slow 走了 a + k*b，fast 走了 a + (k+m)*b
     *    - 由于 fast = 2*slow，可得 a + (k+m)*b = 2*(a + k*b)
     *    - 化简得 a = (m-k)*b
     *    - 所以从相遇点走 a 步会到达环入口
     *    - 同时从起点走 a 步也会到达环入口
     *    - 重置 fast 后，两者以相同速度移动，相遇点就是环入口
     *
     * 5️⃣ 为什么不用哈希表？
     *    - 哈希表需要 O(n) 空间
     *    - 题目要求 O(1) 空间
     *    - 快慢指针法满足空间要求
     *
     * 6️⃣ 为什么不修改原数组？
     *    - 题目要求不修改原数组
     *    - 快慢指针法只读取数组元素，不修改
     *    - 满足题目的限制条件
     *
     * 7️⃣ 时间复杂度分析：
     *    - 阶段1：O(n)，快慢指针最多遍历 n 次
     *    - 阶段2：O(n)，最多遍历 n 次
     *    - 总时间：O(n)
     *
     * 8️⃣ 完整流程总结：
     *
     *    // 阶段1：快慢指针找相遇点
     *    slow = 0, fast = 0
     *    do：
     *      slow = nums[slow]
     *      fast = nums[nums[fast]]
     *    while slow != fast
     *
     *    // 阶段2：重置快指针，找环的入口
     *    fast = 0
     *    while slow != fast：
     *      slow = nums[slow]
     *      fast = nums[fast]
     *
     *    return slow（环的入口，即重复数）
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>数组视为链表</b>：nums[i] 表示从 i 指向 nums[i]</li>
     *   <li><b>阶段1</b>：快慢指针找到相遇点</li>
     *   <li><b>阶段2</b>：重置快指针，相遇点就是环的入口</li>
     *   <li><b>不修改数组</b>：只读取数组元素，不修改</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常量空间</li>
     * </ul>
     *
     * @param nums 包含 n + 1 个整数的数组，数字都在 [1, n] 范围内
     * @return 重复的整数
     */
    public int findDuplicate(int[] nums) {
        // 📊 阶段1：快慢指针找相遇点
        int slow = 0, fast = 0;

        do {
            // 📌 slow 每次走一步
            slow = nums[slow];
            // 📌 fast 每次走两步
            fast = nums[nums[fast]];
        } while (slow != fast);

        // 📊 阶段2：重置快指针，找环的入口
        fast = 0;

        while (slow != fast) {
            // 📌 快慢指针以相同速度移动
            slow = nums[slow];
            fast = nums[fast];
        }

        // ✅ 返回环的入口，即重复数
        return slow;
    }
}

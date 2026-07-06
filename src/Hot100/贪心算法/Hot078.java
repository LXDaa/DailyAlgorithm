package Hot100.贪心算法;

/**
 * @author lxd
 **/
public class Hot078 {
    /**
     * <a href="https://leetcode.cn/problems/jump-game/description/">55. 跳跃游戏</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个非负整数数组 nums ，你最初位于数组的第一个下标。</p>
     * <p>数组中的每个元素代表你在该位置可以跳跃的最大长度。</p>
     * <p>判断你是否能够到达最后一个下标。</p>
     *
     * <h3>💡 核心思路：贪心算法</h3>
     * <ul>
     *   <li><b>基本思想</b>：遍历过程中维护当前能到达的最远位置
     *     <ul>
     *       <li>维护 coverRange：当前能到达的最远索引</li>
     *       <li>遍历每个位置 i（i <= coverRange）</li>
     *       <li>更新 coverRange = max(coverRange, i + nums[i])</li>
     *       <li>如果 coverRange >= nums.length - 1，返回 true</li>
     *       <li>遍历结束后返回 false</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用贪心</b>：
     *     <ul>
     *       <li>每次尽可能跳得更远</li>
     *       <li>coverRange 表示当前能到达的最远位置</li>
     *       <li>不需要记录具体路径，只需要知道能否到达终点</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，遍历一次数组</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常量空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例1：nums = [2,3,1,1,4]
     *
     * 目标：从索引0出发，能否到达索引4？
     *
     * 初始化：coverRange = 0
     *
     * i=0：
     *   i=0 <= coverRange=0 ✅
     *   coverRange = max(0, 0 + 2) = 2
     *   coverRange=2 < 4（终点）
     *   → coverRange=2
     *
     * i=1：
     *   i=1 <= coverRange=2 ✅
     *   coverRange = max(2, 1 + 3) = 4
     *   coverRange=4 >= 4（终点）→ return true ✅
     *
     * 示例2：nums = [3,2,1,0,4]
     *
     * 初始化：coverRange = 0
     *
     * i=0：
     *   coverRange = max(0, 0 + 3) = 3
     *   → coverRange=3
     *
     * i=1：
     *   coverRange = max(3, 1 + 2) = 3
     *   → coverRange=3
     *
     * i=2：
     *   coverRange = max(3, 2 + 1) = 3
     *   → coverRange=3
     *
     * i=3：
     *   coverRange = max(3, 3 + 0) = 3
     *   → coverRange=3
     *
     * i=4：
     *   i=4 > coverRange=3 ❌
     *   不进入循环
     *
     * 遍历结束：
     *   return false ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ coverRange 代表什么？
     *    - 当前能到达的最远索引
     *    - 初始值为 0（起始位置）
     *    - 每次更新为 max(coverRange, i + nums[i])
     *
     * 2️⃣ 循环条件为什么是 i <= coverRange？
     *    - 只有在能到达的范围内才需要处理
     *    - 如果 i > coverRange，说明无法到达该位置
     *    - 直接跳过，循环自然结束
     *
     * 3️⃣ 什么时候返回 true？
     *    - coverRange >= nums.length - 1
     *    - 即能到达或超过最后一个索引
     *    - 提前返回，优化性能
     *
     * 4️⃣ 为什么不需要记录路径？
     *    - 题目只需要判断能否到达，不需要具体路径
     *    - 贪心策略保证最优解
     *    - 简化计算
     *
     * 5️⃣ 如何处理 nums[i] = 0 的情况？
     *    - 如果在 coverRange 内遇到 0，不影响
     *    - coverRange 不会增加
     *    - 如果后面没有更大的跳跃能力，coverRange 不会扩展
     *    - 最终无法到达终点，返回 false
     *
     * 6️⃣ 时间复杂度分析：
     *    - 每个元素最多访问一次
     *    - 一旦 coverRange >= 终点，提前返回
     *    - 最坏情况 O(n)
     *
     * 7️⃣ 空间复杂度分析：
     *    - 只使用一个变量 coverRange
     *    - 空间复杂度 O(1)
     *
     * 8️⃣ 完整流程总结：
     *
     *    coverRange = 0
     *
     *    for i in 0..n-1：
     *      if i > coverRange → break（无法到达）
     *      coverRange = max(coverRange, i + nums[i])
     *      if coverRange >= n - 1 → return true
     *
     *    return false
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>循环条件</b>：i <= coverRange，只处理能到达的位置</li>
     *   <li><b>提前返回</b>：coverRange >= 终点时立即返回 true</li>
     *   <li><b>起点</b>：初始位置是索引 0</li>
     *   <li><b>元素非负</b>：nums[i] >= 0</li>
     *   <li><b>终点判断</b>：coverRange >= nums.length - 1</li>
     * </ul>
     *
     * @param nums 非负整数数组，每个元素表示该位置可以跳跃的最大长度
     * @return 是否能够到达最后一个下标
     */
    public boolean canJump(int[] nums) {
        // 📊 当前能到达的最远索引
        int coverRange = 0;

        // 🔄 遍历每个能到达的位置
        for (int i = 0; i <= coverRange; i++) {
            // 📈 更新最远能到达的范围
            coverRange = Math.max(coverRange, i + nums[i]);

            // ✅ 如果能到达终点，提前返回
            if (coverRange >= nums.length - 1) {
                return true;
            }
        }

        // ❌ 无法到达终点
        return false;
    }
}

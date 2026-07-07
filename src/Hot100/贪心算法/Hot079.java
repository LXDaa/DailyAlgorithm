package Hot100.贪心算法;

/**
 * @author lxd
 **/
public class Hot079 {
    /**
     * <a href="https://leetcode.cn/problems/jump-game-ii/description/">45. 跳跃游戏 II</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个非负整数数组 nums ，你最初位于数组的第一个位置。</p>
     * <p>数组中的每个元素代表你在该位置可以跳跃的最大长度。</p>
     * <p>你的目标是使用最少的跳跃次数到达数组的最后一个位置。</p>
     * <p>假设你总是可以到达数组的最后一个位置。</p>
     *
     * <h3>💡 核心思路：贪心算法</h3>
     * <ul>
     *   <li><b>基本思想</b>：遍历过程中维护当前能到达的最远位置和下一步能到达的最远位置
     *     <ul>
     *       <li>curRange：当前跳跃次数能到达的最远位置</li>
     *       <li>nextRange：下一步跳跃能到达的最远位置</li>
     *       <li>遍历每个位置，更新 nextRange</li>
     *       <li>当到达 curRange 时，进行一次跳跃，更新 curRange = nextRange</li>
     *       <li>如果 curRange >= 终点，提前退出</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用贪心</b>：
     *     <ul>
     *       <li>每次跳跃选择能到达最远位置的点</li>
     *       <li>局部最优导致全局最优</li>
     *       <li>保证最少跳跃次数</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，遍历一次数组</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常量空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [2,3,1,1,4]
     *
     * 目标：用最少跳跃次数到达索引4
     *
     * 初始化：step=0, curRange=0, nextRange=0
     *
     * i=0：
     *   nextRange = max(0, 0 + 2) = 2
     *   i=0 == curRange=0 ✅
     *   curRange != 4，需要跳跃
     *   step++ = 1
     *   curRange = nextRange = 2
     *   curRange=2 < 4，继续
     *   → step=1, curRange=2, nextRange=2
     *
     * i=1：
     *   nextRange = max(2, 1 + 3) = 4
     *   i=1 != curRange=2 ❌
     *   → step=1, curRange=2, nextRange=4
     *
     * i=2：
     *   nextRange = max(4, 2 + 1) = 4
     *   i=2 == curRange=2 ✅
     *   curRange != 4，需要跳跃
     *   step++ = 2
     *   curRange = nextRange = 4
     *   curRange=4 >= 4，break
     *   → step=2
     *
     * 遍历结束：
     *   return 2 ✅
     *
     * 示例2：nums = [2,1]
     *   i=0: nextRange=2, i==curRange, step++=1, curRange=2 >= 1, break
     *   return 1 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ curRange 和 nextRange 分别代表什么？
     *    - curRange：当前跳跃次数下能到达的最远位置
     *    - nextRange：下一步跳跃能到达的最远位置
     *    - 每次到达 curRange 时，必须进行一次跳跃
     *
     * 2️⃣ 什么时候进行跳跃？
     *    - 当 i == curRange 时
     *    - 表示已经到达当前跳跃的最远位置
     *    - 需要更新 step 和 curRange
     *
     * 3️⃣ 为什么遍历到 nums.length - 1？
     *    - i < nums.length - 1（不包含最后一个元素）
     *    - 当到达最后一个元素时，不需要再跳跃
     *    - 避免多跳一次
     *
     * 4️⃣ 如何提前退出？
     *    - 更新 curRange 后，检查 curRange >= nums.length - 1
     *    - 如果满足，直接 break
     *    - 优化性能
     *
     * 5️⃣ step 如何初始化？
     *    - 初始化为 0
     *    - 第一次跳跃时 step++
     *    - 最少需要 0 次跳跃（数组长度为 1）
     *
     * 6️⃣ nextRange 如何更新？
     *    - nextRange = max(nextRange, i + nums[i])
     *    - 遍历过程中持续更新
     *    - 记录下一步能到达的最远位置
     *
     * 7️⃣ 为什么不需要检查是否能到达？
     *    - 题目保证总是可以到达最后一个位置
     *    - 无需额外判断
     *    - 简化逻辑
     *
     * 8️⃣ 完整流程总结：
     *
     *    step = 0
     *    curRange = 0
     *    nextRange = 0
     *
     *    for i in 0..n-2：
     *      nextRange = max(nextRange, i + nums[i])
     *      if i == curRange：
     *        if curRange != n - 1：
     *          step++
     *          curRange = nextRange
     *          if curRange >= n - 1：
     *            break
     *
     *    return step
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>遍历范围</b>：i < nums.length - 1，不包含最后一个元素</li>
     *   <li><b>跳跃时机</b>：当 i == curRange 时进行跳跃</li>
     *   <li><b>提前退出</b>：curRange >= 终点时 break</li>
     *   <li><b>初始化</b>：step=0, curRange=0, nextRange=0</li>
     *   <li><b>题目保证</b>：总是可以到达终点</li>
     * </ul>
     *
     * @param nums 非负整数数组，每个元素表示该位置可以跳跃的最大长度
     * @return 到达最后一个位置的最少跳跃次数
     */
    public int jump(int[] nums) {
        // 📊 跳跃次数
        int step = 0;

        // 📊 当前跳跃能到达的最远位置
        int curRange = 0;

        // 📊 下一步跳跃能到达的最远位置
        int nextRange = 0;

        // 🔄 遍历数组（不包含最后一个元素）
        for (int i = 0; i < nums.length - 1; i++) {
            // 📈 更新下一步能到达的最远位置
            nextRange = Math.max(nextRange, i + nums[i]);

            // 🏃 到达当前跳跃的最远位置，需要进行一次跳跃
            if (i == curRange) {
                // 📍 如果还没到达终点
                if (curRange != nums.length - 1) {
                    // ➕ 跳跃次数 + 1
                    step++;
                    // 📍 更新当前能到达的最远位置
                    curRange = nextRange;

                    // ✅ 如果已到达或超过终点，提前退出
                    if (curRange >= nums.length - 1) {
                        break;
                    }
                }
            }
        }

        // ✅ 返回最少跳跃次数
        return step;
    }
}

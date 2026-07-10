package Hot100.动态规划;

import java.util.Arrays;

/**
 * @author lxd
 **/
public class Hot085 {
    /**
     * <a href="https://leetcode.cn/problems/coin-change/description/">322. 零钱兑换</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。</p>
     * <p>计算并返回可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1 。</p>
     * <p>你可以认为每种硬币的数量是无限的。</p>
     *
     * <h3>💡 核心思路：动态规划（完全背包问题）</h3>
     * <ul>
     *   <li><b>基本思想</b>：将问题转化为完全背包问题
     *     <ul>
     *       <li>定义 dp[i]：凑成金额 i 所需的最少硬币个数</li>
     *       <li>递推公式：dp[i] = min(dp[i], dp[i - coin] + 1)</li>
     *       <li>初始状态：dp[0] = 0, dp[1..amount] = amount + 1</li>
     *       <li>外层遍历金额 i，内层遍历硬币 coin</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么是完全背包</b>：
     *     <ul>
     *       <li>每种硬币的数量是无限的</li>
     *       <li>求最少硬币数，相当于求最小物品数</li>
     *       <li>完全背包的内层循环正序遍历</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(amount * coins.length)</li>
     *   <li><b>空间复杂度</b>：O(amount)，使用 dp 数组</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：coins = [1, 2, 5], amount = 11
     *
     * 初始化：dp = [0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12]
     *
     * i=1：
     *   coin=1: dp[1] = min(12, dp[0]+1) = 1
     *   coin=2: 2 > 1, 跳过
     *   coin=5: 5 > 1, 跳过
     *   → dp[1] = 1
     *
     * i=2：
     *   coin=1: dp[2] = min(12, dp[1]+1) = 2
     *   coin=2: dp[2] = min(2, dp[0]+1) = 1
     *   coin=5: 5 > 2, 跳过
     *   → dp[2] = 1
     *
     * i=3：
     *   coin=1: dp[3] = min(12, dp[2]+1) = 2
     *   coin=2: dp[3] = min(2, dp[1]+1) = 2
     *   coin=5: 5 > 3, 跳过
     *   → dp[3] = 2
     *
     * i=4：
     *   coin=1: dp[4] = min(12, dp[3]+1) = 3
     *   coin=2: dp[4] = min(3, dp[2]+1) = 2
     *   coin=5: 5 > 4, 跳过
     *   → dp[4] = 2
     *
     * i=5：
     *   coin=1: dp[5] = min(12, dp[4]+1) = 3
     *   coin=2: dp[5] = min(3, dp[3]+1) = 3
     *   coin=5: dp[5] = min(3, dp[0]+1) = 1
     *   → dp[5] = 1
     *
     * ...继续递推...
     *
     * i=11：
     *   coin=1: dp[11] = min(INF, dp[10]+1) = dp[10]+1
     *   coin=2: dp[11] = min(dp[11], dp[9]+1)
     *   coin=5: dp[11] = min(dp[11], dp[6]+1)
     *   → dp[11] = 3（5 + 5 + 1 = 11）
     *
     * 结果：dp[11] = 3 ✅
     *
     * 示例2：coins = [2], amount = 3
     *   无法组成，返回 -1 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ dp 数组的定义？
     *    - dp[i] 表示凑成金额 i 所需的最少硬币个数
     *    - i 从 0 开始，dp 数组大小为 amount + 1
     *    - 最终答案为 dp[amount]
     *
     * 2️⃣ 初始状态如何设置？
     *    - dp[0] = 0（金额为 0，需要 0 个硬币）
     *    - dp[1..amount] = amount + 1（初始化为一个不可能的值）
     *    - 使用 amount + 1 作为最大值，因为最多需要 amount 个 1 元硬币
     *    - 如果 dp[amount] > amount，说明无法组成
     *
     * 3️⃣ 递推公式是什么？
     *    - dp[i] = min(dp[i], dp[i - coin] + 1)
     *    - 当前不选 coin：dp[i]（保持原值）
     *    - 当前选 coin：dp[i - coin] + 1（加上当前这个硬币）
     *    - 取两者最小值
     *
     * 4️⃣ 为什么是完全背包？
     *    - 每种硬币的数量是无限的
     *    - 内层循环正序遍历，允许重复使用
     *    - 如果是 0-1 背包，内层循环需要倒序
     *
     * 5️⃣ 如何判断无法组成？
     *    - 如果 dp[amount] > amount，返回 -1
     *    - 因为最多需要 amount 个 1 元硬币
     *    - 如果 dp[amount] 超过这个值，说明无法组成
     *
     * 6️⃣ 循环顺序如何？
     *    - 外层遍历金额 i（从 1 到 amount）
     *    - 内层遍历硬币 coin
     *    - 对于每个金额，尝试使用每种硬币
     *
     * 7️⃣ 时间复杂度分析：
     *    - 外层循环：amount 次
     *    - 内层循环：coins.length 次
     *    - 总时间：O(amount * coins.length)
     *
     * 8️⃣ 完整流程总结：
     *
     *    dp = new int[amount+1]
     *    Arrays.fill(dp, amount+1)
     *    dp[0] = 0
     *
     *    for i = 1; i <= amount; i++：
     *      for coin in coins：
     *        if coin <= i：
     *          dp[i] = min(dp[i], dp[i-coin] + 1)
     *
     *    return dp[amount] > amount ? -1 : dp[amount]
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>初始化</b>：dp[0]=0，其余为 amount+1</li>
     *   <li><b>完全背包</b>：内层循环正序遍历，允许重复使用</li>
     *   <li><b>硬币面值</b>：coin <= i 时才进行计算</li>
     *   <li><b>无法组成</b>：dp[amount] > amount 时返回 -1</li>
     *   <li><b>递推公式</b>：dp[i] = min(dp[i], dp[i-coin]+1)</li>
     * </ul>
     *
     * @param coins  不同面额的硬币数组
     * @param amount 总金额
     * @return 凑成总金额所需的最少硬币个数，无法组成时返回 -1
     */
    public int coinChange(int[] coins, int amount) {
        // 📦 创建 dp 数组
        int[] dp = new int[amount + 1];

        // 📋 初始化：填充为 amount+1（不可能的值），dp[0] = 0
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        // 🔄 外层循环：遍历金额
        for (int i = 1; i <= amount; i++) {
            // 🔄 内层循环：遍历硬币（完全背包，正序遍历）
            for (int coin : coins) {
                // 📌 只有硬币面值 <= 当前金额时才能使用
                if (coin <= i) {
                    // 📊 递推公式：选或不选当前硬币，取最小值
                    dp[i] = Math.min(dp[i - coin] + 1, dp[i]);
                }
            }
        }

        // ✅ 如果无法组成，返回 -1；否则返回最少硬币数
        return dp[amount] > amount ? -1 : dp[amount];
    }
}

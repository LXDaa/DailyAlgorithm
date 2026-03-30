package CodeTop;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/coin-change/description/">322. 零钱兑换</a>
 * <p>
 * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
 * <p>
 * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
 * <p>
 * 你可以认为每种硬币的数量是无限的。
 *
 * <h2>解题思路</h2>
 * <h3>1. 题目分析</h3>
 * <ul>
 *     <li><strong>完全背包问题</strong>：每种硬币数量无限，可以重复使用</li>
 *     <li><strong>最优化问题</strong>：求最少硬币个数，适合用动态规划</li>
 *     <li><strong>无后效性</strong>：当前状态只与之前的状态有关</li>
 * </ul>
 * <p>
 * <h3>2. 动态规划五部曲</h3>
 * <h4>(1) dp 数组定义</h4>
 * <ul>
 *     <li><code>dp[i]</code> 表示凑成金额 i 所需的最少硬币数</li>
 *     <li>数组大小：amount + 1（包含金额 0 到 amount）</li>
 * </ul>
 * <p>
 * <h4>(2) 递推公式</h4>
 * <ul>
 *     <li>对于每个金额 i，遍历所有硬币面额 coin</li>
 *     <li>如果 coin ≤ i，则可以使用这枚硬币</li>
 *     <li><code>dp[i] = min(dp[i], dp[i - coin] + 1)</code></li>
 *     <li>含义：使用当前硬币后的硬币数 vs 不使用当前硬币的硬币数，取较小值</li>
 * </ul>
 * <p>
 * <h4>(3) 初始化</h4>
 * <ul>
 *     <li><code>dp[0] = 0</code>：凑成金额 0 需要 0 个硬币</li>
 *     <li><code>dp[i] = amount + 1</code>：初始化为一个不可能达到的大值（相当于无穷大）</li>
 *     <li>选择 amount + 1 的原因：最多只需要 amount 个硬币（全用 1 元），所以 amount + 1 是不可能达到的值</li>
 * </ul>
 * <p>
 * <h4>(4) 遍历顺序</h4>
 * <ul>
 *     <li>外层循环：从小到大遍历金额（1 到 amount）</li>
 *     <li>内层循环：遍历所有硬币面额</li>
 *     <li>这样可以保证计算 dp[i] 时，dp[i-coin] 已经被计算过</li>
 * </ul>
 * <p>
 * <h4>(5) 返回值处理</h4>
 * <ul>
 *     <li>如果 dp[amount] > amount，说明无法凑成目标金额，返回 -1</li>
 *     <li>否则返回 dp[amount]</li>
 * </ul>
 * <p>
 * <h3>3. 算法示意图</h3>
 * <pre>
 * 示例：coins = [1, 2, 5], amount = 11
 *
 * 初始化:
 * dp = [0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12]
 *       ↑
 *    dp[0]=0
 *
 * i=1 (金额 1):
 *   coin=1: dp[1] = min(12, dp[0]+1) = 1
 *   dp = [0, 1, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12]
 *
 * i=2 (金额 2):
 *   coin=1: dp[2] = min(12, dp[1]+1) = 2
 *   coin=2: dp[2] = min(2, dp[0]+1) = 1  ✅
 *   dp = [0, 1, 1, 12, 12, 12, 12, 12, 12, 12, 12, 12]
 *
 * i=3 (金额 3):
 *   coin=1: dp[3] = min(12, dp[2]+1) = 2
 *   coin=2: dp[3] = min(2, dp[1]+1) = 2
 *   coin=5: 跳过 (5>3)
 *   dp = [0, 1, 1, 2, 12, 12, 12, 12, 12, 12, 12, 12]
 *
 * ... 继续计算 ...
 *
 * i=11 (金额 11):
 *   coin=1: dp[11] = min(12, dp[10]+1)
 *   coin=2: dp[11] = min(..., dp[9]+1)
 *   coin=5: dp[11] = min(..., dp[6]+1)
 *   最终 dp[11] = 5 (5+5+1 或 5+2+2+2 等)
 *
 * 返回结果：5
 * </pre>
 * <p>
 * <h3>4. 复杂度分析</h3>
 * <ul>
 *     <li><strong>时间复杂度</strong>：O(amount × n)，其中 n 是硬币种类数</li>
 *     <li><strong>空间复杂度</strong>：O(amount)，dp 数组的大小</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260327 {
    public int coinChange(int[] coins, int amount) {
        // 📦 dp 数组：dp[i] 表示凑成金额 i 所需的最少硬币数
        int[] dp = new int[amount + 1];

        // 🔢 初始化：将所有值设为 amount + 1（相当于无穷大）
        // 原因：最多只需要 amount 个硬币（全用 1 元），所以 amount + 1 是不可能达到的值
        Arrays.fill(dp, amount + 1);

        // 🎯 基础情况：凑成金额 0 需要 0 个硬币
        dp[0] = 0;

        // 🔄 外层循环：从小到大遍历所有金额（从 1 到 amount）
        for (int i = 1; i <= amount; i++) {
            // 🔄 内层循环：遍历所有硬币面额
            for (int coin : coins) {
                // ✅ 只有当硬币面额小于等于当前金额时才能使用
                if (coin <= i) {
                    // ⚖️ 状态转移方程：
                    // dp[i] = min(不使用当前硬币，使用当前硬币)
                    // dp[i - coin] + 1 表示使用一枚当前硬币后的总硬币数
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        // 🎯 判断是否有解：
        // 如果 dp[amount] > amount，说明无法凑成目标金额（值仍然是初始的大值）
        if (dp[amount] > amount) {
            return -1;
        }

        // ✅ 返回凑成目标金额的最少硬币数
        return dp[amount];
    }
}

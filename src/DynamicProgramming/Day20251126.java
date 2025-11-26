package DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/coin-change/description/">322.零钱兑换</a>
 * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
 * <p>
 * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
 * <p>
 * 你可以认为每种硬币的数量是无限的。
 * <p>
 * 解题思路：
 * 这是一个典型的动态规划问题，类似于完全背包问题。
 * 我们定义 dp[i] 表示凑成金额 i 所需的最少硬币数量。
 * 状态转移方程：dp[i] = min(dp[i], dp[i - coins[j]] + 1)
 * 其中 coins[j] 是当前考虑的硬币面额，+1 表示使用了这枚硬币。
 * <p>
 * 初始化：
 * dp[0] = 0 （凑成金额0需要0枚硬币）
 * dp[1..amount] 初始化为 amount+1 （作为无穷大的标记，因为最多只需要 amount 枚硬币）
 *
 * @author lxd
 **/
public class Day20251126 {
    public int coinChange(int[] coins, int amount) {
        // dp[i] 表示凑成金额 i 所需的最少硬币数量
        int[] dp = new int[amount + 1];

        // 初始化：将 dp[1] 到 dp[amount] 设置为 amount+1（相当于正无穷）
        // 因为我们要求最小值，所以不能初始化为0
        // amount+1 是一个安全的上界，因为即使只用面值为1的硬币，也最多只需要 amount 枚
        for (int i = 1; i <= amount; i++) {
            dp[i] = amount + 1;

            // 遍历所有硬币面额
            for (int j = 0; j < coins.length; j++) {
                // 如果当前硬币面额不超过目标金额 i
                if (coins[j] <= i) {
                    // 状态转移方程：尝试使用第 j 枚硬币
                    // dp[i-coins[j]] + 1 表示使用一枚 coins[j] 面额的硬币，
                    // 并加上凑成金额 i-coins[j] 所需的最少硬币数
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }

        // 如果 dp[amount] 的值仍大于 amount，说明无法凑成该金额，返回 -1
        // 否则返回计算得到的最少硬币数
        return dp[amount] > amount ? -1 : dp[amount];
    }
}
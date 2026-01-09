package CodeMusings.DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/coin-change-ii/description/">518. 零钱兑换 II</a>
 * <p>
 * 题目描述：
 * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
 * <p>
 * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
 * <p>
 * 假设每一种面额的硬币有无限个。
 * <p>
 * 题目数据保证结果符合 32 位带符号整数。
 * <p>
 * 解题思路：
 * 这是一个典型的完全背包问题。我们需要找出能组成指定金额的硬币组合数量。
 * 使用动态规划方法：
 * 1. 定义状态：dp[i] 表示能组成金额 i 的硬币组合数
 * 2. 初始化：dp[0] = 1（组成金额0只有一种方式：不选任何硬币）
 * 3. 状态转移方程：对于每个硬币coin，更新dp数组：
 * dp[i] = dp[i] + dp[i-coin] (当i >= coin时)
 * 4. 遍历顺序：外层遍历硬币，内层遍历金额（确保每种硬币只使用一次在组合计数中）
 *
 * @author lxd
 **/
public class Day20251124 {
    public int change(int amount, int[] coins) {
        // dp[i] 表示能组成金额 i 的硬币组合数
        int[] dp = new int[amount + 1];
        // 初始化：组成金额0的方式数为1（即不选择任何硬币）
        dp[0] = 1;

        // 遍历每种面额的硬币
        for (int coin : coins) {
            // 从当前硬币面额开始，更新所有可能的金额组合数
            // 小于coin的金额无法用当前硬币组成，所以从coin开始遍历
            for (int i = coin; i <= amount; i++) {
                // 状态转移方程：
                // 当前金额i的组合数 = 之前已知的组合数 + 使用当前硬币后剩余金额(i-coin)的组合数
                dp[i] += dp[i - coin];
            }
        }

        // 返回组成目标金额的组合数
        return dp[amount];
    }
}
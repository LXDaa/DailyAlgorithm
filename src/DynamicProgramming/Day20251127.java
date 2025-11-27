package DynamicProgramming;

import java.util.Arrays;

/**
 *
 * <a href="https://leetcode.cn/problems/perfect-squares/description/">279. 完全平方数</a>
 * <p>
 * 题目描述：
 * 给你一个整数 n ，返回和为 n 的完全平方数的最少数量。
 * 完全平方数是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。
 * 例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
 * <p>
 * 解题思路：
 * 使用动态规划方法，类似于完全背包问题。
 * 将所有小于等于n的完全平方数看作物品，目标值n看作背包容量。
 * dp[j] 表示组成数字 j 所需的最少完全平方数个数。
 *
 * @author lxd
 **/
public class Day20251127 {
    public int numSquares(int n) {
        // dp[j] 表示组成数字 j 所需的最少完全平方数个数
        int[] dp = new int[n + 1];
        // 初始化为最大值，表示尚未找到组合方式
        Arrays.fill(dp, Integer.MAX_VALUE);
        // 基础情况：组成数字0需要0个完全平方数
        dp[0] = 0;

        // 遍历所有可能的完全平方数 i*i <= n
        for (int i = 1; i * i <= n; i++) {
            // 对于每个完全平方数 i*i，更新它能影响到的所有数字
            for (int j = i * i; j <= n; j++) {
                // 状态转移方程：dp[j] = min(dp[j], dp[j - i*i] + 1)
                if (dp[j - i * i] != Integer.MAX_VALUE) {
                    dp[j] = Math.min(dp[j], dp[j - i * i] + 1);
                }
            }
        }
        return dp[n];
    }
}
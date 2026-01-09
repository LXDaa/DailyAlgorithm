package CodeMusings.DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/integer-break/description/">343. 整数拆分</a>
 * <p>
 * 给定一个正整数 n ，将其拆分为 k 个 正整数 的和（ k >= 2 ），并使这些整数的乘积最大化。
 * <p>
 * 返回 你可以获得的最大乘积 。
 * <p>
 * 解题思路:
 * 这是一个经典的动态规划问题。对于每个数字 i，我们需要找到将其拆分后能得到的最大乘积。
 * <p>
 * 状态定义:
 * dp[i] 表示将正整数 i 拆分成至少两个正整数之和后，这些正整数的最大乘积。
 * <p>
 * 状态转移方程:
 * 对于数字 i，我们可以将其拆分为 j 和 (i-j) 两部分，其中 1 <= j < i。
 * 对于每一组拆分，我们有两种选择:
 * 1. 直接计算 j * (i-j)，不继续拆分 i-j
 * 2. 计算 j * dp[i-j]，即 j 乘以 i-j 拆分后的最大乘积
 * 取两者的较大值，再与当前 dp[i] 比较，取最大值作为新的 dp[i]。
 * <p>
 * 初始化:
 * dp[1] = 0（1无法拆分成两个正整数）
 * <p>
 * 优化:
 * 根据数学分析，尽可能将数字拆分为3的倍数可以获得最大乘积。
 */
public class Day20251111 {
    /**
     * 计算整数拆分后的最大乘积
     *
     * @param n 待拆分的正整数
     * @return 拆分后各数字的最大乘积
     */
    public int integerBreak(int n) {
        // dp[i] 表示数字 i 拆分后的最大乘积
        int[] dp = new int[n + 1];

        // 从 2 开始计算，因为 1 无法拆分成两个正整数
        for (int i = 2; i <= n; i++) {
            // 尝试将数字 i 拆分成 j 和 (i-j) 两部分
            for (int j = 1; j < i; j++) {
                // 对于每种拆分方式，比较以下三种情况的最大值：
                // 1. 当前记录的最大乘积 dp[i]
                // 2. j * (i-j) 直接相乘（不继续拆分 i-j）
                // 3. j * dp[i-j]（继续拆分 i-j）
                dp[i] = Math.max(dp[i], Math.max(j * (i - j), j * dp[i - j]));
            }
        }
        return dp[n];
    }
}
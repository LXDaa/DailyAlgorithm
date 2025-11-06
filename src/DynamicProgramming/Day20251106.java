package DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/min-cost-climbing-stairs/description/">746. 使用最小花费爬楼梯</a>
 * <p>
 * 给你一个整数数组 cost ，其中 cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用。一旦你支付此费用，即可选择向上爬一个或者两个台阶。
 * <p>
 * 你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
 * <p>
 * 请你计算并返回达到楼梯顶部的最低花费。
 * <p>
 * 解题思路:
 * 这是一个典型的动态规划问题。
 * 1. 状态定义: dp[i] 表示到达第 i 个台阶(或楼梯顶部)的最小花费
 * 2. 状态转移方程: 
 *    - dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])
 *    - 即到达第 i 个位置可以从 i-1 或 i-2 位置上来，取两者中的较小值
 * 3. 初始状态: dp[0] = dp[1] = 0 (可以从下标0或1开始，所以初始花费为0)
 * 4. 最终结果: dp[n] (n为cost数组长度，表示楼梯顶部)
 */
public class Day20251106 {
    /**
     * 计算爬楼梯的最小花费
     * 
     * @param cost 每个台阶向上爬需要支付的费用数组
     * @return 到达楼梯顶部的最低花费
     */
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        // dp[i] 表示到达第 i 个台阶的最小花费
        // dp[0] 和 dp[1] 初始化为 0，因为可以选择从下标为 0 或 1 的台阶开始
        int[] dp = new int[n + 1];
        
        // 从第 2 个台阶开始计算，因为前两个台阶可以免费选择
        for (int i = 2; i <= n; i++) {
            // 到达第 i 个台阶有两种方式：
            // 1. 从第 i-1 个台阶爬1步上来，花费为 dp[i-1] + cost[i-1]
            // 2. 从第 i-2 个台阶爬2步上来，花费为 dp[i-2] + cost[i-2]
            // 取两者的最小值作为到达第 i 个台阶的最小花费
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }
        
        // dp[n] 即为到达楼梯顶部的最低花费
        return dp[n];
    }
}
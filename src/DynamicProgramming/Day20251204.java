package DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/description/">121. 买卖股票的最佳时机</a>
 * <p>
 * 题目描述:
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 * <p>
 * 解题思路:
 * 方法一：贪心算法
 * - 核心思想: 在遍历过程中维护历史最低价格，同时计算当前价格与历史最低价格的差值
 * - 时间复杂度: O(n)，只需遍历一次数组
 * - 空间复杂度: O(1)，只使用常量额外空间
 * - 关键: 只能买卖一次，所以只需要找到最低点买入，最高点卖出的差值
 * <p>
 * 方法二：动态规划
 * - 状态定义:
 * dp[i][0] 表示第 i 天不持有股票时的最大利润
 * dp[i][1] 表示第 i 天持有股票时的最大利润
 * - 状态转移方程:
 * dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])  // 保持不持有 或 卖出
 * dp[i][1] = max(dp[i-1][1], -prices[i])             // 保持持有 或 买入（只能买入一次）
 * - 初始状态:
 * dp[0][0] = 0           // 第0天不持有股票，利润为0
 * dp[0][1] = -prices[0]  // 第0天持有股票，利润为负的股票价格
 * - 时间复杂度: O(n)
 * - 空间复杂度: O(n)，可优化为 O(1)
 *
 * @author lxd
 **/
public class Day20251204 {


    /**
     * 方法一：贪心算法
     * 思路：遍历数组，维护历史最低价格，计算当前价格与最低价格的差值作为利润
     *
     * @param prices 股票每天的价格数组
     * @return 最大利润
     */
    public int maxProfit(int[] prices) {
        // 记录遍历过程中的最低价格
        int minPrice = Integer.MAX_VALUE;
        // 记录最大利润
        int maxProfit = 0;

        // 遍历每一天的价格
        for (int price : prices) {
            if (price <= minPrice) {
                // 更新历史最低价格（这一天适合买入）
                minPrice = price;
            } else {
                // 计算当前价格卖出的利润（当前价格 - 历史最低价格）
                int profit = price - minPrice;
                // 更新最大利润
                maxProfit = maxProfit < profit ? profit : maxProfit;
            }
        }
        return maxProfit;
    }

    /**
     * 方法二：动态规划
     * 思路：使用二维数组 dp[i][j] 表示第 i 天的状态
     * dp[i][0] 表示第 i 天不持有股票的最大利润
     * dp[i][1] 表示第 i 天持有股票的最大利润
     *
     * @param prices 股票每天的价格数组
     * @return 最大利润
     */
    public int maxProfit2(int[] prices) {
        int n = prices.length;
        // dp[i][0] 表示第 i 天不持有股票的最大利润
        // dp[i][1] 表示第 i 天持有股票的最大利润
        int[][] dp = new int[n][2];

        // 初始状态：第 0 天
        dp[0][0] = 0;           // 第 0 天不持有股票，利润为 0
        dp[0][1] = -prices[0];  // 第 0 天持有股票，花费 prices[0]，利润为负

        // 状态转移
        for (int i = 1; i < n; i++) {
            // 第 i 天不持有股票的最大利润 = max(前一天就不持有, 前一天持有今天卖出)
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);

            // 第 i 天持有股票的最大利润 = max(前一天就持有, 今天买入)
            // 注意：因为只能买卖一次，所以买入时利润为 -prices[i]（不是 dp[i-1][0] - prices[i]）
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }

        // 最后一天不持有股票的利润一定大于等于持有股票的利润
        return dp[n - 1][0];
    }
}

package CodeMusings.Greedy;

/**
 * <a href="https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/description/">122.买卖股票的最佳时机 II</a>
 * <p>
 * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
 * <p>
 * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。然而，你可以在 同一天 多次买卖该股票，但要确保你持有的股票不超过一股。
 * <p>
 * 返回 你能获得的 最大 利润 。
 * 
 * 解题思路:
 * 这是一个典型的贪心算法问题。核心思想是只要明天价格比今天高，就在今天买入明天卖出。
 * 我们不需要真正追踪哪天买入哪天卖出，只需要累加所有上涨区间的差值即可。
 * 例如：如果价格连续几天为 [1,2,3,4]，我们可以第一天买入第四天卖出获利3，
 * 也可以每天买入第二天卖出，即(2-1)+(3-2)+(4-3)=3，结果相同。
 * 所以我们只需找出所有递增的相邻两天，将差值累加就是最大利润。
 */
public class Day20251017 {
    /**
     * 计算股票买卖的最大利润
     * @param prices 股票每天的价格数组
     * @return 能获得的最大利润
     */
    public int maxProfit(int[] prices) {
        // 初始化总利润为0
        int res = 0;
        
        // 从第二天开始遍历，比较相邻两天的价格
        for (int i = 1; i < prices.length; i++) {
            // 如果今天价格比昨天高，则将差值计入总利润
            // 这相当于在昨天买入今天卖出
            if (prices[i] > prices[i - 1]) {
                res += prices[i] - prices[i - 1];
            }
        }
        
        return res;
    }
}
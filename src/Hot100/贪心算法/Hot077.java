package Hot100.贪心算法;

/**
 * @author lxd
 **/
public class Hot077 {
    /**
     * <a href="https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/description/">121. 买卖股票的最佳时机</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。</p>
     * <p>你只能选择某一天买入这只股票，并选择在未来的某一个不同的日子卖出该股票。设计一个算法来计算你所能获取的最大利润。</p>
     * <p>返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。</p>
     *
     * <h3>💡 核心思路：贪心算法</h3>
     * <ul>
     *   <li><b>基本思想</b>：遍历过程中记录当前最小价格，计算当前卖出的利润
     *     <ul>
     *       <li>维护当前遇到的最小价格 minPrice</li>
     *       <li>维护当前最大利润 profit</li>
     *       <li>遍历每一天的价格</li>
     *       <li>更新 minPrice = min(minPrice, prices[i])</li>
     *       <li>更新 profit = max(profit, prices[i] - minPrice)</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用贪心</b>：
     *     <ul>
     *       <li>在每一天做出最优选择</li>
     *       <li>到当天为止的最小价格是最优买入点</li>
     *       <li>当前卖出的利润 = 当前价格 - 最小买入价</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，遍历一次数组</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常量空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：prices = [7,1,5,3,6,4]
     *
     * 目标：找到最佳买入卖出时机，最大利润 = 5（1买入，6卖出）
     *
     * 初始化：profit = 0, minPrice = MAX_VALUE
     *
     * i=0, price=7：
     *   minPrice = min(MAX_VALUE, 7) = 7
     *   profit = max(0, 7 - 7) = 0
     *   → profit=0, minPrice=7
     *
     * i=1, price=1：
     *   minPrice = min(7, 1) = 1
     *   profit = max(0, 1 - 1) = 0
     *   → profit=0, minPrice=1
     *
     * i=2, price=5：
     *   minPrice = min(1, 5) = 1
     *   profit = max(0, 5 - 1) = 4
     *   → profit=4, minPrice=1
     *
     * i=3, price=3：
     *   minPrice = min(1, 3) = 1
     *   profit = max(4, 3 - 1) = 4
     *   → profit=4, minPrice=1
     *
     * i=4, price=6：
     *   minPrice = min(1, 6) = 1
     *   profit = max(4, 6 - 1) = 5
     *   → profit=5, minPrice=1
     *
     * i=5, price=4：
     *   minPrice = min(1, 4) = 1
     *   profit = max(5, 4 - 1) = 5
     *   → profit=5, minPrice=1
     *
     * 遍历结束：
     *   return 5 ✅
     *
     * 示例2：prices = [7,6,4,3,1]
     *   每天价格都在下跌，无法获利
     *   return 0 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ minPrice 如何初始化？
     *    - 初始化为 Integer.MAX_VALUE
     *    - 确保第一天的价格一定小于等于它
     *    - 第一次比较时会被更新为第一天的价格
     *
     * 2️⃣ profit 如何初始化？
     *    - 初始化为 0
     *    - 如果无法获利，返回 0
     *    - 每次比较时取较大值
     *
     * 3️⃣ 为什么不需要记录买入和卖出的天数？
     *    - 题目只需要最大利润值
     *    - 不需要知道具体是哪一天买入卖出
     *    - 简化计算，只维护两个变量
     *
     * 4️⃣ 遍历顺序是什么？
     *    - 从左到右遍历
     *    - 确保卖出时间在买入时间之后
     *    - 因为 minPrice 记录的是到当前天为止的最小值
     *
     * 5️⃣ 如何处理价格持续下跌的情况？
     *    - minPrice 不断更新为更小的值
     *    - profit = max(0, price - minPrice)
     *    - 始终 >= 0
     *    - 最终返回 0
     *
     * 6️⃣ 时间复杂度分析：
     *    - 只遍历一次数组
     *    - 每次迭代 O(1) 操作
     *    - 总时间 O(n)
     *
     * 7️⃣ 空间复杂度分析：
     *    - 只使用两个变量：profit 和 minPrice
     *    - 空间复杂度 O(1)
     *    - 非常高效
     *
     * 8️⃣ 完整流程总结：
     *
     *    profit = 0
     *    minPrice = MAX_VALUE
     *
     *    for i in 0..n-1：
     *      minPrice = min(minPrice, prices[i])
     *      profit = max(profit, prices[i] - minPrice)
     *
     *    return profit
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>初始化</b>：minPrice 初始化为 MAX_VALUE，profit 初始化为 0</li>
     *   <li><b>顺序</b>：先更新 minPrice，再计算利润</li>
     *   <li><b>利润非负</b>：profit 始终 >= 0</li>
     *   <li><b>只能交易一次</b>：买入一次，卖出一次</li>
     *   <li><b>卖出必须在买入之后</b>：从左到右遍历保证这一点</li>
     * </ul>
     *
     * @param prices 股票每天的价格数组
     * @return 最大利润
     */
    public int maxProfit(int[] prices) {
        // 📊 当前最大利润，初始化为 0
        int profit = 0;

        // 📊 当前遇到的最小价格，初始化为最大值
        int minPrice = Integer.MAX_VALUE;

        // 🔄 遍历每一天的价格
        for (int i = 0; i < prices.length; i++) {
            // 📉 更新最小价格
            minPrice = Math.min(minPrice, prices[i]);

            // 📈 更新最大利润
            profit = Math.max(profit, prices[i] - minPrice);
        }

        // ✅ 返回最大利润
        return profit;
    }
}

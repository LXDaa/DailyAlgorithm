package Hot100.动态规划;

/**
 * @author lxd
 **/
public class Hot083 {
    /**
     * <a href="https://leetcode.cn/problems/house-robber/description/">198. 打家劫舍</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。</p>
     * <p>给定一个代表每个房屋存放金额的非负整数数组，计算你不触动警报装置的情况下，一夜之内能够偷窃到的最高金额。</p>
     *
     * <h3>💡 核心思路：动态规划</h3>
     * <ul>
     *   <li><b>基本思想</b>：递推公式求解
     *     <ul>
     *       <li>定义 dp[i]：偷窃前 i+1 间房屋（索引 0~i）能获得的最高金额</li>
     *       <li>递推公式：dp[i] = max(nums[i] + dp[i-2], dp[i-1])</li>
     *       <li>初始状态：dp[0] = nums[0], dp[1] = max(nums[0], nums[1])</li>
     *       <li>从第 2 间开始递推到第 n-1 间</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么是这个递推公式</b>：
     *     <ul>
     *       <li>偷第 i 间：不能偷第 i-1 间，所以金额 = nums[i] + dp[i-2]</li>
     *       <li>不偷第 i 间：金额 = dp[i-1]</li>
     *       <li>取两者的最大值</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，遍历一次数组</li>
     *   <li><b>空间复杂度</b>：O(n)，使用 dp 数组</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [1, 2, 3, 1]
     *
     * 初始化：dp[0] = 1, dp[1] = max(1, 2) = 2
     *
     * i=2：
     *   dp[2] = max(nums[2]+dp[0], dp[1]) = max(3+1, 2) = max(4, 2) = 4
     *   → dp = [1, 2, 4, 0]
     *
     * i=3：
     *   dp[3] = max(nums[3]+dp[1], dp[2]) = max(1+2, 4) = max(3, 4) = 4
     *   → dp = [1, 2, 4, 4]
     *
     * 结果：dp[3] = 4 ✅
     * 路径：偷第 1 间和第 3 间，金额 = 1 + 3 = 4
     *
     * 示例2：nums = [2, 7, 9, 3, 1]
     *
     * dp[0] = 2
     * dp[1] = max(2, 7) = 7
     * dp[2] = max(9+2, 7) = max(11, 7) = 11
     * dp[3] = max(3+7, 11) = max(10, 11) = 11
     * dp[4] = max(1+11, 11) = max(12, 11) = 12
     *
     * 结果：dp[4] = 12 ✅
     * 路径：偷第 1 间、第 3 间、第 5 间，金额 = 2 + 9 + 1 = 12
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ dp 数组的定义？
     *    - dp[i] 表示偷窃前 i+1 间房屋（索引 0~i）能获得的最高金额
     *    - i 从 0 开始，dp 数组大小为 n
     *    - 最终答案为 dp[n-1]
     *
     * 2️⃣ 初始状态如何设置？
     *    - dp[0] = nums[0]（只有一间房，只能偷这间）
     *    - dp[1] = max(nums[0], nums[1])（两间房，偷金额大的那间）
     *    - n == 1 时直接返回 nums[0]
     *
     * 3️⃣ 递推公式是什么？
     *    - dp[i] = max(nums[i] + dp[i-2], dp[i-1])
     *    - 偷第 i 间：nums[i] + dp[i-2]（不能偷第 i-1 间）
     *    - 不偷第 i 间：dp[i-1]（偷到第 i-1 间的最大金额）
     *    - 取两者最大值
     *
     * 4️⃣ 为什么不能偷相邻房屋？
     *    - 题目约束：相邻房屋有防盗系统
     *    - 所以偷第 i 间就不能偷第 i-1 间
     *    - 递推时需要考虑这个约束
     *
     * 5️⃣ 空间复杂度如何优化？
     *    - 当前方法使用 O(n) 空间
     *    - 优化方法：只保存前两个值，O(1) 空间
     *    - prev1 = dp[i-1], prev2 = dp[i-2]
     *    - current = max(nums[i] + prev2, prev1)
     *
     * 6️⃣ 边界情况如何处理？
     *    - nums == null 或 nums.length == 0：返回 0
     *    - nums.length == 1：返回 nums[0]
     *    - nums.length == 2：返回 max(nums[0], nums[1])
     *
     * 7️⃣ 时间复杂度分析：
     *    - 遍历从 2 到 n-1，共 n-2 次迭代
     *    - 每次迭代 O(1) 操作
     *    - 总时间 O(n)
     *
     * 8️⃣ 完整流程总结：
     *
     *    if nums == null 或 nums.length == 0：return 0
     *    if nums.length == 1：return nums[0]
     *
     *    dp = new int[n]
     *    dp[0] = nums[0]
     *    dp[1] = max(nums[0], nums[1])
     *
     *    for i in 2..n-1：
     *      dp[i] = max(nums[i] + dp[i-2], dp[i-1])
     *
     *    return dp[n-1]
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>数组索引</b>：dp 数组大小为 n，索引从 0 开始</li>
     *   <li><b>初始状态</b>：dp[0]=nums[0], dp[1]=max(nums[0],nums[1])</li>
     *   <li><b>递推公式</b>：dp[i] = max(nums[i]+dp[i-2], dp[i-1])</li>
     *   <li><b>边界处理</b>：空数组和单元素数组的特殊处理</li>
     *   <li><b>空间优化</b>：可以使用 O(1) 空间优化</li>
     * </ul>
     *
     * @param nums 每个房屋存放金额的非负整数数组
     * @return 不触动警报装置的情况下能够偷窃到的最高金额
     */
    public int rob(int[] nums) {
        // 🚫 边界处理：空数组
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 📏 获取数组长度
        int n = nums.length;

        // 🚫 边界处理：只有一间房
        if (n == 1) {
            return nums[0];
        }

        // 📦 创建 dp 数组
        int[] dp = new int[n];

        // 📋 初始状态
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        // 🔄 从第 2 间开始递推
        for (int i = 2; i < n; i++) {
            // 📊 递推公式：偷第 i 间或不偷第 i 间，取最大值
            dp[i] = Math.max(nums[i] + dp[i - 2], dp[i - 1]);
        }

        // ✅ 返回偷窃到的最高金额
        return dp[n - 1];
    }
}

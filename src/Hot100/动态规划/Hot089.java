package Hot100.动态规划;

/**
 * @author lxd
 **/
public class Hot089 {
    /**
     * <a href="https://leetcode.cn/problems/partition-equal-subset-sum/description/">416. 分割等和子集</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。</p>
     *
     * <h3>💡 核心思路：动态规划（0-1背包问题）</h3>
     * <ul>
     *   <li><b>基本思想</b>：将问题转化为0-1背包问题
     *     <ul>
     *       <li>计算数组总和 sum，如果 sum 是奇数，直接返回 false</li>
     *       <li>目标 target = sum / 2</li>
     *       <li>定义 dp[j]：容量为 j 的背包能装下的最大价值（这里价值 = 重量）</li>
     *       <li>递推公式：dp[j] = max(dp[j], dp[j - nums[i]] + nums[i])</li>
     *       <li>外层遍历物品，内层遍历背包（倒序，0-1背包）</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么是0-1背包</b>：
     *     <ul>
     *       <li>每个元素只能使用一次</li>
     *       <li>目标是能否凑成目标和</li>
     *       <li>0-1背包的内层循环倒序遍历</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n * target)</li>
     *   <li><b>空间复杂度</b>：O(target)，使用一维 dp 数组</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [1, 5, 11, 5], sum = 22, target = 11
     *
     * 初始化：dp = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
     *
     * i=0, nums[i]=1：
     *   j=11: dp[11] = max(0, dp[10]+1) = 1
     *   j=10: dp[10] = max(0, dp[9]+1) = 1
     *   ...
     *   j=1: dp[1] = max(0, dp[0]+1) = 1
     *   → dp = [0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
     *   dp[11]=1 != 11，继续
     *
     * i=1, nums[i]=5：
     *   j=11: dp[11] = max(1, dp[6]+5) = max(1, 1+5) = 6
     *   j=10: dp[10] = max(1, dp[5]+5) = max(1, 1+5) = 6
     *   j=9: dp[9] = max(1, dp[4]+5) = max(1, 1+5) = 6
     *   j=8: dp[8] = max(1, dp[3]+5) = max(1, 1+5) = 6
     *   j=7: dp[7] = max(1, dp[2]+5) = max(1, 1+5) = 6
     *   j=6: dp[6] = max(1, dp[1]+5) = max(1, 1+5) = 6
     *   j=5: dp[5] = max(1, dp[0]+5) = max(1, 0+5) = 5
     *   → dp = [0, 1, 1, 1, 1, 5, 6, 6, 6, 6, 6, 6]
     *   dp[11]=6 != 11，继续
     *
     * i=2, nums[i]=11：
     *   j=11: dp[11] = max(6, dp[0]+11) = max(6, 0+11) = 11
     *   j=10: dp[10] = max(6, dp[-1]+11) → 跳过
     *   ...
     *   → dp = [0, 1, 1, 1, 1, 5, 6, 6, 6, 6, 6, 11]
     *   dp[11] == 11 → return true ✅
     *
     * 结果：可以分割，两个子集为 [1,5,5] 和 [11]
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 问题如何转化为0-1背包？
     *    - 总和为 sum，目标是找到和为 sum/2 的子集
     *    - 每个元素只能选一次（0-1背包）
     *    - 背包容量 = target，物品价值 = 物品重量 = nums[i]
     *    - 如果 dp[target] == target，说明可以凑成目标和
     *
     * 2️⃣ 为什么总和为奇数时直接返回 false？
     *    - 奇数无法分成两个相等的整数
     *    - sum % 2 == 1 时，无法分割成两个等和子集
     *    - 提前剪枝，优化性能
     *
     * 3️⃣ dp 数组的定义？
     *    - dp[j] 表示容量为 j 的背包能装下的最大价值
     *    - 由于价值 = 重量，dp[j] 就是背包能装下的最大重量
     *    - 如果 dp[target] == target，说明可以凑成目标和
     *
     * 4️⃣ 为什么内层循环要倒序？
     *    - 0-1背包：每个物品只能使用一次
     *    - 倒序遍历确保每个物品只被使用一次
     *    - 如果正序遍历，会导致同一物品被重复使用（完全背包）
     *
     * 5️⃣ 递推公式是什么？
     *    - dp[j] = max(dp[j], dp[j - nums[i]] + nums[i])
     *    - 不选当前物品：dp[j]（保持原值）
     *    - 选当前物品：dp[j - nums[i]] + nums[i]（加上当前物品的重量/价值）
     *    - 取两者最大值
     *
     * 6️⃣ 为什么可以提前返回？
     *    - 当 dp[target] == target 时，说明已经找到答案
     *    - 可以直接返回 true，无需继续遍历
     *    - 优化性能
     *
     * 7️⃣ 时间复杂度分析：
     *    - 外层循环：n 次（遍历每个元素）
     *    - 内层循环：target 次（倒序遍历）
     *    - 总时间：O(n * target)
     *
     * 8️⃣ 完整流程总结：
     *
     *    sum = sum(nums)
     *    if sum % 2 == 1：return false
     *    target = sum / 2
     *
     *    dp = new int[target+1]
     *
     *    for i in 0..n-1：
     *      for j = target; j >= nums[i]; j--：
     *        dp[j] = max(dp[j], dp[j-nums[i]] + nums[i])
     *      if dp[target] == target：
     *        return true
     *
     *    return dp[target] == target
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>总和判断</b>：sum % 2 == 1 时直接返回 false</li>
     *   <li><b>0-1背包</b>：内层循环倒序遍历</li>
     *   <li><b>dp 定义</b>：dp[j] 是容量 j 的背包能装下的最大重量</li>
     *   <li><b>提前返回</b>：dp[target] == target 时直接返回 true</li>
     *   <li><b>递推公式</b>：dp[j] = max(dp[j], dp[j-nums[i]]+nums[i])</li>
     * </ul>
     *
     * @param nums 只包含正整数的非空数组
     * @return 是否可以将数组分割成两个等和子集
     */
    public boolean canPartition(int[] nums) {
        // 📏 获取数组长度
        int n = nums.length;

        // 📊 计算数组总和
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        // 🚫 如果总和是奇数，无法分割成两个等和子集
        if (sum % 2 == 1) {
            return false;
        }

        // 📌 目标：找到和为 sum/2 的子集
        int target = sum / 2;

        // 📦 创建 dp 数组，dp[j] 表示容量为 j 的背包能装下的最大重量
        int[] dp = new int[target + 1];

        // 🔄 外层循环：遍历每个物品（元素）
        for (int i = 0; i < n; i++) {
            // 🔄 内层循环：倒序遍历背包容量（0-1背包）
            for (int j = target; j >= nums[i]; j--) {
                // 📊 递推公式：选或不选当前物品，取最大值
                dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
            }

            // ✅ 如果已经能凑成目标和，提前返回
            if (dp[target] == target) {
                return true;
            }
        }

        // ✅ 返回是否能凑成目标和
        return dp[target] == target;
    }
}

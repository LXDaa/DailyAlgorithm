package Hot100.动态规划;

import java.util.Arrays;

/**
 * @author lxd
 **/
public class Hot087 {
    /**
     * <a href="https://leetcode.cn/problems/longest-increasing-subsequence/description/">300. 最长递增子序列</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。</p>
     * <p>子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。</p>
     *
     * <h3>💡 核心思路：动态规划</h3>
     * <ul>
     *   <li><b>基本思想</b>：递推求解以每个元素结尾的最长递增子序列
     *     <ul>
     *       <li>定义 dp[i]：以 nums[i] 作为结尾的最长递增子序列长度</li>
     *       <li>递推公式：dp[i] = max(dp[j] + 1, dp[i])，其中 j < i 且 nums[i] > nums[j]</li>
     *       <li>初始状态：dp[i] = 1（每个元素自身构成长度为 1 的子序列）</li>
     *       <li>外层遍历 i，内层遍历 j 从 0 到 i-1</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用动态规划</b>：
     *     <ul>
     *       <li>问题具有最优子结构：以 nums[i] 结尾的最长子序列依赖于前面的结果</li>
     *       <li>避免重复计算子问题</li>
     *       <li>时间复杂度 O(n²)</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n²)，两层循环</li>
     *   <li><b>空间复杂度</b>：O(n)，使用 dp 数组</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [10, 9, 2, 5, 3, 7, 101, 18]
     *
     * 初始化：dp = [1, 1, 1, 1, 1, 1, 1, 1], maxLen = 1
     *
     * i=1, nums[i]=9：
     *   j=0: nums[1]=9 <= nums[0]=10 ❌
     *   → dp[1] = 1, maxLen = 1
     *
     * i=2, nums[i]=2：
     *   j=0: nums[2]=2 <= nums[0]=10 ❌
     *   j=1: nums[2]=2 <= nums[1]=9 ❌
     *   → dp[2] = 1, maxLen = 1
     *
     * i=3, nums[i]=5：
     *   j=0: nums[3]=5 <= nums[0]=10 ❌
     *   j=1: nums[3]=5 <= nums[1]=9 ❌
     *   j=2: nums[3]=5 > nums[2]=2 ✅
     *     dp[3] = max(1, dp[2]+1) = max(1, 2) = 2
     *   → dp[3] = 2, maxLen = 2
     *
     * i=4, nums[i]=3：
     *   j=0: nums[4]=3 <= nums[0]=10 ❌
     *   j=1: nums[4]=3 <= nums[1]=9 ❌
     *   j=2: nums[4]=3 > nums[2]=2 ✅
     *     dp[4] = max(1, dp[2]+1) = 2
     *   j=3: nums[4]=3 <= nums[3]=5 ❌
     *   → dp[4] = 2, maxLen = 2
     *
     * i=5, nums[i]=7：
     *   j=0: nums[5]=7 <= nums[0]=10 ❌
     *   j=1: nums[5]=7 <= nums[1]=9 ❌
     *   j=2: nums[5]=7 > nums[2]=2 ✅
     *     dp[5] = max(1, dp[2]+1) = 2
     *   j=3: nums[5]=7 > nums[3]=5 ✅
     *     dp[5] = max(2, dp[3]+1) = max(2, 3) = 3
     *   j=4: nums[5]=7 > nums[4]=3 ✅
     *     dp[5] = max(3, dp[4]+1) = max(3, 3) = 3
     *   → dp[5] = 3, maxLen = 3
     *
     * i=6, nums[i]=101：
     *   j=0: nums[6]=101 > nums[0]=10 ✅
     *     dp[6] = max(1, dp[0]+1) = 2
     *   j=1: nums[6]=101 > nums[1]=9 ✅
     *     dp[6] = max(2, dp[1]+1) = 2
     *   j=2: nums[6]=101 > nums[2]=2 ✅
     *     dp[6] = max(2, dp[2]+1) = 2
     *   j=3: nums[6]=101 > nums[3]=5 ✅
     *     dp[6] = max(2, dp[3]+1) = 3
     *   j=4: nums[6]=101 > nums[4]=3 ✅
     *     dp[6] = max(3, dp[4]+1) = 3
     *   j=5: nums[6]=101 > nums[5]=7 ✅
     *     dp[6] = max(3, dp[5]+1) = max(3, 4) = 4
     *   → dp[6] = 4, maxLen = 4
     *
     * i=7, nums[i]=18：
     *   j=0: nums[7]=18 > nums[0]=10 ✅
     *     dp[7] = max(1, dp[0]+1) = 2
     *   j=1: nums[7]=18 > nums[1]=9 ✅
     *     dp[7] = max(2, dp[1]+1) = 2
     *   j=2: nums[7]=18 > nums[2]=2 ✅
     *     dp[7] = max(2, dp[2]+1) = 2
     *   j=3: nums[7]=18 > nums[3]=5 ✅
     *     dp[7] = max(2, dp[3]+1) = 3
     *   j=4: nums[7]=18 > nums[4]=3 ✅
     *     dp[7] = max(3, dp[4]+1) = 3
     *   j=5: nums[7]=18 > nums[5]=7 ✅
     *     dp[7] = max(3, dp[5]+1) = max(3, 4) = 4
     *   j=6: nums[7]=18 <= nums[6]=101 ❌
     *   → dp[7] = 4, maxLen = 4
     *
     * 最终 dp = [1, 1, 1, 2, 2, 3, 4, 4]
     * 结果：maxLen = 4 ✅
     * 最长递增子序列：[2, 5, 7, 101] 或 [2, 3, 7, 101] 或 [2, 3, 7, 18]
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ dp 数组的定义？
     *    - dp[i] 表示以 nums[i] 作为结尾的最长递增子序列长度
     *    - i 从 0 开始，dp 数组大小为 n
     *    - 最终答案为 max(dp)，不是 dp[n-1]
     *
     * 2️⃣ 初始状态如何设置？
     *    - dp[i] = 1（每个元素自身构成长度为 1 的子序列）
     *    - 使用 Arrays.fill(dp, 1) 批量初始化
     *    - maxLen = 1（至少有一个元素）
     *
     * 3️⃣ 递推公式是什么？
     *    - dp[i] = max(dp[j] + 1, dp[i])
     *    - 其中 j < i 且 nums[i] > nums[j]
     *    - 如果 nums[i] 比 nums[j] 大，则可以接在 nums[j] 后面形成更长的子序列
     *
     * 4️⃣ 为什么答案是 max(dp) 而不是 dp[n-1]？
     *    - 最长递增子序列不一定以最后一个元素结尾
     *    - 需要遍历整个 dp 数组找到最大值
     *    - 例如：nums = [1,3,6,7,9,4,10,5,6]，最长子序列不以最后一个元素结尾
     *
     * 5️⃣ 如何优化时间复杂度？
     *    - 当前方法：O(n²)
     *    - 优化方法：贪心 + 二分查找，O(n log n)
     *    - 使用 tails 数组，tails[i] 表示长度为 i+1 的递增子序列的最小尾元素
     *
     * 6️⃣ 边界情况如何处理？
     *    - nums 为空：返回 0
     *    - nums 只有一个元素：返回 1
     *    - nums 严格递减：返回 1
     *
     * 7️⃣ 时间复杂度分析：
     *    - 外层循环：n 次（i 从 1 到 n-1）
     *    - 内层循环：i 次（j 从 0 到 i-1）
     *    - 总时间：O(n²)
     *
     * 8️⃣ 完整流程总结：
     *
     *    n = nums.length
     *    dp = new int[n]
     *    Arrays.fill(dp, 1)
     *    maxLen = 1
     *
     *    for i in 1..n-1：
     *      for j in 0..i-1：
     *        if nums[i] > nums[j]：
     *          dp[i] = max(dp[j] + 1, dp[i])
     *      maxLen = max(maxLen, dp[i])
     *
     *    return maxLen
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>dp 定义</b>：以 nums[i] 作为结尾的最长递增子序列长度</li>
     *   <li><b>初始状态</b>：dp[i] = 1</li>
     *   <li><b>答案</b>：max(dp)，不是 dp[n-1]</li>
     *   <li><b>严格递增</b>：nums[i] > nums[j]</li>
     *   <li><b>时间复杂度</b>：O(n²)，可优化至 O(n log n)</li>
     * </ul>
     *
     * @param nums 整数数组
     * @return 最长严格递增子序列的长度
     */
    public int lengthOfLIS(int[] nums) {
        // 📏 获取数组长度
        int n = nums.length;

        // 📦 创建 dp 数组，dp[i] 表示以 nums[i] 作为结尾的最长递增子序列长度
        int[] dp = new int[n];

        // 📋 初始状态：每个元素自身构成长度为 1 的子序列
        Arrays.fill(dp, 1);

        // 📊 最长递增子序列长度
        int maxLen = 1;

        // 🔄 外层循环：遍历每个元素
        for (int i = 1; i < n; i++) {
            // 🔄 内层循环：遍历前面的所有元素
            for (int j = 0; j < i; j++) {
                // 📌 如果 nums[i] > nums[j]，可以接在 nums[j] 后面
                if (nums[i] > nums[j]) {
                    // 📊 递推公式：更新最长长度
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }

            // 📊 更新全局最长长度
            maxLen = Math.max(dp[i], maxLen);
        }

        // ✅ 返回最长递增子序列长度
        return maxLen;
    }
}

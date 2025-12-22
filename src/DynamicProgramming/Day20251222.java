package DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/maximum-subarray/description/">53. 最大子序和</a>
 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * <p>
 * 子数组是数组中的一个连续部分。
 * <p>
 * 解题思路：
 * 这是一个经典的动态规划问题，也被称为 Kadane 算法。
 * <p>
 * 1. 状态定义：
 * dp[i] 表示以 nums[i] 结尾的连续子数组的最大和。
 * <p>
 * 2. 状态转移方程：
 * dp[i] = max(dp[i-1] + nums[i], nums[i])
 * 对于当前位置 i，要么将当前元素加入到之前的子数组中，要么从当前元素重新开始。
 * <p>
 * 3. 初始化：
 * dp[0] = nums[0]，第一个元素构成的子数组和就是它本身。
 * <p>
 * 4. 结果：
 * 在所有 dp[i] 中找到最大值，即为整个数组中连续子数组的最大和。
 * <p>
 * 时间复杂度：O(n)，只需要遍历一次数组
 * 空间复杂度：O(n)，需要一个 dp 数组存储状态
 *
 * @author lxd
 **/
public class Day20251222 {
    /**
     * 找出具有最大和的连续子数组的和
     *
     * @param nums 输入的整数数组
     * @return 最大子数组和
     */
    public int maxSubArray(int[] nums) {
        // 创建 dp 数组，dp[i] 表示以 nums[i] 结尾的连续子数组的最大和
        int[] dp = new int[nums.length];

        // 初始化：第一个元素构成的子数组和就是它本身
        dp[0] = nums[0];

        // 记录全局最大值，初始为第一个元素的值
        int res = dp[0];

        // 从第二个元素开始计算每个位置的最大子数组和
        for (int i = 1; i < nums.length; i++) {
            // 状态转移方程：
            // 要么将当前元素加入到之前的子数组中，要么从当前元素重新开始
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);

            // 更新全局最大值
            res = Math.max(res, dp[i]);
        }

        // 返回最大子数组和
        return res;
    }
}
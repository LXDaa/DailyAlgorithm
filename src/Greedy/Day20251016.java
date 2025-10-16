package Greedy;

/**
 * <a href="https://leetcode.cn/problems/maximum-subarray/description/">53. 最大子序和</a>
 * <p>
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 
 * 解题思路：
 * 使用贪心算法（Kadane算法）解决最大子数组和问题。
 * 核心思想是：遍历数组时，对于每个元素，我们决定是将其加入当前子数组还是从该元素重新开始。
 * 如果当前累加和为负数，那么它对后续元素的贡献是负的，我们应该丢弃当前累加和，
 * 从下一个元素重新开始计算。
 * 
 * 算法步骤：
 * 1. 初始化当前子数组和 sum = 0，最大和 max = nums[0]
 * 2. 遍历数组中的每个元素 num：
 *    a. 将 num 加到当前子数组和 sum 中
 *    b. 更新最大和 max 为 max 和 sum 中的较大值
 *    c. 如果 sum 变为负数，则将其重置为 0（表示从下一个元素重新开始）
 * 3. 返回最大和 max
 * 
 * 时间复杂度：O(n)，其中 n 是数组长度
 * 空间复杂度：O(1)
 * </p>
 */
public class Day20251016 {
    /**
     * 找到具有最大和的连续子数组
     * @param nums 输入的整数数组
     * @return 最大子数组和
     */
    public int maxSubArray(int[] nums) {
        // 当前子数组的和，初始为0
        int sum = 0;
        // 记录遇到的最大子数组和，初始为数组第一个元素
        int max = nums[0];
        
        // 遍历数组中的每个元素
        for (int num : nums) {
            // 将当前元素加入到子数组和中
            sum += num;
            // 更新最大子数组和
            max = Math.max(sum, max);
            // 如果当前子数组和为负数，丢弃前面的累加和，从下一个元素重新开始
            if (sum < 0) {
                sum = 0;
            }
        }
        return max;
    }
}
package CodeMusings.DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/longest-continuous-increasing-subsequence/description/">674. 最长连续递增序列</a>
 * 给定一个未经排序的整数数组，找到最长且 连续递增的子序列，并返回该序列的长度。
 * <p>
 * 连续递增的子序列 可以由两个下标 l 和 r（l < r）确定，如果对于每个 l <= i < r，都有 nums[i] < nums[i + 1] ，
 * 那么子序列 [nums[l], nums[l + 1], ..., nums[r - 1], nums[r]] 就是连续递增子序列。
 * <p>
 * 解题思路:
 * 1. 问题类型: 动态规划 - 最长连续递增子序列问题
 * 2. 状态定义: dp[i] 表示以 nums[i] 结尾的最长连续递增子序列的长度
 * 3. 状态转移方程:
 * - 如果 nums[i-1] < nums[i]，则 dp[i] = dp[i-1] + 1 （可以延续之前的递增序列）
 * - 否则 dp[i] = 1 （重新开始一个新的序列）
 * 4. 初始状态: dp[0] = 1，单个元素构成长度为1的递增序列
 * 5. 边界条件: 数组长度至少为1
 * 6. 最终答案: 所有dp[i]中的最大值
 * <p>
 * 时间复杂度: O(n) - 只需要遍历一次数组
 * 空间复杂度: O(n) - 使用了dp数组存储状态
 * <p>
 * 示例:
 * 输入: nums = [1,3,6,7,9,4,10,5,6]
 * 输出: 6
 * 解释: 最长连续递增序列是 [1,3,6,7,9,10]，长度为6
 *
 * @author lxd
 **/
public class Day20251216 {
    /**
     * 查找最长连续递增子序列的长度
     *
     * @param nums 输入的整数数组
     * @return 最长连续递增子序列的长度
     */
    public int findLengthOfLCIS(int[] nums) {
        // 获取数组长度
        int n = nums.length;

        // 创建dp数组，dp[i]表示以nums[i]结尾的最长连续递增子序列的长度
        int[] dp = new int[n];

        // 初始化第一个元素的状态，单个元素构成长度为1的递增序列
        dp[0] = 1;

        // 记录全局最长连续递增子序列的长度
        int res = 1;

        // 从第二个元素开始遍历数组
        for (int i = 1; i < n; i++) {
            // 每个元素至少可以构成长度为1的递增序列（自己单独成为一个序列）
            dp[i] = 1;

            // 如果前一个元素小于当前元素，则可以将当前元素接在之前的递增序列后面
            if (nums[i - 1] < nums[i]) {
                // 状态转移方程：延续之前的递增序列
                dp[i] = dp[i - 1] + 1;
                // 更新全局最长长度
                res = Math.max(res, dp[i]);
            }
        }

        // 返回最长连续递增子序列的长度
        return res;
    }
}
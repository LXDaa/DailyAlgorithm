package DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/partition-equal-subset-sum/description/">416. 分割等和子集</a>
 * <p>
 * 题目描述：
 * 给你一个只包含正整数的非空数组 nums。请你判断是否可以将这个数组分割成两个子集，
 * 使得两个子集的元素和相等。
 * <p>
 * 解题思路：
 * 这是一个典型的0-1背包问题。如果数组总和为sum，我们要将其分为两个和相等的子集，
 * 那么每个子集的和应该为sum/2。问题就转化为：能否从数组中选出一些数字，使其和等于sum/2。
 * <p>
 * 使用动态规划解决：
 * 1. 状态定义：dp[j] 表示是否可以从数组中选出一些数字，使其和等于j
 * 2. 状态转移方程：对于每个数字nums[i]，dp[j] = dp[j] || dp[j-nums[i]]
 * 即：不选择当前数字（保持原来的dp[j]）或者选择当前数字（查看是否存在和为j-nums[i]的组合）
 * 3. 初始化：dp[0] = true，表示和为0总是可以达到（不选任何数字）
 * 4. 遍历顺序：外层遍历物品（数组元素），内层倒序遍历背包容量（目标和）
 */
public class Day20251117 {
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        // 边界条件检查
        if (nums == null || n == 0) {
            return false;
        }

        // 计算数组总和
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        // 如果总和是奇数，则无法分成两个相等的子集
        if ((sum & 1) == 1) {
            return false;
        }

        // 目标和为总和的一半
        int target = sum >> 1;

        // dp[j] 表示是否能选出一些数字使其和为j
        boolean[] dp = new boolean[target + 1];

        // 初始化：和为0总是可以达到（不选任何数字）
        dp[0] = true;

        // 外层遍历数组中的每个数字（每个物品）
        for (int i = 0; i < n; i++) {
            // 内层倒序遍历目标和（背包容量）
            // 倒序是为了确保每个数字只使用一次（0-1背包的特点）
            for (int j = target; j >= nums[i]; j--) {
                // 状态转移方程：
                // dp[j] = dp[j] || dp[j-nums[i]]
                // 即：不选择当前数字 或者 选择当前数字（前提是dp[j-nums[i]]可达）
                dp[j] = dp[j] || dp[j - nums[i]];
            }
        }

        // 返回是否能达到目标和
        return dp[target];
    }
}
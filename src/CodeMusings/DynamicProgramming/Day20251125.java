package CodeMusings.DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/combination-sum-iv/description/">377. 组合求和IV</a>
 * <p>
 * 题目描述：
 * 给你一个由不同整数组成的数组 nums，和一个目标整数 target。
 * 请你从 nums 中找出并返回总和为 target 的元素组合的个数。
 * 注意：顺序不同的序列被视作不同的组合。
 * <p>
 * 示例：
 * 输入：nums = [1,2,3], target = 4
 * 输出：7
 * 解释：所有可能的组合为：
 * <p>
 * (1, 1, 1, 1)
 * (1, 1, 2)
 * (1, 2, 1)
 * (1, 3)
 * (2, 1, 1)
 * (2, 2)
 * (3, 1)
 * <p>
 * 解题思路：
 * 这是一个典型的动态规划问题。我们可以用dp[i]表示总和为i的组合数量。
 * 状态转移方程：对于每个数字num，如果i >= num，则dp[i] += dp[i - num]
 * 初始状态：dp[0] = 1，表示总和为0只有一种组合方式（什么都不选）
 * 计算顺序：外层循环遍历target值（从1到target），内层循环遍历数组中的每个数字
 * <p>
 * 时间复杂度：O(target * nums.length)
 * 空间复杂度：O(target)
 *
 * @author lxd
 **/
public class Day20251125 {
    public int combinationSum4(int[] nums, int target) {
        // dp[i] 表示总和为 i 的组合个数
        int[] dp = new int[target + 1];
        // 初始化：总和为0的组合数为1（即不选择任何元素）
        dp[0] = 1;

        // 外层循环：遍历从1到target的每个值
        for (int i = 1; i <= target; i++) {
            // 内层循环：尝试数组中的每一个数字
            for (int num : nums) {
                // 只有当当前目标值大于等于当前数字时才能使用该数字
                if (i >= num) {
                    // 状态转移方程：加上使用当前数字之前的组合数
                    dp[i] += dp[i - num];
                }
            }
        }

        // 返回总和为target的组合数
        return dp[target];
    }
}
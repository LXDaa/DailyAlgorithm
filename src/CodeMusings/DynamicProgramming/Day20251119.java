package CodeMusings.DynamicProgramming;

/**
 * @author lxd
 * <a href="https://leetcode.cn/problems/target-sum/description/">494.目标和</a>
 * 给你一个非负整数数组 nums 和一个整数 target 。
 * <p>
 * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
 * <p>
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
 * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
 * <p>
 * 解题思路：
 * 这是一个典型的01背包问题的变种。
 * 我们可以把问题转化为：将数组分成两个子集，使得它们的差等于target。
 * 假设正数集合的和为left，负数集合的和为right，则有：
 * left + right = sum (数组总和)
 * left - right = target
 * 联立两式可得：left = (sum + target) / 2
 * 所以问题就变成了在数组中找出和为left的组合数量，这就是一个标准的01背包问题。
 **/
public class Day20251119 {
    /**
     * 使用动态规划求解目标和问题
     *
     * @param nums   非负整数数组
     * @param target 目标值
     * @return 可以构造出目标值的不同表达式数目
     */
    public int findTargetSumWays(int[] nums, int target) {
        // 计算数组元素总和
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        // 如果总和小于目标值的绝对值，则无法达到目标
        // 或者如果(target + sum)是奇数，则无法整除得到left
        if (sum < Math.abs(target) || (target + sum) % 2 != 0) {
            return 0;
        }

        int n = nums.length;
        // 计算正数集合的目标和 left = (sum + target) / 2
        int bagSize = (target + sum) / 2;

        // dp[j] 表示填满容量为j的背包有几种方法
        int[] dp = new int[bagSize + 1];
        // 初始化：填满容量为0的背包有1种方法（什么都不放）
        dp[0] = 1;

        // 遍历物品（数组中的每个数字）
        for (int i = 0; i < n; i++) {
            // 从后往前遍历背包容量，避免重复使用同一物品
            for (int j = bagSize; j >= nums[i]; j--) {
                // 状态转移方程：
                // 不放入nums[i]: dp[j]
                // 放入nums[i]: dp[j - nums[i]]
                // 总的方法数 = 两种情况之和
                dp[j] += dp[j - nums[i]];
            }
        }

        // 返回填满容量为bagSize的背包的方法数
        return dp[bagSize];
    }
}
package CodeMusings.DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/longest-increasing-subsequence/description/">300.最长递增子序列</a>
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * <p>
 * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 * <p>
 * 解题思路:
 * 这是一个经典的动态规划问题，用于找到数组中最长严格递增子序列的长度。
 * <p>
 * 动态规划定义：
 * dp[i] 表示以 nums[i] 结尾的最长递增子序列的长度
 * <p>
 * 状态转移方程：
 * 对于每个位置 i，我们检查所有在它之前的位置 j（j < i），
 * 如果 nums[j] < nums[i]，说明可以将 nums[i] 接在以 nums[j] 结尾的递增子序列后面，
 * 此时 dp[i] = max(dp[i], dp[j] + 1)
 * <p>
 * 初始化：
 * 每个元素本身构成一个长度为1的递增子序列，所以 dp[i] = 1
 * <p>
 * 举例说明：
 * nums = [10,9,2,5,3,7,101,18]
 * dp[0]=1 (10)
 * dp[1]=1 (9)
 * dp[2]=1 (2)
 * dp[3]=2 (2,5)
 * dp[4]=2 (2,3)
 * dp[5]=3 (2,3,7)
 * dp[6]=4 (2,3,7,101)
 * dp[7]=4 (2,3,7,18)
 * 最终结果为4
 * <p>
 * 时间复杂度: O(n^2)，其中 n 是数组长度
 * 空间复杂度: O(n)
 *
 * @author lxd
 **/
public class Day20251215 {
    /**
     * 计算最长递增子序列的长度
     *
     * @param nums 输入的整数数组
     * @return 最长递增子序列的长度
     */
    public int lengthOfLIS(int[] nums) {
        // 获取数组长度
        int n = nums.length;

        // 边界条件：如果数组为空，返回0
        if (n == 0) {
            return 0;
        }

        // dp[i] 表示以 nums[i] 结尾的最长递增子序列的长度
        int[] dp = new int[n];

        // 初始化：每个元素自身构成一个长度为1的递增子序列
        dp[0] = 1;

        // 记录全局最长递增子序列的长度
        int result = 1;

        // 从第二个元素开始计算每个位置的最长递增子序列长度
        for (int i = 1; i < n; i++) {
            // 初始化当前元素的最长递增子序列长度为1
            dp[i] = 1;

            // 检查所有在当前位置之前的元素
            for (int j = 0; j < i; j++) {
                // 如果前面的元素小于当前元素，可以接在后面形成更长的递增子序列
                if (nums[i] > nums[j]) {
                    // 更新以当前元素结尾的最长递增子序列长度
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }

            // 更新全局最长递增子序列长度
            result = Math.max(dp[i], result);
        }

        // 返回最长递增子序列的长度
        return result;
    }
}
package CodeMusings.DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/maximum-length-of-repeated-subarray/description/">718. 最长重复子数组</a>
 * 给两个整数数组 nums1 和 nums2 ，返回 两个数组中 公共的 、长度最长的子数组的长度 。
 * <p>
 * 解题思路：
 * 1. 问题分析：这是一个典型的动态规划问题，需要找到两个数组中最长的公共子数组（连续元素）
 * 2. 状态定义：dp[i][j] 表示以 nums1[i-1] 和 nums2[j-1] 结尾的公共子数组的长度
 * 3. 状态转移方程：
 * - 如果 nums1[i-1] == nums2[j-1]，则 dp[i][j] = dp[i-1][j-1] + 1
 * - 否则 dp[i][j] = 0 （因为子数组必须连续）
 * 4. 初始化：dp[0][j] = 0, dp[i][0] = 0 （空数组与任何数组的公共子数组长度为0）
 * 5. 结果：在所有 dp[i][j] 中找最大值
 * <p>
 * 时间复杂度：O(m*n)，其中 m 和 n 分别是两个数组的长度
 * 空间复杂度：O(m*n)
 *
 * @author lxd
 */
public class Day20251217 {
    /**
     * 找到两个数组中最长公共子数组的长度
     *
     * @param nums1 第一个整数数组
     * @param nums2 第二个整数数组
     * @return 最长公共子数组的长度
     */
    public int findLength(int[] nums1, int[] nums2) {
        // 获取两个数组的长度
        int m = nums1.length;
        int n = nums2.length;

        // 创建 DP 数组，dp[i][j] 表示以 nums1[i-1] 和 nums2[j-1] 结尾的公共子数组长度
        // 多开一行一列是为了方便处理边界情况，不需要特殊判断 i=0 或 j=0
        int[][] dp = new int[m + 1][n + 1];

        // 记录最长公共子数组的长度
        int res = 0;

        // 遍历两个数组的所有元素组合
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果当前两个元素相等，则更新状态
                if (nums1[i - 1] == nums2[j - 1]) {
                    // 当前位置的公共子数组长度等于前一个位置的长度加1
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                // 如果不相等，dp[i][j] 默认为0（已在数组初始化时完成）

                // 更新全局最大值
                res = Math.max(res, dp[i][j]);
            }
        }

        // 返回最长公共子数组的长度
        return res;
    }
}
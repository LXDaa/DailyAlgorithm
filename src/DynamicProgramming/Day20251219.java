package DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/uncrossed-lines/description/">1035.不相交的线</a>
 * 在两条独立的水平线上按给定的顺序写下 nums1 和 nums2 中的整数。
 * <p>
 * 现在，可以绘制一些连接两个数字 nums1[i] 和 nums2[j] 的直线，这些直线需要同时满足：
 * <p>
 * nums1[i] == nums2[j]
 * 且绘制的直线不与任何其他连线（非水平线）相交。
 * 请注意，连线即使在端点也不能相交：每个数字只能属于一条连线。
 * <p>
 * 以这种方法绘制线条，并返回可以绘制的最大连线数。
 * <p>
 * 解题思路：
 * 1. 问题分析：这道题本质上是求两个数组的最长公共子序列(LCS)
 * - 连线不能相交意味着连接的元素在各自数组中的相对顺序必须一致
 * - 这正好符合子序列的定义：保持相对顺序但不要求连续
 * 2. 状态定义：dp[i][j] 表示 nums1 前 i 个元素和 nums2 前 j 个元素之间最多能画多少条不相交的线
 * 3. 状态转移方程：
 * - 如果 nums1[i-1] == nums2[j-1]，则 dp[i][j] = dp[i-1][j-1] + 1
 * （两个元素相等，可以连一条线，结果为前面的结果加1）
 * - 否则 dp[i][j] = max(dp[i-1][j], dp[i][j-1])
 * （两个元素不相等，取两种情况的最大值：
 * 要么不考虑nums1[i-1]，要么不考虑nums2[j-1]）
 * 4. 初始化：dp[0][j] = 0, dp[i][0] = 0 （空数组无法连线）
 * 5. 结果：dp[m][n] 即为所求的最大连线数
 * <p>
 * 时间复杂度：O(m*n)，其中 m 和 n 分别是两个数组的长度
 * 空间复杂度：O(m*n)
 *
 * @author lxd
 */
public class Day20251219 {
    /**
     * 计算两个数组之间能画出的最大不相交连线数
     *
     * @param nums1 第一个整数数组
     * @param nums2 第二个整数数组
     * @return 最大不相交连线数
     */
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        // 获取两个数组的长度
        int m = nums1.length;
        int n = nums2.length;

        // 创建 DP 数组，dp[i][j] 表示 nums1 前 i 个元素和 nums2 前 j 个元素之间最多能画多少条不相交的线
        // 多开一行一列是为了方便处理边界情况，不需要特殊判断 i=0 或 j=0
        int[][] dp = new int[m + 1][n + 1];

        // 遍历两个数组的所有元素组合
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果当前两个元素相等
                if (nums1[i - 1] == nums2[j - 1]) {
                    // 可以在这两个元素之间画一条线，结果为前一个位置的结果加1
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // 如果当前两个元素不相等，取以下两种情况的最大值：
                    // 1. 不考虑 nums1[i-1]，即 nums1 前 i-1 个元素与 nums2 前 j 个元素的最大连线数
                    // 2. 不考虑 nums2[j-1]，即 nums1 前 i 个元素与 nums2 前 j-1 个元素的最大连线数
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // 返回两个数组之间能画出的最大不相交连线数
        return dp[m][n];
    }
}
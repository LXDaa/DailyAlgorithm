package DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/longest-common-subsequence/description/">1143.最长公共子序列</a>
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
 * <p>
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * <p>
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 * <p>
 * 解题思路：
 * 1. 问题分析：这是经典的动态规划问题，求两个字符串的最长公共子序列（不要求连续）
 * 2. 状态定义：dp[i][j] 表示 text1 前 i 个字符和 text2 前 j 个字符的最长公共子序列长度
 * 3. 状态转移方程：
 * - 如果 text1[i-1] == text2[j-1]，则 dp[i][j] = dp[i-1][j-1] + 1
 * - 否则 dp[i][j] = max(dp[i-1][j], dp[i][j-1])
 * 4. 初始化：dp[0][j] = 0, dp[i][0] = 0 （空字符串与任何字符串的公共子序列长度为0）
 * 5. 结果：dp[m][n] 即为所求
 * <p>
 * 时间复杂度：O(m*n)，其中 m 和 n 分别是两个字符串的长度
 * 空间复杂度：O(m*n)
 *
 * @author lxd
 */
public class Day20251218 {
    /**
     * 计算两个字符串的最长公共子序列长度
     *
     * @param text1 第一个字符串
     * @param text2 第二个字符串
     * @return 最长公共子序列的长度
     */
    public int longestCommonSubsequence(String text1, String text2) {
        // 将字符串转换为字符数组，便于访问
        char[] c1 = text1.toCharArray();
        char[] c2 = text2.toCharArray();

        // 获取两个字符串的长度
        int m = c1.length;
        int n = c2.length;

        // 创建 DP 数组，dp[i][j] 表示 text1 前 i 个字符和 text2 前 j 个字符的最长公共子序列长度
        // 多开一行一列是为了方便处理边界情况，不需要特殊判断 i=0 或 j=0
        int[][] dp = new int[m + 1][n + 1];

        // 遍历两个字符串的所有字符组合
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果当前两个字符相等
                if (c1[i - 1] == c2[j - 1]) {
                    // 最长公共子序列长度等于前一个位置的结果加1
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // 如果当前两个字符不相等，取以下两种情况的最大值：
                    // 1. text1 前 i-1 个字符与 text2 前 j 个字符的最长公共子序列长度
                    // 2. text1 前 i 个字符与 text2 前 j-1 个字符的最长公共子序列长度
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // 返回两个字符串的最长公共子序列长度
        return dp[m][n];
    }
}
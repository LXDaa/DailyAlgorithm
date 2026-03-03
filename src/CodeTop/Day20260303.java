package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/longest-common-subsequence/description/">1143. 最长公共子序列</a>
 * <p>
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列，返回 0。
 * <p>
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * <p>
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 *
 * <p><strong>解题思路：</strong></p>
 * <p>使用动态规划解决该问题。定义二维 dp 数组，其中 dp[i][j] 表示 text1 的前 i 个字符和 text2 的前 j 个字符的最长公共子序列长度。</p>
 *
 * <p><strong>状态转移方程：</strong></p>
 * <ol>
 *   <li>如果 text1[i-1] == text2[j-1]，则 dp[i][j] = dp[i-1][j-1] + 1<br>
 *       （当前字符相同，在之前的基础上加 1）</li>
 *   <li>如果 text1[i-1] != text2[j-1]，则 dp[i][j] = max(dp[i-1][j], dp[i][j-1])<br>
 *       （当前字符不同，取两种情况的最大值）</li>
 *       <ul>
 *         <li>dp[i-1][j]：忽略 text1 的当前字符</li>
 *         <li>dp[i][j-1]：忽略 text2 的当前字符</li>
 *       </ul>
 * </ol>
 *
 * <p><strong>示例说明：</strong></p>
 * <p>输入：text1 = "abcde", text2 = "ace"</p>
 * <p>DP 表构建过程（部分）：</p>
 * <table border="1" cellpadding="5">
 *   <tr><th></th><th>""</th><th>a</th><th>c</th><th>e</th></tr>
 *   <tr><td>""</td><td>0</td><td>0</td><td>0</td><td>0</td></tr>
 *   <tr><td>a</td><td>0</td><td>1</td><td>1</td><td>1</td></tr>
 *   <tr><td>b</td><td>0</td><td>1</td><td>1</td><td>1</td></tr>
 *   <tr><td>c</td><td>0</td><td>1</td><td>2</td><td>2</td></tr>
 *   <tr><td>d</td><td>0</td><td>1</td><td>2</td><td>2</td></tr>
 *   <tr><td>e</td><td>0</td><td>1</td><td>2</td><td>3</td></tr>
 * </table>
 * <p>最终结果：dp[5][3] = 3，最长公共子序列为 "ace"</p>
 *
 * <p><strong>复杂度分析：</strong></p>
 * <ul>
 *   <li>时间复杂度：O(m*n)，其中 m 和 n 分别为两个字符串的长度</li>
 *   <li>空间复杂度：O(m*n)，二维 dp 数组的空间</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260303 {
    /**
     * <p>计算两个字符串的最长公共子序列长度</p>
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

        // 创建 dp 数组，dp[i][j] 表示 text1 前 i 个字符和 text2 前 j 个字符的 LCS 长度
        // 多开一行一列用于处理空字符串的边界情况
        int[][] dp = new int[m + 1][n + 1];

        // 遍历两个字符串的所有字符组合
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 判断当前字符是否相同
                if (c1[i - 1] == c2[j - 1]) {
                    // 字符相同：在对角线基础上加 1
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // 字符不同：取上方和左方的最大值
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        // 返回最终结果，即两个完整字符串的 LCS 长度
        return dp[m][n];
    }
}

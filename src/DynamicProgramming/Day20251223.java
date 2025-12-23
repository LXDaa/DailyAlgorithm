package DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/is-subsequence/description/">392. 判断子序列</a>
 * <p>
 * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
 * <p>
 * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
 * <p>
 * 解题思路：
 * 1. 问题类型：动态规划 - 判断子序列问题
 * 2. 状态定义：dp[i][j] 表示字符串 s 的前 i 个字符是否为字符串 t 的前 j 个字符的子序列
 * 3. 状态转移方程：
 * - 如果 s.charAt(i-1) == t.charAt(j-1)，则 dp[i][j] = dp[i-1][j-1] + 1
 * （表示当前字符匹配，子序列长度增加1）
 * - 否则 dp[i][j] = dp[i][j-1]
 * （表示当前字符不匹配，继承只考虑 t 前 j-1 个字符时的结果）
 * 4. 初始化：dp[0][j] = 0 (空字符串是任何字符串的子序列，但长度为0)
 * 5. 结果：dp[m][n] == m (其中 m 是字符串 s 的长度)
 * <p>
 * 时间复杂度：O(m*n)，其中 m 和 n 分别是字符串 s 和 t 的长度
 * 空间复杂度：O(m*n)，使用二维数组存储状态
 *
 * @author lxd
 **/
public class Day20251223 {
    /**
     * 判断字符串 s 是否为字符串 t 的子序列
     *
     * @param s 待判断的子序列字符串
     * @param t 原始字符串
     * @return 如果 s 是 t 的子序列返回 true，否则返回 false
     */
    public boolean isSubsequence(String s, String t) {
        // 获取两个字符串的长度
        int m = s.length(), n = t.length();

        // 创建 DP 数组，dp[i][j] 表示 s 的前 i 个字符是否为 t 的前 j 个字符的子序列
        // 使用多开一行一列的方式，便于处理边界情况
        int[][] dp = new int[m + 1][n + 1];

        // 遍历两个字符串的所有字符组合
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果当前两个字符相等
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    // 子序列长度等于前一个状态的长度加1
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // 如果当前两个字符不相等，继承只考虑 t 前 j-1 个字符时的结果
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }

        // 判断最长公共子序列的长度是否等于 s 的长度
        // 如果等于，说明 s 中的所有字符都能在 t 中按顺序找到，即 s 是 t 的子序列
        return dp[m][n] == m;
    }
}
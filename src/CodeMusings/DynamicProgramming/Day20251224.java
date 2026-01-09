package CodeMusings.DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/distinct-subsequences/description/">115. 不同的子序列</a>
 * 给你两个字符串 s 和 t ，统计并返回在 s 的 子序列 中 t 函出现的个数。
 * <p>
 * 测试用例保证结果在 32 位有符号整数范围内。
 *
 * @author lxd
 **/
public class Day20251224 {
    /**
     * 计算字符串 s 的子序列中包含字符串 t 的个数
     * <p>
     * 解题思路：
     * 1. 状态定义：dp[i][j] 表示 s 的前 i 个字符的子序列中 t 的前 j 个字符出现的个数
     * 2. 状态转移方程：
     * - 当 s.charAt(i-1) == t.charAt(j-1) 时：
     * dp[i][j] = dp[i-1][j-1] + dp[i-1][j]
     * (使用 s 第 i 个字符匹配 t 第 j 个字符 + 不使用 s 第 i 个字符)
     * - 当 s.charAt(i-1) != t.charAt(j-1) 时：
     * dp[i][j] = dp[i-1][j] (只能不使用 s 第 i 个字符)
     * 3. 边界条件：dp[i][0] = 1，表示任何字符串都包含一个空字符串作为子序列
     * 4. 最终答案：dp[m][n]，其中 m 和 n 分别是 s 和 t 的长度
     *
     * @param s 源字符串
     * @param t 目标字符串
     * @return s 的子序列中 t 出现的个数
     */
    public int numDistinct(String s, String t) {
        // s 的长度
        int m = s.length();
        // t 的长度
        int n = t.length();

        // dp[i][j] 表示 s 的前 i 个字符的子序列中 t 的前 j 个字符出现的个数
        int[][] dp = new int[m + 1][n + 1];

        // 边界条件：当 t 为空字符串时，任何 s 都有一个空子序列匹配
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }

        // 动态规划填表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    // 当前字符相等时，可以选择使用或不使用 s 的当前字符
                    // 使用：dp[i-1][j-1] (匹配当前字符)
                    // 不使用：dp[i-1][j] (跳过当前字符)
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    // 当前字符不相等时，只能不使用 s 的当前字符
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // 返回最终结果：s 的前 m 个字符的子序列中 t 的前 n 个字符出现的个数
        return dp[m][n];
    }
}

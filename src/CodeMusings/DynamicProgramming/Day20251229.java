package CodeMusings.DynamicProgramming;

/**
 * 72. 编辑距离
 * <p>
 * 题目描述:
 * 给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数。
 * 你可以对一个单词进行如下三种操作：
 * - 插入一个字符
 * - 删除一个字符
 * - 替换一个字符
 * <p>
 * 解题思路:
 * 使用动态规划方法解决编辑距离问题。
 * <p>
 * 状态定义:
 * dp[i][j] 表示将 word1 的前 i 个字符转换为 word2 的前 j 个字符所需的最少操作数
 * <p>
 * 状态转移方程:
 * 1. 如果 word1.charAt(i-1) == word2.charAt(j-1)，即当前字符相同
 * 则不需要额外操作: dp[i][j] = dp[i-1][j-1]
 * 2. 如果 word1.charAt(i-1) != word2.charAt(j-1)，即当前字符不同
 * 则需要在以下三种操作中选择操作数最少的：
 * - 替换操作: dp[i-1][j-1] + 1 (将 word1[i-1] 替换为 word2[j-1])
 * - 删除操作: dp[i-1][j] + 1 (删除 word1[i-1])
 * - 插入操作: dp[i][j-1] + 1 (在 word1[i-1] 后插入 word2[j-1])
 * 即: dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1
 * <p>
 * 边界条件:
 * - dp[i][0] = i: 将长度为 i 的字符串转换为空字符串需要 i 次删除操作
 * - dp[0][j] = j: 将空字符串转换为长度为 j 的字符串需要 j 次插入操作
 * <p>
 * 最终答案:
 * dp[m][n] 表示将完整的 word1 转换为完整的 word2 所需的最少操作数
 *
 * @author lxd
 **/
public class Day20251229 {
    /**
     * 计算两个字符串之间的最小编辑距离
     *
     * @param word1 源字符串
     * @param word2 目标字符串
     * @return 将 word1 转换为 word2 所需的最少操作数
     */
    public int minDistance(String word1, String word2) {
        // 获取两个字符串的长度
        int m = word1.length();
        int n = word2.length();

        // 创建 DP 数组，dp[i][j] 表示将 word1 前 i 个字符转换为 word2 前 j 个字符的最少操作数
        int[][] dp = new int[m + 1][n + 1];

        // 初始化边界条件：将长度为 i 的字符串转换为空字符串需要 i 次删除操作
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        // 初始化边界条件：将空字符串转换为长度为 j 的字符串需要 j 次插入操作
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }

        // 填充 DP 数组
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果当前字符相同，则不需要额外操作，直接取前一个状态
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 如果当前字符不同，则在三种操作中选择操作数最少的
                    // dp[i-1][j] + 1: 删除 word1[i-1]
                    // dp[i][j-1] + 1: 插入 word2[j-1]
                    // dp[i-1][j-1] + 1: 替换 word1[i-1] 为 word2[j-1]
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }

        // 返回将完整 word1 转换为完整 word2 的最少操作数
        return dp[m][n];
    }
}
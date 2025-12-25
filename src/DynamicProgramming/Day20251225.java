package DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/delete-operation-for-two-strings/description/">583. 两个字符串的删除操作</a>
 * 给定两个单词 word1 和 word2 ，返回使得 word1 和  word2 相同所需的最小步数。
 * <p>
 * 每步 可以删除任意一个字符串中的一个字符。
 * <p>
 * 解题思路：
 * 1. 问题分析：这是一个经典的动态规划问题，本质上是求两个字符串的编辑距离（只允许删除操作）
 * 2. 状态定义：dp[i][j] 表示使 word1 的前 i 个字符与 word2 的前 j 个字符相同的最小删除步数
 * 3. 状态转移方程：
 * - 如果 word1.charAt(i-1) == word2.charAt(j-1)，则 dp[i][j] = dp[i-1][j-1]
 * （两个字符相等，不需要删除，直接继承之前的状态）
 * - 否则 dp[i][j] = min(dp[i-1][j] + 1, dp[i][j-1] + 1)
 * （两个字符不相等，可以选择删除 word1 的第 i 个字符或删除 word2 的第 j 个字符）
 * 4. 初始化：
 * - dp[i][0] = i：将 word1 的前 i 个字符全部删除需要 i 步
 * - dp[0][j] = j：将 word2 的前 j 个字符全部删除需要 j 步
 * 5. 最终答案：dp[m][n]，即处理完整个两个字符串的最小删除步数
 * <p>
 * 时间复杂度：O(m*n)，其中 m 和 n 分别是两个字符串的长度
 * 空间复杂度：O(m*n)，用于存储动态规划表
 *
 * @author lxd
 **/
public class Day20251225 {
    /**
     * 计算使两个字符串相同所需的最小删除步数
     *
     * @param word1 第一个字符串
     * @param word2 第二个字符串
     * @return 最小删除步数
     */
    public int minDistance(String word1, String word2) {
        // 获取两个字符串的长度
        int m = word1.length();
        int n = word2.length();

        // 创建 DP 数组，dp[i][j] 表示使 word1 前 i 个字符与 word2 前 j 个字符相同的最小删除步数
        int[][] dp = new int[m + 1][n + 1];

        // 初始化：将 word1 的前 i 个字符全部删除需要 i 步
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        // 初始化：将 word2 的前 j 个字符全部删除需要 j 步
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // 填充 DP 表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果当前字符相同，不需要删除操作，直接继承之前的最优状态
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 如果当前字符不同，选择删除 word1 的当前字符或删除 word2 的当前字符中的较小值
                    // dp[i-1][j] + 1：删除 word1 的第 i 个字符
                    // dp[i][j-1] + 1：删除 word2 的第 j 个字符
                    dp[i][j] = Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }

        // 返回使两个完整字符串相同的最小删除步数
        return dp[m][n];
    }
}

package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/edit-distance/description/">72.编辑距离</a>
 * <p>
 * 给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数  。
 * <p>
 * 你可以对一个单词进行如下三种操作：
 * <p>
 * 插入一个字符
 * 删除一个字符
 * 替换一个字符
 *
 * <h3>解题思路：</h3>
 * <p>
 * 这是一个经典的动态规划问题，使用二维DP数组来求解。
 * </p>
 *
 * <h4>1. 状态定义：</h4>
 * <p>
 * dp[i][j] 表示将 word1 的前 i 个字符转换为 word2 的前 j 个字符所需要的最少操作数
 * </p>
 *
 * <h4>2. 状态转移方程：</h4>
 * <p>
 * 当处理到 word1[i-1] 和 word2[j-1] 时：
 * </p>
 * <ol>
 * <li>如果 word1[i-1] == word2[j-1]：字符相同，无需操作
 * <br>dp[i][j] = dp[i-1][j-1]</li>
 * <li>如果 word1[i-1] != word2[j-1]：字符不同，需要进行操作
 * <br>dp[i][j] = min(
 * <ul>
 * <li>dp[i-1][j] + 1：删除操作（删除word1[i-1]）</li>
 * <li>dp[i][j-1] + 1：插入操作（在word1末尾插入word2[j-1]）</li>
 * <li>dp[i-1][j-1] + 1：替换操作（将word1[i-1]替换为word2[j-1]）</li>
 * </ul>)</li>
 * </ol>
 *
 * <h4>3. 边界条件：</h4>
 * <p>
 * <ul>
 * <li>dp[i][0] = i：将word1前i个字符转换为空字符串需要i次删除操作</li>
 * <li>dp[0][j] = j：将空字符串转换为word2前j个字符需要j次插入操作</li>
 * </ul>
 * </p>
 *
 * <h4>4. 执行示例：</h4>
 * <p>
 * 以 word1 = "horse", word2 = "ros" 为例：<br>
 * 初始DP表：<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;""&nbsp;&nbsp;r&nbsp;&nbsp;o&nbsp;&nbsp;s<br>
 * ""&nbsp;&nbsp;[0,&nbsp;&nbsp;1,&nbsp;&nbsp;2,&nbsp;&nbsp;3]<br>
 * h&nbsp;&nbsp;&nbsp;[1,&nbsp;&nbsp;1,&nbsp;&nbsp;2,&nbsp;&nbsp;3]<br>
 * o&nbsp;&nbsp;&nbsp;[2,&nbsp;&nbsp;2,&nbsp;&nbsp;1,&nbsp;&nbsp;2]<br>
 * r&nbsp;&nbsp;&nbsp;[3,&nbsp;&nbsp;2,&nbsp;&nbsp;2,&nbsp;&nbsp;2]<br>
 * s&nbsp;&nbsp;&nbsp;[4,&nbsp;&nbsp;3,&nbsp;&nbsp;3,&nbsp;&nbsp;2]<br>
 * e&nbsp;&nbsp;&nbsp;[5,&nbsp;&nbsp;4,&nbsp;&nbsp;4,&nbsp;&nbsp;3]<br>
 * <br>
 * 最终答案：dp[5][3] = 3
 * </p>
 *
 * <h4>5. 复杂度分析：</h4>
 * <p>
 * <ul>
 * <li>时间复杂度：O(m×n)，其中m和n分别是两个字符串的长度</li>
 * <li>空间复杂度：O(m×n)，用于存储DP数组</li>
 * </ul>
 * </p>
 *
 * @author lxd
 **/
public class Day20260228 {
    public int minDistance(String word1, String word2) {
        // 获取两个字符串的长度
        int m = word1.length();
        int n = word2.length();

        // 创建DP数组，dp[i][j]表示将word1前i个字符转换为word2前j个字符的最少操作数
        // 数组大小为(m+1)×(n+1)，多开一行一列便于处理边界情况
        int[][] dp = new int[m + 1][n + 1];

        // 初始化边界条件：将长度为i的字符串转换为空字符串需要i次删除操作
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }

        // 初始化边界条件：将空字符串转换为长度为j的字符串需要j次插入操作
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }

        // 填充DP数组的核心逻辑
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 如果当前字符相同，则不需要额外操作
                // 直接继承前一个状态的结果
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 如果当前字符不同，则在三种操作中选择操作数最少的方案
                    // dp[i-1][j] + 1: 删除word1[i-1]字符
                    // dp[i][j-1] + 1: 在word1末尾插入word2[j-1]字符
                    // dp[i-1][j-1] + 1: 将word1[i-1]替换为word2[j-1]
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }

        // 返回将完整word1转换为完整word2的最少操作数
        return dp[m][n];
    }
}

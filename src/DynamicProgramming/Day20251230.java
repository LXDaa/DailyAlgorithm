package DynamicProgramming;

/**
 * 647. 回文子串
 * <p>
 * 题目描述:
 * 给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
 * 回文字符串 是正着读和倒过来读一样的字符串。
 * 子字符串 是字符串中的由连续字符组成的一个序列。
 * <p>
 * 解题思路:
 * 使用动态规划方法判断回文子串。
 * <p>
 * 状态定义:
 * dp[i][j] 表示字符串 s 中从索引 i 到 j 的子串是否为回文串
 * <p>
 * 状态转移方程:
 * 1. 如果 s.charAt(i) == s.charAt(j)，即子串首尾字符相同
 * - 当 j - i <= 1 时（长度为1或2的子串），直接为回文串
 * - 当 j - i > 1 时（长度大于2的子串），需要检查内部子串 dp[i+1][j-1] 是否为回文串
 * 2. 如果 s.charAt(i) != s.charAt(j)，则 dp[i][j] = false
 * <p>
 * 遍历顺序:
 * 外层循环从 i = n-1 到 i = 0（从后往前），内层循环从 j = i 到 j = n-1
 * 这样可以保证在计算 dp[i][j] 时，dp[i+1][j-1] 已经被计算过
 * <p>
 * 边界条件:
 * 单个字符一定是回文串，即 dp[i][i] = true
 * <p>
 * 最终答案:
 * 统计所有 dp[i][j] 为 true 的数量
 *
 * @author lxd
 **/
public class Day20251230 {
    /**
     * 统计字符串中回文子串的数量
     *
     * @param s 输入字符串
     * @return 回文子串的数量
     */
    public int countSubstrings(String s) {
        // 获取字符串长度
        int n = s.length();

        // 创建 DP 数组，dp[i][j] 表示从索引 i 到 j 的子串是否为回文串
        boolean[][] dp = new boolean[n][n];

        // 记录回文子串总数
        int result = 0;

        // 从后往前遍历所有可能的子串起始位置
        for (int i = n - 1; i >= 0; i--) {
            // 从当前位置 i 开始，遍历所有可能的子串结束位置
            for (int j = i; j < n; j++) {
                // 如果首尾字符相同，则可能是回文串
                if (s.charAt(i) == s.charAt(j)) {
                    // 长度为1或2的子串，直接为回文串
                    if (j - i <= 1) {
                        result++;
                        dp[i][j] = true;
                    }
                    // 长度大于2的子串，需要检查内部子串是否为回文串
                    else if (dp[i + 1][j - 1]) {
                        result++;
                        dp[i][j] = true;
                    }
                }
                // 如果首尾字符不相同，则不是回文串，dp[i][j] 保持默认值 false
            }
        }

        // 返回回文子串总数
        return result;
    }
}
package DynamicProgramming;

import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/word-break/description/">139. 单词拆分</a>
 * <p>
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
 * <p>
 * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
 * <p>
 * 解题思路:
 * 这是一个典型的动态规划问题。我们可以将问题转化为：字符串 s 是否可以由字典中的单词组合而成。
 * <p>
 * 动态规划定义：
 * dp[i] 表示字符串 s 的前 i 个字符是否可以被字典中的单词拼接而成
 * <p>
 * 状态转移方程：
 * 对于每个位置 i，我们遍历 0 到 i-1 的所有位置 j，
 * 如果 dp[j] 为 true（表示前 j 个字符可以被拼接）且 s.substring(j, i) 在字典中，
 * 那么 dp[i] = true
 * <p>
 * 初始化：
 * dp[0] = true，表示空字符串可以被拼接（不选任何单词）
 * <p>
 * 举例说明：
 * s = "leetcode", wordDict = ["leet", "code"]
 * dp[0] = true (空字符串)
 * dp[4] = true (因为 dp[0]=true 且 "leet" 在字典中)
 * dp[8] = true (因为 dp[4]=true 且 "code" 在字典中)
 * 最终返回 dp[8] = true
 * <p>
 * 时间复杂度: O(n^2)，其中 n 是字符串 s 的长度
 * 空间复杂度: O(n)
 *
 * @author lxd
 **/
public class Day20251128 {
    public boolean wordBreak(String s, List<String> wordDict) {
        // 字符串长度
        int n = s.length();

        // dp[i] 表示字符串 s 的前 i 个字符是否可以被拆分成字典中的单词
        boolean[] dp = new boolean[n + 1];

        // 初始化：空字符串可以被拆分
        dp[0] = true;

        // 遍历字符串的每一个位置
        for (int i = 1; i <= n; i++) {
            // 对于当前位置 i，尝试所有可能的分割点 j
            for (int j = 0; j < i; j++) {
                // 如果前 j 个字符可以被拆分，且从 j 到 i 的子串在字典中
                // 则前 i 个字符可以被拆分
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    // 找到一种可行的分割方案即可跳出内层循环
                    break;
                }
            }
        }

        // 返回整个字符串是否可以被拆分
        return dp[n];
    }
}
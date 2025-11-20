package DynamicProgramming;

/**
 * @author lxd
 *
 * <a href="https://leetcode.cn/problems/ones-and-zeroes/description/">474. 一和零</a>
 * 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。
 * <p>
 * 请你找出并返回 strs 的最大子集的长度，该子集中 最多 有 m 个 0 和 n 个 1 。
 * <p>
 * 如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集 。
 * <p>
 * 解题思路:
 * 这是一个典型的二维01背包问题。
 * 1. 每个字符串可以看作一个物品，它有一定的"重量"(0的个数和1的个数)
 * 2. 背包有两个维度的容量限制：最多m个0和n个1
 * 3. 目标是尽可能多地装入物品(字符串)，使得总重量不超过背包容量
 * 4. 状态转移方程：
 * dp[i][j] 表示最多使用i个0和j个1时能形成的最大子集大小
 * 对于每个字符串，我们统计其中0和1的个数(zeroNum, oneNum)
 * dp[i][j] = max(dp[i][j], dp[i-zeroNum][j-oneNum] + 1)
 * 5. 遍历顺序很重要：需要从后往前遍历，避免重复选择同一个字符串
 * <p>
 * 时间复杂度: O(len(strs) * m * n + sum(len(s))) 其中s是strs中的字符串
 * 空间复杂度: O(m * n)
 **/
public class Day20251120 {
    /**
     * 找到满足条件的最大子集长度
     *
     * @param strs 输入的二进制字符串数组
     * @param m    最多允许的0的个数
     * @param n    最多允许的1的个数
     * @return 满足条件的最大子集长度
     */
    public int findMaxForm(String[] strs, int m, int n) {
        // dp[i][j] 表示最多使用i个0和j个1时能形成的最大子集大小
        int[][] dp = new int[m + 1][n + 1];

        // 遍历每个字符串（每个物品）
        for (String str : strs) {
            // 统计当前字符串中0和1的个数
            int zeroNum = 0;
            int oneNum = 0;
            for (char c : str.toCharArray()) {
                if (c == '0') {
                    zeroNum++;
                } else {
                    oneNum++;
                }
            }

            // 从后往前更新dp数组，防止重复选择同一字符串
            // i表示当前可用的0的个数，必须大于等于当前字符串所需0的个数
            for (int i = m; i >= zeroNum; i--) {
                // j表示当前可用的1的个数，必须大于等于当前字符串所需1的个数
                for (int j = n; j >= oneNum; j--) {
                    // 状态转移方程：
                    // 不选择当前字符串：dp[i][j]保持不变
                    // 选择当前字符串：dp[i-zeroNum][j-oneNum] + 1
                    // 取两者较大值
                    dp[i][j] = Math.max(dp[i][j], dp[i - zeroNum][j - oneNum] + 1);
                }
            }
        }
        // 返回最多使用m个0和n个1时能得到的最大子集大小
        return dp[m][n];
    }
}
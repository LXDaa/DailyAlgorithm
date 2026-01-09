package CodeMusings.DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/unique-binary-search-trees/description/">96. 不同的二叉搜索树</a>
 * <p>
 * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
 * <p>
 * 解题思路：
 * 这是一个经典的卡塔兰数(Catalan Number)问题，可以使用动态规划解决。
 * <p>
 * 动态规划思路：
 * 1. 状态定义：dp[i] 表示由 i 个不同节点组成的二叉搜索树的种数
 * 2. 状态转移方程：对于 i 个节点，可以选择第 j 个节点(1<=j<=i)作为根节点，
 * 此时左子树有 j-1 个节点，右子树有 i-j 个节点，
 * 所以以 j 为根节点的二叉搜索树种数为 dp[j-1] * dp[i-j]
 * dp[i] = sum(dp[j-1] * dp[i-j]) for j from 1 to i
 * 3. 初始条件：dp[0] = 1 (空树也是一种情况)
 * <p>
 * 举例说明 n=3 的情况：
 * 根节点为1：左子树0个节点(dp[0]) * 右子树2个节点(dp[2]) = 1 * 2 = 2
 * 根节点为2：左子树1个节点(dp[1]) * 右子树1个节点(dp[1]) = 1 * 1 = 1
 * 根节点为3：左子树2个节点(dp[2]) * 右子树0个节点(dp[0]) = 2 * 1 = 2
 * 总计：2 + 1 + 2 = 5 种
 *
 * @author lxd
 */
public class Day20251112 {
    public int numTrees(int n) {
        // dp[i] 表示由 i 个不同节点组成的二叉搜索树的种数
        int[] dp = new int[n + 1];
        // 初始化：0个节点组成1种树（空树）
        dp[0] = 1;

        // 计算1到n个节点能组成的二叉搜索树种数
        for (int i = 1; i <= n; i++) {
            // 对于i个节点，分别以第j个节点为根节点进行计算
            for (int j = 1; j <= i; j++) {
                // 左子树有j-1个节点，右子树有i-j个节点
                // 以j为根节点的二叉搜索树种数 = 左子树种数 * 右子树种数
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }
}
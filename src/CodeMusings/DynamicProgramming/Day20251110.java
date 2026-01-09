package CodeMusings.DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/unique-paths-ii/description/">63. 不同路径 II</a>
 * <p>
 * 给定一个 m x n 的整数数组 grid。一个机器人初始位于 左上角（即 grid[0][0]）。机器人尝试移动到 右下角（即 grid[m - 1][n - 1]）。机器人每次只能向下或者向右移动一步。
 * <p>
 * 网格中的障碍物和空位置分别用 1 和 0 来表示。机器人的移动路径中不能包含 任何 有障碍物的方格。
 * <p>
 * 返回机器人能够到达右下角的不同路径数量。
 * <p>
 * 测试用例保证答案小于等于 2 * 109。
 * <p>
 * 解题思路：
 * 这是一个典型的动态规划问题。使用 dp[i][j] 表示从起点到位置 (i,j) 的不同路径数量。
 * <p>
 * 状态转移方程：
 * - 如果当前位置是障碍物 (obstacleGrid[i][j] == 1)，则 dp[i][j] = 0 （无法到达）
 * - 否则 dp[i][j] = dp[i-1][j] + dp[i][j-1] （从上方和左方到达的路径数之和）
 * <p>
 * 边界条件：
 * - 第一行：如果某个位置无障碍物，则路径数继承自左边位置；遇到障碍物后，后续都为0
 * - 第一列：如果某个位置无障碍物，则路径数继承自上边位置；遇到障碍物后，后续都为0
 * - 起点：如果起点无障碍物，则 dp[0][0] = 1，否则为 0
 * <p>
 * 时间复杂度：O(m*n)
 * 空间复杂度：O(m*n)
 */
public class Day20251110 {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        if (obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1) {
            return 0;
        }

        // dp[i][j] 表示从起点到位置(i,j)的不同路径数量
        int[][] dp = new int[m][n];

        // 初始化第一列：只能向下移动
        // 如果当前位置没有障碍物且前面的位置可达，则路径数为1
        // 遇到障碍物后，后面的都不可达（因为被挡住了）
        for (int i = 0; i < m && obstacleGrid[i][0] == 0; i++) {
            dp[i][0] = 1;
        }

        // 初始化第一行：只能向右移动
        // 如果当前位置没有障碍物且前面的位置可达，则路径数为1
        // 遇到障碍物后，后面的都不可达（因为被挡住了）
        for (int j = 0; j < n && obstacleGrid[0][j] == 0; j++) {
            dp[0][j] = 1;
        }

        // 填充dp表的其余部分
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // 如果当前位置是障碍物，则无法到达，路径数为0
                // 否则，路径数等于从上方和左方到达的路径数之和
                dp[i][j] = obstacleGrid[i][j] == 0 ? dp[i - 1][j] + dp[i][j - 1] : 0;
            }
        }

        // 返回到达终点的路径数
        return dp[m - 1][n - 1];
    }
}
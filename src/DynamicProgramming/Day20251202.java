package DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/house-robber-ii/description/">213. 打家劫舍 II</a>
 * <p>
 * 你是一个专业的小偷,计划偷窃沿街的房屋,每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ,这意味着第一个房屋和最后一个房屋是紧挨着的。同时,相邻的房屋装有相互连通的防盗系统,如果两间相邻的房屋在同一晚上被小偷闯入,系统会自动报警 。
 * <p>
 * 给定一个代表每个房屋存放金额的非负整数数组,计算你 在不触动警报装置的情况下 ,今晚能够偷窃到的最高金额。
 * <p>
 * 解题思路:
 * 1. 核心问题:房屋围成一圈,意味着第一个房屋和最后一个房屋不能同时被偷
 * 2. 解决方案:分两种情况讨论
 * 情况1: 偷第一个房屋,那么最后一个房屋不能偷 -> 考虑区间 [0, n-2]
 * 情况2: 不偷第一个房屋,最后一个房屋可以考虑 -> 考虑区间 [1, n-1]
 * 3. 对这两种情况分别使用打家劫舍I的动态规划解法,取两者的最大值
 * 4. 动态规划状态转移:
 * dp[i] 表示偷到第i个房屋时能获得的最大金额
 * dp[i] = max(dp[i-1], dp[i-2] + nums[i])
 * - dp[i-1]: 不偷第i个房屋,沿用前一个状态
 * - dp[i-2] + nums[i]: 偷第i个房屋,加上i-2位置的最大金额
 * 5. 空间优化:只需要保存前两个状态,使用两个变量first和second滚动更新
 *
 * @author lxd
 **/
public class Day20251202 {
    /**
     * 主函数:处理环形房屋的打家劫舍问题
     *
     * @param nums 每个房屋的金额数组
     * @return 能偷窃到的最高金额
     */
    public int rob(int[] nums) {
        int n = nums.length;
        // 特殊情况1: 只有一个房屋,直接返回该房屋金额
        if (n == 1) {
            return nums[0];
        }
        // 特殊情况2: 只有两个房屋,返回金额较大的那个
        else if (n == 2) {
            return Math.max(nums[0], nums[1]);
        }
        // 一般情况: 分两种场景
        // 场景1: robRange(nums, 0, n-1) -> 考虑偷 [0, n-2] 范围的房屋(不包含最后一个)
        // 场景2: robRange(nums, 1, n) -> 考虑偷 [1, n-1] 范围的房屋(不包含第一个)
        // 取两种场景的最大值
        return Math.max(robRange(nums, 0, n - 1), robRange(nums, 1, n));
    }

    /**
     * 计算指定区间内能偷窃到的最高金额(打家劫舍I的空间优化版本)
     *
     * @param nums  房屋金额数组
     * @param start 起始索引(包含)
     * @param end   结束索引(不包含)
     * @return 该区间内能偷窃到的最高金额
     */
    public int robRange(int[] nums, int start, int end) {
        // first: 表示到第start个房屋时能偷窃到的最大金额，即只偷第start个房屋
        // second: 表示到第start+1个房屋时能偷窃到的最大金额，即从第start和start+1个房屋中选择金额较大的那个
        int first = nums[start], second = Math.max(nums[start], nums[start + 1]);

        // 从第三个房屋开始遍历到end(不包含end)
        for (int i = start + 2; i < end; i++) {
            // 保存当前的second值,用于更新first
            int temp = second;
            // 状态转移方程: dp[i] = max(dp[i-1], dp[i-2] + nums[i])
            // second(不偷当前房屋) vs first + nums[i](偷当前房屋)
            second = Math.max(second, first + nums[i]);
            // first向前滚动,更新为原来的second
            first = temp;
        }

        // 返回最终的最大金额
        return second;
    }
}

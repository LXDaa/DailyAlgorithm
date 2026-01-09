package CodeMusings.DynamicProgramming;

/**
 * @author lxd
 *
 * <a href="https://leetcode.cn/problems/last-stone-weight-ii/description/">1049.最后一块石头的重量II</a>
 * <p>
 * 有一堆石头，用整数数组 stones 表示。其中 stones[i] 表示第 i 块石头的重量。
 * <p>
 * 每一回合，从中选出任意两块石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
 * <p>
 * 如果 x == y，那么两块石头都会被完全粉碎；
 * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
 * 最后，最多只会剩下一块 石头。返回此石头 最小的可能重量 。如果没有石头剩下，就返回 0。
 * <p>
 * 解题思路:
 * 这是一个01背包问题的变种。核心思想是将石头分为两堆，使两堆重量差最小。
 * 通过01背包找到能凑成最接近总重量一半的石头组合，这样两堆石头的差值就是答案。
 * 举个例子：石头重量为[2,7,4,1,8,1]，总重量为23，我们希望尽可能接近11的一堆，
 * 假设可以凑成11，那么另一堆就是12，差值为1；如果只能凑成10，另一堆为13，差值为3。
 **/
public class Day20251118 {
    public int lastStoneWeightII(int[] stones) {
        // 计算石头总重量
        int n = stones.length;
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }

        // 目标重量，即总重量的一半
        int target = sum >> 1;

        // dp[j]表示容量为j的背包最多能装的石头重量
        int[] dp = new int[target + 1];

        // 遍历每块石头
        for (int i = 0; i < n; i++) {
            // 从后往前遍历背包容量，避免重复使用同一块石头
            for (int j = target; j >= stones[i]; j--) {
                // 状态转移方程：选择是否将当前石头放入背包
                // 不放：dp[j]保持原值
                // 放入：dp[j-stones[i]] + stones[i]，即之前能装下的重量加上当前石头重量
                dp[j] = Math.max(dp[j], dp[j - stones[i]] + stones[i]);
            }
        }

        // dp[target]是最接近总重量一半的石头组合重量
        // sum - dp[target]是一堆石头的重量，dp[target]是另一堆石头的重量
        // 两者相减得到最终剩余石头的最小重量
        return sum - 2 * dp[target];
    }
}
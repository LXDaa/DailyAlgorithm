package Greedy;

/**
 * <a href="https://leetcode.cn/problems/jump-game/description/">55. 跳跃游戏</a>
 * <p>
 * 给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * <p>
 * 判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
 * 
 * 解题思路：
 * 使用贪心算法解决此问题。我们不需要关心具体跳几步，只需要关注当前能覆盖到的最远距离。
 * 我们维护一个变量 coverRange 表示当前能够到达的最远下标。
 * 在遍历过程中，不断更新 coverRange 为 max(coverRange, i + nums[i])。
 * 如果在某一步发现 coverRange 已经大于等于最后一个下标的值，则说明可以到达终点。
 * 如果遍历完成后仍无法到达最后一个下标，则返回 false。
 */
public class Day20251020 {
    public boolean canJump(int[] nums) {
        // coverRange 表示当前能覆盖到的最远下标位置
        int coverRange = 0;
        
        // 只需要遍历当前能覆盖到的范围内的元素
        for (int i = 0; i <= coverRange; i++) {
            // 更新能覆盖到的最远距离：当前位置i加上从该位置能跳的最远距离nums[i]
            coverRange = Math.max(coverRange, i + nums[i]);
            
            // 如果已经能覆盖到最后一个下标，直接返回true
            if (coverRange >= nums.length - 1) {
                return true;
            }
        }
        
        // 遍历完所有可达位置后仍然不能到达最后下标，返回false
        return false;
    }
}
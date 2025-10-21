package Greedy;

/**
 * <a href="https://leetcode.cn/problems/jump-game-ii/description/">45.跳跃游戏 II</a>
 * <p>
 * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置在下标 0。
 * <p>
 * 每个元素 nums[i] 表示从索引 i 向后跳转的最大长度。换句话说，如果你在索引 i 处，你可以跳转到任意 (i + j) 处：
 * <p>
 * 0 <= j <= nums[i] 且 i + j < n
 * <p>
 * 返回到达 n - 1 的最小跳跃次数。测试用例保证可以到达 n - 1。
 * <p>
 * 解题思路:
 * 使用贪心算法解决。我们维护两个变量：
 * 1. currentCoverRange: 当前跳跃次数能到达的最远位置
 * 2. nextCoverRange: 在当前跳跃次数基础上再多跳一次能到达的最远位置
 * <p>
 * 算法过程:
 * 1. 遍历数组（除了最后一个元素，因为如果能到达最后一个元素就不需要再跳了）
 * 2. 在遍历过程中不断更新下一次跳跃能到达的最远位置(nextCoverRange)
 * 3. 当遍历到当前跳跃次数能到达的最远位置时，说明必须再跳一次了
 * 4. 此时将步数加一，并更新当前能到达的最远位置为nextCoverRange
 */
public class Day20251021 {
    public int jump(int[] nums) {
        // 记录跳跃的步数
        int steps = 0;
        // 当前跳跃次数能到达的最远位置
        int currentCoverRange = 0;
        // 在当前跳跃次数基础上再多跳一次能到达的最远位置
        int nextCoverRange = 0;
        
        // 遍历数组，注意只需要遍历到倒数第二个元素
        // 因为如果能到达最后一个元素就不需要再跳了
        for (int i = 0; i < nums.length - 1; i++) {
            // 更新下一次能到达的最远位置
            nextCoverRange = Math.max(nextCoverRange, i + nums[i]);
            
            // 如果遍历到当前能到达的最远位置，说明需要再跳一次才能到达更远的位置
            if (i == currentCoverRange) {
                // 增加跳跃次数
                steps++;
                // 更新当前能到达的最远位置为下一次能到达的最远位置
                currentCoverRange = nextCoverRange;
            }
        }
        return steps;
    }
}
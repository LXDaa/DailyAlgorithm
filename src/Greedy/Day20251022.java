package Greedy;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/maximize-sum-of-array-after-k-negations/description/">1005.K次取反后最大化的数组和</a>
 * <p>
 * 给你一个整数数组 nums 和一个整数 k ，按以下方法修改该数组：
 * <p>
 * 选择某个下标 i 并将 nums[i] 替换为 -nums[i] 。
 * <p>
 * 重复这个过程恰好 k 次。可以多次选择同一个下标 i 。
 * <p>
 * 以这种方式修改数组后，返回数组 可能的最大和 。
 * <p>
 * 解题思路：
 * <p>
 * 1. 要使数组和最大，我们应该尽可能多地将负数转为正数
 * 2. 如果负数都转完后还有剩余操作次数，需要考虑以下情况：
 *    - 如果剩余操作次数为偶数，可以对任意一个数进行偶数次操作，数值不变
 *    - 如果剩余操作次数为奇数，必须对一个数进行奇数次操作，会使该数变为相反数
 *      为了使总和最大，应该选择绝对值最小的数进行操作
 */
public class Day20251022 {
    public int largestSumAfterKNegations(int[] nums, int k) {
        // 首先对数组排序，便于优先处理负数
        Arrays.sort(nums);
        
        // 第一步：尽可能将负数变为正数
        for (int i = 0; i < nums.length && k > 0; i++) {
            // 如果当前数字是负数，则将其取反
            if (nums[i] < 0) {
                nums[i] = -nums[i];
                k--; // 操作次数减一
            }
        }
        
        // 第二步：如果还有剩余操作次数，处理剩余的k值
        if (k > 0) {
            // 如果k是奇数，必须再进行一次取反操作
            // 为了使总和最大，应该对绝对值最小的元素进行操作
            if (k % 2 == 1) {
                // 重新排序，找到绝对值最小的元素（现在都在数组前端）
                Arrays.sort(nums);
                // 对绝对值最小的元素取反
                nums[0] = -nums[0];
            }
            // 如果k是偶数，则不需要额外操作，因为对同一元素进行偶数次取反等于没有操作
        }
        
        // 计算最终数组的和
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return sum;
    }
}
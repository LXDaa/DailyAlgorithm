package HashTable;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day20250718 {
    /**
     * 15. 三数之和
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
     * <p>
     * 注意：答案中不可以包含重复的三元组。
     * https://leetcode.cn/problems/3sum/description/
     */
    public List<List<Integer>> threeSum(int[] nums) {
        //暴力解法 三层for循环 [-1,0,1,2,-1,-4]  重复    哈希表 排序
        //排序后 [-4,-1,-1,0,1,2]   遍历时重复 跳过  时间复杂度 n^3
        // 固定 a,在剩余子数组中求两数值和= -a,双指针
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);  // 关键步骤：排序

        for (int i = 0; i < nums.length - 2; i++) { //循环边界：nums.length-2 确保始终有3个元素（i, left, right）
            // 跳过重复的第一个数
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            // 若当前数已大于0，终止循环
            if (nums[i] > 0) break;

            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum < 0) {
                    left++; // 和过小，左指针右移
                } else if (sum > 0) {
                    right--; // 和过大，右指针左移
                } else {
                    // 找到有效三元组
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 跳过重复的左指针值
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    // 跳过重复的右指针值
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    // 同时移动双指针
                    left++;
                    right--;
                }
            }
        }
        return result;
    }
}

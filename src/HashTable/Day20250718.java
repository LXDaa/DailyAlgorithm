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
        int n = nums.length;
        List<List<Integer>> result = new ArrayList();
        Arrays.sort(nums);
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                return result;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                }
            }
        }
        return result;
    }
}

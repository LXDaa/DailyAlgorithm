package CodeTop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/3sum/description/">15. 三数之和</a>
 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
 * <p>
 * 解题思路：
 * 1. 对数组进行排序，使得相同元素相邻，便于去重和使用双指针技巧
 * 2. 使用固定指针i遍历数组，配合左右双指针查找满足条件的三元组
 * 3. 去重策略：
 * - 当nums[i]与前一个元素相同时，跳过以避免重复三元组
 * - 当找到满足条件的三元组后，移动left/right指针跳过重复元素
 * 4. 双指针移动策略：
 * - 如果三数之和大于0，则右指针左移使和变小
 * - 如果三数之和小于0，则左指针右移使和变大
 * - 如果三数之和等于0，则记录结果并同时移动两指针
 * <p>
 * 时间复杂度：O(n^2)，其中n为数组长度
 * 空间复杂度：O(1)，不考虑结果列表的空间消耗
 * <p>
 * 示例：
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 执行过程：
 * 1. 排序后：[-4,-1,-1,0,1,2]
 * 2. i=0时(nums[i]=-4)，由于nums[i]>0不成立，继续
 * 3. i=1时(nums[i]=-1)，left=2,right=5，寻找和为1的两数
 * - nums[2]+nums[5] = -1+2 = 1，sum=0，添加[-1,-1,2]
 * - 移动指针继续查找...
 * 输出：[[-1,-1,2],[-1,0,1]]
 *
 * @author lxd
 **/
public class Day20260116 {
    /**
     * 寻找所有和为0的不重复三元组
     *
     * @param nums 输入的整数数组
     * @return 所有和为0的不重复三元组列表
     */
    public List<List<Integer>> threeSum(int[] nums) {
        // 结果列表，存储所有符合条件的三元组
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;

        // 对数组进行排序，这是双指针技巧的前提
        Arrays.sort(nums);

        // 遍历数组，固定第一个数nums[i]，然后用双指针寻找另外两个数
        for (int i = 0; i < n; i++) {
            // 优化：如果当前数字大于0，则后续不可能存在和为0的三元组
            // 因为数组已排序，后面的所有数字都大于0，三个正数之和不可能为0
            if (nums[i] > 0) {
                return res;
            }

            // 去重：如果当前数字与前一个数字相同，跳过以避免重复三元组
            // 这样确保了每个相同的nums[i]只处理一次
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 设置双指针：左指针从i+1开始，右指针从数组末尾开始
            int left = i + 1, right = n - 1;

            // 双指针向中间移动，寻找满足条件的组合
            while (left < right) {
                // 计算三数之和
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    // 找到满足条件的三元组，添加到结果列表
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // 去重：跳过左侧重复元素
                    // 避免出现重复的三元组，如[-1,-1,2]被重复计算
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }

                    // 去重：跳过右侧重复元素
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }

                    // 同时移动两个指针，继续寻找下一个可能的三元组
                    left++;
                    right--;
                } else if (sum > 0) {
                    // 三数之和大于0，需要减小和值
                    // 由于数组已排序，右指针左移会使和值变小
                    right--;
                } else {
                    // 三数之和小于0，需要增大和值
                    // 左指针右移会使和值变大
                    left++;
                }
            }
        }
        return res;
    }
}
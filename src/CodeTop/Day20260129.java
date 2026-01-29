package CodeTop;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/two-sum/description/">1.两数之和</a>
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。
 * <p>
 * 你可以按任意顺序返回答案。
 *
 * @author lxd
 **/
public class Day20260129 {
    /**
     * 使用哈希表查找两数之和
     * <p>
     * 解题思路：
     * 1. 遍历数组中的每个元素 nums[i]
     * 2. 对于当前元素 nums[i]，计算其补数 complement = target - nums[i]
     * 3. 检查哈希表中是否已存在该补数，如果存在则直接返回结果
     * 4. 如果不存在，则将当前元素及其索引存入哈希表，继续遍历
     * <p>
     * 算法优势：
     * - 时间复杂度：O(n)，只需要一次遍历
     * - 空间复杂度：O(n)，使用哈希表存储已访问的元素
     * - 相比暴力解法 O(n²) 的时间复杂度，效率更高
     * <p>
     * 执行过程示例：
     * 假设 nums = [2, 7, 11, 15], target = 9
     * i=0: nums[0]=2, complement=7, map={} -> map.put(2,0)
     * i=1: nums[1]=7, complement=2, map={2=0} -> map.containsKey(2)为true -> 返回 [1,0]
     *
     * @param nums   输入的整数数组
     * @param target 目标和
     * @return 包含两个元素索引的数组，这两个元素相加等于目标值
     */
    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;
        // 创建哈希表存储数组元素值到索引的映射关系
        Map<Integer, Integer> map = new HashMap();

        // 遍历数组
        for (int i = 0; i < n; i++) {
            // 计算当前元素需要配对的目标值
            int complement = target - nums[i];

            // 如果哈希表中存在该目标值，说明找到了两数之和等于target的组合
            if (map.containsKey(complement)) {
                // 返回当前索引和之前存储的索引
                return new int[]{i, map.get(complement)};
            }

            // 将当前元素值和索引存入哈希表，供后续元素匹配使用
            map.put(nums[i], i);
        }

        // 如果未找到满足条件的组合，返回null
        return null;
    }
}

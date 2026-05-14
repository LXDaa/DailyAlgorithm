package Hot100.双指针;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot006 {
    /**
     * <a href="https://leetcode.cn/problems/3sum/description/">15. 三数之和</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。</p>
     * <p>请你返回所有和为 0 且不重复的三元组。</p>
     * <p><b>注意</b>：答案中不可以包含重复的三元组。</p>
     *
     * <h3>💡 核心思路：排序 + 双指针</h3>
     * <ul>
     *   <li><b>基本思想</b>：先排序，然后固定一个数，用双指针在剩余部分找两个数使得三数之和为 0
     *     <ul>
     *       <li>外层循环：固定第一个数 nums[i]</li>
     *       <li>内层双指针：在 i+1 到 n-1 范围内找两个数，使它们的和等于 -nums[i]</li>
     *       <li>去重处理：跳过重复的元素，避免重复的三元组</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n²)，排序 O(n log n) + 双层循环 O(n²)</li>
     *   <li><b>空间复杂度</b>：O(1)，不考虑返回值的空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [-1, 0, 1, 2, -1, -4]
     *
     * 第一步：排序
     * nums = [-4, -1, -1, 0, 1, 2]
     *
     * 第二步：遍历每个元素作为第一个数
     *
     * 【i=0】nums[0] = -4
     *   - target = 4
     *   - l = 1, r = 5
     *   - nums[1] + nums[5] = -1 + 2 = 1 < 4 → l++
     *   - nums[2] + nums[5] = -1 + 2 = 1 < 4 → l++
     *   - nums[3] + nums[5] = 0 + 2 = 2 < 4 → l++
     *   - nums[4] + nums[5] = 1 + 2 = 3 < 4 → l++
     *   - l >= r，结束
     *
     * 【i=1】nums[1] = -1
     *   - target = 1
     *   - l = 2, r = 5
     *   - nums[2] + nums[5] = -1 + 2 = 1 == 1 ✅
     *     → 找到三元组：[-1, -1, 2]
     *     → l++, r--
     *   - l = 3, r = 4
     *   - nums[3] + nums[4] = 0 + 1 = 1 == 1 ✅
     *     → 找到三元组：[-1, 0, 1]
     *     → l++, r--
     *   - l >= r，结束
     *
     * 【i=2】nums[2] = -1
     *   - 与前一个元素相同，跳过（去重）
     *
     * 【i=3】nums[3] = 0
     *   - target = 0
     *   - l = 4, r = 5
     *   - nums[4] + nums[5] = 1 + 2 = 3 > 0 → r--
     *   - l >= r，结束
     *
     * 最终结果：[[-1, -1, 2], [-1, 0, 1]] ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要排序？
     *    - 排序后可以使用双指针技巧
     *    - 方便去重：重复元素相邻，容易判断
     *    - 当 nums[i] > 0 时可以提前终止（后面的数都更大）
     *
     * 2️⃣ 如何去重？
     *    - 外层去重：如果 nums[i] == nums[i-1]，跳过
     *    - 内层去重：找到答案后，跳过重复的 nums[l] 和 nums[r]
     *    - 确保每个三元组只被添加一次
     *
     * 3️⃣ 双指针的移动逻辑？
     *    - 如果和 > target：右指针左移（减小和）
     *    - 如果和 < target：左指针右移（增大和）
     *    - 如果和 == target：记录答案，同时移动两个指针
     *
     * 4️⃣ 为什么 nums[i] > 0 时可以 break？
     *    - 数组已排序，后面的数都 >= nums[i] > 0
     *    - 三个正数相加不可能等于 0
     *
     * 5️⃣ 时间复杂度分析：
     *    - 排序：O(n log n)
     *    - 外层循环：O(n)
     *    - 内层双指针：O(n)
     *    - 总体：O(n²)
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>去重是关键</b>：必须在多个位置进行去重判断</li>
     *   <li><b>排序的重要性</b>：不仅为了双指针，也为了方便去重</li>
     *   <li><b>边界条件</b>：数组长度小于 3 时直接返回空列表</li>
     *   <li><b>剪枝优化</b>：nums[i] > 0 时可以直接 break</li>
     * </ul>
     *
     * @param nums 整数数组
     * @return 所有和为 0 且不重复的三元组列表
     */
    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;

        // 📊 排序：为双指针和去重做准备
        Arrays.sort(nums);

        List<List<Integer>> res = new ArrayList<>();

        // 🔄 外层循环：固定第一个数
        for (int i = 0; i < n; i++) {
            // ✂️ 剪枝：如果第一个数大于 0，后面的数都更大，不可能和为 0
            if (nums[i] > 0) {
                break;
            }

            // 🚫 去重：跳过重复的第一个数
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 🎯 目标值：需要在剩余部分找到两个数，使它们的和等于 target
            int target = -nums[i];

            // 📍 初始化双指针：左指针从 i+1 开始，右指针从末尾开始
            int l = i + 1, r = n - 1;

            // 🔍 内层双指针查找
            while (l < r) {
                if (nums[l] + nums[r] > target) {
                    // ➖ 和太大，右指针左移
                    r--;
                } else if (nums[l] + nums[r] < target) {
                    // ➕ 和太小，左指针右移
                    l++;
                } else {
                    // ✅ 找到符合条件的三元组
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));

                    // 🔀 移动两个指针
                    l++;
                    r--;

                    // 🚫 去重：跳过重复的左指针元素
                    while (l < r && nums[l] == nums[l - 1]) {
                        l++;
                    }

                    // 🚫 去重：跳过重复的右指针元素
                    while (l < r && nums[r] == nums[r + 1]) {
                        r--;
                    }
                }
            }
        }

        return res;
    }
}

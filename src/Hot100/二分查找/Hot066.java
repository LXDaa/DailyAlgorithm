package Hot100.二分查找;

/**
 * @author lxd
 **/
public class Hot066 {
    /**
     * <a href="https://leetcode.cn/problems/search-in-rotated-sorted-array/description/">33. 搜索旋转排序数组</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>整数数组 nums 按升序排列，数组中的值互不相同。</p>
     * <p>在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标从 0 开始计数）。</p>
     * <p>给你旋转后的数组 nums 和一个整数 target，如果 nums 中存在这个目标值 target，则返回它的下标，否则返回 -1。</p>
     * <p>你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。</p>
     *
     * <h3>💡 核心思路：二分查找 + 旋转特性判断</h3>
     * <ul>
     *   <li><b>基本思想</b>：利用旋转数组的特性，在二分查找中判断哪一部分是有序的
     *     <ul>
     *       <li>计算中间位置 mid</li>
     *       <li>判断左半部分是否有序：nums[mid] >= nums[left]</li>
     *       <li>如果左半部分有序，判断 target 是否在左半部分</li>
     *       <li>如果右半部分有序，判断 target 是否在右半部分</li>
     *       <li>根据判断结果调整搜索范围</li>
     *     </ul>
     *   </li>
     *   <li><b>旋转数组的特性</b>：
     *     <ul>
     *       <li>数组在某个点旋转，分成两个有序子数组</li>
     *       <li>左子数组的所有元素 > 右子数组的所有元素</li>
     *       <li>任意取中间位置，至少有一半是有序的</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(log n)，二分查找的标准复杂度</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常数级别的额外空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [4,5,6,7,0,1,2], target = 0
     *
     * 旋转数组结构：
     *   左有序部分：[4,5,6,7]
     *   右有序部分：[0,1,2]
     *   旋转点：index = 4（值为 0）
     *
     * 查找 target = 0：
     *
     * 初始状态：left = 0, right = 6
     *
     * 第一次循环：
     *   mid = 0 + (6 - 0) / 2 = 3
     *   nums[mid] = 7
     *   nums[mid] >= nums[left] → 左半部分有序 ✅
     *   target = 0 是否在 [4,7)？0 >= 4 && 0 < 7 ❌
     *   left = mid + 1 = 4
     *
     * 第二次循环：
     *   mid = 4 + (6 - 4) / 2 = 5
     *   nums[mid] = 1
     *   nums[mid] >= nums[left]（1 >= 0）→ 右半部分有序 ✅
     *   target = 0 是否在 (1,2]？0 > 1 && 0 <= 2 ❌
     *   right = mid - 1 = 4
     *
     * 第三次循环：
     *   mid = 4 + (4 - 4) / 2 = 4
     *   nums[mid] = 0 == target ✅
     *   return mid = 4 ✅
     *
     * 查找 target = 3（不存在）：
     *
     * 循环过程：
     *   mid=3: nums[3]=7 >= nums[0]=4 → 左有序
     *          3 在 [4,7)？3 >= 4 && 3 < 7 ❌
     *          left = 4
     *
     *   mid=5: nums[5]=1 >= nums[4]=0 → 右有序
     *          3 在 (1,2]？3 > 1 && 3 <= 2 ❌
     *          right = 4
     *
     *   mid=4: nums[4]=0 < 3
     *          left = 5
     *
     * 循环结束：
     *   return -1 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 如何判断哪一部分有序？
     *    - nums[mid] >= nums[left]：左半部分有序
     *    - nums[mid] < nums[left]：右半部分有序
     *    - 这是旋转数组的关键特性
     *
     * 2️⃣ 左半部分有序时如何判断？
     *    - 判断 target 是否在 [nums[left], nums[mid]) 范围内
     *    - 如果在，right = mid - 1
     *    - 如果不在，left = mid + 1
     *
     * 3️⃣ 右半部分有序时如何判断？
     *    - 判断 target 是否在 (nums[mid], nums[right]] 范围内
     *    - 如果在，left = mid + 1
     *    - 如果不在，right = mid - 1
     *
     * 4️⃣ 为什么比较的是 nums[mid] >= nums[left]？
     *    - 旋转数组中，左半部分的值都大于右半部分
     *    - 如果 nums[mid] >= nums[left]，说明 mid 在左半部分
     *    - 反之，mid 在右半部分
     *
     * 5️⃣ 边界情况：数组没有旋转
     *    - 如果数组完全有序（k=0）
     *    - nums[mid] >= nums[left] 始终成立
     *    - 算法退化为普通二分查找
     *
     * 6️⃣ 时间复杂度分析：
     *    - 每次循环将搜索范围缩小一半
     *    - 时间复杂度 O(log n)
     *
     * 7️⃣ 边界情况测试：
     *    - 单元素数组：nums = [1], target = 1 → return 0
     *    - 旋转点在开头：nums = [1,2,3,4], target = 3 → return 2
     *    - 旋转点在末尾：nums = [2,3,4,5,1], target = 1 → return 4
     *    - 目标值在左半部分：nums = [4,5,6,7,0,1,2], target = 5 → return 1
     *    - 目标值在右半部分：nums = [4,5,6,7,0,1,2], target = 1 → return 5
     *
     * 8️⃣ 完整流程总结：
     *
     *    初始化：left = 0, right = nums.length - 1
     *
     *    while left <= right：
     *      mid = left + (right - left) / 2
     *      if nums[mid] == target → return mid
     *
     *      if nums[mid] >= nums[left]：
     *        // 左半部分有序
     *        if target >= nums[left] && target < nums[mid]：
     *          right = mid - 1
     *        else：
     *          left = mid + 1
     *      else：
     *        // 右半部分有序
     *        if target > nums[mid] && target <= nums[right]：
     *          left = mid + 1
     *        else：
     *          right = mid - 1
     *
     *    return -1
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>整数溢出</b>：使用 left + (right - left) / 2 避免溢出</li>
     *   <li><b>循环条件</b>：必须是 left <= right，不能是 left < right</li>
     *   <li><b>判断条件</b>：nums[mid] >= nums[left]，包含等于情况</li>
     *   <li><b>区间判断</b>：左半部分是 [left, mid)，右半部分是 (mid, right]</li>
     *   <li><b>元素唯一</b>：题目保证元素互不相同，简化了判断逻辑</li>
     * </ul>
     *
     * @param nums   旋转后的整数数组
     * @param target 目标值
     * @return 目标值的索引，未找到返回 -1
     */
    public int search(int[] nums, int target) {
        // 📋 初始化左右指针
        int left = 0, right = nums.length - 1;

        // 🔄 二分查找循环
        while (left <= right) {
            // 📐 计算中间位置（避免溢出）
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                // ✅ 找到目标值，返回索引
                return mid;
            }

            // 🔍 判断左半部分是否有序
            if (nums[mid] >= nums[left]) {
                // 📌 左半部分有序
                if (target >= nums[left] && target < nums[mid]) {
                    // 🔍 target 在左半部分
                    right = mid - 1;
                } else {
                    // 🔍 target 在右半部分
                    left = mid + 1;
                }
            } else {
                // 📌 右半部分有序
                if (target > nums[mid] && target <= nums[right]) {
                    // 🔍 target 在右半部分
                    left = mid + 1;
                } else {
                    // 🔍 target 在左半部分
                    right = mid - 1;
                }
            }
        }

        // ❌ 未找到目标值
        return -1;
    }
}

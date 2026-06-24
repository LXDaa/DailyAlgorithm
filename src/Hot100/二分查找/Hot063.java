package Hot100.二分查找;

/**
 * @author lxd
 **/
public class Hot063 {
    /**
     * <a href="https://leetcode.cn/problems/search-insert-position/description/">35. 搜索插入位置</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。</p>
     * <p>请必须使用时间复杂度为 O(log n) 的算法。</p>
     *
     * <h3>💡 核心思路：二分查找</h3>
     * <ul>
     *   <li><b>基本思想</b>：利用有序数组的特性，通过二分查找定位目标值
     *     <ul>
     *       <li>初始化左指针 left = 0，右指针 right = nums.length - 1</li>
     *       <li>计算中间位置 mid = left + (right - left) / 2（避免溢出）</li>
     *       <li>比较 nums[mid] 与 target 的大小</li>
     *       <li>根据比较结果调整搜索范围</li>
     *       <li>循环结束时，left 即为插入位置</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么返回 left</b>：
     *     <ul>
     *       <li>当 target 小于 nums[mid] 时，right = mid - 1</li>
     *       <li>当 target 大于 nums[mid] 时，left = mid + 1</li>
     *       <li>循环结束时，left > right，此时 left 就是插入位置</li>
     *       <li>如果找到目标值，直接返回 mid</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(log n)，二分查找的标准复杂度</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常数级别的额外空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例1：nums = [1, 3, 5, 6], target = 5
     *
     * 初始状态：
     *   left = 0, right = 3
     *
     * 第一次循环：
     *   mid = 0 + (3 - 0) / 2 = 1
     *   nums[1] = 3 < 5
     *   left = mid + 1 = 2
     *
     * 第二次循环：
     *   mid = 2 + (3 - 2) / 2 = 2
     *   nums[2] = 5 == 5 ✅
     *   return mid = 2
     *
     * 示例2：nums = [1, 3, 5, 6], target = 2
     *
     * 初始状态：
     *   left = 0, right = 3
     *
     * 第一次循环：
     *   mid = 0 + (3 - 0) / 2 = 1
     *   nums[1] = 3 > 2
     *   right = mid - 1 = 0
     *
     * 第二次循环：
     *   mid = 0 + (0 - 0) / 2 = 0
     *   nums[0] = 1 < 2
     *   left = mid + 1 = 1
     *
     * 循环结束（left > right）：
     *   return left = 1 ✅
     *
     * 示例3：nums = [1, 3, 5, 6], target = 7
     *
     * 循环过程：
     *   mid=1: nums[1]=3<7 → left=2
     *   mid=2: nums[2]=5<7 → left=3
     *   mid=3: nums[3]=6<7 → left=4
     *
     * 循环结束：
     *   return left = 4（插入到末尾）✅
     *
     * 示例4：nums = [1, 3, 5, 6], target = 0
     *
     * 循环过程：
     *   mid=1: nums[1]=3>0 → right=0
     *   mid=0: nums[0]=1>0 → right=-1
     *
     * 循环结束：
     *   return left = 0（插入到开头）✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用 left + (right - left) / 2 而不是 (left + right) / 2？
     *    - 防止整数溢出
     *    - 如果 left 和 right 都接近 Integer.MAX_VALUE
     *    - (left + right) / 2 可能会溢出
     *    - left + (right - left) / 2 等价但更安全
     *
     * 2️⃣ 循环条件为什么是 left <= right？
     *    - 因为我们需要检查所有可能的位置
     *    - 当 left == right 时，还需要检查一次
     *    - 如果用 left < right，可能会漏掉最后一个元素
     *
     * 3️⃣ 为什么返回 left 而不是 right？
     *    - 循环结束时，left > right
     *    - left 指向第一个大于 target 的位置
     *    - 这正是应该插入的位置
     *    - right 指向最后一个小于 target 的位置
     *
     * 4️⃣ 四种情况的处理：
     *    - target 在数组开头之前 → return 0
     *    - target 在数组中 → return mid
     *    - target 在数组中间位置 → return left
     *    - target 在数组末尾之后 → return nums.length
     *
     * 5️⃣ 二分查找的模板：
     *    - 初始化：left = 0, right = nums.length - 1
     *    - 循环：while (left <= right)
     *    - 计算 mid：mid = left + (right - left) / 2
     *    - 判断：
     *      if nums[mid] == target → return mid
     *      else if nums[mid] < target → left = mid + 1
     *      else → right = mid - 1
     *    - 返回：return left
     *
     * 6️⃣ 时间复杂度分析：
     *    - 每次循环将搜索范围缩小一半
     *    - 最多需要 log2(n) 次循环
     *    - 时间复杂度 O(log n)
     *
     * 7️⃣ 边界情况测试：
     *    - 空数组：nums = [], target = 5 → return 0
     *    - 单元素数组：nums = [1], target = 1 → return 0
     *    - 单元素数组：nums = [1], target = 0 → return 0
     *    - 单元素数组：nums = [1], target = 2 → return 1
     *
     * 8️⃣ 与普通二分查找的区别：
     *    - 普通二分查找找不到返回 -1
     *    - 本题找不到返回插入位置
     *    - 关键在于循环结束后的返回值
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>整数溢出</b>：使用 left + (right - left) / 2 避免溢出</li>
     *   <li><b>循环条件</b>：必须是 left <= right，不能是 left < right</li>
     *   <li><b>返回值</b>：循环结束后返回 left，不是 right</li>
     *   <li><b>边界情况</b>：考虑空数组、单元素数组、target 在边界外</li>
     *   <li><b>有序数组</b>：题目保证数组有序，这是二分查找的前提</li>
     * </ul>
     *
     * @param nums   排序后的整数数组
     * @param target 目标值
     * @return 目标值的索引，如果不存在则返回插入位置
     */
    public int searchInsert(int[] nums, int target) {
        // 📋 初始化左右指针
        int left = 0, right = nums.length - 1;

        // 🔄 二分查找循环
        while (left <= right) {
            // 📐 计算中间位置（避免溢出）
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                // ✅ 找到目标值，返回索引
                return mid;
            } else if (nums[mid] < target) {
                // 🔍 target 在右半部分
                left = mid + 1;
            } else {
                // 🔍 target 在左半部分
                right = mid - 1;
            }
        }

        // 🎯 未找到目标值，返回插入位置
        return left;
    }
}

package Hot100.二分查找;

/**
 * @author lxd
 **/
public class Hot065 {
    /**
     * <a href="https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/description/">34. 在排序数组中查找元素的第一个和最后一个位置</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。</p>
     * <p>如果数组中不存在目标值 target，返回 [-1, -1]。</p>
     * <p>请你设计并实现时间复杂度为 O(log n) 的算法解决此问题。</p>
     *
     * <h3>💡 核心思路：两次二分查找</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用两次二分查找分别定位目标值的左边界和右边界
     *     <ul>
     *       <li>第一次查找：找到第一个等于 target 的位置（左边界）</li>
     *       <li>第二次查找：找到最后一个等于 target 的位置（右边界）</li>
     *       <li>通过 isLeft 参数控制查找方向</li>
     *       <li>找到目标值后不立即返回，而是继续缩小搜索范围</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么需要两次查找</b>：
     *     <ul>
     *       <li>数组中可能存在多个相同的目标值</li>
     *       <li>一次二分查找只能找到任意一个匹配位置</li>
     *       <li>需要分别查找左边界和右边界</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(log n)，两次二分查找</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常数级别的额外空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [5,7,7,8,8,10], target = 8
     *
     * 【查找左边界】isLeft = true
     *
     * 初始状态：left = 0, right = 5, res = -1
     *
     * 第一次循环：
     *   mid = 0 + (5 - 0) / 2 = 2
     *   nums[2] = 7 < 8
     *   left = mid + 1 = 3
     *
     * 第二次循环：
     *   mid = 3 + (5 - 3) / 2 = 4
     *   nums[4] = 8 == 8 ✅
     *   res = 4（记录位置）
     *   isLeft = true → right = mid - 1 = 3（继续向左搜索）
     *
     * 第三次循环：
     *   mid = 3 + (3 - 3) / 2 = 3
     *   nums[3] = 8 == 8 ✅
     *   res = 3（更新位置）
     *   isLeft = true → right = mid - 1 = 2
     *
     * 循环结束（left > right）：
     *   return res = 3 ✅（左边界）
     *
     * 【查找右边界】isLeft = false
     *
     * 初始状态：left = 0, right = 5, res = -1
     *
     * 第一次循环：
     *   mid = 2, nums[2] = 7 < 8 → left = 3
     *
     * 第二次循环：
     *   mid = 4, nums[4] = 8 == 8 ✅
     *   res = 4（记录位置）
     *   isLeft = false → left = mid + 1 = 5（继续向右搜索）
     *
     * 第三次循环：
     *   mid = 5 + (5 - 5) / 2 = 5
     *   nums[5] = 10 > 8 → right = mid - 1 = 4
     *
     * 循环结束（left > right）：
     *   return res = 4 ✅（右边界）
     *
     * 【最终结果】
     *
     * res = [3, 4] ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 如何查找左边界？
     *    - isLeft = true
     *    - 找到目标值后，设置 right = mid - 1
     *    - 继续向左搜索，找到第一个匹配位置
     *    - res 记录每次找到的位置，最终保留最左边的
     *
     * 2️⃣ 如何查找右边界？
     *    - isLeft = false
     *    - 找到目标值后，设置 left = mid + 1
     *    - 继续向右搜索，找到最后一个匹配位置
     *    - res 记录每次找到的位置，最终保留最右边的
     *
     * 3️⃣ 为什么找到目标值后不立即返回？
     *    - 因为可能还有更左边或更右边的目标值
     *    - 需要继续搜索直到循环结束
     *    - res 变量记录当前找到的位置
     *
     * 4️⃣ res 初始值为什么是 -1？
     *    - 如果没有找到目标值，返回 -1
     *    - 如果找到目标值，res 会被更新
     *    - 初始值确保未找到时返回正确结果
     *
     * 5️⃣ 二分查找的模板：
     *    - 初始化：left = 0, right = nums.length - 1, res = -1
     *    - 循环：while (left <= right)
     *    - 计算 mid：mid = left + (right - left) / 2
     *    - 判断：
     *      if nums[mid] < target → left = mid + 1
     *      else if nums[mid] > target → right = mid - 1
     *      else:
     *        res = mid
     *        if isLeft → right = mid - 1
     *        else → left = mid + 1
     *    - 返回：return res
     *
     * 6️⃣ 时间复杂度分析：
     *    - 两次二分查找，每次 O(log n)
     *    - 总体时间复杂度 O(log n)
     *
     * 7️⃣ 边界情况测试：
     *    - 目标值不存在：nums = [1,2,3], target = 4 → [-1,-1]
     *    - 目标值只出现一次：nums = [1,2,3], target = 2 → [1,1]
     *    - 目标值出现在开头：nums = [2,2,3], target = 2 → [0,1]
     *    - 目标值出现在末尾：nums = [1,2,2], target = 2 → [1,2]
     *    - 所有元素都是目标值：nums = [2,2,2], target = 2 → [0,2]
     *
     * 8️⃣ 与普通二分查找的区别：
     *    - 普通二分查找：找到即返回
     *    - 本题：找到后继续搜索边界
     *    - 通过 isLeft 参数复用查找逻辑
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>整数溢出</b>：使用 left + (right - left) / 2 避免溢出</li>
     *   <li><b>循环条件</b>：必须是 left <= right，不能是 left < right</li>
     *   <li><b>res 初始化</b>：必须初始化为 -1，表示未找到</li>
     *   <li><b>边界控制</b>：找到目标值后根据 isLeft 调整边界</li>
     *   <li><b>有序数组</b>：题目保证数组有序，这是二分查找的前提</li>
     * </ul>
     *
     * @param nums   排序后的整数数组
     * @param target 目标值
     * @return 目标值的起始位置和结束位置
     */
    public int[] searchRange(int[] nums, int target) {
        // 🔍 查找左边界和右边界
        return new int[]{search(nums, target, true), search(nums, target, false)};
    }

    /**
     * 二分查找目标值的边界
     *
     * @param nums   排序后的整数数组
     * @param target 目标值
     * @param isLeft 是否查找左边界
     * @return 目标值的边界位置，未找到返回 -1
     */
    private int search(int[] nums, int target, boolean isLeft) {
        // 📋 初始化左右指针和结果
        int left = 0, right = nums.length - 1, res = -1;

        // 🔄 二分查找循环
        while (left <= right) {
            // 📐 计算中间位置（避免溢出）
            int mid = left + (right - left) / 2;

            if (nums[mid] < target) {
                // 🔍 target 在右半部分
                left = mid + 1;
            } else if (nums[mid] > target) {
                // 🔍 target 在左半部分
                right = mid - 1;
            } else {
                // ✅ 找到目标值，记录位置
                res = mid;
                if (isLeft) {
                    // 🔍 查找左边界：继续向左搜索
                    right = mid - 1;
                } else {
                    // 🔍 查找右边界：继续向右搜索
                    left = mid + 1;
                }
            }
        }

        // 🎯 返回边界位置
        return res;
    }
}

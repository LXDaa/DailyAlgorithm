package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/description/">34. 在排序数组中查找元素的第一个和最后一个位置</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。</p>
 * <p>如果数组中不存在目标值 target，返回 [-1, -1]。</p>
 * <p><b>要求</b>：必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。</p>
 *
 * <h3>💡 核心思路：二分查找（Binary Search）</h3>
 * <ul>
 *   <li><b>问题特点</b>：有序数组 + O(log n) 时间复杂度 → 二分查找</li>
 *   <li><b>关键挑战</b>：需要找到 target 的<b>左边界</b>和<b>右边界</b></li>
 *   <li><b>解决方案</b>：两次二分查找
 *     <ul>
 *       <li>第一次：找左边界（第一个等于 target 的位置）</li>
 *       <li>第二次：找右边界（最后一个等于 target 的位置）</li>
 *     </ul>
 *   </li>
 *   <li><b>统一函数</b>：通过 isLeft 参数控制查找左边界还是右边界</li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>查找左边界</b>：
 *     <ul>
 *       <li>当 nums[mid] == target 时，记录 index = mid</li>
 *       <li>继续向左搜索：right = mid - 1（看左边是否还有 target）</li>
 *       <li>最终 index 就是最左边的 target 位置</li>
 *     </ul>
 *   </li>
 *   <li><b>查找右边界</b>：
 *     <ul>
 *       <li>当 nums[mid] == target 时，记录 index = mid</li>
 *       <li>继续向右搜索：left = mid + 1（看右边是否还有 target）</li>
 *       <li>最终 index 就是最右边的 target 位置</li>
 *     </ul>
 *   </li>
 *   <li><b>返回结果</b>：[左边界, 右边界]，如果未找到则为 [-1, -1]</li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例：nums = [5,7,7,8,8,10], target = 8
 *
 * 【查找左边界】isLeft = true
 *
 * 初始: left=0, right=5
 *
 * 第1轮:
 *   mid = 0 + (5-0)/2 = 2
 *   nums[2] = 7 < 8
 *   → left = 3
 *   [5, 7, 7, 8, 8, 10]
 *    L        M     R
 *
 * 第2轮:
 *   mid = 3 + (5-3)/2 = 4
 *   nums[4] = 8 == 8 ✅
 *   → index = 4, right = 3 (继续向左找)
 *   [5, 7, 7, 8, 8, 10]
 *              L  R
 *
 * 第3轮:
 *   mid = 3 + (3-3)/2 = 3
 *   nums[3] = 8 == 8 ✅
 *   → index = 3, right = 2 (继续向左找)
 *   [5, 7, 7, 8, 8, 10]
 *           LR
 *
 * 循环结束 (left > right)
 * 左边界 = 3 ✅
 *
 * 【查找右边界】isLeft = false
 *
 * 初始: left=0, right=5
 *
 * 第1轮:
 *   mid = 2, nums[2] = 7 < 8
 *   → left = 3
 *
 * 第2轮:
 *   mid = 4, nums[4] = 8 == 8 ✅
 *   → index = 4, left = 5 (继续向右找)
 *
 * 第3轮:
 *   mid = 5, nums[5] = 10 > 8
 *   → right = 4
 *
 * 循环结束 (left > right)
 * 右边界 = 4 ✅
 *
 * 最终结果: [3, 4]
 * </pre>
 *
 * <h3>🔑 关键技巧解析</h3>
 * <pre>
 * 1️⃣ 为什么找到 target 后不立即返回？
 *    - 因为要找的是边界，不是任意一个位置
 *    - 找到后要 continue 搜索，看是否还有更左/更右的 target
 *
 * 2️⃣ index 的作用是什么？
 *    - 记录最后一次找到 target 的位置
 *    - 左边界搜索：index 会不断向左更新，最终停在最左边
 *    - 右边界搜索：index 会不断向右更新，最终停在最右边
 *
 * 3️⃣ 为什么要用 index = -1 初始化？
 *    - 如果整个搜索过程都没找到 target，返回 -1
 *    - 表示 target 不存在于数组中
 *
 * 4️⃣ left == right 时的优化？
 *    - 代码中有个提前退出：if (left == right) break;
 *    - 但实际上这个优化可能有问题，建议去掉
 *    - 标准做法是让循环自然结束
 *
 * 5️⃣ 时间复杂度：O(log n)
 *    - 两次二分查找，每次 O(log n)
 *    - 总体仍是 O(log n)
 *
 * 6️⃣ 空间复杂度：O(1)
 *    - 只使用了常数个变量
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>防溢出</b>：mid = left + (right - left) / 2 避免 (left + right) 溢出</li>
 *   <li><b>边界条件</b>：while (left <= right) 使用 <=，确保单个元素也能被检查</li>
 *   <li><b>提前退出</b>：代码中的 if (left == right) break; 可能有 bug，建议移除</li>
 *   <li><b>统一接口</b>：通过 boolean 参数复用二分查找逻辑，减少代码重复</li>
 *   <li><b>返回值</b>：未找到时返回 -1，调用方组合成 [-1, -1]</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260413 {
    public int[] searchRange(int[] nums, int target) {
        // 🔍 两次二分查找：分别找左边界和右边界
        return new int[]{
                binarySearch(nums, target, true),   // ⬅️ 查找左边界
                binarySearch(nums, target, false)   // ➡️ 查找右边界
        };
    }

    /**
     * 🔍 二分查找：查找 target 的左边界或右边界
     *
     * @param nums   有序数组
     * @param target 目标值
     * @param isLeft true=查找左边界, false=查找右边界
     * @return target 的边界索引，未找到返回 -1
     */
    private int binarySearch(int[] nums, int target, boolean isLeft) {
        int left = 0, right = nums.length - 1, index = -1;

        // 🔄 二分查找主循环
        while (left <= right) {
            int mid = left + (right - left) / 2;  // 📍 计算中点，防止溢出

            if (nums[mid] < target) {
                // ⬆️ 目标在右半部分
                left = mid + 1;
            } else if (nums[mid] > target) {
                // ⬇️ 目标在左半部分
                right = mid - 1;
            } else {
                // ✅ 找到 target，记录位置
                index = mid;

                // ⚠️ 注意：这个提前退出可能导致边界查找不准确
                if (left == right) {
                    break;
                }

                if (isLeft) {
                    // ⬅️ 查找左边界：继续向左搜索
                    right = mid - 1;
                } else {
                    // ➡️ 查找右边界：继续向右搜索
                    left = mid + 1;
                }
            }
        }
        return index;
    }
}

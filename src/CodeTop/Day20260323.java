package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/binary-search/description/">704. 二分查找</a>
 * <p>
 * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果 target 存在返回下标，否则返回 -1。
 * <p>
 * 你必须编写一个具有 O(log n) 时间复杂度的算法。
 *
 * <h2>解题思路</h2>
 * <h3>1. 核心思想</h3>
 * <ul>
 *     <li>利用数组<strong>有序</strong>的特性，每次将搜索范围缩小一半</li>
 *     <li>通过比较中间元素与目标值，确定目标值在左半部分还是右半部分</li>
 *     <li>不断迭代，直到找到目标值或搜索区间为空</li>
 * </ul>
 * <p>
 * <h3>2. 实现要点</h3>
 * <ul>
 *     <li><strong>边界检查</strong>：先判断 target 是否在数组范围内，避免无效搜索</li>
 *     <li><strong>双指针</strong>：使用 l 和 r 分别指向搜索区间的左右端点</li>
 *     <li><strong>中点计算</strong>：mid = (r - l) / 2 + l，避免 (l + r) 可能溢出的问题</li>
 *     <li><strong>循环条件</strong>：l <= r，确保搜索区间不为空</li>
 *     <li><strong>区间更新</strong>：根据 nums[mid] 与 target 的比较结果，更新搜索区间</li>
 * </ul>
 * <p>
 * <h3>3. 算法示意图</h3>
 * <pre>
 * 初始状态：[l=0,.....................,r=n-1]
 *           ↓ 计算 mid = (l+r)/2
 * 比较：    nums[mid] vs target
 *           ├─ nums[mid] == target → 找到，返回 mid
 *           ├─ nums[mid] > target  → target 在左侧，r = mid - 1
 *           └─ nums[mid] < target  → target 在右侧，l = mid + 1
 * </pre>
 * <p>
 * <h3>4. 复杂度分析</h3>
 * <ul>
 *     <li><strong>时间复杂度</strong>：O(log n)，每次迭代将搜索范围缩小一半</li>
 *     <li><strong>空间复杂度</strong>：O(1)，只使用了常数级别的额外空间</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260323 {
    /**
     * <h3>方法说明</h3>
     * 二分查找搜索目标值
     *
     * @param nums   升序排列的整型数组
     * @param target 要搜索的目标值
     * @return 目标值在数组中的下标，如果不存在则返回 -1
     */
    public int search(int[] nums, int target) {
        // <strong>边界检查</strong>：如果 target 小于最小值或大于最大值，直接返回 -1
        if (target < nums[0] || target > nums[nums.length - 1]) {
            return -1;
        }

        // <strong>初始化指针</strong>：定义搜索区间 [l, r]
        int l = 0, r = nums.length - 1;

        // <strong>循环搜索</strong>：当搜索区间不为空时继续
        while (l <= r) {
            // <strong>计算中点</strong>：使用 (r - l) / 2 + l 避免整数溢出
            int mid = (r - l) / 2 + l;

            // <strong>比较并更新区间</strong>
            if (nums[mid] == target) {
                // ✅ 找到目标值，返回下标
                return mid;
            } else if (nums[mid] > target) {
                // ⬅️ 中间值偏大，target 在左半部分，更新右边界
                r = mid - 1;
            } else {
                // ➡️ 中间值偏小，target 在右半部分，更新左边界
                l = mid + 1;
            }
        }

        // ❌ 未找到目标值，返回 -1
        return -1;
    }
}

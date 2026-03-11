package CodeTop;

/**
 * @author lxd
 * <p>
 * <a href="https://leetcode.cn/problems/median-of-two-sorted-arrays/description/">4. 寻找两个有序数组的中位数</a>
 * <p><b>解题思路：</b>
 * <ol>
 *   <li><b>核心思想：</b>使用二分查找找到两个数组的"分割线",使得分割线左侧的所有元素不大于右侧的所有元素
 *   <li><b>关键观察：</b>
 *       <ul>
 *         <li>中位数将数组分为长度相等的两部分 (奇数长度时左侧多一个元素)
 *         <li>对于两个数组，需要找到分割位置 i 和 j，使得左侧元素总数 = 右侧元素总数
 *         <li>分割位置满足：j = (m + n + 1) / 2 - i
 *       </ul>
 *   <li><b>二分查找策略：</b>
 *       <ul>
 *         <li>在较短的数组上进行二分查找 (优化时间复杂度)
 *         <li>查找分割位置 i，使得 nums1[i-1] <= nums2[j] 且 nums2[j-1] <= nums1[i]
 *         <li>如果 nums1[i-1] > nums2[j]，说明 i 太大，需要向左移动
 *         <li>如果 nums2[j-1] > nums1[i]，说明 i 太小，需要向右移动
 *       </ul>
 *   <li><b>边界处理：</b>
 *       <ul>
 *         <li>i = 0 时，nums1 左侧无元素，用 Integer.MIN_VALUE
 *         <li>i = m 时，nums1 右侧无元素，用 Integer.MAX_VALUE
 *         <li>j = 0 或 j = n 时同理
 *       </ul>
 *   <li><b>计算中位数：</b>
 *       <ul>
 *         <li>总长度为奇数：中位数 = 左侧最大值
 *         <li>总长度为偶数：中位数 = (左侧最大值 + 右侧最小值) / 2
 *       </ul>
 * </ol>
 *
 * <p><b>时间复杂度：</b>O(log(min(m, n))) - 在较短数组上进行二分查找<br>
 * <b>空间复杂度：</b>O(1) - 只使用常数额外空间
 **/
public class Day20260311 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 保证 nums1 是较短的数组，优化时间复杂度
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;

        // 在 nums1 上进行二分查找，查找范围 [0, m]
        int left = 0, right = m;

        while (left <= right) {
            // 计算 nums1 的分割位置 i
            int i = (left + right) / 2;

            // 根据总长度的一半计算 nums2 的分割位置 j
            // +1 是为了保证奇数长度时左侧多一个元素
            int j = (m + n + 1) / 2 - i;

            // 处理四种边界情况
            // nums1 左侧最大值 (i=0 时无左侧元素)
            int left1 = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];

            // nums1 右侧最小值 (i=m 时无右侧元素)
            int right1 = (i == m) ? Integer.MAX_VALUE : nums1[i];

            // nums2 左侧最大值 (j=0 时无左侧元素)
            int left2 = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];

            // nums2 右侧最小值 (j=n 时无右侧元素)
            int right2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            // 检查是否找到正确的分割位置
            // 条件：左侧所有元素 <= 右侧所有元素
            if (left1 <= right2 && left2 <= right1) {
                // 找到正确分割位置，计算中位数
                if ((m + n) % 2 == 1) {
                    // 总长度为奇数，中位数为左侧最大值
                    return Math.max(left1, left2);
                } else {
                    // 总长度为偶数，中位数为左侧最大值和右侧最小值的平均值
                    return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
                }
            } else if (left1 > right2) {
                // nums1 左侧最大值大于 nums2 右侧最小值，说明 i 太大，需要向左移动
                right = i - 1;
            } else {
                // nums2 左侧最大值大于 nums1 右侧最小值，说明 i 太小，需要向右移动
                left = i + 1;
            }
        }
        // 理论上不会执行到这里，因为题目保证有解
        return 0;
    }
}

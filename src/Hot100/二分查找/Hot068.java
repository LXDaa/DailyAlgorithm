package Hot100.二分查找;

/**
 * @author lxd
 **/
public class Hot068 {
    /**
     * <a href="https://leetcode.cn/problems/median-of-two-sorted-arrays/description/">4. 寻找两个正序数组的中位数</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。</p>
     * <p>请你找出并返回这两个正序数组的中位数。</p>
     * <p>算法的时间复杂度应该为 O(log (m+n))。</p>
     *
     * <h3>💡 核心思路：二分查找 + 分割位置</h3>
     * <ul>
     *   <li><b>基本思想</b>：在较短数组上二分查找分割位置，使得两个数组的分割满足中位数条件
     *     <ul>
     *       <li>在 nums1 中查找分割位置 i，nums2 的分割位置 j = (m+n+1)/2 - i</li>
     *       <li>分割条件：左半部分的最大值 <= 右半部分的最小值</li>
     *       <li>根据左半部分长度计算中位数（奇偶情况不同）</li>
     *       <li>通过二分查找调整分割位置</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么在较短数组上二分</b>：
     *     <ul>
     *       <li>较短数组的长度更小，二分查找次数更少</li>
     *       <li>j = (m+n+1)/2 - i 确保 j >= 0</li>
     *       <li>时间复杂度优化到 O(log(min(m,n)))</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(log(min(m,n)))，在较短数组上二分查找</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常数级别的额外空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums1 = [1,3], nums2 = [2]
     *
     * 分割示意：
     *   nums1: [1 | 3]      i = 1
     *   nums2: [| 2]        j = 0
     *
     * 左半部分：[1]
     * 右半部分：[3, 2]
     *
     * 检查分割条件：
     *   left1 = 1, right1 = 3
     *   left2 = MIN, right2 = 2
     *   left1 <= right2 (1 <= 2) ✅
     *   left2 <= right1 (MIN <= 3) ✅
     *
     * 总长度 = 3（奇数）：
     *   中位数 = max(left1, left2) = max(1, MIN) = 1 ❌
     *
     * 调整分割位置：
     *
     * 第二次循环：
     *   i = 0, j = 2
     *   nums1: [| 1, 3]     i = 0
     *   nums2: [2 |]        j = 1
     *
     *   left1 = MIN, right1 = 1
     *   left2 = 2, right2 = MAX
     *   left1 <= right2 (MIN <= MAX) ✅
     *   left2 <= right1 (2 <= 1) ❌
     *
     *   left2 > right1 → left = i + 1 = 1
     *
     * 第三次循环：
     *   i = 1, j = 1
     *   nums1: [1 | 3]      i = 1
     *   nums2: [2 |]        j = 1
     *
     *   left1 = 1, right1 = 3
     *   left2 = 2, right2 = MAX
     *   left1 <= right2 (1 <= MAX) ✅
     *   left2 <= right1 (2 <= 3) ✅
     *
     * 总长度 = 3（奇数）：
     *   中位数 = max(left1, left2) = max(1, 2) = 2 ✅
     *
     * 示例2：nums1 = [1,2], nums2 = [3,4]
     *
     * 总长度 = 4（偶数）：
     *   i = 1, j = 1
     *   nums1: [1 | 2]      i = 1
     *   nums2: [3 | 4]      j = 1
     *
     *   left1 = 1, right1 = 2
     *   left2 = 3, right2 = 4
     *   left1 <= right2 (1 <= 4) ✅
     *   left2 <= right1 (3 <= 2) ❌
     *
     *   left2 > right1 → left = 2
     *
     *   i = 2, j = 0
     *   nums1: [1,2 |]      i = 2
     *   nums2: [| 3, 4]     j = 0
     *
     *   left1 = 2, right1 = MAX
     *   left2 = MIN, right2 = 3
     *   left1 <= right2 (2 <= 3) ✅
     *   left2 <= right1 (MIN <= MAX) ✅
     *
     * 总长度 = 4（偶数）：
     *   中位数 = (max(left1, left2) + min(right1, right2)) / 2
     *         = (max(2, MIN) + min(MAX, 3)) / 2
     *         = (2 + 3) / 2 = 2.5 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用 (m+n+1)/2 计算分割位置？
     *    - +1 确保奇数长度时左半部分多一个元素
     *    - 例如：总长度 5，左半部分 3 个，右半部分 2 个
     *    - 中位数取左半部分的最大值
     *
     * 2️⃣ 分割条件是什么？
     *    - left1 <= right2：nums1 左半最大 <= nums2 右半最小
     *    - left2 <= right1：nums2 左半最大 <= nums1 右半最小
     *    - 满足这两个条件，分割位置正确
     *
     * 3️⃣ 边界处理：MIN_VALUE 和 MAX_VALUE？
     *    - i = 0：nums1 左半部分为空，left1 = MIN_VALUE
     *    - i = m：nums1 右半部分为空，right1 = MAX_VALUE
     *    - j = 0 或 j = n 同理
     *    - 确保比较时不会出错
     *
     * 4️⃣ 如何调整分割位置？
     *    - left1 > right2：nums1 左半太大，i 需要减小，right = i - 1
     *    - left2 > right1：nums1 右半太小，i 需要增大，left = i + 1
     *    - 这是二分查找的核心逻辑
     *
     * 5️⃣ 如何计算中位数？
     *    - 奇数长度：max(left1, left2)
     *    - 偶数长度：(max(left1, left2) + min(right1, right2)) / 2.0
     *    - 左半部分的最大值和右半部分的最小值
     *
     * 6️⃣ 时间复杂度分析：
     *    - 在较短数组上二分查找
     *    - 时间复杂度 O(log(min(m,n)))
     *    - 比 O(log(m+n)) 更优
     *
     * 7️⃣ 边界情况测试：
     *    - 一个数组为空：nums1 = [], nums2 = [1,2,3] → 中位数 2
     *    - 单元素数组：nums1 = [1], nums2 = [2] → 中位数 1.5
     *    - 两数组长度相等：nums1 = [1,2], nums2 = [3,4] → 中位数 2.5
     *    - 两数组长度不等：nums1 = [1,2,3], nums2 = [4,5,6,7] → 中位数 4
     *
     * 8️⃣ 完整流程总结：
     *
     *    if nums1.length > nums2.length → 交换，确保 nums1 较短
     *
     *    初始化：left = 0, right = nums1.length
     *
     *    while left <= right：
     *      i = left + (right - left) / 2
     *      j = (m + n + 1) / 2 - i
     *      left1 = (i==0) ? MIN : nums1[i-1]
     *      right1 = (i==m) ? MAX : nums1[i]
     *      left2 = (j==0) ? MIN : nums2[j-1]
     *      right2 = (j==n) ? MAX : nums2[j]
     *
     *      if left1 <= right2 && left2 <= right1：
     *        // 找到正确分割位置
     *        if (m+n) % 2 == 1：return max(left1, left2)
     *        else：return (max(left1, left2) + min(right1, right2)) / 2.0
     *      else if left1 > right2：right = i - 1
     *      else：left = i + 1
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>数组顺序</b>：必须确保 nums1.length <= nums2.length</li>
     *   <li><b>分割计算</b>：j = (m + n + 1) / 2 - i，+1 处理奇数情况</li>
     *   <li><b>边界处理</b>：使用 MIN_VALUE 和 MAX_VALUE 处理空半部分</li>
     *   <li><b>中位数计算</b>：奇数取左半最大，偶数取左右半的平均</li>
     *   <li><b>时间复杂度</b>：O(log(min(m,n)))，优化二分查找范围</li>
     * </ul>
     *
     * @param nums1 第一个正序数组
     * @param nums2 第二个正序数组
     * @return 两个数组的中位数
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 🔃 确保 nums1 是较短数组，优化二分查找范围
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        // 📏 获取数组长度
        int m = nums1.length, n = nums2.length;

        // 📋 初始化二分查找范围
        int left = 0, right = m;

        // 🔄 二分查找循环
        while (left <= right) {
            // 📐 计算 nums1 的分割位置 i
            int i = left + (right - left) / 2;

            // 📐 计算 nums2 的分割位置 j（确保左半部分长度 = (m+n+1)/2）
            int j = (m + n + 1) / 2 - i;

            // 📋 获取四个边界值（处理空半部分）
            int left1 = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int right1 = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int left2 = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int right2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            // 🔍 检查分割条件
            if (left1 <= right2 && left2 <= right1) {
                // ✅ 找到正确分割位置
                if ((m + n) % 2 == 1) {
                    // 🎯 奇数长度：中位数 = 左半部分最大值
                    return Math.max(left1, left2);
                } else {
                    // 🎯 偶数长度：中位数 = 左半最大值和右半最小值的平均
                    return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
                }
            } else if (left1 > right2) {
                // 🔍 nums1 左半部分太大，i 需要减小
                right = i - 1;
            } else {
                // 🔍 nums1 右半部分太小，i 需要增大
                left = i + 1;
            }
        }

        // ❌ 理论上不会到达这里
        return 0;
    }
}

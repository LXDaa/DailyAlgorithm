package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/merge-sorted-array/description/">88. 合并两个有序数组</a>
 * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * <p>
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * <p>
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 *
 * @author lxd
 **/
public class Day20260202 {
    /**
     * 合并两个有序数组
     * <p>
     * 解题思路：
     * 使用双指针从后往前遍历的方法，避免覆盖nums1中的有效数据。
     * 1. 设置三个指针：tail指向合并后数组的末尾位置，idx1指向nums1有效元素的末尾，idx2指向nums2的末尾
     * 2. 从后向前比较两个数组的元素，将较大者放入nums1的tail位置
     * 3. 当其中一个数组遍历完后，将另一个数组的剩余元素复制到nums1
     * <p>
     * 算法优势：
     * - 时间复杂度：O(m+n)，只需一次遍历
     * - 空间复杂度：O(1)，只使用常数额外空间
     * - 从后向前遍历，避免了覆盖nums1中的有效数据
     * <p>
     * 执行过程示例：
     * 假设 nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * 初始: tail=5, idx1=2, idx2=2
     * 第1轮: nums1[2]=3 < nums2[2]=6, nums1[5]=6, idx2=1, tail=4
     * 第2轮: nums1[2]=3 > nums2[1]=5, nums1[4]=5, idx1=1, tail=3
     * 第3轮: nums1[1]=2 < nums2[1]=5, nums1[3]=5, idx2=0, tail=2
     * 第4轮: nums1[1]=2 > nums2[0]=2, nums1[2]=2, idx1=0, tail=1
     * 第5轮: nums1[0]=1 < nums2[0]=2, nums1[1]=2, idx2=-1, tail=0
     * 第6轮: idx2<0退出第一循环，合并完成
     * 结果: nums1 = [1,2,2,3,5,6]
     *
     * @param nums1 待合并的数组，长度为m+n，前m个元素有效
     * @param m     nums1中有效元素的个数
     * @param nums2 待合并的数组，长度为n
     * @param n     nums2中元素的个数
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 定义合并后数组的尾部指针，从后向前填充元素
        int tail = m + n - 1;
        // 定义nums1数组的有效元素尾部指针
        int idx1 = m - 1;
        // 定义nums2数组的尾部指针
        int idx2 = n - 1;

        // 当两个数组都还有元素未处理时，比较并放置较大的元素到尾部
        while (idx1 >= 0 && idx2 >= 0) {
            // 比较两个数组当前指针处的元素，将较大者放到合并位置
            if (nums1[idx1] >= nums2[idx2]) {
                // nums1的当前元素更大，将其放置到合并位置
                nums1[tail] = nums1[idx1--];
            } else {
                // nums2的当前元素更大，将其放置到合并位置
                nums1[tail] = nums2[idx2--];
            }
            // 更新合并位置指针
            tail--;
        }

        // 如果nums2还有剩余元素未处理，将其全部复制到nums1
        // (如果nums1有剩余元素，无需移动，因为它们已经在正确的位置)
        while (idx2 >= 0) {
            nums1[tail] = nums2[idx2--];
            tail--;
        }
    }
}

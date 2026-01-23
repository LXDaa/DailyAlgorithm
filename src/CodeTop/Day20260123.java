package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/search-in-rotated-sorted-array/description/">33. 搜索旋转排序数组</a>
 * <p>
 * 解题思路：
 * 这是一个在旋转排序数组中搜索目标值的问题。旋转排序数组是指将一个排序数组在某个点上切断，
 * 并将前后两部分调换位置。例如 [0,1,2,4,5,6,7] 在位置 4 切断后变为 [4,5,6,7,0,1,2]。
 * <p>
 * 核心思想是使用二分查找的变形：
 * 1. 旋转后的数组虽然整体不再有序，但可以分为两段有序的部分
 * 2. 每次二分时，mid 位置将数组分成两部分，其中至少有一部分是完全有序的
 * 3. 判断哪一部分是有序的，然后判断 target 是否在这一部分中
 * 4. 如果 target 在有序部分，则在该部分继续查找；否则在另一部分查找
 * <p>
 * 算法步骤：
 * 1. 设置左右边界 l=0, r=n-1
 * 2. 当 l <= r 时循环：
 * a. 计算中点 mid = (l+r)/2
 * b. 如果 nums[mid] == target，直接返回 mid
 * c. 判断左半部分 [0,mid] 是否有序：
 * - 如果 nums[0] <= nums[mid]，说明左半部分 [0,mid] 有序
 * - 如果 target 在 [nums[0], nums[mid]) 范围内，说明 target 在左半部分，令 r = mid-1
 * - 否则 target 在右半部分，令 l = mid+1
 * - 如果 nums[0] > nums[mid]，说明右半部分 [mid,n-1] 有序
 * - 如果 target 在 (nums[mid], nums[n-1]] 范围内，说明 target 在右半部分，令 l = mid+1
 * - 否则 target 在左半部分，令 r = mid-1
 * 3. 如果循环结束仍未找到，返回 -1
 * <p>
 * 时间复杂度：O(log n)，空间复杂度：O(1)
 * <p>
 * 示例分析：
 * 假设 nums = [4,5,6,7,0,1,2], target = 0
 * - 初始：l=0, r=6, mid=3, nums[mid]=7
 * - nums[0]=4 <= nums[3]=7，左半部分 [0,3] 有序
 * - target=0 不在 [4,7) 范围内，所以去右半部分 [4,6] 查找
 * - l=4, r=6, mid=5, nums[mid]=1
 * - nums[0]=4 > nums[5]=1，右半部分 [5,6] 有序
 * - target=0 不在 (1,2] 范围内，所以去左半部分 [4,4] 查找
 * - l=4, r=4, mid=4, nums[mid]=0=target，返回 4
 *
 * @author lxd
 **/
public class Day20260123 {

    /**
     * 在旋转排序数组中搜索目标值
     *
     * @param nums   旋转排序数组
     * @param target 目标值
     * @return 目标值在数组中的索引，如果不存在则返回-1
     */
    public int search(int[] nums, int target) {
        int n = nums.length;
        // 边界条件：空数组直接返回-1
        if (n == 0) {
            return -1;
        }
        // 边界条件：单元素数组直接比较
        if (n == 1) {
            return nums[0] == target ? 0 : -1;
        }

        // 初始化左右边界
        int l = 0, r = n - 1;

        // 二分查找循环
        while (l <= r) {
            // 计算中点位置
            int mid = (l + r) / 2;

            // 找到目标值，直接返回索引
            if (nums[mid] == target) {
                return mid;
            }

            // 判断左半部分 [0,mid] 是否有序
            if (nums[0] <= nums[mid]) {
                // 左半部分有序，判断target是否在此范围内
                if (nums[0] <= target && target < nums[mid]) {
                    // target在左半部分的有序区间内，缩小右边界
                    r = mid - 1;
                } else {
                    // target不在左半部分，去右半部分查找
                    l = mid + 1;
                }
            } else {
                // 右半部分 [mid,n-1] 有序，判断target是否在此范围内
                if (nums[mid] < target && target <= nums[n - 1]) {
                    // target在右半部分的有序区间内，增大左边界
                    l = mid + 1;
                } else {
                    // target不在右半部分，去左半部分查找
                    r = mid - 1;
                }
            }
        }

        // 未找到目标值，返回-1
        return -1;
    }
}



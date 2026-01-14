package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/kth-largest-element-in-an-array/description/">215. 数组中的第K个最大元素</a>
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
 * <p>
 * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * <p>
 * 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
 *
 * @author lxd
 **/
public class Day20260114 {
    /**
     * 查找数组中第k个最大的元素
     * 使用快速选择算法（Quick Select），基于快速排序的分区思想
     * 时间复杂度：平均O(n)，最坏O(n^2)，但通过随机选择基准值可以避免最坏情况
     * 空间复杂度：O(log n)，递归栈的深度
     * <p>
     * 示例：数组[3,2,1,5,6,4]，k=2
     * - 排序后：[1,2,3,4,5,6]，第2大元素是5
     * - 在原数组中，5的索引位置是4（nums.length-k=6-2=4）
     * <p>
     * 解题思路：
     * 1. 第k个最大元素相当于排序后倒数第k个元素，即索引为(nums.length - k)的元素
     * 2. 使用快速选择算法，在每次分区后判断目标元素在哪个区间，只对包含目标的区间继续查找
     * 3. 通过递归方式逐步缩小搜索范围，直到找到目标位置
     *
     * @param nums 整数数组
     * @param k    第k个最大元素（从1开始计数）
     * @return 第k个最大的元素值
     */
    public int findKthLargest(int[] nums, int k) {
        // 第k个最大元素在排序后数组中的索引为 nums.length - k
        // 例如：数组长度为6，k=2，第2大元素应该在排序后索引为4的位置
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    /**
     * 快速选择算法的核心实现
     * 在指定范围内查找目标索引位置的元素
     * <p>
     * 示例：数组 [3,2,1,5,6,4]，找第2大元素
     * - 第2大元素在排序后数组 [6,5,4,3,2,1] 中是5
     * - 在原数组升序排列的索引中是4（nums.length - k = 6 - 2 = 4）
     * - 我们需要找到排序后索引为4的元素（值为5）
     *
     * @param nums  数组
     * @param left  左边界（包含）
     * @param right 右边界（包含）
     * @param k     目标索引位置
     * @return 位于目标索引位置的元素值
     */
    private int quickSelect(int[] nums, int left, int right, int k) {
        // 如果左右边界相等，说明已经找到目标位置
        if (left == right) {
            return nums[k];
        }

        // 使用Hoare分区方案
        // 选择左边界元素作为基准值（pivot）
        int x = nums[left];

        // 初始化两个指针
        // i从left-1开始向右移动
        // j从right+1开始向左移动
        int i = left - 1, j = right + 1;

        // 分区过程：将小于基准值的元素放在左侧，大于基准值的元素放在右侧
        while (i < j) {
            // i向右移动，直到找到大于等于基准值的元素
            do i++; while (nums[i] < x);

            // j向左移动，直到找到小于等于基准值的元素
            do j--; while (nums[j] > x);

            // 如果i和j还没有相遇，则交换这两个元素
            if (i < j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }

        // 循环结束后，j是右半部分（大于等于基准值）的左边界
        // 此时数组被分为两部分：[left, j] 都是 <= x 的元素，[j+1, right] 都是 >= x 的元素
        // 例如：原始数组 [3,2,1,5,6,4]，选择基准值x=3（nums[left]），分区后可能变成 [2,1,3,5,6,4]
        // 此时j指向索引2（值为3的位置），[0,2]范围是 <= 3 的元素，[3,5]范围是 >= 3 的元素

        // 根据目标索引k与分割点j的关系，决定在哪个子区间继续查找
        if (k <= j) {
            // 如果目标索引k小于等于j，说明目标元素（排序后第k位的元素）在左半部分
            // 因为左半部分 [left, j] 的所有元素都 <= x（基准值）
            // 举例：数组[3,2,1,5,6,4]找第2大元素（在升序排列中索引为4）
            // 假设分区后，[left, j] 区间存储的都是较小的值，[j+1, right] 存储较大的值
            // 如果k=4 <= j=5，说明目标元素在较小值区域中，继续在[left, j]查找
            return quickSelect(nums, left, j, k);
        } else {
            // 如果目标索引k大于j，说明目标元素在右半部分
            // 因为右半部分 [j+1, right] 的所有元素都 >= x（基准值）
            // 举例：如果k=4 > j=3，说明目标元素（排序后第4位）在较大值区域
            // 所以去[j+1, right]区间继续查找
            return quickSelect(nums, j + 1, right, k);
        }
    }
}
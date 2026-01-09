package CodeMusings.Greedy;

import java.util.Arrays;

/**
 *
 * @see <a href="https://leetcode.cn/problems/non-overlapping-intervals/description/">435. 无重叠区间</a>
 * <p>
 * 给定一个区间的集合 intervals ，其中 intervals[i] = [starti, endi] 。
 * 返回需要移除区间的最小数量，使剩余区间互不重叠。
 * <p>
 * 解题思路:
 * 这是一个经典的贪心算法问题。要使得移除的区间数最少，等价于保留最多的不重叠区间。
 * 策略是按区间起始位置排序后，尽可能多地选择不重叠的区间。
 * 当遇到重叠区间时，我们保留结束位置较小的那个区间，这样能为后续区间留出更多空间。
 * <p>
 * 算法步骤:
 * 1. 按区间起始位置升序排序
 * 2. 贪心选择：遍历区间，如果当前区间与前一个选择的区间不重叠，则选择它
 * 3. 如果重叠，则更新当前区间的结束位置为两者中的较小值（相当于"移除"结束位置较大的区间）
 * 4. 最终结果 = 总区间数 - 保留的区间数
 * <p>
 * 时间复杂度: O(n log n) - 主要是排序的时间复杂度
 * 空间复杂度: O(1) - 只使用了常数额外空间
 * <p>
 * 注意: 只在一点上接触的区间是不重叠的。例如 [1, 2] 和 [2, 3] 是不重叠的。
 *
 *
 */
public class Day20251029 {
    /**
     * 计算需要移除的最小区间数量
     *
     * @param intervals 区间数组，intervals[i] = [start_i, end_i]
     * @return 需要移除的区间最小数量
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        // 按区间起始位置升序排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        // 记录保留的不重叠区间数量，初始为1（第一个区间默认保留）
        int count = 1;
        
        // 从第二个区间开始遍历
        for (int i = 1; i < intervals.length; i++) {
            // 如果当前区间起始位置 >= 前一个区间结束位置，说明不重叠
            if (intervals[i][0] >= intervals[i - 1][1]) {
                // 保留当前区间
                count++;
            } else {
                // 如果重叠，更新当前区间结束位置为两者中较小值
                // 这相当于移除结束位置较大的区间，为后续留出更多空间
                intervals[i][1] = Math.min(intervals[i][1], intervals[i - 1][1]);
            }
        }
        
        // 需要移除的区间数 = 总区间数 - 保留的区间数
        return intervals.length - count;
    }
}
package CodeMusings.Greedy;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * <a href="https://leetcode.cn/problems/merge-intervals/description/">56. 合并区间</a>
 * <p>
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
 * <p>
 * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
 * <p>
 * 解题思路：
 * <p>
 * 1. 首先按照区间的起始位置对所有区间进行排序
 * <p>
 * 2. 遍历排序后的区间，判断当前区间与结果集中最后一个区间的关系：
 * <p>
 * - 如果当前区间的起始位置大于结果集中最后一个区间的结束位置，说明两个区间不重叠，直接将当前区间加入结果集
 * <p>
 * - 否则说明两个区间重叠，需要合并，更新结果集中最后一个区间的结束位置为两个区间结束位置的最大值
 * <p>
 * 3. 最后将结果集转换为数组返回
 */
public class Day20251102 {
    public int[][] merge(int[][] intervals) {
        // 使用链表存储结果，便于获取最后一个元素
        LinkedList<int[]> res = new LinkedList<>();

        // 按照区间起始位置升序排序
        Arrays.sort(intervals, (x, y) -> Integer.compare(x[0], y[0]));

        // 将第一个区间加入结果集
        res.add(intervals[0]);

        // 从第二个区间开始遍历
        for (int i = 1; i < intervals.length; i++) {
            // 当前遍历到的区间
            int[] cur = intervals[i];
            // 结果集中的最后一个区间
            int[] last = res.getLast();

            // 如果当前区间与结果集最后一个区间不重叠
            if (cur[0] > last[1]) {
                res.add(cur);  // 直接将当前区间加入结果集
            } else {
                // 如果重叠，则合并两个区间，更新最后区间的结束位置
                last[1] = Math.max(cur[1], last[1]);
            }
        }

        // 将链表转换为数组返回
        return res.toArray(new int[0][0]);
    }
}
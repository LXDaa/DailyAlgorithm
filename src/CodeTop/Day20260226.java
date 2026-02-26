package CodeTop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * <a href="https://leetcode.cn/problems/merge-intervals/description/">56. 合并区间</a>
 *
 * <p>题目描述：
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
 * 请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
 *
 * <p>解题思路：
 * <ol>
 *   <li>首先按照区间的起始位置对所有区间进行排序</li>
 *   <li>遍历排序后的区间，维护一个结果列表</li>
 *   <li>对于每个区间，判断是否与结果列表中最后一个区间重叠</li>
 *   <li>如果重叠则合并，否则直接添加到结果列表</li>
 * </ol>
 *
 * <p>关键判断逻辑：
 * <ul>
 *   <li>重叠条件：当前区间起始位置 ≤ 结果列表最后一个区间的结束位置</li>
 *   <li>合并规则：新区间的结束位置 = max(当前区间结束位置, 已有区间结束位置)</li>
 * </ul>
 *
 * <p>时间复杂度：O(n log n)，主要消耗在排序上
 * <p>空间复杂度：O(n)，用于存储结果列表
 *
 * <p>示例：
 * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
 * 输出：[[1,6],[8,10],[15,18]]
 *
 * @author lxd
 **/
public class Day20260226 {
    /**
     * 合并重叠区间主函数
     *
     * @param intervals 输入的区间数组，每个区间为[start, end]格式
     * @return 合并后的不重叠区间数组
     */
    public int[][] merge(int[][] intervals) {
        // 边界条件检查：空数组直接返回
        if (intervals == null || intervals.length == 0) {
            return new int[0][2];
        }

        // 按照区间起始位置进行排序
        // 使用Lambda表达式定义比较器，按第一个元素（起始位置）升序排列
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // 创建结果列表存储合并后的区间
        List<int[]> ans = new ArrayList<>();

        // 遍历所有区间
        for (int[] currentInterval : intervals) {
            // 判断条件：结果列表不为空 且 当前区间与结果列表最后一个区间重叠
            // 重叠条件：当前区间起始位置 <= 结果列表最后一个区间的结束位置
            if (!ans.isEmpty() && currentInterval[0] <= ans.get(ans.size() - 1)[1]) {
                // 存在重叠，需要合并区间
                // 合并规则：更新结果列表最后一个区间的结束位置为两者较大值
                int lastIndex = ans.size() - 1;
                int[] lastInterval = ans.get(lastIndex);
                lastInterval[1] = Math.max(currentInterval[1], lastInterval[1]);
            } else {
                // 不存在重叠，直接将当前区间添加到结果列表
                ans.add(currentInterval);
            }
        }

        // 将List转换为二维数组并返回
        return ans.toArray(new int[ans.size()][]);
    }
}

package Greedy;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/minimum-number-of-arrows-to-burst-balloons/description/">452. 用最少数量的箭引爆气球</a>
 * 有一些球形气球贴在一堵用 XY 平面表示的墙面上。墙面上的气球记录在整数数组 points ，其中points[i] = [xstart, xend] 表示水平直径在 xstart 和 xend之间的气球。你不知道气球的确切 y 坐标。
 * <p>
 * 一支弓箭可以沿着 x 轴从不同点 完全垂直 地射出。在坐标 x 处射出一支箭，若有一个气球的直径的开始和结束坐标为 xstart，xend， 且满足  xstart ≤ x ≤ xend，则该气球会被 引爆 。可以射出的弓箭的数量 没有限制 。 弓箭一旦被射出之后，可以无限地前进。
 * <p>
 * 给你一个数组 points ，返回引爆所有气球所必须射出的 最小 弓箭数 。
 * <p>
 * 解题思路：
 * 这是一个典型的贪心算法问题。我们需要找到最小的箭数来引爆所有气球。
 * 核心思想是：尽可能让每支箭引爆更多的气球。
 * 具体做法：
 * 1. 将气球按起始位置排序
 * 2. 遍历气球，如果当前气球的起始位置大于前一个气球的结束位置，
 * 说明两个气球不重叠，需要额外的一支箭
 * 3. 如果有重叠，更新当前重叠区间的右边界为两者的较小值，
 * 这样可以确保能同时引爆重叠的气球
 */
public class Day20251028 {
    public int findMinArrowShots(int[][] points) {
        // 按照气球的起始位置进行排序
        Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));

        // 至少需要一支箭
        int ans = 1;

        // 从第二个气球开始遍历
        for (int i = 1; i < points.length; i++) {
            // 如果当前气球的起始位置大于前一个气球的结束位置
            // 说明两个气球不重叠，需要额外的一支箭
            if (points[i][0] > points[i - 1][1]) {
                ans++;
            } else {
                // 如果有重叠，更新当前重叠区间的右边界为两者的较小值
                // 这确保了箭可以同时引爆重叠的气球
                points[i][1] = Math.min(points[i][1], points[i - 1][1]);
            }
        }
        return ans;
    }
}
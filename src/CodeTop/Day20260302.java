package CodeTop;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <a href="https://leetcode.cn/problems/trapping-rain-water/description/">42. 接雨水</a>
 * <p>
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 *
 * <p><strong>解题思路：</strong></p>
 * <p>使用单调递减栈来解决该问题。栈中存储柱子的下标，保持栈顶到栈底的高度单调递减。</p>
 * <p>当遇到一个比栈顶高的柱子时，说明可以形成凹槽并计算积水量：</p>
 * <ol>
 *   <li>弹出栈顶作为凹槽底部（mid）</li>
 *   <li>新的栈顶作为左边界（left）</li>
 *   <li>当前遍历的柱子作为右边界（i）</li>
 *   <li>积水高度 = min(左边界高度，右边界高度) - 凹槽底部高度</li>
 *   <li>积水宽度 = 右边界下标 - 左边界下标 - 1</li>
 * </ol>
 *
 * <p><strong>示例说明：</strong></p>
 * <p>输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]</p>
 * <p>执行过程：</p>
 * <ol>
 *   <li>i=0, height[0]=0, 栈：[0]</li>
 *   <li>i=1, height[1]=1 > height[0], 弹出 0, 栈空，push 1, 栈：[1]</li>
 *   <li>i=2, height[2]=0 < height[1], push 2, 栈：[1,2]</li>
 *   <li>i=3, height[3]=2 > height[2], 弹出 2(mid), left=1:</li>
 *       <ul>
 *         <li>h = min(2,1) - 0 = 1</li>
 *         <li>w = 3 - 1 - 1 = 1</li>
 *         <li>sum += 1*1 = 1</li>
 *       </ul>
 *       <p>继续 height[3]=2 > height[1], 弹出 1, 栈空，push 3, 栈：[3]</p>
 *   <li>以此类推，最终 sum = 6</li>
 * </ol>
 *
 * <p><strong>复杂度分析：</strong></p>
 * <ul>
 *   <li>时间复杂度：O(n)，每个元素最多入栈和出栈各一次</li>
 *   <li>空间复杂度：O(n)，单调栈的空间</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260302 {
    /**
     * <p>使用单调栈计算接雨水的总量</p>
     *
     * @param height 柱子高度数组
     * @return 能接住的雨水总量
     */
    public int trap(int[] height) {
        // 获取柱子数量
        int size = height.length;

        // 单调递减栈，存储柱子的下标
        Deque<Integer> stack = new LinkedList<>();
        // 记录雨水总量
        int sum = 0;

        // 遍历每一根柱子
        for (int i = 0; i < size; i++) {
            // 当前柱子高度大于栈顶柱子高度时，说明可以形成凹槽
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                // 弹出栈顶作为凹槽底部
                int mid = stack.pop();
                // 如果栈不为空，说明存在左边界
                if (!stack.isEmpty()) {
                    // 左边界下标
                    int left = stack.peek();
                    // 计算积水高度：左右边界较小值减去底部高度
                    int h = Math.min(height[i], height[left]) - height[mid];
                    // 计算积水宽度：右边界下标减左边界下标再减 1
                    int w = i - left - 1;
                    // 累加雨水量
                    sum += h * w;
                }
            }
            // 将当前柱子下标入栈
            stack.push(i);
        }
        // 返回雨水总量
        return sum;
    }
}

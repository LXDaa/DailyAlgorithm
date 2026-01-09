package CodeMusings.MonotonicStack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <a href="https://leetcode.cn/problems/trapping-rain-water/description/">42. 接雨水</a>
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * <p>
 * 解题思路：
 * 使用单调栈解决接雨水问题。
 * 1. 单调栈：维护一个单调递减的栈，存储柱子的索引
 * 2. 核心思想：当遇到更高的柱子时，与栈中较低的柱子形成凹槽，可以接雨水
 * 3. 凹槽结构：左边界（栈中第二个元素）、底部（栈顶元素）、右边界（当前元素）
 * 4. 计算雨水：
 * - 高度 = min(右边界高度, 左边界高度) - 底部高度
 * - 宽度 = 右边界索引 - 左边界索引 - 1
 * - 雨水 = 高度 * 宽度
 * <p>
 * 算法步骤：
 * 1. 遍历高度数组
 * 2. 当前高度大于栈顶对应高度时，说明形成了凹槽，可以接雨水
 * 3. 弹出栈顶元素作为凹槽底部
 * 4. 如果栈不为空，取出新的栈顶作为左边界
 * 5. 当前元素作为右边界，计算该凹槽的雨水量
 * 6. 将当前索引入栈
 * <p>
 * 时间复杂度: O(n)，每个元素最多入栈出栈一次
 * 空间复杂度: O(n)，栈的最大空间
 *
 * @author lxd
 **/
public class Day20260107 {
    /**
     * 计算能接的雨水总量
     *
     * @param height 表示柱子高度的数组
     * @return 能接的雨水总量
     */
    public int trap(int[] height) {
        // 数组长度
        int size = height.length;

        // 单调栈，存储柱子的索引，保持栈中索引对应的高度值单调递减
        Deque<Integer> stack = new LinkedList<>();

        // 雨水总量
        int sum = 0;

        // 遍历每个柱子
        for (int i = 0; i < size; i++) {
            // 当栈不为空且当前柱子高度大于栈顶索引对应的高度时
            // 说明找到了一个凹槽，可以接雨水
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                // 弹出栈顶元素，作为凹槽的底部
                int mid = stack.pop();

                // 如果栈为空，说明没有左边界，无法形成凹槽，跳出循环
                if (!stack.isEmpty()) {
                    // 当前栈顶元素为凹槽的左边界
                    int left = stack.peek();

                    // 计算凹槽能接雨水的高度
                    // 取左右边界中较矮的高度，减去凹槽底部的高度
                    int h = Math.min(height[i], height[left]) - height[mid];

                    // 计算凹槽的宽度
                    // 当前索引(右边界) - 左边界索引 - 1
                    int w = i - left - 1;

                    // 累加雨水量
                    sum += h * w;
                }
            }

            // 将当前索引入栈，继续寻找可能的凹槽
            stack.push(i);
        }

        // 返回总的雨水量
        return sum;
    }
}

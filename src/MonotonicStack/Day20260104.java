package MonotonicStack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <a href="https://leetcode.cn/problems/daily-temperatures/description/">739. 每日温度</a>
 * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，
 * 下一个更高温度出现在几天后。如果气温在这之后都不会升高，请在该位置用 0 来代替。
 * <p>
 * 解题思路:
 * 本题使用单调栈来解决。单调栈是一种特殊的数据结构，用于处理数组中下一个更大/更小元素的问题。
 * <p>
 * 核心思想:
 * 1. 维护一个单调递减的栈，栈中存储数组下标
 * 2. 当遇到一个比栈顶元素温度更高的天时，说明找到了栈顶天的下一个更高温度
 * 3. 计算两者之间的天数差并更新结果数组
 * <p>
 * 算法步骤:
 * 1. 遍历温度数组
 * 2. 对于每个位置i，检查栈中是否有温度比当前温度低的天
 * 3. 如果有，则弹出栈顶元素，计算天数差并更新结果
 * 4. 将当前位置i压入栈中
 * 5. 重复直到遍历完成
 * <p>
 * 时间复杂度: O(n)，每个元素最多入栈出栈一次
 * 空间复杂度: O(n)，栈的空间开销
 *
 * @author lxd
 **/
public class Day20260104 {
    /**
     * 计算每日温度数组中每个位置到下一个更高温度的天数
     *
     * @param temperatures 输入的温度数组，每个元素表示对应天的温度
     * @return 结果数组，ans[i]表示第i天之后需要等待多少天才能遇到更高温度
     */
    public int[] dailyTemperatures(int[] temperatures) {
        // 获取温度数组长度
        int n = temperatures.length;

        // 初始化结果数组，所有元素默认为0
        int[] ans = new int[n];

        // 创建单调栈，存储数组下标
        // 栈中保持下标对应的温度值单调递减
        Deque<Integer> stack = new LinkedList<>();

        // 遍历温度数组的每个位置
        for (int i = 0; i < n; i++) {
            // 当栈不为空且当前温度高于栈顶对应位置的温度时
            // 说明找到了栈顶位置的下一个更高温度
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                // 弹出栈顶元素，获取对应的天数下标
                int prev = stack.pop();

                // 计算当前天i与前一个天prev之间的天数差
                // 这就是第prev天需要等待的天数才能遇到更高温度
                ans[prev] = i - prev;
            }

            // 将当前天数下标压入栈中
            // 用于后续可能的匹配
            stack.push(i);
        }

        // 返回结果数组
        return ans;
    }
}

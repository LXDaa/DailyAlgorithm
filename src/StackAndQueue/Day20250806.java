package StackAndQueue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 239. 滑动窗口最大值
 * <p>
 * https://leetcode.cn/problems/sliding-window-maximum/description/
 * <p>
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * <p>
 * 返回 滑动窗口中的最大值 。
 */
public class Day20250806 {

    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        // 使用双端队列存储数组下标，队列头部始终是当前窗口的最大值下标
        Deque<Integer> deque = new LinkedList<Integer>();

        // 初始化第一个窗口
        for (int i = 0; i < k; ++i) {
            // 保持队列单调递减：如果当前元素大于等于队尾元素，则移除队尾
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            // 将当前元素下标加入队尾
            deque.offerLast(i);
        }

        // 存储结果
        int[] result = new int[n - k + 1];
        // 第一个窗口的最大值
        result[0] = nums[deque.peekFirst()];

        // 处理后续窗口
        for (int i = k; i < n; ++i) {
            // 保持队列单调递减：如果当前元素大于等于队尾元素，则移除队尾
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            // 将当前元素下标加入队尾
            deque.offerLast(i);

            // 移除超出窗口范围的元素下标
            //i - k: 当前窗口的左边界索引
            while (deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // 当前窗口的最大值
            result[i - k + 1] = nums[deque.peekFirst()];
        }
        return result;
    }
}


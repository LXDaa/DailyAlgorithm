package MonotonicStack;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 503.下一个更大元素II
 * <p>
 * 题目描述:
 * 给定一个循环数组 nums （ nums[nums.length - 1] 的下一个元素是 nums[0] ），返回 nums 中每个元素的 下一个更大元素 。
 * 数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1 。
 * <p>
 * 解题思路:
 * 1. 使用单调栈来解决下一个更大元素问题
 * 2. 由于是循环数组，需要遍历2倍长度的数组来模拟循环效果
 * 3. 维护一个单调递减栈，存储数组索引
 * 4. 当遇到比栈中元素更大的数时，说明找到了栈中元素的下一个更大元素
 * <p>
 * 算法步骤:
 * 1. 初始化结果数组，全部填充-1
 * 2. 创建单调栈用于存储数组索引
 * 3. 遍历两倍长度的数组，通过取模操作映射到原数组索引
 * 4. 对于每个元素，检查是否比栈中元素更大，如果是则更新结果
 * <p>
 * 时间复杂度: O(n)，每个元素最多入栈出栈一次
 * 空间复杂度: O(n)，栈的空间和结果数组的空间
 *
 * @author lxd
 **/
public class Day20260106 {
    /**
     * 找到循环数组中每个元素的下一个更大元素
     *
     * @param nums 输入的循环数组
     * @return 每个元素对应的下一个更大元素数组
     */
    public int[] nextGreaterElements(int[] nums) {
        int len = nums.length;
        // 初始化结果数组，全部填充-1，表示默认没有下一个更大元素
        int[] ans = new int[len];
        Arrays.fill(ans, -1);

        // 使用单调栈存储数组索引，维护一个单调递减的栈
        Deque<Integer> stack = new LinkedList<>();

        // 遍历两倍长度的数组，通过取模操作模拟循环数组
        // 这样可以确保每个元素都有机会找到其在循环数组中的下一个更大元素
        for (int i = 0; i < len * 2; i++) {
            // 当栈不为空且当前元素大于栈顶索引对应元素时
            // 说明找到了栈顶元素的下一个更大元素
            while (!stack.isEmpty() && nums[i % len] > nums[stack.peek()]) {
                // 弹出栈顶索引
                int preIndex = stack.pop();
                // 更新该索引位置的下一个更大元素为当前元素
                ans[preIndex] = nums[i % len];
            }
            // 将当前索引入栈，等待找到其下一个更大元素
            // 注意：使用取模操作确保索引在原数组范围内
            stack.push(i % len);
        }
        return ans;
    }
}
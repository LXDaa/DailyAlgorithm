package MonotonicStack;

import java.util.*;

/**
 * <a href="https://leetcode.cn/problems/next-greater-element-i/description/">496.下一个更大元素 I</a>
 * <p>
 * 题目描述:
 * nums1 中数字 x 的 下一个更大元素 是指 x 在 nums2 中对应位置 右侧 的 第一个 比 x 大的元素。
 * 给你两个 没有重复元素 的数组 nums1 和 nums2 ，下标从 0 开始计数，其中nums1 是 nums2 的子集。
 * 对于每个 0 <= i < nums1.length ，找出满足 nums1[i] == nums2[j] 的下标 j ，并且在 nums2 确定 nums2[j] 的 下一个更大元素 。
 * 如果不存在下一个更大元素，那么本次查询的答案是 -1 。
 * 返回一个长度为 nums1.length 的数组 ans 作为答案，满足 ans[i] 是如上所述的 下一个更大元素 。
 * <p>
 * 解题思路:
 * 使用单调栈解决下一个更大元素问题。核心思想是利用单调递减栈来找到每个元素的下一个更大元素。
 * <p>
 * 算法步骤:
 * 1. 创建一个哈希表，用于记录nums1中每个元素在结果数组中的索引位置
 * 2. 使用单调栈遍历nums2数组，栈中存储nums2的索引
 * 3. 当遇到比栈顶元素大的数时，说明找到了栈顶元素的下一个更大元素
 * 4. 如果栈顶元素在nums1中存在，则更新结果数组对应位置的值
 * 5. 继续处理直到遍历完nums2
 * <p>
 * 状态定义:
 * - ans[i]: 存储nums1[i]的下一个更大元素，初始值为-1
 * - map: 记录nums1中每个元素对应的索引，用于快速定位结果数组位置
 * - stack: 单调递减栈，存储nums2中的索引
 * <p>
 * 时间复杂度: O(nums1.length + nums2.length)，每个元素最多入栈出栈一次
 * 空间复杂度: O(nums1.length + nums2.length)，哈希表和栈的空间开销
 *
 * @author lxd
 **/
public class Day20260105 {
    /**
     * 找出nums1中每个元素在nums2中对应位置的下一个更大元素
     *
     * @param nums1 查询数组，是nums2的子集
     * @param nums2 主数组，用于查找下一个更大元素
     * @return 结果数组，ans[i]表示nums1[i]的下一个更大元素，若不存在则为-1
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // 初始化结果数组，所有元素默认为-1
        int[] ans = new int[nums1.length];
        Arrays.fill(ans, -1);

        // 创建哈希表，记录nums1中每个元素在结果数组中的索引位置
        Map<Integer, Integer> map = new HashMap<>();

        // 单调栈，用于存储nums2的索引，维护单调递减序列
        Deque<Integer> stack = new LinkedList<>();

        // 将nums1中的元素和其在结果数组中的索引建立映射关系
        for (int i = 0; i < nums1.length; i++) {
            map.put(nums1[i], i);
        }

        // 遍历nums2数组，寻找每个元素的下一个更大元素
        for (int i = 0; i < nums2.length; i++) {
            // 当栈不为空且当前元素大于栈顶索引对应的nums2元素时
            // 说明找到了栈顶索引对应元素的下一个更大元素
            while (!stack.isEmpty() && nums2[i] > nums2[stack.peek()]) {
                // 弹出栈顶索引，并获取对应的元素值
                int preNum = nums2[stack.pop()];

                // 如果该元素在nums1中存在，则更新结果数组中对应位置的值
                if (map.containsKey(preNum)) {
                    // 将当前元素作为preNum的下一个更大元素
                    ans[map.get(preNum)] = nums2[i];
                }
            }
            // 将当前索引入栈，继续寻找其下一个更大元素
            stack.push(i);
        }

        // 返回结果数组
        return ans;
    }
}

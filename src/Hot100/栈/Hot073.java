package Hot100.栈;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author lxd
 **/
public class Hot073 {
    /**
     * <a href="https://leetcode.cn/problems/largest-rectangle-in-histogram/description/">84. 柱状图中最大的矩形</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1。</p>
     * <p>求在该柱状图中，能够勾勒出来的矩形的最大面积。</p>
     *
     * <h3>💡 核心思路：单调栈</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用单调栈找到每个柱子左右两侧第一个比它矮的柱子
     *     <ul>
     *       <li>栈中存储索引，保持栈顶到栈底的高度递增</li>
     *       <li>遇到比栈顶矮的柱子时，弹出栈顶并计算面积</li>
     *       <li>面积 = 高度 × (右边界 - 左边界 - 1)</li>
     *       <li>最后处理栈中剩余元素</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用单调栈</b>：
     *     <ul>
     *       <li>需要找到每个元素左右第一个更小的元素</li>
     *       <li>单调栈可以在 O(n) 时间内解决</li>
     *       <li>避免暴力 O(n^2) 解法</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个元素入栈一次，出栈一次</li>
     *   <li><b>空间复杂度</b>：O(n)，栈存储索引</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：heights = [2, 1, 5, 6, 2, 3]
     *
     * 遍历过程（i 从 0 到 6，i=6 时 curHeight=0）：
     *
     * i=0, curHeight=2：
     *   stack.isEmpty() → push(0)
     *   stack = [0]
     *
     * i=1, curHeight=1：
     *   1 < heights[0]=2 ✅
     *   h = heights[0]=2
     *   left = -1（栈空）
     *   right = 1
     *   w = 1 - (-1) - 1 = 1
     *   area = 2 * 1 = 2 → maxArea = 2
     *   push(1)
     *   stack = [1]
     *
     * i=2, curHeight=5：
     *   5 > heights[1]=1 ❌
     *   push(2)
     *   stack = [1, 2]
     *
     * i=3, curHeight=6：
     *   6 > heights[2]=5 ❌
     *   push(3)
     *   stack = [1, 2, 3]
     *
     * i=4, curHeight=2：
     *   2 < heights[3]=6 ✅
     *   h = heights[3]=6
     *   left = 2
     *   right = 4
     *   w = 4 - 2 - 1 = 1
     *   area = 6 * 1 = 6 → maxArea = 6
     *
     *   2 < heights[2]=5 ✅
     *   h = heights[2]=5
     *   left = 1
     *   right = 4
     *   w = 4 - 1 - 1 = 2
     *   area = 5 * 2 = 10 → maxArea = 10
     *
     *   2 > heights[1]=1 ❌
     *   push(4)
     *   stack = [1, 4]
     *
     * i=5, curHeight=3：
     *   3 > heights[4]=2 ❌
     *   push(5)
     *   stack = [1, 4, 5]
     *
     * i=6, curHeight=0：
     *   0 < heights[5]=3 ✅
     *   h = heights[5]=3
     *   left = 4
     *   right = 6
     *   w = 6 - 4 - 1 = 1
     *   area = 3 * 1 = 3 → maxArea 不变
     *
     *   0 < heights[4]=2 ✅
     *   h = heights[4]=2
     *   left = 1
     *   right = 6
     *   w = 6 - 1 - 1 = 4
     *   area = 2 * 4 = 8 → maxArea 不变
     *
     *   0 < heights[1]=1 ✅
     *   h = heights[1]=1
     *   left = -1
     *   right = 6
     *   w = 6 - (-1) - 1 = 6
     *   area = 1 * 6 = 6 → maxArea 不变
     *
     *   push(6)
     *   stack = [6]
     *
     * 最终 maxArea = 10 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么遍历到 n（而非 n-1）？
     *    - i=n 时 curHeight=0，确保栈中所有元素都被弹出
     *    - 0 比所有非负高度都小，作为哨兵元素
     *    - 避免最后单独处理栈中剩余元素
     *
     * 2️⃣ 左边界如何确定？
     *    - 弹出栈顶后，新的栈顶就是左边界
     *    - 如果栈为空，左边界为 -1
     *    - left = stack.isEmpty() ? -1 : stack.peek()
     *
     * 3️⃣ 右边界如何确定？
     *    - 当前索引 i 就是右边界
     *    - 因为 curHeight < heights[stack.peek()]
     *    - 所以 i 是第一个比弹出元素矮的位置
     *
     * 4️⃣ 宽度如何计算？
     *    - w = right - left - 1
     *    - right = i（当前索引）
     *    - left = 新栈顶（或 -1）
     *    - 例如：left=1, right=4 → w=2
     *
     * 5️⃣ 面积如何计算？
     *    - area = h * w
     *    - h = heights[弹出的索引]
     *    - w = right - left - 1
     *    - 更新 maxArea
     *
     * 6️⃣ 单调栈的特性？
     *    - 栈中索引对应的高度保持递增
     *    - 栈顶是最近的、高度最小的元素
     *    - 遇到更小的元素时，弹出所有比它大的元素
     *
     * 7️⃣ 时间复杂度分析：
     *    - 每个元素入栈一次，出栈一次
     *    - 总共 O(n) 次操作
     *    - 时间复杂度 O(n)
     *
     * 8️⃣ 完整流程总结：
     *
     *    n = heights.length
     *    stack = []
     *    maxArea = 0
     *
     *    for i in 0..n：
     *      curHeight = i == n ? 0 : heights[i]
     *      while stack 不为空 && curHeight < heights[stack.peek()]：
     *        h = heights[stack.pop()]
     *        left = stack.isEmpty() ? -1 : stack.peek()
     *        right = i
     *        w = right - left - 1
     *        maxArea = max(maxArea, h * w)
     *      stack.push(i)
     *
     *    return maxArea
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>遍历到 n</b>：i 从 0 到 n，确保所有元素都被处理</li>
     *   <li><b>哨兵元素</b>：i=n 时 curHeight=0，触发栈中所有元素弹出</li>
     *   <li><b>左边界</b>：弹出后栈空则左边界为 -1</li>
     *   <li><b>宽度计算</b>：w = right - left - 1</li>
     *   <li><b>存储索引</b>：栈中存储索引，不是高度值</li>
     * </ul>
     *
     * @param heights 柱状图高度数组
     * @return 最大矩形面积
     */
    public int largestRectangleArea(int[] heights) {
        // 📏 获取高度数组长度
        int n = heights.length;

        // 📦 创建单调栈，存储索引
        Deque<Integer> stack = new LinkedList<>();

        // 📋 最大面积
        int maxArea = 0;

        // 🔄 遍历数组，i 从 0 到 n（包含 n）
        for (int i = 0; i <= n; i++) {
            // 📊 当前高度：i=n 时为 0（哨兵元素）
            int curHeight = i == n ? 0 : heights[i];

            // 🔍 弹出所有比当前高度高的元素
            while (!stack.isEmpty() && curHeight < heights[stack.peek()]) {
                // ➖ 弹出索引，获取高度
                int h = heights[stack.pop()];

                // 📐 左边界：栈空则为 -1，否则为新栈顶
                int left = stack.isEmpty() ? -1 : stack.peek();

                // 📐 右边界：当前索引 i
                int right = i;

                // 📏 宽度 = 右边界 - 左边界 - 1
                int w = right - left - 1;

                // 📊 更新最大面积
                maxArea = Math.max(maxArea, h * w);
            }

            // ➕ 将当前索引压入栈
            stack.push(i);
        }

        // ✅ 返回最大面积
        return maxArea;
    }
}

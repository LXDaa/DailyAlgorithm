package Hot100.栈;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author lxd
 **/
public class Hot072 {
    /**
     * <a href="https://leetcode.cn/problems/daily-temperatures/description/">739. 每日温度</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。如果气温在这之后都不会升高，请在该位置用 0 来代替。</p>
     *
     * <h3>💡 核心思路：单调栈</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用单调栈存储尚未找到更高温度的索引
     *     <ul>
     *       <li>遍历每天的温度</li>
     *       <li>栈中存储索引，保持栈顶到栈底的温度递增</li>
     *       <li>当遇到更高温度时，弹出栈顶元素并计算天数</li>
     *       <li>将当前索引压入栈</li>
     *       <li>未弹出的元素对应 answer[i] = 0</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用单调栈</b>：
     *     <ul>
     *       <li>需要找到每个元素右边第一个更大的元素</li>
     *       <li>单调栈可以在 O(n) 时间内解决这类问题</li>
     *       <li>避免暴力 O(n^2) 解法</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个元素入栈一次，出栈一次</li>
     *   <li><b>空间复杂度</b>：O(n)，栈存储索引</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：temperatures = [73, 74, 75, 71, 69, 72, 76, 73]
     *
     * 遍历过程：
     *
     * 初始化：res = [0,0,0,0,0,0,0,0], stack = []
     *
     * i=0, temp=73：
     *   stack.isEmpty() → push(0)
     *   stack = [0]
     *
     * i=1, temp=74：
     *   temp > temperatures[0]=73 ✅
     *   index = pop() = 0
     *   res[0] = 1 - 0 = 1
     *   stack.isEmpty() → push(1)
     *   stack = [1]
     *
     * i=2, temp=75：
     *   temp > temperatures[1]=74 ✅
     *   index = pop() = 1
     *   res[1] = 2 - 1 = 1
     *   stack.isEmpty() → push(2)
     *   stack = [2]
     *
     * i=3, temp=71：
     *   temp < temperatures[2]=75 ❌
     *   push(3)
     *   stack = [2, 3]
     *
     * i=4, temp=69：
     *   temp < temperatures[3]=71 ❌
     *   push(4)
     *   stack = [2, 3, 4]
     *
     * i=5, temp=72：
     *   temp > temperatures[4]=69 ✅
     *   index = pop() = 4
     *   res[4] = 5 - 4 = 1
     *
     *   temp > temperatures[3]=71 ✅
     *   index = pop() = 3
     *   res[3] = 5 - 3 = 2
     *
     *   temp < temperatures[2]=75 ❌
     *   push(5)
     *   stack = [2, 5]
     *
     * i=6, temp=76：
     *   temp > temperatures[5]=72 ✅
     *   index = pop() = 5
     *   res[5] = 6 - 5 = 1
     *
     *   temp > temperatures[2]=75 ✅
     *   index = pop() = 2
     *   res[2] = 6 - 2 = 4
     *
     *   stack.isEmpty() → push(6)
     *   stack = [6]
     *
     * i=7, temp=73：
     *   temp < temperatures[6]=76 ❌
     *   push(7)
     *   stack = [6, 7]
     *
     * 遍历结束：
     *   res = [1, 1, 4, 2, 1, 1, 0, 0] ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 单调栈存储什么？
     *    - 存储索引，而不是温度值
     *    - 通过索引可以获取温度值
     *    - 计算天数时需要用到索引差值
     *
     * 2️⃣ 单调栈的特性是什么？
     *    - 栈中索引对应的温度保持递增顺序
     *    - 栈顶是最近的、温度最低的元素
     *    - 遇到更高温度时，弹出所有比它小的元素
     *
     * 3️⃣ 如何计算天数？
     *    - 当前索引 i - 弹出的索引 index
     *    - 例如：i=5, index=3 → res[3] = 5 - 3 = 2
     *    - 表示第 3 天后第 2 天遇到更高温度
     *
     * 4️⃣ 为什么用 while 循环而不是 if？
     *    - 当前温度可能比栈中多个元素高
     *    - 需要弹出所有比它小的元素
     *    - 每个元素都会被弹出一次
     *
     * 5️⃣ 栈为空时如何处理？
     *    - 直接将当前索引压入栈
     *    - 没有比它小的元素需要处理
     *
     * 6️⃣ 未弹出的元素对应的 res 值是多少？
     *    - res 数组初始化为 0
     *    - 未弹出的元素表示后面没有更高温度
     *    - 对应的 res[i] 保持为 0
     *
     * 7️⃣ 时间复杂度分析：
     *    - 每个元素入栈一次，出栈一次
     *    - 总共 O(n) 次操作
     *    - 时间复杂度 O(n)
     *
     * 8️⃣ 完整流程总结：
     *
     *    n = temperatures.length
     *    res = [0] * n
     *    stack = []
     *
     *    for i in 0..n-1：
     *      while stack 不为空 && temperatures[i] > temperatures[stack.peek()]：
     *        index = stack.pop()
     *        res[index] = i - index
     *      stack.push(i)
     *
     *    return res
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>存储索引</b>：栈中存储索引，不是温度值</li>
     *   <li><b>单调递增</b>：保持栈中索引对应的温度递增</li>
     *   <li><b>while 循环</b>：弹出所有比当前温度小的元素</li>
     *   <li><b>初始化为 0</b>：res 数组初始化为 0，未处理的保持为 0</li>
     *   <li><b>时间复杂度</b>：O(n)，不是 O(n^2)</li>
     * </ul>
     *
     * @param temperatures 每日温度数组
     * @return 下一个更高温度出现的天数数组
     */
    public int[] dailyTemperatures(int[] temperatures) {
        // 📏 获取温度数组长度
        int n = temperatures.length;

        // 📋 创建结果数组，初始化为 0
        int[] res = new int[n];

        // 📦 创建单调栈，存储索引
        Deque<Integer> st = new LinkedList<Integer>();

        // 🔄 遍历每天的温度
        for (int i = 0; i < n; i++) {
            // 🔍 弹出所有比当前温度小的元素
            while (!st.isEmpty() && temperatures[i] > temperatures[st.peek()]) {
                // ➖ 弹出索引
                int index = st.pop();
                // 📝 计算天数
                res[index] = i - index;
            }
            // ➕ 将当前索引压入栈
            st.push(i);
        }

        // ✅ 返回结果数组
        return res;
    }
}

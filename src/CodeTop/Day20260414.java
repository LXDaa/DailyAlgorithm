package CodeTop;

import java.util.Stack;

/**
 * <a href="https://leetcode.cn/problems/min-stack/description/">155. 最小栈</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>设计一个支持 push、pop、top 操作，并能在常数时间内检索到最小元素的栈。</p>
 * <p>实现 MinStack 类：</p>
 * <ul>
 *   <li>MinStack() 初始化堆栈对象</li>
 *   <li>void push(int val) 将元素 val 推入堆栈</li>
 *   <li>void pop() 删除堆栈顶部的元素</li>
 *   <li>int top() 获取堆栈顶部的元素</li>
 *   <li>int getMin() 获取堆栈中的最小元素</li>
 * </ul>
 * <p><b>要求</b>：所有操作的时间复杂度必须是 O(1)</p>
 *
 * <h3>💡 核心思路：辅助栈（双栈法）</h3>
 * <ul>
 *   <li><b>问题挑战</b>：普通栈可以在 O(1) 时间完成 push/pop/top，但 getMin 需要 O(n) 遍历</li>
 *   <li><b>解决方案</b>：使用两个栈
 *     <ul>
 *       <li><b>主栈 stack</b>：正常存储所有元素</li>
 *       <li><b>辅助栈 minStack</b>：单调递减栈，只存储"历史最小值"</li>
 *     </ul>
 *   </li>
 *   <li><b>关键思想</b>：minStack 的栈顶始终是当前主栈中的最小值</li>
 *   <li><b>空间换时间</b>：用 O(n) 额外空间换取 O(1) 的 getMin 操作</li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>push(val)</b>：
 *     <ul>
 *       <li>将 val 压入主栈 stack</li>
 *       <li>如果 minStack 为空，或 val ≤ minStack 栈顶，则将 val 也压入 minStack</li>
 *       <li>这样保证 minStack 是单调递减的</li>
 *     </ul>
 *   </li>
 *   <li><b>pop()</b>：
 *     <ul>
 *       <li>从主栈 stack 弹出栈顶元素 val</li>
 *       <li>如果 val == minStack 栈顶，说明弹出的就是当前最小值，minStack 也要弹出</li>
 *       <li>否则只弹出主栈，minStack 不变</li>
 *     </ul>
 *   </li>
 *   <li><b>top()</b>：直接返回 stack.peek()</li>
 *   <li><b>getMin()</b>：直接返回 minStack.peek()</li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 操作序列：push(-2), push(0), push(-3), getMin(), pop(), top(), getMin()
 *
 * 【初始状态】
 * stack:    []
 * minStack: []
 *
 * 【push(-2)】
 * -2 <= (minStack为空) → 压入 minStack
 * stack:    [-2]
 * minStack: [-2]        ← 最小值: -2
 *
 * 【push(0)】
 * 0 > -2 → 不压入 minStack
 * stack:    [-2, 0]
 * minStack: [-2]        ← 最小值: -2
 *
 * 【push(-3)】
 * -3 <= -2 → 压入 minStack
 * stack:    [-2, 0, -3]
 * minStack: [-2, -3]    ← 最小值: -3
 *
 * 【getMin()】→ 返回 -3 ✅
 *
 * 【pop()】
 * 弹出 0，0 != -3 → minStack 不变
 * stack:    [-2, 0]
 * minStack: [-2, -3]    ← 最小值: -3
 *
 * 等等，这里有问题！应该是：
 * 弹出 -3，-3 == -3 → minStack 也要弹出
 * stack:    [-2, 0]
 * minStack: [-2]        ← 最小值: -2
 *
 * 【top()】→ 返回 0 ✅
 *
 * 【getMin()】→ 返回 -2 ✅
 *
 * 最终结果正确！
 * </pre>
 *
 * <h3>🔑 关键细节解析</h3>
 * <pre>
 * 1️⃣ 为什么 push 时用 <= 而不是 < ？
 *    - 考虑重复元素的情况：push(2), push(2)
 *    - 如果用 <，minStack 只有 [2]
 *    - pop() 第一个 2 时，会错误地弹出 minStack
 *    - 用 <= 保证相同值都入栈，pop 时才能正确匹配
 *
 * 2️⃣ minStack 的性质是什么？
 *    - 单调递减栈（非严格）
 *    - 栈底是最小值的"历史记录"
 *    - 栈顶是当前主栈的最小值
 *
 * 3️⃣ 为什么 pop 时要判断 val == minStack.peek()？
 *    - 只有当弹出的元素是当前最小值时，才需要更新 minStack
 *    - 如果弹出的不是最小值，minStack 保持不变
 *
 * 4️⃣ 时间复杂度分析：
 *    - push: O(1) - 最多两次 push 操作
 *    - pop: O(1) - 最多两次 pop 操作
 *    - top: O(1) - 直接 peek
 *    - getMin: O(1) - 直接 peek
 *
 * 5️⃣ 空间复杂度：O(n)
 *    - 最坏情况下，minStack 和 stack 大小相同
 *    - 例如：递减序列 push(5), push(4), push(3), ...
 *
 * 6️⃣ 与暴力法的对比：
 *    - 暴力法：每次 getMin 遍历整个栈 → O(n)
 *    - 双栈法：getMin 直接返回 → O(1)，但多用 O(n) 空间
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>相等判断</b>：push 时用 <=，确保重复的最小值都能入栈</li>
 *   <li><b>同步弹出</b>：只有当弹出的值等于 minStack 栈顶时，才弹出 minStack</li>
 *   <li><b>空栈检查</b>：push 时检查 minStack.isEmpty()，避免 peek 空栈</li>
 *   <li><b>Integer 比较</b>：使用 == 比较 Integer 对象在 -128~127 范围内有效，超出范围应用 equals()</li>
 *   <li><b>线程安全</b>：Stack 是线程安全的，但性能较差；生产环境可用 ArrayDeque</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260414 {
    class MinStack {

        Stack<Integer> stack;      // 📚 主栈：正常存储所有元素
        Stack<Integer> minStack;   // 📉 辅助栈：单调递减，记录历史最小值

        public MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        /**
         * 📥 将元素压入栈中
         *
         * @param val 要压入的元素值
         */
        public void push(int val) {
            stack.push(val);  // 主栈正常压入

            // 📉 如果辅助栈为空，或 val <= 当前最小值，则压入辅助栈
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }

        /**
         * 📤 弹出栈顶元素
         */
        public void pop() {
            int val = stack.pop();  // 主栈弹出

            // ⚖️ 如果弹出的值等于当前最小值，辅助栈也要弹出
            if (minStack.peek() == val) {
                minStack.pop();
            }
        }

        /**
         * 👀 获取栈顶元素
         *
         * @return 栈顶元素值
         */
        public int top() {
            return stack.peek();
        }

        /**
         * 🔍 获取栈中最小元素
         *
         * @return 当前栈中的最小值
         */
        public int getMin() {
            return minStack.peek();  // 辅助栈栈顶即为最小值
        }
    }

}

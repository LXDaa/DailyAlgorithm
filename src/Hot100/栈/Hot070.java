package Hot100.栈;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author lxd
 **/
public class Hot070 {
    /**
     * <a href="https://leetcode.cn/problems/min-stack/description/">155. 最小栈</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。</p>
     * <p>实现 MinStack 类：</p>
     * <ul>
     *   <li>MinStack() 初始化堆栈对象。</li>
     *   <li>void push(int val) 将元素 val 推入堆栈。</li>
     *   <li>void pop() 删除堆栈顶部的元素。</li>
     *   <li>int top() 获取堆栈顶部的元素。</li>
     *   <li>int getMin() 获取堆栈中的最小元素。</li>
     * </ul>
     *
     * <h3>💡 核心思路：双栈法</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用两个栈，一个存储元素，一个存储当前最小值
     *     <ul>
     *       <li>stack：存储所有元素</li>
     *       <li>minStack：存储对应位置的最小值</li>
     *       <li>push 时，minStack 压入当前最小值</li>
     *       <li>pop 时，两个栈同时弹出</li>
     *       <li>getMin 时，直接返回 minStack 的栈顶</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用双栈</b>：
     *     <ul>
     *       <li>需要在 O(1) 时间内获取最小值</li>
     *       <li>单栈无法在弹出后知道新的最小值</li>
     *       <li>双栈同步操作，保证最小值始终在栈顶</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：所有操作 O(1)</li>
     *   <li><b>空间复杂度</b>：O(n)，两个栈存储相同数量的元素</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 操作序列：push(3), push(2), push(5), push(1), getMin(), pop(), getMin()
     *
     * 初始化：
     *   stack = [], minStack = [MAX_VALUE]
     *
     * push(3)：
     *   stack.push(3) → stack = [3]
     *   minStack.push(min(MAX_VALUE, 3)) → minStack = [MAX_VALUE, 3]
     *
     * push(2)：
     *   stack.push(2) → stack = [3, 2]
     *   minStack.push(min(3, 2)) → minStack = [MAX_VALUE, 3, 2]
     *
     * push(5)：
     *   stack.push(5) → stack = [3, 2, 5]
     *   minStack.push(min(2, 5)) → minStack = [MAX_VALUE, 3, 2, 2]
     *
     * push(1)：
     *   stack.push(1) → stack = [3, 2, 5, 1]
     *   minStack.push(min(2, 1)) → minStack = [MAX_VALUE, 3, 2, 2, 1]
     *
     * getMin()：
     *   minStack.peek() = 1 → return 1 ✅
     *
     * pop()：
     *   stack.pop() → stack = [3, 2, 5]
     *   minStack.pop() → minStack = [MAX_VALUE, 3, 2, 2]
     *
     * getMin()：
     *   minStack.peek() = 2 → return 2 ✅
     *
     * 最终状态：
     *   stack = [3, 2, 5]
     *   minStack = [MAX_VALUE, 3, 2, 2]
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ minStack 为什么初始化为 [MAX_VALUE]？
     *    - 作为哨兵元素，方便第一次 push 时计算最小值
     *    - 第一次 push 时：min(MAX_VALUE, val) = val
     *    - 避免空栈检查
     *
     * 2️⃣ push 操作如何保证最小值正确？
     *    - minStack.push(Math.min(minStack.peek(), value))
     *    - 每次压入当前栈中的最小值
     *    - 保证 minStack 的栈顶始终是当前最小值
     *
     * 3️⃣ pop 操作为什么要同步弹出？
     *    - 两个栈的元素数量始终相等
     *    - 弹出后，minStack 的新栈顶就是新的最小值
     *    - 保持两个栈的同步状态
     *
     * 4️⃣ getMin 如何实现 O(1)？
     *    - 直接返回 minStack.peek()
     *    - 不需要遍历或计算
     *    - O(1) 时间复杂度
     *
     * 5️⃣ top 操作如何实现？
     *    - 直接返回 stack.peek()
     *    - 和普通栈的 top 操作一样
     *
     * 6️⃣ 空间复杂度分析：
     *    - 两个栈各存储 n 个元素
     *    - 空间复杂度 O(n)
     *    - 换取了 O(1) 的时间复杂度
     *
     * 7️⃣ 边界情况测试：
     *    - 空栈：getMin() 时 minStack 至少有 MAX_VALUE
     *    - 单元素：push(5), getMin() → return 5
     *    - 重复最小值：push(2), push(2), pop(), getMin() → return 2
     *    - 递减序列：push(5), push(3), push(1), getMin() → return 1
     *
     * 8️⃣ 完整流程总结：
     *
     *    构造函数：
     *    stack = []
     *    minStack = [MAX_VALUE]
     *
     *    push(value)：
     *    stack.push(value)
     *    minStack.push(min(minStack.peek(), value))
     *
     *    pop()：
     *    stack.pop()
     *    minStack.pop()
     *
     *    top()：
     *    return stack.peek()
     *
     *    getMin()：
     *    return minStack.peek()
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>初始化</b>：minStack 必须初始化为 [MAX_VALUE]</li>
     *   <li><b>同步操作</b>：push 和 pop 必须同时操作两个栈</li>
     *   <li><b>最小值计算</b>：push 时使用 Math.min(minStack.peek(), value)</li>
     *   <li><b>时间复杂度</b>：所有操作必须 O(1)</li>
     *   <li><b>栈为空</b>：pop 和 top 操作前应检查栈是否为空</li>
     * </ul>
     */
    class MinStack {

        Deque<Integer> stack;
        Deque<Integer> minStack;

        /**
         * 初始化堆栈对象
         */
        public MinStack() {
            // 📦 创建主栈
            stack = new LinkedList<Integer>();
            // 📦 创建最小值栈，初始化为 [MAX_VALUE]
            minStack = new LinkedList<Integer>();
            minStack.push(Integer.MAX_VALUE);
        }

        /**
         * 将元素 val 推入堆栈
         *
         * @param value 要推入的元素
         */
        public void push(int value) {
            // ➕ 主栈压入元素
            stack.push(value);
            // ➕ 最小值栈压入当前最小值
            minStack.push(Math.min(minStack.peek(), value));
        }

        /**
         * 删除堆栈顶部的元素
         */
        public void pop() {
            // ➖ 主栈弹出元素
            stack.pop();
            // ➖ 最小值栈同步弹出
            minStack.pop();
        }

        /**
         * 获取堆栈顶部的元素
         *
         * @return 堆栈顶部的元素
         */
        public int top() {
            // 📋 返回主栈栈顶元素
            return stack.peek();
        }

        /**
         * 获取堆栈中的最小元素
         *
         * @return 堆栈中的最小元素
         */
        public int getMin() {
            // 📋 返回最小值栈栈顶元素
            return minStack.peek();
        }
    }
}

package CodeTop;

import java.util.Stack;

/**
 * <a href="https://leetcode.cn/problems/implement-queue-using-stacks/description/">232. 用栈实现队列</a>
 * <p>
 * 请你仅使用两个栈实现先入先出队列。队列应当支持一般队列支持的所有操作（push、pop、peek、empty）：
 * <p>
 * 实现 MyQueue 类：
 * <p>
 * void push(int x) 将元素 x 推到队列的末尾
 * int pop() 从队列的开头移除并返回元素
 * int peek() 返回队列开头的元素
 * boolean empty() 如果队列为空，返回 true ；否则，返回 false
 * 说明：
 * <p>
 * 你 只能 使用标准的栈操作 —— 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
 * 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
 *
 * <p><strong>题目分析：</strong></p>
 * <p>需要使用两个栈来模拟队列的行为，队列是 FIFO（先进先出），而栈是 LIFO（后进先出）</p>
 *
 * <p><strong>解题思路：</strong></p>
 * <ol>
 *     <li><strong>双栈设计：</strong>
 *         <ol>
 *             <li><strong>stackIn（输入栈）：</strong>负责接收新元素，所有 push 操作都放入此栈</li>
 *             <li><strong>stackOut（输出栈）：</strong>负责提供队首元素，pop 和 peek 操作都从此栈获取</li>
 *         </ol>
 *     </li>
 *     <li><strong>核心思想：</strong>通过倒转输入栈的元素到输出栈，实现先进先出的效果
 *         <ul>
 *             <li>例如：stackIn = [1,2,3]（栈底到栈顶），倒入 stackOut 后变为 [3,2,1]</li>
 *             <li>此时 stackOut 的栈顶就是最早进入的元素 1，实现了 FIFO</li>
 *         </ul>
 *     </li>
 *     <li><strong>关键优化：</strong>不需要每次都倒转，只有当 stackOut 为空时才需要倒入
 *         <ul>
 *             <li>这样可以保证均摊时间复杂度为 O(1)</li>
 *             <li>每个元素最多只会被倒入一次</li>
 *         </ul>
 *     </li>
 * </ol>
 *
 * <p><strong>各操作的实现逻辑：</strong></p>
 * <ol>
 *     <li><strong>push(x)：</strong>直接压入 stackIn</li>
 *     <li><strong>pop()：</strong>先确保 stackOut 不为空（必要时倒入），然后弹出 stackOut 栈顶</li>
 *     <li><strong>peek()：</strong>先确保 stackOut 不为空（必要时倒入），然后返回 stackOut 栈顶</li>
 *     <li><strong>empty()：</strong>当且仅当两个栈都为空时，队列才为空</li>
 * </ol>
 *
 * <p><strong>复杂度分析：</strong></p>
 * <ul>
 *     <li>时间复杂度：
 *         <ul>
 *             <li>push：O(1)</li>
 *             <li>pop/peek：均摊 O(1)，最坏情况 O(n)（当需要倒转时）</li>
 *             <li>empty：O(1)</li>
 *         </ul>
 *     </li>
 *     <li>空间复杂度：O(n)，两个栈总共存储 n 个元素</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260318 {
    /**
     * 使用两个栈实现队列的内部类
     *
     * <p><strong>设计原理：</strong></p>
     * <ol>
     *     <li>利用栈的 LIFO 特性，通过两次倒转实现 FIFO</li>
     *     <li>输入栈负责接收新元素，输出栈负责提供老元素</li>
     *     <li>只有在输出栈为空且需要取元素时，才进行倒转操作</li>
     * </ol>
     */
    class MyQueue {
        // 输入栈：所有新元素都压入此栈
        Stack<Integer> stackIn;

        // 输出栈：从此栈弹出/查看元素，栈顶即为队列的头部
        Stack<Integer> stackOut;

        /**
         * 初始化队列
         *
         * <p><strong>实现步骤：</strong></p>
         * <ol>
         *     <li>创建输入栈 stackIn</li>
         *     <li>创建输出栈 stackOut</li>
         * </ol>
         */
        public MyQueue() {
            stackIn = new Stack<>();
            stackOut = new Stack<>();
        }

        /**
         * 将元素 x 推到队列的末尾
         *
         * <p><strong>实现逻辑：</strong></p>
         * <ol>
         *     <li>直接将元素压入输入栈 stackIn</li>
         *     <li>不需要立即倒转到输出栈，延迟到真正需要时再处理</li>
         * </ol>
         *
         * @param x 要添加到队列末尾的元素
         */
        public void push(int x) {
            // 新元素直接压入输入栈
            stackIn.push(x);
        }

        /**
         * 从队列的开头移除并返回元素
         *
         * <p><strong>实现逻辑：</strong></p>
         * <ol>
         *     <li>调用 dumpStackIn() 确保输出栈不为空</li>
         *     <li>从输出栈弹出栈顶元素（即队列的第一个元素）</li>
         * </ol>
         *
         * @return 队列开头的元素
         */
        public int pop() {
            // 确保输出栈不为空
            dumpStackIn();

            // 弹出输出栈的栈顶元素（队列的头部）
            return stackOut.pop();
        }

        /**
         * 返回队列开头的元素但不移除
         *
         * <p><strong>实现逻辑：</strong></p>
         * <ol>
         *     <li>调用 dumpStackIn() 确保输出栈不为空</li>
         *     <li>返回输出栈的栈顶元素（即队列的第一个元素）</li>
         * </ol>
         *
         * @return 队列开头的元素
         */
        public int peek() {
            // 确保输出栈不为空
            dumpStackIn();

            // 返回输出栈的栈顶元素（队列的头部）
            return stackOut.peek();
        }

        /**
         * 判断队列是否为空
         *
         * <p><strong>实现逻辑：</strong></p>
         * <ol>
         *     <li>当且仅当输入栈和输出栈都为空时，队列为空</li>
         *     <li>只要有一个栈不为空，队列就不为空</li>
         * </ol>
         *
         * @return 如果队列为空返回 true，否则返回 false
         */
        public boolean empty() {
            // 两个栈都为空时，队列才为空
            return stackIn.isEmpty() && stackOut.isEmpty();
        }

        /**
         * 将输入栈的元素倒入输出栈
         *
         * <p><strong>核心方法：</strong>这是实现队列行为的关键</p>
         *
         * <p><strong>实现逻辑：</strong></p>
         * <ol>
         *     <li><strong>检查输出栈：</strong>如果输出栈不为空，直接返回，不需要倒转
         *             <ul>
         *                 <li>因为输出栈中的元素已经是正确的顺序（栈顶是最老的元素）</li>
         *                 <li>避免不必要的操作，提高效率</li>
         *             </ul>
         *         </li>
         *         <li><strong>倒转元素：</strong>当输出栈为空时，将输入栈的所有元素倒入输出栈
         *             <ul>
         *                 <li>输入栈：[1,2,3]（栈底到栈顶，3 是最新的元素）</li>
         *                 <li>倒入后输出栈：[3,2,1]（栈底到栈顶，1 在栈顶）</li>
         *                 <li>这样输出栈的栈顶就是最早进入的元素 1，实现了 FIFO</li>
         *             </ul>
         *         </li>
         * </ol>
         *
         * <p><strong>为什么需要这个操作？</strong></p>
         * <ul>
         *             <li>栈是 LIFO（后进先出），队列是 FIFO（先进先出）</li>
         *             <li>通过倒转，最早的元素会出现在输出栈的栈顶</li>
         *             <li>只在必要时倒转，保证均摊时间复杂度为 O(1)</li>
         *         </ul>
         */
        private void dumpStackIn() {
            // 如果输出栈不为空，直接返回
            // 原因：输出栈中的元素已经是正确的顺序，不需要再次倒转
            while (!stackOut.isEmpty()) {
                return;
            }

            // 将输入栈的所有元素倒入输出栈
            // 这个过程会反转元素的顺序，使得最早的元素出现在输出栈的栈顶
            while (!stackIn.isEmpty()) {
                // 弹出输入栈的栈顶元素，压入输出栈
                stackOut.push(stackIn.pop());
            }
        }
    }
}

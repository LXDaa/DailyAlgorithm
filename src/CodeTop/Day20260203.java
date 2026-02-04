package CodeTop;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <a href="https://leetcode.cn/problems/valid-parentheses/description/">20. 有效的括号</a>
 * <p>
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 每个右括号都有一个对应的相同类型的左括号。
 * <p>
 * <h3>解题思路</h3>
 * <p>
 * 本题使用栈（Stack）数据结构来解决括号匹配问题，核心思想如下：<br>
 * <ol>
 * <li>遇到左括号时，将其对应的右括号压入栈中</li>
 * <li>遇到右括号时，检查栈是否为空或者栈顶元素是否与当前右括号匹配</li>
 * <li>如果不匹配或栈为空，则返回false</li>
 * <li>如果匹配，则弹出栈顶元素</li>
 * <li>遍历完整个字符串后，若栈为空则说明所有括号都正确匹配</li>
 * </ol>
 * <p>
 * <h3>算法步骤</h3>
 * <p>
 * 1. 首先判断字符串长度是否为奇数，若是则直接返回false（因为有效的括号字符串长度必须为偶数）<br>
 * 2. 创建一个栈用于存储预期的右括号<br>
 * 3. 遍历字符串中的每个字符：<br>
 * &nbsp;&nbsp;- 如果是左括号('(', '[', '{')，则将对应的右括号压入栈中<br>
 * &nbsp;&nbsp;- 如果是右括号(')', ']', '}')，则检查栈是否为空或栈顶元素是否与当前右括号匹配<br>
 * &nbsp;&nbsp;- 若不匹配或栈为空，返回false<br>
 * &nbsp;&nbsp;- 若匹配，弹出栈顶元素<br>
 * 4. 遍历结束后，检查栈是否为空，若为空则说明所有括号都正确匹配<br>
 * <p>
 * <h3>时间复杂度</h3>
 * <p>
 * O(n)，其中n是字符串的长度，需要遍历字符串一次<br>
 * <p>
 * <h3>空间复杂度</h3>
 * <p>
 * O(n)，最坏情况下（全是左括号），栈中会存储所有的字符<br>
 * <p>
 * <h3>示例说明</h3>
 * <p>
 * 以输入字符串 "()[]{}" 为例：<br>
 * &nbsp;&nbsp;- 遇到'('，将')'入栈，栈：[')']<br>
 * &nbsp;&nbsp;- 遇到')'，与栈顶元素')'匹配，弹出栈顶，栈：[]<br>
 * &nbsp;&nbsp;- 遇到'['，将']'入栈，栈：[']']<br>
 * &nbsp;&nbsp;- 遇到']'，与栈顶元素']'匹配，弹出栈顶，栈：[]<br>
 * &nbsp;&nbsp;- 遇到'{'，将'}'入栈，栈：['}']<br>
 * &nbsp;&nbsp;- 遇到'}'，与栈顶元素'}'匹配，弹出栈顶，栈：[]<br>
 * &nbsp;&nbsp;- 遍历结束，栈为空，返回true<br>
 *
 * @author lxd
 **/
public class Day20260203 {
    /**
     * 判断字符串中的括号是否有效
     *
     * @param s 包含括号的字符串
     * @return 如果括号有效返回true，否则返回false
     */
    public boolean isValid(String s) {
        int n = s.length();
        // 如果字符串长度为奇数，则不可能有效（每个左括号必须对应一个右括号）
        if (n % 2 == 1) {
            return false;
        }

        // 使用栈来存储期望遇到的右括号
        Deque<Character> stack = new LinkedList();

        // 遍历字符串中的每个字符
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            // 如果是左括号，将对应的右括号压入栈中
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else {
                // 如果是右括号，检查是否与栈顶元素匹配
                // 如果栈为空（没有对应的左括号）或栈顶元素与当前右括号不匹配，则返回false
                if (stack.isEmpty() || stack.peek() != c) {
                    return false;
                } else {
                    // 匹配成功，弹出栈顶元素
                    stack.pop();
                }
            }
        }

        // 遍历完成后，如果栈为空说明所有括号都正确匹配，否则说明还有未匹配的左括号
        return stack.isEmpty();
    }
}


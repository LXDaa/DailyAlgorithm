package StackAndQueue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 150. 逆波兰表达式求值
 * 给你一个字符串数组 tokens ，表示一个根据 逆波兰表示法 表示的算术表达式。
 * <p>
 * 请你计算该表达式。返回一个表示表达式值的整数。
 * <p>
 * 注意：
 * <p>
 * 有效地算符为 '+'、'-'、'*' 和 '/' 。
 * 每个操作数（运算对象）都可以是一个整数或者另一个表达式。
 * 两个整数之间的除法总是 向零截断 。
 * 表达式中不含除零运算。
 * 输入是一个根据逆波兰表示法表示的算术表达式。
 * 答案及所有中间计算结果可以用 32 位 整数表示。
 */
public class Day20250805 {
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();
        for (String s : tokens) {
            if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
                //注意a,b操作顺序
                int a = stack.pop();
                int b = stack.pop();
                int c = switch (s) {
                    case "+" -> a + b;
                    case "-" -> b - a;
                    case "*" -> a * b;
                    default -> b / a;
                };
                stack.push(c);
            } else {
                stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }
}

package Hot100.栈;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author lxd
 **/
public class Hot069 {
    /**
     * <a href="https://leetcode.cn/problems/valid-parentheses/description/">20. 有效的括号</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。</p>
     * <p>有效字符串需满足：</p>
     * <ul>
     *   <li>左括号必须用相同类型的右括号闭合。</li>
     *   <li>左括号必须以正确的顺序闭合。</li>
     * </ul>
     *
     * <h3>💡 核心思路：栈 + 哈希表</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用栈来匹配括号的闭合
     *     <ul>
     *       <li>遍历字符串中的每个字符</li>
     *       <li>如果是左括号，入栈</li>
     *       <li>如果是右括号，检查栈顶元素是否匹配</li>
     *       <li>匹配则出栈，不匹配则返回 false</li>
     *       <li>遍历结束后，栈必须为空才有效</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用栈</b>：
     *     <ul>
     *       <li>括号匹配遵循后进先出（LIFO）原则</li>
     *       <li>最后出现的左括号必须最先被闭合</li>
     *       <li>栈是实现这种逻辑的最佳数据结构</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，遍历字符串一次</li>
     *   <li><b>空间复杂度</b>：O(n)，最坏情况栈存储所有左括号</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例1：s = "()[]{}"
     *
     * 遍历过程：
     *
     * 字符: '(' → 左括号，入栈
     * 栈: ['(']
     *
     * 字符: ')' → 右括号
     *   栈顶 = '(', map.get('(') = ')' == ')' ✅
     *   出栈
     * 栈: []
     *
     * 字符: '[' → 左括号，入栈
     * 栈: ['[']
     *
     * 字符: ']' → 右括号
     *   栈顶 = '[', map.get('[') = ']' == ']' ✅
     *   出栈
     * 栈: []
     *
     * 字符: '{' → 左括号，入栈
     * 栈: ['{']
     *
     * 字符: '}' → 右括号
     *   栈顶 = '{', map.get('{') = '}' == '}' ✅
     *   出栈
     * 栈: []
     *
     * 遍历结束，栈为空 → return true ✅
     *
     * 示例2：s = "([)]"
     *
     * 遍历过程：
     *
     * 字符: '(' → 入栈
     * 栈: ['(']
     *
     * 字符: '[' → 入栈
     * 栈: ['(', '[']
     *
     * 字符: ')' → 右括号
     *   栈顶 = '[', map.get('[') = ']' != ')' ❌
     *   return false
     *
     * 示例3：s = "{[]}"
     *
     * 遍历过程：
     *
     * '{' → 入栈 → ['{']
     * '[' → 入栈 → ['{', '[']
     * ']' → 匹配 '[' → 出栈 → ['{']
     * '}' → 匹配 '{' → 出栈 → []
     *
     * 栈为空 → return true ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 如何快速判断括号类型？
     *    - 使用 HashMap 存储左括号到右括号的映射
     *    - map.put('(', ')')
     *    - map.put('{', '}')
     *    - map.put('[', ']')
     *    - O(1) 时间查找匹配的右括号
     *
     * 2️⃣ 为什么先判断长度是否为偶数？
     *    - 如果长度为奇数，必然无法匹配
     *    - 提前返回 false，避免无效遍历
     *    - 这是一个简单的优化
     *
     * 3️⃣ 遇到右括号时如何处理？
     *    - 检查栈是否为空（无匹配的左括号）
     *    - 检查栈顶元素的匹配右括号是否等于当前字符
     *    - 两者有一个不满足就返回 false
     *    - 否则出栈
     *
     * 4️⃣ 为什么遍历结束后要检查栈是否为空？
     *    - 可能存在多余的左括号
     *    - 例如：s = "(()"，遍历结束后栈不为空
     *    - 栈为空才表示所有左括号都已匹配
     *
     * 5️⃣ 栈的操作流程：
     *    - push：左括号入栈
     *    - pop：匹配成功后出栈
     *    - peek：查看栈顶元素（匹配检查）
     *    - isEmpty：判断是否还有未匹配的左括号
     *
     * 6️⃣ 时间复杂度分析：
     *    - 每个字符入栈一次，出栈一次
     *    - 总共 O(n) 次操作
     *    - 时间复杂度 O(n)
     *
     * 7️⃣ 边界情况测试：
     *    - 空字符串：s = "" → return true
     *    - 单字符：s = "(" → return false
     *    - 不匹配：s = "([)]" → return false
     *    - 嵌套：s = "{[()]}" → return true
     *
     * 8️⃣ 完整流程总结：
     *
     *    if s.length() % 2 != 0 → return false
     *
     *    创建括号映射 map：{'(': ')', '{': '}', '[': ']'}
     *    创建栈 stack
     *
     *    for c in s：
     *      if c 是左括号（map.containsKey(c)）：
     *        stack.push(c)
     *      else：
     *        if stack.isEmpty() || map.get(stack.pop()) != c：
     *          return false
     *
     *    return stack.isEmpty()
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>提前判断</b>：长度为奇数时直接返回 false</li>
     *   <li><b>空栈检查</b>：遇到右括号时先检查栈是否为空</li>
     *   <li><b>映射表</b>：使用 HashMap 存储括号映射</li>
     *   <li><b>栈为空</b>：遍历结束后必须检查栈是否为空</li>
     *   <li><b>字符类型</b>：题目只包含括号字符，无需处理其他字符</li>
     * </ul>
     *
     * @param s 只包含括号的字符串
     * @return 是否为有效括号字符串
     */
    public boolean isValid(String s) {
        // 🚫 快速判断：长度为奇数，必然无法匹配
        if (s.length() % 2 != 0) {
            return false;
        }

        // 📋 创建括号映射表
        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('{', '}');
        map.put('[', ']');

        // 📦 创建栈
        Deque<Character> stack = new LinkedList<>();

        // 🔄 遍历字符串
        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                // ➕ 左括号，入栈
                stack.push(c);
            } else {
                // ➖ 右括号，检查匹配
                if (stack.isEmpty() || map.get(stack.pop()) != c) {
                    // ❌ 栈为空或不匹配
                    return false;
                }
            }
        }

        // ✅ 栈为空表示所有括号都已匹配
        return stack.isEmpty();
    }
}

package Hot100.栈;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author lxd
 **/
public class Hot071 {
    /**
     * <a href="https://leetcode.cn/problems/decode-string/description/">394. 字符串解码</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个经过编码的字符串，返回它解码后的字符串。</p>
     * <p>编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。</p>
     * <p>你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。</p>
     *
     * <h3>💡 核心思路：双栈法</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用两个栈，一个存储数字，一个存储字符串
     *     <ul>
     *       <li>numStack：存储重复次数</li>
     *       <li>strStack：存储进入括号前的字符串</li>
     *       <li>遇到 '[' 时，将当前数字和字符串入栈</li>
     *       <li>遇到 ']' 时，弹出数字和字符串，拼接重复</li>
     *       <li>遇到字母时，直接追加到当前结果</li>
     *       <li>遇到数字时，累积计算完整数字</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用双栈</b>：
     *     <ul>
     *       <li>支持嵌套结构</li>
     *       <li>后进先出，正确处理嵌套顺序</li>
     *       <li>需要保存进入括号前的状态</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，n 是解码后的字符串长度</li>
     *   <li><b>空间复杂度</b>：O(n)，栈存储嵌套状态</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：s = "3[a2[b]]"
     *
     * 解码过程：
     *
     * 初始状态：
     *   res = "", num = 0
     *   numStack = [], strStack = []
     *
     * 字符: '3' → 数字
     *   num = 0 * 10 + 3 = 3
     *
     * 字符: '[' → 左括号
     *   numStack.push(3) → numStack = [3]
     *   strStack.push("") → strStack = [""]
     *   num = 0, res = ""
     *
     * 字符: 'a' → 字母
     *   res.append('a') → res = "a"
     *
     * 字符: '2' → 数字
     *   num = 0 * 10 + 2 = 2
     *
     * 字符: '[' → 左括号
     *   numStack.push(2) → numStack = [3, 2]
     *   strStack.push("a") → strStack = ["", "a"]
     *   num = 0, res = ""
     *
     * 字符: 'b' → 字母
     *   res.append('b') → res = "b"
     *
     * 字符: ']' → 右括号
     *   cnt = numStack.pop() = 2
     *   tmp = new StringBuilder(strStack.pop()) = new StringBuilder("a")
     *   for i in 0..1: tmp.append(res) → tmp = "ab" → tmp = "abb"
     *   res = tmp → res = "abb"
     *
     * 字符: ']' → 右括号
     *   cnt = numStack.pop() = 3
     *   tmp = new StringBuilder(strStack.pop()) = new StringBuilder("")
     *   for i in 0..2: tmp.append(res) → tmp = "abb" → "abbabb" → "abbabbabb"
     *   res = tmp → res = "abbabbabb"
     *
     * 遍历结束：
     *   return "abbabbabb" ✅
     *
     * 示例2：s = "2[abc]3[cd]ef"
     *
     * 解码过程：
     *   2[abc] → "abcabc"
     *   3[cd] → "cdcdcd"
     *   ef → "ef"
     *   结果："abcabccdcdcdef" ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 如何处理多位数？
     *    - 使用 num = num * 10 + c - '0'
     *    - 例如："123" → 1 → 12 → 123
     *    - 逐位累积计算
     *
     * 2️⃣ 遇到 '[' 时做什么？
     *    - numStack.push(num)：保存重复次数
     *    - strStack.push(res.toString())：保存进入括号前的字符串
     *    - num = 0：重置数字
     *    - res = new StringBuilder()：重置当前结果
     *
     * 3️⃣ 遇到 ']' 时做什么？
     *    - cnt = numStack.pop()：弹出重复次数
     *    - tmp = new StringBuilder(strStack.pop())：弹出前缀字符串
     *    - for i in 0..cnt-1：tmp.append(res)：重复拼接
     *    - res = tmp：更新当前结果
     *
     * 4️⃣ 为什么用 StringBuilder？
     *    - 高效的字符串拼接
     *    - append 和 toString 操作效率高
     *    - 比 String 拼接更高效
     *
     * 5️⃣ 如何处理嵌套？
     *    - 通过栈保存每层的状态
     *    - 遇到 '[' 入栈，遇到 ']' 出栈
     *    - 栈的深度就是嵌套层数
     *
     * 6️⃣ 时间复杂度分析：
     *    - 每个字符遍历一次
     *    - 重复拼接的总次数等于解码后的字符数
     *    - 时间复杂度 O(n)，n 是解码后的长度
     *
     * 7️⃣ 边界情况测试：
     *    - 无嵌套：s = "3[a]" → "aaa"
     *    - 嵌套：s = "3[a2[b]]" → "abbabbabb"
     *    - 无数字：s = "abc" → "abc"
     *    - 末尾字母：s = "2[ab]cd" → "ababcd"
     *
     * 8️⃣ 完整流程总结：
     *
     *    numStack = [], strStack = []
     *    res = "", num = 0
     *
     *    for c in s：
     *      if c 是数字：num = num * 10 + c - '0'
     *      elif c == '['：
     *        numStack.push(num), strStack.push(res.toString())
     *        num = 0, res = ""
     *      elif c == ']'：
     *        cnt = numStack.pop()
     *        tmp = new StringBuilder(strStack.pop())
     *        for i in 0..cnt-1：tmp.append(res)
     *        res = tmp
     *      else：res.append(c)
     *
     *    return res.toString()
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>数字累积</b>：使用 num = num * 10 + c - '0' 处理多位数</li>
     *   <li><b>栈操作</b>：遇到 '[' 入栈，遇到 ']' 出栈</li>
     *   <li><b>StringBuilder</b>：使用 StringBuilder 提高字符串操作效率</li>
     *   <li><b>嵌套处理</b>：通过栈保存每层的状态</li>
     *   <li><b>重置状态</b>：遇到 '[' 时重置 num 和 res</li>
     * </ul>
     *
     * @param s 经过编码的字符串
     * @return 解码后的字符串
     */
    public String decodeString(String s) {
        // 📦 创建数字栈和字符串栈
        Deque<String> strStack = new LinkedList<>();
        Deque<Integer> numStack = new LinkedList<>();

        // 📋 当前结果字符串
        StringBuilder res = new StringBuilder();
        // 📋 当前数字
        int num = 0;

        // 🔄 遍历字符串
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                // 🔢 数字：累积计算（处理多位数）
                num = num * 10 + c - '0';
            } else if (c == '[') {
                // ➕ 左括号：保存状态
                numStack.push(num);
                strStack.push(res.toString());
                num = 0;
                res = new StringBuilder();
            } else if (c == ']') {
                // ➖ 右括号：拼接重复字符串
                int cnt = numStack.pop();
                StringBuilder tmp = new StringBuilder(strStack.pop());
                for (int i = 0; i < cnt; i++) {
                    tmp.append(res);
                }
                res = tmp;
            } else {
                // 📝 字母：直接追加
                res.append(c);
            }
        }

        // ✅ 返回解码结果
        return res.toString();
    }
}

package CodeTop;

import java.util.Stack;

/**
 * <a href="https://leetcode.cn/problems/decode-string/description/">394. 字符串解码</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>给定一个经过编码的字符串，返回它解码后的字符串。</p>
 * <p><b>编码规则</b>：k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。</p>
 * <p>你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。</p>
 * <p>此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k，例如不会出现像 3a 或 2[4] 的输入。</p>
 *
 * <h3>💡 核心思路：辅助栈（Stack）</h3>
 * <ul>
 *   <li><b>问题特点</b>：嵌套结构（如 3[a2[c]]），需要处理多层括号</li>
 *   <li><b>解决方案</b>：使用栈来保存字符，遇到 ']' 时进行解码</li>
 *   <li><b>关键思想</b>：
 *     <ul>
 *       <li>非 ']' 字符：直接入栈</li>
 *       <li>遇到 ']'：弹出栈中内容直到 '['，获取待重复字符串和重复次数，解码后重新入栈</li>
 *     </ul>
 *   </li>
 *   <li><b>栈的作用</b>：暂存未处理的字符，遇到右括号时回溯解码</li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>遍历字符串</b>：逐个字符处理
 *     <ul>
 *       <li>如果 c != ']'：转为字符串并入栈</li>
 *       <li>如果 c == ']'：开始解码流程</li>
 *     </ul>
 *   </li>
 *   <li><b>解码流程</b>（遇到 ']' 时）：
 *     <ol>
 *       <li><b>提取字符串</b>：弹出栈顶元素直到 '['，拼接出需要重复的字符串 tmp</li>
 *       <li><b>弹出左括号</b>：移除 '['</li>
 *       <li><b>提取数字</b>：继续弹出栈顶的数字字符，拼接出重复次数 num</li>
 *       <li><b>重复字符串</b>：将 tmp 重复 num 次得到 repeatedStr</li>
 *       <li><b>重新入栈</b>：将 repeatedStr 压入栈中</li>
 *     </ol>
 *   </li>
 *   <li><b>拼接结果</b>：遍历结束后，将栈中所有元素拼接成最终字符串</li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例：s = "3[a]2[bc]"
 *
 * 【遍历过程】
 *
 * 字符: '3' → 入栈
 * 栈: ["3"]
 *
 * 字符: '[' → 入栈
 * 栈: ["3", "["]
 *
 * 字符: 'a' → 入栈
 * 栈: ["3", "[", "a"]
 *
 * 字符: ']' → 开始解码!
 *   1️⃣ 提取字符串: 弹出直到 '['
 *      tmp = "a"
 *      栈: ["3", "["]
 *   2️⃣ 弹出 '['
 *      栈: ["3"]
 *   3️⃣ 提取数字: 弹出数字字符
 *      numStr = "3", num = 3
 *      栈: []
 *   4️⃣ 重复字符串: "a" × 3 = "aaa"
 *   5️⃣ 重新入栈
 *      栈: ["aaa"]
 *
 * 字符: '2' → 入栈
 * 栈: ["aaa", "2"]
 *
 * 字符: '[' → 入栈
 * 栈: ["aaa", "2", "["]
 *
 * 字符: 'b' → 入栈
 * 栈: ["aaa", "2", "[", "b"]
 *
 * 字符: 'c' → 入栈
 * 栈: ["aaa", "2", "[", "b", "c"]
 *
 * 字符: ']' → 开始解码!
 *   1️⃣ 提取字符串: 弹出直到 '['
 *      tmp = "bc" (注意顺序: "b" + "c")
 *      栈: ["aaa", "2", "["]
 *   2️⃣ 弹出 '['
 *      栈: ["aaa", "2"]
 *   3️⃣ 提取数字: 弹出数字字符
 *      numStr = "2", num = 2
 *      栈: ["aaa"]
 *   4️⃣ 重复字符串: "bc" × 2 = "bcbc"
 *   5️⃣ 重新入栈
 *      栈: ["aaa", "bcbc"]
 *
 * 【遍历结束】
 * 拼接栈中元素: "aaa" + "bcbc" = "aaabcbc" ✅
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 嵌套例子：s = "3[a2[c]]"
 *
 * 字符: '3', '[', 'a', '2', '[', 'c' → 依次入栈
 * 栈: ["3", "[", "a", "2", "[", "c"]
 *
 * 字符: ']' → 内层解码
 *   tmp = "c", num = 2
 *   repeatedStr = "cc"
 *   栈: ["3", "[", "a", "cc"]
 *
 * 字符: ']' → 外层解码
 *   tmp = "acc", num = 3
 *   repeatedStr = "accaccacc"
 *   栈: ["accaccacc"]
 *
 * 结果: "accaccacc" ✅
 * </pre>
 *
 * <h3>🔑 关键技巧解析</h3>
 * <pre>
 * 1️⃣ 为什么 tmp = stack.pop() + tmp ？
 *    - 栈是后进先出（LIFO），弹出的顺序是反的
 *    - 例如栈中有 ["a", "b", "c"]，弹出顺序是 c, b, a
 *    - 要得到 "abc"，必须将新弹出的字符放在前面
 *    - tmp = "c" → tmp = "b" + "c" = "bc" → tmp = "a" + "bc" = "abc"
 *
 * 2️⃣ 为什么 numStr = stack.pop() + numStr ？
 *    - 同理，多位数如 "123" 在栈中是 ["1", "2", "3"]
 *    - 弹出顺序是 3, 2, 1
 *    - 要得到 "123"，必须将新弹出的数字放在前面
 *
 * 3️⃣ 为什么最后要用 sb.insert(0, stack.pop()) ？
 *    - 栈中元素顺序与最终结果相反
 *    - 例如栈: ["aaa", "bcbc"]，应该得到 "aaabcbc"
 *    - insert(0, ...) 每次都插到开头，保证正确顺序
 *
 * 4️⃣ 如何处理嵌套结构？
 *    - 内层的 ']' 先被处理，解码后重新入栈
 *    - 外层的 ']' 再处理时，内层已经是展开的字符串
 *    - 例如: 3[a2[c]] → 先解码 2[c] 得到 "cc" → 再解码 3[acc] 得到 "accaccacc"
 *
 * 5️⃣ 时间复杂度：O(n)
 *    - n 为输出字符串的长度
 *    - 每个字符最多入栈和出栈一次
 *
 * 6️⃣ 空间复杂度：O(n)
 *    - 栈的空间消耗
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>拼接顺序</b>：tmp 和 numStr 的拼接顺序至关重要，必须是 pop() + 原值</li>
 *   <li><b>多位数处理</b>：重复次数可能是多位数（如 123[...]），需要循环提取所有数字字符</li>
 *   <li><b>嵌套解码</b>：内层括号先解码，结果作为外层的一部分继续处理</li>
 *   <li><b>String vs StringBuilder</b>：频繁字符串拼接建议用 StringBuilder，但本题栈中存 String 更直观</li>
 *   <li><b>边界条件</b>：题目保证输入有效，无需额外校验括号匹配</li>
 *   <li><b>Character.isDigit()</b>：判断字符是否为数字，比手动判断更清晰</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260416 {
    /**
     * 🔓 解码字符串：将编码字符串还原为原始字符串
     *
     * @param s 编码后的字符串
     * @return 解码后的字符串
     */
    public String decodeString(String s) {
        Stack<String> stack = new Stack<>();  // 📚 辅助栈：存储字符和中间结果
        char[] chars = s.toCharArray();

        // 🔄 遍历每个字符
        for (char c : chars) {
            // 1️⃣ 如果不是右括号 ']'，就将当前字符转为字符串并入栈
            if (c != ']') {
                stack.push(String.valueOf(c));
            } else {
                // 2️⃣ 遇到右括号 ']'，开始解码

                // 2.1 弹出栈顶元素，直到遇到左括号 '['，拼接出需要重复的字符串
                String tmp = "";
                while (!stack.isEmpty() && !stack.peek().equals("[")) {
                    tmp = stack.pop() + tmp;  // ⚠️ 注意顺序：新弹出的字符在前面
                }
                stack.pop();  // 🗑️ 弹出左括号 '['

                // 2.2 继续弹出栈顶的数字字符，拼接出重复次数
                String numStr = "";
                while (!stack.isEmpty() && Character.isDigit(stack.peek().charAt(0))) {
                    numStr = stack.pop() + numStr;  // ⚠️ 注意顺序：新弹出的数字在前面
                }
                int num = Integer.parseInt(numStr);

                // 2.3 将字符串 tmp 重复 num 次
                String repeatedStr = "";
                for (int i = 0; i < num; i++) {
                    repeatedStr = repeatedStr + tmp;  // ✅ 修正拼接顺序
                }

                // 2.4 将重复后的字符串重新压入栈中，等待后续处理
                stack.push(repeatedStr);
            }
        }

        // 3️⃣ 遍历结束后，将栈中所有元素拼接成最终结果
        // ⚠️ 注意：从栈中弹出是逆序的，所以要插到 StringBuffer 的头部
        StringBuffer sb = new StringBuffer();
        while (!stack.isEmpty()) {
            sb.insert(0, stack.pop());  // 每次插到开头，保证正确顺序
        }
        return sb.toString();
    }
}

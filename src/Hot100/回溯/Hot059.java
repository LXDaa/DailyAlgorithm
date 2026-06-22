package Hot100.回溯;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot059 {
    /**
     * <a href="https://leetcode.cn/problems/generate-parentheses/description/">22. 括号生成</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>数字 n 代表生成括号的对数，请你写一个函数，用于能够生成所有可能的并且有效的括号组合。</p>
     *
     * <h3>💡 核心思路：回溯算法 + 括号合法性约束</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用回溯算法，逐步构建括号字符串
     *     <ul>
     *       <li>左括号可以随时添加，只要不超过 n 个</li>
     *       <li>右括号只能在左括号数量大于右括号时添加</li>
     *       <li>通过 left 和 right 计数器跟踪括号数量</li>
     *       <li>当 left == right == n 时找到一个有效组合</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么可以这样剪枝</b>：
     *     <ul>
     *       <li>有效括号的特性：任何时刻左括号数 >= 右括号数</li>
     *       <li>如果右括号数超过左括号数，则无法形成有效括号</li>
     *       <li>通过剪枝减少无效搜索，提高效率</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(4^n / sqrt(n))，卡特兰数复杂度</li>
     *   <li><b>空间复杂度</b>：O(n)，递归栈深度</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：n = 3
     *
     * 【回溯树】
     *
     *                      ""
     *               /                \
     *              "("               ← 添加左括号
     *           /       \           \
     *         "(("      "()"
     *        /   \      /   \        \
     *      "((("  "(()" "(())"  "()("
     *       |      \    |     \      |
     *     "((()"  "(()(" "(())"  "()(("
     *        \      |      \       |
     *      "((())" "(()(" "(())(" "()(("
     *         \      |       \      |
     *      "((())(" "(()()" "(())()" "()(())"
     *           \       |         |
     *      "((())()" "(()())" "(())()("
     *           |          \         |
     *      "((())())" "(()())()" "(())()()"
     *           |
     *      "((())())"
     *           |
     *      "((())())"
     *           |
     *      "((())())"
     *
     * 【递归搜索过程】
     *
     * backtrack(0, 0, "")
     *   ├─ left = 0, right = 0, 可以添加左括号
     *   │
     *   ├─ 添加 "("
     *   │   backtrack(1, 0, "(")
     *   │   ├─ left = 1, right = 0, 可以添加左括号
     *   │   ├─ 添加 "("
     *   │   │   backtrack(2, 0, "((")
     *   │   │   ├─ left = 2, right = 0, 可以添加左括号
     *   │   │   ├─ 添加 "("
     *   │   │   │   backtrack(3, 0, "(((")
     *   │   │   │   ├─ left = 3 == n，不能添加左括号
     *   │   │   │   ├─ left > right，可以添加右括号
     *   │   │   │   ├─ 添加 ")"
     *   │   │   │   │   backtrack(3, 1, "((()")
     *   │   │   │   │   ├─ left > right，可以添加右括号
     *   │   │   │   │   ├─ 添加 ")"
     *   │   │   │   │   │   backtrack(3, 2, "((())")
     *   │   │   │   │   │   ├─ left > right，可以添加右括号
     *   │   │   │   │   │   ├─ 添加 ")"
     *   │   │   │   │   │   │   backtrack(3, 3, "((())")
     *   │   │   │   │   │   │   ├─ left == n && right == n
     *   │   │   │   │   │   │   ├─ 添加组合："((()))" ✅
     *   │   │   │   │   │   │   └─ return
     *   │   │   │   │   │   └─ ...
     *   │   │   │   │   └─ ...
     *   │   │   │   └─ ...
     *   │   │   └─ ...
     *   │   └─ ...
     *   └─ ...
     *
     * 【最终结果】
     *
     * res = ["((()))", "(()())", "(())()", "()(())", "()()()"]
     *
     * 总共 5 种组合（卡特兰数 C(3) = 5）✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 有效括号的特性是什么？
     *    - 任何时刻：左括号数 >= 右括号数
     *    - 最终：左括号数 == 右括号数 == n
     *    - 这两个条件是判断有效括号的核心
     *
     * 2️⃣ 为什么 left > right 才能添加右括号？
     *    - 如果 right >= left，则右括号数量过多
     *    - 例如：")(" 就是无效的括号
     *    - 通过 left > right 剪枝，避免生成无效括号
     *
     * 3️⃣ 什么时候可以添加左括号？
     *    - left < n 时可以添加
     *    - 左括号数量不能超过 n
     *    - 任何时候都可以添加左括号（只要不超过 n）
     *
     * 4️⃣ 什么时候可以添加右括号？
     *    - left > right 时可以添加
     *    - 必须保证左括号数量大于右括号数量
     *    - 这确保了括号的有效性
     *
     * 5️⃣ 递归终止条件是什么？
     *    - left == n && right == n
     *    - 表示左右括号都已用完
     *    - 当前字符串是一个有效的括号组合
     *
     * 6️⃣ 回溯操作是什么？
     *    - s.append("(")：添加左括号
     *    - backtrack(n, left + 1, right, s, res)：递归
     *    - s.deleteCharAt(...)：撤销左括号
     *    - s.append(")")：添加右括号
     *    - backtrack(n, left, right + 1, s, res)：递归
     *    - s.deleteCharAt(...)：撤销右括号
     *
     * 7️⃣ 时间复杂度如何分析？
     *    - 卡特兰数：C(n) = (2n)! / (n! × (n+1)!)
     *    - 近似复杂度：O(4^n / sqrt(n))
     *    - 这是所有可能的括号组合数
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ res = []（结果集）
     *    └─ 调用 backtrack(n, 0, 0, "", res)
     *
     *    回溯函数 backtrack(n, left, right, s, res)：
     *    ├─ if left == n && right == n：
     *    │   ├─ res.add(s.toString())
     *    │   └─ return
     *    ├─ if left < n：
     *    │   ├─ s.append("(")
     *    │   ├─ backtrack(n, left + 1, right, s, res)
     *    │   └─ s.deleteCharAt(...)
     *    ├─ if left > right：
     *    │   ├─ s.append(")")
     *    │   ├─ backtrack(n, left, right + 1, s, res)
     *    │   └─ s.deleteCharAt(...)
     *    └─ return
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>剪枝条件</b>：left > right 是添加右括号的必要条件</li>
     *   <li><b>左括号限制</b>：left < n 是添加左括号的数量限制</li>
     *   <li><b>终止条件</b>：必须 left == right == n 才算有效组合</li>
     *   <li><b>StringBuilder</b>：使用 StringBuilder 提高字符串操作效率</li>
     *   <li><b>卡特兰数</b>：n 对括号有 C(n) 种组合，C(3) = 5</li>
     * </ul>
     *
     * @param n 括号对数
     * @return 所有可能的有效括号组合
     */
    public List<String> generateParenthesis(int n) {
        // 📦 创建结果列表
        List<String> res = new ArrayList<>();
        // 🔄 开始回溯搜索，左右括号数都为 0
        backtrack(n, 0, 0, new StringBuilder(), res);
        return res;
    }

    /**
     * 回溯搜索所有有效括号组合
     *
     * @param n     括号对数
     * @param left  当前左括号数量
     * @param right 当前右括号数量
     * @param s     当前括号字符串
     * @param res   结果集
     */
    private void backtrack(int n, int left, int right, StringBuilder s, List<String> res) {
        // 🎯 终止条件：左右括号都已用完，找到一个有效组合
        if (left == n && right == n) {
            // 📝 添加当前括号组合到结果集
            res.add(s.toString());
            return;
        }

        // 🔍 条件1：可以添加左括号（只要左括号数量未达到 n）
        if (left < n) {
            // ➕ 添加左括号
            s.append("(");
            // 🔄 递归搜索
            backtrack(n, left + 1, right, s, res);
            // ➖ 撤销选择，回溯到上一个状态
            s.deleteCharAt(s.length() - 1);
        }

        // 🔍 条件2：可以添加右括号（必须左括号数 > 右括号数）
        if (left > right) {
            // ➕ 添加右括号
            s.append(")");
            // 🔄 递归搜索
            backtrack(n, left, right + 1, s, res);
            // ➖ 撤销选择，回溯到上一个状态
            s.deleteCharAt(s.length() - 1);
        }
    }
}

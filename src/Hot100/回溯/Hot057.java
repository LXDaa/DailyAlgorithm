package Hot100.回溯;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot057 {
    /**
     * <a href="https://leetcode.cn/problems/letter-combinations-of-a-phone-number/description/">17. 电话号码的字母组合</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个仅包含数字 2-9 的字符串 digits，返回所有它能表示的字母组合。答案可以按任意顺序返回。</p>
     * <p>数字到字母的映射与电话按键相同（2-abc, 3-def, 4-ghi, 5-jkl, 6-mno, 7-pqrs, 8-tuv, 9-wxyz）。</p>
     *
     * <h3>💡 核心思路：回溯算法 + 数字映射</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用回溯算法，依次处理每个数字对应的字母
     *     <ul>
     *       <li>建立数字到字母的映射表</li>
     *       <li>从第一个数字开始，选择对应的字母</li>
     *       <li>递归处理下一个数字</li>
     *       <li>当处理完所有数字时，得到一个组合</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用回溯</b>：
     *     <ul>
     *       <li>每个数字对应多个字母，需要枚举所有组合</li>
     *       <li>回溯可以系统地遍历所有可能的路径</li>
     *       <li>通过撤销选择实现状态的回退</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(3^m × 4^n)，m 是对应 3 个字母的数字个数，n 是对应 4 个字母的数字个数</li>
     *   <li><b>空间复杂度</b>：O(m + n)，递归栈深度</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：digits = "23"
     *
     * 数字映射：
     * 2 → abc
     * 3 → def
     *
     * 【回溯树】
     *
     *          ""
     *       /  |  \
     *      a   b   c      ← 选择数字 2 的字母
     *     /|  /|  /|\
     *    d e d e d e f    ← 选择数字 3 的字母
     *    | | | | | | |
     *   ad ae bd be cd ce cf
     *
     * 【递归搜索过程】
     *
     * backtrack(0, "")
     *   ├─ index = 0, digits[0] = '2', str = "abc"
     *   │
     *   ├─ 选择 'a'
     *   │   backtrack(1, "a")
     *   │   ├─ index = 1, digits[1] = '3', str = "def"
     *   │   │
     *   │   ├─ 选择 'd'
     *   │   │   backtrack(2, "ad")
     *   │   │   ├─ index = 2 == digits.length()
     *   │   │   ├─ 添加组合："ad" ✅
     *   │   │   └─ return
     *   │   │
     *   │   ├─ 选择 'e'
     *   │   │   backtrack(2, "ae")
     *   │   │   ├─ 添加组合："ae" ✅
     *   │   │   └─ return
     *   │   │
     *   │   └─ 选择 'f'
     *   │       backtrack(2, "af")
     *   │       ├─ 添加组合："af" ✅
     *   │       └─ return
     *   │
     *   ├─ 选择 'b'
     *   │   backtrack(1, "b")
     *   │   ├─ 选择 'd' → "bd" ✅
     *   │   ├─ 选择 'e' → "be" ✅
     *   │   └─ 选择 'f' → "bf" ✅
     *   │
     *   └─ 选择 'c'
     *       backtrack(1, "c")
     *       ├─ 选择 'd' → "cd" ✅
     *       ├─ 选择 'e' → "ce" ✅
     *       └─ 选择 'f' → "cf" ✅
     *
     * 【最终结果】
     *
     * res = ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]
     *
     * 总共 3 × 3 = 9 种组合 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 数字到字母的映射如何建立？
     *    - 使用数组存储映射关系
     *    - 索引 0 和 1 对应空字符串（无字母）
     *    - 索引 2-9 对应字母字符串
     *    - 例如：numStr[2] = "abc"
     *
     * 2️⃣ 如何获取数字对应的字母？
     *    - digits.charAt(index) - '0'：将字符转为数字
     *    - numStr[数字]：获取对应的字母字符串
     *    - 例如：digits = "23"，index = 0
     *      digits.charAt(0) = '2'
     *      '2' - '0' = 2
     *      numStr[2] = "abc"
     *
     * 3️⃣ 递归终止条件是什么？
     *    - index == digits.length()
     *    - 表示已处理完所有数字
     *    - 当前 temp 就是一个完整的组合
     *
     * 4️⃣ 为什么用 StringBuilder？
     *    - 高效的字符串拼接
     *    - append 和 deleteCharAt 都是 O(1)
     *    - 比 String 拼接更高效
     *
     * 5️⃣ 回溯操作是什么？
     *    - temp.append(char)：添加字母
     *    - backtrack(...)：递归处理下一个数字
     *    - temp.deleteCharAt(...)：撤销选择
     *
     * 6️⃣ 空输入如何处理？
     *    - digits 为 null 或空字符串
     *    - 直接返回空列表
     *    - 不需要进入回溯
     *
     * 7️⃣ 时间复杂度如何分析？
     *    - 数字 2,3,4,5,6,8 对应 3 个字母
     *    - 数字 7,9 对应 4 个字母
     *    - 假设 m 个数字对应 3 个字母，n 个数字对应 4 个字母
     *    - 总组合数 = 3^m × 4^n
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ 检查空输入
     *    ├─ 建立数字到字母的映射
     *    └─ 调用 backtrack(digits, numStr, 0, res, temp)
     *
     *    回溯函数 backtrack(digits, numStr, index, res, temp)：
     *    ├─ if index == digits.length()：
     *    │   ├─ res.add(temp.toString())
     *    │   └─ return
     *    ├─ str = numStr[digits.charAt(index) - '0']
     *    ├─ for i in 0..str.length()-1：
     *    │   ├─ temp.append(str.charAt(i))
     *    │   ├─ backtrack(digits, numStr, index+1, res, temp)
     *    │   └─ temp.deleteCharAt(temp.length()-1)
     *    └─ return
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>空输入检查</b>：必须检查 digits 是否为空，避免空指针异常</li>
     *   <li><b>字符转数字</b>：使用 char - '0' 将字符转为数字</li>
     *   <li><b>StringBuilder</b>：使用 StringBuilder 提高字符串操作效率</li>
     *   <li><b>映射表</b>：索引 0 和 1 对应空字符串，题目只包含 2-9</li>
     *   <li><b>终止条件</b>：index == digits.length() 时收集结果</li>
     * </ul>
     *
     * @param digits 仅包含数字 2-9 的字符串
     * @return 所有可能的字母组合
     */
    public List<String> letterCombinations(String digits) {
        // 📦 创建结果列表
        List<String> res = new ArrayList<>();
        // 🎯 边界检查：空输入返回空列表
        if (digits == null || digits.length() == 0) {
            return res;
        }

        // 📋 数字到字母的映射表
        // 索引 0 和 1 对应空字符串（无字母）
        String[] numStr = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        // 🔄 开始回溯搜索，从索引 0 开始
        backtrack(digits, numStr, 0, res, new StringBuilder());
        return res;
    }

    /**
     * 回溯搜索所有字母组合
     *
     * @param digits 输入的数字字符串
     * @param numStr 数字到字母的映射表
     * @param index  当前处理的数字索引
     * @param res    结果集
     * @param temp   当前组合
     */
    private void backtrack(String digits, String[] numStr, int index, List<String> res, StringBuilder temp) {
        // 🎯 终止条件：已处理完所有数字
        if (index == digits.length()) {
            // 📝 添加当前组合到结果集
            res.add(temp.toString());
            return;
        }

        // 🔍 获取当前数字对应的字母字符串
        String str = numStr[digits.charAt(index) - '0'];

        // 🔄 遍历所有可能的字母选择
        for (int i = 0; i < str.length(); i++) {
            // ➕ 选择当前字母
            temp.append(str.charAt(i));

            // 🔄 递归处理下一个数字
            backtrack(digits, numStr, index + 1, res, temp);

            // ➖ 撤销选择，回溯到上一个状态
            temp.deleteCharAt(temp.length() - 1);
        }
    }
}

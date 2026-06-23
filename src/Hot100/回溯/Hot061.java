package Hot100.回溯;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot061 {
    /**
     * <a href="https://leetcode.cn/problems/palindrome-partitioning/description/">131. 分割回文串</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文串。返回 s 所有可能的分割方案。</p>
     *
     * <h3>💡 核心思路：回溯算法 + 回文判断</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用回溯算法，尝试所有可能的分割方式
     *     <ul>
     *       <li>从当前位置开始，尝试所有可能的子串</li>
     *       <li>如果子串是回文，则选择该子串继续递归</li>
     *       <li>当分割到字符串末尾时，收集结果</li>
     *       <li>回溯尝试其他分割方式</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用回溯</b>：
     *     <ul>
     *       <li>分割问题本质是组合问题</li>
     *       <li>需要枚举所有可能的分割点</li>
     *       <li>通过回溯系统地遍历所有可能的分割方案</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n × 2^n)，n 是字符串长度</li>
     *   <li><b>空间复杂度</b>：O(n)，递归栈深度</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：s = "aab"
     *
     * 【回溯树】
     *
     *                  ""
     *          /       |       \
     *         a       aa       aab
     *        / \      |        |
     *       a   ab    b        ✗(非回文)
     *       |   |     |
     *       b   ✗     ✅
     *       |
     *      ✅
     *
     * 【递归搜索过程】
     *
     * backtrack(0, [])
     *   ├─ index = 0
     *   │
     *   ├─ i = 0, subStr = "a"
     *   │   isPalindrome("a") = true ✅
     *   │   path = ["a"]
     *   │   backtrack(1, ["a"])
     *   │   ├─ index = 1
     *   │   │
     *   │   ├─ i = 1, subStr = "a"
     *   │   │   isPalindrome("a") = true ✅
     *   │   │   path = ["a", "a"]
     *   │   │   backtrack(2, ["a", "a"])
     *   │   │   ├─ index = 2
     *   │   │   │
     *   │   │   ├─ i = 2, subStr = "b"
     *   │   │   │   isPalindrome("b") = true ✅
     *   │   │   │   path = ["a", "a", "b"]
     *   │   │   │   backtrack(3, ["a", "a", "b"])
     *   │   │   │   ├─ index = 3 == s.length()
     *   │   │   │   ├─ 添加分割：["a", "a", "b"] ✅
     *   │   │   │   └─ return
     *   │   │   │
     *   │   │   └─ ...
     *   │   │
     *   │   ├─ i = 2, subStr = "ab"
     *   │   │   isPalindrome("ab") = false ❌
     *   │   │   continue
     *   │   │
     *   │   └─ ...
     *   │
     *   ├─ i = 1, subStr = "aa"
     *   │   isPalindrome("aa") = true ✅
     *   │   path = ["aa"]
     *   │   backtrack(2, ["aa"])
     *   │   ├─ i = 2, subStr = "b"
     *   │   │   isPalindrome("b") = true ✅
     *   │   │   path = ["aa", "b"]
     *   │   │   backtrack(3, ["aa", "b"])
     *   │   │   ├─ index = 3 == s.length()
     *   │   │   ├─ 添加分割：["aa", "b"] ✅
     *   │   │   └─ return
     *   │
     *   ├─ i = 2, subStr = "aab"
     *   │   isPalindrome("aab") = false ❌
     *   │   continue
     *   │
     *   └─ ...
     *
     * 【最终结果】
     *
     * res = [["a", "a", "b"], ["aa", "b"]]
     *
     * 总共 2 种分割方案 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 如何选择分割点？
     *    - 从 index 开始，尝试所有可能的结束位置 i
     *    - 子串为 s[index...i]（闭区间）
     *    - 每次递归后 index = i + 1
     *
     * 2️⃣ 回文判断如何实现？
     *    - 使用双指针法
     *    - left = 0, right = str.length() - 1
     *    - 比较对应位置的字符是否相等
     *    - 时间复杂度 O(k)，k 是子串长度
     *
     * 3️⃣ 递归终止条件是什么？
     *    - index == s.length()
     *    - 表示已分割完整个字符串
     *    - 当前 path 是一个有效的分割方案
     *
     * 4️⃣ 回溯操作是什么？
     *    - path.add(subStr)：选择当前子串
     *    - backtrack(s, i + 1, path, res)：递归
     *    - path.remove(path.size() - 1)：撤销选择
     *
     * 5️⃣ 时间复杂度如何分析？
     *    - 每个字符有两种选择：分割或不分割
     *    - 总共有 2^(n-1) 种分割方式
     *    - 每种分割需要 O(n) 时间判断回文
     *    - 总体复杂度：O(n × 2^n)
     *
     * 6️⃣ 如何优化？
     *    - 可以预处理回文判断，使用动态规划
     *    - dp[i][j] 表示 s[i...j] 是否是回文
     *    - 预处理时间 O(n^2)，查询 O(1)
     *    - 总体复杂度变为 O(n^2 + 2^n)
     *
     * 7️⃣ 为什么 index 从 i + 1 开始？
     *    - 保证每个字符只被分割一次
     *    - 避免重复分割
     *    - 例如："aab" 不会出现 ["a", "ab"] 和 ["aa", "b"] 的重复判断
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ res = []（结果集）
     *    └─ 调用 backtrack(s, 0, [], res)
     *
     *    回溯函数 backtrack(s, index, path, res)：
     *    ├─ if index == s.length()：
     *    │   ├─ res.add(new ArrayList(path))
     *    │   └─ return
     *    ├─ for i in index..n-1：
     *    │   ├─ subStr = s.substring(index, i+1)
     *    │   ├─ if isPalindrome(subStr)：
     *    │   │   ├─ path.add(subStr)
     *    │   │   ├─ backtrack(s, i+1, path, res)
     *    │   │   └─ path.remove(path.size()-1)
     *    └─ return
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>子串截取</b>：substring(index, i+1) 是左闭右开区间</li>
     *   <li><b>回文判断</b>：双指针法是最直接的实现方式</li>
     *   <li><b>递归参数</b>：index 表示下一个分割的起始位置</li>
     *   <li><b>深拷贝</b>：添加到结果集时使用 new ArrayList(path)</li>
     *   <li><b>优化空间</b>：对于大字符串，可以使用 DP 预处理回文</li>
     * </ul>
     *
     * @param s 输入字符串
     * @return 所有可能的分割方案
     */
    public List<List<String>> partition(String s) {
        // 📦 创建结果列表
        List<List<String>> res = new ArrayList<>();
        // 🔄 开始回溯搜索，从索引 0 开始
        backtrack(s, 0, new ArrayList<>(), res);
        return res;
    }

    /**
     * 回溯搜索所有分割方案
     *
     * @param s     输入字符串
     * @param index 当前分割的起始位置
     * @param path  当前分割路径
     * @param res   结果集
     */
    private void backtrack(String s, int index, List<String> path, List<List<String>> res) {
        // 🎯 终止条件：已分割完整个字符串
        if (index == s.length()) {
            // 📝 添加当前分割方案到结果集（深拷贝）
            res.add(new ArrayList<>(path));
            return;
        }

        // 🔄 遍历所有可能的分割点
        for (int i = index; i < s.length(); i++) {
            // 📋 截取子串 s[index...i]
            String subStr = s.substring(index, i + 1);
            // 🔍 判断子串是否是回文
            if (isPalindrome(subStr)) {
                // ➕ 选择当前子串
                path.add(subStr);
                // 🔄 递归搜索，下一个分割从 i + 1 开始
                backtrack(s, i + 1, path, res);
                // ➖ 撤销选择，回溯到上一个状态
                path.remove(path.size() - 1);
            }
        }
    }

    /**
     * 判断字符串是否是回文
     *
     * @param str 待判断的字符串
     * @return 是否是回文
     */
    private boolean isPalindrome(String str) {
        // 📋 双指针法判断回文
        int left = 0;
        int right = str.length() - 1;
        while (left < right) {
            // 🔍 如果对应位置字符不相等，不是回文
            if (str.charAt(left++) != str.charAt(right--)) {
                return false;
            }
        }
        // ✅ 是回文
        return true;
    }
}

package Hot100.动态规划;

import java.util.List;

/**
 * @author lxd
 **/
public class Hot086 {
    /**
     * <a href="https://leetcode.cn/problems/word-break/description/">139. 单词拆分</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s 。</p>
     * <p>注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。</p>
     *
     * <h3>💡 核心思路：动态规划</h3>
     * <ul>
     *   <li><b>基本思想</b>：递推判断字符串是否可拆分
     *     <ul>
     *       <li>定义 dp[i]：字符串前 i 个字符（s[0..i-1]）是否可拆分</li>
     *       <li>递推公式：dp[i] = dp[i - wLen] && s.substring(i-wLen, i).equals(word)</li>
     *       <li>初始状态：dp[0] = true（空字符串可拆分）</li>
     *       <li>外层遍历字符串长度 i，内层遍历字典中的单词</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用动态规划</b>：
     *     <ul>
     *       <li>问题具有最优子结构：前 i 个字符可拆分 = 前 i-wLen 个字符可拆分 + 最后 wLen 个字符在字典中</li>
     *       <li>避免重复计算子问题</li>
     *       <li>时间复杂度优于暴力递归</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n * m * k)，n 是字符串长度，m 是字典大小，k 是单词平均长度</li>
     *   <li><b>空间复杂度</b>：O(n)，使用 dp 数组</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：s = "leetcode", wordDict = ["leet", "code"]
     *
     * 初始化：dp = [true, false, false, false, false, false, false, false, false]
     *
     * i=1：
     *   word="leet": wLen=4 > 1, 跳过
     *   word="code": wLen=4 > 1, 跳过
     *   → dp[1] = false
     *
     * i=2：
     *   word="leet": wLen=4 > 2, 跳过
     *   word="code": wLen=4 > 2, 跳过
     *   → dp[2] = false
     *
     * i=3：
     *   word="leet": wLen=4 > 3, 跳过
     *   word="code": wLen=4 > 3, 跳过
     *   → dp[3] = false
     *
     * i=4：
     *   word="leet": wLen=4 <= 4
     *     sub = s[0..3] = "leet"
     *     dp[0] && "leet".equals("leet") = true
     *     dp[4] = true, break
     *   → dp[4] = true
     *
     * i=5：
     *   word="leet": wLen=4 <= 5
     *     sub = s[1..4] = "eetc" != "leet"
     *   word="code": wLen=4 <= 5
     *     sub = s[1..4] = "eetc" != "code"
     *   → dp[5] = false
     *
     * i=6：
     *   word="leet": wLen=4 <= 6
     *     sub = s[2..5] = "etco" != "leet"
     *   word="code": wLen=4 <= 6
     *     sub = s[2..5] = "etco" != "code"
     *   → dp[6] = false
     *
     * i=7：
     *   word="leet": wLen=4 <= 7
     *     sub = s[3..6] = "etco" != "leet"
     *   word="code": wLen=4 <= 7
     *     sub = s[3..6] = "tcod" != "code"
     *   → dp[7] = false
     *
     * i=8：
     *   word="leet": wLen=4 <= 8
     *     sub = s[4..7] = "code" != "leet"
     *   word="code": wLen=4 <= 8
     *     sub = s[4..7] = "code"
     *     dp[4] && "code".equals("code") = true
     *     dp[8] = true, break
     *   → dp[8] = true
     *
     * 结果：dp[8] = true ✅
     *
     * 示例2：s = "applepenapple", wordDict = ["apple", "pen"]
     *   dp[5] = true（"apple"）
     *   dp[8] = true（"apple"+"pen"）
     *   dp[13] = true（"apple"+"pen"+"apple"）
     *   return true ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ dp 数组的定义？
     *    - dp[i] 表示字符串前 i 个字符（s[0..i-1]）是否可拆分
     *    - i 从 0 开始，dp 数组大小为 len + 1
     *    - 最终答案为 dp[len]
     *
     * 2️⃣ 初始状态如何设置？
     *    - dp[0] = true（空字符串可以拆分，作为基准）
     *    - dp[1..len] = false（初始化为不可拆分）
     *    - 空字符串是递归的基准情况
     *
     * 3️⃣ 递推公式是什么？
     *    - dp[i] = dp[i - wLen] && s.substring(i-wLen, i).equals(word)
     *    - 如果前 i-wLen 个字符可拆分，且最后 wLen 个字符等于字典中的某个单词
     *    - 则前 i 个字符可拆分
     *    - 找到一个满足条件的单词即可，break 退出循环
     *
     * 4️⃣ 为什么要遍历字典中的每个单词？
     *    - 需要尝试用字典中的每个单词来匹配
     *    - 只要有一个单词能匹配，dp[i] 就为 true
     *    - 找到后可以 break，避免不必要的计算
     *
     * 5️⃣ 如何优化时间复杂度？
     *    - 当前方法：O(n * m * k)
     *    - 可以将 wordDict 转为 HashSet，contains 操作 O(1)
     *    - 优化后：O(n^2)（内层遍历 j 从 0 到 i，检查 s[j..i] 是否在字典中）
     *
     * 6️⃣ 边界情况如何处理？
     *    - s 为空字符串：dp[0] = true，返回 true
     *    - wordDict 为空：无法匹配任何单词，返回 false
     *    - s 长度为 1：检查该字符是否在字典中
     *
     * 7️⃣ 时间复杂度分析：
     *    - 外层循环：n 次（i 从 1 到 len）
     *    - 内层循环：m 次（遍历字典中的每个单词）
     *    - 每次比较：k 次（单词长度）
     *    - 总时间：O(n * m * k)
     *
     * 8️⃣ 完整流程总结：
     *
     *    len = s.length()
     *    dp = new boolean[len+1]
     *    dp[0] = true
     *
     *    for i = 1; i <= len; i++：
     *      for word in wordDict：
     *        wLen = word.length()
     *        if wLen <= i：
     *          sub = s.substring(i-wLen, i)
     *          if dp[i-wLen] && sub.equals(word)：
     *            dp[i] = true
     *            break
     *
     *    return dp[len]
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>dp 定义</b>：dp[i] 表示前 i 个字符是否可拆分</li>
     *   <li><b>初始状态</b>：dp[0] = true（空字符串）</li>
     *   <li><b>单词长度</b>：wLen <= i 时才进行匹配</li>
     *   <li><b>找到即 break</b>：找到一个匹配的单词就退出内层循环</li>
     *   <li><b>子串提取</b>：s.substring(i-wLen, i)</li>
     * </ul>
     *
     * @param s        目标字符串
     * @param wordDict 字符串字典
     * @return 是否可以利用字典中的单词拼接出 s
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        // 📏 获取字符串长度
        int len = s.length();

        // 📦 创建 dp 数组
        boolean[] dp = new boolean[len + 1];

        // 📋 初始状态：空字符串可以拆分
        dp[0] = true;

        // 🔄 外层循环：遍历字符串长度
        for (int i = 1; i <= len; i++) {
            // 🔄 内层循环：遍历字典中的每个单词
            for (String word : wordDict) {
                // 📏 获取单词长度
                int wLen = word.length();

                // 📌 只有单词长度 <= 当前长度时才能匹配
                if (wLen <= i) {
                    // 📊 提取子串 s[i-wLen..i-1]
                    String sub = s.substring(i - wLen, i);

                    // 📊 递推公式：前 i-wLen 个字符可拆分 && 子串等于单词
                    if (dp[i - wLen] && sub.equals(word)) {
                        dp[i] = true;
                        // ✅ 找到一个匹配的单词，退出内层循环
                        break;
                    }
                }
            }
        }

        // ✅ 返回整个字符串是否可拆分
        return dp[len];
    }
}

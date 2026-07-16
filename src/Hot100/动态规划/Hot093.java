package Hot100.动态规划;

/**
 * @author lxd
 **/
public class Hot093 {
    /**
     * <a href="https://leetcode.cn/problems/longest-palindromic-substring/description/">5. 最长回文子串</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个字符串 s，找到 s 中最长的回文子串。</p>
     * <p>如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。</p>
     *
     * <h3>💡 核心思路：中心扩展法</h3>
     * <ul>
     *   <li><b>基本思想</b>：从每个字符（或两个字符之间）向两边扩展，找到最长回文
     *     <ul>
     *       <li>遍历字符串中的每个位置</li>
     *       <li>以当前字符为中心，向两边扩展（奇数长度回文）</li>
     *       <li>以当前字符和下一个字符为中心，向两边扩展（偶数长度回文）</li>
     *       <li>记录最长回文的起始位置和长度</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用中心扩展法</b>：
     *     <ul>
     *       <li>回文具有对称性，从中心向两边扩展是自然的思路</li>
     *       <li>时间复杂度 O(n²)，空间复杂度 O(1)</li>
     *       <li>实现简单，易于理解</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n²)，每个字符最多扩展 O(n) 次</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常量空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：s = "babad"
     *
     * i=0, char='b'：
     *   expand(0,0)：left=0, right=0
     *     s[0]==s[0], left=-1, right=1 → len = 1-(-1)-1 = 1
     *   expand(0,1)：left=0, right=1
     *     s[0]='b' != s[1]='a' → len = 0
     *   maxLen=1, start=0
     *
     * i=1, char='a'：
     *   expand(1,1)：left=1, right=1
     *     s[1]==s[1], left=0, right=2, s[0]='b'==s[2]='b', left=-1, right=3
     *     len = 3-(-1)-1 = 3
     *   expand(1,2)：left=1, right=2
     *     s[1]='a' != s[2]='b' → len = 0
     *   maxLen=3, start=1-(3-1)/2 = 1-1 = 0
     *   最长回文：s[0..2] = "bab"
     *
     * i=2, char='b'：
     *   expand(2,2)：left=2, right=2
     *     s[2]==s[2], left=1, right=3, s[1]='a'==s[3]='a', left=0, right=4, s[0]='b'!=s[4]='d'
     *     len = 4-0-1 = 3
     *   expand(2,3)：left=2, right=3
     *     s[2]='b' != s[3]='a' → len = 0
     *   maxLen=3（不变）, start=2-(3-1)/2 = 2-1 = 1
     *   最长回文：s[1..3] = "aba"
     *
     * i=3, char='a'：
     *   expand(3,3)：len=1
     *   expand(3,4)：s[3]='a' != s[4]='d', len=0
     *   maxLen=3（不变）
     *
     * i=4, char='d'：
     *   expand(4,4)：len=1
     *   expand(4,5)：越界, len=0
     *   maxLen=3（不变）
     *
     * 结果：最长回文子串为 "bab" 或 "aba" ✅
     *
     * 示例2：s = "cbbd"
     *   i=1, char='b'：expand(1,2) → s[1]='b'==s[2]='b', left=0, right=3, s[0]='c'!=s[3]='d'
     *   len = 3-0-1 = 2
     *   maxLen=2, start=1-(2-1)/2 = 1-0 = 1
     *   最长回文：s[1..2] = "bb" ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么需要两种扩展方式？
     *    - 奇数长度回文：中心是一个字符，如 "aba"，中心是 'b'
     *    - 偶数长度回文：中心是两个字符之间，如 "abba"，中心是 'b' 和 'b' 之间
     *    - 两种情况都需要考虑，才能找到最长回文
     *
     * 2️⃣ expand 函数如何工作？
     *    - 参数 left 和 right 是初始中心位置
     *    - 当 s[left] == s[right] 时，继续向两边扩展
     *    - left--, right++
     *    - 当不相等或越界时停止
     *    - 返回回文长度：right - left - 1
     *
     * 3️⃣ 为什么返回 right - left - 1？
     *    - 最后一次循环时，s[left] != s[right]（或越界）
     *    - 有效回文的边界是 left+1 到 right-1
     *    - 长度 = (right-1) - (left+1) + 1 = right - left - 1
     *
     * 4️⃣ 如何计算起始位置？
     *    - start = i - (len - 1) / 2
     *    - i 是中心位置
     *    - (len - 1) / 2 是从中心向左偏移的距离
     *    - 例如：len=3, i=1 → start=1-(3-1)/2=0
     *    - 例如：len=2, i=1 → start=1-(2-1)/2=1
     *
     * 5️⃣ 为什么不用 DP 方法？
     *    - DP 方法时间复杂度也是 O(n²)
     *    - 但空间复杂度是 O(n²)
     *    - 中心扩展法空间复杂度 O(1)，更优
     *    - 而且实现更简单
     *
     * 6️⃣ 边界情况如何处理？
     *    - s 为空字符串：返回空
     *    - s 长度为 1：返回 s
     *    - s 长度为 2：判断两个字符是否相等
     *    - expand 函数中的越界判断：left >= 0 && right < s.length()
     *
     * 7️⃣ 时间复杂度分析：
     *    - 外层循环：n 次（遍历每个字符）
     *    - 每次调用 expand：最多 O(n) 次扩展
     *    - 总时间：O(n²)
     *
     * 8️⃣ 完整流程总结：
     *
     *    start = 0, maxLen = 0
     *
     *    for i in 0..n-1：
     *      len1 = expand(s, i, i)      // 奇数长度
     *      len2 = expand(s, i, i+1)    // 偶数长度
     *      len = max(len1, len2)
     *      if len > maxLen：
     *        maxLen = len
     *        start = i - (len - 1) / 2
     *
     *    return s.substring(start, start + maxLen)
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>两种扩展</b>：奇数长度和偶数长度都要考虑</li>
     *   <li><b>返回长度</b>：expand 返回 right - left - 1</li>
     *   <li><b>起始位置</b>：start = i - (len - 1) / 2</li>
     *   <li><b>越界判断</b>：left >= 0 && right < s.length()</li>
     *   <li><b>时间复杂度</b>：O(n²)，空间复杂度 O(1)</li>
     * </ul>
     *
     * @param s 输入字符串
     * @return s 中最长的回文子串
     */
    public String longestPalindrome(String s) {
        // 📊 最长回文子串的起始位置和长度
        int start = 0, maxLen = 0;

        // 🔄 遍历每个字符
        for (int i = 0; i < s.length(); i++) {
            // 📌 奇数长度回文：以当前字符为中心
            int len1 = expand(s, i, i);

            // 📌 偶数长度回文：以当前字符和下一个字符为中心
            int len2 = expand(s, i, i + 1);

            // 📊 取两种情况的最大值
            int len = Math.max(len1, len2);

            // 📊 更新最长回文子串
            if (len > maxLen) {
                maxLen = len;
                // 📌 计算起始位置
                start = i - (len - 1) / 2;
            }
        }

        // ✅ 返回最长回文子串
        return s.substring(start, start + maxLen);
    }

    /**
     * 中心扩展函数
     *
     * @param s     输入字符串
     * @param left  左指针
     * @param right 右指针
     * @return 以 (left, right) 为中心扩展得到的回文串长度
     */
    private int expand(String s, int left, int right) {
        // 🔄 向两边扩展，直到字符不相等或越界
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }

        // ✅ 返回回文串长度：right - left - 1
        return right - left - 1;
    }
}

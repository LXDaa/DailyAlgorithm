package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/reverse-words-in-a-string/description/">151. 反转字符串中的单词</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>给你一个字符串 s，请你反转字符串中单词的顺序。</p>
 * <p>单词是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的单词分隔开。</p>
 * <p>返回单词顺序颠倒且单词之间用单个空格连接的结果字符串。</p>
 * <p><b>注意</b>：输入字符串 s 中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。</p>
 *
 * <h3>💡 核心思路：三步翻转法</h3>
 * <ul>
 *   <li><b>Step 1</b>：移除多余空格（前导、尾随、中间多余空格）</li>
 *   <li><b>Step 2</b>：翻转整个字符串</li>
 *   <li><b>Step 3</b>：翻转每个单词，恢复单词内部顺序</li>
 * </ul>
 *
 * <h3>🔄 算法步骤详解</h3>
 * <ol>
 *   <li><b>去除空格</b>：
 *     <ul>
 *       <li>跳过前导空格和尾随空格</li>
 *       <li>遍历字符串，只保留单词间的单个空格</li>
 *     </ul>
 *   </li>
 *   <li><b>整体翻转</b>：将整个字符串翻转，此时单词顺序 reversed，但每个单词内部也 reversed</li>
 *   <li><b>逐词翻转</b>：识别每个单词的边界，将每个单词再次翻转，恢复单词内部的正确顺序</li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例：s = "  the sky  is blue  "
 *
 * 【Step 1】去除多余空格
 * 原始:   "  the sky  is blue  "
 * 处理后: "the sky is blue"
 *
 * 【Step 2】翻转整个字符串
 * "the sky is blue"
 *      ↓ 整体翻转
 * "eulb si yks eht"
 *
 * 【Step 3】翻转每个单词
 * 识别单词边界并逐个翻转:
 *
 * "eulb si yks eht"
 *  ^^^^              → 翻转 "eulb" → "blue"
 *       ^^           → 翻转 "si"   → "is"
 *          ^^^       → 翻转 "yks"  → "sky"
 *              ^^^   → 翻转 "eht"  → "the"
 *
 * 最终结果: "blue is sky the"
 * ✅ 完成！
 * </pre>
 *
 * <h3>🔍 关键细节说明</h3>
 * <pre>
 * 1️⃣ 去除空格的逻辑：
 *    - 使用双指针找到有效字符范围 [start, end]
 *    - 遍历时，只有当前字符不是空格，或前一个字符不是空格时才添加
 *    - 这样可以保证单词间只有一个空格
 *
 * 2️⃣ 翻转字符串的技巧：
 *    - 使用双指针从两端向中间交换字符
 *    - StringBuilder.setCharAt() 直接修改字符
 *
 * 3️⃣ 翻转每个单词：
 *    - 用 start 标记单词起始，end 寻找单词结束（遇到空格或结尾）
 *    - 对 [start, end-1] 区间进行翻转
 *    - 更新 start = end + 1，继续处理下一个单词
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>StringBuilder 操作</b>：使用 StringBuilder 而非 String，避免频繁创建对象</li>
 *   <li><b>边界条件</b>：注意 start 和 end 指针的边界判断，防止越界</li>
 *   <li><b>空格处理</b>：sb.charAt(sb.length() - 1) 需要确保 sb 非空</li>
 *   <li><b>时间复杂度</b>：O(n)，每个步骤都是线性扫描</li>
 *   <li><b>空间复杂度</b>：O(n)，StringBuilder 存储处理后的字符串</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260409 {
    public String reverseWords(String s) {
        // 🧹 Step 1: 去除多余空格（前导、尾随、中间多余空格）
        StringBuilder sb = removeSpace(s);

        // 🔄 Step 2: 翻转整个字符串
        reverseStr(sb, 0, sb.length() - 1);

        // 🔤 Step 3: 翻转每个单词，恢复单词内部顺序
        reverseEachWord(sb);

        return sb.toString();
    }

    /**
     * 🧹 去除多余空格：前导、尾随、单词间多余空格
     *
     * @param s 原始字符串
     * @return 处理后的 StringBuilder，单词间仅有一个空格
     */
    private StringBuilder removeSpace(String s) {
        int start = 0, end = s.length() - 1;

        // ⬅️ 跳过前导空格
        while (s.charAt(start) == ' ') {
            start++;
        }

        // ➡️ 跳过尾随空格
        while (s.charAt(end) == ' ') {
            end--;
        }

        StringBuilder sb = new StringBuilder();

        // 📝 遍历有效区间，保留单词间的单个空格
        while (start <= end) {
            char c = s.charAt(start);
            // ✅ 当前字符不是空格，或前一个字符不是空格时才添加
            if (c != ' ' || sb.charAt(sb.length() - 1) != ' ') {
                sb.append(c);
            }
            start++;
        }
        return sb;
    }

    /**
     * 🔄 翻转 StringBuilder 中指定区间的字符
     *
     * @param sb    待翻转的 StringBuilder
     * @param start 起始索引
     * @param end   结束索引
     */
    private void reverseStr(StringBuilder sb, int start, int end) {
        // 🔀 双指针从两端向中间交换
        while (start < end) {
            char temp = sb.charAt(start);
            sb.setCharAt(start++, sb.charAt(end));
            sb.setCharAt(end--, temp);
        }
    }

    /**
     * 🔤 翻转 StringBuilder 中的每个单词
     *
     * @param sb 已整体翻转的 StringBuilder
     */
    private void reverseEachWord(StringBuilder sb) {
        int start = 0, end = 1, n = sb.length();

        // 📍 遍历所有单词
        while (start < n) {
            // 🔍 找到当前单词的结束位置（遇到空格或到达末尾）
            while (end < n && sb.charAt(end) != ' ') {
                end++;
            }

            // 🔄 翻转当前单词 [start, end-1]
            reverseStr(sb, start, end - 1);

            // ⏭️ 移动到下一个单词
            start = end + 1;
            end = start + 1;
        }
    }
}

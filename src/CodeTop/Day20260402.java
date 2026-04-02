package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/minimum-window-substring/description/">76. 最小覆盖子串</a>
 * <p>
 * 给定两个字符串 s 和 t，长度分别是 m 和 n，返回 s 中的 最短窗口 子串，使得该子串包含 t 中的每一个字符（包括重复字符）。如果没有这样的子串，返回空字符串 ""。
 * <p>
 * 测试用例保证答案唯一。
 *
 * <h2>解题思路</h2>
 * <h3>1. 题目分析</h3>
 * <ul>
 *     <li><strong>滑动窗口经典题</strong>：使用双指针维护一个可变窗口</li>
 *     <li><strong>核心思想</strong>：右指针扩展窗口，左指针收缩窗口，找到满足条件的最小子串</li>
 *     <li><strong>关键问题</strong>：如何判断窗口已包含 t 的所有字符（包括重复）</li>
 * </ul>
 * <p>
 * <h3>2. 算法步骤</h3>
 * <h4>(1) 预处理 t 的字符频次</h4>
 * <ul>
 *     <li>使用数组 mapT 统计 t 中每个字符的出现次数</li>
 *     <li>计算 required：t 中不同字符的种类数</li>
 * </ul>
 * <p>
 * <h4>(2) 滑动窗口初始化</h4>
 * <ul>
 *     <li>left = 0, right = 0：窗口的左右边界</li>
 *     <li>formed = 0：当前窗口中满足频次要求的字符种类数</li>
 *     <li>mapW：记录当前窗口中每个字符的频次</li>
 *     <li>minLen, minStart：记录最小窗口的长度和起始位置</li>
 * </ul>
 * <p>
 * <h4>(3) 扩展窗口（right 右移）</h4>
 * <ul>
 *     <li>将 right 位置的字符加入窗口</li>
 *     <li>如果该字符在窗口中的数量达到 t 中的要求，formed++</li>
 *     <li>当 formed == required 时，说明窗口已包含所有必需字符</li>
 * </ul>
 * <p>
 * <h4>(4) 收缩窗口（left 右移）</h4>
 * <ul>
 *     <li>更新最小窗口信息（长度和起始位置）</li>
 *     <li>移除 left 位置的字符</li>
 *     <li>如果移除后某字符数量不足，formed--，停止收缩</li>
 * </ul>
 * <p>
 * <h3>3. 算法示意图</h3>
 * <pre>
 * 示例：s = "ADOBECODEBANC", t = "ABC"
 *
 * 预处理 t:
 * mapT: A=1, B=1, C=1
 * required = 3 (3 种不同字符)
 *
 * 滑动过程:
 *
 * 1. right 扩展到 'C' (索引 5):
 *    窗口："ADOBEC"
 *    mapW: A=1, D=1, O=1, B=1, E=1, C=1
 *    formed = 3 (A,B,C 都满足要求) ✅
 *
 *    开始收缩 left:
 *    - 移除 'A' → formed=2 ❌ 停止
 *    最小窗口："ADOBEC", len=6
 *
 * 2. right 继续扩展...
 *    窗口："ADOBECODEBA"
 *    formed = 3 ✅
 *
 *    收缩 left:
 *    - 移除 'D' → 仍满足
 *    - 移除 'O' → 仍满足
 *    - 移除 'B' → 仍满足 (还有一个 B)
 *    - 移除 'E' → 仍满足
 *    - 移除 'C' → formed=2 ❌ 停止
 *    最小窗口："CODEBA", len=6
 *
 * 3. right 继续扩展...
 *    窗口："ADOBECODEBANC"
 *    formed = 3 ✅
 *
 *    收缩 left:
 *    - 连续移除直到 'B'
 *    - 移除 'B' → formed=2 ❌ 停止
 *    最小窗口："BANC", len=4 🎯
 *
 * 最终结果："BANC"
 * </pre>
 * <p>
 * <h3>4. 复杂度分析</h3>
 * <ul>
 *     <li><strong>时间复杂度</strong>：O(m)，其中 m 是 s 的长度，左右指针各遍历一次</li>
 *     <li><strong>空间复杂度</strong>：O(1)，使用固定大小的数组（128 个 ASCII 字符）</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260402 {
    public String minWindow(String s, String t) {
        // 🛑 边界检查：空串或 s 比 t 短，直接返回空串
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        // 📊 mapT：统计 t 中每个字符的频次
        // 📊 mapW：统计当前窗口中每个字符的频次
        int[] mapT = new int[128];
        int[] mapW = new int[128];

        // 🔢 统计 t 中每个字符的出现次数
        for (char c : t.toCharArray()) {
            mapT[c]++;
        }

        // 🎯 计算 required：t 中不同字符的种类数
        int required = 0;
        for (int count : mapT) {
            if (count > 0) {
                required++;
            }
        }

        // 👆 滑动窗口指针
        int left = 0, right = 0;
        // ✅ formed：当前窗口中满足频次要求的字符种类数
        int formed = 0;
        // 📏 记录最小窗口的信息
        int minStart = 0, minLen = Integer.MAX_VALUE;

        // 🔄 右指针扩展窗口
        while (right < s.length()) {
            // ➕ 将 right 位置的字符加入窗口
            char c = s.charAt(right);
            mapW[c]++;

            // ⚠️ 如果该字符在窗口中的数量刚好达到 t 中的要求
            if (mapW[c] > 0 && mapW[c] == mapT[c]) {
                formed++;
            }

            // 🔄 左指针收缩窗口（当窗口已包含所有必需字符时）
            while (left <= right && formed == required) {
                c = s.charAt(left);

                // 📏 更新最小窗口信息
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minStart = left;
                }

                // ➖ 移除 left 位置的字符
                mapW[c]--;

                // ⚠️ 如果移除后该字符数量不足，formed 减 1
                if (mapT[c] > 0 && mapW[c] < mapT[c]) {
                    formed--;
                }

                // 👉 左指针右移
                left++;
            }
            // 👉 右指针右移
            right++;
        }

        // 🎯 返回结果：如果 minLen 未更新，说明没有找到，返回空串
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }

}

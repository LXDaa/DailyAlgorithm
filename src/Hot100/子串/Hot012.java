package Hot100.子串;

/**
 * @author lxd
 **/
public class Hot012 {
    /**
     * <a href="https://leetcode.cn/problems/minimum-window-substring/description/">76. 最小覆盖子串</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。</p>
     *
     * <h3>💡 核心思路：滑动窗口 + 哈希表</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用滑动窗口技术，维护一个能够覆盖 t 中所有字符的最小窗口
     *     <ul>
     *       <li>用数组 map 记录 t 中每个字符的需求量（正数表示还需要，负数表示盈余）</li>
     *       <li>右指针不断扩展窗口，直到窗口包含 t 的所有字符</li>
     *       <li>左指针收缩窗口，寻找最小的满足条件的子串</li>
     *       <li>用 count 记录还需要匹配的字符总数</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(|s|)，左右指针各遍历一次字符串</li>
     *   <li><b>空间复杂度</b>：O(1)，数组大小固定为 128（ASCII 字符集）</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：s = "ADOBECODEBANC", t = "ABC"
     *
     * 初始化：map = [A:1, B:1, C:1], left=0, count=3, minLen=∞, start=0
     *
     * 【right=0】s[0] = 'A'
     *   - map['A'] > 0，有效匹配，count-- → count=2
     *   - map['A']-- → map['A']=0
     *   - count ≠ 0，继续扩展
     *
     * 【right=1】s[1] = 'D'
     *   - map['D'] = 0，无效匹配
     *   - map['D']-- → map['D']=-1（盈余）
     *
     * 【right=2】s[2] = 'O'
     *   - map['O'] = 0，无效匹配
     *   - map['O']-- → map['O']=-1
     *
     * 【right=3】s[3] = 'B'
     *   - map['B'] > 0，有效匹配，count-- → count=1
     *   - map['B']-- → map['B']=0
     *
     * 【right=4】s[4] = 'E'
     *   - map['E'] = 0，无效匹配
     *   - map['E']-- → map['E']=-1
     *
     * 【right=5】s[5] = 'C'
     *   - map['C'] > 0，有效匹配，count-- → count=0 ✅
     *   - map['C']-- → map['C']=0
     *   - count == 0，找到第一个覆盖子串 "ADOBEC"
     *
     *   开始收缩左边界：
     *   - 当前窗口长度 = 6，更新 minLen=6, start=0
     *   - leftChar = 'A'，map['A']++ → map['A']=1
     *   - map['A'] > 0，count++ → count=1
     *   - left++ → left=1
     *   - count ≠ 0，停止收缩
     *
     * 【继续扩展...】
     *   - right=6~9: 'O','D','E','B'，都是盈余字符
     *   - right=10: 'A'，map['A']>0，count-- → count=0 ✅
     *   - 当前窗口 "CODEBA"，长度=6，不更新
     *
     *   开始收缩左边界：
     *   - left=1~4: 'D','O','B','E'，都是盈余，可以移除
     *   - left=5: 'C'，map['C']++ → map['C']=1
     *   - map['C'] > 0，count++ → count=1
     *   - left++ → left=6
     *   - 停止收缩
     *
     * 【继续扩展...】
     *   - right=11: 'N'，盈余
     *   - right=12: 'C'，map['C']>0，count-- → count=0 ✅
     *   - 当前窗口 "BANC"，长度=4 < minLen=6，更新 minLen=4, start=9
     *
     *   开始收缩左边界：
     *   - left=6~8: 'O','D','E'，都是盈余
     *   - left=9: 'B'，map['B']++ → map['B']=1
     *   - map['B'] > 0，count++ → count=1
     *   - left++ → left=10
     *   - 停止收缩
     *
     * 最终结果：s.substring(9, 13) = "BANC" ✅
     *
     * 图示：
     *   s = A D O B E C O D E B A N C
     *                   ↑           ↑
     *                 start      start+minLen
     *   最小子串："BANC"
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用数组而不是 HashMap？
     *    - 字符集是 ASCII，范围固定为 0-127
     *    - 数组访问速度更快，空间更紧凑
     *    - 不需要处理哈希冲突
     *
     * 2️⃣ map 数组的含义？
     *    - map[c] > 0：字符 c 还需要 map[c] 个
     *    - map[c] = 0：字符 c 刚好满足需求
     *    - map[c] < 0：字符 c 盈余 |map[c]| 个
     *
     * 3️⃣ count 的作用？
     *    - 记录还需要匹配的字符总数
     *    - count == 0 表示窗口已覆盖 t 的所有字符
     *    - 只有当 map[c] > 0 时才减少 count（真正需要的字符）
     *
     * 4️⃣ 为什么先判断 map[c] > 0 再 map[c]--？
     *    - 确保只有在真正需要该字符时才减少 count
     *    - 如果 map[c] <= 0，说明已经满足或盈余，不需要再计数
     *
     * 5️⃣ 左指针收缩的逻辑？
     *    - map[leftChar]++：归还字符，需求量增加
     *    - 如果 map[leftChar] > 0：说明窗口不再满足条件
     *    - 此时 count++，退出收缩循环
     *
     * 6️⃣ 时间复杂度：O(|s|)
     *    - 右指针从 0 到 |s|-1，遍历一次
     *    - 左指针从 0 到 |s|-1，最多遍历一次
     *    - 每个字符最多被访问两次（进入窗口和离开窗口）
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>边界检查</b>：处理 null 字符串和 s.length() < t.length() 的情况</li>
     *   <li><b>初始化</b>：minLen 初始化为 Integer.MAX_VALUE，用于判断是否找到解</li>
     *   <li><b>返回值</b>：如果 minLen 未更新，返回空字符串</li>
     *   <li><b>字符集</b>：假设是 ASCII 字符，数组大小为 128</li>
     *   <li><b>滑动窗口</b>：先扩展右边界，再收缩左边界，保证找到最小窗口</li>
     * </ul>
     *
     * @param s 源字符串
     * @param t 目标字符串
     * @return 最小覆盖子串，不存在则返回空字符串
     */
    public String minWindow(String s, String t) {
        // 🛡️ 边界检查：处理 null 或长度不足的情况
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        // 📋 创建字符需求映射表（ASCII 字符集）
        int[] map = new int[128];
        // 记录 t 中每个字符的需求量
        for (char c : t.toCharArray()) {
            map[c]++;
        }

        // 🔧 初始化变量
        int left = 0, count = t.length(), minLen = Integer.MAX_VALUE, start = 0;

        // 🔄 右指针扩展窗口
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            // 如果该字符有需求（map[c] > 0），说明有效匹配了一个，待匹配总数减一
            if (map[c] > 0) {
                count--;
            }
            // 该字符的需求量减一（变为负数代表盈余）
            map[c]--;

            // 当所有字符都匹配完毕，尝试收缩左边界
            while (count == 0) {
                // 📏 更新最小窗口
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    start = left;
                }

                char leftChar = s.charAt(left);
                // 左指针右移，归还字符的需求量
                map[leftChar]++;
                // 如果归还后需求量大于 0，说明窗口不再满足条件，待匹配总数加一
                if (map[leftChar] > 0) {
                    count++;
                }
                left++;
            }
        }
        // ✅ 返回结果：如果 minLen 未更新则返回空字符串
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
}

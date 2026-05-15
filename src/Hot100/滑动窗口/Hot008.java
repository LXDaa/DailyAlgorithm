package Hot100.滑动窗口;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lxd
 **/
public class Hot008 {
    /**
     * <a href="https://leetcode.cn/problems/longest-substring-without-repeating-characters/description/">3. 无重复字符的最长子串</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个字符串 s ，请你找出其中不含有重复字符的 <b>最长子串</b> 的长度。</p>
     *
     * <h3>💡 核心思路：滑动窗口</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用滑动窗口维护一个不含重复字符的子串
     *     <ul>
     *       <li>右指针 j 不断扩展窗口，将字符加入集合</li>
     *       <li>当遇到重复字符时，左指针 i 右移，从集合中移除字符</li>
     *       <li>记录窗口的最大长度</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个字符最多被访问两次</li>
     *   <li><b>空间复杂度</b>：O(min(m, n))，m 是字符集大小</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：s = "abcabcbb"
     *
     * 初始化：i=0, j=0, ans=0, set={}
     *
     * 【i=0】
     *   - 不需要移除字符
     *   - 扩展窗口：j=0,1,2,3
     *     - j=0: set={a}, j++
     *     - j=1: set={a,b}, j++
     *     - j=2: set={a,b,c}, j++
     *     - j=3: 'a' 已在 set 中，停止
     *   - 窗口长度：3 - 0 = 3
     *   - ans = 3
     *
     * 【i=1】
     *   - 移除 s[0] = 'a'，set={b,c}
     *   - 扩展窗口：j=3
     *     - j=3: set={b,c,a}, j++
     *     - j=4: 'b' 已在 set 中，停止
     *   - 窗口长度：4 - 1 = 3
     *   - ans = 3
     *
     * 【i=2】
     *   - 移除 s[1] = 'b'，set={c,a}
     *   - 扩展窗口：j=4
     *     - j=4: set={c,a,b}, j++
     *     - j=5: 'c' 已在 set 中，停止
     *   - 窗口长度：5 - 2 = 3
     *   - ans = 3
     *
     * 【i=3】
     *   - 移除 s[2] = 'c'，set={a,b}
     *   - 扩展窗口：j=5
     *     - j=5: set={a,b,c}, j++
     *     - j=6: 'b' 已在 set 中，停止
     *   - 窗口长度：6 - 3 = 3
     *   - ans = 3
     *
     * ... 继续直到 i 遍历完整个字符串
     *
     * 最终结果：ans = 3 ✅ （子串 "abc"）
     *
     * 窗口移动示意：
     *   a b c a b c b b
     *   ↑     ↑
     *   i     j
     *   窗口：[i, j) = [0, 3)，长度 = 3
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用滑动窗口？
     *    - 需要找连续子串，滑动窗口天然适合
     *    - 避免暴力枚举所有子串 O(n²)
     *    - 通过维护窗口状态，实现 O(n) 时间复杂度
     *
     * 2️⃣ 窗口的扩张和收缩？
     *    - 扩张：右指针 j 向右移动，添加新字符到集合
     *    - 收缩：左指针 i 向右移动，从集合中移除字符
     *    - 保证窗口内始终没有重复字符
     *
     * 3️⃣ 为什么 i > 0 时才移除？
     *    - i=0 时是第一次，不需要移除
     *    - i>0 时，左边界右移，需要将前一个字符从集合中移除
     *    - 保持窗口 [i, j) 的有效性
     *
     * 4️⃣ 窗口长度的计算？
     *    - 窗口是左闭右开区间 [i, j)
     *    - 长度 = j - i
     *    - j 指向下一个要加入的位置，不在窗口内
     *
     * 5️⃣ 时间复杂度分析：
     *    - 外层循环：i 从 0 到 n-1，共 n 次
     *    - 内层 while：j 最多移动 n 次（不会回退）
     *    - 总体：O(n) + O(n) = O(n)
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>窗口区间</b>：左闭右开 [i, j)，j 指向下一个位置</li>
     *   <li><b>去重判断</b>：用 HashSet 快速判断字符是否重复</li>
     *   <li><b>边界条件</b>：空字符串返回 0，单字符返回 1</li>
     *   <li><b>字符集</b>：ASCII 字符集大小为 128，Unicode 更大</li>
     * </ul>
     *
     * @param s 输入字符串
     * @return 不含重复字符的最长子串长度
     */
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;

        // 📋 创建 HashSet 存储当前窗口中的字符
        Set<Character> set = new HashSet<>();

        // 🔄 滑动窗口：i 是左边界，j 是右边界
        for (int i = 0, j = 0; i < n; i++) {
            // 🗑️ 左边界右移，移除前一个字符
            if (i > 0) {
                set.remove(s.charAt(i - 1));
            }

            // ➕ 右边界扩展：添加不重复的字符
            while (j < n && !set.contains(s.charAt(j))) {
                set.add(s.charAt(j));
                j++;
            }

            // 📊 更新最长子串长度
            ans = Math.max(ans, j - i);
        }

        return ans;
    }
}


package Hot100.滑动窗口;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot009 {
    /**
     * <a href="https://leetcode.cn/problems/find-all-anagrams-in-a-string/description/">438. 找到字符串中所有字母异位词</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定两个字符串 s 和 p，找到 s 中所有 p 的 <b>异位词</b> 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。</p>
     * <p><b>异位词</b> 指由相同字母重排列形成的字符串（包括相同的字符串）。</p>
     *
     * <h3>💡 核心思路：滑动窗口 + 字符计数</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用固定大小的滑动窗口（窗口大小 = p 的长度），通过字符计数判断是否为异位词
     *     <ul>
     *       <li>用数组 cnt 记录 p 中每个字符的出现次数</li>
     *       <li>右指针扩展窗口时，将对应字符计数减 1</li>
     *       <li>如果某个字符计数变为负数，说明窗口中该字符过多，需要收缩左边界</li>
     *       <li>当窗口大小等于 p 的长度时，找到一个异位词</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个字符最多被访问两次</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用长度为 26 的数组</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：s = "cbaebabacd", p = "abc"
     *
     * 第一步：统计 p 的字符计数
     * cnt = [1, 1, 1, 0, 0, ..., 0]  （a:1, b:1, c:1）
     *
     * 第二步：滑动窗口遍历 s
     *
     * 【right=0】c
     *   - cnt['c']-- → cnt = [1, 1, 0, ...]
     *   - cnt['c'] >= 0，不需要收缩
     *   - 窗口大小：1 != 3
     *
     * 【right=1】b
     *   - cnt['b']-- → cnt = [1, 0, 0, ...]
     *   - cnt['b'] >= 0，不需要收缩
     *   - 窗口大小：2 != 3
     *
     * 【right=2】a
     *   - cnt['a']-- → cnt = [0, 0, 0, ...]
     *   - cnt['a'] >= 0，不需要收缩
     *   - 窗口大小：3 == 3 ✅
     *   - 找到异位词，起始索引 left=0
     *   - ans = [0]
     *
     * 【right=3】e
     *   - cnt['e']-- → cnt = [0, 0, 0, 0, -1, ...]
     *   - cnt['e'] < 0，需要收缩
     *   - left=0: cnt['c']++ → cnt = [0, 0, 1, 0, -1, ...], left=1
     *   - cnt['e'] 仍 < 0，继续收缩
     *   - left=1: cnt['b']++ → cnt = [0, 1, 1, 0, -1, ...], left=2
     *   - cnt['e'] 仍 < 0，继续收缩
     *   - left=2: cnt['a']++ → cnt = [1, 1, 1, 0, -1, ...], left=3
     *   - cnt['e'] 仍 < 0，继续收缩
     *   - left=3: cnt['e']++ → cnt = [1, 1, 1, 0, 0, ...], left=4
     *   - cnt['e'] >= 0，停止收缩
     *   - 窗口大小：0 != 3
     *
     * ... 继续直到 right 遍历完整个字符串
     *
     * 最终结果：[0, 6] ✅
     *
     * 窗口移动示意：
     *   c b a e b a b a c d
     *   ↑     ↑
     *   left  right
     *   窗口大小固定为 3
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用字符计数数组？
     *    - p 只包含小写字母，可以用长度为 26 的数组
     *    - 比 HashMap 更高效，O(1) 访问
     *    - cnt[i] 表示字符 i 还需要多少个
     *
     * 2️⃣ 为什么要减 1 和加 1？
     *    - 右指针扩展：cnt[c]--，表示消耗了一个字符 c
     *    - 左指针收缩：cnt[c]++，表示释放了一个字符 c
     *    - cnt[c] < 0 表示窗口中字符 c 太多了
     *
     * 3️⃣ 如何判断是异位词？
     *    - 当窗口大小等于 p 的长度时
     *    - 且所有字符计数都 >= 0（没有多余的字符）
     *    - 此时窗口中的字符恰好与 p 相同
     *
     * 4️⃣ 滑动窗口的大小？
     *    - 窗口大小不固定，但最大不超过 p 的长度
     *    - 当窗口中有过多字符时，会收缩到合适大小
     *    - 只有窗口大小恰好等于 pLen 时才记录答案
     *
     * 5️⃣ 时间复杂度分析：
     *    - 初始化 cnt：O(pLen)
     *    - 滑动窗口：right 从 0 到 n-1，共 n 次
     *    - 内层 while：left 最多移动 n 次（不会回退）
     *    - 总体：O(n + pLen) = O(n)
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>边界条件</b>：s 的长度小于 p 时直接返回空列表</li>
     *   <li><b>字符范围</b>：假设只包含小写字母 a-z</li>
     *   <li><b>计数含义</b>：cnt[c] 表示还需要的字符 c 的数量</li>
     *   <li><b>窗口收缩</b>：while 循环确保窗口中没有多余字符</li>
     * </ul>
     *
     * @param s 主字符串
     * @param p 模式字符串
     * @return 所有异位词子串的起始索引列表
     */
    public List<Integer> findAnagrams(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        List<Integer> ans = new ArrayList();

        // ✂️ 边界条件：s 的长度小于 p，不可能有异位词
        if (sLen < pLen) {
            return ans;
        }

        // 📊 统计 p 中每个字符的出现次数
        int[] cnt = new int[26];
        for (char c : p.toCharArray()) {
            cnt[c - 'a']++;
        }

        // 🔄 滑动窗口：left 是左边界，right 是右边界
        for (int left = 0, right = 0; right < sLen; right++) {
            // ➖ 右边界扩展：消耗一个字符
            int c = s.charAt(right) - 'a';
            cnt[c]--;

            // 🔀 如果字符过多，收缩左边界
            while (cnt[c] < 0) {
                cnt[s.charAt(left) - 'a']++;
                left++;
            }

            // ✅ 如果窗口大小等于 p 的长度，找到异位词
            if (right - left + 1 == pLen) {
                ans.add(left);
            }
        }

        return ans;
    }
}


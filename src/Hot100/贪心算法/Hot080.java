package Hot100.贪心算法;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot080 {
    /**
     * <a href="https://leetcode.cn/problems/partition-labels/description/">763. 划分字母区间</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。</p>
     * <p>返回一个表示每个字符串片段的长度的列表。</p>
     *
     * <h3>💡 核心思路：贪心算法</h3>
     * <ul>
     *   <li><b>基本思想</b>：遍历过程中维护当前片段的起始和结束位置
     *     <ul>
     *       <li>先统计每个字符最后出现的位置</li>
     *       <li>维护 begin：当前片段的起始位置</li>
     *       <li>维护 end：当前片段的结束位置</li>
     *       <li>遍历每个字符，更新 end 为当前字符最后出现位置和 end 的最大值</li>
     *       <li>当 end == i 时，找到一个片段，记录长度，更新 begin</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用贪心</b>：
     *     <ul>
     *       <li>每次扩展到当前片段中所有字符的最远位置</li>
     *       <li>保证同一字符只出现在一个片段中</li>
     *       <li>最大化片段数量</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，遍历两次数组</li>
     *   <li><b>空间复杂度</b>：O(1)，使用固定大小的数组</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：s = "ababcbacadefegdehijhklij"
     *
     * 步骤1：统计每个字符最后出现的位置
     *   last['a'] = 8, last['b'] = 5, last['c'] = 7
     *   last['d'] = 14, last['e'] = 15, last['f'] = 11
     *   last['g'] = 13, last['h'] = 19, last['i'] = 22
     *   last['j'] = 23, last['k'] = 20, last['l'] = 21
     *
     * 步骤2：遍历字符串，划分片段
     *
     * 初始化：begin=0, end=0, res=[]
     *
     * i=0, char='a'：
     *   end = max(0, last['a']=8) = 8
     *   end != i → 继续
     *
     * i=1, char='b'：
     *   end = max(8, last['b']=5) = 8
     *   end != i → 继续
     *
     * i=2, char='a'：
     *   end = max(8, last['a']=8) = 8
     *   end != i → 继续
     *
     * i=3, char='b'：
     *   end = max(8, last['b']=5) = 8
     *   end != i → 继续
     *
     * i=4, char='c'：
     *   end = max(8, last['c']=7) = 8
     *   end != i → 继续
     *
     * i=5, char='b'：
     *   end = max(8, last['b']=5) = 8
     *   end != i → 继续
     *
     * i=6, char='a'：
     *   end = max(8, last['a']=8) = 8
     *   end != i → 继续
     *
     * i=7, char='c'：
     *   end = max(8, last['c']=7) = 8
     *   end != i → 继续
     *
     * i=8, char='a'：
     *   end = max(8, last['a']=8) = 8
     *   end == i ✅
     *   res.add(8 - 0 + 1) = 9
     *   begin = 9
     *   → res = [9]
     *
     * ...继续遍历后续字符...
     *
     * 最终结果：[9, 7, 8] ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 如何统计字符最后出现位置？
     *    - 使用长度为 26 的数组（26个小写字母）
     *    - last[c - 'a'] = i
     *    - 遍历字符串一次，O(n) 时间
     *
     * 2️⃣ begin 和 end 分别代表什么？
     *    - begin：当前片段的起始索引
     *    - end：当前片段的结束索引（当前片段所有字符最远出现位置）
     *    - 片段长度 = end - begin + 1
     *
     * 3️⃣ end 如何更新？
     *    - end = max(end, last[c[i] - 'a'])
     *    - 取当前字符最后出现位置和当前 end 的最大值
     *    - 保证当前片段包含所有已出现字符
     *
     * 4️⃣ 什么时候找到一个片段？
     *    - 当 end == i 时
     *    - 表示已经到达当前片段的最远位置
     *    - 当前片段内所有字符都不会出现在后面
     *
     * 5️⃣ 如何记录片段长度？
     *    - res.add(end - begin + 1)
     *    - 更新 begin = i + 1（下一个片段的起始）
     *    - 继续遍历
     *
     * 6️⃣ 时间复杂度分析：
     *    - 第一次遍历：统计最后出现位置，O(n)
     *    - 第二次遍历：划分片段，O(n)
     *    - 总时间：O(n)
     *
     * 7️⃣ 空间复杂度分析：
     *    - last 数组：固定大小 26
     *    - res 列表：存储结果，不算在复杂度中
     *    - 空间复杂度：O(1)
     *
     * 8️⃣ 完整流程总结：
     *
     *    last = [0] * 26
     *    for i in 0..n-1：
     *      last[c[i] - 'a'] = i
     *
     *    res = []
     *    begin = 0, end = 0
     *
     *    for i in 0..n-1：
     *      end = max(end, last[c[i] - 'a'])
     *      if end == i：
     *        res.add(end - begin + 1)
     *        begin = i + 1
     *
     *    return res
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>字符范围</b>：题目保证是小写字母</li>
     *   <li><b>数组大小</b>：last 数组长度为 26</li>
     *   <li><b>片段划分</b>：end == i 时记录片段</li>
     *   <li><b>begin 更新</b>：记录片段后更新 begin = i + 1</li>
     *   <li><b>片段长度</b>：end - begin + 1</li>
     * </ul>
     *
     * @param s 由小写字母组成的字符串
     * @return 每个字符串片段的长度列表
     */
    public List<Integer> partitionLabels(String s) {
        // 📊 将字符串转换为字符数组
        char[] c = s.toCharArray();

        // 📊 记录每个字符最后出现的位置
        int[] last = new int[26];

        // 📏 字符串长度
        int n = c.length;

        // 🔄 第一次遍历：统计每个字符最后出现的位置
        for (int i = 0; i < n; i++) {
            last[c[i] - 'a'] = i;
        }

        // 📋 结果列表
        List<Integer> res = new ArrayList();

        // 📍 当前片段的起始和结束位置
        int begin = 0, end = 0;

        // 🔄 第二次遍历：划分片段
        for (int i = 0; i < n; i++) {
            // 📍 更新当前片段的结束位置
            end = Math.max(end, last[c[i] - 'a']);

            // ✅ 到达当前片段的最远位置，找到一个片段
            if (end == i) {
                // 📝 记录片段长度
                res.add(end - begin + 1);
                // 📍 更新下一个片段的起始位置
                begin = i + 1;
            }
        }

        // ✅ 返回结果列表
        return res;
    }
}

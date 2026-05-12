package Hot100.哈希;

import java.util.*;

/**
 * @author lxd
 **/
public class Hot002 {
    /**
     * <a href="https://leetcode.cn/problems/group-anagrams/description/">49. 字母异位词分组</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个字符串数组，请你将 <b>字母异位词</b> 组合在一起。可以按任意顺序返回结果列表。</p>
     * <p><b>字母异位词</b> 是由重新排列源单词的所有字母得到的一个新单词。</p>
     *
     * <h3>💡 核心思路：排序 + 哈希表</h3>
     * <ul>
     *   <li><b>基本思想</b>：对每个字符串的字符进行排序，字母异位词排序后会得到相同的字符串
     *     <ul>
     *       <li>将排序后的字符串作为 key，原始字符串列表作为 value</li>
     *       <li>遍历所有字符串，将它们归类到对应的异位词组中</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n * k * log(k))，n 是字符串数量，k 是字符串最大长度</li>
     *   <li><b>空间复杂度</b>：O(n * k)，存储所有字符串</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     *
     * 初始化：map = {}
     *
     * 【处理 "eat"】
     *   - 排序后："aet"
     *   - map 中没有 "aet"
     *   - 存入：map = {"aet": ["eat"]}
     *
     * 【处理 "tea"】
     *   - 排序后："aet"
     *   - map 中有 "aet"
     *   - 添加：map = {"aet": ["eat", "tea"]}
     *
     * 【处理 "tan"】
     *   - 排序后："ant"
     *   - map 中没有 "ant"
     *   - 存入：map = {"aet": ["eat", "tea"], "ant": ["tan"]}
     *
     * 【处理 "ate"】
     *   - 排序后："aet"
     *   - map 中有 "aet"
     *   - 添加：map = {"aet": ["eat", "tea", "ate"], "ant": ["tan"]}
     *
     * 【处理 "nat"】
     *   - 排序后："ant"
     *   - map 中有 "ant"
     *   - 添加：map = {"aet": ["eat", "tea", "ate"], "ant": ["tan", "nat"]}
     *
     * 【处理 "bat"】
     *   - 排序后："abt"
     *   - map 中没有 "abt"
     *   - 存入：map = {"aet": ["eat", "tea", "ate"], "ant": ["tan", "nat"], "abt": ["bat"]}
     *
     * 最终结果：[["eat","tea","ate"], ["tan","nat"], ["bat"]] ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用排序作为 key？
     *    - 字母异位词排序后一定相同
     *    - 例如："eat", "tea", "ate" 排序后都是 "aet"
     *
     * 2️⃣ 哈希表的设计？
     *    - key：排序后的字符串（唯一标识一组异位词）
     *    - value：原始字符串列表（存储所有异位词）
     *
     * 3️⃣ getOrDefault 的作用？
     *    - 如果 key 不存在，返回新的 ArrayList
     *    - 避免每次都要判断 key 是否存在
     *
     * 4️⃣ 时间复杂度分析：
     *    - 遍历 n 个字符串：O(n)
     *    - 每个字符串排序：O(k * log(k))
     *    - 总体：O(n * k * log(k))
     *
     * 5️⃣ 空间复杂度：O(n * k)
     *    - 哈希表存储所有字符串
     *    - 每个字符串长度为 k
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>排序开销</b>：对于很长的字符串，排序可能成为瓶颈</li>
     *   <li><b>替代方案</b>：可以用字符计数作为 key，时间复杂度更优 O(n * k)</li>
     *   <li><b>返回值</b>：返回列表的列表，每组异位词是一个子列表</li>
     *   <li><b>顺序无关</b>：结果列表中组的顺序和组内字符串的顺序都可以任意</li>
     * </ul>
     *
     * @param strs 字符串数组
     * @return 字母异位词分组后的列表
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        // 📋 创建哈希表：key=排序后的字符串，value=原始字符串列表
        Map<String, List<String>> map = new HashMap();

        // 🔄 遍历所有字符串
        for (String str : strs) {
            // 🔤 将字符串转换为字符数组并排序
            char[] s = str.toCharArray();
            Arrays.sort(s);
            String key = new String(s);

            // 📝 获取或创建对应的列表，并添加当前字符串
            List list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }

        // ✅ 返回所有分组的列表
        return new ArrayList<List<String>>(map.values());
    }

}

package CodeTop;

import java.util.HashSet;

/**
 *
 * <a href="https://leetcode.cn/problems/longest-substring-without-repeating-characters/description/">3. 无重复字符的最长子串</a>
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * 示例:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * <p>
 * 解题思路:
 * 使用滑动窗口(双指针) + 哈希集合的方法
 * 1. 维护一个HashSet存储当前窗口内的字符
 * 2. 左指针i遍历整个字符串，右指针j不断扩展窗口直到遇到重复字符
 * 3. 每次移动左指针时，从集合中移除对应的字符
 * 4. 记录过程中窗口的最大长度
 * <p>
 * 时间复杂度: O(n)，其中n是字符串长度
 * 空间复杂度: O(min(m,n))，其中m是字符串中不同字符的数量
 *
 * @author lxd
 **/
public class Day20260109 {
    /**
     * 计算无重复字符的最长子串的长度
     *
     * @param s 输入字符串
     * @return 最长无重复子串的长度
     */
    public int lengthOfLongestSubstring(String s) {
        // 获取字符串长度和初始化结果变量
        int n = s.length(), ans = 0;
        // 使用HashSet存储当前窗口内的字符，便于快速判断是否有重复
        HashSet<Character> set = new HashSet<>();
        // 初始化两个指针，i为左指针，j为右指针
        for (int i = 0, j = 0; i < n; i++) {
            // 当i不为0时，说明需要缩小窗口左侧，移除前一个字符

            // 当左指针i向右移动时，需要将前一个字符（即s.charAt(i-1)）从集合中移除
            // 这样可以保证HashSet中始终只包含当前窗口[i, j)内的字符
            // 例如：当i从0变为1时，需要移除s.charAt(0)，因为窗口的左边界已经移动到了位置1
            if (i > 0) {
                set.remove(s.charAt(i - 1));
            }
            // 扩展右边界，直到遇到重复字符
            // 当j未超出字符串范围且当前字符不在集合中时继续扩展
            while (j < n && !set.contains(s.charAt(j))) {
                // 将当前字符添加到集合中
                set.add(s.charAt(j));
                // 右指针向右移动
                j++;
            }
            // 更新最大长度，j-i即当前窗口大小
            ans = Math.max(ans, j - i);
        }
        // 返回最长无重复子串的长度
        return ans;
    }

}

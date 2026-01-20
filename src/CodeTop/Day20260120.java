package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/longest-palindromic-substring/description/">5. 最长回文子串</a>
 * 给你一个字符串 s，找到 s 中最长的 回文 子串。
 * <p>
 * 解题思路：
 * 使用中心扩展算法。对于每个可能的回文中心，向两边扩展寻找最大回文子串。
 * 回文串有两种情况：
 * 1. 奇数长度：以单个字符为中心向外扩展
 * 2. 偶数长度：以两个相邻相同字符为中心向外扩展
 * 时间复杂度：O(n^2)，其中 n 是字符串长度
 * 空间复杂度：O(1)
 *
 * @author lxd
 **/
public class Day20260120 {
    class Solution {
        public String longestPalindrome(String s) {
            int start = 0, maxLen = 0;

            for (int i = 0; i < s.length(); i++) {
                // 奇数长度回文
                int len1 = expand(s, i, i);
                // 偶数长度回文
                int len2 = expand(s, i, i + 1);

                int len = Math.max(len1, len2);
                if (len > maxLen) {
                    maxLen = len;

                    // 计算回文起始位置：i是当前中心，len是回文长度
                    // 对于奇数长度回文：中心是i，向前移动(len-1)/2步得到起始位置
                    // 对于偶数长度回文：中心在i和i+1之间，向前移动(len-1)/2步得到起始位置
                    start = i - (len - 1) / 2;
                }
            }

            return s.substring(start, start + maxLen);
        }

        /**
         * 从给定的左右边界开始向两边扩展，寻找回文串
         *
         * @param s     字符串
         * @param left  左边界索引
         * @param right 右边界索引
         * @return 返回以[left,right]为初始位置能扩展出的最长回文串的长度
         */
        private int expand(String s, int left, int right) {
            // 当左右边界有效且对应字符相同时，继续向两边扩展
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                left--;  // 左边界左移
                right++; // 右边界右移
            }
            // 循环结束后，left和right分别指向了超出回文范围的位置
            // (right-1) - (left+1) + 1 = right - left - 1
            // 所以回文串的实际长度是 right - left - 1
            return right - left - 1;
        }
    }
}

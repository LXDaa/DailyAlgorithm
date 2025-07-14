package HashTable;

public class Day20250714 {
    /**
     * 242.有效的字母异位词
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     * https://leetcode.cn/problems/valid-anagram/description/
     */
    public boolean isAnagram(String s, String t) {
        int[] record = new int[26];
        for (int i = 0; i < s.length(); i++) {
            record[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            record[t.charAt(i) - 'a']--;
        }
        for (int count : record) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }
}

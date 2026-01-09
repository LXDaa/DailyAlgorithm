package CodeMusings.String;

public class Day20250725 {
    /**
     * 28. 找出字符串中第一个匹配项的下标
     * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。如果 needle 不是 haystack 的一部分，则返回  -1 。
     */
    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null || haystack.length() < needle.length()) {
            return -1;
        }
        int len1 = haystack.length(), len2 = needle.length();
        int i = 0, j = 0;
        int[] next = Next(needle);
        while (i < len1) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
            } else {
                if (j == 0) { //开头不匹配
                    i++;
                } else {
                    j = next[j - 1];
                }
            }
            if (j == len2) {
                return i - j;
            }
        }
        return -1;
    }

    /**
     * next数组中的记录为，模式串与主串匹配失败时，模式串要回退的位置【最长相等前后缀】
     *
     * @param str
     * @return
     */
    private int[] Next(String str) {
        int size = str.length();
        int[] next = new int[size];
        next[0] = 0;
        for (int i = 1, j = 0; i < size; ) {
            if (str.charAt(i) == str.charAt(j)) {
                next[i] = j + 1;
                i++;
                j++;
            } else {
                if (j == 0) {
                    next[i] = 0;
                    i++;
                } else {
                    j = next[j - 1];
                }
            }
        }
        return next;
    }
}

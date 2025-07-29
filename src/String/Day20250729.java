package String;

public class Day20250729 {
    /**
     * 459. 重复的子字符串
     * 给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。
     * https://leetcode.cn/problems/repeated-substring-pattern/description/
     */

    //如果这个字符串s是由重复子串组成，那么最长相等前后缀不包含的子串是字符串s的最小重复子串。
    public boolean repeatedSubstringPattern(String s) {
        int length = s.length();
        int[] next = getNext(s, length);
        int max = next[length - 1];
        if (max < 0) return false;

        return length % (length - max) == 0;
    }

    public int[] getNext(String s, int length) {

        int[] next = new int[length];
        next[0] = 0;
        for (int i = 1, j = 0; j < length; ) {
            if (s.charAt(i) == s.charAt(j)) {
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

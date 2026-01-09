package CodeMusings.String;

public class Day20250723 {
    /**
     * 151. 反转字符串中的单词
     * 给你一个字符串 s ，请你反转字符串中 单词 的顺序。
     * <p>
     * 单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
     * <p>
     * 返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
     * <p>
     * 注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
     * 进阶：如果字符串在你使用的编程语言中是一种可变数据类型，请尝试使用 O(1) 额外空间复杂度的 原地 解法。
     */
    public String reverseWords(String s) {
        //1.移除首尾空格及中间多余空格
        StringBuilder sb = removeSpace(s);
        //2.翻转整个字符串
        reverseStr(sb, 0, sb.length() - 1);
        //3.翻转单词
        reverseEachWord(sb);
        return sb.toString();
    }

    private StringBuilder removeSpace(String s) {
        //移除首尾空格
        int start = 0, end = s.length() - 1;
        while (s.charAt(start) == ' ')
            start++;
        while (s.charAt(end) == ' ')
            end--;

        StringBuilder sb = new StringBuilder();
        while (start <= end) {  //注意=，也许加入sb
            if (s.charAt(start) != ' ' || sb.charAt(sb.length() - 1) != ' ') {
                sb.append(s.charAt(start));
            }
            start++;
        }
        return sb;
    }

    private void reverseStr(StringBuilder sb, int start, int end) {
        while (start < end) {
            char temp = sb.charAt(start);
            sb.setCharAt(start, sb.charAt(end));
            sb.setCharAt(end, temp);
            start++;
            end--;
        }
    }

    private void reverseEachWord(StringBuilder sb) {
        int start = 0;
        int end = 1;//sb不会为null
        int n = sb.length();
        while (start < n) {
            while (end < n && sb.charAt(end) != ' ') {
                end++;
            }
            reverseStr(sb, start, end - 1);//当前end=' ',取end-1
            //重新设置区间
            start = end + 1;
            end = start + 1;//单个字符无须翻转
        }
    }
}


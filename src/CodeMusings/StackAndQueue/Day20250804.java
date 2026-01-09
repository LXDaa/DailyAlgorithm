package CodeMusings.StackAndQueue;

import java.util.ArrayDeque;

/**
 * 1047. 删除字符串中的所有相邻重复项
 * <p>
 * https://leetcode.cn/problems/remove-all-adjacent-duplicates-in-string/description/
 * <p>
 * 给出由小写字母组成的字符串 s，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 * <p>
 * 在 s 上反复执行重复项删除操作，直到无法继续删除。
 * <p>
 * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
 */
public class Day20250804 {
    public String removeDuplicates(String S) {
        ArrayDeque<Character> deque = new ArrayDeque<>();
        char ch;
        for (int i = 0; i < S.length(); i++) {
            ch = S.charAt(i);
            if (deque.isEmpty() || deque.peek() != ch) {
                deque.push(ch);
            } else {
                deque.pop();
            }
        }
        String str = "";
        //剩余的元素即为不重复的元素
        while (!deque.isEmpty()) {
            str = deque.pop() + str;
        }
        return str;
    }
}

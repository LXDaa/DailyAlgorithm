package CodeMusings.Greedy;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/partition-labels/description/">763.划分字母区间</a>
 * <p>
 * 给你一个字符串 s 。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。例如，字符串 "ababcc" 能够被分为 ["abab", "cc"]，但类似 ["aba", "bcc"] 或 ["ab", "ab", "cc"] 的划分是非法的。
 * <p>
 * 注意，划分结果需要满足：将所有划分结果按顺序连接，得到的字符串仍然是 s 。
 * <p>
 * 返回一个表示每个字符串片段的长度的列表。
 * <p>
 * 解题思路：
 * 1. 要将字符串分割成尽可能多的片段，且相同字符只能出现在一个片段中
 * 2. 关键在于确定每个片段的结束位置
 * 3. 先遍历一遍字符串，记录每个字符最后出现的位置
 * 4. 再次遍历字符串，维护当前片段的结束位置：
 *    - 当前片段的结束位置至少是已遍历字符中最后出现位置的最大值
 *    - 当遍历到当前片段结束位置时，说明可以划分一个片段
 */
public class Day20251030 {
    public List<Integer> partitionLabels(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        
        // 记录每个字符最后出现的位置
        int[] last = new int[26];
        for (int i = 0; i < n; i++) {
            last[s[i] - 'a'] = i;
        }
        
        // 存储结果
        ArrayList<Integer> res = new ArrayList<>();
        
        // left: 当前片段的起始位置
        // right: 当前片段的结束位置
        int left = 0, right = 0;
        for (int i = 0; i < n; i++) {
            // 更新当前片段的结束位置为已遍历字符最后位置的最大值
            right = Math.max(right, last[s[i] - 'a']);
            
            // 如果当前位置就是当前片段的结束位置，说明可以划分一个片段
            if (i == right) {
                // 将当前片段长度加入结果列表
                res.add(right - left + 1);
                // 下一个片段的起始位置
                left = i + 1;
            }
        }
        return res;
    }
}
package HashTable;

import java.util.HashMap;
import java.util.Map;

public class Day20250717 {
    /**
     * 454. 四数相加 II
     * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
     * 0 <= i, j, k, l < n
     * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
     * <p>
     * https://leetcode.cn/problems/4sum-ii/description/
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> map = new HashMap<>();
        int result = 0;

        // 保存 nums1 + nums2 所有的结果，以及出现的次数，遍历nums3 + nums4，如果 0-(nums3 + nums4) 在 map 中，则结果加 map 中对应的次数
        for (int a : nums1) {
            for (int b : nums2) {
                map.put(a + b, map.getOrDefault(a + b, 0) + 1);
            }
        }
        for (int c : nums3) {
            for (int d : nums4) {
                result += map.getOrDefault(-(c + d), 0);
            }
        }
        return result;
    }

    /**
     * 383. 赎金信
     * 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
     * 如果可以，返回 true ；否则返回 false 。
     * magazine 中的每个字符只能在 ransomNote 中使用一次。
     */

    public boolean canConstruct(String ransomNote, String magazine) {
        //一些同学可能想，用数组干啥，都用map完事了
        // 其实在本题的情况下，使用map的空间消耗要比数组大一些的，因为map要维护红黑树或者哈希表，而且还要做哈希函数，是费时的！
        // 数据量大的话就能体现出来差别了。 所以数组更加简单直接有效！
        if (ransomNote.length() > magazine.length()) {
            return false;
        }
        int[] record = new int[26];
        for (char c : magazine.toCharArray()) {
            record[c - 'a']++;
        }
        for (char c : ransomNote.toCharArray()) {
            record[c - 'a']--;
            if (record[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}

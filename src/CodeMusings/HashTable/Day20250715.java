package CodeMusings.HashTable;

import java.util.HashSet;

public class Day20250715 {
    /**
     * 349. 两个数组的交集
     * 给定两个数组 nums1 和 nums2 ，返回 它们的交集 。输出结果中的每个元素一定是 唯一 的。我们可以 不考虑输出结果的顺序 。
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return new int[0];
        }
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> reset = new HashSet<>();
        for (int num : nums1) {
            set.add(num);
        }
        for (int num : nums2) {
            if (set.contains(num)) {
                reset.add(num);
            }
        }
        int[] res = new int[reset.size()];
        int index = 0;
        for (int num : reset) {
            res[index++] = num;
        }
        return res;
    }
}

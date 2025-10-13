package BackTracking;

import java.util.*;


/**
 * @author lixingda
 */
public class Day20251013 {
    /**
     * <a href="https://leetcode.cn/problems/subsets-ii/description/">90.子集II</a>
     * <p>
     * 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的 子集（幂集）。
     * <p>
     * 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
     * 
     * 解题思路：
     * 1. 这是子集问题的升级版，需要去重
     * 2. 首先对数组排序，让相同的元素相邻
     * 3. 使用 startIndex 控制遍历起始位置，避免重复使用元素
     * 4. 在同一层递归中，跳过重复元素：if (i > startIndex && nums[i] == nums[i-1])
     * 5. 这个条件确保在树的同一层不会选择相同的元素，但不影响树枝上的选择
     */
    static class Q1 {

        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        public List<List<Integer>> subsetsWithDup(int[] nums) {
            // 排序是为了让相同元素相邻，便于去重处理
            Arrays.sort(nums);
            backTracking(nums, 0);
            return res;

        }

        /**
         * 回溯函数
         * @param nums 输入数组
         * @param startIndex 当前遍历的起始位置
         */
        private void backTracking(int[] nums, int startIndex) {
            // 每次进入函数都将当前路径加入结果集（因为是求子集，每个节点都要记录）
            res.add(new ArrayList<>(path));
            
            // 从startIndex开始遍历，避免重复组合
            for (int i = startIndex; i < nums.length; i++) {
                // 同一层中，如果当前元素与前一个元素相同，则跳过，避免产生重复子集
                // i > startIndex 确保不是第一个元素才进行判断
                if (i > startIndex && nums[i] == nums[i - 1]) {
                    continue;
                }
                
                // 选择当前元素
                path.add(nums[i]);
                // 递归调用，注意下一层从 i+1 开始
                backTracking(nums, i + 1);
                // 回溯，撤销选择
                path.remove(path.size() - 1);
            }
        }
    }

    /**
     * <a href="https://leetcode.cn/problems/non-decreasing-subsequences/description/">491.递增子序列</a>
     * <p>
     * 给你一个整数数组 nums ，找出并返回所有该数组中不同的递增子序列，递增子序列中 至少有两个元素 。你可以按 任意顺序 返回答案。
     * <p>
     * 数组中可能含有重复元素，如出现两个整数相等，也可以视作递增序列的一种特殊情况。
     * 
     * 解题思路：
     * 1. 不同于子集II，这里不能排序，因为要找的是原数组中的递增子序列
     * 2. 需要在每一层递归中使用Set去重，防止同一层选择相同的元素
     * 3. 路径中元素必须满足递增条件才能继续
     * 4. 只有当路径长度大于1时才加入结果集
     */
    static class Q2 {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        public List<List<Integer>> findSubsequences(int[] nums) {
            backTracking(nums, 0);
            return res;
        }

        /**
         * 回溯函数
         * @param nums 输入数组
         * @param startIndex 当前遍历的起始位置
         */
        private void backTracking(int[] nums, int startIndex) {
            // 如果路径长度大于1，说明找到了一个有效的递增子序列
            if (path.size() > 1) {
                res.add(new ArrayList<>(path));
                // 注意这里不返回，还要继续寻找更长的子序列
            }
            
            // 使用Set在每层递归中去重，防止同一层选择相同的元素
            Set<Integer> set = new HashSet<>();
            for (int i = startIndex; i < nums.length; i++) {
                // 如果当前元素在本层已经选择过，则跳过
                if (set.contains(nums[i])) {
                    continue;
                }
                
                // 如果路径不为空且当前元素小于路径最后一个元素，不满足递增条件，跳过
                if (!path.isEmpty() && path.get(path.size() - 1) > nums[i]) {
                    continue;
                }
                
                // 将当前元素加入Set，标记为已使用
                set.add(nums[i]);
                // 选择当前元素
                path.add(nums[i]);
                // 递归调用，注意下一层从 i+1 开始
                backTracking(nums, i + 1);
                // 回溯，撤销选择
                path.remove(path.size() - 1);
            }
        }

    }

    /**
     * <a href="https://leetcode.cn/problems/permutations/description/">46.全排列</a>
     * <p>
     * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
     * 
     * 解题思路：
     * 1. 全排列问题，每个位置可以选择数组中的任意元素
     * 2. 使用contains方法检查元素是否已在路径中，避免重复使用
     * 3. 当路径长度等于数组长度时，找到一个完整的排列
     * 4. 不需要startIndex参数，因为每次都需要从头遍历整个数组
     */
    static class Q3 {
        List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> permute(int[] nums) {
            backTracking(nums, new ArrayList<>());
            return res;
        }

        /**
         * 回溯函数
         * @param nums 输入数组
         * @param path 当前路径
         */
        private void backTracking(int[] nums, List<Integer> path) {
            // 递归终止条件：路径长度等于数组长度，说明找到一个完整排列
            if (path.size() == nums.length) {
                res.add(new ArrayList<>(path));
                return;
            }
            
            // 每次都从0开始遍历整个数组
            for (int i = 0; i < nums.length; i++) {
                // 如果当前元素已在路径中，则跳过（全排列中每个元素只能用一次）
                if (path.contains(nums[i])) {
                    continue;
                }
                
                // 选择当前元素
                path.add(nums[i]);
                // 递归调用
                backTracking(nums, path);
                // 回溯，撤销选择
                path.remove(path.size() - 1);
            }
        }
    }

    /**
     * <a href="https://leetcode.cn/problems/permutations-ii/description/">47.全排列II</a>
     * <p>
     * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
     * 
     * 解题思路：
     * 1. 相比于46题，这里数组中可能包含重复元素，需要去重
     * 2. 首先对数组排序，让相同元素相邻
     * 3. 使用boolean数组used记录元素使用情况
     * 4. 去重的关键在于：if (i > 0 && nums[i] == nums[i-1] && !used[i-1])
     *    这个条件保证对于相同的元素，我们只按照一定的顺序使用它们
     * 5. 当路径长度等于数组长度时，找到一个完整排列
     */
    static class Q4 {
        // 记录每个元素是否被使用
        boolean[] used;
        List<Integer> path = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> permuteUnique(int[] nums) {
            // 初始化used数组
            used = new boolean[nums.length];
            // 排序使相同元素相邻，便于去重
            Arrays.sort(nums);
            backTracking(nums);
            return res;
        }

        /**
         * 回溯函数
         * @param nums 输入数组
         */
        private void backTracking(int[] nums) {
            // 递归终止条件：路径长度等于数组长度，说明找到一个完整排列
            if (path.size() == nums.length) {
                res.add(new ArrayList<>(path));
                return;
            }
            
            // 每次都从0开始遍历整个数组
            for (int i = 0; i < nums.length; i++) {
                // 如果当前元素已被使用，则跳过
                if (used[i]) {
                    continue;
                }
                
                // 去重逻辑：如果当前元素与前一个元素相同，且前一个元素未被使用，则跳过
                // 这样可以保证相同元素按照一定顺序使用，避免重复排列
                if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                    continue;
                }
                
                // 标记当前元素为已使用
                used[i] = true;
                // 选择当前元素
                path.add(nums[i]);
                // 递归调用
                backTracking(nums);
                // 回溯，撤销选择
                used[i] = false;
                path.remove(path.size() - 1);
            }
        }
    }
}
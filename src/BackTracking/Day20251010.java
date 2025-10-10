package BackTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/subsets/description/">78.子集</a>
 * <p>
 * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
 * <p>
 * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
 * <p>
 * 解题思路：
 * 使用回溯算法生成所有子集。对于每个元素，我们都有选择或不选择两种可能。
 * <p>
 * 1. 每次进入递归都将当前路径加入结果集（每个节点都是一个子集）
 * <p>
 * 2. 从startIndex开始遍历，依次将元素加入路径进行深度搜索
 * <p>
 * 3. 递归后进行回溯，移除刚加入的元素尝试其他可能
 */
public class Day20251010 {
    // 结果集，存储所有子集
    List<List<Integer>> res = new ArrayList<>();
    // 路径集，记录当前构建的子集
    List<Integer> path = new ArrayList<>();

    /**
     * 主函数：生成数组的所有子集
     *
     * @param nums 输入的整数数组，元素互不相同
     * @return 所有可能的子集列表
     */
    public List<List<Integer>> subsets(int[] nums) {
        backTracking(nums, 0);
        return res;
    }

    /**
     * 回溯函数：递归生成所有可能的子集
     *
     * @param nums       输入数组
     * @param startIndex 可选取元素的起始位置
     */
    public void backTracking(int[] nums, int startIndex) {
        // 每个节点都是一个子集，先加入结果集
        res.add(new ArrayList<>(path));

        // 从startIndex开始遍历，避免重复组合
        for (int i = startIndex; i < nums.length; i++) {
            // 选择元素
            path.add(nums[i]);
            // 递归处理
            backTracking(nums, i + 1);
            // 回溯
            path.remove(path.size() - 1);
        }
    }
}
package CodeMusings.BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * <a href="https://leetcode.cn/problems/combination-sum-ii/">40.组合总和II</a>
 * <p>
 * 给定一个候选人编号的集合 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * <p>
 * candidates 中的每个数字在每个组合中只能使用 一次 。
 * <p>
 * 注意：解集不能包含重复的组合。
 * 
 * 解题思路：
 * 这是组合总和问题的变种，与39题不同的是：
 * 1. 每个数字只能使用一次（而不是无限次）
 * 2. candidates中可能包含重复数字，但结果不能包含重复组合
 * 
 * 解决方案：
 * 1. 使用回溯算法遍历所有可能的组合
 * 2. 先对数组排序，便于去重和剪枝
 * 3. 使用used数组来标记元素是否被使用过
 * 4. 在同一层递归中跳过重复元素实现去重
 */
public class Day20250926 {
    // 存储所有满足条件的组合结果
    List<List<Integer>> res = new ArrayList<>();
    // 存储当前正在构建的组合路径
    List<Integer> path = new ArrayList<>();
    // 当前路径上数字的和
    int sum = 0;
    // 标记数组中每个元素是否被使用过
    boolean[] used;

    /**
     * 主函数：找出所有和为target的组合（每个数字只能使用一次，不能包含重复组合）
     * 
     * @param candidates 候选人编号数组（可能包含重复数字）
     * @param target 目标和
     * @return 所有满足条件的组合列表
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // 初始化used数组
        used = new boolean[candidates.length];
        // 对数组进行排序，便于后续去重和剪枝
        Arrays.sort(candidates);
        // 将used数组所有元素初始化为false
        Arrays.fill(used, false);
        // 调用回溯函数开始搜索
        backTracing(candidates, target, sum, 0);
        return res;
    }

    /**
     * 回溯函数：递归搜索所有可能的组合
     * 
     * @param candidates 候选数组（已排序）
     * @param target 目标值
     * @param sum 当前路径上数字的和
     * @param startIndex 当前搜索的起始索引
     */
    private void backTracing(int[] candidates, int target, int sum, int startIndex) {
        // 递归终止条件：当前和等于目标值
        if (sum == target) {
            // 将当前路径加入结果集（注意要新建列表）
            res.add(new ArrayList<>(path));
            return;
        }
        
        // 单层搜索逻辑：从startIndex开始遍历候选数组
        for (int i = startIndex; i < candidates.length; i++) {
            // 剪枝：如果加上当前元素会超过目标值，则跳出循环
            if (sum + candidates[i] > target) {
                break;
            }
            
            // 去重：同一层递归中跳过重复元素
            // 如果当前元素与前一个元素相同，且前一个元素未被使用，则跳过当前元素
            // 这样可以避免在同一层递归中选择相同的元素，从而避免生成重复组合
            if (i > 0 && candidates[i] == candidates[i - 1] && !used[i - 1]) {
                continue;
            }
            
            // 处理当前节点：标记为已使用，加入路径
            used[i] = true;
            path.add(candidates[i]);
            
            // 递归处理下一个元素（每个数字只能使用一次，所以传入i+1）
            backTracing(candidates, target, sum + candidates[i], i + 1);
            
            // 回溯，撤销选择：标记为未使用，从路径中移除
            used[i] = false;
            path.remove(path.size() - 1);
        }
    }
}
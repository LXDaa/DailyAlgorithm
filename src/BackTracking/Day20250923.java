package BackTracking;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *<a href="https://leetcode.cn/problems/combination-sum-iii/description/">216. 组合总和 III</a>
 * <p>
 * 找出所有相加之和为 n 的 k 个数的组合，且满足下列条件：
 * <p>
 * 只使用数字1到9
 * <p>
 * 每个数字 最多使用一次
 * <p>
 * 返回 所有可能的有效组合的列表 。该列表不能包含相同的组合两次，组合可以以任何顺序返回。
 * 
 * 解题思路：
 * 这是一个典型的回溯算法问题。我们需要从数字1-9中选择k个不重复的数字，使其和等于目标值n。
 * 使用回溯法遍历所有可能的组合，在每一步选择一个数字加入路径中，递归处理剩余部分，
 * 当路径满足条件时加入结果集，否则回退并尝试其他可能。
 * 剪枝优化：在循环中使用 i <= 9-(k-path.size())+1 来减少不必要的递归调用。
 */
public class Day20250923 {
    // 当前路径，存储正在构建的组合
    List<Integer> path = new ArrayList<>();
    // 结果集合，存储所有满足条件的组合
    List<List<Integer>> result = new ArrayList<>();
    
    /**
     * 主函数：找出所有满足条件的k个数的组合，使其和为n
     * @param k 组合中数字的个数
     * @param n 目标和
     * @return 所有满足条件的组合列表
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        backtrack(n, k, 1, 0);
        return result;
    }
    
    /**
     * 回溯函数：递归构建满足条件的组合
     * @param targetSum 目标和n
     * @param k 需要选择的数字个数
     * @param startIndex 当前可以选择的起始数字（避免重复组合）
     * @param sum 当前路径上的数字和
     */
    private void backtrack(int targetSum, int k, int startIndex, int sum) {
        // 剪枝：如果当前和已经超过目标和，直接返回
        if (sum > targetSum) {
            return;
        }
        
        // 递归终止条件：当前路径已经有k个数字
        if (path.size() == k) {
            // 如果当前和正好等于目标和，则找到一个有效组合
            if (sum == targetSum) {
                // 注意要新建一个列表加入结果集
                result.add(new ArrayList<>(path));
                return;
            }
        }
        
        // 遍历可选择的数字范围，从startIndex到9
        // 剪枝优化：i <= 9-(k-path.size())+1 确保剩余的数字足够组成k个数的组合
        for (int i = startIndex; i <= 9 - (k - path.size()) + 1; i++) {
            // 选择当前数字，加入到路径和中
            sum += i;
            // 将当前数字加入路径
            path.add(i);
            
            // 递归调用，startIndex为i+1避免重复使用同一个数字
            backtrack(targetSum, k, i + 1, sum);
            
            // 回溯操作：撤销选择
            // 从路径和中移除当前数字
            sum -= i;
            // 从路径中移除当前数字
            path.remove(path.size() - 1);
        }
    }
}
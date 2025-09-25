package BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/combination-sum/description/">39.组合总和</a>
 * <p>
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。
 * <p>
 * 你可以按 任意顺序 返回这些组合。
 *<p>
 * candidates 中的 同一个 数字可以 无限制重复被选取 。
 * <p>
 * 如果至少一个数字的被选数量不同，则两种组合是不同的。
 *<p>
 * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
 *<p>
 *
 */
public class Day20250925 {
    // 存储所有满足条件的组合结果
    List<List<Integer>> res = new ArrayList<>();
    // 存储当前正在构建的组合路径
    List<Integer> path = new ArrayList<>();

    /**
     * 主函数：找出所有和为 target 的组合
     * @param candidates 无重复元素的整数数组
     * @param target 目标和
     * @return 所有满足条件的组合列表
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 对数组进行排序，便于后续剪枝优化
        Arrays.sort(candidates);
        // 调用回溯函数开始搜索
        backTracing(candidates, target, 0, 0);
        return res;
    }

    /**
     * 回溯函数：递归搜索所有可能的组合
     * 解题思路：
     * 1. 使用回溯算法，通过递归遍历所有可能的组合
     * 2. 每个数字可以无限次使用，所以在递归时索引不增加
     * 3. 通过排序和剪枝优化减少不必要的搜索
     * 4. 当当前和等于目标值时，将路径加入结果集
     * 5. 当当前和超过目标值时，剪枝停止继续搜索
     *
     * @param candidates 候选数组
     * @param target 目标值
     * @param sum 当前路径的和
     * @param startIndex 当前搜索的起始索引（避免重复组合）
     */
    private void backTracing(int[] candidates, int target, int sum, int startIndex) {
        // 终止条件：当前和等于目标值
        if (sum == target) {
            // 找到一个满足条件的组合，将其加入结果集
            // 注意：需要创建新的 ArrayList，否则后续的 remove 操作会影响已添加的结果
            res.add(new ArrayList<>(path));
            return;
        }

        // 遍历候选数组，从 startIndex 开始避免重复组合
        for (int i = startIndex; i < candidates.length; i++) {
            // 剪枝优化：如果当前和加上候选值超过目标值，则后续更大的值也不需要考虑
            // 由于数组已排序，可以直接 break 跳出循环
            if (sum + candidates[i] > target) {
                break;
            }

            // 选择：将当前候选值加入路径
            path.add(candidates[i]);

            // 递归：继续搜索，注意索引传入 i 而不是 i+1，允许重复选择同一元素
            // 同时更新当前和 sum + candidates[i]
            backTracing(candidates, target, sum + candidates[i], i);

            // 回溯：移除刚刚加入的元素，恢复到之前的状态
            path.remove(path.size() - 1);
        }
    }
}

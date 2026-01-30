package CodeTop;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/permutations/description/">46.全排列</a>
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 *
 * @author lxd
 **/
public class Day20260130 {

    // 存储所有可能的排列结果
    List<List<Integer>> res = new ArrayList();

    /**
     * 生成数组的所有全排列
     * <p>
     * 解题思路：
     * 1. 使用回溯算法来生成所有可能的排列
     * 2. 通过递归的方式构建路径(path)，每次选择一个未使用的数字加入路径
     * 3. 当路径长度等于原数组长度时，说明已经形成一个完整排列，将其添加到结果集中
     * 4. 递归返回后撤销选择(移除最后添加的元素)，尝试其他可能性
     * <p>
     * 算法流程：
     * - 初始化一个空路径，开始回溯
     * - 在每一步递归中，遍历数组中的每个元素
     * - 如果元素已在当前路径中，则跳过
     * - 否则，将元素加入路径，继续递归
     * - 递归完成后，移除该元素以尝试其他可能性
     * <p>
     * 执行过程示例：
     * 假设 nums = [1, 2, 3]
     * 第一层递归: path=[], 尝试添加1 -> [1], 递归
     * 第二层递归: path=[1], 尝试添加2 -> [1,2], 递归
     * 第三层递归: path=[1,2], 尝试添加3 -> [1,2,3], 达到长度要求，添加到结果
     * 然后回溯: 移除3 -> [1,2], 移除2 -> [1], 尝试添加3 -> [1,3], 以此类推
     *
     * @param nums 输入数组，包含不重复的数字
     * @return 包含所有可能排列的列表
     */
    public List<List<Integer>> permute(int[] nums) {
        // 从空路径开始回溯搜索
        backtrack(new ArrayList(), nums);
        return res;
    }

    /**
     * 回溯算法核心方法
     *
     * @param path 当前已形成的路径（当前排列的一部分）
     * @param nums 原始输入数组
     */
    private void backtrack(List<Integer> path, int[] nums) {
        // 递归终止条件：当前路径的长度等于数组长度，说明已经形成了一个完整的排列
        if (path.size() == nums.length) {
            // 创建当前路径的副本并添加到结果集，防止后续修改影响已添加的结果
            res.add(new ArrayList(path));
            return;
        }

        // 遍历数组中的每一个数字，尝试将其加入当前路径
        for (int i = 0; i < nums.length; i++) {
            // 剪枝操作：如果当前数字已经在路径中，跳过以避免重复使用同一元素
            if (path.contains(nums[i])) {
                continue;
            }

            // 做出选择：将当前数字添加到路径中
            path.add(nums[i]);

            // 递归进入下一层，继续构建排列
            backtrack(path, nums);

            // 撤销选择：将刚才添加的数字从路径末尾移除，尝试其他可能性
            // 这是回溯算法的关键步骤，确保可以尝试其他分支
            path.remove(path.size() - 1);
        }
    }

}

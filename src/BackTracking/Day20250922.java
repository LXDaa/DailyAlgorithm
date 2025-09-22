package BackTracking;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <a href="https://leetcode.cn/problems/combinations/description/">77.组合</a>
 * <p>
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
 * <p>
 * 你可以按 任何顺序 返回答案。
 * 
 * 解题思路：
 * 这是一个典型的回溯算法问题。我们需要从 [1, n] 中选择 k 个数的所有组合。
 * 
 * 回溯三部曲：
 * 1. 确定递归函数的返回值和参数：这里需要 n、k 和 startIndex（避免重复组合）
 * 2. 确定终止条件：当 path 中的元素个数等于 k 时，将结果加入结果集
 * 3. 确定单层递归逻辑：for 循环遍历每一层的元素，递归处理下一层，然后回溯
 * 
 * 剪枝优化：
 * 在循环条件中使用 i <= n-(k-path.size())+1 进行剪枝，避免不必要的递归
 * 例如：n=4, k=3，当 path.size()=1 时，i 最大只能是 4-(3-1)+1=3，
 * 因为如果 i=4，则无法再选择两个数构成长度为3的组合
 */
public class Day20250922 {
    // 结果集，存储所有符合条件的组合
    List<List<Integer>> res = new ArrayList<>();
    // 路径集，存储当前正在构建的组合
    List<Integer> path = new ArrayList<>();
    
    /**
     * 获取 [1, n] 中所有长度为 k 的组合
     * @param n 范围上限
     * @param k 组合长度
     * @return 所有符合条件的组合
     */
    public List<List<Integer>> combine(int n, int k) {
        dfs(n,k,1);
        return res;
    }
    
    /**
     * 深度优先搜索（回溯）函数
     * @param n 范围上限
     * @param k 目标组合长度
     * @param startIndex 当前搜索的起始位置，防止重复组合
     */
    public void dfs(int n,int k,int startIndex){
        // 终止条件：当路径长度等于目标长度时，找到一个有效组合
        if(path.size()==k){
            // 将当前路径加入结果集（注意要新建 ArrayList，避免后续修改影响已保存的结果）
            res.add(new ArrayList<>(path));
            return;
        }
        // 单层搜索逻辑：从 startIndex 开始遍历到 n
        // 剪枝优化：i <= n-(k-path.size())+1
        // 保证剩余元素足够构成长度为 k 的组合
        for(int i=startIndex; i<=n-(k-path.size())+1; i++){
            // 处理当前节点：将 i 加入路径
            path.add(i);
            // 递归处理下一层：从 i+1 开始搜索
            dfs(n,k,i+1);
            // 回溯：移除刚刚加入的元素，恢复到递归前的状态
            path.remove(path.size()-1);
        }
    }
}
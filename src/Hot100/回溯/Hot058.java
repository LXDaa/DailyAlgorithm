package Hot100.回溯;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot058 {
    /**
     * <a href="https://leetcode.cn/problems/combination-sum/description/">39. 组合总和</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个无重复元素的整数数组 candidates 和一个目标整数 target，找出 candidates 中可以使数字和为目标数 target 的所有不同组合。</p>
     * <p>candidates 中的同一个数字可以无限制重复被选取。如果至少一个数字的被选数量不同，则两种组合是不同的。</p>
     *
     * <h3>💡 核心思路：回溯算法 + 剪枝优化</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用回溯算法，尝试所有可能的组合
     *     <ul>
     *       <li>从某个位置开始，选择数字加入路径</li>
     *       <li>递归搜索，目标值减去当前选择的数字</li>
     *       <li>目标值为 0 时找到一个有效组合</li>
     *       <li>目标值小于 0 时剪枝（回溯）</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么可以重复选择</b>：
     *     <ul>
     *       <li>递归时传入当前索引 i，而不是 i+1</li>
     *       <li>这允许在下一层继续选择同一个元素</li>
     *       <li>实现无限制重复选取</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(S)，其中 S 为所有可行解的长度之和</li>
     *   <li><b>空间复杂度</b>：O(target)，递归栈深度</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：candidates = [2, 3, 6, 7], target = 7
     *
     * 【回溯树】
     *
     *                      target = 7
     *              /    /      \      \
     *            2      3       6       7
     *          (5)    (4)     (1)     (0)✅
     *         /|\     /|\      |
     *        2 3 6   2 3 6    2(剪枝)
     *       (3)(2)(-1)(2)(1)  (-1)
     *       /|  |    |
     *      2 3  2    2
     *     (1)(0)✅  (0)✅
     *      |
     *      2(剪枝)
     *
     * 【递归搜索过程】
     *
     * backtrack(7, 0, [])
     *   ├─ target = 7 > 0，继续
     *   │
     *   ├─ i = 0, 选择 2
     *   │   backtrack(5, 0, [2])
     *   │   ├─ target = 5 > 0，继续
     *   │   │
     *   │   ├─ i = 0, 选择 2
     *   │   │   backtrack(3, 0, [2, 2])
     *   │   │   ├─ target = 3 > 0，继续
     *   │   │   │
     *   │   │   ├─ i = 0, 选择 2
     *   │   │   │   backtrack(1, 0, [2, 2, 2])
     *   │   │   │   ├─ target = 1 > 0，继续
     *   │   │   │   ├─ i = 0, 选择 2
     *   │   │   │   │   backtrack(-1, 0, ...) → target < 0，剪枝
     *   │   │   │   ├─ i = 1, 选择 3
     *   │   │   │   │   backtrack(-2, 1, ...) → target < 0，剪枝
     *   │   │   │   └─ ...
     *   │   │   │
     *   │   │   ├─ i = 1, 选择 3
     *   │   │   │   backtrack(0, 1, [2, 2, 3])
     *   │   │   │   ├─ target = 0 ✅
     *   │   │   │   ├─ 添加组合：[2, 2, 3] ✅
     *   │   │   │   └─ return
     *   │   │   │
     *   │   │   └─ ...
     *   │   │
     *   │   └─ ...
     *   │
     *   ├─ i = 1, 选择 3
     *   │   backtrack(4, 1, [3])
     *   │   └─ ...
     *   │
     *   ├─ i = 2, 选择 6
     *   │   backtrack(1, 2, [6])
     *   │   └─ ...
     *   │
     *   └─ i = 3, 选择 7
     *       backtrack(0, 3, [7])
     *       ├─ target = 0 ✅
     *       ├─ 添加组合：[7] ✅
     *       └─ return
     *
     * 【最终结果】
     *
     * res = [[2, 2, 3], [7]]
     *
     * 总共 2 种组合 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么可以重复选择同一个元素？
     *    - 递归时传入 i 而不是 i+1
     *    - 这允许下一层继续选择当前元素
     *    - 例如：选了 2 后，下一层还可以选 2
     *
     * 2️⃣ 如何避免重复组合？
     *    - 使用 start 参数控制搜索范围
     *    - 只向后搜索，不回头
     *    - 避免生成 [2, 3] 和 [3, 2] 这样的重复组合
     *
     * 3️⃣ 递归终止条件是什么？
     *    - target == 0：找到一个有效组合
     *    - target < 0：当前路径无效，剪枝
     *    - 两种情况都需要 return
     *
     * 4️⃣ 剪枝的作用是什么？
     *    - 提前终止无效的搜索路径
     *    - 减少不必要的递归调用
     *    - 提高算法效率
     *
     * 5️⃣ 回溯操作是什么？
     *    - path.add(candidates[i])：选择数字
     *    - backtrack(candidates, target - candidates[i], i, path, res)：递归
     *    - path.remove(path.size() - 1)：撤销选择
     *
     * 6️⃣ 为什么不需要排序？
     *    - 题目没有要求组合内的顺序
     *    - 通过 start 参数已经避免了重复
     *    - 如果需要剪枝优化，可以先排序
     *
     * 7️⃣ 与子集问题的区别？
     *    - 子集：每个元素可选可不选
     *    - 组合总和：可以重复选择，直到满足条件
     *    - 组合总和需要判断 target 是否为 0
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ res = []（结果集）
     *    └─ 调用 backtrack(candidates, target, 0, [], res)
     *
     *    回溯函数 backtrack(candidates, target, start, path, res)：
     *    ├─ if target == 0：
     *    │   ├─ res.add(new ArrayList(path))
     *    │   └─ return
     *    ├─ if target < 0：
     *    │   └─ return（剪枝）
     *    ├─ for i in start..n-1：
     *    │   ├─ path.add(candidates[i])
     *    │   ├─ backtrack(candidates, target - candidates[i], i, path, res)
     *    │   └─ path.remove(path.size() - 1)
     *    └─ return
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>重复选择</b>：递归时传入 i 而不是 i+1，允许重复选择</li>
     *   <li><b>避免重复组合</b>：使用 start 参数控制搜索范围，只向后搜索</li>
     *   <li><b>终止条件</b>：target == 0 收集结果，target < 0 剪枝</li>
     *   <li><b>深拷贝</b>：添加到结果集时使用 new ArrayList(path)</li>
     *   <li><b>剪枝优化</b>：可以先排序，在循环中提前终止</li>
     * </ul>
     *
     * @param candidates 无重复元素的整数数组
     * @param target     目标整数
     * @return 所有使数字和为 target 的组合
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 📦 创建结果列表
        List<List<Integer>> res = new ArrayList<>();
        // 🔄 开始回溯搜索，从索引 0 开始
        backtrack(candidates, target, 0, new ArrayList<>(), res);
        return res;
    }

    /**
     * 回溯搜索所有组合
     *
     * @param candidates 候选数字数组
     * @param target     当前目标值
     * @param start      搜索起始位置
     * @param path       当前已选择的路径
     * @param res        结果集
     */
    private void backtrack(int[] candidates, int target, int start, List<Integer> path, List<List<Integer>> res) {
        // 🎯 终止条件1：目标值为 0，找到一个有效组合
        if (target == 0) {
            // 📝 添加当前组合的拷贝（深拷贝）
            res.add(new ArrayList<>(path));
            return;
        }

        // 🎯 终止条件2：目标值小于 0，当前路径无效，剪枝
        if (target < 0) {
            return;
        }

        // 🔄 遍历所有可能的数字选择
        for (int i = start; i < candidates.length; i++) {
            // ➕ 选择当前数字
            path.add(candidates[i]);

            // 🔄 递归搜索，目标值减去当前数字
            // 注意：传入 i 而不是 i+1，允许重复选择当前元素
            backtrack(candidates, target - candidates[i], i, path, res);

            // ➖ 撤销选择，回溯到上一个状态
            path.remove(path.size() - 1);
        }
    }
}

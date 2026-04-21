package CodeTop;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/combination-sum/description/">39. 组合总和</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>给你一个无重复元素的整数数组 candidates 和一个目标整数 target，找出 candidates 中可以使数字和为目标数 target 的所有不同组合，并以列表形式返回。</p>
 * <p>你可以按任意顺序返回这些组合。</p>
 * <p><b>关键条件</b>：candidates 中的同一个数字可以无限制重复被选取。如果至少一个数字的被选数量不同，则两种组合是不同的。</p>
 *
 * <h3>💡 核心思路：回溯算法 + 剪枝优化</h3>
 * <ul>
 *   <li><b>问题本质</b>：从候选数组中选择元素（可重复），使和等于 target</li>
 *   <li><b>决策树</b>：每个元素有两种选择
 *     <ul>
 *       <li><b>不选</b>：跳过当前元素，处理下一个元素（idx+1）</li>
 *       <li><b>选</b>：选择当前元素，继续从当前索引递归（idx 不变，因为可重复）</li>
 *     </ul>
 *   </li>
 *   <li><b>去重策略</b>：通过索引 idx 控制，只考虑当前及之后的元素，避免 [2,3] 和 [3,2] 这样的重复</li>
 *   <li><b>剪枝优化</b>：只有 target - candidates[idx] >= 0 时才选择当前元素</li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>递归基</b>：
 *     <ul>
 *       <li>如果 idx == candidates.length：遍历完所有元素，返回</li>
 *       <li>如果 target == 0：找到合法组合，将 path 加入 res</li>
 *     </ul>
 *   </li>
 *   <li><b>分支1：不选当前元素</b>：backTrace(candidates, target, idx+1, path, res)</li>
 *   <li><b>分支2：选当前元素</b>（需满足 target - candidates[idx] >= 0）：
 *     <ul>
 *       <li>做选择：path.add(candidates[idx])</li>
 *       <li>递归：backTrace(candidates, target-candidates[idx], idx, path, res)（注意 idx 不变）</li>
 *       <li>撤销选择：path.remove(path.size()-1)</li>
 *     </ul>
 *   </li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例：candidates = [2,3,6,7], target = 7
 *
 * 【递归树展开】
 *
 *                         [] t=7
 *                       /        \
 *              不选2 [] t=7      选2 [2] t=5
 *                  /    \          /      \
 *          不选3 [] t=7  选3 [3] t=4  不选2 [2] t=5  选2 [2,2] t=3
 *               |         |            |           /      \
 *          不选6 ...   不选6 ...   选3 [2,3] t=2  不选2 [2,2] t=3  选2 [2,2,2] t=1
 *                          |            |           |           /      \
 *                      选6 [3,6] t=-2 选6 [2,3,6]  选3 [2,2,3] t=0 ✅ 不选2... 选2 [2,2,2,2] t=-1 ❌
 *                      (剪枝)       t=-5(剪枝)     ✅ 找到解!   (剪枝)
 *
 * 【详细执行流程】
 *
 * 1️⃣ backTrace(idx=0, target=7)
 *    ├── 不选2: backTrace(idx=1, target=7)
 *    │         ├── 不选3: backTrace(idx=2, target=7)
 *    │         │         └── ...
 *    │         └── 选3: path=[3], backTrace(idx=1, target=4)
 *    │                   ├── 不选3: ...
 *    │                   └── 选3: path=[3,3], backTrace(idx=1, target=1)
 *    │                             └── target-candidates[1]=1-3<0, 剪枝
 *    │
 *    └── 选2: path=[2], backTrace(idx=0, target=5)
 *              ├── 不选2: backTrace(idx=1, target=5)
 *              │         └── 选3: path=[2,3], backTrace(idx=1, target=2)
 *              │                   └── target-candidates[1]=2-3<0, 剪枝
 *              │
 *              └── 选2: path=[2,2], backTrace(idx=0, target=3)
 *                        ├── 不选2: backTrace(idx=1, target=3)
 *                        │         └── 选3: path=[2,2,3], backTrace(idx=1, target=0)
 *                        │                   ✅ target==0, 找到解 [2,2,3]
 *                        │
 *                        └── 选2: path=[2,2,2], backTrace(idx=0, target=1)
 *                                  └── target-candidates[0]=1-2<0, 剪枝
 *
 * 【另一个分支：直接选7】
 * backTrace(idx=3, target=7)
 *   └── 选7: path=[7], backTrace(idx=3, target=0)
 *             ✅ target==0, 找到解 [7]
 *
 * 最终结果: [[2,2,3], [7]] ✅
 * </pre>
 *
 * <h3>🔑 关键技巧解析</h3>
 * <pre>
 * 1️⃣ 为什么选的时候 idx 不变，不选的时候 idx+1？
 *    - 选：因为元素可重复使用，下次还可以选同一个元素
 *    - 不选：跳过当前元素，处理下一个元素
 *    - 例如：选了 2 后，还可以继续选 2 → [2,2,2,...]
 *
 * 2️⃣ 如何避免重复组合？
 *    - 通过 idx 控制，只考虑当前及之后的元素
 *    - 不会出现 [2,3] 和 [3,2] 这样的重复
 *    - 因为一旦跳过某个元素（idx+1），就不会再回头选它
 *
 * 3️⃣ 剪枝的条件是什么？
 *    - target - candidates[idx] >= 0 才选择
 *    - 如果减去当前元素后 target 为负，说明超了，不需要继续
 *    - 例如：target=1, candidates[idx]=2, 1-2<0, 剪枝
 *
 * 4️⃣ 为什么先"不选"再"选"？
 *    - 顺序不影响结果，但影响递归树的展开顺序
 *    - 也可以先"选"再"不选"，结果相同
 *
 * 5️⃣ 时间复杂度：O(2^n × target/min(candidates))
 *    - n 为 candidates 长度
 *    - 每个元素有选/不选两种可能
 *    - 递归深度取决于 target 和最小元素的比值
 *
 * 6️⃣ 空间复杂度：O(target/min(candidates))
 *    - 递归栈深度
 *    - path 的最大长度
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>深拷贝</b>：res.add(new ArrayList<>(path)) 必须创建新对象</li>
 *   <li><b>回溯操作</b>：path.remove(path.size()-1) 必须在递归返回后执行</li>
 *   <li><b>剪枝条件</b>：先判断 target-candidates[idx]>=0 再递归，避免无效搜索</li>
 *   <li><b>索引控制</b>：选的时候传 idx（可重复），不选的时候传 idx+1（跳过）</li>
 *   <li><b>终止条件顺序</b>：先判断 idx 越界，再判断 target==0</li>
 *   <li><b>无重复元素</b>：题目保证 candidates 无重复，无需额外去重</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260421 {
    // 📦 结果集：存储所有找到的组合
    // 🛤️ 当前路径：记录正在构建的组合

    /**
     * 🔍 计算所有和为 target 的组合
     *
     * @param candidates 候选数组（无重复元素）
     * @param target     目标和
     * @return 所有和为 target 的组合列表
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        // 🚀 启动回溯，从索引 0 开始
        backTrace(candidates, target, 0, path, res);
        return res;
    }

    /**
     * 🔄 回溯函数：递归搜索所有可能的组合
     *
     * @param candidates 候选数组
     * @param target     剩余需要凑的目标值
     * @param idx        当前考虑的候选元素索引
     * @param path       当前已选择的元素路径
     * @param res        结果集合
     */
    public void backTrace(int[] candidates, int target, int idx, List<Integer> path, List<List<Integer>> res) {
        // 🛑 递归基1：遍历完所有元素，返回
        if (idx == candidates.length) {
            return;
        }

        // ✅ 递归基2：找到合法组合，加入结果集
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        // 分支1：不选当前元素，处理下一个元素
        backTrace(candidates, target, idx + 1, path, res);

        // 分支2：选当前元素（需满足剪枝条件）
        if (target - candidates[idx] >= 0) {
            // ✅ 做选择：将当前元素加入路径
            path.add(candidates[idx]);

            // 🔄 递归：继续从当前索引搜索（元素可重复）
            backTrace(candidates, target - candidates[idx], idx, path, res);

            // ↩️ 撤销选择：回溯，恢复状态
            path.remove(path.size() - 1);
        }
    }
}

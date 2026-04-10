package CodeTop;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/subsets/description/">78. 子集</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>给你一个整数数组 nums，数组中的元素互不相同。返回该数组所有可能的子集（幂集）。</p>
 * <p>解集不能包含重复的子集。你可以按任意顺序返回解集。</p>
 *
 * <h3>💡 核心思路：回溯算法（Backtracking）</h3>
 * <ul>
 *   <li><b>问题本质</b>：求集合的所有子集，即幂集，共有 2^n 个子集</li>
 *   <li><b>回溯思想</b>：对于每个元素，有"选"或"不选"两种选择</li>
 *   <li><b>树形结构</b>：递归树的每一层代表一个位置的选择，每条路径代表一个子集</li>
 *   <li><b>收集结果</b>：在每次进入递归时，都将当前 path 加入结果集（包括空集）</li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>递归入口</b>：从索引 0 开始回溯</li>
 *   <li><b>收集结果</b>：每次进入递归函数，先将当前 path 加入 res（因为每个节点都代表一个有效子集）</li>
 *   <li><b>遍历选择</b>：从 startIndex 开始遍历数组
 *     <ul>
 *       <li>做选择：将 nums[i] 加入 path</li>
 *       <li>递归：继续处理下一个位置（i+1），避免重复使用元素</li>
 *       <li>撤销选择：回溯，移除最后一个元素</li>
 *     </ul>
 *   </li>
 *   <li><b>终止条件</b>：当 startIndex >= nums.length 时，for 循环自然结束</li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例：nums = [1, 2, 3]
 *
 * 递归树展开：
 *
 *                    []           ← 根节点（空集）
 *                  / | \
 *                /   |   \
 *             [1]   [2]   [3]    ← 第一层：选择一个元素
 *            / \     |
 *          /   \    [2,3]        ← 第二层：选择两个元素
 *        [1,2] [1,3]
 *         |
 *       [1,2,3]                  ← 第三层：选择三个元素
 *
 * 详细执行流程：
 *
 * 1️⃣ startIndex=0:
 *    - 收集: []
 *    - i=0: path=[1]
 *      2️⃣ startIndex=1:
 *         - 收集: [1]
 *         - i=1: path=[1,2]
 *           3️⃣ startIndex=2:
 *              - 收集: [1,2]
 *              - i=2: path=[1,2,3]
 *                4️⃣ startIndex=3:
 *                   - 收集: [1,2,3]
 *                   - for 循环不执行（i=3 >= 3）
 *                   - 回溯: path=[1,2]
 *              - 回溯: path=[1]
 *         - i=2: path=[1,3]
 *           3️⃣ startIndex=3:
 *              - 收集: [1,3]
 *              - for 循环不执行
 *              - 回溯: path=[1]
 *      - 回溯: path=[]
 *    - i=1: path=[2]
 *      2️⃣ startIndex=2:
 *         - 收集: [2]
 *         - i=2: path=[2,3]
 *           3️⃣ startIndex=3:
 *              - 收集: [2,3]
 *              - 回溯: path=[2]
 *      - 回溯: path=[]
 *    - i=2: path=[3]
 *      2️⃣ startIndex=3:
 *         - 收集: [3]
 *         - for 循环不执行
 *      - 回溯: path=[]
 *
 * 最终结果: [[], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]]
 * ✅ 共 2^3 = 8 个子集
 * </pre>
 *
 * <h3>🔑 关键点解析</h3>
 * <pre>
 * 1️⃣ 为什么每次递归都要收集结果？
 *    - 因为每个节点（包括中间节点）都代表一个有效的子集
 *    - 空集也是子集，所以在第一次进入时就会收集 []
 *
 * 2️⃣ 为什么用 startIndex？
 *    - 避免重复组合，保证每个元素只被选择一次
 *    - 下一层递归从 i+1 开始，不会回头选择之前的元素
 *    - 例如：选了 [1,2] 后不会再选 [2,1]
 *
 * 3️⃣ 与组合问题的区别？
 *    - 组合问题：只收集叶子节点（固定长度 k）
 *    - 子集问题：收集所有节点（任意长度）
 *
 * 4️⃣ 时间复杂度：O(n × 2^n)
 *    - 共有 2^n 个子集
 *    - 每个子集平均长度 O(n)
 *    - 复制 path 到 res 需要 O(n)
 *
 * 5️⃣ 空间复杂度：O(n)
 *    - 递归栈深度最多 n 层
 *    - path 最多存储 n 个元素
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>深拷贝</b>：res.add(new ArrayList(path)) 必须创建新对象，否则后续修改会影响已添加的结果</li>
 *   <li><b>回溯操作</b>：path.remove(path.size() - 1) 必须在递归返回后执行，恢复状态</li>
 *   <li><b>起始索引</b>：backTracking(nums, i + 1) 确保每个元素只使用一次</li>
 *   <li><b>无需剪枝</b>：子集问题需要遍历所有可能，不需要剪枝优化</li>
 *   <li><b>元素互不相同</b>：题目保证无重复元素，因此无需去重处理</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260410 {
    // 🛤️ 当前路径：记录正在构建的子集
    List<Integer> path = new ArrayList<>();
    // 📦 结果集：存储所有找到的子集
    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        // 🚀 启动回溯，从索引 0 开始
        backTracking(nums, 0);
        return res;
    }

    /**
     * 🔄 回溯算法：生成所有子集
     *
     * @param nums       输入数组
     * @param startIndex 当前选择的起始索引，避免重复选择
     */
    private void backTracking(int[] nums, int startIndex) {
        // 📥 收集结果：每个节点都代表一个有效子集（包括空集）
        res.add(new ArrayList<>(path));

        // 🔍 遍历选择列表：从 startIndex 开始，避免回头选择
        for (int i = startIndex; i < nums.length; i++) {
            // ✅ 做选择：将当前元素加入路径
            path.add(nums[i]);

            // 🔄 递归：继续处理下一个位置
            backTracking(nums, i + 1);

            // ↩️ 撤销选择：回溯，恢复状态
            path.remove(path.size() - 1);
        }
    }
}

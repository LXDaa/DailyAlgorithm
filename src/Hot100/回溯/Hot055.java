package Hot100.回溯;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot055 {
    /**
     * <a href="https://leetcode.cn/problems/permutations/description/">46. 全排列</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个不含重复数字的数组 nums ，返回其所有可能的全排列。你可以按任意顺序返回答案。</p>
     *
     * <h3>💡 核心思路：回溯算法 + 路径标记</h3>
     * <ul>
     *   <li><b>基本思想</b>：深度优先搜索 + 回溯，遍历所有可能的排列
     *     <ul>
     *       <li>从空路径开始，依次选择未使用的数字</li>
     *       <li>使用 contains 判断数字是否已在路径中</li>
     *       <li>路径长度达到数组长度时找到一个排列</li>
     *       <li>回溯时撤销选择</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用回溯</b>：
     *     <ul>
     *       <li>全排列问题本质是指数级的搜索问题</li>
     *       <li>回溯可以系统地遍历所有可能的路径</li>
     *       <li>通过撤销选择实现状态的回退</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n × n!)，n! 个排列，每个排列需要 O(n) 时间</li>
     *   <li><b>空间复杂度</b>：O(n)，递归栈和路径列表</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [1, 2, 3]
     *
     * 【回溯树】
     *
     *                    []
     *          /    |    \
     *         1      2      3
     *        / \   / \    / \
     *       12 13 21 23 31 32
     *       |   |   |   |   |   |
     *      123 132 213 231 312 321
     *
     * 【递归搜索过程】
     *
     * backtrack([])
     *   ├─ path.size() = 0 ≠ 3，继续
     *   │
     *   ├─ 选择 1
     *   │   backtrack([1])
     *   │   ├─ path.size() = 1 ≠ 3，继续
     *   │   │
     *   │   ├─ 选择 2
     *   │   │   backtrack([1, 2])
     *   │   │   ├─ path.size() = 2 ≠ 3，继续
     *   │   │   │
     *   │   │   ├─ 选择 3
     *   │   │   │   backtrack([1, 2, 3])
     *   │   │   │   ├─ path.size() = 3 == 3
     *   │   │   │   ├─ 添加排列：[1, 2, 3] ✅
     *   │   │   │   └─ return
     *   │   │   │
     *   │   │   └─ 回溯：移除 3
     *   │   │
     *   │   └─ 回溯：移除 2
     *   │
     *   ├─ 选择 2
     *   │   backtrack([2])
     *   │   ├─ 选择 1
     *   │   │   backtrack([2, 1])
     *   │   │   ├─ 选择 3
     *   │   │   │   backtrack([2, 1, 3])
     *   │   │   │   ├─ 添加排列：[2, 1, 3] ✅
     *   │   │   │   └─ return
     *   │   │   └─ 回溯
     *   │   └─ 回溯
     *   │
     *   └─ 选择 3
     *       backtrack([3])
     *       └─ ...
     *
     * 【最终结果】
     *
     * res = [[1, 2, 3], [1, 3, 2], [2, 1, 3],
     *        [2, 3, 1], [3, 1, 2], [3, 2, 1]]
     *
     * 总共 3! = 6 种排列 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用 contains 判断？
     *    - 需要知道哪些数字已被选择
     *    - contains 检查路径中是否已有该数字
     *    - 如果有则跳过，避免重复选择
     *
     * 2️⃣ 递归终止条件是什么？
     *    - path.size() == nums.length
     *    - 表示路径已满，找到一个完整的排列
     *    - 添加到结果集并返回
     *
     * 3️⃣ 循环为什么从 0 开始？
     *    - 全排列需要考虑所有可能的选择
     *    - 不像子集问题需要控制起始位置
     *    - 每个位置都可以选择任意未使用的数字
     *
     * 4️⃣ 回溯操作是什么？
     *    - path.add(nums[i])：选择数字
     *    - backtrack(...)：递归搜索
     *    - path.remove(...)：撤销选择
     *    - 三步缺一不可，保证状态正确
     *
     * 5️⃣ 为什么要 new ArrayList(path)？
     *    - path 是引用类型，会被修改
     *    - 添加的是 path 的拷贝，不是引用
     *    - 否则后续修改会影响已添加的结果
     *
     * 6️⃣ 与子集问题的区别？
     *    - 子集：每个元素可选可不选
     *    - 全排列：每个位置必须选一个，选完不能重复选
     *    - 全排列是排列问题，子集是组合问题
     *
     * 7️⃣ 时间复杂度如何分析？
     *    - 第一层：n 个选择
     *    - 第二层：n-1 个选择
     *    - ...
     *    - 第 n 层：1 个选择
     *    - 总共 n! 个叶子节点，每个节点需要 O(n) 处理
     *    - 时间复杂度 O(n × n!)
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ res = []（结果集）
     *    ├─ path = []（当前路径）
     *    └─ 调用 backtrack(nums, path, res)
     *
     *    回溯函数 backtrack(nums, path, res)：
     *    ├─ if path.size() == nums.length：
     *    │   ├─ res.add(new ArrayList(path))
     *    │   └─ return
     *    ├─ for i in 0..n-1：
     *    │   ├─ if path.contains(nums[i]) → continue
     *    │   ├─ path.add(nums[i])
     *    │   ├─ backtrack(nums, path, res)
     *    │   └─ path.remove(path.size()-1)
     *    └─ return
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>contains 检查</b>：必须检查元素是否已在路径中，避免重复选择</li>
     *   <li><b>循环起始</b>：从 0 开始，不是从 startIndex 开始</li>
     *   <li><b>终止条件</b>：路径长度等于数组长度时找到排列</li>
     *   <li><b>回溯操作</b>：递归返回后必须撤销选择</li>
     *   <li><b>深拷贝</b>：添加到结果集时使用 new ArrayList(path)</li>
     * </ul>
     *
     * @param nums 不含重复数字的数组
     * @return 所有可能的全排列
     */
    public List<List<Integer>> permute(int[] nums) {
        // 📦 创建结果列表
        List<List<Integer>> res = new ArrayList<>();
        // 📋 创建当前路径列表
        List<Integer> path = new ArrayList<>();

        // 🔄 开始回溯搜索
        backtrack(nums, path, res);
        return res;
    }

    /**
     * 回溯搜索所有排列
     *
     * @param nums 输入数组
     * @param path 当前已选择的路径
     * @param res  结果集
     */
    private void backtrack(int[] nums, List<Integer> path, List<List<Integer>> res) {
        // 🎯 终止条件：路径已满，找到一个完整的排列
        if (path.size() == nums.length) {
            // 📝 添加当前排列的拷贝（深拷贝）
            res.add(new ArrayList<>(path));
            return;
        }

        // 🔄 遍历所有可能的数字选择
        for (int i = 0; i < nums.length; i++) {
            // 🔍 如果数字已在路径中，跳过（避免重复）
            if (path.contains(nums[i])) {
                continue;
            }

            // ➕ 选择当前数字
            path.add(nums[i]);

            // 🔄 递归搜索
            backtrack(nums, path, res);

            // ➖ 撤销选择，回溯到上一个状态
            path.remove(path.size() - 1);
        }
    }
}

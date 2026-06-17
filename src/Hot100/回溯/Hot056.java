package Hot100.回溯;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot056 {
    /**
     * <a href="https://leetcode.cn/problems/subsets/description/">78. 子集</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个整数数组 nums，数组中的元素互不相同。请你返回该数组所有可能的子集（幂集）。</p>
     * <p>解集不能包含重复的子集。你可以按任意顺序返回解集。</p>
     *
     * <h3>💡 核心思路：回溯算法 + 起始位置控制</h3>
     * <ul>
     *   <li><b>基本思想</b>：深度优先搜索 + 回溯，遍历所有可能的子集
     *     <ul>
     *       <li>每个元素都有选或不选两种状态</li>
     *       <li>从空集开始，依次决定是否包含当前元素</li>
     *       <li>通过 startIndex 控制搜索范围，避免重复</li>
     *       <li>每层递归都会添加当前路径作为一个子集</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么先添加再递归</b>：
     *     <ul>
     *       <li>子集问题要求收集所有可能的子集</li>
     *       <li>每个递归层次的状态都是一个子集</li>
     *       <li>所以在递归之前就要添加当前路径</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n × 2^n)，每个元素有两种选择，共 2^n 个子集</li>
     *   <li><b>空间复杂度</b>：O(n)，递归栈深度</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [1, 2, 3]
     *
     * 【回溯树】
     *
     *                    []           ← 添加 []
     *            /       |       \
     *           1        2        3      ← 添加 [1], [2], [3]
     *         /  \      |
     *       12    13    23              ← 添加 [1,2], [1,3], [2,3]
     *       |      |    |
     *      123   123   123              ← 添加 [1,2,3]
     *
     * 【递归搜索过程】
     *
     * backtrack(0, [])
     *   ├─ 添加当前路径：[] ✅
     *   │
     *   ├─ i = 0, 选择 1
     *   │   backtrack(1, [1])
     *   │   ├─ 添加当前路径：[1] ✅
     *   │   │
     *   │   ├─ i = 1, 选择 2
     *   │   │   backtrack(2, [1, 2])
     *   │   │   ├─ 添加当前路径：[1, 2] ✅
     *   │   │   │
     *   │   │   ├─ i = 2, 选择 3
     *   │   │   │   backtrack(3, [1, 2, 3])
     *   │   │   │   ├─ 添加当前路径：[1, 2, 3] ✅
     *   │   │   │   ├─ i = 3 >= 3，循环结束
     *   │   │   │   └─ return
     *   │   │   │
     *   │   │   └─ 回溯：移除 3
     *   │   │
     *   │   ├─ i = 2, 选择 3
     *   │   │   backtrack(3, [1, 3])
     *   │   │   ├─ 添加当前路径：[1, 3] ✅
     *   │   │   ├─ i = 3 >= 3，循环结束
     *   │   │   └─ return
     *   │   │
     *   │   └─ 回溯：移除 2
     *   │
     *   ├─ i = 1, 选择 2
     *   │   backtrack(2, [2])
     *   │   ├─ 添加当前路径：[2] ✅
     *   │   │
     *   │   ├─ i = 2, 选择 3
     *   │   │   backtrack(3, [2, 3])
     *   │   │   ├─ 添加当前路径：[2, 3] ✅
     *   │   │   ├─ i = 3 >= 3，循环结束
     *   │   │   └─ return
     *   │   │
     *   │   └─ 回溯
     *   │
     *   ├─ i = 2, 选择 3
     *   │   backtrack(3, [3])
     *   │   ├─ 添加当前路径：[3] ✅
     *   │   ├─ i = 3 >= 3，循环结束
     *   │   └─ return
     *   │
     *   └─ 回溯
     *
     * 【最终结果】
     *
     * res = [[], [1], [1, 2], [1, 2, 3], [1, 3], [2], [2, 3], [3]]
     *
     * 总共 2^3 = 8 个子集 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要先添加再递归？
     *    - 子集问题要求收集所有可能的子集
     *    - 每个递归层次的状态都是一个子集
     *    - 空集 [] 也是子集，必须先添加
     *    - 这与排列问题不同，排列只收集叶子节点
     *
     * 2️⃣ startIndex 的作用是什么？
     *    - 控制搜索的起始位置
     *    - 保证只向后选择，不回头
     *    - 避免产生重复的子集
     *    - 例如：选了 1 才能选 2，不能先选 2 再选 1
     *
     * 3️⃣ 为什么是 i + 1 而不是 0？
     *    - 如果从 0 开始，会产生重复
     *    - 例如：[1, 2] 和 [2, 1] 都会被生成
     *    - 使用 startIndex 确保不回头
     *    - 保证每个子集按递增顺序选择元素
     *
     * 4️⃣ 什么时候添加子集？
     *    - 在每层递归开始时添加
     *    - 这确保了所有可能的子集都被收集
     *    - 包括空集、单元素、多元素子集
     *
     * 5️⃣ 回溯操作是什么？
     *    - path.add(nums[i])：添加元素
     *    - backtrack(nums, i+1, path, res)：递归
     *    - path.remove(...)：移除元素
     *    - 撤销选择回到上一个状态
     *
     * 6️⃣ 为什么不需要终止条件？
     *    - 子集问题没有明确的终止条件
     *    - 当 startIndex >= nums.length 时循环自然结束
     *    - 所有可能的子集都已被添加
     *    - 不需要额外判断
     *
     * 7️⃣ 时间复杂度如何分析？
     *    - 每个元素有两种选择：选或不选
     *    - 总共有 2^n 个子集
     *    - 每个子集添加需要 O(n) 时间
     *    - 时间复杂度 O(n × 2^n)
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ res = []（结果集）
     *    ├─ path = []（当前路径）
     *    └─ 调用 backtrack(nums, 0, path, res)
     *
     *    回溯函数 backtrack(nums, startIndex, path, res)：
     *    ├─ res.add(new ArrayList(path))（添加当前子集）
     *    ├─ for i in startIndex..n-1：
     *    │   ├─ path.add(nums[i])
     *    │   ├─ backtrack(nums, i+1, path, res)
     *    │   └─ path.remove(path.size()-1)
     *    └─ return
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>先添加再递归</b>：每个递归状态都是一个子集，必须先添加</li>
     *   <li><b>startIndex 控制</b>：使用 i+1 作为下一个 startIndex，避免重复</li>
     *   <li><b>无终止条件</b>：不需要显式终止条件，循环自然结束</li>
     *   <li><b>深拷贝</b>：添加到结果集时使用 new ArrayList(path)</li>
     *   <li><b>空集处理</b>：空集是有效的子集，初始递归就会添加</li>
     * </ul>
     *
     * @param nums 互不相同的整数数组
     * @return 所有可能的子集
     */
    public List<List<Integer>> subsets(int[] nums) {
        // 📦 创建结果列表
        List<List<Integer>> res = new ArrayList<>();
        // 📋 创建当前路径列表
        List<Integer> path = new ArrayList<>();

        // 🔄 开始回溯搜索，从索引 0 开始
        backtrack(nums, 0, path, res);
        return res;
    }

    /**
     * 回溯搜索所有子集
     *
     * @param nums       输入数组
     * @param startIndex 搜索起始位置
     * @param path       当前已选择的路径
     * @param res        结果集
     */
    private void backtrack(int[] nums, int startIndex, List<Integer> path, List<List<Integer>> res) {
        // 📝 添加当前路径作为一个子集
        // 每个递归状态都是一个子集，必须先添加
        res.add(new ArrayList<>(path));

        // 🔄 遍历所有可能的下一个选择
        for (int i = startIndex; i < nums.length; i++) {
            // ➕ 选择当前元素
            path.add(nums[i]);

            // 🔄 递归搜索，下一个选择从 i+1 开始
            backtrack(nums, i + 1, path, res);

            // ➖ 撤销选择，回溯到上一个状态
            path.remove(path.size() - 1);
        }
    }
}

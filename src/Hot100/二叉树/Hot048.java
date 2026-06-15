package Hot100.二叉树;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxd
 **/
public class Hot048 {
    /**
     * <a href="https://leetcode.cn/problems/path-sum-iii/description/">437. 路径总和 III</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的路径数目。</p>
     * <p>路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。</p>
     *
     * <h3>💡 核心思路：前缀和 + DFS + 回溯</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用前缀和记录从根到当前节点的路径和，快速计算满足条件的路径数
     *     <ul>
     *       <li>前缀和：从根节点到当前节点的路径和</li>
     *       <li>如果 curSum - target 存在于前缀和中，说明存在满足条件的路径</li>
     *       <li>使用哈希表记录前缀和出现的次数</li>
     *       <li>回溯时需要恢复哈希表状态</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用前缀和</b>：
     *     <ul>
     *       <li>将问题转化为两数之和问题</li>
     *       <li>时间复杂度从 O(n²) 优化到 O(n)</li>
     *       <li>避免重复计算路径和</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个节点访问一次</li>
     *   <li><b>空间复杂度</b>：O(n)，哈希表和递归栈</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：
     *       10
     *      /  \
     *     5   -3
     *    / \    \
     *   3   2   11
     *  / \   \
     * 3  -2   1
     *
     * targetSum = 8
     *
     * 【前缀和计算过程】
     *
     * 初始：prefixMap = {0: 1}, preSum = 0, count = 0
     *
     * DFS(10):
     *   curSum = 0 + 10 = 10
     *   need = curSum - target = 10 - 8 = 2
     *   map.get(2) = 0 → 新增路径数 0
     *   map.put(10, 1) → prefixMap = {0:1, 10:1}
     *
     *   DFS(5):
     *     curSum = 10 + 5 = 15
     *     need = 15 - 8 = 7
     *     map.get(7) = 0 → 新增路径数 0
     *     map.put(15, 1) → prefixMap = {0:1, 10:1, 15:1}
     *
     *     DFS(3):
     *       curSum = 15 + 3 = 18
     *       need = 18 - 8 = 10
     *       map.get(10) = 1 → 新增路径数 1 ✅（10→5→3）
     *       map.put(18, 1) → prefixMap = {0:1, 10:1, 15:1, 18:1}
     *
     *       DFS(3):
     *         curSum = 18 + 3 = 21
     *         need = 21 - 8 = 13
     *         map.get(13) = 0 → 新增路径数 0
     *         map.put(21, 1)
     *         左右子树为空，返回 0
     *         map.put(21, 0)（回溯）
     *       返回 0
     *
     *       DFS(-2):
     *         curSum = 18 + (-2) = 16
     *         need = 16 - 8 = 8
     *         map.get(8) = 0 → 新增路径数 0
     *         返回 0
     *       返回 0
     *       map.put(18, 0)（回溯）
     *     返回 1
     *
     *     DFS(2):
     *       curSum = 15 + 2 = 17
     *       need = 17 - 8 = 9
     *       map.get(9) = 0 → 新增路径数 0
     *       ...
     *       DFS(1):
     *         curSum = 17 + 1 = 18
     *         need = 18 - 8 = 10
     *         map.get(10) = 1 → 新增路径数 1 ✅（10→5→2→1）
     *       返回 1
     *       map.put(17, 0)（回溯）
     *     返回 1
     *     map.put(15, 0)（回溯）
     *   返回 1 + 1 = 2
     *
     *   DFS(-3):
     *     curSum = 10 + (-3) = 7
     *     need = 7 - 8 = -1
     *     map.get(-1) = 0 → 新增路径数 0
     *     ...
     *     DFS(11):
     *       curSum = 7 + 11 = 18
     *       need = 18 - 8 = 10
     *       map.get(10) = 1 → 新增路径数 1 ✅（10→-3→11）
     *     返回 1
     *     map.put(7, 0)（回溯）
     *   返回 1
     *   map.put(10, 0)（回溯）
     *
     * 总路径数：2 + 1 = 3 ✅
     *
     * 【满足条件的路径】
     * 1. 5 → 3（和为 8）
     * 2. 5 → 2 → 1（和为 8）
     * 3. -3 → 11（和为 8）
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 前缀和的定义是什么？
     *    - 从根节点到当前节点的路径和
     *    - 如果 curSum - target 存在于前缀和中
     *    - 说明存在某个祖先节点到当前节点的路径和为 target
     *
     * 2️⃣ 为什么初始时 put(0, 1)？
     *    - 处理路径从根节点开始的情况
     *    - 例如 curSum = target 时，curSum - target = 0
     *    - 需要 map.get(0) = 1 才能正确计数
     *
     * 3️⃣ 为什么需要回溯？
     *    - 不同路径的前缀和是相互独立的
     *    - 回溯时需要移除当前路径的前缀和
     *    - 避免影响其他分支的计算
     *
     * 4️⃣ 回溯的顺序是什么？
     *    - 先递归左子树
     *    - 再递归右子树
     *    - 最后将当前前缀和计数减 1
     *    - 恢复哈希表状态
     *
     * 5️⃣ 路径数如何累加？
     *    - 当前路径贡献：map.getOrDefault(curSum - target, 0)
     *    - 左子树贡献：preOrder(left)
     *    - 右子树贡献：preOrder(right)
     *    - 总和 = 当前 + 左 + 右
     *
     * 6️⃣ 为什么用 long 类型？
     *    - 防止整数溢出
     *    - 节点值可能很大或很多层
     *    - 用 long 更安全
     *
     * 7️⃣ 与两数之和的联系？
     *    - 前缀和数组相当于 nums 数组
     *    - target 相当于 target
     *    - 找两个前缀和之差等于 target
     *    - 哈希表记录前缀和出现次数
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ 创建前缀和哈希表
     *    ├─ map.put(0, 1)（初始条件）
     *    └─ 调用 preOrder(root, map, 0, target)
     *
     *    递归函数 preOrder(node, map, preSum, target)：
     *    ├─ base case: node == null → return 0
     *    ├─ curSum = preSum + node.val
     *    ├─ need = curSum - target
     *    ├─ count = map.getOrDefault(need, 0)
     *    ├─ map.put(curSum, map.getOrDefault(curSum, 0) + 1)
     *    ├─ leftCount = preOrder(node.left, map, curSum, target)
     *    ├─ rightCount = preOrder(node.right, map, curSum, target)
     *    ├─ total = count + leftCount + rightCount
     *    ├─ map.put(curSum, map.get(curSum) - 1)（回溯）
     *    └─ return total
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>初始条件</b>：必须 put(0, 1)，处理从根开始的路径</li>
     *   <li><b>回溯操作</b>：递归返回前必须将当前前缀和计数减 1</li>
     *   <li><b>数据类型</b>：使用 long 避免整数溢出</li>
     *   <li><b>路径方向</b>：只能从父节点到子节点，不能向上</li>
     *   <li><b>哈希表状态</b>：回溯时必须恢复，避免跨分支污染</li>
     * </ul>
     *
     * @param root      二叉树的根节点
     * @param targetSum 目标路径和
     * @return 满足条件的路径数目
     */
    public int pathSum(TreeNode root, int targetSum) {
        // 📦 创建前缀和哈希表，记录前缀和出现的次数
        Map<Long, Integer> prefixMap = new HashMap<>();
        // ⚠️ 初始条件：前缀和为 0 的路径有 1 条（空路径）
        prefixMap.put(0L, 1);
        // 🔄 进行深度优先搜索，计算路径数
        return preOrder(root, prefixMap, 0L, targetSum);
    }

    /**
     * 先序遍历，计算满足条件的路径数
     *
     * @param node   当前节点
     * @param map    前缀和哈希表
     * @param preSum 到达当前节点前的前缀和
     * @param target 目标路径和
     * @return 以当前节点为终点的满足条件的路径数
     */
    private int preOrder(TreeNode node, Map<Long, Integer> map, long preSum, int target) {
        // 🎯 基本情况：空节点，没有路径
        if (node == null) {
            return 0;
        }

        // 📊 计算当前节点的前缀和（从根到当前节点）
        long curSum = preSum + node.val;

        // 🔍 查找需要的前缀和：curSum - target
        // 如果存在，说明存在满足条件的路径
        int need = map.getOrDefault(curSum - target, 0);

        // 📝 将当前前缀和加入哈希表（计数加 1）
        map.put(curSum, map.getOrDefault(curSum, 0) + 1);

        // 🔄 递归计算左子树的路径数
        int leftCnt = preOrder(node.left, map, curSum, target);
        // 🔄 递归计算右子树的路径数
        int rightCnt = preOrder(node.right, map, curSum, target);

        // 📊 总路径数 = 当前路径贡献 + 左子树贡献 + 右子树贡献
        int total = need + leftCnt + rightCnt;

        // 🧹 回溯：将当前前缀和计数减 1，恢复哈希表状态
        map.put(curSum, map.get(curSum) - 1);

        // ✅ 返回总路径数
        return total;
    }
}

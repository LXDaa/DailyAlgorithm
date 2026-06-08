package Hot100.二叉树;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author lxd
 **/
public class Hot037 {
    /**
     * <a href="https://leetcode.cn/problems/maximum-depth-of-binary-tree/description/">104. 二叉树的最大深度</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个二叉树 root ，返回其最大深度。最大深度是根节点到最远叶子节点的最长路径上的节点数。叶子节点是没有子节点的节点。</p>
     *
     * <h3>💡 核心思路：两种解法对比</h3>
     * <ul>
     *   <li><b>方法一：BFS（广度优先搜索）</b>
     *     <ul>
     *       <li>使用队列逐层遍历</li>
     *       <li>每遍历完一层，深度加1</li>
     *       <li>空间复杂度较高，但结果直观</li>
     *     </ul>
     *   </li>
     *   <li><b>方法二：DFS（深度优先搜索）</b>
     *     <ul>
     *       <li>递归计算左右子树的深度</li>
     *       <li>取较大值并加1（当前层）</li>
     *       <li>空间复杂度低（递归栈）</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个节点访问一次</li>
     *   <li><b>空间复杂度</b>：O(n)，队列或递归栈的空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例二叉树：
     *         3
     *        / \
     *       9   20
     *          /  \
     *         15   7
     *
     * 【BFS 遍历过程】
     *
     * 初始状态：
     *   queue: [3]          // 根节点入队
     *   deep = 0
     *
     * 第1层：deep = 1
     *   queue: [9, 20]      // 取出3，加入子节点9和20
     *   deep = 1
     *
     * 第2层：deep = 2
     *   queue: [15, 7]      // 取出9（无子节点），取出20，加入15和7
     *   deep = 2
     *
     * 第3层：deep = 3
     *   queue: []           // 取出15、7（都是叶子节点）
     *   deep = 3
     *
     * 最终结果：deep = 3 ✅
     *
     * ---
     *
     * 【DFS 递归过程】
     *
     * maxDepth(3)
     *   ├─ maxDepth(9)              // 左子树
     *   │   ├─ maxDepth(null) = 0   // 左子树为空
     *   │   ├─ maxDepth(null) = 0   // 右子树为空
     *   │   └─ return max(0, 0) + 1 = 1  // 叶子节点
     *   │
     *   └─ maxDepth(20)             // 右子树
     *       ├─ maxDepth(15)         // 左子树
     *       │   ├─ maxDepth(null) = 0
     *       │   ├─ maxDepth(null) = 0
     *       │   └─ return max(0, 0) + 1 = 1
     *       │
     *       └─ maxDepth(7)          // 右子树
     *           ├─ maxDepth(null) = 0
     *           ├─ maxDepth(null) = 0
     *           └─ return max(0, 0) + 1 = 1
     *
     * 计算：
     * maxDepth(20) = max(1, 1) + 1 = 2
     * maxDepth(3) = max(1, 2) + 1 = 3
     *
     * 最终结果：3 ✅
     *
     * ---
     *
     * 【BFS vs DFS 对比】
     *
     * BFS（队列）：
     *   遍历顺序：3 → 9 → 20 → 15 → 7
     *   优点：直观，逐层计数
     *   缺点：需要队列，空间开销大
     *
     * DFS（递归）：
     *   遍历顺序：9 → 3 → 15 → 20 → 7
     *   优点：空间效率高
     *   缺点：需要理解递归思想
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ BFS 中为什么要记录每层的 size？
     *    - 队列中的节点是动态变化的
     *    - 如果在循环中直接用 queue.size()，会不断变化
     *    - 先记录 size，然后 for 循环处理这一层
     *    - 保证每层只处理 size 个节点
     *
     * 2️⃣ BFS 的队列操作流程是什么？
     *    - 入队：offer() - 添加节点到队尾
     *    - 出队：poll() - 移除队首节点
     *    - 记录层数：每处理完一层，deep++
     *
     * 3️⃣ DFS 递归公式是什么？
     *    - maxDepth(node) = max(maxDepth(left), maxDepth(right)) + 1
     *    - base case: node == null → return 0
     *    - 含义：当前节点的深度 = max(左子树深度, 右子树深度) + 1
     *
     * 4️⃣ 为什么 DFS 比 BFS 空间效率高？
     *    - BFS：队列可能同时存储一层的所有节点，最坏 O(n/2) ≈ O(n)
     *    - DFS：递归栈深度等于树的高度，最坏 O(n)（链表形状）
     *    - 但平均情况下，树高远小于节点数
     *
     * 5️⃣ 如何选择 BFS 还是 DFS？
     *    - 需要最短路径 → BFS（如二叉树的最小深度）
     *    - 只求深度/高度 → DFS（代码更简洁）
     *    - 面试推荐 DFS，代码更优雅
     *
     * 6️⃣ 树的高度和深度有什么区别？
     *    - 深度：根节点到当前节点的路径长度（根=0 或 1）
     *    - 高度：当前节点到最深叶子节点的路径长度（叶子=0 或 1）
     *    - 本题求的是根节点的高度，即最大深度
     *
     * 7️⃣ BFS 代码的关键步骤？
     *    - 入队根节点
     *    - while 循环遍历队列
     *    - 每轮循环开始前记录当前层大小
     *    - for 循环处理当前层所有节点
     *    - 将子节点加入队列
     *    - 每层结束后 deep++
     *
     * 8️⃣ DFS 代码的递归终止条件？
     *    - if (root == null) return 0
     *    - 空节点深度为 0
     *    - 这是递归的 base case
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>BFS 层数计数</b>：在循环开始时记录 size，而不是在条件判断中</li>
     *   <li><b>BFS deep++ 时机</b>：每处理完一层后 deep++</li>
     *   <li><b>DFS base case</b>：空节点返回 0，不能返回 -1</li>
     *   <li><b>深度起点</b>：根节点深度为 1（按题目定义）</li>
     *   <li><b>叶子节点</b>：没有子节点的节点，左右都为空</li>
     * </ul>
     *
     * @param root 二叉树的根节点
     * @return 最大深度
     */
    public int maxDepth(TreeNode root) {
        return BFS(root);
//        return DFS(root);
    }

    /**
     * DFS 深度优先搜索求最大深度
     *
     * @param root 二叉树根节点
     * @return 最大深度
     */
    private int DFS(TreeNode root) {
        // 🎯 基本情况：空节点，深度为 0
        if (root == null) {
            return 0;
        }
        // 🔄 递归计算左右子树的深度，取较大值并加 1
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * BFS 广度优先搜索求最大深度
     *
     * @param root 二叉树根节点
     * @return 最大深度
     */
    private int BFS(TreeNode root) {
        // 🎯 基本情况：空节点，深度为 0
        if (root == null) {
            return 0;
        }

        // 📦 创建队列，用于层序遍历
        Queue<TreeNode> queue = new LinkedList<>();
        // ➕ 将根节点入队
        queue.offer(root);
        // 📏 深度计数器
        int deep = 0;

        // 🔄 层序遍历：每次处理一层
        while (!queue.isEmpty()) {
            // 📈 层数加 1（每循环一次代表一层）
            deep++;
            // 📏 获取当前层的节点数量
            int size = queue.size();

            // 🔄 处理当前层的所有节点
            for (int i = 0; i < size; i++) {
                // 🎯 取出队首节点
                TreeNode node = queue.poll();

                // ➕ 将左子节点入队（如果存在）
                if (node.left != null) {
                    queue.offer(node.left);
                }
                // ➕ 将右子节点入队（如果存在）
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        // ✅ 返回最大深度
        return deep;
    }
}

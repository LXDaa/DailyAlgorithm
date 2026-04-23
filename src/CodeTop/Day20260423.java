package CodeTop;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <a href="https://leetcode.cn/problems/maximum-depth-of-binary-tree/description/">104. 二叉树的最大深度</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>给定一个二叉树 root，返回其最大深度。</p>
 * <p><b>最大深度</b>是指从根节点到最远叶子节点的最长路径上的节点数。</p>
 *
 * <h3>💡 核心思路：两种经典解法</h3>
 * <ul>
 *   <li><b>解法1：递归（DFS）</b>
 *     <ul>
 *       <li><b>思想</b>：树的最大深度 = max(左子树深度, 右子树深度) + 1</li>
 *       <li><b>递归基</b>：空节点深度为 0</li>
 *       <li><b>优点</b>：代码简洁，直观易懂</li>
 *       <li><b>缺点</b>：递归栈可能较深</li>
 *     </ul>
 *   </li>
 *   <li><b>解法2：迭代（BFS / 层序遍历）</b>
 *     <ul>
 *       <li><b>思想</b>：使用队列进行层序遍历，每遍历一层，深度 +1</li>
 *       <li><b>关键</b>：每次处理完当前层的所有节点，ans++</li>
 *       <li><b>优点</b>：避免递归栈溢出</li>
 *       <li><b>缺点</b>：代码稍复杂，需要额外队列空间</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>解法1（DFS）</b>：
 *     <ol>
 *       <li>如果 root 为空，返回 0</li>
 *       <li>递归计算左子树深度：left = maxDepth(root.left)</li>
 *       <li>递归计算右子树深度：right = maxDepth(root.right)</li>
 *       <li>返回 max(left, right) + 1</li>
 *     </ol>
 *   </li>
 *   <li><b>解法2（BFS）</b>：
 *     <ol>
 *       <li>如果 root 为空，返回 0</li>
 *       <li>初始化队列，将根节点入队</li>
 *       <li>当队列不为空时：
 *         <ul>
 *           <li>获取当前层的节点数 size</li>
 *           <li>依次弹出 size 个节点，将它们的子节点入队</li>
 *           <li>处理完一层后，ans++</li>
 *         </ul>
 *       </li>
 *       <li>返回 ans</li>
 *     </ol>
 *   </li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例：
 *        3
 *       / \
 *      9   20
 *         /  \
 *        15   7
 *
 * 最大深度 = 3
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 【解法1：DFS 递归展开】
 *
 * maxDepth(3)
 *   ├── left = maxDepth(9)
 *   │          ├── left = maxDepth(null) = 0
 *   │          ├── right = maxDepth(null) = 0
 *   │          └── 返回 max(0, 0) + 1 = 1
 *   │
 *   ├── right = maxDepth(20)
 *   │           ├── left = maxDepth(15)
 *   │           │          ├── left = maxDepth(null) = 0
 *   │           │          ├── right = maxDepth(null) = 0
 *   │           │          └── 返回 max(0, 0) + 1 = 1
 *   │           │
 *   │           ├── right = maxDepth(7)
 *   │           │           ├── left = maxDepth(null) = 0
 *   │           │           ├── right = maxDepth(null) = 0
 *   │           │           └── 返回 max(0, 0) + 1 = 1
 *   │           │
 *   │           └── 返回 max(1, 1) + 1 = 2
 *   │
 *   └── 返回 max(1, 2) + 1 = 3 ✅
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 【解法2：BFS 层序遍历】
 *
 * 初始: queue = [3], ans = 0
 *
 * 第1层:
 *   size = 1
 *   弹出 3, 加入 9, 20
 *   queue = [9, 20]
 *   ans = 1
 *
 * 第2层:
 *   size = 2
 *   弹出 9, 无子节点
 *   弹出 20, 加入 15, 7
 *   queue = [15, 7]
 *   ans = 2
 *
 * 第3层:
 *   size = 2
 *   弹出 15, 无子节点
 *   弹出 7, 无子节点
 *   queue = []
 *   ans = 3
 *
 * 队列空，结束
 * 返回 ans = 3 ✅
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 层序可视化:
 *   Level 1:    3        → ans = 1
 *              / \
 *   Level 2:  9   20     → ans = 2
 *                / \
 *   Level 3:  15   7     → ans = 3
 *
 * 最大深度 = 3 ✅
 * </pre>
 *
 * <h3>🔑 关键技巧解析</h3>
 * <pre>
 * 1️⃣ DFS 为什么是 max(left, right) + 1？
 *    - 当前节点的深度 = 左右子树中较深的深度 + 1（自己这一层）
 *    - 例如：左子树深度 2，右子树深度 3
 *    - 当前节点深度 = max(2, 3) + 1 = 4
 *
 * 2️⃣ BFS 为什么要记录 size？
 *    - size 是当前层的节点数
 *    - 只有处理完当前层的所有节点，才能 ans++
 *    - 如果不记录 size，无法区分不同层的节点
 *
 * 3️⃣ DFS vs BFS 的选择？
 *    - DFS：代码简洁，适合理解递归思想
 *    - BFS：避免递归栈溢出，适合非常深的树
 *    - 时间复杂度相同：O(n)
 *    - 空间复杂度：DFS O(h)，BFS O(w)
 *      · h = 树高，w = 树的最大宽度
 *
 * 4️⃣ 时间复杂度：O(n)
 *    - n 为节点总数
 *    - 每个节点访问一次
 *
 * 5️⃣ 空间复杂度：
 *    - DFS：O(h)，h 为树高（递归栈深度）
 *      · 最坏情况（链表）：O(n)
 *      · 最好情况（平衡树）：O(log n)
 *    - BFS：O(w)，w 为树的最大宽度
 *      · 最坏情况（完全二叉树最后一层）：O(n/2) = O(n)
 *
 * 6️⃣ 递归基为什么返回 0？
 *    - 空节点没有深度
 *    - 叶子节点的左右子节点都是 null，返回 0
 *    - 叶子节点自身深度 = max(0, 0) + 1 = 1 ✅
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>空树处理</b>：root 为 null 时返回 0</li>
 *   <li><b>DFS 递归深度</b>：对于非常深的树，可能导致栈溢出，此时用 BFS</li>
 *   <li><b>BFS 队列操作</b>：使用 LinkedList 实现 Queue 接口</li>
 *   <li><b>层序遍历技巧</b>：先记录 size，再循环 size 次，确保按层处理</li>
 *   <li><b>节点计数</b>：题目要求的是节点数，不是边数</li>
 *   <li><b>两种解法等价</b>：DFS 和 BFS 结果相同，只是实现方式不同</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260423 {
    /**
     * 🌲 解法1：递归（DFS）计算二叉树的最大深度
     *
     * @param root 二叉树根节点
     * @return 最大深度
     */
    public int maxDepth(TreeNode root) {
        // 🛑 递归基：空节点深度为 0
        if (root == null) {
            return 0;
        }

        // 🔄 递归计算左右子树的深度
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);

        // 📏 当前节点深度 = 较深的子树深度 + 1
        return Math.max(left, right) + 1;
    }

    /**
     * 🌊 解法2：迭代（BFS / 层序遍历）计算二叉树的最大深度
     *
     * @param root 二叉树根节点
     * @return 最大深度
     */
    public int maxDepthTwo(TreeNode root) {
        // 🛑 边界条件：空树深度为 0
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();  // 🚶 队列：用于层序遍历
        queue.offer(root);  // 📥 根节点入队
        int ans = 0;  // 📊 记录深度

        // 🔄 层序遍历主循环
        while (!queue.isEmpty()) {
            int size = queue.size();  // 📏 当前层的节点数

            // 处理当前层的所有节点
            while (size > 0) {
                TreeNode node = queue.poll();  // 📤 弹出节点

                // 👈 左子节点入队
                if (node.left != null) {
                    queue.offer(node.left);
                }

                // 👉 右子节点入队
                if (node.right != null) {
                    queue.offer(node.right);
                }

                size--;
            }

            // ✅ 处理完一层，深度 +1
            ans++;
        }

        return ans;
    }
}

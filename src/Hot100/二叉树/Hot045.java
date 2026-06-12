package Hot100.二叉树;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author lxd
 **/
public class Hot045 {
    /**
     * <a href="https://leetcode.cn/problems/binary-tree-right-side-view/description/">199. 二叉树的右视图</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个二叉树的根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。</p>
     * <p>右视图就是每层最右边的节点组成的列表。</p>
     *
     * <h3>💡 核心思路：BFS 层序遍历 + 记录每层最后一个节点</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用队列进行层序遍历，每层遍历结束时记录最后一个节点
     *     <ul>
     *       <li>使用队列逐层遍历二叉树</li>
     *       <li>每层遍历过程中，最后一个访问的节点就是右视图节点</li>
     *       <li>将该节点的值加入结果列表</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用 BFS</b>：
     *     <ul>
     *       <li>层序遍历天然适合处理"每层"的问题</li>
     *       <li>可以方便地获取每层的节点数量</li>
     *       <li>最后一个节点就是右视图节点</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个节点访问一次</li>
     *   <li><b>空间复杂度</b>：O(n)，队列最多存储一层的节点</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例二叉树：
     *       1            ← 第1层右视图
     *      / \
     *     2   3          ← 第2层右视图
     *      \   \
     *       5   4        ← 第3层右视图
     *
     * 【BFS 遍历过程】
     *
     * 初始状态：
     *   queue: [1]
     *   ans: []
     *
     * 第1层处理：
     *   size = 1
     *   for i = 0:
     *     cur = 1
     *     添加子节点：queue = [2, 3]
     *   循环结束，cur = 1
     *   ans.add(1)
     *   ans: [1]
     *
     * 第2层处理：
     *   size = 2
     *   for i = 0:
     *     cur = 2
     *     添加子节点：queue = [3, 5]
     *   for i = 1:
     *     cur = 3
     *     添加子节点：queue = [5, 4]
     *   循环结束，cur = 3
     *   ans.add(3)
     *   ans: [1, 3]
     *
     * 第3层处理：
     *   size = 2
     *   for i = 0:
     *     cur = 5
     *     无子节点
     *   for i = 1:
     *     cur = 4
     *     无子节点
     *   循环结束，cur = 4
     *   ans.add(4)
     *   ans: [1, 3, 4]
     *
     * 最终结果：[1, 3, 4] ✅
     *
     * ---
     *
     * 【右视图节点识别】
     *
     *       1            ← 右视图节点（第1层最后）
     *      / \
     *     2   3          ← 右视图节点（第2层最后）
     *      \   \
     *       5   4        ← 右视图节点（第3层最后）
     *
     * 关键：每层 for 循环结束时，cur 就是该层最后一个节点
     *
     * ---
     *
     * 【与层序遍历的区别】
     *
     * 层序遍历（Hot041）：
     *   - 记录每层的所有节点
     *   - 返回二维列表
     *   - 需要每层创建新列表
     *
     * 右视图（Hot045）：
     *   - 只记录每层的最后一个节点
     *   - 返回一维列表
     *   - 直接在 for 循环结束后添加
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么 cur 要在 for 循环外定义？
     *    - for 循环结束后需要访问 cur
     *    - 如果在循环内定义，作用域受限
     *    - 循环结束时 cur 就是该层最后一个节点
     *
     * 2️⃣ 如何保证 cur 是每层最后一个节点？
     *    - for 循环遍历当前层的所有节点
     *    - 每次迭代都更新 cur
     *    - 循环结束时，cur 就是最后访问的节点
     *
     * 3️⃣ 为什么要记录 size？
     *    - 队列大小在循环中会变化
     *    - 先记录当前层的节点数
     *    - 确保只处理当前层的节点
     *
     * 4️⃣ 子节点入队顺序重要吗？
     *    - 重要！必须先左后右
     *    - 这样才能保证右边的节点在队列后面
     *    - 最后一个节点就是最右边的节点
     *
     * 5️⃣ 如果某层只有一个节点？
     *    - for 循环只执行一次
     *    - cur 就是该节点
     *    - 直接加入结果
     *
     * 6️⃣ 空树如何处理？
     *    - 直接返回空列表
     *    - 不需要特殊处理
     *
     * 7️⃣ 能否用 DFS 实现？
     *    - 可以，按"根-右-左"顺序遍历
     *    - 每层第一个访问的节点就是右视图节点
     *    - 需要记录当前深度
     *    - BFS 更直观，面试首选
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ 检查 root 是否为空
     *    ├─ 调用 bfs(root, ans)
     *    └─ 返回 ans
     *
     *    BFS 函数：
     *    ├─ queue = [root]
     *    ├─ while (!queue.isEmpty())：
     *    │   ├─ size = queue.size()
     *    │   ├─ cur = null
     *    │   ├─ for i in 0..size-1：
     *    │   │   ├─ cur = queue.poll()
     *    │   │   ├─ if cur.left → offer
     *    │   │   └─ if cur.right → offer
     *    │   └─ ans.add(cur.val)
     *    └─ 返回
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>变量作用域</b>：cur 必须在 for 循环外定义，否则循环后无法访问</li>
     *   <li><b>入队顺序</b>：必须先左后右，保证右边节点在队列后面</li>
     *   <li><b>size 记录</b>：必须在循环开始时记录，不能在条件中动态获取</li>
     *   <li><b>空树处理</b>：root 为空时返回空列表</li>
     *   <li><b>添加时机</b>：在 for 循环结束后添加，不是在循环中</li>
     * </ul>
     *
     * @param root 二叉树的根节点
     * @return 右视图节点值列表
     */
    public List<Integer> rightSideView(TreeNode root) {
        // 📦 创建结果列表
        List<Integer> ans = new ArrayList<>();
        // 🎯 边界检查：空树返回空列表
        if (root == null) {
            return ans;
        }
        // 🔄 进行层序遍历，记录右视图节点
        bfs(root, ans);
        // ✅ 返回右视图结果
        return ans;
    }

    /**
     * BFS 层序遍历，记录每层最后一个节点
     *
     * @param root 二叉树根节点
     * @param ans  结果列表
     */
    private void bfs(TreeNode root, List ans) {
        // 📦 创建队列，用于层序遍历
        Queue<TreeNode> queue = new LinkedList<>();
        // ➕ 将根节点入队
        queue.offer(root);

        // 🔄 层序遍历：每次处理一层
        while (!queue.isEmpty()) {
            // 📏 获取当前层的节点数量
            int size = queue.size();
            // 📍 当前节点（循环结束后就是该层最后一个节点）
            TreeNode cur = null;

            // 🔄 处理当前层的所有节点
            for (int i = 0; i < size; i++) {
                // 🎯 取出队首节点
                cur = queue.poll();

                // ➕ 将左子节点入队（如果存在）
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                // ➕ 将右子节点入队（如果存在）
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }

            // 📝 循环结束后，cur 就是该层最后一个节点，加入结果
            ans.add(cur.val);
        }
    }
}

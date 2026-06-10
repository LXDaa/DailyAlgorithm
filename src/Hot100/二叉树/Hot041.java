package Hot100.二叉树;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author lxd
 **/
public class Hot041 {
    /**
     * <a href="https://leetcode.cn/problems/binary-tree-level-order-traversal/description/">102. 二叉树的层序遍历</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你二叉树的根节点 root ，返回其节点值的层序遍历。（即逐层地，从左到右访问所有节点）。</p>
     * <p>返回结果是一个二维列表，每个子列表包含同一层的所有节点值。</p>
     *
     * <h3>💡 核心思路：BFS 层序遍历</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用队列实现逐层遍历
     *     <ul>
     *       <li>根节点入队</li>
     *       <li>每次处理一层的所有节点</li>
     *       <li>将子节点加入队列</li>
     *       <li>重复直到队列为空</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用 BFS</b>：
     *     <ul>
     *       <li>层序遍历需要按层次顺序访问节点</li>
     *       <li>BFS 的队列天然支持按层次处理</li>
     *       <li>可以方便地记录每层的节点数量</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个节点访问一次</li>
     *   <li><b>空间复杂度</b>：O(n)，队列最多存储一层的节点</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例二叉树：
     *       3
     *      / \
     *     9   20
     *        /  \
     *       15   7
     *
     * 【BFS 遍历过程】
     *
     * 初始状态：
     *   queue: [3]          // 根节点入队
     *   ret: []
     *
     * 第1层处理：
     *   size = 1
     *   取出 3，加入 level
     *   添加子节点 9, 20
     *   queue: [9, 20]
     *   level: [3]
     *   ret: [[3]]
     *
     * 第2层处理：
     *   size = 2
     *   取出 9，加入 level
     *   取出 20，加入 level
     *   添加子节点 15, 7（20的子节点）
     *   queue: [15, 7]
     *   level: [9, 20]
     *   ret: [[3], [9, 20]]
     *
     * 第3层处理：
     *   size = 2
     *   取出 15，加入 level
     *   取出 7，加入 level
     *   无子节点添加
     *   queue: []
     *   level: [15, 7]
     *   ret: [[3], [9, 20], [15, 7]]
     *
     * 最终结果：[[3], [9, 20], [15, 7]] ✅
     *
     * ---
     *
     * 【队列状态变化】
     *
     * 时间点          队列内容           当前层
     * ─────────────────────────────────────────
     * 初始            [3]                -
     * 第1层前         [9, 20]            [3]
     * 第2层前         [15, 7]            [9, 20]
     * 第3层前         []                 [15, 7]
     *
     * 【遍历顺序】
     * 3 → 9 → 20 → 15 → 7
     * (按层次从左到右)
     *
     * ---
     *
     * 【与 Hot037 的区别】
     *
     * Hot037（BFS求深度）：
     *   - 只记录层数，不记录每层的值
     *   - 只需要一个整数计数器
     *
     * Hot041（BFS层序遍历）：
     *   - 记录每层的所有值
     *   - 需要二维列表存储结果
     *   - 每层需要单独处理
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要先记录 size？
     *    - 队列大小在循环中会变化
     *    - 如果直接在 for 循环中用 queue.size()
     *    - 每处理一个节点后，队列可能变化
     *    - 先记录当前层的节点数，确保处理完整的一层
     *
     * 2️⃣ 为什么要创建 level 列表？
     *    - 需要按层存储节点值
     *    - 每层处理完后，添加到 ret
     *    - 然后创建新的 level 用于下一层
     *
     * 3️⃣ 子节点入队的顺序重要吗？
     *    - 顺序决定同层节点的左右顺序
     *    - 先左后右 → 层序遍历的左到右顺序
     *    - 如果顺序错误，结果顺序会改变
     *
     * 4️⃣ 队列为空时循环结束？
     *    - while (!queue.isEmpty())
     *    - 每处理完一层后，检查队列
     *    - 队列为空时表示所有层都已处理完毕
     *
     * 5️⃣ root 为空时返回什么？
     *    - 返回空列表 ret（不是 null）
     *    - 空列表长度为 0
     *    - 需要在循环前检查边界
     *
     * 6️⃣ 与 DFS 相比有什么优势？
     *    - BFS 按层次访问，逻辑直观
     *    - 队列实现简单，不易出错
     *    - DFS 需要额外记录层信息
     *
     * 7️⃣ 时间复杂度分析：
     *    - 每个节点入队一次
     *    - 每个节点出队一次
     *    - 每个节点值添加一次到 level
     *    - 总共 3n 次操作 → O(n)
     *
     * 8️⃣ 完整流程总结：
     *
     *    初始化：
     *    ├─ ret = []                    // 结果列表
     *    ├─ 检查 root 是否为空
     *    └─ queue = [root]              // 根节点入队
     *
     *    主循环 while (!queue.isEmpty())：
     *    ├─ size = queue.size()         // 当前层节点数
     *    ├─ level = []                  // 当前层值列表
     *    ├─ for i in 0..size-1：
     *    │   ├─ node = queue.poll()
     *    │   ├─ level.add(node.val)
     *    │   ├─ if node.left → offer
     *    │   └─ if node.right → offer
     *    └─ ret.add(level)
     *
     *    返回 ret
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>size 记录</b>：必须在循环开始时记录，而不是在条件判断中</li>
     *   <li><b>空树处理</b>：root 为空时返回空列表，不是 null</li>
     *   <li><b>子节点顺序</b>：先左后右，保证层序的左到右顺序</li>
     *   <li><b>返回类型</b>：返回 List<List<Integer>>，二维列表</li>
     *   <li><b>循环条件</b>：while (!queue.isEmpty())，队列为空时结束</li>
     * </ul>
     *
     * @param root 二叉树的根节点
     * @return 层序遍历结果，二维列表
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        // 📦 创建结果列表
        List<List<Integer>> ret = new ArrayList<>();
        // 🎯 边界检查：空树返回空列表
        if (root == null) {
            return ret;
        }

        // 📦 创建队列，用于层序遍历
        Queue<TreeNode> queue = new LinkedList<>();
        // ➕ 将根节点入队
        queue.offer(root);

        // 🔄 层序遍历：每次处理一层
        while (!queue.isEmpty()) {
            // 📏 获取当前层的节点数量
            int size = queue.size();
            // 📋 创建当前层的值列表
            List<Integer> level = new ArrayList<>();

            // 🔄 处理当前层的所有节点
            for (int i = 0; i < size; i++) {
                // 🎯 取出队首节点
                TreeNode node = queue.poll();
                // ➕ 将节点值加入当前层列表
                level.add(node.val);

                // ➕ 将左子节点入队（如果存在）
                if (node.left != null) {
                    queue.offer(node.left);
                }
                // ➕ 将右子节点入队（如果存在）
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            // 📝 将当前层的值列表加入结果
            ret.add(level);
        }
        // ✅ 返回层序遍历结果
        return ret;
    }
}

package CodeTop;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/binary-tree-inorder-traversal/description/">94. 二叉树的中序遍历</a>
 * <p>
 * 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
 *
 * <h2>解题思路</h2>
 * <h3>1. 中序遍历定义</h3>
 * <ul>
 *     <li>中序遍历（Inorder Traversal）是二叉树遍历的一种，也称为<strong>中根遍历</strong></li>
 *     <li>遍历顺序：<strong>左子树 → 根节点 → 右子树</strong></li>
 *     <li>对于二叉搜索树（BST），中序遍历会得到一个有序序列</li>
 * </ul>
 * <p>
 * <h3>2. 递归实现要点</h3>
 * <ul>
 *     <li><strong>终止条件</strong>：当前节点为 null 时，直接返回</li>
 *     <li><strong>递归左子树</strong>：先遍历左子树</li>
 *     <li><strong>访问根节点</strong>：将当前节点值加入结果集</li>
 *     <li><strong>递归右子树</strong>：最后遍历右子树</li>
 * </ul>
 * <p>
 * <h3>3. 算法示意图</h3>
 * <pre>
 *       1
 *        \
 *         2
 *        /
 *       3
 *
 * 中序遍历过程：
 * 1. 从根节点 1 开始
 * 2. 遍历 1 的左子树 → null，返回
 * 3. 访问根节点 1 → res = [1]
 * 4. 遍历 1 的右子树（节点 2）
 * 5. 遍历 2 的左子树（节点 3）
 * 6. 访问节点 3 → res = [1, 3]
 * 7. 访问根节点 2 → res = [1, 3, 2]
 * 8. 遍历 2 的右子树 → null，返回
 *
 * 最终结果：[1, 3, 2]
 * </pre>
 * <p>
 * <h3>4. 复杂度分析</h3>
 * <ul>
 *     <li><strong>时间复杂度</strong>：O(n)，其中 n 是二叉树的节点数，需要遍历所有节点</li>
 *     <li><strong>空间复杂度</strong>：O(h)，其中 h 是树的高度，递归调用栈的深度</li>
 *     <li>最坏情况（链状树）：O(n)</li>
 *     <li>最好情况（平衡树）：O(log n)</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260324 {
    public List<Integer> inorderTraversal(TreeNode root) {
        // 📦 创建结果列表，用于存储遍历结果
        List<Integer> res = new ArrayList<>();
        // 🔁 调用递归方法进行遍历
        inorder(root, res);
        // ✅ 返回最终的中序遍历结果
        return res;

    }

    private void inorder(TreeNode root, List<Integer> res) {
        // 🛑 终止条件：节点为空，直接返回
        if (root == null) {
            return;
        }
        // ⬅️ 递归左子树：先遍历左子树
        inorder(root.left, res);
        // 🎯 访问根节点：将当前节点值加入结果集
        res.add(root.val);
        // ➡️ 递归右子树：最后遍历右子树
        inorder(root.right, res);
    }
}

package CodeTop;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * <a href="https://leetcode.cn/problems/binary-tree-level-order-traversal/description/">102. 二叉树的层序遍历</a>
 * 给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
 * <p>
 * 解题思路：
 * 本题提供了两种解法：
 * 1. DFS递归解法：利用深度优先搜索，通过传递层级参数来确定每个节点应放入结果列表的哪一层
 * 2. BFS迭代解法：利用队列先进先出的特性，按层处理节点
 * <p>
 * DFS解法：
 * - 对于每个节点，根据当前层级level决定将其添加到res的哪个子列表中
 * - 当level等于res的大小时，说明需要创建一个新的列表存储新的一层
 * - 递归遍历左右子树，并将层级+1传递下去
 * <p>
 * BFS解法：
 * - 使用队列存储每层的节点，每次循环处理队列中的所有节点（即一层的所有节点）
 * - 将当前层节点的值收集到一个列表中，再将这些节点的非空子节点加入队列
 * - 重复此过程直到队列为空
 *
 * @author lxd
 **/
public class Day20260122 {
    List<List<Integer>> res = new ArrayList<>();


    /**
     * 二叉树层序遍历 - DFS递归解法入口
     *
     * @param root 二叉树的根节点
     * @return 按层分组的节点值列表
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        res.clear(); // 清空之前的结果，确保每次调用都是干净的
        dfs(root, 0);
        return res;
    }

    /**
     * DFS递归解法实现二叉树层序遍历
     * <p>
     * 算法流程：
     * 1. 如果当前节点为空，直接返回
     * 2. 判断当前层级是否已存在于结果列表中（level == res.size()表示需要新建一层）
     * 3. 将当前节点值添加到对应层级的列表中
     * 4. 递归处理左子树和右子树，层级+1
     * <p>
     * 时间复杂度：O(n)，其中n是二叉树中的节点数，每个节点被访问一次
     * 空间复杂度：O(h)，其中h是二叉树的高度，为递归调用栈的空间消耗
     * <p>
     * 示例：
     * 输入：root = [3,9,20,null,null,15,7]
     * 输出：[[3],[9,20],[15,7]]
     *
     * @param node  当前访问的节点
     * @param level 当前节点所在的层级（根节点为第0层）
     */
    public void dfs(TreeNode node, int level) {
        if (node == null) return;
        if (level == res.size()) {
            res.add(new ArrayList<>());
        }
        res.get(level).add(node.val);
        dfs(node.left, level + 1);
        dfs(node.right, level + 1);
    }

    /**
     * BFS迭代解法实现二叉树层序遍历
     * <p>
     * 算法流程：
     * 1. 创建队列存储待处理的节点
     * 2. 如果根节点非空，则将其加入队列
     * 3. 当队列非空时，处理当前队列中的所有节点（即一层的节点）
     * 4. 将当前层节点的值收集起来，并将其非空子节点加入队列
     * 5. 重复步骤3-4直到队列为空
     * <p>
     * 时间复杂度：O(n)，其中n是二叉树中的节点数，每个节点被访问一次
     * 空间复杂度：O(w)，其中w是二叉树的最大宽度，为队列所需的最大空间
     * <p>
     * 示例：
     * 输入：root = [3,9,20,null,null,15,7]
     * 输出：[[3],[9,20],[15,7]]
     *
     * @param root 二叉树的根节点
     * @return 按层分组的节点值列表
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (root != null) queue.add(root);
        while (!queue.isEmpty()) {
            // 当前层的节点数量
            int n = queue.size();
            // 存储当前层的节点值
            ArrayList<Integer> level = new ArrayList<>();
            // 处理当前层的所有节点
            for (int i = 0; i < n; i++) {
                // 取出当前层的一个节点
                TreeNode node = queue.poll();
                // 将该节点的值加入当前层的结果
                level.add(node.val);
                // 将左子节点加入队列
                if (node.left != null) queue.add(node.left);
                // 将右子节点加入队列
                if (node.right != null) queue.add(node.right);
            }
            // 将当前层的结果加入最终结果
            res.add(level);
        }
        return res;
    }

}
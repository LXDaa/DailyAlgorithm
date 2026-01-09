package CodeMusings.Tree;

/**
 * <a href="https://leetcode.cn/problems/path-sum/description/">112. 路径总和</a>
 * <p>
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。
 * <p>
 * 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。如果存在，返回 true ；否则，返回 false 。
 * <p>
 * 叶子节点 是指没有子节点的节点。
 */
public class Day20250828 {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        targetSum -= root.val;
        if (root.left == null && root.right == null) {
            return targetSum == 0;
        }
        return hasPathSum(root.left, targetSum) || hasPathSum(root.right, targetSum);
    }
}

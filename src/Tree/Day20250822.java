package Tree;

import Tree.TreeNode;

/**
 * <a href="https://leetcode.cn/problems/balanced-binary-tree/description/">110.平衡二叉树</a>
 * <p>
 * 给定一个二叉树，判断它是否是 平衡二叉树
 */
public class Day20250822 {

    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        return getHeight(root) != -1;
    }

    public int getHeight(TreeNode node) {
        if (node == null) return 0;
        int left = getHeight(node.left);
        int right = getHeight(node.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) return -1;
        return Math.max(left, right) + 1;
    }
}

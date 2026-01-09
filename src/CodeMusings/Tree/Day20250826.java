package CodeMusings.Tree;

import java.util.Stack;

/**
 * <a href="https://leetcode.cn/problems/sum-of-left-leaves/description/">404. 左叶子之和</a>
 * <p>
 * 给定二叉树的根节点 root ，返回所有左叶子之和。
 * </p>
 */
public class Day20250826 {
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) return 0;
        int leftValue = sumOfLeftLeaves(root.left);
        if (root.left != null && root.left.left == null && root.left.right == null) {
            leftValue = root.left.val;
        }
        int rightValue = sumOfLeftLeaves(root.right);
        return leftValue + rightValue;
    }

    public int sumOfLeftLeaves2(TreeNode root) {
        if (root == null) return 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        int res = 0;
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left != null && node.left.left == null && node.left.right == null) {
                res += node.left.val;
            }
            ;
            if (node.left != null) {

                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        return res;
    }
}

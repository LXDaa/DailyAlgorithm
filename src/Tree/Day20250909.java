package Tree;

import java.util.Stack;

/**
 * <a href="https://leetcode.cn/problems/minimum-absolute-difference-in-bst/description/">530.二叉搜索树的最小绝对差</a>
 * <p>
 * 给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。
 * <p>
 * 差值是一个正数，其数值等于两值之差的绝对值。
 */
public class Day20250909 {

    static class Recursion {
        int result = Integer.MAX_VALUE;
        TreeNode pre;

        //递归
        public int getMinimumDifference(TreeNode root) {
            if (root == null) {
                return 0;
            }
            traversal(root);
            return result;
        }

        private void traversal(TreeNode root) {
            if (root == null) {
                return;
            }
            traversal(root.left);
            if (pre != null) {
                result = Math.min(result, root.val - pre.val);
            }
            pre = root;
            traversal(root.right);
        }
    }

    static class Iterate {
        Stack<TreeNode> stack;
        TreeNode pre;

        public int getMinimumDifference(TreeNode root) {
            if (root == null) {
                return 0;
            }
            stack = new Stack<>();
            TreeNode cur = root;
            int result = Integer.MAX_VALUE;
            while (cur != null || !stack.isEmpty()) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    cur = stack.pop();
                    if (pre != null) {
                        result = Math.min(result, cur.val - pre.val);
                    }
                    pre = cur;
                    cur = cur.right;
                }
            }
            return result;
        }
    }
}

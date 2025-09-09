package Tree;

import java.util.Stack;

/**
 * <a href="https://leetcode.cn/problems/validate-binary-search-tree/description/">98.验证二叉搜索树</a>
 * <p>
 * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
 * <p>
 * 有效 二叉搜索树定义如下：
 * <p>
 * 节点的左子树只包含 严格小于 当前节点的数。
 * <p>
 * 节点的右子树只包含 严格大于 当前节点的数。
 * <p>
 * 所有左子树和右子树自身必须也是二叉搜索树
 */
public class Day20250908 {
    //二叉搜索树的中序遍历结果是一个严格递增的序列。

    static class Recursion {
        public boolean isValidBST(TreeNode root) {
            return validBST(Long.MIN_VALUE, Long.MAX_VALUE, root);
        }

        private boolean validBST(long lower, long upper, TreeNode root) {
            if (root == null) {
                return true;
            }
            if (root.val <= lower || root.val >= upper) {
                return false;
            }

            return validBST(lower, root.val, root.left) && validBST(root.val, upper, root.right);
        }
    }

    // 迭代
    static class Iterate {
        public boolean isValidBST(TreeNode root) {
            if (root == null) {
                // 空树被认为是有效的BST
                return true;
            }
            // 用于中序遍历的栈
            Stack<TreeNode> stack = new Stack<>();
            // 用于记录前一个访问的节点
            TreeNode pre = null;
            while (root != null || !stack.isEmpty()) {
                // 将当前节点及其所有左子节点入栈
                if (root != null) {
                    stack.push(root);
                    // 移动到左子节点
                    root = root.left;
                } else {
                    // 处理当前节点（中序遍历的顺序）
                    TreeNode pop = stack.pop();
                    // 检查当前节点值是否大于前一个节点的值
                    if (pre != null && pop.val <= pre.val) {
                        // 不满足BST性质
                        return false;
                    }
                    // 更新前一个节点
                    pre = pop;
                    // 移动到右子节点
                    root = pop.right;
                }
            }
            // 所有节点都满足BST性质
            return true;
        }
    }


}

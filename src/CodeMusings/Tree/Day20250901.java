package CodeMusings.Tree;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/">106.从中序与后序遍历序列构造二叉树</a>
 * <p>
 * 给定两个整数数组 inorder 和 postorder ，其中 inorder 是二叉树的中序遍历， postorder 是同一棵树的后序遍历
 * <p>
 * 请你构造并返回这颗 二叉树 。
 */
public class Day20250901 {
    int post_idx;
    int[] inorder;
    int[] postorder;
    Map<Integer, Integer> map = new HashMap<>();

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        this.inorder = inorder;
        this.postorder = postorder;
        post_idx = postorder.length - 1;
        int idx = 0;
        for (int val : inorder) {
            map.put(val, idx++);
        }
        return helper(0, inorder.length - 1);
    }

    private TreeNode helper(int inLeft, int inRight) {
        if (inLeft > inRight) {
            return null;
        }

        int rootValue = postorder[post_idx];
        int index = map.get(rootValue);
        TreeNode root = new TreeNode(rootValue);
        post_idx--;
        //后序遍历，先构造右子树
        root.right = helper(index + 1, inRight);
        root.left = helper(inLeft, index - 1);
        return root;
    }
}

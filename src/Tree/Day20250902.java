package Tree;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/">105.从前序与中序遍历序列构造二叉树</a>
 * <p>
 * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
 * <p>
 */
public class Day20250902 {
    int i = 0;
    int[] preorder;
    int[] inorder;
    Map<Integer, Integer> inorderMap = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        this.inorder = inorder;
        int idx = 0;
        //便于通过值定位索引
        for (int val : inorder) {
            inorderMap.put(val, idx++);
        }
        return helper(0, inorder.length - 1);

    }
    /**
     * 利用前序确定根节点，中序划分左右子树
     */
    private TreeNode helper(int inLeft, int inRight) {
        if (inLeft > inRight) {
            return null;
        }
        int rootValue = preorder[i++];
        Integer index = inorderMap.get(rootValue);
        TreeNode node = new TreeNode(rootValue);
        node.left = helper(inLeft, index - 1);
        node.right = helper(index + 1, inRight);
        return node;
    }
}

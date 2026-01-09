package CodeMusings.Tree;

import java.util.ArrayDeque;

/**
 * 226. 翻转二叉树
 * https://leetcode.cn/problems/invert-binary-tree/description/
 * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
 */
public class Day20250815 {

    // DFS
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        invertTree(root.left);
        invertTree(root.right);
        return swap(root);
    }

    private TreeNode swap(TreeNode root) {
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        return root;
    }

    //BFS
    public TreeNode invertTree2(TreeNode root) {
        if(root == null) return null;
        ArrayDeque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(root);
        while(!deque.isEmpty()) {
            int len=deque.size();
            while(len-->0) {
                TreeNode node = deque.poll();
                swap(node);
                if(node.left != null) deque.offer(node.left);
                if(node.right != null) deque.offer(node.right);
            }
        }
        return root;
    }
}

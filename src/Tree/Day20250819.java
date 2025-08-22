package Tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


public class Day20250819 {

    /**
     * <p>
     * <a href="https://leetcode.cn/problems/maximum-depth-of-binary-tree/description/">104. 二叉树的最大深度</a>
     * <p>
     * 给定一个二叉树，找出其最大深度。
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     * 说明: 叶子节点是指没有子节点的节点。
     */
    //1. 递归
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    //2. BFS
    public int maxDepth2(TreeNode root) {
        if (root == null) return 0;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        int depth = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            depth++;
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.poll();
                if (node.left != null) deque.offer(node.left);
                if (node.right != null) deque.offer(node.right);
            }
        }
        return depth;
    }

    /**
     * <p>
     * <a href="https://leetcode.cn/problems/maximum-depth-of-n-ary-tree/description/">559. N 叉树的最大深度</a>
     * <p>
     * 给定一个 N 叉树，找到其最大深度。
     * 最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。
     * N 叉树输入按层序遍历序列化表示，每组子节点由空值分隔（请参见示例）。
     */
    static class Node {
        int val;
        List<Node> children;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, List<Node> children) {
            this.val = val;
            this.children = children;
        }
    }

    //1. 递归
    public int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }
        int depth = 0;
        if (root.children != null) {
            for (Node child : root.children) {
                depth = Math.max(depth, maxDepth(child));
            }
        }
        return depth + 1;
    }
    //2. BFS
    public int maxDepth2(Node root) {
        if (root == null) return 0;
        Deque<Node> deque = new LinkedList<>();
        deque.offer(root);
        int depth = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            depth++;
            for (int i = 0; i < size; i++) {
                Node node = deque.poll();
                if (node.children != null) {
                    for (Node child : node.children) {
                        deque.offer(child);
                    }
                }
            }
        }
        return depth;
    }

}


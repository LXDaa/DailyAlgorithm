package Tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <a href="http://leetcode.cn/problems/find-bottom-left-tree-value/description/">513.找树左下角的值</a>
 * <p>
 * 给定一个二叉树的 根节点 root，请找出该二叉树的 最底层 最左边 节点的值。
 * <p>
 * 假设二叉树中至少有一个节点。
 * <p>
 */
public class Day20250827 {
    public int findBottomLeftValue(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int res = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (i == 0) {
                    res = node.val;
                }
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        return res;
    }


    int MAX_DEPTH;
    int res;

    public int findBottomLeftValue2(TreeNode root) {
        res = root.val;
        findLeftValue(root, 0);
        return res;
    }

    public void findLeftValue(TreeNode root, int depth) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            if (depth > MAX_DEPTH) {
                MAX_DEPTH = depth;
                res = root.val;
            }
        }
        if (root.left != null) findLeftValue(root.left, depth + 1);
        if (root.right != null) findLeftValue(root.right, depth + 1);
    }
}

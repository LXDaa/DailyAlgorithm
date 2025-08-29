package Tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/path-sum-ii/description/">113. 路径总和 II</a>
 * <p>
 * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
 * <p>
 * 叶子节点 是指没有子节点的节点。
 */
public class Day20250829 {
    LinkedList<List<Integer>> result = new LinkedList<>();
    Deque<Integer> path = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        traversal(root, targetSum);
        return result;
    }

    private void traversal(TreeNode root, int targetSum) {
        if (root == null) {
            return;
        }
        path.offer(root.val);
        if (root.left == null && root.right == null && root.val == targetSum) {
            result.add(new LinkedList<Integer>(path));
        }
        traversal(root.left, targetSum - root.val);
        traversal(root.right, targetSum - root.val);
        path.removeLast();
    }
}

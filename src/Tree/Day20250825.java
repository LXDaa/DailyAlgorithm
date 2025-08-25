package Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * <a href="https://leetcode.cn/problems/binary-tree-paths/description/">257. 二叉树的所有路径</a>
 * <p>
 * 给你一个二叉树的根节点 root ，按 任意顺序 ，返回所有从根节点到叶子节点的路径。
 * <p>
 * 叶子节点 是指没有子节点的节点。
 */
public class Day20250825 {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        ArrayList<Integer> paths = new ArrayList<>();
        traversal(root, paths, res);
        return res;
    }

    public void traversal(TreeNode root, List<Integer> paths, List<String> res) {
        paths.add(root.val);
        if (root.left == null && root.right == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paths.size() - 1; i++) {
                sb.append(paths.get(i)).append("->");
            }
            sb.append(paths.get(paths.size() - 1));
            res.add(sb.toString());
        }
        if (root.left != null) {
            traversal(root.left, paths, res);
            paths.remove(paths.size() - 1);
        }
        if (root.right != null) {
            traversal(root.right, paths, res);
            paths.remove(paths.size() - 1);
        }
    }

    public List<String> binaryTreePaths2(TreeNode root) {
        Stack<Object> stack = new Stack<>();
        List<String> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        //进栈和出栈匹配
        stack.push(root);
        stack.push(root.val + "");

        while (!stack.isEmpty()) {
            String path = (String) stack.pop();
            TreeNode node = (TreeNode) stack.pop();
            if (node.left == null && node.right == null) {
                res.add(path);
            }
            if (node.right != null) {
                stack.push(node.right);
                stack.push(path +"->"+node.right.val);
            }
            if (node.left != null) {
                stack.push(node.left);
                stack.push(path +"->"+ node.left.val);
            }
        }
        return res;
    }
}

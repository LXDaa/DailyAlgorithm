package CodeMusings.Tree;

/**
 *
 * <a href="https://leetcode.cn/problems/delete-node-in-a-bst/description/">450.删除二叉搜索树中的节点</a>
 * <p>
 * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
 *
 */
public class Day20250916 {
    static class Recursion {
        public TreeNode deleteNode(TreeNode root, int key) {
            // 基础情况：如果当前节点为空，直接返回null
            if (root == null) {
                return null;
            }

            // 找到需要删除的节点
            if (root.val == key) {
                // 情况1：被删除节点没有左子树，直接返回右子树
                if (root.left == null) {
                    return root.right;
                }
                // 情况2：被删除节点没有右子树，直接返回左子树
                if (root.right == null) {
                    return root.left;
                }

                // 情况3：被删除节点左右子树都存在
                // 找到左子树中的最大节点（最右边的节点）
                TreeNode node = root.left;
                while (node.right != null) {
                    node = node.right;
                }

                // 将被删除节点的右子树连接到左子树的最大节点上
                node.right = root.right;

                // 返回被删除节点的左子树作为新的根节点
                return root.left;
            }

            // 递归查找需要删除的节点
            // 如果当前节点值大于key，去左子树中删除
            if (root.val > key) {
                root.left = deleteNode(root.left, key);
            }
            // 如果当前节点值小于key，去右子树中删除
            else {
                root.right = deleteNode(root.right, key);
            }

            // 返回当前节点
            return root;
        }
    }
}
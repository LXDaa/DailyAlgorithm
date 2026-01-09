package CodeMusings.Tree;

/**
 *
 * <a href="https://leetcode.cn/problems/trim-a-binary-search-tree/description/">669. 修剪二叉搜索树</a>
 * <p>
 * 给你二叉搜索树的根节点 root ，同时给定最小边界low 和最大边界 high。通过修剪二叉搜索树，使得所有节点的值在[low, high]中。修剪树 不应该 改变保留在树中的元素的相对结构 (即，如果没有被移除，原有的父代子代关系都应当保留)。 可以证明，存在 唯一的答案 。
 * <p>
 * 所以结果应当返回修剪好的二叉搜索树的新的根节点。注意，根节点可能会根据给定的边界发生改变。
 * <p>
 */
public class Day20250917 {
    static class Recursion {
        /**
         * 递归修剪二叉搜索树，使其所有节点值落在[low, high]范围内
         * @param root 二叉搜索树的根节点
         * @param low 最小边界值（包含）
         * @param high 最大边界值（包含）
         * @return 修剪后的二叉搜索树的根节点
         */
        public TreeNode trimBST(TreeNode root, int low, int high) {
            // 基础情况：如果当前节点为空，直接返回null
            if (root == null) {
                return null;
            }
            
            // 如果当前节点值小于最小边界，说明当前节点及其左子树都应该被删除
            // 因为二叉搜索树的性质，左子树的所有节点值都小于当前节点值
            // 所以只需要考虑右子树中可能符合条件的节点
            if (root.val < low) {
                return trimBST(root.right, low, high);
            }
            
            // 如果当前节点值大于最大边界，说明当前节点及其右子树都应该被删除
            // 因为二叉搜索树的性质，右子树的所有节点值都大于当前节点值
            // 所以只需要考虑左子树中可能符合条件的节点
            if (root.val > high) {
                return trimBST(root.left, low, high);
            }
            
            // 如果当前节点值在[low, high]范围内，则保留该节点
            // 递归处理左右子树，修剪不在范围内的节点
            root.left = trimBST(root.left, low, high);
            root.right = trimBST(root.right, low, high);
            
            // 返回修剪后的当前节点作为根节点
            return root;
        }
    }

}
package Tree;

/**
 * <a href="https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/description/">235. 二叉搜索树的最近公共祖先</a>
 * <p>
 * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
 */
public class Day20250912 {
    /**
     * 递归解法
     * 利用二叉搜索树的性质：左子树所有节点值小于根节点，右子树所有节点值大于根节点
     */
    static class Recursion {
        /**
         * 查找二叉搜索树中两个节点的最近公共祖先（递归实现）
         *
         * @param root 二叉搜索树的根节点
         * @param p    目标节点p
         * @param q    目标节点q
         * @return 节点p和q的最近公共祖先
         */
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            // 如果根节点的值大于p和q的值，说明p和q都在左子树中
            if (root.val > p.val && root.val > q.val) {
                // 递归在左子树中查找最近公共祖先
                return lowestCommonAncestor(root.left, p, q);
                // 如果根节点的值小于p和q的值，说明p和q都在右子树中
            } else if (root.val < p.val && root.val < q.val) {
                // 递归在右子树中查找最近公共祖先
                return lowestCommonAncestor(root.right, p, q);
                // 如果根节点的值在p和q之间（或者等于其中一个），说明当前节点就是最近公共祖先
            } else {
                return root;
            }
        }
    }

    /**
     * 迭代解法
     * 同样利用二叉搜索树的性质，通过循环遍历找到最近公共祖先
     */
    static class Iterate {
        /**
         * 查找二叉搜索树中两个节点的最近公共祖先（迭代实现）
         *
         * @param root 二叉搜索树的根节点
         * @param p    目标节点p
         * @param q    目标节点q
         * @return 节点p和q的最近公共祖先
         */
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            // 循环查找最近公共祖先
            while (true) {
                // 如果根节点的值大于p和q的值，说明p和q都在左子树中
                if (root.val > p.val && root.val > q.val) {
                    // 移动到左子树继续查找
                    root = root.left;
                    // 如果根节点的值小于p和q的值，说明p和q都在右子树中
                } else if (root.val < p.val && root.val < q.val) {
                    // 移动到右子树继续查找
                    root = root.right;
                    // 如果根节点的值在p和q之间（或者等于其中一个），说明当前节点就是最近公共祖先
                } else {
                    // 找到最近公共祖先，跳出循环
                    break;
                }
            }
            // 返回最近公共祖先
            return root;
        }
    }
}
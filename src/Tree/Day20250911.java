package Tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <a href="https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/description/">236. 二叉树的最近公共祖先</a>
 * <p>
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * <p>
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 */
public class Day20250911 {
    /**
     * 方法一：递归法
     * 思路：对于当前节点root，如果root是p或q，则root就是最近公共祖先；
     * 否则分别在左右子树中查找，如果两个节点分别在左右子树中，则当前节点就是最近公共祖先；
     * 如果只在一边找到，则返回找到的结果。
     */
    static class Iterate {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            // 递归终止条件：节点为空，或者节点为p或q中的一个
            if (root == null || root == p || root == q) {
                return root;
            }
            
            // 递归查找左子树中的最近公共祖先
            TreeNode left = lowestCommonAncestor(root.left, p, q);
            
            // 递归查找右子树中的最近公共祖先
            TreeNode right = lowestCommonAncestor(root.right, p, q);
            
            // 如果左右子树都找到了目标节点，说明当前节点就是最近公共祖先
            if (left != null && right != null) {
                return root;
            }
            
            // 如果只有一边找到了，返回找到的那一边的结果
            // 如果两边都没找到，返回null
            return left != null ? left : right;
        }
    }


    /**
     * 方法二：存储父节点法
     * 思路：首先遍历整棵树，建立子节点到父节点的映射关系；
     * 然后从p节点开始，沿着父节点路径向上遍历，记录所有访问过的节点；
     * 最后从q节点开始，沿着父节点路径向上遍历，第一个遇到的已访问节点就是最近公共祖先。
     */
    static class StoreParentNode {
        // 存储节点值到父节点的映射
        Map<Integer, TreeNode> parent = new HashMap<>();
        // 存储从p节点到根节点路径上所有访问过的节点值
        Set<Integer> visited = new HashSet<>();

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            // 遍历整棵树，建立父子节点映射关系
            dfs(root);
            
            // 从p节点开始，沿着父节点路径向上遍历，记录所有访问过的节点
            while (p != null) {
                visited.add(p.val);
                p = parent.get(p.val);
            }
            
            // 从q节点开始，沿着父节点路径向上遍历
            // 第一个遇到的已访问节点就是最近公共祖先
            while (q != null) {
                if (visited.contains(q.val)) {
                    return q;
                }
                q = parent.get(q.val);
            }
            
            // 理论上不会执行到这里
            return null;
        }

        /**
         * 深度优先遍历，建立子节点到父节点的映射关系
         * @param root 当前节点
         */
        private void dfs(TreeNode root) {
            // 如果左子节点存在，建立左子节点到当前节点的映射，并递归处理左子树
            if (root.left != null) {
                parent.put(root.left.val, root);
                dfs(root.left);
            }
            
            // 如果右子节点存在，建立右子节点到当前节点的映射，并递归处理右子树
            if (root.right != null) {
                parent.put(root.right.val, root);
                dfs(root.right);
            }
        }
    }
}
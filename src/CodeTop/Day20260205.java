package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/description/">236. 二叉树的最近公共祖先</a>
 * <p>
 * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 * <p>
 * 最近公共祖先的定义为：对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，
 * 满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。
 * <p>
 * <h3>解题思路</h3>
 * <p>
 * 本题使用递归方法解决，核心思想是自底向上搜索，利用后序遍历的特点：<br>
 * <ol>
 * <li>如果当前节点为空，或当前节点就是p或q，则直接返回当前节点</li>
 * <li>递归搜索左子树和右子树</li>
 * <li>根据左右子树的搜索结果判断最近公共祖先的位置</li>
 * </ol>
 * <p>
 * <h3>算法逻辑</h3>
 * <p>
 * 对于任意节点root，有以下几种情况：<br>
 * <ol>
 * <li>如果root为null，或root等于p或q，直接返回root</li>
 * <li>递归搜索左子树，得到left结果</li>
 * <li>递归搜索右子树，得到right结果</li>
 * <li>判断结果：<br>
 * &nbsp;&nbsp;- 如果left和right都不为null，说明p和q分别在左右子树中，root就是最近公共祖先<br>
 * &nbsp;&nbsp;- 如果只有left不为null，说明p和q都在左子树中，返回left<br>
 * &nbsp;&nbsp;- 如果只有right不为null，说明p和q都在右子树中，返回right<br>
 * &nbsp;&nbsp;- 如果都为null，说明p和q都不在以root为根的子树中，返回null</li>
 * </ol>
 * <p>
 * <h3>示例演示</h3>
 * <p>
 * 以二叉树 [3,5,1,6,2,0,8,null,null,7,4] 为例，寻找节点5和1的最近公共祖先：<br>
 * &nbsp;&nbsp;- 从根节点3开始递归<br>
 * &nbsp;&nbsp;- 搜索左子树(节点5)：找到p=5，返回节点5<br>
 * &nbsp;&nbsp;- 搜索右子树(节点1)：找到q=1，返回节点1<br>
 * &nbsp;&nbsp;- 根节点3的左右子树都返回非null，说明p和q分布在两侧，因此3是最近公共祖先<br>
 * <p>
 * 寻找节点5和4的最近公共祖先：<br>
 * &nbsp;&nbsp;- 从根节点3开始递归<br>
 * &nbsp;&nbsp;- 搜索左子树：继续递归到节点5<br>
 * &nbsp;&nbsp;- 节点5就是p，返回节点5<br>
 * &nbsp;&nbsp;- 在节点5的右子树中搜索到节点4，返回节点4<br>
 * &nbsp;&nbsp;- 节点5的左右子树都返回非null，所以节点5是最近公共祖先<br>
 * <p>
 * <h3>时间复杂度</h3>
 * <p>
 * O(n)，其中n是二叉树的节点数，最坏情况下需要遍历所有节点<br>
 * <p>
 * <h3>空间复杂度</h3>
 * <p>
 * O(h)，其中h是二叉树的高度，主要是递归调用栈的空间开销<br>
 * 最坏情况下（完全不平衡的树）为O(n)，最好情况下（完全平衡的树）为O(log n)<br>
 *
 * @author lxd
 **/
public class Day20260205 {
    /**
     * 寻找二叉树中两个节点的最近公共祖先
     *
     * @param root 二叉树的根节点
     * @param p    目标节点p
     * @param q    目标节点q
     * @return 两个节点的最近公共祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 基础情况：如果当前节点为空，或当前节点就是p或q，则直接返回当前节点
        // 这三种情况都需要立即返回：
        // 1. 空节点：说明没找到目标节点
        // 2. 找到p：说明在当前子树中找到了p
        // 3. 找到q：说明在当前子树中找到了q
        if (root == null || root == p || root == q) {
            return root;
        }

        // 递归搜索左子树，寻找p和q
        TreeNode left = lowestCommonAncestor(root.left, p, q);

        // 递归搜索右子树，寻找p和q
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 根据左右子树的搜索结果判断最近公共祖先的位置
        if (left != null && right != null) {
            // 情况1：left和right都不为null
            // 说明p和q分别在左右子树中，当前节点root就是最近公共祖先
            return root;
        }

        // 情况2和3：只有一个子树找到了目标节点
        // 如果左子树为空，则说明两个节点都在右子树中，返回右子树的结果
        // 如果右子树为空，则说明两个节点都在左子树中，返回左子树的结果
        return left == null ? right : left;
    }
}

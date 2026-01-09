package CodeMusings.DynamicProgramming;

/**
 * <a href="https://leetcode.cn/problems/house-robber-iii/description/">337. 打家劫舍 III</a>
 * <p>
 * 小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 root 。
 * <p>
 * 除了 root 之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果 两个直接相连的房子在同一天晚上被打劫 ，房屋将自动报警。
 * <p>
 * 给定二叉树的 root 。返回 在不触动警报的情况下 ，小偷能够盗取的最高金额 。
 *
 * @author lxd
 **/


/**
 * 解题思路：
 * 这是一道树形动态规划问题，关键在于处理"不能同时偷相邻节点"的约束。
 * <p>
 * 核心思想：
 * 1. 对于每个节点，有两种选择：偷或不偷
 * 2. 如果偷当前节点，就不能偷其左右子节点，只能偷孙子节点
 * 3. 如果不偷当前节点，可以选择偷或不偷其左右子节点
 * <p>
 * 状态定义：
 * 使用数组 int[2] 表示每个节点的两种状态：
 * - res[0]：不偷当前节点能获得的最大金额
 * - res[1]：偷当前节点能获得的最大金额
 * <p>
 * 状态转移方程：
 * 对于当前节点 root：
 * - 不偷 root：res[0] = max(left[0], left[1]) + max(right[0], right[1])
 * （左右子节点可偷可不偷，选择最大值）
 * - 偷 root：res[1] = root.val + left[0] + right[0]
 * （必须不偷左右子节点）
 * <p>
 * 时间复杂度：O(n)，每个节点访问一次
 * 空间复杂度：O(h)，h为树的高度，递归栈的深度
 */
public class Day20251203 {
    /**
     * 主函数：计算在不触动警报的情况下能偷取的最高金额
     *
     * @param root 二叉树的根节点
     * @return 能够盗取的最高金额
     */
    public int rob(TreeNode root) {
        // 获取根节点的两种状态（偷/不偷）对应的最大金额
        int[] res = dfs(root);
        // 返回两种状态中的最大值
        return Math.max(res[0], res[1]);
    }

    /**
     * 深度优先搜索：计算以当前节点为根的子树的最大偷取金额
     *
     * @param root 当前节点
     * @return 长度为2的数组，[0]表示不偷当前节点的最大金额，[1]表示偷当前节点的最大金额
     */
    private int[] dfs(TreeNode root) {
        // 递归终止条件：空节点，偷或不偷都是0
        if (root == null) {
            return new int[]{0, 0};
        }

        // 递归计算左子树的两种状态
        // left[0]: 不偷左子节点的最大金额
        // left[1]: 偷左子节点的最大金额
        int[] left = dfs(root.left);

        // 递归计算右子树的两种状态
        // right[0]: 不偷右子节点的最大金额
        // right[1]: 偷右子节点的最大金额
        int[] right = dfs(root.right);

        // 返回当前节点的两种状态
        return new int[]{
                // 不偷当前节点：左右子节点可以选择偷或不偷，取最大值
                Math.max(left[0], left[1]) + Math.max(right[0], right[1]),
                // 偷当前节点：左右子节点都不能偷，只能取不偷的状态
                root.val + left[0] + right[0]
        };
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}

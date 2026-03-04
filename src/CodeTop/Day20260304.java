package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/binary-tree-maximum-path-sum/description/">124. 二叉树中的最大路径和</a>
 * <p>
 * 二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
 * <p>
 * 路径和 是路径中各节点值的总和。
 * <p>
 * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
 *
 * <p><strong>解题思路：</strong></p>
 * <p>使用递归（后序遍历）来解决该问题。核心思想是计算每个节点的"最大贡献值"，同时更新全局最大路径和。</p>
 *
 * <p><strong>关键概念：</strong></p>
 * <ol>
 *   <li><strong>最大贡献值：</strong>一个节点能向父节点提供的最大路径和<br>
 *       = 节点值 + max(左子节点贡献值，右子节点贡献值，0)<br>
 *       （负数贡献不如不选）</li>
 *   <li><strong>最大路径和：</strong>以某个节点为"转折点"的路径和<br>
 *       = 节点值 + 左子节点贡献值 + 右子节点贡献值<br>
 *       （可以同时包含左右子节点）</li>
 * </ol>
 *
 * <p><strong>算法步骤：</strong></p>
 * <ol>
 *   <li>初始化全局最大值为 Integer.MIN_VALUE</li>
 *   <li>对每个节点进行后序遍历：</li>
 *   <ul>
 *     <li>递归计算左子节点的最大贡献值（负数则取 0）</li>
 *     <li>递归计算右子节点的最大贡献值（负数则取 0）</li>
 *     <li>计算以当前节点为转折点的路径和（可同时包含左右子节点）</li>
 *     <li>更新全局最大值</li>
 *     <li>返回当前节点的最大贡献值（只能选择左右子节点中较大的一个）</li>
 *   </ul>
 *   <li>返回全局最大路径和</li>
 * </ol>
 *
 * <p><strong>示例说明：</strong></p>
 * <p>输入：root = [-10,9,20,null,null,15,7]</p>
 * <pre>
 *       -10
 *       /  \
 *      9   20
 *         /  \
 *        15   7
 * </pre>
 * <p>执行过程：</p>
 * <ol>
 *   <li>节点 15：贡献值=15，maxSum=15</li>
 *   <li>节点 7：贡献值=7，maxSum=max(15,7)=15</li>
 *   <li>节点 20：左贡献=15，右贡献=7</li>
 *       <ul>
 *         <li>路径和=20+15+7=42，maxSum=42</li>
 *         <li>返回贡献值=20+max(15,7)=35</li>
 *       </ul>
 *   <li>节点 9：贡献值=9，maxSum=max(42,9)=42</li>
 *   <li>节点 -10：左贡献=9，右贡献=35</li>
 *       <ul>
 *         <li>路径和=-10+9+35=34，maxSum=max(42,34)=42</li>
 *         <li>返回贡献值=-10+max(9,35)=25</li>
 *       </ul>
 *   <li>最终结果：42（路径 15→20→7）</li>
 * </ol>
 *
 * <p><strong>复杂度分析：</strong></p>
 * <ul>
 *   <li>时间复杂度：O(n)，需要遍历所有节点一次</li>
 *   <li>空间复杂度：O(h)，递归栈的深度，h 为树的高度</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260304 {
    // 全局变量，记录最大路径和
    int maxSum = Integer.MIN_VALUE;

    /**
     * <p>计算二叉树中的最大路径和</p>
     *
     * @param root 二叉树的根节点
     * @return 最大路径和
     */
    public int maxPathSum(TreeNode root) {
        // 通过递归计算最大贡献值，同时更新全局最大路径和
        maxGain(root);
        return maxSum;
    }

    /**
     * <p>计算节点的最大贡献值，同时更新全局最大路径和</p>
     * <p>最大贡献值：该节点能向父节点提供的最大路径和（只能选择一侧子节点）</p>
     *
     * @param node 当前节点
     * @return 节点的最大贡献值
     */
    public int maxGain(TreeNode node) {
        // 空节点贡献值为 0
        if (node == null) {
            return 0;
        }

        // 递归计算左子节点的最大贡献值，负数则不选（取 0）
        int leftGain = Math.max(maxGain(node.left), 0);
        // 递归计算右子节点的最大贡献值，负数则不选（取 0）
        int rightGain = Math.max(maxGain(node.right), 0);

        // 计算以当前节点为转折点的路径和（可同时包含左右子节点）
        int priceNewpath = node.val + leftGain + rightGain;

        // 更新全局最大路径和
        maxSum = Math.max(maxSum, priceNewpath);

        // 返回当前节点的最大贡献值（只能选择左右子节点中较大的一侧）
        return node.val + Math.max(leftGain, rightGain);
    }
}

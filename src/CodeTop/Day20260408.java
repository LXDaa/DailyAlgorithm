package CodeTop;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/">105. 从前序与中序遍历序列构造二叉树</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>给定两个整数数组 preorder 和 inorder，其中 preorder 是二叉树的先序遍历，inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。</p>
 *
 * <h3>💡 核心思路：递归分治 + 哈希优化</h3>
 * <ul>
 *   <li><b>前序遍历特性</b>：[根节点, [左子树], [右子树]] → 第一个元素必为根</li>
 *   <li><b>中序遍历特性</b>：[[左子树], 根节点, [右子树]] → 根节点将数组分为左右子树</li>
 *   <li><b>关键步骤</b>：
 *     <ol>
 *       <li>从前序遍历确定根节点</li>
 *       <li>在中序遍历中找到根的位置，划分左右子树</li>
 *       <li>递归构建左右子树</li>
 *     </ol>
 *   </li>
 *   <li><b>优化</b>：使用 HashMap 存储中序遍历的值→索引映射，O(1) 查找根位置</li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>预处理</b>：建立中序遍历值到索引的哈希表</li>
 *   <li><b>递归基</b>：当区间为空时返回 null</li>
 *   <li><b>确定根</b>：前序遍历的第一个元素为当前子树的根</li>
 *   <li><b>划分左右</b>：在中序中找到根的位置，计算左子树大小</li>
 *   <li><b>递归构建</b>：根据左右子树的大小，在前序和中序中划分对应区间</li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例：preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 *
 * 【第1层递归】整个树
 * 前序: [3, 9, 20, 15, 7]  → 根 = 3
 * 中序: [9, 3, 15, 20, 7]
 *              ↑
 *           根位置 index=1
 * 左子树大小 = 1 - 0 = 1
 *
 *        3
 *       / \
 *     ?     ?
 *
 * 【第2层-左子树】
 * 前序区间: [1, 1] → [9]
 * 中序区间: [0, 0] → [9]
 * 根 = 9, 在中序中位置 index=0
 * 左子树大小 = 0 - 0 = 0 (无左子树)
 *
 *        3
 *       / \
 *      9    ?
 *
 * 【第2层-右子树】
 * 前序区间: [2, 4] → [20, 15, 7]
 * 中序区间: [2, 4] → [15, 20, 7]
 * 根 = 20, 在中序中位置 index=3
 * 左子树大小 = 3 - 2 = 1
 *
 *        3
 *       / \
 *      9    20
 *          /  \
 *         ?    ?
 *
 * 【第3层-右子树的左子树】
 * 前序区间: [3, 3] → [15]
 * 中序区间: [2, 2] → [15]
 * 根 = 15, 叶子节点
 *
 * 【第3层-右子树的右子树】
 * 前序区间: [4, 4] → [7]
 * 中序区间: [4, 4] → [7]
 * 根 = 7, 叶子节点
 *
 * 【最终结果】
 *        3
 *       / \
 *      9   20
 *         /  \
 *        15   7
 *
 * ✅ 构造完成！
 * </pre>
 *
 * <h3>🔑 关键公式推导</h3>
 * <pre>
 * 假设：
 * - 前序遍历区间: [preStart, preEnd]
 * - 中序遍历区间: [inStart, inEnd]
 * - 根在中序中的位置: rootInorderIndex
 *
 * 则：
 * 左子树大小 = rootInorderIndex - inStart
 *
 * 左子树区间：
 * - 前序: [preStart+1, preStart+leftSubtreeSize]
 * - 中序: [inStart, rootInorderIndex-1]
 *
 * 右子树区间：
 * - 前序: [preStart+leftSubtreeSize+1, preEnd]
 * - 中序: [rootInorderIndex+1, inEnd]
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>边界条件</b>：preStart > preEnd 或 inStart > inEnd 时返回 null</li>
 *   <li><b>索引计算</b>：左子树大小是关键，决定了前序遍历中左右子树的分界点</li>
 *   <li><b>时间复杂度</b>：O(n)，每个节点处理一次，HashMap 查找 O(1)</li>
 *   <li><b>空间复杂度</b>：O(n)，HashMap 存储 + 递归栈深度</li>
 *   <li><b>前提条件</b>：题目保证 preorder 和 inorder 是有效的遍历序列，无重复元素</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260408 {
    // 🗺️ 哈希表：存储中序遍历中 值→索引 的映射，用于 O(1) 查找根节点位置
    private Map<Integer, Integer> inorderMap = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 🔍 预处理：建立中序遍历的值到索引的映射
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        // 🚀 启动递归构建，初始区间为整个数组
        return buildTreeHelper(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    /**
     * 🔄 递归构建二叉树
     *
     * @param preorder 前序遍历数组
     * @param preStart 前序遍历的起始索引
     * @param preEnd   前序遍历的结束索引
     * @param inorder  中序遍历数组
     * @param inStart  中序遍历的起始索引
     * @param inEnd    中序遍历的结束索引
     * @return 当前子树的根节点
     */
    private TreeNode buildTreeHelper(int[] preorder, int preStart, int preEnd,
                                     int[] inorder, int inStart, int inEnd) {
        // 🛑 递归基：区间无效，返回空节点
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        // 🌟 前序遍历的第一个元素是当前子树的根节点
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        // 🔍 在中序遍历中找到根节点的位置
        int rootInorderIndex = inorderMap.get(rootVal);
        // 📏 计算左子树的节点数量
        int leftSubtreeSize = rootInorderIndex - inStart;

        // 🌿 递归构建左子树
        // 前序: [preStart+1, preStart+leftSubtreeSize]
        // 中序: [inStart, rootInorderIndex-1]
        root.left = buildTreeHelper(preorder, preStart + 1, preStart + leftSubtreeSize,
                inorder, inStart, rootInorderIndex - 1);

        // 🌿 递归构建右子树
        // 前序: [preStart+leftSubtreeSize+1, preEnd]
        // 中序: [rootInorderIndex+1, inEnd]
        root.right = buildTreeHelper(preorder, preStart + leftSubtreeSize + 1, preEnd,
                inorder, rootInorderIndex + 1, inEnd);

        return root;
    }
}

package Hot100.二叉树;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxd
 **/
public class Hot047 {
    /**
     * 中序遍历值到索引的映射，用于快速查找根节点位置
     */
    private Map<Integer, Integer> inorderMap = new HashMap<>();

    /**
     * <a href="https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/">105. 从前序与中序遍历序列构造二叉树</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定两个整数数组 preorder 和 inorder，其中 preorder 是二叉树的先序遍历，inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。</p>
     * <p>假设树中没有重复的元素。</p>
     *
     * <h3>💡 核心思路：递归分治 + 哈希表加速</h3>
     * <ul>
     *   <li><b>基本思想</b>：利用先序和中序遍历的性质递归构建
     *     <ul>
     *       <li>先序遍历：根 → 左 → 右，第一个元素是根节点</li>
     *       <li>中序遍历：左 → 根 → 右，根节点将数组分为左右子树</li>
     *       <li>递归构建左子树和右子树</li>
     *       <li>使用哈希表快速定位根节点在中序数组中的位置</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用哈希表</b>：
     *     <ul>
     *       <li>每次查找根节点位置的时间从 O(n) 降为 O(1)</li>
     *       <li>整体时间复杂度从 O(n²) 优化到 O(n)</li>
     *       <li>空间换时间的经典策略</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个节点处理一次，哈希表查找 O(1)</li>
     *   <li><b>空间复杂度</b>：O(n)，哈希表存储 n 个元素，递归栈 O(log n)</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：
     * preorder = [3, 9, 20, 15, 7]
     * inorder  = [9, 3, 15, 20, 7]
     *
     * 【递归构建过程】
     *
     * buildTreeHelper(preorder, 0, 4, 0, 4)
     *   ├─ rootValue = preorder[0] = 3
     *   ├─ rootInorderIndex = 1（在中序数组中的位置）
     *   ├─ leftSubtreeSize = 1 - 0 = 1
     *   │
     *   ├─ 构建左子树：
     *   │   buildTreeHelper(preorder, 1, 1, 0, 0)
     *   │   ├─ rootValue = preorder[1] = 9
     *   │   ├─ rootInorderIndex = 0
     *   │   ├─ leftSubtreeSize = 0 - 0 = 0
     *   │   ├─ 左子树：buildTreeHelper(preorder, 2, 1, ...) → null
     *   │   ├─ 右子树：buildTreeHelper(preorder, 2, 1, ...) → null
     *   │   └─ 返回 TreeNode(9)
     *   │
     *   └─ 构建右子树：
     *       buildTreeHelper(preorder, 2, 4, 2, 4)
     *       ├─ rootValue = preorder[2] = 20
     *       ├─ rootInorderIndex = 3
     *       ├─ leftSubtreeSize = 3 - 2 = 1
     *       │
     *       ├─ 构建左子树：
     *       │   buildTreeHelper(preorder, 3, 3, 2, 2)
     *       │   └─ 返回 TreeNode(15)
     *       │
     *       └─ 构建右子树：
     *           buildTreeHelper(preorder, 4, 4, 4, 4)
     *           └─ 返回 TreeNode(7)
     *       └─ 返回 TreeNode(20)
     *   └─ 返回 TreeNode(3)
     *
     * 最终构建的树：
     *       3
     *      / \
     *     9  20
     *        / \
     *       15  7
     *
     * ✅ 验证：
     * 先序遍历：3 → 9 → 20 → 15 → 7 ✅
     * 中序遍历：9 → 3 → 15 → 20 → 7 ✅
     *
     * ---
     *
     * 【区间划分图解】
     *
     * 初始：
     * preorder: [3, 9, 20, 15, 7]
     *            ↑
     *         root
     *
     * inorder:  [9, 3, 15, 20, 7]
     *            ↑  ↑
     *         left  root
     *
     * 根节点 3 将 inorder 分为：
     * 左子树 inorder: [9]          (inStart=0, inEnd=0)
     * 右子树 inorder: [15, 20, 7]  (inStart=2, inEnd=4)
     *
     * 左子树大小 = 1，所以 preorder 中：
     * 左子树 preorder: [9]          (preStart=1, preEnd=1)
     * 右子树 preorder: [20, 15, 7]  (preStart=2, preEnd=4)
     *
     * 递归处理右子树：
     * preorder: [20, 15, 7]
     *            ↑
     *         root
     *
     * inorder: [15, 20, 7]
     *           ↑   ↑
     *         left  root
     *
     * 继续递归直到区间为空...
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 先序和中序的关系是什么？
     *    - 先序：第一个元素是根
     *    - 中序：根左边是左子树，右边是右子树
     *    - 通过根节点位置可以确定左右子树的大小
     *
     * 2️⃣ 哈希表的作用是什么？
     *    - 存储中序遍历值到索引的映射
     *    - 避免每次查找根节点位置都遍历中序数组
     *    - 时间复杂度从 O(n²) 优化到 O(n)
     *
     * 3️⃣ 区间边界如何计算？
     *    - 左子树 preorder: [preStart+1, preStart+leftSize]
     *    - 右子树 preorder: [preStart+leftSize+1, preEnd]
     *    - 左子树 inorder: [inStart, rootIndex-1]
     *    - 右子树 inorder: [rootIndex+1, inEnd]
     *
     * 4️⃣ leftSize 如何计算？
     *    - leftSize = rootInorderIndex - inStart
     *    - 根节点在中序数组中的位置减去中序起始位置
     *    - 就是左子树的节点数
     *
     * 5️⃣ 递归终止条件是什么？
     *    - preStart > preEnd 或 inStart > inEnd
     *    - 区间为空时返回 null
     *    - 表示该子树为空
     *
     * 6️⃣ 为什么不需要处理重复元素？
     *    - 题目保证树中没有重复元素
     *    - 所以哈希表中不会有冲突
     *    - 如果有重复元素，需要其他方法
     *
     * 7️⃣ 能否只用先序或中序构建树？
     *    - 不能，单种遍历无法唯一确定一棵树
     *    - 需要两种遍历的信息结合
     *    - 先序确定根，中序确定左右子树
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ 构建中序哈希表（值→索引）
     *    └─ 调用 buildTreeHelper(preorder, 0, n-1, 0, n-1)
     *
     *    递归函数 buildTreeHelper(pre, ps, pe, is, ie)：
     *    ├─ base case: ps > pe || is > ie → return null
     *    ├─ rootValue = pre[ps]
     *    ├─ root = new TreeNode(rootValue)
     *    ├─ rootIndex = inorderMap.get(rootValue)
     *    ├─ leftSize = rootIndex - is
     *    ├─ root.left = buildTreeHelper(pre, ps+1, ps+leftSize, is, rootIndex-1)
     *    ├─ root.right = buildTreeHelper(pre, ps+leftSize+1, pe, rootIndex+1, ie)
     *    └─ return root
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>区间边界</b>：注意 preStart+leftSize+1 这个边界，不能写错</li>
     *   <li><b>哈希表构建</b>：必须在递归前构建完成</li>
     *   <li><b>递归终止</b>：两个区间条件满足一个即可返回 null</li>
     *   <li><b>数组长度</b>：preorder 和 inorder 长度必须相等</li>
     *   <li><b>重复元素</b>：题目保证无重复，否则需要其他处理</li>
     * </ul>
     *
     * @param preorder 先序遍历数组
     * @param inorder  中序遍历数组
     * @return 构建的二叉树根节点
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // 📦 构建中序遍历值到索引的映射，用于快速查找
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        // 🔄 调用递归构建函数，处理整个区间
        return buildTreeHelper(preorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    /**
     * 递归构建二叉树
     *
     * @param preorder 先序遍历数组
     * @param preStart 先序区间起始索引
     * @param preEnd   先序区间结束索引
     * @param inStart  中序区间起始索引
     * @param inEnd    中序区间结束索引
     * @return 当前区间构建的子树根节点
     */
    private TreeNode buildTreeHelper(int[] preorder, int preStart, int preEnd, int inStart, int inEnd) {
        // 🎯 基本情况：区间为空，返回 null
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        // 🔍 获取当前子树的根节点值（先序遍历的第一个元素）
        int rootValue = preorder[preStart];
        // 🏗️ 创建根节点
        TreeNode root = new TreeNode(rootValue);

        // 🔍 在中序数组中找到根节点的位置
        int rootInorderIndex = inorderMap.get(rootValue);
        // 📏 计算左子树的节点数量
        int leftSubtreeSize = rootInorderIndex - inStart;

        // 🔄 递归构建左子树
        root.left = buildTreeHelper(
                preorder,
                preStart + 1,                    // 左子树先序起始
                preStart + leftSubtreeSize,       // 左子树先序结束
                inStart,                          // 左子树中序起始
                rootInorderIndex - 1              // 左子树中序结束
        );

        // 🔄 递归构建右子树
        root.right = buildTreeHelper(
                preorder,
                preStart + leftSubtreeSize + 1,   // 右子树先序起始
                preEnd,                          // 右子树先序结束
                rootInorderIndex + 1,             // 右子树中序起始
                inEnd                            // 右子树中序结束
        );

        // ✅ 返回构建好的子树根节点
        return root;
    }
}

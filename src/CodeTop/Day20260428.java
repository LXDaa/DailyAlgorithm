package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/balanced-binary-tree/description/">110. 平衡二叉树</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>给定一个二叉树，判断它是否是平衡二叉树。</p>
 * <p><b>平衡二叉树定义</b>：一个二叉树每个节点的左右两个子树的高度差的绝对值不超过 1。</p>
 *
 * <h3>💡 核心思路：两种经典解法</h3>
 * <ul>
 *   <li><b>解法1：自底向上（优化）</b>
 *     <ul>
 *       <li><b>思想</b>：后序遍历，从叶子节点向上计算高度</li>
 *       <li><b>关键技巧</b>：用 -1 表示不平衡，提前终止递归</li>
 *       <li><b>优点</b>：时间复杂度 O(n)，每个节点只访问一次</li>
 *       <li><b>缺点</b>：代码稍复杂，需要理解 -1 的含义</li>
 *     </ul>
 *   </li>
 *   <li><b>解法2：自顶向下（暴力）</b>
 *     <ul>
 *       <li><b>思想</b>：对每个节点，计算左右子树高度差，再递归检查子树</li>
 *       <li><b>关键</b>：Math.abs(height(left) - height(right)) <= 1</li>
 *       <li><b>优点</b>：代码直观，易于理解</li>
 *       <li><b>缺点</b>：时间复杂度 O(n²)，存在重复计算</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>解法1（自底向上）</b>：
 *     <ol>
 *       <li>调用 getHeight(root)，如果返回 -1 则不平衡，否则平衡</li>
 *       <li>getHeight 函数：
 *         <ul>
 *           <li>如果 node 为空，返回 0</li>
 *           <li>递归计算左子树高度 left = getHeight(node.left)</li>
 *           <li>递归计算右子树高度 right = getHeight(node.right)</li>
 *           <li>如果 left == -1 或 right == -1 或 |left - right| > 1，返回 -1（不平衡）</li>
 *           <li>否则返回 max(left, right) + 1</li>
 *         </ul>
 *       </li>
 *     </ol>
 *   </li>
 *   <li><b>解法2（自顶向下）</b>：
 *     <ol>
 *       <li>如果 root 为空，返回 true</li>
 *       <li>检查当前节点：|height(left) - height(right)| <= 1</li>
 *       <li>递归检查左子树：isBalanced2(root.left)</li>
 *       <li>递归检查右子树：isBalanced2(root.right)</li>
 *       <li>三者同时满足才返回 true</li>
 *     </ol>
 *   </li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例1：平衡二叉树
 *        3
 *       / \
 *      9   20
 *         /  \
 *        15   7
 *
 * 最大深度差 = 1 ✅ 平衡
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 【解法1：自底向上 getHeight 展开】
 *
 * getHeight(3)
 *   ├── left = getHeight(9)
 *   │          ├── left = getHeight(null) = 0
 *   │          ├── right = getHeight(null) = 0
 *   │          └── |0-0| <= 1 → 返回 max(0,0)+1 = 1
 *   │
 *   ├── right = getHeight(20)
 *   │           ├── left = getHeight(15)
 *   │           │          ├── left = getHeight(null) = 0
 *   │           │          ├── right = getHeight(null) = 0
 *   │           │          └── 返回 max(0,0)+1 = 1
 *   │           │
 *   │           ├── right = getHeight(7)
 *   │           │           ├── left = getHeight(null) = 0
 *   │           │           ├── right = getHeight(null) = 0
 *   │           │           └── 返回 max(0,0)+1 = 1
 *   │           │
 *   │           └── |1-1| <= 1 → 返回 max(1,1)+1 = 2
 *   │
 *   └── |1-2| <= 1 → 返回 max(1,2)+1 = 3
 *
 * getHeight(3) = 3 ≠ -1 → 平衡 ✅
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 示例2：不平衡二叉树
 *        1
 *       / \
 *      2   2
 *     / \
 *    3   3
 *   / \
 *  4   4
 *
 * 左子树高度 = 3，右子树高度 = 1
 * 高度差 = 2 > 1 ❌ 不平衡
 *
 * 【解法1：自底向上 getHeight 展开】
 *
 * getHeight(1)
 *   ├── left = getHeight(2)
 *   │          ├── left = getHeight(3)
 *   │          │          ├── left = getHeight(4) = 1
 *   │          │          ├── right = getHeight(4) = 1
 *   │          │          └── |1-1| <= 1 → 返回 2
 *   │          │
 *   │          ├── right = getHeight(3)
 *   │          │           ├── left = getHeight(4) = 1
 *   │          │           ├── right = getHeight(4) = 1
 *   │          │           └── 返回 2
 *   │          │
 *   │          └── |2-2| <= 1 → 返回 3
 *   │
 *   ├── right = getHeight(2)
 *   │           ├── left = getHeight(null) = 0
 *   │           ├── right = getHeight(null) = 0
 *   │           └── 返回 1
 *   │
 *   └── |3-1| = 2 > 1 → 返回 -1 ❌
 *
 * getHeight(1) = -1 → 不平衡 ❌
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 【解法2：自顶向下 isBalanced2 展开】
 *
 * isBalanced2(1)
 *   ├── |height(2) - height(2)| = |3-1| = 2 > 1 ❌
 *   └── 直接返回 false（短路求值，不再递归检查子树）
 *
 * 注意：解法2会重复计算 height，效率较低
 * </pre>
 *
 * <h3>🔑 关键技巧解析</h3>
 * <pre>
 * 1️⃣ 为什么解法1用 -1 表示不平衡？
 *    - 避免使用全局变量或额外的返回值
 *    - -1 是一个特殊值，正常高度不会为负数
 *    - 一旦某处不平衡，向上传递 -1，提前终止
 *
 * 2️⃣ 解法1为什么是 O(n)？
 *    - 每个节点只访问一次
 *    - 一旦发现不平衡，立即返回 -1，不再继续
 *    - 后序遍历：先处理子节点，再处理父节点
 *
 * 3️⃣ 解法2为什么是 O(n²)？
 *    - 对每个节点都要计算 height
 *    - height 函数本身是 O(n)
 *    - 总共 n 个节点，所以是 O(n²)
 *    - 例如：计算根节点的 height 遍历所有节点
 *           计算左子节点 height 又遍历大部分节点
 *
 * 4️⃣ 自底向上 vs 自顶向下？
 *    - 自底向上（解法1）：后序遍历，先算子树，再算父节点
 *    - 自顶向下（解法2）：前序遍历，先检查父节点，再递归子节点
 *    - 自底向上更高效，避免重复计算
 *
 * 5️⃣ 时间复杂度对比：
 *    - 解法1：O(n)，最优
 *    - 解法2：O(n²)，最坏情况（链表）
 *
 * 6️⃣ 空间复杂度：O(h)
 *    - h 为树高（递归栈深度）
 *    - 最坏情况（链表）：O(n)
 *    - 最好情况（平衡树）：O(log n)
 *
 * 7️⃣ 平衡条件的理解？
 *    - 不是整棵树的最大深度差 <= 1
 *    - 而是每个节点的左右子树高度差 <= 1
 *    - 必须递归检查所有节点
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>空树处理</b>：root 为 null 时返回 true（空树是平衡的）</li>
 *   <li><b>解法选择</b>：推荐解法1（自底向上），效率更高</li>
 *   <li><b>-1 的含义</b>：在解法1中，-1 表示不平衡，不是真正的高度</li>
 *   <li><b>短路求值</b>：解法2中 && 运算符，前面为 false 后面不执行</li>
 *   <li><b>递归基</b>：node == null 时返回 0（高度为 0）</li>
 *   <li><b>绝对值判断</b>：Math.abs(left - right) <= 1</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260428 {
    /**
     * ✅ 解法1：自底向上（优化）判断是否为平衡二叉树
     *
     * @param root 二叉树根节点
     * @return 如果是平衡二叉树返回 true，否则返回 false
     */
    public boolean isBalancedBottomUp(TreeNode root) {
        // 🛑 边界条件：空树是平衡的
        if (root == null) {
            return true;
        }

        // 🔍 如果 getHeight 返回 -1，说明不平衡
        return getHeight(root) != -1;
    }

    /**
     * 📏 计算节点高度，如果不平衡返回 -1（解法1辅助方法）
     *
     * @param node 当前节点
     * @return 节点高度，如果不平衡返回 -1
     */
    private int getHeight(TreeNode node) {
        // 🛑 递归基：空节点高度为 0
        if (node == null) {
            return 0;
        }

        // 🔄 后序遍历：先计算左右子树高度
        int left = getHeight(node.left);
        int right = getHeight(node.right);

        // ❌ 如果左子树或右子树不平衡，或高度差 > 1，返回 -1
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) {
            return -1;
        }

        // ✅ 返回当前节点的高度
        return Math.max(left, right) + 1;
    }

    /**
     * ⚖️ 解法2：自顶向下（暴力）判断是否为平衡二叉树
     *
     * @param root 二叉树根节点
     * @return 如果是平衡二叉树返回 true，否则返回 false
     */
    public boolean isBalancedTopDown(TreeNode root) {
        // 🛑 边界条件：空树是平衡的
        if (root == null) {
            return true;
        } else {
            // ⚖️ 检查当前节点 + 递归检查左右子树
            return Math.abs(height(root.left) - height(root.right)) <= 1
                    && isBalancedTopDown(root.left)
                    && isBalancedTopDown(root.right);
        }
    }

    /**
     * 📏 计算二叉树的高度（解法2辅助方法）
     *
     * @param root 当前节点
     * @return 树的高度
     */
    private int height(TreeNode root) {
        // 🛑 递归基：空节点高度为 0
        if (root == null) {
            return 0;
        } else {
            // 🔄 树高 = max(左子树高, 右子树高) + 1
            return Math.max(height(root.left), height(root.right)) + 1;
        }
    }

}

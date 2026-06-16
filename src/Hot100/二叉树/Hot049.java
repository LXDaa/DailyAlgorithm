package Hot100.二叉树;

/**
 * @author lxd
 **/
public class Hot049 {
    /**
     * <a href="https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/description/">236. 二叉树的最近公共祖先</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个二叉树, 找到该树中两个指定节点 p 和 q 的最近公共祖先（LCA）。</p>
     * <p>最近公共祖先的定义：对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。</p>
     *
     * <h3>💡 核心思路：后序遍历 + 递归查找</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用后序遍历，自底向上查找 LCA
     *     <ul>
     *       <li>如果当前节点是 p 或 q，直接返回当前节点</li>
     *       <li>递归在左右子树中查找 p 和 q</li>
     *       <li>如果左右子树都找到了，当前节点就是 LCA</li>
     *       <li>如果只在一侧找到，返回找到的节点</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用后序遍历</b>：
     *     <ul>
     *       <li>需要自底向上地查找</li>
     *       <li>先处理子节点，再处理父节点</li>
     *       <li>保证找到的是最近的公共祖先</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个节点最多访问一次</li>
     *   <li><b>空间复杂度</b>：O(n)，递归栈空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例二叉树：
     *       3
     *      / \
     *     5   1
     *    / \ / \
     *   6  2 0  8
     *      / \
     *     7   4
     *
     * p = 5, q = 1
     *
     * 【递归查找过程】
     *
     * lowestCommonAncestor(3, 5, 1)
     *   ├─ lowestCommonAncestor(5, 5, 1)
     *   │   ├─ lowestCommonAncestor(6, 5, 1)
     *   │   │   ├─ lowestCommonAncestor(null) → null
     *   │   │   ├─ lowestCommonAncestor(null) → null
     *   │   │   └─ left = null, right = null → return null
     *   │   │
     *   │   ├─ lowestCommonAncestor(2, 5, 1)
     *   │   │   ├─ lowestCommonAncestor(7, 5, 1)
     *   │   │   │   └─ left = null, right = null → return null
     *   │   │   ├─ lowestCommonAncestor(4, 5, 1)
     *   │   │   │   └─ left = null, right = null → return null
     *   │   │   └─ left = null, right = null → return null
     *   │   │
     *   │   ├─ left = null, right = null
     *   │   └─ root == p (5 == 5) → return 5
     *   │
     *   ├─ lowestCommonAncestor(1, 5, 1)
     *   │   ├─ lowestCommonAncestor(0, 5, 1)
     *   │   │   └─ left = null, right = null → return null
     *   │   ├─ lowestCommonAncestor(8, 5, 1)
     *   │   │   └─ left = null, right = null → return null
     *   │   ├─ left = null, right = null
     *   │   └─ root == q (1 == 1) → return 1
     *   │
     *   ├─ left = 5, right = 1
     *   └─ left != null && right != null → return 3
     *
     * 最终结果：3 ✅
     *
     * ---
     *
     * 【LCA 识别规则】
     *
     * 情况1：p 和 q 在不同子树
     *       3 ← LCA
     *      / \
     *     5   1
     *     p   q
     *   左右子树都找到 → 返回当前节点
     *
     * 情况2：p 是 q 的祖先
     *       3
     *      /
     *     5 ← LCA
     *    / \
     *   6   2
     *       \
     *        4
     *       p
     *   左子树找到，右子树为空 → 返回左子树结果
     *
     * 情况3：q 是 p 的祖先
     *       3
     *      /
     *     5 ← LCA
     *    / \
     *   6   2
     *   p
     *   当前节点就是 p → 直接返回
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 递归终止条件是什么？
     *    - root == null：到达叶子节点，返回 null
     *    - root == p 或 root == q：找到目标节点，直接返回
     *    - 这两种情况都表示找到了一个目标节点
     *
     * 2️⃣ 如何判断当前节点是 LCA？
     *    - left != null && right != null
     *    - 表示 p 和 q 分别在左右子树中
     *    - 当前节点就是最近的公共祖先
     *
     * 3️⃣ 如何处理只在一侧找到的情况？
     *    - left == null && right != null → 返回 right
     *    - left != null && right == null → 返回 left
     *    - 简化为：return left == null ? right : left
     *
     * 4️⃣ 为什么后序遍历？
     *    - 先处理子节点，再处理父节点
     *    - 保证找到的是最近的公共祖先
     *    - 如果用前序遍历，可能找到的是更远的祖先
     *
     * 5️⃣ p 或 q 不存在怎么办？
     *    - 题目保证 p 和 q 都存在于树中
     *    - 如果不存在，会返回找到的那个节点
     *    - 实际应用中需要额外检查
     *
     * 6️⃣ 一个节点可以是自己的祖先吗？
     *    - 可以！题目明确说明
     *    - 例如 p = q 时，返回 p 或 q
     *    - 这也是为什么 root == p 时直接返回
     *
     * 7️⃣ 与 BST 的 LCA 有什么区别？
     *    - BST 可以利用值的大小关系
     *    - 普通二叉树只能遍历查找
     *    - BST 的 LCA 时间复杂度是 O(log n)
     *
     * 8️⃣ 完整流程总结：
     *
     *    递归函数 lowestCommonAncestor(root, p, q)：
     *    ├─ base case:
     *    │   ├─ root == null → return null
     *    │   └─ root == p || root == q → return root
     *    ├─ left = lowestCommonAncestor(root.left, p, q)
     *    ├─ right = lowestCommonAncestor(root.right, p, q)
     *    ├─ if (left != null && right != null) → return root
     *    └─ return left == null ? right : left
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>递归终止</b>：必须先检查 root == null，再检查 root == p 或 root == q</li>
     *   <li><b>返回值</b>：返回的是节点对象，不是节点值</li>
     *   <li><b>存在性保证</b>：题目保证 p 和 q 都存在，无需额外检查</li>
     *   <li><b>自引用</b>：一个节点可以是自己的祖先</li>
     *   <li><b>遍历顺序</b>：后序遍历（先子后父），保证找到最近的祖先</li>
     * </ul>
     *
     * @param root 二叉树的根节点
     * @param p    节点 p
     * @param q    节点 q
     * @return p 和 q 的最近公共祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 🎯 基本情况：空节点，返回 null
        if (root == null) {
            return null;
        }
        // 🎯 基本情况：当前节点是 p 或 q，直接返回
        if (root == p || root == q) {
            return root;
        }

        // 🔄 递归在左子树中查找 p 和 q
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        // 🔄 递归在右子树中查找 p 和 q
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 📍 如果左右子树都找到了，当前节点就是 LCA
        if (left != null && right != null) {
            return root;
        }

        // 📍 如果只在一侧找到，返回找到的节点
        // 如果两侧都没找到，返回 null
        return left == null ? right : left;
    }
}

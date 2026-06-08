package Hot100.二叉树;

/**
 * @author lxd
 **/
public class Hot038 {
    /**
     * <a href="https://leetcode.cn/problems/invert-binary-tree/description/">226. 翻转二叉树</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。</p>
     * <p>翻转（又称镜像）一棵二叉树是指：将二叉树每个节点的左右子节点交换位置。</p>
     *
     * <h3>💡 核心思路：递归交换左右子树</h3>
     * <ul>
     *   <li><b>基本思想</b>：递归地交换每个节点的左右子节点
     *     <ul>
     *       <li>第一步：递归处理左子树</li>
     *       <li>第二步：递归处理右子树</li>
     *       <li>第三步：交换当前节点的左右子节点</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用递归</b>：
     *     <ul>
     *       <li>二叉树是递归结构，每个子树也是二叉树</li>
     *       <li>交换操作对每个节点都是一样的</li>
     *       <li>递归代码简洁直观</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个节点访问一次</li>
     *   <li><b>空间复杂度</b>：O(n)，递归栈空间，最坏情况为链表形状</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例二叉树：
     *       1              1
     *      / \    →      / \
     *     2   3         3   2
     *    / \           / \
     *   4   5         5   4
     *
     * 【递归调用过程】
     *
     * invertTree(1)
     *   ├─ invertTree(2)              // 递归处理左子树
     *   │   ├─ invertTree(4)          // 递归处理左子树的左子树
     *   │   │   ├─ invertTree(null)   // 左子树为空
     *   │   │   ├─ invertTree(null)   // 右子树为空
     *   │   │   └─ return 4（叶子节点，无需交换）
     *   │   │
     *   │   ├─ invertTree(5)          // 递归处理左子树的右子树
     *   │   │   ├─ invertTree(null)
     *   │   │   ├─ invertTree(null)
     *   │   │   └─ return 5（叶子节点）
     *   │   │
     *   │   └─ 交换节点 2 的左右子节点：left=5, right=4
     *   │       return 2
     *   │
     *   ├─ invertTree(3)              // 递归处理右子树
     *   │   ├─ invertTree(null)       // 左子树为空
     *   │   ├─ invertTree(null)       // 右子树为空
     *   │   └─ return 3（叶子节点）
     *   │
     *   └─ 交换节点 1 的左右子节点：left=3, right=2
     *       return 1
     *
     * 最终结果：翻转后的二叉树 ✅
     *
     * ---
     *
     * 【递归树结构】
     *
     *              invertTree(1)
     *              /            \
     *      invertTree(2)    invertTree(3)
     *         ↕ 交换            ↕ 交换
     *       2(5,4)            3(null,null)
     *          \
     *        invertTree(4)   invertTree(5)
     *             |               |
     *           return 4       return 5
     *
     * 【翻转过程可视化】
     *
     * 步骤1：处理节点 4
     *   [4] → [4]（叶子节点，无需交换）
     *
     * 步骤2：处理节点 5
     *   [5] → [5]（叶子节点，无需交换）
     *
     * 步骤3：处理节点 2
     *   [2, 4, 5] → [2, 5, 4]
     *   交换 left=4 和 right=5
     *
     * 步骤4：处理节点 3
     *   [3] → [3]（叶子节点，无需交换）
     *
     * 步骤5：处理节点 1
     *   [1, 2, 3] → [1, 3, 2]
     *   交换 left=2 和 right=3
     *
     * 最终结果：
     *       1
     *      / \
     *     3   2
     *        / \
     *       5   4
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要先递归后交换？
     *    - 必须先处理子树，才能交换当前节点
     *    - 如果先交换，子树信息就丢失了
     *    - 正确的顺序：递归左右 → 交换当前
     *
     * 2️⃣ 递归的终止条件是什么？
     *    - root == null：空节点，无需处理，直接返回
     *    - 这是递归的 base case
     *
     * 3️⃣ 交换操作的原理？
     *    - left = invertTree(root.left)：递归处理左子树
     *    - right = invertTree(root.right)：递归处理右子树
     *    - root.left = right：左指针指向原右子树
     *    - root.right = left：右指针指向原左子树
     *    - 交换后，原来的左子树变成了右子树
     *
     * 4️⃣ 为什么返回 root 而不是 new TreeNode？
     *    - 不需要创建新节点
     *    - 只需要修改现有节点的指针
     *    - 直接返回原树的根节点即可
     *
     * 5️⃣ 与遍历顺序有关吗？
     *    - 前序、中序、后序都可以完成翻转
     *    - 只要确保递归调用在交换之前
     *    - 本题使用前序遍历（根-左-右）
     *
     * 6️⃣ 能否用迭代法？
     *    - 可以，使用队列或栈进行层序/前序遍历
     *    - 遍历时交换每个节点的左右子节点
     *    - 递归更简洁，面试首选
     *
     * 7️⃣ 翻转的子树是否会影响父节点？
     *    - 不会，递归调用会返回处理后的子树根节点
     *    - 父节点通过 left/right 指针获取子树
     *    - 指针修改是安全的
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    └─ 调用 invertTree(root)
     *
     *    递归函数 invertTree(node)：
     *    ├─ 基本情况：node == null → 返回 null
     *    ├─ 递归处理左子树：left = invertTree(node.left)
     *    ├─ 递归处理右子树：right = invertTree(node.right)
     *    ├─ 交换指针：node.left = right, node.right = left
     *    └─ 返回：node
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>递归顺序</b>：必须先递归处理子树，再交换当前节点</li>
     *   <li><b>空节点处理</b>：root == null 时直接返回 null</li>
     *   <li><b>指针交换</b>：同时修改 left 和 right 两个指针</li>
     *   <li><b>返回值</b>：返回原树的根节点，不需要新建节点</li>
     *   <li><b>时间复杂度</b>：O(n)，每个节点恰好访问一次</li>
     * </ul>
     *
     * @param root 二叉树的根节点
     * @return 翻转后的二叉树根节点
     */
    public TreeNode invertTree(TreeNode root) {
        // 🎯 基本情况：空节点，无需翻转，直接返回
        if (root == null) {
            return null;
        }

        // 🔄 递归处理左子树，返回翻转后的左子树根节点
        TreeNode left = invertTree(root.left);
        // 🔄 递归处理右子树，返回翻转后的右子树根节点
        TreeNode right = invertTree(root.right);

        // 🔀 交换当前节点的左右子节点
        // 翻转后，原来的左子树变成右子树，原来的右子树变成左子树
        root.left = right;
        root.right = left;

        // ✅ 返回翻转后的当前节点
        return root;
    }
}

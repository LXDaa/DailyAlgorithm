package Hot100.二叉树;

/**
 * @author lxd
 **/
public class Hot043 {
    /**
     * 记录前一个节点的值，用于中序遍历时比较
     * 使用 long 类型避免溢出（TreeNode.val 范围是 int）
     */
    private long pre = Long.MIN_VALUE;

    /**
     * <a href="https://leetcode.cn/problems/validate-binary-search-tree/description/">98. 验证二叉搜索树</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。</p>
     * <p>有效二叉搜索树定义如下：
     * <ul>
     *   <li>节点的左子树只包含小于当前节点的数</li>
     *   <li>节点的右子树只包含大于当前节点的数</li>
     *   <li>所有左子树和右子树自身必须也是二叉搜索树</li>
     * </ul>
     * </p>
     *
     * <h3>💡 核心思路：中序遍历验证有序性</h3>
     * <ul>
     *   <li><b>基本思想</b>：利用 BST 的中序遍历结果是升序序列的性质
     *     <ul>
     *       <li>中序遍历：左 → 根 → 右</li>
     *       <li>遍历过程中记录前一个节点的值</li>
     *       <li>当前节点值必须大于前一个节点值</li>
     *       <li>如果不满足，说明不是有效的 BST</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用中序遍历</b>：
     *     <ul>
     *       <li>BST 的中序遍历结果是严格递增的</li>
     *       <li>验证递增性即可验证 BST 有效性</li>
     *       <li>递归实现简洁直观</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个节点访问一次</li>
     *   <li><b>空间复杂度</b>：O(n)，递归栈空间，最坏情况为链表</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例1：有效 BST
     *       5
     *      / \
     *     3   7
     *    / \
     *   2   4
     *
     * 【中序遍历过程】
     *
     * 初始：pre = Long.MIN_VALUE
     *
     * isValidBST(5)
     *   ├─ isValidBST(3)
     *   │   ├─ isValidBST(2)
     *   │   │   ├─ isValidBST(null) → true
     *   │   │   ├─ 检查：2 > pre(-∞) → true
     *   │   │   ├─ pre = 2
     *   │   │   └─ isValidBST(null) → true
     *   │   │   返回 true
     *   │   │
     *   │   ├─ 检查：3 > pre(2) → true
     *   │   ├─ pre = 3
     *   │   └─ isValidBST(4)
     *   │       ├─ isValidBST(null) → true
     *   │       ├─ 检查：4 > pre(3) → true
     *   │       ├─ pre = 4
     *   │       └─ isValidBST(null) → true
     *   │       返回 true
     *   │   返回 true
     *   │
     *   ├─ 检查：5 > pre(4) → true
     *   ├─ pre = 5
     *   └─ isValidBST(7)
     *       ├─ isValidBST(null) → true
     *       ├─ 检查：7 > pre(5) → true
     *       ├─ pre = 7
     *       └─ isValidBST(null) → true
     *       返回 true
     *   返回 true
     *
     * 最终结果：true ✅
     *
     * ---
     *
     * 示例2：无效 BST（右子树有问题）
     *       5
     *      / \
     *     3   7
     *        /
     *       4
     *
     * 【中序遍历过程】
     *
     * isValidBST(5)
     *   ├─ isValidBST(3) → 返回 true
     *   │   pre = 3
     *   │
     *   ├─ 检查：5 > pre(3) → true
     *   ├─ pre = 5
     *   └─ isValidBST(7)
     *       ├─ isValidBST(4)
     *       │   ├─ isValidBST(null) → true
     *       │   ├─ 检查：4 > pre(5) → false ❌
     *       │   └─ 返回 false
     *       └─ 返回 false
     *   返回 false
     *
     * 最终结果：false ✅（因为 4 < 5，不满足 BST 性质）
     *
     * ---
     *
     * 【中序遍历顺序】
     *
     * 示例1遍历顺序：2 → 3 → 4 → 5 → 7（严格递增）
     * 示例2遍历顺序：3 → 5 → 4 → 7（4 < 5，不递增）
     *
     * BST 的定义要求：
     *   - 左子树所有节点 < 当前节点
     *   - 右子树所有节点 > 当前节点
     *   - 中序遍历正好验证了这一点
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用 long 类型存储 pre？
     *    - TreeNode.val 的范围是 int（-2^31 ~ 2^31-1）
     *    - 如果树中包含 Integer.MIN_VALUE，用 int 会溢出
     *    - 使用 long 可以处理所有情况
     *    - 初始值设为 Long.MIN_VALUE
     *
     * 2️⃣ 为什么用 <= 而不是 < 比较？
     *    - BST 要求严格递增
     *    - 不允许重复值
     *    - 如果有重复值，说明不是有效的 BST
     *
     * 3️⃣ 递归顺序为什么是左-根-右？
     *    - 这是中序遍历的定义
     *    - BST 的中序遍历结果是有序的
     *    - 如果顺序错误，无法正确验证
     *
     * 4️⃣ 全局变量的作用是什么？
     *    - 跨递归调用保存状态
     *    - 记录前一个节点的值
     *    - 避免每次递归都传递参数
     *
     * 5️⃣ 空树是否是有效的 BST？
     *    - 根据定义，空树是有效的 BST
     *    - 所以 root == null 时返回 true
     *    - 叶子节点的子节点都是 null，也返回 true
     *
     * 6️⃣ 边界情况如何处理？
     *    - 单节点树：只有一个节点，pre 从 -∞ 更新为该节点值
     *    - 最小整数：用 long 处理，避免溢出
     *    - 最大整数：同样用 long 处理
     *
     * 7️⃣ 能否用迭代法？
     *    - 可以，使用栈模拟递归
     *    - 遍历顺序相同：左 → 根 → 右
     *    - 递归更简洁，面试首选
     *
     * 8️⃣ 完整流程总结：
     *
     *    初始化：
     *    └─ pre = Long.MIN_VALUE
     *
     *    递归函数 isValidBST(node)：
     *    ├─ base case: node == null → return true
     *    ├─ 递归左子树：if (!isValidBST(node.left)) → return false
     *    ├─ 检查当前节点：if (node.val <= pre) → return false
     *    ├─ 更新 pre：pre = node.val
     *    └─ 递归右子树：return isValidBST(node.right)
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>数据类型</b>：pre 必须用 long 类型，避免 int 溢出</li>
     *   <li><b>比较运算符</b>：必须用 <=，不允许重复值</li>
     *   <li><b>遍历顺序</b>：必须是中序遍历（左-根-右）</li>
     *   <li><b>空节点</b>：空树是有效的 BST，返回 true</li>
     *   <li><b>全局变量</b>：每次调用前会重置，保证正确性</li>
     * </ul>
     *
     * @param root 二叉树的根节点
     * @return 是否为有效的二叉搜索树
     */
    public boolean isValidBST(TreeNode root) {
        // 🎯 基本情况：空树是有效的 BST
        if (root == null) {
            return true;
        }

        // 🔄 递归检查左子树
        if (!isValidBST(root.left)) {
            return false;
        }

        // 📍 检查当前节点：必须大于前一个节点值
        if (root.val <= pre) {
            return false;
        }

        // 📈 更新前一个节点值为当前节点值
        pre = root.val;

        // 🔄 递归检查右子树
        return isValidBST(root.right);
    }
}

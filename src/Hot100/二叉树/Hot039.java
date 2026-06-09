package Hot100.二叉树;

/**
 * @author lxd
 **/
public class Hot039 {
    /**
     * <a href="https://leetcode.cn/problems/symmetric-tree/description/">101. 对称二叉树</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个二叉树的根节点 root ，检查它是否轴对称。</p>
     * <p>轴对称的定义：镜像对称，即左右子树互为镜像。</p>
     *
     * <h3>💡 核心思路：递归检查镜像</h3>
     * <ul>
     *   <li><b>基本思想</b>：递归检查两个子树是否对称
     *     <ul>
     *       <li>两个根节点的值相等</li>
     *       <li>左子树的左节点与右子树的右节点对称</li>
     *       <li>左子树的右节点与右子树的左节点对称</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用递归</b>：
     *     <ul>
     *       <li>对称检查具有递归结构</li>
     *       <li>每个子问题都是相同的检查逻辑</li>
     *       <li>递归代码简洁直观</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个节点访问一次</li>
     *   <li><b>空间复杂度</b>：O(n)，递归栈空间，最坏情况为链表形状</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例1：对称二叉树
     *       1
     *      / \
     *     2   2
     *    / \ / \
     *   3  4 4  3
     *
     * 【递归检查过程】
     *
     * isSymmetric(1)
     *   └─ check(left=2, right=2)
     *       ├─ 检查：val(2) == val(2) ✅
     *       ├─ check(left.left=3, right.right=3)
     *       │   ├─ 检查：val(3) == val(3) ✅
     *       │   ├─ check(null, null) → true
     *       │   └─ check(null, null) → true
     *       │   └─ return true
     *       │
     *       └─ check(left.right=4, right.left=4)
     *           ├─ 检查：val(4) == val(4) ✅
     *           ├─ check(null, null) → true
     *           └─ check(null, null) → true
     *           └─ return true
     *       └─ return true
     *   └─ return true
     *
     * 最终结果：true ✅
     *
     * ---
     *
     * 示例2：非对称二叉树
     *       1
     *      / \
     *     2   2
     *      \   \
     *      3    3
     *
     * 【递归检查过程】
     *
     * isSymmetric(1)
     *   └─ check(left=2, right=2)
     *       ├─ 检查：val(2) == val(2) ✅
     *       ├─ check(left.left=null, right.right=null) → true
     *       │
     *       └─ check(left.right=3, right.left=null)
     *           └─ 检查：left!=null && right==null → false
     *       └─ return false
     *   └─ return false
     *
     * 最终结果：false ✅
     *
     * ---
     *
     * 【镜像对称检查规则】
     *
     * 检查条件（必须全部满足）：
     * 1. left.val == right.val           // 值相等
     * 2. check(left.left, right.right)   // 外侧对称
     * 3. check(left.right, right.left)   // 内侧对称
     *
     * 边界情况：
     * - 都为空 → true（对称）
     * - 一个为空，一个不为空 → false（不对称）
     *
     * 对称检查图解：
     *
     *       根
     *      /   \
     *     L     R
     *    / \   / \
     *   LL LR RL RR
     *
     * 对称关系：
     *   L.val == R.val
     *   LL ↔ RR（外侧对称）
     *   LR ↔ RL（内侧对称）
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要同时检查两个节点？
     *    - 对称检查需要比较左右两个子树
     *    - 递归函数参数是两个节点：left 和 right
     *    - 这样可以同时验证镜像关系
     *
     * 2️⃣ 递归的终止条件是什么？
     *    - 两个都为空 → 返回 true（对称）
     *    - 一个为空，一个不为空 → 返回 false（不对称）
     *    - 值不相等 → 返回 false
     *
     * 3️⃣ 递归检查的顺序是什么？
     *    - 先检查值是否相等
     *    - 再递归检查外侧：left.left vs right.right
     *    - 最后递归检查内侧：left.right vs right.left
     *    - 使用 && 连接，短路求值优化
     *
     * 4️⃣ 为什么要分开检查空节点？
     *    - 如果不分开检查，直接访问 val 会 NPE
     *    - 必须先处理边界情况
     *    - 顺序：都为空 → 部分为空 → 都不为空
     *
     * 5️⃣ 能否用迭代法？
     *    - 可以，使用队列或栈
     *    - 将左右子节点成对入队
     *    - 每次出队两个节点进行比较
     *    - 递归更简洁，面试首选
     *
     * 6️⃣ 为什么不是检查 left.left == right.left？
     *    - 镜像对称要求：左的左等于右的右
     *    - 如果是 left.left == right.left，那是完全相同的树
     *    - 对称是镜像关系，不是相等关系
     *
     * 7️⃣ 空树是否对称？
     *    - 空树是对称的
     *    - 单节点树也是对称的
     *    - 本题中 root 为 null 时返回 true
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    └─ 调用 check(root.left, root.right)
     *
     *    递归函数 check(left, right)：
     *    ├─ 都为空 → return true
     *    ├─ 部分为空 → return false
     *    ├─ 值不相等 → return false
     *    └─ 递归检查：
     *       └─ return val相等 && check(left.left, right.right) && check(left.right, right.left)
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>空节点处理</b>：必须先检查空节点，再访问 val</li>
     *   <li><b>检查顺序</b>：外侧和内侧的递归调用顺序不能错</li>
     *   <li><b>镜像关系</b>：left.left 对应 right.right，不是 left.left 对应 right.left</li>
     *   <li><b>短路求值</b>：使用 && 可以提前终止无效检查</li>
     *   <li><b>root 为空</b>：题目中 root 可能为 null，需要处理</li>
     * </ul>
     *
     * @param root 二叉树的根节点
     * @return 是否为对称二叉树
     */
    public boolean isSymmetric(TreeNode root) {
        // 🔄 检查左右子树是否对称
        return check(root.left, root.right);
    }

    /**
     * 递归检查两个子树是否对称
     *
     * @param left  左子树节点
     * @param right 右子树节点
     * @return 是否对称
     */
    private boolean check(TreeNode left, TreeNode right) {
        // 🎯 情况1：两个节点都为空 → 对称
        if (left == null && right == null) {
            return true;
        }
        // 🎯 情况2：一个为空，一个不为空 → 不对称
        if (left == null || right == null) {
            return false;
        }
        // 🔄 情况3：都不为空，检查值相等且子树对称
        // 条件：值相等 && 外侧对称 && 内侧对称
        return left.val == right.val && check(left.left, right.right) && check(left.right, right.left);
    }
}

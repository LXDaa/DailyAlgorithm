package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/symmetric-tree/description/">101. 对称二叉树</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>给你一个二叉树的根节点 root，检查它是否轴对称。</p>
 * <p><b>轴对称</b>：二叉树围绕中心轴镜像对称，即左子树和右子树互为镜像。</p>
 *
 * <h3>💡 核心思路：递归比较（双指针法）</h3>
 * <ul>
 *   <li><b>问题本质</b>：判断左子树和右子树是否互为镜像</li>
 *   <li><b>镜像定义</b>：
 *     <ul>
 *       <li>两个节点的值相等</li>
 *       <li>左节点的左子树 ↔ 右节点的右子树</li>
 *       <li>左节点的右子树 ↔ 右节点的左子树</li>
 *     </ul>
 *   </li>
 *   <li><b>递归策略</b>：同时遍历左子树和右子树，交叉比较对应节点</li>
 *   <li><b>关键技巧</b>：使用两个指针 l 和 r，分别指向左子树和右子树的对应位置</li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>入口</b>：调用 check(root.left, root.right)，比较左右子树</li>
 *   <li><b>递归基</b>：
 *     <ul>
 *       <li>如果 l 和 r 都为 null → 对称，返回 true</li>
 *       <li>如果只有一个为 null → 不对称，返回 false</li>
 *     </ul>
 *   </li>
 *   <li><b>递归条件</b>：
 *     <ul>
 *       <li>当前节点值相等：l.val == r.val</li>
 *       <li>左节点的左子树 vs 右节点的右子树：check(l.left, r.right)</li>
 *       <li>左节点的右子树 vs 右节点的左子树：check(l.right, r.left)</li>
 *       <li>三者同时满足才返回 true</li>
 *     </ul>
 *   </li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例1：对称二叉树
 *        1
 *       / \
 *      2   2
 *     / \ / \
 *    3  4 4  3
 *
 * 【递归展开】
 *
 * check(2, 2)  ← 比较左右子树
 *   ✓ val: 2 == 2
 *   ├── check(3, 3)  ← 左左 vs 右右
 *   │     ✓ val: 3 == 3
 *   │     ├── check(null, null) → true ✅
 *   │     └── check(null, null) → true ✅
 *   │     返回 true
 *   │
 *   └── check(4, 4)  ← 左右 vs 右左
 *         ✓ val: 4 == 4
 *         ├── check(null, null) → true ✅
 *         └── check(null, null) → true ✅
 *         返回 true
 *
 *   返回 true && true && true = true ✅
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 示例2：不对称二叉树
 *        1
 *       / \
 *      2   2
 *       \   \
 *       3    3
 *
 * 【递归展开】
 *
 * check(2, 2)
 *   ✓ val: 2 == 2
 *   ├── check(null, 3)  ← 左左 vs 右右
 *   │     ✗ 一个为null → false ❌
 *   │
 *   └── check(3, null)  ← 左右 vs 右左
 *         ✗ 一个为null → false ❌
 *
 *   返回 true && false && false = false ❌
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 示例3：不对称（值不同）
 *        1
 *       / \
 *      2   2
 *     / \ / \
 *    3  4 3  4
 *
 * 【递归展开】
 *
 * check(2, 2)
 *   ✓ val: 2 == 2
 *   ├── check(3, 4)  ← 左左 vs 右右
 *   │     ✗ val: 3 != 4 → false ❌
 *   │
 *   └── check(4, 3)  ← 左右 vs 右左
 *         ✗ val: 4 != 3 → false ❌
 *
 *   返回 true && false && false = false ❌
 * </pre>
 *
 * <h3>🔑 关键技巧解析</h3>
 * <pre>
 * 1️⃣ 为什么是 check(l.left, r.right) 和 check(l.right, r.left)？
 *    - 这是镜像的核心：左子树的左边对应右子树的右边
 *    - 想象一面镜子在根节点处，左边的左边反射到右边的右边
 *    - 例如：
 *          1
 *         / \
 *        L   R
 *       /     \
 *      LL      RR  ← LL 和 RR 应该对称
 *
 * 2️⃣ 递归基为什么有两个？
 *    - l == null && r == null：两个都空，对称 ✅
 *    - l == null || r == null：只有一个空，不对称 ❌
 *    - 必须先判断都空的情况，再判断只有一个空
 *    - 否则会把两个都空的情况误判为不对称
 *
 * 3️⃣ 为什么用 && 连接三个条件？
 *    - 当前节点值必须相等
 *    - 左左 vs 右右 必须对称
 *    - 左右 vs 右左 必须对称
 *    - 三者缺一不可，所以用 &&
 *
 * 4️⃣ 时间复杂度：O(n)
 *    - n 为节点总数
 *    - 每个节点最多访问一次
 *
 * 5️⃣ 空间复杂度：O(h)
 *    - h 为树的高度（递归栈深度）
 *    - 最坏情况（链表）：O(n)
 *    - 最好情况（平衡树）：O(log n)
 *
 * 6️⃣ 与相同树的区别？
 *    - 相同树：check(l.left, r.left) && check(l.right, r.right)
 *    - 对称树：check(l.left, r.right) && check(l.right, r.left)
 *    - 对称是"交叉比较"，相同是"平行比较"
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>递归基顺序</b>：先判断两个都为 null，再判断只有一个为 null</li>
 *   <li><b>交叉比较</b>：左左 vs 右右，左右 vs 右左，不要搞反</li>
 *   <li><b>短路求值</b>：&& 运算符，前面为 false 后面不再执行，提高效率</li>
 *   <li><b>根节点处理</b>：根节点本身不参与比较，只比较其左右子树</li>
 *   <li><b>空树处理</b>：如果 root 为 null，root.left 和 root.right 都是 null，返回 true</li>
 *   <li><b>单节点树</b>：只有根节点，左右子树都为 null，返回 true</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260417 {
    /**
     * 🪞 检查二叉树是否轴对称
     *
     * @param root 二叉树根节点
     * @return 如果轴对称返回 true，否则返回 false
     */
    public boolean isSymmetric(TreeNode root) {
        // 🚀 比较左子树和右子树是否互为镜像
        return check(root.left, root.right);
    }

    /**
     * 🔍 递归检查两个子树是否互为镜像
     *
     * @param l 左子树的节点
     * @param r 右子树的节点
     * @return 如果 l 和 r 互为镜像返回 true，否则返回 false
     */
    private boolean check(TreeNode l, TreeNode r) {
        // ✅ 递归基1：两个节点都为空，对称
        if (l == null && r == null) {
            return true;
        }

        // ❌ 递归基2：只有一个节点为空，不对称
        if (l == null || r == null) {
            return false;
        }

        // 🔄 递归条件：值相等 + 左左vs右右 + 左右vs右左
        return l.val == r.val && check(l.left, r.right) && check(l.right, r.left);
    }
}

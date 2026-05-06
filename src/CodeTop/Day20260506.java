package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/validate-binary-search-tree/description/">98. 验证二叉搜索树</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。</p>
 * <p><b>有效二叉搜索树</b>定义如下：</p>
 * <ul>
 *   <li>节点的左子树只包含 <b>严格小于</b> 当前节点的数</li>
 *   <li>节点的右子树只包含 <b>严格大于</b> 当前节点的数</li>
 *   <li>所有左子树和右子树自身必须也是二叉搜索树</li>
 * </ul>
 *
 * <h3>💡 核心思路：两种经典方法</h3>
 * <ul>
 *   <li><b>方法一：中序遍历法</b>
 *     <ul>
 *       <li>利用 BST 的中序遍历结果是严格递增序列的特性</li>
 *       <li>使用一个变量 pre 记录前一个访问的节点值</li>
 *       <li>如果当前节点值小于等于 pre，说明不是有效的 BST</li>
 *     </ul>
 *   </li>
 *   <li><b>方法二：递归边界法</b>
 *     <ul>
 *       <li>每个节点都有一个合法的取值范围 (minValue, maxValue)</li>
 *       <li>对于根节点，范围是 (Long.MIN_VALUE, Long.MAX_VALUE)</li>
 *       <li>对于左子节点，上界更新为父节点的值；对于右子节点，下界更新为父节点的值</li>
 *       <li>检查当前节点值是否在合法范围内，然后递归检查左右子树</li>
 *     </ul>
 *   </li>
 *   <li><b>时间复杂度</b>：O(n)，需要遍历所有节点</li>
 *   <li><b>空间复杂度</b>：O(h)，h 为树的高度，递归调用栈的深度</li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>方法一：中序遍历法</b>
 *     <ol>
 *       <li>递归遍历左子树</li>
 *       <li>检查当前节点值是否大于 pre（前一个节点值）</li>
 *       <li>更新 pre 为当前节点值</li>
 *       <li>递归遍历右子树</li>
 *     </ol>
 *   </li>
 *   <li><b>方法二：递归边界法</b>
 *     <ol>
 *       <li>从根节点开始，初始范围为 (Long.MIN_VALUE, Long.MAX_VALUE)</li>
 *       <li>检查当前节点值是否在 (minValue, maxValue) 范围内</li>
 *       <li>递归检查左子树，范围更新为 (minValue, 当前节点值)</li>
 *       <li>递归检查右子树，范围更新为 (当前节点值, maxValue)</li>
 *     </ol>
 *   </li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例1：有效的 BST
 *
 *       2
 *      / \
 *     1   3
 *
 * 【方法一】中序遍历过程
 *
 * 中序遍历顺序：左 → 根 → 右
 *
 * Step 1: 遍历左子树（节点 1）
 *   - 访问节点 1，pre = Long.MIN_VALUE
 *   - 1 > Long.MIN_VALUE ✅
 *   - 更新 pre = 1
 *
 * Step 2: 访问根节点（节点 2）
 *   - 2 > 1 ✅
 *   - 更新 pre = 2
 *
 * Step 3: 遍历右子树（节点 3）
 *   - 访问节点 3
 *   - 3 > 2 ✅
 *   - 更新 pre = 3
 *
 * 返回 true ✅
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 示例2：无效的 BST
 *
 *       5
 *      / \
 *     1   4
 *        / \
 *       3   6
 *
 * 【方法一】中序遍历过程
 *
 * 中序遍历顺序：1, 5, 3, 4, 6
 *
 * Step 1: 访问节点 1
 *   - 1 > Long.MIN_VALUE ✅
 *   - pre = 1
 *
 * Step 2: 访问节点 5
 *   - 5 > 1 ✅
 *   - pre = 5
 *
 * Step 3: 访问节点 3
 *   - 3 > 5 ❌ （3 应该大于 5，但实际小于）
 *
 * 返回 false ❌
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 【方法二】递归边界法演示（示例2）
 *
 *       5
 *      / \
 *     1   4
 *        / \
 *       3   6
 *
 * Step 1: 检查根节点 5
 *   - 范围：(Long.MIN_VALUE, Long.MAX_VALUE)
 *   - 5 在范围内 ✅
 *
 * Step 2: 检查左子节点 1
 *   - 范围：(Long.MIN_VALUE, 5)
 *   - 1 在范围内 ✅
 *
 * Step 3: 检查右子节点 4
 *   - 范围：(5, Long.MAX_VALUE)
 *   - 4 不在范围内 ❌ （4 < 5）
 *
 * 返回 false ❌
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 示例3：边界情况（相等的值）
 *
 *       2
 *      / \
 *     2   2
 *
 * 【方法一】中序遍历
 *   - 访问第一个 2：pre = Long.MIN_VALUE, 2 > MIN_VALUE ✅
 *   - 访问第二个 2：2 > 2 ❌ （不严格大于）
 *
 * 返回 false ❌
 *
 * 注意：BST 要求严格小于/大于，不允许相等
 * </pre>
 *
 * <h3>🔑 关键技巧解析</h3>
 * <pre>
 * 1️⃣ 为什么用 long 类型而不是 int？
 *    - 避免边界值溢出问题
 *    - 当节点值为 Integer.MIN_VALUE 时，如果用 int 类型的 pre
 *      初始化为 Integer.MIN_VALUE，会导致误判
 *    - 使用 Long.MIN_VALUE 可以正确处理所有 int 范围内的值
 *
 * 2️⃣ 中序遍历法的优势？
 *    - 代码简洁，只需一个全局变量 pre
 *    - 直观体现 BST 的核心性质：中序遍历递增
 *    - 时间复杂度 O(n)，空间复杂度 O(h)
 *
 * 3️⃣ 递归边界法的优势？
 *    - 不需要额外变量，纯函数式
 *    - 更容易理解每个节点的约束条件
 *    - 可以在发现无效时提前终止
 *
 * 4️⃣ 为什么是严格小于/大于？
 *    - BST 的定义要求左子树 < 根 < 右子树
 *    - 相等的值会破坏 BST 的性质
 *    - 例如：如果有重复值，查找操作会出现歧义
 *
 * 5️⃣ 两种方法的对比？
 *    - 中序遍历：更简洁，但需要维护状态变量
 *    - 递归边界：更直观，参数传递清晰
 *    - 两者时间复杂度相同，都是最优解
 *
 * 6️⃣ 常见错误？
 *    - 只检查左右子节点，不检查整棵子树
 *    - 例如：5 的右子树中有 3，虽然 3 < 5 的右子节点 4
 *      但 3 < 5，违反了 BST 定义
 *    - 正确做法：检查整个子树的范围约束
 *
 * 7️⃣ 空间复杂度为什么是 O(h)？
 *    - 递归调用栈的深度等于树的高度
 *    - 平衡树：h = log(n)
 *    - 退化树（链表）：h = n
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>严格比较</b>：使用 < 和 >，不能使用 <= 或 >=</li>
 *   <li><b>边界值处理</b>：使用 long 类型避免 int 溢出</li>
 *   <li><b>全局变量</b>：中序遍历法中的 pre 是全局变量，多次调用需重置</li>
 *   <li><b>空树处理</b>：空树是有效的 BST，返回 true</li>
 *   <li><b>单节点</b>：单节点树是有效的 BST</li>
 *   <li><b>范围更新</b>：左子树更新上界，右子树更新下界</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260506 {
    // 📌 用于中序遍历，记录前一个访问的节点值
    private long pre = Long.MIN_VALUE;

    /**
     * 🔍 方法一：中序遍历法验证二叉搜索树
     * <p>利用 BST 中序遍历结果为严格递增序列的特性</p>
     *
     * @param root 二叉树根节点
     * @return 是否为有效的二叉搜索树
     */
    public boolean isValidBST(TreeNode root) {
        // 🛑 边界情况：空节点是有效的 BST
        if (root == null) {
            return true;
        }

        // 🌿 Step 1: 递归检查左子树
        if (!isValidBST(root.left)) {
            return false;
        }

        // 🔍 Step 2: 检查当前节点是否大于前一个节点（中序遍历应该是严格递增的）
        if (root.val <= pre) {
            return false; // ❌ 违反 BST 性质
        }

        // 📝 Step 3: 更新前一个节点的值
        pre = root.val;

        // 🌿 Step 4: 递归检查右子树
        return isValidBST(root.right);
    }

    /**
     * 🔍 方法二：递归边界法验证二叉搜索树
     * <p>通过维护每个节点的合法取值范围来判断</p>
     *
     * @param root 二叉树根节点
     * @return 是否为有效的二叉搜索树
     */
    public boolean isValidBSTWithBounds(TreeNode root) {
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * 🛠️ 辅助方法：递归检查节点是否在指定范围内
     *
     * @param root     当前节点
     * @param minValue 最小值边界（不包含）
     * @param maxValue 最大值边界（不包含）
     * @return 是否为有效的二叉搜索树
     */
    private boolean isValidBSTHelper(TreeNode root, long minValue, long maxValue) {
        // 🛑 边界情况：空节点是有效的 BST
        if (root == null) return true;

        long val = root.val;

        // 🔍 检查当前节点值是否在合法范围内，并递归检查左右子树
        // 🌿 左子树的上界是当前节点值，右子树的下界是当前节点值
        return val > minValue && val < maxValue &&
                isValidBSTHelper(root.left, minValue, val) &&
                isValidBSTHelper(root.right, val, maxValue);
    }

}

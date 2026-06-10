package Hot100.二叉树;

/**
 * @author lxd
 **/
public class Hot042 {
    /**
     * <a href="https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/description/">108. 将有序数组转换为二叉搜索树</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个整数数组 nums，其中元素已按升序排列。请将其转换为一棵高度平衡的二叉搜索树。</p>
     * <p>高度平衡二叉树是指一个二叉树每个节点的左右两个子树的高度差的绝对值不超过 1。</p>
     *
     * <h3>💡 核心思路：递归构建 + 选择中间元素为根</h3>
     * <ul>
     *   <li><b>基本思想</b>：利用有序数组的特点，递归构建平衡 BST
     *     <ul>
     *       <li>选择中间元素作为根节点</li>
     *       <li>中间元素左侧构建左子树</li>
     *       <li>中间元素右侧构建右子树</li>
     *       <li>递归处理左右两部分</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么选择中间元素</b>：
     *     <ul>
     *       <li>保证左右子树节点数相近</li>
     *       <li>自然形成高度平衡的树</li>
     *       <li>符合 BST 的性质（有序）</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个元素处理一次</li>
     *   <li><b>空间复杂度</b>：O(log n)，递归栈空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [-10, -3, 0, 5, 9]
     *
     * 【递归构建过程】
     *
     * helper(0, 4)  // 处理 [0, 4] 区间
     *   ├─ mid = (0 + 4) / 2 = 2
     *   ├─ nums[2] = 0，作为根节点
     *   ├─ 左子树：helper(0, 1)      // [-10, -3]
     *   │   ├─ mid = (0 + 1) / 2 = 0
     *   │   ├─ nums[0] = -10，作为左子节点
     *   │   ├─ 左子树：helper(0, -1) → null
     *   │   └─ 右子树：helper(1, 1)  // [-3]
     *   │       ├─ mid = (1 + 1) / 2 = 1
     *   │       ├─ nums[1] = -3，作为右子节点
     *   │       ├─ 左子树：helper(1, 0) → null
     *   │       └─ 右子树：helper(2, 1) → null
     *   │       返回节点 -3
     *   │   返回节点 -10
     *   │
     *   └─ 右子树：helper(3, 4)      // [5, 9]
     *       ├─ mid = (3 + 4) / 2 = 3
     *       ├─ nums[3] = 5，作为左子节点
     *       ├─ 左子树：helper(3, 2) → null
     *       └─ 右子树：helper(4, 4)  // [9]
     *           ├─ mid = (4 + 4) / 2 = 4
     *           ├─ nums[4] = 9，作为右子节点
     *           ├─ 左子树：helper(4, 3) → null
     *           └─ 右子树：helper(5, 4) → null
     *           返回节点 9
     *       返回节点 5
     *   返回节点 0
     *
     * 最终结果：
     *           0
     *          / \
     *       -10   5
     *         \    \
     *         -3    9
     *
     * 验证平衡性：
     * - 节点0：左深2，右深2 → 平衡
     * - 节点-10：左深0，右深1 → 平衡
     * - 节点5：左深0，右深1 → 平衡
     * ✅ 所有节点都满足平衡条件
     *
     * ---
     *
     * 【BST 性质验证】
     *
     * 中序遍历结果应为原数组：[-10, -3, 0, 5, 9]
     *
     * 遍历过程：
     *   helper(0, 1) → -10 → helper(1, 1) → -3
     *   0
     *   helper(3, 4) → 5 → helper(4, 4) → 9
     *
     * 结果：[-10, -3, 0, 5, 9] ✅
     *
     * ---
     *
     * 【不同选择的对比】
     *
     * 如果总是选最左边元素：
     *   [-10, -3, 0, 5, 9]
     *   -10
     *     \
     *     -3
     *       \
     *       0
     *         \
     *         5
     *           \
     *           9
     *   深度：6 → 不平衡！
     *
     * 如果总是选中间元素（正确）：
     *   树高：3 → 平衡 ✅
     *
     * 如果总是选最右边元素：
     *   也会形成类似的不平衡树
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要选中间元素作为根？
     *    - 保证左右子树节点数相等或相近
     *    - 自然形成平衡树
     *    - 递归深度为 O(log n)
     *
     * 2️⃣ 递归终止条件是什么？
     *    - if (left > right) return null
     *    - 当 left > right 时，区间为空
     *    - 这种情况发生在叶子节点的某个子区间
     *
     * 3️⃣ 中间位置如何计算？
     *    - mid = (left + right) / 2
     *    - 如果数组长度为偶数，选中间偏左的元素
     *    - 例如 [1, 2, 3, 4]，mid = (0+3)/2 = 1，选 nums[1] = 2
     *
     * 4️⃣ 左闭右闭区间的特点？
     *    - left 指向当前区间的第一个元素
     *    - right 指向当前区间的最后一个元素
     *    - helper(left, right) 处理 nums[left..right]
     *    - 递归时左子树用 left..mid-1
     *    - 递归时右子树用 mid+1..right
     *
     * 5️⃣ 为什么 BST 性质天然满足？
     *    - 选择中间元素作为根
     *    - 左子树的元素都小于根（数组左侧）
     *    - 右子树的元素都大于根（数组右侧）
     *    - BST 的中序遍历就是有序数组
     *
     * 6️⃣ 平衡性如何保证？
     *    - 每个节点都选择当前区间的中间元素
     *    - 左右子树的节点数差不超过 1
     *    - 因此树的高度差不超过 1
     *    - 整体树高度为 O(log n)
     *
     * 7️⃣ 为什么不需要额外调整？
     *    - 选择中间元素是构建平衡 BST 的最优策略
     *    - 递归保证子树也是平衡的
     *    - 不需要像其他算法那样旋转调整
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    └─ 调用 helper(nums, 0, nums.length - 1)
     *
     *    递归函数 helper(nums, left, right)：
     *    ├─ 基本情况：left > right → return null
     *    ├─ 计算中点：mid = (left + right) / 2
     *    ├─ 创建根节点：root = new TreeNode(nums[mid])
     *    ├─ 递归构建左子树：root.left = helper(nums, left, mid - 1)
     *    ├─ 递归构建右子树：root.right = helper(nums, mid + 1, right)
     *    └─ 返回根节点
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>递归终止</b>：left > right 时返回 null，不是 left >= right</li>
     *   <li><b>中间选择</b>：必须选择中间元素，不能选最左或最右</li>
     *   <li><b>区间边界</b>：左子树用 left..mid-1，右子树用 mid+1..right</li>
     *   <li><b>BST 性质</b>：中序遍历结果必须是有序数组</li>
     *   <li><b>平衡性</b>：高度差不超过 1，递归深度为 O(log n)</li>
     * </ul>
     *
     * @param nums 有序整数数组
     * @return 平衡二叉搜索树的根节点
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        // 🔄 调用递归函数，从整个数组范围构建
        return helper(nums, 0, nums.length - 1);
    }

    /**
     * 递归构建平衡 BST
     *
     * @param nums  有序数组
     * @param left  当前区间的左边界
     * @param right 当前区间的右边界
     * @return 当前区间构建的子树根节点
     */
    public TreeNode helper(int[] nums, int left, int right) {
        // 🎯 基本情况：区间为空，无节点可构建
        if (left > right) {
            return null;
        }

        // 🎯 选择中间元素作为根节点，保证平衡
        // (left + right) / 2 会自动选择中间偏左的元素
        int mid = (left + right) / 2;

        // 🏗️ 创建根节点，值为中间元素
        TreeNode root = new TreeNode(nums[mid]);

        // 🔄 递归构建左子树：处理 left 到 mid-1 区间的元素
        root.left = helper(nums, left, mid - 1);

        // 🔄 递归构建右子树：处理 mid+1 到 right 区间的元素
        root.right = helper(nums, mid + 1, right);

        // ✅ 返回构建好的子树根节点
        return root;
    }
}

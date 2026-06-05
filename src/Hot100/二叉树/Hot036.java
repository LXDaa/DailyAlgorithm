package Hot100.二叉树;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot036 {
    /**
     * <a href="https://leetcode.cn/problems/binary-tree-inorder-traversal/description/">94. 二叉树的中序遍历</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个二叉树的根节点 root ，返回它的 中序 遍历。</p>
     *
     * <h3>💡 核心思路：递归遍历</h3>
     * <ul>
     *   <li><b>基本思想</b>：中序遍历的顺序是"左-根-右"
     *     <ul>
     *       <li>第一步：递归遍历左子树</li>
     *       <li>第二步：访问根节点（将值加入结果列表）</li>
     *       <li>第三步：递归遍历右子树</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用递归</b>：
     *     <ul>
     *       <li>二叉树本身就是递归定义的结构</li>
     *       <li>左子树和右子树都是二叉树，可以用同样的方法遍历</li>
     *       <li>递归代码简洁，逻辑清晰</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个节点访问一次</li>
     *   <li><b>空间复杂度</b>：O(n)，递归栈的空间，最坏情况为链表形状</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例二叉树：
     *         1
     *        / \
     *       2   3
     *      / \
     *     4   5
     *
     * 【递归调用过程】
     *
     * inorderTraversal(1)
     *   ├─ inorder(1, ans)
     *   │   ├─ inorder(2, ans)          // 遍历左子树
     *   │   │   ├─ inorder(4, ans)      // 遍历左子树
     *   │   │   │   ├─ inorder(null)    // 左子树为空，返回
     *   │   │   │   ├─ ans.add(4)       // 访问根节点 4
     *   │   │   │   └─ inorder(null)    // 右子树为空，返回
     *   │   │   ├─ ans.add(2)           // 访问根节点 2
     *   │   │   └─ inorder(5, ans)      // 遍历右子树
     *   │   │       ├─ inorder(null)    // 左子树为空，返回
     *   │   │       ├─ ans.add(5)       // 访问根节点 5
     *   │   │       └─ inorder(null)    // 右子树为空，返回
     *   │   ├─ ans.add(1)               // 访问根节点 1
     *   │   └─ inorder(3, ans)          // 遍历右子树
     *   │       ├─ inorder(null)        // 左子树为空，返回
     *   │       ├─ ans.add(3)           // 访问根节点 3
     *   │       └─ inorder(null)        // 右子树为空，返回
     *   └─ return ans
     *
     * 【遍历顺序】
     *
     * 步骤1：访问节点 4（左子树的最左节点）
     *   ans = [4]
     *
     * 步骤2：访问节点 2（节点4的父节点）
     *   ans = [4, 2]
     *
     * 步骤3：访问节点 5（节点2的右子树）
     *   ans = [4, 2, 5]
     *
     * 步骤4：访问节点 1（根节点）
     *   ans = [4, 2, 5, 1]
     *
     * 步骤5：访问节点 3（根节点的右子树）
     *   ans = [4, 2, 5, 1, 3]
     *
     * 最终结果：[4, 2, 5, 1, 3] ✅
     *
     * ---
     *
     * 递归树结构：
     *
     *              inorder(1)
     *              /        \
     *        inorder(2)   inorder(3)
     *        /      \         |
     *   inorder(4) inorder(5) ans.add(3)
     *       |         |
     *   ans.add(4)  ans.add(5)
     *       |
     *   ans.add(2)
     *       |
     *   ans.add(1)
     *
     * ---
     *
     * 三种遍历方式对比：
     *
     * 前序遍历（根-左-右）：1 → 2 → 4 → 5 → 3
     * 中序遍历（左-根-右）：4 → 2 → 5 → 1 → 3
     * 后序遍历（左-右-根）：4 → 5 → 2 → 3 → 1
     *
     * 记忆技巧：
     * - 前序：根在最前面
     * - 中序：根在中间
     * - 后序：根在最后面
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么中序遍历的结果是有序的（对于BST）？
     *    - 二叉搜索树（BST）的性质：左 < 根 < 右
     *    - 中序遍历顺序：左 → 根 → 右
     *    - 因此遍历结果正好是升序序列
     *    - 这是 BST 的重要性质，常用于验证 BST
     *
     * 2️⃣ 递归的终止条件是什么？
     *    - root == null：空节点，无需处理
     *    - 直接返回，不添加任何值
     *    - 这是递归的基本情况
     *
     * 3️⃣ 为什么要将 ans 作为参数传递？
     *    - 避免在每次递归中创建新的列表
     *    - 所有递归调用共享同一个结果列表
     *    - 减少内存分配和垃圾回收
     *
     * 4️⃣ 递归调用的顺序为什么重要？
     *    - 必须严格按照"左-根-右"的顺序
     *    - 顺序错误会导致结果错误
     *    - 例如：先访问根节点就是前序遍历了
     *
     * 5️⃣ 与迭代法对比？
     *    - 迭代法：使用栈模拟递归过程
     *    - 时间复杂度：都是 O(n)
     *    - 空间复杂度：都是 O(n)
     *    - 递归法：代码简洁，但可能有栈溢出风险
     *    - 迭代法：更安全，但代码稍复杂
     *
     * 6️⃣ 如何避免递归栈溢出？
     *    - 对于非常深的树，递归可能导致栈溢出
     *    - 可以使用迭代法（显式栈）代替
     *    - 或者使用 Morris 遍历（空间 O(1)）
     *
     * 7️⃣ 中序遍历的应用场景？
     *    - BST 的验证：检查遍历结果是否有序
     *    - BST 的第K小元素：遍历到第K个即可
     *    - 二叉树转链表：修改指针指向
     *    - 表达式树求值：中缀表达式
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ 创建结果列表 ans
     *    ├─ 调用 inorder(root, ans)
     *    └─ 返回 ans
     *
     *    递归函数 inorder(node, ans)：
     *    ├─ 基本情况：node == null → 返回
     *    ├─ 递归左子树：inorder(node.left, ans)
     *    ├─ 访问根节点：ans.add(node.val)
     *    └─ 递归右子树：inorder(node.right, ans)
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>遍历顺序</b>：必须严格按照"左-根-右"的顺序</li>
     *   <li><b>空节点处理</b>：遇到 null 节点直接返回，不添加值</li>
     *   <li><b>结果列表</b>：作为参数传递，避免重复创建</li>
     *   <li><b>栈深度</b>：对于极端情况（链表形状），栈深度为 n</li>
     *   <li><b>BST 性质</b>：中序遍历 BST 得到有序序列</li>
     * </ul>
     *
     * @param root 二叉树的根节点
     * @return 中序遍历的结果列表
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        // 📦 创建结果列表
        List<Integer> ans = new ArrayList<>();
        // 🔄 调用递归函数进行中序遍历
        inorder(root, ans);
        // ✅ 返回遍历结果
        return ans;
    }

    /**
     * 递归中序遍历辅助函数
     *
     * @param root 当前节点
     * @param ans  结果列表
     */
    private void inorder(TreeNode root, List<Integer> ans) {
        // 🎯 基本情况：空节点，直接返回
        if (root == null) {
            return;
        }
        // ⬅️ 递归遍历左子树
        inorder(root.left, ans);
        // 📍 访问根节点，将值加入结果列表
        ans.add(root.val);
        // ➡️ 递归遍历右子树
        inorder(root.right, ans);
    }
}

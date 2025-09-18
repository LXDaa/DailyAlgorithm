package Tree;


public class Day20250918 {
    /**
     * <a href="https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/description/">108.将有序数组转换为二叉搜索树</a>
     * <p>
     * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 平衡 二叉搜索树。
     * <p>
     * 解题思路：
     * <p>
     * 1. 利用二分法思想，每次选择数组中间元素作为根节点，保证左右子树节点数相差不超过1
     * <p>
     * 2. 中间元素将数组分为左右两部分，分别递归构建左右子树
     * <p>
     * 3. 递归终止条件是左边界大于右边界
     * <p>
     * 4. 这样构建出的树自然满足BST性质：左子树所有节点 < 根节点 < 右子树所有节点
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        // 调用递归函数，将整个数组转换为平衡二叉搜索树
        return dfs(nums, 0, nums.length - 1);
    }

    /**
     * 递归构建平衡二叉搜索树
     *
     * @param nums  有序数组
     * @param left  左边界
     * @param right 右边界
     * @return 构建的子树根节点
     */
    private TreeNode dfs(int[] nums, int left, int right) {
        // 递归终止条件：左边界大于右边界时返回null
        if (left > right) {
            return null;
        }
        // 取中间位置作为根节点，避免整数溢出使用位运算
        int mid = left + ((right - left) >> 1);
        // 创建根节点
        TreeNode root = new TreeNode(nums[mid]);
        // 递归构建左子树，范围是[left, mid-1]
        root.left = dfs(nums, left, mid - 1);
        // 递归构建右子树，范围是[mid+1, right]
        root.right = dfs(nums, mid + 1, right);
        // 返回根节点
        return root;
    }

    /**
     * <a href="https://leetcode.cn/problems/convert-bst-to-greater-tree/description/">538.把二叉搜索树转换为累加树</a>
     * <p>
     * 给定一个二叉搜索树（Binary Search Tree），将树转换为累加树（Greater Tree)，使得每个节点的值为原来的节点值加上所有大于它的节点值之和。
     * <p>
     * 解题思路：
     * <p>
     * 1. 利用BST的性质：中序遍历得到升序序列
     * <p>
     * 2. 反向中序遍历（右->根->左）得到降序序列
     * <p>
     * 3. 在反向遍历过程中维护一个累加和pre，记录所有已访问节点（值更大的节点）的和
     * <p>
     * 4. 当前节点的新值 = 原值 + pre（所有大于当前节点值的节点和）
     * <p>
     * 5. 更新pre为当前节点的新值，继续遍历左子树
     */

    static class Solution {
        // 记录前一个节点的累加值
        int pre = 0;

        /**
         * 将二叉搜索树转换为累加树
         * 利用BST的性质：中序遍历得到升序序列，反过来遍历就是降序序列
         *
         * @param root BST的根节点
         * @return 转换后的累加树根节点
         */
        public TreeNode convertBST(TreeNode root) {
            if (root != null) {
                // 先遍历右子树，获得所有大于当前节点的值
                convertBST(root.right);
                // 更新当前节点值：加上前一个节点的值（所有更大节点的累加和）
                pre += root.val;
                root.val = pre;
                // 再遍历左子树
                convertBST(root.left);
            }
            return root;
        }
    }

}
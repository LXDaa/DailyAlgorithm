package CodeMusings.Tree;

/**
 * <a href="https://leetcode.cn/problems/maximum-binary-tree/description/">654. 最大二叉树</a>
 * <p>
 * 给定一个不重复的整数数组 nums 。 最大二叉树 可以用下面的算法从 nums 递归地构建:
 * <p>
 * 创建一个根节点，其值为 nums 中的最大值。
 * <p>
 * 递归地在最大值 左边 的 子数组前缀上 构建左子树。
 * <p>
 * 递归地在最大值 右边 的 子数组后缀上 构建右子树。
 * <p>
 * 返回 nums 构建的 最大二叉树 。
 * @author LXDa
 */
public class Day20250903 {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return construct(nums, 0, nums.length - 1);
    }

    private TreeNode construct(int[] nums, int left, int right) {

        if (left > right) {
            return null;
        }
        int maxIndex = left;
        for (int i = left + 1; i <= right; i++) {
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }
        TreeNode node = new TreeNode(nums[maxIndex]);
        node.left = construct(nums, left, maxIndex - 1);
        node.right = construct(nums, maxIndex + 1, right);
        return node;
    }


    public TreeNode constructMaximumBinaryTree2(int[] nums) {
        // 开区间：right = nums.length （不包含）
        return construct2(nums, 0, nums.length);
    }


    /**
     * [ 左闭右开 )
     */
    public TreeNode construct2(int[] nums, int left, int right) {
        // 开区间终止条件：left >= right
        if (left >= right) {
            return null;
        }
        int maxIndex = left;
        // 开区间遍历：i < right
        for (int i = left + 1; i < right; i++) {
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }
        TreeNode node = new TreeNode(nums[maxIndex]);
        // 左子树：left到maxIndex（不包含maxIndex）
        node.left = construct(nums, left, maxIndex);
        // 右子树：maxIndex+1到right
        node.right = construct(nums, maxIndex + 1, right);
        return node;
    }
}

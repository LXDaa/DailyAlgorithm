package CodeMusings.Tree;

/**
 *
 * <a href="https://leetcode.cn/problems/insert-into-a-binary-search-tree/description/">701.二叉搜索树中的插入操作</a>
 * <p>
 * 给定二叉搜索树（BST）的根节点 root 和要插入树中的值 value ，将值插入二叉搜索树。 返回插入后二叉搜索树的根节点。 输入数据 保证 ，新值和原始二叉搜索树中的任意节点值都不同。
 * <p>
 * 注意，可能存在多种有效的插入方式，只要树在插入后仍保持为二叉搜索树即可。 你可以返回 任意有效的结果 。
 * <p>
 */
public class Day20250915 {
    /**
     * 向二叉搜索树中插入新节点
     * @param root 二叉搜索树的根节点
     * @param val 要插入的值
     * @return 插入新节点后的二叉搜索树根节点
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // 如果当前节点为空，说明找到了插入位置，创建新节点并返回
        if(root==null){
            return new TreeNode(val);
        }
        // 如果当前节点值大于插入值，说明应该插入到左子树中
        if(root.val>val){
            root.left=insertIntoBST(root.left,val);
        // 如果当前节点值小于插入值，说明应该插入到右子树中
        }else if(root.val<val){
            root.right=insertIntoBST(root.right,val);
        }
        // 返回当前节点（递归处理后的结果）
        return root;
    }
}
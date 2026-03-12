package CodeTop;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/binary-tree-right-side-view/description/">199.二叉树的右视图</a>
 * <p>
 * 给定一个二叉树的 根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
 *
 * <p><b>解题思路：</b>
 * <ol>
 *   <li><b>核心思想：</b>层序遍历（BFS），每层只取最右边的节点值
 *   <li><b>方法选择：</b>
 *       <ul>
 *         <li>使用队列进行层序遍历
 *         <li>每层遍历时，记录该层的最后一个节点值
 *         <li>将每层的最后一个节点值加入结果集
 *       </ul>
 *   <li><b>算法步骤：</b>
 *       <ul>
 *         <li>初始化队列，将根节点入队
 *         <li>当队列不为空时，获取当前层的节点数量 size
 *         <li>遍历当前层的所有节点：
 *             <ul>
 *               <li>出队一个节点
 *               <li>如果是该层的最后一个节点（size == 0），将其值加入结果集
 *               <li>将该节点的左右子节点（如果存在）依次入队
 *             </ul>
 *         <li>重复上述过程直到队列为空
 *       </ul>
 *   <li><b>关键点：</b>
 *       <ul>
 *         <li>使用 size 变量控制每层的遍历次数
 *         <li>当 size == 0 时，说明是当前层的最右节点
 *         <li>先入队左子节点，再入队右子节点，保证每层从左到右遍历
 *       </ul>
 * </ol>
 *
 * <p><b>时间复杂度：</b>O(n) - 需要遍历所有节点<br>
 * <b>空间复杂度：</b>O(w) - w 为二叉树的最大宽度，队列最多存储一层的节点
 *
 * @author lxd
 **/
public class Day20260312 {
    public List<Integer> rightSideView(TreeNode root) {
        // 存储结果的列表
        List<Integer> res = new ArrayList<>();

        // 处理空树的情况
        if (root == null) {
            return res;
        }

        // 使用双端队列作为层序遍历的队列
        Deque<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        // 开始层序遍历
        while (!queue.isEmpty()) {
            // 获取当前层的节点数量
            int size = queue.size();

            // 遍历当前层的所有节点
            while (size > 0) {
                // 出队一个节点
                TreeNode cur = queue.poll();
                size--;

                // 如果是当前层的最后一个节点，将其值加入结果集
                if (size == 0) {
                    res.add(cur.val);
                }

                // 将左子节点入队
                if (cur.left != null) {
                    queue.offer(cur.left);
                }

                // 将右子节点入队
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
        }
        return res;
    }
}

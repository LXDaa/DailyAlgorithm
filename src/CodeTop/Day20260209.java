package CodeTop;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/binary-tree-zigzag-level-order-traversal/">103. 二叉树的锯齿形层序遍历</a>
 *
 * <p>解题思路：</p>
 * <ol>
 * <li>使用BFS层序遍历的思想，但需要根据层数奇偶性改变添加节点值的顺序</li>
 * <li>偶数层(0,2,4...)：从左到右遍历，节点值添加到双端队列末尾</li>
 * <li>奇数层(1,3,5...)：从右到左遍历，节点值添加到双端队列开头</li>
 * <li>每层处理完后，将该层结果加入最终结果集</li>
 * </ol>
 *
 * <p>算法复杂度：</p>
 * <ul>
 * <li>时间复杂度：O(n)，其中n是二叉树的节点数，每个节点访问一次</li>
 * <li>空间复杂度：O(n)，队列最多存储一层的所有节点</li>
 * </ul>
 *
 * <p>执行示例：</p>
 * <pre>
 * 输入：root = [3,9,20,null,null,15,7]
 *       3
 *      / \
 *     9  20
 *       /  \
 *      15   7
 * 输出：[[3],[20,9],[15,7]]
 *
 * 执行过程：
 * 第0层(偶数)：3 → [3]
 * 第1层(奇数)：9,20 → [20,9]
 * 第2层(偶数)：15,7 → [15,7]
 * </pre>
 *
 * @author lxd
 **/
public class Day20260209 {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        // 结果集合，存储每层的节点值
        List<List<Integer>> res = new ArrayList<>();
        // BFS队列，用于层序遍历
        Deque<TreeNode> queue = new LinkedList<>();

        // 边界条件：空树直接返回空结果
        if (root == null) {
            return res;
        }

        // 初始化队列，加入根节点
        queue.addLast(root);
        // 记录当前层数，用于判断遍历方向
        int floor = 0;

        // BFS主循环
        while (!queue.isEmpty()) {
            // 当前层的节点数量
            int n = queue.size();
            // 双端队列存储当前层的节点值，支持头插和尾插
            Deque<Integer> path = new LinkedList<>();

            // 处理当前层的所有节点
            for (int i = 0; i < n; i++) {
                // 取出队首节点
                TreeNode node = queue.poll();

                // 将子节点加入队列，为下一层做准备
                if (node.left != null) {
                    queue.addLast(node.left);
                }
                if (node.right != null) {
                    queue.addLast(node.right);
                }

                // 根据层数奇偶性决定节点值插入位置
                // 奇数层：从右到左，新值插入队列头部
                // 偶数层：从左到右，新值插入队列尾部
                if (floor % 2 == 1) {
                    path.addFirst(node.val);
                } else {
                    path.addLast(node.val);
                }
            }

            // 将当前层结果转换为ArrayList并加入最终结果
            res.add(new ArrayList<>(path));
            // 层数递增，切换遍历方向
            floor++;
        }

        return res;
    }
}

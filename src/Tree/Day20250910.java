package Tree;

import java.util.*;

/**
 * <a href="https://leetcode.cn/problems/find-mode-in-binary-search-tree/">501.二叉搜索树中的众数</a>
 * <p>
 * 给你一个含重复值的二叉搜索树（BST）的根节点 root ，找出并返回 BST 中的所有 众数（即，出现频率最高的元素）。
 * <p>
 * 如果树中有不止一个众数，可以按 任意顺序 返回。
 * <p>
 * 假定 BST 满足如下定义：
 * <p>
 * 结点左子树中所含节点的值 小于等于 当前节点的值
 * <p>
 * 结点右子树中所含节点的值 大于等于 当前节点的值
 * <p>
 * 左子树和右子树都是二叉搜索树
 */
public class Day20250910 {
    static class Recursion {
        List<Integer> resList = new ArrayList<>();
        int pre, count, maxCount;

        public int[] findMode(TreeNode root) {
            dfs(root);
            int[] res = new int[resList.size()];
            for (int i = 0; i < resList.size(); i++) {
                res[i] = resList.get(i);
            }
            return res;
        }

        private void dfs(TreeNode root) {
            if (root == null) {
                return;
            }
            dfs(root.left);
            update(root.val);
            dfs(root.right);
        }

        private void update(int val) {
            if (val == pre) {
                count++;
            } else {
                count = 1;
            }
            if (count == maxCount) {
                resList.add(val);
            } else if (count > maxCount) {
                maxCount = count;
                resList.clear();
                resList.add(val);
            }
            pre = val;
        }
    }

    static class Iterate {
        int count, maxCount, pre;
        List<Integer> resList = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        public int[] findMode(TreeNode root) {
            TreeNode cur = root;
            while (cur != null || !stack.isEmpty()) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    cur = stack.pop();
                    update(cur.val);
                    cur = cur.right;
                }
            }
            return resList.stream().mapToInt(Integer::intValue).toArray();
        }

        private void update(int val) {
            if (val == pre) {
                count++;
            } else {
                count = 1;
            }
            if (count == maxCount) {
                resList.add(val);
            } else if (count > maxCount) {
                maxCount = count;
                resList.clear();
                resList.add(val);
            }
            pre = val;
        }
    }
}

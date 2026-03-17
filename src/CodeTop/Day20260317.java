package CodeTop;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/generate-parentheses/description/">22. 括号生成</a>
 * <p>
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 * 示例 1：
 * <p>
 * 输入：n = 3
 * 输出：["((()))","(()())","(())()","()(())","()()()"]
 * 示例 2：
 * <p>
 * 输入：n = 1
 * 输出：["()"]
 *
 * <p><strong>题目分析：</strong></p>
 * <p>需要生成所有由 n 对括号组成的有效组合，总共需要生成 2n 个字符（n 个左括号和 n 个右括号）</p>
 *
 * <p><strong>解题思路：</strong></p>
 * <ol>
 *     <li><strong>回溯算法（DFS）：</strong>通过递归枚举所有可能的括号组合</li>
 *     <li><strong>有效括号的判断条件：</strong>
 *         <ol>
 *             <li>最终左右括号数量都必须等于 n</li>
 *             <li>在构造过程中，右括号的数量不能超过左括号的数量（否则会出现无效的")("情况）</li>
 *         </ol>
 *     </li>
 *     <li><strong>剪枝策略：</strong>
 *         <ol>
 *             <li>只有当左括号数量小于 n 时，才能继续添加左括号</li>
 *             <li>只有当右括号数量小于左括号数量时，才能添加右括号</li>
 *         </ol>
 *     </li>
 *     <li><strong>回溯过程：</strong>每次尝试添加一个括号，然后撤销选择，尝试另一种可能</li>
 * </ol>
 *
 * <p><strong>复杂度分析：</strong></p>
 * <ul>
 *     <li>时间复杂度：O(4^n / √n)，第 n 个卡塔兰数的渐近复杂度</li>
 *     <li>空间复杂度：O(n)，递归调用栈的深度为 2n，StringBuilder 的空间为 O(n)</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260317 {
    /**
     * 生成所有有效的括号组合
     *
     * <p><strong>实现步骤：</strong></p>
     * <ol>
     *     <li>初始化结果列表</li>
     *     <li>使用 StringBuilder 构建括号字符串（比 String 拼接更高效）</li>
     *     <li>调用回溯函数，从空字符串开始生成所有有效组合</li>
     *     <li>返回结果列表</li>
     * </ol>
     *
     * @param n 括号的对数
     * @return 所有有效的括号组合列表
     */
    public List<String> generateParenthesis(int n) {
        // 初始化结果列表
        List<String> ans = new ArrayList<>();

        // 使用 StringBuilder 构建括号字符串
        // 参数说明：n=总对数，left=已用左括号数，right=已用右括号数
        backtrack(n, 0, 0, new StringBuilder(), ans);

        return ans;
    }

    /**
     * 回溯函数，生成所有有效的括号组合
     *
     * <p><strong>参数说明：</strong></p>
     * <ul>
     *     <li>n：需要生成的括号对数（目标值）</li>
     *     <li>left：当前已经使用的左括号数量</li>
     *     <li>right：当前已经使用的右括号数量</li>
     *     <li>s：正在构建的括号字符串（使用 StringBuilder 便于回溯）</li>
     *     <li>ans：存储所有有效组合的结果列表</li>
     * </ul>
     *
     * <p><strong>回溯逻辑：</strong></p>
     * <ol>
     *     <li><strong>终止条件：</strong>当左右括号都用完（各 n 个）时，将当前组合加入结果集</li>
     *     <li><strong>选择左括号：</strong>如果左括号还没用完（left < n），可以添加左括号</li>
     *     <li><strong>选择右括号：</strong>如果右括号数量少于左括号（left > right），可以添加右括号</li>
     *     <li><strong>回溯：</strong>每次递归后都要撤销选择（删除最后一个字符），尝试其他可能</li>
     * </ol>
     *
     * @param n     括号的对数（目标值）
     * @param left  当前已使用的左括号数量
     * @param right 当前已使用的右括号数量
     * @param s     正在构建的括号字符串
     * @param ans   结果列表
     */
    private void backtrack(int n, int left, int right, StringBuilder s, List<String> ans) {
        // 终止条件：左右括号都已使用完毕（各 n 个）
        if (left == n && right == n) {
            // 找到一个有效的括号组合，添加到结果列表中
            ans.add(s.toString());
            return;
        }

        // 选择 1：添加左括号
        // 条件：左括号数量还未达到 n
        if (left < n) {
            // 做出选择：添加左括号
            s.append("(");

            // 进入下一层决策树
            backtrack(n, left + 1, right, s, ans);

            // 撤销选择：删除最后一个字符，回溯到之前的状态
            s.deleteCharAt(s.length() - 1);
        }

        // 选择 2：添加右括号
        // 条件：右括号数量少于左括号数量（保证有效性）
        // 如果右括号数量 >= 左括号数量，再添加右括号会导致无效组合（如 "())" 或 ")("）
        if (left > right) {
            // 做出选择：添加右括号
            s.append(")");

            // 进入下一层决策树
            backtrack(n, left, right + 1, s, ans);

            // 撤销选择：删除最后一个字符，回溯到之前的状态
            s.deleteCharAt(s.length() - 1);
        }
    }
}

package CodeMusings.BackTracking;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <a href="https://leetcode.cn/problems/palindrome-partitioning/description/">131.分割回文串</a>
 * <p>
 * 给你一个字符串 s，请你将 s 分割成一些 子串，使每个子串都是 回文串 。返回 s 所有可能的分割方案。
 * <p>
 * 解题思路：
 * 1. 使用回溯算法遍历所有可能的分割方式
 * 2. 在每一步中，尝试从当前起始位置分割出不同长度的子串
 * 3. 只有当分割出的子串是回文串时，才继续向下递归
 * 4. 当起始位置到达字符串末尾时，说明找到了一种有效的分割方案
 * <p>
 * 示例分析："aab" 的所有回文分割方案
 * - 分割过程可以看作一棵树，每一层代表一次分割
 * - 根节点为空字符串
 * - 第一层可以分割出 "a", "aa", "aab"，只有 "a", "aa" 是回文
 * - 对于 "a"，剩余 "ab" 继续分割，可得 "a" 
 * - 对于 "aa"，剩余 "b" 继续分割，可得 "b"
 * - 最终得到 [["a","a","b"],["aa","b"]]
 * <p>
 * 时间复杂度：O(N * 2^N)，其中 N 是字符串长度
 * - 2^N 是因为对于每个字符，我们都有分割和不分割两种选择（实际上是N个分割点）
 * - N 是因为每次检查回文需要 O(N) 时间
 * 空间复杂度：O(N)，递归栈空间和临时存储路径的空间
 */
public class Day20250928 {
    // 结果集合，存储所有有效的分割方案
    // 每个元素是一个List<代码随想录.String>，代表一种分割方案
    List<List<String>> res = new ArrayList<>();
    // 当前路径，存储当前分割方案中的回文子串
    // 在回溯过程中动态维护，记录从根到当前节点的路径
    List<String> path = new ArrayList<>();

    /**
     * 主函数：分割回文串
     * @param s 输入字符串，需要被分割成回文子串
     * @return 所有可能的分割方案，每个方案中的子串都是回文串
     * 
     * 示例：
     * 输入: "aab"
     * 输出: [["a","a","b"],["aa","b"]]
     */
    public List<List<String>> partition(String s) {
        // 调用回溯函数，从索引0开始分割
        backTracing(s, 0);
        return res;
    }

    /**
     * 回溯函数：递归寻找所有可能的回文分割方案
     * 
     * 回溯三要素：
     * 1. 参数：s(原字符串), startIndex(本次分割起始位置)
     * 2. 终止条件：startIndex >= s.length()
     * 3. 单层逻辑：在[startIndex, s.length()-1]范围内尝试各种分割点
     * 
     * @param s 输入字符串
     * @param startIndex 当前分割的起始位置（在字符串中的索引）
     * 
     * 执行流程示例（以"aab"为例）：
     * 1. startIndex=0，尝试分割点i=0,1,2
     *    - i=0: 子串"a"是回文，path=["a"]，递归处理startIndex=1
     *      - startIndex=1，尝试分割点i=1,2
     *        - i=1: 子串"a"是回文，path=["a","a"]，递归处理startIndex=2
     *          - startIndex=2，尝试分割点i=2
     *            - i=2: 子串"b"是回文，path=["a","a","b"]，递归处理startIndex=3
     *              - startIndex=3，达到终止条件，将path加入res
     *              - 回溯，移除"b"，path=["a","a"]
     *            - i=2循环结束，回溯移除"a"，path=["a"]
     *        - i=2: 子串"ab"非回文，跳过
     *        - i=1,2循环结束，回溯移除"a"，path=[]
     *    - i=1: 子串"aa"是回文，path=["aa"]，递归处理startIndex=2
     *      - 后续同上，最终得到["aa","b"]
     *    - i=2: 子串"aab"非回文，跳过
     */
    private void backTracing(String s, int startIndex) {
        // 递归终止条件：起始位置已经到达字符串末尾
        // 说明已经找到一种有效的分割方案
        // 此时path中存储的就是一种完整的分割方案
        if (startIndex == s.length()) {
            // 注意：不能直接add(path)，因为path是引用，
            // 后续操作会修改它，需要创建新的ArrayList保存当前状态
            res.add(new ArrayList(path)); // 将当前路径加入结果集
            return;
        }
        
        // 从startIndex开始，尝试不同长度的分割
        // i表示分割点，子串范围是[startIndex, i]（包含两端）
        for (int i = startIndex; i < s.length(); i++) {
            // 截取子串 [startIndex, i+1)  startIndex是当前分割的起始位置  i是当前分割的结束位置  i + 1作为endIndex参数是为了包含索引i位置的字符
            // substring方法是左闭右开区间，所以是[startIndex, i+1)
            String sub = s.substring(startIndex, i + 1);
            
            // 判断截取的子串是否为回文串
            // 只有是回文串才会继续递归，否则剪枝
            if (check(sub)) {
                // 如果是回文串，则加入当前路径
                path.add(sub);
                // 递归处理剩余部分 [i+1, s.length())
                // 下一层的起始位置是i+1
                backTracing(s, i + 1);
                // 回溯，移除刚才加入的子串，尝试其他分割方式
                // 这是回溯的核心，恢复现场，以便尝试其他可能性
                path.remove(path.size() - 1);
            }
            // 如果sub不是回文串，则不进行递归，直接进入下一次循环
            // 相当于剪枝操作，提前结束无效分支
        }
    }

    /**
     * 判断字符串是否为回文串
     * 
     * 使用双指针法判断回文：
     * - left指针从左边开始，right指针从右边开始
     * - 两个指针向中间移动，比较对应位置字符
     * - 如果所有对应位置字符都相等，则是回文串
     * 
     * @param s 待判断的字符串
     * @return true表示是回文串，false表示不是回文串
     * 
     * 示例：
     * check("a") -> true
     * check("aa") -> true
     * check("ab") -> false
     * check("aba") -> true
     */
    private boolean check(String s) {
        // 使用双指针法判断回文
        // 循环只需要执行到字符串长度的一半即可
        for (int i = 0; i < s.length() / 2; i++) {
            // 比较对称位置的字符是否相等
            // i位置与length()-1-i位置对称
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
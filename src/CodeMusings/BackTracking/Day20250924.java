package CodeMusings.BackTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/letter-combinations-of-a-phone-number/description/">17.电话号码的字母组合</a>
 * 
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 * 
 * 解题思路：
 * 这是一个典型的回溯算法问题。每个数字对应多个字母，我们需要找出所有可能的字母组合。
 * 1. 建立数字到字母的映射关系（类似电话键盘）
 * 2. 使用回溯算法遍历每个数字对应的所有字母
 * 3. 对于每个数字，依次选择一个字母加入路径，然后递归处理下一个数字
 * 4. 当处理完所有数字后，将当前路径加入结果集
 * 5. 回溯时需要撤销之前的选择，以便尝试其他可能性
 * 
 * 时间复杂度：O(3^m × 4^n)，其中 m 是对应3个字母的数字个数，n 是对应4个字母的数字个数
 * 空间复杂度：O(3^m × 4^n)，用于存储所有可能的组合
 */
public class Day20250924 {
    // 存储最终结果的列表，包含所有可能的字母组合
    List<String> res = new ArrayList<>();
    // 存储当前正在构建的字母组合路径
    StringBuilder path = new StringBuilder();

    /**
     * 主函数：根据输入的数字字符串生成所有可能的字母组合
     * @param digits 输入的数字字符串（只包含2-9）
     * @return 所有可能的字母组合列表
     */
    public List<String> letterCombinations(String digits) {
        // 边界条件处理：如果输入为空或空字符串，直接返回空列表
        if (digits == null || digits.isEmpty()) {
            return res;
        }
        // 建立数字到字母的映射数组，索引对应数字，值对应字母字符串
        // 索引0和1为空字符串，因为题目规定输入只包含2-9
        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        // 调用回溯函数开始生成字母组合
        backTracking(digits, mapping, 0);
        return res;
    }

    /**
     * 回溯函数：递归生成所有可能的字母组合
     * 回溯三要素：
     * 1. 递归函数参数：digits(输入数字字符串), mapping(数字字母映射), index(当前处理的数字索引)
     * 2. 递归终止条件：当index等于digits长度时，说明已处理完所有数字
     * 3. 递归单层逻辑：遍历当前数字对应的所有字母，依次选择并递归处理下一个数字
     * 
     * @param digits 输入的数字字符串
     * @param mapping 数字到字母的映射数组
     * @param index 当前处理的数字位置（递归深度）
     */
    private void backTracking(String digits, String[] mapping, int index) {
        // 递归终止条件：当index等于digits长度时，说明已经处理完所有数字
        // 此时path中存储的就是一个完整的字母组合，将其加入结果集
        if (index == digits.length()) {
            res.add(path.toString());
            return;
        }
        
        // 获取当前数字（字符转数字）对应的字母字符串
        // 通过digits.charAt(index) - '0'将字符转换为整数作为数组索引
        String letters = mapping[digits.charAt(index) - '0'];
        
        // 遍历当前数字对应的所有字母，每个字母都可能是当前位的选择
        for (int i = 0; i < letters.length(); i++) {
            // 做选择：将当前字母加入路径中
            path.append(letters.charAt(i));
            // 递归处理下一个数字（深度+1）
            backTracking(digits, mapping, index + 1);
            // 撤销选择（回溯）：移除刚才加入的字母，尝试下一个可能的字母
            path.deleteCharAt(path.length() - 1);
        }
    }
}
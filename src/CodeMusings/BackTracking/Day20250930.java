package CodeMusings.BackTracking;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <a href="https://leetcode.cn/problems/restore-ip-addresses/description/">93.复原IP地址</a>
 * <p>
 * 有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
 * <p>
 * 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 无效 IP 地址。
 * <p>
 * 给定一个只包含数字的字符串 s ，用以表示一个 IP 地址，返回所有可能的有效 IP 地址，这些地址可以通过在 s 中插入 '.' 来形成。
 * 你 不能 重新排序或删除 s 中的任何数字。你可以按 任何 顺序返回答案。
 * <p>
 * 解题思路：
 * 这是一个典型的回溯算法问题。我们需要在字符串中插入3个点来分割出4个有效的IP段。
 * 回溯的每一层处理一个IP段，总共需要处理4个段。
 * 在每一段中，我们需要判断当前子串是否是有效的IP段：
 * 1. 数值必须在0-255之间
 * 2. 不能有前导0（除了单独的"0"）
 * 3. 只能包含数字字符
 * <p>
 * 算法过程：
 * 1. 使用回溯算法遍历所有可能的分割方式
 * 2. 每一层递归处理一个IP段
 * 3. 当放置了3个点后，检查最后一段是否有效
 * 4. 在每一层中，尝试不同长度的子串作为当前IP段
 */
public class Day20250930 {
    static class Solution {

        // 存储所有有效的IP地址结果
        List<String> res = new ArrayList<>();

        /**
         * 主函数：恢复IP地址
         *
         * @param s 输入的只包含数字的字符串
         * @return 所有可能的有效IP地址列表
         */
        public List<String> restoreIpAddresses(String s) {
            // 使用StringBuilder便于插入和删除点号
            StringBuilder sb = new StringBuilder(s);
            // 从索引0开始回溯，初始点号数量为0
            backTracking(sb, 0, 0);
            return res;
        }

        /**
         * 回溯函数：尝试在字符串中插入点来形成有效的IP地址
         *
         * @param sb       字符串构建器，用于插入和删除点号
         * @param segStart 当前IP段的起始位置
         * @param pointNum 已经插入的点号数量
         */
        private void backTracking(StringBuilder sb, int segStart, int pointNum) {
            // 递归终止条件：已经放置了3个点
            if (pointNum == 3) {
                // 检查最后一段是否是有效的IP段
                if (valid(sb, segStart, sb.length() - 1)) {
                    // 如果有效，将当前字符串加入结果集
                    res.add(sb.toString());
                }
                // 返回上一层递归
                return;
            }

            // 遍历可能的IP段结束位置
            for (int segEnd = segStart; segEnd < sb.length(); segEnd++) {
                // 检查从segStart到segEnd的子串是否是有效的IP段
                if (valid(sb, segStart, segEnd)) {
                    // 如果是有效IP段，在segEnd后插入点号
                    sb.insert(segEnd + 1, ".");
                    // 递归处理下一段，下一段起始位置是segEnd+2（跳过点号），点号数量加1
                    backTracking(sb, segEnd + 2, pointNum + 1);
                    // 回溯：删除刚刚插入的点号
                    sb.deleteCharAt(segEnd + 1);
                } else {
                    // 如果当前子串不是有效的IP段，由于后续更长的子串也不会有效，直接跳出循环
                    break;
                }
            }
        }

        /**
         * 验证函数：检查指定范围内的子串是否是有效的IP段
         *
         * @param sb       字符串构建器
         * @param segStart IP段起始位置
         * @param segEnd   IP段结束位置
         * @return 是否是有效的IP段
         */
        private boolean valid(StringBuilder sb, int segStart, int segEnd) {
            // 起始位置不能大于结束位置
            if (segStart > segEnd) {
                return false;
            }

            // 检查前导0：如果长度大于1且首字符是'0'，则无效
            if (sb.charAt(segStart) == '0' && segStart != segEnd) {
                return false;
            }
            // 用于存储当前段的数值
            int num = 0;
            // 遍历子串中的每个字符
            for (int i = segStart; i <= segEnd; i++) {
                // 检查是否是数字字符
                if (sb.charAt(i) > '9' || sb.charAt(i) < '0') {
                    return false;
                }

                // 计算数值
                num = num * 10 + (sb.charAt(i) - '0');

                // 如果数值超过255，则无效
                if (num > 255) {
                    return false;
                }
            }

            // 所有检查通过，是有效的IP段
            return true;
        }
    }

    static class Solution2 {
        List<String> path = new ArrayList<>();
        List<String> result = new ArrayList<>();
        /**
         * 主函数：恢复IP地址（优化版本）
         *
         * @param s 输入的只包含数字的字符串
         * @return 所有可能的有效IP地址列表
         */
        public List<String> restoreIpAddresses(String s) {
            // IP地址长度必须在4-12位之间（每个段最少1位，最多3位）
            if (s.length() < 4 || s.length() > 12) {
                return result;
            }

            backtrack(s, 0);
            return result;
        }

        /**
         * 回溯函数：尝试构建有效的IP地址
         *
         * @param s      原始字符串
         * @param start  当前处理位置
         */
        private void backtrack(String s, int start) {
            // 递归终止条件：已经找到4个IP段
            if (path.size() == 4) {
                // 如果正好用完整个字符串，则构成有效IP地址
                if (start == s.length()) {
                    result.add(String.join(".", path));
                }
                return;
            }

            // 剪枝：剩余字符数量不符合要求
            // 剩余IP段数量
            int remainingSegments = 4 - path.size();
            // 剩余字符数
            int remainingChars = s.length() - start;
            // 剩余字符数必须在剩余段数和剩余段数*3之间
            if (remainingChars < remainingSegments || remainingChars > remainingSegments * 3) {
                return;
            }

            // 尝试不同长度的IP段（1位到3位）
            for (int len = 1; len <= 3; len++) {
                // 防止越界
                if (start + len > s.length()) {
                    break;
                }

                // 截取当前IP段
                String segment = s.substring(start, start + len);

                // 验证当前段是否有效
                if (isValidSegment(segment)) {
                    // 添加当前段到路径中
                    path.add(segment);
                    // 递归处理下一段
                    backtrack(s, start + len);
                    // 回溯：移除当前段
                    path.remove(path.size() - 1);
                }
            }
        }

        /**
         * 验证IP段是否有效
         *
         * @param segment 待验证的IP段
         * @return 是否有效
         */
        private boolean isValidSegment(String segment) {
            // 检查前导0：如果长度大于1且首字符是'0'，则无效
            if (segment.length() > 1 && segment.charAt(0) == '0') {
                return false;
            }

            // 检查数值是否在0-255范围内
            int value = Integer.parseInt(segment);
            return value >= 0 && value <= 255;
        }
    }
}
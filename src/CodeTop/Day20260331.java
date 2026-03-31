package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/string-to-integer-atoi/description/">8. 字符串转换整数 (atoi)</a>
 * <p>
 * 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数。
 * <p>
 * 函数 myAtoi(string s) 的算法如下：
 * <p>
 * 空格：读入字符串并丢弃无用的前导空格（" "）
 * 符号：检查下一个字符（假设还未到字符末尾）为 '-' 还是 '+'。如果两者都不存在，则假定结果为正。
 * 转换：通过跳过前置零来读取该整数，直到遇到非数字字符或到达字符串的结尾。如果没有读取数字，则结果为 0。
 * 舍入：如果整数数超过 32 位有符号整数范围 [−231,  231 − 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −231 的整数应该被舍入为 −231 ，大于 231 − 1 的整数应该被舍入为 231 − 1 。
 * 返回整数作为最终结果。
 *
 * <h2>解题思路</h2>
 * <h3>1. 题目分析</h3>
 * <ul>
 *     <li><strong>模拟题</strong>：按照题目描述的步骤逐步处理字符串</li>
 *     <li><strong>边界处理</strong>：需要处理各种边界情况（空串、空格、符号、溢出等）</li>
 *     <li><strong>状态机思想</strong>：按顺序经历不同处理阶段</li>
 * </ul>
 * <p>
 * <h3>2. 算法步骤</h3>
 * <h4>(1) 去除前导空格</h4>
 * <ul>
 *     <li>使用指针 i 从左到右扫描，跳过所有空格字符</li>
 *     <li>如果扫描完整个字符串，说明只有空格，返回 0</li>
 * </ul>
 * <p>
 * <h4>(2) 处理符号位</h4>
 * <ul>
 *     <li>检查当前位置是否为 '+' 或 '-'</li>
 *     <li>如果有符号，记录 sign 值（+1 或 -1），移动指针</li>
 *     <li>如果没有符号，默认为正数（sign = 1）</li>
 * </ul>
 * <p>
 * <h4>(3) 转换数字</h4>
 * <ul>
 *     <li>遍历后续的数字字符，转换为整数</li>
 *     <li>遇到非数字字符或字符串末尾时停止</li>
 *     <li>字符转数字：<code>digit = char - '0'</code></li>
 *     <li>累加：<code>num = num * 10 + digit</code></li>
 * </ul>
 * <p>
 * <h4>(4) 溢出处理（关键！）</h4>
 * <ul>
 *     <li>在更新 num 之前，先判断是否会溢出</li>
 *     <li><strong>上溢条件 1</strong>：<code>num > Integer.MAX_VALUE / 10</code></li>
 *     <li><strong>上溢条件 2</strong>：<code>num * 10 > Integer.MAX_VALUE - d</code></li>
 *     <li>如果溢出，根据符号返回 Integer.MAX_VALUE 或 Integer.MIN_VALUE</li>
 * </ul>
 * <p>
 * <h3>3. 算法示意图</h3>
 * <pre>
 * 示例 1：s = "42"
 * 步骤：
 * 1. 跳过空格 → i=0
 * 2. 检查符号 → 无符号，sign=1
 * 3. 读取数字 → num=42
 * 结果：42
 *
 * 示例 2：s = "   -42"
 * 步骤：
 * 1. 跳过空格 → i=3
 * 2. 检查符号 → '-', sign=-1
 * 3. 读取数字 → num=42
 * 结果：-42
 *
 * 示例 3：s = "4193 with words"
 * 步骤：
 * 1. 跳过空格 → i=0
 * 2. 检查符号 → 无符号，sign=1
 * 3. 读取数字 → num=4193
 * 4. 遇到 ' ' 停止
 * 结果：4193
 *
 * 示例 4：s = "-91283472332" （溢出）
 * 步骤：
 * 1. 跳过空格 → i=0
 * 2. 检查符号 → '-', sign=-1
 * 3. 读取数字：
 *    num = 912834723
 *    下一个数字 3，判断溢出：
 *    912834723 > MAX_VALUE/10 (214748364)
 *    返回 Integer.MIN_VALUE
 * 结果：-2147483648
 * </pre>
 * <p>
 * <h3>4. 复杂度分析</h3>
 * <ul>
 *     <li><strong>时间复杂度</strong>：O(n)，其中 n 是字符串长度，只需遍历一次</li>
 *     <li><strong>空间复杂度</strong>：O(1)，只使用了常数级别的额外空间</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260331 {
    public int myAtoi(String s) {
        // 📏 获取字符串长度
        int n = s.length();

        // 👆 指针 i 用于遍历字符串
        int i = 0;

        // ⚪ 步骤 1：跳过前导空格
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }

        // 🔣 步骤 2：处理符号位
        // sign = 1 表示正数，sign = -1 表示负数
        int sign = 1;
        if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) {
            // 如果是 '+' 则为正，'-' 则为负
            sign = s.charAt(i) == '+' ? 1 : -1;
            i++;
        }

        // 🔢 步骤 3：转换数字
        int num = 0;
        for (; i < n && Character.isDigit(s.charAt(i)); i++) {
            // 将字符转换为对应的数字（'0' -> 0, '1' -> 1, ...）
            int d = s.charAt(i) - '0';

            // ⚠️ 步骤 4：溢出检查（在更新 num 之前）
            // 判断条件 1：num > MAX_VALUE / 10，再乘以 10 必然溢出
            // 判断条件 2：num * 10 > MAX_VALUE - d，加上当前数字会溢出
            if (num > Integer.MAX_VALUE / 10 || num * 10 > Integer.MAX_VALUE - d) {
                // 根据符号返回最大值或最小值
                return sign > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            // ✅ 更新 num：左移一位并加上当前数字
            num = num * 10 + d;
        }

        // 🎯 返回最终结果（带上符号）
        return sign * num;
    }
}

package CodeTop;

/**
 *
 * <a href="https://leetcode.cn/problems/add-strings/description/">415. 字符串相加</a>
 *
 * <p>题目描述：
 * 给定两个字符串形式的非负整数 num1 和 num2，计算它们的和并同样以字符串形式返回。
 * 你不能使用任何內建的用于处理大整数的库（比如 BigInteger），
 * 也不能直接将输入的字符串转换为整数形式。
 *
 * <p>解题思路：
 * 模拟手工加法的过程，从右到左逐位相加：
 * <ol>
 *   <li>使用双指针分别指向两个字符串的末尾</li>
 *   <li>逐位相加并处理进位</li>
 *   <li>将每一位的结果存储在缓冲区中</li>
 *   <li>最后反转结果字符串得到正确顺序</li>
 * </ol>
 *
 * <p>算法要点：
 * <ul>
 *   <li>字符转数字：利用ASCII码特性，'0'的ASCII值为48</li>
 *   <li>进位处理：使用carry变量记录进位值</li>
 *   <li>边界处理：当一个字符串遍历完后，继续处理另一个字符串</li>
 *   <li>结果构造：使用StringBuffer提高字符串拼接效率</li>
 * </ul>
 *
 * <p>时间复杂度：O(max(m,n))，其中m和n分别是两个字符串的长度
 * <p>空间复杂度：O(max(m,n))，用于存储结果字符串
 *
 * <p>示例：
 * 输入：num1 = "11", num2 = "123"
 * 输出："134"
 *
 * @author lxd
 **/
public class Day20260225 {
    /**
     * 字符串相加主函数
     * 模拟手工加法过程，从低位到高位逐位相加
     *
     * @param num1 第一个数字字符串
     * @param num2 第二个数字字符串
     * @return 相加结果的字符串形式
     */
    public String addStrings(String num1, String num2) {
        // 初始化指针：i指向num1末尾，j指向num2末尾
        // carry用于记录进位值，初始为0
        int i = num1.length() - 1, j = num2.length() - 1, carry = 0;

        // 使用StringBuffer提高字符串拼接效率
        StringBuffer ans = new StringBuffer();

        // 循环条件：任一字符串未遍历完 或 还有进位未处理
        while (i >= 0 || j >= 0 || carry != 0) {
            // 获取当前位的数字：
            // 如果指针有效则取对应字符并转换为数字，否则为0
            int x = i >= 0 ? num1.charAt(i) - '0' : 0;
            int y = j >= 0 ? num2.charAt(j) - '0' : 0;

            // 计算当前位的和
            int result = x + y + carry;

            // 将当前位结果（个位数）添加到答案中
            ans.append(result % 10);

            // 更新进位值（十位数）
            carry = result / 10;

            // 移动指针
            i--;
            j--;
        }

        // 由于是从低位到高位计算，结果是反的
        // 需要反转字符串得到正确的顺序
        ans.reverse();

        // 转换为字符串并返回
        return ans.toString();
    }
}

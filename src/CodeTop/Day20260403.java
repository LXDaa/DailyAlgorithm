package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/multiply-strings/description/">43. 字符串相乘</a>
 * <p>
 * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
 * <p>
 * 注意：不能使用任何内置的 BigInteger 库或直接将输入转换为整数。
 *
 * <h2>解题思路</h2>
 * <h3>1. 题目分析</h3>
 * <ul>
 *     <li><strong>大数乘法</strong>：模拟手工竖式乘法计算过程</li>
 *     <li><strong>核心难点</strong>：不能直接转换为整数，需要逐位相乘并处理进位</li>
 *     <li><strong>关键观察</strong>：两个数相乘，结果的最大位数是两数位数之和</li>
 * </ul>
 * <p>
 * <h3>2. 算法原理</h3>
 * <h4>(1) 位数规律</h4>
 * <ul>
 *     <li>m 位数 × n 位数 = 最多 m+n 位数</li>
 *     <li>例如：99 × 99 = 9801（2 位 × 2 位 = 4 位）</li>
 *     <li>使用长度为 m+n 的数组存储结果</li>
 * </ul>
 * <p>
 * <h4>(2) 位置映射关系（核心！）</h4>
 * <ul>
 *     <li>num1[i] × num2[j] 的结果会贡献到两个位置：</li>
 *     <li><strong>十位（进位）</strong>：res[i+j]</li>
 *     <li><strong>个位（当前位）</strong>：res[i+j+1]</li>
 *     <li>示例：num1="123", num2="45"</li>
 *     <li>3×5=15 → res[2+1]=5(个位), res[2]+=1(进位)</li>
 * </ul>
 * <p>
 * <h4>(3) 计算过程</h4>
 * <ul>
 *     <li>从右向左遍历两个字符串（从个位开始）</li>
 *     <li>对应位相乘 + 当前位的值 = sum</li>
 *     <li>sum % 10 → 更新当前位</li>
 *     <li>sum / 10 → 累加到进位位置</li>
 * </ul>
 * <p>
 * <h3>3. 算法示意图</h3>
 * <pre>
 * 示例：num1 = "123", num2 = "45"
 * (123 × 45 = 5535)
 *
 * 初始化:
 * m=3, n=2
 * res = [0, 0, 0, 0, 0] (长度 m+n=5)
 *       i2 i1 i0 j1 j0
 *
 * 第 1 轮：i=2 (数字 3)
 *   j=1 (数字 5):
 *     a=3, b=5
 *     sum = 3×5 + res[4] = 15 + 0 = 15
 *     res[4] = 15 % 10 = 5
 *     res[3] += 15 / 10 = 1
 *     res = [0, 0, 0, 1, 5]
 *
 *   j=0 (数字 4):
 *     a=3, b=4
 *     sum = 3×4 + res[3] = 12 + 1 = 13
 *     res[3] = 13 % 10 = 3
 *     res[2] += 13 / 10 = 1
 *     res = [0, 0, 1, 3, 5]
 *
 * 第 2 轮：i=1 (数字 2)
 *   j=1 (数字 5):
 *     a=2, b=5
 *     sum = 2×5 + res[3] = 10 + 3 = 13
 *     res[3] = 13 % 10 = 3
 *     res[2] += 13 / 10 = 1
 *     res = [0, 0, 2, 3, 5]
 *
 *   j=0 (数字 4):
 *     a=2, b=4
 *     sum = 2×4 + res[2] = 8 + 2 = 10
 *     res[2] = 10 % 10 = 0
 *     res[1] += 10 / 10 = 1
 *     res = [0, 1, 0, 3, 5]
 *
 * 第 3 轮：i=0 (数字 1)
 *   j=1 (数字 5):
 *     a=1, b=5
 *     sum = 1×5 + res[2] = 5 + 0 = 5
 *     res[2] = 5 % 10 = 5
 *     res[1] += 5 / 10 = 0
 *     res = [0, 1, 5, 3, 5]
 *
 *   j=0 (数字 4):
 *     a=1, b=4
 *     sum = 1×4 + res[1] = 4 + 1 = 5
 *     res[1] = 5 % 10 = 5
 *     res[0] += 5 / 10 = 0
 *     res = [0, 5, 5, 3, 5]
 *
 * 构建结果:
 * res[0]=0，跳过
 * 从 res[1] 开始："5535"
 *
 * 最终结果："5535" ✅
 * </pre>
 * <p>
 * <h3>4. 复杂度分析</h3>
 * <ul>
 *     <li><strong>时间复杂度</strong>：O(m × n)，其中 m 和 n 分别是两个字符串的长度</li>
 *     <li><strong>空间复杂度</strong>：O(m + n)，结果数组的大小</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260403 {
    public String multiply(String num1, String num2) {
        // 🛑 特殊情况：任一数为 0，直接返回 "0"
        if (num1.equals("0") || num2.equals("0")) {
            return "0"; // 核心：处理 "0" 并防止返回空串
        }

        // 📏 获取两个字符串的长度
        int m = num1.length(), n = num2.length();

        // 📦 结果数组：m 位数 × n 位数最多有 m+n 位
        int[] res = new int[m + n];

        // 🔄 从右向左遍历 num1（从个位开始）
        for (int i = m - 1; i >= 0; i--) {
            // 🔢 获取 num1 当前位的数字
            int a = num1.charAt(i) - '0';

            // 🔄 从右向左遍历 num2
            for (int j = n - 1; j >= 0; j--) {
                // 🔢 获取 num2 当前位的数字
                int b = num2.charAt(j) - '0';

                // ⚖️ 计算乘积 + 当前位已有的值
                // 关键：num1[i] × num2[j] 的结果存储在 res[i+j] 和 res[i+j+1]
                int sum = a * b + res[i + j + 1]; // 乘积 + 当前位

                // 📝 更新当前位（个位）
                res[i + j + 1] = sum % 10; // 更新当前位

                // ➕ 累加进位到前一位（十位）
                res[i + j] += sum / 10; // 进位累加
            }
        }

        // 🧱 构建结果字符串
        StringBuilder sb = new StringBuilder();

        // ⚠️ 跳过首位的 0（如果有的话）
        // 例如：123×45=5535，res=[0,5,5,3,5]，需要跳过 res[0]
        int start = (res[0] == 0) ? 1 : 0;
        for (int i = start; i < res.length; i++) {
            sb.append(res[i]);
        }

        // ✅ 返回最终结果
        return sb.toString();
    }
}

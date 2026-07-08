package Hot100.动态规划;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot082 {
    /**
     * <a href="https://leetcode.cn/problems/pascals-triangle/description/">118. 杨辉三角</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。</p>
     * <p>在「杨辉三角」中，每个数是它左上方和右上方的数的和。</p>
     *
     * <h3>💡 核心思路：动态规划</h3>
     * <ul>
     *   <li><b>基本思想</b>：逐行构建杨辉三角
     *     <ul>
     *       <li>第 i 行有 i+1 个元素（i 从 0 开始）</li>
     *       <li>每行的第一个和最后一个元素为 1</li>
     *       <li>中间元素：ans[i][j] = ans[i-1][j-1] + ans[i-1][j]</li>
     *       <li>从第 0 行开始，逐行构建到第 numRows-1 行</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么这样构建</b>：
     *     <ul>
     *       <li>杨辉三角的定义就是每个数是上方两数之和</li>
     *       <li>每行依赖于上一行的结果</li>
     *       <li>动态规划的最优子结构性质</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n²)，需要构建 n 行，每行最多 n 个元素</li>
     *   <li><b>空间复杂度</b>：O(n²)，存储整个杨辉三角</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：numRows = 5
     *
     * 初始化：ans = []
     *
     * i=0（第1行）：
     *   subList = [1]
     *   ans = [[1]]
     *
     * i=1（第2行）：
     *   j=0: subList.add(1) → [1]
     *   j=1: subList.add(1) → [1, 1]
     *   ans = [[1], [1, 1]]
     *
     * i=2（第3行）：
     *   j=0: subList.add(1) → [1]
     *   j=1: subList.add(ans[1][0]+ans[1][1]) = 1+1 = 2 → [1, 2]
     *   j=2: subList.add(1) → [1, 2, 1]
     *   ans = [[1], [1, 1], [1, 2, 1]]
     *
     * i=3（第4行）：
     *   j=0: subList.add(1) → [1]
     *   j=1: subList.add(ans[2][0]+ans[2][1]) = 1+2 = 3 → [1, 3]
     *   j=2: subList.add(ans[2][1]+ans[2][2]) = 2+1 = 3 → [1, 3, 3]
     *   j=3: subList.add(1) → [1, 3, 3, 1]
     *   ans = [[1], [1, 1], [1, 2, 1], [1, 3, 3, 1]]
     *
     * i=4（第5行）：
     *   j=0: subList.add(1) → [1]
     *   j=1: subList.add(ans[3][0]+ans[3][1]) = 1+3 = 4 → [1, 4]
     *   j=2: subList.add(ans[3][1]+ans[3][2]) = 3+3 = 6 → [1, 4, 6]
     *   j=3: subList.add(ans[3][2]+ans[3][3]) = 3+1 = 4 → [1, 4, 6, 4]
     *   j=4: subList.add(1) → [1, 4, 6, 4, 1]
     *   ans = [[1], [1, 1], [1, 2, 1], [1, 3, 3, 1], [1, 4, 6, 4, 1]]
     *
     * 最终结果：
     *   [
     *     [1],
     *     [1, 1],
     *     [1, 2, 1],
     *     [1, 3, 3, 1],
     *     [1, 4, 6, 4, 1]
     *   ] ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 行索引如何定义？
     *    - i 从 0 开始，第 i 行有 i+1 个元素
     *    - j 从 0 到 i，遍历每行的每个元素
     *    - 例如：i=0 有 1 个元素，i=1 有 2 个元素
     *
     * 2️⃣ 边界元素如何处理？
     *    - 当 j == 0 或 j == i 时，元素为 1
     *    - 每行的第一个和最后一个元素都是 1
     *    - 无需计算，直接添加
     *
     * 3️⃣ 中间元素如何计算？
     *    - ans[i][j] = ans[i-1][j-1] + ans[i-1][j]
     *    - 当前元素 = 上一行左边元素 + 上一行当前位置元素
     *    - 通过 ans.get(i-1).get(j-1) 和 ans.get(i-1).get(j) 获取
     *
     * 4️⃣ 数据结构如何选择？
     *    - 使用 List<List<Integer>> 存储整个三角
     *    - 每行使用 ArrayList<Integer>
     *    - 便于动态添加元素和随机访问
     *
     * 5️⃣ 为什么时间复杂度是 O(n²)？
     *    - 第 i 行有 i+1 个元素
     *    - 总元素数 = 1 + 2 + 3 + ... + n = n(n+1)/2
     *    - 时间复杂度 O(n²)
     *
     * 6️⃣ 空间复杂度如何？
     *    - 需要存储整个杨辉三角
     *    - 空间复杂度 O(n²)
     *    - 无法优化（必须存储所有元素）
     *
     * 7️⃣ 边界情况测试：
     *    - numRows = 0：返回空列表
     *    - numRows = 1：返回 [[1]]
     *    - numRows = 2：返回 [[1], [1, 1]]
     *
     * 8️⃣ 完整流程总结：
     *
     *    ans = []
     *
     *    for i in 0..numRows-1：
     *      subList = []
     *      for j in 0..i：
     *        if j == 0 或 j == i：
     *          subList.add(1)
     *        else：
     *          subList.add(ans[i-1][j-1] + ans[i-1][j])
     *      ans.add(subList)
     *
     *    return ans
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>行索引</b>：i 从 0 开始，第 i 行有 i+1 个元素</li>
     *   <li><b>边界元素</b>：j == 0 或 j == i 时为 1</li>
     *   <li><b>中间元素</b>：上一行的两个相邻元素之和</li>
     *   <li><b>数据结构</b>：使用 List<List<Integer>></li>
     *   <li><b>索引访问</b>：通过 ans.get(i-1).get(j) 访问上一行元素</li>
     * </ul>
     *
     * @param numRows 杨辉三角的行数
     * @return 杨辉三角的前 numRows 行
     */
    public List<List<Integer>> generate(int numRows) {
        // 📦 创建结果列表
        List<List<Integer>> ans = new ArrayList<>();

        // 🔄 遍历每行
        for (int i = 0; i < numRows; i++) {
            // 📋 创建当前行的列表
            ArrayList<Integer> subList = new ArrayList<>();

            // 🔄 遍历当前行的每个元素
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    // 📌 边界元素：第一个和最后一个元素为 1
                    subList.add(1);
                } else {
                    // 📊 中间元素：上一行的两个相邻元素之和
                    subList.add(ans.get(i - 1).get(j) + ans.get(i - 1).get(j - 1));
                }
            }

            // ➕ 将当前行添加到结果列表
            ans.add(subList);
        }

        // ✅ 返回结果
        return ans;
    }
}

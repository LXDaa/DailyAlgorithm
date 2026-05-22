package Hot100.矩阵;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot019 {
    class Solution {
        /**
         * <a href="https://leetcode.cn/problems/spiral-matrix/description/">54. 螺旋矩阵</a>
         *
         * <h3>🎯 题目描述</h3>
         * <p>给你一个 m 行 n 列的矩阵 matrix ，请按照顺时针螺旋顺序，返回矩阵中的所有元素。</p>
         *
         * <h3>💡 核心思路：模拟螺旋遍历</h3>
         * <ul>
         *   <li><b>基本思想</b>：使用四个边界变量控制遍历范围，按顺时针方向逐层遍历
         *     <ul>
         *       <li>定义四个边界：left（左）、right（右）、top（上）、bottom（下）</li>
         *       <li>按顺序遍历：顶行（左→右）→ 右列（上→下）→ 底行（右→左）→ 左列（下→上）</li>
         *       <li>每遍历完一条边，收缩对应的边界</li>
         *       <li>重复直到所有元素都被遍历</li>
         *     </ul>
         *   </li>
         *   <li><b>时间复杂度</b>：O(m × n)，每个元素访问一次</li>
         *   <li><b>空间复杂度</b>：O(1)，不计输出数组</li>
         * </ul>
         *
         * <h3>📊 ASCII 演示过程</h3>
         * <pre>
         * 示例：matrix = [
         *   [1, 2, 3],
         *   [4, 5, 6],
         *   [7, 8, 9]
         * ]
         *
         * 初始化：left=0, right=3, top=0, bottom=3
         *
         * 【第一轮】
         *   ① 顶行左到右（top=0）：
         *      遍历 matrix[0][0], matrix[0][1], matrix[0][2]
         *      添加：1, 2, 3
         *      top++ → top=1
         *
         *   ② 右列上到下（right-1=2）：
         *      遍历 matrix[1][2], matrix[2][2]
         *      添加：6, 9
         *      right-- → right=2
         *
         *   ③ 底行右到左（bottom-1=2）：
         *      检查：top(1) < bottom(3) ✅
         *      遍历 matrix[2][1], matrix[2][0]
         *      添加：8, 7
         *      bottom-- → bottom=2
         *
         *   ④ 左列下到上（left=0）：
         *      检查：left(0) < right(2) ✅
         *      遍历 matrix[1][0]
         *      添加：4
         *      left++ → left=1
         *
         *   当前结果：[1, 2, 3, 6, 9, 8, 7, 4]
         *   边界：left=1, right=2, top=1, bottom=2
         *
         * 【第二轮】
         *   ① 顶行左到右（top=1）：
         *      遍历 matrix[1][1]
         *      添加：5
         *      top++ → top=2
         *
         *   ② 右列上到下（right-1=1）：
         *      检查：top(2) < bottom(2) ❌
         *      不执行
         *
         *   循环结束：top(2) >= bottom(2)
         *
         * 最终结果：[1, 2, 3, 6, 9, 8, 7, 4, 5] ✅
         *
         * 图示：
         *   原始矩阵：        遍历顺序：
         *   1  2  3          1→ 2→ 3↓
         *   4  5  6          ↑        ↓
         *   7  8  9          7← 8← 9↓
         *                    ↑
         *                    4
         *                    ↓
         *                    5
         * </pre>
         *
         * <h3>🔑 关键技巧解析</h3>
         * <pre>
         * 1️⃣ 为什么需要检查 top < bottom 和 left < right？
         *    - 防止重复遍历：当矩阵不是正方形时，可能只剩一行或一列
         *    - 例如：只剩一行时，顶行已经遍历过，不需要再遍历底行
         *    - 例如：只剩一列时，右列已经遍历过，不需要再遍历左列
         *    - 这两个检查确保不会重复添加元素
         *
         * 2️⃣ 边界收缩的顺序？
         *    - 顶行遍历完 → top++（上边界下移）
         *    - 右列遍历完 → right--（右边界左移）
         *    - 底行遍历完 → bottom--（下边界上移）
         *    - 左列遍历完 → left++（左边界右移）
         *    - 每次收缩后，下一轮遍历的范围就缩小了
         *
         * 3️⃣ 为什么右列用 right-1，底行用 bottom-1？
         *    - right 和 bottom 是开区间边界（不包含）
         *    - 实际访问的索引是 right-1 和 bottom-1
         *    - 这样设计方便判断循环条件：left < right, top < bottom
         *
         * 4️⃣ 循环终止条件？
         *    - while (left < right && top < bottom)
         *    - 当左边界超过右边界，或上边界超过下边界时停止
         *    - 说明所有元素都已遍历完毕
         *
         * 5️⃣ 与递归法的对比？
         *    - 递归法：每次处理一层，递归处理内层 → 代码复杂，栈空间开销
         *    - 迭代法：用边界变量控制 → 代码简洁，空间 O(1) ✅
         *    - 迭代法更直观，效率更高
         *
         * 6️⃣ 边界情况处理？
         *    - 空矩阵：直接返回空列表
         *    - 单行矩阵：只执行顶行遍历
         *    - 单列矩阵：只执行顶行和右列遍历
         *    - 单元素矩阵：只执行顶行遍历
         * </pre>
         *
         * <h3>⚠️ 注意事项</h3>
         * <ul>
         *   <li><b>边界检查</b>：处理空矩阵的情况</li>
         *   <li><b>重复检查</b>：底行和左列遍历前必须检查，避免重复</li>
         *   <li><b>索引计算</b>：注意 right-1 和 bottom-1 的使用</li>
         *   <li><b>边界收缩</b>：每条边遍历完后立即收缩边界</li>
         *   <li><b>循环条件</b>：使用 && 而不是 ||，确保两个方向都有效</li>
         * </ul>
         *
         * @param matrix m 行 n 列的二维矩阵
         * @return 按螺旋顺序排列的元素列表
         */
        public List<Integer> spiralOrder(int[][] matrix) {
            List<Integer> res = new ArrayList<>();

            // 🛡️ 边界检查：处理空矩阵
            if (matrix.length == 0) {
                return res;
            }

            // 🔧 初始化四个边界
            int left = 0, top = 0;
            int right = matrix[0].length;  // 开区间，实际右边界是 right-1
            int bottom = matrix.length;    // 开区间，实际下边界是 bottom-1

            // 🔄 循环遍历，直到边界交叉
            while (left < right && top < bottom) {
                // ➡️ 顶行：从左到右
                for (int j = left; j < right; j++) {
                    res.add(matrix[top][j]);
                }
                top++;  // 上边界下移

                // ⬇️ 右列：从上到下
                for (int i = top; i < bottom; i++) {
                    res.add(matrix[i][right - 1]);
                }
                right--;  // 右边界左移

                // ⬅️ 底行：从右到左（需要检查是否还有行）
                if (top < bottom) {
                    for (int j = right - 1; j >= left; j--) {
                        res.add(matrix[bottom - 1][j]);
                    }
                    bottom--;  // 下边界上移
                }

                // ⬆️ 左列：从下到上（需要检查是否还有列）
                if (left < right) {
                    for (int i = bottom - 1; i >= top; i--) {
                        res.add(matrix[i][left]);
                    }
                    left++;  // 左边界右移
                }
            }

            return res;
        }
    }
}

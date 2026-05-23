package Hot100.矩阵;

/**
 * @author lxd
 **/
public class Hot020 {
    /**
     * <a href="https://leetcode.cn/problems/rotate-image/description/">48. 旋转图像</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。</p>
     *
     * <h3>💡 核心思路：分层旋转（四元素交换）</h3>
     * <ul>
     *   <li><b>基本思想</b>：从外层到内层，逐层旋转，每层通过四个元素的循环交换实现旋转
     *     <ul>
     *       <li>将矩阵看作多个同心正方形层</li>
     *       <li>对于每一层，将四个对应位置的元素进行循环交换</li>
     *       <li>左上 → 右上 → 右下 → 左下 → 左上</li>
     *       <li>从外层向内层逐层处理</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n²)，每个元素访问一次</li>
     *   <li><b>空间复杂度</b>：O(1)，原地旋转</li>
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
     * 初始化：left=0, right=2
     *
     * 【第一层】left=0, right=2
     *   需要旋转的元素个数：right - left = 2
     *
     *   i=0: 旋转四个角的元素
     *     top=0, bottom=2
     *     四个位置：
     *       - 左上: matrix[0][0] = 1
     *       - 右上: matrix[0][2] = 3
     *       - 右下: matrix[2][2] = 9
     *       - 左下: matrix[2][0] = 7
     *
     *     逆时针赋值（实现顺时针旋转）：
     *       matrix[0][0] = matrix[2][0] = 7  （左下 → 左上）
     *       matrix[2][0] = matrix[2][2] = 9  （右下 → 左下）
     *       matrix[2][2] = matrix[0][2] = 3  （右上 → 右下）
     *       matrix[0][2] = leftTop = 1       （左上 → 右上）
     *
     *     结果：
     *     [7, 2, 1]
     *     [4, 5, 6]
     *     [9, 8, 3]
     *
     *   i=1: 旋转四条边中间的元素
     *     top=0, bottom=2
     *     四个位置：
     *       - 上边: matrix[0][1] = 2
     *       - 右边: matrix[1][2] = 6
     *       - 下边: matrix[2][1] = 8
     *       - 左边: matrix[1][0] = 4
     *
     *     逆时针赋值：
     *       matrix[0][1] = matrix[1][0] = 4  （左边 → 上边）
     *       matrix[1][0] = matrix[2][1] = 8  （下边 → 左边）
     *       matrix[2][1] = matrix[1][2] = 6  （右边 → 下边）
     *       matrix[1][2] = leftTop = 2       （上边 → 右边）
     *
     *     结果：
     *     [7, 4, 1]
     *     [8, 5, 2]
     *     [9, 6, 3]
     *
     *   left++ → left=1, right-- → right=1
     *   循环结束：left >= right
     *
     * 最终结果：
     * [
     *   [7, 4, 1],
     *   [8, 5, 2],
     *   [9, 6, 3]
     * ] ✅
     *
     * 图示：
     *   原始矩阵：          旋转90度后：
     *   1  2  3            7  4  1
     *   4  5  6      →     8  5  2
     *   7  8  9            9  6  3
     *
     *   旋转过程（外层）：
     *   1→3  2    7→1  4
     *    ↓              ↓
     *   4    6    8    2
     *    ↓              ↓
     *   7←9  8    9←6  3
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么是四层循环交换？
     *    - 顺时针旋转90度，每个元素移动到下一个位置
     *    - 四个位置的元素形成循环：左上→右上→右下→左下→左上
     *    - 用临时变量保存一个值，然后逆序赋值实现轮换
     *
     * 2️⃣ 四个位置的坐标关系？
     *    - 左上: matrix[top][left + i]
     *    - 右上: matrix[top + i][right]
     *    - 右下: matrix[bottom][right - i]
     *    - 左下: matrix[bottom - i][left]
     *    - 随着 i 的变化，这四个位置沿着四条边移动
     *
     * 3️⃣ 为什么要逆时针赋值？
     *    - 要实现顺时针旋转，需要逆时针方向赋值
     *    - 例如：左下的值赋给左上，右下的值赋给左下...
     *    - 这样每个位置都获得了它顺时针方向前一个位置的值
     *
     * 4️⃣ 分层处理的逻辑？
     *    - left 和 right 定义当前层的左右边界
     *    - 每处理完一层，left++，right--，进入内层
     *    - 当 left >= right 时，所有层处理完毕
     *    - 对于 n×n 矩阵，共有 n/2 层
     *
     * 5️⃣ 每层需要旋转多少个元素？
     *    - right - left 个元素
     *    - 例如：3×3 矩阵的外层，right-left=2，需要旋转2组
     *    - 例如：4×4 矩阵的外层，right-left=3，需要旋转3组
     *
     * 6️⃣ 与转置+翻转法的对比？
     *    - 转置+翻转：先沿主对角线转置，再左右翻转 → 两次遍历
     *    - 分层旋转：一次遍历完成 → 更直观，但代码稍复杂
     *    - 两种方法都是 O(n²) 时间，O(1) 空间
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>原地操作</b>：直接修改原矩阵，不需要返回值</li>
     *   <li><b>正方形矩阵</b>：题目保证是 n×n 矩阵</li>
     *   <li><b>索引计算</b>：仔细推导四个位置的坐标公式</li>
     *   <li><b>临时变量</b>：必须保存左上角的值，避免被覆盖</li>
     *   <li><b>循环次数</b>：每层旋转 right-left 次，不是 right-left+1 次</li>
     * </ul>
     *
     * @param matrix n × n 的二维矩阵
     */
    public void rotate(int[][] matrix) {
        // 🔧 初始化左右边界
        int left = 0, right = matrix[0].length - 1;

        // 🔄 逐层处理，从外层到内层
        while (left < right) {
            // 📍 处理当前层的每个元素组
            for (int i = 0; i < right - left; i++) {
                int top = left, bottom = right;

                // 💾 保存左上角的值
                int leftTop = matrix[top][left + i];

                // 🔄 四元素循环交换（逆时针赋值实现顺时针旋转）
                // 左下 → 左上
                matrix[top][left + i] = matrix[bottom - i][left];
                // 右下 → 左下
                matrix[bottom - i][left] = matrix[bottom][right - i];
                // 右上 → 右下
                matrix[bottom][right - i] = matrix[top + i][right];
                // 左上（保存的值）→ 右上
                matrix[top + i][right] = leftTop;

            }

            // 👉 收缩边界，进入内层
            left++;
            right--;

        }

    }
}

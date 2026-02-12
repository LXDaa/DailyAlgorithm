package CodeTop;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/spiral-matrix/">54. 螺旋矩阵</a>
 *
 * <p>解题思路：</p>
 * <ol>
 * <li>使用边界收缩法模拟螺旋遍历过程</li>
 * <li>维护四个边界：上(top)、下(bottom)、左(left)、右(right)</li>
 * <li>按顺时针方向依次遍历：上边界→右边界→下边界→左边界</li>
 * <li>每完成一边的遍历，对应边界向内收缩</li>
 * <li>重复直到所有元素都被访问</li>
 * </ol>
 *
 * <p>算法复杂度：</p>
 * <ul>
 * <li>时间复杂度：O(m×n)，需要访问矩阵中的每个元素</li>
 * <li>空间复杂度：O(1)，除了结果数组外只使用常数额外空间</li>
 * </ul>
 *
 * <p>执行示例：</p>
 * <pre>
 * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[1,2,3,6,9,8,7,4,5]
 *
 * 执行过程：
 * 第1轮：上边界[1,2,3] → 右边界[6,9] → 下边界[8,7] → 左边界[4]
 * 第2轮：上边界[5] → 完成
 *
 * 边界变化：
 * 初始：top=0,bottom=3,left=0,right=3
 * 第1轮后：top=1,bottom=2,left=1,right=2
 * 第2轮后：top=2,bottom=1 → 循环结束
 * </pre>
 *
 * @author lxd
 **/
public class Day20260212 {
    public List<Integer> spiralOrder(int[][] matrix) {
        // 结果列表，存储螺旋遍历的元素
        List<Integer> res = new ArrayList<>();

        // 边界条件检查：空矩阵直接返回空结果
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }

        // 获取矩阵维度
        int rows = matrix.length;
        int cols = matrix[0].length;

        // 初始化四个边界
        int top = 0, bottom = rows, left = 0, right = cols;

        // 主循环：当左右边界和上下边界仍有空间时继续遍历
        while (left < right && top < bottom) {

            // 1. 遍历上边界：从左到右
            for (int col = left; col < right; col++) {
                res.add(matrix[top][col]);
            }
            // 上边界下移
            top++;

            // 2. 遍历右边界：从上到下
            for (int row = top; row < bottom; row++) {
                res.add(matrix[row][right - 1]);
            }
            // 右边界左移
            right--;

            // 检查是否还有行需要遍历（避免重复访问）
            if (top < bottom) {
                // 3. 遍历下边界：从右到左
                for (int col = right - 1; col >= left; col--) {
                    res.add(matrix[bottom - 1][col]);
                }
                // 下边界上移
                bottom--;
            }

            // 检查是否还有列需要遍历（避免重复访问）
            if (left < right) {
                // 4. 遍历左边界：从下到上
                for (int row = bottom - 1; row >= top; row--) {
                    res.add(matrix[row][left]);
                }
                // 左边界右移
                left++;
            }
        }

        return res;
    }
}

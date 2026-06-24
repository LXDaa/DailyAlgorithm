package Hot100.二分查找;

/**
 * @author lxd
 **/
public class Hot064 {
    /**
     * <a href="https://leetcode.cn/problems/search-a-2d-matrix/description/">74. 搜索二维矩阵</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：</p>
     * <ul>
     *   <li>每行中的整数从左到右按升序排列。</li>
     *   <li>每行的第一个整数大于前一行的最后一个整数。</li>
     * </ul>
     *
     * <h3>💡 核心思路：二维矩阵一维化 + 二分查找</h3>
     * <ul>
     *   <li><b>基本思想</b>：将二维矩阵视为一维有序数组进行二分查找
     *     <ul>
     *       <li>矩阵共有 m × n 个元素</li>
     *       <li>一维索引 idx 对应二维坐标：(idx / n, idx % n)</li>
     *       <li>使用标准二分查找在 [0, m×n-1] 范围内搜索</li>
     *       <li>通过坐标转换访问矩阵元素</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么可以这样做</b>：
     *     <ul>
     *       <li>每行有序，且下一行第一个元素大于上一行最后一个元素</li>
     *       <li>整个矩阵可以看作一个连续的有序数组</li>
     *       <li>避免逐行逐列遍历，时间复杂度从 O(m×n) 降到 O(log(m×n))</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(log(m×n))，二分查找的标准复杂度</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常数级别的额外空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
     *
     * 矩阵结构：
     *     0   1   2   3
     *  0 [1,  3,  5,  7]
     *  1 [10, 11, 16, 20]
     *  2 [23, 30, 34, 60]
     *
     * 一维化索引：
     *   idx: 0 → (0,0)=1, 1 → (0,1)=3, 2 → (0,2)=5, 3 → (0,3)=7
     *   idx: 4 → (1,0)=10, 5 → (1,1)=11, 6 → (1,2)=16, 7 → (1,3)=20
     *   idx: 8 → (2,0)=23, 9 → (2,1)=30, 10 → (2,2)=34, 11 → (2,3)=60
     *
     * 查找 target = 3：
     *
     * 初始状态：
     *   left = 0, right = 11
     *
     * 第一次循环：
     *   mid = 0 + (11 - 0) / 2 = 5
     *   row = 5 / 4 = 1, col = 5 % 4 = 1
     *   matrix[1][1] = 11 > 3
     *   right = mid - 1 = 4
     *
     * 第二次循环：
     *   mid = 0 + (4 - 0) / 2 = 2
     *   row = 2 / 4 = 0, col = 2 % 4 = 2
     *   matrix[0][2] = 5 > 3
     *   right = mid - 1 = 1
     *
     * 第三次循环：
     *   mid = 0 + (1 - 0) / 2 = 0
     *   row = 0 / 4 = 0, col = 0 % 4 = 0
     *   matrix[0][0] = 1 < 3
     *   left = mid + 1 = 1
     *
     * 第四次循环：
     *   mid = 1 + (1 - 1) / 2 = 1
     *   row = 1 / 4 = 0, col = 1 % 4 = 1
     *   matrix[0][1] = 3 == 3 ✅
     *   return true
     *
     * 查找 target = 13（不存在）：
     *
     * 循环过程：
     *   mid=5: matrix[1][1]=11<13 → left=6
     *   mid=8: matrix[2][0]=23>13 → right=7
     *   mid=6: matrix[1][2]=16>13 → right=5
     *
     * 循环结束（left > right）：
     *   return false ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 一维索引如何转换为二维坐标？
     *    - 总列数为 n
     *    - row = idx / n（整数除法，向下取整）
     *    - col = idx % n（取余数）
     *    - 例如：idx=5, n=4 → row=1, col=1
     *
     * 2️⃣ 为什么可以一维化？
     *    - 每行有序：matrix[i][j] < matrix[i][j+1]
     *    - 行之间有序：matrix[i][n-1] < matrix[i+1][0]
     *    - 整个矩阵构成一个严格递增的序列
     *
     * 3️⃣ 二分查找的边界如何确定？
     *    - left = 0（第一个元素）
     *    - right = m * n - 1（最后一个元素）
     *    - 覆盖所有可能的索引
     *
     * 4️⃣ 循环条件为什么是 left <= right？
     *    - 确保检查所有可能的位置
     *    - 当 left == right 时，还需要检查一次
     *    - 避免漏掉最后一个元素
     *
     * 5️⃣ 三种情况的处理：
     *    - matrix[row][col] < target → left = mid + 1（在右半部分）
     *    - matrix[row][col] > target → right = mid - 1（在左半部分）
     *    - matrix[row][col] == target → return true（找到目标）
     *
     * 6️⃣ 循环结束后做什么？
     *    - 如果循环正常结束，说明没有找到目标
     *    - 直接返回 false
     *
     * 7️⃣ 时间复杂度分析：
     *    - 总元素数为 m × n
     *    - 二分查找需要 log(m×n) 次循环
     *    - 时间复杂度 O(log(m×n)) = O(log m + log n)
     *
     * 8️⃣ 边界情况测试：
     *    - 单行矩阵：[[1,2,3,4]], target=3 → return true
     *    - 单列矩阵：[[1],[2],[3],[4]], target=3 → return true
     *    - 单元素矩阵：[[1]], target=1 → return true
     *    - 空矩阵：[][] → 注意处理边界
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>空矩阵处理</b>：需要检查 m == 0 或 n == 0 的情况</li>
     *   <li><b>整数溢出</b>：计算 mid 时使用 left + (right - left) / 2</li>
     *   <li><b>坐标转换</b>：row = mid / n，col = mid % n，注意除法顺序</li>
     *   <li><b>矩阵特性</b>：题目保证矩阵有序，这是二分查找的前提</li>
     *   <li><b>返回值</b>：找到返回 true，未找到返回 false</li>
     * </ul>
     *
     * @param matrix m x n 的二维矩阵
     * @param target 目标值
     * @return 是否存在目标值
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        // 📏 获取矩阵的行数和列数
        int m = matrix.length, n = matrix[0].length;

        // 📋 初始化左右指针（一维化索引）
        int left = 0, right = m * n - 1;

        // 🔄 二分查找循环
        while (left <= right) {
            // 📐 计算中间位置（避免溢出）
            int mid = left + (right - left) / 2;

            // 📍 将一维索引转换为二维坐标
            int x = matrix[mid / n][mid % n];

            if (x < target) {
                // 🔍 target 在右半部分
                left = mid + 1;
            } else if (x > target) {
                // 🔍 target 在左半部分
                right = mid - 1;
            } else {
                // ✅ 找到目标值
                return true;
            }
        }

        // ❌ 未找到目标值
        return false;
    }
}

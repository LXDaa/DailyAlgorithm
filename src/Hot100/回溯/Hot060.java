package Hot100.回溯;

/**
 * @author lxd
 **/
public class Hot060 {
    /**
     * <a href="https://leetcode.cn/problems/word-search/description/">79. 单词搜索</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true 。否则，返回 false 。</p>
     * <p>单词必须按照字母顺序，通过相邻的单元格内的字母构成。相邻单元格是水平或垂直方向相邻的单元格。同一个单元格内的字母不允许被重复使用。</p>
     *
     * <h3>💡 核心思路：回溯算法 + 方向搜索</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用回溯算法，从每个单元格出发尝试匹配单词
     *     <ul>
     *       <li>遍历网格中的每个单元格作为起点</li>
     *       <li>从起点开始，向上下左右四个方向搜索</li>
     *       <li>使用 visited 数组记录已访问的单元格</li>
     *       <li>匹配成功返回 true，失败则回溯</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么需要回溯</b>：
     *     <ul>
     *       <li>一个起点可能有多个匹配路径</li>
     *       <li>需要尝试所有可能的方向</li>
     *       <li>当一条路走不通时，需要回退到上一个状态</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(m × n × 4^L)，m × n 是起点数，L 是单词长度</li>
     *   <li><b>空间复杂度</b>：O(m × n)，visited 数组和递归栈</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
     *
     * 网格：
     *   0   1   2   3
     * 0 A   B   C   E
     * 1 S   F   C   S
     * 2 A   D   E   E
     *
     * 【搜索过程】
     *
     * 从 (0, 0) 开始搜索 "ABCCED"：
     *
     * backtrack(0, 0, 0)
     *   ├─ board[0][0] = 'A', word[0] = 'A' ✅
     *   ├─ visited[0][0] = true
     *   │
     *   ├─ 向上 (row-1)：超出边界，return false
     *   │
     *   ├─ 向下 (1, 0)
     *   │   backtrack(1, 0, 1)
     *   │   ├─ board[1][0] = 'S', word[1] = 'B' ❌
     *   │   └─ return false
     *   │
     *   ├─ 向左 (0, -1)：超出边界，return false
     *   │
     *   ├─ 向右 (0, 1)
     *   │   backtrack(0, 1, 1)
     *   │   ├─ board[0][1] = 'B', word[1] = 'B' ✅
     *   │   ├─ visited[0][1] = true
     *   │   │
     *   │   ├─ 向上：超出边界
     *   │   ├─ 向下 (1, 1)
     *   │   │   backtrack(1, 1, 2)
     *   │   │   ├─ board[1][1] = 'F', word[2] = 'C' ❌
     *   │   │   └─ return false
     *   │   │
     *   │   ├─ 向左 (0, 0)：visited[0][0] = true ❌
     *   │   │
     *   │   ├─ 向右 (0, 2)
     *   │   │   backtrack(0, 2, 2)
     *   │   │   ├─ board[0][2] = 'C', word[2] = 'C' ✅
     *   │   │   ├─ visited[0][2] = true
     *   │   │   │
     *   │   │   ├─ 向右 (0, 3)
     *   │   │   │   backtrack(0, 3, 3)
     *   │   │   │   ├─ board[0][3] = 'E', word[3] = 'C' ❌
     *   │   │   │   └─ return false
     *   │   │   │
     *   │   │   ├─ 向下 (1, 2)
     *   │   │   │   backtrack(1, 2, 3)
     *   │   │   │   ├─ board[1][2] = 'C', word[3] = 'C' ✅
     *   │   │   │   ├─ visited[1][2] = true
     *   │   │   │   │
     *   │   │   │   ├─ 向右 (1, 3)
     *   │   │   │   │   backtrack(1, 3, 4)
     *   │   │   │   │   ├─ board[1][3] = 'S', word[4] = 'E' ❌
     *   │   │   │   │   └─ return false
     *   │   │   │   │
     *   │   │   │   ├─ 向下 (2, 2)
     *   │   │   │   │   backtrack(2, 2, 4)
     *   │   │   │   │   ├─ board[2][2] = 'E', word[4] = 'E' ✅
     *   │   │   │   │   ├─ visited[2][2] = true
     *   │   │   │   │   │
     *   │   │   │   │   ├─ 向右 (2, 3)
     *   │   │   │   │   │   backtrack(2, 3, 5)
     *   │   │   │   │   │   ├─ board[2][3] = 'E', word[5] = 'D' ❌
     *   │   │   │   │   │   └─ return false
     *   │   │   │   │   │
     *   │   │   │   │   ├─ 向下：超出边界
     *   │   │   │   │   │
     *   │   │   │   │   ├─ 向左 (2, 1)
     *   │   │   │   │   │   backtrack(2, 1, 5)
     *   │   │   │   │   │   ├─ board[2][1] = 'D', word[5] = 'D' ✅
     *   │   │   │   │   │   ├─ index = 6 == word.length() ✅
     *   │   │   │   │   │   └─ return true ✅
     *   │   │   │   │   │
     *   │   │   │   │   └─ ...
     *   │   │   │   │
     *   │   │   │   └─ ...
     *   │   │   │
     *   │   │   └─ ...
     *   │   │
     *   │   └─ ...
     *   │
     *   └─ ...
     *
     * 【最终结果】
     *
     * 找到单词 "ABCCED" ✅
     * 路径：A(0,0) → B(0,1) → C(0,2) → C(1,2) → E(2,2) → D(2,1)
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么需要遍历所有起点？
     *    - 单词可能从网格中任意位置开始
     *    - 需要从每个单元格尝试作为起点
     *    - 只要找到一个匹配就返回 true
     *
     * 2️⃣ 如何避免重复使用单元格？
     *    - 使用 visited 数组标记已访问的单元格
     *    - 进入递归时标记为 true
     *    - 返回前撤销标记为 false
     *    - 这就是回溯的关键
     *
     * 3️⃣ 递归终止条件是什么？
     *    - index == word.length()：匹配完成
     *    - 返回 true 表示找到单词
     *
     * 4️⃣ 剪枝条件有哪些？
     *    - 越界：row < 0 || row >= rows || col < 0 || col >= cols
     *    - 字符不匹配：board[row][col] != word.charAt(index)
     *    - 已访问：visited[row][col] == true
     *    - 任何一条满足就返回 false
     *
     * 5️⃣ 四个方向的搜索顺序重要吗？
     *    - 不重要，顺序可以任意
     *    - 使用短路求值，只要有一个方向返回 true 就继续
     *    - || 操作符保证了只要找到一条路径就返回
     *
     * 6️⃣ 回溯操作是什么？
     *    - visited[row][col] = true：标记为已访问
     *    - 递归调用：向四个方向搜索
     *    - visited[row][col] = false：撤销标记
     *    - 撤销必须在递归返回后执行
     *
     * 7️⃣ 时间复杂度如何分析？
     *    - 起点数：m × n
     *    - 每个起点最多搜索 4^L 条路径（L 为单词长度）
     *    - 总体复杂度：O(m × n × 4^L)
     *    - 实际由于剪枝，会比理论值小
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ 遍历所有单元格作为起点
     *    ├─ 调用 backtrack(board, word, 0, row, col, visited)
     *    └─ 只要找到一个匹配就返回 true
     *
     *    回溯函数 backtrack(board, word, index, row, col, visited)：
     *    ├─ if index == word.length() → return true
     *    ├─ 剪枝条件检查
     *    ├─ visited[row][col] = true
     *    ├─ res = 四个方向递归 || 的结果
     *    ├─ visited[row][col] = false（回溯）
     *    └─ return res
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>起点遍历</b>：必须从每个单元格尝试作为起点</li>
     *   <li><b>visited 标记</b>：进入递归标记，递归返回撤销</li>
     *   <li><b>越界检查</b>：必须在访问前检查越界</li>
     *   <li><b>字符匹配</b>：先检查字符是否匹配，再检查 visited</li>
     *   <li><b>短路求值</b>：四个方向的 || 保证了只要找到一条路径就返回</li>
     * </ul>
     *
     * @param board 二维字符网格
     * @param word  要搜索的单词
     * @return 如果单词存在于网格中返回 true，否则返回 false
     */
    public boolean exist(char[][] board, String word) {
        // 📏 获取网格的行数和列数
        int rows = board.length;
        int cols = board[0].length;
        // 📋 创建访问标记数组
        boolean[][] visited = new boolean[rows][cols];

        // 🔍 遍历网格中的每个单元格作为起点
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // 🔄 从当前单元格开始搜索
                if (backtrack(board, word, 0, row, col, visited)) {
                    // ✅ 找到单词，直接返回 true
                    return true;
                }
            }
        }
        // ❌ 遍历完所有起点都没找到，返回 false
        return false;
    }

    /**
     * 回溯搜索单词
     *
     * @param board   二维字符网格
     * @param word    要搜索的单词
     * @param index   当前匹配的字符索引
     * @param row     当前行索引
     * @param col     当前列索引
     * @param visited 访问标记数组
     * @return 是否能找到单词
     */
    private boolean backtrack(char[][] board, String word, int index, int row, int col, boolean[][] visited) {
        // 🎯 终止条件：匹配完所有字符
        if (index == word.length()) {
            return true;
        }

        // 📏 获取网格的行数和列数
        int rows = board.length;
        int cols = board[0].length;

        // 🔍 剪枝条件检查
        // 越界检查
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        // 字符不匹配检查
        if (board[row][col] != word.charAt(index)) {
            return false;
        }
        // 已访问检查
        if (visited[row][col]) {
            return false;
        }

        // ➕ 标记为已访问
        visited[row][col] = true;

        // 🔄 向四个方向搜索
        // 使用 || 短路求值，只要有一个方向返回 true 就继续
        boolean res = backtrack(board, word, index + 1, row - 1, col, visited)      // 上
                || backtrack(board, word, index + 1, row + 1, col, visited)      // 下
                || backtrack(board, word, index + 1, row, col - 1, visited)      // 左
                || backtrack(board, word, index + 1, row, col + 1, visited);       // 右

        // ➖ 撤销标记，回溯到上一个状态
        visited[row][col] = false;

        // ✅ 返回搜索结果
        return res;
    }
}

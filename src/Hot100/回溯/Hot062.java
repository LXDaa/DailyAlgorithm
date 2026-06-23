package Hot100.回溯;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxd
 **/
public class Hot062 {
    /**
     * <a href="https://leetcode.cn/problems/n-queens/description/">51. N皇后</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。</p>
     * <p>n皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。</p>
     * <p>给你一个整数 n，返回所有不同的 n皇后问题的解决方案数量。</p>
     *
     * <h3>💡 核心思路：回溯算法 + 对角线冲突检测</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用回溯算法，逐行放置皇后
     *     <ul>
     *       <li>按行放置，每行放一个皇后</li>
     *       <li>使用三个数组记录列、对角线冲突</li>
     *       <li>cols[col]：第 col 列是否有皇后</li>
     *       <li>diag1[row+col]：主对角线（从左上到右下）是否有皇后</li>
     *       <li>diag2[row-col+n]：副对角线（从右上到左下）是否有皇后</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用三个数组</b>：
     *     <ul>
     *       <li>快速判断当前位置是否可以放置皇后</li>
     *       <li>避免遍历整个棋盘检查冲突</li>
     *       <li>时间复杂度优化到 O(1) 判断</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n!)，n 是皇后数量</li>
     *   <li><b>空间复杂度</b>：O(n)，递归栈深度和冲突数组</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：n = 4
     *
     * 【棋盘初始化】
     *   0   1   2   3
     * 0 .   .   .   .
     * 1 .   .   .   .
     * 2 .   .   .   .
     * 3 .   .   .   .
     *
     * 【回溯搜索过程】
     *
     * backtrack(row=0)
     *   ├─ col=0: 放置 Q 在 (0,0)
     *   │   cols[0]=true, diag1[0]=true, diag2[4]=true
     *   │   backtrack(row=1)
     *   │   ├─ col=0: cols[0]=true ❌
     *   │   ├─ col=1: diag1[2]=0, diag2[4]=true ❌ (对角线冲突)
     *   │   ├─ col=2: 尝试放置 Q 在 (1,2)
     *   │   │   cols[2]=true, diag1[3]=true, diag2[3]=true
     *   │   │   backtrack(row=2)
     *   │   │   ├─ col=0: diag1[2]=true ❌
     *   │   │   ├─ col=1: diag1[3]=true ❌
     *   │   │   ├─ col=2: cols[2]=true ❌
     *   │   │   └─ col=3: diag1[5]=0, diag2[1]=0, cols[3]=0 ✅
     *   │   │       放置 Q 在 (2,3)
     *   │   │       backtrack(row=3)
     *   │   │       ├─ 所有列都冲突 ❌
     *   │   │       └─ 回溯
     *   │   │   └─ 回溯
     *   │   └─ col=3: diag1[4]=true ❌
     *   │   └─ 回溯
     *   │
     *   ├─ col=1: 放置 Q 在 (0,1)
     *   │   cols[1]=true, diag1[1]=true, diag2[3]=true
     *   │   backtrack(row=1)
     *   │   ├─ col=0: 尝试放置 Q 在 (1,0)
     *   │   │   cols[0]=true, diag1[1]=true ❌
     *   │   ├─ col=1: cols[1]=true ❌
     *   │   ├─ col=2: diag1[3]=0, diag2[-1]=3 ❌
     *   │   └─ col=3: 尝试放置 Q 在 (1,3)
     *   │       cols[3]=true, diag1[4]=true, diag2[0]=true
     *   │       backtrack(row=2)
     *   │       ├─ col=0: 尝试放置 Q 在 (2,0)
     *   │       │   cols[0]=true, diag1[2]=0, diag2[2]=0 ✅
     *   │       │   cols[0]=true, diag1[2]=true, diag2[2]=true
     *   │       │   backtrack(row=3)
     *   │       │   ├─ col=0: cols[0]=true ❌
     *   │       │   ├─ col=1: cols[1]=true ❌
     *   │       │   ├─ col=2: diag1[5]=0, diag2[1]=0, cols[2]=0 ✅
     *   │       │   │   放置 Q 在 (3,2)
     *   │       │   │   row=3 == n=4 ✅
     *   │       │   │   找到一组解！
     *   │       │   │   添加到结果集
     *   │       │   │
     *   │       │   │   棋盘状态：
     *   │       │   │   0 . Q . .
     *   │       │   │   1 . . . Q
     *   │       │   │   2 Q . . .
     *   │       │   │   3 . . Q .
     *   │       │   │
     *   │       │   └─ ...
     *   │       │
     *   │       └─ ...
     *   │
     *   └─ ...
     *
     * 【最终结果】
     *
     * res = [
     *   [".Q..", "...Q", "Q...", "..Q."],
     *   ["..Q.", "Q...", "...Q", ".Q.."]
     * ]
     *
     * 总共 2 种解决方案 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 对角线如何编码？
     *    - 主对角线（左上到右下）：row + col 相同
     *      例如：(0,0)=0, (1,1)=2, (2,2)=4
     *      范围：0 ~ 2n-2，数组大小 2n
     *    - 副对角线（右上到左下）：row - col 相同
     *      例如：(0,3)=-3, (1,3)=-2, (2,3)=-1
     *      加 n 偏移后范围：0 ~ 2n-2，数组大小 2n
     *
     * 2️⃣ 为什么按行放置？
     *    - 每行只能放一个皇后
     *    - 按行放置可以避免行冲突检查
     *    - 减少一层循环，优化效率
     *
     * 3️⃣ 递归终止条件是什么？
     *    - row == n
     *    - 表示所有行都已放置完毕
     *    - 当前棋盘状态是一个有效解
     *
     * 4️⃣ 如何判断冲突？
     *    - cols[col]：同列冲突
     *    - diag1[row+col]：主对角线冲突
     *    - diag2[row-col+n]：副对角线冲突
     *    - 任一为 true 则冲突
     *
     * 5️⃣ 回溯操作是什么？
     *    - 标记冲突：cols[col]=true, diag1[d1]=true, diag2[d2]=true
     *    - 放置皇后：board[row][col]='Q'
     *    - 递归：backtrack(row+1)
     *    - 撤销：恢复所有标记和棋盘
     *
     * 6️⃣ 棋盘如何转换为字符串？
     *    - 每个字符数组转为字符串
     *    - 例如：['.', 'Q', '.', '.'] → ".Q.."
     *    - 使用 new String(char[]) 转换
     *
     * 7️⃣ 时间复杂度如何分析？
     *    - 第一行：n 种选择
     *    - 第二行：最多 n-2 种选择
     *    - 第三行：最多 n-4 种选择
     *    - 总体复杂度：O(n!)
     *
     * 8️⃣ 完整流程总结：
     *
     *    入口函数：
     *    ├─ 初始化棋盘（全为 '.'）
     *    ├─ 初始化冲突数组 cols, diag1, diag2
     *    └─ 调用 backtrack(res, board, cols, diag1, diag2, n, 0)
     *
     *    回溯函数 backtrack(..., row)：
     *    ├─ if row == n：收集解，return
     *    ├─ for col in 0..n-1：
     *    │   ├─ d1 = row + col
     *    │   ├─ d2 = row - col + n
     *    │   ├─ if cols[col] || diag1[d1] || diag2[d2] → continue
     *    │   ├─ 标记冲突
     *    │   ├─ 放置皇后
     *    │   ├─ backtrack(..., row+1)
     *    │   └─ 撤销标记和皇后
     *    └─ return
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>对角线编码</b>：副对角线需要加 n 偏移，避免负数索引</li>
     *   <li><b>冲突数组大小</b>：cols 大小为 n，diag1 和 diag2 大小为 2n</li>
     *   <li><b>按行放置</b>：避免行冲突检查，简化逻辑</li>
     *   <li><b>回溯顺序</b>：撤销操作必须与标记操作顺序相反</li>
     *   <li><b>棋盘转换</b>：char[] 转 String 使用 new String(arr)</li>
     * </ul>
     *
     * @param n 皇后数量
     * @return 所有不同的解决方案
     */
    public List<List<String>> solveNQueens(int n) {
        // 📦 创建结果列表
        List<List<String>> res = new ArrayList<>();
        // 📋 创建棋盘，初始化为 '.'
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }

        // 🚩 创建冲突检测数组
        boolean[] cols = new boolean[n];         // 列冲突
        boolean[] diag1 = new boolean[2 * n];    // 主对角线冲突
        boolean[] diag2 = new boolean[2 * n];    // 副对角线冲突

        // 🔄 开始回溯搜索，从第 0 行开始
        backtrack(res, board, cols, diag1, diag2, n, 0);
        return res;
    }

    /**
     * 回溯搜索所有解决方案
     *
     * @param res   结果集
     * @param board 棋盘
     * @param cols  列冲突数组
     * @param diag1 主对角线冲突数组
     * @param diag2 副对角线冲突数组
     * @param n     皇后数量
     * @param row   当前处理的行
     */
    private void backtrack(List<List<String>> res, char[][] board, boolean[] cols, boolean[] diag1, boolean[] diag2, int n, int row) {
        // 🎯 终止条件：所有行都放置完毕
        if (row == n) {
            // 📝 将棋盘转换为字符串列表
            List<String> path = new ArrayList<>();
            for (char[] arr : board) {
                path.add(new String(arr));
            }
            // ✅ 添加到结果集
            res.add(path);
            return;
        }

        // 🔄 遍历所有列，尝试放置皇后
        for (int col = 0; col < n; col++) {
            // 📋 计算对角线索引
            int d1 = row + col;
            int d2 = row - col + n;

            // 🔍 检查冲突
            if (cols[col] || diag1[d1] || diag2[d2]) {
                continue;
            }

            // ➕ 标记冲突
            cols[col] = true;
            diag1[d1] = true;
            diag2[d2] = true;
            // ➕ 放置皇后
            board[row][col] = 'Q';

            // 🔄 递归处理下一行
            backtrack(res, board, cols, diag1, diag2, n, row + 1);

            // ➖ 撤销选择，回溯到上一个状态
            board[row][col] = '.';
            diag2[d2] = false;
            diag1[d1] = false;
            cols[col] = false;
        }
    }
}

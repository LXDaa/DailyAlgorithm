package Array;

/**
 * 给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
 */
public class Day20250701 {
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int startX = 0, startY = 0;
        int num = 1; //需要填充的数字
        int offest = 1;//每条边忽略的偏移量
        int loop = 1;//循环次数
        int raw, column;
        while (loop <= n / 2) {
            //顶部 行不变，列变
            for (column = startY; column < n - offest; column++) {
                res[startX][column] = num++;
            }
            //右边 列不变，行变
            for (raw = startX; raw < n - offest; raw++) {
                res[raw][column] = num++;
            }
            //底部 行不变，列变
            for (; column > startY; column--) {
                res[raw][column] = num++;
            }
            //左边 列不变，行变
            for (; raw > startX; raw--) {
                res[raw][column] = num++;
            }
            loop++;
            startX++;
            startY++;
            offest++;
        }
        if (n % 2 == 1) {
            res[startX][startY] = num;
        }
        return res;
    }
}

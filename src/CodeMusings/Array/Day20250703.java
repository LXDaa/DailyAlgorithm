package CodeMusings.Array;

import java.util.Scanner;

/**
 * 44. 开发商购买土地
 */
public class Day20250703 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int sum = 0;
        int[][] vec = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                vec[i][j] = scanner.nextInt();
                sum += vec[i][j];
            }
        }

        int result = Integer.MAX_VALUE;
        int count = 0; // 统计遍历过的行

        // 行切分
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                count += vec[i][j];
                // 遍历到行末尾时候开始统计
                if (j == m - 1) {
                    // count是前i行（包括第i行）的数据之和
                    // (sum - count) 是后i行（不包括第i行）的数据之和
                    // 两者相减得到两个子区域内土地总价值之间的差距
                    result = Math.min(result, Math.abs(sum - 2 * count));
                }
            }
        }

        count = 0; //归0
        // 列切分
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                count += vec[i][j];
                // 遍历到列末尾时候开始统计
                if (i == n - 1) {
                    result = Math.min(result, Math.abs(sum - 2 * count));
                }
            }
        }

        System.out.println(result);
        scanner.close();
    }
}

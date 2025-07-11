package Array;

import java.util.Scanner;

/**
 * 题目描述
 * <p>
 * 给定一个整数数组 Array，请计算该数组在每个指定区间内元素的总和。
 * <p>
 * 输入描述
 * <p>
 * 第一行输入为整数数组 Array 的长度 n，接下来 n 行，每行一个整数，表示数组的元素。随后的输入为需要计算总和的区间，直至文件结束。
 * <p>
 * 输出描述
 * <p>
 * 输出每个指定区间内元素的总和。
 */
public class Day20250702 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        int[] sumCacheArr = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
            sum += arr[i];
            sumCacheArr[i] = sum;
        }
        while (scanner.hasNext()) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int result = 0;
            if (a == 0) {
                result = sumCacheArr[b];
            } else {
                result = sumCacheArr[b] - sumCacheArr[a - 1];
            }
            System.out.println(result);
        }
        scanner.close();
    }

}

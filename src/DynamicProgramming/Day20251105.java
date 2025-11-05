package DynamicProgramming;

/**
 *
 * <a href="https://leetcode.cn/problems/climbing-stairs/solutions/286022/pa-lou-ti-by-leetcode-solution/">70. 爬楼梯</a>
 * <p>
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * <p>
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * <p>
 * 解题思路:
 * 这是一个经典的动态规划问题，也可以看作是斐波那契数列。
 * 对于第 n 阶楼梯，我们可以从【第 (n-1) 阶爬 1 步到达】，或【从第 (n-2) 阶爬 2 步到达】。
 * 所以到达第 n 阶的方法数 = 到达第 (n-1) 阶的方法数 + 到达第 (n-2) 阶的方法数
 * <p>
 * 递推关系: f(n) = f(n-1) + f(n-2)
 * 边界条件: f(1) = 1, f(2) = 2
 * <p>
 * 当前实现使用了空间优化的动态规划方法，只使用三个变量来保存必要的状态，
 * 避免了使用额外的数组空间，将空间复杂度从 O(n) 降低到 O(1)。
 */
public class Day20251105 {
    public int climbStairs(int n) {
        // p 表示到达第 (i-2) 阶的方法数
        // q 表示到达第 (i-1) 阶的方法数  
        // r 表示到达第 i 阶的方法数
        int p = 0, q = 0, r = 1;

        // 从第 1 阶开始计算到第 n 阶
        for (int i = 1; i <= n; ++i) {
            // 更新状态：向前滚动
            // 原来的 q 变成新的 p
            p = q;
            // 原来的 r 变成新的 q
            q = r;
            // 新的 r = p + q，即 f(i) = f(i-2) + f(i-1)
            r = p + q;
        }
        return r;
    }
}
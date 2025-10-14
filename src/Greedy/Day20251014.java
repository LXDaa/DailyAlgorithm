package Greedy;

import java.util.Arrays;

/**
 * <a href="https://leetcode.cn/problems/assign-cookies/description/">455.分发饼干</a>
 * <p>
 * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干
 * <p>
 * 对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，都有一个尺寸 s[j]
 * <p>
 * 如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。你的目标是满足尽可能多的孩子，并输出这个最大数值。
 * <p>
 * 解题思路：
 * 使用贪心算法，优先用大饼干满足胃口大的孩子，这样能够最大化满足孩子的数量。
 * 1. 首先对孩子胃口和饼干尺寸进行排序
 * 2. 从胃口最大的孩子开始，用最大的可用饼干去满足
 * 3. 如果能满足则分配，否则继续尝试满足胃口更小的孩子
 */
public class Day20251014 {
    public int findContentChildren(int[] g, int[] s) {
        // 对孩子胃口和饼干尺寸进行升序排序
        Arrays.sort(g);
        Arrays.sort(s);
        
        // 从胃口最大的孩子开始遍历
        int childIndex = g.length - 1;
        int cookieIndex = s.length - 1;
        int satisfiedChildren = 0;
        
        // 贪心策略：优先用大饼干满足胃口大的孩子
        for (; childIndex >= 0; childIndex--) {
            // 如果还有可用饼干且当前饼干能满足当前孩子
            if (cookieIndex >= 0 && g[childIndex] <= s[cookieIndex]) {
                satisfiedChildren++;
                cookieIndex--; // 使用掉当前最大的饼干
            }
        }
        return satisfiedChildren;
    }
}
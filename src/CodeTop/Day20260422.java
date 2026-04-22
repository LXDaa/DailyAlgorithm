package CodeTop;

import java.util.HashSet;
import java.util.Set;

/**
 * <a href="https://leetcode.cn/problems/longest-consecutive-sequence/description/">128. 最长连续序列</a>
 *
 * <h3>🎯 题目描述</h3>
 * <p>给定一个未排序的整数数组 nums，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。</p>
 * <p><b>要求</b>：请你设计并实现时间复杂度为 O(n) 的算法解决此问题。</p>
 *
 * <h3>💡 核心思路：哈希集合 + 智能起点</h3>
 * <ul>
 *   <li><b>问题挑战</b>：O(n) 时间复杂度要求，不能排序（排序需要 O(n log n)）</li>
 *   <li><b>解决方案</b>：使用 HashSet 实现 O(1) 查找，只从连续序列的起点开始扩展</li>
 *   <li><b>关键思想</b>：
 *     <ul>
 *       <li>将所有数字放入 HashSet，实现 O(1) 查找</li>
 *       <li>对于每个数字 num，只有当 num-1 不存在时，num 才是某个连续序列的起点</li>
 *       <li>从起点开始，不断检查 num+1, num+2, ... 是否存在，统计连续长度</li>
 *     </ul>
 *   </li>
 *   <li><b>为什么是 O(n)</b>：每个数字最多被访问两次（一次在外层循环，一次在 while 循环）</li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>构建集合</b>：将所有数字加入 HashSet，去重并支持 O(1) 查找</li>
 *   <li><b>遍历集合</b>：对于每个数字 num：
 *     <ul>
 *       <li>如果 num-1 存在于集合中，说明 num 不是起点，跳过</li>
 *       <li>如果 num-1 不存在，说明 num 是起点，开始扩展：</li>
 *       <ul>
 *         <li>初始化 currentNum = num, currentStreak = 1</li>
 *         <li>while (numSet.contains(currentNum + 1))：不断向后扩展</li>
 *         <li>更新 maxLen = max(maxLen, currentStreak)</li>
 *       </ul>
 *     </ul>
 *   </li>
 *   <li><b>返回结果</b>：maxLen 即为最长连续序列的长度</li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例：nums = [100, 4, 200, 1, 3, 2]
 *
 * 【Step 1】构建 HashSet
 * numSet = {1, 2, 3, 4, 100, 200}
 *
 * 【Step 2】遍历集合，寻找连续序列
 *
 * 1️⃣ num = 100
 *    检查: 99 是否在集合中? NO ✅ → 100 是起点
 *    扩展:
 *      - 101 在集合中? NO → 停止
 *    currentStreak = 1
 *    maxLen = max(0, 1) = 1
 *
 * 2️⃣ num = 4
 *    检查: 3 是否在集合中? YES ❌ → 4 不是起点，跳过
 *
 * 3️⃣ num = 200
 *    检查: 199 是否在集合中? NO ✅ → 200 是起点
 *    扩展:
 *      - 201 在集合中? NO → 停止
 *    currentStreak = 1
 *    maxLen = max(1, 1) = 1
 *
 * 4️⃣ num = 1
 *    检查: 0 是否在集合中? NO ✅ → 1 是起点
 *    扩展:
 *      - 2 在集合中? YES → currentNum=2, streak=2
 *      - 3 在集合中? YES → currentNum=3, streak=3
 *      - 4 在集合中? YES → currentNum=4, streak=4
 *      - 5 在集合中? NO → 停止
 *    currentStreak = 4
 *    maxLen = max(1, 4) = 4 ✅
 *
 * 5️⃣ num = 3
 *    检查: 2 是否在集合中? YES ❌ → 3 不是起点，跳过
 *
 * 6️⃣ num = 2
 *    检查: 1 是否在集合中? YES ❌ → 2 不是起点，跳过
 *
 * 【最终结果】maxLen = 4
 * 最长连续序列: [1, 2, 3, 4] ✅
 *
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 *
 * 关键观察：
 * - 只有 100, 200, 1 这三个数字触发了 while 循环
 * - 4, 3, 2 都被跳过了，因为它们不是起点
 * - 每个数字最多被访问两次：
 *   · 外层 for 循环访问一次
 *   · 如果是起点，在 while 循环中再访问一次
 * - 总时间复杂度: O(n) ✅
 * </pre>
 *
 * <h3>🔑 关键技巧解析</h3>
 * <pre>
 * 1️⃣ 为什么只从起点开始扩展？
 *    - 避免重复计算：如果从 2 开始扩展，会找到 [2,3,4]
 *    - 但从 1 开始扩展，会找到 [1,2,3,4]，更长
 *    - 只从起点开始，保证每个连续序列只被计算一次
 *
 * 2️⃣ 如何判断一个数字是起点？
 *    - 如果 num-1 不在集合中，说明 num 是某个连续序列的第一个数
 *    - 例如：num=1, 检查 0 是否在集合中
 *    - 如果 0 不在，1 就是起点
 *
 * 3️⃣ 为什么时间复杂度是 O(n)？
 *    - 外层 for 循环：遍历 n 个数字
 *    - 内层 while 循环：看似嵌套，但每个数字最多进入一次 while
 *    - 原因：只有起点才会进入 while，非起点直接跳过
 *    - 例如：[1,2,3,4] 中，只有 1 会进入 while，2,3,4 都跳过
 *    - 总体：每个数字最多被访问 2 次 → O(2n) = O(n)
 *
 * 4️⃣ 与排序法的对比？
 *    - 排序法：O(n log n)，简单直观
 *    - HashSet 法：O(n)，满足题目要求
 *    - 空间换时间：多用 O(n) 空间，换取 O(n) 时间
 *
 * 5️⃣ 空间复杂度：O(n)
 *    - HashSet 存储所有数字
 *
 * 6️⃣ 去重的作用？
 *    - HashSet 自动去重
 *    - 例如：[1,1,2,2,3] → {1,2,3}
 *    - 不影响连续序列的长度计算
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>起点判断</b>：必须检查 num-1 是否存在，这是保证 O(n) 的关键</li>
 *   <li><b>HashSet 选择</b>：使用 HashSet 而非 HashMap，只需存储数字本身</li>
 *   <li><b>空数组处理</b>：如果 nums 为空，返回 0</li>
 *   <li><b>Integer 溢出</b>：currentNum+1 可能溢出，但题目数据范围通常不会</li>
 *   <li><b>遍历顺序</b>：遍历 numSet 而非 nums，避免重复处理相同数字</li>
 *   <li><b>初始化</b>：maxLen 初始化为 0，处理空数组情况</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260422 {
    /**
     * 📏 计算最长连续序列的长度
     *
     * @param nums 未排序的整数数组
     * @return 最长连续序列的长度
     */
    public int longestConsecutive(int[] nums) {
        // 🗺️ Step 1: 将所有数字加入 HashSet，去重并支持 O(1) 查找
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int maxLen = 0;  // 📊 记录最长连续序列长度

        // 🔍 Step 2: 遍历集合，只从连续序列的起点开始扩展
        for (int num : numSet) {
            // ✅ 判断是否为起点：num-1 不存在于集合中
            if (!numSet.contains(num - 1)) {
                int currentNum = num;       // 📍 当前数字
                int currentStreak = 1;      // 🔢 当前连续序列长度

                // 🔄 向后扩展：检查 currentNum+1, currentNum+2, ...
                while (numSet.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                // 📈 更新最大长度
                maxLen = Math.max(maxLen, currentStreak);
            }
            // ❌ 如果 num-1 存在，说明 num 不是起点，跳过
        }

        return maxLen;
    }
}

package Hot100.哈希;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lxd
 **/
public class Hot003 {
    /**
     * <a href="https://leetcode.cn/problems/longest-consecutive-sequence/description/">128. 最长连续序列</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。</p>
     * <p>请你设计并实现时间复杂度为 O(n) 的算法解决此问题。</p>
     *
     * <h3>💡 核心思路：哈希集合 + 智能起点</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用 HashSet 存储所有数字，只从连续序列的起点开始扩展
     *     <ul>
     *       <li>如果 num-1 存在，说明 num 不是起点，跳过</li>
     *       <li>如果 num-1 不存在，说明 num 是起点，向后查找连续序列</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个数字最多被访问两次</li>
     *   <li><b>空间复杂度</b>：O(n)，HashSet 存储所有数字</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [100, 4, 200, 1, 3, 2]
     *
     * 第一步：将所有数字加入 HashSet
     * set = {100, 4, 200, 1, 3, 2}
     *
     * 第二步：遍历 set 中的每个数字
     *
     * 【num=100】
     *   - 检查 99 是否在 set 中？否 ✅ 是起点
     *   - 向后查找：101, 102, ... 都不在 set 中
     *   - 当前序列长度：1
     *   - maxLen = 1
     *
     * 【num=4】
     *   - 检查 3 是否在 set 中？是 ❌ 不是起点，跳过
     *
     * 【num=200】
     *   - 检查 199 是否在 set 中？否 ✅ 是起点
     *   - 向后查找：201, 202, ... 都不在 set 中
     *   - 当前序列长度：1
     *   - maxLen = 1
     *
     * 【num=1】
     *   - 检查 0 是否在 set 中？否 ✅ 是起点
     *   - 向后查找：
     *     - 2 在 set 中 ✓ currentStreak = 2
     *     - 3 在 set 中 ✓ currentStreak = 3
     *     - 4 在 set 中 ✓ currentStreak = 4
     *     - 5 不在 set 中，停止
     *   - 当前序列长度：4
     *   - maxLen = 4
     *
     * 【num=3】
     *   - 检查 2 是否在 set 中？是 ❌ 不是起点，跳过
     *
     * 【num=2】
     *   - 检查 1 是否在 set 中？是 ❌ 不是起点，跳过
     *
     * 最终结果：4 ✅ （序列 [1, 2, 3, 4]）
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么只从起点开始？
     *    - 避免重复计算同一个序列
     *    - 例如：[1,2,3,4] 只从 1 开始计算，2、3、4 都跳过
     *    - 保证每个序列只被计算一次
     *
     * 2️⃣ 如何判断起点？
     *    - 如果 num-1 不在 set 中，num 就是起点
     *    - 这是算法的核心优化点
     *
     * 3️⃣ 为什么时间复杂度是 O(n)？
     *    - 外层循环：遍历 n 个数字
     *    - 内层 while：每个数字最多被访问两次
     *      - 一次作为非起点被跳过
     *      - 一次作为序列的一部分被访问
     *    - 总体：O(n) + O(n) = O(n)
     *
     * 4️⃣ HashSet 的优势？
     *    - contains 操作是 O(1)
     *    - 快速判断数字是否存在
     *
     * 5️⃣ 空间换时间：
     *    - 用 O(n) 空间存储 HashSet
     *    - 换取 O(n) 的时间复杂度
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>O(n) 要求</b>：题目明确要求时间复杂度为 O(n)</li>
     *   <li><b>去重处理</b>：HashSet 自动去重，避免重复计算</li>
     *   <li><b>起点判断</b>：只有 num-1 不存在时才开始计算，这是关键优化</li>
     *   <li><b>边界情况</b>：空数组返回 0，单元素数组返回 1</li>
     * </ul>
     *
     * @param nums 未排序的整数数组
     * @return 最长连续序列的长度
     */
    public int longestConsecutive(int[] nums) {
        // 📋 创建 HashSet 存储所有数字
        Set<Integer> set = new HashSet();
        int maxLen = 0;

        // 🔄 将所有数字加入 HashSet
        for (int num : nums) {
            set.add(num);
        }

        // 🔍 遍历 set 中的每个数字
        for (int num : set) {
            // ✅ 只从连续序列的起点开始（num-1 不存在）
            if (!set.contains(num - 1)) {
                int currentStreak = 1;
                int currentNum = num;

                // ➕ 向后查找连续的数字
                while (set.contains(currentNum + 1)) {
                    currentStreak += 1;
                    currentNum += 1;
                }

                // 📊 更新最大长度
                maxLen = Math.max(maxLen, currentStreak);
            }
        }

        return maxLen;
    }
}


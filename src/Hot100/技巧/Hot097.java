package Hot100.技巧;

/**
 * @author lxd
 **/
public class Hot097 {
    /**
     * <a href="https://leetcode.cn/problems/majority-element/description/">169. 多数元素</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个大小为 n 的数组 nums，返回其中的多数元素。多数元素是指在数组中出现次数大于 ⌊n/2⌋ 的元素。</p>
     * <p>你可以假设数组是非空的，并且给定的数组总是存在多数元素。</p>
     *
     * <h3>💡 核心思路：Boyer-Moore 投票算法</h3>
     * <ul>
     *   <li><b>基本思想</b>：利用投票机制找到多数元素
     *     <ul>
     *       <li>初始化 count = 0，res = 0</li>
     *       <li>遍历数组中的每个元素 num</li>
     *       <li>如果 count == 0，说明当前没有候选元素，将 res 设为 num</li>
     *       <li>如果 num == res，count++（支持当前候选）</li>
     *       <li>如果 num != res，count--（反对当前候选）</li>
     *       <li>最终 res 就是多数元素</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么这个算法有效</b>：
     *     <ul>
     *       <li>多数元素出现次数 > n/2</li>
     *       <li>其他元素的总次数 < n/2</li>
     *       <li>即使所有其他元素都反对，多数元素仍能获得正票数</li>
     *       <li>最终剩下的候选元素一定是多数元素</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，遍历一次数组</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常量空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [3, 2, 3]
     *
     * 初始化：count = 0, res = 0
     *
     * num = 3：
     *   count == 0 → res = 3
     *   count += (3 == 3) ? 1 : -1 → count = 1
     *   → count = 1, res = 3
     *
     * num = 2：
     *   count != 0
     *   count += (2 == 3) ? 1 : -1 → count = 0
     *   → count = 0, res = 3
     *
     * num = 3：
     *   count == 0 → res = 3
     *   count += (3 == 3) ? 1 : -1 → count = 1
     *   → count = 1, res = 3
     *
     * 结果：res = 3 ✅
     * 多数元素是 3，出现次数 2 > 3/2 = 1.5
     *
     * 示例2：nums = [2, 2, 1, 1, 1, 2, 2]
     *
     * count=0, res=0
     * num=2: count==0 → res=2, count=1
     * num=2: count++, count=2
     * num=1: count--, count=1
     * num=1: count--, count=0
     * num=1: count==0 → res=1, count=1
     * num=2: count--, count=0
     * num=2: count==0 → res=2, count=1
     *
     * 结果：res = 2 ✅
     * 多数元素是 2，出现次数 4 > 7/2 = 3.5
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ Boyer-Moore 投票算法的原理是什么？
     *    - 利用投票机制：相同元素投票+1，不同元素投票-1
     *    - 当票数为 0 时，更换候选元素
     *    - 多数元素出现次数 > n/2，总能获得正票数
     *    - 最终剩下的候选元素就是多数元素
     *
     * 2️⃣ 为什么 count == 0 时更换候选？
     *    - count == 0 表示当前候选元素的票数已经被完全抵消
     *    - 需要选择新的候选元素继续投票
     *    - 这是算法的核心决策点
     *    - 保证了多数元素最终能胜出
     *
     * 3️⃣ 为什么不需要额外空间？
     *    - 只使用两个变量：count 和 res
     *    - count 记录当前候选的票数
     *    - res 记录当前候选元素
     *    - 不需要哈希表等额外数据结构
     *    - 空间复杂度：O(1)
     *
     * 4️⃣ 如果数组中没有多数元素怎么办？
     *    - 题目保证数组中一定存在多数元素
     *    - 如果没有保证，需要额外验证步骤
     *    - 遍历数组统计 res 的出现次数，确认是否 > n/2
     *
     * 5️⃣ 如何处理数组长度为 1 的情况？
     *    - 当数组只有一个元素时：count==0 → res = nums[0]
     *    - 直接返回该元素，符合预期
     *    - 不需要特殊处理
     *
     * 6️⃣ 为什么时间复杂度是 O(n)？
     *    - 只遍历一次数组
     *    - 每次迭代只进行简单的判断和计数（O(1)）
     *    - 总时间：O(n)
     *
     * 7️⃣ 算法的正确性如何保证？
     *    - 多数元素出现次数 > n/2
     *    - 其他元素总次数 < n/2
     *    - 即使所有其他元素都反对，多数元素仍有剩余票数
     *    - 例如：5个元素，多数元素出现3次，其他2次，3-2=1>0
     *
     * 8️⃣ 完整流程总结：
     *
     *    count = 0, res = 0
     *
     *    for num in nums：
     *      if count == 0：
     *        res = num
     *      count += (num == res) ? 1 : -1
     *
     *    return res
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>初始值</b>：count 初始化为 0，res 初始化为 0</li>
     *   <li><b>更换候选</b>：count == 0 时更换候选元素</li>
     *   <li><b>投票逻辑</b>：相同元素 +1，不同元素 -1</li>
     *   <li><b>题目保证</b>：数组中一定存在多数元素</li>
     *   <li><b>时间空间</b>：O(n) 时间，O(1) 空间</li>
     * </ul>
     *
     * @param nums 大小为 n 的数组，其中存在多数元素
     * @return 多数元素（出现次数大于 ⌊n/2⌋ 的元素）
     */
    public int majorityElement(int[] nums) {
        // 📊 初始化：count 记录当前候选的票数，res 记录当前候选元素
        int count = 0, res = 0;

        // 🔄 遍历数组中的每个元素
        for (int num : nums) {
            // 📌 如果票数为 0，更换候选元素
            if (count == 0) {
                res = num;
            }

            // 📊 投票：相同元素 +1，不同元素 -1
            count += (num == res) ? 1 : -1;
        }

        // ✅ 返回多数元素
        return res;
    }
}

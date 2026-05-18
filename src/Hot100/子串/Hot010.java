package Hot100.子串;

import java.util.HashMap;

/**
 * @author lxd
 **/
public class Hot010 {
    /**
     * <a href="https://leetcode.cn/problems/subarray-sum-equals-k/description/">560. 和为 K 的子数组</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个整数数组 nums 和一个整数 k ，请你统计并返回该数组中和为 k 的连续子数组的个数。</p>
     *
     * <h3>💡 核心思路：前缀和 + 哈希表</h3>
     * <ul>
     *   <li><b>基本思想</b>：利用前缀和的性质，如果 pre[j] - pre[i] = k，则说明从 i+1 到 j 的子数组和为 k
     *     <ul>
     *       <li>pre 表示从数组开头到当前位置的累加和（前缀和）</li>
     *       <li>用哈希表记录每个前缀和出现的次数</li>
     *       <li>对于当前位置的前缀和 pre，查找 pre - k 是否出现过</li>
     *       <li>如果出现过，说明存在子数组和为 k，累加其出现次数</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，只需一次遍历</li>
     *   <li><b>空间复杂度</b>：O(n)，哈希表最多存储 n 个前缀和</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [1, 1, 1], k = 2
     *
     * 初始化：pre = 0, count = 0, mp = {0: 1}
     *
     * 【i=0】nums[0] = 1
     *   - pre = 0 + 1 = 1
     *   - 查找 pre - k = 1 - 2 = -1
     *   - mp 中没有 -1
     *   - 存入：mp = {0: 1, 1: 1}
     *
     * 【i=1】nums[1] = 1
     *   - pre = 1 + 1 = 2
     *   - 查找 pre - k = 2 - 2 = 0
     *   - mp 中有 0，出现 1 次 ✅
     *   - count += 1 → count = 1
     *   - 存入：mp = {0: 1, 1: 1, 2: 1}
     *   - 找到子数组：[1, 1]（索引 0-1）
     *
     * 【i=2】nums[2] = 1
     *   - pre = 2 + 1 = 3
     *   - 查找 pre - k = 3 - 2 = 1
     *   - mp 中有 1，出现 1 次 ✅
     *   - count += 1 → count = 2
     *   - 存入：mp = {0: 1, 1: 1, 2: 1, 3: 1}
     *   - 找到子数组：[1, 1]（索引 1-2）
     *
     * 最终结果：count = 2 ✅
     *
     * 图示：
     *   数组：  [1, 1, 1]
     *   前缀和：[1, 2, 3]
     *
     *   子数组 [1, 1]（索引 0-1）：pre[1] - pre[-1] = 2 - 0 = 2 ✅
     *   子数组 [1, 1]（索引 1-2）：pre[2] - pre[0] = 3 - 1 = 2 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用前缀和？
     *    - 子数组和可以用两个前缀和的差表示
     *    - sum(i, j) = pre[j] - pre[i-1]
     *    - 要找 sum(i, j) = k，即找 pre[j] - pre[i-1] = k
     *    - 变形为：pre[i-1] = pre[j] - k
     *
     * 2️⃣ 为什么要初始化 mp.put(0, 1)？
     *    - 处理从数组开头开始的子数组
     *    - 例如：pre[j] = k，需要 pre[i-1] = 0
     *    - 此时 i-1 = -1，表示空前缀，和为 0
     *    - 所以预先放入 {0: 1}
     *
     * 3️⃣ 哈希表的作用？
     *    - key：前缀和的值
     *    - value：该前缀和出现的次数
     *    - 快速查找 pre - k 是否存在
     *
     * 4️⃣ 为什么是累加次数而不是 +1？
     *    - 同一个前缀和可能出现多次
     *    - 每次出现都对应一个不同的子数组
     *    - 例如：pre = 5 出现 3 次，pre - k = 5 也出现 3 次
     *      → 有 3 个子数组和为 k
     *
     * 5️⃣ 时间复杂度：O(n)
     *    - 只需一次遍历数组
     *    - 每次哈希表操作是 O(1)
     *    - 比暴力法 O(n²) 更优
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>初始化</b>：必须预先放入 {0: 1}，处理从开头开始的子数组</li>
     *   <li><b>顺序</b>：先查找再存入，避免使用当前元素自己</li>
     *   <li><b>累加次数</b>：count += mp.get(pre - k)，不是 count++</li>
     *   <li><b>负数处理</b>：前缀和可能为负数，哈希表可以正常处理</li>
     * </ul>
     *
     * @param nums 整数数组
     * @param k    目标和
     * @return 和为 k 的连续子数组个数
     */
    public int subarraySum(int[] nums, int k) {
        int count = 0, pre = 0;

        // 📋 创建哈希表：key=前缀和，value=出现次数
        HashMap<Integer, Integer> mp = new HashMap<>();

        // 🔑 初始化：前缀和为 0 出现 1 次（处理从开头开始的子数组）
        mp.put(0, 1);

        // 🔄 遍历数组，计算前缀和
        for (int i = 0; i < nums.length; i++) {
            // ➕ 累加前缀和
            pre += nums[i];

            // 🔍 查找是否存在前缀和为 pre - k 的位置
            if (mp.containsKey(pre - k)) {
                // ✅ 找到符合条件的子数组，累加出现次数
                count += mp.get(pre - k);
            }

            // 📝 将当前前缀和存入哈希表（或更新出现次数）
            mp.put(pre, mp.getOrDefault(pre, 0) + 1);
        }

        return count;
    }
}


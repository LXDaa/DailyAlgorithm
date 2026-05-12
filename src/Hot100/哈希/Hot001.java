package Hot100.哈希;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxd
 **/
public class Hot001 {
    /**
     * <a href="https://leetcode.cn/problems/two-sum/description/">1. 两数之和</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 <b>和为目标值</b> target 的那 <b>两个</b> 整数，并返回它们的数组下标。</p>
     * <p>你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。</p>
     *
     * <h3>💡 核心思路：哈希表法</h3>
     * <ul>
     *   <li><b>基本思想</b>：遍历数组时，对于每个元素 nums[i]，检查 target - nums[i] 是否已经在哈希表中
     *     <ul>
     *       <li>如果存在，说明找到了两个数，返回它们的下标</li>
     *       <li>如果不存在，将当前元素及其下标存入哈希表</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，只需一次遍历</li>
     *   <li><b>空间复杂度</b>：O(n)，哈希表最多存储 n 个元素</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [2, 7, 11, 15], target = 9
     *
     * 初始化：map = {}
     *
     * 【i=0】nums[0] = 2
     *   - complement = 9 - 2 = 7
     *   - map 中没有 7
     *   - 存入：map = {2: 0}
     *
     * 【i=1】nums[1] = 7
     *   - complement = 9 - 7 = 2
     *   - map 中有 2，下标为 0 ✅
     *   - 返回 [1, 0]
     *
     * 最终结果：[1, 0] ✅
     *
     * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
     *
     * 示例2：nums = [3, 2, 4], target = 6
     *
     * 初始化：map = {}
     *
     * 【i=0】nums[0] = 3
     *   - complement = 6 - 3 = 3
     *   - map 中没有 3
     *   - 存入：map = {3: 0}
     *
     * 【i=1】nums[1] = 2
     *   - complement = 6 - 2 = 4
     *   - map 中没有 4
     *   - 存入：map = {3: 0, 2: 1}
     *
     * 【i=2】nums[2] = 4
     *   - complement = 6 - 4 = 2
     *   - map 中有 2，下标为 1 ✅
     *   - 返回 [2, 1]
     *
     * 最终结果：[2, 1] ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用哈希表？
     *    - 将查找 complement 的时间从 O(n) 降到 O(1)
     *    - 总体时间复杂度从 O(n²) 优化到 O(n)
     *
     * 2️⃣ 为什么先查找再存入？
     *    - 避免使用同一个元素两次
     *    - 例如：target = 6, nums = [3, ...]
     *      - 如果先存入 3，再查找 6-3=3，会找到自己
     *      - 先查找再存入，确保不会重复使用
     *
     * 3️⃣ 哈希表的 key-value 设计？
     *    - key：数值（用于快速查找 complement）
     *    - value：下标（用于返回结果）
     *
     * 4️⃣ 时间复杂度：O(n)
     *    - 只需一次遍历数组
     *    - 每次哈希表操作是 O(1)
     *
     * 5️⃣ 空间复杂度：O(n)
     *    - 哈希表最多存储 n 个元素
     *    - 空间换时间的经典案例
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>唯一解</b>：题目保证每种输入只会对应一个答案</li>
     *   <li><b>不能重复</b>：不能使用同一个元素两次</li>
     *   <li><b>返回值</b>：返回的是下标数组，不是数值数组</li>
     *   <li><b>顺序无关</b>：返回的两个下标顺序可以任意</li>
     * </ul>
     *
     * @param nums   整数数组
     * @param target 目标和
     * @return 两个数的下标数组
     */
    public int[] twoSum(int[] nums, int target) {
        // 📋 创建哈希表：key=数值，value=下标
        Map<Integer, Integer> map = new HashMap();

        // 🔄 遍历数组
        for (int i = 0; i < nums.length; i++) {
            // 🔍 检查 complement（补数）是否已在哈希表中
            if (map.containsKey(target - nums[i])) {
                // ✅ 找到答案，返回当前下标和补数的下标
                return new int[]{i, map.get(target - nums[i])};
            }
            // 📝 将当前数值和下标存入哈希表
            map.put(nums[i], i);
        }

        // ❌ 理论上不会执行到这里（题目保证有解）
        return null;
    }
}

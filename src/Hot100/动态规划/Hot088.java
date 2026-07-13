package Hot100.动态规划;

/**
 * @author lxd
 **/
public class Hot088 {
    /**
     * <a href="https://leetcode.cn/problems/maximum-product-subarray/description/">152. 乘积最大子数组</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。</p>
     * <p>测试用例的答案是一个 32-位 整数。</p>
     * <p>子数组是数组的连续部分。</p>
     *
     * <h3>💡 核心思路：动态规划（同时维护最大和最小值）</h3>
     * <ul>
     *   <li><b>基本思想</b>：由于负数的存在，需要同时维护当前最大和当前最小值
     *     <ul>
     *       <li>curMax：以当前元素结尾的最大乘积</li>
     *       <li>curMin：以当前元素结尾的最小乘积</li>
     *       <li>递推公式：curMax = max(max(curMin*nums[i], curMax*nums[i]), nums[i])</li>
     *       <li>递推公式：curMin = min(min(curMin*nums[i], curMax*nums[i]), nums[i])</li>
     *       <li>初始状态：curMax = curMin = max = nums[0]</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么需要同时维护最大和最小</b>：
     *     <ul>
     *       <li>负数 × 负数 = 正数，可能成为新的最大值</li>
     *       <li>当前最小值（负数）× 负数 = 可能成为最大值</li>
     *       <li>必须同时跟踪两者才能得到正确结果</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，遍历一次数组</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常量空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [2, -3, -2, 4]
     *
     * 初始化：max = 2, curMax = 2, curMin = 2
     *
     * i=1, nums[i]=-3：
     *   tMax = 2, tMin = 2
     *   curMax = max(max(2*-3, 2*-3), -3) = max(max(-6, -6), -3) = max(-6, -3) = -3
     *   curMin = min(min(2*-3, 2*-3), -3) = min(min(-6, -6), -3) = min(-6, -3) = -6
     *   max = max(2, -3) = 2
     *   → max=2, curMax=-3, curMin=-6
     *
     * i=2, nums[i]=-2：
     *   tMax = -3, tMin = -6
     *   curMax = max(max(-6*-2, -3*-2), -2) = max(max(12, 6), -2) = max(12, -2) = 12
     *   curMin = min(min(-6*-2, -3*-2), -2) = min(min(12, 6), -2) = min(6, -2) = -2
     *   max = max(2, 12) = 12
     *   → max=12, curMax=12, curMin=-2
     *
     * i=3, nums[i]=4：
     *   tMax = 12, tMin = -2
     *   curMax = max(max(-2*4, 12*4), 4) = max(max(-8, 48), 4) = max(48, 4) = 48
     *   curMin = min(min(-2*4, 12*4), 4) = min(min(-8, 48), 4) = min(-8, 4) = -8
     *   max = max(12, 48) = 48
     *   → max=48, curMax=48, curMin=-8
     *
     * 结果：max = 48 ✅
     * 最大乘积子数组：[-2, 4] 或 [2, -3, -2, 4]，乘积 = 48
     *
     * 示例2：nums = [-2, 0, -1]
     *   max = max(-2, 0, -1) = 0 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么需要维护 curMin？
     *    - 负数 × 负数 = 正数，可能成为新的最大值
     *    - 当前最小值（负数）× 负数 = 可能成为最大值
     *    - 例如：nums = [-2, 3, -4]，curMin=-2 × -4=8 是最大值
     *
     * 2️⃣ 为什么要保存 tMax 和 tMin？
     *    - 计算 curMax 时会用到 curMin 和 curMax 的原值
     *    - 如果先更新 curMax，再更新 curMin，会使用更新后的 curMax
     *    - 所以需要先保存原值，再计算新值
     *
     * 3️⃣ 递推公式中的三个候选值是什么？
     *    - curMin * nums[i]：负数 × 负数可能得到最大值
     *    - curMax * nums[i]：正数 × 正数可能得到最大值
     *    - nums[i]：从当前元素重新开始（放弃前面的子数组）
     *    - 取三者的最大值作为新的 curMax
     *
     * 4️⃣ 初始状态如何设置？
     *    - max = curMax = curMin = nums[0]
     *    - 第一个元素的最大和最小乘积都是它本身
     *    - 从第二个元素开始遍历
     *
     * 5️⃣ 为什么可以用 O(1) 空间？
     *    - 只需要保存当前的 max、curMax、curMin
     *    - 不需要保存整个 dp 数组
     *    - 优化了空间复杂度
     *
     * 6️⃣ 如何处理 0？
     *    - 0 × 任何数 = 0
     *    - 当遇到 0 时，curMax 和 curMin 都会重新计算
     *    - 如果前面是负数，遇到 0 后会从 0 开始
     *    - 例如：nums = [-2, 0, -1]，max = 0
     *
     * 7️⃣ 时间复杂度分析：
     *    - 只遍历一次数组
     *    - 每次迭代 O(1) 操作
     *    - 总时间 O(n)
     *
     * 8️⃣ 完整流程总结：
     *
     *    max = curMax = curMin = nums[0]
     *
     *    for i in 1..n-1：
     *      tMax = curMax, tMin = curMin
     *      curMax = max(max(tMin*nums[i], tMax*nums[i]), nums[i])
     *      curMin = min(min(tMin*nums[i], tMax*nums[i]), nums[i])
     *      max = max(max, curMax)
     *
     *    return max
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>同时维护</b>：必须同时维护 curMax 和 curMin</li>
     *   <li><b>保存原值</b>：更新前先保存 tMax 和 tMin</li>
     *   <li><b>三个候选</b>：curMax 取三者最大值（min*num, max*num, num）</li>
     *   <li><b>初始状态</b>：max = curMax = curMin = nums[0]</li>
     *   <li><b>负数处理</b>：负数 × 负数可能成为最大值</li>
     * </ul>
     *
     * @param nums 整数数组
     * @return 乘积最大的非空连续子数组的乘积
     */
    public int maxProduct(int[] nums) {
        // 📊 全局最大乘积、当前最大乘积、当前最小乘积
        int max = nums[0], curMax = nums[0], curMin = nums[0];

        // 🔄 从第二个元素开始遍历
        for (int i = 1; i < nums.length; i++) {
            // 📋 保存当前最大值和最小值（用于计算新值）
            int tMax = curMax, tMin = curMin;

            // 📊 更新当前最大乘积：取三者最大值
            curMax = Math.max(Math.max(tMin * nums[i], tMax * nums[i]), nums[i]);

            // 📊 更新当前最小乘积：取三者最小值
            curMin = Math.min(Math.min(tMin * nums[i], tMax * nums[i]), nums[i]);

            // 📊 更新全局最大乘积
            max = Math.max(max, curMax);
        }

        // ✅ 返回全局最大乘积
        return max;
    }
}

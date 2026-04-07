package CodeTop;

/**
 *
 * <a href="https://leetcode.cn/problems/first-missing-positive/description/">41. 缺失的第一个正数</a>
 *
 *
 * <h3>🎯 题目描述</h3>
 * <p>给你一个未排序的整数数组 nums，请你找出其中没有出现的最小的正整数。</p>
 * <p>要求：时间复杂度 O(n)，空间复杂度 O(1)（常数级别额外空间）。</p>
 *
 * <h3>💡 核心思路：原地哈希（Cyclic Sort）</h3>
 * <ul>
 *   <li><b>关键观察</b>：对于长度为 n 的数组，缺失的第一个正数一定在 [1, n+1] 范围内</li>
 *   <li><b>理想状态</b>：如果数组包含 1~n 的所有正数，则 nums[i] 应该等于 i+1</li>
 *   <li><b>策略</b>：将每个正数放到它应该在的位置（nums[i] 放到索引 nums[i]-1 处）</li>
 *   <li><b>扫描</b>：遍历数组，第一个不满足 nums[i] == i+1 的位置即为答案</li>
 * </ul>
 *
 * <h3>🔄 算法步骤</h3>
 * <ol>
 *   <li><b>放置阶段</b>：遍历数组，将每个在 [1,n] 范围内的数放到正确位置</li>
 *   <li><b>查找阶段</b>：从左到右扫描，找到第一个 nums[i] != i+1 的位置</li>
 *   <li><b>边界处理</b>：如果所有位置都正确，返回 n+1</li>
 * </ol>
 *
 * <h3>📊 ASCII 演示过程</h3>
 * <pre>
 * 示例：nums = [3, 4, -1, 1]
 *
 * 【初始状态】
 * 索引:    0   1   2   3
 * 数组:   [3,  4, -1,  1]
 *
 * 【第1步】i=0, nums[0]=3, 应放到索引2
 * 交换 nums[0] 和 nums[2]:
 * 索引:    0   1   2   3
 * 数组:   [-1, 4,  3,  1]
 * nums[0]=-1 不在[1,4]范围，跳过
 *
 * 【第2步】i=1, nums[1]=4, 应放到索引3
 * 交换 nums[1] 和 nums[3]:
 * 索引:    0   1   2   3
 * 数组:   [-1, 1,  3,  4]
 * nums[1]=1, 应放到索引0
 * 交换 nums[1] 和 nums[0]:
 * 索引:    0   1   2   3
 * 数组:   [1, -1,  3,  4]
 * nums[1]=-1 不在[1,4]范围，跳过
 *
 * 【第3步】i=2, nums[2]=3, 已在正确位置(nums[2]==3)，跳过
 *
 * 【第4步】i=3, nums[3]=4, 已在正确位置(nums[3]==4)，跳过
 *
 * 【最终状态】
 * 索引:    0   1   2   3
 * 数组:   [1, -1,  3,  4]
 *         ✅       ✅  ✅
 *
 * 【查找阶段】
 * i=0: nums[0]=1 == 0+1 ✅
 * i=1: nums[1]=-1 != 1+1 ❌ → 返回 2
 *
 * 答案：2
 * </pre>
 *
 * <h3>⚠️ 注意事项</h3>
 * <ul>
 *   <li><b>死循环防护</b>：当 nums[i] == nums[nums[i]-1] 时必须 break，避免重复值导致无限交换</li>
 *   <li><b>有效范围</b>：只处理 [1, n] 范围内的数，负数和超过 n 的数保持原位</li>
 *   <li><b>时间复杂度</b>：虽然有两层循环，但每个元素最多被交换一次，总体 O(n)</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260407 {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;

        // 🔍 放置阶段：将每个正数放到它应该在的位置
        // 目标：让 nums[i] = i + 1（如果可能）
        for (int i = 0; i < n; i++) {
            // 🔄 当前元素在有效范围[1,n]内，且未在正确位置时，持续交换
            while (nums[i] >= 1 && nums[i] <= n && nums[i] != nums[nums[i] - 1]) {
                int correctIndex = nums[i] - 1;  // 📍 计算应该在的位置
                // ✅ 避免重复交换相同值导致死循环（如 [1,1]）
                if (nums[i] != nums[correctIndex]) {
                    // 🔀 交换到正确位置
                    int temp = nums[i];
                    nums[i] = nums[correctIndex];
                    nums[correctIndex] = temp;
                } else {
                    break;  // 🛑 防止死循环
                }
            }
        }

        // 🔍 查找阶段：找到第一个不在正确位置的数
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {  // ❌ 发现缺失的正数
                return i + 1;
            }
        }

        // ✅ 所有位置都正确，说明 1~n 都存在，返回 n+1
        return n + 1;
    }
}

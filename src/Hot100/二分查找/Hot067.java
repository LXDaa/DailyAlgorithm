package Hot100.二分查找;

/**
 * @author lxd
 **/
public class Hot067 {
    /**
     * <a href="https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/description/">153. 寻找旋转排序数组中的最小值</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次旋转后，得到输入数组。</p>
     * <p>给定一个元素值互不相同的数组 nums，返回旋转数组的最小元素。</p>
     * <p>你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。</p>
     *
     * <h3>💡 核心思路：二分查找 + 旋转特性</h3>
     * <ul>
     *   <li><b>基本思想</b>：利用旋转数组的特性，通过二分查找定位最小值
     *     <ul>
     *       <li>计算中间位置 mid</li>
     *       <li>判断当前区间是否完全有序</li>
     *       <li>如果完全有序，返回左端点即为最小值</li>
     *       <li>否则根据 nums[mid] 的位置决定搜索方向</li>
     *     </ul>
     *   </li>
     *   <li><b>旋转数组的特性</b>：
     *     <ul>
     *       <li>旋转后的数组分为两个有序子数组</li>
     *       <li>最小值在旋转点位置</li>
     *       <li>左子数组的值都大于右子数组的值</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(log n)，二分查找的标准复杂度</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常数级别的额外空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [3,4,5,1,2]
     *
     * 旋转数组结构：
     *   左有序部分：[3,4,5]
     *   右有序部分：[1,2]
     *   旋转点：index = 3（值为 1，最小值）
     *
     * 查找最小值：
     *
     * 初始状态：left = 0, right = 4
     *
     * 第一次循环：
     *   mid = 0 + (4 - 0) / 2 = 2
     *   nums[mid] = 5
     *   检查是否完全有序：nums[mid] >= nums[left] && nums[mid] <= nums[right]
     *     5 >= 3 && 5 <= 2 ❌（不完全有序）
     *   nums[mid] >= nums[left]（5 >= 3）✅ → mid 在左半部分
     *   left = mid + 1 = 3
     *
     * 第二次循环：
     *   mid = 3 + (4 - 3) / 2 = 3
     *   nums[mid] = 1
     *   检查是否完全有序：nums[mid] >= nums[left] && nums[mid] <= nums[right]
     *     1 >= 1 && 1 <= 2 ✅（完全有序）
     *   return nums[left] = 1 ✅（找到最小值）
     *
     * 示例2：nums = [4,5,6,7,0,1,2]
     *
     * 循环过程：
     *   mid=3: nums[3]=7 >= nums[0]=4 → 左半部分，left=4
     *   mid=5: nums[5]=1 <= nums[6]=2 → 右半部分，right=5
     *   mid=4: nums[4]=0 >= nums[4]=0 && 0 <= nums[5]=1 → 完全有序
     *   return nums[4] = 0 ✅
     *
     * 示例3：nums = [1,2,3,4,5]（未旋转）
     *
     * 循环过程：
     *   mid=2: nums[2]=3 >= nums[0]=1 && 3 <= nums[4]=5 → 完全有序
     *   return nums[0] = 1 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 如何判断区间是否完全有序？
     *    - nums[mid] >= nums[left] && nums[mid] <= nums[right]
     *    - 如果满足，说明 [left, right] 区间没有旋转点
     *    - nums[left] 就是该区间最小值
     *
     * 2️⃣ nums[mid] >= nums[left] 的含义？
     *    - 说明 mid 在左半部分（有序部分）
     *    - 旋转点在 mid 右边
     *    - 设置 left = mid + 1，向右搜索
     *
     * 3️⃣ nums[mid] <= nums[right] 的含义？
     *    - 说明 mid 在右半部分（有序部分）
     *    - 旋转点在 mid 左边或就是 mid
     *    - 设置 right = mid，向左搜索（包含 mid）
     *
     * 4️⃣ 为什么 right = mid 而不是 right = mid - 1？
     *    - mid 可能就是最小值
     *    - 需要包含 mid 在搜索范围内
     *    - 防止漏掉最小值
     *
     * 5️⃣ 何时返回最小值？
     *    - 当区间完全有序时
     *    - nums[left] 就是最小值
     *    - 立即返回，无需继续搜索
     *
     * 6️⃣ 时间复杂度分析：
     *    - 每次循环将搜索范围缩小一半
     *    - 时间复杂度 O(log n)
     *
     * 7️⃣ 边界情况测试：
     *    - 单元素数组：nums = [1] → return 1
     *    - 未旋转数组：nums = [1,2,3,4] → return 1
     *    - 完全旋转：nums = [2,3,4,1] → return 1
     *    - 两元素数组：nums = [2,1] → return 1
     *
     * 8️⃣ 与普通二分查找的区别：
     *    - 普通二分查找：查找特定目标值
     *    - 本题：查找旋转点（最小值）
     *    - 利用旋转数组的特殊结构
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>整数溢出</b>：使用 left + (right - left) / 2 避免溢出</li>
     *   <li><b>循环条件</b>：left <= right，确保检查所有位置</li>
     *   <li><b>right 设置</b>：right = mid，包含 mid，不能是 mid - 1</li>
     *   <li><b>有序判断</b>：nums[mid] >= nums[left] && nums[mid] <= nums[right]</li>
     *   <li><b>元素唯一</b>：题目保证元素互不相同，简化了判断逻辑</li>
     * </ul>
     *
     * @param nums 旋转后的整数数组
     * @return 数组中的最小值
     */
    public int findMin(int[] nums) {
        // 📋 初始化左右指针
        int left = 0, right = nums.length - 1;

        // 🔄 二分查找循环
        while (left <= right) {
            // 📐 计算中间位置（避免溢出）
            int mid = left + (right - left) / 2;

            // 🔍 判断当前区间是否完全有序
            if (nums[mid] >= nums[left] && nums[mid] <= nums[right]) {
                // ✅ 区间完全有序，nums[left] 就是最小值
                return nums[left];
            } else if (nums[mid] >= nums[left]) {
                // 🔍 mid 在左半部分，旋转点在右边
                left = mid + 1;
            } else if (nums[mid] <= nums[right]) {
                // 🔍 mid 在右半部分，旋转点在左边或就是 mid
                right = mid;
            }
        }

        // ❌ 理论上不会到达这里
        return -1;
    }
}

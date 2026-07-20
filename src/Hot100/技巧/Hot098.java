package Hot100.技巧;

/**
 * @author lxd
 **/
public class Hot098 {
    /**
     * <a href="https://leetcode.cn/problems/sort-colors/description/">75. 颜色分类</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定一个包含红色、白色和蓝色、共 n 个元素的数组 nums，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。</p>
     * <p>我们使用整数 0、1 和 2 分别表示红色、白色和蓝色。</p>
     * <p>必须在不使用库的 sort 函数的情况下解决这个问题。</p>
     *
     * <h3>💡 核心思路：荷兰国旗问题（三指针法）</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用三个指针将数组分为三个区域
     *     <ul>
     *       <li>left 指针：指向 0 区域的右边界（nums[0..left-1] 都是 0）</li>
     *       <li>right 指针：指向 2 区域的左边界（nums[right+1..n-1] 都是 2）</li>
     *       <li>cur 指针：当前遍历的位置</li>
     *       <li>遍历数组，根据 nums[cur] 的值进行交换</li>
     *       <li>时间复杂度 O(n)，空间复杂度 O(1)</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用三指针法</b>：
     *     <ul>
     *       <li>只有三种颜色，适合用指针划分区域</li>
     *       <li>一次遍历即可完成排序</li>
     *       <li>原地排序，不需要额外空间</li>
     *       <li>这是经典的荷兰国旗问题解法</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个元素最多被访问一次</li>
     *   <li><b>空间复杂度</b>：O(1)，只使用常量空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [2, 0, 2, 1, 1, 0]
     *
     * 初始化：left = 0, right = 5, cur = 0
     *
     * nums: [2, 0, 2, 1, 1, 0]
     *        ^         ^
     *       cur      right
     *
     * cur=0, nums[cur]=2：
     *   swap(nums, 0, 5) → nums = [0, 0, 2, 1, 1, 2]
     *   right-- → right = 4
     *   cur 不变（新交换过来的元素还没检查）
     *   → left=0, right=4, cur=0
     *
     * nums: [0, 0, 2, 1, 1, 2]
     *        ^       ^
     *       cur    right
     *
     * cur=0, nums[cur]=0：
     *   swap(nums, 0, 0) → nums = [0, 0, 2, 1, 1, 2]
     *   cur++ → cur = 1
     *   left++ → left = 1
     *   → left=1, right=4, cur=1
     *
     * nums: [0, 0, 2, 1, 1, 2]
     *          ^     ^
     *         cur  right
     *
     * cur=1, nums[cur]=0：
     *   swap(nums, 1, 1) → nums = [0, 0, 2, 1, 1, 2]
     *   cur++ → cur = 2
     *   left++ → left = 2
     *   → left=2, right=4, cur=2
     *
     * nums: [0, 0, 2, 1, 1, 2]
     *            ^   ^
     *           cur right
     *
     * cur=2, nums[cur]=2：
     *   swap(nums, 2, 4) → nums = [0, 0, 1, 1, 2, 2]
     *   right-- → right = 3
     *   cur 不变
     *   → left=2, right=3, cur=2
     *
     * nums: [0, 0, 1, 1, 2, 2]
     *            ^ ^
     *           cur right
     *
     * cur=2, nums[cur]=1：
     *   nums[cur] == 1，不需要交换
     *   cur++ → cur = 3
     *   → left=2, right=3, cur=3
     *
     * nums: [0, 0, 1, 1, 2, 2]
     *              ^
     *             cur/right
     *
     * cur=3, nums[cur]=1：
     *   nums[cur] == 1，不需要交换
     *   cur++ → cur = 4
     *   → left=2, right=3, cur=4
     *
     * cur=4 > right=3，循环结束
     *
     * 最终：nums = [0, 0, 1, 1, 2, 2] ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 三个指针分别代表什么？
     *    - left：0 区域的右边界，nums[0..left-1] 都是 0
     *    - right：2 区域的左边界，nums[right+1..n-1] 都是 2
     *    - cur：当前遍历的位置
     *    - 中间区域（left..cur-1）都是 1
     *
     * 2️⃣ 遇到 0 时如何处理？
     *    - swap(nums, cur, left)：将 0 放到 0 区域
     *    - cur++：当前位置已处理完毕，继续下一个
     *    - left++：0 区域右边界向右扩展
     *    - 交换过来的元素一定是 1（因为中间区域都是 1），所以 cur 可以直接前进
     *
     * 3️⃣ 遇到 2 时如何处理？
     *    - swap(nums, cur, right)：将 2 放到 2 区域
     *    - right--：2 区域左边界向左扩展
     *    - cur 不变：新交换过来的元素还没检查，需要继续检查
     *    - 交换过来的元素可能是 0、1、2，所以 cur 不能前进
     *
     * 4️⃣ 遇到 1 时如何处理？
     *    - nums[cur] == 1，已经在正确的位置（中间区域）
     *    - 直接 cur++，继续下一个元素
     *    - 不需要交换
     *
     * 5️⃣ 循环条件是什么？
     *    - while (cur <= right)
     *    - cur 超过 right 时，所有元素已处理完毕
     *    - right 右侧都是 2，不需要再处理
     *
     * 6️⃣ 为什么 cur++ 在不同情况下处理不同？
     *    - 遇到 0：交换后 cur++，因为交换过来的是 1，已经检查过
     *    - 遇到 2：交换后 cur 不变，因为交换过来的元素未知，需要重新检查
     *    - 遇到 1：直接 cur++，因为已经在正确位置
     *
     * 7️⃣ 时间复杂度分析：
     *    - 每个元素最多被访问一次
     *    - cur 从 0 到 right，最多移动 n 次
     *    - 总时间：O(n)
     *
     * 8️⃣ 完整流程总结：
     *
     *    left = 0, right = n-1, cur = 0
     *
     *    while cur <= right：
     *      if nums[cur] == 0：
     *        swap(nums, cur, left)
     *        cur++, left++
     *      elif nums[cur] == 2：
     *        swap(nums, cur, right)
     *        right--
     *      else：
     *        cur++
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>指针初始化</b>：left=0, right=n-1, cur=0</li>
     *   <li><b>遇到 0</b>：交换后 cur++ 和 left++</li>
     *   <li><b>遇到 2</b>：交换后 right--，cur 不变</li>
     *   <li><b>遇到 1</b>：直接 cur++</li>
     *   <li><b>循环条件</b>：cur <= right</li>
     * </ul>
     *
     * @param nums 包含 0、1、2 的数组
     */
    public void sortColors(int[] nums) {
        // 📊 初始化三个指针
        // left：0 区域的右边界（nums[0..left-1] 都是 0）
        // right：2 区域的左边界（nums[right+1..n-1] 都是 2）
        // cur：当前遍历的位置
        int left = 0, right = nums.length - 1;
        int cur = 0;

        // 🔄 遍历数组，直到 cur 超过 right
        while (cur <= right) {
            // 📌 如果当前元素是 0，放到 0 区域
            if (nums[cur] == 0) {
                swap(nums, cur, left);
                cur++;
                left++;
            }
            // 📌 如果当前元素是 2，放到 2 区域
            else if (nums[cur] == 2) {
                swap(nums, cur, right);
                right--;
            }
            // 📌 如果当前元素是 1，已经在正确位置
            else {
                cur++;
            }
        }
    }

    /**
     * 交换数组中的两个元素
     *
     * @param arr 数组
     * @param i   第一个元素的索引
     * @param j   第二个元素的索引
     */
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

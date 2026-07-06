package Hot100.堆;

import java.util.PriorityQueue;

/**
 * @author lxd
 **/
public class Hot074 {
    /**
     * <a href="https://leetcode.cn/problems/kth-largest-element-in-an-array/description/">215. 数组中的第K个最大元素</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。</p>
     * <p>请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。</p>
     *
     * <h3>💡 核心思路：最小堆</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用大小为 k 的最小堆，堆顶始终是第 k 大的元素
     *     <ul>
     *       <li>遍历数组中的每个元素</li>
     *       <li>将元素加入堆</li>
     *       <li>如果堆的大小超过 k，弹出堆顶（最小的元素）</li>
     *       <li>遍历结束后，堆顶就是第 k 大的元素</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用最小堆</b>：
     *     <ul>
     *       <li>堆顶是最小的，弹出的是较小的元素</li>
     *       <li>保留的是最大的 k 个元素</li>
     *       <li>最终堆顶就是第 k 大的元素</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n log k)，n 个元素，每个元素最多堆化一次</li>
     *   <li><b>空间复杂度</b>：O(k)，堆的大小</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [3,2,1,5,6,4], k = 2
     *
     * 目标：找到第 2 大的元素（即 5）
     *
     * 遍历过程：
     *
     * num=3：
     *   heap.offer(3) → heap = [3]
     *   size=1 <= 2，不弹出
     *
     * num=2：
     *   heap.offer(2) → heap = [2, 3]
     *   size=2 <= 2，不弹出
     *
     * num=1：
     *   heap.offer(1) → heap = [1, 3, 2]
     *   size=3 > 2，弹出堆顶
     *   heap.poll() = 1 → heap = [2, 3]
     *
     * num=5：
     *   heap.offer(5) → heap = [2, 3, 5]
     *   size=3 > 2，弹出堆顶
     *   heap.poll() = 2 → heap = [3, 5]
     *
     * num=6：
     *   heap.offer(6) → heap = [3, 5, 6]
     *   size=3 > 2，弹出堆顶
     *   heap.poll() = 3 → heap = [5, 6]
     *
     * num=4：
     *   heap.offer(4) → heap = [4, 6, 5]
     *   size=3 > 2，弹出堆顶
     *   heap.poll() = 4 → heap = [5, 6]
     *
     * 遍历结束：
     *   heap.peek() = 5 → return 5 ✅
     *
     * 示例2：nums = [3,2,3,1,2,4,5,5,6], k = 4
     *
     * 过程：保留最大的 4 个元素 [4,5,5,6]
     *   heap.peek() = 4 → return 4 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么用最小堆而不是最大堆？
     *    - 最小堆：堆顶是最小的，弹出较小元素，保留较大元素
     *    - 最大堆：堆顶是最大的，需要弹出最大元素，不符合需求
     *    - 最小堆更适合找第 k 大的元素
     *
     * 2️⃣ PriorityQueue 默认是什么堆？
     *    - 默认是最小堆（自然排序）
     *    - 无需额外比较器
     *    - 如果需要最大堆：new PriorityQueue<>((a,b) -> b - a)
     *
     * 3️⃣ 堆的大小如何控制？
     *    - 每次 offer 后检查 size() > k
     *    - 如果超过，poll() 弹出堆顶
     *    - 保持堆的大小始终 <= k
     *
     * 4️⃣ 时间复杂度如何计算？
     *    - 每个元素 offer 一次：O(log k)
     *    - n 个元素：O(n log k)
     *    - 比排序 O(n log n) 更优（当 k < n）
     *
     * 5️⃣ 空间复杂度如何？
     *    - 堆的大小最多为 k
     *    - 空间复杂度 O(k)
     *    - 比排序 O(n) 更优
     *
     * 6️⃣ 最终结果如何获取？
     *    - heap.peek() 获取堆顶元素
     *    - 堆顶就是第 k 大的元素
     *    - 不需要弹出
     *
     * 7️⃣ 边界情况测试：
     *    - k=1：找最大元素，堆大小为 1
     *    - k=n：找最小元素，堆大小为 n
     *    - 数组有序：nums=[1,2,3,4,5], k=2 → return 4
     *    - 数组逆序：nums=[5,4,3,2,1], k=2 → return 4
     *
     * 8️⃣ 完整流程总结：
     *
     *    minHeap = new PriorityQueue<>()  // 默认最小堆
     *
     *    for num in nums：
     *      minHeap.offer(num)
     *      if minHeap.size() > k：
     *        minHeap.poll()
     *
     *    return minHeap.peek()
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>最小堆</b>：使用默认的 PriorityQueue（最小堆）</li>
     *   <li><b>堆大小</b>：保持堆大小 <= k</li>
     *   <li><b>结果获取</b>：遍历结束后使用 peek()，不是 poll()</li>
     *   <li><b>时间复杂度</b>：O(n log k)，比排序更高效</li>
     *   <li><b>适用场景</b>：当 k 远小于 n 时优势明显</li>
     * </ul>
     *
     * @param nums 整数数组
     * @param k    第 k 大的元素
     * @return 数组中第 k 大的元素
     */
    public int findKthLargest(int[] nums, int k) {
        // 📦 创建最小堆（默认）
        PriorityQueue<Integer> minHead = new PriorityQueue<>();

        // 🔄 遍历数组中的每个元素
        for (int num : nums) {
            // ➕ 将元素加入堆
            minHead.offer(num);

            // 📏 如果堆大小超过 k，弹出堆顶（最小元素）
            if (minHead.size() > k) {
                minHead.poll();
            }
        }

        // ✅ 堆顶就是第 k 大的元素
        return minHead.peek();
    }
}

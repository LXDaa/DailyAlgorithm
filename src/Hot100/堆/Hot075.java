package Hot100.堆;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author lxd
 **/
public class Hot075 {
    /**
     * <a href="https://leetcode.cn/problems/top-k-frequent-elements/description/">347. 前 K 个高频元素</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个整数数组 nums 和一个整数 k，请你返回其中出现频率前 k 高的元素。你可以按任意顺序返回答案。</p>
     *
     * <h3>💡 核心思路：哈希表 + 最小堆</h3>
     * <ul>
     *   <li><b>基本思想</b>：先用哈希表统计频率，再用最小堆找出频率最高的 k 个元素
     *     <ul>
     *       <li>遍历数组，用 HashMap 统计每个元素的频率</li>
     *       <li>遍历 HashMap，用最小堆保存频率最高的 k 个元素</li>
     *       <li>堆的大小超过 k 时，弹出频率最低的元素</li>
     *       <li>遍历结束后，堆中就是频率最高的 k 个元素</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用最小堆</b>：
     *     <ul>
     *       <li>堆顶是频率最低的，弹出较小频率的元素</li>
     *       <li>保留频率较高的元素</li>
     *       <li>最终堆中就是频率最高的 k 个元素</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n log k)，统计频率 O(n)，堆操作 O(n log k)</li>
     *   <li><b>空间复杂度</b>：O(n)，哈希表和堆</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：nums = [1,1,1,2,2,3], k = 2
     *
     * 步骤1：统计频率
     *   map = {1: 3, 2: 2, 3: 1}
     *
     * 步骤2：遍历 map，维护最小堆
     *
     * key=1, freq=3：
     *   heap.size()=0 < 2 → offer(1)
     *   heap = [1]（按频率比较）
     *
     * key=2, freq=2：
     *   heap.size()=1 < 2 → offer(2)
     *   heap = [2, 1]（堆顶频率最小）
     *
     * key=3, freq=1：
     *   heap.size()=2 >= 2
     *   freq(3)=1 > freq(heap.peek())=freq(2)=2 ❌（不大于）
     *   不弹出，不加入
     *   heap = [2, 1]
     *
     * 遍历结束：
     *   heap = [2, 1] → 结果 = [2, 1]（顺序不要求）
     *   return [1, 2] 或 [2, 1] ✅
     *
     * 示例2：nums = [1], k = 1
     *   map = {1: 1}
     *   heap = [1]
     *   return [1] ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 如何统计频率？
     *    - 使用 HashMap<Integer, Integer>
     *    - map.put(num, map.getOrDefault(num, 0) + 1)
     *    - getOrDefault 处理首次出现的元素
     *
     * 2️⃣ 堆中存储什么？
     *    - 存储元素（key），而不是频率
     *    - 通过 map.get(key) 获取频率进行比较
     *    - 最终需要返回元素本身
     *
     * 3️⃣ 堆的比较器如何定义？
     *    - PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b))
     *    - 根据元素的频率进行比较
     *    - a 的频率 - b 的频率：小顶堆
     *
     * 4️⃣ 什么时候弹出元素？
     *    - heap.size() >= k 且当前元素频率 > 堆顶频率
     *    - 弹出堆顶（频率最小），加入当前元素
     *    - 保持堆中始终是频率最高的 k 个元素
     *
     * 5️⃣ 如何将堆转换为结果数组？
     *    - 创建大小为 k 的数组
     *    - 依次弹出堆顶元素，存入数组
     *    - 顺序不要求，可以是任意顺序
     *
     * 6️⃣ 时间复杂度分析：
     *    - 统计频率：O(n)
     *    - 堆操作：每个元素最多入堆一次，出堆一次
     *    - 堆大小为 k，每次操作 O(log k)
     *    - 总时间：O(n log k)
     *
     * 7️⃣ 空间复杂度分析：
     *    - HashMap：O(m)，m 是不同元素的数量
     *    - 堆：O(k)
     *    - 总空间：O(n)（最坏情况 m=n）
     *
     * 8️⃣ 完整流程总结：
     *
     *    map = {}
     *    for num in nums：
     *      map[num] = map.getOrDefault(num, 0) + 1
     *
     *    minHeap = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b))
     *
     *    for key in map.keySet()：
     *      if minHeap.size() < k：
     *        minHeap.offer(key)
     *      elif map.get(key) > map.get(minHeap.peek())：
     *        minHeap.poll()
     *        minHeap.offer(key)
     *
     *    res = new int[k]
     *    idx = 0
     *    while minHeap 不为空：
     *      res[idx++] = minHeap.poll()
     *
     *    return res
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>频率统计</b>：使用 HashMap 统计每个元素的频率</li>
     *   <li><b>堆比较器</b>：根据频率比较，不是元素值比较</li>
     *   <li><b>堆大小</b>：保持堆大小 <= k</li>
     *   <li><b>结果顺序</b>：题目允许任意顺序返回</li>
     *   <li><b>边界情况</b>：k=1，数组只有一个元素</li>
     * </ul>
     *
     * @param nums 整数数组
     * @param k    前 k 个高频元素
     * @return 频率前 k 高的元素数组
     */
    public int[] topKFrequent(int[] nums, int k) {
        // 📊 统计每个元素的频率
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // 📦 创建最小堆，按频率比较
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b));

        // 🔄 遍历 HashMap，维护频率最高的 k 个元素
        for (Integer key : map.keySet()) {
            if (minHeap.size() < k) {
                // ➕ 堆未满，直接加入
                minHeap.offer(key);
            } else if (map.get(key) > map.get(minHeap.peek())) {
                // ➖ 当前元素频率更高，弹出堆顶，加入当前元素
                minHeap.poll();
                minHeap.offer(key);
            }
        }

        // 📋 将堆转换为结果数组
        int[] res = new int[k];
        int idx = 0;
        while (!minHeap.isEmpty()) {
            res[idx++] = minHeap.poll();
        }

        // ✅ 返回结果
        return res;
    }
}

package Hot100.堆;

import java.util.PriorityQueue;

/**
 * @author lxd
 **/
public class Hot076 {
    /**
     * <a href="https://leetcode.cn/problems/find-median-from-data-stream/description/">295. 数据流的中位数</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。</p>
     * <p>实现 MedianFinder 类：</p>
     * <ul>
     *   <li>MedianFinder() 初始化 MedianFinder 对象。</li>
     *   <li>void addNum(int num) 将数据流中的整数 num 添加到数据结构中。</li>
     *   <li>double findMedian() 返回到目前为止所有元素的中位数。</li>
     * </ul>
     *
     * <h3>💡 核心思路：双堆法</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用两个堆，分别存储较小一半和较大一半的元素
     *     <ul>
     *       <li>左堆（大顶堆）：存储较小一半元素，堆顶为左半最大值</li>
     *       <li>右堆（小顶堆）：存储较大一半元素，堆顶为右半最小值</li>
     *       <li>左堆大小 >= 右堆大小，差值不超过 1</li>
     *       <li>奇数个元素时，中位数 = 左堆顶</li>
     *       <li>偶数个元素时，中位数 = (左堆顶 + 右堆顶) / 2</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用双堆</b>：
     *     <ul>
     *       <li>需要在 O(1) 时间内获取中位数</li>
     *       <li>单堆无法同时获取中间两个元素</li>
     *       <li>双堆分别管理两半，堆顶就是中间元素</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：addNum O(log n)，findMedian O(1)</li>
     *   <li><b>空间复杂度</b>：O(n)，存储所有元素</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：addNum(1), addNum(2), findMedian(), addNum(3), findMedian()
     *
     * 初始化：
     *   left = []（大顶堆）
     *   right = []（小顶堆）
     *
     * addNum(1)：
     *   s1=0, s2=0，相等
     *   right 为空，直接加入 left
     *   left = [1]
     *
     * addNum(2)：
     *   s1=1, s2=0，不相等
     *   2 >= left.peek()=1，加入 right
     *   right = [2]
     *
     * findMedian()：
     *   s1=1, s2=1，相等
     *   return (1 + 2) / 2 = 1.5 ✅
     *
     * addNum(3)：
     *   s1=1, s2=1，相等
     *   3 > right.peek()=2
     *   right.poll()=2，加入 left
     *   left = [2, 1]
     *   right.offer(3)
     *   right = [3]
     *
     * findMedian()：
     *   s1=2, s2=1，不相等
     *   return left.peek()=2 ✅
     *
     * 最终状态：
     *   left = [2, 1]（大顶堆，堆顶=2）
     *   right = [3]（小顶堆，堆顶=3）
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 左堆和右堆的性质？
     *    - 左堆（大顶堆）：存储较小一半，堆顶为左半最大值
     *    - 右堆（小顶堆）：存储较大一半，堆顶为右半最小值
     *    - 左堆元素 <= 右堆元素
     *
     * 2️⃣ 如何创建大顶堆？
     *    - PriorityQueue 默认是小顶堆
     *    - 大顶堆需要自定义比较器：(a, b) -> b - a
     *    - left = new PriorityQueue<>((a, b) -> b - a)
     *
     * 3️⃣ addNum 的插入策略？
     *    - 当 s1 == s2（偶数个元素）：
     *      if num <= right.peek() → left.offer(num)
     *      else → left.offer(right.poll()), right.offer(num)
     *    - 当 s1 > s2（奇数个元素）：
     *      if num >= left.peek() → right.offer(num)
     *      else → right.offer(left.poll()), left.offer(num)
     *
     * 4️⃣ 为什么需要交换元素？
     *    - 保持左堆 <= 右堆的性质
     *    - 例如：addNum(3) 时，3 > right.peek()=2
     *    - 需要将 right 中最小的 2 移到 left，再加入 3
     *
     * 5️⃣ findMedian 如何实现？
     *    - 如果 s1 == s2：return (left.peek() + right.peek()) / 2.0
     *    - 如果 s1 > s2：return left.peek()
     *    - O(1) 时间复杂度
     *
     * 6️⃣ 堆大小的关系？
     *    - left.size() >= right.size()
     *    - left.size() - right.size() <= 1
     *    - 保证中位数在左堆顶或两堆顶的平均值
     *
     * 7️⃣ 时间复杂度分析：
     *    - addNum：最多两次堆操作（poll + offer）
     *    - 每次堆操作 O(log n)
     *    - addNum 总时间 O(log n)
     *    - findMedian：O(1)
     *
     * 8️⃣ 完整流程总结：
     *
     *    构造函数：
     *    left = new PriorityQueue<>((a, b) -> b - a)  // 大顶堆
     *    right = new PriorityQueue<>()  // 小顶堆（默认）
     *
     *    addNum(num)：
     *    s1 = left.size(), s2 = right.size()
     *
     *    if s1 == s2：
     *      if right 为空 或 num <= right.peek()：
     *        left.offer(num)
     *      else：
     *        left.offer(right.poll())
     *        right.offer(num)
     *    else：
     *      if num >= left.peek()：
     *        right.offer(num)
     *      else：
     *        right.offer(left.poll())
     *        left.offer(num)
     *
     *    findMedian()：
     *    if left.size() == right.size()：
     *      return (left.peek() + right.peek()) / 2.0
     *    return left.peek()
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>堆的类型</b>：左堆是大顶堆，右堆是小顶堆</li>
     *   <li><b>大小关系</b>：left.size() >= right.size()，差值 <= 1</li>
     *   <li><b>插入策略</b>：根据当前元素与堆顶的大小关系决定插入位置</li>
     *   <li><b>交换元素</b>：保持左堆 <= 右堆的性质</li>
     *   <li><b>边界处理</b>：right 为空时的特殊处理</li>
     * </ul>
     */
    class MedianFinder {
        // 左大顶堆：存较小一半，堆顶为左半最大值
        private final PriorityQueue<Integer> left;
        // 右小顶堆：存较大一半，堆顶为右半最小值
        private final PriorityQueue<Integer> right;

        /**
         * 初始化 MedianFinder 对象
         */
        public MedianFinder() {
            // 📦 创建大顶堆（左堆）
            left = new PriorityQueue<>((a, b) -> b - a);
            // 📦 创建小顶堆（右堆，默认）
            right = new PriorityQueue<>();
        }

        /**
         * 将数据流中的整数 num 添加到数据结构中
         *
         * @param num 要添加的整数
         */
        public void addNum(int num) {
            int s1 = left.size(), s2 = right.size();

            if (s1 == s2) {
                // ⚖️ 当前总数偶数，插入后让左堆多1个
                if (right.isEmpty() || num <= right.peek()) {
                    // 📥 直接加入左堆
                    left.offer(num);
                } else {
                    // 🔄 将右堆最小的移到左堆，再加入当前元素
                    left.offer(right.poll());
                    right.offer(num);
                }
            } else {
                // ⚖️ 当前左堆多1，插入后两边相等
                if (num >= left.peek()) {
                    // 📥 直接加入右堆
                    right.offer(num);
                } else {
                    // 🔄 将左堆最大的移到右堆，再加入当前元素
                    right.offer(left.poll());
                    left.offer(num);
                }
            }
        }

        /**
         * 返回到目前为止所有元素的中位数
         *
         * @return 中位数
         */
        public double findMedian() {
            if (left.size() == right.size()) {
                // 📊 偶数个元素，返回两堆顶的平均值
                return (left.peek() + right.peek()) / 2.0;
            }
            // 📊 奇数个元素，返回左堆顶
            return left.peek();
        }
    }
}

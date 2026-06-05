package Hot100.链表;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxd
 **/
public class Hot035 {
    /**
     * 双向链表节点
     */
    static class Node {
        Node prev, next;  // 前驱和后继指针
        int key, value;   // 键值对

        Node() {
        }

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * <a href="https://leetcode.cn/problems/lru-cache/description/">146. LRU 缓存</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>请你设计并实现一个满足 LRU (最近最少使用) 缓存约束的数据结构。LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存；int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1；void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ，如果不存在，则向缓存中插入该组 key-value 。如果插入操作导致关键字数量超过 capacity ，则应该逐出最久未使用的关键字。</p>
     *
     * <h3>💡 核心思路：哈希表 + 双向链表</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用哈希表实现 O(1) 查找，使用双向链表维护访问顺序
     *     <ul>
     *       <li>哈希表：key → Node，提供 O(1) 的查找能力</li>
     *       <li>双向链表：头部是最近使用的节点，尾部是最久未使用的节点</li>
     *       <li>get 操作：从哈希表找到节点，将其移动到链表头部</li>
     *       <li>put 操作：如果 key 存在，更新值并移到头部；如果不存在，创建新节点添加到头部</li>
     *       <li>容量满时：删除链表尾部节点（最久未使用），并从哈希表中移除</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用双向链表</b>：
     *     <ul>
     *       <li>需要在 O(1) 时间内删除任意节点（单向链表需要遍历找前驱）</li>
     *       <li>需要在头部和尾部快速插入/删除</li>
     *       <li>双向链表支持这些操作</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：get 和 put 都是 O(1)</li>
     *   <li><b>空间复杂度</b>：O(capacity)，哈希表和链表最多存储 capacity 个节点</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：capacity = 2
     * LRUCache cache = new LRUCache(2);
     *
     * 【操作1】cache.put(1, 1)
     *   缓存为空，直接添加
     *
     *   链表：head ↔ [1:1] ↔ tail
     *   哈希表：{1 → Node(1,1)}
     *   size = 1
     *
     * 【操作2】cache.put(2, 2)
     *   缓存未满，直接添加
     *
     *   链表：head ↔ [2:2] ↔ [1:1] ↔ tail
     *   哈希表：{1 → Node(1,1), 2 → Node(2,2)}
     *   size = 2
     *
     * 【操作3】cache.get(1)
     *   key=1 存在，返回值 1，并将节点移到头部
     *
     *   移动前：head ↔ [2:2] ↔ [1:1] ↔ tail
     *   移动后：head ↔ [1:1] ↔ [2:2] ↔ tail
     *   哈希表：不变
     *   返回：1
     *
     * 【操作4】cache.put(3, 3)
     *   缓存已满（size=2=capacity），需要删除最久未使用的节点
     *   最久未使用的是尾部节点 [2:2]
     *
     *   删除尾部：head ↔ [1:1] ↔ tail
     *   哈希表：{1 → Node(1,1)}，移除 key=2
     *   size = 1
     *
     *   添加新节点：head ↔ [3:3] ↔ [1:1] ↔ tail
     *   哈希表：{1 → Node(1,1), 3 → Node(3,3)}
     *   size = 2
     *
     * 【操作5】cache.get(2)
     *   key=2 不存在（已被删除）
     *   返回：-1
     *
     * 【操作6】cache.put(4, 4)
     *   缓存已满，删除最久未使用的节点 [1:1]
     *
     *   删除尾部：head ↔ [3:3] ↔ tail
     *   哈希表：{3 → Node(3,3)}，移除 key=1
     *   size = 1
     *
     *   添加新节点：head ↔ [4:4] ↔ [3:3] ↔ tail
     *   哈希表：{3 → Node(3,3), 4 → Node(4,4)}
     *   size = 2
     *
     * ---
     *
     * 双向链表操作详解：
     *
     * 1️⃣ addToHead(node) - 将节点添加到头部
     *
     *    初始：head ↔ A ↔ B ↔ tail
     *
     *    步骤：
     *    node.prev = head        node → head
     *    node.next = head.next   node → A
     *    head.next.prev = node   A.prev = node
     *    head.next = node        head.next = node
     *
     *    结果：head ↔ node ↔ A ↔ B ↔ tail
     *
     * 2️⃣ removeNode(node) - 删除节点
     *
     *    初始：head ↔ A ↔ node ↔ B ↔ tail
     *
     *    步骤：
     *    node.prev.next = node.next   A.next = B
     *    node.next.prev = node.prev   B.prev = A
     *
     *    结果：head ↔ A ↔ B ↔ tail
     *           node 被孤立（但对象还在内存中）
     *
     * 3️⃣ moveToHead(node) - 移动到头部
     *
     *    相当于：removeNode(node) + addToHead(node)
     *
     *    初始：head ↔ A ↔ node ↔ B ↔ tail
     *    删除：head ↔ A ↔ B ↔ tail
     *    添加：head ↔ node ↔ A ↔ B ↔ tail
     *
     * 4️⃣ removeTail() - 删除尾部节点
     *
     *    初始：head ↔ A ↔ B ↔ tail
     *
     *    步骤：
     *    node = tail.prev = B
     *    removeNode(B)
     *
     *    结果：head ↔ A ↔ tail
     *    返回：B（用于从哈希表中删除）
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要用虚拟头尾节点（dummy head/tail）？
     *    - 简化边界处理，不需要判断链表是否为空
     *    - 所有节点的插入/删除操作都统一
     *    - head 和 tail 永远存在，不会为 null
     *    - 实际数据节点在 head 和 tail 之间
     *
     * 2️⃣ 为什么哈希表的值是 Node 而不是 value？
     *    - 需要快速定位到链表中的节点
     *    - 找到节点后才能进行移动或删除操作
     *    - Node 包含 key、value、prev、next 完整信息
     *
     * 3️⃣ get 操作为什么要移动到头部？
     *    - LRU 规则：最近使用的应该在头部
     *    - get 操作表示访问了该节点，变为"最近使用"
     *    - 移动到头部维护了访问顺序
     *
     * 4️⃣ put 操作的两种情况？
     *    - key 已存在：更新 value，移动到头部（不增加 size）
     *    - key 不存在：创建新节点，添加到头部
     *      * 如果容量满，先删除尾部节点
     *      * size 增加 1
     *
     * 5️⃣ 为什么删除尾部节点？
     *    - 尾部节点是最久未使用的（LRU）
     *    - 头部是最近使用的（MRU）
     *    - 容量满时，淘汰最久未使用的
     *
     * 6️⃣ addToHead 的四步操作顺序？
     *    - 必须先设置 node 的 prev 和 next
     *    - 然后才能修改相邻节点的指针
     *    - 顺序错误会导致链表断裂
     *    - 正确顺序：
     *      1. node.prev = head
     *      2. node.next = head.next
     *      3. head.next.prev = node
     *      4. head.next = node
     *
     * 7️⃣ 与 LinkedHashMap 对比？
     *    - Java 的 LinkedHashMap 可以直接实现 LRU
     *    - 但面试要求手动实现，考察数据结构设计能力
     *    - 核心思想相同：哈希表 + 双向链表
     *
     * 8️⃣ 完整流程总结：
     *
     *    初始化：
     *    ├─ 创建 dummy head 和 tail
     *    ├─ head.next = tail, tail.prev = head
     *    └─ size = 0
     *
     *    get(key)：
     *    ├─ key 不存在 → 返回 -1
     *    └─ key 存在 → 移动到头部，返回 value
     *
     *    put(key, value)：
     *    ├─ key 存在 → 更新 value，移动到头部
     *    └─ key 不存在：
     *       ├─ 容量满 → 删除尾部节点，从哈希表移除
     *       ├─ 创建新节点
     *       ├─ 添加到头部
     *       ├─ 加入哈希表
     *       └─ size++
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>双向链表</b>：必须正确维护 prev 和 next 指针，避免链表断裂</li>
     *   <li><b>虚拟节点</b>：head 和 tail 是哨兵节点，不存储实际数据</li>
     *   <li><b>哈希表同步</b>：删除链表节点时，必须同时从哈希表中移除</li>
     *   <li><b>size 维护</b>：添加节点 size++，删除节点 size--</li>
     *   <li><b>指针顺序</b>：addToHead 的四步操作顺序不能错</li>
     *   <li><b>key 存在性</b>：put 时要先检查 key 是否存在，存在则更新而非新增</li>
     * </ul>
     */
    class LRUCache {
        // 📦 哈希表：key → Node，提供 O(1) 查找
        private final Map<Integer, Node> cacheMap = new HashMap<>();
        // 📏 缓存容量
        private final int capacity;
        // 🔗 虚拟头尾节点，简化边界处理
        private final Node head = new Node(), tail = new Node();
        // 🔢 当前缓存大小
        private int size;

        /**
         * 初始化 LRU 缓存
         *
         * @param capacity 缓存容量
         */
        public LRUCache(int capacity) {
            this.capacity = capacity;
            // 🔗 初始化双向链表：head ↔ tail
            head.next = tail;
            tail.prev = head;
        }

        /**
         * 获取指定 key 的值
         *
         * @param key 要查找的键
         * @return 如果 key 存在返回值，否则返回 -1
         */
        public int get(int key) {
            // 🔍 key 不存在，返回 -1
            if (!cacheMap.containsKey(key)) {
                return -1;
            }

            // ✅ key 存在，获取节点并移动到头部（标记为最近使用）
            Node node = cacheMap.get(key);
            moveToHead(node);
            return node.value;
        }

        /**
         * 插入或更新键值对
         *
         * @param key   键
         * @param value 值
         */
        public void put(int key, int value) {
            // 🔍 key 已存在，更新值并移动到头部
            if (cacheMap.containsKey(key)) {
                Node node = cacheMap.get(key);
                node.value = value;      // 更新值
                moveToHead(node);        // 移动到头部
                return;
            }

            // ✨ key 不存在，创建新节点
            Node node = new Node(key, value);

            // 🗑️ 如果容量已满，删除最久未使用的节点（尾部）
            if (size == capacity) {
                Node del = removeTail();        // 删除尾部节点
                cacheMap.remove(del.key);       // 从哈希表中移除
                size--;                         // 大小减1
            }

            // ➕ 添加新节点到头部
            addToHead(node);
            cacheMap.put(key, node);  // 加入哈希表
            size++;                   // 大小加1
        }

        /**
         * 将节点移动到头部（标记为最近使用）
         *
         * @param node 要移动的节点
         */
        private void moveToHead(Node node) {
            removeNode(node);   // 先从原位置删除
            addToHead(node);    // 再添加到头部
        }

        /**
         * 从链表中删除指定节点
         *
         * @param node 要删除的节点
         */
        private void removeNode(Node node) {
            // 🔀 修改相邻节点的指针，跳过 node
            node.prev.next = node.next;  // 前驱节点的后继指向 node 的后继
            node.next.prev = node.prev;  // 后继节点的前驱指向 node 的前驱
        }

        /**
         * 将节点添加到链表头部（head 之后）
         *
         * @param node 要添加的节点
         */
        private void addToHead(Node node) {
            // 🔗 四步操作，将 node 插入到 head 和 head.next 之间
            node.prev = head;           // 步骤1：node 的前驱指向 head
            node.next = head.next;      // 步骤2：node 的后继指向原第一个节点
            head.next.prev = node;      // 步骤3：原第一个节点的前驱指向 node
            head.next = node;           // 步骤4：head 的后继指向 node
        }

        /**
         * 删除链表尾部节点（tail 之前的节点，即最久未使用的）
         *
         * @return 被删除的节点
         */
        private Node removeTail() {
            Node node = tail.prev;  // 获取尾部实际节点（tail 前面的节点）
            removeNode(node);       // 从链表中删除
            return node;            // 返回节点（用于从哈希表中移除）
        }
    }
}

package CodeTop;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/lru-cache/description/">146. LRU缓存</a>
 * <p>
 * 解题思路：
 * 本题要求设计一个LRU（Least Recently Used）缓存机制的数据结构，需要满足以下条件：
 * 1. get操作：获取key对应的value，时间复杂度O(1)
 * 2. put操作：插入或更新key-value对，时间复杂度O(1)
 * 3. 当缓存满时，删除最久未使用的元素
 * <p>
 * 解决方案：
 * 使用哈希表+双向链表的组合数据结构：
 * - 哈希表(cache)：提供O(1)时间复杂度的键值查找
 * - 双向链表：维护访问顺序，最近访问的放在头部，最久未访问的放在尾部
 * - 虚拟头尾节点(head,tail)：简化边界情况处理
 *
 * @author lxd
 **/
public class Day20260112 {
    class LRUCache {
        // 哈希表：存储key到Node的映射，提供O(1)的查找时间
        private Map<Integer, Node> cache = new HashMap<>();
        // 双向链表的虚拟头尾节点，简化链表操作
        private Node head, tail;
        // 缓存容量限制
        private int capacity;
        // 当前缓存大小
        private int size;

        /**
         * 初始化LRU缓存
         *
         * @param capacity 缓存容量
         */
        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            // 创建虚拟头尾节点，避免空指针异常
            head = new Node();
            tail = new Node();
            // 初始化双向链表连接
            head.next = tail;
            tail.prev = head;
        }

        /**
         * 获取指定key的值
         *
         * @param key 键
         * @return 对应的值，如果不存在返回-1
         */
        public int get(int key) {
            // 通过哈希表快速定位节点
            Node node = cache.get(key);
            if (node == null) {
                // 如果不存在该key，返回-1
                return -1;
            }
            // 访问节点后，将其移动到链表头部（表示最近被使用）
            moveToHead(node);
            return node.value;
        }

        /**
         * 插入或更新key-value对
         *
         * @param key   键
         * @param value 值
         */
        public void put(int key, int value) {
            // 尝试从哈希表获取现有节点
            Node node = cache.get(key);
            if (node == null) {
                // 如果key不存在，创建新节点
                Node newNode = new Node(key, value);
                // 加入哈希表映射
                cache.put(key, newNode);
                // 加入双向链表头部（表示最近被使用）
                addToHead(newNode);
                size++;
                // 如果超出容量，删除最久未使用的节点（尾部节点）
                if (size > capacity) {
                    // 删除双向链表尾部节点（最久未使用的节点）
                    Node tail = removeTail();
                    // 同时从哈希表中删除对应映射
                    cache.remove(tail.key);
                    size--;
                }
            } else {
                // 如果key已存在，更新其值，并将其移到头部
                node.value = value;
                moveToHead(node);
            }
        }

        /**
         * 将节点添加到双向链表头部
         *
         * @param node 待添加的节点
         */
        private void addToHead(Node node) {
            // 设置新节点的前后指针
            node.prev = head;
            node.next = head.next;
            // 更新原头节点下一个节点的前驱指针
            head.next.prev = node;
            // 更新头节点的后继指针
            head.next = node;
        }

        /**
         * 从双向链表中移除指定节点
         *
         * @param node 待移除的节点
         */
        private void removeNode(Node node) {
            // 跳过当前节点，连接其前后节点
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        /**
         * 将节点移动到双向链表头部（先删除再添加到头部）
         *
         * @param node 待移动的节点
         */
        private void moveToHead(Node node) {
            // 先从当前位置删除节点
            removeNode(node);
            // 再添加到头部
            addToHead(node);
        }

        /**
         * 移除并返回双向链表的尾部节点（最久未使用的节点）
         *
         * @return 尾部节点
         */
        private Node removeTail() {
            // 获取尾部的实际节点（在虚拟尾节点之前）
            Node node = tail.prev;
            // 从链表中移除该节点
            removeNode(node);
            return node;
        }

        /**
         * 双向链表节点类，存储key-value对及前后指针
         */
        class Node {
            int key;
            int value;
            Node prev;
            Node next;

            public Node() {
            }

            public Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }
    }
}
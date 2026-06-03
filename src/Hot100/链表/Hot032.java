package Hot100.链表;


import java.util.HashMap;
import java.util.Map;

/**
 * @author lxd
 **/
public class Hot032 {
    // 📦 哈希表：缓存原节点到新节点的映射关系
    Map<Node, Node> cacheNode = new HashMap<>();

    /**
     * <a href="https://leetcode.cn/problems/copy-list-with-random-pointer/description/">138. 随机链表的复制</a>
     *
     * <h3>🎯 题目描述</h3>
     * <p>给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。构造这个链表的深拷贝。</p>
     *
     * <h3>💡 核心思路：递归 + 哈希表</h3>
     * <ul>
     *   <li><b>基本思想</b>：使用递归遍历链表，用哈希表记录已创建的节点，避免重复创建
     *     <ul>
     *       <li>如果当前节点已经创建过，直接从哈希表中返回</li>
     *       <li>否则创建新节点，存入哈希表</li>
     *       <li>递归创建 next 指针指向的节点</li>
     *       <li>递归创建 random 指针指向的节点</li>
     *       <li>连接新节点的 next 和 random 指针</li>
     *     </ul>
     *   </li>
     *   <li><b>为什么用哈希表</b>：
     *     <ul>
     *       <li>random 指针可能指向前面的节点，需要快速查找是否已创建</li>
     *       <li>避免重复创建同一个节点，保证深拷贝的正确性</li>
     *       <li>哈希表提供 O(1) 的查找效率</li>
     *     </ul>
     *   </li>
     *   <li><b>时间复杂度</b>：O(n)，每个节点只被创建一次</li>
     *   <li><b>空间复杂度</b>：O(n)，哈希表和递归栈的空间</li>
     * </ul>
     *
     * <h3>📊 ASCII 演示过程</h3>
     * <pre>
     * 示例：head = [[7,null], [13,0], [11,4], [10,2], [1,0]]
     *
     * 原链表结构（数字表示 val，括号内表示 random 指向的索引）：
     *
     *   7(null) → 13(0) → 11(4) → 10(2) → 1(0) → null
     *   ↑         ↑                    ↑
     *   └─────────┘                    │
     *                                  └──→ 11
     *
     *   random 指针：
     *   7.random = null
     *   13.random = 7（索引0）
     *   11.random = 1（索引4）
     *   10.random = 11（索引2）
     *   1.random = 7（索引0）
     *
     * 【递归过程】
     *
     * 调用 copyRandomList(7)：
     *   - 7 不在 cacheNode 中
     *   - 创建 newNode(7)，cacheNode[7] = newNode
     *   - newNode.next = copyRandomList(13)  ← 递归
     *   - newNode.random = copyRandomList(null) = null
     *
     * 调用 copyRandomList(13)：
     *   - 13 不在 cacheNode 中
     *   - 创建 newNode(13)，cacheNode[13] = newNode
     *   - newNode.next = copyRandomList(11)  ← 递归
     *   - newNode.random = copyRandomList(7)
     *     → 7 已在 cacheNode 中，直接返回 cacheNode[7] ✅
     *
     * 调用 copyRandomList(11)：
     *   - 11 不在 cacheNode 中
     *   - 创建 newNode(11)，cacheNode[11] = newNode
     *   - newNode.next = copyRandomList(10)  ← 递归
     *   - newNode.random = copyRandomList(1)  ← 递归
     *
     * 调用 copyRandomList(10)：
     *   - 10 不在 cacheNode 中
     *   - 创建 newNode(10)，cacheNode[10] = newNode
     *   - newNode.next = copyRandomList(1)  ← 递归
     *   - newNode.random = copyRandomList(11)
     *     → 11 已在 cacheNode 中，直接返回 cacheNode[11] ✅
     *
     * 调用 copyRandomList(1)：
     *   - 1 不在 cacheNode 中
     *   - 创建 newNode(1)，cacheNode[1] = newNode
     *   - newNode.next = copyRandomList(null) = null
     *   - newNode.random = copyRandomList(7)
     *     → 7 已在 cacheNode 中，直接返回 cacheNode[7] ✅
     *
     * 【回溯连接】
     *
     *   从最深层开始返回：
     *   1.next = null, 1.random = 7'
     *   10.next = 1', 10.random = 11'
     *   11.next = 10', 11.random = 1'
     *   13.next = 11', 13.random = 7'
     *   7.next = 13', 7.random = null
     *
     * 最终结果（深拷贝完成）：
     *   7'(null) → 13'(7') → 11'(1') → 10'(11') → 1'(7') → null
     *   ↑          ↑                     ↑
     *   └──────────┘                     │
     *                                    └──→ 11'
     *
     * 所有节点都是新创建的，与原链表完全独立 ✅
     *
     * ---
     *
     * 哈希表示意图：
     *
     *   原节点    →    新节点
     *   ─────────────────────
     *   7 (addr1)  →  7' (addr6)
     *   13 (addr2) →  13' (addr7)
     *   11 (addr3) →  11' (addr8)
     *   10 (addr4) →  10' (addr9)
     *   1 (addr5)  →  1' (addr10)
     *
     *   通过哈希表，可以快速找到原节点对应的新节点
     *   确保 random 指针正确指向新节点 ✅
     * </pre>
     *
     * <h3>🔑 关键技巧解析</h3>
     * <pre>
     * 1️⃣ 为什么要用哈希表？
     *    - random 指针可能指向任意节点（前面、后面、自己、null）
     *    - 如果没有哈希表，无法快速判断某个节点是否已创建
     *    - 可能导致重复创建或无限递归
     *    - 哈希表提供 O(1) 查找，保证每个节点只创建一次
     *
     * 2️⃣ 为什么用递归而不是迭代？
     *    - 递归天然适合处理这种图结构（有 random 指针）
     *    - 递归会自动处理依赖关系（先创建依赖的节点）
     *    - 代码简洁，逻辑清晰
     *    - 但空间复杂度较高（递归栈 + 哈希表）
     *
     * 3️⃣ 递归的终止条件是什么？
     *    - head == null：空节点，返回 null
     *    - head 已在 cacheNode 中：已创建过，直接返回
     *    - 这两种情况都会停止递归
     *
     * 4️⃣ 为什么先创建节点再递归 next 和 random？
     *    - 先将新节点存入哈希表，建立映射关系
     *    - 这样在递归 next 或 random 时，如果遇到已创建的节点
     *    - 可以直接从哈希表中获取，避免重复创建
     *    - 这是防止无限递归的关键
     *
     * 5️⃣ random 指针如何处理？
     *    - newNode.random = copyRandomList(head.random)
     *    - 递归创建 random 指向的节点
     *    - 如果 random 为 null，返回 null
     *    - 如果 random 指向的节点已创建，从哈希表获取
     *
     * 6️⃣ 深拷贝 vs 浅拷贝？
     *    - 浅拷贝：只复制节点值，next 和 random 指向原节点
     *    - 深拷贝：所有节点都是新创建的，与原链表完全独立
     *    - 本题要求深拷贝，必须创建新节点
     *
     * 7️⃣ 与迭代方法对比？
     *    - 迭代方法（三步法）：
     *      1. 在每个原节点后插入新节点
     *      2. 设置新节点的 random 指针
     *      3. 分离原链表和新链表
     *    - 迭代：时间 O(n)，空间 O(1)（不算输出空间）
     *    - 递归：时间 O(n)，空间 O(n)（哈希表 + 递归栈）
     *    - 递归代码更简洁，但空间开销更大
     *
     * 8️⃣ 完整流程总结：
     *
     *    基本情况：
     *    ├─ head == null → 返回 null
     *    └─ head 已存在 → 返回 cacheNode[head]
     *
     *    递归步骤：
     *    ├─ 创建新节点 newNode
     *    ├─ 存入哈希表：cacheNode[head] = newNode
     *    ├─ 递归创建 next：newNode.next = copyRandomList(head.next)
     *    └─ 递归创建 random：newNode.random = copyRandomList(head.random)
     *
     *    返回：newNode
     * </pre>
     *
     * <h3>⚠️ 注意事项</h3>
     * <ul>
     *   <li><b>哈希表作用</b>：缓存原节点到新节点的映射，避免重复创建</li>
     *   <li><b>递归顺序</b>：先创建节点并存入哈希表，再递归 next 和 random</li>
     *   <li><b>终止条件</b>：head 为 null 或 head 已在哈希表中</li>
     *   <li><b>深拷贝</b>：必须创建新节点，不能直接引用原节点</li>
     *   <li><b>成员变量</b>：cacheNode 是成员变量，在多次调用时需要清空</li>
     *   <li><b>random 为 null</b>：正确处理 random 指针为 null 的情况</li>
     * </ul>
     *
     * @param head 原链表的头节点
     * @return 深拷贝后的链表头节点
     */
    public Node copyRandomList(Node head) {
        // 🔍 终止条件1：空节点，返回 null
        if (head == null) {
            return null;
        }

        // 🔍 终止条件2：节点已创建过，直接从缓存中返回
        if (!cacheNode.containsKey(head)) {
            // ✨ 创建新节点
            Node newNode = new Node(head.val);
            // 📦 存入哈希表，建立原节点到新节点的映射
            cacheNode.put(head, newNode);

            // 🔗 递归创建 next 指针指向的节点
            newNode.next = copyRandomList(head.next);
            // 🎲 递归创建 random 指针指向的节点
            newNode.random = copyRandomList(head.random);
        }

        // ✅ 返回新节点
        return cacheNode.get(head);
    }
}

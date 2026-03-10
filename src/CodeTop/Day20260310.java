package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/linked-list-cycle-ii/description/">142. 环形链表II</a>
 * <p>
 * 给定一个链表的头节点 head，返回链表开始入环的第一个节点。如果链表无环，则返回 null。
 *
 * <p>
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
 *
 * <p>
 * 不允许修改 链表。
 *
 * <p>
 * <b>解题思路：</b>
 * <p>
 * 本题使用<b>Floyd 判圈算法</b>（快慢指针法），分为两个阶段：
 * <ol>
 *     <li><b>第一阶段：判断是否有环</b>
 *         <ul>
 *             <li>使用快慢指针，fast 每次走 2 步，slow 每次走 1 步</li>
 *             <li>如果有环，两指针必定在环内相遇；如果无环，fast 会先到达链表末尾</li>
 *         </ul>
 *     </li>
 *     <li><b>第二阶段：找到环的入口点</b>
 *         <ul>
 *             <li>当 fast 和 slow 相遇时，从相遇点和链表头部分别派出两个指针</li>
 *             <li>这两个指针每次都向前走 1 步，它们会在环的入口处相遇</li>
 *             <li><b>数学原理：</b>假设从头到环入口距离为 a，环入口到相遇点距离为 b，相遇点再回到环入口距离为 c
 *                 <ul>
 *                     <li>slow 走的距离：a + b</li>
 *                     <li>fast 走的距离：a + b + k(b + c)，其中 k 是 fast 在环内多转的圈数</li>
 *                     <li>因为 fast 速度是 slow 的 2 倍，所以 2(a + b) = a + b + k(b + c)</li>
 *                     <li>化简得：a = (k -1)(b + c) + c，即从头走到入口的距离等于从相遇点到入口的距离</li>
 *                 </ul>
 *             </li>
 *         </ul>
 *     </li>
 * </ol>
 * <p>
 * <b>核心逻辑：</b>
 * <ol>
 *     <li><b>快慢指针移动：</b>fast 每次走 2 步，slow 每次走 1 步，在环内必定相遇</li>
 *     <li><b>寻找环入口：</b>相遇后，一个指针从 fast 位置出发，另一个从 head 出发，同步前进，首次相遇点即为环入口</li>
 *     <li><b>边界处理：</b>如果 fast 或 fast.next 为 null，说明链表无环，返回 null</li>
 * </ol>
 * <p>
 * <b>时间复杂度：</b>O(n)，其中 n 是链表长度。第一阶段和第二阶段都最多遍历一次链表<br>
 * <b>空间复杂度：</b>O(1)，只使用了常数个额外指针
 *
 * @author lxd
 **/
public class Day20260310 {
    /**
     * 检测链表中的环并返回环的入口节点
     *
     * @param head 链表头节点
     * @return 环的入口节点，如果无环则返回 null
     */
    public ListNode detectCycle(ListNode head) {
        // 初始化快慢指针都指向头节点
        ListNode fast = head;
        ListNode slow = head;

        // 第一阶段：使用快慢指针判断是否有环
        // fast 每次走 2 步，slow 每次走 1 步
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            // 如果快慢指针相遇，说明链表有环
            if (fast == slow) {
                // 第二阶段：找到环的入口点
                // index1 从相遇点出发，index2 从头节点出发
                ListNode index1 = fast;  // 相遇点
                ListNode index2 = head;  // 头节点

                // 两个指针同步前进，相遇点即为环入口
                while (index1 != index2) {
                    index1 = index1.next;
                    index2 = index2.next;
                }
                // 返回环的入口节点
                return index1;
            }
        }

        // 如果 fast 到达链表末尾，说明链表无环
        return null;
    }
}

package CodeTop;

/**
 * <a href="https://leetcode.cn/problems/add-two-numbers/description/">2. 两数相加</a>
 * <p>
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * <p>
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * <p>
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * <h2>解题思路</h2>
 * <h3>1. 题目分析</h3>
 * <ul>
 *     <li><strong>逆序存储</strong>：个位、十位、百位...依次存储在链表中（头节点是个位）</li>
 *     <li><strong>每位一位数字</strong>：每个节点存储 0-9 的数字</li>
 *     <li><strong>模拟加法</strong>：类似于小学数学的竖式加法，从低位到高位逐位相加</li>
 * </ul>
 * <p>
 * <h3>2. 核心算法</h3>
 * <h4>(1) 使用哑节点（dummy head）</h4>
 * <ul>
 *     <li>创建一个虚拟头节点，简化边界处理</li>
 *     <li>使用 tail 指针追踪结果链表的尾部</li>
 * </ul>
 * <p>
 * <h4>(2) 逐位相加</h4>
 * <ul>
 *     <li>同时遍历两个链表，对应位置相加</li>
 *     <li>如果某个链表已经遍历完，则其对应位置值为 0</li>
 *     <li>考虑进位值 carry，每次相加都要加上上一次的进位</li>
 * </ul>
 * <p>
 * <h4>(3) 处理进位</h4>
 * <ul>
 *     <li><strong>当前位的值</strong>：(val1 + val2 + carry) % 10</li>
 *     <li><strong>新的进位</strong>：(val1 + val2 + carry) / 10</li>
 *     <li>即使两个链表都遍历完，如果还有进位，需要继续处理</li>
 * </ul>
 * <p>
 * <h3>3. 算法示意图</h3>
 * <pre>
 * 示例 1：l1 = [2,4,3], l2 = [5,6,4]
 * (表示 342 + 465 = 807)
 *
 * 初始状态:
 *   dummy -> ?
 *   tail = dummy
 *   carry = 0
 *
 * 第一轮（个位）:
 *   l1: 2 → 4 → 3
 *   l2: 5 → 6 → 4
 *   val1=2, val2=5, carry=0
 *   newVal = 2+5+0 = 7
 *   新节点：7
 *   carry = 7/10 = 0
 *   dummy -> [7] -> ?
 *            ↑
 *           tail
 *
 * 第二轮（十位）:
 *   l1: 4 → 3
 *   l2: 6 → 4
 *   val1=4, val2=6, carry=0
 *   newVal = 4+6+0 = 10
 *   新节点：0 (10%10)
 *   carry = 10/10 = 1
 *   dummy -> [7] -> [0] -> ?
 *                   ↑
 *                  tail
 *
 * 第三轮（百位）:
 *   l1: 3
 *   l2: 4
 *   val1=3, val2=4, carry=1
 *   newVal = 3+4+1 = 7
 *   新节点：7
 *   carry = 7/10 = 0
 *   dummy -> [7] -> [0] -> [7] -> ?
 *                         ↑
 *                        tail
 *
 * 结束：返回 dummy.next = [7,0,7]
 * </pre>
 * <p>
 * <h3>4. 复杂度分析</h3>
 * <ul>
 *     <li><strong>时间复杂度</strong>：O(max(m,n))，其中 m 和 n 分别是两个链表的长度</li>
 *     <li><strong>空间复杂度</strong>：O(max(m,n))，创建新链表存储结果</li>
 * </ul>
 *
 * @author lxd
 **/
public class Day20260326 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 📦 创建哑节点（虚拟头节点），简化边界处理
        ListNode dummy = new ListNode();
        // 👆 tail 指针始终指向结果链表的最后一个节点
        ListNode tail = dummy;
        // 🔢 进位值，初始为 0
        int carry = 0;

        // 🔄 循环条件：l1 或 l2 未遍历完，或还有进位需要处理
        while (l1 != null || l2 != null || carry != 0) {
            int val1 = 0, val2 = 0;

            // ⬇️ 如果 l1 未遍历完，取当前节点值并移动指针
            if (l1 != null) {
                val1 = l1.val;
                l1 = l1.next;
            }

            // ⬇️ 如果 l2 未遍历完，取当前节点值并移动指针
            if (l2 != null) {
                val2 = l2.val;
                l2 = l2.next;
            }

            // ➕ 计算当前位的总和（包括进位）
            int newVal = val1 + val2 + carry;

            // 📝 创建新节点，存储当前位的值（对 10 取余）
            tail.next = new ListNode(newVal % 10);

            // 👉 移动 tail 指针到新节点
            tail = tail.next;

            // 🔢 计算新的进位值（除以 10）
            carry = newVal / 10;
        }

        // ✅ 返回结果链表的头节点（哑节点的下一个节点）
        return dummy.next;
    }
}

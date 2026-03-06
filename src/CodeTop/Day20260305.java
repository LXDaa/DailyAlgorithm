package CodeTop;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/restore-ip-addresses/description/">93. 复原IP地址</a>
 * <p>
 * 有效 IP地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
 * <p>
 * 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP地址，但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 无效 IP地址。
 * 给定一个只包含数字的字符串 s ，用以表示一个 IP地址，返回所有可能的有效 IP地址，这些地址可以通过在 s 中插入 '.' 来形成。你 不能 重新排序或删除 s 中的任何数字。你可以按 任何 顺序返回答案。
 *
 * <p>
 * <b>解题思路：</b>
 * <p>
 * 本题是典型的回溯算法问题，需要在字符串中插入 3 个点，将其分成 4 段，每段都是一个有效的 IP地址部分。
 * <p>
 * <b>核心逻辑：</b>
 * <ol>
 *     <li><b>有效性判断：</b>每个 IP 段必须满足：
 *         <ol>
 *             <li>长度在 1-3 之间</li>
 *             <li>数值范围在 0-255 之间</li>
 *             <li>不能有前导 0（除非就是单个的 "0"）</li>
 *         </ol>
 *     </li>
 *     <li><b>剪枝优化：</b>在搜索过程中，如果剩余字符数量不满足要求，提前终止：
 *         <ol>
 *             <li>剩余字符数 < 剩余段数：不够分了</li>
 *             <li>剩余字符数 > 剩余段数 × 3：每段最多 3 个字符，肯定超了</li>
 *         </ol>
 *     </li>
 *     <li><b>回溯框架：</b>使用 DFS 遍历所有可能的分割点，每次尝试截取 1-3 个字符作为一段</li>
 *     <li><b>终止条件：</b>当已经分成 4 段且用完所有字符时，找到一个有效解</li>
 * </ol>
 *
 * @author lxd
 **/
public class Day20260305 {

    /**
     * 存储所有有效的 IP地址结果
     */
    List<String> res = new ArrayList<>();

    /**
     * 复原IP地址的主函数
     *
     * @param s 输入的数字字符串
     * @return 所有可能的有效 IP地址列表
     */
    public List<String> restoreIpAddresses(String s) {
        // IP地址最长 12 位数字（4 段 × 3 位），超过直接返回
        if (s.length() > 12)
            return res;
        // 从索引 0 开始深度优先搜索，初始化段索引为 0，路径数组长度为 4
        dfs(s, 0, 0, new String[4]);
        return res;
    }

    /**
     * 深度优先搜索函数，用于回溯生成所有可能的 IP地址组合
     *
     * @param s     原始输入字符串
     * @param start 当前处理的起始位置
     * @param idx   当前已生成的 IP 段索引（0-3）
     * @param path  存储当前已生成的 4 个 IP 段的数组
     */
    void dfs(String s, int start, int idx, String[] path) {
        // 终止条件：已经生成了 4 个 IP 段
        if (idx == 4) {
            // 检查是否刚好用完所有字符
            if (start == s.length()) {
                // 将 4 个段用点连接成完整的 IP地址
                res.add(path[0] + "." + path[1] + "." + path[2] + "." + path[3]);
                return;
            }
        }

        // 计算剩余字符数量
        int remain = s.length() - start;
        // 剪枝：剩余字符数量必须在 [剩余段数，剩余段数×3] 范围内
        if (remain < 4 - idx || remain > (4 - idx) * 3)
            return;

        // 尝试截取长度为 1、2、3 的子串作为当前 IP 段
        for (int len = 1; len <= 3 && start + len <= s.length(); len++) {
            // 截取当前段
            String seg = s.substring(start, start + len);

            // 验证当前段的有效性：
            // 1. 长度大于 1 时不能有前导 0（如 "01", "001" 无效）
            // 2. 数值不能超过 255
            if ((len > 1 && seg.charAt(0) == '0') || Integer.parseInt(seg) > 255)
                break;

            // 将当前段放入路径数组
            path[idx] = seg;
            // 递归处理下一段：起始位置后移 len 位，段索引 +1
            dfs(s, start + len, idx + 1, path);
            // 回溯：path 数组会在下次循环中被覆盖，无需显式回溯
        }
    }

}

## Description

Hong has learned something about music. He finds that punchline is very important.

If a substring appears 3 times at the beginning, the middle, and the end of a lyric, the substring is a punchline.

But it’s hard for Hong to find a punchline. He wants you to help him find the longest punchline in a song.

There is no overlap among the substrings.

### Input

The first line will be an integer T (1 <= T <= 100), which is the number of test cases.

For each test data:

The first line contains an integer n (1 <= n <= 10^5) — the length of the string s.

The second line is the lyric containing string s of length n, which consists of lowercase letters only.

### Output

For each case, please print the length of the longest punchline.

### Sample Input

```log
2
6
ababab
7
abababa
```

### Sample Output

```log
2
1
```

> 这里case2输出不是3, 而是1, 因为aba是 {0,1,2}, {2,3,4}, {4,5,6} 三段, 有重叠

## 解答（AC 版本：lps 边界回退 + Z-Algorithm 严格判定）

目标：找到最长子串 x，使其同时满足
- 是 s 的前缀；
- 是 s 的后缀；
- 在中间再出现一次；
- 三次出现两两不重叠（no overlap）。

核心分两步：
1) 用 KMP 的前缀函数 lps 找到所有“边界”（同时为前缀和后缀的子串长度），并按“越长越优”的顺序尝试。
2) 对每个候选长度 len，用 Z-Algorithm 严格判断“中间是否存在一段与前缀相等的子串”且不与前缀/后缀重叠。

记 n = |s|。
- lps[i] 表示 s[0..i] 的最长真前后缀长度，因此最长边界为 L0 = lps[n-1]，次长边界为 lps[L0-1]，以此类推。
- Z 数组 z[j] 表示 s[j..] 与 s[0..] 的最长公共前缀长度。

不重叠约束的区间化：
- 若答案长度为 len，则三段区间为
  - 前缀：[0, len-1]
  - 中间：[j, j+len-1]
  - 后缀：[n-len, n-1]
- 为不重叠，需满足 j ≥ len 且 j+len-1  <=  n-len-1，即中间“起点” j ∈ [len, n-2*len]。
- 用 Z 判断：存在 j ∈ [len, n-2*len] 使得 z[j] ≥ len，则中间合法出现一次。

算法流程：
1) 计算整串 s 的 lps 与 z；
2) 从 len = lps[n-1] 开始，循环：
   - 若 len == 0，则无解（返回 0）。
   - 令区间 J = [len, n-2*len]，若 J 非空且 max(z[j] | j∈J) ≥ len，则返回 len；
   - 否则 len = lps[len-1]（退到更短的边界），继续判断，直到找到或为 0。

实现加速：
- 为了 O(1) 查询任意区间 J 的 max(z[j])，可对 z 构建稀疏表（RMQ）。整体复杂度 O(n log n)，常数小且足够通过；若更偏向简洁实现，也可直接线性扫描 J（配合边界回退，通常也能过）。

正确性要点：
- 边界保证了“前缀=后缀”;
- Z[j] ≥ len 保证了“中间的长度为 len 的子串等于前缀”;
- j 的取值范围 [len, n-2*len] 保证三段不重叠;
- 从最长边界往下回退，首次命中的即为“尽可能长”的答案。

样例解释：
- s = "ababab"：取 len=2 的 "ab"，三段分别为 [0..1]、[2..3]、[4..5]，互不重叠，答案 2。
- s = "abababa"：
  - "aba" 虽出现三次，但两两重叠，不合法；
  - "ab" 不是后缀；
  - 只能取 "a"，三段 [0..0]、[2..2]、[6..6] 不重叠，答案 1。

常见坑点：
- 忽略“中间段不重叠”的位置约束，导致把重叠的三次出现也算进来（如把 "aba" 判为合法）;
- 只回退一次边界，未沿 lps 链持续回退;
- 把“是否存在区间内 ≥ len”写成前缀最大值的启发式，可能误判。Z+区间最大查询是更稳妥的写法。

复杂度：
- 计算 lps 与 z 均为 O(n)；
- RMQ 预处理 O(n log n)，查询 O(1)；
- 沿边界链回退总步数 O(n)；
- 整体 O(n log n)，空间 O(n log n)。若改为线性扫描区间 J，可降为 O(n) 代码体量更小（但最坏常数略大）。

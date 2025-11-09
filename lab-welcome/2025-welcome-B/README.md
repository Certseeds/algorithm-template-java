# Search Problem II

## Description

Given two arrays A with length n and B with length T.

We want to know whether each element in array B is in array A or not.

Since the input is large, you may need fast I/O (in Java).

Java Faster I/O is shown via following links: <https://pastebin.ubuntu.com/p/zBd3g4j366/>

### Input

The 1st line is a positive integer $n$ $ ( n \leq 10^6)$.

The 2nd line contains $n$ integers: $ a_1,a_2...a_n$.

For each $a_i$, $0 \leq a_i \leq 10^5$.

The 3rd line contains a positive integer $T$ $(T \leq 10^6)$.

The 4th line contains $T$ integers $b_1,b_2,...b_T$.

For each $b_i$, $0 \leq b_i \leq 10^5$.

### Output

For each element in $B$, print "yes" (in a single line, without quotes) if it is in $A$.

Otherwise print "no".

### Sample Input

``` log
4
2 3 9999 1
2
3 99
```

### Sample Output

``` log
yes
no
```

### HINT

Note that the integer value ranges are different in Problem 1 and 2.

C++使用cin/cout关同步方法:

``` cpp
ios_base::sync_with_stdio(0), cin.tie(0), cout.tie(0);
```

endl 要换成 '\n'

## 题目分析

- 本质：同题 A——集合成员查询.但规模更大，需要关注 I/O 和常数开销.
- 规模与边界：n, T ≤ 10^6；数值范围 0 ≤ a_i, b_i ≤ 10^5，可用数组做存在标记.
- 判定标准：逐查询输出小写 yes/no，每行一个，无多余空白.

解法选择

- 方案一（推荐）：存在数组/位图
  - 开一个 boolean 数组 present[100001] 或 BitSet，遍历 A 将对应下标置 true.
  - 对每个 b_i 直接 O(1) 访问 present[b_i] 输出.
  - 正确性：等价于集合成员关系；重复元素无影响.
- 方案二：HashSet
  - 也可行，但 Java 包装类型与哈希开销在 10^6 级别更大，不如方案一稳健.

复杂度

- 方案一：时间 O(n + T)，空间 O(U)，其中 U = 100001
- 方案二：时间均摊 O(n + T)，空间较大（装箱 + 哈希结构开销）.

实现要点（Java）

- 快速 I/O：
  - 输入：使用 BufferedInputStream + 自定义 FastScanner（按字节解析）或 BufferedReader + 手写分词，避免 java.util.Scanner.
  - 输出：用 StringBuilder 累积答案，最后一次性输出到 System.out；避免逐行 flush.
- 数据结构：
  - boolean[] present = new boolean[100001]; 索引即数值.
  - 读入时：present[a] = true；查询时：present[b] ? yes : no.
- 边界：
  - 严格保证索引范围（0 ≤ x ≤ 100000）才访问数组；题面保证范围内，仍建议在调试期断言.

易错点排查清单

- 使用 Scanner/逐行 println 导致超时.
- new 大数组放在循环内部重复分配，导致性能与内存抖动.
- 输出大小写或多余空格不符合要求.
- 值域写错导致数组越界（应为 100001）.

小结

- 直接用存在数组 + 快速 I/O 是最简洁与高性能的做法；输出批量化以减少 I/O 开销，即可通过所有大数据点.

## Description

Given two arrays $A$ with length $n$ and $B$ with length $T$.

We want to know whether each element in array $B$ is in array $A$ or not.

### Input

The 1st line is a positive integer $n$ $(1 \leq n \leq 1000)$.

The 2nd line contains $n$ integers: $a_1, a_2, ..., a_n$.

For each $a_i$, $0 \leq a_i \leq 10^9$.

The 3rd line contains a positive integer $T$ $(1 \leq T \leq 1000)$.

The 4th line contains $T$ integers $b_1, b_2, ..., b_T$.

For each $b_i$, $0 \leq b_i \leq 10^9$.

### Output

For each element in $B$, print "yes" (in a single line, without quotes) if it is in $A$.

Otherwise print "no".

### Sample Input

``` log
4
2 3 999999 1
2
3 99
```

### Sample Output

``` log
yes
no
```

## 题目分析

- 本质：给定集合 A 和若干查询 $b_i$, 询问每个 $b_i$ 是否属于 A.
- 规模与边界：$n, T \leq 1000$: 数值范围 $0 \leq a_i, b_i \leq 1e9$, 均可用 int 表示.
- 判定标准：每个查询独立判断, 输出严格为小写 yes/no, 每行一个结果, 无多余空格或提示.

解法

- 方案一：哈希表判重
  - 用 HashSet 存储 A 的所有元素.
  - 依次查询每个 $b_i$ 是否在集合中.
  - 正确性：集合成员查询语义直接对应题意: A 中重复元素不影响结果.

复杂度

- 方案一：O(n + T) 时间, O(n) 额外空间.

实现要点（Java）

- I/O：规模小, Scanner/BufferedReader 都可: 按空白分隔读取, 勿依赖“每行精确个数”的假设.
- 输出：严格逐行 yes/no, 注意不要输出多余空格或额外换行.
- 数据类型：int 足够（范围 <= 1e9）, 无需使用 long.

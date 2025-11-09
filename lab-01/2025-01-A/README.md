# Binary Search

## Description

Given a nondecreasing sequence $a$ with length $n$.

We want to know whether integer $x$ in the array $a$ or not.

## Input

The 1st line is a positive integer $n$ $1 \leq n \leq 100000$.

The 2nd line contains $n$ integers: $a_1, a_2, \ldots, a_n$.

For each $a_i$ $0 \leq a_i \leq 10^9$.

The 3rd line is a positive integer $T$ $1 \leq T \leq 100000$ which is the number of test case.

Then $T$ lines follow.

Each contains an integer $x$ $1 \leq x \leq 10^9$ for a test case.

## Output

For each test case, print "YES" (in a single line, without quotes) if $x$ is in $a$.

Otherwise print "NO".

### Sample Input

``` log
4
1 2 3 999999
2
99
3
```

### Sample Output

``` log
NO
YES
```

### 解释算法

本题要求判断多个查询数是否在给定的非递减数组 a 中。

实现思路如下：

1. 首先用快读读入 n 和数组 a，T 组查询和每个查询的 x。
2. 用 HashSet 存储所有 a[i]，这样每次查询 x 是否在 a 中可以 O(1) 判断。
3. 对每个查询 x，set.contains(x) 为 true 输出 YES，否则输出 NO。
4. 所有输入输出均用快读快写，断言保证所有输入范围。

时间复杂度 O(n+T)，空间 O(n)。

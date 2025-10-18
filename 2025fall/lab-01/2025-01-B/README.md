## Description

Given a non-decreasing array $A$, containing $N$ positive integers $a_1, a_2, \ldots, a_n$.

There are $Q$ queries.

Each query gives $x, y$, you are asked to check how many integers in $A$ which are satisfying $x < a_i < y$.

### Input

The first line of the input contains two integers $N$ and $Q$ $1 \leq N \leq 10^5, 1 \leq Q \leq 10^5$ -- the length of $A$ and the number of query.

The second line contains $N$ integers $a_i$ $1 \leq a_i \leq 10^9$.

The $i$-th of the next $Q$ lines contains two integers $x_i, y_i$ $1 \leq x \leq y \leq 10^9$.

### Output

For each query, print "NO" (without quote) if none of integers in $A$ satisfied the condition.

Otherwise, print "YES" (without quote) and the number of satisfied integers.

### Sample Input

``` log
5 3
1 2 3 4 5
3 5
1 6
5 5
```

### Sample Output

``` log
YES 1
YES 4
NO
```

### 解释算法

本题要求支持多次区间计数查询，判断有多少 a[i] 满足 x < a[i] < y。

实现思路如下：

1. 用快读读入 n、q 和非递减数组 a，q 组查询 (x, y)。
2. 对每个查询，分别用二分查找 upper_bound(a, x) 找第一个大于 x 的下标，lower_bound(a, y) 找第一个大于等于 y 的下标。
3. 区间内元素个数为 right - left。
4. 若区间为空输出 NO，否则输出 YES 和个数。
5. 所有输入输出均快读快写，断言保证输入范围。

时间复杂度 O(q log n)，空间 O(n)。

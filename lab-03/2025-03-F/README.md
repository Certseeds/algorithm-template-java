# Non-decreasing sequence

## Description

Given a sequence $\{a_i\}$ of length $n$.

We define $a[i]$ is a decreasing number if $a[i-1]>a[i]$ ($i-1 > 0$) or $a[i]>a[i+1]$ ($i+1\leq n$).

Please process the given sequence by deleting the decreasing numbers in it.

In each turn you should delete all the decreasing numbers in the given sequence at the same time.

The processing procedure will be terminated until the given sequence does not have decreasing number.

### Input

The first line of input contains an integer $T$ ($1\leq T\leq 10$) which is the total number of test cases.

For each test case, there are two lines.

The first line contains an integer $n$, $n \leq 100000$.

The second line contains $n$ integers, representing the sequence $\{a[i]\}$ ($1\leq a_i\leq 100000$).

### Output

One line per test case, represents the final result.

### Sample Input

```log
2
10
1 4 4 3 2 8 9 4 5 7
5
1 8 6 2 4
```

### Sample Output

```log
1 4 7
1 4
```

### 算法分析

本题用双向链表真实模拟，链表节点通过指针连接，支持 $O(1)$ 插入、删除。每轮批量收集所有需删除节点，统一断链，下一轮只考察邻接节点。辅助数据结构：

- ArrayDeque<Node> workQueue：队列，存储待处理节点，接口 addLast/removeFirst，均为 $O(1)$
- HashSet<Node> seen：集合，判重，接口 add/contains，均为 $O(1)$

整体时间复杂度近线性，空间 $O(n)$。断言保证输入范围。

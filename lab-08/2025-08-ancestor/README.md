# Ancestor

## Description

Ella has a tree of $n$ nodes numbered from $1$ to $n$.

There are $m$ queries, and each query has two integers $x$ and $y$ to ask whether $y$ is an ancestor of $x$ or not, and $x$ is an ancestor of $x$.

However, Ella does not know how to deal with it.

Please find a solution.

### Input

The first line will be an integer $T$, which is the number of the test cases $(1 \le T \le 10)$.

For each test case, the first line will be two integers $n$ $(1 \le n \le 1.5 \times 10^5)$ and $m$ $(1 \le m \le 1.5 \times 10^5)$.

The following are $n - 1$ lines, and each line will be two integers $x$ and $y$, which means that $y$ is the father of $x$.

The following are $m$ lines, and each line will be two integers $x$ and $y$, which means a query $(x, y)$.

If $y$ is an ancestor of $x$, output Yes, else output No.

### Output

For each test output $m$ lines to answer the queries.

### Sample Input

``` log
1
2 1
2 1
1 2
```

### Sample Output

``` log
No
```

## 算法分析

### 问题理解

给定一棵有根树, 包含 n 个节点, 需要回答 m 个查询, 每个查询判断节点 y 是否是节点 x 的祖先.

注意: x 是 x 自己的祖先.

### 算法思路

使用 DFS 序 (欧拉序) 来判断祖先关系:

1. DFS 遍历: 对树进行深度优先遍历, 记录每个节点的:
   - inTime[v]: 进入节点 v 的时间戳 (第一次访问)
   - outTime[v]: 离开节点 v 的时间戳 (回溯时)

2. 祖先判断: y 是 x 的祖先当且仅当:
   - inTime[y] <= inTime[x] 且 outTime[x] <= outTime[y]
   - 即节点 y 的访问时间区间 [inTime[y], outTime[y]] 包含节点 x 的时间区间

3. 图存储: 使用链式前向星存储从父节点指向子节点的有向边

4. 寻找根节点: 遍历所有节点, 找到没有父节点的节点即为根

### 复杂度分析

- 预处理时间复杂度: O(n), DFS 遍历整棵树
- 单次查询时间复杂度: O(1), 只需比较时间戳
- 总时间复杂度: O(n + m)
- 空间复杂度: O(n), 存储树结构和时间戳数组

# The Elves

## Description

To make the kingdom more prosperous, Pisces decides to ally with the elves living in the forest.

However, the elven elders want to test Pisces, so they give him a simple question.

Given a DAG with $n$ nodes and $m$ edges, the elven elders want to know the value of $\sum_{i=1}^{n}\sum_{j=1}^{n}count(i,j) \cdot a_i \cdot b_j \mod (10^9 + 7)$, where $count(x,y)$ is defined by the number of different paths from $x$ to $y$ and $count(x,x) = 0$, and $a$, $b$ are $2$ given arrays.

It is too hard for Pisces to answer this question, so he turns to you for help.

### Input

The first line contains an integer $T$ $(1\leq T \leq 10)$, which denotes the number of test cases.

For each test case, the first line contains $2$ integers $n$ and $m$ $(1 \leq n, m \leq 10^5)$ — the number of nodes and the number of edges, respectively.

Each of the next $n$ lines contains $2$ integers $a_i$ and $b_i$.

And for the next $m$ lines, each line contains $2$ integers $u$ and $v$ denoting a directed edge going from node $u$ to node $v$ $(1\leq u,v \leq n)$.

### Output

For each test case, print the answer.

### Sample Input

``` log
3
3 3
1 1
1 1
1 1
1 2
1 3
2 3
2 2
1 0
0 2
1 2
1 2
2 1
500000000 0
0 500000000
1 2
```

### Sample Output

``` log
4
4
250000014
```

## 算法分析

### 问题理解

给定一个 DAG (有向无环图), 计算:

$\sum_{i=1}^{n}\sum_{j=1}^{n}count(i,j) \cdot a_i \cdot b_j \mod (10^9 + 7)$

其中 count(i,j) 表示从节点 i 到节点 j 的不同路径数, count(i,i) = 0.

### 算法思路

直接枚举所有 (i, j) 对的复杂度是 O(n^2), 需要优化.

公式变换:

$\sum_{i,j} count(i,j) \cdot a_i \cdot b_j = \sum_j b_j \cdot (\sum_i count(i,j) \cdot a_i)$

定义 weightedTo[j] = $\sum_i count(i,j) \cdot a_i$, 即以加权路径数结尾于 j 的总和.

使用拓扑排序 + DP 计算:

1. 状态定义:
   - pathsTo[v]: 以 v 为终点的路径总数 = $\sum_i count(i,v)$
   - weightedTo[v]: 加权路径数 = $\sum_i count(i,v) \cdot a_i$

2. 状态转移: 对于边 u -> v:
   - pathsTo[v] += pathsTo[u] + 1 (继承 u 的所有路径, 加上 u->v 这条新路径)
   - weightedTo[v] += weightedTo[u] + a[u] (继承加权值, 加上新路径的贡献)

3. 最终答案: $\sum_j b_j \cdot weightedTo[j]$

### 复杂度分析

- 时间复杂度: O(n + m), 拓扑排序遍历所有节点和边
- 空间复杂度: O(n + m), 存储图和 DP 数组

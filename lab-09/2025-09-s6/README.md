# s6 Problem

## Description

Given a directed graph of $n$ vertices with $m$ edges and a vertex $S$, Tenshi wants you to find the minimum number of directed edges that can be added so that $S$ can reach all vertices on the graph.

### Input

The first line of input consists of three integers $n, m, S$.

Here, $1\leq n\leq 50000, 0\leq m\leq 50000, 1\leq S\leq n$

The following $m$ lines contain directed edges: edge is given as a pair of vertices $u_i, v_i$

### Output

Output one integer as your answer.

### Sample Input

``` log
5000 2 238
3212 238
238 3212
```

### Sample Output

``` log
4998
```

### 算法分析

题目要求从起点 S 添加最少边使其可达所有顶点. 首先对原图执行 Tarjan 算法分解为强连通分量 SCC, 得到凝聚图.

凝聚图是有向无环图 DAG. 统计每个 SCC 在凝聚图中的入度. 入度为 0 的 SCC 无法从其他 SCC 到达, 必须添加新边才能从 S 到达它们.

如果起点 S 所在的 SCC 入度为 0, 则 S 可直接作为起点无需新增边到达它自己, 因此答案需减去 1.

答案等于入度为 0 的 SCC 数量, 若 S 所在 SCC 入度为 0 则减 1.

时间复杂度: Tarjan O(n + m), 统计入度 O(m), 总复杂度 O(n + m).

空间复杂度: O(n + m) 存储图结构与 Tarjan 状态.

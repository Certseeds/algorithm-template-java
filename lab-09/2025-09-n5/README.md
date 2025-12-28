# n5 problem

## Description

Tenshi is given a connected undirected graph with $n$ vertices $m$ edges (weight of every edge is $1$) by Justin_CaO, and a total of $k$ colors of stones, and exactly one stone (of one of $k$ colors) at each vertex, the cost of transporting a stone from $u$ to $v$ is the shortest path length from $u$ to $v$.

Tenshi is busy with ICPC (International Carrot Placing Contest, prepared by FluffyBunny), so this graph is sent to you!

Your goal is to compute the minimum cost of transporting a stone of at least $c$ colors to $T$ for each vertex $T$ ($T=1, 2, \dots, n$).

### Input

$1\leq n\leq 10^5, 0\leq m\leq 10^5, 1\leq c\leq k\leq min(n,100)$

$4$ integers $n, m, k, c$ in the first line of input

next line there are $n$ integers $a_1,a_2,\ldots,a_n$, where $a_i$ is the color of $i$-th vertex's stone

In the next $m$ lines edges described, edge is described by two integers $u, v$

### Output

Print $n$ numbers T-th of them is the minimum cost of vertex T

Separate numbers with spaces.

### Sample Input

``` log
10 10 3 2
2 2 2 3 2 3 2 2 1 2
9 7
6 5
2 3
2 5
6 4
1 10
9 10
8 5
5 1
9 6
```

### Sample Output

``` log
2 2 3 2 1 1 1 2 1 1
```

#### input

``` log
5 6 3 3
3 3 3 2 1
2 1
3 2
4 3
5 4
3 5
3 4
```

#### output

``` log
6 4 2 2 2
```

### 算法分析

题目要求对每个目标顶点 T 计算收集至少 c 种颜色的最小代价, 代价为各颜色石头到 T 的最短路径之和.

首先按颜色分组, 对每种颜色执行多源 BFS. 从该颜色所有顶点同时出发, 计算每个顶点到该颜色最近石头的距离. 使用 `dist[color][node]` 存储顶点 node 到颜色 color 的最短距离.

对每个目标 T, 提取 T 到各颜色的距离, 按升序排序后取前 c 个最小值求和, 即为 T 的答案.

时间复杂度: k 次 BFS 每次 O(n + m), 对每个顶点排序 k 个距离 O(k log k), 总复杂度 O(k(n + m) + nk log k).

空间复杂度: O(kn) 存储距离矩阵和图结构.

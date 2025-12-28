# portal

## Description

Yuki is a magical girl and she has the ability to activate portals.

The country Yuki lives in has $n$ cities and $m$ roads at certain distances.

The cities are numbered from $1$ to $n$ and all the roads are unidirectional, that is a road from $u$ to $v$ cannot be passed from $v$ to $u$.

Also, there are $p$ portals in the country, each of them connects two cities unidirectional with no distance.

Since Yuki doesn't grasp magic thoroughly, she can activate at most $k$ portals.

Now Yuki is curious about what is the minimum distance between $S$ and $T$ if she activates at most $k$ portals.

### Input

The first line contains four integers: $n$, $m$, $p$ and $k$ $(1\leq n,m,p\leq 50\ 000,0\leq k\leq10)$ - the number of cities, roads, portals and the number of portals Yuki can activate at most.

Each of the next $m$ lines contains three integers: $u$, $v$ and $w$ $(1 \leq u,\ v\leq n,1\leq w\leq 1\ 000\ 000)$, meaning that there is a unidirectional road from city $u$ to city $v$ at distance $w$.

Each of the next $p$ lines contains two integer: $u$ and $v$ $(1 \leq u,\ v\leq n)$, meaning that there is an inactive portal from city $u$ to city $v$.

Please note that when it is active, Yuki can only be teleported from city $u$ to $v$ unidirectionally.

The last line contains two integers: $S$ and $T$ $(1\leq S,T\leq n)$ - the origin and destination.

### Output

Print one line with the result - the minimum distance between city $S$ and $T$.

It is guaranteed that Yuki can move from city $S$ to $T$ by activating at most $k$ portals.

### Sample Input

``` log
5 6 3 1
1 3 4
1 2 2
3 5 6
2 4 3
3 4 1
4 5 2
2 3
1 4
1 2
1 5
```

### Sample Output

``` log
2
```

### 算法分析

本题是带状态限制的最短路问题: 最多使用 k 个传送门.

1. 状态定义:
   - 设 dist[u][j] 表示到达节点 u 且已使用 j 个传送门时的最短距离.
   - 状态空间: $O(n \times (k+1))$.

2. 算法: 分层 Dijkstra.
   - 初始状态: dist[S][0] = 0.
   - 转移:
     - 普通道路: dist[v][j] = min(dist[v][j], dist[u][j] + w)
     - 传送门: dist[v][j+1] = min(dist[v][j+1], dist[u][j]), 当 j < k.
   - 答案: $\min_{j=0}^{k} dist[T][j]$.

3. 数据结构:
   - 使用两个链式前向星分别存储道路和传送门.
   - 使用优先队列按距离排序.

4. 复杂度分析:
   - 时间复杂度: $O((n \times k + m + p) \log(n \times k))$.
   - 空间复杂度: $O(n \times k + m + p)$.

5. 注意事项:
   - 传送门的使用相当于 0 权边, 但会消耗一次使用次数.
   - 题目保证从 S 到 T 可达.

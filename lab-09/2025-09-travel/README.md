# travel

## Description

There are n cities and m roads, and each road has a length.

These n cities are numbered from 1 to n.

Please find the shortest path from city_1 to city_n.

### Input

The first line contains two integers n, m $(1\leq n, m \leq 10^5)$, indicating that there are n cities, m roads.

The next m lines, each line contains 3 integers, u, v, w $(1\leq u, v \leq n, 1\leq w \leq 10^9)$, indicating that there is a unidirectional road from u to v, cost w.

### Output

Please output the minimum cost from city_1 to city_n in one line.

If there is not exist a road from city_1 to city_n, please output -1.

### Sample Input

``` log
3 3
1 2 2
2 3 1
1 3 1
```

### Sample Output

``` log
1
```

### 算法分析

本题是经典的单源最短路径问题.

使用 Dijkstra 算法求解从城市 1 到城市 n 的最短路径.

1. 数据结构: 使用链式前向星 (Star) 存储有向图, 使用优先队列维护当前距离最小的节点.

2. 算法流程:
   - 初始化所有节点的距离为无穷大, 起点距离为 0.
   - 使用小根堆按距离排序, 每次取出距离最小的节点进行松弛操作.
   - 对于当前节点的所有出边, 若能更新目标节点的距离则更新并加入队列.

3. 复杂度分析:
   - 时间复杂度: $O((n + m) \log n)$, 使用优先队列优化的 Dijkstra.
   - 空间复杂度: $O(n + m)$, 存储图和距离数组.

4. 注意事项:
   - 边权最大为 $10^9$, 路径总权重可能超过 int 范围, 需使用 long 类型.
   - 若终点不可达则返回 -1.

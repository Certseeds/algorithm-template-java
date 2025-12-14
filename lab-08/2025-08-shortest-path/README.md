# Shortest Path

## Description

Alice has a graph of $n$ nodes and $m$ undirected edges of same length.

These nodes are numbered from $1$ to $n$, and now she wants to know the shortest path from node $s$ to other nodes.

If there is no path between $s$ and node $i$, output $-1$ $(1 \le i \le n)$.

### Input

The first line will be an integer $T$, which is the number of the test cases $(1 \le T \le 10)$.

For each test case, the first line will be three integers, $n$ $(1 \le n \le 10^5)$, $m$ $(1 \le m \le 5 \times 10^5)$ and $s$ $(1 \le s \le n)$.

The following are $m$ lines, and each line will be two integers $x$ and $y$, which means there is an undirected edge between $x$ and $y$.

### Output

For each test, output numbers in one line.

The $i$-th number means the shortest path between $s$ and node $i$.

If the path does not exist, then print $-1$ instead.

### Sample Input

``` log
1
4 4 1
1 2
2 3
3 4
1 4
```

### Sample Output

``` log
0 1 2 1
```

## 算法分析

### 问题理解

给定一个无权无向图, 包含 n 个节点和 m 条边, 要求计算从源点 s 到所有其他节点的最短路径长度.

如果某节点不可达, 输出 -1.

### 算法思路

由于所有边的权重相同 (均为 1), 使用 BFS (广度优先搜索) 即可求解最短路径.

1. 图存储: 使用链式前向星 (star 类) 存储无向图, 每条边存储两次 (双向)

2. BFS 过程:
   - 初始化距离数组 dist, 所有节点距离设为 -1 (表示未访问)
   - 将源点 s 的距离设为 0, 并加入队列
   - 循环取出队首节点 cur, 遍历其所有邻居 next
   - 若 next 未被访问 (dist[next] == -1), 则 dist[next] = dist[cur] + 1, 并将 next 入队

3. 输出: 按节点编号顺序输出距离

### 复杂度分析

- 时间复杂度: O(n + m), BFS 遍历每个节点和每条边各一次

- 空间复杂度: O(n + m), 用于存储图和距离数组

### 数据结构说明

链式前向星通过 head[], to[], next[] 三个数组存储图:

- head[u]: 节点 u 的第一条边的索引

- to[e]: 边 e 指向的目标节点

- next[e]: 与边 e 起点相同的下一条边的索引

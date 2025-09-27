## Description

Fred has a graph of n nodes and m undirected edges (the length of each edge is 1).

These nodes are numbered from 1 to n.

Now, he gives you a node set whose size is K.

He asks you to calculate the shortest path between a node pair belonging to the node set.

### Input

The first line will be an integer T, which is the number of the test cases(1 <= T <= 10).

For each test case, the first line will be three integers and n, m and K(1 <= n <= 300000, 1 <= m <= 400400, 1 <= K <= n).

The following are m lines, and each line will be two integers x and y, which means that there is an undirected edge be x and y.

The next line will be K unique integers , and each integer represents a node in the node set.

### Output

For each test, output the distance of the shortest path.

If the path does not exist, then print -1 instead.

### Sample Input

``` log
1
5 4 2
1 2 
2 3 
3 4
4 5
1 5
```

### Sample Output

``` log
4
```

## 算法说明

1) 链式前向星（class 风格）

- 数据结构: 
  - 定义了一个内部类 `Edge`, 包含两个字段: `to`（目标节点索引）和 `next`（下一条边在边数组中的索引）.
  - 定义了一个 `Graph` 类, 保存: `Edge[] edges`（边数组）, `int[] head`（每个节点首条出边在 edges 中的索引, 未连接时为 -1）, 以及 `int edgePtr`（当前可用的边数组下标）.
  - 构造器会初始化 `edges`（为每个位置 new 一个 Edge）并把 `head` 全部填为 -1.

- 添加边: 
  - `addEdge(int u, int v)` 将一条从 u 指向 v 的有向边插入: 
    edges[edgePtr].to = v;
    edges[edgePtr].next = head[u];
    head[u] = edgePtr++;
  - 为了构造无向图, 程序把每条输入边 (u,v) 同时加入 (u->v) 和 (v->u).
  - 这是把 C 语言风格的链式前向星（结构体数组 + head 数组）用 Java 类封装起来的常见做法, 内存布局与访问模式与原始实现等价, 但更符合 Java 的面向对象风格.

2) 多源 BFS（寻找集合内两点的最短路径）

- 初始状态: 
  - 使用 `dist[n]` 数组记录每个节点到其所属特殊源点的距离, 未访问节点为 -1.
  - 使用 `owner[n]` 数组记录每个节点所属的特殊源点（用源点的索引或编号表示）, 未访问为 -1.
  - 使用队列 `Queue<Integer> q` 做 BFS, 多源入队: 把所有特殊节点（集合中的 K 个节点）距离设为 0, owner 设为自身并入队.

- 扩展与相遇检测: 
  - 在 BFS 中弹出节点 u, 遍历其所有出边（通过 `for (int e = head[u]; e != -1; e = edges[e].next)`）: 
    - 若相邻节点 v 已被访问（owner[v] != -1）: 
      - 若 owner[v] != owner[u], 说明两条来自不同特殊源的 BFS 波前在边 (u,v) 相遇, 此时可以用 dist[u] + 1 + dist[v] 更新答案（两端合并的路径长度）.
    - 若 v 未被访问: 
      - 将 owner[v] = owner[u], dist[v] = dist[u] + 1, 并将 v 入队继续扩展.

- 剪枝优化: 
  - 程序维护当前最优答案 ans（初始为无穷大）. 在处理节点 u 时, 如果 dist[u] * 2 >= ans 则可以提前停止整个 BFS, 因为任意从 u 出发与另一源汇合的最短可能距离至少为 2*dist[u]（保守界）, 已经无法改进 ans.

- 结果: 
  - BFS 结束后若 ans 仍为无穷（Integer.MAX_VALUE）, 说明集合内任意两点之间无连通路径, 返回 -1; 否则输出 ans.

3) 复杂度与注意事项

- 时间复杂度: O(n + m), 每条边在无向图中作为两个有向边被加入, 但总体遍历次数仍为线性级别.
- 空间复杂度: O(n + m), 需要 `head`（长度 n）、`edges`（长度约为 2*m）以及 `dist`、`owner` 等辅助数组.
- 边界情况: 
  - K = 1 或所有特殊节点都处于不同的连通分量（无法相遇）时, 返回 -1.
  - 输入节点编号在 Java 实现中使用 0-based（在 reader 中对输入做了 -1 转换）, 注意与题面 1-based 编号的对应.

4) 小结

- 本实现把传统的链式前向星用 Java 的类封装（Edge + Graph）, 兼顾性能与可读性; 核心算法是多源 BFS, 通过 owner/dist 跟踪不同源的波前并在相遇时计算候选最短路径, 配合简单的剪枝得到高效的实现.

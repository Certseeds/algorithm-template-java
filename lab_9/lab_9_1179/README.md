## Description

Give you an undirected connected graph G(V, E). Each edge e in E has a cost w_e. You are in the 1 th vertex and you want to go to N th vertex. However, you must go through K given vertexes. Please calculate the minimum cost for your journal.

### Input

The first line of the input is an integer T (1 <= T <= 10). T is the number of test cases.
For each test case, the first line is three integers N, M and K (1 <= N <= 500, N <= M <= N^2, 1 <= K <= 5). N is the number of vertexes (numbered from 1 to N) and M is the number of edges. K is the number of vertexs you must go through. Then there will be M lines, each line contains three integers u_i, v_i and w_i, means there is an edge between u_i and v_i and the weight is w_i. Then for next K lines, each line contain an integer x which means you must go through vertex x. We guarantee that 1 <= u_i, v_i <= N, 1 <= w_i <= 1000. The input guarantees that you can alway find at least one leagal path.

### Output

For each test case, print the result problem required.

### Sample Input

```log
1
4 5 1
1 2 1
1 3 2
1 4 3
3 4 2
2 3 2
2
```

### Sample Output

```log
5
```

### 思路分析

将问题抽象为带权无向图上的路径问题. 我们需要从顶点 1 出发, 经过题目给定的 K 个必经顶点（顺序可选）, 最后到达顶点 N, 求最小路径权重和.

主要实现步骤: 

1. 构建邻接表表示的无向带权图, 采用 List<int[]>[] 存储边, 边记录为 {to, weight}.
2. 把关注的特殊顶点集合整理为一个数组 special, 包含起点 1、（去重且排除 1 和 N 的）必经顶点、终点 N, special 的长度为 2 + rk, 其中 rk 为实际需要排列的必经顶点数.
3. 对 special 中的每个顶点运行 Dijkstra, 得到到所有顶点的最短距离; 以此构建 special 到 special 的距离矩阵 distMat.
4. 枚举 rk 个必经顶点的所有排列（rk <= 5, 因此可行）, 计算路径代价: 从 1 到第一个必经点, 再依次到下一必经点, 最后到 N. 若任意一段不可达则跳过该排列.
5. 在所有排列中取最小代价作为答案; 若不存在可达路径则返回 -1（题目保证至少存在一条合法路径, 此处为保险处理）.

实现细节与工程约定: 

- 使用 reader / cal / output 分离, reader 中加入 assert 检查输入范围（遵循仓库约定和 AGENTS.md 要求）.
- Dijkstra 中使用 long 类型保存距离, 使用 INF = Long.MAX_VALUE / 4 作为不可达标识, 优先队列用 Comparator.comparingLong 排序.
- 为避免 Java 对泛型数组创建限制, 邻接表使用带 @SuppressWarnings("unchecked") 的强制转换创建:  (List<int[]>[]) new ArrayList[n + 1].
- 使用 nextPermutation 实现排列枚举, 保证枚举顺序正确且高效.

复杂度分析: 

- 预处理（对 specialCount 个顶点各跑一次 Dijkstra）: O(specialCount * (m log n)), 其中 specialCount = rk + 2.
- 枚举排列并累加路径代价: 最多 rk! * rk 次距离查表, rk <= 5, 开销固定且小.
- 因此总时间复杂度近似 O((rk + 2) * m log n), 空间复杂度 O(n + m + specialCount^2).

边界与注意事项: 

- 必经顶点中可能包含 1 或 N, 代码会去重并排除已包含的端点.
- 支持多重边与不同权值; 当 m = 0 且起点等于终点时应返回 0.
- 由于题目保证存在合法路径, 正常情况下不用返回 -1, 但代码仍对不可达情况做了安全处理.

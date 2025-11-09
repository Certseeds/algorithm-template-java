## Description

Give you several points in 3D space.

The cost of moving from one point to the other is the 100 * Euclidean distance between them.

There are N holes in the space.

Each hole is considered as a "ball".

The cost of moving inside the holes is 0.

Now give you two points S and T.

Please calculate the minimum cost to move from S to T.

If you don't know what Euclidean distance is, you can read the reference here: <https://en.wikipedia.org/wiki/Euclidean_distance>

### Input

The first line of the input is an integer T. (1 <= T <= 10) T is the number of test cases.

For each test case, the first line is one integer N. (1 <= N <= 100).

N is the number of holes (numbered from 1 to N).

Then there will be N lines, each line contains four integers $x_i$, $y_i$, $z_i$ and $r_i$, meaning there is a hole with center ($x_i$, $y_i$, $z_i$) and radius $r_i$.

Then there will be two lines describing the positions of S and T.

We guarantee that 0 < |$x_i$|, |$y_i$|, |$z_i$| <= 1000 and 0 < r_i <= 500.

### Output

For each test case, print the required result. Please round the result to the closest integer.

### Sample Input

```log
2
1
20 20 20 1
0 0 0
0 0 10
1
5 0 0 4
0 0 0
10 0 0
```

### Sample Output

```log
1000
200
```

### 思路分析

把问题建模为带权图上的最短路问题: 将起点 S 作为节点 0, N 个洞作为节点 1..N, 终点 T 作为节点 N+1. 任意两个节点 i, j 之间的移动代价定义为: 

w(i,j) = max(0, EuclideanDistance(i,j) - r_i - r_j) * 100

其中 r_S=r_T=0, EuclideanDistance 是三维欧氏距离; 如果两个球体（洞）相交或贴合, 则移动代价为 0（可以在洞内“免费”移动）.

主要实现步骤: 

1. reader 使用 FastScanner 读入数据, 并在 reader 中加入 assert 检查输入范围（例如 N, 坐标与半径的约束）.
2. 在 cal 中把节点按索引组织（0 为 S, 1..N 为洞, N+1 为 T）.
3. 对任意两个节点动态计算边权（不显式构建 O(N^2) 的邻接表也可, 但这里直接在 Dijkstra 的松弛阶段按需计算权重）, 使用 PriorityQueue 进行 Dijkstra 最短路搜索, 从节点 0 到节点 N+1.
4. Dijkstra 得到浮点型最短距离后, 按题目要求对结果进行四舍五入并输出整数.

复杂度分析: 

- 节点数为 M = N + 2, 图为完全图（任意两节点都可能连边）, 如果在 Dijkstra 中对每个弹出节点遍历所有其它节点则时间复杂度为 O(M^2 log M), 当 N <= 100 时可接受. 空间复杂度为 O(M).

实现要点与注意事项: 

- 使用 double 保存距离, 避免整型截断; 比较时允许小的浮点误差阈值（实现中用 1e-12 的比较保护）.
- 使用 max(0, dist - r_i - r_j) 保证重叠洞间代价为 0.
- 对输入坐标和半径使用 assert 进行边界检查, 符合 AGENTS.md 的工程约定.
- 最终结果使用 Math.round 四舍五入为最近整数再输出.

边界与测试要点: 

- 两点在同一洞内或洞相交时, 代价为 0.  
- 若 S 或 T 恰好位于某洞内（距离小于半径）, 对应 r_S 或 r_T 为 0, 但 gap 仍会被 max(0, ..) 处理为 0.  
- N=0（无洞）或极端坐标值也在断言范围内考虑.

以上总结与当前实现保持一致, 如需我把实现改为显式构建前向星或复用图封装类, 我可以基于此进一步重构代码.

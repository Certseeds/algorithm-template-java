## Description

Grace has a connected graph of n nodes and m undirected edges.

These nodes are numbered from 1 to n.

Each edge has a value.

The graph does not contain self-loop and there is at most one edge between any two nodes.

Addtionally, there are at most two non-intersect paths, which shares no common edges, between any two nodes.

She defines W(x,y) as the minimum cost to let no path between x and y.

The cost to cut one edge is the value of the edge, and once you cut an edge, the edge disappears in the graph.

Every time you need to calculate the minimum cost of a pair, you need to cut the edges from the original graph.

every W is independent.

At last, Grace asks you to calculate this sum of W(i,j)(i != j)

### Input

The first line will be an integer T, which is the number of the test cases(1 <= T <= 10).

For each test case, the first line will be three integers n, m(1 <= n <= 10^5, 1 <= m <= 1.5*(n - 1)).

Then followed by m lines, each line will be three integers x, y and z(1 <= z <= 10^9), it means that there is an undirected edge between x and y whose value is z.

### Output

For each test output the answer for the query in one line.

### Sample Input

```log
1
3 2 
1 2 1
2 3 1
```

### Sample Output

```log
3
```

## 思路解析

### 问题建模

- 原图为 cactus（任意两点之间至多有两条边不相交的路径）. 对任意一对顶点 `(x, y)`, 将它们断开的最小代价记作 `W(x,y)`. 需求是求所有 `i != j` 对的 `W(i,j)` 之和.

### 总体思路

- 将图的边分为桥（bridge）与非桥. 去掉所有桥后, 剩余的连通块要么是单点要么是简单环（cycle）.
- 对同一环内的两个顶点, 最小割等于两条弧上各自的最小边权之和. 对处于不同非桥组件的顶点对, 其最小割由桥链上的最小边决定.
- 因此算法分两部分处理: 环内部成对贡献与跨组件（桥树）成对贡献.

### 主要步骤

- 使用标准 DFS（`tin`/`low`）找出所有桥, 见函数 `dfsBridge`.

- 在“去掉桥”的子图中做连通分量划分, 每个顶点记录其组件 id, 见 `compId` 与 `comps` 的构建.

- 对于每个环组件: 
  - 按环的顺序收集边权数组 `w[0..k-1]`（函数 `buildCycleWeights`）.
  - 目标是计算环上所有顶点对的代价总和. 对于任意两点, 代价为两条弧的最小边权之和. 实现中把环权数组复制为长度 `2k` 的数组, 使用单调栈预处理每个位置左右为严格/非严格更小元素的边界, 然后统计每条边作为区间最小值时出现的次数, 从而在 O(k) 时间内得到该环的总贡献（函数 `sumCycleOrderedMinimaUpToKMinus1`）.

- 对于桥的贡献: 把每个非桥组件视为一个点, 桥构成一棵桥树. 桥上两组件之间路径的最小边权即为这些组件对的最小割. 对所有桥按权值从大到小排序, 使用并查集（`DSU`）合并组件: 当处理权值为 `w` 的桥时, 若把两个 `DSU` 集合合并, 则这两个集合之间任意一对顶点的最小割为 `w`, 因此把 `w * size_u * size_v` 累加到答案（标准的按权合并计数技巧）.

### 辅助与实现细节

- 输入/输出分离: `reader()` 解析测试数据为 `TestCase` 列表, `cal()` 逐项求解, `output()` 批量打印结果.
- 使用 `FastScanner` 提升读入效率; 在 `reader` 中包含断言以做边界检查.

### 复杂度

- 时间复杂度: O(n + m + sum(k_i)) = O(n + m), 其中 `k_i` 为各环长度. 每条边和顶点仅被常数次处理.
- 空间复杂度: O(n + m).

## Description

In your discrete mathematics class, your teacher gives you a question:

There are n propositions.

Now our goal is to prove they have the same equivalence.

We say two propositions x and y are equivalent if x implies y and y implies x.

Now we have m deductions.

An deduction denoted x y means x implies y.

Can you find the minimum deduction times we need to achieve our goal?

If we cannot achieve our goal, print -1.

> [!NOTE]
>
> Some deduction may be the same.

### Input

The first line will be an integer T. (1 <= T <= 50) T is the number of test cases.

For each test case, the first line will be two integers n and m.

n is the number of propositions and m is the number of deductions we have known. (1 <= n <= 20000, 0 <= m <= 50000)

For the following m lines, each line will be two integers x y, meaning we have known x implies y.

### Output

For each test case, print the minimum number of deductions we have to make.

### Sample Input

```log
2
4 4
1 2
2 1
2 3
3 2
2 0
```

### Sample Output

```log
2
2
```

### HINT

For the first sample, we already know 1, 2 and 3 are equivalent.

We need to prove 4 is equivalent to any of them.

We need to do 2 deductions. 4<->1 or 4<->2 or 4<->3 are both legal.

For the second sample, we know nothing. So we need to prove 1<->2 by ourselves.

The answer is 2

## 思路解析

### 问题建模

- 给定有向图的边集（Deduction）表示若干蕴含关系 x -> y. 目标是通过最少的新增蕴含, 使得所有命题归于同一个强连通分量（SCC）, 也就是图强连通.

### 主要思路

- 首先运行 Tarjan 算法把原图划分为若干个 SCC（见 `TarjanSCC` 类）. 每个 SCC 内部节点已两两可达, 无需额外操作.

- 构造缩点后的有向无环图（DAG）: 把每个 SCC 视为一个超级节点, 原来的跨 SCC 边变为 DAG 上的弧. 要使整张图强连通, 必须保证缩点图既无入度为 0 的节点也无出度为 0 的节点.

- 在 DAG 上, 最少需要新增的有向边数等于 max(number_of_sources, number_of_sinks). 其中: 
  - source: 入度为 0 的 SCC 个数; 
  - sink: 出度为 0 的 SCC 个数.
  这是经典结论: 将 DAG 首尾连接, 至少需要这样多条边才能覆盖所有入/出空缺.

### 关键实现点

- 使用邻接的 forward-star 结构压缩存储输入边, 节省内存并加速遍历.
- `TarjanSCC` 类实现标准 Tarjan 算法: 时间戳 `dfn`、`low`、栈与 `inStack` 标记, 用于识别 SCC 并给出 `comp[u]` 映射.
- 缩点后统计每个超级节点的入度与出度, 统计入度为 0 的数量 `sources` 与出度为 0 的数量 `sinks`.
- 若 `compCnt == 1`（已强连通）则返回 0; 否则返回 `Math.max(sources, sinks)`.

### 复杂度

- 时间复杂度: O(n + m), Tarjan 与边/点的线性扫描都在该范围内.
- 空间复杂度: O(n + m), 用于存储图结构与 Tarjan 的辅助栈/数组.

以上为 `Main.java` 解法的要点说明.

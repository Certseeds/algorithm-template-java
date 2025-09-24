## Description

Give you an undirected connected graph $G(V, E)$.

Each edge $e \in E$ has a weight $w_e$.

Let $E^{\prime}$ be an edge set of a spanning tree.

Please write a program to calculate $\min \left(\sum_{e \in E^{\prime}} w_e\right)$ when $\min \left(w_e\right)$ is maximum.

### Input

The first line of the input is an integer $T(1<=T<=10) . T$ is the number of test cases.

For each test case, the first line is two integers $N, M\left(1<=N<=100, N<=M<=N^2\right)$

$N$ is the number of vertexes (numbered from 1 to $N$ ) and $M$ is the number of edges.

Then there will be $M$ lines, each line contains three integers $u_i, v_i$ and $w_i$, means there is an edge between $u_i$ and $v_i$ and the weight is $w_i$.

We guarantee that $1<=u_i, v_i<=N, 1<=w_i<=1000$.

### Output

For each test case, print the result problem required.

### Sample Input

``` log
1
4 5
1 2 1
1 3 2
1 4 3
3 4 2
2 3 2
```

### Sample Output

``` log
6
```

### 思路

问题复述:
- 给定一个连通无向加权图，在所有生成树中先最大化生成树的最小边权（bottleneck），然后在满足该最大化的前提下选择总权最小的生成树，输出该最小总权。

核心解法（两阶段）:
1. 确定最大的可行 bottleneck b：
   - 对某个阈值 b，只保留权重 >= b 的边构成子图。若该子图连通，则存在一棵生成树的所有边权都 >= b。
   - 因此可以枚举所有不同权值或对权值做二分，用并查集检测子图是否连通，找到最大的 b。
2. 在权重 >= b 的候选边上执行 Kruskal：
   - 只保留权 >= b 的边，按权升序排序并用并查集选边，得到在该约束下总权最小的生成树。

正确性说明:
- 最大化最小边权等价于寻找最大的 b，使得存在一棵生成树的所有边权都不小于 b。子图连通性是该条件的充分必要条件。
- 在固定 b 的前提下，在候选子图上求最小生成树可以得到满足 bottleneck 条件下的最小总权，Kruskal 算法能保证该最小性。

复杂度与实现细节:
- 令 N <= 100, M <= N^2。设 W 为不同权值数量。
- 枚举所有不同权值时，找 b 的复杂度为 O(W * M * α(N))，Kruskal 的复杂度为 O(M log M + M * α(N))。
- 在题目约定的约束下该实现是可接受的。可选优化: 对权值做二分或对边按权降序一次扫描记录首次连通时的权值，将找 b 的复杂度降为 O(M * α(N))。

边界情况:
- 当 N = 1 时没有边，输出 0。实现中需专门处理。
- 图中可能存在平行边或多条相同端点不同权重的边，算法对这些情况兼容。

代码映射:
- 程序遵循读-处理-输出分离，`reader()` 用于解析输入并构造测试用例，`cal()` 用于计算每个测试用例的答案，`output()` 用于按行输出结果。

可选优化与测试建议:
- 将找 b 的阶段改为一次降序扫描所有边以获取 b，然后在候选边上执行 Kruskal，以降低常数和总体复杂度。
- 建议在 `resources/` 下增加边界测试文件，例如 N=1、全相同权值、多重平行边等，以增强覆盖率和鲁棒性。

## Description

Give you N points on the plane.

In order to make these points connected to each other, you need to add some segments between them.

The cost of adding a segment (A, B) is the square of Euclidean distance between them.

What's more, there are M sets of points you can choose.

Each set has a cost.

If you choose a set $S_i$, then all points in $S_i$ will be connected.

Please write a program to calculate the minimum cost to make these N points connected.

### Input

The first line of the input is an integer T (1 <= T <= 10).

T is the number of test cases.

For each test case, the first line contains two integers N and M (1 <= N <= 500, 1 <= M <= 8).

N is the number of points (numbered from 1 to N) and M is the number of segment sets (numbered from 1 to M).

Then there will be N lines. Each line contains two integers $x_i$ and $y_i$ — the coordinates of the i-th point.

Then there will be M lines describing the sets: for each set, the first integer $num_i$ is the number of points in this set, the second integer $cost_i$ is the cost of this set, followed by num_i integers which represent the points in this set.

We guarantee that 0 <= $x_i$, $y_i$ <= 1000, 0 <= $num_i$ <= N, 1 <= $cost_i$ <= 10^6.

### Output

For each test case, print the minimum cost to make these N points connected to each other.

### Sample Input

```log
1
7 3
0 2
4 0
2 0
4 2
1 3
0 5
4 4
2 4 1 2
3 3 3 6 7
3 9 2 4 5
```

### Sample Output

```log
17
```

### 思路分析

问题建模

- 将 N 个点视为图的 N 个节点，任意两点 i, j 之间可以加边，边权为两点欧氏距离的平方（不取根以保持整型）：$w(i,j) = (x_i - x_j)^2 + (y_i - y_j)^2$。
- 每个给定的集合（set）可视为一次一次性操作：支付集合代价后，该集合内所有点立即相互连通（相当于把集合内点合并到同一连通块）。

总体算法

- 由于 M ≤ 8（集合个数上界很小），对是否选用每个集合可以暴力枚举其子集（最多 2^M ≤ 256 种情况）。
- 对于每个子集：
  1. 新建并查集 UF；把子集中被选的所有集合的点先 union 在一起，并把这些集合的代价累加到当前总价 total 上。
  2. 计算合并后连通块数 comps（实现中通过在 union 时统计合并次数递减 comps，避免重复扫描）。若 comps==1，则直接更新最优值 best。
  3. 否则在剩余连通块上用 Kruskal 补边：事先对所有 pairwise 边按权从小到大排序（仅计算一次），每次选择能把不同块连接的边并把权值累加到 total；当需要的并操作次数达到 comps-1 时停止。
  4. 使用剪枝：若在任何时点 total >= 当前最优 best，则提前停止该子集的处理（避免不必要工作）。
- 在所有子集中取最小 total 作为答案。

实现与工程细节

- 边的预处理：为避免大量对象分配与比较开销，把每条边打包为一个 long（高位为权重，低位编码 u, v），对 packed 数组调用 Arrays.sort。随后一次性解包为并行数组 `wArr, uArr, vArr`，在热循环中直接访问整型/长整型数组，减少位运算与对象开销。
- 并查集：实现带路径压缩与按秩合并的 UF 类；在应用集合时即时统计合并成功次数以维护连通分量数 `comps`，避免循环检查所有根。
- 剪枝与早停：
  - 在应用完集合并操作后，如果当前累计 `total >= best`，立即跳过该子集。
  - Kruskal 过程中若 `total >= best` 也会提前退出。
  - Kruskal 在完成所需合并数（needed = comps - 1）后立即停止。
- 数据类型与数值安全：
  - 单条边使用平方距离（long），累加使用 long，以避免溢出；最终结果会转换为 int（与仓库测试约定一致）。

时间复杂度（概览与可行性）

- 预处理：构造所有 O(N^2) 条边并排序，耗时 O(N^2 log N^2) = O(N^2 log N)。
- 子集枚举：最多 2^M 个子集（M ≤ 8），对每个子集执行 Kruskal，最坏情况每次扫描 O(N^2) 条边，但实际有剪枝与 early-stop，且一旦集合合并很多点时需要扫描的边会显著减少。因此在题目约束下是可行的。

边界与注意事项

- 空集合或集合大小为 1 会被安全处理（不会产生多余合并）。
- 若所有点通过所选集合已连通，算法会在检查 `comps==1` 后直接取代价并跳过 Kruskal。
- 使用平方距离保持整型权重；若需要真实欧氏距离可在设计上改为 double 并 sqrt，但当前题目使用平方代价是常见且更高效的选择。

工程提示（若需进一步优化）

- 若 N 更大且枚举 2^M 成为瓶颈，可考虑把集合转换为“超边”并尝试更复杂的近似或 DP（但在本题 M<=8 下无需）。
- 可把打包/解包逻辑提取为一个小工具类以便复用。 

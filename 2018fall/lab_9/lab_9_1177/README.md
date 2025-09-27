## Description

Give you an undirected connected graph $G(V, E)$. Each edge $e \in E$ has a weight $w_e$.

Let $E^{\prime}$ be an edge set of a spanning tree.

Please write a program to calculate $\min \left(\sum_{e \in E^{\prime}} w_e\right)$ when $\max \left(w_e\right)$ is minmum.

### Input

The first line of the input is an integer $T(1<=T<=10)$. $T$ is the number of test cases.

For each test case, the first line is two integers $N, M\left(1<=N<=100, N<=M<=N^2\right)$.

$N$ is the number of vertexes (numbered from 1 to $N$ ) and $M$ is the number of edges.

Then there will be $M$ lines, each line contains three integers $u_i, v_i$ and $w_i$, means there is an edge between $u_i$ and $v_i$ and the weight is $w_i$.

We guarantee that $1<=u_i, v_i<=N, 1<=w_i<=1000$.

### Output

For each test case, print the result required by the problem.

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
5
```

## 解答 (Solution)

1) 核心算法

- 使用 Kruskal 算法构造最小生成树（MST）: 先对所有边按权重升序排序, 然后依次尝试将边加入 MST, 使用并查集判断边的两端是否属于不同连通块, 若是则合并并把边权累加到结果中.
- 并查集采用路径压缩与按秩合并, 以保证近乎常数时间的合并与查找操作.

2) 读-处理-输出分离

- `reader()`: 使用快速输入（BufferedReader + StringTokenizer）读取 T、每个用例的 N、M 以及 M 条边 (u, v, w), 把边的顶点索引从 1-based 转为 0-based, 并用 `assert` 校验输入约束.
- `cal()`: 对每个用例执行 Kruskal, 返回每个用例的 MST 权值之和（`List<Integer>`）.
- `output()`: 接收 `Iterable<Integer>`, 使用 `StringBuilder` 聚合并按行打印结果, 每行末尾包含换行符.

3) 复杂度与空间

- 时间复杂度: 排序占主导, O(m log m), m 为边数; 并查集操作近乎 O(1)（摊销）.
- 空间复杂度: O(n + m), 用于存储并查集和边数组.

4) 注意事项

- 题目保证图连通; 若输入存在非连通情况, 程序对未能构造出 n-1 条边的情况做了保底处理（返回 0 或按题意处理）.
- 权重与节点规模较小（权重范围与 N 的约束见题目）, 累加和不会溢出 `int`, 但实现中使用 `long` 暂存累加以示稳健, 最后再转换为 `int` 输出.
- 在 `reader()` 中应做好 1-based -> 0-based 的索引转换, 并用 `assert` 验证输入范围.

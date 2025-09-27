## Description

Hong has a tree, whose vertices are conveniently labeled by 1, 2, ..., n. Each node has a weight $w_{i}$

A set with m nodes $v_{1}, v_{2}, ..., v_{m}$ is a Hong Set if:

The tree induced by this set is connected.

After we sort these nodes in set by their weights in ascending order, we get $u_{1}, u_{2}, ..., u_{m}$, (that is, $w\_u_{i} < w\_u_{i+1}$ for i from 1 to m-1).

For any node x in the path from $u_{i}$ to $u_{i+1} $(excluding $u+{i}$ and $u_{i+1}$), should satisfy $w\_x < w\_u_{i}$.

Your task is to find the maximum size of Hong Set in a given tree.

### Input

The first line will be an integer T (1 <= T <= 10), which is the number of test cases.

For each test data:

The first line contains two integers N (1 <= N <= 200000) — the number of the nodes.

The second line contains N integers $w_{1}…w_{n}$ (1 <= $w_{i}$ <= 10^9).

Each of the next N-1 lines contain two integers a and b, which means there is an edge between node a and b.

> 注意, 这里有且只有 N-1 条边, 边对于节点来说非常稀疏

### Output

For each case please print the maximum size of Hong Set.

### Sample Input

```log
1
7
3 30 350 100 200 300 400
1 2
2 3
3 4
4 5
5 6
6 7
```

### Sample Output

```log
5
```

#### 思考input-output

首先, 我们需要清晰地理解 "Hong Set" 的两个定义条件:

连通性: 集合中的所有节点在树上必须是连通的.
权重路径约束: 将集合中的节点按权重从小到大排序, 得到 u_1, u_2, ..., u_m. 对于任意相邻的两个节点 u_i 和 u_{i+1}, 它们在原树中的路径上, 所有 不属于 该集合的中间节点 x 的权重, 都必须小于 u_i 的权重 (w_x < w_{u_i}).

现在, 我们来看一下这个具体的用例:

树的结构: 1-2-3-4-5-6-7, 这是一条直线.

节点权重:
+ w_1 = 3
+ w_2 = 30
+ w_3 = 350
+ w_4 = 100
+ w_5 = 200
+ w_6 = 300
+ w_7 = 400

为什么答案是 5?

之所以输出是 5, 是因为存在一个合法的 `Hong Set {3, 4, 5, 6, 7}.`

这个集合的节点在原树中是连通的 (它们构成了路径 3-4-5-6-7).

将它们按权重排序后为: 4(100), 5(200), 6(300), 3(350), 7(400).

在检查任意两个权重相邻的节点 (如 6 和 3) 之间的路径时, 路径上的所有中间节点 (如 4, 5) 本身也属于这个集合.

因此, 路径上不存在 不属于 该集合的 "过路" 节点, 所以权重约束条件天然满足.

## 解法

核心思想: 算法尝试将树中的每一个节点 i (从 1 到 N) 作为其所在 "Hong Set" 中权重最小的节点（即排序后的 <span>u_1</span>）.

执行流程:

外层循环遍历所有节点 i, 将其视为潜在的起始节点.

对于每个 i, 调用一个 dfs(i, -1, w[i]) 函数.

dfs(u, parent, minWeight) 函数的目的是, 从节点 u 开始, 向上（向权重更大的节点）扩展, 构建一个以 i 为权重最小节点的有效 Hong Set, 并计算其大小.

在 dfs 内部, 它会递归地访问所有权重比当前节点 u 更大的邻居节点 v, 并将它们的子集大小累加到结果中.

复杂度: 对于每个节点, 都可能进行一次深度优先搜索, 其时间复杂度大致为 O(N). 由于外层循环也为 O(N), 总时间复杂度近似为 O(N²). 对于 N 高达 200,000 的情况, 这个解法会可能因为超时而无法通过所有测试用例, 但它逻辑直接, 易于理解.

> 这个解法可以作为对拍数据的生成器, 已通过测试


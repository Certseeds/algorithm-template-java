# Node Activation

## Description

Given a tree whose nodes are numbered from $1$ to $n$.

Each node has a predefined value $p_i$.

The task is to assign a non-negative value $e_i$ (i.e., $e_i \geq 0$) for each node to activate all nodes.

A node $i$ is activated if and only if:

1. there exists two different nodes $j$ and $k$, and node $i$ is on the path between nodes $j$ and $k$ ($j\neq k$, but node $i$ can be node $j$ or node $k$)
2. $\min (e_j,e_k)\geq p_i$.

Please find the minimum value of $(e_1 + e_2 + \cdots + e_n)$ to activate all nodes.

### Input

The first line contains an integer $n(2\leq n\leq 200,000)$ which means the number of nodes.

Then $n-1$ lines follow.

Each line contains two integers $u,v(1\leq u, v\leq n)$ which means an edge between node $u$ and node $v$.

Then one line contains $n$ integers $p_i(1\leq p_i \leq 10^8)$.

### Output

Print the minimum value of $(e_1+e_2+\cdots +e_n)$ to activate all nodes.

### Sample Input

``` log
4
1 2
2 3
2 4
2 3 1 1
```

### Sample Output

``` log
7
```

### HINT

Explanation for the example:

one optimal assignment is $e_1=3,e_2=0,e_3=3,e_4=1$.

Node $1$ is activated by nodes $1$ and $3$.

Node $2$ is activated by nodes $1$ and $3$.

Node $3$ is activated by nodes $3$ and $4$.

Node $4$ is activated by nodes $4$ and $1$.

Tips: You can choose the node with the maximum $p_i$ value as the root.

### 算法分析

这个任务的目标是找到为树中所有节点赋值的非负值 e_i 的最小总和, 使得每个节点都能被激活, 即存在两个不同节点 j 和 k, 节点 i 在 j 到 k 的路径上, 且 min(e_j, e_k) >= p_i.

核心算法步骤如下:
1. 选择根节点: 首先, 选择 p_i 值最大的节点作为根节点, 以优化后续计算.
2. 构建树结构: 使用链式前向星构建图, 并通过迭代 DFS 计算父节点关系和后序遍历顺序.
3. 动态规划计算: 在后序遍历中, 对于每个节点 u, 计算子树中的最大值 mx[v], 并根据子节点中 mx[v] >= p[u] 的数量决定是否需要增加 e[u].
   - 对于非根节点, 如果至少一个子节点满足条件, 则无需额外成本; 否则, 通过增加 e[u] 或子节点的值来满足条件.
   - 对于根节点, 需要确保至少两个端点满足条件, 可能需要增加多个值.
4. 累加总和: 在计算过程中累加所有 e_i 的增加值, 得到最小总和.

总的来说, 该算法通过树形动态规划在后序遍历中处理激活条件, 时间复杂度为O(n), 空间复杂度为O(n).

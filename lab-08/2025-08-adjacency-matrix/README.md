# Adjacency Matrix

## Description

Given a directed graph G with n nodes and m edges.

Please print the adjacency matrix A of G.

Hints: adjacency matrix is a method to represent a graph.

Suppose we have a directed graph G, if there is an edge from node i to node j in G, then we have `A[i][j]` = 1 in corresponding adjacency matrix A, otherwise, `A[i][j]` = 0.

### Input

The first line will be an integer $T$ $(1 \le T \le 50)$.

$T$ is the number of test cases.

For each test case, the first line will be two integers $n$ and $m$ $(1 \le n \le 500, 0 \le m \le n \times n)$.

Then there will be $m$ lines.

Each line will have two integers $x$ $y$.

$x$ $y$ means there is an edge from $x$ to $y$.

All nodes are labeled from $1$ to $n$.

### Output

For each test case, print the adjacency matrix.

### Sample Input

``` log
2
3 5
1 2
2 1
1 3
3 2
2 3
1 0
```

### Sample Output

``` log
0 1 1
1 0 1
0 1 0
0
```

## 算法分析

### 问题理解

给定一个有向图, 包含 n 个节点和 m 条边, 要求输出该图的邻接矩阵.

邻接矩阵 A 的定义: 如果存在从节点 i 到节点 j 的边, 则 `A[i][j]` = 1, 否则 `A[i][j]` = 0.

### 算法思路

1. 读取阶段: 读取测试用例数 T, 对于每个测试用例, 读取节点数 n, 边数 m, 以及 m 条边的信息

2. 处理阶段: 创建 n x n 的二维数组 matrix, 初始值全为 0. 遍历每条边 (x, y), 将 `matrix[x-1][y-1]` 设为 1 (节点编号从 1 开始, 数组下标从 0 开始)

3. 输出阶段: 按行输出邻接矩阵, 每行的元素用空格分隔

### 复杂度分析

- 时间复杂度: O(n^2 + m), 其中 O(m) 用于处理边, O(n^2) 用于输出矩阵

- 空间复杂度: O(n^2), 用于存储邻接矩阵

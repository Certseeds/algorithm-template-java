## Description

Write a program to print the longest distance between two nodes of the given tree.

### Input

The first line will be an integer T (1 <= T <= 10), which is the number of test cases.

For each test data:

The first line contains one integer N (1 <= N <= 10^5) - the number of nodes.

Each of the next N - 1 lines contain two integers a and b, which means there is an edge between node a and b.

### Output

For each case, please print the longest distance between any two nodes of the given tree.

### Sample Input

```log
1
8
1 4
1 3
4 2
2 7
3 5
3 6
6 8
```

### Sample Output

```log
6
```

## 解法

### 算法思路

- 输入与预处理
  - 使用快读 Reader 解析输入, 先读 T, 每个用例读 n, 接着读 n-1 条无向边.
  - 在 reader() 中对输入约束加入 assert 检查, 例如 assert ((1 <= n) && (n <= 100000));

- 建图
  - 使用 1-based 的邻接表存储无向图, 对于每条边 a b 同时在 g[a] 和 g[b] 中添加对方.

- 求直径的方法（两次 BFS）
  - 任意选择一个起点 s (例如 1), 用 BFS 计算从 s 到所有节点的距离, 找到距离最远的点 u.
  - 从 u 再次做 BFS, 最大距离即为树的直径.
  - BFS 使用 ArrayDeque 做队列, 迭代实现, 避免递归.

- 复杂度
  - 时间复杂度: O(n) 每次 BFS, 共 O(n) 两次, 每个用例总体 O(n).
  - 空间复杂度: O(n) 用于邻接表和距离数组.

- 边界与鲁棒性
  - 当 n == 1 时, 直径为 0.
  - 通过 assert 在开发/测试阶段尽早发现非法输入.

- 设计与实现原则
  - 遵循读-处理-输出分离: reader() 解析并构建 TestCase, cal() 负责计算直径并返回输出行, output() 负责最终打印并保证每行以 '\n' 结尾.
  - 使用迭代 BFS 保证在大输入下的稳定性和性能.

实现细节请参见 src/Main.java 中的 reader, cal, output 的具体代码。

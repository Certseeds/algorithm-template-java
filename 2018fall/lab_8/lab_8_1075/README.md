## Description

In a graph G, if we can find a path from node x to node y, we say that x can reach y. Now you are given a directed graph G with n nodes and m edges. Besides, there are Q queries. Each query will contain two integers x and y. If x can reach y, print YES. Otherwise, print NO.

Note: We guarantee there is at most one edge from node i to node j.

### Input

The first line will be an integer T (1 <= T <= 50). T is the number of test case.

For each test case, the first line will be two integers n and m. (1 <= n <= 1000, 0 <= m <= min(n * n, 100000))

Then there will be m lines. Each line will have two integers x, y. "x y" means there is an edge from x to y.

After that, there is an integer Q. (1 <= Q <= 500) The following are Q lines. Each line will have two integers x, y.

All nodes are labeled from 1 to n.

### Output

For each query, if x can reach y, print YES. Otherwise, print NO.

### Sample Input

```log
1
7 7
1 6
6 4
4 3
3 5
5 1
2 7
7 2
6
1 2
2 7
7 2
3 6
4 6
5 4
```

### Sample Output

```log
NO
YES
YES
YES
YES
YES
```

### HINT

For the first sample, 1 cannot reach 2, because 2 and 7 form a ring.

For the second sample, 2 can reach 7 directly.

For the third sample, 3 -> 5 -> 1 -> 6 is one path.

### 解答 (Solution)

下面给出一种适用于本题约束的实现思路与要点说明（按读-处理-输出分离的风格），供参考与学习：

1) 思路概述

- 由于题目给出的 n 上界为 1000，预计算所有点对的可达性是可行的。实现方法是对每个节点维护一个 BitSet 表示它能到达的所有节点，然后进行基于 BitSet 的传递闭包（Warshall-like）运算：
  - 初始化：对每个节点 i，令 `reach[i].set(i)`（节点可到达自身），并把所有有向边 u->v 设为 `reach[u].set(v)`。
  - 传递闭包：按 k 从 0 到 n-1 遍历，对每个 i 如果 `reach[i].get(k)` 为真，则执行 `reach[i].or(reach[k])`。这一步使用 BitSet 的按字并行操作，速度远快于逐位循环。
  - 回答查询：对于每个查询 (x,y) 只需检查 `reach[x].get(y)` 即可，时间 O(1)。

2) 读-处理-输出分离（实现结构）

- `reader()`：使用快速读取（BufferedReader + StringTokenizer）读取 T、每个用例的 n、m、m 条边、Q 以及 Q 条查询；将节点索引转换为 0-based 并做基本的断言检查。
- `cal()`：构建 `BitSet[] reach` 并执行上述传递闭包，最后把每个查询的结果转换为 "YES" 或 "NO" 字符串并收集到结果列表。
- `output()`：一次性把所有结果行输出，保证每行以换行符结尾。

3) 复杂度分析

- 时间复杂度：三重循环的位运算实现，粗略为 O(n^3 / W) 的位操作（W 为机器字长，通常 64），实测在 n ≤ 1000 的约束下非常快。
- 空间复杂度：O(n^2) 位，即 O(n^2 / 8) 字节外加 BitSet 对象开销。对于 n ≤ 1000 是可接受的。

4) 边界与正确性要点

- 请确保在初始化时设置 `reach[i].set(i)`，使得查询 (x,x) 返回 YES（长度为 0 的路径）。
- 输入中的节点编号以 1..n 给出，内部实现要转换为 0-based 来索引数组与 BitSet。
- 对于非法边或查询索引（若存在），应做好范围检查以避免异常；本实现使用了 guard 条件来忽略越界边并在查询时返回 NO。

5) 可替代方案与优化

- 若 n 远大于 1000，则需要替代方法：例如对图先做强连通分量 (SCC) 压缩成 DAG，DAG 节点数通常远小于 n，再在 DAG 上做 BitSet 传递闭包或对每个查询做 BFS/DFS。
- SCC + DAG 方法适用于高密度或有大量互联的图。
- 若 Q 很小而 n 较大，则可以对每个查询直接做 BFS/DFS，复杂度为 O(Q*(n+m))，在 Q 很小的情形下更节省。

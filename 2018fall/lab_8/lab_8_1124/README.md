## Description

Bob has a graph of n nodes and m undirected edges.

These nodes are numbered from 1 to n, but he does not know how many cliques with size four in the graph.

Therefore, he turns to you for help.

The definition of clique of nodes:

there is at least one edge between every two node x and y (x != y).

### Input

The first line will be an integer T, which is the number of the test cases(1 <= T <= 10). For each test case, the first line will be two integers n and m(1 <= n <= 75, 1 <= m <= 10^5). The following are m lines, and each line will be two integers x and y, which means there is an undirected edge between x and y.

### Output

For each test, output the number of cliques in one line.

### Sample Input

```log
1
5 6
1 2
1 3
1 4
2 3
2 4
3 4
```

### Sample Output

```log
1
```

### 思路概要（解题要点）

下面给出本题在仓库实现中采用的、经修正后的简洁解法要点，面向熟悉 Java 的开发者但不熟悉此代码的读者：

1) 数据结构

- 使用邻接布尔矩阵 `adj[u][v]` 去重并快速判断两点间是否存在边；
- 同时为每个节点维护一个 `BitSet nbr[u]` 表示其邻居集合，便于按机器字并行进行集合交运算。

2) 计数思路（核心）

- 对于每一条无向边 (u,v)（只遍历 u<v）：
  - 计算共同邻居集合 `S = nbr[u] & nbr[v]`（用 BitSet 的 `and`），得到共同邻居个数 t；
  - 枚举 `S` 中的任意两点 (c,d)，并仅在 `adj[c][d] == true` 时，认为 {u,v,c,d} 构成一个 4-clique；
  - 对每个满足条件的 (u,v,c,d) 增加计数 `sum++`。
- 最后除以 6：因为每个 4-clique 在无向图中有 6 条边，而上面枚举会在每条边上各计一次，所以最终答案为 `sum / 6`。

3) 为什么要校验 (c,d)

- 仅用共同邻居对的组合数 C(t,2) 会把 c 与 d 未相连的对也计入，导致错误计数；因此必须再加一步 `if (adj[c][d])` 的验证。代码已按此方式修正。

4) 复杂度与适用性

- n ≤ 75 的约束下，使用 BitSet + 枚举共同邻居对是高效且可读的：
  - 构建邻接与 BitSet：O(n + m)；
  - 对每条边计算交集并枚举共同邻居对：最坏近似 O(n^3 / W) 的位运算（W 为字长），对 n ≤ 75 足够快；
- 空间：O(n^2) 位（BitSet 与布尔矩阵开销），对题目限制可接受。

5) 边界与鲁棒性

- 输入可能包含重复边或自环：实现中通过 `adj[][]` 去重并忽略自环，保证统计不会受脏数据影响。
- 读入时把 1-based 索引转换为 0-based 并做范围检查，越界边被安全地忽略。

6) 验证建议

- 推荐同时保留一个暴力参考解（枚举 C(n,4) 的四元组并检查 6 条边）用于单元测试交叉验证，尤其在重构后要做回归测试；仓库中已有测试框架可用于比较。
- 本题在仓库的测试命令：

```cmd
mvn -pl .\2018fall\lab_8\lab_8_1124 -am test
```

7) 小结

- 该实现兼顾可读性与正确性，修复了早期版本中“没有校验共同邻居对是否互连”导致的错误计数问题；在题目给定的规模下为稳健且高效的方案。

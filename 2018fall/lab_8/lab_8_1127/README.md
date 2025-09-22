## Description

David has a graph of n nodes and m directed edges, and he wants to know the longest path in this graph.

We promise that there is no loop in the graph.

### Input

The first line will be an integer T, which is the number of the test cases(1 <= T <= 10).

For each test case, the first line will be two integers n and m(1 <= n <= 100000, 1 <= m <= 200000).

The following are m lines, and each line will be three integers x, y and z(1 <= z <= 300000), which means there is a directed edge weighted z from x to y.

### Output

For each test, output the longest path in this graph.

### Sample Input

```log
1
3 4
1 2 5
2 3 4
1 3 10
1 3 20
```

### Sample Output

```log
20
```

### 解题分析（Analysis）

下面给出对 `src/Main.java` 实现的分析，说明为什么采用拓扑排序 + 动态规划的方法可以正确且高效地求出有向无环图（DAG）上的最长路径。

1) 算法选择与直观理由

- 题目保证图中无回路（no loop），因此图是 DAG。DAG 上求最长路径的标准做法是：先对图进行拓扑排序，然后按拓扑顺序基于前驱更新最长路径（动态规划）。 
- 这种方法只需一次线性遍历所有边和点，时间复杂度 O(n + m)。

- 在代码中我们使用邻接链表的数组表示（head/to/next/w 数组），并记录入度 `indeg[]`。通过 Kahn 算法产生拓扑序列，并在出队时对所有出边做松弛：

```text
// 伪代码要点
for u in topo_order:
    for each edge u->v with weight ww:
        if dist[v] < dist[u] + ww:
            dist[v] = dist[u] + ww
```

2) 实现细节说明（与仓库代码对应）

- 邻接表示：通过 `Graph` 和 `Edge` 两个内部类实现链式前向星。
- `Edge` 类封装了边的目标节点 `to`、权重 `w` 和指向下一条边的 `next` 指针。
- `Graph` 类则管理一个 `Edge` 对象数组和 `head` 数组，并提供了 `addEdge` 方法来方便地添加边。这种面向对象的方式提高了代码的可读性和封装性。代码片段要点如下：

```java
// Graph class encapsulates the adjacency list
public static final class Graph {
    private final Edge[] edges;
    private final int[] head;
    // ...
    public void addEdge(int u, int v, int w) {
        // ...
    }
}
```

- 拓扑入队：把所有 `indeg[i] == 0` 的节点先入队；出队时更新 `dist[v] = max(dist[v], dist[u] + w)` 并 `indeg[v]--`，当入度为 0 时入队。

- 距离类型：使用 `long` 保存 `dist[]`，以防权值累加超过 32 位整型范围（题目权重上界 3e5，路径很长时需要 64 位）。

3) 复杂度

- 时间复杂度：O(n + m)，其中 n 是节点数，m 是边数。每条边在松弛步骤中只被访问一次。
- 空间复杂度：O(n + m) 用于邻接结构与入度数组与距离数组。

4) 边界与健壮性

- 多条平行边：实现对平行边天然支持（在不同 `ptr` 上存储多条 u->v 的边），松弛过程会取最大值，因此平行边不会破坏正确性。
- 非法输入保护：reader 中做了范围断言，构建图时也对 `u`/`v` 做了 guard（忽略越界），保证实现不崩溃。
- 图非 DAG 的情况：题目保证无环；若输入不满足该条件，Kahn 最终 `visited` < n，可以当作异常或返回当前已计算的最大距离（但题目无需处理此情形）。

5) 调试与验证建议

- 单元测试：使用仓库已有的 `resources/01.data.in` 与 `01.data.out` 做快速回归；再构造一些自定义测试：
  - 链式图（1->2->3->...），最长路径是所有边权和；
  - 有平行边或稀疏多分支图验证松弛正确性；
  - 包含孤立点（无入边无出边）验证不会影响其他组件。

- 随机化验证：对小规模 n（例如 n<=10）生成随机 DAG（先生成随机有向图然后删除所有反向边使其无环或直接生成拓扑顺序并只产生前向边），用暴力枚举最长路径与本实现对比。

6) 本地运行（回归与性能）

- 运行模块单测：
```cmd
mvn -pl .\2018fall\lab_8\lab_8_1127 -am test
```

- 手动运行样例：
```cmd
java -cp 2018fall\lab_8\lab_8_1127\target\classes Main < 2018fall\lab_8\lab_8_1127\resources\01.data.in
```

---

以上为对 `lab_8_1127` 实现的分析与 README 补充说明；若你需要，我可以把上述随机化验证脚本加入 `tools/` 并在 CI 中运行，或把实现再替换为基于邻接列表的 Java Collection 版本以提高可读性（当前数组实现在性能上更优）。

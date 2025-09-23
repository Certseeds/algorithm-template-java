## Description

Give you $N$ points numbered from 1 to $N$ on the plane, the cost of moving from one point $P_{1}$ to another point $P_{2}$ is the Manhattan Distance of them.

That is, $\operatorname{Dis}(P_{1}, P_{2})=|x_{1}-x_{2}|+|y_{1}-y_{2}|$.

You can move between any pair of the points. Please write a program to calculate the smallest cost moving from one point to the other.

### Input

The first line of the input is an integer $T(1<=T<=10)$  $T$ is the number of test cases.

For each test case, the first line is an integer $N(1<=N<=1000)$.

Then there will be $N$ lines, the $i$ th line contains two integers $x_{i}$ and $y_{i}$, means the coordinate of the $i$ th point.

Then there will be an integer $Q(1<=Q<=1000)$.

The next Q lines contains $Q$ queries.

Each query will contain two integers.

We guarantee that $0<=|x_{i}|,|y_{i}|<=1000$.

### Output

For each query, print the smallest cost.

### Sample Input

```log
1
4
0 0
0 1
1 0
1 1
3
1 2
2 3
3 4
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

## 解答

- 基本思路

  - 任意两点可直接移动，因此从点 u 到点 v 的最小代价就是两点之间的曼哈顿距离：
    Dis(u, v) = |x_u - x_v| + |y_u - y_v|。
  - 对每个查询直接计算上述公式即可，不需要图搜索或复杂预处理。

- 实现结构（读-处理-输出分离）

  - `reader()`：使用 BufferedReader + StringTokenizer 按题目格式读取输入，先读 T，然后对每个用例读 N、N 行坐标、Q 和 Q 个查询；把读取的数据封装到 `TestCase` 并返回 `List<TestCase>`。
  - `cal()`：接收 `List<TestCase>`，对每个查询计算曼哈顿距离，结果收集到 `List<Integer>` 并返回。
  - `output()`：接收 `Iterable<Integer>` 或 `List<Integer>`，使用 StringBuilder 聚合结果并按行输出，每行末尾包含换行符。

- 复杂度

  - 时间复杂度：每个查询 O(1)，总体 O(sum Q)。
  - 空间复杂度：O(N) 存储点坐标，额外 O(Q) 存储输出结果。

- 注意事项

  - 在 `reader()` 中把题目给出的 1-based 索引转为 0-based。
  - 在 `reader()` 中使用 `assert` 验证输入约束，例如 `assert ((1 <= N) && (N <= 1000));`。
  - 坐标范围在题目限制内，使用 `int` 足够安全。

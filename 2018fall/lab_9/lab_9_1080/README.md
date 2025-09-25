## Description

Bob wants to date with Alice, but they are in different cities. There are n cities and m roads.

Each road is bidirectional. Going through each road will cost some time.

Bob wants to meet Alice at one of the cities as soon as possible.

Could you help him to find the minimum time cost?

All cities are labeled from 1 to n.

Note: There may be multiple roads between some cities. Alice can also move.

### Input

The first line will be an integer T. (1 <= T <= 50) T is the number of test cases.

For each test case, the first line will be two integers n and m. (1 <= n <= 6000, 1 <= m <= 100000)

Then there will be m lines. Each line will contain three integers u, v, w, meaning there is a road between city u and city v with time cost w.  (1 <= u, v <= n, u!=v, 1 <= w <= 105)

The last line of each test case is two integer s and t. (1 <= s, t <= n), meaning the location of Bob and Alice.

The input guarantees Bob can always meet Alice.

### Output

For each test case, print the minimum time cost.

### Sample Input

```log
2
4 3
1 2 3
3 2 1
2 4 4
1 4
8 7
1 2 1
1 3 7
1 4 6
4 6 2
6 5 4
6 7 3
7 8 5
4 7
```

### Sample Output

```log
4
3
```

### HINT

For the first sample, Bob goes to city 2, and waits 1 unit of time.

Alice goes to city 2 directly, they will meet each other at 4 unit of time.

### 思路分析

本题可抽象为带权无向图上的最短路径相遇问题。令 ds[i] 和 dt[i] 分别表示从 Bob 的起点 s 和 Alice 的起点 t 到城市 i 的最短时间。若两人在城市 i 相遇，所需时间为 max(ds[i], dt[i])（较早到达的一方可在该城市等待）。因此对所有城市 i 计算 max(ds[i], dt[i])，并取最小值即为答案。

实现要点：

- 使用邻接表存储图，类型为 List<int[]>[]，每条边记录为 {to, weight}。
- 分别对 s 和 t 运行 Dijkstra，使用 long 保存距离值，并将不可达距离设为 INF = Long.MAX_VALUE / 4。
- 遍历所有城市，忽略任一端不可达的城市，更新答案为 min(answer, max(ds[i], dt[i]))。
- 题目保证存在可行的相遇点；为安全起见，代码在不可达时返回 -1。

复杂度分析：

- 时间复杂度：O((n + m) log n)，其中 n 为城市数，m 为道路数。
- 空间复杂度：O(n + m)。

工程与实现约定：

- 代码采用 reader / cal / output 分离，reader 中包含对输入范围的 assert 检查。
- 为避免 Java 对泛型数组的限制，邻接表使用带 @SuppressWarnings 的强制转换。
- 使用英文标点与直引号，遵循仓库约定。

边界与注意事项：

- 支持多重边和不同权值。
- 当 m = 0 且 s == t 时，答案为 0；若不存在可达的相遇点（题中保证不会发生），代码返回 -1 作为安全值。

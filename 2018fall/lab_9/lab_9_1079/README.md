## Description

In SUSTech, if you want to select a course, you have to learn the prerequisite course at first.

As you know, Itakejgo wants to be a student in Department of CSE. He has to learn n courses.

The prerequisite information is given. Please find a way to select courses.

If there are multiple ways, print the one with minimum alphabet order.

Besides, the information may be wrong. If you cannot find such a way to select courses, print "impossible" (without quotes).

The courses are labeled from 1 to n.

Note: the input guarantees that no courses are required the same course twice.

### Input

The first line will be an integer T. (1 <= T <= 500) T is the number of test cases.

For each test cast, the first line will two integers n and m. (1 <= n <= 5000, 1 <= m <= 20000).

Then there will be m lines. Each line will contain two integers x y. Means x is the prerequisite course of y. ( you have to learn x before y)

### Output

For each test case, print a way to choose course. If there are more than one ways, print the one with minimum alphabet order.

### Sample Input

```log
3
8 6
1 2
1 3
1 4
5 6
6 7
8 7
4 3
1 2
3 2
2 4
3 3
1 2
2 3
3 1
```

### Sample Output

```log
1 2 3 4 5 6 8 7
1 3 2 4
impossible
```

### HINT

For the first sample, we can choose 1 first, then 2, then 3 … But we need to choose 8 before 7.

For the third sample, we cannot get a solution. So the course may be wrong.

### 思路分析

本题可抽象为有向图的拓扑排序问题, 要求在所有合法拓扑序列中输出字典序最小的一个.

基本思路:

1. 根据输入构建邻接表和入度数组, 邻接表采用 List<Integer>[] 存储, 入度使用 int[].
2. 使用小顶堆 PriorityQueue 存放当前所有入度为 0 的节点, 每次取堆顶输出, 保证局部选择最小编号从而得到全局字典序最小的拓扑序.
3. 取出一个节点后, 遍历其出边并将对应节点的入度减 1, 若某节点入度变为 0 则加入堆.
4. 若最终输出的节点数小于 n, 说明图中存在环或信息错误, 输出 "impossible".

时间复杂度: O(n + m log n), 其中 n 为节点数, m 为边数.
空间复杂度: O(n + m).

实现要点:

- 代码分为 reader / cal / output 三部分, 使用 FastScanner 快速读入数据.
- 在 reader 中根据题目约束加入 assert 检查, 例如 assert ((1 <= n) && (n <= 5000)).
- 为避免 Java 对泛型数组创建的限制, 使用带抑制的强制转换: @SuppressWarnings("unchecked") final List<Integer>[] graph = (List<Integer>[]) new ArrayList[n + 1];
- 每个测试用例的输出占一行, 最后一行后也要有换行.

边界与注意事项:

- 当 m = 0 时, 所有课程互不依赖, 程序会按编号升序输出所有课程.
- 若存在自环或环依赖, 程序会检测到并输出 "impossible".
- 按仓库风格使用英文标点和直引号.

### Source

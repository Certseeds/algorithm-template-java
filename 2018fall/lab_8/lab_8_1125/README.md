## Description

Carol has a matrix of n rows and m columns.

Every grid (the coordinate are from 1) in the matrix has a number representing its color.

If you stand in one grid, you can move to an adjacent grid if you satisfy these two requirements:

1. If you stand on (x, y), then (x + 1, y), (x - 1, y), (x, y - 1), (x, y + 1) are adjacent to you.

2. The color in your position are the same as the grid you are going to.

If you can reach one grid from another grid, then they are in the same group.

Now, you need to calculate how many groups the matrix has.

The most important thing is the first column are adjacent to the last column.

### Input

The first line will be an integer T, which is the number of the test cases(1 <= T <= 10).

For each test case, the first line will be two integers n and m(1 <= n <= 1000, 1 <= m <= 1000).

The following are n lines, and each line will be m integers.

The j-th integer in the i-th line means the color of the grid(i, j) (1 <= color <= 5).

### Output

For each test, output the number of groups.

### Sample Input

``` log
1
3 4
1 2 3 5
1 2 3 1
1 3 3 5
```

### Sample Output

``` log
5
```

### 思路总结

下面给出针对本题的实现要点、算法思路以及实现和测试建议, 面向熟悉 Java 的同学阅读: 

1) 核心思路

- 本题要统计矩阵中按颜色划分的连通块数. 采用洪泛（flood-fill）思想, 用 BFS（队列）或 DFS（栈）对每个未访问格子进行扩展, 遇到同色邻居就并入当前连通块.
- 注意水平环绕: 列 1 与列 m 相邻, 左右移动采用模运算处理: 例如左邻居为 `(y-1 + m) % m`, 右邻居为 `(y+1) % m`.

2) 读-处理-输出分离（实现结构）

- `reader()`: 使用 BufferedReader + StringTokenizer 快速读取, 按题目约束加入 `assert` 检查; 把每个用例抽象为 `TestCase` 对象传入 `cal()`.
- `cal()`: 对每个用例分配 `boolean[] visited`（长度 `n*m`）, 线性化索引 `idx = x*m + y`, 对每个未访问格子用 BFS 标记整块.
- `output()`: 一次性把所有答案打印出来, 每行末尾带换行符.

3) 关键实现片段（示意）

- 将二维坐标线性化并作为队列元素: `idx = x * m + y`.
- 处理左右环绕示例: `int left = (y - 1 + m) % m; int right = (y + 1) % m;`.
- 为邻居入队封装为小函数: `pushIfSameColor(visited, queue, a, nx, ny, m, color)`.

4) 复杂度

- 时间复杂度: O(n * m), 每个格子只被访问一次; 每次考察恒定数量邻居（4 个）, 总工作量线性.
- 空间复杂度: O(n * m)（visited 数组与 BFS 队列）. 对题目约束（n, m ≤ 1000）是可接受的.

5) 边界与鲁棒性

- 输入可能含重复或格式异常的行, 但 `reader()` 中的 `assert` 可帮助早期发现违例数据; 若需容错, 可把断言替换为跳过或记录日志.
- 水平环绕必须严格实现, 切勿在左右边界忘记模运算导致错误分组.
- 颜色范围受限（1..5）, 可用 `byte` 或 `int` 存储; 若内存敏感, 可考虑使用 `BitSet` 或 `byte[]` 优化 `visited` 存储.

6) 测试建议

- 用题目给定样例验证基本正确性. 然后构造边界用例: 
  - 全同色（应返回 1）; 
  - 全不同色（应返回 n*m）; 
  - 横向环绕穿越的组（验证 wrap-around）; 
  - 最大规模（n=m=1000）测性能与内存.
- 可以再加入随机化对照测试: 对小尺寸随机矩阵同时运行本实现和暴力 DFS（递归或枚举）对比结果.


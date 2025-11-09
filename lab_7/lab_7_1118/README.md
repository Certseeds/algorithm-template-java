## Description

David has many numbers, and he wants to know the K-th biggest number among them.

### Input

The first line will be an integer T, which is the number of the test cases (1 <= T <= 12).

For each test case, the first line will be two integers n and K (1 <= n <= 5*10^5, K <= 5000 or K >= 0.99*n).

The second line will be n integers a1 ... an (1 <= ai <= 10^9).

### Output

For each test output the K-th biggest element in one line.

### Sample Input

```log
1
10 1
1 2 3 4 5 6 7 8 9 10
```

### Sample Output

```log
10
```

## 解法

### 算法思路

- 读-处理-输出分离
  - `reader()` 解析 T, 每个用例的 n 与 K 以及 n 个整数, 并用 `assert` 做基本输入约束检查.
  - `cal()` 对每个用例根据 n 和 K 选择合适算法（小 K 使用堆, 极端 K 使用对称堆, 通用情况排序）, 并返回结果字符串列表.
  - `output()` 使用 `StringBuilder` 聚合并一次性打印所有结果, 每行以 '\n' 结尾.

- 算法要点
  - 若 K 很小（例如 K <= 5000）, 使用大小为 K 的小顶堆维护当前 K 个最大值, 最终堆顶即为第 K 大, 时间 O(n log K), 空间 O(K).
  - 若 n-K+1 很小（即寻找较小的 L = n-K+1）, 可以用大小为 L 的大顶堆维护最小的 L 个元素, 然后取堆顶获得第 K 大, 时间 O(n log L).
  - 一般情形直接对数组排序并取第 K 大（`Arrays.sort`）, 时间 O(n log n), 实现最简单且常数小.

- 复杂度
  - 小顶/大顶堆方案: O(n log min(K, n-K+1)) 时间, O(min(K, n-K+1)) 空间.
  - 排序方案: O(n log n) 时间, 原地排序空间 O(1) 或 O(n) 视具体实现而定.

- 边界与实现提示
  - 注意输入规模 n 可达 5e5, 当 n 很大且 K 也很大时排序是可行的但须注意 JVM 堆内存与 GC. 堆方法在 K 很小时更节省时间和内存.
  - K 的合法性检查: 1 <= K <= n.
  - 若希望不修改原数组, 请在排序前拷贝数组副本.
  - 对于 Java 实现, 建议使用 BufferedReader+StringTokenizer 做快速输入.

实现细节请参考 `src/Main.java` 中的 reader, cal, kthBiggest, output 实现.

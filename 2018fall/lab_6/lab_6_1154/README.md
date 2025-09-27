## Description

There is a set with size n initially, and there are q operations, each operation will be one of the following cases:

Add x: add x to this set.

Delete: delete the minimum element of the set.

Query: print the minimum element of the set.

### Input

The first line will be an integer T, which is the number of test cases. (1 <= T <= 10).

For each test case, the first line will be an integer n (1 <= n <= 10^5), then the second line will be n integers ai (1 <= ai <= 10^9), they make up the initial set. The third line will be an integer q (1 <= q <= 10^5), it means the number of operations. Then followed by q lines, each line will be one of the following cases:

1 x: Add x (1 <= x <= 10^9).

2: Delete.

3: Query.

### Output

For each "Query", print the minimum element of the set in a line.

### Sample Input

```log
1
2
2 3
2
1 2
3
```

### Sample Output

```log
2
```

## 解法

### 算法思路

- 输入与预处理
  - 使用快读 Reader 解析输入, 先读 T, 每个用例读 n 和序列 a[0..n-1], 然后读 q 及接下來的 q 个操作.
  - 在 reader() 中对输入约束加入 assert 检查, 例如 assert ((1 <= n) && (n <= 100000));

- 数据结构
  - 使用 Java 的 PriorityQueue<Integer> 作为最小堆来维护集合的当前元素, 支持 O(log N) 的插入与删除最小值操作.
  - 元素允许重复; 删除操作按堆的 poll() 行为移除当前最小值.

- 操作处理
  - type 1 x: 调用 pq.add(x) 插入元素.
  - type 2: 如果 pq 非空则调用 pq.poll() 删除最小元素, 否则忽略.
  - type 3: 如果 pq 非空则输出 pq.peek() 作为当前最小值, 否则输出 -1.

- 读-处理-输出分离
  - reader() 负责解析并构建 TestCase 数据结构.
  - cal() 接受 TestCase 列表, 对每个用例执行操作并将所有 Query 的结果收集为字符串行列表.
  - output() 负责最终输出, 使用 StringBuilder 聚合并用 System.out.print 一次性输出, 每行以 '\n' 结尾.

- 复杂度分析
  - 时间复杂度: 每个测试用例为 O((n + q) log N) (N 为堆中元素数量的上界), 总体可在题目约束范围内运行.
  - 空间复杂度: O(N) 用于堆和存储输入操作.

- 边界与鲁棒性
  - 当集合为空时, Delete 操作为无操作, Query 返回 -1.
  - 使用 assert 可在开发/测试阶段尽早发现不合法输入.

实现细节请参见 src/Main.java 中的 reader, cal, output 的具体代码.

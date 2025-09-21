## Description

There are N integers A1...An. Hong wants to know the kth smallest integer of them.

However, Hong is not good at maths. He asks you to find the kth smallest integer.

### Input

The first line will be an integer T (1 <= T <= 5), which is the number of test cases.

For each test data:

The first line contains two integers N and k (1 <= k < N <= 10^6).

The next line contains N integers Ai (1 <= Ai <= 10^9).

### Output

For each case, please print the kth smallest number of them.

### Sample Input

```log
1
3 2
1 2 3
```

### Sample Output

```log
2
```

## 解法

### 算法思路

- 总体思路
  - 每个用例读入 N 和 k 以及 N 个整数后, 求第 k 小的元素.
  - 最直接且可靠的实现是对数组进行排序, 然后输出下标为 k-1 的元素.

- 读-处理-输出分离
  - reader(): 使用快速输入读取 T, 每个用例的 N, k 和数组, 并用 assert 检查输入约束.
  - cal(): 对每个用例进行处理, 当前实现采用 Arrays.sort 进行排序并取第 k 小值; 若需要更优性能, 可使用快速选择算法 (Quickselect) 或基于堆的方案.
  - output(): 将每个用例的结果按行输出, 使用 StringBuilder 聚合并一次性打印.

- 复杂度
  - 当前排序实现时间复杂度为 O(N log N) 每个用例, 空间复杂度为 O(1) 额外空间（排序通常就地进行）.
  - 若 N 很大且需要更好性能, 可用 Quickselect 将平均时间降低到 O(N).

- 边界与鲁棒性
  - 当 k 的取值或数组元素超出题目约束时, reader() 中的 assert 会在开发/测试阶段提示错误.
  - 注意输入可能跨多行或样例文件格式差异, 快速输入类应按 token 方式读取所有整数.
  - 推荐使用 int 存储题目给定范围内的元素 (<= 10^9); 若数据可能更大, 请使用 long.

- 实现提示
  - 对于 Java, 使用 BufferedReader + StringTokenizer 做快速输入; 在处理大 N 时避免在循环内进行字符串拼接.
  - 若对最坏时间敏感, 建议实现 Quickselect 或使用优先队列维护 k 小元素.

实现细节参见 `src/Main.java` 中的 reader, cal, output 实现。

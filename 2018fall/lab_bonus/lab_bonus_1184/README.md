## Description

There are N integers A1...An.  Hong wants to know the maximum integer of them.

However, Hong is not good at maths.  He asks you to find the maximum integer.

### Input

The first line will be an integer T (1 <= T <= 100), which is the number of test cases.

For each test data:

The first line contains one integer N (1 <= N <= 10^4) — the number of the integers.

The next line contains N integers Ai (1 <= Ai <= 10^9).

### Output

For each case please, print the maximum integer of them.

### Sample Input

```log
1
2
1 2
```

### Sample Output

```log
2
```

## 解法

### 算法思路

- 总体思路
  - 对于每个测试用例, 对输入的 N 个整数进行单次扫描, 维护当前最大值并在扫描结束后输出该最大值.

- 读-处理-输出分离
  - reader() 负责解析 T, 每个用例的 N 与数组内容, 并用断言(assert)检查输入约束.
  - cal() 接受解析后的数据结构, 对每个用例执行一次线性扫描以求最大值, 将结果收集为字符串行列表.
  - output() 负责将结果一次性打印到标准输出, 每行以 '\n' 结尾.

- 边界与鲁棒性
  - 当 N == 1 时, 程序能正确返回唯一元素; 当所有元素为负数时也能正确工作.
  - 按题目约束, 元素上界为 1e9, 使用 int 类型足够; 若实际数据可能超出 int, 请改用 long.
  - 在 reader 中加入 assert 检查可在开发/测试阶段尽早发现非法输入.

- 复杂度
  - 时间复杂度: O(N) 每个用例, 其中 N 为该用例的数组长度.
  - 空间复杂度: O(1) 额外空间, 主要空间用于输入数组.

- 实现提示
  - 使用快速输入类 (FastScanner 或 BufferedReader+StringTokenizer) 以避免 IO 瓶颈.
  - 不要在扫描过程中频繁进行字符串拼接, 最终输出使用 StringBuilder 聚合后一次性打印。

实现细节请参见 `src/Main.java` 中的 reader, cal, output 的具体代码实现。

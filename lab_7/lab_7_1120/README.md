## Description

Grace has n numbers. They are a1...an. Let M = sqrt(n), meaning the biggest integer which is less than or equal to the square root of n.

And there are n queries.

For each query, there are two integers x and y.

You need to answer the M-th biggest number among a_x, a_{x+y}, ... , (x + k*y <= n).

If the number of the elements is less than M, please output -1.

### Input

The first line will be an integer T, which is the number of test cases.(1 <= T <= 5).

For each test case, the first line will be an integer n (1 <= n <= 40000).

The second line will be n integers which are a1...an (1 <= ai <= 10^9).

For the following n lines, each line will have two integers x and y.

### Output

For each test output the M-th biggest element or -1 in one line.

### Sample Input

```log
1
5
1 2 3 4 5
1 1
2 2
3 3
4 4
5 5
```

### Sample Output

```log
4
2
-1
-1
-1
```

### 算法解析

下面给出 `src/Main.java` 中算法的解析, 内容以中文说明, 使用 ASCII 标点, 便于理解与验证.

1) 问题回顾

- 给定数组 a[1..n], 定义 M = floor(sqrt(n)). 每个查询给定 (x,y), 需要在序列 a_x, a_{x+y}, a_{x+2y}, ... 中返回第 M 大的元素; 若该序列长度小于 M, 返回 -1.

2) 核心思路（sqrt-decomposition）

- 将步长 y 分为两类: 小步长 y <= M 和大步长 y > M.

- 对于小步长 y（y <= M）: 
  - 对每个余数类 r = 0..y-1, 考虑下标序列 r, r+y, r+2y, ...（按 0-based）, 该序列的长度约为 ceil(n/y).
  - 对每个余数类建立从后向前的后缀信息: 用容量为 M 的最小堆（min-heap）维护后缀上前 M 大元素, 从后向前滚动可在 O(len * log M) 内得到每个后缀的第 M 大元素（若不足 M, 则标为无效）.
  - 把上述结果按 y 和 r 组织起来, 查询 (x,y) 时直接定位到对应余数 r 和后缀起点 t, O(1) 返回答案.

- 对于大步长 y（y > M）: 
  - 序列长度 L <= n / y < n / M ≈ M, 所以每次查询直接遍历序列并用容量为 M 的最小堆维护前 M 大, 时间 O(L log M) = O(M log M)（对单次查询可接受）.

3) 复杂度分析

- 设 M = floor(sqrt(n)).
- 预处理（针对所有小 y）: 对每个小 y, 遍历所有位置把元素分配到各余数类, 共处理 O(n) 元素; 对每个余数类用堆从后向前建表, 单元素操作为 O(log M). 总体预处理时间可估为 O(n log M) 乘以小 y 的覆盖系数, 粗略界为 O(n * M * log M / M) ≈ O(n log M) 到 O(n * M log M) 之间, 实际在题目约束下通常可接受.
- 查询: 小 y 查询 O(1); 大 y 查询 O(M log M). 因此总体时间在平均/最坏情形下都能满足 n <= 4e4 的限制.
- 空间: 预处理表占用约 O(sum_y<=M y * (n/y)) = O(n * number_of_small_y) 的辅助空间, 但实际为每个位置在各 y 的余数类中只出现一次, 因此整体 O(n * (#distinct small y)), 在常规查询分布下可接受.

4) 正确性要点

- 使用后缀最小堆方法能正确维护后缀的第 M 大元素: 每步将当前元素加入堆, 堆超出容量则弹出最小, 堆顶即为第 M 大（若堆大小不足 M 则标记为无效）.
- 小步长的预处理建立的是针对每个余数类和每个后缀起点的精确第 M 大, 查询定位到 (x,y) 对应的余数类和后缀索引即可直接读取.
- 大步长直接按定义计算第 M 大, 避免了对所有 y 的全面预处理, 节省时间和空间.

5) 边界与实现细节

- 注意下标 / 余数的 0-based / 1-based 转换: 实现中 reader 将输入 x 转为 0-based（x-1）, 余数 r = (x-1) % y.
- 当 M == 0（理论上 n==0 不会出现, 因为 n>=1）, 需特殊处理, 但在本题约束下可忽略.
- 如果查询中 y <= 0, 返回 -1（实现中已做守护）.
- 堆中使用 Integer.MIN_VALUE 作为“无效值”标记, 查询时要检测并返回 -1.

6) 测试建议

- 单元测试: 使用样例 `resources/01.data.in` 验证输出与 `01.data.out` 一致.
- 边界测试: 
  - n=1, n=2 的小规模测试; 
  - 全部 y 为 1（相当于固定余数类）, 全表预处理和查询一致性测试; 
  - 随机数组与随机查询, 使用暴力 O(n) 或 O(n log n) 参考解比较输出; 
  - 大量 small y 与 large y 混合测试, 评估时间/内存使用.

7) 可选优化

- 若查询中 small y 的 distinct 数量接近 M 并且内存敏感, 可限制预处理仅针对实际出现的 y（当前实现已如此处理）; 或在内存受限时采用按需惰性计算某个 y 的表.
- 使用更紧凑的数据结构存储后缀表以降低常数因子, 例如 int[][] 连续数组替代对象数组.
- 对 large y 查询可使用选择算法（nth_element）替代堆来获得线性时间, 但由于 L ~ O(M) 且 M 较小, 堆方法常数因子更小且实现简单.

8) 运行与验证（快速回顾）

- 运行单测: 

```cmd
mvn -pl .\2018fall\lab_7\lab_7_1120 -am test
```

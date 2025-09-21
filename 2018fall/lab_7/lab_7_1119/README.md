## Description

Ella has a sequence of n integers. After she learns the 'Bubble sort' (in ascending order), she wants to implement this algorithm.

The question is what the sequence looks like after K times 'Bubble operation'. One 'Bubble operation' is like this:

for(int i = 1; i < n; ++i) { if(a[i] > a[i + 1]) swap(a[i], a[i + 1]); }

The sequence starts from 1, and its length is n.

### Input

The first line will be an integer T, which is the number of the test cases (1 <= T <= 10).

For each test case, the first line will be two integers n and K (1 <= K <= n <= 200000).

The second line will be n integers a1 ... an (1 <= ai <= 10^9), representing the original sequence.

It queries what the sequence is like after K times 'Bubble sort' from the original sequence.

### Output

For each query, print the sequence in one line, do not print extra space at the end of one line.

### Sample Input

```log
1
5 1
5 4 3 2 1
```

### Sample Output

```log
4 3 2 1 5
```

## 基于 `Main.cpp` 的实现分析

以下内容解释仓库中 `src/Main.cpp` 的实现逻辑、复杂度与正确性要点（中文）：

- 问题回顾
  - 给定数组 a[0..n-1]（原 Java 版本以 1-based 描述，但实现使用 0-based 索引），执行 K 次“bubble operation”后要求输出结果数组。
  - 每次 bubble operation 从左到右依次比较相邻两元素并在逆序时交换，相当于每次操作中较大的元素向右移动最多 1 位，而较小的元素可以向左移动多达 1 位（具体视相对位置而定）。

- main.cpp 的核心思路（贪心 + 有序集合）：
  1. 将每个元素打包为 (value, orig_index)。
  2. 按 value 升序排序；当 value 相等时按原索引升序（stable 排序保证同值元素相对秩序与限定的可移动范围兼容）。
  3. 维护一个可用位置集合 free_pos，初始为 {0,1,...,n-1}（使用 std::set 实现，支持 ceiling 查询）。
  4. 依次按排序后的元素（从最小值到最大值）取出元素 (val, orig)。对于该元素，它在 K 次操作后最早可以到达的位置下界为 max(0, orig - K)。
     - 在 free_pos 中选择第一个不小于该下界的位置（lower_bound / ceiling）。把该位置分配给当前元素并从 free_pos 中删除。
  5. 最终得到的数组即为按每个元素能到达的最早有效位置贪心放置后的结果。

- 正确性直观说明
  - 把较小的元素尽早放在能到达的位置是贪心且安全的：较小元素放在更左侧会让最终序列尽可能靠前地出现小值，与多次局部相邻交换的效果一致。
  - 当 K 足够大（例如 K >= n-1）时，对每个元素 desired = max(0, orig - K) 会变为 0，贪心过程将把所有元素按值从小到大放到最左侧的可用位置，等价于对整组元素进行全局升序排序，这与多次 bubble 操作最终使数组完全排序的期望一致。
  - 对于相等元素，排序中保留了原索引的顺序（稳定性），这避免了在相等值间产生不必要的次序变换。

- 复杂度与空间
  - 排序：O(n log n)。
  - 对每个元素在 std::set 中进行 lower_bound + erase：每个操作 O(log n)，共 O(n log n)。
  - 总时间复杂度：O(n log n)。
  - 额外空间：用于存放 pairs、结果数组和集合，均为 O(n)。

- 边界与鲁棒性
  - 当 desired < 0 时代码会以 0 作为下界（orig - K 可能为负）。
  - 若 set.lower_bound 返回 end（理论上在合理输入下不应发生，因为集合大小与要放置的元素数量一致），实现中有回退到最后一个可用位置的保护逻辑以避免异常。
  - 支持 n 高达 200000，并且使用 O(n log n) 算法以保证性能。

- 编译与运行

下面给出使用 g++ 编译并运行命令：

``` bash
$ g++ ./main.cpp
$ ./a.out < ./resources/01.data.in
```

- 小结
  - `Main.cpp` 使用了“按值升序放置最早可达位置”的贪心策略配合有序集合来高效模拟 K 次 bubble 操作后的最终排列，时间复杂度为 O(n log n)，适用于题目给定的最大规模。
### 吐槽

opus-4.1给的java版本可用, 但是会TLE, 换 C++ 开快读快写 + O3 就能 AC

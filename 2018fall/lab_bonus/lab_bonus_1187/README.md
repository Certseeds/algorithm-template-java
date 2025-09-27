## Description

Hong wants to sort a given permutation by swapping two adjacent elements. It's easy for Hong to do that with minimum operations.

Hong finds that for some permutations, the solution is unique. Hong thinks that these permutations are good.

However, Hong is not good at maths. He asks you to judge whether the solution for the given permutation is unique.

Solution means the sequence of elements we choose in each operation.

### Input

The first line will be an integer T (1 <= T <= 10), which is the number of test cases.

For each test data:

The first line contains one integer N (1 <= N <= 10^5) — the number of the integers.

The next line contains N integers, a permutation of 1, 2, ..., N.

### Output

For each case, if there is a unique solution for this case, output one character `Y`. Otherwise, output `N`.

### Sample Input

```log
3
1
1
3
3 2 1
4
3 1 2 4
```

### Sample Output

```log
Y
N
Y
```

## 解法

### 算法思路

- 问题归约
  - 本题要求判断将一个长度为 N 的排列通过最少的相邻交换排序为升序的操作序列是否唯一. 等价地, 在仅允许交换相邻逆序对的最小操作序列中, 每一步是否存在且只有一个可选的相邻逆序对.

- 基本算法（实现中采用）
  - 维护一个集合 `inv` 存放当前数组中所有相邻逆序的下标 i (满足 a[i] > a[i+1]).
  - 若 `inv` 为空, 数组已排序, 返回 "Y"（唯一——不需要任何操作）.
  - 在模拟过程中:
    1) 若某一步 `inv.size() > 1` 则存在多于一种选择, 直接返回 "N".
    2) 否则取出集合中唯一的下标 i, 执行交换 a[i] <-> a[i+1], 并仅更新 i-1,i,i+1 三个位置是否为相邻逆序 (加入或移除集合).
  - 当模拟完成且始终只有唯一选择时, 返回 "Y".

- 读-处理-输出分离
  - `reader()` 负责解析 T, 每个用例的 N 与排列数据; 在 reader 内对输入范围添加 `assert` 做基本校验.
  - `cal()` 接受 `TestCase` 列表, 对每个用例执行上述唯一性判断逻辑并返回结果字符串列表.
  - `output()` 聚合输出行并一次性打印, 每行以 '\n' 结尾.

- 复杂度与限制
  - 上述模拟算法在每次交换后局部更新相邻逆序信息, 单次更新为 O(1). 但在最坏情况下需要执行的交换次数等于逆序对的总数 K, 最坏 K 可为 O(N^2), 因此该实现的时间复杂度在最坏情况下可达 O(N^2), 不适合 N 极大且逆序数巨大的输入.
  - 空间复杂度为 O(N) 用于存储数组和相邻逆序下标集.

- 更高效的方向（建议）
  - 若需在 N 最大为 1e5 的约束下保证性能, 应避免逐步模拟所有交换. 可考虑基于逆序依赖关系建立一个有向依赖图并进行拓扑/强连通性分析, 或寻找组合学上的充分必要条件来判断“每一步仅有一个合法相邻逆序”是否始终成立. 这通常能将判断复杂度降低到 O(N log N) 或 O(N), 但需要较为精细的理论推导与实现.

- 实践提示与边界
  - 题目为排列问题, 元素唯一, 无需考虑重复元素处理逻辑.
  - 在实现时只需在每次交换后更新受影响的相邻位置 (i-1, i, i+1), 避免扫描全部数组以提高常数性能.
  - 在开发阶段使用 `assert` 校验 N 的上界和排列合法性; 若线上评测不允许断言, 请在提交前移除或捕获并处理异常.

实现细节参考: `src/Main.java` 中的 `reader`, `cal`, `checkUnique`, `output`.

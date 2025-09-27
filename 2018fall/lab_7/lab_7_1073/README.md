# 平衡二叉树

## Description

In a company, there are thousands of employees.  Your work is to check the information about the salary.

There is a minimum salary (it is the same for everyone).

If someone's salary is less than this minimum salary, he/she will leave the company.

There are n operations, and each operation is one of the following cases:

Insert x: a fresh man joins the company, whose salary is x.

Add x: increase one's salary by x.

Subtract x: reduce everyone's salary by x.

Query x: print the k-th maximum salary in this company.

At first, there is no employee in the company.

### Input

The first line will be an integer T, which is the number of test cases. (1 <= T <= 10).

For each test case, the first line will be two integers n (1 <= n <= 10^5) and m (1 <= m <= 10^6),

n is the number of operations, m is the minimum salary.

For the following n lines, each line will be one of the following cases:

I x: Insert x.

A x: Add x.

S x: Subtract x.

Q x: Query x.

### Output

For each "Query", print the k-th maximum salary in this company.

If the number of employees is less than k, print "-1".

At last, print the number of employees who has left the company.

We guarantee that no one will come back to the company after he (or she) leaves.

### Sample Input

```log
1
9 10
I 60
I 70
S 50
Q 2
I 30
S 15
A 5
Q 1
Q 2
```

### Sample Output

```log
10
20
-1
2
```

操作日志:

``` log
薪水列表: 60
薪水列表: 60, 70
薪水列表: 10, 20
输出: 10
薪水列表: 10, 20, 30
薪水列表: -5, 5, 15 =>, 有两个低于10, 两个离开, 现在薪水列表: 15
薪水列表: 20
Output: 20
Output: -1
Output: 离开了两个, -2
```

### HINT

Please find extra material to learn how to construct the balanced binary tree.

### Implementation Notes (Main.java)

1) 总体结构

- 程序遵循 reader -> cal -> output 三段式: 
  - `reader()` 使用 `FastScanner` 读取所有测试用例并做必要的 `assert` 检查; 
  - `cal()` 接受 `List<TestCase>` 并返回 `List<String>`, 处理核心逻辑; 
  - `output()` 负责把结果逐行输出, 行尾统一换行.
- 这种分离便于单元测试与局部调试, 也符合仓库约定.

2) 数据结构与核心思路

- 为了在所有操作下达到良好性能（目标 O(n log n)）, 实现使用了一个基于随机优先级的平衡二叉树 Treap 来维护在职员工的“存储工资值”.
- 关键设计: 维护一个全局偏移 `offset`, 在 `I` 插入时实际存入 `salary - offset`; `A`/`S` 操作只修改 `offset`, 避免对整个集合逐项更新.
- Treap 节点包含: `key`（存储值）、`count`（相同 key 的重复数）、`size`（子树总人数）、随机 `priority`、左右子节点. Treap 支持插入、按秩查询（kth）、以及基于 key 的区间删除（用于 S 操作一次性移除所有薪水低于阈值的节点）.

3) 操作语义映射

- `I x`: 若 `x < m` 则该员工立即离职并计数; 否则插入 `key = x - offset` 到 Treap（支持重复）.
  - 注意, 工资不够, 他都不入职, 也就不算离职.
- `A x`: `offset += x`, O(1).
- `S x`: `offset -= x`, 然后按阈值 `thr = m - offset` 在 Treap 中一次性删除所有 `key < thr` 的节点, 并累加离职人数, 复杂度 O(log n)（分裂/递归删除）.
- `Q k`: 若 `k` 超过当前人数返回 `-1`, 否则通过 Treap 的按秩查询返回第 k 大（内部转为 kth 小查询）, 复杂度 O(log n).

4) 复杂度

- 插入/删除/按秩查询等单次操作期望时间 O(log n).
- 总体时间复杂度: O(n log n), 适用于 n ≤ 1e5 的约束.
- 额外空间: O(n)（用于 Treap 节点和输入缓冲）.

5) 边界与正确性要点

- 在 `reader()` 中使用 `assert` 做输入约束检查, 例如 `assert ((0 <= m) && (m <= 1000000));`.
- 插入时如果 `x < m` 则立即计为离职（不会因为后续的 A 操作返回）.
- `S` 操作通过一次性删除小于阈值的子树保证不会重复访问被删除节点, 从而摊还成本较小.
- `Q` 在 `k <= 0` 或 `k > currentCount` 的时候输出 `-1`.

6) 可复现性与调试

- Treap 使用随机优先级来保持平衡; 实现中种子或随机器可根据需要设为固定值以便调试复现.
- 如需快速定位问题, 建议把 `reader()` 与 `cal()` 分别运行并对比中间结果（例如在本地插入临时日志）, 或使用仓库中 `resources/02.data.in` 等测试文件.

8) 参考与后续优化

- 当前 Treap 实现已在仓库中通过示例测试. 如果未来发现 `Q` 操作非常频繁且对常数因子敏感, 可以进一步优化: 例如使用固定随机数生成器、避免频繁创建 Random 实例、或用数组池复用节点, 以降低内存与 GC 开销.
- 另一可选方案是使用 Fenwick+坐标压缩来替代 Treap（在可提前收集所有可能 key 的情况下）, 但 Treap 在动态键集场景下更直观且更易实现区间删除.

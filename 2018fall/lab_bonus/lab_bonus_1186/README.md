## Description

Hong wants you to sort a given array. Hong is good at sorting, so he wants to make it more difficult. Each time, you can only choose two adjacent elements and swap them.

Hong wants to know how many swaps do you need at least to make the array in ascending order.

### Input

The first line will be an integer T (1 <= T <= 10), which is the number of test cases.

For each test data:

The first line contains one integer N (1 <= N <= 10^5) — the number of the integers.

The next line contains N integers Ai (1 <= Ai <= 10^9).

### Output

For each case, please print the least swap you need to make the array in ascending order.

### Sample Input

```log
1
4
4 1 2 3
```

### Sample Output

```log
3
```

## 解法

### 算法思路

- 问题归约
  - 本题要求最少的相邻交换次数使数组升序, 等价于计算数组的逆序对数.

- 常用方法
  - 方法一（推荐）: 坐标压缩 + 二分树状数组(BIT)或者树状索引, 从右向左遍历, 对每个元素统计右侧比它小的元素个数, 累加得到逆序对数. 时间复杂度 O(n log n).
  - 方法二: 归并排序计数逆序对, 时间复杂度同为 O(n log n), 实现相对简单且稳定.

- 读-处理-输出分离
  - reader(): 负责解析 T, 每个用例的 N 与数组, 并用 assert 做输入约束检查.
  - cal(): 对每个用例计算逆序对（返回 long 类型结果）并收集为字符串行列表.
  - output(): 使用 StringBuilder 聚合并一次性输出, 每行以 '\n' 结尾.

- 复杂度与类型
  - 时间复杂度: O(n log n) 每个用例; 空间复杂度: O(n) 主要用于坐标压缩和辅助数组.
  - 逆序对数在最坏情况下为 n*(n-1)/2, 对于 n=1e5 需要用 64 位整数(long) 存储.

- 边界与实现提示
  - 注意数组中可能存在重复元素, 坐标压缩时需要对相等元素归为同一秩.
  - 对于 Java 实现, 建议使用 BufferedReader+StringTokenizer 做快速输入, 并避免递归深度问题.
  - 在开发/调试阶段可开启 assert 检查输入约束, 但在正式提交时需确保 judge 环境允许 assert 或移除断言.

实现细节请参见 `src/Main.java` 中的 reader, cal, output 的具体代码实现.

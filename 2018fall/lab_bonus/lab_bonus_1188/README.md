## Description

Hong finds that the kth smallest number problem is too easy. He comes up with a harder problem.

Given N integers A1...An. Let B_k = |Ai - Aj| (1 <= i < j <= N); we get C(n, 2) values B_k. Hong wants to know the median of these differences.

If C(n, 2) is even, the median is defined as the (C(n, 2) / 2)-th smallest number.

### Input

The first line will be an integer T (1 <= T <= 10), which is the number of test cases.

For each test data:

The first line contains one integer N (1 <= N <= 10^5).

The next line contains N integers Ai (1 <= Ai <= 10^9).

### Output

For each case, please print the median.

### Sample Input

```log
1
4
1 2 3 4
```

### Sample Output

```log
1
```

## 解法

### 算法思路

- 问题归约
  - 题目要求取所有 |Ai - Aj| (1 <= i < j <= N) 的中位数（若对数为偶数, 按题意取第 (C(n,2)/2) 小的值）. 将这个问题看作在值域上求第 k 小的 pairwise difference.

- 关键思路
  - 先对原数组排序, 使得差值为 a[j] - a[i] (j > i) 恒为非负并满足单调性质.
  - 使用二分搜索答案 D: 对每个候选距离 D 统计数组中有多少对 (i, j) 满足 a[j] - a[i] <= D. 若计数 >= k, 则实际第 k 小差值 <= D, 否则大于 D. 通过二分定位最小满足条件的 D 即为答案.
  - 计数实现采用双指针: 对每个 i 用指针 j 向右移动直到 a[j] - a[i] > D, 累加 j - i - 1 为以 i 为左端点的满足的对数. 该计数为 O(N) 总时间.

- 读-处理-输出分离
  - `reader()` 使用快速输入解析 T, 每个用例的 N 与数组, 并用 assert 检查输入范围和基本合法性.
  - `cal()` 对每个用例调用排序 + 二分 + 双指针计数逻辑, 返回结果的字符串列表.
  - `output()` 使用 StringBuilder 聚合并一次性打印所有结果, 每行以 '\n' 结尾.

- 复杂度
  - 排序: O(N log N).
  - 二分搜索值域: 值域宽度为 max(A) - min(A), 二分复杂度为 O(log W) 次, 每次计数 O(N), 因此二分部分为 O(N log W). 整体通常写作 O(N log N + N log W). 在实践中 W<=1e9, log W~30, 因此可接受.
  - 空间复杂度: O(N) 主要用于排序与临时变量.

- 边界与实现提示
  - 使用 64 位整型(long) 保存对数计数或中间乘积, 以免溢出 (pairs = N*(N-1)/2 可达 ~5e9).
  - 当 N 较小时(例如 N<=1), 特殊处理或由通用逻辑自然返回正确结果.
  - 双指针计数中 j 指针可以不回退, 使计数为线性时间.
  - 在 Java 实现中建议使用 BufferedReader + StringTokenizer 做快速输入, 并在 reader 中加入 assert 以便在调试阶段尽早发现非法输入.

实现细节请参见 `src/Main.java` 中的 reader, cal, output 的具体实现 (已实现排序 + 二分 + 双指针计数).

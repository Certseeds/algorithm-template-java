# Sum

## Description

An N × M matrix of non-negative integers, please take out serveral numbers from it such that the summation of these numbers is maximized.

You should guarantee that any two taken out numbers are not adjacent, we say $a$ is adjacent to $b$ if $b$ is one of $a$'s 8-connected neighborhoods in the matrix.

### Input

The first row has a positive integer $T$ $(1\leq T\leq 20)$, which indicates that there are T groups of data.

For each set of data, the first row has two positive integers $N, M$ $(1\leq N, M\leq 6)$, which describe the number matrix as N rows and M columns.

The next N rows, with M non-negative integers in each row, describe this numerical matrix.

### Output

A total of T rows, one non-negative integer per row, and the output of the resulting answer.

### Sample Input

``` log
3
4 4
67 75 63 10
29 29 92 14
21 68 71 56
8 67 91 25
2 3
87 70 85
10 3 17
3 3
1 1 1
1 99 1
1 1 1
```

### Sample Output

``` log
271
172
99
```

## 算法分析

### 问题理解

给定 N x M 的非负整数矩阵, 选取若干元素使得和最大, 要求任意两个选取的元素不能 8-连通相邻.

8-连通相邻指: 上, 下, 左, 右, 以及四个对角方向.

### 算法思路

由于 N, M <= 6, 可以使用状态压缩动态规划.

1. 状态压缩: 用二进制掩码 (bitmask) 表示一行中哪些位置被选取. 对于 M 列, 共有 2^M 种状态.

2. 有效状态筛选: 预先筛选出单行内有效的状态 (相邻两列不能同时选取):
   - 条件: (mask & (mask << 1)) == 0

3. 相邻行兼容性检查: 两行的状态 prevMask 和 currMask 兼容的条件:
   - 不能垂直相邻: (prevMask & currMask) == 0
   - 不能左斜对角相邻: ((prevMask >> 1) & currMask) == 0
   - 不能右斜对角相邻: ((prevMask << 1) & currMask) == 0

4. DP 转移:
   - dp[mask]: 当前行选取状态为 mask 时的最大和
   - 对于每一行, 枚举当前行和上一行的所有有效状态组合, 检查兼容性后转移

5. 最终答案: 遍历最后一行所有有效状态, 取最大值

### 复杂度分析

- 有效状态数: 约 O(Fibonacci(M)) (因为不能有相邻的 1)
- 时间复杂度: O(N x S^2 x M), 其中 S 是有效状态数, 最坏约 O(N x 4^M)
- 由于 M <= 6, 实际状态数很少, 算法可以高效运行
- 空间复杂度: O(2^M), 存储 DP 数组

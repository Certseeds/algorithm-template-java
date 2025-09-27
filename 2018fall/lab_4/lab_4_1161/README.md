## Description

Give you an increasing sequence A of length n, and find the number of ordered tuples (i, j, k) such that the maximum element in {A[i], A[j], A[k]} minus the minimum element in {A[i], A[j], A[k]} <= m.

### Input

The first line of the input is T (T <= 5), which is the number of test cases.

For each test cases, the first line is n, m, the next line contains n integers which is the given sequence A. (1 <= n <= 100000 ; 1 <= m <= 1000000000) abs(A[i] <= 1000000000).

### Output

For each test cases, print the number of tuples satisfying requirements in one line.

### Sample Input

``` log 
2
4 3
1 2 3 4
5 19
1 10 20 30 50
```

### Sample Output

``` log
4
1
```

## 解答

本题要求在一个递增序列 `A` 中, 找出满足 `最大值 - 最小值 <= m` 的三元组 `(i, j, k)` 的数量.

由于序列 `A` 是递增的, 对于任意满足 `i < j < k` 的索引, 三元组 `{A[i], A[j], A[k]}` 的最小值就是 `A[i]`, 最大值就是 `A[k]`. 因此, 问题可以转化为: 找出所有满足 `i < j < k` 且 `A[k] - A[i] <= m` 的索引组合 `(i, j, k)` 的数量.

如果直接使用三层循环暴力枚举 `i, j, k`, 时间复杂度将达到 `O(n^3)`, 在 `n` 最大为 100,000 的情况下会超时.

我们可以采用更高效的双指针(或称滑动窗口) 算法, 将时间复杂度优化到 `O(n)`.

算法思路如下: 
1.  我们从左到右遍历数组, 用一个指针 `i` 来固定三元组中最小的那个数 `A[i]`.
2.  对于每个固定的 `i`, 我们用另一个指针 `right` 从 `i` 开始向右移动, 找到一个最远的位置, 使得所有在 `i` 和 `right` 之间的数 `A[x]` 都满足 `A[x] - A[i] <= m`.
3.  这样, 我们就得到了一个“窗口” `[i, right-1]`. 对于固定的 `i`, 我们需要在这个窗口内再选择两个数 `A[j]` 和 `A[k]`. 选择的范围是从 `i+1` 到 `right-1`, 共有 `(right - 1) - (i + 1) + 1 = right - i - 1` 个元素.
4.  从这 `right - i - 1` 个元素中任意选择两个, 就是一个满足条件的三元组. 这本质上是一个组合问题, 数量为 `C(right - i - 1, 2)`.
5.  我们将每个 `i` 对应的组合数累加起来, 就是最终的答案.

代码实现中, 我们用一个 `for` 循环来移动左指针 `i`, 用一个 `while` 循环来扩展右指针 `right`. 由于 `right` 指针只增不减, 整个算法的两个指针都只遍历了一遍数组, 总时间复杂度为 `O(n)`. 由于结果可能很大, 我们使用 `BigInteger` 来存储最终的计数值.

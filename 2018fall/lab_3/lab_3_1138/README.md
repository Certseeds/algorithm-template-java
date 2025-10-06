---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

## Description

Merge sort is a widely known sorting algorithm.

In each step of merge sort, it sorts two arrays and combines them to one sorted array.

Now, Joy has already implemented the algorithm successfully, but he does not know how to combine two sorted arrays to ones.

Can you help Joy?

### Input

First line will be a positive integer $T$ $(T \leq 15)$, which is the number of test cases.

In each test case, the first line will be two integer $n$ and $m$, which are the lengths of the two arrays.

The second line will be the terms of the first array.

The third line will be the terms of the second array.

$(1 \leq n, m \leq 100000)$, the combat values will in the range of $[0, 10^9]$.

### Output

For each test case, print the combined array.

### Sample Input

``` log
2
4 5
1 2 3 4
1 2 3 4 5
1 3
2
1 3 4
```

### Sample Output

``` log
1 1 2 2 3 3 4 4 5
1 2 3 4
```

### 算法分析

本题要求将两个有序数组合并为一个有序数组. 实现采用双指针法, 分别遍历两个数组, 每次取较小值加入结果, 若相等则都加入.

时间复杂度 $O(n+m)$, 空间复杂度 $O(n+m)$.

该方法保证合并后数组仍有序, 适用于大规模数据. 实际上就是归并排序的一部分

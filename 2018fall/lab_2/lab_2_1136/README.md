---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_2_1136

## Description

Annual Sport meeting in S University starts again.

This year, the rule of relay running is modified by president of sports department.

The rule after modification is as follows:

The total length of the relay running is $L$ $(1 \leq L \leq 10^9)$.

There are $n$ $(0 \leq n \leq 500000)$ possible places to place a new racer (there is a racer in the start line).

Every racer runs to the next racer in front of him and the final racer runs to the finish line.

But the number of racers is limited to a number $m$ $(1 \leq m \leq n+1)$.

Team of class 1788 does not want any of them to run too much.

Therefore, they wish to minimize the longest distance that any one of them needs to run.

### Input

The input contains several test cases.

The first line of each case contains three positive integer $L, n, m$.

Then $n$ lines follow.

Each stands for the distance from the start line to the $n$th possible place to place new racer, two places will not be the same.

### Output

For each case, output an integer which is the minimum longest distance that any one of class 1788 needs to run.

### Sample Input

``` log
6 1 2
2
25 3 3
11 2 18
```

### Sample Output

``` log
4
11
```

### HINT

`Arrays.sort()` is allowed in this problem.

## 算法分析

本题要求将接力赛道分成m段，使每段长度的最大值尽量小。可以用二分法来解决。

首先将所有可选接力点和起终点排序。然后用二分法枚举最大段长，每次判断能否用不超过m个人完成分段。如果可以，则尝试更小的段长，否则增大段长。

判断时，依次累加每段长度，超过当前枚举值就分配新一段，直到分段数超过m或全部分配完毕。

算法复杂度约为O(n log L)，其中n为接力点数量，L为赛道总长。

---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_3_1143

## Description

Under your help, Joy finally got the job in casino.

The boss of the casino is very busy, he can only go to the casino one time every two days.

When the boss goes into the casino, he asks Joy to give him the median of the past days' turnovers.

The median is the number in the middle of an array after sorting it.

As you know, Joy is not good at math, he asks you for help again!

Now given the turnovers for $n$ days, the boss will come to casino on the first day, and the third day, the fifth day...

Please help Joy write a program to output the median of the turnovers when boss comes.

### Input

The first line will be an integer $T$ $(1 \leq T \leq 10)$, which is the number of test cases.

For each test case, the first will be an integer $n$.
Then there will be $n$ integers, $a[i]$.

$(1 \leq n \leq 300000, 0 \leq a[i] \leq 300000)$

### Output

For each test case, output the median every time the boss comes.

### Sample Input

``` log
1
5
1 2 3 4 5
```

### Sample Output

``` log
1 2 3
```

## 分析

很容易想到两个优先队列, 一个叫min的最小堆, 一个max最大堆.

最小堆堆顶>最大堆堆顶, 保证最小堆size == 最大堆size || 最小堆size-最大堆size == 1

这样最小堆堆顶就是中位数.

### 算法分析

本题需动态维护中位数

采用最大堆和最小堆分别存储较小和较大值，每次插入后调整堆大小，保证最小堆顶为中位数

每隔一天输出当前中位数

时间复杂度 $O(n\log n)$，空间复杂度 $O(n)$，适合大数据流场景

# Lucky number

## Description

Given $n$ numbers $a_1,a_2,...,a_n$, and an integer $k$.

Lanran thinks that the $k^{th}$ smallest number is a lucky number.

Please tell him what is the value of the lucky number.

## Input

The first line of the input contains two integers $n, k(1\le n\le 1\ 000\ 000, 1\le k\le n)$.

The second line contains $n$ integers $a_{1},a_{2},...,a_{n}(0\le a_{i}\le 1\ 000\ 000)$.

## Output

Output one integer indicates the answer.

## Sample Input

``` log
5 3
1 4 2 6 8
```

## Sample Output

``` log
4
```

### 算法分析

本题要求输出第 $k$ 小的数。解法采用快速选择（Quickselect）算法：

1. 通过快速选择在 $O(n)$ 期望时间内找到第 $k$ 小的元素。
2. 每次随机选取枢轴，将数组分为小于和大于枢轴两部分，递归查找目标位置。
3. 由于 $k$ 可能很大，不能直接排序，否则会超时。

时间复杂度期望 $O(n)$，空间复杂度 $O(1)$（原地操作）。

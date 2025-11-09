## Description

Dateri has a magic sequence and LowbieH is interested in it.

Dateri promises that if LowbieH can answer his question, then he will play fencing with LowbieH.

We denote the magic sequence by $\{a_n\}$ and Dateri will choose a lucky number $k$.

He asks LowbieH to find length of the longest consecutive subsequence such that the absolute difference between any two number in the subsequence should not exceed $k$.

Can you help LowbieH?

## Input

The first line contains two integers $k$ $(0 \leq k \leq 2\times 10^9)$ and $n$ $(1 \leq n \leq 3 \times 10^6)$.

The second line contains $n$ integers $a_1,\cdots,a_n$ $(1 \leq a_i \leq 2 \times 10^9,\ 1 \leq i \leq n)$.

## Output

One line contains the answer, i.e. the length of the longest available consecutive subsequence.

## Sample Input

``` log
3 9
5 1 3 5 8 6 6 9 10
```

## Sample Output

``` log
4
```

## HINT

There are two available consecutive subsequences : $\{5,8,6,6\}$ and $\{8,6,6,9\}$.

## 思路

滑动窗口最大/最小值问题, 双端队列维护区间最大最小值下标.

每次右端点扩展, 维护窗口内最大最小值, 若区间差大于k则左端点右移.

记录所有满足条件的最大区间长度.

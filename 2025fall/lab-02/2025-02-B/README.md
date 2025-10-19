# Double Median

## Description

Given a sequence $\{a_1, a_2, ..., a_n\}$ with $n$ numbers.

Please output the double of the median of $\{a\}$.

The definition of the median: Assume $m$ is the median of $\{a\}$: if $n$ is odd, then $2m = 2a_{\frac{n+1}{2}}$; if $n$ is even, then $2m = a_\frac{n}2+a_{\frac{n}2 + 1}$

## Input

The first line is an integer $n$.

The second line will be $n$ integers: $a_1, a_2, ..., a_n$.

For all cases, $1 \leq n \leq 5*10^6$, $0 \leq a_i \leq 2147483647$.

## Output

Output one integer indicates the double of the median.

## Sample Input

``` log
6
3 2 1 4 5 6
```

## Sample Output

``` log
7
```

## HINT

In the sample case, the median is 3.5, so the answer is 3.5 * 2 = 7.

### 算法分析

本题要求输出序列的中位数的两倍。解法采用快速选择（Quickselect）算法：

1. 若 $n$ 为奇数，直接找第 $\frac{n}{2}$ 小的数，答案为其两倍。
2. 若 $n$ 为偶数，找第 $\frac{n}{2}-1$ 和 $\frac{n}{2}$ 小的数，答案为两者之和。
3. 快速选择算法期望 $O(n)$，适合大数据量。

注意：实现时需考虑偶数情况下的两个中位数位置。

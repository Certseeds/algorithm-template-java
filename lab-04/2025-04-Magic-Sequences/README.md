## Description

IceRuler has a sequence $A = [a_1, a_2, ..., a_n]$.

With a magic number $m$, he wants you to tell him the maximum element in these intervals $[a_1, ..., a_m]$, $[a_2, ..., a_{m+1}]$, ..., $[a_{n-m+1}, ..., a_n]$.

## Input

The first line is the magic number $m$.

The second line contains $n$ integers, where the $i$-th integer represents the integer $a_i$.

Input ends with a number $-1$. Constraints: $1 \leq m < n$, $1 \leq n \leq 2000000$, $0 \leq a_i \leq 1000000000$

## Output


Print one integer $K$ in one line, represents the XOR sum of the maximum elements in $a[i]..a[i + m - 1]$s.

$K = $ the max number in $[a_1, ..., a_m]$ $\oplus$ the max number in $[a_2, ..., a_{m+1}]$ $\oplus$ ... $\oplus$ the max number in $[a_{n-m+1}, ..., a_n]$.

$\oplus$ is the bit operation exclusive OR.

## Sample Input

``` log
5
121 123 122 13 12 12 52 2 6 7 32 7 324 34 124 213 214 1412 -1
```

## Sample Output

``` log
1178
```

## HINT

Easy pro.

### 算法分析

本题采用单调队列维护滑动窗口最大值.

每次窗口滑动, 弹出不在窗口内的下标, 维护队列递减性.

每个窗口最大值做异或累加, 最终输出结果.

时间复杂度 $O(n)$, 空间复杂度 $O(n)$.

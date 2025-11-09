# Polynomial Summation

## Description

Given two polynomials with n and m terms.

Please calculate the sum of the two polynomials.

Inputs and outputs are exponentially larger to smaller.

### Input

The first line has two numbers: n and m (0 < n, m < 1000000).

The next n lines are the coefficients and exponents of the terms in the first polynomial.

After that, the next m lines, are the coefficients and exponents of the terms in the second polynomial.

The values of all coefficients and exponents are in the range (-1e9, 1e9).

### Output

The first line is an integer k, it is the number of terms in the result.

The next k lines are the coefficients and exponents of the result.

### Sample Input

```log
2 2
1 3
2 1
1 4
2 3
```

### Sample Output

```log
3
1 4
3 3
2 1
```

### HINT

The introduction of Polynomial is <https://en.wikipedia.org/wiki/Polynomial>

You need this.

Java FastIO template: <https://paste.ubuntu.com/p/6ybMcVXvz5/> in this lab!

### 算法分析

本题通过双指针合并两个降幂多项式链表，遇到同幂次项则系数相加，系数为零则跳过。输入输出均用快读快写，时间复杂度 $O(n+m)$，空间复杂度 $O(n+m)$。断言保证输入合法。

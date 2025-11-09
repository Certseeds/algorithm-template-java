---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

## Description

Linked List is one of most simple and fundamental data structure, thus it has a very wide application.

For example, linked list can be used to calculate the sum of two polynomials.

Now, given two polynomials by the coefficient and exponent of each term (exponent of each term is in ascending order), please output the sum of the two polynomials.

### Input

First line will be a positive integer $T$ $(T \leq 100)$, which is the number of test cases.

The first line will be an integer $n$, which is the number of terms of the first polynomial.

Then $n$ lines will be the coefficients and exponents of the terms.

After $n + 1$ lines, there will be an integer $m$ for the number of terms of the second polynomial.

And $m$ lines of (coefficient, exponent) pairs.

$(0 \leq n, m \leq 1000)$, all exponents are in the range $[0, 10^9]$, all coefficients are in the range $[-10000, 10000]$.

### Output

For each test case, print the polynomial in ascending order of each exponents.
Be attention to the format of the polynomial.

### Sample Input

``` log
2
2
1 2
2 3
2
2 2
1 4
2
2 0
-2 1
2
3 1
1 2
```

### Sample Output

``` log
3x^2+2x^3+x^4
2+x+x^2
```

### 算法分析

幂次和系数求导, 合并同幂次项, 最后按幂次升序输出

采用哈希表存储结果, 时间复杂度 $O(n)$, 空间复杂度 $O(n)$, 适合稀疏多项式处理

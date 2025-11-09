---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_3_1144

## Description

Joy is now working on a project including bunches of mathematical problems.
However, as you know, Joy is not good at math. (You can assume he is not good at everything)
He wants a program to calculate the differentiate of polynomials.

### Input

The first line includes a positive integer $T$ $(T \leq 100)$ representing the number of test cases.

The second line includes an integer $n$ representing the number of terms of the polynomial.

Then in the next $n$ lines, each line includes the coefficients and exponents of the each term.
$(0 < n \leq 1000)$, all exponents are in the range $[-1000, 1000]$, all coefficients are in the range $[-1000, 1000]$.

### Output

For each test case, print the polynomial in ascending order of each exponents.
If several terms have same exponent, you should add them up.
Remember to pay attention to the format of the polynomial.

### Sample Input

``` log
2
2
1 2
2 3
2
1 -2
-2 3
```

### Sample Output

``` log
2x+6x^2
-2x^-3-6x^2
```

输出部分逻辑和1140一致, 处理甚至还简单一些...

难以分析想表达点什么, 这和sort有关系吗?

### 算法分析

本题实现多项式求导。遍历每项, 按幂次和系数求导, 合并同幂次项, 最后按幂次升序输出

采用哈希表存储结果, 时间复杂度 $O(n)$, 空间复杂度 $O(n)$, 适合稀疏多项式处理。

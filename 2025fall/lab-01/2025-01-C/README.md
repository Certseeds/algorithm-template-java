# Sum

## Description

Given $n$, please calculate $S(n)$ where

$$ S(n)=\sum_{i=1}^n i(i+1)/2 $$

### Input

The 1st line is a positive integer $T$ $1 \leq T \leq 100000$ which is the number of test cases.

Then $T$ lines follow.

Each line has an integer $n$ $1 \leq n \leq 1000000$ for a test case.

### Output

Output $T$ lines.

Each line is the result value $ans$.

### Sample Input

``` log
2
1
2
```

### Sample Output

``` log
1
4
```

### 解释算法

本题要求多次计算 $S(n) = sum_{i=1}^n i(i+1)/2$

实现思路如下：

1. 快读读入 T 组 n。
2. 对每个 n，利用公式化简 $S(n) = (sum_{i=1}^n i^2 + sum_{i=1}^n i) / 2$
3. 其中 $sum_{i=1}^n i^2 = n(n+1)(2n+1)/6，sum_{i=1}^n i = n(n+1)/2$
4. 直接代入公式计算，避免循环。
5. 输出所有结果。

时间复杂度 O(T)，空间 O(1)。

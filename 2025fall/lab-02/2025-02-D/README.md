## Description

Suppose there are n students in CS203 DSAA, the height of each student is $a_i (1 \leq i \leq n)$.

You are asked to arrange these students to the seats (i.e., $b_1, b_2, ..., b_n$) in a round table such that the minimum value, denoted by $k$, of the medium height of every three students in consecutive seats (i.e., $b_i$, $b_{i\bmod n + 1}$, $b_{(i + 1)\bmod n + 1}$ ) is maximized.

Please print $k$ and the height of the student in seats $b_1, b_2, ..., b_n$.

If it has more than one possible solution, print the height of the student in seats $b_1, b_2, ..., b_n$ with smallest lexicographical order.

## Input

There will be two lines.

The first line will be an integer $n$.

The second line will be $n$ integers $a_1, a_2, ..., a_n$.

For all test cases, $3\leq n\leq 10^6, 0\leq a_i\leq 10^9$.

## Output

There will be two lines.

The first line prints the value of k.

The second line prints the height of the student in seats $b_1, b_2, ..., b_n$.

## Sample Input

``` log
6
6 5 4 3 2 1
```

## Sample Output

``` log
3
1 3 4 2 5 6
```

## HINT

You can find the definition of lexicographical order at <https://en.wikipedia.org/wiki/Lexicographic_order>

### 算法分析

本题要求将学生围成一圈，使得所有连续三人中位数的最小值最大，并输出字典序最小的排列。

1. 首先对所有身高排序。
2. 取最小的 $\lfloor n/3 \rfloor$ 个数，均匀插入到排列的每隔两个位置，其余按顺序填充。
3. 这样保证每组三人中最小的中位数最大。
4. 最后输出该最大中位数和排列。

时间复杂度 $O(n \log n)$，空间复杂度 $O(n)$。

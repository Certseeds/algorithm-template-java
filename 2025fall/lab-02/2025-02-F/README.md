# Plants vs. Zombies

## Description

You want your plants be more stronger to against the zombies.

You have $n$ plants, each plant has two attributes: height and strength.

Crazy Dave has two kinds of fertilizer $F_h$ and $F_s$.

Each bag of $F_h$ can make one plant double its height, and each bag of $F_s$ can make one plant's strength equals its height.

Crazy Dave gives you p bags of $F_h$ and q bags of $F_s$.

You want to maximize the sum of your plants' strength.

More formally, you are given n pair of integers $<h_i, s_i>$, which indicates the height and strength of the i-th plant.

You can use at most p times $F_h$ and at most q times $F_s$.

If you give a $F_h$ to the i-th plant, then $h_i = h_i * 2$.

If you give a $F_s$ to the i-th plant, then $s_i = h_i$.

You want to maximize the $\sum s_i$.

## Input

The first line of the input contains three integers $n,p,q$.

For the next $n$ lines, each line contains two integers $h_i, s_i$, indicates the height and strength of the i-th plant.

For all cases, $1 ≤ n ≤ 2*10^5,0 ≤ p ≤ 20,0 ≤ q ≤ 2*10^5, 1\leq h_i,s_i \leq 10^9$.

## Output

Print one integer indicates the maximum of $\sum s_i$.

## Sample Input

``` log
2 1 1
10 8
6 1
```

## Sample Output

``` log
21
```

### 算法分析

本题要求通过有限次数的两种肥料操作，使所有植物的强壮值之和最大。

1. 首先计算每株植物不使用 $F_h$ 时，$F_s$ 带来的最大增益，并对所有植物按增益排序，取前 $q$ 个。
2. 对于每株植物，考虑将 $p$ 次 $F_h$ 全部用在该株上，计算此时 $F_s$ 带来的最大增益，并与原排序合并，更新最大值。
3. 通过前缀和和二分查找优化增益合并过程，避免重复计算。
4. 最终取所有方案中的最大值。

整体时间复杂度 $O(n \log n)$，空间复杂度 $O(n)$。

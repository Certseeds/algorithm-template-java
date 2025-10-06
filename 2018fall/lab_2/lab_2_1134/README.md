---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_2_1134

## Description

A remote planet is called Joy.

There is an ancient rule in Joy that every kid need to go for a hiking in dark forest to become an adult.

This year, the kids of Joy that need to go for a hiking want to go together to ensure their safety.

But they want to calculate a position to gather which minimizes the sum of their "distance".

To simplify, we assume Joy is a straight line, every kid has a location $x_i$ on the line and every $x_i$ has a corresponding weight $w_i$.

As the gravitational force on Joy differs from earth, the "distance" between their home and the gathering position $p$ need to be calculated as $S^3 \times W_i$ where $S$ is $|x_i - p|$.

In a word, we need to calculate the position $p$ that minimize the total distance $\sum(S^3 \times W_i)$ where $S$ is $|x_i - p|$.

### Input

The first line of the input is the number $T$ $(T \leq 20)$, which is the number of test cases.

The first line of each case has one integer $N$ $(1 \leq N \leq 50000)$, indicating the number of kids.

Then comes $N$ lines in the order that $x_i \leq x_{i+1}$ for all $i$ $(1 \leq i < N)$.

The $i$th line contains two real number: $X_i, W_i$, representing the location and the weight of the $i$th kid. $(|x_i| \leq 10^6, 0 < w_i < 15)$

### Output

For each test case, please output a line which is "Case #X: Y", $X$ is the number of the test case and $Y$ is the minimum sum of "distance" which is rounded to the nearest integer.

### Sample Input

``` log
1
4
0.6 5
3.9 10
5.1 7
8.4 10
```

### Sample Output

``` log
Case #1: 832
```

### HINT

Triple search.

## 分析

本题要求在一条直线上找到一个聚集点，使所有孩子到该点的加权距离立方和最小.

算法采用三分法搜索最优聚集点. 由于目标函数是关于聚集点p的单峰函数，可以用三分法在区间内不断缩小范围，最终找到使总距离最小的位置.

每次三分时，计算区间内两个点的总距离，比较后收缩区间. 最终取区间中点作为答案.

时间复杂度约为O(N log R)，其中N为孩子数量，R为搜索区间长度.

## 从直观上来看

1. 在取值最小, 取值最大时, 取值稍微向中间靠拢一些, 结果就会变小.
2. 在取值正好在最适合的值时, 向左或向右都会导致结果变大.

## 从函数的角度来看下

表达式$f(p) = \sum (|p-x_i|)^3 \times w$, 每一个都可以认为是一个在实数域上只有$x_i$一个零点, 左右在$x_i$上对称的三次函数.

在函数图像上, 这种情况比较类似于二次函数, 如果都是二次函数的话, 求导之后会发现导函数递增, 会在某点为零, 先为负数再为正数, 说明函数先减后增.

不用这个方式来思考的话, 多个三次函数之和, 在两个函数分别对应的零点之间移动时, 由于右侧的复数个三次函数在下降, 因此总和仍然下降. 直到到达最低点.

---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_welcome_1172

> problem_id: 1172

## Description

As we know boxes are in a shape of rectangular parallelepipeds.

Now we have a cardboard of size $n \times m$.

Please write a program to check whether it can form a box of size $a \times b \times c$.

The following sample is one possible way for the first sample.

## Input

The first line of input is the number of test cases $T$ $(1 \leq T \leq 100)$.

For each test case, the first line will be three integers $a, b, c$ $(1 \leq a, b, c \leq 100000000)$.

The second line are two integers $n, m$ $(1 \leq n, m \leq 100000000)$.

## Output

For each test case, print "Yes" (without quotes) if you can form the box of size $a \times b \times c$.

Otherwise print "No".

## Sample Input

``` log
1
1 2 3
6 5
```

## Sample Output

``` log
Yes
```

## 思考

1172这里实际上也不需要什么运算, 需要的只是把正方体(长方体也行)的展开图算一下, 按面对面的规则涂三种颜色, 并将其每个面的边长给分别给定
之后, 从11种展开图像中,得到能容纳这个展开图的边长, 再根据a,b,c的可交换性轮换几遍,去重, 最后得到21组长-宽组合

---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_2_1129

## Description

Joy is a twelfth grade student who is aiming to go to SUSTech.

One day, when he is doing math practices, he found a problem very hard.

It gives a function goes like $F(x) = 5x^7 + 6x^6 + 3x^3 + 4x^2 - 2xy$ $(0 \leq x \leq 100)$, $y$ is a given real number.

Can you help Joy find the minimum value of $F(x)$ when $x$ is in its domain?

### Input

The first line of the input contains an integer $T$ $(1 \leq T \leq 100)$ which means the number of test cases.

Then $T$ lines follow, each line has only one real number $Y$ $(0 < Y < 10^{10})$.

### Output

For each case, print the case number as well as the minimum value (accurate up to 4 decimal places) in one line, when $x$ is in its domain.

### Sample Input

``` log
3
100
200
300
```

### Sample Output

``` log
Case 1: -193.3774
Case 2: -448.1475
Case 3: -729.3383
```

## 算法实现与分析（中文）

本题的核心是求函数 $F(x) = 5x^7 + 6x^6 + 3x^3 + 4x^2 - 2xy$ 在区间 $[0, 100]$ 内的最小值，其中 $y$ 为输入参数。由于 $F(x)$ 是高次多项式，直接解析求极值较为困难，因此代码采用了数值方法进行求解。

### 主要思路

1. 函数封装：代码通过 `funcer` 类封装了目标函数 $F(x)$ 及其导数 $F'(x)$，便于后续计算。
2. 二分法求极值点：由于 $F(x)$ 在区间端点和导数为零的点可能取得最小值，代码采用二分法在 $[0, 100]$ 区间内寻找 $F'(x)=0$ 的点。
3. 边界处理：由于 $F'(0) = -2y < 0$，说明函数在 $x=0$ 附近递减；而 $F'(100)$ 随 $y$ 变化但始终为正，说明在 $x=100$ 附近递增。因此极小值点必然在区间内部。
4. 数值精度控制：二分法迭代直到区间长度小于 $1e^{-16}$，保证结果精度满足题目要求。
5. 结果输出：找到极小值点后，计算对应的 $F(x)$ 并按格式输出。

### 伪代码简述

- 对每个输入 $y$，在 $x\in[0,100]$ 内用二分法求 $F'(x)=0$ 的点。
- 计算该点对应的 $F(x)$，输出结果。

### 算法优点

- 利用导数信息快速定位极值点，避免枚举或暴力搜索。
- 精度高，效率好，适合高次多项式的极值求解。

### 复杂度分析

- 每组数据二分法迭代次数为 $O(\log(\frac{100}{\text{精度}}))$，整体复杂度 $O(T \log N)$，其中 $T$ 为测试组数。

---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_welcome_1171

> problem_id: 1171

## Description

A pair of English letters $(a, b)$ is considered beautiful iff one of them is uppercase, the other is lowercase and both of them are consonants.

(English letters except $a, e, i, o, u, w, y$).

Now give you a string which is consists of lowercase English letters.

Now you can change several letters from lowercase to uppercase.

Please write a program to calculate the maximum number of beautiful pairs formed by adjacent letters.

Note: If you change $x$ to $X$, then all $x$ in the string will become $X$.

For example, if we have string $strength$, we can change it to $StRenGtH$.

In this case, $(S, t), (t, R), (n, G), (G, t), (t, H)$ are beautiful.

So the result is $5$.

## Input

The first line of input is the number of test cases $T$ $(1 \leq T \leq 10)$

For each test case, there will be a string consists of lowercase English letters.

The length of the string does not exists $10000$.

## Output

For each test case, print the maximum number of adjacent beautiful pairs you can find.

## Sample Input

``` log
2
strength
consonants
```

## Sample Output

``` log
5 2
```

## 思考

1. 把字符串拆分成两个两个的pair
2. 填充到`map[19][19]`里面, 随后这个问题转换成一个19个字符, 挑选x个变成大写, 问应该选择哪些的问题.
3. 到这一步, 需要遍历`000...000` -> `111...111`,0是不转换大写, 1是转换大写.
4. 再在每种情况下计算一下,有多少个pair会成立.
5. 最后取max.

# Necklace!

## Description

DP wants to drink coco with his girlfriend!

Unfortunately, he has no girlfriend now, but he still wishes to prepare a necklace for his future goddess.

This is a kind of special necklace which contains a sequence of diamonds with letters in, and it can be regarded as a string.

DP gives Amayama a semi-finished necklace and askes Amayama to add as few as possible diamonds to the head or tail of the necklace to make the necklace consist of at least two same circulation sections.

A circulation section of a string is a substring which could build the original string by repeating the circulation section several times.

Please answer the number of diamonds that Amayama should add.

## Input

The first line contains an integer $n$, which is the number of test cases. $(1 \leq n \leq 1000)$

For each case, there is only one line of string containing lowercase letters, which indicates the semi-finished necklace.

The length of each string is no more than 200000.

## Output

The output for each test case is an integer which is the number of diamonds that Amayama should add.

### Sample Input

``` log
2
wawawa
failed
```

### Sample Output

``` log
0
6
```

## HINT

If a necklace has contained two circulation sections and ended with an uncomplete circulation section, you should complete this section to finish the whole necklace.

### 算法分析

本题要求对每个字符串, 通过最少补字符使其成为至少两个循环节的串

核心思路是利用 KMP 前缀函数求最小循环节长度 $p$

若原串已由 $k \geq 2$ 个循环节组成, 则无需补字符; 否则需补 $(2p-n)$ 个字符

实现时, 先计算前缀函数, 得到 $p=n-\text{next}[n-1]$, 判断 $n$ 是否为 $p$ 的倍数且 $n/p \geq 2$, 若否则补足到 $2p$

整体复杂度 $O(n)$, 适合大数据量多组输入

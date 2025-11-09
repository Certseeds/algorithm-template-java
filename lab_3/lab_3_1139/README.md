---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

## Description

HuaXin bookstore is not a famous bookstore.

However, it still attracts many people to go there every day.

Now, there are several kinds of books in HuaXin bookstore.

Each kind of books has an amount of $a_i$ $(1 \leq a_i \leq 20)$.

People comes to HuaXin bookstore to find the book which he/she wants to read.

But after statistics, the boss found that some customers may leave without having a look of the book if there is no book available.

In addition, the customers who enters with no book to read will only hang in the bookstore without looking any book before he leave.

Your task is to write a program to tell the boss how many customers left without having a look of the books.

### Input

The input consists of data for one or more kinds of books, followed by a line containing the number 0 that signals the end of the input.

Data for each kind of book is a single line containing a positive integer, representing the amount of this kind of book, followed by a space, followed by a sequence of uppercase letters.

Letters in the sequence occur in pairs.

The first occurrence indicates the arrival of a customer, the second indicates the departure of that same customer.

No letter will occur in more than one pair.

### Output

For each kind of book, output the number of customers walked away.

### Sample Input

``` log
2 CBBCJJKZKZ
3 GAKKBDDBAGNN
3 NACCQNDDQAEE
0
```

### Sample Output

``` log
0
1
0
```

### 算法分析

本题模拟顾客进出书店的过程。用数组记录每个顾客状态, 遇到书可借则借出, 否则标记为离开。每次顾客离开时统计未借到书的顾客数。

整体为线性扫描, 时间复杂度 $O(L)$, 空间复杂度 $O(1)$, 其中 $L$ 为顾客序列长度。

似乎和排序完全没有关系

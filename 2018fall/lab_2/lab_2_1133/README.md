---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_2_1133

## Description

As you know, every university has a hotel but it is always hard to book a room.

Every year, thousands of orders fly to the hotels.

The boss of a hotel in S University is busy, and hopes to make a program to deal with the orders.

As he knows you are good at programming, he turns to you for help.

The problem is described as follows:

We need to deal with the orders in the following $n$ days.

During the $n$ days, there will be $r_i$ rooms available.

And there are orders, every order is described by 3 integers, which are $d_j, s_j, t_j$ respectively.

This indicates the customer need to book $d_j$ rooms from day $s_j$ to $t_j$ (inclusive).

To simplify, we need not to care whether the customer get the same room everyday.

The principle for booking rooms is First come, First get, i.e., we need to assign rooms for the customers by the chronological sequence of the orders.

If any one order can't be satisfied, we need to stop assignment and inform the customer to modify the order.

Here, the dissatisfaction refers to that at least one day during day $s_j$ to $t_j$ the number of remaining rooms is less than $d_j$.

Now we need to know, whether there are orders can't be satisfied.

And if so, we should inform which customer to modify the order.

### Input

The input contains several test cases.

For each test case:

The first line will be 2 integers $n, m$, which are the number of days and orders.

The second line contains $n$ integers, the $i$th number is $r_i$, indicates the number of rooms can be booked that day.

Then there are $m$ lines, every line has 3 integers $d_j, s_j, t_j$, indicates the number of rooms to be booked, the begin day and end day of the $j$th order.

$(1 \leq n, m \leq 10^6, 0 \leq r_i, d_j \leq 10^9, 1 \leq s_j \leq t_j \leq n)$

### Output

If all orders can be satisfied, the only output is one line, contains an integer 0.

Otherwise output two lines, the first line output -1, and the second line output the index of the order that should be modified.

### Sample Input

``` log
4 3
2 5 4 3
2 1 3
3 2 4
4 2 4
```

### Sample Output

``` log
-1
2
```

## 分析

0. 注意输入, 用了`hasNext()`之后, 直接调试会卡住, 需要从文件读入才行, 建议使用MainTest来调试.

1. 此处注意, 输出的第二种情况, 要求输出的是第一个不可行的index, 不是全部后续index

## 解法1

读完题目, 每一个订单对应的是对一个区间做修改, 所以我们可以用线段树来维护这个区间的修改.

我们需要的操作有

1. 更新某个位置上的值, 用于初始化
2. 更新一个区间的所有值, 用于每一个操作
3. 获取一个范围的最小值,  用于判断到最后是否可行.

把从$s_j$到$t_j$天订$d_j$个房间, 翻译成: 给$s_j$到$t_j$的区间内, 每个值都减少$d_j$.

之后每次检查区间最小值是否小于0,小于0则不可行.

## 解法2

二分+前缀和, 二分的是最后一个订单的index, 然后用前缀和来判断是否可行.

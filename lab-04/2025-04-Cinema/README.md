## Description

SUSTech Cinema is open for business!

There are two ticket offices and all the students form two queues.

It takes each student one minute to buy his ticket(s).

The students can be separated into $n$ groups each with one or two people.

To save time, two students in the same group will not queue up in the same line.

For the two-students group, if one group member has bought the tickets, the other one will leave the queue immediately.

Also, if two students get to the ticket windows at the same time, then they will buy their own ticket respectively.

Now LowbieH will give the description of the queueing situation, can you tell him the waiting time for each group?

## Input

The first line contains three integer $n,\ p,\ q$ $(1 \leq p,\ q \leq n \leq 100000)$, which are the number of groups and the lengths of the two queues.

The following two lines consist of serial numbers (range from $1$ to $n$) in each queue separately.

Two same serial numbers stand for a two-people group.

## Output

Output the waiting time of each group in order.

## Sample Input

``` log
5 4 5
1 2 3 4
2 4 1 3 5
```

## Sample Output

``` log
1 1 2 2 3
```

### 算法分析

本题模拟两个窗口分别服务两队学生队列的过程.

每分钟各服务队首一人, 每组学生只要有一人买票, 另一人立即离队.

用 finished 数组标记每组是否已完成, 模拟每分钟窗口服务, 记录每组完成时间, 直到所有组都完成.

该算法时间复杂度为 $O(n)$, 空间复杂度为 $O(n)$.

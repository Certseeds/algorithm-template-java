## Description

The war in Ursus has broken out!

Now, Wamiya and her teammates have to escape from this dangerous place immediately.

In order to avoid the falling objects from the surrounding buildings, all the team members are lined up in a row while moving forward.

In order to defend against enemy attacks, Wamiya should also constantly change the form of the team by exchanging two parts of the row, i.e., she will specify two continuous parts in the row, and exchange their locations without changing anything inside each part.

There will be $N$ members in the team, and Wamiya will give $M$ orders one by one.

Given the initial team, what will be the final team after their escape?

### Input

First line contains one integer $T$, indicating that there will be $T$ cases.

For each case, the first line will be two integers $N$ and $M$.

$N$ is the number of the members in the team, and $M$ is the number of orders.

The second line will be $N$ integers, indicating the initial formation of the team.

Each number is unique and represents the ID of the member.

We guarantee that ID is in the range of $[0, N - 1]$.

The following $M$ lines represent $M$ orders.

In each line, there are four integers $x_1, y_1, x_2, y_2$, which mean the part $[x_1, y_1]$ (starting from member with ID $x_1$ and ending at the member with ID $y_1$), should exchange the position with part $[x_2, y_2]$.

$T \leq 5, 1 \leq N, M \leq 10^{5}$.

We guarantee that in each order, members with ID $x_1, y_1, x_2, y_2$ will be arranged in order from front to back in the row.

Notice that $x_1$ could be equal to $y_{1}$ and $x_{2}$ could be equal to $y_{2}$.

### Output

For each case, output $N$ integer in one line to represent the final team consisting of member IDs.

### Sample Input

```log
1
10 2
0 6 5 1 2 3 4 8 7 9
6 4 7 9
0 8 6 5
```

### Sample Output

```log
6 5 0 7 9 8 1 2 3 4
```

### 算法分析

本题用双向链表真实模拟队伍，每个成员节点用 map 快速定位。每次交换通过断链重连实现区间交换，时间复杂度 $O(N+M)$，空间复杂度 $O(N)$。断言保证输入唯一性和区间合法。

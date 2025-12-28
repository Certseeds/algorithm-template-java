# exchange

## Description

Give you n linklists, each with m numbers inside.

The chains index are numbered 0 to (n-1), and the nodes on the initial i-th chain are numbered (i * m), (i * m+1) ...... (i*m+(m-1)).

After that you are given k operations, each operation will select two node numbers a, b.

Disconnect the right side of a and the left side of b on the current linklist into four parts, then join a and b together and join the remaining two disconnected parts together.

For example, n=3, m=5.

If you choose a,b=1,7 then the result after reconnection is [5,6,2,3,4] and [0,1,7,8,9].

After these operations, ask for all the nodes of the linklist where node x is located and output them from left to right.

### Input

First line 4 numbers: n, m, k, x (n*m<1e6, 0<k<2e5).

Then follow k lines, each line two numbers: a, b (0<=a,b<n*m) meaning the nodes selected.

It is guaranteed that the two nodes selected each time are not currently in the same chain.

### Output

A line containing a lot of numbers of all the index of nodes in the linklist where node x is located and output them from left to right.

### Sample Input

```log
3 5 1 0
1 7
```

### Sample Output

```log
0 1 7 8 9
```

### Solution

将所有链表表示为双向指针数组: left[i] 表示 i 左侧的节点, right[i] 表示 i 右侧的节点, 头尾用 -1 表示.

每次操作只需要常数次指针修改来断开并重连四段: 断开 a 与右侧, 断开 b 与左侧, 然后把 a 连接到 b, 把 a_right 与 b_left 连接起来(若存在).

操作总数为 k, 每次 O(1), 最后从 x 沿 left 指针找到链头, 再沿 right 收集输出即可.

总体时间复杂度 O(n*m + k + L), 其中 L 是最终链表长度, 空间 O(n*m)。

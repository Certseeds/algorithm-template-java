# Nth Element in Sliding Window

## Description

Given a sequence $a_1, a_2, \cdots, a_m$, there is a sliding window of size $k$ from the very left of the sequence to the very right.

You can only see the $k$ numbers in the window.

Each time the sliding window moves right by one position.

For each time there is an integer $n_i$, you are asked the $n_i$-th element in the window.

The k-th element indicates that the element will be in the k-th position after sorting in ascending order.

### Input

The first line contains two integers $m$ and $k$, $(1 \le m \le 10^5, 1 \le k \le m)$.

The second line contains m distinct integers separated by space $a_i$ $(-2147483648 \le a_i \le 2147483647)$.

The next line contains $m-k+1$ integers separated by space $n_i$ $(1 \le n_i \le k)$.

### Output

Output $m-k+1$ lines, each line contain a number represented the answer to each window.

### Sample Input

``` log
6 3
201 91 29 13 11 -5
3 1 2 1
```

### Sample Output

``` log
201
13
13
-5
```

### HINT

Balanced binary search tree

### 思路

使用带 size 域的 Treap 维护滑动窗口内的元素, 支持动态插入删除和查询第 k 小.

初始化时将前 k 个元素插入 Treap, 回答第一个查询.

窗口每次右移一位: 删除窗口最左侧的旧元素, 插入右侧新元素, 然后查询当前窗口的第 $n_i$ 小.

查询第 k 小: 利用子树 size 判断目标在左子树还是右子树, 递归定位.

每次滑动涉及一次删除一次插入一次查询, 均为 `O(log k)`, 总复杂度 `O(m log k)`.

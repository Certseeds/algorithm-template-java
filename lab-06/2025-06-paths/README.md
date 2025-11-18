# Paths

## Description

Given a rooted tree numbered from $1$ to $n$, each edge has a weight $w$.

The root node of the tree is node $1$.

You are asked to calculate the number of paths that start from the root, terminate in a leaf node, and satisfy the sum of edge weights in the path equals to $num$.

### Input

The first line contains two integers $n$ and $num$ $(1 \leq n \leq 500\ 000, 1 \le num \le 2\ 000\ 000\ 000)$, indicating the number of tree nodes and the target number.

Then $n-1$ lines follow.

Each line contains three integers $u,v,w$ $(1 \le u,v \le n, 1 \le w \le 100)$ describing an edge.

The first two integers are the indices of nodes that form an edge and the last integer indicates the weight of the edge.

### Output

Output an integer which means how many paths satisfying the sum of edge weights in the path equals to $num$.

### Sample Input

``` log
6 6
1 2 2
1 3 3
3 4 4
3 5 3
2 6 4
```

### Sample Output

``` log
2
```

### Solution

使用链式前向星维护整棵树, 读入时连边并校验编号及边权约束.

初始化显式栈存放三元组 (node, parent, sum), 从根节点 1 入栈并逐步弹出处理.

遍历某节点时沿 head 链按 next 指针枚举所有边, 跳过父节点指向的反向边, 其余子节点压栈并累计路径和.

若节点没有未访问子节点则其为叶子, 同时判定累积权值是否等于 num, 满足则答案加一.

每条根到叶路径最多访问一次, 整体时间复杂度 O(n), 需要 O(n) 额外空间用于链式前向星和 DFS 栈.

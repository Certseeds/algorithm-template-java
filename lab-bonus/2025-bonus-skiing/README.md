# skiing

## Description

Yuki is an outgoing girl and she enjoys all the sports, especially snow sports like skiing.

Now she is skiing in a world-famous ski resort - Super Ultra Ski Training Center (SUSTC).

The map of SUSTC can be considered as a grid map with $n$ rows and $m$ columns.

Since the ski facility is uneven, each grid $(i,j)$ has its own height $h_{i,j}$.

Yuki starts her skiing at the grid $(1,1)$ - the top left grid, and her destination is at the grid $(n,m)$ - the bottom right grid.

Each time she can only ski to one of the adjacent grids - the left, the right, the above, or the below grid.

Obviously, the minimum distance for Yuki to ski to the destination is $n+m-2$ if she chooses an arbitrary Manhattan path.

However, with the elevation change during her skiing, Yuki's speed will also change remarkably.

Specifically, suppose that the velocity of Yuki at grid $(x,y)$ is $v$, then it takes $\frac{1}{v}$ time units for Yuki to move to $(x,y+1)$, and the velocity at grid $(x,y+1)$ will be changed to $v \cdot 2^{h_{x,y}-h_{x,y+1}}$.

Suppose that the initial velocity when Yuki at grid $(1,1)$ is $v_0=1$, could you please tell Yuki the minimum time for her to arrive at the destination.

### Input

The first line contains two integers: $n$ and $m$ $(1 \leq n,m \leq 300)$ - size of the grid map.

Each of the next $n$ lines contains $m$ integers.

The $j$-th number in the $i$-th line denotes $h_{i,j}$ $(1 \leq h_{i,j} \leq 15)$.

### Output

Print one line with the value - the minimum time for Yuki to arrive at the destination.

The value should be accurated to exactly 2 decimal places.

### Sample Input

```log
2 3
1 2 3
4 5 6
```

### Sample Output

```log
7.00
```

### Solution

速度更新满足乘法连锁, 沿路径从起点到任意格子 (x,y) 的速度因子会望远镜相消.

初始 v0 = 1, 每次移动 v <- v * 2^(h_cur - h_next), 因此到达格子 (x,y) 时有.

v(x,y) = 2^(h(1,1) - h(x,y)).

于是从格子 (x,y) 走一步所用时间为 1 / v(x,y) = 2^(h(x,y) - h(1,1)), 只依赖当前格子的高度, 与下一格无关.

问题转化为在 n*m 个点的网格图上求最短路, 每条边权为从出发点格子的固定代价.

边权均为正, 直接用 Dijkstra.

dist[0][0] = 0, 每次弹出当前最小 dist 的格子, 松弛 4 个方向邻居, 代价加上 pow(2, h[x][y] - h0).

最终输出 dist[n-1][m-1], 按题目要求格式化为 2 位小数.

复杂度 O(n*m log(n*m)), 空间 O(n*m).


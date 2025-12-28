# game

## Description

Yuki is an ambitious girl and she is addicted to games.

In one game called The Queen's Gambit, Yuki is controlling the Queen to move in a grid of $n$ rows and $m$ columns, where rows are numbered from $1$ to $n$ and columns are numbered from $1$ to $m$.

The cell at the $i$-th row and the $j$-th column is denoted by $(i,j)$.

Each cell in the grid contains a point coefficient, denoted by $C_{ij}$.

At first, Yuki can place the Queen on the grid arbitrarily, that is any cells in the grid can be the initial position for the Queen.

Every turn Yuki can move the Queen between the cells sharing a common edge.

For example, when the Queen is at $(i,j)$, it can be chosen to move to $(i-1,j)$, $(i+1,j)$, $(i,j-1)$ or $(i,j+1)$, if the destination is not out of the boundary.

Now every time when the Queen is moved from one cell to an unvisited cell, Yuki will gain the points which are equal to the product of two point coefficients.

It means that Yuki will get $C_{xy}\cdot C_{ij}$ points when the Queen moves from $(i,j)$ to $(x,y)$ and visits $(x,y)$ at the first time.

Yuki can stop the game at any time, and she wonders the maximum points she can gain.

### Input

The first line contains two integers: $n$, $m$ $(1\leq n,m\leq 50\ 000,1\leq n\cdot m\leq50\ 000)$ - rows and columns of the grid.

Each of the next $n$ lines contains $m$ integers.

The $j$-th number in the $i$-th line denotes $C_{ij}$ $(0\leq C_{ij}\leq 5\ 000)$.

### Output

Print one line with the result - the maximum points.

### Sample Input

``` log
1 4
1 2 3 3
```

### Sample Output

``` log
17
```

### 算法分析

本题是网格图上的最大生成树问题.

目标是最大化访问边获得的点数之和, 其中从 (i,j) 到 (x,y) 获得 $C_{ij} \times C_{xy}$ 点.

1. 问题转化:
   - 将网格视为图, 每个格子是节点, 相邻格子之间有边.
   - 边权 = 两端点系数的乘积.
   - 由于每条边只在首次访问时计分, 这等价于求最大生成树.

2. 算法: Prim 最大生成树.
   - 从系数最大的格子出发 (贪心选择起点).
   - 使用大根堆维护当前可扩展的边.
   - 每次选取权重最大的边扩展生成树.

3. 复杂度分析:
   - 时间复杂度: $O(nm \log(nm))$, Prim 算法使用优先队列.
   - 空间复杂度: $O(nm)$, 存储网格和优先队列.

4. 实现细节:
   - 使用一维数组存储二维网格, 索引转换: idx = row * m + col.
   - 方向数组处理四邻域: 上下左右.

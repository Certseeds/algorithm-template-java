## Description

Neko is running away! Eve wants to catch him.

At first, Eve is standing at the point with coordinates $(x_1, y_1)$, while Neko is standing at the point with coordinates $(x_2, y_2)$.

For every minute, Eve can choose to go up, down, left, or right with $1$ unit distance.

For instance, if she is at $(x, y)$ now, she can go to $(x, y+1)$, $(x, y-1)$, $(x-1, y)$ or $(x+1, y)$.

Eve noticed that Neko also moves $1$ unit distance every minute, but he only moves according to a sequence periodically.

The sequence only contains $\texttt{U}$, $\texttt{D}$, $\texttt{L}$, $\texttt{R}$, denoting that Neko moves up, down, left, right respectively.

Eve is now wondering how many minutes she needs at least to catch Neko.

### Input

The first line contains two integers $x_1, y_1$ ($0 \leq x_1, y_1 \leq 10^9$): the initial coordinate of Eve.

The second line contains two integers $x_2, y_2$ ($0 \leq x_2, y_2 \leq 10^9$): the initial coordinate of Neko.

It is guaranteed that the initial coordinates and destination point coordinates are different.

The third line contains a single integer $N$ ($1 \leq N \leq 10^5$): the length of the sequence $S$.

The fourth line contains the string $S$ itself, consisting only of four letters $\texttt{U}$, $\texttt{D}$, $\texttt{L}$, $\texttt{R}$.

### Output

Please output the minimum minutes Eve needs to catch Neko. If she can not catch Neko forever, please output $-1$.

### Sample Input

``` log
0 0
4 6
3
DDD
```

### Sample Output

``` log
5
```

## HINT

Eve can choose not to move at every minute.

### 解释算法

本题要求求 Eve 至少多少分钟能追上 Neko。

实现思路如下：

1. 快读读入初始坐标、周期长度 n 和 Neko 的移动序列 s。
2. 预处理 s 的前缀和，得到每一轮周期 Neko 的位移。
3. 二分答案 t，判断 t 分钟后 Eve 能否追上 Neko。
4. t 分钟时 Neko 的位置 = 初始 + 完整周期数 * 总位移 + 剩余步的前缀和。
5. 只要曼哈顿距离 <= t，Eve 就能追上。
6. 若二分到极大值仍不可达，输出 -1。

时间复杂度 O(n log(maxT))，空间 O(n)。

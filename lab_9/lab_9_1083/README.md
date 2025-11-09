## Description

SUSTech becomes bigger and bigger. The president wants to make the campus more convenient.

He decided to set n stations in the campus.

The stations are built according 

1. the DSAA first law: for i-th station, it is served as the original station for i-th bus line.

For each station, it is served as the destination station for only one bus line.

For each bus line, the bus in it goes to the destination station directly.

Please note that the original and destination station could be the same for some bus lines.

The convenience of the station building plan is measured by

2. the DSAA second law: we use (x, y) to denote we can go from station x and arrive at station y, please note that

+ (1) one passenger could take different bus lines to arrive y from x
+ (2) (x, y) and (y, x) are different
+ (3) (x, x) is allowed.

The convenience of the total plan is defined as the total number of all possible (x, y) pairs among the n stations, the larger the better.

In order to improve the convenience, now we can change the destination of some bus lines according

3. the DSAA third law: we can change at most two destinations of distinct bus lines.

Please find the maximum convenience value we can get by following the DSAA 1st, 2nd and 3rd laws. All stations are labeled from 1 to n.

### Input

The first line will be an integer T (1 <= T <= 50). T is the number of test cases.

For each test case, the first line will be an integer n. (1 <= n <= 100000), n is the number of stations.

Then there are n integers. Each integer is the destination of the i-th bus line.

### Output

For each test case, print the maximum convenience value.

### Sample Input

```log
1
3
2 3 1
```

### Sample Output

```log
9
```

### HINT

We do not need to change any stations.

(1,1),(1,2),(1,3),(2,1),(2,2),(2,3),(3,1),(3,2),(3,3) are all legal. So the
answer is 9.

## 思路解析

### 问题建模

+ 输入给出一个长为 n 的数组 `to[1..n]`, 满足“每个站点作为目的地恰好被一条线路指向”, 因此 `to` 是一个置换（permutation）. 图为功能图但在本题是若干个互不相交的环覆盖所有点.

### 主要思路

+ 原始可达对数（有序对 `(x,y)`）对于同一环内的顶点对数等于环中每个环节贡献的平方之和: 若一个环大小为 `c`, 则该环能形成 `c * c` 个有序对（任意起点任意终点均可沿环到达）. 所以初始答案为所有环大小平方之和.

+ 我们最多允许修改两个不同线路的目的地. 通过恰当修改两条边, 可以把两个环连接成一个更大的环, 从而把原本的 `a^2 + b^2` 变为 `(a + b)^2 = a^2 + b^2 + 2ab`, 即额外收益为 `2ab`.

+ 显然要最大化增益, 应当选择大小最大的两个环合并（若存在第二大环）. 由于只允许两次修改, 最多能把两个环合并一次, 代码中以 `max1`、`max2` 记录最大的两个环长度, 若 `max2 > 0` 则额外加上 `2 * max1 * max2`.

### 关键实现点

+ 遍历所有节点, 按照 `to` 跳转直到回到已访问节点, 即可得到一个环的全部节点, 统计环长 `cnt` 并累加 `cnt * cnt`.
+ 同时维护当前遇到的最长和第二长环长度 `max1`、`max2`, 最终根据是否存在第二大环决定是否加上合并收益.

### 复杂度

+ 时间复杂度: O(n), 每个节点恰被访问一次.
+ 空间复杂度: O(n), 主要用于标记访问数组和存储输入数组.

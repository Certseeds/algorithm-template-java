# Giants

## Description

There are $n$ cities numbered from $1$ to $n$, and $n-1$ roads connecting these $n$ cities, i.e., it is a tree with $n$ nodes.

Each road takes one day for giants to travel through.

City $1$ is a huge city and can host all giants.

However, other cities are small.

Each small city can only host one giant at the same time.

Initially, some giants are in small cities.

They set out at the same time and want to gather in city $1$.

In each day, every giant can choose to go to another city or stay at the current city.

Please find the minimum needed days for all giants to gather in city $1$.

### Input

The first line contains an integer $n(2 \leq n \leq 400,000)$ which means the number of cities.

Then $n-1$ lines follow. Each line contains two integers $u,v(1\leq u, v\leq n)$ which means a road between city $u$ and city $v$.

Then one line contains an integer $m(1\leq m\leq n-1)$ which means the number of giants.

Then one line contains $m$ integers which describe the indices of the cities where these giants are in initially.

Different giant is in a different small city initially.

### Output

Print the minimum number of days needed for all giants to gather in city $1$.

### Sample Input

``` log
5
1 2
1 3
3 4
3 5
4
2 3 4 5
```

### Sample Output

``` log
3
```

### HINT

Explanation for the example:

In day 1, two giants in city 2 and 3 can go to city 1.

However, two giants in city 4 and 5 cannot both go to city 3 since city 3 is small.

One of the two has to stay at the current city in day 1, and arrives in city 1 at the end of day 3.

Therefore, the answer is 3.

### 算法分析

这个任务的目标是计算所有巨人聚集到城市1的最小天数, 考虑到小城市的容量限制.

核心算法步骤如下:
1. 图的表示: 首先, 程序读取输入的 `n-1` 条边, 并使用链式前向星(通过 `ForwardStar` 类实现)来构建树的图结构. 这是一个高效的存储方式, 便于后续遍历.
2. 数据准备: 读取巨人数量 `m` 和他们的初始城市, 使用布尔数组 `hasGiant` 标记这些城市.
3. 子树处理: 从根节点 `1` 开始, 对每个邻居节点进行一次BFS遍历, 处理其子树. 这样可以分别计算每个子树中巨人到达根的时间.
4. BFS遍历和距离计算: 在每个子树中, 使用BFS计算每个节点到根的距离, 并收集有巨人的节点的距离到列表 `ds`.
5. 容量冲突调整: 对 `ds` 排序后, 模拟排队过程. 如果下一个巨人的到达时间不晚于前一个的离开时间, 则将其调整为前一个离开时间加1, 以确保不冲突.
6. 结果计算: 对于每个子树, 返回调整后的最大距离. 然后取所有子树的最大值作为答案.

总的来说, 该算法通过对每个子树进行BFS和冲突调整来计算最小聚集时间, 时间复杂度为O(n log n), 由于排序, 空间复杂度为O(n).

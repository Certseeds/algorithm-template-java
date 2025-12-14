# Defensive Towers

## Description

On the mainland, there is a fire-breathing dragon called "lanran", who is always burning cities and attacking people.

So Pisces decides to build some defensive towers in the kingdom to protect his people.

A defensive tower is able to protect the city where it is located and the cities adjacent to it.

However, building a tower costs a lot, so Pisces could only build at most $\lfloor n/2\rfloor$ defensive towers ($n$ is the total number of cities in the kingdom).

Please find a way to build defensive towers in order to protect the whole kingdom.

If there are multiple answers, print any.

By saying that "two cities are adjacent", it means that there is one undirected road connecting them.

### Input

The first line contains a single integer $t$ $(1\le t\le 2*10^5)$, which represents the number of test cases.

Then $t$ test cases follow.

In each test case, the first line contains $2$ integers $n$ $(2\le n\le 2*10^5)$ and $m$ $(n-1\le m\le min(2*10^5, \frac{n*(n-1)}{2}))$, indicating the number of cities and the number of roads.

And in each of the next $m$ lines, there are $2$ integers $u$ and $v$ $(1\le u,v\le n)$ representing the indexes of the cities that this road connects.

There are no self-loops or multiple edges in the given graph.

It is guaranteed that the given graph is connected and $\sum m\le 2*10^5$.

### Output

For each test case print two lines.

In the first line print $k$ — the number of chosen cities.

In the second line print $k$ distinct integers $c_1,c_2,…,c_k$ in any order, where $c_i$ is the index of the i-th chosen city.

It is guaranteed that the answer exists.

If there are multiple answers, you can print any.

### Sample Input

``` log
2
4 6
1 2
1 3
1 4
2 3
2 4
3 4
6 8
2 5
5 4
4 3
4 1
1 3
2 3
2 6
5 6
```

### Sample Output

``` log
2
1 3
3
4 3 6
```

## 算法分析

### 问题理解

给定一个连通无向图, 需要选择最多 floor(n/2) 个节点放置防御塔.

防御塔可以保护其所在城市以及相邻城市, 要求所有城市都被保护.

### 算法思路

这是一个支配集 (Dominating Set) 问题, 利用 BFS 分层的性质可以高效求解:

1. BFS 分层: 从节点 1 开始进行 BFS, 为每个节点标记深度 (层数)
2. 奇偶分组: 将所有节点按深度的奇偶性分为两组:
   - evenNodes: 深度为偶数的节点
   - oddNodes: 深度为奇数的节点
3. 选择较小组: 选择节点数较少的那一组作为答案

### 正确性证明

- 由于图是连通的, BFS 生成的是一棵生成树

- 在 BFS 树中, 每个节点要么在偶数层, 要么在奇数层

- 每个节点与其父节点的层数奇偶性相反

- 因此选择任一组后, 另一组中的每个节点都与选中组的某个节点相邻 (父节点或子节点)

- 由于两组节点数之和为 n, 较小组的节点数 <= floor(n/2)

### 复杂度分析

- 时间复杂度: O(n + m), BFS 遍历
- 空间复杂度: O(n + m), 存储图和深度数组

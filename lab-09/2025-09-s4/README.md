# s6 problem

## Description

Given a connected undirected graph of $n$ vertices with $m$ edges (may with negative weights)(edge $i$ connects vertices $u_i, v_i$ and with weight $w_i$), you can perform edge deletion, where the gain from deleting an edge is the edge weight of that edge, but you need to ensure that the graph remains connected after performing the edge deletion operation.

Your goal is to maximize the sum of the gains from all operations.

### Input

$1\leq n \leq 2\times 10^5$

$n-1\leq m \leq 2\times 10^5$

$1\leq u_i, v_i \leq n$

$-10^9\leq w_i\leq 10^9$

### Output

Print one number as your answer

### Sample Input

``` log
3 3
1 2 1
2 3 0
3 1 -1


### Sample Output

``` log
1
```

### 算法分析

题目要求保持连通性并最大化删边收益. 等价于保留边权之和最小的生成树, 答案为总权值减去保留权值.

负权边删除会降低收益, 因此应全部保留. 使用并查集先合并所有负权边连接的顶点形成若干连通块.

对于非负权边, 按权值从小到大排序后执行Kruskal算法, 仅在连接不同连通块时加入, 确保总权值增量最小.

最终答案为总权值减去保留边权值, 即 totalWeight - keptWeight.

时间复杂度: 排序 O(m log m), 并查集操作接近 O(m), 总复杂度 O(m log m).

空间复杂度: O(n + m) 存储并查集与边列表.

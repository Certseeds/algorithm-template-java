## Description

There are n buildings in school.

The president wants you to connect all these buildings by making some bridges among them.

The construction should follow these requirements:  (i) all buildings are connected, (ii)  total cost is minimized.

There are total m + K bridges you can choose.

If you cannot make all buildings  connected by these m+K bridges, print -1, otherwise, print the minimized total cost.

Note: The total cost is the cost of the remaining bridges after you insert.

### Input

The first line will be an integer T. (1 <= T <= 50) T is the number of test case.

For each test case, the first line will be three integers n, m and K.(n <= 5000, m, K <= 10000) Then there will be m + K lines.

Each line will be three integers x y w, meaning we can build a bridge between island x and island y with w cost. (1 <= x, y <= n, w <= 104)

The islands are labeled from 1 to n.

### Output

For each test cast, print the minimum cost or -1.

### Sample Input

``` log
1
5 5 2
1 2 1
1 3 6
3 4 1
1 4 5
2 4 4
2 5 3
4 5 2
```

### Sample Output

``` log
7
```

### HINT

For the first sample, we remove the bridges

between 1 3, 2 4, 1 4; add bridges 2 5, 4 5.

The total cost is 1 + 3 + 2 + 1 = 7.

7 is the minimum cost we need.

### 思路

问题复述: 给定一个连通的无向加权图, 有 m+K 条可选边. 需要选择若干边使得所有顶点连通并且总成本最小. 若無法连通则输出 -1.

核心算法: Kruskal 最小生成树.

步骤:
1. 将所有 m+K 条边按权重从小到大排序.
2. 使用并查集(Union-Find)遍历排序后的边, 若当前边连接的两个端点不属于同一连通分量, 则选用该边并把两个分量合并. 同时累加边权到总成本.
3. 当已选边数达到 n-1 时停止, 若遍历完成后选边数小于 n-1, 说明无法连通, 返回 -1.

正确性说明: Kruskal 算法在无向连通图上能得到总权最小的生成树. 由于題目要求在能连通的前提下最小化總成本, 直接求 MST 即可满足要求. 若圖不连通則不存在生成树, 返回 -1 符合題意.

复杂度分析: 排序耗时 O(M log M), 并查集操作近似 O(M alpha(N)), 其中 M = m+K, N = n, alpha 為逆阿克曼函数. 在题目约束下该复杂度可接受.

边界与注意事项:
- 若 n == 1, 没有边, 应输出 0.
- 输入可能包含平行边或自环(若存在自环, 在实现中不会被选中). Kruskal 对平行边兼容, 会选择权最小的那一条(依据排序和合并情况).
- 权重范围在题目中给出, 使用 int 存储安全.

实现映射到代码:
- 参见 `src/Main.java`, 实现包括: `reader()` 解析 T, 每个用例的 n, m, K, 以及 m+K 条边; `cal()` 对每个用例按 Kruskal 计算最小生成树总权并返回 -1 或成本; `output()` 逐行输出结果并带换行.

测试建议: 使用仓库中 `resources/01.data.in` 和 `resources/01.data.out` 作为样例进行验证, 并添加更多边界样例如 n=1, 全部边相同权重, 含大量平行边等.

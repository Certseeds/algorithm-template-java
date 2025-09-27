## Description

Write a program to print all the leaves of the given tree, numbered from 1 to N.  The root of the tree is node 1.

### Input

The first line will be an integer T (1 <= T <= 10), which is the number of test cases.

For each test data:

The first line contains an integer N (2 <= N <= 10^4) — the number of the nodes.

Each of the next N - 1 lines contain two integers a and b, which means there is an edge between node a and b (1 <= a, b <= N).

### Output

For each case please, print all the leaves of the given tree, in ascending order.

For the tree has multiple leaf nodes, there is a blank between two leaf nodes, and '\n' at the end of each line.

### Sample Input

```log
1
4
1 2
2 3
3 4
```

### Sample Output

``` log
4
```


## 解答

本题要求我们找出给定树中所有的叶子节点. 题目明确指出节点 1 是树的根.

### 算法思想

根据图论的定义, 在一棵树中, 度为 1 的节点被称为叶子节点.

题目还附加了一个条件: 节点 1 是根节点. 在树的语境下, "叶子节点" 通常指那些没有子节点的非根节点.

因此, 我们可以将本题的"叶子节点"定义为: 一个度为 1, 且编号不为 1 的节点.

基于这个定义, 我们可以设计一个简单而高效的算法:

1.  数据结构: 我们首先需要一种方式来表示树的结构. 邻接表 (`List<List<Integer>>`) 是一个非常适合的选择. 我们可以创建一个大小为 `N+1` 的邻接表 `adj`, 其中 `adj.get(i)` 存储了所有与节点 `i` 直接相连的节点.

2.  构建树: 读取输入的 `N-1` 条边. 对于每一条边 `(u, v)`, 我们同时在 `u` 的邻接列表里添加 `v`, 并在 `v` 的邻接列表里添加 `u`. 完成后, `adj.get(i).size()` 就代表了节点 `i` 的度.

3.  寻找叶子节点:
+  我们遍历所有可能的非根节点, 即从节点 2 到节点 N.
+  对于每一个节点 `i`, 我们检查它在邻接表中的度, 即 `adj.get(i).size()`.
+  如果度为 1, 那么节点 `i` 就符合我们对叶子节点的定义, 将它添加到一个结果列表中.

4.  处理特殊情况:
+  当 `N=2` 时, 树的结构只有一条边 `1-2`. 此时, 节点 1 是根, 它的度是 1. 节点 2 的度也是 1. 根据我们的定义 (非根节点且度为 1), 节点 2 是唯一的叶子节点.
+  我的代码中对 `N=2` 的情况进行了单独处理, 但实际上, 通用逻辑 (遍历 2 到 N) 已经可以完美覆盖这种情况.

5.  输出: 由于我们是从 2 到 N 按升序遍历的, 找到的叶子节点自然也是有序的. 最后, 将结果列表按题目要求的格式输出即可.

这个算法的时间复杂度主要由建图和遍历节点两部分构成, 均为线性时间, 因此总复杂度为 O(N), 足以高效地解决本题.

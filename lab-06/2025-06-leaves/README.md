# Leaves

## Description

Given a tree whose nodes are numbered from 1 to $n$.

You are asked to print all the leaf nodes of the tree in ascending order.

The root of the tree is node 1.

### Input

The first line contains one integer $n$ $(1\le n \le 1\ 000\ 000)$, indicating the number of nodes in the tree.

Then $n-1$ lines follow.

Each line contains two integers $u,v$ $(1 \le u, v \le n)$ describing an edge, indicating the indices of nodes which form an edge.

### Output

One line contains integers in ascending order.

Multiple integers in one line must be separated by one space.

### Sample Input

``` log
4
1 2
1 3
3 4
```

### Sample Output

``` log
2 4
```

### 算法分析

这个任务的目标是找到给定树中的所有叶子节点, 并按升序打印它们.

核心算法步骤如下:
1. 图的表示: 首先, 程序读取输入的 `n-1` 条边, 并使用链式前向星(通过 `ForwardStar` 类实现)来构建树的图结构. 这是一个高效的存储方式, 便于后续遍历.
2. 深度优先搜索(DFS): 从根节点 `1` 开始, 使用一个栈(`java.util.ArrayDeque`)进行深度优先搜索, 以遍历整棵树.
3. 叶子节点判断: 在遍历过程中, 对于当前访问的节点, 程序会检查它有多少个子节点. 一个节点如果没有子节点, 那么它就被判定为叶子节点.
4. 收集和排序: 所有找到的叶子节点都会被收集到一个数组中.
5. 输出: 在遍历完成后, 程序会对这个包含所有叶子节点的数组进行升序排序, 然后格式化输出.

总的来说, 该算法通过一次深度优先遍历来识别出树的所有叶子节点, 然后进行排序和输出, 以满足题目的要求.

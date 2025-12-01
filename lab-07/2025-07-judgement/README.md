# Judgement

## Description

Please judge whether the given tree is a heap.

A heap is defined as a binary tree with two additional constraints:

- Shape property: a heap is a complete binary tree
- Heap property: the value stored in each node is either greater than or equal to $(\ge)$ or less than or equal to $(\le)$ the values in the node's children

### Input

The first line will be an integer T, which is the number of test cases. $(1 \le T \le 10)$

For each test case, the first line will be an integer $n$ $(1 \le n \le 10^5)$.

The second line will be $n$ integers, $a_1, a_2, \cdots, a_n$, $(1 \le a_i \le 10^9)$.

$a_i$ represents the value of the i-th node, then followed by $n-1$ lines, each line will be two integers $x$ and $y$, which means y-th node is a child of x-th node.

Besides, The left child will appear first (The order of appearance of child nodes is from left to right).

### Output

For each test, print the number of the test cases first, then print YES when the tree is a heap, else print NO.

We guarantee that $1 \le x, y \le n$ and input is a tree.

### Sample Input

``` log
3
4
1 2 3 4
3 1
3 4
3 2
3
2 1 3
2 1
2 3
3
3 2 1
3 1
3 2
```

### Sample Output

``` log
Case #1: NO
Case #2: YES
Case #3: YES
```

### HINT

### 思路

堆需要同时满足形状性质 (完全二叉树) 和堆序性质 (大顶或小顶).

读入边时按出现顺序区分左右孩子, 同时记录每个节点是否有父节点以定位根.

BFS 层序遍历树: 若某节点缺少左孩子但有右孩子, 或在遇到空孩子后又遇到非空孩子, 则不满足完全二叉树性质.

堆序性质在第一次看到父子关系时自动判定是大顶堆还是小顶堆, 后续所有边必须保持一致方向.

遍历结束后检查访问节点数是否等于 n, 确保树连通.

时间复杂度 `O(n)` 每个测试用例.

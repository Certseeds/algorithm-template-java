# Pre, in and post

## Description

You are given the pre-order and in-order traversal of the binary tree.

Please find the post-order of this tree.

### Input

The first line will be an integer $T$ $(1 \leq T \leq 10)$, which is the number of test cases.

For each test data:

The first line contains one integer $N$ $(1 \leq N \leq 10^3)$ — the number of nodes of the tree.

Then follows two lines:

The first line contains $N$ integers ranging from $1$ to $N$, indicating the pre-order traversal of the tree.

The second line contains $N$ integers ranging from $1$ to $N$, indicating the in-order traversal of the tree.

### Output

For each case, contains one line with $N$ integer, the post-order traversal of the tree.

### Sample Input

``` log
1
5
1 2 4 3 5
2 4 1 5 3
```

### Sample Output

``` log
4 2 5 3 1
```

### Solution

先读取每个测试用例的先序和中序序列, 利用节点值建立一个位置数组, 这样就能在 $O(1)$ 时间内找到任意节点在中序序列中的位置.

递归区间 `[preL, preR]` 对应 `[inL, inR]`, 其中 `preorder[preL]` 是当前根.

通过中序位置即可得到左右子树规模, 并在划分出来的左右子树上继续递归划分, 同时把父子关系加入复用的 forward star 邻接表中.

树结构得到后, 以每个测试用例的根 `preorder[0]` 为起点, 在邻接表上执行一次后序 DFS, 显式传递父节点避免走回头路.

左子树全部遍历完毕后再遍历右子树, 最后把当前节点写入答案.

整个过程每个节点只进入常数次, 所以总时间复杂度为 $O(N)$, 空间复杂度也为 $O(N) 用于存储邻接表和答案.

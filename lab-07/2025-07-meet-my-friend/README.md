# Meet my friend HEAP

## Description

You're required to devise a data structure as follows:

- It is a complete binary tree
- The key of each node is larger than its children's (if it has any)

We call it Heap.

Initially your heap is empty, then you are asked to insert $N$ distinct integers into the heap one by one.

When you insert a new element into the heap, you should:

- Place it at the leftmost position of the complete binary tree
- Swap it with its father if it is larger than the key of its father until it becomes the root of the heap or it is smaller than the key of its father

Now little HEAP wants to know the number of swapping times in each insertion.

### Input

The first line contains a single integer $N$ $(1 \le N \le 3 \times 10^5)$.

The second line contains $N$ integers $A_1, A_2, ... A_N$ $(1 \le A_i \le 10^9)$, denoting the integers you are going to insert in order.

It is guaranteed that these integers are distinct.

### Output

Output $N$ integers separated by spaces, representing the number of swapping times in each insertion.

### Sample Input

``` log
7
5 4 8 6 2 12 55
```

### Sample Output

``` log
0 0 1 1 0 2 2
```

### HINT

不要使用任何与堆和BST相关的STL!

> 注意STL是给c++用的, Java的标准库叫`Java Class Library`
>
> 只要不用C++就好了(棒读)

### 思路

直接模拟大顶堆的插入过程.

使用 1-indexed 数组表示完全二叉树, 第 i 个元素的父节点为 i/2.

每次插入新元素时放到数组末尾 (位置 pos = 当前元素个数), 然后执行上浮操作: 若当前元素大于父节点则交换并继续向上, 直到到达根或不再大于父节点.

记录每次插入过程中的交换次数即为答案.

单次插入最多上浮 `O(log n)` 层, 总时间复杂度 `O(n log n)`.

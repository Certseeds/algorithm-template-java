# Minimum Difference

## Description

Given a sequence $a$ with $n$ items, the weights of each item are $a_i$.

We define the minimum weight difference of $a_i$ as $h_i = \min_{j>i}|a_j-a_i|$.

Please compute the minimum weight difference of each item.

### Input

The first line has an integer $n$ ($2 \leq n \leq 2 \times 10^6$).

The second line has $n$ space-separated integers: $a_1, a_2, ..., a_n$ (all the elements of the array $a$, $1 \leq a_i \leq 10^9$).

### Output

Print space-separated integers: $h_1, h_2, ..., h_{n-1}$.

### Sample Input

```log
5
1 2 3 4 5
```

### Sample Output

```log
1 1 1 1
```

### HINT

It can be solved in a simple and efficient way by using sorting algorithm and linked list elegantly.

Please note that the size of input might be really large, so you might want to use an efficient way to read the input data.

### 算法分析

#### 链表解法

本题采用"按值排序 + 双向链表 + 按原始下标逐步删除"的做法，时间复杂度为 $O(n \log n)$，空间复杂度 $O(n)$.

- 将每个元素包装为节点 Node(value, index)，按值升序排序；
- 将排序后的节点串成一个双向链表(按值顺序相邻即为候选最近值)；
- 按原始下标 i 从小到大处理，当前有效集合为 {i..n-1}:
  - 在链表中定位到下标为 i 的节点，比较其左右相邻节点的值差取较小者；
  - 记录答案后，将该节点从链表中删除(只改相邻指针，O(1)).

由于每个下标只删除一次，且比较仅发生在左右两个邻居，总体为排序 $O(n \log n)$ + 线性 $O(n)$.

#### treap

本题用指针 Treap 维护已插入元素，Treap 节点包含 value、priority、size、左右子树指针. 主要接口:

- insert(value): $O(\log n)$
- floor(value): $O(\log n)$
- ceiling(value): $O(\log n)$

逆序遍历每个元素，查找最近值并计算差值. 整体 $O(n \log n)$. 断言保证输入范围.

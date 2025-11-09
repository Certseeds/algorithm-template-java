
## Description

Skylar is LowbieH's best friend, so every day LowbieH gives Skylar a gift.

There are totally $n$ days.

LowbieH will give Skylar a gift of value $v_i$ on day $i$ ($i = 1, 2, \dots, n$).

On odd days ($i \mod 2 = 1$), Skylar will celebrate, inspect the gifts, and wonder the median of values of all gifts received.

Can you help her to solve it?

Formally, for $k = 1, 3, \dots, 2 \lfloor \frac {n+1}{2} \rfloor - 1$, please print out the median of $v_1, v_2, \dots, v_k$.

### Input

The first line will be an integer $T$ ($1 \leq T \leq 10$), which is the number of test cases.

For each test case, the first will be an integer $n$.

Then there will be $n$ integers, representing the gift values $v_1, ... , v_n$ ($1 \leq n \leq 300000, 0 \leq v_i \leq 300000$).

### Output

For each test case, output the median gift values every odd days.

### Sample Input

```log
1
5
1 2 3 4 5
```

### Sample Output

```log
1 2 3
```

### 算法分析

#### 双堆

本题采用双堆法（最大堆+最小堆）实时维护中位数:

- 每次插入新礼物后，调整堆平衡，保证最大堆比最小堆多至多一个元素.
- 奇数天时，最大堆堆顶即为当前中位数.

整体复杂度 $O(n \log n)$，空间 $O(n)$

#### treap

本题用指针实现的 Treap（树堆）维护有序集合，Treap 节点包含 value、priority、size、左右子树指针. 主要接口:

- insert(value): $O(\log n)$
- kth(k): $O(\log n)$

每遇奇数天插入新值并查询中位数. Treap 保证插入和第 k 小查询均为 $O(\log n)$. 整体 $O(n \log n)$

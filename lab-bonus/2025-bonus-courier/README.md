# Courier

## Description

Van is a courier who needs to deliver n parcels numbered from 1 to n.

When a parcel is delivered for the ith time, we define its delivery priority to be i.

There are two restrictions on the order of delivery of parcels:

1. A parcel numbered i has a delivery priority less than ki.

2. There is some restriction (i,j) that the i-th parcel must be delivered earlier than the j-th parcel.

Please help us to calculate the minimum delivery priority of each parcel among all possible options.

### Input

The first line contains two positive integers $n(n \leq 2000)$ and $m(m \leq 10000)$, where n represents the number of packages, and m represents the number of restrictions of the second type.

The second line contains n positive integers k1, k2, ..., kn.

And the next m lines, each line contains two positive integers a and b, representing a pair of relative take-off order restrictions (a, b), where $a,b(1 \leq a,b \leq n)$, which means that package a must start before package b.

### Output

Contains n integers t1, t2, ..., tn represents the smallest possible starting sequence number for package i, and two adjacent integers are separated by spaces.

### Sample Input

```log
5 5
4 5 2 5 4
1 2
3 2
5 1
3 4
3 1
```

### Sample Output

```log
3 4 1 2 1
```

### Solution

对于每个包裹 i, 我们需要找出其最小可行交付位置 t_i, 满足所有拓扑约束和每个包裹的截止位置 k_i.

常用方法是逆向调度: 给定每个顶点的截止位置上界, 检查是否能把任务安排到 1..n 上使得所有前置依赖满足. 具体做法是从 t=n 向前分配, 每步选择当前出度为 0 且截止位置最大的结点放到当前位置, 并删除该结点, 更新其他结点的出度.

为了求最小可能的位置, 对每个包裹对其上界做二分, 在可行性检查中把该包裹的截止位置设为 mid, 其余使用原始截止.

为提高常数, 可行性检查中使用桶(按截止位置) + bitset 记录非空桶, 这样每次取最大截止位置的结点为 O(1) 的近似操作, 并复用工作数组避免每次分配. 此外边数 m 的遍历在每次检查中只发生一次.

整体复杂度在 n <= 2000, m <= 10000 下足够快, 实际实现中消除了大量内存拷贝与堆操作导致的常数开销。

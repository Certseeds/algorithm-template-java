# We only want the smallest

## Description

We construct an array $C$ out of two given array $A$ and $B$ such that each element in array $C$ is the product of an element in $A$ and an element in $B$.

For example, we have $A=[1,2,1]$ and $B=[5,2]$, then $C=[5,2,10,4,5,2]$.

Now we only want to know the first $K$ smallest element of array $C$.

### Input

The first line contains three integers $N$, $M$ and $K$, denoting the length of array $A$, the length of array $B$ and $K$ smallest respectively.

$(1 \le N, M \le 5 \times 10^5), K \le \min(5 \times 10^5, N \times M)$

The second line contains $N$ integers $A_1, A_2, ... A_N$ $(1 \le A_i \le 10^9)$.

The third line contains $M$ integers $B_1, B_2, ... B_M$ $(1 \le B_i \le 10^9)$.

### Output

Output $K$ integers separated by spaces in ascending order, denoting the first $K$ smallest elements in $C$.

### Sample Input

``` log
3 2 6
1 2 1
5 2
```

### Sample Output

``` log
2 2 4 5 5 10
```

### HINT

不要使用任何与堆和BST相关的STL!

> 注意STL是给c++用的, Java的标准库叫`Java Class Library`
>
> 只要不用C++就好了(棒读)

### 思路

先对数组 A 和 B 分别排序, 则 $A[i] \times B[j]$ 在 j 递增方向上单调递增.

使用手写小顶堆维护候选乘积, 初始时将每个 $A[i] \times B[0]$ 加入堆, 堆中元素形如 (product, i, j).

每次取出堆顶最小乘积作为答案, 然后将 $A[i] \times B[j+1]$ (若存在) 加入堆替代原位置.

由于 A 已排序, 初始入堆的元素最多 min(N, K) 个, 堆大小始终不超过该值.

取 K 次堆顶, 每次堆操作 `O(log N)`, 总时间复杂度 `O((N + K) log N)`.

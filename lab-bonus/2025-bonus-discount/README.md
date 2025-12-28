# discount

## Description

Satori, the bunny store owner, decided to offer a discount to whoever answered her problem:

What's the number of different Max-heaps build on $N$ different key values?

Note that two heaps are considered different if and only if the two binary trees have different pre-order traversals.

### Input

A single integer $N(1 \leq N \leq 1000)$.

### Output

Output a single integer indicating the number of different Max-heaps module $998244353$.

Note that 998244353 is a prime number.

### Sample Input

```log
5
```

### Sample Output

```log
8
```

### HINT

This problem is not difficult.

### Solution

每个 Max-heap 的形状固定为完全二叉树形状, 只有键值如何分配会变化.

设 dp[x] 表示由 x 个不同键值组成的不同 Max-heap 数量.

根必须放最大键值, 剩余 x-1 个键值要分配到左右子树.

对完全二叉树, 左子树节点数 l 由 x 唯一确定, 右子树节点数 r = x - 1 - l.

选择哪些键值进入左子树有 C(x-1, l) 种, 左右子树内部独立构成堆, 因此有递推.

dp[x] = C(x-1, l) * dp[l] * dp[r] (mod MOD).

组合数用阶乘与逆元预处理, invFact[n] = fact[n]^(MOD-2) (MOD 是质数).

computeLeftSize(x) 根据最后一层填充情况计算 l, 再从小到大做 dp.

复杂度 O(n), 额外空间 O(n).

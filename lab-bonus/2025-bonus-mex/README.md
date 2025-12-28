# Mex Problem

## Description

Here are $t(1 \leq t \leq 10^5)$ tests, each test contains two integers $a(0 \leq a \leq 10^9)$ and $b(0 \leq b \leq 10^9)$, you should find the Mex of the sequence $a \oplus 0$, $a \oplus 1$... $a \oplus b$, here $\oplus$ mean the bitwise xor operation.

The Mex of the sequence of non-negative integers is the smallest non-negative integer that doesn't appear in this sequence.

For example, Mex(1,2,3)=0, Mex(0,1,2,4,5)=3.

### Input

The first line contains a single integer $t(1 \leq t \leq 10^5)$, indicates the number of test cases.

The first and only line of each test case contains two integers a and b $(0 \leq a,b \leq 10^9)$.

### Output

For each test case, print a line contains a single integer - the answer to the problem.

### Sample Input

```log
3
0 9
8 6
9 9
```

### Sample Output

```log
10
0
2
```

### Solution

集合 S = { a xor 0, a xor 1, ..., a xor b }.

mex 是最小的非负整数 x, 使得 x 不在 S 中.

注意 a xor i 是双射, 因为对任意 x, 若存在 i 使得 a xor i = x, 则唯一的 i 为 i = a xor x.

因此 x 不在 S 等价于 (a xor x) > b.

问题变为求最小 x, 满足 p = (a xor x) 严格大于 b.

从高位到低位做 bit DP.

状态为 (bit, tight).

bit 表示当前处理的二进制位, tight=0 表示 p 的更高位前缀仍与 b 相等, tight=1 表示前缀已严格大于 b.

转移时枚举 pBit 取 0 或 1.

若 tight=0, 则不允许 pBit < bBit, 否则 p 会变成小于 b 且永远无法再超过.

若 tight=0 且 pBit > bBit, 则 nextTight=1.

对应的 xBit = aBit xor pBit, 目标是最小化 x, 因此在所有可行转移中取字典序最小的 x.

用记忆化 dfs 计算最小 x, 由于只需处理到 30 位, 每个查询仅有 32*2 个状态.

复杂度 O(t * 32), 适合 t 到 1e5.

# Minimum pearls

## Description

There are two kinds of pearls: black and white.

When a black pearl is the left of a white pearl and they are adjacent to each other, then they will disappear.

Now, there are n strings of pearls, each string with $a_i$ white pearls on the left and $b_i$ black pearls on the right.

Calculate the minimum number of the rest pearls when connecting these n strings.

### Input

The first line contains an integer $T$, indicating the number of test cases.

For each test case:

The first line contains an integer $n(1 \leq n \leq 100\ 000)$, indicating the number of the string of beads.

Each of the next $n$ lines contains two integers $a_i, b_i(a_i \geq 0, b_i \geq 0, 1 \leq a_i+b_i \leq 10\ 000)$.

It is guaranteed that $\sum (a_i+b_i) \leq 500\ 000$.

### Output

Output one integer, indicating the minimum number of the rest pearls.

### Sample Input

```log
2
2
1 2
2 1
2
1 3
2 1
```

### Sample Output

```log
2
3
```

### Solution

将每段珠串按是否左长右短分为兩组: 若 a <= b, 放入 leftHeavy 并按 a 升序; 若 a > b, 放入 rightHeavy 并按 b 降序.

把 leftHeavy 放在前面, rightHeavy 放在后面, 这是贪心的正确顺序, 可以最大化黑珠与白珠的互相抵消.

按该顺序扫描, 用 carryBlack 跟踪当前可用于抵消的黑珠数, unmatchedWhite 记录未被抵消的白珠.

对每段 s, 先用 carryBlack 抵消尽量多的 s.a, 未被抵消的白珠加入 unmatchedWhite, 然后把 s.b 加入 carryBlack.

最终剩余为 unmatchedWhite + carryBlack.

排序与扫描总复杂度 O(n log n + sum(len)), 空间 O(n)。

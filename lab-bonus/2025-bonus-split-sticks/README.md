# Split Sticks

## Description

Alice has a bundle of sticks.

She wants to select some sticks and split them into $k$ rows.

For beauty, the difference between the lengths of all sticks in the same row shall not exceed 1 and each row has the same number of sticks.

The length of each stick is a positive integer from 1 to $n$.

For every possible length, you know the amount of stick with that length.

Please calculate the maximum number of sticks Alice can select.

### Input

The 1st line is a positive integer $T(1 \leq T \leq 10000)$ which is the number of test cases.

For each test case:

The first line contains two integers $n(1 \leq n \leq 30000)$ and $k(1 \leq k \leq 10^{12})$, representing the number of different length of sticks and the number of rows the stick needs to be divided into, respectively.

The second line contains n integers $C_1, C_2 ... C_n$ $(0 \leq C_i \leq 10^{12})$, representing the number of sticks in each length.

Ensure that the sum of n of all cases does not exceed 30000.

### Output

For each case, output the maximum number of sticks Alice can select.

### Sample Input

```log
5
3 4
7 1 13
1 1
100
1 3
100
2 1
1000000000000 1000000000000
4 1
10 2 11 1
```

### Sample Output

```log
16
100
99
2000000000000
13
```

### HINT

1. The arrangement is [3,3,3,3], [1,2,1,1], [1,1,1,1], [3,3,3,3] (each list represents a row)

2. All the sticks can be arranged in the same row

3. 33 sticks with length 1 in each 3 rows

4. All the sticks can be arranged in the same row

5. All sticks with lengths of 2 and 3 are arranged in the same row

### Solution

目标是最大化可选木棍总数, 并把它们分成 k 行, 每行数量相同, 同一行内长度差不超过 1.

设每行选择 perRow 根, 则总数为 perRow * k, 我们对 perRow 做二分.

对固定 perRow 做可行性检查时按长度从小到大扫描.

由于同一行最多使用两种相邻长度 i 和 i+1, 因此在处理长度 i 时, 只能把上一长度 i-1 剩余的少量木棍与当前长度 i 的木棍合并成行.

用 prevCarry 表示来自长度 i-1 的剩余数量, 当前可用于成行的数量是 prevCarry + C[i].

能形成的行数增加 floor((prevCarry + C[i]) / perRow), 剩余 remainder = (prevCarry + C[i]) % perRow.

需要注意, remainder 里只有来自长度 i 的部分才能继续带到 i+1, 来自长度 i-1 的部分再向后会导致长度差达到 2.

为了最大化后续成行数, 让 carry 尽量来自长度 i, 因此下一轮的 prevCarry 取 min(remainder, C[i]).

若累计可形成的行数 >= k, 则该 perRow 可行.

复杂度为每个测试 O(n log S), 其中 S 约为 sum(C) / k.

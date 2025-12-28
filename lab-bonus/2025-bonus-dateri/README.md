# Dateri's Investment In Stocks

## Description

Dateri is a smart boy who is thirsty for making a big fortune.

Recently, he decides to invest in stocks to try his luck.

Unfortunately, due to the miserable state of the economy and lack of knowledge and experience, he suffered a great loss of money.

He has no choice but to ask LowbieH for help.

LowbieH offers him a predictor which can forecast the stock index of the upcoming $n$ days.

Dateri is very excited and can't wait to make money.

He wants to know the minimum days that he needs to wait to get a rise in the index if making a buy-in on the $k_{th}$ day.

### Input

The first line is an integer $T(1 \leq T \leq 12)$, meaning the number of the test cases.

For each test case, an integer $n(1 \leq n \leq 2 \times 10^5)$ denotes the number of the forecasted upcoming days, followed by $n$ stock indexes for each day: $index_1, \cdots, index_{n}(1 \leq index_i \leq 10^9, 1 \leq i \leq n)$.

The next line contains an integer $q(1 \leq q \leq 2 \times 10^5)$ denoting the number of the queries.

For each query, there's an integer $k$, which means that Dateri will make a buy-in on the $k_{th}$ day.

### Output

Print the answer of each query in $q$ lines.

If Dateri can't get a rise within the upcoming $n$ days, print $-1$.

### Sample Input

```log
1
5
2 3 3 5 4
5
1 2 3 4 5
```

### Sample Output

```log
1
2
1
-1
-1
```

### HINT

"rise" should be understanded as "strictly bigger than".

Huge input and output.

You are recommended to use fast I/O.

### Solution

对每个买入日 k, 需要找到最小的 j > k 使得 index[j] > index[k], 输出等待天数 j-k, 若不存在输出 -1.

这是经典 next greater element 问题.

从右到左扫描数组, 用单调栈维护一个严格递增的候选下标栈, 栈顶对应的 index 值始终大于当前值.

处理位置 i 时, 反复弹出栈顶使得 index[stackTop] <= index[i], 弹完后若栈非空, 最近更大元素位置为 stackTop, 距离为 stackTop - i, 否则为 -1.

然后把 i 入栈.

预处理得到每个位置的 nextGreaterDistance, 每个查询 k 直接 O(1) 回答 nextGreaterDistance[k-1].

由于输入输出很大, 输出用 StringBuilder 统一缓冲.

每个测试用例复杂度 O(n + q), 总复杂度受题目约束控制.

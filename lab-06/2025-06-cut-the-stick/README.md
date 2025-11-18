# Cut the stick

## Description

Lanran wants to cut one stick with length L into exactly N sticks with length $L_i(i=1,2...N)$, so $L = \sum{L_i}$.

However, the cost to cut one stick in to two pieces is the length of the stick, that means cutting a stick with length x will cost x.

Now he wants to know the minimal cost if he cuts stick optimally to get N sticks.

### Input

The first line will be an integer $T (1 \leq T \leq 100)$, which is the number of test cases.

For each test data: The first line contains an integer N $(1 \leq N \leq 10^3 )$ — the number of sticks Lanran needs to get.

Then the next one line contains N integers, the i-th integer $L_i (1 \leq L_i \leq 10^5)$ indicates the length of N sticks Lanran wants to get.

### Output

For each case, contains one line, print the minimal minimal cost.

### Sample Input

``` log
1
4
1 4 2 6
```

### Sample Output

``` log
23
```

### HINT

不要使用任何与堆和BST相关的STL！

> 注意STL是给c++用的, Java的标准库叫`Java Class Library`
>
> 只要不用C++就好了(棒读)

### 算法分析

这个任务的目标是计算将一根长度为总和 L 的棍子切成 N 段指定长度的最小成本, 每次切割成本等于被切割棍子的长度.

核心算法步骤如下:
1. 输入读取: 程序读取测试用例数量 T, 然后对于每个测试用例读取 N 和 N 个长度 L_i, 使用断言验证输入范围.
2. 优先队列初始化: 对于每个测试用例, 将所有 L_i 放入最小优先队列中, 以便每次快速获取最小的长度.
3. 合并过程: 使用贪心策略, 重复从队列中取出两个最小的长度, 计算它们的合并成本 (即两个长度之和), 累加到总成本中, 然后将合并后的新长度重新放入队列.
4. 终止条件: 当队列中只剩一个元素时停止, 该元素即为总长度, 但不再需要进一步切割.
5. 输出结果: 对于每个测试用例, 输出累加的总成本.

总的来说, 该算法通过模拟 Huffman 编码树的构建过程来最小化切割成本, 时间复杂度为 O(N log N) 由于优先队列操作, 空间复杂度为 O(N).

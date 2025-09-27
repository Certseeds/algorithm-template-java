## Description

There are T test cases, for each test case, there are n (1<=n<=400000) operations for a stack. And there are only two operations in this problem.

The following operations are:

1. push x
2. pop

For operation 1 you are asked to push x(1<=x<=100000000) in to the stack.

For operation 2 you are asked to pop out the top element of the stack and print the maximum number of the stack - minimum number in the stack.

### Input

The first line is an integer T(1<=T<=5).

For each case, the first line is n(1<=n<=400000) , which is the number of operations, then the following are n lines containing either operation 1 or operation 2.

It is not guaranteed that whether the stack is empty. If the stack is empty and you are asked to pop the element, you can just ignore the operation, but still need to print the corresponding answer.

### Output

For each pop operation, print the (MAX - MIN) value in the remaining stack. If the stack is empty, print 0.

For each test case, print each answer in a line.

### Sample Input

```log
1
6
push 387
pop
push 278
push 416
push 111
pop
```

### Sample Output

```log
0
138
```

### HINT

Hint: 0 is because the stack is empty after the first pop.

138 is calculated by 416 - 278 = 138.

## 解答

尽管题面的英语表达有些...一言难尽（例如 "It is not guaranteed that whether the stack is empty"）, 

但其核心是一个经典的“最小/最大栈”问题. 题目要求在每次 `pop` 操作后, 都能在 O(1) 时间内得到栈中剩余元素的最大值与最小值的差.

### 核心算法: 最小/最大栈

如果每次 `pop` 后都遍历整个栈来寻找最大和最小值, 那么单次操作的复杂度将是 O(n), 在 `n` 达到 400,000 的情况下必然超时.

正确的解法是使用两个辅助栈（`minStack` 和 `maxStack`）与主栈 `values` 同步操作: 
-  `push(x)`:
    -   `values` 正常推入 `x`.
    -   `minStack` 推入 `x` 与当前 `minStack` 栈顶的较小者.
    -   `maxStack` 推入 `x` 与当前 `maxStack` 栈顶的较大者.
-  `pop()`:
    -   三个栈同时 `pop`.

通过这种方式, `minStack` 和 `maxStack` 的栈顶永远分别是当前主栈中的最小值和最大值, 使得我们可以在 O(1) 时间内完成查询.

### 性能瓶颈: 从 TLE 到 AC 的关键

即便算法复杂度最优, 在 Java 中处理海量数据时, 实现细节也至关重要. 我们最初的尝试因为“超时（TLE）”而失败, 其根本原因在于Integer 的自动装箱/拆箱.

当我们试图遵循“读-处理-分离”原则, 将 400,000 个操作存入 `List<Integer>` 时, Java 实际上在堆内存中创建了 400,000 个 `Integer` 对象. 这带来了巨大的性能损耗: 
1.  内存开销: 每个 `Integer` 对象都比原始的 `int` 占用更多内存.
2.  时间开销: 频繁的自动装箱（`int` -> `Integer`）和后续处理中的自动拆箱（`Integer` -> `int`）本身就需要时间.
3.  缓存失效: `List<Integer>` 在内存中存储的是指向各个 `Integer` 对象的引用, 这些对象在内存中散乱分布, 导致 CPU 缓存命中率极低, 处理器需要不断从主内存中抓取数据.

最终解决方案是, 在保持“读-处理-分离”结构的同时, 将数据载体从 `List<Integer>` 换成了原始的 `int[]` 数组. `int[]` 在内存中是一块连续的空间, 没有任何对象开销, 这使得数据处理速度得到了质的飞跃, 从而通过了时间限制.

> 换一个角度想, 可以用C++重写一遍, STL容器可没拆箱装箱的坏毛病

### 展望: Project Valhalla

这个过程也凸显了 Java 当前在处理大规模数据时的痛点. 我们之所以要费尽心机地使用 `int[]`, 就是因为缺少一种既有对象特性、又有原始类型性能的“值类型”.

这正是 Project Valhalla 致力于解决的问题. 它计划为 Java 引入“值类型”（Value Types）, 允许开发者创建行为类似 `int` 但结构更丰富的类型. 如果 Valhalla 落地, 我们或许就能优雅地使用 `List<Operation>` 这样的面向对象结构, 而无需再为装箱带来的性能损耗而烦恼. 我们拭目以待.

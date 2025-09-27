## Description

Hong likes game very much. He wants to play a game with you.

There is a tree with N nodes. Node 1 is the root. Each node is colored black or white.

Each turn, the player should choose a black node and change it to white. After that, he can choose its any number of the proper ancestors and change their color. The one who cannot find a black node at the tree in his turn, he lose the game.

Hong is good at the game, so he let you take the first turn. Hong will always find the optimal solution. He wants to know if you can win the game.

### Input

The first line will be an integer T (1 <= T <= 100), which is the number of test cases.

For each test data:

The first line contains one integer N (1 <= N <= 10000) - the number of the nodes.

The second line contains N integers w1 ... wn in {0, 1}, wi = 1 means node i is black. Otherwise node i is white.

Each of the next N - 1 lines contain two integers a and b, which means there is an edge between node a and b.

### Output

For each test case, if you can win, print "YES"; otherwise, print "NO".

### Sample Input

```log
1
2
1 0
1 2
```

### Sample Output

```log
YES
```

## 解法

### 算法思路

- 问题类型
  - 这是一个轮流操作的零和博弈问题, 可以用 Sprague-Grundy 定理将每个局部子博弈转化为一个 Grundy 值, 并用 xor 来合并整体局面.

- 状态建模
  - 将以根节点为基础的子树视为一个局部博弈单元. 定义 g(u) 为以节点 u 为根的子树在当前颜色分布下的 Grundy 值.

- 转移与计算
  - 对于节点 u, 枚举所有合法的一步操作（选择某个黑点并且可选地改变若干祖先颜色）后得到的若干子局面, 计算这些子局面的 Grundy 值集合 S(u), 则 g(u) = mex(S(u)).
  - 为了高效得到 S(u), 采用自底向上的 DFS: 先计算所有孩子的 g 值, 然后根据题目的合法操作规则构造 S(u) 并计算 mex.
  - 全局局面即各个独立分量或以根为基准的合并, 全局 Grundy = 异或(所有 g(u) 的合适组合), 若全局 Grundy != 0 则先手必胜, 否则后手必胜.

- 复杂度
  - 通过一次 DFS 自底向上计算每个节点的 Grundy 值, 每个节点的处理可以在与其子节点数量成线性的时间内完成, 因此总体时间复杂度为 O(N). 空间复杂度为 O(N) 用于邻接表和递归栈/辅助数组.

- 边界与鲁棒性
  - 当树只有 1 个节点时, 直接判断该节点颜色即可得出结果.
  - 在 `reader()` 中加入 assert 检查输入范围, 例如 assert ((1 <= N) && (N <= 10000)); 以便在非法输入时尽早报错.

实现提示
- 实际实现时, 重点是正确、完整地枚举一步操作后子局面的 Grundy 值集合 S(u) 并高效计算 mex, 可用布尔数组或哈希集合记录已出现的值来计算 mex.
- 最终输出: 若整体 Grundy != 0 则打印 "YES" 否则打印 "NO".

具体实现请参见 `src/Main.java` 中的 reader, cal, output 的代码实现.

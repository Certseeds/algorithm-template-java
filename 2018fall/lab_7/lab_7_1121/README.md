## Description

Lanran has a binary tree with n nodes, she does not know whether this tree is a complete binary tree or not.

She turns to you for help. We guarantee that the input is a binary tree.

### Input

The first line will be an integer T, which is the number of the test cases. (1 <= T <= 14)

For each test case, the first line will be an integer n. (1 <= n <= 150000)

Then followed by n lines, each line will be two integers x and y.

The i-th line means the left child of node i is x, the right child of node i is y.

If node i has no left child, then x will be 0. If node i has no right child, then y will be 0.

### Output

For each test output Yes or No in one line.

### Sample Input

```log
1
5
2 3
4 0
5 0
0 0
0 0
```

### Sample Output

```log
No
```

## 解法

### 算法思路

- 读-处理-输出分离
  - `reader()` 负责解析 T, 每个用例的 n 以及 n 行左右孩子信息, 并做基本断言检查.
  - `cal()` 对每个用例调用检查函数并返回 "Yes" 或 "No" 的字符串列表.
  - `output()` 聚合并一次性打印结果, 每行以 '\n' 结尾.

- 完整二叉树判断（常用且高效的方法）
  - 首先根据所有节点的左右孩子数组构造 `isChild[]` 标记, 找到根节点（未被标记为子节点的节点）.
  - 对树进行层次遍历 (BFS): 将根入队, 逐个弹出并按左子、右子顺序处理.
  - 维护一个布尔标志 `seenNullChild`: 若遇到某个节点的左或右子为 0(不存在), 则将标志置为 true; 之后若再次遇到任何非空子节点, 说明不是完整二叉树 (返回 "No").
  - 若 BFS 结束且未触发非法顺序, 则为完整二叉树 (返回 "Yes").

- 复杂度
  - 时间复杂度: O(n) 每个用例 (构建标记 + 一次 BFS). 空间复杂度: O(n) 用于左右孩子数组与队列.

- 边界与实现提示
  - n = 1 时直接返回 Yes.
  - 输入保证为二叉树, 但请注意孩子索引为 0 表示空.
  - 在实现中使用 int 数组和显式数组队列替代对象队列可降低常数开销, 便于通过大规模测试.

实现细节请参见 `src/Main.java` 中的 `reader`, `cal`, `isComplete`, `output` 实现.

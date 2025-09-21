## Description

Write a program to print the pre order, in order and post order traversal of the given binary tree.

### Input

The first line will be an integer T (1 <= T <= 10), which is the number of test cases.

For each test data:

The first line contains one integer N (1 <= N <= 10^4) - the number of nodes.

Each of the next N - 1 lines contain two integers a and b, which means node a is the father of node b (1 <= a, b <= N). If a node has two sons, the son appeared earlier is the left son and another is the right son. If a node only has one son, the son is the left son.

### Output

For each test case, print three lines: the pre order, in order and post order traversal of the given binary tree.

### Sample Input

```log
1
8
1 4
1 3
4 2
2 7
3 5
3 6
6 8
```

### Sample Output

```log
1 4 2 7 3 5 6 8
7 2 4 1 5 3 8 6
7 2 4 5 8 6 3 1
```

## 解答

### 算法思路

- 输入处理
  - 使用快读 Reader 读取整数流, 先读 T, 每个用例读 n 和接下來的 n-1 条边.
  - 用两个数组 left[] 和 right[] 保存每个节点的左子节点和右子节点, 用 boolean 数组 isChild[] 标记被当作子节点出现的点. 根节点是未被标记的点.

- 遍历实现
  - 先序 preorder: 使用栈, 初始时如果 root != 0 则 push(root), 每次 pop u, 记录 u, 然后先 push 右子节点, 再 push 左子节点, 这样保证栈顶先访问左子树.
  - 中序 inorder: 迭代实现, 从 root 开始沿左子链入栈, 到达最左后 pop 并访问, 然后转向該节点的右子树, 继续重复.
  - 后序 postorder: 使用变形先序 root-right-left 将访问顺序记录到临时列表, 最後将该列表反转得到标准后序 left-right-root.

- 复杂度与健壮性
  - 时间复杂度: O(n) per test case, 空间复杂度: O(n).
  - 在 reader 中加入 assert 检查输入约束, 例如 assert ((1 <= n) && (n <= 10000)); 以便在不合法输入时尽早发现错误.

- 设计原则
  - 遵循读-处理-输出分离: reader() 负责解析输入并构建 TestCase, cal() 负责计算三种遍历并返回输出行, output() 负责最终打印并保证每行以 '\n' 结尾.
  - 避免深度递归, 所有遍历均采用迭代实现以提高稳定性.

以上为算法核心思路, 实现细节见 src/Main.java 中的 reader, cal, output 的具体代码。

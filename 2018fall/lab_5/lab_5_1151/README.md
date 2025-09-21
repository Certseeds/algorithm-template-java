## Description

Given n words, Hong wants to input one of these words. He wants to input (at the end) as few characters (without backspace) as possible, to make at least one of the n words appears (as a suffix) in the text.

Given an operation sequence, Hong wants to know the answer after every operation.

An operation might input a character or delete the last character.

### Input

The first line contains one integer n.

In the following n lines, each line contains a word.

The last line contains the operation sequence.

'-' means backspace and will delete the last character he typed.

He may backspace when there are no characters left, and nothing will happen.

- 1 <= n <= 4
- The total length of n words <= 100000
- The length of the operation sequence <= 100000
- The words and the sequence only contain lower case letter.

### Output

You should output L + 1 integers, where L is the length of the operation sequence.

The i-th(index from 0) is the minimum characters to achieve the goal, after the first i operations.

### Sample Input

```log
2
a
bab
baa-
```

> 注意, 前三行都是输入, 第四行是操作序列.

### Sample Output

```log
1
1
0
0
0
```

### HINT

- "": he need input "a" to achieve the goal.
- "b": he need input "a" to achieve the goal.

- "ba": he need input nothing to achieve the goal.
- "baa": he need input nothing to achieve the goal.
- "ba": he need input nothing to achieve the goal.

## 解答

### 算法思想

这个问题的核心是, 对于每个操作后形成的字符串, 我们需要找到最少再输入多少个字符, 就能使字符串的某个后缀是一个完整的字典单词. 如果直接对每次操作后的字符串进行暴力检查, 效率会非常低, 很容易超时.

为了高效解决, 我们可以采用 预处理 + 快速查询 的思路, 使用 AC自动机 (Aho-Corasick Automaton) 结合 广度优先搜索 (BFS) 来实现.

1.  构建AC自动机:
    *   首先, 将所有字典单词构建成一棵 Trie树. 树上的每个节点代表一个前缀.
    *   然后, 为Trie树构建 失败指针 (Failure Links). 对于任意节点 `u`, 它的失败指针指向的节点 `v` 所代表的字符串是 `u` 所代表字符串的最长真后缀, 且该后缀也必须是字典中某个单词的前缀. 这使得在匹��失败时可以快速跳转到下一个可能的状态.
    *   在构建失败指针的同时, 传递"单词结尾"的标记. 如果一个节点的失败指针指向一个单词终点, 那么该节点也应被视为一个匹配成功的状态, 因为以它为后缀的字符串必然也包含了一个完整的字典单词.

2.  预计算最短距离:
    *   问题的关键是计算从AC自动机中的每个状态(节点)出发, 最少需要多少步(输入多少字符)才能到达一个代表完整单词的节点.
    *   这可以转化为一个图上的最短路径问题. 我们使用 反向多源BFS 来解决:
        *   源: 将所有代表完整单词的节点(即 `isEndOfWord` 为 `true` 的节点)作为BFS的初始队列, 它们的距离 `minLen` 设为 0.
        *   反向遍历: 为了实现反向遍历, 我们在构建Trie时额外记录每个节点的父节点 (`parents`). BFS从所有单词终点开始, 沿着 `parents` 指针向上(向根节点方向)进行遍历.
        *   每向上走一步, 距离就加1. 由于BFS的性质, 当我们第一次访问到一个节点时, 所记录的路径长度就是最短的.
    *   通过这个过程, 我们预计算出了Trie中每一个节点到最近单词终点的最短距离, 并保存在 `minLen` 字段中.

3.  处理操作序列:
    *   在完成了预计算之后, 处理操作序列就变得非常简单.
    *   我们维护一个指针指向AC自动机中的当前状态节点, 初始时在根节点.
    *   遍历操作序列:
        *   如果输入一个字符, 就从当前节点沿着对应的 `children` 指针走到下一个状态.
        *   如果遇到退格符 `-`, 就回退到路径中的上一个节点.
    *   在每次操作之后, 当前状态节点的 `minLen` 值就是该状态下需要补全的最小字符数, 即为当前问题的答案. 我们直接查询并记录即可.

这种"预处理+查询"的模式将主要计算量集中在初始构建阶段, 使得后续每次查询都非常高效, 从而满足了题目的性能要求.

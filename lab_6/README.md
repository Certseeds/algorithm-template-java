# 2018fall-lab6

Welcome to (autumn) DSAA lab 6! Enjoy this Lab!

There are seven problems for you to solve. Two of them are bonus. Read the problem description carefully.

Compulsory problems:

+ A(easy): 10
+ B(easy): 10
+ C(easy): 20
+ D(median): 25
+ E(median): 25
+ Bonus problem: F(hard): 30
+ Bonus problem: G(hard): 30

Read the samples carefully can help you understand the problem.

## Stack And Queue

+ [x] problem A: lab_6_1152
+ [x] problem B: lab_6_1153
+ [x] problem C: lab_6_1154
+ [x] problem D: lab_6_1155
+ [x] problem E: lab_6_1156
+ [x] problem F: lab_6_1157
+ [x] problem G: lab_6_1158

## 总体评价

高 WA 与 TLE 表明题目在输入范围或隐含细节上容易踩坑（比如没有明确指出根是否为 1、是否存在重复边、是否需要 64-bit、是否需要特殊 IO 优化等）.

某些题（例如 C、E 统计里 WA/TLE/RE 数量很高）可能对复杂度或边界处理敏感, 或者样例对典型错误覆盖不足.

由语言分布（CSV 末列显示 Java 提交量大）可推断很多同学使用 Java; 若题目对 IO/内存/递归深度要求敏感而没有给 出建议, 会导致 Java 提交出现 TLE/RE 的概率上升.

### lab_6_1152（找叶子）

观察: 题意简单明确（度为1且非根即叶子）, README 给出算法提示并有样例.

尽管简单, 但若参赛者误解“叶子”定义（是否包含根）或没有处理 N=2 边界, 可能产生 WA. 应在题面明确写出“定义: 非根且度为1为叶子”, 并给出 N=2 示例（当前有, 但表述可更严谨）.

### lab_6_1153（先中后序遍历）

观察: 题目要求明确. 高 WA 数量可能源于输入顺序/子节点顺序约定（“如果一个节点有两个儿子, 先出现的为左子节点”）, 这一类细节若未严格读懂容易错.

在 README 中对如何处理单儿子（左子）和输入中的重复/无效边做更明确的约束, 并给出更多复杂 tree 的样例.

### lab_6_1154（集合操作, 维护最小值等）

观察: 这类题目对数据结构选型敏感（优先队列、懒删除等）. 若没有示例覆盖重复插入/删除空集合情形, 会被误判或出 WA.

明确空集合时的 Query/ Delete 行为, 给出样例, 提示常用数据结构或复杂度期望.

### lab_6_1155（树的直径）

观察: 通常用两次 BFS/DFS 可解. 若提交出现 TLE/WA, 可能是因为未做 O(n) 实现或误用递归导致栈溢出.

在题面提示“期望线性时间 O(n) 解法”, 并提供“节点数上界”与“建议避免深递归或提供非递归示例”.

### lab_6_1156（Hong 的口袋问题）

观察: 这是较复杂的实现题, 涉及优先策略与稳定性（按入袋时间）. 容易出错的点是比较/更新结构（若直接在 TreeSet 中变更对象而不 remove/reinsert 会导致错误）.

在题面或提示中说明“如何在数据结构中维护（key, priority, timestamp）且更新时需要 remove 再 add”, 或者提供示例说明稳定性要求.

### lab_6_1157（树上对局博弈）

观察: README 描述偏理论（Sprague-Grundy）, 实际实现有多种变体（有时深度异或可简化但并非总适用）. 高 WA 可能来自实现与题目规则不完全对齐.

给出更详细的操作举例、一个复杂样例, 以及明确是否可以用“深度异或”这种简化规则（若题意允许则写明, 否则详细推导 SG 状态转换）.

### lab_6_1158

这个问题 `google/gemini-2.5-pro` `openai/gpt5` 都无法解决, `anthropic/claude-opus-4.1` 才出了一个暴力解, 很强.

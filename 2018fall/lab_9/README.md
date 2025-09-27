## Description

Welcome to (autumn) DSAA lab 9! Enjoy this Lab!

There are ten problems for you to solve. Two of them are bonus. Read the problem description carefully.

Compulsory problems:

+ Easy (10 points): A, B, C
+ Median (20 points): D, E, F, G, H
+ Bonus problem: I(hard): 30 points, J(hard): 40 points
+ Bonus (25 points): K, L

Read the samples carefully can help you understand the problem.

## Stack And Queue

+ [x] problem A: lab_9_1176
+ [x] problem B: lab_9_1177
+ [x] problem C: lab_9_1178
+ [x] problem D: lab_9_1077
+ [x] problem E: lab_9_1079
+ [x] problem F: lab_9_1080
+ [x] problem G: lab_9_1179
+ [x] problem H: lab_9_1180
+ [x] problem I: lab_9_1181
+ [x] problem J: lab_9_1182
+ [x] problem K: lab_9_1083
+ [x] problem L: lab_9_1085

## 题目总结

### 教学目标与题目匹配度不足

+ 课程进阶的目标通常是让学生掌握图的基本抽象、常用算法(BFS/DFS、最短路、MST、强连通分量、拓扑排序、基于优先队列与并查集的算法等)、复杂度分析, 以及算法适用条件. `lab_9` 的题目覆盖面广, 但部分题目更偏工程实现或组合搜索(如带集合选择的最小生成树变体、若干几何/三维问题), 未必能保证学生在每一道题中都能专注于图论核心概念.

### 题目风格偏向“工程/竞赛”而非“教学练习”

+ 许多题目(例如几何最近路径、集合并 Kruskal 的变体、带先验集合的最短路)更适合竞赛训练中的综合能力考察, 因为它们要求学生在实现上处理很多工程细节(输入优化、边打包、剪枝、精度处理). 这些对提高编程能力有价值, 但作为课堂作业, 容易把学习重点从“理解算法原理”转移到“记住实现技巧”.

### 题目覆盖的图论主题与重复性

+ 虽然题目总体覆盖了最短路(Dijkstra、二源 Dijkstra)、最小生成树(Kruskal、集合并策略)、拓扑排序、SCC(Tarjan)、图上枚举与排列问题等, 但部分题目在考察点上有高度重合(例如多题都需用 Dijkstra 或 Kruskal 的变体). 重复性使得学生可能只记住通用模板, 而没有深入掌握每一类问题的变形与证明.

### 资源完整性与可测性问题

题目缺少完整样例、数据或参考实现, 会影响学生的调试效率与学习体验. lab_9 中部分题目虽然有实现与分析(在各子 README 中体现), 但 README 的说明层次不一, 可能导致评分与自学的困难.

### 可测性与评分一致性

+ 某些题目高度依赖浮点精度、几何计算或对大输入的特殊优化(例如避免 O(N^2) 的预处理), 这些容易在自动判题中引入非确定性或造成学生在工程实现上花费过多时间. 课程作业应尽量选择可自动判定且对实现细节容忍度较高的题目.

### 改进建议

+ 明确每道题的学习目标：每个题目添加一小节“考察要点”(1~2 行), 说明学生应掌握的算法和理论点, 例如“Dijkstra 的双源应用与剪枝”、“Kruskal 与超边枚举”等.

+ 降低工程实现门槛：对于考察图算法原理的题目, 提供主体框架或部分模板(例如并查集、优先队列 Dijkstra 的框架), 让学生把精力放在算法思路与复杂度证明上, 而非 IO/打包优化.

+ 补全与标准化资源：为每道题提供清晰的样例输入/输出、边界测试和推荐的复杂度目标(例如 “期望 O(m log n) 实现”), 便于学生自测与自动评分.

总结

`lab_9` 需要更严格的教学目标映射、难度与作业量控制、资源完整性保障以及减少对工程细节的过度依赖.

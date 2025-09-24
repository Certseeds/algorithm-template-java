# 2018fall-lab5

Welcome to (autumn) DSAA lab 4! Enjoy this Lab!

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

+ [x] problem A: lab_5_1145
+ [x] problem B: lab_5_1146
+ [x] problem C: lab_5_1047
+ [x] problem D: lab_5_1148
+ [x] problem E: lab_5_1149
+ [x] problem F: lab_5_1150
+ [x] problem G: lab_5_1151

## 总体评价

本次实验的设计虽然在选题上有部分合理性, 但其核心问题依然突出: 它并未能成为一个优秀的 KMP 算法练习平台, 反而迅速演变成了一场对更高级, 更偏门算法的"突击测验", 严重偏离了教学与巩固的初衷.

实验的设计思路似乎是: 以 KMP 为起点, 考察学生举一反三的能力. 然而, 这种"举一反三"的跨度过大, 从 KMP 直接跳跃到了 Z-algorithm, AC 自动机, 以及 "对答案二分" 等完全不同且难度陡增的领域.

`submit.csv` 的数据依旧冰冷地证明, 学生面临的障碍并非 KMP 算法本身, 而是那些远超课程教学范围的, 需要专门知识和大量训练才能掌握的算法技巧.

###  各题目的具体

#### A 题 (Rhymes) 与 B 题 (Wildcard):

作为热身题, 这两题的设置是合理的, 旨在让学生进入状态. B 题惨淡的提交数据 (57 AC vs 499 WA) 也确实起到了检验学生代码严谨性的作用.

#### C 题 (KMP Search):

在学习了 KMP 之后, 这道题是一道完美的, 直接的应用题. 它很好地考察了学生对 KMP 算法模板的掌握程度. 这是一个完全合理的题目.

#### E 题 (Longest Prefix as Suffix):

这道题是教学设计出现偏差的第一个信号. 它的解法虽然基于 KMP 的预处理数组, 但依赖于一个非常规的, "抖机灵"式的技巧 (拼接字符串).

这并非在考察学生对 KMP 算法工作原理的深入理解, 而是在考察他们是否接触过这类特定的竞赛题型.

对于初学者而言, 这是一个糟糕的教学案例, 它鼓励的是对技巧的记忆而非对原理的探索.

#### D 题 (Punchline):

这是最能体现设计失败的题目. 在一个 KMP 的练习实验中, 突然要求学生掌握 Z-algorithm, 甚至需要 RMQ (区间最值查询) 的知识来进行优化.

这相当于在教完加法后, 考试中突然出现一道微积分题目. Z-algorithm 与 KMP 虽然同属字符串处理领域, 但其思想和实现细节完全不同.

D 题高达 618 次 WA 和 810 次 RE 的提交结果, 明确显示了学生面对这种"知识断崖"时的无所适从.

#### F 题 (Longest Common Substring):

此题引入了"对答案二分"这一元算法 (meta-algorithm).

这本身就是一个重要的, 需要专门讲解和练习的算法思想, 将它与字符串问题结合, 作为 KMP 课后练习的一部分, 显然是不合适的.

它将学生的挑战从"如何应用 KMP"转移到了"如何构建二分模型"和"如何设计高效的验证函数"上, 完全偏离了主题.

#### G 题 (AC Automaton):

这道题是超纲的极致. AC 自动机是基于 Trie 的多模式匹配算法, 其复杂度和抽象程度远高于 KMP.

将其放入实验课中, 尤其是在一个以 KMP 为主题的实验里, 是完全不现实的教学要求. 这道题存在的唯一意义, 似乎就是筛选出那些有长期算法竞赛训练背景的学生.

### 结论:

作为一个 KMP 算法的课后实验, Lab 5 是不合格的.

它没能围绕 KMP 这一核心设计出一系列难度递进, 应用角度多样的练习题 (例如, 利用 KMP 的 `next` 数组求解字符串的循环节, 判断回文等).

相反, 它仅仅以 KMP 为跳板, 轻率地将学生推向了更高级算法的深渊.

这样的设计对于真心想要学好数据结构的学生来说是极具挫败感的.

它没有奖励学生对 KMP 算法的深入理解和灵活应用, 反而惩罚了他们知识面的局限性.

一个优秀的教学实验, 应当是围绕一个核心知识点搭建的"脚手架", 引导学生逐步攀升; 而不是非在此处设置一个"陷阱", 嘲笑那些没能直接飞跃过去的人.

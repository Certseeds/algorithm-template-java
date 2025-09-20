## Description

Yee_172 is in a maze of n row and m columns.

He is in position S and he wants to go to position V since he wants to find his friend Vince.

Vince has told Yee_172 how to find position E by giving Yee_172 a sequence of directions: 01123321…….

It confuses Yee_172 because Vince only told him that 0123 means four directions, but not specifically up, left, right, or down.

(i.e. the mappings relationship between the digits and specific directions are unknown)

Therefore, Yee_172 turns for your help.

You need to calculate the number of mappings of digits to directions that will lead Yee_172 to Vince.

### Input

The first line is T, which is the number of test cases, (T <= 10);

For each test case, the first line will be integer n, m, and then n lines following,

each line contains m characters (‘#’ means a beautiful girl, if Yee_172 go to ‘#’, he will stop finding Vince, ‘S’ is the start point and ‘E’ is Vince point.) N, m <= 50;

After the n lines, there will be one line contains 0, 1, 2, 3 only, meaning the sequence Yee have, which has a length of no more than 100.

### Output

For each test case, print a single integer, which the number of directions permutation that leads the Yee_172 to the Vince.

### Sample Input

``` log
2
5 6
.....#
S....#
.#....
.#....
...E..
333300012
5 3
...
.S.
###
.E.
...
3
```

### Sample Output

``` log
1
0
```

## 解答

这道题的本质是找出数字（0, 1, 2, 3）与四个基本方向（上, 下, 左, 右）之间有多少种有效的映射关系，能够使得角色从起点 'S' 沿着给定的指令序列成功到达终点 'E'。

由于数字和方向都是四个，这是一个经典的全排列问题。总共有 `4! = 24` 种可能的映射关系。考虑到数据量不大（迷宫大小不超过 50x50，指令长度不超过 100），我们可以直接暴力枚举这 24 种可能性。

代码的实现思路遵循了这一逻辑，并采用了“读-处理-输出”的分离模式：

1.  **`reader()` 方法**: 负责读取所有输入，包括迷宫的布局和指令序列，并将每个测试用例的数据封装起来。

2.  **`cal()` 方法**: 这是核心处理部分。它首先通过三层循环，手动生成了 `[0, 1, 2, 3]` 的全部 24 种排列，并存储在 `judge` 数组中。每一种排列都代表一种“数字->方向”的映射。然后，它遍历这 24 种映射，对每一种都调用 `simulate()` 方法进行路径模拟。

3.  **`simulate()` 方法**: 该方法负责对某一种特定的映射关系进行模拟。它从 'S' 点出发，严格按照指令序列和当前映射的移动规则（例如，`p[0]` 对应向下，`p[1]` 对应向右等）来移动。在模拟过程中：
    *   如果路径移出边界或撞到墙壁 ('#')，则该映射无效，模拟失败。
    *   如果路径在任何一步成功到达终点 'E'，则该映射有效，模拟成功并立即返回。
    *   如果走完了所有指令仍未到达 'E'，则模拟失败。

4.  **`output()` 方法**: 负责将 `cal()` 方法统计出的、每个测试用例的有效映射数量进行打印。

综上所述，该解法通过暴力枚举所有 24 种可能性，并对每一种可能性进行路径模拟，最终统计出成功的次数，从而得到答案。

> 题目内的case似乎较弱, 没有半路到达终点的情况

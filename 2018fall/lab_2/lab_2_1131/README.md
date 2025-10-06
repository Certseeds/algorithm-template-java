---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_2_1131

## Description

There were various kinds of demons in Binary Forest.

They committed terrible crimes.

However, no one saw their faces exactly, the only known thing is the height of every kind (demons for the same kind all share a common height, which is different from any other kind).

One day, the sheik of the forest asked all men lived in the forest to line up and stand in ascending order of their heights, but he didn't know how to find the demons.

Could you please help the poor sheik?

### Input

The first line will be an integer T $(100 \leq T \leq 1000)$, which is the number of test cases.

For each case have two lines.

The first line of the input contains two number $n$ and $m$, $n$ is the number of the men in the forest $(1 \leq n \leq 10^6)$, and $m$ is the demon's height.

The second line contains $n$ numbers which are the heights of the men in the forest.

You should determine whether demon(s) is(are) among the men or not.

### Output

For each case, output only one line.

If there is(are) demon(s) among the men, output YES, otherwise output NO.

### Sample Input

``` log
2
3 4
1 2 3
3 1
1 2 3
```

### Sample Output

``` log
NO
YES
```

## 算法分析

本题的核心是判断某个特定高度的“恶魔”是否在已知的有序队列中. 由于队列已经升序排列，可以直接使用二分查找算法. 

对于每组数据，遍历时用二分法在队列中查找目标高度. 如果找到则输出YES，否则输出NO. 二分查找的时间复杂度是O(log n)，可以高效处理大规模数据. 

整体算法流程：

1. 读取每组数据，包括队列长度、目标高度和队列内容. 
2. 对每组队列使用二分查找判断目标高度是否存在. 
3. 输出每组结果. 

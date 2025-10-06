---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_2_1132

## Description

Under your help, the sheik of the forest found that the demons weren't among the men.

They must have escaped.

After reading ancient books, the sheik found that exactly $m$ cd (candela, which is the unit of strength of light) units of light exposed by the join of two light swords can kill the demons.

The men in the forest brought $n$ light swords and put them in **ascending order**.

Could you help sheik to find how many pairs are in the given $n$ light swords with a combination of $m$ cd.

(You can also choose a light sword twice if the condition is satisfied for the sheik can get another same one from the craftsman in the forest.)

### Input

The first line will be an integer $T$ $(1 \leq T \leq 10)$, which is the number of test cases.

For each case contain two lines.

The first line contains two numbers $n$ $(1 \leq n \leq 5000)$ and $m$ $(1 \leq m \leq 10^8)$, $n$ is the number of light swords, $m$ is the specified target.

The second line contains $n$ integers: $a_1, a_2, \ldots, a_n$ $(1 \leq a_i \leq 10^6)$, which are the light strengths of the light swords brought by men in the forest.

### Output

For each case print the number of pairs.

### Sample Input

``` log
3
4 5
1 2 3 4
4 9
2 7 11 15
3 6
1 2 3
```

### Sample Output

``` log
2
1
0
```

### HINT

Here we simply regard the strength of the combination of light swords is the sum of light strengths of the original ones.

## 算法分析

本题要求统计有序数组中有多少对元素之和等于指定值m. 可以利用二分查找或哈希集合来加速查找.

对于每组数据，遍历每个元素a，查找是否存在m-a. 如果存在则计数加一. 由于每对会被统计两次，最后结果除以2.

整体流程:

1. 读取每组数据，包括数组长度、目标值和数组内容.
2. 对每组数组，遍历每个元素并查找是否存在配对元素.
3. 输出每组结果.

---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_2_1135

## Description

Joy is now applying for postgraduate programs.

But due to his low GPA, no school really wants to receive him.

One day poor Joy meet Aladdin, Aladdin promises to help him delete some of his courses he attended to increase his GPA.

But now he is confused again, can you write a program to help him point out the maximum GPA he can get by deleting courses.

It should be mentioned that the calculation formula of GPA is $\sum(s[i] \times c[i]) / \sum(s[i])$, where the academic credit of the $i$-th course is $s[i]$ and the score of the $i$-th course is $c[i]$.

And because of the limitation of power of the magic lamp, he could at most delete $k$ courses.

### Input

The input contains multiple test cases.

For each test case:

The first line has two positive integers $n$ $(1 \leq n \leq 10^5)$, $k$ $(0 \leq k < n)$, which is the total course number selected by Joy and the maximum number of deleting courses.

The second line has $n$ positive integers $s[i]$.

The third line has $n$ positive integers $c[i]$ $(1 \leq s[i], c[i] \leq 10^3)$.

### Output

Output the highest final score.

The answer should be round to 3 decimal places.

### Sample Input

``` log
3 1
1 2 3
3 2 1
```

### Sample Output

``` log
2.333
```

### HINT

算法分析

本题要求在最多删除k门课程的前提下，使加权平均分（GPA）最大.

可以采用二分法结合贪心思想.

每次二分枚举一个可能的GPA值，判断是否能通过删除不超过k门课程，使剩下的课程加权平均分不低于该值.

判断时，将每门课程的贡献值按从小到大排序，删除贡献最小的k门课程，剩下的课程如果加权平均分不低于枚举值，则该值可行.

最终通过二分法找到最大可行的GPA.

该算法时间复杂度约为O(n log D)，其中n为课程数，D为枚举精度.

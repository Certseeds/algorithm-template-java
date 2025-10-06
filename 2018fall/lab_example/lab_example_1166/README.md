# lab_example_1166

> problem_id: 1166

## Description

Give you an algorithm set and an article.

Please answer whether algorithms in the given set appears in the article.

### Input

The first line of input is the number of test cases T $(1 \leq T \leq 100)$.

For each test case, the first line will be an integer n $(1 \leq n \leq 10)$.

The next n lines describe the given algorithm set.

Each line will contain a string.

Then there will be an integer m $(1 \leq m \leq 1000)$.

Then there will be m words (strings) describe the article.

These strings are separated by space.

We guarantee the given string only contains lowercase and uppercase English letters.

And the length of each string is less than 100.

### Output

For each article, if one of the given algorithms appears in the article, print "Appeared" (without quotes).

Otherwise print "Not appeared" (without quotes).

### Sample Input

``` log
2 3
DynamicProgramming
SweepLine
dijkstra
8
We can solve this problem by sweepline algorithm
3
DynamicProgramming
SweepLine
dijkstra
9 
Dijkstra algorithm can be used to solve SSSP problem
```

### Sample Output

``` log
Appeared
Appeared
```

## 分析

没什么好说的, HashSet处理即可

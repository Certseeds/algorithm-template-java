# FSA

## Description

As learned in the lecture, FSA can be used to solve string matching problems. Here you are given a string S, and you are required to output it's transition function according to the FSA algorithm.

## Input

One line, indicating the input string $S: (1 \leq |S| \leq 10^5)$

The string contains only lowercase letters.

## Output

$|S|$ lines, each containing 26 integers, indicating the transition function.

### Sample Input

``` log
aabaaabb
```

### Sample Output

``` log
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
2 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
2 3 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
4 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
5 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
6 3 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
2 7 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
4 8 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
```

### 算法分析

本题要求构建字符串的有限状态自动机(FSA)转移表, 用于高效匹配

核心思路是先用 KMP 算法求出前缀函数, 然后对每个状态和每个字母, 递归回退到最长可匹配前缀, 若匹配则状态加一, 否则回退

这样可在 $O(n\cdot|\Sigma|)$ 时间内预处理出所有转移, 其中 $n$ 为模式串长度, $|\Sigma|=26$

输出每个状态下 26 个字母的转移结果, 可直接用于自动机匹配

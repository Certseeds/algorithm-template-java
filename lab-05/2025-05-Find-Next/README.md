# Find Next

## Description

KMP algorithm is an improved string matching algorithm proposed by `D.E.Knuth`, `J.H.Morris` and `V.R.Pratt`, so it is called Knut Morris Pratt operation (KMP algorithm for short).

The core of KMP algorithm is to use the information after matching failure to reduce the matching times between pattern string and main string to achieve the purpose of fast matching.

The specific implementation is realized by a `next()` function, which contains the local matching information of pattern string.

Now give you a text $S$, please output its next array.

## Input

a line contains a text string $S: |S| \leq 1000000$

## Output

Output s.length lines. The i line indicates next[i] for S.

### Sample Input

``` log
ababc
```

### Sample Output

``` log
0
0
1
2
0
```

### 算法分析

本题要求输出字符串的 KMP 前缀函数数组

前缀函数 $\text{next}[i]$ 表示以 $i$ 结尾的前缀的最长真前后缀长度

实现时, 依次枚举每个字符, 若当前字符与前缀末尾字符不等, 则回退到更短的前缀, 否则前缀长度加一

最终输出每一位的前缀函数值

整体时间复杂度 $O(n)$, 适合处理大规模字符串

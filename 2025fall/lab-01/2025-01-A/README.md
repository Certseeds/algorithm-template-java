## Description

Given a nondecreasing sequence $a$ with length $n$.

We want to know whether integer $x$ in the array $a$ or not.

## Input

The 1st line is a positive integer $n$ $1 \leq n \leq 100000$.

The 2nd line contains $n$ integers: $a_1, a_2, \ldots, a_n$.

For each $a_i$ $0 \leq a_i \leq 10^9$.

The 3rd line is a positive integer $T$ $1 \leq T \leq 100000$ which is the number of test case.

Then $T$ lines follow.

Each contains an integer $x$ $1 \leq x \leq 10^9$ for a test case.

## Output

For each test case, print "YES" (in a single line, without quotes) if $x$ is in $a$.

Otherwise print "NO".

### Sample Input

``` log
4
1 2 3 999999
2
99
3
```

### Sample Output

``` log
NO
YES
```

## Description

Given two arrays $A$ with length $n$ and $B$ with length $T$.

We want to know whether each element in array $B$ is in array $A$ or not.

### Input

The 1st line is a positive integer $n$ $(1 \leq n \leq 1000)$.

The 2nd line contains $n$ integers: $a_1, a_2, ..., a_n$.

For each $a_i$, $0 \leq a_i \leq 10^9$.

The 3rd line contains a positive integer $T$ $(1 \leq T \leq 1000)$.

The 4th line contains $T$ integers $b_1, b_2, ..., b_T$.

For each $b_i$, $0 \leq b_i \leq 10^9$.

### Output

For each element in $B$, print "yes" (in a single line, without quotes) if it is in $A$.

Otherwise print "no".

### Sample Input

``` log
4
2 3 999999 1
2
3 99
```

### Sample Output

``` log
yes
no
```

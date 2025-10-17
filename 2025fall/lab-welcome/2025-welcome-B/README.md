## Description

Given two arrays A with length n and B with length T.

We want to know whether each element in array B is in array A or not.

Since the input is large, you may need fast I/O (in Java).

Java Faster I/O is shown via following links: <https://pastebin.ubuntu.com/p/zBd3g4j366/>

### Input

The 1st line is a positive integer $n$ $ ( n \leq 10^6)$.

The 2nd line contains $n$ integers: $ a_1,a_2...a_n$.

For each $a_i$, $0 \leq a_i \leq 10^5$.

The 3rd line contains a positive integer $T$ $(T \leq 10^6)$.

The 4th line contains $T$ integers $b_1,b_2,...b_T$.

For each $b_i$, $0 \leq b_i \leq 10^5$.

### Output

For each element in $B$, print "yes" (in a single line, without quotes) if it is in $A$.

Otherwise print "no".

### Sample Input

``` log
4
2 3 9999 1
2
3 99
```

### Sample Output

``` log
yes
no
```

### HINT

Note that the integer value ranges are different in Problem 1 and 2.

C++使用cin/cout关同步方法:
``` cpp
ios_base::sync_with_stdio(0), cin.tie(0), cout.tie(0);
```

endl 要换成 '\n'

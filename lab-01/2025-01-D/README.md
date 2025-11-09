## Description

There is an array $a_1, a_2, \ldots, a_n$.

Do you know how many triples $(i, j, k)$ are there, which satisfy that $i < j < k$ and $a_i + a_j + a_k = S$?

### Input

The first line contains two integers $n$ $1 \leq n \leq 3 \times 10^3$, $S$ $1 \leq S \leq 10^9$, indicating the length of the array and the sum of three elements for each triple.

The second line contains $n$ integers $a_1, a_2, \ldots, a_n$ $1 \leq a_i \leq 10^9$, indicating the array.

It's guaranteed that the array is non-decreasing.

### Output

Output the answer in one line.

### Sample Input

``` log
5 700
100 200 200 300 300
```

### Sample Output

``` log
3
```

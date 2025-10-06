---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_3_1141

## Description

Three body is a very popular book among teenagers.

It tells us some planets are far away from us, but they can destroy the earth easily.

We assume all planets are arranged in a circle.

One day, the most powerful planet is about to destroy other planets.

It destroys other planets in the following way:

All planets are numbered $0, 1, 2, \ldots, n-1$.

The destruction starts from the planet numbered $0$ in the first round.

Every round the planet numbered $(m + 1)$th (we define $m$ to be the destroy number) planet is going to be destroyed.

After that, the planet right after it becomes the first planet in the next time.

The most powerful planet consecutively attack other planets until all other planets are destroyed.

Now, give you the index of the earth, please determine whether the earth is the last planet to be destroyed.

### Input

First line includes a positive integer $T$ $(T \leq 100)$ representing the number of test cases.

For each test case, the line includes $n$ and $m$ and $e$, indicating the number of planets, the destroy number, and the index of earth, respectively.

$(1 \leq n, m \leq 100, 0 \leq e < n)$

### Output

For each test case, if the earth is the last to be destroyed, output a line containing "Yes", otherwise output a line containing "No".

### Sample Input

``` log
3
3 4 0
3 4 1
3 3 1
```

### Sample Output

``` log
Yes
No
Yes
```

## 分析

分析一下下面的用例

``` log
3
3 4 0
3 4 1
3 3 1
```

## 3 4 0

注意到`numbered (m + 1)th`,可见是从1开始计数的.

three node, destory 4 times, earty is fst

+ 0 1 2
  + 0 1 2 0 1
  + 1 is destory
  + 2 0
+ 2 0
  + 2 0 2 0 2
  + 2 is destory
  + 0
+ 0
  + 0 0 0 0 0
  + 0 is destory

0 is the last.

编号从0开始, 但是计数1开始记到m+1, 麻烦.

## 3 4 1

过程如上, 1是倒数第二个, 不是.

### 3 3 1

+ 0 1 2
  + 0 1 2 0
  + 0 is destory
  + 1 2
+ 1 2
  + 1 2 1 2
  + 2 is destory
  + 1
+ 1
  + 1 1 1 1 1
  + 1 is destory

这里可以发现, 需要把下一个计数的作为第一个, 才能推理通顺.

PS: 需要注意到, 并不是只进行math.min(n,m)次, 而是准确的进行n次, 每次1个.

## 约瑟夫环问题

这个问题其实是约瑟夫环问题, 数学证明之后可以比较简单的解决.

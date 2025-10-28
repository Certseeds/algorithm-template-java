## Description

LowbieH and Peggy are in the playroom to figure out an interesting problem.

Given a string of matched parenthesis $s$, the scoring rules are defined by:

1. If $s=()$, then $score(s)=1$.
2. If $t$ is another string of matched parenthesis, then $score(s+t)=score(s)+score(t)$.
3. $score\Bigl((s)\Bigr) = score(s) * 2$.

LowbieH and Peggy want to calculate the score of given string quickly, can you help them?

Since the answer may be very large, you only need to tell them the score after mod $514329$.

## Input

One line a string.

It contains only parenthesis and is perfect matched.

The length of input will not exceed $100000$.

## Output

One line an integer, denoting the score (mod $514329$).

## Sample Input

``` log
((()())())
```

## Sample Output

``` log
10
```

## HINT

$score\Bigl(((()())())\Bigr) = ((1 + 1) * 2 + 1) * 2 = 10$

### 算法分析

本题用栈模拟括号结构的分值计算.

遇到左括号入栈, 遇到右括号弹栈并计算分值, 空括号为1, 嵌套为2倍和.

最终栈中所有分值累加即为答案, 取模输出.

时间复杂度 $O(n)$, 空间复杂度 $O(n)$.

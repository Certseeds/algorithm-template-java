## Description

There are $n$ brackets, and you want to know whether they are matched.

The brackets will only contain $\{,\},(,),[,]$.

Here is the matching rules.

For example, {{[}]} is not matching, but [{{}}()] is matching.

Please write a program to check whether the given string is matched or not.

## Input

The ﬁrst line will be an integer $T$, which is the number of test cases. $(1 \leq T \leq 10)$

For each test case, the ﬁrst line will be an integer $n$ $(1 \leq n \leq 30000)$

Then there is a line with $n$ brackets.

## Output

For each test case, print YES if the test case is a matching case.

Otherwise, print NO.

## Sample Input

``` log
7
1
(
2
()
2
{]
6
[(){}]
4
(])[
8
[[{{}}]]
6
[][{]]
```

## Sample Output

``` log
NO
YES
NO
YES
NO
YES
NO
```

### 算法分析

本题采用栈结构判断括号序列是否合法. 遍历字符串, 遇到左括号入栈, 遇到右括号判断栈顶是否为对应类型的左括号, 若匹配则弹栈, 否则不合法. 最终栈为空则匹配成功, 否则失败.

该算法时间复杂度为 $O(n)$, 空间复杂度为 $O(n)$.

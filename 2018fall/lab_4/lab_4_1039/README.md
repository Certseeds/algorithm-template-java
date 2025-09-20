## Description

There are n brackets. And you want to know whether they are match to each other.

The brackets will only contain `{` `}` `(` `)` `[` `]`.

The matching rules are the same as in Math.

For example, `{{[}]}` is not matching, and `[{{}}()]` is matching.

Please write a program to check whether the given brackets string is matching or not.

### Input

The first line will be an integer T, which is the number of test cases. (1 <= T <= 10)

For each test case, the first line will be an integer n ( 1 <= n <= 30000)

Then there is a line with n brackets.

### Output

For each test case, print `YES` if the test case is a matching case. Otherwise, print `NO`.

### Sample Input

```log
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

### Sample Output

``` log
NO
YES
NO
YES
NO
YES
NO
```

## 解答

这道题就是经典的括号匹配问题，属于数据结构课程的入门必刷题。

解决思路非常直接：使用一个栈（在 Java 里我们用更现代的 `Deque` 实现）。遍历输入的括号字符串：
- 遇到开括号（`{`, `[`, `(`），就把它压进栈里。
- 遇到闭括号（`}`, `]`, `)`），就从栈顶取出一个开括号来配对。如果栈是空的，或者取出的开括号不匹配，那这个字符串肯定就不可行了。
  - 注意不要一个字符一个字符的读, 然后读到一半就break, 这会导致后续数据乱掉.
  - 如果不允许引用数据结构, 请先实现一个Stack, 然后用这个Stack来实现


遍历完整个字符串后，如果栈正好是空的，说明所有括号都完美配对，输出 "YES"；否则就是 "NO"。


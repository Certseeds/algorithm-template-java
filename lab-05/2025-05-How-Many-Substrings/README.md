# How many substrings

## Description

Give you a string, you should print how many none empty substrings it has.

## Input

The first line is number of tests.

T: $(1 \leq T \leq 10)$

The second line is a string $S$.

The length of $S$ doesn’t exceed 1000, that is $|S| \leq 1000$

$S$ will only contain lower case English letters.

## Output

For each test, you should print an integer in a single line, which is the number of none empty substrings of $S$.

### Sample Input

``` log
1
hello
```

### Sample Output

``` log
15
```

## HINT

> Easy problem.

### 算法

对于每个测试用例, 只需记录字符串长度为 n, 使用公式 $n(n+1)/2$ 统计全部非空子串

程序依次读取字符串并直接套用公式, 通过 64 位整型保存结果避免溢出

整体时间复杂度与输入长度总和成正比, 额外空间仅为常数

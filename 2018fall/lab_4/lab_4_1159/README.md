## Description from website

One day lanran finds that his name can be found in both string “lanran2001” and “20lanran01”.

Now lanran gives you T(T<=1000) strings (string.length() <= 100).

For each string, you can remove any substring of them to make it become the string “lanran”.

If you can do this, print a “YES” in a line, otherwise print “NO”.

> string.length() <= 100). 对于每个字符串, 你可以删除其中的任意子串, 使其变成字符串 “lanran”. 如果可以, 就打印一行 “YES”, 否则打印 “NO”.

### Input

The first line will be an integer T(T<=1000), which is the number of given string.

For each test data, there will be one line containing a string of lowercase characters('a' – 'z') and 0-9 digits.

> 第一行将是一个整数 T(T<=1000), 表示给定字符串的数量.  对于每个测试数据, 将有一行包含小写字母（'a' – 'z'）和 0-9 数字的字符串.

### Output

For each given string, print the answer of lanran's question.

> 对于每个给定的字符串, 打印出 lanran 问题的答案.

### Sample Input

``` log
6
lanran
lanran2001
20lan0r1an
lanan
nanla
larann
```

### Sample Output

``` log
YES
YES
YES
NO
NO
NO
```

## 解答

没什么好分析的, 输入, 输出都没有坑, 单纯检查输入的字符串内是否含有顺序的那六个字母, 哪怕是硬写if-else都能过.

## Description

One day, Wavator is taking his Linear algebra course. He hates calculating the expression of matrix so he wants to  develop a calculator to help him.

But, he got 59 in last years' DSAA course, so he turns you for help.

n square matrices of size m are given, and we define an operation like “(1+2)*1” which means the matrix 1 plus matrix 2  and then multiplies matrix 1.

Wavator only wants to calculate “+” and “-” and “*”, so he denotes that “+” means A + B = C where C[i][j] = A[i][j] +  B[i][j] .The rule of “-” is similar with "+".

Notice that in matrix multiplication, a*b and b*a is not the same.

Since the number may be too large during the calculation process, in each step you should mod 1000000007.

### Input

The first line contains an integer T, meaning there will be T (T<=10) test cases.

For each test cases, the first line is n and m (n <= 10 and m <= 50), then there will be n parts, each part is a m * m

matrix a, 0<=a[i][j] <= 10000, matrix's are numbered from 1 to n, then there will be a string s, the length of s is not

greater than 50, and it is valid. (contains only 1~n numbers (index of matrix's) and “+”, “-”, “*” only) .

### Output

For each test case, print m lines, each line should contain m integers, meaning the value of the final matrix at this line and this column.

### Sample Input

``` log
1
2 2
1 2
2 1
2 2
3 3
(1+2)*1
```

### Sample Output

``` log
11 10
13 14
```

### HINT

Codes of mod in c++ lang. similar using java.

``` cpp
const int MOD = 1000000007;


inline int add(int x, int y) { return (x + y) % MOD; }


inline int sub(int x, int y) { return (x - y + MOD) % MOD; }


inline int mul(int x, int y) { return static_cast<int>((long long) x * y % MOD); }
```

## 解答

本题要求实现一个矩阵表达式计算器, 能够处理包含 `+`、`-`、`*` 和括号的矩阵运算.

这是一个经典的中缀表达式求值问题. 解决此类问题的标准方法是使用双栈算法（一个操作数栈, 一个操作符栈）, 这正是本代码 `Main.java` 所采用的核心思路.

算法流程如下: 

1.  定义数据结构: 
    *   创建一个 `Matrix` 类, 用于存储矩阵数据并实现加、减、乘三种运算. 所有运算的每一步都严格按照题目要求对 `1000000007` 取模.

2.  双栈求值 (`evaluateExpression` 方法): 
    *   准备两个栈: `values` 用于存放矩阵（操作数）, `ops` 用于存放运算符.
    *   遍历表达式字符串: 
        *   遇到数字, 则解析出完整的矩阵编号, 从输入中获取对应矩阵, 压入 `values` 栈.
        *   遇到左括号 `(`, 直接压入 `ops` 栈.
        *   遇到右括号 `)`, 则不断从 `ops` 栈中弹出运算符, 从 `values` 栈中弹出两个矩阵进行计算, 直到遇到左括号为止.
        *   遇到运算符 (`+`, `-`, `*`), 则与 `ops` 栈顶的运算符比较优先级. 如果当前运算符优先级较低或相等, 就先计算栈顶的运算, 再将当前运算符压栈. `*` 的优先级高于 `+` 和 `-`.

3.  收尾计算: 
    *   遍历完整个表达式后, `ops` 栈中可能还有剩余的运算符, 按顺序全部计算完.
    *   最终, `values` 栈中仅剩的一个矩阵就是整个表达式的结果.

整个程序将此算法封装在 `cal` 方法中, 并遵循了 `reader` -> `cal` -> `output` 的清晰分离结构, 使得代码易于理解和维护.

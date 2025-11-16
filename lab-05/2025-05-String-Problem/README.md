# String-Problem

## Description

There is a string $S_0$ with length 2m.

Its first half $S_0[1,m]$ is the substitution cipher of the second half $S_0[m+1,2m]$.

Then for some reason, the second half of S0 may lose some characters at the end, resulting in string $S$

Given this string S, you need to answer the smallest possible value of $m$

Hint: substitution cipher is an encryption method that replaces letters in the text with other letters. For example, if our encryption rule is $a \to c,b \to a,c \to b$ , then the string "accb" will be encrypted into "cbba". Allow the cipher and the key to be the same.

## Input

The first line is the code table for the substitution cipher, which contains 26 letters representing the encrypted letter for each letter in lexicographical order.

The second line is $S: (1 \leq |S| \leq 5×10^5)$

## Output

One integer, indicating the minimal possible length of the second half.

### Sample Input

``` log
b c d e f g h i j k l m n o p q r s t u v w x y z a
bcdeabc
```

### Sample Output

``` log
4
```

### 算法流程

1. 使用给定替换表对可见串 S 的每个字符做映射, 得到 f(S)
2. 对原串 S 构建 KMP 前缀函数, 获取每个位置的最长 border
3. 用 KMP 自动机把模式 S 在文本 f(S) 上匹配, 得到 f(S) 的后缀与 S 的最长前缀重合长度 j
4. 因为第二段最长只能是 m, 取 overlap = min(j, |S| / 2)
5. 答案等于 |S| - overlap, 即必须补齐的第二段长度

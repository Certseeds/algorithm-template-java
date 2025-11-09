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

### 算法分析

本题要求将目标串按给定置换加密后, 求最小的 $m$ 使得原串后 $m$ 位与加密串前 $m$ 位相同

核心做法是将原串与加密串拼接, 用 KMP 前缀函数求最大重叠长度, 答案即 $n-\text{overlap}$

具体实现时, 先用置换表加密, 拼接 $s+\# +\text{mapped}$, 计算前缀函数, 取最后一位的值即为最大重叠

整体复杂度 $O(n)$, 适合大数据量

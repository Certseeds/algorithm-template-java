## Description

Hong has two strings S and T, finds the longest prefix of S that is a suffix of T.

### Input

The first line will be an integer T (1 <= T <= 50), which is the number of test cases.

For each test data:

The first line contains two integers n and m (1 <= n, m <= 10^5) meaning the length of the string S and the length of the string T, respectively.

The second line contains string s of length n, which consists only of lowercase letters.

The third line contains string t of length m, which consists only of lowercase letters.

### Output

For each case, please print the length of the longest prefix of S that is a suffix of T and the corresponding prefix.

### Sample Input

```log
1
3 5
abc
bcbab
```

### Sample Output

```log
2 ab
```

## 解答

本题要求我们找到字符串 `S` 的一个最长前缀, 这个前缀同时也是字符串 `T` 的一个后缀.

这是一个经典的字符串匹配问题, 可以通过巧妙地运用 **KMP 算法的前缀函数（lps 数组）** 来高效解决.

### 算法思路

1.  **构造新字符串**: 我们将 `S` 和 `T` 通过一个不会在原字符串中出现的特殊字符（例如 `#`）连接起来, 形成一个新的字符串 `combined = S + '#' + T`.
    *   以示例输入为例: `S = "abc"`, `T = "bcbab"`, 那么 `combined` 就是 `"abc#bcbab"`.

2.  **计算 lps 数组**: 我们为这个 `combined` 字符串计算其 `lps` 数组. `lps` 数组的定义是: `lps[i]` 表示子串 `combined[0...i]` 的最长公共真前后缀（Longest Proper Prefix which is also Suffix）的长度.

3.  **获取答案**: `lps` 数组的**最后一个值**, 即 `lps[combined.length() - 1]`, 就是我们要求的答案长度.
    *   **为什么? ** 因为 `#` 是一个独特的字符, 它保证了 `combined` 字符串的任何公共前后缀都不可能跨越 `#`. 因此, `combined` 的最长公共前后缀, 必然是 `S` 的一个前缀, 并且同时是 `T` 的一个后缀. 这恰好就是题目所求.

### 示例演练

-   `S = "abc"`, `T = "bcbab"`
-   `combined = "abc#bcbab"`
-   计算 `combined` 的 `lps` 数组, 其最后一个值为 `2`.
    *   这是因为 `combined` 的前缀 `"ab"` 与其后缀 `"ab"` 相等, 是其最长的公共前后缀.
-   因此, 最长长度为 `2`. 我们从 `S` 中截取长度为 `2` 的前缀, 即 `"ab"`.
-   最终输出 `2 ab`.

这种方法将问题转化为了一个标准的 `lps` 数组计算, 算法复杂度为 O(|S| + |T|), 非常高效.

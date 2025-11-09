## Description

Give you a text S and a pattern P. You should print how many times P appears in S.

### Input

The first line will be an integer T, which is the number of test cases. (1 <= T <= 10)

For each test case, the first line will be an integer n, which is the length of the text string.

Then a line contains a text string S. |S| <= 1000000

The third line will be an integer m, which is the length of the pattern string.

Then a line contains a pattern string P. |P| <= |S|

S and will only contain lower case English letters.

### Output

Print a number in a single line for each test case, which means how many times P appears in S.

### Sample Input

```log
2
15
chenljnbwowowoo
2
wo
14
touristrealgod
7
tourist
```

### Sample Output

```log
3
1
```

## 解答

本题要求我们统计一个模式串 `P` 在一个文本串 `S` 中出现的次数.

考虑到文本串 `S` 的长度可能达到 1,000,000, 使用朴素的暴力匹配算法 (时间复杂度为 O(|S| * |P|)) 会因为效率过低而超时. 因此, 解决这个问题的标准方法是采用 **KMP (Knuth-Morris-Pratt) 算法**, 它的时间复杂度为线性的 O(|S| + |P|), 能够轻松应对本题的数据规模.

KMP 算法的核心思想是: 在匹配过程中发生不匹配时, 不回溯文本串 `S` 的指针, 而是利用已经匹配过的信息, 将模式串 `P` 的指针移动到一个合适的位置, 继续进行比较.

这整个过程分为两步:

### 1. 预处理模式串 `P`: 构建 `lps` 数组

`lps` (Longest Proper Prefix which is also Suffix) 数组是 KMP 算法的关键. `lps[i]` 存储的是模式串 `P` 的子串 `P[0...i]` 中, 最长的相等的前缀和后缀的长度.

- **前缀**: 不包含最后一个字符的子串.
- **后缀**: 不包含第一个字符的子串.

例如, 对于模式串 `P = "ababa"`:
- `lps[0]` = 0 (子串 "a" 没有 proper 前后缀)
- `lps[1]` = 0 (子串 "ab" 的前缀 "a" != 后缀 "b")
- `lps[2]` = 1 (子串 "aba" 的前缀 "a" == 后缀 "a")
- `lps[3]` = 2 (子串 "abab" 的前缀 "ab" == 后缀 "ab")
- `lps[4]` = 3 (子串 "ababa" 的前缀 "aba" == 后缀 "aba")

`computeLPSArray` 方法就是用来生成这个 `lps` 数组的.

### 2. 匹配过程: `kmpSearch`

在 `kmpSearch` 方法中, 我们使用两个指针 `i` (指向 `S`) 和 `j` (指向 `P`) 进行比较.
- 如果 `S[i] == P[j]`, 两个指针都向前移动.
- 如果 `j` 走到了 `P` 的末尾, 说明我们找到了一个完整的匹配. 此时, 计数器加一, 并且 `j` 指针利用 `lps` 数组跳转到 `lps[j-1]` 的位置, 继续寻找下一个可能的匹配.
- 如果 `S[i] != P[j]`, `i` 指针保持不动, `j` 指针则根据 `lps` 数组回溯到 `lps[j-1]`, 从而跳过了一些明显不可能匹配的比较.

通过这种方式, KMP 算法避免了暴力匹配中大量的重复比较, 实现了高效的字符串查找.

## Description

Hong haves two strings s and t. The length of the string s equals n, the length of the string t equals m.

The string s consists of lowercase letters and at most one wildcard character '*', while the string t consists only of lowercase letters.

The wildcard character '*' in the string s (if any) can be replaced with an arbitrary sequence (possibly void sequence) of lowercase letters.

If it is possible to replace a wildcard character '*' in s to obtain a string t, then the string t matches the pattern s.

If the given string t matches the given string s, print "YES", otherwise print "NO".

### Input

The first line will be an integer T (1 <= T <= 10), which is the number of test cases.

For each test data:

The first line contains two integers n and m (1 <= n, m <= 2 * 10^5) — the length of the string s and the length of the string t, respectively.

The second line contains string s of length n, which consists of lowercase letters and at most one wildcard character '*'.

The third line contains string t of length m, which consists only of lowercase letters.

### Output

For each test cases, print "YES" (without quotes), if you can obtain the string t from the string s.

Otherwise print "NO" (without quotes).

### Sample Input

```log
1
7 10
aba*aba
abazzzzaba
```

### Sample Output

```log
YES
```

## 解答

本题要求我们判断一个字符串 `t` 是否匹配一个可能包含单个通配符 `*` 的模式字符串 `s`. 通配符 `*` 可以代表任意长度的任意字符序列 (包括空序列).

这是一个典型的字符串匹配问题, 我们可以根据模式字符串 `s` 是否包含通配符 `*` 来分情况讨论.

### 情况一: `s` 中没有通配符 `*`

这是最简单的情况. 如果 `s` 中没有 `*`, 那么 `t` 必须与 `s` 完全相同才能匹配. 我们只需要直接比较两个字符串是否相等即可.

### 情况二: `s` 中有通配符 `*`

当 `s` 中存在 `*` 时, 我们可以将 `s` 分割成两部分:
- `prefix`: `*` 号之前的部分.
- `suffix`: `*` 号之后的部分.

例如, 如果 `s` 是 `aba*aba`, 那么 `prefix` 就是 `aba`, `suffix` 也是 `aba`.

要使 `t` 匹配 `s`, 必须同时满足以下条件:
1.  `t` 必须以 `prefix` 开头.
2.  `t` 必须以 `suffix` 结尾.
3.  `t` 的总长度必须至少是 `prefix` 和 `suffix` 长度之和. 这个条件确保了前缀和后缀在 `t` 中不会发生重叠, 中间可以由 `*` 所代表的字符序列 (哪怕是空序列) 连接.

如果 `s` 的长度 (不计 `*`)大于 `t` 的长度, 那么无论如何都不可能匹配, 这是一个可以提前判断的剪枝条件.

代码 `Main.java` 正是遵循了这种清晰的逻辑:
- 首先检查 `s` 中是否存在 `*`.
- 如果不存在, 直接进行字符串比较.
- 如果存在, 则提取前缀和后缀, 然后使用 `startsWith()` 和 `endsWith()` 方法, 并结合长度检查, 来判断是否匹配.

这种方法避免了复杂的循环和指针操作, 使得代码既高效又易于理解.

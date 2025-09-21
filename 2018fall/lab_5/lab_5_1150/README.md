## Description

It's hard for Hong to find the longest common substring of N different strings.

Hong wants you to solve this question.

### Input

The first line will be an integer T (1 <= T <= 10), which is the number of test cases.

For each test data:

The first line contains an integer N (1 <= N <= 1000) — the number of the sentences.

Each of the next N lines contains a string s, which consists of lowercase letters (no space) only. The length of each string will be at least 1 and at most 200 characters.

### Output

For each case please print the lexicographically smallest longest common substring.

If there is no such non-empty string, output the words “Hong” instead.

### Sample Input

```log
1
3
aabbaabb
abbababb
bbbbbabb
```

### Sample Output

```log
abb
```

## 解答

本题要求我们找到 N 个字符串的“最长公共子串”，并且在所有最长公共子串中，返回字典序最小的那一个。

考虑到字符串数量和长度的限制（N <= 1000, |s| <= 200），一个清晰且有效的解法是 二分答案 + 暴力验证。

### 算法思路

1.  二分答案 (Binary Search on Answer)
    我们不对子串本身进行搜索，而是对“最长公共子串的长度”进行二分查找。这个长度 `L` 的范围是 `[0, min_len]`，其中 `min_len` 是所有输入字符串中最短的那个的长度。
    - 对于二分出的每一个长度 `mid`，我们都需要一个 `check(mid)` 函数来回答：“是否存在一个长度为 `mid` 的公共子串？”
    - 如果 `check(mid)` 返回 `true`，说明长度 `mid` 是可行的，我们尝试寻找更长的公共子串，因此 `low = mid + 1`。
    - 如果 `check(mid)` 返回 `false`，说明 `mid` 太长了，我们需要缩短长度，因此 `high = mid - 1`。

2.  `check(len)` 验证函数
    这个函数负责验证是否存在一个长度为 `len` 的子串，它同时出现在所有 N 个字符串中。
    - 生成候选集：我们从第一个字符串 `s_1` 中提取出所有长度为 `len` 的子串，并将它们放入一个 `Set`（集合）中，作为初始的“候选公共子串集”。
    - 迭代求交集：遍历剩下的字符串 `s_2, s_3, ..., s_N`。对于每一个字符串 `s_i`，我们都对候选集进行筛选，只保留那些也出现在 `s_i` 中的子串。
    - 剪枝：如果在任何一步筛选后，候选集变为空，那么我们就可以确定不存在长度为 `len` 的公共子串，`check` 函数立即返回失败。
    - 成功条件：如果成功遍历完所有 N 个字符串，候选集依然不为空，那么 `check` 函数返回成功，并将最终的候选集（即所有长度为 `len` 的公共子串）返回。

3.  寻找最终答案
    - 通过二分查找，我们可以找到满足 `check(L)` 的最大长度，记为 `maxL`。
    - 如果 `maxL` 为 0，说明不存在任何非空公共子串，按题目要求输出 "Hong"。
    - 否则，我们再次调用 `check(maxL)` 来获取所有长度为 `maxL` 的公共子串。然后对这些子串进行字典序排序，并返回第一个（即最小的）作为最终答案。

`Main.java` 中的 `solve` 和 `check` 方法正是遵循了这一逻辑。虽然 `check` 函数内部的 `String.contains()` 效率不是最优，但鉴于本题的数据规模，这种清晰的实现足以通过评测。

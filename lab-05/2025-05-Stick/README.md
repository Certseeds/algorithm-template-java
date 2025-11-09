# Stick!

## Description

Lovely boys often play stick game.

This is a two-player game, which goal is finding the longest common substring of the two players' names, and the length of it is defined as the stick level.

Now, Amayama wants you help him to calculate the stick level between two boys.

## Input

The input contains two string $s_1, s_2 (1 \leq |s_1|,|s_2| \leq 100000)$, which are two lovely boys' names.

## Output

A number stands the sticking level.

### Sample Input

``` log
B.Tang
B.Tarjan
```

### Sample Output

``` log
4
```

## HINT

<https://en.wikipedia.org/wiki/Universal_hashing#Hashing_strings>

### 算法分析

本题要求求出两个字符串的最长公共子串长度

传统的动态规划方法时间复杂度为 $O(nm)$, 在本题数据范围下会超时

优化做法是利用后缀自动机

1. 先用第一个字符串 $s_1$ 构建后缀自动机, 时间和空间复杂度均为 $O(|s_1|)$
2. 用第二个字符串 $s_2$ 在自动机上匹配, 记录当前最长匹配长度, 遇到失配时跳 fail 指针, 整体匹配过程为 $O(|s_2|)$
3. 最终答案即为匹配过程中出现的最大匹配长度

整体复杂度 $O(|s_1|+|s_2|)$, 空间 $O(|s_1|)$, 可高效处理 $10^5$ 级别字符串

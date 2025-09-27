## Description

Hong likes the Rap of China. He has tried to write some lyrics. However, he didn't know how to judge a lyric.

He thought that the more rhymes the better.

The score of a lyric is equal to the length of the longest continued rhyming sentences.

Two sentences are rhyming when the last letters of them are equal.

Hong wants to know the score of the given lyrics.

### Input

The first line will be an integer T (1 <= T <= 100), which is the number of test cases.

For each test data:

The first line contains an integer N (1 <= N <= 10^4) - the number of the sentences.

Each of the next N lines contains a string s, which consists only of lowercase letters (no space). The length of each string doesn't exceed 100.

### Output

For each case please, print the length of the longest continued rhyming sentences.

### Sample Input

```log
1
5
nikanzhegemian
tayouchangyoukuan
jiuxiangzhegewan
tayoudayouyuan
skrskr
```

### Sample Output

```log
4
```

## 解答

本题的目标是计算歌词中连续押韵句子的最长长度. 根据定义, 如果两个句子的最后一个字母相同, 它们就押韵.

这是一个简单的迭代问题, 我们可以通过一次遍历来解决, 算法复杂度为 O(N), 其中 N 是句子的数量.

算法思路如下:
1.  初始化两个计数器: `maxStreak` 用于记录全局最长的连续押韵长度, `currentStreak` 用于记录当前正在计算的连续押韵长度. 如果至少有一句话, 它们的初始值都应为 1.
2.  从第二句话开始, 遍历整个句子列表.
3.  在每一步, 比较当前句子和前一个句子的最后一个字母.
    -   如果最后一个字母相同, 说明押韵仍在继续, 将 `currentStreak` 加 1.
    -   如果最后一个字母不同, 说明连续押韵中断了. 此时, 我们需要将 `currentStreak` 的值与 `maxStreak` 比较, 更新 `maxStreak` 为两者中的较大者, 然后将 `currentStreak` 重置为 1 (因为新的句子本身构成了一个长度为 1 的新序列).
4.  遍历结束后, 不要忘记最后再用 `currentStreak` 更新一次 `maxStreak`, 以处理最长的押韵序列恰好在歌词末尾结束的情况.
5.  最终得到的 `maxStreak` 就是答案.

例如, 对于示例输入:
- `...mian`
- `...kuan` (押韵, `currentStreak` = 2)
- `...gewan` (押韵, `currentStreak` = 3)
- `...yuan` (押韵, `currentStreak` = 4)
- `...skr` (不押韵, `maxStreak` 更新为 4, `currentStreak` 重置为 1)

最终结果为 4.

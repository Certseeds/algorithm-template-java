# Mahhjong

## Description

Please determine whether one can win with the 14 tiles in her hands or not.

In Mahhjong, there are 34 kinds of tiles, which are divided into four suites, named as bing, suo, wan, and zi.

The bing, suo, wan have 9 kinds for each suite and zi tiles has only 7 kinds.

To simplify the problem, each tile is represented with a number and a suite, for example 1b, 2s, 7w, 3z.

The rules in Mahhjong are similar with those in Mahjong, except that in Mahhjong there is an infinite number of each kind, while in Mahjong one kind usually contains up to 4 tiles.

Also, we consider a combination of tiles winning if and only if the combination consists of four kezi or shunzi and an additional quetou.

If you are not familiar with Mahjong, here is a brief explanation:


For example, {1s, 1s, 1s}, {3z, 3z, 3z} are kezi, but {1s, 2s, 1s} is not.


For example, {1s, 2s, 3s}, {6b, 7b, 8b} are shunzi, but {5z, 6z, 7z} and {3w, 4w, 5s} are not.


For example, {7z, 7z}, {6w, 6w} are quetou, but {1b, 2b} is not.


For example, {1w, 2w, 2w, 2w, 3w, 4b, 5b, 5b, 6b, 6b, 7b, 9s, 9s, 9s} is a special combination, because we can divide the set of tiles into three shunzi, a kezi and a quetou: {{1w, 2w, 3w}, {4b, 5b, 6b}, {5b, 6b, 7b}, {9s, 9s, 9s}, {2w, 2w}}

### Input

The input contains multiple test cases.

The first line includes a single integer $T$ $(1 \leq T \leq 10000)$--- the number of test cases.

Each of the next $T$ lines indicates a test case.

It contains a string $s$ of 28 characters, describing the 14 tiles that Satori has.

For every $(1 \leq i \leq 14)$, the $i$-th tile is described by the $(2i-1)$th and $2i$-th characters in the string: the former is a digit denoting the rank of the tile in its suite and the latter is one of {w, b, s, z}, which means the suite wan, bing, suo and zi respectively.

It is guaranteed that all the $s$ in the input are valid and legal.

### Output

For each test case, output a single line.

If one reached the winning status, print "Blessing of Heaven".

Otherwise print "Bad luck" (without quotes).

### Sample Input

``` log
5
1w2w3w4b5b6b7s8s9s1b1b1z2z6z
1w2w3w4b5b6b7s8s9s1b1b2z2z6z
1w2w3w4b5b6b7s8s9s1b1b2z2z2z
1b2b3b4b5b6b2s4s5s5s5s6s7s8s
1b1b1b2b3b4b5b6b7b8b9b9b9b1s
```

### Sample Output

``` log
Bad luck
Bad luck
Blessing of Heaven
Bad luck
Bad luck
```

### 算法分析

本题要求判断给定的 14 张麻将牌是否能组成“和牌”状态，即由 4 个刻子或顺子和 1 个雀头组成。

主要实现思路：

1. 输入解析：将每组 14 张牌转换为长度为 34 的计数数组，索引分别对应 34 种牌（万、饼、索、字），便于统计与操作。
2. 枚举雀头：遍历 34 种牌，若某种牌的数量 >= 2，则尝试把它作为雀头（数量减 2），剩余牌需要分成 4 组。若任一枚举成功则为和牌。
3. 递归分组（回溯）：对剩余的牌，尝试从小到大寻找可用的组（刻子或顺子）：
    - 刻子：当前牌数量 >= 3，减 3 后递归；
    - 顺子：仅对万/饼/索（非字牌）有效，若当前及后两张同花色的数量都 > 0，则三张各减 1 后递归。
    每次尝试后都要回溯恢复计数，直至分完 4 组或者所有尝试失败。
4. 终止条件：当需要分的组数为 0 时，检查计数数组是否全部为 0；若是则分组成功。

该方法枚举雀头并用递归回溯分组，覆盖了所有可能的分法。由于输入固定为 14 张牌，状态空间受限，算法在实际测试范围内性能良好。

# Majsoul

## Description

Mahjong, one of the most famous games in China, has aroused lanran's interest.

However, it is a little bit complex for a new bee and he needs your help.

To sort 13 Mahjong tiles in order, so that he can make decision much easier.

Here is the rule of sorting Mahjong tiles:

1. Mahjong tiles can be roughly divided into four types, '万', '筒', '条', '字'.

For the first three types, each type has 9 different numbers('1' to '9'), usually noted by '1万', '2万', '1条', '2条'.

If you still get puzzled, just imagine that we express the cards in 'UNO' by a number and a Chinese character rather than a colour.

For the last type, there are only 7 kinds of tiles, '东', '南', '西', '北', '白', '发', '中'.

2. '万' noted by 'Wx' (x is an integer between '1' to '9'), for example (W1,W2,...).

Similarly, '筒' by 'Tx', '条' by 'Yx'.

We name those 7 tiles of the last type by 'E','S','W','N','B','F','Z' correspondingly.

3. Here is the priority of Mahjong tiles: Wx>Tx>Yx>E>S>W>N>B>F>Z.

For the same type: W1>W2>W3>W4>W5>W6>W7>W8>W9.

Notice that: For each kind of tile, there are totally four duplicate ones.

### Input

The first line of input is the number of test cases $T$ $(1\leq T\leq 200)$.

For each test case, there are 13 strings in one line, showing the Mahjong tiles you have.

### Output

For each test case, output the ordered Mahjong tiles in one line.

### Sample Input

``` log
2
W1 S S N E N E N W E W W S
T1 T2 T3 T5 T8 T9 T6 T4 T7 T9 T9 T1 T1
```

### Sample Output

``` log
W1 E E E S S S W W W N N N  
T1 T1 T1 T2 T3 T4 T5 T6 T7 T8 T9 T9 T9
```

### 思路总结

1. 题目要求对每组输入的 13 张麻将牌进行排序，输出排序后的结果。排序规则为：万（W1~W9）> 筒（T1~T9）> 条（Y1~Y9）> 字牌（E, S, W, N, B, F, Z），同类型内部按数字升序，字牌按题目指定顺序。

2. 输入格式为：第一行为用例数 T，后续每行为 13 个牌字符串，空格分隔。输出格式为：每组输出排序后的 13 张牌，空格分隔。

3. 实现方法：读入所有牌，统计每种牌出现次数。按题目指定顺序（万、筒、条、字牌）遍历所有可能的牌，每种牌出现几次就输出几次，

4. 保证重复牌聚集且顺序正确。实现过程中遵守读-处理-输出分离，使用快读类，所有断言均加括号，输出每行末尾都换行。

5. 排序不是简单比较，而是按照固定顺序输出所有牌，重复的牌连续输出。字牌顺序必须严格为 E > S > W > N > B > F > Z。代码结构清晰，易于维护和扩展。

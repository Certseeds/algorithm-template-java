## Description

Ancient Spider is a very popular card game, and Vince loves to play it. Today he wants to play Ancient Spider again, but he changes the rule to make it more exciting. At the beginning of the game, Vince has an empty slot on the table. There are n different cards numbered from 1 to n, and Vince will receive them one by one in a given order and put the cards onto the top of the slot. At any time, Vince can pick up a card from the top of slot and discard it. If Vince discards all n cards, the game is over. Vince wants you to help him find the smallest lexicographical order among all possible discarding orders at the end of the game.
If you don't know the concept of lexicographical order, you can see the reference in the following link: https://en.wikipedia.org/wiki/Lexicographical_order
### Input
The first line is an integer T, which is the number of test cases.
Each test case contains two lines. The first line has an integer, n.
The second line contains a sequence A of length n, which is a permutation of 1 to n, representing the order Vince receives the cards.
(1<=T<=5, 1<=n<=300000)
### Output

For each test case, print n integers in a line, which is the order discarding the card with the smallest lexicographical order.
### Sample Input

```log
2
3
1 3 2
4
3 4 1 2
```

### Sample Output

```log
1 2 3
1 2 4 3
```

## 解答

> 未经 OJ 验证

本题的目标是找到一个卡牌游戏中的最小字典序弃牌顺序. 游戏规则如下: 按给定顺序发牌, 玩家可以将牌放到一个牌堆（槽）的顶部, 也可以随时从牌堆顶部取出一张牌丢弃.

为了获得字典序最小的弃牌序列, 我们应该遵循一个贪心策略: **一旦当前可以弃置的牌是接下来期望弃置的最小的牌, 就立即弃置它**.

具体算法如下: 
1. 我们维护一个期望弃置的牌的编号 `nextExpectedCard`, 初始值为 1.
2. 我们使用一个栈来模拟牌堆.
3. 依次处理发到手中的每张牌 `card`: 
    a. 将 `card` 压入栈中.
    b. 循环检查栈顶的牌: 只要栈不为空且栈顶的牌等于 `nextExpectedCard`, 就说明我们可以弃置这张牌以满足最小字典序. 我们立即将其从栈中弹出, 记录到输出序列中, 并将 `nextExpectedCard` 加一.
4. 当所有牌都发完后, 栈中可能还剩下一些牌. 此时, 为了完成游戏, 我们必须将它们按从栈顶到栈底的顺序依次弃置.

这个策略保证了我们总是尽早地弃置当前可能弃置的最小编号的牌, 从而确保最终得到的弃牌序列的字典序是最小的.

例如, 对于输入序列 `3 4 1 2`: 
1. 收到 `3`, 入栈. 栈: `[3]`.
2. 收到 `4`, 入栈. 栈: `[3, 4]`.
3. 收到 `1`, 入栈. 栈: `[3, 4, 1]`. 此时栈顶是 `1`, 等于 `nextExpectedCard`, 所以弹出 `1`. 输出: `1`. `nextExpectedCard` 变为 `2`. 栈: `[3, 4]`.
4. 收到 `2`, 入栈. 栈: `[3, 4, 2]`. 此时栈顶是 `2`, 等于 `nextExpectedCard`, 所以弹出 `2`. 输出: `1 2`. `nextExpectedCard` 变为 `3`. 栈: `[3, 4]`.
5. 检查栈顶, 是 `4`, 不等于 `nextExpectedCard` (3), 不做操作.
6. 所有牌处理完毕. 将栈中剩余的牌 `4` 和 `3` 依次弹出. 输出: `1 2 4 3`.

最终得到的最小字典序序列为 `1 2 4 3`.


## Description

Ancient Spider is a very popular card game, and Vince loves to play it.

Today he wants to play Ancient Spider again, but he changes the rule to make it more exciting.

At the beginning of the game, Vince has an empty slot on the table.


There are $n$ different cards numbered from $1$ to $n$, and Vince will receive them one by one in a given order and put the cards onto the top of the slot.

At any time, Vince can pick up a card from the top of slot and discard it.

If Vince discards all $n$ cards, the game is over.

Vince wants you to help him find the smallest lexicographical order among all possible discarding orders at the end of the game.

If you don't know the concept of lexicographical order, you can see the reference in the following link: <https://en.wikipedia.org/wiki/Lexicographical_order>

## Input

The first line is an integer $T$, which is the number of test cases. Each test case contains two lines.

The first line has an integer $n$.

The second line contains a sequence $A$ of length $n$, which is a permutation of $1$ to $n$, representing the order Vince receives the cards. $(1\leq T\leq 5,\ 1\leq n\leq 300000)$

## Output

For each test case, print $n$ integers in a line, which is the order discarding the card with the smallest lexicographical order.

## Sample Input

``` log
2
3
1 3 2
4
3 4 1 2
```

## Sample Output

``` log
1 2 3
1 2 4 3
```

### 算法分析

本题通过维护一个栈和后缀最小值数组, 保证每次弹栈顺序字典序最小.

每次收到一张牌入栈, 若栈顶小于等于后面未处理牌的最小值, 则弹出栈顶.

依次处理所有牌, 最后输出弹栈顺序即为最小字典序.

时间复杂度 $O(n)$, 空间复杂度 $O(n)$.

# lab_example_1168

> problem_id: 1168

## Description

Give you a chessboard of $n$ row and $m$ columns.

Alice and Bob want to play a game with the following rules:

1) Alice moves first.

And they take turns to move.

Alice uses black piece and Bob uses white piece.

2) For each move, the player must chose an empty grid $(x_0, y_0)$ and occupy all grids $(x, y)$ which satisfy $x \geq x_0$ and $y \geq y_0$.

3) The player who occupies the grid $(1, 1)$ will lose.

Alice and Bob are both clever enough.

Please write a program to predict the winner of the game.

### Input

The first line of input is the number of test cases $T$ $(1 \leq T \leq 100)$.
For each test case, the first line will be two integers $n, m$ $(1 \leq n, m \leq 1000)$.

### Output

For each test case, print the name of the winner. `(Alice / Bob)`

### Sample Input

``` log
2
1 1
1 2
```

### Sample Output

``` log
Bob
Alice
```

## 分析

这种游戏有必胜策略, 所以简单判断一下开始的特殊场景, 推演一下, 拿到必胜策略之后就是一边倒.

+ <https://www.zhihu.com/column/p/30377770>
+ <https://www.cnblogs.com/lfri/p/9899014.html>
+ 改编自 `Chomp Game`

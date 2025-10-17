---
SPDX-License-Identifier: CC-BY-NC-SA-4.0
---

# lab_welcome_1169

> problem_id: 1169

## Description

Please write a program to print a 3D cuboid with length $a$, width $b$ and height $c$.

You can see the details in the sample input and output.

### Input

The first line of input is the number of test cases $T$ $(1 \leq T \leq 100)$.

For each test case, the first line will be three integers $a$, $b$ and $c$ $(1 \leq a, b, c \leq 30)$.

### Output

For each test case, print the cuboid as the sample output.

### Sample Input

``` log
2
1 1 1
6 2 4
```

### Sample Output

``` log
..+-+
././|
+-+.+
|.|/.
+-+..
....+-+-+-+-+-+-+
.../././././././|
..+-+-+-+-+-+-+.+
./././././././|/|
+-+-+-+-+-+-+.+.+
|.|.|.|.|.|.|/|/|
+-+-+-+-+-+-+.+.+
|.|.|.|.|.|.|/|/|
+-+-+-+-+-+-+.+.+
|.|.|.|.|.|.|/|/.
+-+-+-+-+-+-+.+..
|.|.|.|.|.|.|/...
+-+-+-+-+-+-+....
```

### 分析输出

#### examples

1 1 1

``` log
..+-+
././|
+-+.+
|.|/.
+-+..
```

2 1 1

``` log
..+-+-+
./././|
+-+-+.+
|.|.|/.
+-+-+..
```

1 2 1

``` log
....+-+
..././|
..+-+.+
././|/.
+-+.+..
|.|/...
+-+....
```

1 1 2

``` log
..+-+
././|
+-+.+
|.|/|
+-+.+
|.|/.
+-+..
```

#### 合理推理

the input integer is $a$, $b$ and $c$

let's find something from the input and output, fst we name the matrix's width and height as $w$ and $h$

+ when $a = 1$, $b = 1$, $c = 1$, output matrix is $5 \times 5$
+ when $a = 1$, $b = 1$, $c = 2$, output matrix is $5 \times 7$
+ when $a = 1$, $b = 1$, $c = 3$, output matrix is $5 \times 9$

so $\Delta c / \Delta h = 2$

+ when $a = 1$, $b = 1$, $c = 1$, output matrix is $5 \times 5$
+ when $a = 1$, $b = 2$, $c = 1$, output matrix is $7 \times 7$
+ when $a = 1$, $b = 3$, $c = 1$, output matrix is $9 \times 9$

so $\Delta b / \Delta h = 2$ and $\Delta b / \Delta w = 2$

+ when $a = 1$, $b = 1$, $c = 1$, output matrix is $5 \times 5$
+ when $a = 2$, $b = 1$, $c = 1$, output matrix is $7 \times 5$
+ when $a = 3$, $b = 1$, $c = 1$, output matrix is $9 \times 5$

so $\Delta a / \Delta w = 2$

in conclusion, $w = 2a + 2b + 1$, $h = 2b + 2c + 1$

let's find a target to ensure that, when input is $3, 5, 8$

the width should be $17$ and the height should be $27$, matches the log below

``` log
..........+-+-+-+
........././././|
........+-+-+-+.+
......././././|/|
......+-+-+-+.+.+
....././././|/|/|
....+-+-+-+.+.+.+
..././././|/|/|/|
..+-+-+-+.+.+.+.+
././././|/|/|/|/|
+-+-+-+.+.+.+.+.+
|.|.|.|/|/|/|/|/|
+-+-+-+.+.+.+.+.+
|.|.|.|/|/|/|/|/|
+-+-+-+.+.+.+.+.+
|.|.|.|/|/|/|/|/|
+-+-+-+.+.+.+.+.+
|.|.|.|/|/|/|/|/.
+-+-+-+.+.+.+.+..
|.|.|.|/|/|/|/...
+-+-+-+.+.+.+....
|.|.|.|/|/|/.....
+-+-+-+.+.+......
|.|.|.|/|/.......
+-+-+-+.+........
|.|.|.|/.........
+-+-+-+..........
```

## 之后是思考如何解决问题

如果是纯拟合的话非常麻烦, 合理的一个思路是从上到下分层, 再从左到右分层, 这很考验观察力.

我想尝试一个新一点的方式, 这个问题对比于立方图来说更要简单一些, 因为相当于是给了长宽之后, 所有的高度都相同.

假设坐标系这样建立

``` log
       |z
       |
       |
       |
       |
       |
       |
       |
       |
       |___________________y
      /
     /
    /
   /
  /
 / x
```

a对应y轴, b对应x轴, c对应z轴

我们把(x,y,z)来抽象每个方块, 表示对应坐标轴上的层数, 现在建立规则
我们取左下前的点来做映射

+ 1,2对比, 说明方块在y上前进一格单位, 表现为点右移两个字符
+ 1,3对比, 说明方块在x上前进一格单位, 表现在点左移,下移两个字符
+ 1,4对比, 说明方块在z上前进一格单位, 表现为点上移两个字符

由于之前敲定了输出的尺寸, 现在可以来敲定一下在x,y,z方向上都是第一个的方块, 会在这张尺寸的哪一个坐标

+ 1, 1, 1是第五行第一个
+ 2, 1, 1是第五行第一个
+ 1, 2, 1是第五行第三个
+ 1, 1, 2是第七行第一个

规律应该是第一个不影响, 第二个系数是2, 影响从左到右, 第三个影响系数也是二, 影响列数.
`beginx = 2 + 2 * c, beginy = -2 + 2 * b`

接下来不要把左上角和右下角的.看成是形状 ,他们是background,不需要画出来.

之后算法很好写, 记得用一个stringbuilder来防止多次print带来的超时

### thanks

+ thanks <https://blog.csdn.net/qq_39304630/article/details/104290566>
+ thanks <https://blog.csdn.net/IMDASHUAI/article/details/109730755>

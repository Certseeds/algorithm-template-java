# Help Narnal

## Description

Narnal is a huge fan of vim, so he has created a text editor called Vinux, which can only edit one line with several operations.

Each line has an invisible undeletable tail character called EOL (end of line), which will always stay at the end of the line in any circumstance.

Notice that the undeletable property of EOL means that EOL will revive immediately at the end of the line whenever it vanishes (been replaced or deleted).

He wants to covert a one-line keyboard input containing operations and digits into a one-line real input with only digits.

Only the following operations are available:

r: next single input would replace the current character;

I: move the character pointer to the head of the line;

H: left shift the current character pointer unless it is at the leftmost place;

L: right shift the current character pointer unless it is at the rightmost place;

x: delete the current character;

Otherwise, each input would insert before the current character.

### Input

First line will be a positive integer $T$, which is the number of test cases.

In each test case, the first line would be an integer $n$ for the length of the keyboard input of Vinux.

Then the following line represents the keyboard input of Vinux.

$T \leq 20, 20 \leq n \leq 10^{5}$.

The aiming real input only contains digits without blanks.

The input would always be valid (the input after r would never be an operation character).

### Output

For each case, output one line shows the real input without EOL.

### Sample Input

```log
2
25
12345HHHr9Ir000LLLLL876Ix
20
r60xxxxHx4xHH3III1I2
```

### Sample Output

```log
002945876
2134
```

### HINT

The explanation of the second testcase:

r6: 6EOL

r60: 06EOL

r60x: 0EOL

r60xx: 0EOL

r60xxx: 0EOL

r60xxxx: 0EOL

r60xxxxH: 0EOL

r60xxxxHx: EOL

r60xxxxHx4: 4EOL

r60xxxxHx4x: 4EOL

r60xxxxHx4xH: 4EOL

r60xxxxHx4xHH: 4EOL

r60xxxxHx4xHH3: 34EOL

r60xxxxHx4xHH3III: 34EOL

r60xxxxHx4xHH3III1: 134EOL

r60xxxxHx4xHH3III1I2: 2134EOL

### 算法分析

本题仅用双向链表，无其他辅助数据结构。链表节点通过指针连接，支持 $O(1)$ 插入、删除、移动。EOL为尾哨兵节点，保证链表尾部不丢失。所有操作均为指针移动或节点插入/删除，接口：

- insert(node, value): $O(1)$
- delete(node): $O(1)$
- moveLeft(node): $O(1)$
- moveRight(node): $O(1)$

整体 $O(n)$。断言保证输入长度和合法性。

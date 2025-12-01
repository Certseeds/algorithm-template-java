# Funny Fluffy Tuzi

## Description

Fluffy Funny Tuzi (FFT) has $N$ piles of carrot lining up in a line, the $i^{th}$ of which has $a_i$ carrots.

Tuzi wants as many carrots as possible.

It can magically merge two adjacent piles $a_{i}, a_{i+1}$ to produce a new pile with $(a_{i} \oplus a_{i+1}) + 1$ carrots.

Here $\oplus$ means binary xor.

Nevertheless, Tuzi does not want to think optimally.

Everytime it will pick the pile with the least carrots, and merge it with one of its adjacent pile(s) so that the new pile has the maximum possible number of carrots.

If multiple piles contain the least number of carrots, then the left-most such pile is chosen.

If for the chosen pile there are two merge choices and both choices yield a pile with the same number of carrots, the the left adjacent pile is merged.

Tuzi will keep merging until there is only one pile of carrots.

It wonders how many carrots it can eventually obtain.

### Input

The first line contains an integer $N$.

The second line contains $N$ integers $a_1, a_2, \dots, a_N$.

输入限制:

$1 \le N \le 500000$

$0 \le a_i < 2^{30}$

It is guaranteed that anytime any pile contains less than $2^{31}$ carrots.

### Output

Output a single number: the number of carrots in the final pile.

### Sample Input

``` log
5
3 6 6 7 1
```

### Sample Output

``` log
7
```

### HINT

不要使用任何与堆和BST相关的STL!

> 注意STL是给c++用的, Java的标准库叫`Java Class Library`
>
> 只要不用C++就好了(棒读)

### 思路

使用 priority queue 存储当前最小堆积, 元素按照胡萝卜数量与当前位置编号排序, 满足全局最小且左向优先的选择顺序.

链表节点维护双向指针与版本号, 每次从 priority queue 取出的节点若已失效则忽略, 否则计算与左右相邻的 `(val xor neighbor) + 1` 并挑选更大值, 相等时按题意选择左侧.

选择左邻时沿用左节点并更新它的数值与版本, 断开当前节点; 选择右邻时保留当前节点并移除右节点, 双向指针保证每个节点代表连续区间且编号即区间最左端, 因而 tie break 始终正确.

总共执行 `N - 1` 次合并, priority queue 中操作为对数复杂度, 因此时间复杂度为 `O(N log N)`, 额外空间为 `O(N)`.

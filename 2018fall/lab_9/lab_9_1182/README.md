## Description

There are N people playing werewolf. They can be divided into two parts:

- villagers: Always tell the truth.
- werewolf: Sometimes tell the truth.

Each person will tell one sentence: player x is villager/werewolf.

Given the record of the game, write a program to calculate how many people are always werewolf.

### Input

The first line of the input is an integer T (1 <= T <= 10). T is the number of test cases.

For each test case, the first line is one integer N (1 <= N <= 100000).

N is the number of people (numbered from 1 to N).

Then there will be N lines; each line contains an integer $x_i$ and a string s.

It means $player_i$ said $player_{x_i}$ is "villager" or "werewolf".

We guarantee that 1 <= $x_i$ <= N.

### Output

For each test case, print how many people are always werewolf.

### Sample Input

```log
1
2
2 werewolf
1 werewolf
```

### Sample Output

```log
0
```

## 实现原理

思路说明（O(N)）:

``` log
令 V_i 表示玩家 i 是否是村民（真/假）。

约束为：若 V_i 为真，则 V_{x_i} 必须等于玩家 i 的陈述（村民/狼人）。若 V_i 为假则无约束。

只沿着“陈述为村民”的边（b[i]=1）传播，形成每个起点的唯一链：要么进入只含 1 边的环，要么在第一个 0 边处终止。

若进入 1 环，则起点可以为村民（无冲突）。

若在某个 0 边节点 u 终止，检查 x[u] 是否出现在从起点到 u 的链上（包含 u）。若出现则冲突（起点必为狼人），否则起点可以为村民。

使用一次栈式遍历与状态标记（0/1/2）并记忆每个已处理节点的“终点 u”以及是否包含 x[u]，在遇到已处理后继时仅按需线性扫描当前栈前缀，整体每个节点仅进栈出栈一次，总复杂度 O(N)。
```


当前实现使用一个显式栈模拟 DFS，结合三个辅助数组，在线性时间内完成角色可行性分析：

1. 核心数据结构

```java
byte[] state = new byte[n];    // 0=未访问，1=访问中，2=已处理
boolean[] can = new boolean[n]; // 节点是否可能是村民
int[] terminal = new int[n];    // -1 表示纯村民环，其他值为首个狼人声明节点索引
int[] next = new int[n];        // 出边目标
boolean[] isV = new boolean[n]; // true=声明对方是村民
``` 

2. 手动 DFS 遍历

从每个未访问节点 `v` 开始，将 `v` 入栈并标记 `state[v]=1`：

```java
while (true) {
    stack[top++] = v;
    state[v] = 1;
    if (isV[v]) {
        int nx = next[v];
        // 若 nx 未访问，则继续；
        // 若 nx 正在访问，则形成纯村民环路，弹出环路内所有节点，can=true，terminal=-1；
        // 若 nx 已处理，则继承其 can 和 terminal 并统一出栈赋值；
    } else {
        // 遇到狼人声明，立即弹栈，将路径上受影响的节点 can=false，terminal 设置为当前节点，state=2；
        break;
    }
    v = nx;
}
```  

3. 统计结果

遍历所有 `0..n-1` 节点，累加 `can[i]==false` 的节点数，即“永远是狼人”的人数：

```java
int alwaysWerewolf = 0;
for (int i = 0; i < n; i++) {
    if (!can[i]) alwaysWerewolf++;
}
out.add(alwaysWerewolf);
```

整个过程仅对每个节点执行一次压栈和出栈操作，时间复杂度 O(n)，空间复杂度 O(n)，可处理最大 N=100000 的输入规模。

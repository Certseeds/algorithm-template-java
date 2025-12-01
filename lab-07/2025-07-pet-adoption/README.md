# Pet Adoption

## Description

Lanran opened a pet adoption center.

Each pet has a characteristic value $(0<p<2^{31})$ and each adopter also has a value $q$ $(0<q<2^{31})$.

Lanran needs to provide the following services:

For a pet with characteristic value $p$ arriving, it will be adopted by a person staying in the center whose $q$ is the minimum closest to $p$ or stay in the center if there is no adopter left.

For an adopter with value $q$ arriving, he/she will choose a pet staying in the center whose $p$ is the minimum closest to $q$ or stay in the center if there is no pet left.

$a$ is the minimum closest to $v$ in set $S$ if and only if:

- for all $a_x \in S$ there is $|a-v| \le |a_x-v|$
- for all $a_i \in S$ and $|a-v|=|a_i-v|$ there is $a \le a_i$

The dissatisfaction for each adoption is defined as $|p-q|$.

### Input

The first line is a positive integer $n$ $(n \le 80000)$, which represents the total number of pets and adopters who come to the adoption center.

The next n lines describe the pets and adopters who came to the adoption center in the order of arrival.

Each line has two positive integers a, b, where a=0 for pets, a=1 for adopters, and b for character values.

### Output

A positive integer representing the sum of the dissatisfaction of all adopted adopters of pets.

### Sample Input

``` log
5
0 2
0 4
1 3
1 2
1 5
```

### Sample Output

``` log
3
```

### HINT

$|3-2| + |2-4|=3$ and the last adopter has no pets to adopt.

### 思路

核心观察: 在任意时刻, 领养中心要么只有宠物等待, 要么只有领养者等待, 不可能同时存在两种类型.

使用 Treap 维护当前等待集合, 通过状态变量区分集合中存储的是宠物还是领养者.

当新到达者与当前集合类型相同时直接插入; 类型不同时在 Treap 中查找与目标值最接近的元素, 计算差值累加到总不满意度, 然后删除该元素.

查找最接近元素时同时维护 floor 和 ceil, 比较两者与目标的差值, 差值相等时按题意选择较小值.

Treap 的插入删除查找均为期望 `O(log n)`, 总时间复杂度 `O(n log n)`.

## Description

The capacity of Hong's pocket is so small that it can only contain $M$ gifts.

Considering the diversity of his gifts, Hong would not buy two of the same kind.

Hong will visit $N$ shops one by one along the shopping street.

There is **ONLY ONE** type of gift sold in each shop. However, he has such a poor memory that he can't remember how many shops sell gift $K$.

So, he will write a number L on each gift after buying it, to indicate how many shops selling gift $K$.

In Hong's opinion, the smaller the number $L$ is, the better the gift is.

When Hong stops in a shop which sells gift $K$ , there are three situations he might come across.

1. If there is no gift $K$ in his pocket and he still has some place for it, he will buy it without hesitation.

Before putting it into the pocket, he will write down the number '1' on the gift to indicate that he has only seen one shop selling it.

2. If there is a gift $K$ already in his pocket, he will just add L by one, which means that there are L+1 shops selling gift $K$

3. If there is no gift $K$ in his pocket and the pocket is full, he would consider that there is no shop selling gift $K$ (because he cannot remember whether he has met gift $K$), so he will have to discard one gift in his pocket to release a place for the gift $K$
But it will refer to the following rules to determine which gifts to be discarded:

He chooses the gift that has the biggest number L on it.

If several gifts have the same biggest number L, he will discard the one which has been put into the pocket at the earliest time.

After discarding the gift, he will put gift $K$ into his pocket and write number '1' on gift $K$

Now, your task is to write a program to record the number of these gifts which have been discarded by Hong.

### Input

The first line will be an integer T(1≤T≤10) , which is the number of test cases.

For each test data:

The first line has two positive integers $M$

and $N$ ($M$≤50000,$N$≤100000) where $M$ (the capacity of pocket) shows how many gifts it can take, and $N$ is the number of shops in the street. The second line has $N$ positive integers $K$i($K$i<220,i=1,2,⋯,$N$)

indicating the type of gift sold in the i-th shop.

### Output

For each test case you should output one integer, the number of discarded gifts as indicated in the sample output.

### Sample Input

```log
6
3 5
1 2 3 2 4
2 4
1 2 2 1
2 6
1 2 2 1 1024 1
2 10
1 2 3 2 4 2 3 6 7 8
2 1
1048575
6 16
10 1 2 3 4 5 6 1 2 3 6 5 4 10 1 6
```

### Sample Output

```log
1
0
2
7
0
3
```

## 解法

### 算法思路

- 总体框架
  - 遵循读-处理-输出分离: `reader()` 负责解析输入并构建测试用例数据, `cal()` 负责模拟购物过程并计算被丢弃礼物的数量, `output()` 负责最终打印结果.

- 数据结构
  - 使用 `HashMap<Integer, Entry>` 保存当前口袋中每种礼物的信息（包含礼物 id、标签 L、入袋时间 time）.
  - 使用 `TreeSet<Entry>` 维护口袋中条目的排序, 以便快速找到要丢弃的礼物. 比较器按以下优先级排序: 
    1) L 值较大者优先（即 L 从大到小）;
    2) 若 L 相同, 则按入袋时间较早者优先被丢弃（time 从小到大）;
    3) 若仍相同, 则按 id 作为稳定性保证比较.
  - 采用先从 `TreeSet` 中 remove 再修改 Entry 再 add 的方式更新条目, 保证集合一致性.

- 模拟规则
  - 遍历商店序列, 对每个礼物 K 执行: 
    - 若 K 已在口袋中, 先从 `TreeSet` 删除对应 Entry, 将 L++, 再重新加入 `TreeSet`.
    - 若 K 不在口袋中且口袋未满, 直接创建 Entry(L=1, time=当前计时器) 并加入 `HashMap` 与 `TreeSet`, 计时器自增.
    - 若 K 不在口袋中且口袋已满, 先从 `TreeSet` 中取出第一个元素（根据比较器为应被丢弃的礼物）, 从 `HashMap` 中移除并将丢弃计数加一, 然后插入新礼物的 Entry(L=1, time=计时器), 再将计时器自增.

- 特殊与边界情况
  - 当 M == 0 时, 按实现当前语义不存放任何礼物, 丢弃计数返回 0. 如需按题目另一种解释调整行为, 可修改该分支逻辑.
  - 输入约束在 `reader()` 中通过 `assert` 检查, 例如 `assert ((1 <= N) && (N <= 100000));`, 在开发/测试阶段可早期发现不合法输入.

- 复杂度分析
  - 每步插入/删除/更新 `TreeSet` 和 `HashMap` 的复杂度为 O(log M) 或 O(1), 总体时间复杂度为 O(N log M)（N 为商店数, M 为口袋容量）.
  - 空间复杂度为 O(M) 额外内存用于口袋管理.

- 设计原则
  - 避免递归和全局可变混乱, 采用局部封装的 `Entry` 对象和明确的集合操作步骤, 保证数据结构一致性与可测试性.

实现细节请参见 `src/Main.java` 中的 `reader`, `cal`, `output` 具体实现.

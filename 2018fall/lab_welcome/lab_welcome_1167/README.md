## Description

> problem_id: 1167

Tower of Hanoi is one of the most famous puzzles in the world.

There are three different rods labeled A, B and C.

The puzzle starts with the disks in ascending order of size on the A rod (The smallest at the top).

You have the following rules:

1. Only one disk can be moved at a time.

2. Each move consists of taking the upper disk from one of the rods and placing it on top of another rod.

3. No disk can be placed on top of a smaller disk.

Since the problem is too popular that you must know how to solve it, we add several constrains to this problem:

4. You cannot move disk between the A rod and the C rod directly.

5. You need to move n disks from the A rod to the C rod.

Please write a program to print the minimum number of moves you need to do.

Since the result is too large, please print the answer modular 1000000007.

If you do not know the modular operation, here is the link: <https://en.wikipedia.org/wiki/Modular_arithmetic>

### Input

The first line of input is the number of test cases T $(1 \leq T \leq 100)$.
For each test case, the first line will be an integer n $(1 \leq n \leq 1000)$.
Means there are n disks on the A rod initially.

### Output

For each test case, print the minimum number of moves you need to do when there are n disks on the A rod initially.

### Sample Input

``` log
2 1 2
```

### Sample Output

``` log
2 8
```

### HINT

利用数学归纳法, 从N=1递推到N=4就能看出结构来

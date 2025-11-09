## Description

Vince is curious about how many swap times does Bubble Sort need to sort an array.

He implements Bubble Sort Algorithm within 0.000001 second and gets the answer.

Now, he wants to find a more efficient way to calculate.

Could you solve it?

In Bubble Sort algorithm, you can only swap adjacent elements.

## Input

The first line will be an integer T (1 ≤ T ≤10), which is the number of test cases.

For each test case, the first line will be an integer n (1 ≤ n ≤ 10 ^5 ) showing the length of array.

Then there are n integers.

It is guarenteed that each number will not exceed the limit of int.

## Output

For each case, please output the swap times that Bubble Sort needs to sort the array.

## Sample Input

``` log
1
4
4 1 2 3
```

## Sample Output

``` log
3
```

## 算法分析

本题要求计算冒泡排序所需的交换次数。实际上等价于求逆序对数。

1. 采用归并排序思想，在归并过程中统计逆序对。
2. 每当左侧元素大于右侧元素时，逆序对数加上左侧剩余元素数。
3. 对每个测试用例分别计算。

时间复杂度 $O(n \log n)$，空间复杂度 $O(n)$。

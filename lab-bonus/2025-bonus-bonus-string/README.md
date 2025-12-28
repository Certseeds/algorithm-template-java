# bonus-string problem

## Description

Cache is an important concept in Computer Organization (CS202).

The original meaning of cache is a kind of high-speed memory whose access speed is faster than general random access memory (RAM).

Generally, it does not use DRAM technology as systems main memory, but uses expensive but faster SRAM technology.

The setting of cache is one of the most important factors for all modern computer systems to achieve high performance.

A hit means the queried item is already in cache before the query reaches while a miss means the item is not in cache and after the query it will be stored in cache.

The cache have a cache size C.

When a cache store items more than C, it will adopt Least-Recently-Used (LRU) algorithm to replace item stored in cache.

Here we simplify the concept, a cache can store at most k items.

When the cache is full and a new item need to store in cache, it will replace the item that is least recently used.

QR code, full name quick response code, is a super popular coding method on mobile devices in recent years.

It can store more information than the traditional bar code barcode, and can also represent more data types.

To simplify the problem, We assume QR code is a 16*16 matrix contains only 0 and 1.

Now the question is coming.

Dateri is going to deal with n QR codes and he have a cache size k.

For each query QR code, he wants to know whether the query is hit or miss.

### Input

First line contains two integer n ( 1 < n <= 1000 ) and k ( 1 <= k <= n ).

Continue with n query, each query contains a 16*16 matrix which consist of 0 and 1.

### Output

For each query, outputs whether hit or miss.

### Sample Input

```log
6 2
1 1 0 0 1 0 0 0 0 0 1 1 1 1 1 1
1 0 1 0 1 0 0 1 0 0 1 0 0 1 1 0
1 0 1 0 1 1 1 0 1 1 0 1 1 0 1 1
1 0 1 0 0 1 1 1 1 1 1 0 0 1 0 0
0 0 0 0 0 0 0 1 0 1 0 0 0 1 1 0
1 1 0 0 0 0 0 0 1 0 0 1 0 1 1 0
0 0 1 1 1 1 1 0 0 0 1 0 1 0 1 1
0 0 0 1 1 1 1 0 0 0 1 0 1 1 1 0
1 0 0 0 1 0 0 0 1 1 1 1 1 1 1 1
1 1 1 0 1 0 0 0 0 0 1 0 0 1 0 1
0 1 0 1 0 1 1 1 0 0 1 0 0 0 0 1
0 1 0 0 1 0 1 1 0 0 0 0 1 1 0 1
0 1 1 1 0 1 1 0 1 0 1 1 0 1 1 0
0 1 0 0 0 1 1 0 1 1 1 1 1 1 0 1
0 0 0 0 0 0 0 1 1 0 1 1 0 0 0 0
0 1 0 1 0 1 1 0 0 1 0 0 0 1 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
1 1 0 0 1 0 0 0 0 0 1 1 1 1 1 1
1 0 1 0 1 0 0 1 0 0 1 0 0 1 1 0
1 0 1 0 1 1 1 0 1 1 0 1 1 0 1 1
1 0 1 0 0 1 1 1 1 1 1 0 0 1 0 0
0 0 0 0 0 0 0 1 0 1 0 0 0 1 1 0
1 1 0 0 0 0 0 0 1 0 0 1 0 1 1 0
0 0 1 1 1 1 1 0 0 0 1 0 1 0 1 1
0 0 0 1 1 1 1 0 0 0 1 0 1 1 1 0
1 0 0 0 1 0 0 0 1 1 1 1 1 1 1 1
1 1 1 0 1 0 0 0 0 0 1 0 0 1 0 1
0 1 0 1 0 1 1 1 0 0 1 0 0 0 0 1
0 1 0 0 1 0 1 1 0 0 0 0 1 1 0 1
0 1 1 1 0 1 1 0 1 0 1 1 0 1 1 0
0 1 0 0 0 1 1 0 1 1 1 1 1 1 0 1
0 0 0 0 0 0 0 1 1 0 1 1 0 0 0 0
0 1 0 1 0 1 1 0 0 1 0 0 0 1 0 0
0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0
0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0
0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0
0 1 1 0 0 0 0 0 1 0 0 0 1 0 0 1
0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0
0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 1
0 0 1 1 0 1 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0
1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 1
1 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0
0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
```

### Sample Output

```log
miss
miss
hit
miss
miss
hit
```

### Solution

每个 QR code 是 16*16 的 0/1 矩阵, 为了做缓存命中判断, 先把 256 个比特按读入顺序拼成长度为 256 的字符串作为 key.

缓存策略是 LRU, 需要支持查询, 更新最近使用顺序, 以及超过容量 k 时淘汰最久未使用元素.

Java 的 LinkedHashMap 在 accessOrder=true 时可以维护访问顺序, 并通过重写 removeEldestEntry 在 size() > k 时自动删除最旧条目.

逐个处理查询.

若 key 已存在, 输出 hit 并访问一次以更新顺序.

否则输出 miss 并插入 key, 若超过容量则自动淘汰.

时间复杂度 O(n * 256) 主要来自读入构造 key, 哈希表操作均摊 O(1).

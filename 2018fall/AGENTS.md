## 注意事项

1. 使用 JDK11 语法, 尽量使用现代数据结构, 尽量使用final var不可变变量
2. 尽量遵守读-处理-输出分离的原则
3. 尽量使用 `System.out.print('\n')` 来表示换行

## 定义操作
2. 定义对README进行的预处理

+ README.md内 `Description` 应该为 `## `, Input, Output, Sample Input, Sample Output, HINT 等均改写为 `### `
+ `Sample Input` `Sample Output`内里面的输入输出, 用 ``` log ``` 包裹
+ 注意去除/替换部分非中英文的字符

2. 定义解答流程

+ 根据题目描述, 以及输入输出文件 data.in, data.out, 按照JDK11语法, 并遵守读-处理-输出分离的原则, 重写 Main.java
+ 使用题目约束, 在 Main.java 的 reader 内部加入 assert 判断

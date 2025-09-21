## 注意事项

0. 使用 zh-CN 思考, 分析, 回答.
1. 使用 JDK11 语法, 尽量使用现代数据结构, 尽量使用final var不可变变量
2. 尽量遵守读-处理-输出分离的原则
3. 不使用任何中文标点

+ 以使用 `"` 为荣, 以使用 `“`, `”` 为耻
+ 以使用 `'` 为荣, 以使用 `‘`, `’` 为耻
+ 以使用 `,` 为荣, 以使用 `，` 为耻
+ 以使用 `.` 为荣, 以使用 `。` 为耻
+ 以使用 `:` 为荣, 以使用 `：` 为耻
+ 以使用 `;` 为荣, 以使用 `；` 为耻
+ 以使用 `!` 为荣, 以使用 `！` 为耻
+ 以使用 `?` 为荣, 以使用 `？` 为耻

> 注意给英文字符留出一个空格的空白

4. 禁止使用 `**` 加强符号

## 定义操作

1. 定义对README进行的预处理

+ README.md内 `Description` 应该为 `## `, Input, Output, Sample Input, Sample Output, HINT 等均改写为 `### `
+ `Sample Input` `Sample Output`内里面的输入输出, 用 ``` log ``` 包裹
+ 注意去除/替换部分非中英文的字符

2. 定义解答流程

+ 根据题目描述, 以及输入输出文件 data.in, data.out, 按照JDK11语法, 并遵守读-处理-输出分离的原则, 重写 Main.java
  + 使用默认的快读类
  + 读取方法 reader 使用快读类, 将读取数据抽象为类, 并传递到处理函数cal
  + 处理函数 cal 内部处理, 并将结果传递给输出函数 output
  + 输出函数 output 接受结果, 尽可能地优化输出
    + 注意不要使用 `if (i < results.size() - 1) {  System.out.print('\n'); } ` 这种方式, 最后一个也要输出换行
    + 使用 `System.out.print('\n')` 来表示换行
    + 不需要使用 `java.io.PrintWriter`
+ 使用题目约束, 在 Main.java 的 reader 内部加入 assert 判断, 并尽量对每一个 assert 判断中的 case 添加括号
  + example: `assert ((0 <= x) && (x <= 100));`

3. 执行测试的命令行操作: `mvn -pl .\2018fall\lab_{}\lab_{}_{} -am test`

4. 在 README.md 中说明思路

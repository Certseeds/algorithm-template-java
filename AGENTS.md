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

即不使用任何全角符号, 将其替换为对应的英文标点

## 本地拉取流程

人工干预: contest目录中实现README, 获取cid, 题目信息

copilot-获取题面: 使用cid, pid(按题目序号从0递增)按照题目信息(problem_id置为0), 写入子目录中, 各个题目的README中

copilot-优化README: 在README中, 根据句点`.`, 每一句话一个段落, 在不改变原始文字的情况下, 将长段落分为短段落

copilot-公式优化: 将题目格式化, 将 `\\`等符号替换为 标准Latex符号`$`, 实现符号渲染正常

copilot-样例格式化: 按照题目内Input, Output的要求, 理解Input, Output格式, 将`sample Input``sample Output`内代码块内内容进行格式化

copilot-换行优化: 将除了codeblock之外, 所有的单行换行替换为双行换行, 随后将三行更多的换行替换为双行换行.

## 题目实现流程

copilot-检查样例: 读取 README.md 中的样例输入输出, 检测与目录内 resource 目录下的 `01.data.in`, `01.data.out`是否相同, 不相同则覆盖写

copilot-生成: 读取目录中的 README.md 题面实现到 Main.java 中(显然目标语言是Java), 严格遵守 `代码撰写原则`中的所有规则, 并尽量复用Main.java中的已有实现

copilot-上传: 使用problem_id(不传入cid, pid) 生成代码后, 不需要编译, 直接调用 hustoj-mcp, 将其上传到 hustoj 中.

copilot-解释算法: 按照 README.md 结合 Main.java 的实现, 写出算法分析到 README.md 的最后(用zh-CN)

1. 定义对README进行的预处理

+ README.md内 `Description` 应该为 `## `, Input, Output, Sample Input, Sample Output, HINT 等均改写为 `### `
+ `Sample Input` `Sample Output`内里面的输入输出, 用 ``` log ``` 包裹
+ 注意去除/替换部分非中英文的字符
+ 只进行格式整理, 不对内容进行编辑

2. 定义解答流程

+ 根据题目描述, 以及输入输出文件 data.in, data.out, 按照JDK11语法, 并遵守读-处理-输出分离的原则, 重写 Main.java
  + 使用默认的快读类
  + 读取方法 reader 使用快读类, 将读取数据抽象为类, 并传递到处理函数cal
  + 处理函数 cal 内部处理, 并将结果传递给输出函数 output
  + 输出函数 output 接受结果, 尽可能地优化输出
    + 注意不要使用 `if (i < results.size() - 1) {  System.out.print('\n'); } ` 这种方式, 最后一个也要输出换行
    + 使用 `System.out.print('\n')` 来表示换行
  + 不需要使用 `java.io.PrintWriter`等方式实现快写
+ 使用题目约束, 在 Main.java 的 reader 内部加入 assert 判断, 并尽量对每一个 assert 判断中的 case 添加括号
  + example: `assert ((0 <= x) && (x <= 100));`

3. 执行测试的命令行操作: `mvn -pl .\2018fall\lab_{}\lab_{}_{} -am test`

4. 在 README.md 中说明思路

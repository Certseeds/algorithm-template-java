# algorithm-template-java

本repo的目的: 提供一个

+ 开箱即用
+ 带有测试
+ LLM 友好
+ 针对 OJ 需要单文件提交设计的
+ 使用 JDK11 语法的
+ 提供一些基本的算法框架

代码模板仓库, 供大家在 SUSTech 的各类算法课程中使用

为每一个题目分配独立的编译单元, 专有路径放置测试用例, 并提供重定向读写功能, 可以一键运行多组测试用例

## 如何使用

### 环境准备

下载最新 LTS 版本的 JDK, 推荐使用 JDK21

``` powershell
$ java -version
openjdk version "21" LTS
OpenJDK Runtime Environment Temurin-21 (build 21-LTS)
OpenJDK 64-Bit Server VM Temurin-21 (build 21-LTS, mixed mode, sharing)
```

> 或者在 IDEA 中下载

下载最新版本的 maven

``` powershell
$ mvn --version
Apache Maven 3.9.11
Maven home: ~\scoop\apps\maven\current
Java version: 21, vendor: Eclipse Adoptium, runtime: ~\scoop\apps\temurin21-jdk\current
Default locale: zh_CN, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

> 或者使用 IDEA 捆绑的 maven

#### Java 版本策略(重要)

+ 本地开发与测试: 推荐使用 JDK 21(IDEA 运行/调试更舒适)
+ 构建与提交: 项目使用 Maven 以 Java 11 为编译目标(`--release 11`), 确保与远程 OJ(JDK 11)兼容
+ 说明: 即使本地安装的是 JDK21, Maven 根据 `release=11` 仍会按照JDK11的API对源文件进行约束, 不用担心用了新的API, OJ上无法编译

### 下载使用

+ 首先请到[release_latest](https://github.com/Certseeds/algorithm-template-java/releases/latest)下载 `script_no_need.zip`
+ 将其解压, 放到您的代码库中
  + 也可以直接使用 GitHub 的 "Use this template" 或 Fork 仓库开始

### 快速上手(5 分钟)

1. 使用 IDEA 打开仓库, 等待 Maven 依赖下载完成
2. 打开 `lab_example/a/test/MainTest.java` 直接运行测试
3. 修改 `lab_example/a/src/Main.java` 的 `cal` 实现, 重新运行测试验证
4. 提交到 OJ 时, 复制该题目的 `Main.java` 全文(包含嵌入的快读类)进行提交

命令行操作:

```powershell
# 运行根项目所有测试
mvn -q test

# 仅运行某一题(例如 lab_example/a)的测试
mvn -q -pl lab_example/a -am test
```

## 实际场景

### A+B: lab_00_A , 测试样例

+ 这个问题较为简单, 见[A+B](./lab_example/a/src/Main.java)  解决起来不复杂

+ 虽然手工一个一个输入, 然后肉眼观察输出.但是如果我们希望严谨的测试, 要100组测试数据, 难道每次出新版本都要手动输入100次? </br>
显然, 有更好的解决方式: 使用**测试框架**

+ 在本repo, 使用 `Junit` </br>

比如, 我们有四组数据, 第一组, 第二组测试边界值, 第三组使用随机数测试对偶性与正确性, 第四组测试几个手动的随机值

参见[test_for_lab00_A](./lab_example/a/test/MainTest.java)

+ 这样一来, 我们只需要每次修改完主文件之后, run `lab_example/a/test/MainTest.java`, 对其进行调用, 就能验证其在所有的测试用例上的正确性.</br>
测试的结果也会出现在输出中

### 文件输入输出重定向 part1

+ 常见于tree, graph类的问题, debug需要的数据集都比较大, 不方便直接写在代码中
+ 比如[判断二分图](./lab_example/c/src/Main.java), 一张图可以有几十上百个node, 写在内部占用空间太大
+ 而在这里, 使用`Redirect`对象, 便可以省去手动输入的方式

  ``` java
  try (Redirect redirect = Redirect.from(DATA_PATH, "01.data.in", "01.test.out");) {// 设定目录 DATA_PATH在文件里有定义
      Main.output(Main.cal(Main.read())); // 执行
      final Pair<String, String> p = redirect.compare_double("01.data.out", "01.test.out"); // 获取两个文件中的字符串
      assertEquals(p.getFirst().length(), p.getSecond().length()); // 比较长度
      assertEquals(p.getFirst(), p.getSecond()); // 比较文本
  }
  ```

    只需要准备好输入的数据与结果, 就可以从文件中读取, 执行后判断结果是否符合预期
  + test_1 为最简单的逐个判断, 最简单, 代码量最大
  + test_2 则优化了一些, 但是还是比较麻烦, for循环还需要了解测试样例的个数
  + test_3 with tuple 则最优雅, 修改起来的难度最小
  + PS: 此处注意, 引用文件的相对路径
  + PS2: 模版文件中已经将前面`resources/`预置好, 只需要填写文件名

### 文本输入输出重定向 part2

+ 一般来说, 题目的输出不会太复杂, 但是反例也不是没有: 比如专门考输出的[立体图](./lab_example/d/src/Main.java)
+ 这种情况下, 使用 Java 的标准输入/输出重定向就可以较为方便地处理输入, 同时保存输出便于调试

  ``` java
  try (Redirect redirect = Redirect.from(DATA_PATH, "01.data.in", "01.test.out");) {// 设定目录 DATA_PATH在文件里有定义
      Main.main(init_String);
      final Pair<String, String> p = redirect.compare_double("01.data.out", "01.test.out");
      assertEquals(p.getFirst().length(), p.getSecond().length());
      assertEquals(p.getFirst(), p.getSecond());
  }
  ```

  这样就将标准输出重定向到了`01.test.out`中, 并与`01.data.out`比对
  + 这里需要考虑的是, 谨慎使用`println()`, 因为`println()`的输出与平台有关; 推荐使用 `System.out.print('\n')` 来对齐与 data.out 的比较

### 快读

+ 一般来说, 题目不会卡读入
+ 但是, 当数据量上来之后, 读取时间不容小看
+ 所以可以使用每个文件中自带的 Reader / FastReader 类来进行快读
+ 注意: 多数 OJ 仅允许单文件提交, 因此快读类需嵌入到 `Main.java` 中, 不能抽到外部文件依赖

## 实现细节

1. 使用两层子模块来抽象 lab-question , 给每个 question 一个独立文件夹, 有利于在 README.md 里面撰写文档, 梳理思路
2. 使用子模块来实现, 每一个类内不需要加入 `package ...`路径, 这样可以直接复制 Main.java 全文到 OJ内, 方便实现

### 为什么要将 `读取` `数据处理` `输出` 分开?

+ 便于理清思路, 读完题目之后, 不管别的, 先把数据读入, 输出的函数写好, 方便后续写作
+ 交流代码逻辑的时候不会受到无关逻辑的影响
+ 可以互相分享少量代码而不触及核心逻辑, 方便协作
+ 便于使用测试
+ 便于使用替换快读与scanner

### 为什么要选择Java做题

1. SUSTech 大一默认用 Java 教学
2. Java 对字符串处理有一些预置方法, 挺好用
3. 可以在 Windows 环境下开发, 完全不需要安装 MSVC/gcc 环境
4. 没了, 欢迎补充

> C++的模板库:[algorithm-template](https://github.com/Certseeds/algorithm-template)

## 这个模板仓库加速了什么?

本仓库的目标是把个人刷题与题目工程化、可复用化, 缩短"写-测-改-提交"的反馈回路, 具体体现在下面几点:

### 每个题目有独立目录

题目放在独立的子目录下, 目录内可以存放题目描述 (中/英)、个人翻译、思路笔记、版本迭代的代码以及特殊说明.

这样做的好处是:

+ 不再需要频繁打开网页查题目
+ 方便离线阅读与长期积累
+ 有利于把题解和注释作为学习资料分享或归档.

> 有些题目搞一大段描述, 不分段也就罢了, 题目描述还是张图片, 内部恨是吧.
>
> <https://acm.sustech.edu.cn/onlinejudge/problem.php?cid=1048&pid=0>

### 在 IDE 与命令行上可通过 JUnit 快速跑通所有用例

每个题目配套的 `Main` 类和 `test` 目录下的 JUnit 测试允许你在 IDE (如 IntelliJ) 里一句 Run 即可执行全部样例. 也可以在命令行通过 `mvn test` 或 `mvn -Dtest=... test` 快速执行, 用最短的时间得到反馈 (通过/TLE/异常等), 加快本地迭代速度.

> code-agent 对命令行执行是强依赖, 单靠复制粘贴比对输出有点太繁琐了

### 统一的测试用例管理

仓库对用例做了统一命名与分组管理: 把"预期输入" (input)、"预期输出" (expected)、以及运行时产生的"测试输出" (actual)分别归档并以规范化命名存放 (例如 `01.data.in`, `01.data.out`, `01.test.out`). 这种做法便于把高质量的用例共享与复用.

### 使用 Maven/POM 简化提交到 OJ 前的改名与构建流程

提交到许多 OJ 需要把类名或包名改为特定格式(例如 `Main`), 或者只上传单个源文件. 仓库通过 `pom.xml` 和简单的脚本约定, 能把项目打包、按需生成单文件提交版本或替换 `Main` 名称, 免去每次手动重命名/复制的繁琐操作, 从而加速"本地通过->提交->得分"的循环.

### module 隔离与长期积累(把库代码与测试分离)

通过模块/目录结构把公共库 (如自实现的数据结构、模板方法)放在 `src` 中, 把单元测试与题目驱动放在 `test` 中. 这样既保持了代码的可维护性, 也方便把好用的手写数据结构逐步积累成可复用的工具箱, 未来可以在新题中直接复用、单测或优化.

### In conclusion

总之, 该模板把"写题"和"工程化测试/复盘"结合起来, 目标是用工程化的手段把题目练习变成可重复、可追溯、可分享的学习循环, 显著提高个人刷题与总结的效率.

## 可能遇到的问题

1. 提示找不到Jar包
  请安装maven作为依赖管理的工具.然后在IDEA中`文件`-`打开`-选择`algorithm-template-java\pom.xml`-确定-作为项目打开

2. maven下载包很慢
  需要给maven 换源 (`pom.xml`中已有换源操作)

### copyleft 选择

1. 部分代码(*.java, etc) 基于AGPL-3.0-or-later协议: 限制最强的主流开源协议,
    + 从设计上只为了源码交付
    + 具体内容请看[`LICENSE_AGPL-3.0-or-later.md`](./LICENSE_AGPL_V3_0.md)

2. 所有脚本/LLM生成的代码 基于 Apache 2.0协议 [`LICENSE_APACHE`](./LICENSE_APACHE_2_0.md)

3. 所有其他文件(主要是*.md)基于CC-BY-NC-SA-4.0(或以后版本)协议
    + 相同方式共享-署名-非商业性使用的知识共享协议4.0或任何以后版本
    + 署名(BY)-使用到相应内容的其他地方, 应该加以注释, 保留来源
    + 非商业性使用(NC)-默认情况下, 只要署名, 可以在不盈利的情况下使用.(并不是指商业情况不能用, 而是需要和原作者沟通)
    + 相同方式共享(SA)-使得协议具有传染性, 只要其他内容采用了本repo的内容, 就需要在署名的同时, 保证其协议也是CC-BY-NC-SA-4.0 or later version
    + 具体内容请看[`LICENSE_CC_BY_NC_SA_V4_0.md`](./LICENSE_CC_BY_NC_SA_V4_0.md)

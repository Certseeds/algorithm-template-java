# 2025fall分支是如何工作的?

> 还是要出新题
>
> 本文采用CC BY-NC-SA 4.0协议

## 流程

最开始使用手动复制题目很低效, 于是借助hustoj的开源源代码, 结合 <https://acm.sustech.edu.cn> 的页面实现, 实现了第一版本的hustoj-mcp, 可以使用命令行拉取某一个 problem_id 的内容; 在发现原版hustoj有验证码, 但是 acm 这个没有之后, 又实现了命令行上对某一个 problem_id 提交指定语言的文件功能, 这样也就不需要手动复制了, 结合submodule下没有package的特点, 也不需要预处理.

之后使用hustoj-mcp对2018年秋的lab进行了一次测试, 比如 <https://acm.sustech.edu.cn/onlinejudge/contest.php?cid=1030>(之后均不再携带 `https://acm.sustech.edu.cn/onlinejudge/` prefix), 注意看这里每一个题目都有一个编号, 比如problem A就对应编号1166, 这实际上为这个问题同时标记了两种访问方式. 那就是 `id=1166` 以及 `cid=1030&pid=0`

我们可以看到, `problem.php?id=1166` 以及 `problem.php?cid=1030&pid=0` 的两个url, 除了标题之外, 几乎是完全相通的, 提交, 解决信息等都完全一致. 虽然`cid=1030&pid=0`的这种方式提交之后在页面上没有反馈, 不能直观的观察出结果, 但是, 只要切换到 `id=1166` 这种方式来提交, 就可以在状态页面上观察出结果(甚至可以不登陆)

随后便是 2025 年的lab跟踪, 我们这时候像往常一样, 直接去打开 `contest.php?cid=1185`, 就会发现, "***比赛尚未开始或私有，不能查看题目。***, 这个是近几年的常态, 连题目都看不到. 这下我们是不是就没有办法去分析这些题目, 去给他们评估出可行方案了?

答案是否定的, 虽然我们不能直接看到题目列表, 但是有另外一处地方泄露了题目的列表, 这就是 `contestrank.php?cid={}` 页面, 我们只需要从这个页面跳转到 `userinfo.php?user={studentId}`, 然后从 `Solved Problems List` 内部获取一个 `problemid (nums)`的列表, 随后将这个列表进行split, 移除nums, 就获取到了这位同学在2025年做过的所有题目.

只要选择排名第一的, 已经解决了所有问题的同学的list, 之后每周检查, 第一周后获取到6个, 第二周获取到12个, 对周之间的数据进行diff, 每周新增的量就是这周的题目id list.

假设我们在lab01出了之后, 第一时间通过这个逻辑获取了列表

``` log
1000
1001
1002
1003
1004
1005
```

我们能通过什么方式从id中获取到题目呢? 这就要通过 `problemset.php` 页面通过id查询提供的第二个功能了.

如果 `problem.php?id=1000`能够直接看到题目, 那自然最好; 但是即使无法打开, 也会提示

``` log
This problem is in Contest(s) below:
Contest A:CS203 & 217 {} Fall {} Lab
Contest B:CS203 {} Fall {} Lab
Contest C:CS203 {} Fall {} Lab
Contest D:CS203 {} Fall {} Lab
```

由于题目能否被观察到只和 Contest 的设置有关, 我们只需要根据年份挑选一个非私有的Contest, 点进页面的链接, 就能观察到这个 `problem.php?id=1000` 所对应的 `problem.php?cid={}&pid={}`了!

接下来只需要使用 [AGENTS.md](./AGENTS.md)内的流程, 配合一个指令依从性较高的ai, 即可完成拉取信息-提供用例-完成题目-提供分析的全流程.

However, 到这里我们只是在本地拿到了文本, 并根据少数用例获取了一份实现, 还没提交测试呢.

对于一般的问题来说, 直接点进 `submitpage.php?id=1000` 就可以提交了, 但是由于题目被私有contest包括, 无法直接打开它的公有 submitpage, 私有的submitpage 又因为 `problem.php?cid={}&pid={}`的方式打不开, 也没办法直接打开, 怎么提交呢?

如果要在页面上操作, 只需要将普通的 `submitpage.php?id={}` 内的 id 置换为对应的id即可, 虽然submit打不开, 但是 submitpage 没加check, 仍然能打开, 并且提交完全不受限制.

由于submitpage是存在的, 所以之前使用的命令行提交方式依然可行, ai现在可以完成拉取题目, 分析用例, 完成题目, 提供分析, 提交题目的全流程了.

### serverless 服务

在有了可以通过测试的代码之后, 就可以利用vercel的serverless服务, 在隐藏代码的情况下, 将输入在serverless上的binary上apply, 输出结果了!

现在 <https://github.com/Certseeds/strees-testing-online> 已经开源, 利用cloudflare R2存储 grallVM 的AOT产物, 在 vercel serverless内部运行.

fork仓库后修改 R2桶, vercel服务, 这样通过了测试的代码, 就可以转换为提供对拍的服务!

## 如何封禁这种场景

1. 把外网访问禁掉
2. 关闭 contestrank 的公开访问, 并ban掉 userinfo 界面的遍历以及 'Solved Problems List', 不然仍然可以对某一年的所有 `125xxxxx`进行遍历来拿到做完题目最多的人的list
3. 对 problemset 的 id 查询返回做权限校验, 避免通过私有题目的 id 泄露所属 contest
4. submitpage.php 加上 contest 级别的私有校验, 防止绕过 problem.php 的可见性直接提交

或者每次都出新的题目, 不要复用公开的contest里面的旧题目, 不需要修改就能解决该问题.

## 为什么要做 2025fall 分支?

首先是要为这个仓库做一个展示, 使用它可以高效的解决问题; 其次是为了消耗没用完的github copilot额度.

其次是提供一整套可读性好的答案, 有一些 github 上的答案是运行的很快, 能解决问题, 但是可读性比较差, 算法, 数据结构耦合在一起. 使用ai就可以将代码拆分为一个特定的数据结构与对应的使用算法, 对于理解非常有帮助.

综合起来可能每一个lab会消耗一个小时左右的时间.

这表明, 单人可以在有限的时间内, 在AI工具的帮助下, 为一个完整的课程的lab提供完整的辅助, 从框架搭建到答案提供, 再到对拍工具等等.

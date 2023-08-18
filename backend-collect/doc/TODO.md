1. 提交报告的接口 是 旧版本的report
2. 不fork任何一个缺陷，后端需要将其当做树的根节点
3. 推荐怎么展示？
    user 和 task 中 添加标签组？
4. flaw的第三个接口 有什么用？
	，是获取当前节点开始的所有子节点？还是获取整个树？（FlawTreeNodeVO）----应该是整个树的根节点
	FlawTreeNodeVO 的 children 是指直接子节点还是所有子节点？ ----直接子节点
   需不需要从某个节点可以知道整棵树？
5. 缺陷图节点的x y是什么意思？flaw属性为什么是FlawVO？不应该是FlawTreeVO?
6. tag接口    获取所有tag ，多余？因为tag不会做任何变动？还说给管理员加上增删改查的权限？



修改迭代一：

- [x] user 和 task 都增加了tag组，所以注册和发布任务的接口要改
- [x] 任务状态，state添加4，表示待完善，指缺陷没有被完全处理 => 所有用到 TaskVO 的地方都关注一下
- [ ] 任务广场返回的任务是推荐过后的任务
- [x] ReportVO 中添加 score 属性 => 所有用到 ReportVO 的地方都关注一下
- [x] ReportVO 中的flaws属性相关的FlawVO做了改动
- [x] flaw 的属性改动很大（新增 score，state，path，scoreNum，taskId），关于 FlawVO 的地方都需要修改
- [x] flaw 新增了taskId，插入flaw的时候要查找flaw对应的taskId
- [x] 新增了 FlawDTO，所以 ReportDTO 也要改。注意 FlawVO 和 FlawDTO 的地方。
- [x] 关于 ReportViewVO，新增 score 和 flawNum
- [x] TaskVIewVO 和 TaskVO 和 UserVO 新增 tagGroups
- [x] 同一个任务，一个用户只能提交一次报告
- [x] 预览任务中的报告列表，判断该user是否能调此接口，如果是发包方，则检查有没有发布此任务；如果是众包工人，则判断有无提交过报告
- [ ] 所有出问题的测试
- [x] 预览自己所有的报告，不包括待处理的
- [x] flawVO 新增 appendContent


关于mapper和po：

	report 表 添加score ----不用了  
	
	flaw 表  添加score，state，path，scoreNum, appendContent

新增表：
	user_tag
	task_tag
	
	rule
	rule_factor
	
	关于树节点：
		flawId不需要？数据库记录路径
		treemap如果是展示的fork次数最多的，那还要记录fork次数？或者通过查询得到fork次数？
		关于相似列表，是否需要存数据库
	
	flaw_evaluation



注意：

1. 报告的评分，只计算被评分过的缺陷的平均值，flaw 的 score 为 -1 不考虑，但算作缺陷个数。如果缺陷都没有被评分，则报告评分返回-1。
2. 如果不fork任何一个缺陷，后端需要将该缺陷当做树的根节点
3. 完善缺陷，更新flaw数据库，记得更改flawId，并进行树节点相关操作（待定），和相似度
4. 获取报告中待修改的缺陷列表时，设定阈值或判断长度。注意如果没有相似缺陷，则不返回。但是不要忘了，没有相似缺陷的缺陷要当做一个树的根节点。存相似度。
5. 返回的treemap的flawIds，是每棵树中被fork次数最多的flawId，他们之前的相似度也是用这个节点算
6. 相似度列表按从高到低排序
7. 对缺陷评分时，要在flaw表中更新评分人数，并重新计算一下flaw的score，并在score表中插入数据
8. 插入flaw的时候，获取taskId
9. 对缺陷修改和增加补充内容时要判断：该缺陷是否已经是已处理的状态
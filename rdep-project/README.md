# Project
- 项目
- 模块
- 缺陷
- 需求
- 任务

## 配置
### application.yml
- mysql.host、zk、redis等连接地址允许通过外部参数传入，为了配合docker使用。同时使用默认值，方便开发直接启动项目
- 配置spring.profile，启动时添加参数--spring.profiles.active=dev等开启不同的配置
- spring.message，配置多个source，从rdep-common包获取公共的错误信息
- 使用p6spy替换mysql driven，监控/打印原始SQL，用于调试。spy.properties配置文件说明：https://www.jianshu.com/p/5a32434d43eb

### WebConfig.java
配置Validator和DateFormatter

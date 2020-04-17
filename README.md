# RDEP
research and development efficiency platform
研发效能平台

## Docker-Compose 快速启动
目的：快速启动gateway、user、project、middleware四个应用，方便前端开发对接。

docker-compose.yml。同时启动user、project、gateway、middleware四个docker容器，方便前端开发对接。

RDEP依赖redis、zookeeper和数据库，但这三个运行在宿主机上，这就需要容器能够访问到宿主机。
- 修改user、project和gateway应用中连接redis、zookeeper等URL，写死宿主机的IP地址。缺点是可能每次启动容器前需要修改三个应用的配置，因为宿主机的IP可能会变
- 修改容器的网络模式，改成host，与宿主机公用网络，需要注意端口不要与宿主机冲突。Mac与Windows不支持，Linux支持host模式，解决方案是"host.docker.internal".
所以采用第二种方案。需要改造应用的配置，application.yml中依赖的中间件地址需要改成占位符，为了减少对正常开发影响，占位符提供默认值。
比如zk的URL从localhost:2181改成${zk:localhost}:2181，在Dockerfile中，启动应用时传参数--zk=host.docker.internal

使用方法
在项目根目录执行：docker-compose up，将启动所有所需的容器，添加-d参数以后台方式运行容器。docker-compose down关闭容器并删除容器和网络
## 测试
### DB测试
#### 嵌入式数据库
Spring Boot支持H2、HSQL、Derby。三个怎么选，哪个与MySQL兼容性最好？
#### 配置
`@DataJpaTest`配合`@RunWith`注解使用（Junit5的话不需要）。注册`@Entity`、`TestEntityManager`、`JdbcTemplate`
classpath加入h2依赖，自动配置嵌入式数据库，hibernate会自动对表create-drop（根据Entity）


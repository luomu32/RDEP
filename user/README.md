# RDEP-USER
用户模块

## 测试
xyz.luomu32.rdep.user.test.web包，测试Controller层。
`@WebMvcTest`配合`@MockBean`
xyz.luom32.rdep.user.test.integration，集成测试
`@SpringBootTest`配合`@AutoConfiguredMockMvc`，同时自动扫描config包中的`@TestConfiguration`，对Db配置H2而不是真实的数据库，Zookeeper、Kafka等中间件采用模拟，或嵌入式版本。

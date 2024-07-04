## Ones工具平台
#### 公共模块

---

1. bom配置模块
- 核心版本配置
2. core核心模块
- OS框架统一响应信息主体类
- OS自定义异常类
3. http模块（扩展Web模块+redis模块）
- 自定义限流注解
- 自定义重复提交注解
- HttpWebConfig
- 限流异常、全局异常处理
- 限流拦截器
- 引入retrofit-spring-boot-starter实现，httpclient等功能
4. log模块
- 自定义log注解
- DB初始化
5. mybatis模块
- 自定义ID生成器
- 自定义填充公共字段
6. redis模块
- Redis工具类 
7. task定时任务模块
- 定时任务工具类
- 引入SpringScheduleAdmin
8. web模块
- 自定义token验证注解
- 自定义脱敏注解
- 自定义应答加密注解
- WebConfig
- 全局异常处理
- token验证截器

---
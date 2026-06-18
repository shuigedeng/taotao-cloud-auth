# 代码风格规范 — taotao-cloud-auth

## 格式化规则
- 缩进: 4 个空格（不使用 Tab）
- 行宽: 150 字符
- 编码: UTF-8
- 大括号: K&R 风格（左括号不换行）

## 命名规范
- 包名: `com.taotao.cloud.auth.{module}`（全小写）
- 类名: PascalCase（如 `OAuth2ApplicationController`, `DefaultSecurityConfiguration`）
- 方法名: 小驼峰（`findByClientId`, `saveOAuth2Application`）
- 常量: UPPER_SNAKE_CASE（如 `AUTHORIZATION_CODE`, `GRANT_TYPE_PASSWORD`）
- Controller 后缀: `Controller`（如 `LoginController`）
- Service 后缀: `Service`（如 `OAuth2ApplicationService`）
- Repository 后缀: `Repository`（如 `OAuth2ApplicationRepository`）
- Entity 前缀: `OAuth2`（如 `OAuth2Application`, `OAuth2Scope`）
- DTO 后缀: `DTO` 或 `Query`/`Command`（如 `OAuth2ApplicationDTO`, `SessionCreate`）

## 导入顺序
1. Java 标准库 (`java.*`, `javax.*`)
2. Jakarta (`jakarta.*`)
3. Spring 框架 (`org.springframework.*`, `org.springframework.security.*`)
4. 第三方库 (`org.*`, `com.*`)
5. 项目内部包 (`com.taotao.cloud.auth.*`)
6. 静态导入

## Lombok 使用规范
```java
@Slf4j                       // 日志（所有类必备）
@Service                     // Service 层
@RequiredArgsConstructor      // 构造器注入（首选）
public class OAuth2ApplicationService {
    private final OAuth2ApplicationRepository repository;  // final + @RequiredArgsConstructor
    
    public void someMethod() {
        log.info("业务日志: {}", param);  // 使用 SLF4J，禁止 System.out
    }
}
```

## 注解使用规范
- `@Entity` + `@Table` — JPA 实体
- `@Service` — 业务服务
- `@RestController` — REST 控制器
- `@Repository` — 数据访问
- `@Configuration` — 配置类
- `@Transactional` — 事务管理（Service 层）
- `@Valid` / `@Validated` — 参数校验
- `@Tag`, `@Operation`, `@Schema` — API 文档

## 代码质量要求
- 方法长度不超过 80 行
- 循环复杂度不超过 15
- 无重复代码（抽离公共方法）
- 无 System.out.println（使用 log.info / log.debug）
- 所有 Controller 方法返回 `Result<T>` 统一包装

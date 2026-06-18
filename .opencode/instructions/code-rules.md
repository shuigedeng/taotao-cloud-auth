# 项目编码规范 — taotao-cloud-auth

> 认证授权中心编码规范，涵盖 OAuth2、Spring Security、Spring Boot 4.1 相关实现规范。

---

## 1. 模块依赖规则

```
taotao-cloud-auth-api    (protobuf + gRPC 接口定义、DTO、Swagger 注解)
       ↑ 依赖
taotao-cloud-auth-biz    (业务实现、配置、启动入口)
       │
       ├── authentication/    (认证授权核心逻辑)
       ├── configuration/     (Spring Security / OAuth2 配置)
       └── springdoc/         (文档定制)
```

### 禁止违反的依赖
```java
// ❌ 禁止：api 模块依赖 biz 模块（api 是纯接口，必须零业务依赖）
// ❌ 禁止：在 Controller 中直接操作 Entity
@Autowired private OAuth2ApplicationEntity applicationEntity;

// ❌ 禁止：在配置类中硬编码安全规则到业务逻辑
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) {
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers("/public/**").permitAll()
        .anyRequest().authenticated());
    // ❌ 不要在这里写业务判断
}

// ✅ 正确：Controller → Service → Repository
@RestController
public class OAuth2ApplicationController {
    private final OAuth2ApplicationService applicationService;
}
```

## 2. 包结构规范

```
com.taotao.cloud.auth/
├── authentication/
│   ├── controller/     # REST 控制器 (12 个)
│   ├── service/        # 业务服务 (8 个)
│   ├── repository/     # JPA Repository (6 个)
│   ├── entity/         # JPA 持久化实体 (6 个)
│   ├── dto/            # 内部数据传输对象
│   ├── converter/      # OAuth2 RegisteredClient 转换器
│   ├── details/        # UserDetailsService 实现
│   └── generator/      # 自定义 ID 生成器
├── configuration/      # Security / OAuth2 / Client 配置
└── springdoc/          # OpenAPI 文档全局定制
```

### Controller 规范
```java
@RestController
public class OAuth2ApplicationController {
    // HTTP 解析 + 参数校验 + Result 封装
    // 委托给 Service 层，禁止业务逻辑

    @GetMapping("/page")
    public Result<PageResult<OAuth2ApplicationDTO>> page(PageQuery query) {
        return Result.success(applicationService.page(query));
    }

    @PostMapping
    public Result<Void> save(@Valid @RequestBody OAuth2ApplicationDTO dto) {
        applicationService.save(dto);
        return Result.success();
    }
}
```

### Service 规范
```java
@Service
public class OAuth2ApplicationService {
    private final OAuth2ApplicationRepository repository;
    private final OAuth2ApplicationToRegisteredClientConverter converter;

    public OAuth2ApplicationDTO save(OAuth2ApplicationDTO dto) {
        // 1. 参数校验 (通过 @Valid)
        // 2. 业务逻辑
        // 3. 持久化
        // 4. 返回 DTO
    }
}
```

### Configuration 规范
```java
@Configuration
@EnableWebSecurity
public class DefaultSecurityConfiguration {
    // 所有安全配置集中在此
    // 使用 application.yml 外部化配置
    // 不内嵌硬编码 URL 或 Secret

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        // OAuth2 端点安全配置
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        // 默认安全配置
    }
}
```

### Converter 规范
```java
@Component
public class OAuth2ApplicationToRegisteredClientConverter
        implements RegisteredClientConverter<OAuth2Application> {

    @Override
    public RegisteredClient convert(OAuth2Application application) {
        // OAuth2Application Entity → Spring Security RegisteredClient
        // 单向转换，不包含反向逻辑
    }
}
```

## 3. OAuth2 授权流程规范

### 授权码模式流程
```
用户 → [客户端] → /oauth2/authorize (授权端点)
                    → redirect to login (/login)
                    → 用户认证
                    → redirect with code
                    → /oauth2/token (令牌端点) → access_token + refresh_token
```

### 设备码模式流程
```
用户 → [设备] → /oauth2/device_authorization (设备授权端点)
                → device_code + user_code + verification_uri
                → 用户访问 verification_uri 输入 user_code
                → 设备轮询 /oauth2/token → 授权完成
```

### 端点注册规范
```java
// 在 Oauth2AuthorizationServerConfiguration 中注册
RegisteredClient registeredClient = RegisteredClient.withId(application.getId())
    .clientId(application.getClientId())
    .clientSecret(application.getClientSecret())
    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
    .authorizationGrantType(AuthorizationGrantType.DEVICE_CODE)
    .redirectUri(application.getRedirectUris())
    .scope(OidcScopes.OPENID)
    .scope(application.getScopes())
    .clientSettings(ClientSettings.builder()
        .requireAuthorizationConsent(true)
        .build())
    .build();
```

## 4. 数据模型规范

### OAuth2Application — OAuth2 客户端注册
```java
@Entity
@Table(name = "oauth2_application")
public class OAuth2Application {
    @Id
    private String id;

    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "redirect_uris", columnDefinition = "TEXT")
    private String redirectUris;        // JSON 数组字符串

    @Column(name = "authorization_grant_types", columnDefinition = "TEXT")
    private String authorizationGrantTypes;  // JSON 数组字符串

    @Column(name = "client_authentication_methods", columnDefinition = "TEXT")
    private String clientAuthenticationMethods;  // JSON 数组字符串

    @Column(name = "scopes", columnDefinition = "TEXT")
    private String scopes;              // JSON 数组字符串

    // create_by, create_time, update_by, update_time (BaseEntity)
    // is_deleted, tenant_id, version (BaseEntity)
}
```

### 表必备字段
```sql
`id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
`create_by` bigint DEFAULT NULL COMMENT '创建人ID',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_by` bigint DEFAULT NULL COMMENT '更新人ID',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`is_deleted` tinyint(1) DEFAULT 0 COMMENT '删除标记',
`tenant_id` bigint DEFAULT 0 COMMENT '租户ID',
`version` int DEFAULT 0 COMMENT '乐观锁'
```

## 5. 安全规范

- 密码编码：统一使用 `BCryptPasswordEncoder`（Spring Security 内置）
- 禁止自定义加密算法
- 禁止在日志中打印 Secret / Token / Password
- OAuth2 Client Secret 使用加密存储
- 所有认证端点需要速率限制（集成 Sentinel）
- 敏感操作记录合规审计（OAuth2Compliance）

```java
// ✅ 正确：使用 DelegatingPasswordEncoder
@Bean
public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
}

// ❌ 禁止：自定义 MD5/SHA 加密
public class CustomPasswordEncoder { ... }
```

## 6. 枚举规范

```java
// 在 api 模块或 common 中定义
public enum GrantTypeEnum {
    AUTHORIZATION_CODE("authorization_code"),
    CLIENT_CREDENTIALS("client_credentials"),
    DEVICE_CODE("urn:ietf:params:oauth:grant-type:device_code"),
    REFRESH_TOKEN("refresh_token");

    private final String value;
    // ...
}
```

## 7. 构建与测试

```bash
# 全量构建 (Windows)
gradlew build

# 启动开发环境
gradlew :taotao-cloud-auth-biz:bootRun --args='--spring.profiles.active=dev'

# 运行测试
gradlew test

# 运行指定模块测试
gradlew :taotao-cloud-auth-biz:test

# 代码质量检查
gradlew checkstyleMain spotlessCheck pmdMain spotbugsMain

# 打包
gradlew :taotao-cloud-auth-biz:bootJar

# macOS / Linux
./gradlew build
```

## 8. gRPC / Protobuf 规范

- API 接口定义在 `taotao-cloud-auth-api/src/main/proto/`
- DTO 使用 Protobuf `Message` 定义
- gRPC 服务使用 `service` 定义
- 生成的 Java 代码在 `build/generated/sources/`
- 禁止修改生成的 Java 代码
- API 接口使用 swagger 注解说明

## 9. 编码样式

- 缩进：4 空格
- 编码：UTF-8
- 继承 CommonEntity/BaseEntity 实现审计字段
- Service 层事务注解：`@Transactional(readOnly = true)` 查询，写操作单独覆盖
- 禁止 System.out.println，使用 SLF4J Logger
- 所有 Controller 返回 Result 统一包装

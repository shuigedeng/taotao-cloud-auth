# PROJECT KNOWLEDGE BASE

**Generated:** 2026-06-18
**Commit:** `56589ba`
**Branch:** (active branch)

## OVERVIEW

认证授权中心，基于 Spring Boot 4.1.0 / JDK 25 / Gradle 9.5。作为 taotao-cloud 微服务体系的 **OAuth2 授权服务器**，提供统一的认证、授权、单点登录能力。

采用 Spring Authorization Server 框架，支持多种 OAuth2 授权模式（授权码、客户端凭证、设备码、刷新令牌），集成 Spring Security 安全体系。

## STRUCTURE

```
taotao-cloud-auth/
├── .opencode/              # OpenCode AI 配置
├── taotao-cloud-auth-api/  # API 接口 + DTO + Protobuf 定义
└── taotao-cloud-auth-biz/  # 业务实现（启动入口）
    └── src/main/java/com/taotao/cloud/auth/
        ├── TaoTaoCloudAuthApplication.java    # 启动类
        ├── authentication/                    # 认证授权核心模块
        │   ├── controller/       # REST 控制器（OAuth2 端点）
        │   ├── service/          # 业务服务
        │   ├── repository/       # 数据访问
        │   ├── entity/           # 持久化实体
        │   ├── dto/              # 内部 DTO
        │   ├── converter/        # OAuth2 模型转换器
        │   ├── details/          # UserDetailsService
        │   └── generator/        # ID 生成器
        ├── configuration/        # Spring Security / OAuth2 配置
        └── springdoc/            # OpenAPI 文档定制
```

## MODULES

| Module | Path | Responsibility |
|--------|------|----------------|
| api | `taotao-cloud-auth-api/` | gRPC/Protobuf 定义、DTO、API 接口、Swagger 注解 |
| biz | `taotao-cloud-auth-biz/` | 业务实现、控制器、配置、认证逻辑（启动模块） |

## WHERE TO LOOK

| Task | Location |
|------|----------|
| OAuth2 授权端点 | `authentication/controller/` — `AuthorizationController`, `DeviceController`, `TokenController` |
| 客户端管理 | `authentication/controller/OAuth2ApplicationController.java` + `authentication/service/OAuth2ApplicationService.java` |
| 登录流程 | `authentication/controller/LoginController.java` + `configuration/DefaultSecurityConfiguration.java` |
| 授权服务器配置 | `configuration/Oauth2AuthorizationServerConfiguration.java` |
| Security 配置 | `configuration/DefaultSecurityConfiguration.java` + `configuration/OAuth2ClientConfiguration.java` |
| Scope/权限管理 | `authentication/service/OAuth2ScopeService.java`, `OAuth2PermissionService.java` |
| 合规审计 | `authentication/service/OAuth2ComplianceService.java` |
| 设备码授权 | `authentication/controller/OAuth2DeviceController.java`, `DeviceController.java` |
| 用户身份 | `authentication/details/SecurityUserDetailsService.java` |
| Consent 页面 | `authentication/controller/ConsentController.java` |
| 常量/枚举 | `authentication/controller/OAuth2ConstantController.java` |
| API 定义 | `taotao-cloud-auth-api/` (protobuf + gRPC) |

## CONVENTIONS

- **包路径**: `com.taotao.cloud.auth.{module}`
- **Controller 命名**: `{业务}Controller` (如 `OAuth2ApplicationController`, `LoginController`)
- **Service 命名**: `{业务}Service` (如 `OAuth2ApplicationService`)
- **Repository 命名**: `{实体}Repository` (JPA Repository)
- **配置类**: 统一放在 `configuration/` 包下
- **模型转换**: `converter/` 中实现 `RegisteredClientConverter` 等转换器
- **API 隔离**: `api/` 模块只放接口定义和 DTO (swagger 注解), `biz/` 模块放实现
- **多环境**: dev / test / pre / pro 四套配置

## TECH STACK

| Category | Technology | Version |
|----------|-----------|---------|
| Language | Java (JDK 25 preview) | 25 |
| Build | Gradle | 9.5 |
| Framework | Spring Boot | 4.1.0 |
| Security | Spring Security / Spring Authorization Server | 7.x |
| API | gRPC + Protobuf | 1.82.0 / 4.35.0 |
| Persistence | JPA / MyBatis-Plus | - |
| Cache | Redis (Redisson/Lettuce) | 4.3.1 |
| MQ | RocketMQ / Kafka | - |
| Registry | Nacos (via Spring Cloud Alibaba) | - |
| Docs | Knife4j / Swagger / SpringDoc | - |
| Quality | Checkstyle / SpotBugs / PMD / Spotless / OWASP | - |
| Container | Docker / Jib / K8s | - |

## ANTI-PATTERNS (THIS PROJECT)

- Controller 中写业务逻辑（应委托给 Service）
- 配置类中硬编码安全规则（应使用 application.yml）
- 直接操作 HttpSession 管理认证状态（应通过 SecurityContext）
- 自定义加密/哈希算法（应使用 Spring Security 内置 PasswordEncoder）

## COMMANDS

```bash
gradlew build                                          # 全量编译
gradlew :taotao-cloud-auth-biz:bootRun --args='--spring.profiles.active=dev'  # 启动 dev
gradlew checkstyleMain spotlessCheck pmdMain spotbugsMain  # 质量检查
gradlew test                                           # 运行测试
gradlew :taotao-cloud-auth-biz:bootJar                 # 打包可执行 JAR
gradlew publishToMavenLocal                            # 发布到本地 Maven
```

## NOTES

- JDK 25 预览特性，`--enable-preview` + 大量 `--add-exports`
- `taotao-cloud-dependencies:2026.07` BOM 未开源，外部构建需要私有仓库凭据
- `taotao-cloud-starter-security` 提供框架安全能力
- OAuth2 客户端注册信息存储在 `OAuth2Application` 实体中
- 设备码授权流程：`DeviceController` → `OAuth2DeviceService` → `OAuth2Device`
- 所有 OAuth2 endpoint 均有合规审计记录（`OAuth2Compliance`）
- 前端页面（登录、consent）由 `ConsentController` 和 `LoginController` 处理
- 四套环境配置：dev / test / pre / pro

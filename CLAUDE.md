# taotao-cloud-auth — 认证授权中心

## 项目概述

taotao-cloud-auth 是 taotao-cloud 微服务架构中的 **OAuth2 授权服务器（认证中心）**，基于 Spring Authorization Server（内置在 Spring Security 中）构建。提供统一的认证、授权、单点登录能力，支持授权码、客户端凭证、设备码等多种 OAuth2 授权模式。

- **group**: `io.github.shuigedeng`
- **version**: `2026.09`
- **模块**: `taotao-cloud-auth-api`（纯接口） + `taotao-cloud-auth-biz`（业务实现）

## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 语言 | Java | 25 (preview) |
| 构建 | Gradle | 9.6.0 |
| 微服务 | Spring Cloud / Spring Cloud Alibaba | 2025.1.1 / 2025.1.0.0 |
| 框架 | Spring Boot / Spring Security | 4.1.0 / 7.1.0 |
| API | gRPC / Protobuf | 1.80.0 / 4.35.0 |
| 持久化 | JPA + MyBatis-Plus | 3.5.16 |
| 缓存 | Redis (Redisson) | 4.3.1 |
| 消息 | RocketMQ / Kafka | — |
| 注册中心 | Nacos | — |
| 文档 | Knife4j / Swagger | 4.5.0 / 3.0.0 |
| 工具 | Lombok / MapStruct / Record Builder / Hutool | 1.18.46 / 1.6.3 / 53 / 5.8.44 |
| BOM | taotao-cloud-dependencies | 2026.09 |
| 质量 | Checkstyle / SpotBugs / PMD / Spotless / OWASP | — |
| 容器化 | Jib / Docker / K8s | — |

## 项目结构

```
taotao-cloud-auth/
├── taotao-cloud-auth-api/              # 接口定义 + DTO——纯接口模块，零业务依赖
│   └── src/main/
│       ├── java/.../                   # Java 接口 + DTO（Swagger 注解）
│       └── proto/                      # gRPC Protobuf 定义
├── taotao-cloud-auth-biz/              # 业务实现——启动入口，依赖 api 模块
│   └── src/main/java/com/taotao/cloud/auth/
│       ├── TaoTaoCloudAuthApplication.java
│       ├── authentication/             # 认证核心层
│       │   ├── controller/             #   REST 控制器
│       │   ├── service/                #   业务服务
│       │   ├── repository/             #   JPA Repository
│       │   ├── entity/                 #   JPA 实体
│       │   ├── dto/                    #   内部 DTO
│       │   ├── converter/             #   MapStruct 转换器
│       │   ├── details/               #   UserDetailsService 实现
│       │   └── generator/             #   ID 生成器
│       ├── configuration/              # 安全/OAuth2 配置集中目录
│       │   ├── DefaultSecurityConfiguration.java
│       │   ├── Oauth2AuthorizationServerConfiguration.java
│       │   └── OAuth2ClientConfiguration.java
│       ├── oauth2/                     # OAuth2 协议层
│       │   ├── client/                 #   客户端相关
│       │   └── server/                 #   服务端相关
│       └── springdoc/                 # OpenAPI 文档定制
```

## 核心概念

### OAuth2 授权模式
- **授权码模式** (`authorization_code`) — Web 应用标准流程
- **客户端凭证模式** (`client_credentials`) — 服务间调用
- **设备码模式** (`device_code`) — 无浏览器设备
- **刷新令牌** (`refresh_token`) — Token 续期

### 关键实体
| 实体 | 说明 |
|------|------|
| `OAuth2Application` | OAuth2 客户端注册信息 |
| `OAuth2Device` | 设备码授权设备 |
| `OAuth2Scope` | 权限范围定义 |
| `OAuth2Permission` | 权限点 |
| `OAuth2Compliance` | 合规审计记录 |
| `OAuth2Product` | 产品线 |

### 配置类（全部集中在 `configuration/` 包）
- `DefaultSecurityConfiguration` — Spring Security 默认安全配置
- `Oauth2AuthorizationServerConfiguration` — 授权服务器核心配置
- `OAuth2ClientConfiguration` — OAuth2 客户端配置

## 常用命令

```bash
# 全量编译
gradlew build

# 编译指定模块
gradlew :taotao-cloud-auth-biz:compileJava

# 启动开发环境（默认 dev profile）
gradlew :taotao-cloud-auth-biz:bootRun --args='--spring.profiles.active=dev'

# 运行测试
gradlew test

# 代码质量检查
gradlew checkstyleMain spotlessCheck pmdMain spotbugsMain

# OWASP 依赖安全检查
gradlew dependencyCheckAnalyze

# 打包可执行 JAR
gradlew :taotao-cloud-auth-biz:bootJar

# 发布到本地 Maven
gradlew publishToMavenLocal
```

## 编码约定

### 命名规范
- **Controller**: `{业务}Controller`（如 `LoginController`）
- **Service**: `{业务}Service`（如 `OAuth2ApplicationService`）
- **Repository**: `{实体}Repository`（如 `OAuth2ApplicationRepository`）
- **Entity**: `OAuth2{业务}`（如 `OAuth2Application`）
- **配置类**: 统一放在 `configuration/` 包
- **DTO**: `{实体}DTO`，使用 `@Schema` 注解描述字段

### 分层职责
```
Controller (参数校验 + Result<T> 统一包装)
  ↓ 委托
Service (业务逻辑 + @Transactional 事务管理)
  ↓ 调用
Repository (JPA 数据访问，接口继承 JpaRepository)
```

### API 规范
- `taotao-cloud-auth-api` 模块：只放接口定义和 DTO，**不包含任何业务实现**
- `taotao-cloud-auth-biz` 模块：依赖 api 模块，实现所有业务
- Controller 返回统一 `Result<T>` 包装
- 接口方法使用 `@Tag`、`@Operation`、`@Schema` 注解完善 OpenAPI 文档

### 安全规范
- 密码编码使用 `BCryptPasswordEncoder`（通过 `DelegatingPasswordEncoder`）
- 禁止自定义加密算法替代 Spring Security 内置实现
- 禁止在日志中打印 Secret / Token / Password
- OAuth2 端点需要速率限制（Sentinel）
- 敏感操作记录合规审计（`OAuth2Compliance`）

### 工具链约定
- **Lombok**: `@Data`、`@Builder`、`@Slf4j` 等标准注解
- **Record Builder**: 实体使用 Java Record + `@RecordBuilder`
- **MapStruct**: 实体 ↔ DTO 互转，`defaultComponentModel=spring`
- **Protobuf**: gRPC 接口定义放在 api 模块的 `proto/` 目录

## 禁止项 (ANTI-PATTERNS)
- ❌ Controller 中直接调用 Repository
- ❌ Controller 中包含业务逻辑判断
- ❌ api 模块依赖 biz 模块（禁止反向依赖）
- ❌ 配置类中硬编码安全规则 / URL / Secret
- ❌ 在非 `configuration/` 包中定义 `SecurityFilterChain`
- ❌ 在日志中打印密码、token、secret 等敏感信息

## 环境配置
- **四套环境**: `dev` / `test` / `pre` / `pro`
- **配置文件**: `taotao-cloud-auth-biz/src/main/resources/application-{profile}.yml`
- **激活**: `--spring.profiles.active={profile}`

## JDK 25 预览特性注意事项
- 编译时：`--enable-preview` + `--release 25`
- 运行时：`--enable-preview`
- `--add-exports` JVM 参数大量使用（接入 Spring Boot 4 + JDK 25 的模块化兼容需求）
- BOM `taotao-cloud-dependencies:2026.09` 未开源，需私有仓库凭据
- `OAuth2Application` 中 `redirectUris`、`scopes` 等字段使用 JSON 数组字符串存储

## 团队协作
- **语言**: 中文（代码注释、文档、沟通）
- **代码审查**: 变更需通过 `/review` 命令审查，安全相关变更需额外安全审计
- **变更流程**: `/propose`（提案）→ 确认 → `/apply`（编码）→ `/review`（审查）→ `/fix`（修正）→ `/test`（测试）→ `/archive`（归档）
- **决策原则**: 安全优先 → 架构规范 → 代码简洁 → 性能优化
- **输出风格**: 直接、简洁、问题导向

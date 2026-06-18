# taotao-cloud-auth — 认证授权中心开发规范

## 项目概述

taotao-cloud-auth 是 taotao-cloud 微服务体系的 **OAuth2 授权服务器（认证中心）**，基于 Spring Authorization Server 框架构建。提供统一的认证、授权、单点登录能力，支持多种 OAuth2 授权模式。

## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 语言 | Java (JDK 25 preview) | 25 |
| 构建 | Gradle | 9.5 |
| 框架 | Spring Boot / Spring Security / Spring Authorization Server | 4.1.0 / 7.x |
| API | gRPC / Protobuf | 1.82.0 / 4.35.0 |
| 持久化 | JPA / MyBatis-Plus | - |
| 缓存 | Redis (Redisson / Lettuce) | 4.3.1 |
| 消息 | RocketMQ / Kafka | - |
| 注册中心 | Nacos (Spring Cloud Alibaba) | - |
| BOM | taotao-cloud-dependencies | 2026.07 |
| 质量 | Checkstyle / SpotBugs / PMD / Spotless / OWASP | - |

## 项目结构

```
taotao-cloud-auth/
├── taotao-cloud-auth-api/            # API 接口定义 + DTO + Protobuf（纯接口模块）
│   └── src/main/
│       ├── java/.../                 # Java 接口 + DTO 类
│       └── proto/                    # Protobuf 定义
├── taotao-cloud-auth-biz/            # 业务实现模块（启动入口）
│   └── src/main/java/com/taotao/cloud/auth/
│       ├── TaoTaoCloudAuthApplication.java      # 启动类
│       ├── authentication/
│       │   ├── controller/           # REST 控制器 (12个)
│       │   ├── service/              # 业务服务 (8个)
│       │   ├── repository/           # JPA Repository (6个)
│       │   ├── entity/               # JPA 实体 (6个)
│       │   ├── dto/                  # 内部数据传输对象
│       │   ├── converter/            # OAuth2 模型转换器
│       │   ├── details/             # UserDetailsService 实现
│       │   └── generator/           # ID 生成器
│       ├── configuration/            # Security / OAuth2 / Client 配置
│       └── springdoc/               # OpenAPI 文档定制
```

## 核心概念

### OAuth2 授权模式支持
- **授权码模式** (authorization_code) — Web 应用标准流程
- **客户端凭证模式** (client_credentials) — 服务间调用
- **设备码模式** (device_code) — 无浏览器设备
- **刷新令牌** (refresh_token) — Token 续期

### 关键实体
- `OAuth2Application` — OAuth2 客户端注册信息
- `OAuth2Device` — 设备码授权设备
- `OAuth2Scope` — 权限范围定义
- `OAuth2Permission` — 权限点
- `OAuth2Compliance` — 合规审计记录
- `OAuth2Product` — 产品线

## 常用命令

```bash
# 全量编译
gradlew build

# 启动开发环境
gradlew :taotao-cloud-auth-biz:bootRun --args='--spring.profiles.active=dev'

# 编译指定模块
gradlew :taotao-cloud-auth-biz:compileJava

# 运行测试
gradlew test

# 代码质量检查
gradlew checkstyleMain spotlessCheck pmdMain spotbugsMain

# 打包可执行 JAR
gradlew :taotao-cloud-auth-biz:bootJar

# 发布到本地 Maven
gradlew publishToMavenLocal
```

## 编码约定

### 命名规范
- **Controller**: `{业务}Controller`（如 `LoginController`, `OAuth2ApplicationController`）
- **Service**: `{业务}Service`（如 `OAuth2ApplicationService`, `OAuth2ScopeService`）
- **Repository**: `{实体}Repository`（如 `OAuth2ApplicationRepository`）
- **Entity**: `OAuth2{业务}`（如 `OAuth2Application`, `OAuth2Device`）
- **配置类**: 统一放在 `configuration/` 包

### 分层职责
```
Controller (参数校验 + Result封装)
    ↓ 委托
Service (业务逻辑 + 事务管理)
    ↓ 调用
Repository (数据访问, JPA接口)
```

### 配置集中原则
- 所有 Spring Security / OAuth2 安全配置 → `configuration/` 包
- `DefaultSecurityConfiguration.java` — 默认安全配置
- `Oauth2AuthorizationServerConfiguration.java` — 授权服务器配置
- `OAuth2ClientConfiguration.java` — OAuth2 客户端配置

### 安全规范
- 密码编码使用 `BCryptPasswordEncoder`（DelegatingPasswordEncoder）
- 禁止自定义加密算法
- 禁止在日志中打印 Secret / Token / Password
- OAuth2 端点需要速率限制（Sentinel）
- 敏感操作记录合规审计（OAuth2Compliance）

### API 规范
- `taotao-cloud-auth-api` 模块只放接口定义和 DTO（Swagger 注解）
- `biz` 模块放实现
- Controller 返回统一 `Result` 包装
- 使用 `@Tag`、`@Operation`、`@Schema` 注解完善 API 文档

## 禁止项 (ANTI-PATTERNS)
- ❌ Controller 中直接调用 Repository
- ❌ Controller 中包含业务逻辑判断
- ❌ 配置类中硬编码安全规则/URL/Secret
- ❌ 自定义加密算法替代 Spring Security 内置实现
- ❌ 在非 configuration 包中定义 SecurityFilterChain
- ❌ 在日志中打印敏感信息（密码、token、secret）
- ❌ 跨模块循环依赖（api ↔ biz）

## 环境配置
- 四套环境：`dev` / `test` / `pre` / `pro`
- 配置位置：`taotao-cloud-auth-biz/src/main/resources/application-{profile}.yml`
- 激活方式：`--spring.profiles.active={profile}`

## 团队协作方式
- **语言**: 中文（代码注释、文档、沟通）
- **代码审查**: 所有变更需通过 `/review` 命令审查，安全相关变更需额外安全审计
- **变更流程**: `/propose`（提案）→ 确认 → `/apply`（编码）→ `/review`（审查）→ `/fix`（修正）→ `/test`（测试）→ `/archive`（归档）
- **决策原则**: 安全优先 > 架构规范 > 代码简洁 > 性能优化
- **输出风格**: 直接、简洁、问题导向

## 备注
- JDK 25 预览特性，编译和运行时需加 `--enable-preview`
- 大量 `--add-exports` JVM 参数（build.gradle 中配置）
- `taotao-cloud-dependencies:2026.07` BOM 未开源，需私有仓库凭据
- `OAuth2Application` 中的 `redirectUris`、`scopes` 等字段使用 JSON 数组字符串存储

---
description: 初始化项目上下文，分析工程结构、依赖、模式
agent: general
---

你是 taotao-cloud-auth 项目架构分析师，正在执行 /spec-init 命令。

## 任务目标

分析工程结构、依赖关系和分层模式，生成项目上下文总结。

## 执行步骤

### 1. 分析项目结构
- 使用 `read` 读取根目录结构
- 识别 2 个 Gradle 模块（taotao-cloud-auth-api + taotao-cloud-auth-biz）
- 确认 biz 模块下的包结构（authentication/controller, service, repository, entity, configuration）

### 2. 分析技术栈
- JDK 版本（25 预览特性）
- Gradle 版本及关键插件（spotbugs/checkstyle/pmd/spotless）
- Spring Boot / Spring Security / OAuth2 版本
- 持久化框架（JPA / MyBatis-Plus）
- 消息中间件（RocketMQ / Kafka）
- 注册中心（Nacos）
- gRPC / Protobuf

### 3. 分析分层模式
- authentication/controller：REST API 端点
- authentication/service：业务服务
- authentication/repository：数据访问
- authentication/entity：JPA 实体
- authentication/converter：OAuth2 模型转换
- configuration：Spring Security / OAuth2 配置

### 4. 输出项目上下文
生成分析报告，包含：
- 项目全貌（模块 + 职责）
- 技术栈清单
- 各层职责和关键类
- 常见操作指引

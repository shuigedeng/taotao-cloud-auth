---
description: 按确认后的 Spec 执行编码
agent: general
---

你是 taotao-cloud-auth 项目的实现助手，正在执行 /apply 命令。

变更名称：$ARGUMENTS

## 前置检查
1. 确认 Spec 已完成并获用户批准
2. 确认所有待澄清问题已解决

## 零偏差原则
- Spec 是合同，严格按 Spec 执行
- 不允许偏离 Spec 的任何变更

## 分层实现原则

| 实现步骤 | 放入哪一层 | 注意事项 |
|----------|-----------|----------|
| REST Controller | `authentication/controller/` | 参数校验 + Result 封装，无业务逻辑 |
| Service 业务逻辑 | `authentication/service/` | 事务管理，调用 Repository |
| JPA Repository | `authentication/repository/` | 接口继承 JpaRepository |
| 实体定义 | `authentication/entity/` | JPA Entity + Table 注解 |
| DTO | `authentication/dto/` | 内部数据传输 |
| Security 配置 | `configuration/` | 所有安全配置集中在此 |
| API 接口定义 | `taotao-cloud-auth-api/` | protobuf + swagger 注解 |
| 模型转换 | `authentication/converter/` | OAuth2 模型双向转换 |

## 执行流程

每个 Task：
1. 使用 `read` 确认目标文件
2. 使用 `edit` 或 `write` 修改代码
3. 验证编译：
```bash
gradlew :taotao-cloud-auth-biz:compileJava
```
4. Git Commit
```bash
git add -A
git commit -m "apply: [变更名] [Task描述]"
```

## 输出格式
每个 Task 完成后输出：
```
✅ Task 完成
📝 改动：{文件列表}
🔧 编译：SUCCESS
📦 Commit：{message}
```

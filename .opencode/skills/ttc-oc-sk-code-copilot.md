# Code Copilot — taotao-cloud-auth 开发技能

适配项目：taotao-cloud-auth（OAuth2 授权中心 + Spring Security + Gradle + JDK 25）

## 触发条件

通过 `/propose` → `/apply` → `/review` → `/fix` → `/test` → `/archive` 命令触发。

## 核心规则

1. **No Spec No Code** — 没有确认的 Spec 不准写代码
2. **Spec is Truth** — Spec 和代码冲突时，错的一定是代码
3. **安全优先** — 所有变更从安全/认证角度出发，确保 OAuth2 流程完整性

## 工作流

### /propose — 创建变更提案
1. 调研涉及的模块（authentication/controller, service, configuration 等）
2. 逐个澄清需求（每次一个问题，用 `question` 工具）
3. 生成 Spec，包含：模型变更、安全规则、接口变更
4. 用户确认后才进入 /apply

### /apply — 按 Spec 编码
1. 严格遵循分层实现（Controller → Service → Repository）
2. 每个 Task 执行后 `gradlew :taotao-cloud-auth-biz:compileJava` 验证
3. 确保安全配置在 `configuration/` 中，不在 Controller 中硬编码

### /review — 代码审查
1. 检查安全合规（密码编码、认证流程、权限检查）
2. 检查架构合规（分层职责、依赖方向）
3. 检查 OAuth2 配置完整性

### /fix — 修正问题
1. 增量修改，不重构式修复
2. 验证编译通过
3. 更新相关文档

### /test — 运行测试
```bash
gradlew test
```

### /archive — 归档变更
1. 记录变更总结（涉及模块、安全配置、接口）
2. 如需更新 AGENTS.md（新模块/新约定）
3. Git commit

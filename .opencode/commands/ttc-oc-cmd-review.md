---
description: 代码审查 — 检查代码质量、安全合规、架构合理性
agent: general
---

你是 taotao-cloud-auth 项目的代码审查专家，正在执行 /review 命令。

变更范围：$ARGUMENTS

## 审查维度

### 1. 安全合规
- 密码编码是否使用 BCryptPasswordEncoder
- 是否硬编码了 Secret / Token / Password
- 日志中是否可能泄露敏感信息
- OAuth2 端点是否有适当的认证和授权
- Scope/Permission 检查是否完整

### 2. 架构合规
- Controller 是否不含业务逻辑（仅参数校验 + 响应封装）
- Service 层是否正确使用 @Transactional
- 配置是否集中放在 configuration/ 包，而非分散在各 Controller
- 是否符合分层依赖方向

### 3. 代码风格
- 命名：`{业务}Controller`, `{业务}Service`, `{业务}Repository`
- 包路径：`com.taotao.cloud.auth.{module}`
- 是否符合 `.opencode/instructions/code-rules.md`

### 4. 项目特定禁止项
- Controller 中直接调用 Repository
- 配置类中硬编码业务规则
- 自定义加密算法替代 Spring Security 内置实现
- 在非 configuration 包中配置 SecurityFilterChain

## 输出格式

```
📊 Code Review Report

✅ 通过：
- [内容]

⚠️ 警告：
- [内容]

❌ 违规：
- [严重度] [位置] [问题描述]

💡 改进建议：
- [建议]
```

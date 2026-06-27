---
description: Review 后修正迭代 — 增量修正 + 代码合规
agent: general
---

你是 taotao-cloud-auth 项目的修复助手，正在执行 /fix 命令。

参数：$ARGUMENTS
- 第一个参数（$1）：修复主题/目标
- 第二个参数（$2）：修复描述（可选）

## 增量修正 + 合规铁律

### 1. 分析问题
- 使用 `read` 定位问题代码
- 使用 `grep` 搜索相关引用
- 确认修复不影响安全配置

### 2. 执行修正
- 使用 `edit` 工具修改代码（优先于 `write`）
- 只修改与问题直接相关的部分，禁止重构式修复

### 3. 验证
```bash
gradlew :taotao-cloud-auth-biz:compileJava
```
- 展示完整编译输出
- 确保零 error

### 4. 合规检查
修复完成后校验：
- 未破坏安全配置结构
- 未在 Controller 中添加业务逻辑
- 未引入硬编码安全规则
- 未泄露密钥/Secret

### 5. Git Commit
```bash
git commit -m "fix: [修复内容]"
```

## 输出格式
```
✅ 修正完成
📝 改动文件：[列表]
🔧 编译结果：SUCCESS/FAIL
📋 合规检查：PASS/FAIL
```

---
description: 运行测试并生成 JaCoCo 覆盖率报告
parameters:
  - name: module
    type: string
    description: 测试模块 (api / biz / 留空则全部)
    required: false
  - name: coverage
    type: boolean
    default: true
---

# Test — 测试 taotao-cloud-auth

## 执行步骤

### 1. 运行测试
```bash
gradlew test
```

如果指定了模块参数：
```bash
gradlew :taotao-cloud-auth-{{module}}:test
```

### 2. 生成覆盖率报告（可选）
```bash
gradlew jacocoTestReport
```
报告位置: `build/reports/jacoco/test/html/index.html`

### 3. 输出测试结果
```
测试统计
总测试数: {total}
通过: {passed}
失败: {failed}
耗时: {duration}ms

失败测试详情（如有）
- {className}.{methodName}: {errorMessage}
```

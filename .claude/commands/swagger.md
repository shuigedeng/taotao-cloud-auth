---
description: 生成 OpenAPI / Swagger 文档
---

# Swagger / OpenAPI 文档生成 — taotao-cloud-auth

## 执行步骤

### 1. 确认 Knife4j/Swagger 配置
检查 `taotao-cloud-auth-biz/build.gradle` 中 springdoc-openapi 和 swagger 依赖是否正确。

### 2. 启动服务
```bash
gradlew :taotao-cloud-auth-biz:bootRun --args='--spring.profiles.active=dev'
```

### 3. 访问文档
- Knife4j UI: `http://localhost:8080/doc.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

### 4. 检查 API 完整性
- 所有 Controller 是否有 `@Tag` 注解
- 所有接口方法是否有 `@Operation` 注解
- DTO 字段是否有 `@Schema` 注解
- 请求/响应体是否完整定义
- OAuth2 端点是否正确文档化

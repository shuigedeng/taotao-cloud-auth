# 个人开发配置 — taotao-cloud-auth

## 开发工具
- **IDE**: IntelliJ IDEA Ultimate
- **API 调试**: Knife4j (http://localhost:{port}/doc.html)
- **OAuth2 调试**: OAuth2 Playground / Postman

## 个人偏好
- **测试驱动**: Service 层单元测试先行
- **代码生成**: 使用 Lombok + MapStruct + Record Builder 减少样板代码
- **调试模式**: 开启 Spring Security DEBUG 日志查看认证流程
- **本地启动**: `gradlew :taotao-cloud-auth-biz:bootRun --args='--spring.profiles.active=dev'`

## 本地配置覆盖
```yaml
# application-dev.yml 本地覆盖示例
spring:
  security:
    oauth2:
      authorization-server:
        issuer-url: http://localhost:8080
  datasource:
    url: jdbc:mysql://localhost:3306/taotao_cloud_auth?useUnicode=true&characterEncoding=utf-8
    username: root
    password: local_password
```

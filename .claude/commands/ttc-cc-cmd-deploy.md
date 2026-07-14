---
description: 部署应用到指定环境（dev/test/pre/pro）
parameters:
  - name: environment
    type: string
    enum: [dev, test, pre, pro]
    default: dev
  - name: skipTests
    type: boolean
    default: false
---

# Deploy — 部署 taotao-cloud-auth

目标环境: `{{environment}}`

## 部署流程

### 1. 运行测试
{% if skipTests %}
跳过测试。
{% else %}
```bash
gradlew test
```
测试失败则中止部署。
{% endif %}

### 2. 打包
```bash
gradlew :taotao-cloud-auth-biz:bootJar
```

### 3. 启动
```bash
java --enable-preview ^
  -jar taotao-cloud-auth-biz/build/libs/taotao-cloud-auth-*.jar ^
  --spring.profiles.active={{environment}}
```

### 4. 健康检查
```bash
curl -f http://localhost:8080/actuator/health
```

## 部署报告
- 环境: {{environment}}
- JAR 位置: `taotao-cloud-auth-biz/build/libs/`
- 健康检查: 待确认

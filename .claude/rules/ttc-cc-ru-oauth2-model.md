# OAuth2 模型设计规范 — taotao-cloud-auth

## 核心实体设计

### OAuth2Application（OAuth2 客户端注册）
```java
@Entity
@Table(name = "oauth2_application")
public class OAuth2Application {
    @Id
    private String id;

    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "redirect_uris", columnDefinition = "TEXT")
    private String redirectUris;        // JSON数组字符串

    @Column(name = "authorization_grant_types", columnDefinition = "TEXT")
    private String authorizationGrantTypes;  // JSON数组字符串

    @Column(name = "client_authentication_methods", columnDefinition = "TEXT")
    private String clientAuthenticationMethods;  // JSON数组字符串

    @Column(name = "scopes", columnDefinition = "TEXT")
    private String scopes;              // JSON数组字符串

    // 继承 BaseEntity: createBy, createTime, updateBy, updateTime
    // isDeleted, tenantId, version
}
```

### OAuth2Device（设备码授权设备）
```java
@Entity
@Table(name = "oauth2_device")
public class OAuth2Device {
    @Id
    private String id;
    private String deviceCode;
    private String userCode;
    private String clientId;
    private String status;        // PENDING / AUTHORIZED / EXPIRED
    private LocalDateTime expiresAt;
    private LocalDateTime lastPolledAt;
}
```

### OAuth2Scope（权限范围）
```java
@Entity
@Table(name = "oauth2_scope")
public class OAuth2Scope {
    @Id
    private String id;
    private String scopeKey;       // "read", "write", "user.info"
    private String scopeName;      // 显示名称
    private String description;
    private Boolean isAutoApprove; // 是否自动批准
}
```

## 模型转换规范

### Entity → Spring Security RegisteredClient
```java
@Component
public class OAuth2ApplicationToRegisteredClientConverter
        implements RegisteredClientConverter<OAuth2Application> {

    @Override
    public RegisteredClient convert(OAuth2Application application) {
        return RegisteredClient.withId(application.getId())
            .clientId(application.getClientId())
            .clientSecret(application.getClientSecret())
            .clientAuthenticationMethods(methods ->
                parseJsonArray(application.getClientAuthenticationMethods())
                    .forEach(m -> methods.add(resolveAuthMethod(m))))
            .authorizationGrantTypes(types ->
                parseJsonArray(application.getAuthorizationGrantTypes())
                    .forEach(g -> types.add(resolveGrantType(g))))
            .redirectUris(uris ->
                parseJsonArray(application.getRedirectUris())
                    .forEach(uris::add))
            .scopes(scopes ->
                parseJsonArray(application.getScopes())
                    .forEach(scopes::add))
            .clientSettings(ClientSettings.builder()
                .requireAuthorizationConsent(true).build())
            .build();
    }
}
```

## JSON 数组字段规范
- `redirectUris`, `scopes`, `authorizationGrantTypes`, `clientAuthenticationMethods`
- 存储格式: `["value1","value2","value3"]`
- 使用 Jackson ObjectMapper 或手动 JSON 解析
- 禁止使用字符串拼接/分割方式处理

## 审计字段（BaseEntity）
```sql
`id`          bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
`create_by`   bigint DEFAULT NULL COMMENT '创建人ID',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_by`   bigint DEFAULT NULL COMMENT '更新人ID',
`update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`is_deleted`  tinyint(1) DEFAULT 0 COMMENT '删除标记',
`tenant_id`   bigint DEFAULT 0 COMMENT '租户ID',
`version`     int DEFAULT 0 COMMENT '乐观锁'
```

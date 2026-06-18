# 安全规范 — taotao-cloud-auth

## 密码编码
- 统一使用 `BCryptPasswordEncoder`（通过 `DelegatingPasswordEncoder` 工厂）
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
}
```

## OAuth2 端点安全规则
```java
@Configuration
@EnableWebSecurity
public class DefaultSecurityConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http.securityMatcher("/oauth2/**", "/.well-known/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/oauth2/authorize").authenticated()
                .requestMatchers("/oauth2/token").permitAll()
                .requestMatchers("/oauth2/revoke").permitAll()
                .requestMatchers("/.well-known/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }
}
```

## 安全禁止项
- ❌ 禁止自定义加密/哈希算法
- ❌ 禁止在日志中打印 Secret、Token、Password
- ❌ 禁止硬编码安全规则中的 URL 或 Secret
- ❌ 禁止将 SecurityFilterChain 定义在 configuration 包之外
- ❌ 禁止暴露 `/actuator/**` 端点到公网

## 敏感数据保护
- OAuth2 Client Secret 使用加密存储
- 令牌使用 JWT 格式，签名算法 RS256
- 敏感操作（登录、令牌颁发、权限变更）记录合规审计
- Token 有效期配置:
  - access_token: 2 小时
  - refresh_token: 30 天
  - device_code: 5 分钟
  - user_code: 5 分钟

## 速率限制
- OAuth2 令牌端点: 集成 Sentinel 限流
- 登录端点: IP 级别限流
- 设备码端点: 客户端级别限流

## 合规审计
- OAuth2Compliance 记录所有敏感操作
- 审计字段: 用户ID、客户端ID、操作类型、IP 地址、时间戳
- 审计数据保留策略: 90 天

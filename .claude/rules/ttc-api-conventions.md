# API 设计规范 — taotao-cloud-auth

## RESTful 约定

### 资源命名
- OAuth2 客户端管理: `/oauth2/applications`
- OAuth2 授权端点: `/oauth2/authorize`, `/oauth2/token`
- 登录: `/login`, `/logout`
- Scope 管理: `/oauth2/scopes`
- 权限管理: `/oauth2/permissions`
- 设备管理: `/oauth2/devices`
- 合规审计: `/oauth2/compliances`
- 常量/枚举: `/oauth2/constants`

### HTTP 方法与语义
| 方法 | 用途 | 示例 |
|------|------|------|
| GET | 查询列表/详情 | `GET /oauth2/applications/{id}` |
| POST | 创建/授权 | `POST /oauth2/applications`, `POST /oauth2/token` |
| PUT | 全量更新 | `PUT /oauth2/applications/{id}` |
| DELETE | 删除 | `DELETE /oauth2/applications/{id}` |

### OAuth2 端点规范
- 授权端点: `GET /oauth2/authorize` — 用户浏览器跳转
- 令牌端点: `POST /oauth2/token` — 获取 access_token
- 设备授权端点: `POST /oauth2/device_authorization` — 设备码请求
- Token 撤销: `POST /oauth2/revoke`
- JWK Set: `GET /.well-known/jwks.json`
- OpenID Connect: `GET /.well-known/openid-configuration`

### 统一响应格式
```json
{
  "code": 200,
  "message": "success",
  "data": { ... },
  "timestamp": "2026-06-18T10:00:00Z"
}
```

### 分页响应
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "content": [...],
    "page": 0,
    "size": 20,
    "totalElements": 100,
    "totalPages": 5
  }
}
```

### 参数校验
```java
public class OAuth2ApplicationDTO {
    @NotBlank(message = "客户端ID不能为空")
    private String clientId;

    @NotBlank(message = "客户端名称不能为空")
    private String clientName;

    @Email(message = "邮箱格式不正确")
    private String email;

    @NotNull(message = "授权类型不能为空")
    private List<String> authorizationGrantTypes;
}
```

### API 文档注解
```java
@Tag(name = "OAuth2 客户端管理", description = "OAuth2 应用注册与配置")
@RestController
@RequestMapping("/oauth2/applications")
public class OAuth2ApplicationController {

    @Operation(summary = "分页查询客户端列表")
    @GetMapping("/page")
    public Result<PageResult<OAuth2ApplicationDTO>> page(
            @ParameterObject OAuth2ApplicationPageQuery query) {
        return Result.success(applicationService.page(query));
    }
}
```

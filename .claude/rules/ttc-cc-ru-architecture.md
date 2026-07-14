# 架构规范 — taotao-cloud-auth

## 分层职责

### Controller 层
- **职责**: HTTP 请求解析、参数校验、响应封装 (Result 包装)
- **禁止**: 业务逻辑、直接调用 Repository、事务管理
- **包路径**: `com.taotao.cloud.auth.authentication.controller`
- **示例**:
```java
@RestController
public class OAuth2ApplicationController {
    private final OAuth2ApplicationService applicationService;

    @GetMapping("/page")
    public Result<PageResult<OAuth2ApplicationDTO>> page(OAuth2ApplicationPageQuery query) {
        return Result.success(applicationService.page(query));
    }

    @PostMapping
    public Result<Void> save(@Valid @RequestBody OAuth2ApplicationDTO dto) {
        applicationService.save(dto);
        return Result.success();
    }
}
```

### Service 层
- **职责**: 业务逻辑、事务管理、调用 Repository
- **禁止**: 直接操作 Entity 内部状态、SQL 拼接
- **包路径**: `com.taotao.cloud.auth.authentication.service`
- **示例**:
```java
@Service
public class OAuth2ApplicationService {
    private final OAuth2ApplicationRepository repository;
    private final OAuth2ApplicationToRegisteredClientConverter converter;

    @Transactional
    public OAuth2ApplicationDTO save(OAuth2ApplicationDTO dto) {
        OAuth2Application entity = new OAuth2Application();
        // ... 业务逻辑
        repository.save(entity);
        return OAuth2ApplicationDTO.from(entity);
    }
}
```

### Repository 层
- **职责**: 数据访问、JPA 查询封装
- **禁止**: 业务逻辑、返回 DTO
- **包路径**: `com.taotao.cloud.auth.authentication.repository`
- **示例**:
```java
@Repository
public interface OAuth2ApplicationRepository extends JpaRepository<OAuth2Application, String> {
    Optional<OAuth2Application> findByClientId(String clientId);
    Page<OAuth2Application> findByClientIdContaining(String clientId, Pageable pageable);
}
```

### Configuration 层
- **职责**: Spring Security / OAuth2 安全配置集中管理
- **包路径**: `com.taotao.cloud.auth.configuration`
- **规则**: 所有 SecurityFilterChain 定义、Security 配置必须在此包
- **禁止**: 在 Controller 或 Service 中配置安全规则

## 依赖方向
```
Controller → Service → Repository
                    ↓
              Configuration (安全)
```
- Controller 只能依赖 Service
- Service 可以依赖多个 Repository
- Repository 不能依赖 Service
- Configuration 不依赖业务层

## 模块间依赖
```
taotao-cloud-auth-api  (纯接口 + DTO + Protobuf)
       ↑ 依赖
taotao-cloud-auth-biz  (业务实现)
```
- api 模块不可依赖 biz 模块
- biz 模块依赖 api 模块

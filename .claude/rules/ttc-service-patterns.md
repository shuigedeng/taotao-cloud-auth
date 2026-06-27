# Service 层设计规范 — taotao-cloud-auth

## 基础 Service 模式

### 标准 CRUD Service
```java
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2ApplicationService {
    private final OAuth2ApplicationRepository repository;

    public PageResult<OAuth2ApplicationDTO> page(OAuth2ApplicationPageQuery query) {
        Page<OAuth2Application> page = repository.findAll(
            SpecificationFactory.like("clientId", query.getClientId()),
            PageRequest.of(query.getPage(), query.getSize())
        );
        return PageResult.of(page.map(OAuth2ApplicationDTO::from));
    }

    public OAuth2ApplicationDTO findById(String id) {
        return repository.findById(id)
            .map(OAuth2ApplicationDTO::from)
            .orElseThrow(() -> new ResourceNotFoundException("客户端不存在"));
    }

    @Transactional
    public OAuth2ApplicationDTO save(OAuth2ApplicationDTO dto) {
        OAuth2Application entity = new OAuth2Application();
        BeanUtils.copyProperties(dto, entity);
        repository.save(entity);
        log.info("OAuth2 client created: {}", entity.getClientId());
        return OAuth2ApplicationDTO.from(entity);
    }

    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
        log.warn("OAuth2 client deleted: {}", id);
    }
}
```

### OAuth2 模型转换 Service
```java
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2ApplicationService {
    private final OAuth2ApplicationRepository repository;
    private final OAuth2ApplicationToRegisteredClientConverter converter;

    public RegisteredClient loadRegisteredClient(String clientId) {
        return repository.findByClientId(clientId)
            .map(converter::convert)
            .orElse(null);
    }
}
```

### 合规审计 Service
```java
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2ComplianceService {
    private final OAuth2ComplianceRepository complianceRepository;

    @Transactional
    public void recordCompliance(String principalName, String clientId,
                                  String operation, String ipAddress) {
        OAuth2Compliance compliance = OAuth2Compliance.builder()
            .principalName(principalName)
            .clientId(clientId)
            .operation(operation)
            .ipAddress(ipAddress)
            .build();
        complianceRepository.save(compliance);
        log.info("Compliance recorded: {} - {}", principalName, operation);
    }
}
```

## Service 层规范
1. **事务管理**: 写操作使用 `@Transactional`，查询使用 `@Transactional(readOnly = true)`
2. **方法命名**: 以动词开头（`save`, `find`, `delete`, `load`, `record`）
3. **参数校验**: 使用 `@Valid` 注解在 Controller 层完成，Service 层不做重复校验
4. **异常处理**: 业务异常使用自定义 `BusinessException`，资源不存在使用 `ResourceNotFoundException`
5. **日志规范**: 写操作使用 `log.info`，删除操作使用 `log.warn`，错误使用 `log.error`
6. **禁止**: Service 层直接返回 Entity，必须转换为 DTO

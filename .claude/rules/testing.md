# 测试规范 — taotao-cloud-auth

## 测试层级

### 单元测试（Service 层）
```java
@ExtendWith(MockitoExtension.class)
class OAuth2ApplicationServiceTest {
    @Mock
    private OAuth2ApplicationRepository repository;
    @Mock
    private OAuth2ApplicationToRegisteredClientConverter converter;

    @InjectMocks
    private OAuth2ApplicationService service;

    @Test
    void shouldSaveApplication() {
        // Given
        OAuth2ApplicationDTO dto = new OAuth2ApplicationDTO();
        dto.setClientId("test-client");

        // When
        OAuth2ApplicationDTO result = service.save(dto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getClientId()).isEqualTo("test-client");
        verify(repository).save(any(OAuth2Application.class));
    }
}
```

### 集成测试（Spring Boot）
```java
@SpringBootTest
@AutoConfigureMockMvc
class OAuth2ApplicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldPageApplications() throws Exception {
        mockMvc.perform(get("/oauth2/applications/page")
                .param("page", "0")
                .param("size", "20"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
```

### OAuth2 端点测试
```java
@SpringBootTest
@AutoConfigureMockMvc
class OAuth2AuthorizationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnTokenForValidClient() throws Exception {
        mockMvc.perform(post("/oauth2/token")
                .param("grant_type", "client_credentials")
                .param("scope", "read")
                .header("Authorization", "Basic " + Base64.encode("client:secret")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.access_token").isNotEmpty());
    }
}
```

## 覆盖率要求
- Service 层: ≥ 85%
- Controller 层: ≥ 70%
- 整体项目: ≥ 80%

## 禁止事项
- 禁止使用 `@DirtiesContext`（影响测试性能）
- 禁止在测试中硬编码敏感信息（密码、secret）
- 禁止依赖外部服务（使用 Mock / Testcontainers）
- 禁止测试之间共享可变状态

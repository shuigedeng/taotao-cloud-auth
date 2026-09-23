/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.auth;

import com.taotao.boot.common.utils.context.EnableContextUtils;
import com.taotao.boot.core.startup.StartupSpringApplication;
import com.taotao.boot.data.jpa.extend.JpaExtendRepositoryFactoryBean;
import com.taotao.boot.security.spring.annotation.EnableOauth2AuthorizationServer;
import com.taotao.boot.security.spring.annotation.EnableOauth2ResourceServer;
import com.taotao.boot.web.annotation.TaoTaoBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.security.oauth2.client.autoconfigure.OAuth2ClientProperties;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;

/**
 * TaoTaoCloudAuthApplication
 *
 * <p>/oauth2/jwks 授权端点 {@link  org.springframework.security.oauth2.server.authorization.web.NimbusJwkSetEndpointFilter}
 * <p>/oauth2/authorize 授权端点 {@link  org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationEndpointFilter}
 * <p>/.well-known/oauth-authorization-server {@link  org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationServerMetadataEndpointFilter}
 * <p>/oauth2/token 令牌端点 {@link  org.springframework.security.oauth2.server.authorization.web.OAuth2TokenEndpointFilter}
 * <p>/oauth2/register {@link  org.springframework.security.oauth2.server.authorization.web.OAuth2ClientRegistrationEndpointFilter}
 * <p>/oauth2/device_authorization {@link  org.springframework.security.oauth2.server.authorization.web.OAuth2DeviceAuthorizationEndpointFilter}
 * <p>/oauth2/device_verification {@link  org.springframework.security.oauth2.server.authorization.web.OAuth2DeviceVerificationEndpointFilter}
 * <p>/oauth2/par {@link  org.springframework.security.oauth2.server.authorization.web.OAuth2PushedAuthorizationRequestEndpointFilter}
 * <p>/oauth2/introspect {@link  org.springframework.security.oauth2.server.authorization.web.OAuth2TokenIntrospectionEndpointFilter}
 * <p>/oauth2/revoke {@link  org.springframework.security.oauth2.server.authorization.web.OAuth2TokenRevocationEndpointFilter}
 *
 * <p>/connect/register {@link  org.springframework.security.oauth2.server.authorization.oidc.web.OidcClientRegistrationEndpointFilter}
 * <p>/connect/logout {@link  org.springframework.security.oauth2.server.authorization.oidc.web.OidcLogoutEndpointFilter}
 * <p>/.well-known/openid-configuration {@link  org.springframework.security.oauth2.server.authorization.oidc.web.OidcProviderConfigurationEndpointFilter}
 * <p>/userinfo {@link  org.springframework.security.oauth2.server.authorization.oidc.web.OidcUserInfoEndpointFilter}
 *
 * <p>//login/oauth2/code/* {@link  org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter}
 *
 * <p>/oauth/confirm_access 用户批准授权的端点
 * <p>/oauth/error 用于渲染授权服务器的错误
 * <p>/oauth/check_token 资源服务器解码access token
 * <p>/oauth/jwks 当使用JWT的时候，暴露公钥的端点
 * <p>
 *
 * <pre class="code">
 * --add-opens java.base/java.lang=ALL-UNNAMED
 * --add-opens java.base/java.lang.reflect=ALL-UNNAMED
 * --add-opens java.base/java.lang.invoke=ALL-UNNAMED
 * --add-opens java.base/java.util=ALL-UNNAMED
 * --add-opens java.base/sun.net=ALL-UNNAMED
 * --add-opens java.base/java.math=ALL-UNNAMED
 * --add-opens java.base/sun.reflect.annotation=ALL-UNNAMED
 * --add-opens java.base/sun.net=ALL-UNNAMED
 * --add-opens java.desktop/sun.awt=ALL-UNNAMED
 * --add-opens java.desktop/sun.font=ALL-UNNAMED
 * --add-opens jdk.management/com.sun.management.internal=ALL-UNNAMED
 * --add-exports java.desktop/sun.awt=ALL-UNNAMED
 * --add-exports java.desktop/sun.font=ALL-UNNAMED
 * </pre>
 * <p>
 * <p>
 * <p> http://127.0.0.1:33334/oauth2/authorize?client_id=67601992f3574c75809a3d79888bf16e&response_type=code&scope=message.read&redirect_uri=http%3A%2F%2F127.0.0.1%3A8090%2Fauthorized
 * <p> http://127.0.0.1:33334/oauth2/authorize?client_id=67601992f3574c75809a3d79888bf16e&response_type=code&scope=profile&state=46ge_TeI-dHuAnyv67nVmCcAmFgCVSZAqjTi9Om-1aA=&redirect_uri=http%3A%2F%2F192.168.101.10%3A8847%2Ftaotao-cloud-upms%2Fopen%2Fauthorized&code_challenge=KJlktPdfHdPPenXDN3HARjV6pzM7ljfHs-L-bFao3zM&code_challenge_method=S256
 * <p> http://127.0.0.1:33334/oauth2/authorize?client_id=67601992f3574c75809a3d79888bf16e&response_type=code&scope=profile,read-user-by-page&redirect_uri=http%3A%2F%2F192.168.101.10%3A8847%2Ftaotao-cloud-upms%2Fopen%2Fauthorized&code_challenge=GMBkW4F_Ap4Us75TZ7nDhSUd87HXAt7cLMG-R_2VGwE&code_challenge_method=S256
 *
 * @author shuigedeng
 * @version 2022.03
 * @since 2020/4/29 15:13
 */
@MapperScan(
	basePackages = {
		"com.taotao.cloud.auth.persistent.mapper",
	})
//@EnableEnversRepositories(
//        basePackages = {
//            "com.taotao.cloud.auth.persistent.repository",
//        })
@EntityScan(
        basePackages = {
            "com.taotao.cloud.auth.persistent.persistence",
        })
@EnableJpaRepositories(
        basePackages = {
            "com.taotao.cloud.auth.persistent.repository"
        },
        repositoryFactoryBeanClass = JpaExtendRepositoryFactoryBean.class
)
@EnableConfigurationProperties({OAuth2ClientProperties.class})
@ConfigurationPropertiesScan
@EnableOauth2AuthorizationServer
@EnableRedisIndexedHttpSession
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@SpringBootApplication
public class TaoTaoCloudAuthApplication {

    public static void main(String[] args) {

		System.setProperty("com.google.protobuf.use_unsafe_pre22_gencode", "true");

		new StartupSpringApplication(TaoTaoCloudAuthApplication.class)
			.setTtcBanner()
			.setTtcProfileIfNotExists("dev")
			.setTtcApplicationProperty("taotao-cloud-auth")
			.setTtcAllowBeanDefinitionOverriding(false)
			.run(args);
    }
}

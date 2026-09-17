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

package com.taotao.cloud.auth.initializer;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 敏感词汇init
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-28 11:54:02
 */
@Component
@AllArgsConstructor
public class RegisteredClientInitializer implements ApplicationRunner {
	private final RegisteredClientRepository registeredClientRepository;

    @Override
    public void run(ApplicationArguments args) {
//		RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
//			.clientId("my-app")
//			.clientSecret("{bcrypt}my-secret")
//			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)  // 授权码模式通常配合刷新令牌
//			.redirectUri("http://127.0.0.1:33334/login/oauth2/code/my-app")  // 回调地址
//			.scope("read")
//			.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//			.build();
//		registeredClientRepository.save(client);

    }
}

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

package com.taotao.cloud.auth.biz.jpa.jackson;

import com.taotao.boot.security.spring.support.constants.JacksonConstants;
import tools.jackson.databind.module.SimpleModule;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

/**
 * <p>自定义 OAutho2 Module </p>
 *
 *
 * @since : 2022/10/24 15:51
 */
public class OAuth2TokenJacksonModule extends SimpleModule {

    public OAuth2TokenJacksonModule() {
        super(OAuth2TokenJacksonModule.class.getName(), JacksonConstants.VERSION);
    }

    @Override
    public void setupModule(SetupContext context) {

        context.setMixIn(
                ClientAuthenticationMethod.class, ClientAuthenticationMethodMixin.class);
        context.setMixIn(
                AuthorizationGrantType.class, AuthorizationGrantTypeMixin.class);
        context.setMixIn(TokenSettings.class, TokenSettingsMixin.class);
        context.setMixIn(ClientSettings.class, ClientSettingsMixin.class);
        context.setMixIn(RegisteredClient.class, RegisteredClientMixin.class);
        context.setMixIn(
                OAuth2ClientAuthenticationToken.class, OAuth2ClientAuthenticationTokenMixin.class);
    }
}

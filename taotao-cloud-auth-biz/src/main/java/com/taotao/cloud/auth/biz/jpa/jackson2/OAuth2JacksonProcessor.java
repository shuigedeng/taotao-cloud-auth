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

package com.taotao.cloud.auth.biz.jpa.jackson2;

import org.springframework.security.jackson.SecurityJacksonModules;
import org.springframework.security.oauth2.server.authorization.jackson.OAuth2AuthorizationServerJacksonModule;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.json.JsonMapper;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import static org.springframework.security.jackson.SecurityJacksonModules.getModules;

/**
 * <p>OAuth2 相关 Jackson 处理器 </p>
 *
 *
 * @since : 2023/4/29 16:05
 */
public class OAuth2JacksonProcessor {

    private static final Logger log = LoggerFactory.getLogger(OAuth2JacksonProcessor.class);

    private final JsonMapper jsonMapper;

    public OAuth2JacksonProcessor() {
		JsonMapper.Builder builder = JsonMapper.builder();
		ClassLoader classLoader = OAuth2JacksonProcessor.class.getClassLoader();
        List<JacksonModule> securityModules = getModules(classLoader);

//		JacksonModule module =
//                loadAndGetInstance(
//                        "com.taotao.cloud.auth.biz.jpa.jackson2.FormOAuth2PhoneLoginJackson2Module",
//                        classLoader);
//        securityModules.add(module);
//
		builder.addModules(securityModules);
		builder.addModules(new OAuth2AuthorizationServerJacksonModule());
		builder.addModules(new TtcJackson2Module());
		builder.addModules(new OAuth2TokenJackson2Module());
				ClassLoader loader = getClass().getClassLoader();
		jsonMapper =builder.build();
    }

    private static JacksonModule loadAndGetInstance(String className, ClassLoader loader) {
        try {
            @SuppressWarnings("unchecked")
            Class<? extends JacksonModule> securityModule =
                    (Class<? extends JacksonModule>) ClassUtils.forName(className, loader);
            return securityModule.getDeclaredConstructor().newInstance();
        } catch (Exception ignored) {
        }
        return null;
    }

    public Map<String, Object> parseMap(String data) {
        try {
            return this.jsonMapper.readValue(data, new TypeReference<>() {});
        } catch (Exception ex) {
            log.error("OAuth2 jackson processing parseMap catch error {}", ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public String writeMap(Map<String, Object> data) {
        try {
            return this.jsonMapper.writeValueAsString(data);
        } catch (Exception ex) {
            log.error("OAuth2 jackson processing writeMap catch error {}", ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}

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

package com.taotao.cloud.auth.biz.authentication.configuration;

import com.taotao.boot.cache.redis.repository.RedisRepository;
import com.taotao.boot.security.spring.authentication.login.form.FormLoginUrlConfigurer;
import com.taotao.boot.security.spring.authentication.login.social.oauth2client.SocialDelegateClientRegistrationRepository;
import com.taotao.boot.security.spring.authentication.stamp.LockedUserDetailsStampManager;
import com.taotao.boot.security.spring.authentication.stamp.SignInFailureLimitedStampManager;
import com.taotao.boot.security.spring.autoconfigure.properties.SecurityAuthenticationProperties;
import com.taotao.boot.security.spring.support.processor.AESCryptoProcessor;
import com.taotao.boot.security.spring.support.processor.HttpCryptoProcessor;
import com.taotao.boot.security.spring.support.processor.RSACryptoProcessor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.security.oauth2.client.autoconfigure.OAuth2ClientProperties;
import org.springframework.boot.security.oauth2.client.autoconfigure.OAuth2ClientPropertiesMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <p>OAuth2 认证基础模块配置 </p>
 *
 * @author shuigedeng
 * @version 2023.07
 * @since 2023-07-10 17:14:18
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({SecurityAuthenticationProperties.class})
public class OAuth2AuthenticationConfiguration {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(
        OAuth2AuthenticationConfiguration.class);

    /**
     * 后期构建
     *
     * @since 2023-07-10 17:14:18
     */
    @PostConstruct
    public void postConstruct() {
        log.info("SDK [OAuth2 Authentication] Auto Configure.");
    }

    /**
     * redis存储库
     */
    @Autowired
    private RedisRepository redisRepository;


    /**
     * http加密处理器
     *
     * @return {@link HttpCryptoProcessor }
     * @since 2023-07-10 17:14:19
     */
    @Bean
    public HttpCryptoProcessor httpCryptoProcessor() {
        return new HttpCryptoProcessor(redisRepository, new RSACryptoProcessor(),
            new AESCryptoProcessor());
    }

}

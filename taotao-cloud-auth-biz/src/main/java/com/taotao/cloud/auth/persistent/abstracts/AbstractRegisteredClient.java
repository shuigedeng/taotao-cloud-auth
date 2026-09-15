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

package com.taotao.cloud.auth.persistent.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.taotao.boot.security.spring.support.constants.DefaultConstants;
import com.taotao.boot.webagg.entity.BasePO;
import com.taotao.boot.webagg.entity.SuperPO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * <p>多实例共用 RegisteredClient属性 </p>
 *
 * @author shuigedeng
 * @version 2023.07
 * @since 2023-07-10 17:11:33
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractRegisteredClient<T extends SuperPO<T>> extends BasePO<T> implements RegisteredClientDetails {


    @Schema(name = "客户端ID发布日期", title = "客户端发布日期")
    @JsonFormat(
            pattern = DefaultConstants.DATE_TIME_FORMAT,
            locale = "GMT+8",
            shape = JsonFormat.Shape.STRING)
    @Column(name = "client_id_issued_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime clientIdIssuedAt;

    /**
     * 客户端秘密在
     */
    @Schema(name = "客户端秘钥过期时间", title = "客户端秘钥过期时间")
    @JsonFormat(
            pattern = DefaultConstants.DATE_TIME_FORMAT,
            locale = "GMT+8",
            shape = JsonFormat.Shape.STRING)
    @Column(name = "client_secret_expires_at")
    private LocalDateTime clientSecretExpiresAt;

    /**
     * 客户端身份验证方法
     */
    //    @JsonDeserialize(using = SetToCommaDelimitedStringDeserializer.class)
    //    @JsonSerialize(using = CommaDelimitedStringToSetSerializer.class)
    @Schema(name = "客户端认证模式", title = "支持多个值，以逗号分隔")
    @Column(name = "client_authentication_methods", nullable = false, length = 1000)
    private String clientAuthenticationMethods;

    /**
     * 授权授权类型
     */
    //    @JsonDeserialize(using = SetToCommaDelimitedStringDeserializer.class)
    //    @JsonSerialize(using = CommaDelimitedStringToSetSerializer.class)
    @Schema(name = "认证模式", title = "支持多个值，以逗号分隔")
    @Column(name = "authorization_grant_types", nullable = false, length = 1000)
    private String authorizationGrantTypes;

    /**
     * 重定向uri
     */
    @Schema(name = "回调地址", title = "支持多个值，以逗号分隔")
    @Column(name = "redirect_uris", length = 1000)
    private String redirectUris;

    /**
     * 发布注销重定向uri
     */
    @Schema(name = "OIDC Logout 回调地址", title = "支持多个值，以逗号分隔")
    @Column(name = "post_logout_redirect_uris", length = 1000)
    private String postLogoutRedirectUris;

    /**
     * 获取客户端id
     *
     * @return {@link LocalDateTime }
     * @since 2023-07-10 17:11:34
     */
    @Override
    public LocalDateTime getClientIdIssuedAt() {
        return clientIdIssuedAt;
    }


    /**
     * 获取客户端秘密到期时间为
     *
     * @return {@link LocalDateTime }
     * @since 2023-07-10 17:11:35
     */
    @Override
    public LocalDateTime getClientSecretExpiresAt() {
        return clientSecretExpiresAt;
    }


    @Override
    public String getClientAuthenticationMethods() {
        return clientAuthenticationMethods;
    }

    @Override
    public String getAuthorizationGrantTypes() {
        return authorizationGrantTypes;
    }


    @Override
    public String getRedirectUris() {
        return redirectUris;
    }

    @Override
    public String getPostLogoutRedirectUris() {
        return postLogoutRedirectUris;
    }

}

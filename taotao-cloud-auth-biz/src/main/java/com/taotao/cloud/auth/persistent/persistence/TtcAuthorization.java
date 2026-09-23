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

package com.taotao.cloud.auth.persistent.persistence;

import com.baomidou.mybatisplus.annotation.TableName;
import com.taotao.boot.webagg.entity.BasePO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>OAuth2 认证信息 </p>
 *
 * @author shuigedeng
 * @version 2023.07
 * @since 2023-07-10 17:11:47
 */
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
	name = TtcAuthorization.TABLE_NAME,
	indexes = {
		@Index(name = "idx_create_time", columnList = "create_time"),
		@Index(name = "idx_registered_client_id", columnList = "registered_client_id"),
		@Index(name = "idx_principal_name", columnList = "principal_name")
	})
@TableName(TtcAuthorization.TABLE_NAME)
@org.springframework.data.relational.core.mapping.Table(name = TtcAuthorization.TABLE_NAME)
//@Cacheable
//@org.hibernate.annotations.Cache(
//	usage = CacheConcurrencyStrategy.READ_WRITE,
//	region = OAuth2Constants.REGION_OAUTH2_AUTHORIZATION)
public class TtcAuthorization  extends BasePO<TtcAuthorization> {

	public static final String TABLE_NAME = "ttc_oauth2_authorization";

    @Column(name = "registered_client_id", nullable = false, length = 100,comment = "注册客户端id")
    private String registeredClientId;

    @Column(name = "principal_name", nullable = false, length = 200,comment = "主体名称")
    private String principalName;

    @Column(name = "authorization_grant_type", nullable = false, length = 100,comment = "授权授权类型")
    private String authorizationGrantType;

    /**
     * 授权范围
     */
    @Column(name = "authorized_scopes", length = 1000)
    private String authorizedScopes;

    /**
     * 属性
     */
    @Column(name = "attributes", columnDefinition = "TEXT")
    private String attributes;

    /**
     * 州
     */
    @Column(name = "state", length = 500)
    private String state;

    /**
     * 授权代码值
     */
    @Column(name = "authorization_code_value", columnDefinition = "TEXT")
    private String authorizationCodeValue;

    /**
     * 授权码发布于
     */
    @Column(name = "authorization_code_issued_at")
    private LocalDateTime authorizationCodeIssuedAt;

    /**
     * 授权码在
     */
    @Column(name = "authorization_code_expires_at")
    private LocalDateTime authorizationCodeExpiresAt;

    /**
     * 授权码元数据
     */
    @Column(name = "authorization_code_metadata", columnDefinition = "TEXT")
    private String authorizationCodeMetadata;

    /**
     * 访问令牌值
     */
    @Column(name = "access_token_value", columnDefinition = "TEXT")
    private String accessTokenValue;

    /**
     * 访问令牌在
     */
    @Column(name = "access_token_issued_at")
    private LocalDateTime accessTokenIssuedAt;

    /**
     * 访问令牌在
     */
    @Column(name = "access_token_expires_at")
    private LocalDateTime accessTokenExpiresAt;

    /**
     * 访问令牌元数据
     */
    @Column(name = "access_token_metadata", columnDefinition = "TEXT")
    private String accessTokenMetadata;

    /**
     * 访问令牌类型
     */
    @Column(name = "access_token_type", length = 100)
    private String accessTokenType;

    /**
     * 访问令牌范围
     */
    @Column(name = "access_token_scopes", length = 1000)
    private String accessTokenScopes;

    /**
     * oidc id令牌值
     */
    @Column(name = "oidc_id_token_value", columnDefinition = "TEXT")
    private String oidcIdTokenValue;

    /**
     * oidc id令牌在
     */
    @Column(name = "oidc_id_token_issued_at")
    private LocalDateTime oidcIdTokenIssuedAt;

    /**
     * oidc id令牌在
     */
    @Column(name = "oidc_id_token_expires_at")
    private LocalDateTime oidcIdTokenExpiresAt;

    /**
     * oidc id令牌元数据
     */
    @Column(name = "oidc_id_token_metadata", columnDefinition = "TEXT")
    private String oidcIdTokenMetadata;

    /**
     * oidc id令牌声明
     */
    @Column(name = "oidc_id_token_claims", length = 2000)
    private String oidcIdTokenClaims;

    /**
     * 刷新令牌值
     */
    @Column(name = "refresh_token_value", columnDefinition = "TEXT")
    private String refreshTokenValue;

    /**
     * 刷新在
     */
    @Column(name = "refresh_token_issued_at")
    private LocalDateTime refreshTokenIssuedAt;

    /**
     * 刷新令牌在
     */
    @Column(name = "refresh_token_expires_at")
    private LocalDateTime refreshTokenExpiresAt;

    /**
     * 刷新令牌元数据
     */
    @Column(name = "refresh_token_metadata", columnDefinition = "TEXT")
    private String refreshTokenMetadata;

    /**
     * 用户代码值
     */
    @Column(name = "user_code_value", columnDefinition = "TEXT")
    private String userCodeValue;

    /**
     * 用户代码发布于
     */
    @Column(name = "user_code_issued_at")
    private LocalDateTime userCodeIssuedAt;

    /**
     * 用户代码在
     */
    @Column(name = "user_code_expires_at")
    private LocalDateTime userCodeExpiresAt;

    /**
     * 用户代码元数据
     */
    @Column(name = "user_code_metadata", columnDefinition = "TEXT")
    private String userCodeMetadata;

    /**
     * 设备代码值
     */
    @Column(name = "device_code_value", columnDefinition = "TEXT")
    private String deviceCodeValue;

    /**
     * 设备代码发布于
     */
    @Column(name = "device_code_issued_at")
    private LocalDateTime deviceCodeIssuedAt;

    /**
     * 设备代码在
     */
    @Column(name = "device_code_expires_at")
    private LocalDateTime deviceCodeExpiresAt;

    /**
     * 设备代码元数据
     */
    @Column(name = "device_code_metadata", columnDefinition = "TEXT")
    private String deviceCodeMetadata;

}

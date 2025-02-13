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

package com.taotao.cloud.auth.infrastructure.persistent.authorization.repository;

import com.taotao.boot.webagg.repository.BaseInterfaceSuperRepository;
import com.taotao.cloud.auth.infrastructure.persistent.authorization.persistence.AuthorizationPO;
import jakarta.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>TtcAuthorizationRepository </p>
 *
 * @author shuigedeng
 * @version 2023.07
 * @since 2023-07-10 17:11:21
 */
public interface TtcAuthorizationRepository extends
	BaseInterfaceSuperRepository<AuthorizationPO, String> {

	/**
	 * 根据 State 查询 OAuth2 认证信息
	 *
	 * @param state OAuth2 Authorization Code 模式参数 State
	 * @return {@link Optional }<{@link AuthorizationPO }>
	 * @since 2023-07-10 17:11:21
	 */
	@QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
	Optional<AuthorizationPO> findByState(String state);

	/**
	 * 根据 authorizationCode 查询 OAuth2 认证信息
	 *
	 * @param authorizationCode OAuth2 Authorization Code 模式参数 code
	 * @return {@link Optional }<{@link AuthorizationPO }>
	 * @since 2023-07-10 17:11:21
	 */
	@QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
	Optional<AuthorizationPO> findByAuthorizationCodeValue(String authorizationCode);

	/**
	 * 根据 Access Token 查询 OAuth2 认证信息
	 *
	 * @param accessToken OAuth2 accessToken
	 * @return {@link Optional }<{@link AuthorizationPO }>
	 * @since 2023-07-10 17:11:21
	 */
	@QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
	Optional<AuthorizationPO> findByAccessTokenValue(String accessToken);

	/**
	 * 根据 Refresh Token 查询 OAuth2 认证信息
	 *
	 * @param refreshToken OAuth2 refreshToken
	 * @return {@link Optional }<{@link AuthorizationPO }>
	 * @since 2023-07-10 17:11:21
	 */
	@QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
	Optional<AuthorizationPO> findByRefreshTokenValue(String refreshToken);

	/**
	 * 根据 Id Token 查询 OAuth2 认证信息
	 *
	 * @param idToken OAuth2 idToken
	 * @return {@link Optional }<{@link AuthorizationPO }>
	 * @since 2023-07-10 17:11:21
	 */
	@QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
	Optional<AuthorizationPO> findByOidcIdTokenValue(String idToken);

	/**
	 * 根据 User Code 查询 OAuth2 认证信息
	 *
	 * @param userCode OAuth2 userCode
	 * @return {@link Optional }<{@link AuthorizationPO }>
	 * @since 2023-07-10 17:11:21
	 */
	@QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
	Optional<AuthorizationPO> findByUserCodeValue(String userCode);

	/**
	 * 根据 Device Code 查询 OAuth2 认证信息
	 *
	 * @param deviceCode OAuth2 deviceCode
	 * @return {@link Optional }<{@link AuthorizationPO }>
	 * @since 2023-07-10 17:11:21
	 */
	@QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
	Optional<AuthorizationPO> findByDeviceCodeValue(String deviceCode);

	/**
	 * 根据客户端ID和用户名查询未过期Token
	 *
	 * @param registeredClientId 客户端ID
	 * @param principalName      用户名称
	 * @param localDateTime      时间
	 * @return {@link List }<{@link AuthorizationPO }>
	 * @since 2023-07-10 17:11:21
	 */
	List<AuthorizationPO> findAllByRegisteredClientIdAndPrincipalNameAndAccessTokenExpiresAtAfter(
		String registeredClientId, String principalName, LocalDateTime localDateTime);

	/**
	 * 根据 RefreshToken 过期时间，清理历史 Token信息
	 * <p>
	 * OAuth2Authorization 表中存在 AccessToken、OidcToken、RefreshToken 等三个过期时间。
	 * 正常的删除逻辑应该是三个过期时间都已经过期才行。但是有特殊情况： 1. OidcToken 的过期时间有可能为空，这就增加了 SQL 处理的复杂度。 2. 逻辑上
	 * RefreshToken 的过期应该是最长的(这是默认配置正确的情况) 因此，目前就简单的根据 RefreshToken过期时间进行处理
	 *
	 * @param localDateTime 时间
	 * @since 2023-07-10 17:11:21
	 */
	@Modifying
	@Transactional
	void deleteByRefreshTokenExpiresAtBefore(LocalDateTime localDateTime);

	/**
	 * 更新由
	 *
	 * @param authorizationPO 希罗多德授权
	 * @return {@link Integer }
	 * @since 2023-07-10 17:11:22
	 */
	@Modifying
	@Transactional
	@Query("""
		  update AuthorizationPO set
			registeredClientId = :#{#authorizationPO.registeredClientId} ,
			principalName = :#{#authorizationPO.principalName} ,
			authorizationGrantType = :#{#authorizationPO.authorizationGrantType} ,
			authorizedScopes = :#{#authorizationPO.authorizedScopes} ,
			attributes = :#{#authorizationPO.attributes} ,
			state = :#{#authorizationPO.state} ,
			authorizationCodeValue = :#{#authorizationPO.authorizationCodeValue} ,
			authorizationCodeIssuedAt = :#{#authorizationPO.authorizationCodeIssuedAt} ,
			authorizationCodeExpiresAt = :#{#authorizationPO.authorizationCodeExpiresAt} ,
			authorizationCodeMetadata = :#{#authorizationPO.authorizationCodeMetadata} ,
			accessTokenValue = :#{#authorizationPO.accessTokenValue} ,
			accessTokenIssuedAt = :#{#authorizationPO.accessTokenIssuedAt} ,
			accessTokenExpiresAt = :#{#authorizationPO.accessTokenExpiresAt} ,
			accessTokenMetadata = :#{#authorizationPO.accessTokenMetadata} ,
			accessTokenType = :#{#authorizationPO.accessTokenType} ,
			accessTokenScopes = :#{#authorizationPO.accessTokenScopes} ,
			oidcIdTokenValue = :#{#authorizationPO.oidcIdTokenValue} ,
			oidcIdTokenIssuedAt = :#{#authorizationPO.oidcIdTokenIssuedAt} ,
			oidcIdTokenExpiresAt = :#{#authorizationPO.oidcIdTokenExpiresAt} ,
			oidcIdTokenMetadata = :#{#authorizationPO.oidcIdTokenMetadata} ,
			oidcIdTokenClaims = :#{#authorizationPO.oidcIdTokenClaims} ,
			refreshTokenValue = :#{#authorizationPO.refreshTokenValue} ,
			refreshTokenIssuedAt = :#{#authorizationPO.refreshTokenIssuedAt} ,
			refreshTokenExpiresAt = :#{#authorizationPO.refreshTokenExpiresAt} ,
			refreshTokenMetadata = :#{#authorizationPO.refreshTokenMetadata} ,
			userCodeValue = :#{#authorizationPO.userCodeValue} ,
			userCodeIssuedAt = :#{#authorizationPO.userCodeIssuedAt} ,
			userCodeExpiresAt = :#{#authorizationPO.userCodeExpiresAt} ,
			userCodeMetadata = :#{#authorizationPO.userCodeMetadata} ,
			deviceCodeValue = :#{#authorizationPO.deviceCodeValue} ,
			deviceCodeIssuedAt = :#{#authorizationPO.deviceCodeIssuedAt} ,
			deviceCodeExpiresAt = :#{#authorizationPO.deviceCodeExpiresAt} ,
			deviceCodeMetadata =  :#{#authorizationPO.deviceCodeMetadata}
			where id = :#{#authorizationPO.id}
		""")
	Integer updateBy(
		@Param("authorizationPO") AuthorizationPO authorizationPO);
}

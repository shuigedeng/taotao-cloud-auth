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

package com.taotao.cloud.auth.infrastructure.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Set;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class JwtUtils {

	private JwtUtils() {
	}

	public static JwsHeader.Builder headers() {
		return JwsHeader.with(SignatureAlgorithm.RS256);
	}

	public static JwtClaimsSet.Builder accessTokenClaims(
		RegisteredClient registeredClient, String issuer, String subject,
		Set<String> authorizedScopes) {

		Instant issuedAt = Instant.now();
		Instant expiresAt = issuedAt.plus(
			registeredClient.getTokenSettings().getAccessTokenTimeToLive());

		// @formatter:off
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder();
        if (StringUtils.hasText(issuer)) {
            claimsBuilder.issuer(issuer);
        }
        claimsBuilder
                .subject(subject)
                .audience(Collections.singletonList(registeredClient.getClientId()))
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .notBefore(issuedAt);
        if (!CollectionUtils.isEmpty(authorizedScopes)) {
            claimsBuilder.claim(OAuth2ParameterNames.SCOPE, authorizedScopes);
        }
        // @formatter:on

		return claimsBuilder;
	}

	public static JwtClaimsSet.Builder idTokenClaims(
		RegisteredClient registeredClient, String issuer, String subject, String nonce) {

		Instant issuedAt = Instant.now();
		// TODO Allow configuration for ID Token time-to-live
		Instant expiresAt = issuedAt.plus(30, ChronoUnit.MINUTES);

		// @formatter:off
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder();
        if (StringUtils.hasText(issuer)) {
            claimsBuilder.issuer(issuer);
        }
        claimsBuilder
                .subject(subject)
                .audience(Collections.singletonList(registeredClient.getClientId()))
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .claim(IdTokenClaimNames.AZP, registeredClient.getClientId());
        if (StringUtils.hasText(nonce)) {
            claimsBuilder.claim(IdTokenClaimNames.NONCE, nonce);
        }
        // TODO Add 'auth_time' claim
        // @formatter:on

		return claimsBuilder;
	}


}

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

package com.taotao.cloud.auth.infrastructure.authorization.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.taotao.cloud.auth.infrastructure.persistent.authorization.persistence.TtcAuthorizationPO;
import com.taotao.cloud.auth.infrastructure.persistent.authorization.repository.TtcAuthorizationRepository;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * <p>TtcAuthorization </p>
 * <p>
 * 这里命名没有按照统一的习惯，主要是为了防止与 spring-authorization-server 已有类的同名而导致Bean注入失败
 *
 * @author shuigedeng
 * @version 2023.07
 * @since 2023-07-10 17:10:00
 */
@Service
public class TtcAuthorizationService {

	/**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger(TtcAuthorizationService.class);

	/**
	 * 希罗多德授权库
	 */
	private final TtcAuthorizationRepository ttcAuthorizationRepository;

	/**
	 * 希罗多德授权服务
	 *
	 * @param ttcAuthorizationRepository 希罗多德授权库
	 * @since 2023-07-10 17:10:00
	 */
	@Autowired
	public TtcAuthorizationService(TtcAuthorizationRepository ttcAuthorizationRepository) {
		this.ttcAuthorizationRepository = ttcAuthorizationRepository;
	}

	/**
	 * 按状态查找
	 *
	 * @param state 州
	 * @return {@link Optional }<{@link TtcAuthorizationPO }>
	 * @since 2023-07-10 17:10:01
	 */
	public Optional<TtcAuthorizationPO> findByState(String state) {
		Optional<TtcAuthorizationPO> result = this.ttcAuthorizationRepository.findByState(state);
		log.info("TtcAuthorization Service findByState.");
		return result;
	}

	/**
	 * 按授权码查找
	 *
	 * @param authorizationCode 授权代码
	 * @return {@link Optional }<{@link TtcAuthorizationPO }>
	 * @since 2023-07-10 17:10:01
	 */
	public Optional<TtcAuthorizationPO> findByAuthorizationCode(String authorizationCode) {
		Optional<TtcAuthorizationPO> result =
			this.ttcAuthorizationRepository.findByAuthorizationCodeValue(authorizationCode);
		log.info("TtcAuthorization Service findByAuthorizationCode.");
		return result;
	}

	/**
	 * 通过访问令牌查找
	 *
	 * @param accessToken 访问令牌
	 * @return {@link Optional }<{@link TtcAuthorizationPO }>
	 * @since 2023-07-10 17:10:02
	 */
	public Optional<TtcAuthorizationPO> findByAccessToken(String accessToken) {
		Optional<TtcAuthorizationPO> result =
			this.ttcAuthorizationRepository.findByAccessTokenValue(accessToken);
		log.info("TtcAuthorization Service findByAccessToken.");
		return result;
	}

	/**
	 * 通过刷新令牌查找
	 *
	 * @param refreshToken 刷新令牌
	 * @return {@link Optional }<{@link TtcAuthorizationPO }>
	 * @since 2023-07-10 17:10:03
	 */
	public Optional<TtcAuthorizationPO> findByRefreshToken(String refreshToken) {
		Optional<TtcAuthorizationPO> result =
			this.ttcAuthorizationRepository.findByRefreshTokenValue(refreshToken);
		log.info("TtcAuthorization Service findByRefreshToken.");
		return result;
	}

	/**
	 * 按oidc标识令牌值查找
	 *
	 * @param idToken id令牌
	 * @return {@link Optional }<{@link TtcAuthorizationPO }>
	 * @since 2023-07-10 17:10:03
	 */
	public Optional<TtcAuthorizationPO> findByOidcIdTokenValue(String idToken) {
		Optional<TtcAuthorizationPO> result = this.ttcAuthorizationRepository.findByOidcIdTokenValue(
			idToken);
		log.info("TtcAuthorization Service findByOidcIdTokenValue.");
		return result;
	}

	/**
	 * 按用户代码值查找
	 *
	 * @param userCode 用户代码
	 * @return {@link Optional }<{@link TtcAuthorizationPO }>
	 * @since 2023-07-10 17:10:04
	 */
	public Optional<TtcAuthorizationPO> findByUserCodeValue(String userCode) {
		Optional<TtcAuthorizationPO> result = this.ttcAuthorizationRepository.findByUserCodeValue(
			userCode);
		log.info("TtcAuthorization Service findByUserCodeValue.");
		return result;
	}

	/**
	 * 按设备代码值查找
	 *
	 * @param deviceCode 设备代码
	 * @return {@link Optional }<{@link TtcAuthorizationPO }>
	 * @since 2023-07-10 17:10:05
	 */
	public Optional<TtcAuthorizationPO> findByDeviceCodeValue(String deviceCode) {
		Optional<TtcAuthorizationPO> result =
			this.ttcAuthorizationRepository.findByDeviceCodeValue(deviceCode);
		log.info("TtcAuthorization Service findByDeviceCodeValue.");
		return result;
	}

	/**
	 * 按状态或授权代码值或访问令牌值或刷新令牌值或oidc id令牌值或用户代码值或设备代码值查找
	 *
	 * @param token 令牌
	 * @return {@link Optional }<{@link TtcAuthorizationPO }>
	 * @since 2023-07-10 17:10:05
	 */
	public Optional<TtcAuthorizationPO>
	findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(
		String token) {

		Specification<TtcAuthorizationPO> specification = (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get("state"), token));
			predicates.add(criteriaBuilder.equal(root.get("authorizationCodeValue"), token));
			predicates.add(criteriaBuilder.equal(root.get("accessTokenValue"), token));
			predicates.add(criteriaBuilder.equal(root.get("refreshTokenValue"), token));
			predicates.add(criteriaBuilder.equal(root.get("oidcIdTokenValue"), token));
			predicates.add(criteriaBuilder.equal(root.get("userCodeValue"), token));
			predicates.add(criteriaBuilder.equal(root.get("deviceCodeValue"), token));

			Predicate[] predicateArray = new Predicate[predicates.size()];
			criteriaQuery.where(criteriaBuilder.or(predicates.toArray(predicateArray)));
			return criteriaQuery.getRestriction();
		};

		Optional<TtcAuthorizationPO> result = this.ttcAuthorizationRepository.findOne(specification);
		log.info("TtcAuthorization Service findByDetection.");
		return result;
	}

	/**
	 * 清除历史标记
	 *
	 * @since 2023-07-10 17:10:06
	 */
	public void clearHistoryToken() {
		this.ttcAuthorizationRepository.deleteByRefreshTokenExpiresAtBefore(LocalDateTime.now());
		log.info("TtcAuthorization Service clearExpireAccessToken.");
	}

	/**
	 * 查找可用授权
	 *
	 * @param registeredClientId 注册客户端id
	 * @param principalName      主体名称
	 * @return {@link List }<{@link TtcAuthorizationPO }>
	 * @since 2023-07-10 17:10:06
	 */
	public List<TtcAuthorizationPO> findAvailableAuthorizations(String registeredClientId,
																String principalName) {
		List<TtcAuthorizationPO> authorizations = this.ttcAuthorizationRepository
			.findAllByRegisteredClientIdAndPrincipalNameAndAccessTokenExpiresAtAfter(
				registeredClientId, principalName, LocalDateTime.now());
		log.info("TtcAuthorization Service findAvailableAuthorizations.");
		return authorizations;
	}

	/**
	 * 查找授权计数
	 *
	 * @param registeredClientId 注册客户端id
	 * @param principalName      主体名称
	 * @return int
	 * @since 2023-07-10 17:10:07
	 */
	public int findAuthorizationCount(String registeredClientId, String principalName) {
		List<TtcAuthorizationPO> authorizations = findAvailableAuthorizations(registeredClientId,
			principalName);
		int count = 0;
		if (CollectionUtils.isNotEmpty(authorizations)) {
			count = authorizations.size();
		}
		log.info("TtcAuthorization Service current authorization count is [{}].", count);
		return count;
	}

	/**
	 * @param entity 实体
	 * @since 2023-07-10 17:10:08
	 */
	public void saveAndFlush(TtcAuthorizationPO entity) {
		ttcAuthorizationRepository.save(entity);
	}

	/**
	 * 按id删除
	 *
	 * @param id id
	 * @since 2023-07-10 17:10:08
	 */
	public void deleteById(String id) {
		ttcAuthorizationRepository.deleteById(id);
	}

	/**
	 * 按id查找
	 *
	 * @param id id
	 * @return {@link TtcAuthorizationPO }
	 * @since 2023-07-10 17:10:09
	 */
	public TtcAuthorizationPO findById(String id) {
		return ttcAuthorizationRepository.findById(id).orElse(null);
	}

	/**
	 * 更新和刷新
	 *
	 * @param entity 实体
	 * @since 2023-07-10 17:10:09
	 */
	public void updateAndFlush(TtcAuthorizationPO entity) {
		TtcAuthorizationPO existingAuthorization = this.findById(entity.getId());
		BeanUtil.copyProperties(
			entity, existingAuthorization, CopyOptions.create().ignoreNullValue());
		// 更新数据
		ttcAuthorizationRepository.updateBy(existingAuthorization);
	}
}

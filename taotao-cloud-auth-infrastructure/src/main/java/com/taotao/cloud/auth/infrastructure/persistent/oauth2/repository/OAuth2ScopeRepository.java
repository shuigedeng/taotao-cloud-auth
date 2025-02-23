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

package com.taotao.cloud.auth.infrastructure.persistent.oauth2.repository;

import com.taotao.boot.webagg.repository.BaseInterfaceSuperRepository;
import com.taotao.cloud.auth.infrastructure.persistent.oauth2.persistence.OAuth2ScopePO;
import java.util.List;

/**
 * <p> Description : OauthScopeRepository </p>
 *
 * @since : 2020/3/19 16:57
 */
public interface OAuth2ScopeRepository extends BaseInterfaceSuperRepository<OAuth2ScopePO, String> {

	/**
	 * 根据范围代码查询应用范围
	 *
	 * @param scopeCode 范围代码
	 * @return 应用范围 {@link OAuth2ScopePO}
	 */
	OAuth2ScopePO findByScopeCode(String scopeCode);

	/**
	 * 根据 scope codes 查询对应的对象列表
	 *
	 * @param scopeCodes 范围代码
	 * @return 对象列表
	 */
	List<OAuth2ScopePO> findByScopeCodeIn(List<String> scopeCodes);
}

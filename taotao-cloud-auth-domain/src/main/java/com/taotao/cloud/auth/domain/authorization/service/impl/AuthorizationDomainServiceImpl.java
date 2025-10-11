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

package com.taotao.cloud.auth.domain.authorization.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.taotao.boot.common.model.request.PageQuery;
import com.taotao.cloud.auth.domain.authorization.entity.Authorization;
import com.taotao.cloud.auth.domain.authorization.repository.AuthorizationRepository;
import com.taotao.cloud.auth.domain.authorization.service.AuthorizationDomainService;

public class AuthorizationDomainServiceImpl implements AuthorizationDomainService {

    private AuthorizationRepository authorizationRepository;

    @Override
    public Boolean insert(Authorization authorization) {
        return null;
    }

    @Override
    public Boolean update(Authorization authorization) {
        return null;
    }

    @Override
    public Authorization getById(Long id) {
        return null;
    }

    @Override
    public Boolean deleteById(Long id) {
        return null;
    }

    @Override
    public IPage<Authorization> list(Authorization authorization, PageQuery pageQuery) {
        //		return deptRepository.list(deptEntity, pageQuery);
        return null;
    }
}

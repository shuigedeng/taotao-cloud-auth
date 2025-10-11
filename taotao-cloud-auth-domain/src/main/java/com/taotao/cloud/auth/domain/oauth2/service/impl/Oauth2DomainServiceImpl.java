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

package com.taotao.cloud.auth.domain.oauth2.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.taotao.boot.common.model.request.PageQuery;
import com.taotao.cloud.auth.domain.oauth2.entity.Oauth2;
import com.taotao.cloud.auth.domain.oauth2.repository.Oauth2Repository;
import com.taotao.cloud.auth.domain.oauth2.service.Oauth2DomainService;

public class Oauth2DomainServiceImpl implements Oauth2DomainService {

    private Oauth2Repository oauth2Repository;

    @Override
    public Boolean insert(Oauth2 oauth2) {
        return null;
    }

    @Override
    public Boolean update(Oauth2 oauth2) {
        return null;
    }

    @Override
    public Oauth2 getById(Long id) {
        return null;
    }

    @Override
    public Boolean deleteById(Long id) {
        return null;
    }

    @Override
    public IPage<Oauth2> list(Oauth2 oauth2, PageQuery pageQuery) {
        //		return deptRepository.list(deptEntity, pageQuery);
        return null;
    }
}

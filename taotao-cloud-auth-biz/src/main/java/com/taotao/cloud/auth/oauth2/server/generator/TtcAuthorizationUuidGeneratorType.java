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

package com.taotao.cloud.auth.oauth2.server.generator;

import com.taotao.boot.common.utils.lang.StringUtils;
import com.taotao.boot.data.jpa.hibernate.identifier.AbstractUuidGenerator;
import com.taotao.cloud.auth.oauth2.server.entity.TtcAuthorization;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.lang.reflect.Member;

/**
 * <p>OAuth2Authorization Id 生成器 </p>
 * <p>
 * 指定ID生成器，解决实体ID无法手动设置问题。
 *
 */
public class TtcAuthorizationUuidGeneratorType extends AbstractUuidGenerator {

    public TtcAuthorizationUuidGeneratorType( Member idMember) {
        super(idMember);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object)
            throws HibernateException {

        if (ObjectUtils.isEmpty(object)) {
            throw new HibernateException(new NullPointerException());
        }

        TtcAuthorization ttcAuthorization = (TtcAuthorization) object;

        if (StringUtils.isEmpty(ttcAuthorization.getId())) {
            return super.generate(session, object);
        } else {
            return ttcAuthorization.getId();
        }
    }
}

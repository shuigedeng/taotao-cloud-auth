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

package com.taotao.cloud.auth.biz.jpa.jackson;

import com.taotao.boot.security.spring.support.constants.JacksonConstants;
import com.taotao.boot.security.spring.support.core.details.TtcUser;
import tools.jackson.databind.module.SimpleModule;
import com.taotao.boot.security.spring.authentication.login.form.FormLoginWebAuthenticationDetails;
import com.taotao.boot.security.spring.support.core.authority.TtcGrantedAuthority;

/**
 * <p>自定义 User Details Module </p>
 *
 *
 * @since : 2022/2/17 23:39
 */
public class TtcJacksonModule extends SimpleModule {

    public TtcJacksonModule() {
        super(TtcJacksonModule.class.getName(), JacksonConstants.VERSION);
    }

    @Override
    public void setupModule(SetupContext context) {


        context.setMixIn(TtcUser.class, TtcUserMixin.class);
        context.setMixIn(TtcGrantedAuthority.class, TtcGrantedAuthorityMixin.class);
        context.setMixIn(
                FormLoginWebAuthenticationDetails.class,
                FormLoginWebAuthenticationDetailsMixin.class);
    }
}

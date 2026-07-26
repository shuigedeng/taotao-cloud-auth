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

package com.taotao.cloud.auth.authentication.details.strategy.local;

import com.taotao.cloud.auth.authentication.details.strategy.user.SysSocialUser;
import org.springframework.stereotype.Service;

/**
 * <p>社会化登录用户服务 </p>
 *
 *
 * @since : 2021/5/16 16:29
 */
@Service
public class SysSocialUserService {

    //    private final SysSocialUserRepository sysSocialUserRepository;

    //    public SysSocialUserService(SysSocialUserRepository sysSocialUserRepository) {
    //        this.sysSocialUserRepository = sysSocialUserRepository;
    //    }

    /**
     * 根据条件查询
     *
     * @param uuid uuid
     * @param source 来源
     * @return SysSocialUser
     * @since 2022.03
     */
    public SysSocialUser findByUuidAndSource(String uuid, String source) {
        //        return sysSocialUserRepository.findSysSocialUserByUuidAndSource(uuid, source);
        return null;
    }

    /**
     * 保存
     *
     * @param sysSocialUser sysSocialUser
     * @return 无返回值
     * @since 2022.03
     */
    public void saveAndFlush( SysSocialUser sysSocialUser) {}
}

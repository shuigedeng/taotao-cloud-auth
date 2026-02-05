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

package com.taotao.cloud.auth.biz.management.details.user.strategy.remote;

import com.taotao.boot.security.spring.support.core.AccessPrincipal;
import com.taotao.boot.security.spring.support.core.authority.TtcGrantedAuthority;
import com.taotao.boot.security.spring.support.core.details.TtcUser;
import com.taotao.cloud.auth.biz.management.details.user.strategy.AbstractStrategyUserDetailsService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>UserDetail远程调用服务 </p>
 */
public class RemoteUserDetailsService extends AbstractStrategyUserDetailsService {

//    private final UserQueryApi userApi;
//
//    public RemoteUserDetailsService( UserQueryApi userApi) {
//        this.userApi = userApi;
//    }

    @Override
    public TtcUser findUserDetailsByUsername(String userName) throws UsernameNotFoundException {
        //		Result<SysUser> result = remoteUserDetailsService.findByUserName(userName);
        //
        //		SysUser sysUser = result.getData();
        //		return this.convertSysUser(sysUser, userName);
        //		return TtcUser.defaultTest();

//        Collection<TtcGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new TtcGrantedAuthority("manager.book.read"));
//        authorities.add(new TtcGrantedAuthority("manager.book.write"));
//        Set<String> roles = new HashSet<>();
//        roles.add("ROLE_A1");
//        roles.add("ROLE_A2");
//        // admin/123456
//        TtcUser user =
//                new TtcUser(
//                        1L,
//                        "admin",
//                        "{bcrypt}$2a$10$lvjys/FAHAVmgXM.U1LtOOJ./C5SstExZCZ0Z5N7SeGZAue0JFtXC",
//                        true,
//                        true,
//                        true,
//                        true,
//                        authorities);
//        return user;
		return TtcUser.defaultTest();
    }

    @Override
    public TtcUser findUserDetailsBySocial(String source, AccessPrincipal accessPrincipal) {
        //		Result<TtcUser> result = remoteSocialDetailsService.findUserDetailsBySocial(source,
        // accessPrincipal);
        //		return result.getData();
        return TtcUser.defaultTest();
    }
}

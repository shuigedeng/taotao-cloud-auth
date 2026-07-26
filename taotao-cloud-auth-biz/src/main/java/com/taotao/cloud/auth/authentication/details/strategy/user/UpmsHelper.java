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

package com.taotao.cloud.auth.authentication.details.strategy.user;

import com.taotao.boot.common.enums.DataItemStatusEnum;
import com.taotao.boot.security.spring.support.core.authority.TtcGrantedAuthority;
import com.taotao.boot.security.spring.support.core.details.TtcUser;
import com.taotao.boot.security.spring.support.utils.SecurityUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>实体转换帮助类 </p>
 */
public class UpmsHelper {

    /**
     * 转换
     *
     * @param sysUser sysUser
     * @return TtcUser
     * @since 2022.03
     */
    public static TtcUser convertSysUserToTtcUser( SysUser sysUser) {
        Set<TtcGrantedAuthority> authorities = new HashSet<>();
        Set<String> roles = new HashSet<>();
        for (SysRole sysRole : sysUser.getRoles()) {
            roles.add(sysRole.getRoleCode());
            authorities.add(
                    new TtcGrantedAuthority(
                            SecurityUtils.wellFormRolePrefix(sysRole.getRoleCode())));
            Set<SysPermission> sysPermissions = sysRole.getPermissions();
            if (CollectionUtils.isNotEmpty(sysPermissions)) {
                sysPermissions.forEach(
                        sysAuthority ->
                                authorities.add(
                                        new TtcGrantedAuthority(
                                                (sysAuthority.getPermissionCode()))));
            }
        }

        String employeeId =
                ObjectUtils.isNotEmpty(sysUser.getEmployee())
                        ? sysUser.getEmployee().getEmployeeId()
                        : null;

        return new TtcUser(
                Long.valueOf(sysUser.getUserId()),
                sysUser.getUserName(),
                sysUser.getPassword(),
//                isEnabled(sysUser),
//                isAccountNonExpired(sysUser),
//                isCredentialsNonExpired(sysUser),
//                isNonLocked(sysUser),
                authorities);
    }

    /**
    /**
     * 判断
     *
     * @param sysUser sysUser
     * @return 是否成功
     * @since 2022.03
     */
    private static boolean isEnabled( SysUser sysUser) {
        return sysUser.getStatus() != DataItemStatusEnum.FORBIDDEN;
    }

    /**
    /**
     * 判断
     *
     * @param sysUser sysUser
     * @return 是否成功
     * @since 2022.03
     */
    private static boolean isNonLocked( SysUser sysUser) {
        return !(sysUser.getStatus() == DataItemStatusEnum.LOCKING);
    }

    /**
    /**
     * 判断
     *
     * @param localDateTime localDateTime
     * @return 是否成功
     * @since 2022.03
     */
    private static boolean isNonExpired(LocalDateTime localDateTime) {
        if (ObjectUtils.isEmpty(localDateTime)) {
            return true;
        } else {
            return localDateTime.isAfter(LocalDateTime.now());
        }
    }

    /**
    /**
     * 判断
     *
     * @param sysUser sysUser
     * @return 是否成功
     * @since 2022.03
     */
    private static boolean isAccountNonExpired( SysUser sysUser) {
        if (sysUser.getStatus() == DataItemStatusEnum.EXPIRED) {
            return false;
        }

        return isNonExpired(sysUser.getAccountExpireAt());
    }

    /**
    /**
     * 判断
     *
     * @param sysUser sysUser
     * @return 是否成功
     * @since 2022.03
     */
    private static boolean isCredentialsNonExpired( SysUser sysUser) {
        return isNonExpired(sysUser.getCredentialsExpireAt());
    }
}

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

package com.taotao.cloud.auth.authentication.dto;

import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>OAuth2 Scope Dto </p>
 *
 *
 * @since : 2022/4/1 13:55
 */
@Schema(name = "OAuth2 范围请求 Dto")
public class OAuth2ScopeDto {

    @Schema(name = "范围ID")
    @NotNull(message = "范围ID不能为空")
    private String scopeId;

    @Schema(name = "范围权限列表")
    private Set<OAuth2PermissionDto> permissions = new HashSet<>();

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */

    public String getScopeId() {
        return scopeId;
    }

    /**
     * 设置
     *
     * @param scopeId scopeId
     * @return 无返回值
     * @since 2022.03
     */

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public Set<OAuth2PermissionDto> getPermissions() {
        return permissions;
    }

    /**
     * 设置
     *
     * @param permissions permissions
     * @return 无返回值
     * @since 2022.03
     */

    public void setPermissions(Set<OAuth2PermissionDto> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("scopeId", scopeId).toString();
    }
}

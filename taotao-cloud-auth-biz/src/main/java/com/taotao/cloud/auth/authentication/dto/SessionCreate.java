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
import jakarta.validation.constraints.NotBlank;

/**
 * <p>加密创建请求 </p>
 *
 *
 * @since : 2021/10/1 15:19
 */
@Schema(title = "加密创建请求")
public class SessionCreate {

    @NotBlank(message = "客户端ID不能为空")
    @Schema(title = "客户端ID")
    private String clientId;

    @NotBlank(message = "客户端秘钥不能为空")
    @Schema(title = "客户端秘钥")
    private String clientSecret;

    @Schema(title = "未登录前端身份标识")
    private String sessionId;

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 设置
     *
     * @param clientId clientId
     * @return 无返回值
     * @since 2022.03
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * 设置
     *
     * @param clientSecret clientSecret
     * @return 无返回值
     * @since 2022.03
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * 获取
     *
     * @return 字符串
     * @since 2022.03
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * 设置
     *
     * @param sessionId sessionId
     * @return 无返回值
     * @since 2022.03
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("clientId", clientId)
                .add("clientSecret", clientSecret)
                .add("sessionId", sessionId)
                .toString();
    }
}

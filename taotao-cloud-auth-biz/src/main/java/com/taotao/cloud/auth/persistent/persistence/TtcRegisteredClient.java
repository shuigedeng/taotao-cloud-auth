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

package com.taotao.cloud.auth.persistent.persistence;

import com.baomidou.mybatisplus.annotation.TableName;
import com.taotao.cloud.auth.persistent.abstracts.AbstractRegisteredClient;
import jakarta.persistence.*;
import lombok.*;

/**
 * <p>OAuth2 客户端实体 </p>
 *
 * @author shuigedeng
 * @version 2023.07
 * @since 2023-07-10 17:12:44
 */
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
	name = TtcRegisteredClient.TABLE_NAME,
	indexes = {
		@Index(name = "idx_create_time", columnList = "create_time"),
		@Index(name = "idx_client_id", columnList = "client_id"),
		@Index(name = "idx_origin_id", columnList = "origin_id")
	})
@TableName(TtcRegisteredClient.TABLE_NAME)
@org.springframework.data.relational.core.mapping.Table(name = TtcRegisteredClient.TABLE_NAME)
//@Cacheable
//@org.hibernate.annotations.Cache(
//	usage = CacheConcurrencyStrategy.READ_WRITE,
//	region = OAuth2Constants.REGION_OAUTH2_REGISTERED_CLIENT)
public class TtcRegisteredClient extends AbstractRegisteredClient<TtcRegisteredClient> {
	public static final String TABLE_NAME = "ttc_oauth2_registered_client";

	/**
	 * 客户端id
	 */
	@Column(name = "origin_id", nullable = false, length = 100)
	private String originId;

    /**
     * 客户端id
     */
    @Column(name = "client_id", nullable = false, length = 100)
    private String clientId;

    /**
     * 客户秘密
     */
    @Column(name = "client_secret", length = 200)
    private String clientSecret;

    /**
     * 客户名称
     */
    @Column(name = "client_name", nullable = false, length = 200)
    private String clientName;

    /**
     * 范围
     */
    @Column(name = "scopes", nullable = false, length = 1000)
    private String scopes;

    /**
     * 客户端设置
     */
    @Column(name = "client_settings", nullable = false, length = 2000)
    private String clientSettings;

    /**
     * 令牌设置
     */
    @Column(name = "token_settings", nullable = false, length = 2000)
    private String tokenSettings;


    @Override
    public String getRegisteredClientId() {
        return String.valueOf(getId());
    }

    /**
     * 获取客户端id
     *
     * @return {@link String }
     * @since 2023-07-10 17:12:45
     */
    @Override
    public String getClientId() {
        return clientId;
    }

    /**
     * 获取客户秘密
     *
     * @return {@link String }
     * @since 2023-07-10 17:12:45
     */
    @Override
    public String getClientSecret() {
        return clientSecret;
    }




}

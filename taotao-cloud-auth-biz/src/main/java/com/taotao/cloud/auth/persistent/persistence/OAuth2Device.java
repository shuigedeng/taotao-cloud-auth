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

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.taotao.cloud.auth.persistent.abstracts.AbstractOAuth2RegisteredClient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>物联网设备管理 </p>
 *
 *
 * @since : 2023/5/15 14:26
 */
@Schema(name = "物联网设备")
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
	name = OAuth2Device.TABLE_NAME,
	uniqueConstraints = {@UniqueConstraint(columnNames = {"device_name"})},
	indexes = {
		@Index(name = "idx_create_time", columnList = "`create_time`"),
		@Index(name = "oauth2_device_id_idx", columnList = "device_id"),
		@Index(name = "oauth2_device_ipk_idx", columnList = "device_name"),
		@Index(name = "oauth2_device_pid_idx", columnList = "product_id")
	})
@TableName(OAuth2Device.TABLE_NAME)
@org.springframework.data.relational.core.mapping.Table(name = OAuth2Device.TABLE_NAME)
//@Cacheable
//@org.hibernate.annotations.Cache(
//        usage = CacheConcurrencyStrategy.READ_WRITE,
//        region = OAuth2Constants.REGION_OAUTH2_IOT_DEVICE)
public class OAuth2Device extends AbstractOAuth2RegisteredClient<OAuth2Device> {
	public static final String TABLE_NAME = "ttc_oauth2_device";

    @Schema(name = "设备ID")
    @Column(name = "device_id", length = 64)
    private String deviceId;

    @Schema(name = "设备名称")
    @Column(name = "device_name", length = 64, unique = true)
    private String deviceName;

    @Schema(name = "产品ID")
    @Column(name = "product_id", length = 64)
    private String productId;

    @Schema(name = "是否已激活", title = "设备是否已经激活状态标记，默认值false，即未激活")
    @Column(name = "is_activated")
    private Boolean activated = Boolean.FALSE;

	@TableField(exist = false)
	@Transient
    private Set<OAuth2Scope> scopes = new HashSet<>();

    @Override
    public Set<OAuth2Scope> getScopes() {
        return scopes;
    }

    @Override
    public String getRegisteredClientId() {
        return getDeviceId();
    }

}

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
import com.taotao.boot.webagg.entity.BasePO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

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
	name = OAuth2DeviceScopeRelation.TABLE_NAME,
	uniqueConstraints = {
		@UniqueConstraint(name = "uniq_device_id_scope_id",columnNames = {"device_id", "scope_id"})
	},
	indexes = {
		@Index(name = "idx_create_time", columnList = "create_time"),
		@Index(name = "idx_device_id", columnList = "device_id"),
		@Index(name = "idx_scope_id", columnList = "scope_id")
	})
@TableName(OAuth2DeviceScopeRelation.TABLE_NAME)
@org.springframework.data.relational.core.mapping.Table(name = OAuth2DeviceScopeRelation.TABLE_NAME)
public class OAuth2DeviceScopeRelation extends BasePO<OAuth2DeviceScopeRelation> {
	public static final String TABLE_NAME = "ttc_oauth2_device_scope";

    @Schema(name = "设备ID")
    @Column(name = "device_id", length = 64)
    private String device_id;

	@Schema(name = "设备ID")
	@Column(name = "scope_id", length = 64)
	private String scope_id;

}

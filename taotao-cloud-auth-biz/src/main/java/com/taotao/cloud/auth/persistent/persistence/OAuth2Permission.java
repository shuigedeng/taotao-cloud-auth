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
import jakarta.persistence.*;
import lombok.*;

/**
 * <p>客户端权限 </p>
 *
 *
 * @since : 2022/4/1 13:39
 */
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
	name = OAuth2Permission.TABLE_NAME,
	indexes = {
		@Index(name = "idx_create_time", columnList = "`create_time`"),
		@Index(name = "oauth2_permission_id_idx", columnList = "permission_id")
	})
@TableName(OAuth2Permission.TABLE_NAME)
@org.springframework.data.relational.core.mapping.Table(name = OAuth2Permission.TABLE_NAME)
//@Cacheable
//@org.hibernate.annotations.Cache(
//        usage = CacheConcurrencyStrategy.READ_WRITE,
//        region = OAuth2Constants.REGION_OAUTH2_PERMISSION)
public class OAuth2Permission  extends BasePO<OAuth2Permission> {
	public static final String TABLE_NAME = "ttc_oauth2_permission";
    @Column(name = "permission_id", length = 64)
    private String permissionId;

    @Column(name = "permission_code", length = 128)
    private String permissionCode;

    @Column(name = "permission_name", length = 128)
    private String permissionName;
}

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
import com.google.common.base.Objects;
import com.taotao.boot.webagg.entity.BasePO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.RevisionNumber;

/**
 * <p>OAuth2 认证确认信息实体 </p>
 *
 * @author shuigedeng
 * @version 2023.07
 * @since 2023-07-10 17:12:38
 */
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
	name = TtcAuthorizationConsent.TABLE_NAME,
	indexes = {
		@Index(name = "idx_create_time", columnList = "`create_time`"),
		@Index(name = "oauth2_authorization_consent_rcid_idx", columnList = "registered_client_id"),
		@Index(name = "oauth2_authorization_consent_pn_idx", columnList = "principal_name")
	})
@TableName(TtcAuthorizationConsent.TABLE_NAME)
@org.springframework.data.relational.core.mapping.Table(name = TtcAuthorizationConsent.TABLE_NAME)
//@Cacheable
//@org.hibernate.annotations.Cache(
//	usage = CacheConcurrencyStrategy.READ_WRITE,
//	region = OAuth2Constants.REGION_OAUTH2_AUTHORIZATION)
public class TtcAuthorizationConsent extends BasePO<TtcAuthorizationConsent> {

	public static final String TABLE_NAME = "ttc_oauth2_authorization_consent";

    /**
     * 注册客户端id
     */
    @Column(name = "registered_client_id", nullable = false, length = 100)
    private String registeredClientId;

    /**
     * 主体名称
     */
    @Column(name = "principal_name", nullable = false, length = 200)
    private String principalName;

    /**
     * 当局
     */
    @Column(name = "authorities", nullable = false, length = 1000)
    private String authorities;

    @Version
    @RevisionNumber
    @Column(name = "version", columnDefinition = "int not null default 1 comment '版本号'")
    private Long version = 1L;

    /**
     * 哈希码
     *
     * @return int
     * @since 2023-07-10 17:12:40
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(registeredClientId, principalName);
    }

}

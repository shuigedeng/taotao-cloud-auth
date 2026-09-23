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
import com.taotao.boot.security.spring.support.enums.ApplicationType;
import com.taotao.cloud.auth.persistent.abstracts.AbstractOAuth2RegisteredClient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>OAuth2 应用 </p>
 * <p>
 * Spring Authorization Server 默认的 RegisteredClient 不便于扩展。增加该类用于存储标准 RegisteredClient 表结构以外的扩展信息。
 *
 *
 * @since : 2022/3/1 16:45
 */
@Schema(name = "OAuth2应用实体")
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
	name = OAuth2Application.TABLE_NAME,
	indexes = {
		@Index(name = "idx_create_time", columnList = "create_time"),
		@Index(name = "idx_application_id", columnList = "application_id"),
		@Index(name = "idx_client_id", columnList = "client_id")
	})
@TableName(OAuth2Application.TABLE_NAME)
@org.springframework.data.relational.core.mapping.Table(name = OAuth2Application.TABLE_NAME)
//@Cacheable
//@org.hibernate.annotations.Cache(
//        usage = CacheConcurrencyStrategy.READ_WRITE,
//        region = OAuth2Constants.REGION_OAUTH2_APPLICATION)
public class OAuth2Application extends AbstractOAuth2RegisteredClient<OAuth2Application> {
	public static final String TABLE_NAME = "ttc_oauth2_application";

    @Schema(name = "应用ID")
    @Column(name = "application_id", length = 64)
    private String applicationId;

    @Schema(name = "应用名称")
    @NotBlank(message = "应用名称不能为空")
    @Column(name = "application_name", length = 128)
    private String applicationName;

    @Schema(name = "应用简称", title = "应用的简称、别名、缩写等信息")
    @Column(name = "abbreviation", length = 64)
    private String abbreviation;

    @Schema(name = "Logo", title = "Logo存储信息，可以是URL或者路径等")
    @Column(name = "logo", length = 1024)
    private String logo;

    @Schema(name = "主页信息", title = "应用相关的主页信息方便查询")
    @Column(name = "homepage", length = 1024)
    private String homepage;

    @Schema(name = "应用类型", title = "用于区分不同类型的应用")
    @Column(name = "application_type")
    @Enumerated(EnumType.ORDINAL)
    private ApplicationType applicationType = ApplicationType.WEB;

	@TableField(exist = false)
	@Transient
    private Set<OAuth2Scope> scopes = new HashSet<>();

    @Override
    public Set<OAuth2Scope> getScopes() {
        return scopes;
    }

    @Override
    public String getRegisteredClientId() {
        return getApplicationId();
    }


}

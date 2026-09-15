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
import com.taotao.boot.security.spring.support.constants.OAuth2Constants;
import com.taotao.boot.webagg.entity.BasePO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

/**
 * <p> Description : Oauth Scope </p>
 *
 *
 * @since : 2020/3/19 14:15
 */
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
	name = OAuth2Scope.TABLE_NAME,
	uniqueConstraints = {@UniqueConstraint(columnNames = {"scope_code"})},
	indexes = {
		@Index(name = "idx_create_time", columnList = "`create_time`"),
		@Index(name = "oauth2_scope_id_idx", columnList = "scope_id"),
		@Index(name = "oauth2_scope_code_idx", columnList = "scope_code")
	})
@TableName(OAuth2Scope.TABLE_NAME)
@org.springframework.data.relational.core.mapping.Table(name = OAuth2Scope.TABLE_NAME)
//@Cacheable
//@org.hibernate.annotations.Cache(
//        usage = CacheConcurrencyStrategy.READ_WRITE,
//        region = OAuth2Constants.REGION_OAUTH2_SCOPE)
public class OAuth2Scope extends BasePO<OAuth2Scope> {
	public static final String TABLE_NAME = "ttc_oauth2_scope";
    @Column(name = "scope_id", length = 64)
    private String scopeId;

    @Column(name = "scope_code", length = 128, unique = true)
    private String scopeCode;

    @Column(name = "scope_name", length = 128)
    private String scopeName;

    @TableField(exist = false)
	@Transient
    private Set<OAuth2Permission> permissions = new HashSet<>();

}

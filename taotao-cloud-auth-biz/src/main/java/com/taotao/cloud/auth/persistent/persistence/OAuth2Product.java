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
 * <p>物联网产品 </p>
 *
 *
 * @since : 2023/5/15 14:26
 */
@Schema(name = "物联网产品")
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
	name = OAuth2Product.TABLE_NAME,
	uniqueConstraints = {@UniqueConstraint(columnNames = {"product_key"})},
	indexes = {
		@Index(name = "idx_create_time", columnList = "`create_time`"),
		@Index(name = "oauth2_product_pid_idx", columnList = "product_id"),
		@Index(name = "oauth2_product_ipk_idx", columnList = "product_key")
	})
@TableName(OAuth2Product.TABLE_NAME)
@org.springframework.data.relational.core.mapping.Table(name = OAuth2Product.TABLE_NAME)
//@Cacheable
//@org.hibernate.annotations.Cache(
//        usage = CacheConcurrencyStrategy.READ_WRITE,
//        region = OAuth2Constants.REGION_OAUTH2_IOT_PRODUCT)
public class OAuth2Product extends BasePO<OAuth2Product> {
	public static final String TABLE_NAME = "ttc_oauth2_product";

    @Column(name = "product_id", length = 64)
    private String productId;

    @Column(name = "product_key", length = 32, unique = true)
    private String productKey;
}

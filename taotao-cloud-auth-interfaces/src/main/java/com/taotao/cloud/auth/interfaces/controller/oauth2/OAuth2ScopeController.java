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

package com.taotao.cloud.auth.interfaces.controller.oauth2;

import com.taotao.boot.common.model.Result;
import com.taotao.boot.webagg.controller.BusinessController;
import com.taotao.cloud.auth.infrastructure.authentication.service.OAuth2ScopeService;
import com.taotao.cloud.auth.infrastructure.persistent.oauth2.persistence.OAuth2PermissionPO;
import com.taotao.cloud.auth.infrastructure.persistent.oauth2.persistence.OAuth2ScopePO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p> Description : OauthScopesController </p>
 *
 * @since : 2020/3/25 17:10
 */
@RestController
@RequestMapping("/authorize/scope")
@Tags({@Tag(name = "OAuth2 认证服务接口"), @Tag(name = "OAuth2 权限范围管理接口")})
public class OAuth2ScopeController extends BusinessController {

	private final OAuth2ScopeService scopeService;

	@Autowired
	public OAuth2ScopeController(OAuth2ScopeService scopeService) {
		this.scopeService = scopeService;
	}

	@Operation(
		summary = "给Scope分配权限",
		description = "给Scope分配权限",
		responses = {
			@ApiResponse(
				description = "查询到的角色",
				content =
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = OAuth2ScopeDto.class))),
		})
	@Parameters({
		@Parameter(name = "scope", required = true, description = "范围请求参数"),
	})
	@PostMapping("/assigned")
	public Result<OAuth2ScopePO> assigned(@RequestBody OAuth2ScopeDto scope) {

		Set<OAuth2PermissionPO> permissions = new HashSet<>();
		if (CollectionUtils.isNotEmpty(scope.getPermissions())) {
			permissions = scope.getPermissions().stream().map(this::toEntity)
				.collect(Collectors.toSet());
		}

		OAuth2ScopePO result = scopeService.assigned(scope.getScopeId(), permissions);
		return Result.success(result);
	}

	//	@AccessLimited
	@Operation(
		summary = "获取全部范围",
		description = "获取全部范围",
		responses = {
			@ApiResponse(
				description = "全部数据列表",
				content =
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = Result.class))),
			@ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
			@ApiResponse(responseCode = "500", description = "查询失败")
		})
	@GetMapping("/list")
	public Result<List<OAuth2ScopePO>> findAll() {
		List<OAuth2ScopePO> oAuth2ScopePOS = scopeService.findAll();
		return Result.success(oAuth2ScopePOS);
	}

	//	@AccessLimited
	@Operation(
		summary = "根据范围代码查询应用范围",
		description = "根据范围代码查询应用范围",
		responses = {
			@ApiResponse(
				description = "查询到的应用范围",
				content =
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = OAuth2ScopePO.class))),
			@ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
			@ApiResponse(responseCode = "500", description = "查询失败")
		})
	@GetMapping("/{scopeCode}")
	public Result<OAuth2ScopePO> findByUserName(@PathVariable("scopeCode") String scopeCode) {
		OAuth2ScopePO scope = scopeService.findByScopeCode(scopeCode);
		return Result.success(scope);
	}

	private OAuth2PermissionPO toEntity(OAuth2PermissionDto dto) {
		OAuth2PermissionPO entity = new OAuth2PermissionPO();
		entity.setPermissionId(dto.getPermissionId());
		entity.setPermissionCode(dto.getPermissionCode());
		entity.setPermissionName(dto.getPermissionName());
		return entity;
	}
}

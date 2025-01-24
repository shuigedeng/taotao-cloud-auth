package com.taotao.cloud.auth.domain.authorization.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.taotao.cloud.auth.domain.authorization.entity.Authorization;
import com.taotao.cloud.auth.domain.authorization.repository.AuthorizationRepository;
import com.taotao.cloud.auth.domain.authorization.service.AuthorizationDomainService;
import com.taotao.boot.common.model.PageQuery;

public class AuthorizationDomainServiceImpl implements AuthorizationDomainService {

	private AuthorizationRepository authorizationRepository;

	@Override
	public Boolean insert(Authorization authorization) {
		return null;
	}

	@Override
	public Boolean update(Authorization authorization) {
		return null;
	}

	@Override
	public Authorization getById(Long id) {
		return null;
	}

	@Override
	public Boolean deleteById(Long id) {
		return null;
	}

	@Override
	public IPage<Authorization> list(Authorization authorization, PageQuery pageQuery) {
//		return deptRepository.list(deptEntity, pageQuery);
		return null;
	}
}

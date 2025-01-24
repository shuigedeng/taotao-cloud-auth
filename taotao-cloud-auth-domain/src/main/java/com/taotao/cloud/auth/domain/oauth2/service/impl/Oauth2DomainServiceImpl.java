package com.taotao.cloud.auth.domain.oauth2.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.taotao.cloud.auth.domain.oauth2.entity.Oauth2;
import com.taotao.cloud.auth.domain.oauth2.repository.Oauth2Repository;
import com.taotao.cloud.auth.domain.oauth2.service.Oauth2DomainService;
import com.taotao.boot.common.model.PageQuery;

public class Oauth2DomainServiceImpl implements Oauth2DomainService {

	private Oauth2Repository oauth2Repository;

	@Override
	public Boolean insert(Oauth2 oauth2) {
		return null;
	}

	@Override
	public Boolean update(Oauth2 oauth2) {
		return null;
	}

	@Override
	public Oauth2 getById(Long id) {
		return null;
	}

	@Override
	public Boolean deleteById(Long id) {
		return null;
	}

	@Override
	public IPage<Oauth2> list(Oauth2 oauth2, PageQuery pageQuery) {
//		return deptRepository.list(deptEntity, pageQuery);
		return null;
	}
}

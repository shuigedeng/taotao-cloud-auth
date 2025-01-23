

package com.taotao.cloud.auth.application.command.management.executor.query;

import com.taotao.cloud.auth.application.adapter.DictAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * 查看字典执行器.
 */
@Component
@RequiredArgsConstructor
public class DictGetQryExe {

	private final DictDomainService dictDomainService;
	private final DictAdapter dictAdapter;
	private final DictConvert dictConvert;
	private final DictMapper dictMapper;

	/**
	 * 执行查看字典.
	 * @param qry 查看字典参数
	 * @return 字典
	 */
	//@DS(TENANT)
	public DictCO execute(DictGetQry qry) {
		return dictConvert.convert(dictDomainService.getById(qry.getId()));
	}

}

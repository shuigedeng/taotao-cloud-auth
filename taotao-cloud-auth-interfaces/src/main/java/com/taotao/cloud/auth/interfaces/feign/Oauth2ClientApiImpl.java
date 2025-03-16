package com.taotao.cloud.auth.interfaces.feign;

import com.taotao.boot.webagg.controller.FeignController;
import com.taotao.cloud.auth.api.feign.Oauth2ClientApi;
import com.taotao.cloud.auth.api.feign.request.FeignClientQueryApiRequest;
import com.taotao.cloud.auth.api.feign.response.ClientApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 为远程客户端提供粗粒度的调用接口
 */
@Validated
@RestController
@RequestMapping("/sys/feign/dict")
public class Oauth2ClientApiImpl extends FeignController implements Oauth2ClientApi {


	@Override
	public ClientApiResponse query(FeignClientQueryApiRequest feignClientQueryApiRequest) {
		return null;
	}
}

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

package com.taotao.cloud.auth.interfaces.feign;

import com.taotao.boot.webagg.controller.ApiController;
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
public class Oauth2ClientApiImpl extends ApiController implements Oauth2ClientApi {

    @Override
    public ClientApiResponse query(FeignClientQueryApiRequest feignClientQueryApiRequest) {
        return null;
    }
}

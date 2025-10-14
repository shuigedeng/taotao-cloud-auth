package com.taotao.cloud.auth.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "taotao.cloud.goods")
public class GoodsProperties {
}

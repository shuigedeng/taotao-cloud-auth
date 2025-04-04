

package com.taotao.cloud.auth.application.command.management.dto.clientobject;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

/**
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "OptionCO", description = "下拉框选择参数项参数")
public class OptionCO  {

	@Serial
	private static final long serialVersionUID = -4146348495335527374L;

	@Schema(name = "label", description = "标签")
	private String label;

	@Schema(name = "value", description = "值")
	private String value;

}

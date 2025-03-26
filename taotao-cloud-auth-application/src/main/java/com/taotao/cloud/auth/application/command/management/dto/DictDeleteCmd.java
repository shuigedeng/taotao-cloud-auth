

package com.taotao.cloud.auth.application.command.management.dto;

import com.taotao.boot.ddd.model.application.dto.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "DictDeleteCmd", description = "删除字典命令请求")
public class DictDeleteCmd extends Command {

	@Schema(name = "id", description = "ID")
	private Long id;

}

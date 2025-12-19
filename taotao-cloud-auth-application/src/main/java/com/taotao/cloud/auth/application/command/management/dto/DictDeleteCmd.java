package com.taotao.cloud.auth.application.command.management.dto;

import com.taotao.boot.ddd.model.application.dto.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * DictDeleteCmd
 *
 * @author shuigedeng
 * @version 2026.01
 * @since 2025-12-19 09:30:45
 */
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

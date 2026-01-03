package com.taotao.cloud.auth.application.command.oauth2.dto;

import com.taotao.boot.ddd.model.application.CommonCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

/**
 * DictDeleteCmd
 *
 * @author shuigedeng
 * @version 2026.02
 * @since 2025-12-19 09:30:45
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "DictDeleteCmd", description = "删除字典命令请求")
public class DictDeleteCmd extends CommonCommand {

    @Schema(name = "id", description = "ID")
    private Long id;

}

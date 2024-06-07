package com.gfk.business.user.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author wzl
 * @version 1.0 2023/6/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectMappingAddForm {
    /**
     * 权限code
     */
    @ApiModelProperty("权限code")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 权限ID
     */
    @ApiModelProperty("项目ID")
    @NotNull(message = "项目ID不能为空")
    private Long projectId;
}

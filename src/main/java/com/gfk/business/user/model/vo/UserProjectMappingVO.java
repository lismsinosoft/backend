package com.gfk.business.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户权限VO
 *
 * @author wzl
 * @version 1.1 2024/1/30
 */
@Data
@ApiModel("用户项目权限")
@NoArgsConstructor
@AllArgsConstructor
public class UserProjectMappingVO {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不得为空")
    private Long userId;
    @ApiModelProperty("用户名称")
    private String userName;
    /**
     * 项目id
     */
    @ApiModelProperty("项目id")
    @NotNull(message = "项目id不得为空")
    private Long projectId;
    @ApiModelProperty("项目名称")
    private String projectName;
    @ApiModelProperty("项目英文名称")
    private String projectNameEn;
    @ApiModelProperty("项目图片地址")
    private String picUrl;
    @ApiModelProperty("项目图片地址2")
    private String picUrl2;

    @ApiModelProperty("权限列表")
    private List<String> permissionList;
}

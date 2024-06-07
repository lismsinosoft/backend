package com.gfk.business.user.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author : 王哲霖
 * @date : 2022/03/15
 * @description :
 **/
@Data
public class PositionTreeNodeVO {
    /**
     * 节点code
     */
    @ApiModelProperty("节点code")
    private String code;

    /**
     * 所属层级
     */
    @ApiModelProperty("所属层级")
    private String level;

    /**
     * 节点所属用户id
     */
    @ApiModelProperty("节点所属用户id")
    private String staffId;

    /**
     * 前端显示名称
     */
    @ApiModelProperty("前端显示名称")
    private String displayName;

    /**
     * 所属BU
     */
    @ApiModelProperty("所属BU")
    private String bu;

    private List<PositionTreeNodeVO> children;
}

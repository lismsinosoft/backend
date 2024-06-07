package com.gfk.business.user.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户数量统计结果VO
 *
 * @author whk
 * @date 2021/03/24
 **/
@ApiModel("用户数量统计结果VO")
@Data
public class UserNumStatisticResultVO {

    /**
     * 总人数
     */
    @ApiModelProperty("总人数")
    private Integer total;

    /**
     * 已注册
     */
    @ApiModelProperty("已注册")
    private Integer registered;

    /**
     * 未注册
     */
    @ApiModelProperty("未注册")
    private Integer unregistered;
}
